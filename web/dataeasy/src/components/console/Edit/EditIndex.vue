<template>

<!-- 编辑数据大屏页面组件绘制页 -->
  <div class="editor-view" v-if="chartData">
    <!-- 顶部组件 -->
    <transition name="fade"><div class="top-bar-view" v-show="!isFullScreen"><EditTopBar></EditTopBar></div></transition>

    <!-- 侧边工具栏 -->
    <transition name="fade"><div class="toolbar-view" v-show="!preview">
        <!--   占位div   -->
        <div style="height: 5%;"></div>
        <EditToolBar></EditToolBar>
      </div></transition>

    <!-- 编辑控件区域 -->
    <transition name="fade"><div class="config-view" v-show="!preview"><EditConfig></EditConfig></div></transition>

    <!-- 缩放组件 -->
    <transition name="fade"><div class="scale-view" v-show="!preview"><EditScaleBar @updateScale="updateScale"></EditScaleBar></div></transition>

    <!-- 画布组件 传递缩放数据给画布 -->
    <div class="main-view" :class="{'main-view-preview': preview, 'main-view-preview-fullscreen' : isFullScreen}" ><router-view ref="exportContent"></router-view></div>

    <!-- 警告提示 -->
    <div class="dangerTips-active" :class="{'dangerTips-inactive': !pictureWaittingUpload}" v-show="!preview">您有未上传的图片，请先进行图片上传</div>

  </div>
</template>

<script>
import html2canvas from 'html2canvas';

export default {
  name: "EditIndex",

  components: {
    EditTopBar: () => import('../../views/edit/EditTopBar'),
    EditToolBar: () => import('../../views/edit/EditToolBar'),
    EditConfig: () => import('../../views/edit/EditConfig'),
    EditScaleBar: () => import('../../views/edit/EditScaleBar'),
  },

  data() {
    return {
      title: '',
      preview: false,

      // scale: number  缩放比例（只能等比例缩放）
      scale: 1.0,

      chartid: this.$route.query.chartid,

      //可视化大屏数据, 里面记录了可视化大屏的数据，控件的数据，一般来说有下面的属性
      chartData: {},

      //用户数据List
      userData: [],

      //是否为编辑，还是新创建的项目 true为编辑，false为新创建项目
      isEditor: this.$route.query.editor,

      //当前活跃的控件的索引(-1为初始状态，表明当前活跃的是画布)
      currentElementIndex: -1,

      submitStatus: false,

      pictureWaittingUpload: false,

      //需要删除的控件的id
      deleteElementid: [],

      //是否为全屏状态
      isFullScreen: false
    };
  },

  computed: {
    //返回当前活跃的控件的索引(-1为画布， >=0才是控件)
    getCurrentElementIndex: function () {
      return this.currentElementIndex
    },

    //通过索引拿到当前活跃的控件
    getCurrentElement: function () {
      if (this.currentElementIndex < 0) {
        return this.chartData
      }
      else {
        return this.chartData.elements[this.currentElementIndex]
      }
    },
  },
  watch: {
    //是否为预览模式
    preview: {
      handler() {
        if (this.preview) {
          this.$router.push({
            path: "/Edit/PreviewCanvas",
            query: {
              chartid: this.chartid
            }
          })
        }
        else {
          this.$router.push({
            path: "/Edit/EditCanvas",
            query: {
              chartid: this.chartid
            }
          })
        }
      }
    },
  },

  mounted() {
    this.preview = false
    this.isFullScreen = false
    this.getChartAllData()
    this.getUserData()
  },

  beforeDestroy() {
    this.preview = false
  },

  methods: {
    //更新缩放系数
    updateScale: function (scale) {
      console.log(scale)
      return this.scale = scale
    },
    //设置当前活跃元素（currentElement）的Index（从Canvas中获取）， -1为画布， >=0为控件
    setActiveComponentByIndex: function (index) {
      //设定当前活跃元素的index为传入的index
      this.currentElementIndex = index
      //如果当前画布有控件存在
      if (this.chartData.elements) {
        if (this.chartData.elements.length !== 0) {
          //当传入的参数为-1， 不进行遍历（-1为画布在活跃）
          if (index >= 0) {
            //将控件遍历， 并更改其状态
            for (let i = 0; i < this.chartData.elements.length; i++) {
              const activeElement = this.chartData.elements[i]
              //当控件index与正在活跃的index一致， 则更改该控件的状态为true
              if (index === i) {
                activeElement.active = true
              }
              //不一致，为false
              else {
                activeElement.active = false
              }
            }
          }
          //如果传入的索引为-1，也即桌面，将全部控件的active变为false
          else {
            for (let i = 0; i < this.chartData.elements.length; i++) {
              this.chartData.elements[i].active = false
            }
          }
        }
      }
      else {
        console.log("没有控件")
      }
    },

    //添加控件
    addComponent: function (element) {
      this.chartData.elements.push(element)
    },
    //移除控件
    removeComponent: function (elementid) {
      this.setActiveComponentByIndex(-1)
      console.log("传入", elementid)
      this.chartData.elements = this.chartData.elements.filter((item) => {
        return item.elementid !== elementid
      })

      if (elementid !== -1) {
        this.deleteElementid.push(elementid)
      }
    },

    //根据用户id和传入的图表id，来获取全部的数据
    getChartAllData: function () {
      return (async () => {
        try {
          const response = await this.$axios.post("chart/getAllCurrentChartData", {
            userid: localStorage.getItem("id"),
            chartid: this.chartid,
          })
          console.log("获取该画布的全部控件数据", response.data)
          this.merge_element_base_data(response.data.elements)
          this.chartData = response.data
          this.setActiveComponentByIndex(-1)
          return true
        } catch (error) {
          console.log(error)
          return false
        }
      })()
    },

    //获取用户的全部数据
    getUserData: function () {
      this.$axios.post("userdata/getUserData", {
        userid: localStorage.getItem("id")
      })
          .then((response) => {
            response.data.forEach((item) => {
              item.metrics = JSON.parse(item.metrics)
              item.columns = JSON.parse(item.columns)
              item.exdata = JSON.parse(item.exdata)
            })
            console.log("用户数据获取", response.data)
            this.userData = response.data
          })
    },


    //保存画布数据-分多步进行
    submit: async function () {
      this.setActiveComponentByIndex(-1)
      console.log("开始上传画布数据")
      let res
      if (this.pictureWaittingUpload) {
        res = await this.$confirm("您有未上传的图片，继续上传将不会保存未上传的图片，确定要直接保存吗", "Tips", {
          confirmButtonText: "确定",
          confirmButtonClass: "el-button el-button--warning",
          cancelButtonText: "返回"
        })
            .then(() => {return true}
              , () => {return false})
      }
      else {
        res = await this.$confirm("确认上传数据？", "Tips", {
          confirmButtonText: "确认",
          cancelButtonClass: "返回"
        })
            .then(() => {return true},
                () => {return false})
      }
      if (res) {
        this.openFullScreenLock()
        let chartData = this.chartData
        //画布数据上传
        await this.submit_Canvas_Request(chartData)
        // 将控件基本属性和控件详细属性分离 elementList[0] 是基础属性， [1]是详细属性
        let elementList = this.upload_element_deal_to_base_data(chartData)
        //发送控件基础数据请求
        await this.submit_ElementBase_Request(elementList[0])
        //发送控件详细信息请求
        await this.submit_ElementData_Request(elementList[1])
        //发送需要删除的组件的数组
        await this.submit_DeleteElement_Request()

        this.$message({
          type: "success",
          message: "数据保存成功，页面即将刷新"
        })

        setTimeout(() => {
          location.reload()
        }, 2000)
      }
    },


    //接收到baseList和dataList， 进行处理和结合
    merge_element_base_data: function (elements) {
      let data = []
      data = elements
      data.forEach((item) => {
        item.data = JSON.parse(item.data)
      })
      return data
    },
    //画布数据上传请求, 上传的数据中不包括画布标题， 画布封面， 画布背景
    submit_Canvas_Request: function (chartData) {
      this.$axios.post("chart/saveChart", {
        userid: localStorage.getItem("id"),
        chartid: this.chartid,
        width: chartData.width,
        height: chartData.height,
        top: chartData.top,
        rightresever: chartData.rightresever,
        backgroundsetting: chartData.backgroundsetting,
        backgroundcolor: chartData.backgroundcolor,
        backgroundimagesize: chartData.backgroundimagesize,
      }, {
        headers: {
          "Content-Type":"application/json"
        }
      })
          .then(() => {
            return true
          }
          ,() => {
            return false
              }
          )
    },
    //画布控件数据处理，将控件分为Base和Data部分
    upload_element_deal_to_base_data: function (chartData) {
      const elementBaseList = []
      const elementDataList = []

      chartData.elements.forEach((item) => {
        elementDataList.push(item.data)
        delete item['data']
        elementBaseList.push(item)
      })

      elementDataList.forEach((item, index) => {
        elementDataList[index] = {
          chartid: chartData.chartid,
          elementid: elementBaseList[index].elementid,
          data: JSON.stringify(item).toString()
        }
      })

      return [elementBaseList, elementDataList]
    },
    //控件基础数据上传
    submit_ElementBase_Request: function (elementBaseDataList) {
      console.log("控件基础数据上传")
      this.$axios.post("chart/saveElementBase", elementBaseDataList, {
        headers: {
          "Content-Type": "application/json"
        }
      })
          .then(() => {

          })
    },
    //控件详细数据上传
    submit_ElementData_Request: function (elementDataList) {
      console.log("控件详细数据上传")
      this.$axios.post("chart/saveElementData", elementDataList, {
        headers: {
          "Content-Type": "application/json"
        }
      })
          .then(() => {

          })
    },
    //上传需要删除的控件的idList
    submit_DeleteElement_Request: function () {
      this.$axios.post("chart/deleteElement", {
        chartid: this.chartid,
        deleteElementIdList: this.deleteElementid.toString(),
      })
    },

    //将画布内容变为图片并下载
    exportAsImage: function () {
      //需要获取的节点内容
      const content = this.$refs.exportContent.getContentData
      //配置html2canvas
      html2canvas(content, {
        useCORS: true
      })
          .then((canvas) => {
            const imageData = canvas.toDataURL("image/png")
            let link = document.createElement("a")
            link.href = imageData
            link.download = this.chartData.title + '_image.png'
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link)
          })
    },

    //页面遮罩
    openFullScreenLock: function () {
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: 'rgba(0, 0, 0, 0.7)'
      })
      setTimeout(() => {
        loading.close()
      }, 3000)
    },

  },
}
</script>

<style scoped>
.editor-view {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.top-bar-view {
  position: absolute;
  height: 60px;
  width: 100vw;
  z-index: 1000;
}

.toolbar-view {
  position: absolute;
  top: 60px;
  width: 50px;
  height: calc(100% - 60px);
  min-height: 560px;
  bottom: 0;
  z-index: 1000;
}

.config-view {
  position: absolute;
  right: 0;
  top: 60px;
  width: 300px;
  bottom: 0;
  z-index: 1000;
}

.scale-view {
  position: absolute;
  right: 316px;
  bottom: 16px;
  z-index: 1000;
}
.scale-view::before {
  right: 40px;
}


.main-view {
  min-width: 1550px;
  min-height: 920px;
  padding: 60px 310px 0 60px;
  height: calc(100vh - 60px);
  background: whitesmoke;
  transition: 0.5s ease;
}

.main-view-preview {
  padding: 60px 185px 0 185px;
  transition: 0.5s ease;
}

.main-view-preview-fullscreen {
  background: black;
  padding: 100px 185px 30px 185px;
  transition: 0.5s ease;
}


.dangerTips-active {
  position: absolute;
  width: 270px;
  height: 50px;
  line-height: 50px;
  font-size: 14px;
  text-align: center;
  left: 10px;
  bottom: 10px;
  color: whitesmoke;
  background: #F56C6C;
  border: 1px solid #ff1d1d;
  border-radius: 12px;
  transition: 1s ease;
}

.dangerTips-inactive {
  left: -280px;
  transition: 1s ease;
}

/*动画效果*/
.fade-enter-active, .fade-leave-active{
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

</style>
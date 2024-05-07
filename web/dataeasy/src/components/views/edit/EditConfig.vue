<template>
  <div class="config">

    <div style="overflow-y: scroll; height: 100%;">
      <!--  选择区域 根据当前控件的类型显示菜单 -->
      <div class="panel-selector">
        <!--  选择区域  -->
        <div class="radio-group">
          <el-row v-show="currenElementType === 'screen'">
            <el-col>
              <div class="radio-btn" :class="{'radio-btn-active': currenElementType === 'screen'}">画布控件设置</div>
            </el-col>
          </el-row>

          <!--  编辑组件顶部信息选择栏  -->
          <el-row v-show="currenElementType !== 'screen'">
            <el-col class="radio-group-row-col">
              <div class="radio-btn" :class="{'radio-btn-active': thisKey === 'general'}" @click="thisKey = 'general'">控件基础设置</div>
            </el-col>
            <el-col class="radio-group-row-col">
              <div v-show="currenElementType === 'chart'"  class="radio-btn" :class="{'radio-btn-active': thisKey === 'data'}" @click="thisKey = 'data'">图表控件设置</div>
              <div v-show="currenElementType === 'text'"   class="radio-btn" :class="{'radio-btn-active': thisKey === 'data'}" @click="thisKey = 'data'">文本控件设置</div>
              <div v-show="currenElementType === 'image'"  class="radio-btn" :class="{'radio-btn-active': thisKey === 'data'}" @click="thisKey = 'data'">图片控件设置</div>
              <div v-show="currenElementType === 'border'" class="radio-btn" :class="{'radio-btn-active': thisKey === 'data'}" @click="thisKey = 'data'">边框控件设置</div>
            </el-col>
          </el-row>
        </div>
      </div>

      <!--  画布控件设置区域  -->
      <div class="panel" v-show="currenElementType === 'screen'">
        <CanvasConfig></CanvasConfig>
      </div>

      <!--  控件通用设置区域  -->
      <div class="panel" v-show="thisKey === 'general' &amp;&amp; currenElementType !== 'screen'">
        <GeneralConfig></GeneralConfig>
      </div>

      <!--  文本控件配置区域  -->
      <div class="panel" v-if="thisKey === 'data' &amp;&amp; currenElementType === 'text'">
        <TextConfig></TextConfig>
      </div>

      <!--  边框控件配置区域  -->
      <div class="panel" v-if="thisKey === 'data' &amp;&amp; currenElementType === 'border'">
        <BorderConfig></BorderConfig>
      </div>

      <!--  图片控件配置区域  -->
      <div class="panel" v-if="thisKey === 'data' &amp;&amp; currenElementType === 'image'">
        <ImageConfig></ImageConfig>
      </div>

      <div class="panel" v-if="thisKey === 'data' &amp;&amp; currenElementType === 'chart'">
        <ChartConfig></ChartConfig>
      </div>
    </div>
  </div>
</template>


<script>
// import vueJsonEditor from 'vue-json-editor'
export default {
  name: "EditConfig",

  components: {
    // vueJsonEditor
    CanvasConfig: () => import("./configBox/CanvasConfig"),
    GeneralConfig: () => import("./configBox/GeneralConfig"),
    TextConfig: () => import("./configBox/TextConfig"),
    BorderConfig: () => import("./configBox/BorderConfig"),
    ImageConfig: () => import("./configBox/ImageConfig"),
    ChartConfig: () => import("./configBox/ChartConfig")
  },


  data() {
    return {
      //所点击的控制块
      thisKey: 'general',
      editorSettings: {
        parentBg: 0, // 0代表背景颜色，1代表背景图片
        parentBgUrl: '',
      },

      colorInput: "",

      transparentBackground: false,
      //默认为画布
      currenElementType: 'screen',

      //预览图
      previewImage: "",
    }
  },

  computed: {
    //获取当前活跃的控件的索引
    getCurrentElementIndex: function () {
      return this.$parent.currentElementIndex
    },

    //获取当前活跃的控件
    getCurrentElement: function () {
      return this.$parent.getCurrentElement
    },

  },

  watch: {
    //获取活跃元素的index，防止多次点击而造成浏览器压力
    getCurrentElementIndex: {
      handler(newValue, oldValue) {
        if (newValue !== oldValue) {
          this.currenElementType = this.showPanel()
        }
      }
    },

    //如果点击的是画布，点击控件时自动切回控件基础设置面板
    currenElementType: {
      handler() {
        if (this.currenElementType === 'screen') {
          this.thisKey = 'general'
        }
      }
    },
  },


  mounted() {

  },

  methods: {
    //输入规则

    //获取当前控件的类型，如果是画布，则直接返回画布，如果是控件，则根据控件的类型来返回对应的数值
    showPanel: function () {
      let currentElementIndex = this.getCurrentElementIndex
      if(currentElementIndex < 0) {
        return 'screen'
      }
      else {
        return this.getCurrentElement.type
      }
    },
    //上传图片的统一检查
    handleBackgroundImageUploadCheck: function (file) {
      //检验文件是否为指定的格式
      const fileTypeLimit = ['jpg', 'png', 'jpeg']
      let thisFileType = file.name.split(".")[1]
      const typeResult = fileTypeLimit.some((item) => {
        return item === thisFileType
      })
      //如果文件不合规
      if (!typeResult) {
        return false
      }
      //检验上传的文件的大小是否合规
      if (file.size / 1024 / 1024 > 10) {
        return false
      }
      return true
    },


    test: function (fileObject) {
      console.log(fileObject)
    },

    //移除控件
    removeComponent: function () {
      this.$parent.removeComponent(this.getCurrentElement.elementid)
    }
  },
}
</script>


<style scoped>
.config {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  flex-direction: column;
  background: #ffffffe9;
  color: #515151;
  box-shadow: -4px 0 4px #00000005;
  padding: 0;
  overflow: hidden;
}
.panel {
  overflow-x: hidden;
  user-select: none;
}

.test > template {
  width: 50px;
}

.panel-selector {
  z-index: 100;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.06);
}

.radio-group {
  text-align: center;
}

.radio-group-row {
  display: flex;
  justify-content: center;
  align-content: center;
}

.radio-group-row-col {
  width: 50%;
}

.radio-btn {
  display: inline-block;
  padding: 20px 10px;
  margin: 0 10px;
  color: #999999;
  border-bottom: 2px solid transparent;
}

.radio-btn-active {
   color: #212121;
   border-bottom: 2px solid #212121;
}


.component-config /deep/ .jsoneditor-menu {
  display: none;
}

.num-input > el-input{
  background-color: #ffffff !important;
}

.bg-uploader /deep/ .el-upload:hover {
  border-color: #409eff;
}
</style>

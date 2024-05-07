<template>
  <div class="header">
    <!--  返回按钮  -->
    <div style="min-width: 70px">
      <div class="publish-btn back-btn" v-show="!this.$parent.preview" @click="backToChartView">返回</div>
    </div>
    <!--  标题  -->
    <div class="filename">{{this.$parent.chartData.title}}</div>


    <div class="button-box">
      <!--  预览按钮 点击后向父组件传递需要预览信息 -->
      <div class="publish-btn preview-btn" style="margin-right: 20px" @click="toPreviewMode">{{previewButtonText}}</div>
      <!--  发布按钮  -->
      <div class="publish-btn" v-show="!this.$parent.preview" @click="triggerSubmit">{{pubButtonText}}</div>
      <!--  另存为图片按钮  -->
      <div v-show="this.$parent.preview"  class="publish-btn save-btn" @click="triggerSavaAsPicture">另存为图片</div>
    </div>
  </div>
</template>

<script>
export default {

  name: 'EditTopBar',

  props: ['preview'],

  data() {
    return {
      isSave: false,
      previewButtonText: '预览',
    };
  },
  computed: {
    pubButtonText: function () {
      if (this.$parent.isEditor) {
        return "保存"
      }
      else {
        return "发布"
      }
    }
  },
  methods: {
    //返回到可视化编辑数据界面
    backToChartView: function () {
      if (!this.isSave) {
        this.$confirm("您有未保存的可视化大屏编辑数据，是否取消保存并返回工作台", "未保存（未发布）提示", {
          confirmButtonText: "确定返回",
          confirmButtonClass: "el-button-danger",
          cancelButtonText: "回到画布",
          cancelButtonClass: "el-button-warning"
        })
            //  点击确定返回（未保存返回）,返回到工作台（我的可视化区域）
            .then(() => {
              this.$parent.preview = false
              this.$router.push('/console/ChartView')
            }
            ,() => {}
            )
      }
    },

    //进入到预览模式
    toPreviewMode: function () {
      this.changePreviewButtonText()
      return this.$parent.preview = !this.$parent.preview
    },

    //切换预览按钮的文字
    changePreviewButtonText: function () {
      if (!this.$parent.preview) {
         this.previewButtonText = '退出预览'
      }
      else {
        this.previewButtonText = "预览"
      }
    },

    //触发保存
    triggerSubmit: function () {
      this.$parent.submitStatus = true
      this.$parent.submit()
    },

    triggerSavaAsPicture: function () {
      this.$confirm("是否另存为图片", "Tips", {
        confirmButtonText: "确认",
        cancelButtonText: "取消"
      })
          .then(() => {
            this.$parent.exportAsImage()
              }
          ,() => {

              })
    },

  },
};
</script>

<style  scoped>
.header {
  height: 100%;
  display: flex;
  align-items: center;
  background: #333333ee;
  padding: 0 20px;
}

.filename {
  /*标题样式*/
  flex: 1;
  text-align: center;
  font-size: 16px;
  color: #ffffff;
  user-select:none;
}

.publish-btn {
  /*发布按钮样式*/
  height: 36px;
  line-height: 36px;
  color: #0099CC;
  border-radius: 6px;
  border: 1px solid #0099CC;
  padding: 0 18px;
  transition: all 0.3s ease;
  user-select:none;
}
.publish-btn:hover {
  border: 1px solid #0099CC;
  cursor: pointer;
  color: white;
  background-color: #008CBA
}

.preview-btn {
  /*预览按钮样式*/
  border: 1px solid yellowgreen;
  color: yellowgreen;
}

.preview-btn:hover {
  border: 1px solid yellowgreen;
  color: white;
  cursor: pointer;
  background-color: yellowgreen;
}

.back-btn {
  /*返回按钮样式*/
  color: white;
  background: #666666;
  border: 1px solid grey;
}

.back-btn:hover {
  color: #EEEEEE;
  border: 1px solid grey;
  background: #54534d;
}

.save-btn {
  border: 1px solid #cd4432;
  color: #cd4432;
}

.save-btn:hover {
  color: white;
  background: #cd4432;
  border: 1px solid #cd4432;
}

.button-box {
  display: flex;
  flex-direction: row;
  min-width: 100px;
}
</style>
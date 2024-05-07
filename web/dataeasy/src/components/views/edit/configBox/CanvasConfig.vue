<template>
  <div>
    <!--  画布尺寸设计区域  -->
    <div class="config-box">
      <!--  画布大小，位置设置区域  -->
      <div class="title">画布大小</div>
      <el-input class="chart-size-input" v-model="getChartData.width"><template slot="prepend">W</template></el-input>
      <el-input class="chart-size-input" v-model="getChartData.height"><template slot="prepend">H</template></el-input>
    </div>

    <!--  画布背景设置区域  -->
    <div class="config-box">
      <div class="title">背景设置</div>
      <el-select class="backgroundSetting-input" placeholder="请选择你的背景配置" v-model="getChartData.backgroundsetting">
        <el-option label="背景颜色" :value="'背景颜色'"></el-option>
        <el-option label="背景图片" :value="'背景图片'"></el-option>
      </el-select>
    </div>

    <!--  画布背景设置为背景颜色时， 背景配置区域 v-show="getChartData.backgroundsetting === '背景颜色'" -->
    <div class="config-box" v-show="getChartData.backgroundsetting === '背景颜色'">
      <div class="title">背景颜色设置</div>
      <!--  颜色选择器  -->
      <el-color-picker v-model="getChartData.backgroundcolor" style="margin-bottom: 10px"></el-color-picker>
      <!--  颜色输入框  -->
      <el-input @blur="updateColorPicker" ref="colorInput" v-model="getChartData.backgroundcolor">
        <template slot="prepend">十六进制颜色</template>
      </el-input>
    </div>

    <!--  画布背景设置为背景图片时， 背景配置区域 v-show="getChartData.backgroundsetting === '背景图片'" -->
    <div class="config-box" v-show="getChartData.backgroundsetting === '背景图片'">
      <div class="title">背景图片设置</div>
      <!--  画布图片上传区域  -->
      <el-upload class="bg-upload-contain"
                 action=""
                 :auto-upload="false"
                 ref="CanvasBackgroundImageUpload"
                 :drag="false"
                 :multiple="false"
                 :show-file-list="false"
                 :on-change="handelUploadPicture"
                 :http-request="uploadCanvasBackgroundImage"
      >
        <div class="bg-preview-wrapper">
          <img :src="previewImage" class="bg-preview" v-show="previewImage" :style="{objectFit: getBackgroundImageSize}">
          <i class="el-icon-plus avatar-uploader-icon bg-upload-icon" v-show="!previewImage"></i>
        </div>
        <div class="el-upload__tip bg-upload-tips" slot="tip">请上传图片文件[目前支持 .png, .jpg, .jpeg]为后缀的文件 ,仅支持上传小于<span style="color: red">2</span>MB的图片</div>
      </el-upload>

      <el-select placeholder="请选择样式" class="bg-image-setting" v-model="getChartData.backgroundimagesize">
        <el-option label="覆盖" value="cover"></el-option>
        <el-option label="平铺" value="contain"></el-option>
        <el-option label="填充" value="100% 100%"></el-option>
      </el-select>

      <div class="el-upload__tip bg-upload-tips" slot="tip">背景图片需要确认上传，不然无法保存</div>
      <div class="el-upload__tip bg-upload-tips" slot="tip" style="color: red">删除背景将直接删除数据，请慎重使用</div>
      <div class="bg-upload-button-box">
        <el-button class="bg-upload-button" type="primary" @click="uploadTrigger">上传图片</el-button>
        <el-button class="bg-upload-button" type="success" @click="clearUploadFiles">清空上传</el-button>
        <el-button class="bg-upload-button" type="danger"  @click="handleScreenBgDelete" style="margin-top: 10px">删除背景</el-button>
      </div>
    </div>

    <div class="config-box">
      <div class="title">画布JSON-Setting(只读)-控件列表</div>
      <pre class="code-box" v-html="getJSONElementList" style="user-select: text"></pre>
    </div>

  </div>
</template>

<script>
export default {

  data() {
    return {
      //画布背景设置的预览图
      previewImage: null,
      //颜色输入框输入的值
      colorInput: "",

      elements: []
    }
  },

  computed: {
    getChartData: function () {
      return this.$parent.$parent.chartData
    },

    //图片样式与背景图片样式转换
    getBackgroundImageSize: function () {
      if (this.getChartData.backgroundimagesize === "100% 100%") {
        return "fill"
      }
      else {
        return this.getChartData.backgroundimagesize
      }
    },

    //返回一个控件列表
    getJSONElementList: function () {
      let elementList = this.getChartData.elements
      if (elementList.length === 0) {
        return null
      }
      else {
        elementList.forEach((item) => {
          const {elementid, name, type} = item
          const tempElement = {elementid, name, type}

          const existingElement = this.elements.find((el) => el.elementid === elementid)

          if (!existingElement) {
            this.elements.push(tempElement)
          }

        })
        return this.elements
      }
    }
  },

  watch: {
    //当画布颜色选择器发生改变时，改变颜色输入框的内容
    'chartData.backgroundcolor': {
      handler() {
        this.colorInput = this.chartData.backgroundColor.replace(/^#|#$/g, '')
      }
    },
  },

  methods: {
    uploadTrigger: function () {
      //判空
      if (this.$refs.CanvasBackgroundImageUpload.uploadFiles.length === 0) {
        this.$message({
          type: "warning",
          message: "上传文件不得为空，请检查"
        })
        return null
      }
      else {
        this.$refs.CanvasBackgroundImageUpload.submit()
      }

    },

    //上传图片
    uploadCanvasBackgroundImage: function (fileObject) {
      this.$axios.post("chart/saveChartBackgroundImage", {
        userid: localStorage.getItem("id"),
        chartid: this.getChartData.chartid,
        file: fileObject.file
      }, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
          .then((response) => {
                console.log(response)
                //更新图片
                this.getChartData.backgroundimage = response.data
                //更新状态
                this.$parent.$parent.pictureWaittingUpload = false
                //弹窗
                this.$message({
                  type: "success",
                  message: "上传画布背景图片成功"
                })
              }
              ,() => {
                //弹窗
                this.$message({
                  type: 'error',
                  message: "上传画布背景图片失败"
                })
              }
          )
    },

    //实时更新颜色选择器
    updateColorPicker: function () {
      if(/^#([\dA-Fa-f]{6}|[\dA-Fa-f]{3})$/.test(this.$refs.colorInput.value)) {
        this.chartData.backgroundColor = '#' + this.colorInput
      }
      else {
        this.colorInput = ''
      }
    },

    //监听画布背景图是否被删除，如果是被删除，则清空画布的背景图
    handleScreenBgDelete: function () {
      this.getChartData.backgroundimage = ''
    },

    //清空上传的文件
    clearUploadFiles: function () {
      this.$refs.CanvasBackgroundImageUpload.clearFiles()
      this.previewImage = ""
      this.$parent.$parent.pictureWaittingUpload = false
    },

    //检查上传的图片
    handelUploadPicture: function (file, fileList) {
      //动态文件列表
      if (fileList.length > 1) {
        //只保留最后一个
        fileList.splice(0, 1)
      }
      //检查文件格式以及大小
      let checkResult = this.$parent.handleBackgroundImageUploadCheck(file)
      if (!checkResult) {
        this.$message({
          type: "warning",
          message: "选择的文件类型或大小不符合要求"
        })
        this.$refs.CanvasBackgroundImageUpload.clearFiles()
      }
      else {
        //生成预览
        this.previewImage = URL.createObjectURL(file.raw)
        this.$parent.$parent.pictureWaittingUpload = true
      }
    },
  },

}

</script>

<style scoped>
/*控件各部分设置容器*/
.config-box {
  border-top: 1px solid rgba(0, 0, 0, 0.06);
  margin: 0;
  padding: 14px 20px;
}
/*控件设置标题*/
.title {
  font-weight: bold;
  font-size: 0.86rem;
  margin-bottom: 12px;
}
/*画布大小输入框样式*/
.chart-size-input {
  width: 245px;
  margin-bottom: 10px;
}

.chart-size-input /deep/ div {
  width: 20px;
  text-align: center;
}

/*画布背景设置输入框*/
.backgroundSetting-input {
  width: 245px;
  margin-bottom: 10px;
}

.bg-upload-contain {
  height: 240px;
  margin-bottom: 10px;
}

/*上传图片预览框*/
.bg-preview-wrapper {
  width: 245px;
  height: 200px;
  border-radius: 4px;
  border: 1px solid #0099CC;
}
/*预览图片的样式*/
.bg-preview {
  width: 243px;
  height: 198px;
}
/*上传框内的加号样式*/
.bg-upload-icon {
  line-height: 200px;
}

.bg-image-setting {
  width: 245px;
  margin-bottom: 10px;
}

.bg-upload-tips {
  user-select: none;
}
.bg-upload-button-box {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.bg-upload-button {
  width: 112px;
  margin: 0 5px 0 0;
}


/*控件JSON内容的容器*/
.code-box {
  border: 1px solid #eeeeee;
  border-radius: 4px;
  padding: 6px;
  overflow: scroll;
}
</style>
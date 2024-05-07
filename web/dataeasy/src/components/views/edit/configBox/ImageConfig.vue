<template>
  <div>
    <div class="config-box">
      <div class="title">图片上传</div>
      <el-upload class="imageElement-Upload-contain"
                 action=""
                 :auto-upload="false"
                 ref="imageElementUpload"
                 :drag="false"
                 :multiple="false"
                 :show-file-list="false"
                 :on-change="handleImageElementImageUpload"
                 :http-request="uploadImageElementImage"
      >
        <div class="bg-preview-wrapper">
          <img v-show="previewImage" class="bg-preview" :src="previewImage" :style="{objectFit: getBackgroundImageSize}">
          <i class="el-icon-plus avatar-uploader-icon bg-upload-icon" v-show="!previewImage"></i>
        </div>
        <div class="el-upload__tip bg-upload-tips" slot="tip">请上传图片文件[目前支持 .png, .jpg, .jpeg]为后缀的文件 ,目前仅支持上传小于<span style="color: red">2</span>MB的图片</div>
      </el-upload>

      <div class="el-upload__tip bg-upload-tips" slot="tip">背景图片需要确认上传，不然无法保存</div>
      <div class="el-upload__tip bg-upload-tips" slot="tip" style="color: red">上传图片后，控件图片才会变更图片内容</div>
      <div class="bg-upload-button-box">
        <el-button class="upload-button" type="primary" @click="uploadTrigger">上传图片</el-button>
        <el-button class="upload-button" type="success" @click="clearPicture">清空上传</el-button>
        <el-button class="upload-button" type="danger"  @click="deleteImageElementImage" style="margin-top: 10px">删除图片</el-button>
      </div>

    </div>

    <!--  图片样式设置  -->
    <div class="config-box">
      <div class="title">图片样式</div>
      <el-select class="select-box-style" v-model="getCurrentElement.data.backgroundImageSize">
        <el-option label="覆盖" value="cover"></el-option>
        <el-option label="平铺" value="contain"></el-option>
        <el-option label="填充" value="100% 100%"></el-option>
      </el-select>
      <el-switch style="margin: 10px 0 0 10px"
                 v-model="vertical"
                 :disabled="!getVertical"
                 active-text="开启垂直居中"
                 inactive-text="关闭垂直居中"
      ></el-switch>
    </div>

    <!--  图片位置设定  -->
    <div class="config-box">
      <div class="title">图片位置</div>
      <div class="title">X轴位置调整</div>
      <el-input v-model="getCurrentElement.data.backgroundPositionX"><template slot="prepend">X</template></el-input>
      <div class="title" style="margin-top: 10px">Y轴位置调整</div>
      <el-input v-model="getCurrentElement.data.backgroundPositionY" :disabled="vertical"><template slot="prepend">Y</template></el-input>
    </div>

    <!--  图片透明度设定  -->
    <div class="config-box">
      <div class="title">透明度设置</div>
      <el-input  v-model="getCurrentElement.data.opacity" class="image-input"><template slot="append">Max:1</template><template slot="prepend">Min:0</template></el-input>
      <el-slider v-model="getCurrentElement.data.opacity" :max="1" :min="0" :step="0.01" class="slider"></el-slider>
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return{
      previewImage: "",

      vertical: false,
    }
  },

  computed: {
    //获取当前活跃的控件
    getCurrentElement: function () {
      return this.$parent.$parent.getCurrentElement
    },

    getVertical: function () {
      let isVertical;
      if (this.getCurrentElement.data.backgroundImageSize === 'contain') {
        isVertical = true
      }
      else {
        isVertical = false
      }
      return isVertical
    },

    //图片样式与背景图片样式转换
    getBackgroundImageSize: function () {
      if (this.getCurrentElement.data.backgroundImageSize === "100% 100%") {
        return "fill"
      }
      else {
        return this.getCurrentElement.data.backgroundImageSize
      }
    }
  },

  methods: {
    uploadTrigger: function () {
      if (this.$refs.imageElementUpload.uploadFiles.length === 0) {
        this.$message({
          type: "warning",
          message: "上传的文件内容不得为空，请检查"
        })
      }
      else {
        console.log("test")
        this.$refs.imageElementUpload.submit()
      }
    },


    handleImageElementImageUpload: function (file, fileList) {
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
        this.$refs.imageElementUpload.clearFiles()
      }
      else {
        //生成预览
        this.previewImage = URL.createObjectURL(file.raw)
        this.$parent.$parent.pictureWaittingUpload = true
      }
    },

    //上传图片
    uploadImageElementImage: function (fileObject) {
      this.$axios.post("chart/saveImageElementImage", {
        chartid: this.getCurrentElement.chartid,
        elementid: this.getCurrentElement.elementid,
        file: fileObject.file
      },{
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
          .then((response) => {
            this.getCurrentElement.data = JSON.parse(response.data)
            this.$parent.$parent.pictureWaittingUpload = false
          })
    },

    //清空上传
    clearPicture: function () {
      this.previewImage = ""
      this.$parent.$parent.pictureWaittingUpload = false
      this.$refs.imageElementUpload.clearFiles()
    },

    //删除图片
    deleteImageElementImage: function () {
      console.log("delete")
    }
  }
}
</script>

<style scoped>
.config-box {
  border-top: 1px solid rgba(0, 0, 0, 0.06);
  margin: 0;
  padding: 14px 20px;
}

.title {
  font-weight: bold;
  font-size: 0.86rem;
  margin-bottom: 12px;
}

.imageElement-Upload-contain {
  height: 250px;
  margin-bottom: 10px;
  user-select: none;
}

.bg-preview-wrapper {
  width: 245px;
  height: 200px;
  box-sizing: border-box;
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

.select-box-style {
  width: 245px;
}
.image-input {
  text-align: center;
}
.image-input > span > span{
  text-align: center;
  width: 50px;
}

.slider {
  width: 235px;
}

.bg-upload-button-box {
  display: flex;
  flex-wrap: wrap;
  margin-top: 10px;
}

.upload-button {
  width: 115px;
  margin: 0 5px 0 0;
}
</style>
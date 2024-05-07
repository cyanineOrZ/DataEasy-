<template>
  <div class="personBlog">
    <div class="personBlog-contain" v-if="currentUserInfo">
      
      <!--   背景图区域    -->
      <div class="headerPicture-contain" :style="{background: userHeadPictureUrl, backgroundSize: 'cover'}">
        <!--   更换头图片按钮    -->
        <button class="headerPicture-change-button" @click="uploadHeadPictureButton">编辑图片</button>
        <!--   个性签名区域    -->
        <div class="Personal-signature-contain">
          <div class="Personal-signature-content">{{currentUserInfo.usersignature}}</div>
        </div>
      </div>

      <!--   博客区域    -->
      <div class="personBlog-blog-total-contain">
        <!--   博客侧边工具栏区域    -->
        <div class="personBlog-tool-side">
          <BlogSideTool></BlogSideTool>
        </div>

        <!--   博客内容区域    -->
        <div class="personBlog-blog-contain">
          <div class="personBlog-tool-side-module-contain" style="color: grey; font-size: 14px; width: 100%;height: 20px;">我的博客
            <!--   书写博客按钮    -->
            <button class="personBlog-blog-contain-addBlog-button" v-show="!editor"   @click="changeMode">书写博客</button>
            <button class="personBlog-blog-contain-addBlog-button" v-show="editor"  @click="changeMode">返回博客</button>
          </div>

          <!--   博客文章区域，博客文章添加区域    -->
          <div class="personBlog-blog-contain-blog-component-contain">
            <router-view></router-view>
          </div>

        </div>

      </div>

      <!--   小头像区域    -->
      <div class="Person-avatar-contain">
        <img class="Person-avatar" :src="this.currentUserInfo.useravatarpicture">
        <div class="Person-avatar-username">{{currentUserInfo.username}}</div>
      </div>
    </div>

    <!--   头图片上传对话框    -->
    <el-dialog :visible.sync="uploadHeadPicture"  title="封面上传">
      <el-form>
        <el-form-item>编辑封面</el-form-item>
        <el-form-item>
          <!--   小头像区域    -->
          <el-upload
              action="#"
              class="upload"
              :auto-upload="false"
              ref="headPictureUpload"
              style="width: 300px;"
              :drag="false"
              :show-file-list="false"
              :on-exceed="false"
              :on-change="handleHeadPictureUploadCheck"
              :http-request="uploadRequest"
          >
            <div class="bg-preview-wrapper">
              <img class="bg-preview" :src="this.previewImage" v-show="previewImage">
              <i v-show="!previewImage" class="el-icon-plus avatar-uploader-icon"></i>
            </div>
            <div class="el-upload__tip" slot="tip" style="width: 400px; user-select: none" >请上传图片文件[目前支持 .png, .jpg, .jpeg]为后缀的文件 ,目前仅支持上传小于<span style="color: red">2</span>MB的图片</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="el-dialog__footer">
        <el-button @click="cancelHeadPictureUpload">取消</el-button>
        <el-button type="primary" @click="submitUpload">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>


export default {
  data() {
    return {
      editor: false,
      currentUserInfo: null,
      blogList: [],
      uploadHeadPicture: false,
      previewImage: "",
    }
  },
  components: {
    BlogSideTool: () => import("../../views/blog/BlogSideTool")
  },
  computed: {
    userHeadPictureUrl: function () {
      if (this.currentUserInfo) {
        return `url(${this.currentUserInfo.userblogheadpicture})` + 'center top no-repeat'
      }
      else {
        return ""
      }
    },

  },
  mounted() {
    this.editor = false
    this.getUserInfo()
    this.getUserBlogs()
  },

  methods: {
    uploadHeadPictureButton: function () {
      this.uploadHeadPicture = true
    },

    //
    changeMode: function () {
      this.editor = !this.editor
      if (this.editor) {
        this.$router.push("/console/PersonBlogView/PersonBlogEditorView")
      }
      else {
        this.$router.push("/console/PersonBlogView/PersonBlogDetailView")
      }
    },

    //请求博客数据
    getUserBlogs: function () {
      this.$axios.post("http://localhost:8080/blog/getUserBlog", {
        userid: localStorage.getItem("id")
      }).then((response) => {
        console.log("博客数据请求成功", response)
        this.blogList = response.data
      })
    },
    //请求头图片
    getUserInfo: function () {
      this.$axios.post("http://localhost:8080/userinfo/getUserInfo", {
        userid: localStorage.getItem("id")
      })
          .then((response) => {
            console.log("头图片数据请求成功", response)
            this.currentUserInfo = response.data
          })
    },

    // 头图片上传前的检测，动态文件列表
    handleHeadPictureUploadCheck: function (file, fileList) {
      if (!file) {
        return null
      }
      else {
        //动态文件列表
        if (fileList.length > 1) {
          //只保留最后一个
          fileList.splice(0, 1)
        }
        //检验文件是否符合相对应的文件名
        const fileTypeLimit = ['jpg', 'png', 'jpeg']
        let thisFileType = file.name.split(".")[1]
        const typeResult = fileTypeLimit.some((item) => {
          return item === thisFileType
        })
        //如果文件不合规   headPictureUpload
        if (!typeResult) {
          this.$message({
            type: "warning",
            message: "改格式的文件不支持上传，请重新选择文件"
          })
          //清空文件
          this.$refs.headPictureUpload.clearFiles()
          return false
        }
        //检验文件大小是否符合标准
        else if (file.size / 1024 / 1024 > 10) {
          this.$message({
            type: 'warning',
            message: "上传的文件不得大于5MB"
          })
          //清空文件
          this.$refs.headPictureUpload.clearFiles()
          return false
        }
        this.previewImage = URL.createObjectURL(file.raw)

      }
    },
    //点击取消上传
    cancelHeadPictureUpload: function () {
      this.previewImage = ""
      this.$refs.headPictureUpload.clearFiles()
      this.uploadHeadPicture = false
    },
    //点击确认上传
    submitUpload: function () {
      this.previewImage = ""
      this.$refs.headPictureUpload.submit()
      this.uploadHeadPicture = false
    },
    //上传头图片的请求
    uploadRequest: function (fileObject) {
      console.log("上传头图片")
      this.$axios.post("userinfo/uploadHeadPicture", {
        userid: localStorage.getItem("id"),
        file: fileObject.file
      }, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
          .then((response) => {
            this.$message({
              type: "success",
              message: "封面上传成功"
            })
            console.log(response)
            this.currentUserInfo = response.data
            this.previewImage = ""
            this.uploadHeadPicture = false
          })
    },
  }
}
</script>

<style scoped>
.personBlog {
  width: 100%;
  height: 100%;
  overflow-y: scroll;
  min-width: 900px;
  background: whitesmoke;
}

/*滚动条样式*/
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}
::-webkit-scrollbar-track {
  width: 6px;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}
::-webkit-scrollbar-thumb {
  background-color: rgba(144,147,153,.5);
  background-clip: padding-box;
  min-height: 28px;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
  transition: background-color .3s;
  cursor: pointer;
}
::-webkit-scrollbar-thumb:hover {
  background-color: rgba(144,147,153,.3);
}

/*头图片样式和附属元素样式*/
.personBlog-contain {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
}
.headerPicture-contain {
  position: relative;
  width: 100%;
  height: 600px;
  z-index: 1;
}
.headerPicture-change-button {
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 10px 20px;
  border: 1px solid #008CBA;
  border-radius: 6px;
  background: transparent;
  backdrop-filter: blur(10px);
  color: #fff;
  opacity: 0.7;
  transition: 0.3s ease;
}
.headerPicture-change-button:hover {
  background: #008CBA;
  opacity: 1;
  border: 1px solid #008CBA;
  transition: 0.3s ease;
}

/*个性签名样式*/
.Personal-signature-contain {
  /*个性签名容器样式， 毛玻璃效果*/
  position: absolute;
  bottom: 55px;
  right: 20px;
  padding: 20px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 270px;
  height: 60px;
  border-radius: 12px;
  backdrop-filter: blur(10px);
  color: #FFFFFF;
  box-shadow: 10px 10px 10px 2px rgba(0, 0, 0, .3);
  z-index: 2;
}
.Personal-signature-content {
  /*个性签名内容样式*/
  width: 100%;
  height: 100%;
  letter-spacing: 0.1em;
  word-wrap: break-word;
}
/*用户头像区样式*/
.Person-avatar-contain {
  /*用户头像容器*/
  position: sticky;
  top: 10px;
  width: 190px;
  height: 190px;
  margin-top: -50px;
  border-radius: 12px;
  z-index: 2;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: white;
  box-shadow: 10px 10px 10px 2px rgba(0, 0, 0, .3);
}
.Person-avatar {
  width: 96px;
  height: 96px;
  border-radius: 50%;
  border: 3px solid grey;
  object-fit: cover;
  background-position: center center;
}
.Person-avatar-username {
  margin-top: 10px;
  color: #222222;
}

/*博客内容容器*/
.personBlog-blog-total-contain {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 70%;
  margin-top: -50px;
  margin-right: 30px;
  margin-bottom: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 10px 10px 10px 2px rgba(0, 0, 0, .3);
  z-index: 2;
  overflow: hidden;
}

/*博客内容侧边栏样式*/
.personBlog-tool-side {
  width: 230px;
  padding: 10px;
  user-select: none;
}
/*博客内容侧边栏工具组件容器*/
.personBlog-tool-side-module-contain {
  padding: 10px 0;
  border-bottom: 1px solid grey;
}

.personBlog-tool-side-module-contain-title {
  color: grey;
  font-size: 14px;
  margin-bottom: 4px;
  margin-top: 4px;
  margin-left: 4px;
}


.personBlog-blog-contain {
  position: relative;
  flex: 1;
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  justify-content: center;
}

.personBlog-blog-contain::before {
  content: "";
  position: absolute;
  width: 2px;
  height: 95%;
  background: grey;
  top: 0;
  left: 0;
  margin-top: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  opacity: 0.7;
}
.personBlog-blog-contain-addBlog-button {
  position: absolute;
  top: 10px;
  right: 20px;
  padding: 5px 8px;
  font-size: 12px;
  border-radius: 6px;
  color: #409EFF;
  border: 2px solid #409EFF;
  background: white;
  transition: 0.3s ease;
  cursor: pointer;
}
.personBlog-blog-contain-addBlog-button:hover {
  color: white;
  background: #409EFF;
}
.personBlog-blog-contain-addBlog-button a{
  color: inherit;
  text-decoration: none;
}
.personBlog-blog-contain-blog-component-contain {
  width: calc(100%);
  height: calc(100% - 40px);
  margin: 10px 0;
  max-width: 100%;
}


.tiptap > * {
  margin-top: 10px;
  padding: 0 10px;
  outline: none;
}
.tiptap ul,
.tiptap ol {
  padding: 0 1rem;
}

.tiptap h1,
.tiptap h2,
.tiptap h3,
.tiptap h4,
.tiptap h5,
.tiptap h6 {
  line-height: 1.1;
}
.tiptap code {
  background-color: #616161;
  color: #616161;
}
.tiptap pre {
  background: #0D0D0D;
  color: #FFF;
  font-family: 'JetBrainsMono', monospace;
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
}
.tiptap pre code {
  color: inherit;
  padding: 0;
  background: none;
  font-size: 0.8rem;
}
.tiptap img {
  max-width: 100%;
  height: auto;
}
.tiptap blockquote {
  padding-left: 1rem;
  border-left: 2px solid #0D0D0D;
}
.tiptap hr {
  border: none;
  border-top: 2px solid #0D0D0D;
  margin: 2rem 0;
}


.bg-preview-wrapper {
  width: 100%;
  border-radius: 4px;
  border: 1px solid #0099CC;
  min-width: 450px;
  height: 300px;
  overflow: hidden;
}

.bg-preview {
  height: 300px;
  width: 100%;
  object-fit: cover;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100%;
  height: 100%;
  line-height: 300px;
  text-align: center;
}

</style>
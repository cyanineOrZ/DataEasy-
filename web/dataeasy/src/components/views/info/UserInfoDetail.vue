<template>
  <div class="contain">
    <!--  可移动头像区域  -->
    <div class="userInfo-avatar-sticky-contain">
      <div class="userInfo-avatar-abs-contain" :style="{borderColor: getCurrentUserInfo.userbackgroundcolor}">
        <!--  蒙砂层  -->
        <div class="userInfo-avatar-mask-contain">
          <!--  点击上传图片  -->
          <el-upload class="el-upload"
                     ref="userAvatarUpload"
                     :drag="false"
                     action="#"
                     :auto-upload="false"
                     :on-change="handleUserAvatarUpload"
                     :show-file-list="false"
                     :http-request="submitRequest"
          >
            <i class="el-icon-camera userInfo-avatar-upload-icon"></i>
            <div class="el-upload__text userInfo-avatar-upload-tips">点击编辑头像</div>
          </el-upload>
        </div>
        <!--  头像区域  -->
        <img class="userInfo-avatar-image" :src="tempAvatar" alt="">
        <div v-show="isChangeAvatar" class="userInfo-avatar-save-avatar-tips">您的头像未保存，提交修改后方可更换头像</div>
      </div>
    </div>
    <!--  具体布局区域  -->
    <div class="userInfo-Info-Main-Contain">
      <!--  按钮容器，包含布局切换按钮和颜色选择器  -->
      <div class="layout-change-button-box">
        <el-button                           @click="getCurrentUserInfo.userinfolayout = true">布局1</el-button>
        <el-button style="margin-left: 10px" @click="getCurrentUserInfo.userinfolayout = false">布局2</el-button>
        <el-input style="width: 220px;border-radius: 8px;margin-left: 10px;" v-model="getCurrentUserInfo.userbackgroundcolor" :value="getCurrentUserInfo.userbackgroundcolor">
          <template slot="prepend">边框颜色选择</template>
        </el-input>
        <el-color-picker style="margin-left: 10px" v-model="getCurrentUserInfo.userbackgroundcolor"></el-color-picker>
      </div>
      <!--   布局一 -->
      <div class="layout1-contain" :style="{borderColor: getCurrentUserInfo.userbackgroundcolor}" v-show="getCurrentUserInfo.userinfolayout">
        <div class="layout1-content-contain layout-username-contain" style="margin-left: 0">{{getCurrentUserInfo.username}}</div>
        <div class="layout1-content-contain" v-for="item in infoList" :key="item.id">
          <span class="layout1-title-style">{{item.title}}:</span>
          <span v-text="item.value(getCurrentUserInfo)"></span>
        </div>
      </div>
      <!--   布局二  -->
      <div v-show="!getCurrentUserInfo.userinfolayout">
        <div class="layout1-content-contain layout-username-contain" style="margin-left: 0;padding-left: 0">{{getCurrentUserInfo.username}}</div>
        <div class="layout2-contain" :style="{borderColor: getCurrentUserInfo.userbackgroundcolor}">
          <div v-for="item in infoList" :key="item.id" class="layout2-content-contain">
            <span class="layout2-title-style">{{item.title}}:</span>
            <span v-text="item.value(getCurrentUserInfo)"></span>
          </div>
        </div>
      </div>
      <!--   未保存信息提示 -->
      <div class="wait-save-tips" v-show="(this.$parent.hadEditor || this.isChangeAvatar)">您有编辑后的个人信息未保存，请检查</div>
      <!--   按钮容器，修改和编辑 -->
      <div class="userInfo-bottom-button-box">
        <el-button @click="isEditor = true" type="primary">编辑</el-button>
        <el-button @click="submitUserInfo" type="success">保存</el-button>
      </div>
    </div>
    <!--   编辑用户个人信息 -->
    <el-dialog title="编辑用户信息" :visible.sync="isEditor" top="0">
      <el-form>
        <el-form-item label="用户名">
          <el-input v-model="getCurrentUserInfo.username"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="getCurrentUserInfo.userage"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="getCurrentUserInfo.usersex"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="getCurrentUserInfo.useremail"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="getCurrentUserInfo.userphone"></el-input>
        </el-form-item>
        <el-form-item label="微信">
          <el-input v-model="getCurrentUserInfo.userwechart"></el-input>
        </el-form-item>
        <el-form-item label="个性签名">
          <el-input v-model="getCurrentUserInfo.usersignature"></el-input>
        </el-form-item>
        <el-form-item label="就职企业/就读院校">
          <el-input v-model="getCurrentUserInfo.userword"></el-input>
        </el-form-item>
        <el-form-item label="居住地">
          <el-input v-model="getCurrentUserInfo.useraddress"></el-input>
        </el-form-item>
        <el-form-item label="教育经历">
          <el-input v-model="getCurrentUserInfo.usereducate"></el-input>
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input v-model="getCurrentUserInfo.userintro"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmUserInfo">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  data() {
    return {
      props: ['currentUserInfo'],
      isEditor: false,
      isChangeAvatar: false,
      tempAvatar: this.$parent.currentUserInfo.useravatarpicture,
      infoList: [
        {title: "年龄",             value: function (currentUserInfo) {return currentUserInfo.userage}, id: "userage"},
        {title: "性别",             value: function (currentUserInfo) {return currentUserInfo.usersex}, id: "usersex"},
        {title: "邮箱",             value: function (currentUserInfo) {return currentUserInfo.useremail}, id: "useremail"},
        {title: "联系方式",           value: function (currentUserInfo) {return currentUserInfo.userphone}, id: "userphone"},
        {title: "微信号",            value: function (currentUserInfo) {return currentUserInfo.userwechart}, id: "userwechart"},
        {title: "个性签名",           value: function (currentUserInfo) {return currentUserInfo.usersignature}, id: "usersignature"},
        {title: "就职企业/就读院校",      value: function (currentUserInfo) {return currentUserInfo.userword}, id: "userword"},
        {title: "居住地",            value: function (currentUserInfo) {return currentUserInfo.useraddress}, id: "useraddress"},
        {title: "教育经历",           value: function (currentUserInfo) {return currentUserInfo.usereducate}, id: "usereducate"},
        {title: "个人简介",           value: function (currentUserInfo) {return currentUserInfo.userintro}, id: "userintro"},
      ],
    }
  },

  computed: {
    getCurrentUserInfo: function () {
      return this.$parent.currentUserInfo
    },

  },


  methods: {
    //头像上传前的检查
    handleUserAvatarUpload: function (file, fileList) {
      if (!file) {
        return null
      }
      else {
        this.isChangeAvatar = true
        //动态文件列表
        if (fileList.length > 1) {
          //只保留最后一个
          fileList.splice(0, 1)
        }
        //检验文件名是否合规
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
          this.$refs.userAvatarUpload.clearFiles()
          return false
        }
        else if (file.size / 1024 / 1024 > 10) {
          this.$message({
            type: 'warning',
            message: "上传的文件不得大于10MB"
          })
          //清空文件
          this.$refs.userAvatarUpload.clearFiles()
          return false
        }
        this.tempAvatar = URL.createObjectURL(file.raw)
      }
    },

    //头像上传的请求
    submitRequest: function (fileObject) {
      console.log("上传头像")
      console.log(fileObject)
      this.$axios.post("http://localhost:8080/userinfo/uploadUserAvatar", {
        userid: localStorage.getItem("id"),
        file: fileObject.file
      }, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
          .then(() => {
            this.isChangeAvatar = false
            this.$store.state.avatarChange = true
          })
    },

    //触发父组件的用户信息提交
    submitUserInfo: function () {
      this.$confirm("确认提交编辑好的用户信息？", "Tips", {
        confirmButtonText: "确认提交",
        cancelButtonText: "手滑点错了Q_Q"
      })
          //点击确认后
          .then(() => {
            //一定要切换为非编辑状态
            this.isEditor = false
            //提交头像
            this.$refs.userAvatarUpload.submit()
            //提交信息
            this.$parent.submitUserInfo()

          }
          ,() => {}
          )

    },

    confirmUserInfo : function () {
      this.isEditor = false;
    },

    test: function () {
      console.log(this.$parent.currentUserInfo)
    }
  }
}
// userAvatarUpload
</script>


<style scoped>
.contain {
  position: relative;
  width: 100%;
  border-radius: 8px;
  box-shadow: 10px 10px 10px 4px rgba(0, 0, 0, .3);
}
/*可移动头像容器*/
.userInfo-avatar-sticky-contain {
  position: sticky;
  top: 40px;
  left: 20px;
}
.userInfo-avatar-abs-contain {
  position: absolute;
  width: 200px;
  height: 200px;
  top: -20px;
  left: 20px;
  border-radius: 10px;
  border: 5px solid;
  background: white;
}
/*用户头像图片样式*/
.userInfo-avatar-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 6px;
  z-index: 1;
}
/*蒙纱层，悬浮时触发*/
.userInfo-avatar-mask-contain {
  position: absolute;
  width: 100%;
  height: 100%;
  background: grey;
  border-radius: 8px;
  opacity: 0;
  z-index: 2;
  transition: 0.3s ease;
}
.userInfo-avatar-mask-contain:hover {
  opacity: 0.8;
}
/*上传的图标占满*/
.userInfo-avatar-upload-icon {
  width: 200px;
  height: 200px;
  line-height: 180px;
  font-size: 40px;
  color: white;
  z-index: 2;
}
/*上传蒙版的提示语言*/
.userInfo-avatar-upload-tips {
  width: 100%;
  position: absolute;
  top: 65%;
  color: white;
  font-size: 16px;
}
/*上传头像未保存提示*/
.userInfo-avatar-save-avatar-tips {
  width: 120%;
  color: red;
  font-size: 10px;
  margin-top: 10px;
  margin-left: -20px;
  text-align: center;
}


/*主要布局容器*/
.userInfo-Info-Main-Contain {
  width: calc(100% - 270px);
  border-radius: 8px;
  padding: 10px 20px 20px 250px;
  background: white;
}
/*布局按钮*/
.layout-change-button-box {
  display: flex;
  justify-content: flex-end;
}

.layout-username-contain {
  line-height: 70px;
  font-size: 30px;
  font-weight: 600;
}

/*布局一样式*/
.layout1-contain {
  width: calc(100% - 16px);
  height: 93%;
  margin-top: 10px;
  padding-bottom: 20px;
  border-radius: 8px;
  overflow: hidden;
  border: 8px solid grey;
}
.layout1-content-contain {
  display: flex;
  position: relative;
  align-items: center;
  width: calc(100% - 40px);
  height: 90px;
  padding: 0 20px;
  margin-left: 20px;
}
.layout1-content-contain:nth-child(n+2)::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  width: 70%;
  height: 2px;
  background: grey;
  border-radius: 50%;
}
.layout1-content-contain:nth-child(n+2)::before {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  width: 2px;
  height: 80%;
  background: grey;
  border-radius: 50%;
}

.layout1-title-style {
  font-size: 20px;
  font-weight: 500;
  margin-right: 20px;
}

/*布局二样式*/
.layout2-contain {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  border: 8px solid grey;
  border-radius: 8px;
  padding: 0 0 20px 0;
}
.layout2-content-contain {
  display: flex;
  position: relative;
  flex-direction: column;
  height: 150px;
  width: 50%;
  box-sizing: border-box;
  justify-content: center;
  padding-left: 20px;
}
/*底部横线*/
.layout2-content-contain:nth-child(n)::after {
  content: "";
  position: absolute;
  width: 80%;
  height: 1px;
  margin: auto;
  bottom: 0;
  background: grey;
}
/*左侧横线*/
.layout2-content-contain::before {
  content: "";
  position: absolute;
  width: 2px;
  height: 80%;
  top: 20px;
  right: 20px;
  background: grey;
}
.layout2-title-style {
  margin: 10px 0 20px;
  font-size: 20px;
  font-weight: 550;
}

.wait-save-tips {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  margin-top: 10px;
  margin-right: 10px;
  color: red;
  font-size: 12px;
}

/*按钮容器*/
.userInfo-bottom-button-box {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  margin: 10px 0;
  padding: 0 10px 0 0;
}
</style>
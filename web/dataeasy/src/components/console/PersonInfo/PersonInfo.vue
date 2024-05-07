<template>
  <div class="personInfo-total-contain">
    <!--   背景图片区域   -->
    <div class="personInfo-backgroundPicture" v-if="currentUserInfo">
      <img v-if="currentUserInfo" class="personInfo-backgroundPicture-imageStyle" :src="this.currentUserInfo.userblogheadpicture">
    </div>
    <div class="personInfo-Info-contain" v-if="currentUserInfo">
      <!--   个人信息详情区域   -->
      <UserInfoDetail :userinfo="currentUserInfo"></UserInfoDetail>
      <!--   占位div，解决bug   -->
      <div style="width: 100%;height: 60px;background: transparent"></div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return{
      //用户修改了数据吗？
      hadEditor: false,
      currentUserInfo: null,
      avatarChange: this.$store.state.avatarChange
    }
  },
  components: {
    UserInfoDetail: () => import("../../views/info/UserInfoDetail")
  },

  computed: {

  },

  watch: {
    avatarChange() {
      if (this.$store.state.avatarChange) {
        this.getUserInfo()
      }
    }
  },

  mounted() {
    //请求信息，添加监听器
    this.getUserInfo()
  },

  methods: {
    //获取用户信息
    getUserInfo: function () {
      this.$axios.post("userinfo/getUserInfo", {
        userid: localStorage.getItem("id")
      }, {
        cache: 'no-cache'
      })
          .then((response) => {
            console.log("用户信息获取成功", response)
            this.currentUserInfo = response.data
            this.$parent.user_avatar = this.currentUserInfo.useravatarpicture
            this.hadEditor = false
            this.$watch("currentUserInfo", () => {
              this.hadEditor = true
            },
                {
                  deep: true
                })
          }
          ,() => {
              })
    },

    //更改用户信息
    submitUserInfo: function () {
      this.$axios.post("userinfo/updateUserInfo", {
        userid: this.currentUserInfo.userid,
        username: this.currentUserInfo.username,
        userage: this.currentUserInfo.userage,
        usersex: this.currentUserInfo.usersex,
        useremail: this.currentUserInfo.useremail,
        userphone: this.currentUserInfo.userphone,
        userwechart: this.currentUserInfo.userwechart,
        usersignature: this.currentUserInfo.usersignature,
        userword: this.currentUserInfo.userword,
        useraddress: this.currentUserInfo.useraddress,
        usereducate: this.currentUserInfo.usereducate,
        userintro: this.currentUserInfo.userintro,
        userbackgroundcolor: this.currentUserInfo.userbackgroundcolor,
        userinfolayout: this.currentUserInfo.userinfolayout,
      }, {
        headers: {
          "Content-Type": "application/json"
        }
      })
          .then((response) => {
            this.currentUserInfo = response.data
            this.$message({
              type: 'success',
              message: "编辑个人资料成功"
            })
            console.log("数据更新成功", response.data)
            this.$nextTick(() => {
              this.hadEditor = false
            })
          })
    }
  }
}

</script>

<style scoped>
.personInfo-total-contain {
  position: relative;
  width: 100%;
  height: calc(100% + 20px);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow-y: scroll;
  min-width: 1200px;
}

.personInfo-backgroundPicture {
  position: sticky;
  width: 100%;
  height: 100%;
  top: 0;
}

.personInfo-backgroundPicture-imageStyle {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.personInfo-Info-contain {
  position: absolute;
  width: 65%;
  min-width: 900px;
  top: 60px;
  border-radius: 8px;
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
</style>
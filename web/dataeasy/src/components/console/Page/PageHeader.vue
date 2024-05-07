<template>
  <div class="header">
    <div class="page-title">{{getTitle}}</div>
    <el-dropdown trigger="click">
      <!--   头像   -->
      <div class="avatar-image">
        <img :src="this.user_avatar" class="avatar-image">
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>

export default {
  name: "PageHeader",

  data() {
    return {
      user_avatar: null,
      props: ['title'],
      user: {
        id: localStorage.getItem('id'),
        name: localStorage.getItem('name')
      },
      avatarChange: this.$store.state.avatarChange
    };
  },
  computed: {
    getTitle: function () {
      return this.$parent.$parent.$parent.$parent.title
    }
  },

  mounted() {
    this.getUserInfo()
  },

  watch: {
    avatarChange() {
      if (this.avatarChange) {
        console.log(this.avatarChange)
        this.getUserInfo()
      }
    }
  },

  methods: {
    logout() {
      //推出按键， 清除用户信息，并返回登录页面
      localStorage.clear()
      this.$router.push('/')
    },

    //请求头图片
    getUserInfo: function () {
      this.$axios.post("http://localhost:8080/userinfo/getUserInfo", {
        userid: localStorage.getItem("id")
      })
          .then((response) => {
            this.user_avatar = response.data.useravatarpicture
            this.$store.state.avatarChange = false
          })
    },
  },


};
</script>

<style scoped>
.header {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px 50px 0 20px;
}

.page-title {
  flex: 1;
  font-size: 20px;
  font-weight: normal;
}

.icon-row {
  display: flex;
  align-items: center;
}

.icon-row .el-icon-bell {
  font-size: 20px;
  margin-right: 14px;
}

.avatar-image {
  width: 50px;
  height: 50px;
  background: #ccc;
  border-radius: 50%;
  cursor: pointer;
  object-fit: cover;
  object-position: center center;
}

</style>
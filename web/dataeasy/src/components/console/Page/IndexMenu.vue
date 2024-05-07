<template>
  <!-- 主容器 -->
  <el-container>
    <!--  侧边栏  -->
    <el-aside style="width: 240px">
      <div class="sidebar">
        <div class="logo">DataEasy</div>
        <router-link
            class="sidebar-item"
            v-for="item in menuList"
            :key="item.name"
            :to="item.path"
            @click.native="changeTitle(item.name)"
        >
          <i :class="item.icon"></i>
          <span>{{ item.name }}</span>
        </router-link>
      </div>
    </el-aside>
    <el-container>
      <!--  头部导航栏    -->
      <el-header style="height: 96px; min-width: 995px" class="el-header"><PageHeader></PageHeader></el-header>
      <!--  主要内容    -->
      <el-main class="el-main"><router-view></router-view></el-main>
    </el-container>

  </el-container>
</template>

<script>


export default {

  name: "IndexMenu",

  data() {
    return {
      menuList: [
        {
          name: "个人博客",
          icon: "el-icon-s-comment",
          path: "/console/PersonBlogView/PersonBlogDetailView",
        },
        {
          name: "个人资料",
          icon: "el-icon-s-custom",
          path: '/console/PersonInfoView'
        },
        {
          name: '数据管理',
          icon: 'el-icon-menu',
          path: '/console/DataView'
        },
        {
          name: '我的可视化',
          icon: 'el-icon-document',
          path: '/console/ChartView'
        }
      ],

      title: "个人博客",

    }
  },


  components: {
    PageHeader: () => import('./PageHeader'),
  },

  methods: {
    changeTitle: function (title) {
      this.title = title
    },

    setAvatar: function (useravatarpicture) {
      console.log(useravatarpicture)
    }
  }
};
</script>

<style scoped>
.el-header {
  position: fixed;
  left: 240px;
  right: 0;
  padding: 0;
  z-index: 10;
  background: rgba(255, 255, 255, 0.94);
}

.el-main {
  width: 100%;
  height: 100vh;
  padding: 106px 0 0;
  overflow-y: hidden;
}

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  width: 240px;
  background: #f7f9fa;
  z-index: 20;
}

.logo {
  height: 120px;
  line-height: 120px;
  text-align: center;
  font-weight: bold;
  font-size: 22px;
}

.sidebar-item-contain {

}

.sidebar-item {
  display: flex;
  align-items: center;
  width: 100%;
  height: 36px;
  padding: 15px 0;
  font-size: 14px;
  color: #808080;
  border-radius: 8px;
  text-decoration: none;
  transition: linear 0.3s;
}

.sidebar-item:hover {
  color: #222222;
  background: rgba(0, 0, 0, 0.06);
  transition: linear 0.3s;
}

.sidebar-item i {
  font-size: 16px;
  margin-right: 8px;
  margin-left: 16px;
}

.sidebar-item i:hover {
  color: #333;
}

.sidebar-item span {
  padding: 0 0 0 15px;
}
</style>
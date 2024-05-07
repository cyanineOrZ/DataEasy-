import Router from 'vue-router'
import Vue from "vue";
import {next} from "lodash/seq";

Vue.use(Router);

const router = new Router({
    routes: [
        {
            path: "/",
            component: () => import("../components/console/Login/LoginRegister"),
            meta: {
              title: "DataEasy登录注册页"
            },
        },
        {
            path: "/console",
            component: () => import("../components/console/Page/IndexMenu"),
            meta: {
              title: "DataEasy控制台页面"
            },
            children: [
                {
                    path: "PersonBlogView",
                    component: () => import("../components/console/PersonBlog/PersonBlog"),
                    meta: {
                        title: "个人博客"
                    },
                    children: [
                        {
                            path: "PersonBlogDetailView",
                            component: () => import("../components/views/blog/BlogDetail")
                        },
                        {
                            path: "PersonBlogEditorView",
                            component: () => import("../components/views/blog/AddBlog")
                        }
                    ]
                },
                {
                  path: "PersonInfoView",
                  component: () => import("../components/console/PersonInfo/PersonInfo"),
                  meta: {
                    title: "个人信息"
                  },
                },
                {
                    path: "DataView",
                    component: () => import('../components/views/console/DataView'),
                    meta: {
                      title: "数据服务"
                    },
                },

                {
                    path: 'ChartView',
                    component: () => import('../components/views/console/ChartView'),
                    meta: {
                        title: "数据可视化大屏"
                    }
                },

                {
                    path: 'DataAdd',
                    component: () => import('../components/views/console/DataAddView'),
                    meta: {
                        title: "数据添加"
                    }
                }
            ]
        },

        {
            path: '/Edit',
            component: () => import('../components/console/Edit/EditIndex'),
            children: [
                {
                    //用于编辑的组件
                    name: "EditCanvas",
                    path: "EditCanvas",
                    component: () => import('../components/views/edit/EditCanvas'),
                    meta: {
                        title: "可视化大屏编辑"
                    }
                },

                {
                    //用于预览的组件
                    name: 'PreviewCanvas',
                    path: 'PreviewCanvas',
                    component: () => import('../components/views/preview/PreviewCanvas'),
                    meta: {
                        title: "可视化大屏预览"
                    }
                }
            ]
        },
    ]
})

//切换页面标题
router.afterEach(function (to) {
    if (to.meta.title) {
        document.title = to.meta.title
    }
    next()
})

export default router

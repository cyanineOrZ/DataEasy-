<template>
  <div style="width: 100%;height: 100%;margin-top: 10px">
    <div class="blog-card" v-for="(item, index) in getBlogList" :key="item.title">
      <div class="blog-card-title">{{item.title}}
        <button class="blog-card-title-delete-button" @click="deleteBlog(item)">删除</button>
        <button class="blog-card-title-delete-button blog-card-title-edit-button" @click="editorBlog(item)">编辑</button>
      </div>
      <div class="blog-card-content" v-html="item.content" :class="{'active-blog-card-content' : activeIndex.includes(index)}"></div>
      <div class="blog-card-readadll" @click="handleReadAll(index)">{{readAllText(index)}}</div>
      <div class="blog-card-upload-time">{{item.uploadtime}}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return{
      activeIndex: []
    }
  },

  computed: {
    getBlogList: function () {
      return this.$parent.blogList
    },

    readAllText: function () {
      return (index) => {
        if (this.activeIndex.includes(index)) {
          return "收起博客"
        }
        else {
          return "展开博客"
        }
      }
    }
  },

  methods: {
    //监听是否点击了展开博客或者收起博客
    handleReadAll: function (index) {
      //如果该博客卡片不在活跃数组中，则添加进活跃数组中，并且让其调用activeBlog这个方法
      if (!this.activeIndex.includes(index)) {
        this.activeIndex.push(index)
      }
      //如果在该数组里面，则弹出该元素，并且调用取消活跃的方法
      else {
        this.activeIndex.splice(this.activeIndex.indexOf(index), 1)
      }
    },

    //删除该博客
    deleteBlog: function (item) {
      this.$confirm("您确定要删除这篇博客吗", "Tips", {
        confirmButtonText: "确认",
        cancelButtonText: "我点错了",
      })
          //点击确认删除
          .then(() => {
                this.$axios.post("http://localhost:8080/blog/deleteBlog", {
                  userid: item.userid,
                  blogid: item.blogid,
                })
                    .then(() => {
                      this.$message({
                        type: "success",
                        message: "删除博客成功"
                      })
                      this.$parent.getUserBlogs()
                    })
          },
          //点击取消删除
              () => {
                console.log("点击了取消删除")
              }
          )
    },

    //编辑该博客
    editorBlog: function (item) {
      this.$confirm("您确定要编辑这篇博客吗", "Tips", {
        confirmButtonText: "确认",
        cancelButtonText: "我点错了",
      })
          //点击确认后
          .then(() => {
            //通过路由传递参数
            this.$parent.editor = true
            this.$router.push({
              path: "/console/PersonBlogView/PersonBlogEditorView",
              query: {
                editTitle: item.title,
                editContent: item.content,
                editBlogid: item.blogid,
              }
            })
          }
          //点击取消后
          , () => {

      }
          )
    },

  }

}
</script>

<style scoped>
.blog-card {
  position: relative;
  padding: 10px;
  border: 2px solid grey;
  border-radius: 6px;
  margin-bottom: 20px;
  overflow: hidden;
}

.blog-card-title {
  position: relative;
  width: 100%;
  padding: 0 0 10px 0;
  border-bottom: 1px solid grey;
  font-size: 22px;
  font-weight: bold;
  min-height: 29px;
}

.blog-card-title-delete-button {
  position: absolute;
  top: 6px;
  right: 0;
  font-size: 10px;
  padding: 2px 4px;
  background: white;
  color: #e74c3c;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
  border: 1px solid #e74c3c;
  transition: 0.3s ease;
}

.blog-card-title-delete-button:hover {
  border: 1px solid #e74c3c;
  background: #e74c3c;
  color: white;
}

.blog-card-title-edit-button {
  right: 32px;
  border-radius: 4px 0 0 4px;
  color: #409EFF;
  border: 1px solid #409EFF;
}

.blog-card-title-edit-button:hover {
  background: #409EFF;
  border: 1px solid #409EFF;
}

.blog-card-content {
  max-height: 100px;
  padding: 10px 0;
  overflow: hidden;
}

.active-blog-card-content {
  max-height: none;
}

.blog-card-readadll {
  display: inline;
  font-size: 14px;
  color: #409EFF;
  cursor: pointer;
}

.blog-card-upload-time {
  position: absolute;
  font-size: 14px;
  color: grey;
  bottom: 5px;
  right: 10px;
}
</style>
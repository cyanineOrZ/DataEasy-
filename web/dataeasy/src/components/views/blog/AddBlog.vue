<template>
  <div class="addBlog-contain">

    <div class="addBlog-title-contain">
      <div class="Blog-Editor-Title">博客标题</div>
      <input type="text" class="blog-title-input" placeholder="请在此输入博客标题" :value="getCurrentTitle" ref="BlogTitle">
    </div>

    <BlogEdit></BlogEdit>

  </div>
</template>

<script>
import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";

dayjs.extend(utc)
dayjs.extend(timezone)
dayjs.tz.setDefault("Asia/Shanghai")

export default {
  data() {
    return {
      currentBlogid: this.$route.query.editBlogid,
      currentBlogTitle: this.$route.query.editTitle,
      reditorContent: this.$route.query.editContent,
      uploadtime: dayjs().format(('YYYY-MM-DD | HH:mm:ss'))
    }
  },
  components: {
    BlogEdit: () => import("./BlogEdit")
  },

  computed: {
    getReditorContentStatus: function () {
      if (this.reditorContent) {
        return true
      }
      else {
        return false
      }
    },

    getCurrentTitle: function () {
      if (this.$parent.editor) {
        return this.currentBlogTitle
      }
      else {
        return ""
      }
    }
  },

  methods: {
    //获取标题
    getTitle: function () {
      return this.$refs.BlogTitle.value
    }

  },
}

</script>

<style scoped>
.addBlog-title-contain {
  border: 2px solid grey;
  border-radius: 6px;
  padding: 10px 10px;
  margin-bottom: 10px;
}

.Blog-Editor-Title {
  border-bottom: 1px solid grey;
  padding-bottom: 10px;
  font-size: 14px;
  margin-bottom: 10px;
  user-select: none;
  opacity: 0.7;
}

.blog-title-input {
  font-size: 20px;
  font-weight: 700;
  padding: 6px 10px;
  border: none;
  border-bottom: 1px solid grey;
  outline: none;
}

.addBlog-contain {
  width: calc(100% - 20px);
  height: 100%;
  background: white;
  padding: 0 10px;
  border-bottom: 1px solid grey;
  border-radius: 6px;
  margin-bottom: 30px;
}
</style>
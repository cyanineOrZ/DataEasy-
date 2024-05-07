<template>
  <div class="editor-all-contain" ref="editor-contain">
    <div class="Blog-Editor-Title">博客内容</div>
    <template v-if="editor">
      <div class="editor-button-contain">
        <!--  字体设置   -->
        <div class="editor-button-config-box">
          <!--  加粗   -->
          <button class="editor-button" @click="editor.chain().focus().toggleBold().run()" :disabled="!editor.can().chain().focus().toggleBold().run()" :class="{ 'editor-button-active': editor.isActive('bold') }">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M8 11h4.5a2.5 2.5 0 1 0 0-5H8v5zm10 4.5a4.5 4.5 0 0 1-4.5 4.5H6V4h6.5a4.5 4.5 0 0 1 3.256 7.606A4.498 4.498 0 0 1 18 15.5zM8 13v5h5.5a2.5 2.5 0 1 0 0-5H8z"></path>
              </g>
            </svg>
          </button>
          <!--  斜体   -->
          <button class="editor-button" @click="editor.chain().focus().toggleItalic().run()" :disabled="!editor.can().chain().focus().toggleItalic().run()" :class="{ 'editor-button-active': editor.isActive('italic') }">
            <svg width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M15 20H7v-2h2.927l2.116-12H9V4h8v2h-2.927l-2.116 12H15z"></path>
              </g>
            </svg>
          </button>
          <!--  删除线   -->
          <button class="editor-button" @click="editor.chain().focus().toggleStrike().run()" :disabled="!editor.can().chain().focus().toggleStrike().run()" :class="{ 'editor-button-active': editor.isActive('strike') }">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M17.154 14c.23.516.346 1.09.346 1.72 0 1.342-.524 2.392-1.571 3.147C14.88 19.622 13.433 20 11.586 20c-1.64 0-3.263-.381-4.87-1.144V16.6c1.52.877 3.075 1.316 4.666 1.316 2.551 0 3.83-.732 3.839-2.197a2.21 2.21 0 0 0-.648-1.603l-.12-.117H3v-2h18v2h-3.846zm-4.078-3H7.629a4.086 4.086 0 0 1-.481-.522C6.716 9.92 6.5 9.246 6.5 8.452c0-1.236.466-2.287 1.397-3.153C8.83 4.433 10.271 4 12.222 4c1.471 0 2.879.328 4.222.984v2.152c-1.2-.687-2.515-1.03-3.946-1.03-2.48 0-3.719.782-3.719 2.346 0 .42.218.786.654 1.099.436.313.974.562 1.613.75.62.18 1.297.414 2.03.699z"></path>
              </g>
            </svg>
          </button>

        </div>
        <!--  字号设置   -->
        <div class="editor-button-config-box">
          <!--  设置文本样式区域   -->
          <button class="editor-button" @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'editor-button-active': editor.isActive('heading', { level: 2 }) }">
            H1
          </button>
          <button class="editor-button" @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'editor-button-active': editor.isActive('heading', { level: 1 }) }">
            H2
          </button>
          <button class="editor-button" @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'editor-button-active': editor.isActive('heading', { level: 3 }) }">
            H3
          </button>
          <button class="editor-button" @click="editor.chain().focus().toggleHeading({ level: 4 }).run()" :class="{ 'editor-button-active': editor.isActive('heading', { level: 4 }) }">
            H4
          </button>
          <button class="editor-button" @click="editor.chain().focus().toggleHeading({ level: 5 }).run()" :class="{ 'editor-button-active': editor.isActive('heading', { level: 5 }) }">
            H5
          </button>
        </div>
        <!--  段落设置   -->
        <div class="editor-button-config-box">
          <!--  左中右对齐   -->
          <button class="editor-button" @click="editor.chain().focus().setTextAlign('left').run()"  :class="{ 'editor-button-active': editor.isActive({ textAlign: 'left' }) }">
            <svg t="1710723381365" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4688" width="16" height="16"><path d="M96 128h832v96H96zM96 576h832v96H96zM96 352h576v96H96zM96 800h576v96H96z" p-id="4689"></path></svg>
          </button>
          <button class="editor-button" @click="editor.chain().focus().setTextAlign('center').run()"  :class="{ 'editor-button-active': editor.isActive({ textAlign: 'center' }) }">
            <svg t="1710723423335" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4664" width="16" height="16"><path d="M96 128h832v96H96zM96 576h832v96H96zM224 352h576v96H224zM224 800h576v96H224z" p-id="4665"></path></svg>
          </button>
          <button class="editor-button" @click="editor.chain().focus().setTextAlign('right').run()" :class="{ 'editor-button-active': editor.isActive({ textAlign: 'right' }) }">
            <svg t="1710723435729" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4816" width="16" height="16"><path d="M96 128h832v96H96zM96 576h832v96H96zM352 352h576v96H352zM352 800h576v96H352z" p-id="4817"></path></svg>
          </button>

          <!--  强制换行   -->
          <button class="editor-button" @click="editor.chain().focus().setHardBreak().run()">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M15 18h1.5a2.5 2.5 0 1 0 0-5H3v-2h13.5a4.5 4.5 0 1 1 0 9H15v2l-4-3 4-3v2zM3 4h18v2H3V4zm6 14v2H3v-2h6z"></path>
              </g>
            </svg>
          </button>

          <!--  设置点标题   -->
          <button class="editor-button" @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'editor-button-active': editor.isActive('bulletList') }">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M8 4h13v2H8V4zM4.5 6.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 7a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm0 6.9a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zM8 11h13v2H8v-2zm0 7h13v2H8v-2z"></path>
              </g>
            </svg>
          </button>

          <!--  设置数字标题   -->
          <button class="editor-button" @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'editor-button-active': editor.isActive('orderedList') }">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M13 3v7.267l6.294-3.633 1 1.732-6.293 3.633 6.293 3.635-1 1.732L13 13.732V21h-2v-7.268l-6.294 3.634-1-1.732L9.999 12 3.706 8.366l1-1.732L11 10.267V3z"></path>
              </g>
            </svg>
          </button>

          <!--  Tab符   -->
          <button class="editor-button" @click="editor.chain().focus().toggleBlockquote().run()" :class="{ 'editor-button-active': editor.isActive('blockquote') }">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M4.583 17.321C3.553 16.227 3 15 3 13.011c0-3.5 2.457-6.637 6.03-8.188l.893 1.378c-3.335 1.804-3.987 4.145-4.247 5.621.537-.278 1.24-.375 1.929-.311 1.804.167 3.226 1.648 3.226 3.489a3.5 3.5 0 0 1-3.5 3.5c-1.073 0-2.099-.49-2.748-1.179zm10 0C13.553 16.227 13 15 13 13.011c0-3.5 2.457-6.637 6.03-8.188l.893 1.378c-3.335 1.804-3.987 4.145-4.247 5.621.537-.278 1.24-.375 1.929-.311 1.804.167 3.226 1.648 3.226 3.489a3.5 3.5 0 0 1-3.5 3.5c-1.073 0-2.099-.49-2.748-1.179z"></path>
              </g>
            </svg>
          </button>

          <!--  设置横线   -->
          <button class="editor-button" @click="editor.chain().focus().setHorizontalRule().run()" :class="{ 'editor-button-active' : editor.isActive('HorizontalRule')}">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M2 11h2v2H2v-2zm4 0h12v2H6v-2zm14 0h2v2h-2v-2z"></path>
              </g>
            </svg>
          </button>
        </div>
        <!--  代码块   -->
        <div class="editor-button-config-box">
          <!--  设置代码区域   -->
          <button class="editor-button" @click="editor.chain().focus().toggleCodeBlock().run()" :class="{ 'editor-button-active': editor.isActive('codeBlock') }">
            <svg class="icon w-6 h-6 text-gray-200" width="16" height="16" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
              <g>
                <path fill="none" d="M0 0h24v24H0z"></path>
                <path d="M16.95 8.464l1.414-1.414 4.95 4.95-4.95 4.95-1.414-1.414L20.485 12 16.95 8.464zm-9.9 0L3.515 12l3.535 3.536-1.414 1.414L.686 12l4.95-4.95L7.05 8.464z"></path>
              </g>
            </svg>
          </button>
        </div>
        <!--  操作   -->
        <div class="editor-button-config-box">
          <!--  撤回   -->
          <button class="editor-button" @click="editor.chain().focus().undo().run()" :disabled="!editor.can().chain().focus().undo().run()">
            <svg t="1710723680547" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4818" width="16" height="16"><path d="M512 992C246.912 992 32 777.088 32 512 32 246.912 246.912 32 512 32c265.088 0 480 214.912 480 480 0 265.088-214.912 480-480 480z m0-64c229.76 0 416-186.24 416-416S741.76 96 512 96 96 282.24 96 512s186.24 416 416 416z" fill="#000000" p-id="4819"></path><path d="M336 480a32 32 0 0 0 0 64h416a32 32 0 0 0 0-64h-416z" fill="#000000" p-id="4820"></path><path d="M316.928 512l151.36-170.784a32 32 0 1 0-47.872-42.432l-170.208 192a32 32 0 0 0 0 42.432l170.24 192a32 32 0 1 0 47.872-42.432L316.928 512z" fill="#000000" p-id="4821"></path></svg>
          </button>
          <!--  前进   -->
          <button class="editor-button" @click="editor.chain().focus().redo().run()" :disabled="!editor.can().chain().focus().redo().run()">
            <svg t="1710723668872" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4664" width="16" height="16"><path d="M512 992C246.912 992 32 777.088 32 512 32 246.912 246.912 32 512 32c265.088 0 480 214.912 480 480 0 265.088-214.912 480-480 480z m0-64c229.76 0 416-186.24 416-416S741.76 96 512 96 96 282.24 96 512s186.24 416 416 416z" fill="#000000" p-id="4665"></path><path d="M688 480a32 32 0 0 1 0 64h-416a32 32 0 0 1 0-64h416z" fill="#000000" p-id="4666"></path><path d="M707.072 512l-151.36-170.784a32 32 0 1 1 47.872-42.432l170.208 192a32 32 0 0 1 0 42.432l-170.24 192a32 32 0 1 1-47.872-42.432L707.072 512z" fill="#000000" p-id="4667"></path></svg>
          </button>
        </div>

      </div>
    </template>
    <div ref="editor-input">
      <editor-content :editor="editor" class="editorClearOutline" ></editor-content>
    </div>

    <button class="pubButton" @click="pubBlog">{{pubButtonText}}</button>

  </div>
</template>

<script>
import { Editor, EditorContent } from '@tiptap/vue-2'
import StarterKit from '@tiptap/starter-kit'
import TextAlign from '@tiptap/extension-text-align'
import {HardBreak} from "@tiptap/extension-hard-break";

export default {
  data() {
    return {
      editor: null,
      reditor: false
    }
  },
  components: {
    EditorContent,
  },

  computed: {
    pubButtonText: function () {
      if (this.reditor) {
        return "保留编辑"
      }
      else {
        return "发布博客"
      }
    },
  },
  mounted() {
    let innerContent
    this.reditor = this.$parent.getReditorContentStatus
    if (this.reditor) {
      innerContent = this.$parent.reditorContent
    }
    else {
      innerContent = `<p>请在此输入博客内容</p>`
    }
    //初始化编辑器
    this.editor = new Editor({
      content: innerContent,
      autofocus: true,
      editable: true,
      injectCSS: false,
      // 拓展
      extensions: [
        StarterKit,
        TextAlign.configure({
          types: ['heading', 'paragraph'],
        }),
        HardBreak.configure({
          keepMarks: true,
        })
      ]
    })
    {
      //限制宽度
      const editorElement = this.$refs["editor-contain"]
      const editorInputElement = this.$refs["editor-input"]
      const resizeObserver = new ResizeObserver(entries => {
        for (let entry of entries) {
          const newWidth = entry.contentRect.width
          editorInputElement.style.width = newWidth - 24 + 'px'
        }
      })
      resizeObserver.observe(editorElement)

      this.$once('hook:beforeDestroy', () => {
        resizeObserver.unobserve(editorElement);
        resizeObserver.disconnect();
      })
    }
  },

  beforeDestroy() {
    this.reditor = false
    this.editor.destroy()
  },

  methods: {

    pubBlog: function () {
      console.log(this.reditor)
      console.log()
      this.$confirm("您确定要发表这篇博客吗", "Tips", {
        confirmButtonText: "确认发表",
        cancelButtonText: "手滑,按错了"
      })
          //确认发表
          .then(() => {
            let url
            let message
            if (this.reditor) {
              url ="http://localhost:8080/blog/editBlog"
              message = "编辑成功"
            }
            else {
              url = "http://localhost:8080/blog/addBlog"
              message = "发布成功"
            }
            this.$axios.post(url, {
              userid: 1,
              blogid: this.$parent.currentBlogid,
              title: this.$parent.getTitle(),
              content: this.editor.getHTML(),
              uploadtime: this.$parent.uploadtime,
            }, {
              headers: {
                "Content-Type": "application/json"
              }
            })
                .then((response) => {
                      console.log(response.data)
                      this.$parent.$parent.getUserBlogs()
                      this.$message({
                        type: "success",
                        message: message
                      })
                      this.$parent.$parent.editor = false
                      this.$router.push("/console/PersonBlogView/PersonBlogDetailView")
                    }
                    ,() => {
                      console.log("error")
                    }
                )
          }
          //取消发表
          ,() => {}
          )
    }
  }
}
</script>
<style>
.Blog-Editor-Title {
  border-bottom: 1px solid grey;
  padding-bottom: 10px;
  font-size: 14px;
  margin-bottom: 10px;
  user-select: none;
  opacity: 0.7;
}

.pubButton {
  position: absolute;
  font-size: 14px;
  border-radius: 6px;
  background: white;
  border: 2px solid #0099CC;
  color: #0099CC;
  padding: 6px 6px;
  bottom: -45px;
  right: 0;
  transition: 0.3s ease;
}
.pubButton:hover {
  color: white;
  background: #0099CC;
}

.editor-button {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 3px 2px;
  margin: 0 1px;
  background: whitesmoke;
  border: 2px solid grey;
  border-radius: 4px;
  transition: 0.3s ease;
}

.editor-button:hover {
  border: 2px solid darkblue;
  background: #0099CC;
}

.editor-button-active {
  background: #0099CC;
  transition: 0.3s ease;
  border: 2px solid black;
}

.editor-all-contain {
  position: relative;
  padding: 10px;
  border: 2px solid grey;
  border-radius: 6px;
}

.editor-button-contain {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  border-bottom: 1px solid grey;
  padding-bottom: 8px;
  margin-bottom: 8px;
  user-select: none;
}

.editor-button-config-box {
  display: flex;
  position: relative;
  flex-wrap: nowrap;
  padding: 0 7px 0 5px;
  margin-bottom: 5px;
}

.editor-button-config-box::after {
  position: absolute;
  content: "";
  width: 2px;
  height: 100%;
  background: grey;
  right: 0;
  top: 0;
}

.editorClearOutline div{
  outline: none;
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


</style>
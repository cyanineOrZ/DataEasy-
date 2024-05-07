<template>
  <div style="margin-left: 20px" v-if="chartList">
    <el-row :gutter="36" style="min-width: 1600px; max-width: 1600px">
      <!--  展示可视化成果的区域  -->
      <el-col v-for="(item, index) in chartList" :key="index" :span="6">
        <div style="margin-bottom: 20px">
          <el-card :body-style="{padding: '0px'}" shadow="hover">
            <img class="image" @click="editChart(item)" :src="item.coverimage" />
            <div style="padding: 14px; min-height: 21px">
              <span>{{item.title}}</span>

              <!--  下拉菜单区域  -->
              <el-dropdown style="float:right;" trigger="hover">
                <i class="el-icon-more"></i>
                <el-dropdown-menu slot="">
<!--                  <el-dropdown-item @click.native="checkChart(item)">查看</el-dropdown-item>-->
                  <el-dropdown-item @click.native="editChart(item)">编辑</el-dropdown-item>
                  <el-dropdown-item @click.native="renameChartTitle(item)">重命名</el-dropdown-item>
                  <el-dropdown-item @click.native="reCover(item)">编辑封面</el-dropdown-item>
                  <el-dropdown-item @click.native="deleteChartData(item)">删除</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </el-card>
        </div>
      </el-col>

      <!--  添加可视化的卡牌区域  -->
      <el-col :span="6">
        <el-card :body-style="{padding: '0px'}" shadow="hover" @click.native="addNewChart">
          <div class="add-card"><i class="el-icon-circle-plus"></i></div>
        </el-card>
      </el-col>
    </el-row>

    <!--  创建项目和添加封面的对话框，用来上传东西  -->
    <el-dialog :visible.sync="isChangeCover"
               :title="dialogTitle"
               width="400px"
               top="80px"
               :closed="cancelUpload"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :show-close="false">
      <el-form style="width: 300px;">
        <div v-show="(isCreate && isChangeCover)">
          <el-form-item>项目名称</el-form-item>
          <el-form-item style="width: 250px;">
            <el-input v-model="creatChartTitle" :value="creatChartTitle"></el-input>
          </el-form-item>
        </div>
        <el-form-item>编辑封面</el-form-item>
        <el-form-item>
          <!--     封面上传     -->
          <el-upload
              action=""
              class="upload"
              :auto-upload="false"
              ref="chartCoverUpload"
              style="width: 245px;"
              :drag="false"
              :multiple="false"
              :show-file-list="false"
              :on-change="handleChartCoverUploadCheck"
              :http-request="uploadRequest"
          >
            <div class="bg-preview-wrapper">
              <img class="bg-preview" v-show="previewImage" :src="previewImage">
              <i v-show="!previewImage" class="el-icon-plus avatar-uploader-icon" style="min-height: 150px; max-height: 150px; height: 150px; display: block"></i>
            </div>
            <div class="el-upload__tip" slot="tip" style="max-width: 260px; user-select: none" >请上传图片文件[目前支持 .png, .jpg, .jpeg]为后缀的文件 ,目前仅支持上传小于<span style="color: red">2</span>MB的图片</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="el-dialog__footer">
        <el-button @click="cancelUpload">取消</el-button>
        <el-button type="primary" @click.native="submitUpload">确认</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

export default {

  name: "ChartView",

  data() {
    return {
      id: localStorage.getItem("id"),
      chartList: [],
      //是否为新创建项目
      isCreate: false,
      //编辑封面状态
      isChangeCover: false,
      //
      creatChartTitle: "",

      //预览图片的临时url
      previewImage: "",
      //当前选择的图表项目
      currentItem: {},
    };
  },

  computed: {
    dialogTitle: function () {
      if (this.isCreate && this.isChangeCover) {
        return "新建项目"
      }
      else {
        return "编辑封面"
      }
    }
  },

  mounted() {
    this.getChartData()
  },

  methods: {
    //获取数据，根据userid
    getChartData: function () {
      this.$axios.post("chart/getChartData", {
        userid: localStorage.getItem("id")
      }).then((response) => {
        this.chartList = response.data
        console.log("chart数据请求成功", response)
      }, (error) => {
        console.log(error)
      })
    },

    //上传项目封面前进行的检验
    handleChartCoverUploadCheck: function (file, fileList) {
      console.log("检验")
      //如果文件为空，则不进行检验
      if (!file) {
        this.$message({
          type: 'warning',
          message: "封面图片文件为空，请检查"
        })
        this.creatChartTitle = ''
        return null
      }
      //文件不为空
      else {
        //动态文件列表
        if (fileList.length > 1) {
          //只保留最后一个
          fileList.splice(0, 1)
        }
        //检验文件是否为指定的格式
        const fileTypeLimit = ['jpg', 'png', 'jpeg']
        let thisFileType = file.name.split(".")[1]
        const typeResult = fileTypeLimit.some((item) => {
          return item === thisFileType
        })
        //如果文件不合规
        if (!typeResult) {
          this.$message({
            type: "warning",
            message: "改格式的文件不支持上传，请重新选择文件"
          })
          //清空文件
          this.$refs.chartCoverUpload.clearFiles()
          return false
        }
        //检验上传的文件的大小是否合规
        else if (file.size / 1024 / 1024 > 10) {
          this.$message({
            type: 'warning',
            message: "上传的文件不得大于10MB"
          })
          //清空文件
          this.$refs.chartCoverUpload.clearFiles()
          return false
        }
        //创建临时url，预览图片
        this.previewImage = URL.createObjectURL(file.raw)
      }
    },

    //新建项目或者编辑封面的请求
    uploadRequest: function (fileObject) {
      //实现手动上传
      console.log("上传", fileObject)
      //新建项目的请求
      if (this.isCreate === true) {
        if (this.creatChartTitle !== "") {
          //新建项目的请求
          this.$axios.post("chart/creatChart", {
            userid: localStorage.getItem("id"),
            file: fileObject.file,
            title: this.creatChartTitle
          }, {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          })
              .then((response) => {
                console.log(response)
                this.$message({
                  type: 'success',
                  message: "新建项目成功"
                })
                //清空文件列表
                this.cancelUpload()
                this.getChartData()
              })
        }
        else {
          this.$message({
            type: 'warning',
            message: "项目标题不得为空，请检查"
          })
          this.$refs.chartCoverUpload.clearFiles()
          console.log(this.$refs.chartCoverUpload)
        }
      }
      //封面更改的请求
      else {
        this.$axios.post("chart/uploadChartCover", {
          chartid: this.currentItem.chartid,
          file: fileObject.file
        }, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
            .then((response) => {
              console.log(response)
              this.$message({
                type: 'success',
                message: "编辑封面成功"
              })
              //清空文件列表
              this.cancelUpload()
              this.getChartData()
            })
      }

    },

    //上传封面图片或者新增项目取消
    cancelUpload: function () {
      this.previewImage = ""
      this.$refs.chartCoverUpload.clearFiles()
      this.isChangeCover = false
      this.isCreate = false
    },

    //确认提交，关闭对话框，启动表格提交
    submitUpload: function () {
      //清空预览
      this.previewImage = ""
      console.log(this.$refs.chartCoverUpload.uploadFiles)
      if (this.$refs.chartCoverUpload.uploadFiles.length === 0) {
        this.$message({
          type: "warning",
          message: "上传的文件不得为空，请检查"
        })
      }
      else {
        this.$refs.chartCoverUpload.submit()
      }
      //关闭对话框，重置状态
      this.isCreate = false
      this.isChangeCover = false
    },

    //创建项目 isChangeCover = true && isCreate = true
    addNewChart: function () {
      this.isChangeCover = true
      this.isCreate = true
    },

    //编辑项目
    editChart: function (item) {
      this.$confirm("确认编辑项目名为 " + item.title + "的项目吗", "Tips", {
        confirmButtonText: "确认编辑",
        cancelButtonText: "我手滑了哎嘿"
      })
          //点击确认
          .then(() => {
            this.$router.push({
              path: "/Edit/EditCanvas",
              query: {
                chartid: item.chartid,
                editor: true
              }
            })
          }
          ,() => {

              }
          )
    },

    //重命名项目
    renameChartTitle: function (item) {
      this.$prompt("重命名项目", "重命名", {
        inputValue: item.title,
        confirmButtonText: "确认",
        cancelButtonText: "取消",
      })
          //点击确认后
          .then((event) => {
            if (event.value) {
              this.$axios.post("chart/renameChartTitle", {
                chartid: item.chartid,
                reTitle: event.value
              })
                  //请求成功发送，并接收到响应后
                  .then((response) => {
                        if (response) {
                          this.$message({
                            type: "success",
                            message: "重命名成功"
                          })
                          this.getChartData()
                        }
                        console.log("重命名后的数据", response.data)
                      },
                      //请求发送成功，但是响应回答为失败
                      (error) => {
                        console.log(error)
                      })
            }
            else {
              this.$message({
                type: "warning",
                message: "重命名的值不可为空"
              })
            }
          },
          //点击取消后
              () => {
                console.log("重命名图表数据取消")
              }
          )
    },

    //重新编辑封面,isChangeCover = true
    reCover: function (item) {
      this.currentItem = item
      this.isChangeCover = true
    },

    //删除项目
    deleteChartData: function (item) {
      this.$prompt(("您确认要删除这个项目吗 ， 请手动输入项目名称再进行删除" + item.title), "删除项目", {
        confirmButtonText: "确认删除",
        cancelButtonText: "抱歉手滑了",
        inputPlaceholder: "请输入项目名"
      })
          //点击确认后
          .then((input) => {
            console.log(input.value)
            if (input.value !== item.title) {
              this.$message({
                type: "warning",
                message: "项目名称输入错误，请检查"
              })
            }
            else {
              this.$axios.post("chart/deleteChart", {
                userid: item.userid,
                chartid: item.chartid
              })
                  //删除成功
                  .then(() => {
                    this.$message({
                      type: 'success',
                      message: "删除项目成功"
                    })
                    //更新数据
                    this.getChartData()
                  }
                  //删除失败
                  ,() => {

                      }
                  )
            }
          }
          ,() => {

              }
          )
    },

  }
};
</script>

<style scoped>
.image {
  width: 100%;
  height: 200px;
  display: block;
  opacity: 0.8;
  transition: opacity 0.3s ease;
}

.image:hover {
  cursor: pointer;
}

.el-card .add-card {
  height: 250px;
  line-height: 250px;
  text-align: center;
  font-size: 82px;
  color: rgba(0, 0, 0, 0.08);
}

.bg-preview-wrapper {
  width: 100%;
  border-radius: 4px;
  border: 1px solid #0099CC;
  min-height: 150px;
  max-height: 150px;
  min-width: 245px;
  height: 150px;
  overflow: hidden;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100%;
  height: 150px;
  line-height: 150px;
  text-align: center;
}

.bg-preview {
  height: 150px;
  max-width: 250px;
  object-fit: cover;
}


</style>

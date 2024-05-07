<template>
  <div style="margin-left: 20px">
    <el-row style="margin-bottom: 20px">
      <el-button type="primary" @click="addData">新增数据源</el-button>
    </el-row>

    <el-table :data="connectionList" style="min-width: 1200px" :key="tableKey">
      <el-table-column
          v-for="(item, index) in this.tableColumnsList"
          :key="index"
          :label="item.label"
          :prop="item.prop"
      ></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="small" @click="renameUserData(scope.row)" type="primary">重命名</el-button>
          <el-button size="small" @click="deleteUserData(scope.row)" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>

export default {
  name: "DataView",

  data() {
    return {
      tableColumnsList: [
        {label: "数据源名称", prop: "dataname"},
        {label: "工作表名称", prop: "sheetname"},
        {label: "数据维度", prop: "dimension"},
        {label: "数据指标", prop: "metrics"},
        {label: "数据源上传时间", prop: "uploadtime"},
      ],
      connectionList: [],
      watitingDelete: {userid: undefined, dataid: undefined},
      tableKey: 0,
    }
  },

  mounted() {
    this.getUserData()
  },

  methods: {
    //点击添加数据，跳转到添加数据界面
    addData: function() {
      this.$router.push('DataAdd')
    },

    getUserData: function () {
      //获取该用户的数据列表
      this.$axios.post("userdata/getUserData", {
        userid: localStorage.getItem("id")
      })
          .then((response) => {
            this.connectionList = response.data
            console.log("用户数据列表请求成功", response)
          }, (error) => {
            console.log(error)
          })
    },

    renameUserData: function (scopeRow) {
      this.$prompt("重命名数据源", "重命名", {
        inputValue: scopeRow.name,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      })
          //点击确认后
          .then((event) => {
            this.$axios.post("userdata/renameUserData", {
              dataid: scopeRow.dataid,
              rename: event.value
            })
                //请求成功发送，并且成功接收到响应
                .then((response) => {
                  if (response) {
                    this.$message({
                      type: "success",
                      message: "重名名成功"
                    })
                    this.getUserData()
                  }
                },
                    //请求成功发送，但是响应报错，并且错误码为930
                    (error) => {
                  if (error.code === "930") {
                    this.$message({
                      type: "warning",
                      message: "重名名的数据源名字不可为空"
                    })
                  }
                })
          },
              //点击取消后
              () => {
          })
    },


    //删除用户数据
    deleteUserData: function (scopeRow){
      //暂存id，使用
      this.watitingDelete.userid = scopeRow.userid
      this.watitingDelete.dataid = scopeRow.dataid

      this.$confirm("是否删除该数据源", {
        confirmButtonText: "确定删除",
        cancelButtonText: "取消",
        type: "warning"
      })
          //点击确认删除
          .then(() => {
            if(scopeRow.userid !== undefined && scopeRow.dataid !== undefined) {
              this.$axios.post("userdata/deleteUserData", {
                userid: scopeRow.userid,
                dataid: scopeRow.dataid
              }).then(() => {
                this.$message({
                  type: 'success',
                  message: "删除数据成功"
                })
                this.getUserData()
              }, () => {

              })
            }
          },
          //点击取消
              () => {})

    }
  }
}
</script>
<template>
  <div>
    <el-button type="primary" @click="backDataView" style="margin-bottom: 20px;margin-left: 40px">返回</el-button>
    <el-row :gutter="20" style="margin: 0">
      <el-col :span="12">
        <!--    上传数据区域      -->
        <el-form ref="ruleForm" :model="form" :rules="rules" :hide-required-asterisk="true" label-width="180px" >

          <!--    选定数据源的名称      -->
          <el-form-item label="名称" prop="name"><el-input v-model="form.name" placeholder="请输入数据源名称" style="width:300px;" maxlength="15" show-word-limit></el-input></el-form-item>
          <!--    上传文件区域      -->
          <el-form-item label="数据文件">
            <el-upload class="upload"
                       ref="fileUpload"
                       drag
                       action="/"
                       :auto-upload="false"
                       :limit="1"
                       :on-exceed="onFilesLimit"
                       :on-change="importFile"
                       :on-remove="onFileRemove"
                       style="min-width: 530px"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip" style="min-width: 530px">请上传 Excel 表格文件 [支持 .xlsx, .xlc, .xlm, .xls, .xlt, .xlw]为后缀的文件 ，其中第一行为表头,目前仅支持上传<span style="color: red">小于100KB</span>的文件</div>
            </el-upload>
          </el-form-item>

          <!--    选择张工作表作为数据源     -->
          <el-form-item label="选择工作表" prop="sheetName" style="min-width: 530px">
            <el-select placeholder="请选择" v-model="form.sheetName" @change="selectedSheetName">
              <el-option v-for="(item, index) in form.sheetNameList" :key="index" :label="item" :value="item"></el-option>
            </el-select>
            <div class="el-upload__tip">默认为读取的文件的第一张工作表</div>
          </el-form-item>

          <!--    选择哪一列作为具体数据      -->
          <el-form-item label="选择维度" prop="dimension"  style="min-width: 530px">
            <el-select placeholder="请选择" v-model="form.dimension" v-show="this.form.sheetName !== ''" @change="selectColumnsRaw">
              <el-option v-for="item in form.columnsRaw" :key="item" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>

          <!--     选择具体的指标数据     -->
          <el-form-item label="选择指标" prop="metrics" style="min-width: 530px">
            <el-checkbox-group v-model="form.metrics" v-show="form.dimension !== ''" ref="columnsRaw">
              <el-checkbox v-for="item in form.columnsRaw" :key="item" :label="item" :disabled="item === form.dimension"></el-checkbox>
            </el-checkbox-group>
          </el-form-item>

          <!--     提交按钮     -->
          <el-form-item><el-button @click="onSubmit">提交</el-button></el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import XLSX from 'xlsx'
const Clone = require('lodash')
export default {
  name: "DataAddView",
  data() {
    return {
      // 上传的文件预览
      form: {
        //工作表列表
        sheetNameList: [],
        //该工作表的第一行的数据
        columnsRaw: [],

        //数据源名字
        name: '',

        //数据源所在工作表名字
        sheetName: '',

        //数据源所在工作表索引
        sheetIndex: '',

        //维度
        dimension: '',

        //数据指标
        metrics: [],

        //数据文本
        data: [],
      },
      allData: [],
      //上传的规则规则
      rules: {
        name: [{ required: true, message:'请输入数据源名称',min:1, max:15, trigger: 'blur' }],
        data: [{ type: 'array', required: true, min: 1, message: '请上传数据', trigger: 'blur' }],
        sheetName: [{ required: true, message: "请选择工作表", trigger: 'blur'}],
        dimension: [{ required: true, message: '请选择维度', trigger: 'blur' }],
        metrics: [{ type: 'array', required: true, min: 1, message: '请选择指标', trigger: 'blur' }]
      },
    }
  },
  computed: {
  //根据维度和指标生成的数据，最终传入数据库的数据(只有columns)
    newData() {
      let columns = this.form.metrics.concat()
      //往该数列前面加入数据
      columns.unshift(this.form.dimension)
      //这里是列数据,也就是图表数据的columns， 第一个为维度， 其他为指标
      return columns
    }
  },
  methods: {
    //返回数据视图组件
    backDataView: function () {
      this.$router.push("DataView")
    },
    //限制上传文件数量，当数量超出限制发送信息
    onFilesLimit: function () {
      this.$message.error("上传文件数量超出限制")
    },
    //移除文件时，重置当前组件的数据
    onFileRemove: function () {
      this.$refs.fileUpload.clearFiles()
      this.form.sheetNameList = []
      this.form.fileData = ""
      this.form.dimension = ""
      this.form.metrics = []
    },

    //上传文件
    importFile: function (file) {
      if(this.form.data === null) {
        this.onFileRemove()
      }

      //截取文件名，获取文件后缀
      const fileTypeLimit = ['xlsx', 'xlc', 'xlm', 'xls', 'xlt', 'xlw']
      let thisFileType = file.name.split('.')[1]

      const result = fileTypeLimit.some((item) => {
        return item === thisFileType
      })
      // 如果文件后缀不在这些之中，则清空文件上传框内容，并提示
      if (!result) {
        this.$message.error("该格式的文件不支持上传，请重新选择文件")
        this.$refs.fileUpload.clearFiles()
        return false
      }

      //限制上传文件的大小
      else if (file.size / 1024 > 100) {
        this.$message.error("上传的文件的大小不得超过5KB")
        this.$refs.fileUpload.clearFiles()
        return false
      }
      else {
        //如果上传的文件符合格式，并且成功地被解析成JSON数据，加载工作表数据，并等待选取工作表
        this.file2Xce(file).then((resoleParams) => {
          this.form.sheetNameList = resoleParams[0]
          //将整体数据（对象【成员属性：工作表名，工作表数据】复制一份）
          return this.allData = Clone.cloneDeep(resoleParams[1])
        })
      }
    },
    //将上述后缀的文件转换为JSON(每个文件可能有多个工作表)
    file2Xce(file) {
      //指针指向VC
      let that = this
      return new Promise( function (resolve, reject) {
        const reader = new FileReader()
        try{
          //读取数据
          reader.onload = function (event) {
            const data = event.target.result
            that.form.fileData = data
            this.wb = XLSX.read(data, {type: "binary"})
            //分别对不同的工作表进行处理，以对象的形式，存入到result中, 用来获取数据指标
            const result = []
            this.wb.SheetNames.forEach((sheetName) => {
              result.push(
                  {
                    sheetName: sheetName,
                    sheet: XLSX.utils.sheet_to_json(this.wb.Sheets[sheetName])
                  }
              )
            })
            //
            let resoleParams = [this.wb.SheetNames, result]
            resolve(resoleParams)
          }
          //成功解析，并且
          reader.readAsBinaryString(file.raw)
        }catch (error) {
          reject(error)
        }
      })
    },
    //选择工作表后，进行的数据操作
    selectedSheetName: function () {
      if (this.allData && this.allData.length > 0) {
        /**
         * allData
         * [
         *  {
         *    工作表名字
         *    sheetName: sheet,
         *    工作表数据（整体）
         *    sheet: sheetData
         *  }
         * ]
         *
         * */
        //根据选择到工作表，读取其中的数据作为数据项（包括了表头和数据【整个数据】）
        this.form.sheetIndex = this.allData.map((item) => item.sheetName).indexOf(this.form.sheetName)

        //将第一行的数据转换为表头，并读取在form的columnsRaw中，
        this.form.columnsRaw = Object.keys(this.allData[0].sheet[0])
      }
    },
    //重新选择数据维度后清空数据指标
    selectColumnsRaw: function () {
      return this.form.metrics = []
    },

    //提交数据
    onSubmit: function () {
      let that = this

      //检验规则是否通过
      that.$refs['ruleForm'].validate((valid) => {
        //如果检验通过, 中间弹窗，并且再次询问
        if (valid) {
          that.$confirm("确定提交名为" + that.form.name + '的数据源吗' + '\n请检查您的数据指标是否为' + that.form.dimension + '\t请检查您的数据指标是否为' + that.form.metrics, {
            confirmButtonText: "确定提交",
            cancelButtonText: "取消提交",
            type: "warning",
            center: false
          })
              //确定提交的操作
              .then(() => {
                //1、确定数据
                //这里是选择到的工作表的全部数据, 0表示数据项， 1是工作表名字
                let tempResult = this.allData[this.form.sheetIndex].sheet
                let sheetNameList = this.newData
                //进行数据数组中对象的数据的顺序的排序（图表插件需要使用特定的顺序）
                let result = []
                tempResult.forEach((item) => {
                  const obj = {}
                  for(let i = 0; i < sheetNameList.length; i++) {
                    if (item[sheetNameList[i]] === null || item[sheetNameList[i]] === undefined) {
                      item[sheetNameList[i]] = 0
                    }
                    obj[sheetNameList[i]] = item[sheetNameList[i]]
                  }
                  result.push(obj)
                })
                //通过JSON转为字符串
                this.form.data = JSON.stringify(result)
                //检验将字符串转回原来的数据格式
                // console.log(JSON.parse(this.form.data))

                //2、发送请求，保存数据
                this.$axios.post("userdata/setUserData", {
                  userid: localStorage.getItem("id"),
                  dataname: this.form.name,
                  sheetname: this.form.sheetName,
                  dimension: this.form.dimension,
                  metrics: JSON.stringify(this.form.metrics),
                  columns: JSON.stringify(this.newData),
                  exdata: this.form.data,
                  uploadtime: Date.now()
                }, {
                  headers: {
                    "Content-Type": "application/json"
                  }
                }).
                  //  如果请求发送成功，则
                  then((response) => {
                    if (response.code === "0") {
                      this.$message({
                        type: "success",
                        message: "上传数据成功"
                      })
                      //返回到数据标题
                      that.$router.push("/console/DataView")
                    }
                  },
                    //  如果请求发送失败，则
                    (error) => {
                    console.log(error)
                      //检验重名失败

                  })
              },() => {
                //取消提交的操作
              })
        }
      })
    }
  }
}
</script>
<style>
.data-preview {
  padding: 10px;
  background-color: #f9f9f9;
}
.data-preview .tips {
  margin: 8px 4px 12px;
  color: #909399;
  font-size: 16px;
}
.data-preview .tips span {
  margin-left: 4px;
  font-size: 14px;
}
</style>
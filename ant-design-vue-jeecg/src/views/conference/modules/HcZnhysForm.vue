<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="会议室名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['conferenceName']" placeholder="请输入会议室名称"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="会议室地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['conferenceSite']" placeholder="请输入会议室地点"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="设备明细" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['equipment']" placeholder="请输入设备明细"  ></a-textarea>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="容纳人数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['galleryful']" placeholder="请输入容纳人数" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="介绍" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['introduce']" rows="4" placeholder="请输入介绍" />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple  v-decorator="['photo']" ></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JImageUpload from '@/components/jeecg/JImageUpload'

  export default {
    name: 'HcZnhysForm',
    components: {
      JFormContainer,
      JImageUpload,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/conference/hcZnhys/add",
          edit: "/conference/hcZnhys/edit",
          queryById: "/conference/hcZnhys/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'createTime','conferenceName','conferenceSite','equipment','galleryful','introduce','photo'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'createTime','conferenceName','conferenceSite','equipment','galleryful','introduce','photo'))
      },
    }
  }
</script>
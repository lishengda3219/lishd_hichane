<template>

  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form"  slot="detail">
        <a-row class="form-row" :gutter="24" >
          <a-col :span="12">
            <a-form-item label="单据号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['no']" placeholder="请输入单据号"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['time']" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="会议主题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['conferenceTheme', validatorRules.conferenceTheme]" placeholder="请输入会议主题"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="预定开始日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择预定开始日期" v-decorator="['reserveStart', validatorRules.reserveStart]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="预定结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择预定结束日期" v-decorator="['reserveEnd', validatorRules.reserveEnd]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="会议类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['conferenceType', validatorRules.conferenceType]" :trigger-change="true" dictCode="c_type" placeholder="请选择会议类型" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="会议室名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['conferenceName', validatorRules.conferenceName]" placeholder="请输入会议室名称"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="申请人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['proposer']" placeholder="请输入申请人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="申请人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['proposerPhone', validatorRules.proposerPhone]" placeholder="请输入申请人电话"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="使用人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['user', validatorRules.user]" placeholder="请输入使用人"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="使用人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['usePhone', validatorRules.usePhone]" placeholder="请输入使用人电话"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="公司参会人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['companyParticipant', validatorRules.companyParticipant]" placeholder="请输入公司参会人员"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" :gutter="24">
          <a-col :span="12">
            <a-form-item label="来访单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['visitorUnit', validatorRules.visitorUnit]" placeholder="请输入来访单位"  ></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="人员职位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['crewPost', validatorRules.crewPost]" placeholder="请输入人员职位"  ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row class="form-row" >
          <a-col :span="24"  :lg="12">
            <a-form-item  label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" >
              <a-textarea v-decorator="['remark']" rows="4"  style="width: 100%" placeholder="请输入备注" />
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="12" style="text-align: left;width: 100%">
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
    import JDate from '@/components/jeecg/JDate'
    import JDictSelectTag from "@/components/dict/JDictSelectTag"

    export default {
        name: 'HcHysydForm',
        components: {
            JFormContainer,
            JDate,
            JDictSelectTag,
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
                    conferenceTheme: {
                        rules: [
                            { required: true, message: '请输入会议主题!'},
                        ]
                    },
                    reserveStart: {
                        rules: [
                            { required: true, message: '请输入预定开始日期!'},
                        ]
                    },
                    reserveEnd: {
                        rules: [
                            { required: true, message: '请输入预定结束日期!'},
                        ]
                    },
                    conferenceType: {
                        rules: [
                            { required: true, message: '请输入会议类型!'},
                        ]
                    },
                    conferenceName: {
                        rules: [
                            { required: true, message: '请输入会议室名称!'},
                        ]
                    },
                    proposerPhone: {
                        rules: [
                            { required: true, message: '请输入申请人电话!'},
                        ]
                    },
                    user: {
                        rules: [
                            { required: true, message: '请输入使用人!'},
                        ]
                    },
                    usePhone: {
                        rules: [
                            { required: true, message: '请输入使用人电话!'},
                        ]
                    },
                    companyParticipant: {
                        rules: [
                            { required: true, message: '请输入公司参会人员!'},
                        ]
                    },
                    visitorUnit: {
                        rules: [
                            { required: true, message: '请输入来访单位!'},
                        ]
                    },
                    crewPost: {
                        rules: [
                            { required: true, message: '请输入人员职位!'},
                        ]
                    },
                },
                url: {
                    add: "/conference/hcHysyd/add",
                    edit: "/conference/hcHysyd/edit",
                    queryById: "/conference/hcHysyd/queryById"
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
                    this.form.setFieldsValue(pick(this.model,'no','time','conferenceTheme','reserveStart','reserveEnd','conferenceType','conferenceName','proposer','proposerPhone','user','usePhone','companyParticipant','visitorUnit','crewPost','remark'))
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
                this.form.setFieldsValue(pick(row,'no','time','conferenceTheme','reserveStart','reserveEnd','conferenceType','conferenceName','proposer','proposerPhone','user','usePhone','companyParticipant','visitorUnit','crewPost','remark'))
            },
        }
    }
</script>
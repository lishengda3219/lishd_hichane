<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="创建时间">
              <a-input placeholder="创建时间" v-model="queryParam.time"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd"  type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('会议室预定')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <hc-hysyd-modal ref="modalForm" @ok="modalFormOk"></hc-hysyd-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import HcHysydModal from './modules/HcHysydModal'
  import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

  export default {
    name: 'HcHysydList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      HcHysydModal,
      JSuperQuery,
    },
    data () {
      return {
        description: '会议室预定管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          // {
            //   title:'单据号',
            //   align:"center",
            //   dataIndex: 'no'
            // },
            // {
            //   title:'创建时间',
            //   align:"center",
            //   dataIndex: 'time'
            // },
          {
            title:'会议主题',
            align:"center",
            dataIndex: 'conferenceTheme'
          },
            {
                title:'会议室名称',
                align:"center",
                dataIndex: 'conferenceName'
            },
          {
            title:'预定开始日期',
            align:"center",
            dataIndex: 'reserveStart'
          },
          {
            title:'预定结束日期',
            align:"center",
            dataIndex: 'reserveEnd'
          },
          // {
          //   title:'会议类型',
          //   align:"center",
          //   dataIndex: 'conferenceType_dictText'
          // },

          {
            title:'申请人',
            align:"center",
            dataIndex: 'proposer'
          },
          // {
          //   title:'申请人电话',
          //   align:"center",
          //   dataIndex: 'proposerPhone'
          // },
          {
            title:'使用人',
            align:"center",
            dataIndex: 'user'
          },
          // {
          //   title:'使用人电话',
          //   align:"center",
          //   dataIndex: 'usePhone'
          // },
          // {
          //   title:'公司参会人员',
          //   align:"center",
          //   dataIndex: 'companyParticipant'
          // },
          // {
          //   title:'来访单位',
          //   align:"center",
          //   dataIndex: 'visitorUnit'
          // },
          // {
          //   title:'人员职位',
          //   align:"center",
          //   dataIndex: 'crewPost'
          // },
          // {
          //   title:'备注',
          //   align:"center",
          //   dataIndex: 'remark'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/conference/hcHysyd/list",
          delete: "/conference/hcHysyd/delete",
          deleteBatch: "/conference/hcHysyd/deleteBatch",
          exportXlsUrl: "/conference/hcHysyd/exportXls",
          importExcelUrl: "conference/hcHysyd/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'no',text:'单据号',dictCode:''})
        fieldList.push({type:'datetime',value:'time',text:'创建时间'})
        fieldList.push({type:'string',value:'conferenceTheme',text:'会议主题',dictCode:''})
        fieldList.push({type:'datetime',value:'reserveStart',text:'预定开始日期'})
        fieldList.push({type:'datetime',value:'reserveEnd',text:'预定结束日期'})
        fieldList.push({type:'string',value:'conferenceType',text:'会议类型',dictCode:''})
        fieldList.push({type:'string',value:'conferenceName',text:'会议室名称',dictCode:''})
        fieldList.push({type:'string',value:'proposer',text:'申请人',dictCode:''})
        fieldList.push({type:'string',value:'proposerPhone',text:'申请人电话',dictCode:''})
        fieldList.push({type:'string',value:'user',text:'使用人',dictCode:''})
        fieldList.push({type:'string',value:'usePhone',text:'使用人电话',dictCode:''})
        fieldList.push({type:'string',value:'companyParticipant',text:'公司参会人员',dictCode:''})
        fieldList.push({type:'string',value:'visitorUnit',text:'来访单位',dictCode:''})
        fieldList.push({type:'string',value:'crewPost',text:'人员职位',dictCode:''})
        fieldList.push({type:'string',value:'remark',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
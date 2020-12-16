package org.jeecg.modules.demo3.conference.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 会议室预定
 * @Author: jeecg-boot
 * @Date:   2020-12-14
 * @Version: V1.0
 */
@Data
@TableName("hc_hysyd")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="hc_hysyd对象", description="会议室预定")
public class HcHysyd implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**单据号*/
	@Excel(name = "单据号", width = 15)
    @ApiModelProperty(value = "单据号")
    private String no;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date time;
	/**会议主题*/
	@Excel(name = "会议主题", width = 15)
    @ApiModelProperty(value = "会议主题")
    private String conferenceTheme;
	/**预定开始日期*/
	@Excel(name = "预定开始日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "预定开始日期")
    private Date reserveStart;
	/**预定结束日期*/
	@Excel(name = "预定结束日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "预定结束日期")
    private Date reserveEnd;
	/**会议类型*/
	@Excel(name = "会议类型", width = 15)
    @ApiModelProperty(value = "会议类型")
    private String conferenceType;
	/**会议室名称*/
	@Excel(name = "会议室名称", width = 15)
    @ApiModelProperty(value = "会议室名称")
    private String conferenceName;
	/**申请人*/
	@Excel(name = "申请人", width = 15)
    @ApiModelProperty(value = "申请人")
    private String proposer;
	/**申请人电话*/
	@Excel(name = "申请人电话", width = 15)
    @ApiModelProperty(value = "申请人电话")
    private String proposerPhone;
	/**使用人*/
	@Excel(name = "使用人", width = 15)
    @ApiModelProperty(value = "使用人")
    private String user;
	/**使用人电话*/
	@Excel(name = "使用人电话", width = 15)
    @ApiModelProperty(value = "使用人电话")
    private String usePhone;
	/**公司参会人员*/
	@Excel(name = "公司参会人员", width = 15)
    @ApiModelProperty(value = "公司参会人员")
    private String companyParticipant;
	/**来访单位*/
	@Excel(name = "来访单位", width = 15)
    @ApiModelProperty(value = "来访单位")
    private String visitorUnit;
	/**人员职位*/
	@Excel(name = "人员职位", width = 15)
    @ApiModelProperty(value = "人员职位")
    private String crewPost;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**备用1*/
	@Excel(name = "备用1", width = 15)
    @ApiModelProperty(value = "备用1")
    private String by1;
	/**备用2*/
	@Excel(name = "备用2", width = 15)
    @ApiModelProperty(value = "备用2")
    private String by2;
	/**备用3*/
	@Excel(name = "备用3", width = 15)
    @ApiModelProperty(value = "备用3")
    private String by3;
}

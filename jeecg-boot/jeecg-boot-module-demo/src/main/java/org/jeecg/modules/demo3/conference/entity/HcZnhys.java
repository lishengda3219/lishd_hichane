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
 * @Description: 智能会议室
 * @Author: jeecg-boot
 * @Date:   2020-12-08
 * @Version: V1.0
 */
@Data
@TableName("hc_znhys")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="hc_znhys对象", description="智能会议室")
public class HcZnhys implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
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
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**会议室名称*/
	@Excel(name = "会议室名称", width = 15)
    @ApiModelProperty(value = "会议室名称")
    private String conferenceName;
	/**会议室地点*/
	@Excel(name = "会议室地点", width = 15)
    @ApiModelProperty(value = "会议室地点")
    private String conferenceSite;
	/**设备明细*/
	@Excel(name = "设备明细", width = 15)
    @ApiModelProperty(value = "设备明细")
    private String equipment;
	/**容纳人数*/
	@Excel(name = "容纳人数", width = 15)
    @ApiModelProperty(value = "容纳人数")
    private Integer galleryful;
    /**介绍*/
    @Excel(name = "介绍", width = 15)
    @ApiModelProperty(value = "介绍")
    private java.lang.String introduce;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private String photo;
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

package org.jeecg.modules.demo3.conference.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo3.conference.entity.HcWdyd;
import org.jeecg.modules.demo3.conference.service.IHcWdydService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 我的预定
 * @Author: jeecg-boot
 * @Date:   2020-12-10
 * @Version: V1.0
 */
@Api(tags="我的预定")
@RestController
@RequestMapping("/conference/hcWdyd")
@Slf4j
public class HcWdydController extends JeecgController<HcWdyd, IHcWdydService> {
	@Autowired
	private IHcWdydService hcWdydService;
	
	/**
	 * 分页列表查询
	 *
	 * @param hcWdyd
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "我的预定-分页列表查询")
	@ApiOperation(value="我的预定-分页列表查询", notes="我的预定-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(HcWdyd hcWdyd,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
        hcWdyd.setConferenceName("*"+hcWdyd.getConferenceName()+"*");
		QueryWrapper<HcWdyd> queryWrapper = QueryGenerator.initQueryWrapper(hcWdyd, req.getParameterMap());
		Page<HcWdyd> page = new Page<HcWdyd>(pageNo, pageSize);
		IPage<HcWdyd> pageList = hcWdydService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param hcWdyd
	 * @return
	 */
	@AutoLog(value = "我的预定-添加")
	@ApiOperation(value="我的预定-添加", notes="我的预定-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody HcWdyd hcWdyd) {
		hcWdydService.save(hcWdyd);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param hcWdyd
	 * @return
	 */
	@AutoLog(value = "我的预定-编辑")
	@ApiOperation(value="我的预定-编辑", notes="我的预定-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody HcWdyd hcWdyd) {
		hcWdydService.updateById(hcWdyd);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "我的预定-通过id删除")
	@ApiOperation(value="我的预定-通过id删除", notes="我的预定-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		hcWdydService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "我的预定-批量删除")
	@ApiOperation(value="我的预定-批量删除", notes="我的预定-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.hcWdydService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "我的预定-通过id查询")
	@ApiOperation(value="我的预定-通过id查询", notes="我的预定-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		HcWdyd hcWdyd = hcWdydService.getById(id);
		if(hcWdyd==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(hcWdyd);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param hcWdyd
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HcWdyd hcWdyd) {
        return super.exportXls(request, hcWdyd, HcWdyd.class, "我的预定");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, HcWdyd.class);
    }

}

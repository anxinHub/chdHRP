package com.chd.hrp.hr.controller.sc;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.entity.sc.HrTableType;
import com.chd.hrp.hr.service.sc.HrTableTypeService;
import com.chd.hrp.hr.util.excel.ExcelUtils;

/**
 * 
 * @ClassName: HrTableTypeController
 * @Description: 数据表分类
 * @author zn
 * @date 2017年10月17日 下午2:44:39
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrtabletype")
public class HrTableTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(HrTableTypeController.class);

	// 引入Service服务
	@Resource(name = "hrTableTypeService")
	private final HrTableTypeService hrTableTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableTypeMainPage", method = RequestMethod.GET)
	public String hrTableTypeMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabletype/hrTableTypeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableTypeAddPage", method = RequestMethod.GET)
	public String hrTableTypeAddPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrtabletype/hrTableTypeAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTableTypeUpdatePage", method = RequestMethod.GET)
	public String hrTableTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HrTableType hrTableType = hrTableTypeService.queryHrTableTypeByCode(mapVo);

		if (hrTableType != null) {
			mode.addAttribute("type_tab_code", hrTableType.getType_tab_code());
			mode.addAttribute("type_tab_name", hrTableType.getType_tab_name());
			mode.addAttribute("table_sort", hrTableType.getTable_sort());
			mode.addAttribute("table_note", hrTableType.getTable_note());
		} else {
			throw new SysException("请求错误,数据不存在!");
		}
		return "hrp/hr/sc/hrtabletype/hrTableTypeUpdate";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTableType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return hrTableTypeService.queryHrTableType(mapVo);
	}

	/**
	 * @Description 添加数据 如：人事档案、组织机构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrTableType", method = RequestMethod.POST)
	@ResponseBody
	public String addHrTableType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			return hrTableTypeService.addHrTableType(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 更新数据 如：人事档案、组织机构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrTableType", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrTableType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			return hrTableTypeService.updateHrTableType(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 删除数据 如：人事档案、组织机构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrTableType", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrTableType(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrTableType> listVo = JSONArray.parseArray(paramVo, HrTableType.class);
		try {
			return hrTableTypeService.deleteBatchHrTableType(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrTableTypeService.importExcel(mapVo);
		return reJson;
	}

	@RequestMapping(value = "/importTemplate")
	public String importFileTemplate(HttpServletResponse response) {
		try {
			List<String> header = new ArrayList<String>();
			header.add("类别代码");
			header.add("类别名称");
			header.add("备注");
			String filename = "数据表分类导入模板.xls";
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			ExcelUtils.getInstance().exportObjects2Excel(new ArrayList<List<String>>(), header,
					response.getOutputStream());
			return null;
		} catch (Exception e) {
			return "{\"error\":\"导入模板下载失败！\"}";
		}
	}
	
	@RequestMapping(value = "/exportExcel")
	public String exportExcel(HttpServletResponse response){
		try {
			List<String> header = new ArrayList<String>();
			header.add("类别代码");
			header.add("类别名称");
			header.add("备注");
			
			List<List<String>> list = hrTableTypeService.queryHrTableTypeList(new HashMap<String,Object>());
			String filename = "数据表分类.xls";
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			ExcelUtils.getInstance().exportObjects2Excel(list, header,response.getOutputStream());
			return null;
		} catch (Exception e) {
			return "{\"error\":\"导出失败！\"}";
		}
	}

}

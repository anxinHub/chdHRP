package com.chd.hrp.hr.controller.sysstruc;

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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.entity.sysstruc.HrTabType;
import com.chd.hrp.hr.service.sysstruc.HrTabTypeService;
import com.chd.hrp.hr.util.excel.ExcelUtils;

/**
 * 
 * @ClassName: HrTabTypeController
 * @Description: 数据表分类
 * @author zn
 * @date 2017年10月17日 下午2:44:39
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrtabtype")
public class HrTabTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(HrTabTypeController.class);

	// 引入Service服务
	@Resource(name = "hrTabTypeService")
	private final HrTabTypeService hrTabTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTabTypeMainPage", method = RequestMethod.GET)
	public String hrTabTypeMainPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrtabtype/hrTabTypeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTabTypeAddPage", method = RequestMethod.GET)
	public String hrTabTypeAddPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrtabtype/hrTabTypeAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTabTypeUpdatePage", method = RequestMethod.GET)
	public String hrTabTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HrTabType hrTabType = hrTabTypeService.queryHrTabTypeByCode(mapVo);

		if (hrTabType != null) {
			mode.addAttribute("type_tab_code", hrTabType.getType_tab_code());
			mode.addAttribute("type_tab_name", hrTabType.getType_tab_name());
			mode.addAttribute("note", hrTabType.getNote());
		} else {
			throw new SysException("请求错误,数据不存在!");
		}
		return "hrp/hr/sysstruc/hrtabtype/hrTabTypeUpdate";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTabType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTabTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return hrTabTypeService.queryHrTabType(mapVo);
	}

	/**
	 * @Description 添加数据 如：人事档案、组织机构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrTabType", method = RequestMethod.POST)
	@ResponseBody
	public String addHrTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			return hrTabTypeService.addHrTabType(mapVo);
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
	@RequestMapping(value = "/updateHrTabType", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			return hrTabTypeService.updateHrTabType(mapVo);
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
	@RequestMapping(value = "/deleteHrTabType", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrTabType(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrTabType> listVo = JSONArray.parseArray(paramVo, HrTabType.class);
		for (HrTabType hrTabType : listVo) {
			hrTabType.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			hrTabType.setHos_id(Long.parseLong(SessionManager.getHosId()));
			//hrTabType.setCopy_code(SessionManager.getCopyCode());
		}
		try {
			return hrTabTypeService.deleteBatchHrTabType(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrTabTypeService.importExcel(mapVo);
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
			
			List<List<String>> list = hrTabTypeService.queryHrTabTypeList(new HashMap<String,Object>());
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

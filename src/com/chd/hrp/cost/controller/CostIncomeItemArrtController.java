/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostIncomeItemArrt;
import com.chd.hrp.cost.serviceImpl.CostIncomeItemArrtServiceImpl;

/**
 * @Title. @Description. 成本_收入项目字典
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostIncomeItemArrtController extends BaseController {
	private static Logger logger = Logger
			.getLogger(CostIncomeItemArrtController.class);

	@Resource(name = "costIncomeItemArrtService")
	private final CostIncomeItemArrtServiceImpl costIncomeItemArrtService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	/**
	 * 成本_收入项目字典<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/costIncomeItemArrtMainPage", method = RequestMethod.GET)
	public String costIncomeItemArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costincomeitemarrt/costIncomeItemArrtMain";

	}

	/**
	 * 成本_收入项目字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/costIncomeItemArrtAddPage", method = RequestMethod.GET)
	public String costIncomeItemArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costincomeitemarrt/costIncomeItemArrtAdd";

	}

	/**
	 * 成本_收入项目字典<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/addCostIncomeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostIncomeItemArrt(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 根据名称生成拼音码
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"income_item_name").toString()));
		// 根据名称生成五笔码
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("income_item_name").toString()));
		String costIncomeItemArrtJson = costIncomeItemArrtService
				.addCostIncomeItemArrt(mapVo);

		return JSONObject.parseObject(costIncomeItemArrtJson);

	}

	/**
	 * 成本_收入项目字典<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/queryCostIncomeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIncomeItemArrt(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costIncomeItemArrt = costIncomeItemArrtService
				.queryCostIncomeItemArrt(getPage(mapVo));

		return JSONObject.parseObject(costIncomeItemArrt);

	}

	/**
	 * 成本_收入项目字典<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/deleteCostIncomeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIncomeItemArrt(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_income_item_arrt", ids[3]);
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的收入项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("income_item_id", ids[3]);
			listVo.add(mapVo);
		}
		String costIncomeItemArrtJson = costIncomeItemArrtService
				.deleteBatchCostIncomeItemArrt(listVo);
		return JSONObject.parseObject(costIncomeItemArrtJson);

	}

	/**
	 * 成本_收入项目字典<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/costIncomeItemArrtUpdatePage", method = RequestMethod.GET)
	public String costIncomeItemArrtUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostIncomeItemArrt costIncomeItemArrt = new CostIncomeItemArrt();
		costIncomeItemArrt = costIncomeItemArrtService
				.queryCostIncomeItemArrtByCode(mapVo);
		mode.addAttribute("group_id", costIncomeItemArrt.getGroup_id());
		mode.addAttribute("hos_id", costIncomeItemArrt.getHos_id());
		mode.addAttribute("copy_code", costIncomeItemArrt.getCopy_code());
		mode.addAttribute("income_item_id",
				costIncomeItemArrt.getIncome_item_id());
		mode.addAttribute("income_item_code",
				costIncomeItemArrt.getIncome_item_code());
		mode.addAttribute("income_item_name",
				costIncomeItemArrt.getIncome_item_name());
		mode.addAttribute("is_stop", costIncomeItemArrt.getIs_stop());
		mode.addAttribute("spell_code", costIncomeItemArrt.getSpell_code());
		mode.addAttribute("wbx_code", costIncomeItemArrt.getWbx_code());
		return "hrp/cost/costincomeitemarrt/costIncomeItemArrtUpdate";
	}

	/**
	 * 成本_收入项目字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/updateCostIncomeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostIncomeItemArrt(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 根据名称生成拼音码
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"income_item_name").toString()));
		// 根据名称生成五笔码
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("income_item_name").toString()));

		String costIncomeItemArrtJson = costIncomeItemArrtService
				.updateCostIncomeItemArrt(mapVo);

		return JSONObject.parseObject(costIncomeItemArrtJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/costIncomeItemArrtImportPage", method = RequestMethod.GET)
	public String costIncomeItemArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costincomeitemarrt/costIncomeItemArrtImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costincomeitemarrt/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "cost\\基础设置", "收入项目.xls");
		return null;
	}

	/**
	 * 成本_收入项目字典<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/readCostIncomeItemArrtFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<CostIncomeItemArrt> list_err = new ArrayList<CostIncomeItemArrt>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostIncomeItemArrt costIncomeItemArrt = new CostIncomeItemArrt();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {
					
					mapVo.put("group_id", SessionManager.getGroupId());
					
				}
				if (mapVo.get("hos_id") == null) {
					
					mapVo.put("hos_id", SessionManager.getHosId());
					
				}
				if (mapVo.get("copy_code") == null) {
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
				}
				if (StringUtils.isNotEmpty(temp[0])) {
					
					costIncomeItemArrt.setIncome_item_code(temp[0]);
					
					mapVo.put("income_item_code", temp[0]);
					
				} else {
					
					err_sb.append("收入项目编码为空  ");
					
				}
				if (StringUtils.isNotEmpty(temp[1])) {
					
					costIncomeItemArrt.setIncome_item_name(temp[1]);
					
					mapVo.put("income_item_name", temp[1]);
					
				} else {
					
					err_sb.append("收入项目名称为空  ");
					
				}
				if (StringUtils.isNotEmpty(temp[2])) {
					
					/*
						2016/11/4 lxj
						解决停用标志录入格式不正确报错的问题
					*/
					
					if("0".equals(temp[2]) || "否".equals(temp[2]) ||"是".equals(temp[2]) || "1".equals(temp[2])){
						
						costIncomeItemArrt.setIs_stop(Integer.parseInt(temp[2]));
						
						mapVo.put("is_stop", temp[2]);
					}else{
						
						err_sb.append("停用标志格式错误,只能填写0 1 是 否 ");
					}
					
				} else {
					
					err_sb.append("停用标志为空  ");
					
				}

				CostIncomeItemArrt data_exc_extis = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					costIncomeItemArrt.setError_type(err_sb.toString());
					
					list_err.add(costIncomeItemArrt);
					
				} else {
					
					mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("income_item_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_item_name").toString()));
					
					String dataJson = costIncomeItemArrtService.addCostIncomeItemArrt(mapVo);
				}
			}
		} catch (DataAccessException e) {
			
			CostIncomeItemArrt data_exc = new CostIncomeItemArrt();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
	}

	/**
	 * 成本_收入项目字典<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costincomeitemarrt/addBatchCostIncomeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostIncomeItemArrt(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		List<CostIncomeItemArrt> list_err = new ArrayList<CostIncomeItemArrt>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String s = null;
		
		Iterator it = json.iterator();
		
		try {
			
			while (it.hasNext()) {
				
				StringBuffer err_sb = new StringBuffer();
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				mapVo.put("income_item_code", jsonObj.get("income_item_code"));
				
				mapVo.put("income_item_name", jsonObj.get("income_item_name"));
				
				mapVo.put("is_stop", jsonObj.get("is_stop"));
				
				mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("income_item_name").toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_item_name").toString()));
				
				CostIncomeItemArrt data_exc_extis = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
				}
				
				CostIncomeItemArrt costIncomeItemArrt = new CostIncomeItemArrt();
				
				if (err_sb.toString().length() > 0) {
					
					costIncomeItemArrt.setIncome_item_code(mapVo.get("income_item_code").toString());
					
					costIncomeItemArrt.setIncome_item_name(mapVo.get("income_item_name").toString());
					
					costIncomeItemArrt.setIs_stop(Integer.parseInt(mapVo.get("is_stop").toString()));
					
					costIncomeItemArrt.setError_type(err_sb.toString());
					
					list_err.add(costIncomeItemArrt);
					
				} else {
					
					costIncomeItemArrtService.addCostIncomeItemArrt(mapVo);
				}
			}
			
		} catch (DataAccessException e) {
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
		
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
	}
}

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
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.entity.CostIncomeItemArrt;
import com.chd.hrp.cost.entity.CostIncomeType;
import com.chd.hrp.cost.serviceImpl.CostChargeKindArrtServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostIncomeItemArrtServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostIncomeTypeServiceImpl;

/**
* @Title. @Description.
* 成本_收费类别字典
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostChargeKindArrtController extends BaseController{
	private static Logger logger = Logger.getLogger(CostChargeKindArrtController.class);
	
	
	@Resource(name = "costChargeKindArrtService")
	private final CostChargeKindArrtServiceImpl costChargeKindArrtService = null;
	
	@Resource(name = "costIncomeItemArrtService")
	private final CostIncomeItemArrtServiceImpl costIncomeItemArrtService = null;
	
	@Resource(name = "costIncomeTypeService")
	private final CostIncomeTypeServiceImpl costIncomeTypeService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
   
	/**
	*成本_收费类别字典<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/costChargeKindArrtMainPage", method = RequestMethod.GET)
	public String costChargeKindArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costchargekindarrt/costChargeKindArrtMain";

	}
	/**
	*成本_收费类别字典<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/costChargeKindArrtAddPage", method = RequestMethod.GET)
	public String costChargeKindArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costchargekindarrt/costChargeKindArrtAdd";

	}
	/**
	*成本_收费类别字典<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/addCostChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostChargeKindArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_kind_name").toString()));
		
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_kind_name").toString()));
		String costChargeKindArrtJson = costChargeKindArrtService.addCostChargeKindArrt(mapVo);

		return JSONObject.parseObject(costChargeKindArrtJson);
		
	}
	/**
	*成本_收费类别字典<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/queryCostChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostChargeKindArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costChargeKindArrt = costChargeKindArrtService.queryCostChargeKindArrt(getPage(mapVo));

		return JSONObject.parseObject(costChargeKindArrt);
		
	}
	/**
	*成本_收费类别字典<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/deleteCostChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostChargeKindArrt(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_charge_kind_arrt", ids[3]);
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的收费类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("charge_kind_id",ids[3]); 
            listVo.add(mapVo);
        }
		String costChargeKindArrtJson = costChargeKindArrtService.deleteBatchCostChargeKindArrt(listVo);
	   return JSONObject.parseObject(costChargeKindArrtJson);
			
	}
	
	/**
	*成本_收费类别字典<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/costChargeKindArrtUpdatePage", method = RequestMethod.GET)
	
	public String costChargeKindArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostChargeKindArrt costChargeKindArrt = new CostChargeKindArrt();
		costChargeKindArrt = costChargeKindArrtService.queryCostChargeKindArrtByCode(mapVo);
		mode.addAttribute("group_id", costChargeKindArrt.getGroup_id());
		mode.addAttribute("hos_id", costChargeKindArrt.getHos_id());
		mode.addAttribute("copy_code", costChargeKindArrt.getCopy_code());
		mode.addAttribute("income_item_id_in", costChargeKindArrt.getIncome_item_id_in());
		mode.addAttribute("income_item_id_out", costChargeKindArrt.getIncome_item_id_out());
		mode.addAttribute("income_item_name_in", costChargeKindArrt.getIncome_item_name_in());
		mode.addAttribute("income_item_name_out", costChargeKindArrt.getIncome_item_name_out());
		mode.addAttribute("charge_kind_id", costChargeKindArrt.getCharge_kind_id());
		mode.addAttribute("charge_kind_code", costChargeKindArrt.getCharge_kind_code());
		mode.addAttribute("charge_kind_name", costChargeKindArrt.getCharge_kind_name());
		mode.addAttribute("income_type_id", costChargeKindArrt.getIncome_type_id());
		mode.addAttribute("income_type_name", costChargeKindArrt.getIncome_type_name());
		mode.addAttribute("spell_code", costChargeKindArrt.getSpell_code());
		mode.addAttribute("wbx_code", costChargeKindArrt.getWbx_code());
		return "hrp/cost/costchargekindarrt/costChargeKindArrtUpdate";
	}
	/**
	*成本_收费类别字典<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/updateCostChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostChargeKindArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_kind_name").toString()));
		
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_kind_name").toString()));
		
		String costChargeKindArrtJson = costChargeKindArrtService.updateCostChargeKindArrt(mapVo);
		
		return JSONObject.parseObject(costChargeKindArrtJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/costChargeKindArrtImportPage", method = RequestMethod.GET)
	public String costChargeKindArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costchargekindarrt/costChargeKindArrtImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costchargekindarrt/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","收费类别.xls");
	    return null; 
	 }
	 
	/**
	*成本_收费类别字典<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costchargekindarrt/readCostChargeKindArrtFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<CostChargeKindArrt> list_err = new ArrayList<CostChargeKindArrt>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				CostChargeKindArrt costChargeKindArrt = new CostChargeKindArrt();
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
						costChargeKindArrt.setCharge_kind_code(temp[0]);
						mapVo.put("charge_kind_code", temp[0]);
					} else {
						err_sb.append("收费类别编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						costChargeKindArrt.setCharge_kind_name(temp[1]);
						mapVo.put("charge_kind_name", temp[1]);
					} else {
						err_sb.append("收费类别名称为空  ");
					}
					
					if(temp.length>2){
						if (StringUtils.isNotEmpty(temp[2])) {
							mapVo.put("income_item_code", temp[2]);
							CostIncomeItemArrt incomeItemArrt = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
							costChargeKindArrt.setIncome_item_code_in(temp[2]);
							costChargeKindArrt.setIncome_item_name_in(temp[3]);
							if(incomeItemArrt != null){
								mapVo.put("income_item_id_in", incomeItemArrt.getIncome_item_id());
							}
						} 
						if (StringUtils.isNotEmpty(temp[4])) {
							mapVo.put("income_item_code", temp[4]);
							CostIncomeItemArrt incomeItemArrt = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
							costChargeKindArrt.setIncome_item_code_out(temp[4]);
							costChargeKindArrt.setIncome_item_name_out(temp[5]);
							if(incomeItemArrt != null){
								mapVo.put("income_item_id_out", incomeItemArrt.getIncome_item_id());
							}
						} 
						if (StringUtils.isNotEmpty(temp[6])) {
							mapVo.put("income_type_code", temp[6]);
							CostIncomeType incomeType = costIncomeTypeService.queryCostIncomeTypeByCode(mapVo);
							costChargeKindArrt.setIncome_type_code(temp[6]);
							costChargeKindArrt.setIncome_type_name(temp[7]);
							if(incomeType != null){
								mapVo.put("income_type_id", incomeType.getIncome_type_id());
							}
						} 
					}
					
					
				CostChargeKindArrt data_exc_extis = costChargeKindArrtService.queryCostChargeKindArrtByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					costChargeKindArrt.setError_type(err_sb.toString());
					list_err.add(costChargeKindArrt);
				} else {
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_kind_name").toString()));
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_kind_name").toString()));
					String dataJson = costChargeKindArrtService.addCostChargeKindArrt(mapVo);
				}
			}
		} catch (DataAccessException e) {
			CostChargeKindArrt data_exc = new CostChargeKindArrt();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
    }  
	/**
	*成本_收费类别字典<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costchargekindarrt/addBatchCostChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostChargeKindArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<CostChargeKindArrt> list_err = new ArrayList<CostChargeKindArrt>();
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
			
			mapVo.put("income_item_code", jsonObj.get("income_item_code_in"));
			CostIncomeItemArrt incomeItemInArrt = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
			if(incomeItemInArrt != null){
				mapVo.put("income_item_id_in", incomeItemInArrt.getIncome_item_id());
			}else{
				err_sb.append("收入项目_门诊不存在 ");
			}
			mapVo.put("income_item_code_in", jsonObj.get("income_item_code_in"));
			mapVo.put("income_item_name_in", jsonObj.get("income_item_name_in"));
			
			mapVo.put("income_item_code", jsonObj.get("income_item_code_out"));
			CostIncomeItemArrt incomeItemOutArrt = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
			if(incomeItemOutArrt != null){
				mapVo.put("income_item_id_out", incomeItemOutArrt.getIncome_item_id());
			}else{
				err_sb.append("收入项目_住院不存在 ");
			}
			
			mapVo.put("income_item_code_out", jsonObj.get("income_item_code_out"));
			mapVo.put("income_item_name_out", jsonObj.get("income_item_name_out"));
			
			mapVo.put("charge_kind_code", jsonObj.get("charge_kind_code"));
			mapVo.put("charge_kind_name", jsonObj.get("charge_kind_name"));
			
			mapVo.put("income_type_code",jsonObj.get("income_type_code"));
			mapVo.put("income_type_name",jsonObj.get("income_type_name"));
			CostIncomeType incomeType = costIncomeTypeService.queryCostIncomeTypeByCode(mapVo);
			if(incomeType != null){
				mapVo.put("income_type_id", incomeType.getIncome_type_id());
			}else{
				err_sb.append("收入类型编码不存在  ");
			}
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_kind_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_kind_name").toString()));
				CostChargeKindArrt data_exc_extis = costChargeKindArrtService.queryCostChargeKindArrtByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				CostChargeKindArrt costChargeKindArrt = new CostChargeKindArrt();
				if (err_sb.toString().length() > 0) {
					costChargeKindArrt.setIncome_item_code_in(mapVo.get("income_item_code_in").toString());
					costChargeKindArrt.setIncome_item_name_in(mapVo.get("income_item_name_in").toString());
					costChargeKindArrt.setIncome_item_code_out(mapVo.get("income_item_code_out").toString());
					costChargeKindArrt.setIncome_item_name_out(mapVo.get("income_item_name_out").toString());
					costChargeKindArrt.setCharge_kind_code(mapVo.get("charge_kind_code").toString());
					costChargeKindArrt.setCharge_kind_name(mapVo.get("charge_kind_name").toString());
					costChargeKindArrt.setIncome_type_code(mapVo.get("income_type_code").toString());
					costChargeKindArrt.setIncome_type_name(mapVo.get("income_type_name").toString());
					costChargeKindArrt.setError_type(err_sb.toString());
					list_err.add(costChargeKindArrt);
				} else {
					costChargeKindArrtService.addCostChargeKindArrt(mapVo);
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


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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostIncomeType;
import com.chd.hrp.cost.serviceImpl.CostIncomeTypeServiceImpl;

/**
* @Title. @Description.
* 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostIncomeTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(CostIncomeTypeController.class);
	
	
	@Resource(name = "costIncomeTypeService")
	private final CostIncomeTypeServiceImpl costIncomeTypeService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
   
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costincometype/costIncomeTypeMainPage", method = RequestMethod.GET)
	public String costIncomeTypeMainPage(Model mode) throws Exception {

		return "hrp/cost/costincometype/costIncomeTypeMain";

	}
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costincometype/costIncomeTypeAddPage", method = RequestMethod.GET)
	public String costIncomeTypeAddPage(Model mode) throws Exception {

		return "hrp/cost/costincometype/costIncomeTypeAdd";

	}
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costincometype/addCostIncomeType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostIncomeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("income_type_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_type_name").toString()));
		String costIncomeTypeJson = costIncomeTypeService.addCostIncomeType(mapVo);

		return JSONObject.parseObject(costIncomeTypeJson);
		
	}
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costincometype/queryCostIncomeType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostIncomeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costIncomeType = costIncomeTypeService.queryCostIncomeType(getPage(mapVo));

		return JSONObject.parseObject(costIncomeType);
		
	}
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costincometype/deleteCostIncomeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIncomeType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_income_type", ids[0]);
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的收入类型被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("income_type_id",ids[0]); 
            listVo.add(mapVo);
        }
		String costIncomeTypeJson = costIncomeTypeService.deleteBatchCostIncomeType(listVo);
	   return JSONObject.parseObject(costIncomeTypeJson);
			
	}
	
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costincometype/costIncomeTypeUpdatePage", method = RequestMethod.GET)
	
	public String costIncomeTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        CostIncomeType costIncomeType = new CostIncomeType();
		costIncomeType = costIncomeTypeService.queryCostIncomeTypeByCode(mapVo);
		mode.addAttribute("income_type_id", costIncomeType.getIncome_type_id());
		mode.addAttribute("income_type_code", costIncomeType.getIncome_type_code());
		mode.addAttribute("income_type_name", costIncomeType.getIncome_type_name());
		mode.addAttribute("is_stop", costIncomeType.getIs_stop());
		mode.addAttribute("spell_code", costIncomeType.getSpell_code());
		mode.addAttribute("wbx_code", costIncomeType.getWbx_code());
		return "hrp/cost/costincometype/costIncomeTypeUpdate";
	}
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costincometype/updateCostIncomeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostIncomeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("income_type_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_type_name").toString()));
		
		String costIncomeTypeJson = costIncomeTypeService.updateCostIncomeType(mapVo);
		
		return JSONObject.parseObject(costIncomeTypeJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costincometype/costIncomeTypeImportPage", method = RequestMethod.GET)
	public String costIncomeTypeImportPage(Model mode) throws Exception {

		return "hrp/cost/costincometype/costIncomeTypeImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costincometype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","收入类型.xls");
	    return null; 
	 }
	 
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costincometype/readCostIncomeTypeFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<CostIncomeType> list_err = new ArrayList<CostIncomeType>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				CostIncomeType costIncomeType = new CostIncomeType();
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (StringUtils.isNotEmpty(temp[0])) {
						costIncomeType.setIncome_type_code(temp[0]);
						mapVo.put("income_type_code", temp[0]);
					} else {
						err_sb.append("收入类型编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						costIncomeType.setIncome_type_name(temp[1]);
						mapVo.put("income_type_name", temp[1]);
					} else {
						err_sb.append("收入类型名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						if("0".equals(temp[2]) || "1".equals(temp[2]) || "是".equals(temp[2]) || "否".equals(temp[2])){
							costIncomeType.setIs_stop(Integer.getInteger(temp[2]));
							mapVo.put("is_stop", temp[2]);
						}else{
							err_sb.append("停用标志不符合要求： 0 1 是 否 ");
						}
						
						
					} else {
						err_sb.append("停用标志为空  ");
					}
					costIncomeType.setIs_stop(Integer.getInteger(temp[2]));
				CostIncomeType data_exc_extis = costIncomeTypeService.queryCostIncomeTypeByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					costIncomeType.setError_type(err_sb.toString());
					list_err.add(costIncomeType);
				} else {
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("income_type_name").toString()));
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_type_name").toString()));
					String dataJson = costIncomeTypeService.addCostIncomeType(mapVo);
				}
			}
		} catch (DataAccessException e) {
			CostIncomeType data_exc = new CostIncomeType();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
    }  
	/**
	*成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costincometype/addBatchCostIncomeType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostIncomeType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<CostIncomeType> list_err = new ArrayList<CostIncomeType>();
		JSONArray json = JSONArray.parseArray(paramVo);
		Map<String, Object> mapVo = new HashMap<String, Object>();
		String s = null;
		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {
			StringBuffer err_sb = new StringBuffer();
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			mapVo.put("income_type_code", jsonObj.get("income_type_code"));
			mapVo.put("income_type_name", jsonObj.get("income_type_name"));
			mapVo.put("is_stop", jsonObj.get("is_stop"));
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("income_type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_type_name").toString()));
				CostIncomeType data_exc_extis = costIncomeTypeService.queryCostIncomeTypeByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				CostIncomeType costIncomeType = new CostIncomeType();
				if (err_sb.toString().length() > 0) {
					costIncomeType.setIncome_type_code(mapVo.get("income_type_code").toString());
					costIncomeType.setIncome_type_name(mapVo.get("income_type_name").toString());
					costIncomeType.setIs_stop(Integer.getInteger(mapVo.get("is_stop").toString()));
					costIncomeType.setError_type(err_sb.toString());
					list_err.add(costIncomeType);
				} else {
					costIncomeTypeService.addCostIncomeType(mapVo);
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


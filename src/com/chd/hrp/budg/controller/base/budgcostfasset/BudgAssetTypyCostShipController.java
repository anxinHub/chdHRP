/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgcostfasset;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.budg.entity.BudgAssetTypyCostShip;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgAssetTypyCostShipService;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgCostFassetTypeService;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemCostShipService;
/**
 * 
 * @Description:
 * 资金性质取自系统平台
 * @Table:
 * BUDG_ASSET_TYPY_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAssetTypyCostShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAssetTypyCostShipController.class);
	
	//引入Service服务
	@Resource(name = "budgAssetTypyCostShipService")
	private final BudgAssetTypyCostShipService budgAssetTypyCostShipService = null;
	
	//引入Service服务
	@Resource(name = "budgWageItemCostShipService")
	private final BudgWageItemCostShipService budgWageItemCostShipService = null;
	
	//引入Service服务
	@Resource(name = "budgCostFassetTypeService")
	private final BudgCostFassetTypeService budgCostFassetTypeService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipMainPage", method = RequestMethod.GET)
	public String budgCostFassetShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipAddPage", method = RequestMethod.GET)
	public String budgCostFassetShipAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		return "hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipAdd";

	}

	/**
	 * @Description 
	 * 添加数据 资金性质取自系统平台
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/addBudgCostFassetShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCostFassetShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
		String budgAssetTypyCostShipJson = budgAssetTypyCostShipService.add(mapVo);

		return JSONObject.parseObject(budgAssetTypyCostShipJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 资金性质取自系统平台
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipUpdatePage", method = RequestMethod.GET)
	public String budgCostFassetShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		BudgAssetTypyCostShip budgAssetTypyCostShip = new BudgAssetTypyCostShip();
    
		budgAssetTypyCostShip = budgAssetTypyCostShipService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAssetTypyCostShip.getGroup_id());
		mode.addAttribute("hos_id", budgAssetTypyCostShip.getHos_id());
		mode.addAttribute("copy_code", budgAssetTypyCostShip.getCopy_code());
		mode.addAttribute("budg_year", budgAssetTypyCostShip.getBudg_year());
		mode.addAttribute("asset_type_id", budgAssetTypyCostShip.getAsset_type_id());
		mode.addAttribute("fund_nature", budgAssetTypyCostShip.getFund_nature());
		mode.addAttribute("subj_code", budgAssetTypyCostShip.getSubj_code());
		
		return "hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 资金性质取自系统平台
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/updateBudgCostFassetShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCostFassetShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgAssetTypyCostShipJson = budgAssetTypyCostShipService.update(mapVo);
		
		return JSONObject.parseObject(budgAssetTypyCostShipJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 资金性质取自系统平台
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/addOrUpdateBudgCostFassetShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgCostFassetShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAssetTypyCostShipJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		budgAssetTypyCostShipJson = budgAssetTypyCostShipService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAssetTypyCostShipJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 资金性质取自系统平台
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/deleteBudgCostFassetShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCostFassetShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("asset_type_id", ids[4])   ;
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAssetTypyCostShipJson = budgAssetTypyCostShipService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAssetTypyCostShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 资金性质取自系统平台
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/queryBudgCostFassetShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCostFassetShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgAssetTypyCostShip = budgAssetTypyCostShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAssetTypyCostShip);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 资金性质取自系统平台
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipImportPage", method = RequestMethod.GET)
	public String budgCostFassetShipImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/costfassetship/budgCostFassetShipImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 资金性质取自系统平台
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgcostfasset/costfassetship/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","资产类别与支出预算科目对应关系模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 资金性质取自系统平台
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgcostfasset/costfassetship/readBudgCostFassetShipFiles",method = RequestMethod.POST)  
    public String readBudgCostFassetShipFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> pageMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行预算年度、资产分类相同 ,多个预算支出科目 不允许对应相同的预算年度、资产分类;");
						}
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					} 
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						pageMap.put("budg_year", temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						pageMap.put("asset_type_code", temp[1]);
			    		
			    		mapVo.put("ass_type_code", temp[1]);
			    		mapVo.put("is_stop", "0");
			    		
			    		AssTypeDict typeCode = budgCostFassetTypeService.queryByCode(mapVo);
			    		
			    		if(typeCode == null ){
			    			
			    			err_sb.append("资产类别编码不存在或已停用;");
			    		}else{
			    			mapVo.put("asset_type_id", typeCode.getAss_type_id());
			    		}
					
					} else {
						
						err_sb.append("资产类别编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						pageMap.put("subj_code", temp[2]);
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgWageItemCostShipService.queryCostSubjByCode(mapVo);
						if(count == 0 ){
							err_sb.append("该年度支出预算科目不是末级科目或不存在;");
						}
					
					} else {
						
						err_sb.append("支出预算科目编码为空;");
						
					}
					 
					
				BudgAssetTypyCostShip data_exc_extis = budgAssetTypyCostShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					
					pageMap.put("error_type", err_sb.toString());
					
					list_err.add(pageMap);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
			if(list_err.size() == 0){
				budgAssetTypyCostShipService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	/**
	 * 资产类别 下拉框  添加页面用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/queryAssTypes", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = null;
		if(mapVo.get("is_filter")!= null && "1".equals(mapVo.get("is_filter").toString())){
			item = budgAssetTypyCostShipService.queryAssTypesFilter(mapVo);
		}else{
			item = budgAssetTypyCostShipService.queryAssTypes(mapVo);
		}
		return item;

	}
	
	/**
	 * 支出预算科目下拉框  添加页面用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/queryBudgSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("budg_year") == null||mapVo.get("budg_year").toString().equals("")) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		String method = budgAssetTypyCostShipService.queryBudgSubj(mapVo);
		return method;

	}
	
	/**
	 * 资金性质 下拉框  添加页面用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/querySourceNature", method = RequestMethod.POST)
	@ResponseBody
	public String querySourceNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String method = budgAssetTypyCostShipService.querySourceNature(mapVo);
		return method;

	}
	
	/**
	 * @Description 
	 * 继承上一年度资产分类与预算科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassetship/extendBudgCostFassetShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendBudgCostFassetShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
		String budgAssetTypyCostShipJson = budgAssetTypyCostShipService.extendBudgCostFassetShip(mapVo);

		return JSONObject.parseObject(budgAssetTypyCostShipJson);
		
	}

}


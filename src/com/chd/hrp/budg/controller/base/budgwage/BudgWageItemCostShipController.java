/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgwage;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgCostSubj;
import com.chd.hrp.budg.entity.BudgWageItemCostShip;
import com.chd.hrp.budg.entity.BudgWageItemDict;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjService;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemCostShipService;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemDictService;
/**
 * 
 * @Description:
 * 工资项目与预算支出科目对应关系
 * @Table:
 * BUDG_WAGE_ITEM_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWageItemCostShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWageItemCostShipController.class);
	
	//引入Service服务
	@Resource(name = "budgWageItemCostShipService")
	private final BudgWageItemCostShipService budgWageItemCostShipService = null;
	
	//引入Service服务
	@Resource(name = "budgWageItemDictService")
	private final BudgWageItemDictService budgWageItemDictService = null;
	
	//引入Service服务
	@Resource(name = "budgCostSubjService")
	private final BudgCostSubjService budgCostSubjService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipMainPage", method = RequestMethod.GET)
	public String budgWageItemCostShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipAddPage", method = RequestMethod.GET)
	public String budgWageItemCostShipAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipAdd";

	}

	/**
	 * @Description 
	 * 添加数据 工资项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/addBudgWageItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWageItemCostShip(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());   
	
		mapVo.put("hos_id", SessionManager.getHosId());   
	
		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgWageItemCostShipJson = budgWageItemCostShipService.add(mapVo);
		
		return JSONObject.parseObject(budgWageItemCostShipJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 工资项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipUpdatePage", method = RequestMethod.GET)
	public String budgWageItemCostShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		BudgWageItemCostShip budgWageItemCostShip = new BudgWageItemCostShip();
    
		budgWageItemCostShip = budgWageItemCostShipService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWageItemCostShip.getGroup_id());
		mode.addAttribute("hos_id", budgWageItemCostShip.getHos_id());
		mode.addAttribute("copy_code", budgWageItemCostShip.getCopy_code());
		mode.addAttribute("budg_year", budgWageItemCostShip.getBudg_year());
		mode.addAttribute("wage_item_code", budgWageItemCostShip.getWage_item_code());
		mode.addAttribute("subj_code", budgWageItemCostShip.getSubj_code());
		
		return "hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 工资项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/updateBudgWageItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWageItemCostShip(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());   
			}

			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());   
			}

			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());   
			}
		  
			String budgWageItemCostShipJson = budgWageItemCostShipService.update(mapVo);
			
			return JSONObject.parseObject(budgWageItemCostShipJson);
			
		
	}
	
	/**
	 * @Description 
	 * 更新数据 工资项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/addOrUpdateBudgWageItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWageItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWageItemCostShipJson ="";
		

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
	  
		budgWageItemCostShipJson = budgWageItemCostShipService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWageItemCostShipJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 工资项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/deleteBudgWageItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWageItemCostShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("wage_item_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWageItemCostShipJson = budgWageItemCostShipService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWageItemCostShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 工资项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/queryBudgWageItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWageItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("bugd_year") == null){
			
		mapVo.put("bugd_year", SessionManager.getAcctYear());
        
		}
		String budgWageItemCostShip = budgWageItemCostShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWageItemCostShip);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 工资项目与预算支出科目对应关系
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipImportPage", method = RequestMethod.GET)
	public String budgWageItemCostShipImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgwageitemcosthship/budgWageItemCostShipImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 工资项目与预算支出科目对应关系
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgwage/budgwageitemcosthship/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","工资项目与预算支出科目对应关系.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 工资项目与预算支出科目对应关系
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgwage/budgwageitemcosthship/readBudgWageItemCostShipFiles",method = RequestMethod.POST)  
    public String readBudgWageItemCostShipFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWageItemCostShip> list_err = new ArrayList<BudgWageItemCostShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWageItemCostShip budgWageItemCostShip = new BudgWageItemCostShip();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行预算年度、工资项目相同 ,多个预算支出科目 不允许对应同一工资项目;");
						}
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					} 
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
					budgWageItemCostShip.setBudg_year(temp[0]);
		    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgWageItemCostShip.setWage_item_code(temp[1]);
						
			    		mapVo.put("wage_item_code", temp[1]);
			    		
			    		BudgWageItemCostShip data_exc_extis = budgWageItemCostShipService.queryByCode(mapVo);
						
						if (data_exc_extis != null) {
							
							err_sb.append("该工资项目与其他预算支出科目已存在对应关系。多个预算支出科目 不允许对应同一工资项目!!");
							
						}
						
						BudgWageItemDict  code = budgWageItemDictService.queryByCode(mapVo);
						
						if(code == null){
							err_sb.append("该工资项目编码不存在;");
						}
					
					} else {
						
						err_sb.append("该年度工资项目编码为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgWageItemCostShip.setSubj_code(temp[2]);
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgWageItemCostShipService.queryCostSubjByCode(mapVo);
						if(count == 0 ){
							err_sb.append("该年度支出预算科目编码不是末级科目或不存在;");
						}
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
				if (err_sb.toString().length() > 0) {
					
					budgWageItemCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgWageItemCostShip);
					
				} else {
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgWageItemCostShipService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgWageItemCostShip data_exc = new BudgWageItemCostShip();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 工资项目与预算支出科目对应关系
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/addBatchBudgWageItemCostShip", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWageItemCostShip(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWageItemCostShip> list_err = new ArrayList<BudgWageItemCostShip>();
		
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
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgWageItemCostShip budgWageItemCostShip = new BudgWageItemCostShip();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgWageItemCostShip.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wage_item_code"))) {
						
					budgWageItemCostShip.setWage_item_code((String)jsonObj.get("wage_item_code"));
		    		mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));
		    		} else {
						
						err_sb.append("工资项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgWageItemCostShip.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
				BudgWageItemCostShip data_exc_extis = budgWageItemCostShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWageItemCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgWageItemCostShip);
					
				} else {
			  
					String dataJson = budgWageItemCostShipService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWageItemCostShip data_exc = new BudgWageItemCostShip();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	/**
	 * 添加 页面  工资项目 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemcosthship/queryWageItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgWageItemCostShipService.queryWageItem(mapVo);
		return item;

	}
    
}


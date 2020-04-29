/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgawardsitem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgAwardsItemCostShip;
import com.chd.hrp.budg.entity.BudgAwardsItemDict;
import com.chd.hrp.budg.entity.BudgWageItemCostShip;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsItemCostShipService;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsItemDictService;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemCostShipService;
/**
 * 
 * @Description:
 * 奖金项目与预算支出科目对应关系
 * @Table:
 * BUDG_AWARDS_ITEM_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAwardsItemCostShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAwardsItemCostShipController.class);
	
	//引入Service服务
	@Resource(name = "budgAwardsItemCostShipService")
	private final BudgAwardsItemCostShipService budgAwardsItemCostShipService = null;
	
	@Resource(name = "budgWageItemCostShipService")
	private final BudgWageItemCostShipService budgWageItemCostShipService = null;
	
	@Resource(name = "budgAwardsItemDictService")
	private final BudgAwardsItemDictService budgAwardsItemDictService = null;
	
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipMainPage", method = RequestMethod.GET)
	public String budgAwardsItemCostShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipAddPage", method = RequestMethod.GET)
	public String budgAwardsItemCostShipAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipAdd";

	}
	
	/**
	 * @Description 
	 * 添加数据 奖金项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/addBudgAwardsItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWageItemCostShip(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());   
	
		mapVo.put("hos_id", SessionManager.getHosId());   
	
		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgAwardsItemCostShipJson = budgAwardsItemCostShipService.add(mapVo);
		
		return JSONObject.parseObject(budgAwardsItemCostShipJson);
		
	}
	
	
	/*@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/addBudgAwardsItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAwardsItemCostShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String str  = "" ;
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", id.split("@")[0]);
			mapVo.put("awards_item_code", id.split("@")[1]);
            mapVo.put("subj_code", id.split("@")[2]);
            mapVo.put("awards_item_name", id.split("@")[3]);
            mapVo.put("subj_name", id.split("@")[4]);
            //查询 数据是否已存在
            List<BudgAwardsItemCostShip> list = (List<BudgAwardsItemCostShip>) budgAwardsItemCostShipService.queryExists(mapVo);
            if(list.size() >0 ){
            	
            	str += "奖金项目:"+mapVo.get("awards_item_name")+"与预算支出科目:" +mapVo.get("subj_name")+";";
            	
            }else{
            	listVo.add(mapVo);
            }
		}
		if( str == ""){
			String budgAwardsItemCostShipJson = budgAwardsItemCostShipService.addBatch(listVo);
			return JSONObject.parseObject(budgAwardsItemCostShipJson);
		}else{
			return JSONObject.parseObject("{\"error\":\"添加失败,以下数据:"+str+" 对应关系已经存在.无需设置.\"}"); 
		}
       
	}*/
	/**
	 * @Description 
	 * 更新跳转页面 奖金项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipUpdatePage", method = RequestMethod.GET)
	public String budgAwardsItemCostShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgAwardsItemCostShip budgAwardsItemCostShip = new BudgAwardsItemCostShip();
    
		budgAwardsItemCostShip = budgAwardsItemCostShipService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAwardsItemCostShip.getGroup_id());
		mode.addAttribute("hos_id", budgAwardsItemCostShip.getHos_id());
		mode.addAttribute("copy_code", budgAwardsItemCostShip.getCopy_code());
		mode.addAttribute("budg_year", budgAwardsItemCostShip.getBudg_year());
		mode.addAttribute("awards_item_code", budgAwardsItemCostShip.getAwards_item_code());
		mode.addAttribute("subj_code", budgAwardsItemCostShip.getSubj_code());
		
		return "hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 奖金项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/updateBudgAwardsItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAwardsItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgAwardsItemCostShipJson = budgAwardsItemCostShipService.update(mapVo);
		
		return JSONObject.parseObject(budgAwardsItemCostShipJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 奖金项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/addOrUpdateBudgAwardsItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAwardsItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAwardsItemCostShipJson ="";
		

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
	  
		budgAwardsItemCostShipJson = budgAwardsItemCostShipService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAwardsItemCostShipJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 奖金项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/deleteBudgAwardsItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAwardsItemCostShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("awards_item_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAwardsItemCostShipJson = budgAwardsItemCostShipService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAwardsItemCostShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 奖金项目与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/queryBudgAwardsItemCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAwardsItemCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgAwardsItemCostShip = budgAwardsItemCostShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAwardsItemCostShip);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 奖金项目与预算支出科目对应关系
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipImportPage", method = RequestMethod.GET)
	public String budgAwardsItemCostShipImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsitemcostship/budgAwardsItemCostShipImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 奖金项目与预算支出科目对应关系
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgawardsitem/awardsitemcostship/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","奖金项目与预算支出科目对应关系.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 奖金项目与预算支出科目对应关系
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgawardsitem/awardsitemcostship/readBudgAwardsItemCostShipFiles",method = RequestMethod.POST)  
    public String readBudgAwardsItemCostShipFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAwardsItemCostShip> list_err = new ArrayList<BudgAwardsItemCostShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAwardsItemCostShip budgAwardsItemCostShip = new BudgAwardsItemCostShip();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行预算年度、奖金项目相同 ,多个预算支出科目 不允许对应同一奖金项目;");
						}
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					} 
					 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgAwardsItemCostShip.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgAwardsItemCostShip.setAwards_item_code(temp[1]);
						
			    		mapVo.put("awards_item_code", temp[1]);
			    		
			    		BudgAwardsItemDict code = budgAwardsItemDictService.queryByCode(mapVo);
			    		
			    		if( code == null ){
			    			
			    			err_sb.append("奖金项目编码不存在;");
			    		}
			    		
			    		BudgAwardsItemCostShip data = budgAwardsItemCostShipService.queryByCode(mapVo);

			    		if (data != null) {

			    			err_sb.append("奖金项目:"+mapVo.get("awards_item_code")+",与其他预算支出科目存在对应关系。多个预算支出科目 不允许对应同一奖金项目!!");
			    		}
					
					} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgAwardsItemCostShip.setSubj_code(temp[2]);
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgWageItemCostShipService.queryCostSubjByCode(mapVo);
						if(count == 0 ){
							err_sb.append("该年度支出预算科目编码不是末级科目或不存在;");
						}
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					budgAwardsItemCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsItemCostShip);
					
				} else {
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgAwardsItemCostShipService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsItemCostShip data_exc = new BudgAwardsItemCostShip();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 奖金项目与预算支出科目对应关系
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/addBatchBudgAwardsItemCostShip", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgAwardsItemCostShip(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAwardsItemCostShip> list_err = new ArrayList<BudgAwardsItemCostShip>();
		
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
			
			BudgAwardsItemCostShip budgAwardsItemCostShip = new BudgAwardsItemCostShip();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgAwardsItemCostShip.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_code"))) {
						
					budgAwardsItemCostShip.setAwards_item_code((String)jsonObj.get("awards_item_code"));
		    		mapVo.put("awards_item_code", jsonObj.get("awards_item_code"));
		    		} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgAwardsItemCostShip.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
				BudgAwardsItemCostShip data_exc_extis = budgAwardsItemCostShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsItemCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsItemCostShip);
					
				} else {
			  
					String dataJson = budgAwardsItemCostShipService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsItemCostShip data_exc = new BudgAwardsItemCostShip();
			
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
	 * 奖金项目 下拉框(添加页面用)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemcostship/queryAwardsItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAwardsItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgAwardsItemCostShipService.queryAwardsItem(mapVo);
		return item;

	}
}


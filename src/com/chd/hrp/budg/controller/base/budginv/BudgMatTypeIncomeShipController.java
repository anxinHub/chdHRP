/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budginv;
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
import com.chd.hrp.budg.entity.BudgMatTypeCostShip;
import com.chd.hrp.budg.entity.BudgMatTypeIncomeShip;
import com.chd.hrp.budg.service.base.budginv.BudgMatTypeIncomeShipService;
/**
 * 
 * @Description:
 * 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
 * @Table:
 * BUDG_MAT_TYPE_INCOME_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMatTypeIncomeShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMatTypeIncomeShipController.class);
	
	//引入Service服务
	@Resource(name = "budgMatTypeIncomeShipService")
	private final BudgMatTypeIncomeShipService budgMatTypeIncomeShipService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipMainPage", method = RequestMethod.GET)
	public String budgMatTypeIncomeShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipAddPage", method = RequestMethod.GET)
	public String budgMatTypeIncomeShipAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipAdd";

	}

	/**
	 * @Description 
	 * 添加数据 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/addBudgMatTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMatTypeIncomeShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String str  = "" ;
        List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", id.split("@")[0]);
			  mapVo.put("subj_code", id.split("@")[1]);
			mapVo.put("mat_type_id", id.split("@")[2].split(" ")[0]);
			mapVo.put("mat_type_no", id.split("@")[2].split(" ")[1]);
       	mapVo.put("mat_type_name", id.split("@")[3]);
       	mapVo.put("subj_name", id.split("@")[4]);
        
			List<BudgMatTypeCostShip> list = (List<BudgMatTypeCostShip>) budgMatTypeIncomeShipService.queryExists(mapVo);
			 if(list.size() >0 ){
	         	
	         	str += "物资名称:"+mapVo.get("mat_type_name")+"与预算支出科目:" +mapVo.get("subj_name")+";";
	         	
	         }else{
	         	listVo.add(mapVo);
	         }
		}
		if( str == ""){
			String budgWageItemCostShipJson = budgMatTypeIncomeShipService.addBatch(listVo);
			return JSONObject.parseObject(budgWageItemCostShipJson);}
		else{
			return JSONObject.parseObject("{\"error\":\"添加失败,以下数据:"+str+" 已经存在.\"}"); 
			}
      
	}
/**
	 * @Description 
	 * 更新跳转页面 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipUpdatePage", method = RequestMethod.GET)
	public String budgMatTypeIncomeShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMatTypeIncomeShip budgMatTypeIncomeShip = new BudgMatTypeIncomeShip();
    
		budgMatTypeIncomeShip = budgMatTypeIncomeShipService.queryByCode(mapVo);
		
		mode.addAttribute("mat_type_id", budgMatTypeIncomeShip.getMat_type_id());
		mode.addAttribute("mat_type_no", budgMatTypeIncomeShip.getMat_type_no());
		mode.addAttribute("subj_code", budgMatTypeIncomeShip.getSubj_code());
		
		return "hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/updateBudgMatTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMatTypeIncomeShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgMatTypeIncomeShipJson = budgMatTypeIncomeShipService.update(mapVo);
		
		return JSONObject.parseObject(budgMatTypeIncomeShipJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/addOrUpdateBudgMatTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMatTypeIncomeShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMatTypeIncomeShipJson ="";
		

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
	  
		budgMatTypeIncomeShipJson = budgMatTypeIncomeShipService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMatTypeIncomeShipJson);
	}

	/**
	 * 添加 页面  物资分类名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/queryMatTypeIncome", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String item = budgMatTypeIncomeShipService.queryMatTypeIncome(mapVo);
		return item;

	}
    
	/**
	 * @Description 
	 * 删除数据 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/deleteBudgMatTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMatTypeIncomeShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("mat_type_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMatTypeIncomeShipJson = budgMatTypeIncomeShipService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMatTypeIncomeShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/queryBudgMatTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMatTypeIncomeShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMatTypeIncomeShip = budgMatTypeIncomeShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMatTypeIncomeShip);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipImportPage", method = RequestMethod.GET)
	public String budgMatTypeIncomeShipImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budginv/budgmattypeincomeship/budgMatTypeIncomeShipImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budginv/budgmattypeincomeship/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","物资分类与预算收入科目对应关系模板.xlsx");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budginv/budgmattypeincomeship/readBudgMatTypeIncomeShipFiles",method = RequestMethod.POST)  
    public String readBudgMatTypeIncomeShipFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMatTypeIncomeShip> list_err = new ArrayList<BudgMatTypeIncomeShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMatTypeIncomeShip budgMatTypeIncomeShip = new BudgMatTypeIncomeShip();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		mapVo.put("hos_id", SessionManager.getHosId()); 
		    		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		    		
		    		for (int j = i + 1; j < list.size(); j++) {
						String error[] = list.get(j);
						if (temp[0].equals(error[0])) {
							err_sb.append("第" + i + "行数据与第 " + j + "行数据重复;");
						}
						if (temp[1].equals(error[1])) {
							err_sb.append("第" + i + "行数据与第 " + j + "行数据重复;");
						}
					}
		    			if (StringTool.isNotBlank(temp[0])) {
						
						budgMatTypeIncomeShip.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						err_sb.append("预算年度为空  ");
					}

				if (StringTool.isNotBlank(temp[1])) {
	
    	           budgMatTypeIncomeShip.setMat_type_code(temp[1]);
    	   			mapVo.put("mat_type_code", temp[1]);
	
    	   				Map<String,Object> code=budgMatTypeIncomeShipService.queryBudgTypeDictByCode(mapVo);
    	   					if(code==null){
    	   							err_sb.append("物资分类不存在;");
    	   							}
    	   						mapVo.put("mat_type_id",code.get("mat_type_id"));
    
    	   							mapVo.put("mat_type_no",code.get("mat_type_no") );                    

    
    
							} else {
									err_sb.append("物资类别为空  ");
									}
	 
						if (StringTool.isNotBlank(temp[2])) {
							budgMatTypeIncomeShip.setSubj_code(temp[2]);
							mapVo.put("subj_code", temp[2]);
		    		
							int count = budgMatTypeIncomeShipService.queryCostSubjByCode(mapVo);
							if(count == 0 ){
							err_sb.append("该年度支出预算科目编码不是末级科目或不存在;");
								}
							} 
						else {
					
					err_sb.append("科目编码为空  ");
					
				}
				BudgMatTypeIncomeShip data_exc_extis = budgMatTypeIncomeShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMatTypeIncomeShip.setError_type(err_sb.toString());
					
					list_err.add(budgMatTypeIncomeShip);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
				if(list_err.size() == 0 ){
				
				String dataJson = budgMatTypeIncomeShipService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgMatTypeIncomeShip data_exc = new BudgMatTypeIncomeShip();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/budgmattypeincomeship/addBatchBudgMatTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgMatTypeIncomeShip(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMatTypeIncomeShip> list_err = new ArrayList<BudgMatTypeIncomeShip>();
		
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
			
			BudgMatTypeIncomeShip budgMatTypeIncomeShip = new BudgMatTypeIncomeShip();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("mat_type_code"))) {
						
					budgMatTypeIncomeShip.setMat_type_code((String)jsonObj.get("mat_type_code"));
		    		mapVo.put("mat_type_code", jsonObj.get("mat_type_code"));
		    		} else {
						
						err_sb.append("物资分类编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMatTypeIncomeShip.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
				BudgMatTypeIncomeShip data_exc_extis = budgMatTypeIncomeShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMatTypeIncomeShip.setError_type(err_sb.toString());
					
					list_err.add(budgMatTypeIncomeShip);
					
				} else {
			  
					String dataJson = budgMatTypeIncomeShipService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMatTypeIncomeShip data_exc = new BudgMatTypeIncomeShip();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}


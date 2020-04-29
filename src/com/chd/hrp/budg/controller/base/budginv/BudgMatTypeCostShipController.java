/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budginv;
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
import com.chd.hrp.budg.entity.BudgMatTypeCostShip;
import com.chd.hrp.budg.service.base.budginv.BudgMatTypeCostShipService;
import com.chd.hrp.budg.service.base.budginv.BudgTypeDictService;
/**
 * 
 * @Description:
 * 物资分类与预算支出科目对应关系
 * @Table:
 * BUDG_MAT_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMatTypeCostShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMatTypeCostShipController.class);
	
	//引入Service服务
	@Resource(name = "budgMatTypeCostShipService")
	private final BudgMatTypeCostShipService budgMatTypeCostShipService = null;
	
	@Resource(name = "budgTypeDictService")
	private final BudgTypeDictService budgTypeDictService = null;
	
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipMainPage", method = RequestMethod.GET)
	public String budgMatTypeCostShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipAddPage", method = RequestMethod.GET)
	public String budgMatTypeCostShipAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		return "hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipAdd";

	}

	/**
	 * @Description 
	 * 添加数据 物资分类与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/addBudgMatTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMatTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String budgMatTypeCostShipJson  = "";
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
			budgMatTypeCostShipJson = budgMatTypeCostShipService.add(mapVo);
			
		} catch (Exception e) {
			budgMatTypeCostShipJson = e.getMessage();
		}
		return JSONObject.parseObject(budgMatTypeCostShipJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 物资分类与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipUpdatePage", method = RequestMethod.GET)
	public String budgMatTypeCostShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgMatTypeCostShip budgMatTypeCostShip = new BudgMatTypeCostShip();
		
		
		budgMatTypeCostShip = budgMatTypeCostShipService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMatTypeCostShip.getGroup_id());
		mode.addAttribute("hos_id", budgMatTypeCostShip.getHos_id());
		mode.addAttribute("copy_code", budgMatTypeCostShip.getCopy_code());
		mode.addAttribute("budg_year", budgMatTypeCostShip.getBudg_year());
		mode.addAttribute("mat_type_id", budgMatTypeCostShip.getMat_type_id());
		mode.addAttribute("cost_subj_code", budgMatTypeCostShip.getCost_subj_code());
		mode.addAttribute("no_medical", budgMatTypeCostShip.getNo_medical());
		
		mode.addAttribute("income_subj_code", mapVo.get("income_subj_code"));
		
		return "hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 物资分类与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/updateBudgMatTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMatTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMatTypeCostShipJson  = "";
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
			budgMatTypeCostShipJson = budgMatTypeCostShipService.update(mapVo);
			
		} catch (Exception e) {
			budgMatTypeCostShipJson = e.getMessage();
		}
		return JSONObject.parseObject(budgMatTypeCostShipJson);
		
	}
	
	/**
	 * @Description 
	 * 更新数据 物资分类与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/addOrUpdateBudgMatTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMatTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMatTypeCostShipJson ="";
		

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
	  
		budgMatTypeCostShipJson = budgMatTypeCostShipService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMatTypeCostShipJson);
	}
	
	/**
	 * 添加 页面  物资分类名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/queryMatTypes", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			item = budgMatTypeCostShipService.queryMatTypesFilter(mapVo);
		}else{
			item = budgMatTypeCostShipService.queryMatTypes(mapVo);
		}
		
		return item;

	}
	
	/**
	 * 添加 页面  预算科目下拉框 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/queryBudgSubj", method = RequestMethod.POST)
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
		
		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		if(mapVo.get("subj_type").equals("05")){
			mapVo.put("table", "BUDG_COST_SUBJ");
			mapVo.put("column", "cost_subj_code");
		}
		if(mapVo.get("subj_type").equals("04")){
			mapVo.put("table", "BUDG_INCOME_SUBJ");
			mapVo.put("column", "income_subj_code");
		}
		
		String budgSubj = budgMatTypeCostShipService.queryBudgSubj(mapVo);
		return budgSubj;

	}
    
	/**
	 * @Description 
	 * 删除数据 物资分类与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/deleteBudgMatTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMatTypeCostShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("income_subj_code", ids[5]);
				
				listVo.add(mapVo);
	      
			}
	    
		String budgMatTypeCostShipJson = budgMatTypeCostShipService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgMatTypeCostShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 物资分类与预算支出科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/queryBudgMatTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMatTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMatTypeCostShip = budgMatTypeCostShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMatTypeCostShip);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 物资分类与预算支出科目对应关系
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipImportPage", method = RequestMethod.GET)
	public String budgMatTypeCostShipImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budginv/mattypecostship/budgMatTypeCostShipImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 物资分类与预算支出科目对应关系
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budginv/mattypecostship/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","物资分类与预算支出科目对应关系模板.xlsx");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 物资分类与预算支出科目对应关系
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budginv/mattypecostship/readBudgMatTypeCostShipFiles",method = RequestMethod.POST)  
    public String readBudgMatTypeCostShipFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMatTypeCostShip> list_err = new ArrayList<BudgMatTypeCostShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMatTypeCostShip budgMatTypeCostShip = new BudgMatTypeCostShip();
				
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
						
						budgMatTypeCostShip.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						err_sb.append("预算年度为空  ");
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					budgMatTypeCostShip.setMat_type_code(temp[1]);
		    		mapVo.put("mat_type_code", temp[1]);
		    		
		    		Map<String,Object> code=budgMatTypeCostShipService.queryBudgTypeDictByCode(mapVo);
		    		if(code==null){
						err_sb.append("物资分类不存在;");
					}
		    	    mapVo.put("mat_type_id",code.get("mat_type_id"));
                    
		    	    mapVo.put("mat_type_no",code.get("mat_type_no") );                    

		    	    
                    
					} else {
						err_sb.append("物资类别为空  ");
					}
					 
                      if (StringTool.isNotBlank(temp[2])) {
						
                    	  //budgMatTypeCostShip.setSubj_code(temp[2]);
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgMatTypeCostShipService.queryCostSubjByCode(mapVo);
						if(count == 0 ){
							err_sb.append("该年度支出预算科目编码不是末级科目或不存在;");
						}
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
                      
				BudgMatTypeCostShip data_exc_extis = budgMatTypeCostShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					budgMatTypeCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgMatTypeCostShip);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
				if(list_err.size() == 0 ){
				
				budgMatTypeCostShipService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgMatTypeCostShip data_exc = new BudgMatTypeCostShip();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 物资分类与预算支出科目对应关系
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/addBatchBudgMatTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgMatTypeCostShip(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMatTypeCostShip> list_err = new ArrayList<BudgMatTypeCostShip>();
		
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
			
			BudgMatTypeCostShip budgMatTypeCostShip = new BudgMatTypeCostShip();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgMatTypeCostShip.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("mat_type_id"))) {
						
					budgMatTypeCostShip.setMat_type_id(Long.valueOf((String)jsonObj.get("mat_type_id")));
		    		mapVo.put("mat_type_id", jsonObj.get("mat_type_id"));
		    		} else {
						
						err_sb.append("物资类别ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("mat_type_no"))) {
						
					//budgMatTypeCostShip.setMat_type_no(Long.valueOf((String)jsonObj.get("mat_type_no")));
		    		mapVo.put("mat_type_no", jsonObj.get("mat_type_no"));
		    		} else {
						
						err_sb.append("物资类别变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					//budgMatTypeCostShip.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
				BudgMatTypeCostShip data_exc_extis = budgMatTypeCostShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMatTypeCostShip.setError_type(err_sb.toString());
					
					list_err.add(budgMatTypeCostShip);
					
				} else {
			  
					budgMatTypeCostShipService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMatTypeCostShip data_exc = new BudgMatTypeCostShip();
			
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
	 * @Description 
	 * 继承上一年度物资分类与预算科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/extendBudgMatTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendBudgMatTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String budgAssetTypyCostShipJson = budgMatTypeCostShipService.extendBudgMatTypeCostShip(mapVo);

		return JSONObject.parseObject(budgAssetTypyCostShipJson);
		
	} 
	
	/**
	 * 新导入功能
	 */
	@RequestMapping(value = "/hrp/budg/base/budginv/mattypecostship/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = budgMatTypeCostShipService.importExcel(mapVo);
		return reJson;
	}
	
}


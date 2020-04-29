/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgdrug;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.budg.entity.BudgDrugTypeDict;
import com.chd.hrp.budg.entity.BudgDrugTypeIncomeShip;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeDictService;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeIncomeShipService;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
/**
 * 
 * @Description:
 * 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
 * @Table:
 * BUDG_DRUG_TYPE_INCOME_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDrugTypeIncomeShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDrugTypeIncomeShipController.class);
	
	//引入Service服务
	@Resource(name = "budgDrugTypeIncomeShipService")
	private final BudgDrugTypeIncomeShipService budgDrugTypeIncomeShipService = null;
	
	@Resource(name = "budgDrugTypeDictService")
	private final BudgDrugTypeDictService budgDrugTypeDictService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
	
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipMainPage", method = RequestMethod.GET)
	public String budgDrugTypeIncomeShip2MainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipAddPage", method = RequestMethod.GET)
	public String budgDrugTypeIncomeShip2AddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipAdd";

	}

	/**
	 * @Description 
	 * 添加数据 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/addBudgDrugTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDrugTypeIncomeShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String str  = "" ;
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("budg_year", id.split("@")[0]);
			mapVo.put("drug_type_id", id.split("@")[1].split(" ")[0]);
			mapVo.put("drug_type_no", id.split("@")[1].split(" ")[1]);
            mapVo.put("subj_code", id.split("@")[2]);
            mapVo.put("drug_type_name", id.split("@")[3]);
            mapVo.put("subj_name", id.split("@")[4]);
            //查询 数据是否已存在
            List<BudgDrugTypeIncomeShip> list = (List<BudgDrugTypeIncomeShip>) budgDrugTypeIncomeShipService.queryExists(mapVo);
            if(list.size() >0 ){
            	
            	str += "收入预算科目:" +mapVo.get("subj_name")+"与药品分类:"+mapVo.get("drug_type_name")+";";
            	
            }else{
            	listVo.add(mapVo);
            }
		}
		if(str == ""){
			String budgDrugTypeIncomeShipJson = budgDrugTypeIncomeShipService.addBatch(listVo);

			return JSONObject.parseObject(budgDrugTypeIncomeShipJson);
		}else{
			return JSONObject.parseObject("{\"error\":\"添加失败,以下数据:"+str+" 对应关系已经存在.无需设置.\"}");
		}
		
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipUpdatePage", method = RequestMethod.GET)
	public String budgDrugTypeIncomeShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDrugTypeIncomeShip budgDrugTypeIncomeShip = new BudgDrugTypeIncomeShip();
    
		budgDrugTypeIncomeShip = budgDrugTypeIncomeShipService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDrugTypeIncomeShip.getGroup_id());
		mode.addAttribute("hos_id", budgDrugTypeIncomeShip.getHos_id());
		mode.addAttribute("copy_code", budgDrugTypeIncomeShip.getCopy_code());
		mode.addAttribute("budg_year", budgDrugTypeIncomeShip.getBudg_year());
		mode.addAttribute("drug_type_id", budgDrugTypeIncomeShip.getDrug_type_id());
		mode.addAttribute("drug_type_no", budgDrugTypeIncomeShip.getDrug_type_no());
		mode.addAttribute("subj_code", budgDrugTypeIncomeShip.getSubj_code());
		
		return "hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/updateBudgDrugTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDrugTypeIncomeShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgDrugTypeIncomeShipJson = budgDrugTypeIncomeShipService.update(mapVo);
		
		return JSONObject.parseObject(budgDrugTypeIncomeShipJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/addOrUpdateBudgDrugTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDrugTypeIncomeShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDrugTypeIncomeShipJson ="";
		

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
	  
		budgDrugTypeIncomeShipJson = budgDrugTypeIncomeShipService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDrugTypeIncomeShipJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/deleteBudgDrugTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDrugTypeIncomeShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDrugTypeIncomeShipJson = budgDrugTypeIncomeShipService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDrugTypeIncomeShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/queryBudgDrugTypeIncomeShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDrugTypeIncomeShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDrugTypeIncomeShip = budgDrugTypeIncomeShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDrugTypeIncomeShip);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipImportPage", method = RequestMethod.GET)
	public String budgDrugTypeIncomeShip2ImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgdrug/drugtypeincomeship/budgDrugTypeIncomeShipImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgdrug/drugtypeincomeship/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","药品分类与预算收入科目对应关系模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgdrug/drugtypeincomeship/readBudgDrugTypeIncomeShipFiles",method = RequestMethod.POST)  
    public String readBudgDrugTypeIncomeShip2Files(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDrugTypeIncomeShip> list_err = new ArrayList<BudgDrugTypeIncomeShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgDrugTypeIncomeShip budgDrugTypeIncomeShip = new BudgDrugTypeIncomeShip();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行预算年度、收入预算科目相同 ,多个药品分类不允许对应同一收入预算科目;");
						}
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					} 
		    		
		    		if (StringTool.isNotBlank(temp[0])) {
						
		    			budgDrugTypeIncomeShip.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
		    		
		    		if (StringTool.isNotBlank(temp[1])) {
						
						budgDrugTypeIncomeShip.setSubj_code(temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		
			    		BudgIncomeSubj subj = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			    		
			    		if( subj == null ){
			    			err_sb.append("该年度收入预算科目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					if (StringTool.isNotBlank(temp[2])) {
						
						budgDrugTypeIncomeShip.setDrug_type_code(temp[2]);
						
			    		mapVo.put("drug_type_code", temp[2]);
			    		
			    		mapVo.put("med_type_code", temp[2]);
			    		
			    		mapVo.put("is_stop", "0");
			    		
			    		BudgDrugTypeDict  type = budgDrugTypeDictService.queryByCode(mapVo);
						
						if(type == null){
							err_sb.append("该药品分类编码不存在或已停用;");
						}else{
							budgDrugTypeIncomeShip.setDrug_type_id(type.getMed_type_id());
							
							budgDrugTypeIncomeShip.setDrug_type_no(type.getMed_type_no());
							
							mapVo.put("drug_type_id", type.getMed_type_id());
							mapVo.put("drug_type_no", type.getMed_type_no());
						}
					
					} else {
						
						err_sb.append("药品分类编码为空  ");
						
					}
					 
					 
					
				BudgDrugTypeIncomeShip data_exc_extis = budgDrugTypeIncomeShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("所填收入预算科目已与其他药品分类存在对应关系,多个药品分类不允许对应同一收入预算科目;");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDrugTypeIncomeShip.setError_type(err_sb.toString());
					
					list_err.add(budgDrugTypeIncomeShip);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgDrugTypeIncomeShipService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgDrugTypeIncomeShip data_exc = new BudgDrugTypeIncomeShip();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypeincomeship2/addBatchBudgDrugTypeIncomeShip2", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDrugTypeIncomeShip2(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDrugTypeIncomeShip> list_err = new ArrayList<BudgDrugTypeIncomeShip>();
		
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
			
			BudgDrugTypeIncomeShip budgDrugTypeIncomeShip = new BudgDrugTypeIncomeShip();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("drug_type_code"))) {
						
					budgDrugTypeIncomeShip.setDrug_type_code((String)jsonObj.get("drug_type_code"));
		    		mapVo.put("drug_type_code", jsonObj.get("drug_type_code"));
		    		} else {
						
						err_sb.append("药品分类编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgDrugTypeIncomeShip.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
				BudgDrugTypeIncomeShip data_exc_extis = budgDrugTypeIncomeShipService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDrugTypeIncomeShip.setError_type(err_sb.toString());
					
					list_err.add(budgDrugTypeIncomeShip);
					
				} else {
			  
					String dataJson = budgDrugTypeIncomeShipService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDrugTypeIncomeShip data_exc = new BudgDrugTypeIncomeShip();
			
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


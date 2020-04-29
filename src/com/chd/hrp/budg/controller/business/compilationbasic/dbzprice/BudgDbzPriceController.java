/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.dbzprice;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDbzPrice;
import com.chd.hrp.budg.service.business.compilationbasic.dbzprice.BudgDbzPriceService;
/**
 * 
 * @Description:
 * 单病种收费标准
 * @Table:
 * BUDG_DBZ_PRICE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDbzPriceController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDbzPriceController.class);
	
	//引入Service服务
	@Resource(name = "budgDbzPriceService")
	private final BudgDbzPriceService budgDbzPriceService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceMainPage", method = RequestMethod.GET)
	public String budgDbzPriceMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceAddPage", method = RequestMethod.GET)
	public String budgDbzPriceAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceAdd";

	}
	

	/**
	 * @Description 
	 * 保存数据 单病种收费标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/saveBudgDbzPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDbzPrice(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("budg_year", ids[0])   ;
			mapVo.put("disease_code", ids[1]);
			mapVo.put("insurance_code", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("charge_standard", "");
			}else{
				mapVo.put("charge_standard", ids[3]);
			}
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgDbzPriceJson = null ;
		
		try {
			
			budgDbzPriceJson = budgDbzPriceService.save(listVo);
			
		} catch (Exception e) {
			
			budgDbzPriceJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgDbzPriceJson);
	}
	/**
	 * @Description 
	 * 添加数据 单病种收费标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/addBudgDbzPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDbzPrice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgDbzPriceJson = budgDbzPriceService.add(mapVo);
		return JSONObject.parseObject(budgDbzPriceJson);		
	}
	/**
	 * @Description 
	 * 更新跳转页面 单病种收费标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceUpdatePage", method = RequestMethod.GET)
	public String budgDbzPriceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDbzPrice budgDbzPrice = new BudgDbzPrice();
    
		budgDbzPrice = budgDbzPriceService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDbzPrice.getGroup_id());
		mode.addAttribute("hos_id", budgDbzPrice.getHos_id());
		mode.addAttribute("copy_code", budgDbzPrice.getCopy_code());
		mode.addAttribute("budg_year", budgDbzPrice.getBudg_year());
		mode.addAttribute("disease_code", budgDbzPrice.getDisease_code());
		mode.addAttribute("insurance_code", budgDbzPrice.getInsurance_code());
		mode.addAttribute("charge_standard", budgDbzPrice.getCharge_standard());
		
		return "hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 单病种收费标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/updateBudgDbzPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDbzPrice(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		 List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();			
				String[] ids=id.split("@");
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("budg_year", ids[0]);
				mapVo.put("insurance_code", ids[1]);
				mapVo.put("disease_code", ids[2]);
				if("-1".equals(ids[3])){
					mapVo.put("charge_standard", "");
				}else{
					mapVo.put("charge_standard", ids[3]);
				}
				
		      listVo.add(mapVo);	      
		    }        
	  
		String budgDbzPriceJson = budgDbzPriceService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDbzPriceJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 单病种收费标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/addOrUpdateBudgDbzPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDbzPrice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDbzPriceJson ="";
		

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
	  
		budgDbzPriceJson = budgDbzPriceService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDbzPriceJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 单病种收费标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/deleteBudgDbzPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDbzPrice(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("disease_code", ids[4])   ;
				mapVo.put("insurance_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDbzPriceJson = budgDbzPriceService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDbzPriceJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 单病种收费标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/queryBudgDbzPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDbzPrice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDbzPrice = budgDbzPriceService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDbzPrice);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 单病种收费标准
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceImportPage", method = RequestMethod.GET)
	public String budgDbzPriceImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 单病种收费标准
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/dbzprice/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","单病种收费标准模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 单病种收费标准
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/dbzprice/readBudgDbzPriceFiles",method = RequestMethod.POST)  
    public String readBudgDbzPriceFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDbzPrice> list_err = new ArrayList<BudgDbzPrice>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgDbzPrice budgDbzPrice = new BudgDbzPrice();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						budgDbzPrice.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						budgDbzPrice.setDisease_code(temp[1]);
			    		mapVo.put("disease_code", temp[1]);
			    		
			    		int count = budgDbzPriceService.queryBudgDiseaseCode(mapVo);
			    		
			    		if(count == 0 ){
			    			err_sb.append("病种编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("病种编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						budgDbzPrice.setInsurance_code(temp[2]);
			    		mapVo.put("insurance_code", temp[2]);
			    		
			    		//根据医保单病种编码  查询医保类型编码是否存在（查询 医保单病种维护表 BUDG_YB_SINGLE_DISEASE ）
			    		int count = budgDbzPriceService.queryBudgInsuranceCode(mapVo);
			    		
			    		if(count == 0 ){
			    			err_sb.append("填写的病种编码与医保类型编码的关系未维护;");
			    		}
						
					} else {
						
						err_sb.append("医保类型编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						budgDbzPrice.setCharge_standard(Double.valueOf(temp[3]));
			    		mapVo.put("charge_standard", temp[3]);
					
					} else {
						
						err_sb.append("收费标准为空;");
						
					}
					 
					
				int count = budgDbzPriceService.queryDataExist(mapVo);
				
				if (count > 0) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDbzPrice.setError_type(err_sb.toString());
					
					list_err.add(budgDbzPrice);
					
				} else {
			  
					String dataJson = budgDbzPriceService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDbzPrice data_exc = new BudgDbzPrice();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 单病种收费标准
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/addBatchBudgDbzPrice", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDbzPrice(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDbzPrice> list_err = new ArrayList<BudgDbzPrice>();
		
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
			
			BudgDbzPrice budgDbzPrice = new BudgDbzPrice();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgDbzPrice.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("disease_code"))) {
						
					budgDbzPrice.setDisease_code((String)jsonObj.get("disease_code"));
		    		mapVo.put("disease_code", jsonObj.get("disease_code"));
		    		} else {
						
						err_sb.append("病种编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgDbzPrice.setInsurance_code((String)jsonObj.get("insurance_code"));
					
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("charge_standard"))) {
						
					budgDbzPrice.setCharge_standard(Double.valueOf((String)jsonObj.get("charge_standard")));
		    		mapVo.put("charge_standard", jsonObj.get("charge_standard"));
		    		} else {
						
						err_sb.append("收费标准为空  ");
						
					}
					
					
				BudgDbzPrice data_exc_extis = budgDbzPriceService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDbzPrice.setError_type(err_sb.toString());
					
					list_err.add(budgDbzPrice);
					
				} else {
			  
					String dataJson = budgDbzPriceService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDbzPrice data_exc = new BudgDbzPrice();
			
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
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			// 查询要生成的数据
			List<Map<String,Object>> list = budgDbzPriceService.queryDate(mapVo);
			
			for(Map<String, Object> item : list ){
				
				item.put("charge_standard","");
					
			}
			
			String budgDbzPriceJson = null ;
			
			if(list.size() > 0 ){
				
				budgDbzPriceService.addBatch(list);
				
				budgDbzPriceJson = "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		         
			}else{
				budgDbzPriceJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
			}
		   
			return JSONObject.parseObject(budgDbzPriceJson);	
	} 	
	
	/**
	 * 病种名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/queryBudgSingleDC", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSingleDC(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String typeNature = budgDbzPriceService.queryBudgSingleDC(mapVo);
		return typeNature;

	}
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/queryBudgYBTY", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBTY(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String ybType = budgDbzPriceService.queryBudgYBTY(mapVo);
		
		return ybType;

	}
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzprice/budgDbzPriceImportNewPage", method = RequestMethod.POST)
	@ResponseBody
	public String budgDbzPriceImportNewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgDbzPriceService.budgDbzPriceImportNewPage(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	
	
	
}


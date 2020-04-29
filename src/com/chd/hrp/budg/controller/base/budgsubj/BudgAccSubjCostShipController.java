
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.controller.base.budgsubj;
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
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgCostSubj;
import com.chd.hrp.budg.entity.BudgCostSubjType;
import com.chd.hrp.budg.entity.BudgCostTypeSet;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgIncomeSubjType;
import com.chd.hrp.budg.entity.BudgIncomeTypeSet;
import com.chd.hrp.budg.service.base.budgsubj.BudgAccSubjShipService;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjService;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjTypeService;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostTypeSetService;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjTypeService;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeTypeSetService;

/**
 * 
 * @Description:
 * 预算科目与会计科目对应关系表
 * @Table:
 * BUDG_ACC_SUBJ_SHIP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class BudgAccSubjCostShipController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAccSubjCostShipController.class);
	
	//引入Service服务
	@Resource(name = "budgAccSubjShipService")
	private final BudgAccSubjShipService budgAccSubjShipService = null;
	
	@Resource(name = "budgCostSubjService")
	private final BudgCostSubjService budgCostSubjService = null;
	
	@Resource(name = "budgCostSubjTypeService")
	private final BudgCostSubjTypeService budgCostSubjTypeService = null;
	
	@Resource(name = "budgCostTypeSetService")
	private final BudgCostTypeSetService budgCostTypeSetService = null;
	/**
	 * @Description 
	 * 主页面跳转 (收入预算科目与会计科目对应关系)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipMainPage", method = RequestMethod.GET)
	public String budgAccSubjShipMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipAddPage", method = RequestMethod.GET)
	public String budgAccSubjShipAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipAdd";

	}

	/**
	 *支出预算科目类别维护<BR>
	 *保存
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/saveBudgAccSubjCostShip", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> saveBudgAccSubjIncomeShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("budg_year", id.split("@")[0]);
			mapVo.put("acc_subj_code2", id.split("@")[1]);
			mapVo.put("acc_subj_name", id.split("@")[2]);
			mapVo.put("subj_code", id.split("@")[3]);
			mapVo.put("subj_name", id.split("@")[4]);
			mapVo.put("subj_type", id.split("@")[5]);
			mapVo.put("acc_subj_code2_old", id.split("@")[6]);
			
			mapVo.put("rowNo", id.split("@")[7]);
			mapVo.put("flag", id.split("@")[8]);
			
			//根据编码查询数据使用
			mapVo.put("table1", "BUDG_COST_SUBJ");
			mapVo.put("table2", "BUDG_COST_SUBJ_TYPE");
			
			listVo.add(mapVo);
		}
		String typeSetJson = budgAccSubjShipService.save(listVo);
		
		return JSONObject.parseObject(typeSetJson);
		
	}
	
	
	/**
	 * @Description 
	 * 添加数据 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/addBudgAccSubjCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAccSubjCostShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("acc_subj_code2", id.split("@")[0]);
            mapVo.put("subj_code", id.split("@")[1]);
            mapVo.put("budg_year", id.split("@")[2]);
            mapVo.put("acc_subj_name", id.split("@")[3]);
            mapVo.put("subj_name", id.split("@")[4]);
            mapVo.put("subj_type", id.split("@")[5]);
            //查询 数据是否已存在
            int count = budgAccSubjShipService.queryIsExist(mapVo);
            if(count >0 ){
            	return JSONObject.parseObject("{\"msg\":\"数据  会计科目名称:"+mapVo.get("acc_subj_name")+"  预算科目名称:" +mapVo.get("subj_name")+"对应关系已经存在.无需设置.\"}"); 
            }else{
            	listVo.add(mapVo);
            }
            
        }
       
		String budgAccSubjShipJson = budgAccSubjShipService.addBatchBudgAccSubjShip(listVo);

		return JSONObject.parseObject(budgAccSubjShipJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipUpdatePage", method = RequestMethod.GET)
	public String budgAccSubjShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		mapVo.put("table1", "BUDG_COST_SUBJ");
		
		mapVo.put("table2", "BUDG_COST_SUBJ_TYPE");
		
		BudgAccSubjShip budgAccSubjShip = budgAccSubjShipService.queryBudgAccSubjShipByCode(mapVo);
		
		mode.addAttribute("group_id", budgAccSubjShip.getGroup_id());
		mode.addAttribute("hos_id", budgAccSubjShip.getHos_id());
		mode.addAttribute("copy_code", budgAccSubjShip.getCopy_code());
		mode.addAttribute("budg_year", budgAccSubjShip.getBudg_year());
		mode.addAttribute("subj_code", budgAccSubjShip.getSubj_code());
		mode.addAttribute("subj_name", budgAccSubjShip.getSubj_name());
		mode.addAttribute("acc_subj_code2", budgAccSubjShip.getAcc_subj_code2());
		mode.addAttribute("acc_subj_name", budgAccSubjShip.getAcc_subj_name());
		mode.addAttribute("subj_type", budgAccSubjShip.getSubj_type());
		mode.addAttribute("subj_type_name", budgAccSubjShip.getSubj_type_name());
		
		return "hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/updateBudgAccSubjCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAccSubjCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		mapVo.put("table", "BUDG_COST_SUBJ");
		
		String budgAccSubjShipJson = budgAccSubjShipService.updateBudgAccSubjShip(mapVo);
		
		return JSONObject.parseObject(budgAccSubjShipJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/deleteBudgAccSubjCostShip",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAccSubjCostShip(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3]);
				mapVo.put("acc_subj_code2", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String budgAccSubjShipJson = budgAccSubjShipService.deleteBatchBudgAccSubjShip(listVo);
			
	  return JSONObject.parseObject(budgAccSubjShipJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 预算科目与会计科目对应关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/queryBudgAccSubjCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAccSubjCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("table", "BUDG_COST_SUBJ");
		mapVo.put("table2", "BUDG_COST_SUBJ_TYPE");
		
		String budgAccSubjShip = budgAccSubjShipService.queryBudgAccSubjShip(getPage(mapVo));

		return JSONObject.parseObject(budgAccSubjShip);
		
	}
	/**
	 * 由会计科目对照生成预算科目
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/setBudgAccSubjCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setBudgAccSubjCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgAccSubjShip = budgAccSubjShipService.setBudgAccSubjShip(mapVo);

		return JSONObject.parseObject(budgAccSubjShip);
		
	}
  /**
	 * @Description 
	 * 导入跳转页面 预算科目与会计科目对应关系表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipImportPage", method = RequestMethod.GET)
	public String budgAccSubjCostShipImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgcostship/budgAccSubjCostShipImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 预算科目与会计科目对应关系表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/budg/base/budgsubj/budgcostship/downTemplate1", method = RequestMethod.GET)  
	 public String downTemplate1(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","支出预算科目与会计科目对应关系模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 预算科目与会计科目对应关系表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/base/budgsubj/budgcostship/readBudgAccSubjCostShipFiles",method = RequestMethod.POST)  
    public String readBudgAccSubjShipFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAccSubjShip> list_err = new ArrayList<BudgAccSubjShip>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAccSubjShip budgAccSubjShip = new BudgAccSubjShip();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[3].equals(error[3])&& temp[4].equals(error[4])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				
					if (StringTool.isNotBlank(temp[0])) {
						
						budgAccSubjShip.setBudg_year(String.valueOf(temp[0]));		
						mapVo.put("budg_year", temp[0]);
						
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
						budgAccSubjShip.setSubj_code(String.valueOf(temp[1]));	
						mapVo.put("subj_code", temp[1]);
						
						BudgCostSubj cost = budgCostSubjService.queryBudgCostSubjByCode(mapVo);
						if(cost == null ){
							err_sb.append("该年度科目编码不存在;");
						}
		    		
					} else {
						
						err_sb.append("科目编码为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
						budgAccSubjShip.setSubj_name(String.valueOf(temp[2]));		
						mapVo.put("subj_name", temp[2]);
						
					} else {
						
						err_sb.append("科目名称为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
						budgAccSubjShip.setAcc_subj_code2(String.valueOf(temp[3]));	
						mapVo.put("acc_subj_code2", temp[3]);
						
						int count = budgAccSubjShipService.queryAccSubjCodeExist(mapVo);
						
						if(count == 0 ){
							err_sb.append("该年度会计科目编码不存在或已停用;");
						}
					} else {
						
						err_sb.append("会计科目编码为空;");
						
					}
					if (StringTool.isNotBlank(temp[4])) {
						
						budgAccSubjShip.setAcc_subj_name(String.valueOf(temp[4]));		
						mapVo.put("acc_subj_name", temp[4]);
						
					} else {
						
						err_sb.append("会计科目名称为空;");
						
					}
					
					/*if (StringTool.isNotBlank(temp[3])) {
						
						budgAccSubjShip.setSubj_type(String.valueOf(temp[3]));	
						mapVo.put("subj_type", temp[3]);
						
						mapVo.put("type_code", temp[3]);
						
						mapVo.put("cost_subj_type_code", temp[3]);
						
						BudgCostSubjType type = budgCostSubjTypeService.queryBudgCostSubjTypeByCode(mapVo);
						
						if(type == null ){
							err_sb.append("科目类别不存在;");
						}
						
						BudgCostTypeSet typeSet = budgCostTypeSetService.queryBudgCostTypeSetByCode(mapVo);
						if( typeSet == null ){
							err_sb.append("科目类别与科目对应关系不存在;");
						}
					} else {
						
						err_sb.append("科目类别为空;");
						
					}*/
					
					mapVo.put("subj_type", "05");
					
					mapVo.put("table", "BUDG_COST_SUBJ");
					
				int data_exc_extis = budgAccSubjShipService.queryIsExist(mapVo);
				
				if (data_exc_extis >0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAccSubjShip.setError_type(err_sb.toString());
					
					list_err.add(budgAccSubjShip);
					
				} else {
					
					addList.add(mapVo);
					
					
				}
				
			}
			if(list_err.size() == 0 ){
				String dataJson = budgAccSubjShipService.addBatchBudgAccSubjShip(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgAccSubjShip data_exc = new BudgAccSubjShip();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 暂时不用
	 * 批量添加数据 预算科目与会计科目对应关系表 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/addBatchBudgAccSubjCostShip",method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgAccSubjCostShip(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAccSubjShip> list_err = new ArrayList<BudgAccSubjShip>();
		
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
			
			BudgAccSubjShip budgAccSubjShip = new BudgAccSubjShip();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
						budgAccSubjShip.setBudg_year(String.valueOf(jsonObj.get("budg_year")));					
						mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
						budgAccSubjShip.setSubj_code(String.valueOf(jsonObj.get("subj_code")));				
						mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("acc_subj_code2"))) {
						
						budgAccSubjShip.setAcc_subj_code2(String.valueOf(jsonObj.get("acc_subj_code2")));				
						mapVo.put("acc_subj_code2", jsonObj.get("acc_subj_code2"));
		    		
					} else {
						
						err_sb.append("会计科目编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("subj_type"))) {
						
						budgAccSubjShip.setSubj_type(String.valueOf(jsonObj.get("subj_type")));				
						mapVo.put("subj_type", jsonObj.get("subj_type"));
		    		
					} else {
						
						err_sb.append("科目类别为空  ");
						
					}
					if(mapVo.get("subj_type").equals("05")){
						mapVo.put("table", "BUDG_COST_SUBJ");
					}
					if(mapVo.get("subj_type").equals("04")){
						mapVo.put("table", "BUDG_INCOME_SUBJ");
					}
				BudgAccSubjShip data_exc_extis = budgAccSubjShipService.queryBudgAccSubjShipByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAccSubjShip.setError_type(err_sb.toString());
					
					list_err.add(budgAccSubjShip);
					
				} else {
			  
					String dataJson = budgAccSubjShipService.addBudgAccSubjShip(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAccSubjShip data_exc = new BudgAccSubjShip();
			
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
	 * 添加页面 会计科目  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/queryAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		return budgAccSubjShipService.queryAccSubj(mapVo);

	}
	
	/**
	 * 添加页面支出预算科目  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/queryBudgCostTypeSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostTypeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		return budgAccSubjShipService.queryBudgCostTypeSet(mapVo);
	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String budgWorkHosYearJson = null ;
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		mapVo.put("subj_type", "05");
		
		budgWorkHosYearJson =budgAccSubjShipService.generate(mapVo);
			
		return JSONObject.parseObject(budgWorkHosYearJson);	
	} 
	/**
	 * @Description 
	 * 继承
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostship/extend", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String budgWorkHosYearJson = null ;
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		mapVo.put("subj_type", "05");
		
		budgWorkHosYearJson =budgAccSubjShipService.extend(mapVo);
		
		return JSONObject.parseObject(budgWorkHosYearJson);	
	} 
}


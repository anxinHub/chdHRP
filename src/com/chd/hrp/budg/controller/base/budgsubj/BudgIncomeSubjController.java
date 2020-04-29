
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.controller.base.budgsubj;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.service.AccSubjNatureService;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgAccSubjShipService;
import com.chd.hrp.budg.serviceImpl.base.budgsubj.BudgIncomeSubjServiceImpl;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;

/**
 * 
 * @Description:
 * 收入预算科目
 * @Table:
 * BUDG_INCOME_SUBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class BudgIncomeSubjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgIncomeSubjController.class);
	
	//引入Service服务
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjServiceImpl budgIncomeSubjService = null;
	@Resource(name = "budgAccSubjShipService")
	private final BudgAccSubjShipService budgAccSubjShipService = null;
	
	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;
	
	@Resource(name = "accSubjNatureService")
	private final AccSubjNatureService accSubjNatureService = null;
	

	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjMainPage", method = RequestMethod.GET)
	public String incomeBudgSubjMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjAddPage", method = RequestMethod.GET)
	public String incomeBudgSubjAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjAdd";

	}
	/**
	 * 由会计生成界面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/budgAccRelationshipMainPage", method = RequestMethod.GET)
	public String budgAccRelationshipMainPage(Model mode) throws Exception {
		mode.addAttribute("subj_type", "04");
		return "hrp/budg/budgaccrelationship/budgAccRelationshipMain";

	}
	
	/**
	 * 批量修改
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/budgBathUpdateMainPage", method = RequestMethod.GET)
	public String budgBathUpdateMainPage(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		mode.addAttribute("subj_code", paramVo);
		return "hrp/budg/base/budgsubj/budgincomesubj/budgBathUpdateMainPage";

	}
	/**
	 * @Description 
	 * 添加数据 收入预算科目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/addBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
       
		String incomeBudgSubjJson = budgIncomeSubjService.addBudgIncomeSubj(mapVo);

		return JSONObject.parseObject(incomeBudgSubjJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 收入预算科目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjUpdatePage", method = RequestMethod.GET)
	public String budgIncomeSubjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    BudgIncomeSubj incomeBudgSubj = new BudgIncomeSubj();
    
		incomeBudgSubj = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
		
		mode.addAttribute("group_id", incomeBudgSubj.getGroup_id());
		mode.addAttribute("hos_id", incomeBudgSubj.getHos_id());
		mode.addAttribute("copy_code", incomeBudgSubj.getCopy_code());
		mode.addAttribute("budg_year", incomeBudgSubj.getBudg_year());
		mode.addAttribute("subj_code", incomeBudgSubj.getSubj_code());
		mode.addAttribute("subj_name", incomeBudgSubj.getSubj_name());
		mode.addAttribute("subj_name_all", incomeBudgSubj.getSubj_name_all());
		mode.addAttribute("super_code", incomeBudgSubj.getSuper_code());
		mode.addAttribute("super_name", incomeBudgSubj.getSuper_name());
		mode.addAttribute("subj_level", incomeBudgSubj.getSubj_level());
		mode.addAttribute("type_code", incomeBudgSubj.getType_code());
		mode.addAttribute("type_name", incomeBudgSubj.getType_name());
		//mode.addAttribute("subj_nature", incomeBudgSubj.getSubj_nature());
		//mode.addAttribute("subj_nature_name", incomeBudgSubj.getSubj_nature_name());
		mode.addAttribute("is_last", incomeBudgSubj.getIs_last());
		mode.addAttribute("is_carried", incomeBudgSubj.getIs_caarried());
		mode.addAttribute("spell_code", incomeBudgSubj.getSpell_code());
		mode.addAttribute("wbx_code", incomeBudgSubj.getWbx_code());
		
		return "hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 收入预算科目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/updateBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIncomeBudgSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
		String incomeBudgSubjJson = budgIncomeSubjService.updateBudgIncomeSubj(mapVo);
		
		return JSONObject.parseObject(incomeBudgSubjJson);
	}
	/**
	 * @Description 
	 * 更新数据 收入预算科目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/budgBathUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> budgBathUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String subj_code=mapVo.get("subj_code").toString();
		for ( String id: subj_code.split(",")) {
			Map<String, Object> mapV=new HashMap<String, Object>();
			String[] ids=id.split("@");
			
			//表的主键
			mapV.put("group_id", SessionManager.getGroupId());
			mapV.put("hos_id", SessionManager.getHosId());
			mapV.put("copy_code", SessionManager.getCopyCode());
			mapV.put("budg_year",  ids[0]);
			
			mapV.put("type_code",  mapVo.get("type_code"));
			mapV.put("subj_code", ids[1]);
			
			listVo.add(mapV);
		}
		String incomeBudgSubjJson = budgIncomeSubjService.budgBathUpdate(listVo);
		
		return JSONObject.parseObject(incomeBudgSubjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 收入预算科目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/deleteBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgIncomeSubj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String retErrot = "";
		String errStr = "";
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				mapVo.put("super_code",ids[5]);
				//判断是否被引用
				String str_sup = budgIncomeSubjService.isExistsDataByTable("budg_income_subj", ids[4]);
				
				if (Strings.isNotBlank(str_sup)) {
					retErrot = "{\"error\":\"删除失败，选择的科目被以下业务使用：【" + str_sup.substring(0, str_sup.length() - 1) + "】。\",\"state\":\"false\"}";
					return JSONObject.parseObject(retErrot);
				}
			if(ids[6].toString().equals("0")){
				errStr+=mapVo.get("subj_code") +",";
			}
	      listVo.add(mapVo);
		
	    }
			
		if(errStr != null && !"".equals(errStr)){
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的数据：【" + errStr.substring(0, errStr.length() - 1) + "】不是末级,不允许删除。\",\"state\":\"false\"}");
		}
		String incomeBudgSubjJson = budgIncomeSubjService.deleteBatchBudgIncomeSubj(listVo);
			
		return JSONObject.parseObject(incomeBudgSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 收入预算科目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String incomeBudgSubj = budgIncomeSubjService.queryBudgIncomeSubj(getPage(mapVo));

		return JSONObject.parseObject(incomeBudgSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 收入预算科目
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjImportPage", method = RequestMethod.GET)
	public String incomeBudgSubjImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgincomesubj/budgIncomeSubjImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 收入预算科目
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/budg/base/budgsubj/budgincomesubj/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","收入预算科目模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 收入预算科目
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/base/budgsubj/budgincomesubj/readBudgIncomeSubjFiles",method = RequestMethod.POST)  
    public String readIncomeBudgSubjFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgIncomeSubj> list_err = new ArrayList<BudgIncomeSubj>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		StringBuffer err_sb = new StringBuffer();
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		if(list.size()==0){
			err_sb.append("导入数据为空");
		}
		try {
			for (int i = 1; i < list.size(); i++) {
				
				BudgIncomeSubj incomeBudgSubj = new BudgIncomeSubj();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
					//if(temp[3].equals(error[3])){
					//	err_sb.append("第"+i+"行数据与第 "+j+"行数据全称重复;");
					//}						
				}
				
				if (StringTool.isNotBlank(temp[0])) {
						
					incomeBudgSubj.setBudg_year(temp[0]);			
		    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					incomeBudgSubj.setSubj_code(String.valueOf(temp[1]));	
		    		mapVo.put("subj_code", temp[1]);
		    		
					} else {
						
						err_sb.append("科目编码为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					incomeBudgSubj.setSubj_name(String.valueOf(temp[2]));
					incomeBudgSubj.setSubj_name_all(String.valueOf(temp[2]));
		    		mapVo.put("subj_name", temp[2]);
		    		mapVo.put("subj_name_all", temp[2]);
		    		mapVo.put("is_last", 1);
					} else {
						
						err_sb.append("科目名称为空;");
						
					}					
					/*原科目全称
					if (StringTool.isNotBlank(temp[3])) {	
					incomeBudgSubj.setSubj_name_all(String.valueOf(temp[3]));	
		    		mapVo.put("subj_name_all", temp[3]);
					} */
					
					if (StringTool.isNotBlank(temp[3])) {
						
						incomeBudgSubj.setType_code(String.valueOf(temp[3]));		
			    		
						mapVo.put("type_code", temp[3]);
			    		
						Map<String, Object> nature = budgIncomeSubjService.queryAccTypeCodeByCode(mapVo) ;
						
						if(nature == null ){
							err_sb.append("收入预算科目类别不存在;");
						}
		    		
					} else {
						
						incomeBudgSubj.setType_code(String.valueOf(temp[3]));		
			    		
						mapVo.put("type_code", temp[3]);
						
					}
					/*原是否末级
					if (StringTool.isNotBlank(temp[5])) {
						
					incomeBudgSubj.setIs_last(Integer.valueOf(String.valueOf(whetherMap.get(temp[5]).toString())));
								
		    		mapVo.put("is_last", whetherMap.get(temp[5]));
		    		
					} else {						
						err_sb.append("是否末级为空;");		
					}
					*/
					if (StringTool.isNotBlank(temp[4])) {
					incomeBudgSubj.setIs_caarried(Integer.parseInt(String.valueOf(whetherMap.get(temp[4]).toString())));	
		    		mapVo.put("is_caarried", whetherMap.get(temp[4]));
		    		
					} else {
						
						err_sb.append("是否结转为空;");
						
					}
				String rules= new String();
				
				try {
					
					rules = getRules(mapVo);
					
					if(rules == null || rules == ""){
						err_sb.append("预算收入科目编码规则未设置！");
						
					}else{
						String sup="TOP";
						String subj_code = (String)mapVo.get("subj_code");
						String [] ruless  = rules.split("-");
						Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
						    
					    Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
					    
					    int super_num = 0;
					    
					    for(int j = 0; j < ruless.length; j++){
					    	int num = Integer.parseInt(ruless[j].replace(" ", ""));
					    	super_num += num;
					    	maxNumMap.put(super_num, j + 1);
					    	position.put(j + 1, super_num);
					    }
					    
					    if(maxNumMap.containsKey(subj_code.length())){//编码匹配
					    	 int subj_level = maxNumMap.get(subj_code.length());
					    	 mapVo.put("subj_level", subj_level);
					    	 if(subj_level == 1){
					    		 mapVo.put("super_code", sup);
					    	 }else{
					    		 int super_level =  subj_level - 1;//上级级次
					        	 int subPosition = position.get(super_level);//上级级次位置
					        	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
					        	 mapVo.put("super_code", super_code);
					        	 
					        	 Map<String,Object> supMap=budgIncomeSubjService.querySup(mapVo);
					        	 int count =0;
					        	 for(int m = 1 ; m < list.size(); m++){					        		 
										String subjcodes[] = list.get(m);
										if(m!=i && super_code.equals(subjcodes[1])){
											count =1;
											break;
										}											
								}
					        	 if(supMap==null && count== 0 ){
					        		 err_sb.append("上级编码不存在！"); 
					        	 }        	
					    	 }
					    }else{
					    	err_sb.append("不符合编码规则！");
					    }
					  
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
				  
				BudgIncomeSubj data_exc_extis = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
				//BudgAccRelationship budgRealtionship = budgAccRelationshipService.queryBudgAccRelationshipByCode(mapVo);
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				/*if(budgRealtionship != null){
					err_sb.append("对应会计科目 对应已经存在！ ");
				}*/
				if (err_sb.toString().length() > 0) {
					
					incomeBudgSubj.setError_type(err_sb.toString());
					
					list_err.add(incomeBudgSubj);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
				  
				  addList.add(mapVo);
					
				}
				err_sb = new StringBuffer();
			}
			if(list_err.size() == 0){
				
				String dataJson = budgIncomeSubjService.addBatchBudgIncomeSubj(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgIncomeSubj data_exc = new BudgIncomeSubj();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 收入预算科目
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/addBatchBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgIncomeSubj(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgIncomeSubj> list_err = new ArrayList<BudgIncomeSubj>();
		
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
			
			BudgIncomeSubj incomeBudgSubj = new BudgIncomeSubj();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
						incomeBudgSubj.setBudg_year(String.valueOf(jsonObj.get("budg_year")));					
						mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
						incomeBudgSubj.setSubj_code(String.valueOf(jsonObj.get("subj_code")));				
						mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("subj_name"))) {
						
						incomeBudgSubj.setSubj_name(String.valueOf(jsonObj.get("subj_name")));				
						mapVo.put("subj_name", jsonObj.get("subj_name"));
		    		
					} else {
						
						err_sb.append("科目名称为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("subj_name_all"))) {
						
						incomeBudgSubj.setSubj_name_all(String.valueOf(jsonObj.get("subj_name_all")));					
						mapVo.put("subj_name_all", jsonObj.get("subj_name_all"));
		    		
					} else {
						
						err_sb.append("科目全称为空  ");
						
					}
					/*if (StringTool.isNotBlank(jsonObj.get("subj_nature"))) {
						
						incomeBudgSubj.setSubj_nature(String.valueOf(jsonObj.get("subj_nature")));					
						mapVo.put("subj_nature", jsonObj.get("subj_nature"));
		    		
					} else {
						
						err_sb.append("科目性质为空  ");
						
					}*/
					if (StringTool.isNotBlank(jsonObj.get("is_last"))) {
						
						incomeBudgSubj.setIs_last(Integer.valueOf(String.valueOf(jsonObj.get("is_last"))));
											
						mapVo.put("is_last", jsonObj.get("is_last"));
		    		
					} else {
						
						err_sb.append("是否末级为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("is_caarried"))) {
						
					incomeBudgSubj.setIs_caarried(Integer.parseInt(String.valueOf(jsonObj.get("is_caarried"))));					
		    		mapVo.put("is_carried", jsonObj.get("is_carried"));
		    		
					} else {
						
						err_sb.append("是否结转为空  ");
						
					}
				String rules= new String();
				try {
					rules = getRules(mapVo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String subj_code = (String)mapVo.get("subj_code");
				String [] ruless  = rules.split("-");
				Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
				    
			    Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
			    
			    int super_num = 0;
			    
			    for(int j = 0; j < ruless.length; j++){
			    	int num = Integer.parseInt(ruless[j].replace(" ", ""));
			    	super_num += num;
			    	maxNumMap.put(super_num, j + 1);
			    	position.put(j + 1, super_num);
			    }
			    
			    if(maxNumMap.containsKey(subj_code.length())){//编码匹配
			    	 int subj_level = maxNumMap.get(subj_code.length());
			    	 mapVo.put("subj_level", subj_level);
			    	 if(subj_level == 1){
			    		 mapVo.put("super_code", "0");
			    	 }else{
			    		 int super_level =  subj_level - 1;//上级级次
			        	 int subPosition = position.get(super_level);//上级级次位置
			        	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
			        	 mapVo.put("super_code", super_code);
			    	 }
			    }else{
			    	err_sb.append("添加失败 不符合编码规则 请重新输入！");
			    }
				BudgIncomeSubj data_exc_extis = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			//	BudgAccRelationship budgRealtionship = budgAccRelationshipService.queryBudgAccRelationshipByCode(mapVo);
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				/*if(budgRealtionship != null){
					err_sb.append("对应会计科目 对应已经存在！ ");
				}*/
				
				if (err_sb.toString().length() > 0) {
					
					incomeBudgSubj.setError_type(err_sb.toString());
					
					list_err.add(incomeBudgSubj);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
			  
					String dataJson = budgIncomeSubjService.addBudgIncomeSubj(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgIncomeSubj data_exc = new BudgIncomeSubj();
			
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
	 * 获取会计科目编码规则
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo) throws Exception {
        
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	    mapVo.put("proj_code", "BUDG_INCOME_SUBJ");
	    
	    mapVo.put("mod_code", "02");
	        
	    Rules rules = rulesService.queryRulesByCode(mapVo);
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 1; i <= 10; i++){
	    	Method m = (Method) rules.getClass().getMethod(
					"get" + ("Level" + i));
	    	Object obj = m.invoke(rules,new Object[] {});
	    	if(obj.equals("0")){
	    		break;
	    	}
	    	if(i == 10){
	    		sb.append(obj);
	    	}else{
	    		sb.append(obj+"-");
	    	}
			
	    }
		return sb.toString();
	}
	/**
	 * 获取上级编码 、科目级次
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/getSuperCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSuperCode(@RequestParam Map<String, Object> mapVo, Model mode){
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptJson = budgIncomeSubjService.getSuperCode(mapVo);
		return JSONObject.parseObject(deptJson);
	}
	/**
	 * 继承
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgincomesubj/extendBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    mapVo.put("group_id", SessionManager.getGroupId());
	    mapVo.put("hos_id", SessionManager.getHosId());
  	    mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String incomeBudgSubj = budgIncomeSubjService.extendBudgIncomeSubj(getPage(mapVo));

		return JSONObject.parseObject(incomeBudgSubj);
		
	}
}


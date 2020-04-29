/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.commonbuilder;
import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccEcoType;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccEcoTypeServiceImpl;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;

/**
* @Title. @Description.
* 支出经济分类
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccEcoTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccEcoTypeController.class);
	
	
	@Resource(name = "accEcoTypeService")
	private final AccEcoTypeServiceImpl accEcoTypeService = null;
	
	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;
   
    
	/**
	*支出经济分类<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accecotype/accEcoTypeMainPage", method = RequestMethod.GET)
	public String accEcoTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/accecotype/accEcoTypeMain";

	}
	/**
	*支出经济分类<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accecotype/accEcoTypeAddPage", method = RequestMethod.GET)
	public String accEcoTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/accecotype/accEcoTypeAdd";

	}
	
	/**
	*支出经济分类<BR>
	*继承数据页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accecotype/accEcoTypeExtendPage", method = RequestMethod.GET)
	public String accEcoTypeExtendPage(Model mode) throws Exception {

		return "hrp/acc/accecotype/accEcoTypeExtend";

	}
	
	/**
	*支出经济分类<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accecotype/addAccEcoType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccEcoType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("eco_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("eco_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        String resultJson = "";
        
        String rules = getRules(mapVo);//编码规则4-2-2....
        
        String eco_code = (String)mapVo.get("eco_code");//科目编码
        
        String [] ruless  = rules.split("-");
        
        Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
        
        Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
        int super_num = 0;
        
        for(int i = 0; i < ruless.length; i++){
        	int num = Integer.parseInt(ruless[i].replace(" ", ""));
        	super_num += num;
        	maxNumMap.put(super_num, i + 1);
        	position.put(i + 1, super_num);
        }
        
        if(maxNumMap.containsKey(eco_code.length())){//编码匹配
        	 int eco_level = maxNumMap.get(eco_code.length());
        	 mapVo.put("eco_level", eco_level);
        	 
        	 if(eco_level==1){
        		 
        		 mapVo.put("super_code", "top");
        		 
        	 }else{
        		 
        		 int super_level =  eco_level - 1;//上级级次
            	 int subPosition = position.get(super_level);//上级级次位置
            	 String super_code = eco_code.substring(0,subPosition);//截取上级编码
            	 mapVo.put("super_code", super_code);
            	 
            	 List<Map<String, Object>> superCode = accEcoTypeService.qureySurp_code(mapVo);
            	 
            	 if(superCode.size() == 0){
            		 
            		 resultJson = "{\"error\":\"输入支出分类的上级分类编码不存在，不允许添加，请添加上级支出分类后再操作！\"}";
            		 return JSONObject.parseObject(resultJson);
            	 }
            	 
        	 }
        	 
        }else{
        	resultJson =  "{\"error\":\"添加失败 不符合编码规则 请重新输入！\"}";
        	return JSONObject.parseObject(resultJson);
        }
        
        resultJson = accEcoTypeService.addAccEcoType(mapVo);

		return JSONObject.parseObject(resultJson);
		
	}
	
	public String getRules(Map<String, Object> mapVo)throws Exception{
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	    mapVo.put("proj_code", "ACC_ECO_TYPE");
	    
	    mapVo.put("mod_code", "01");
	        
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
	*支出经济分类<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accecotype/queryAccEcoType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccEcoType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
	        mapVo.put("hos_id", SessionManager.getHosId());
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        mapVo.put("acc_year", SessionManager.getAcctYear());
		String accEcoType = accEcoTypeService.queryAccEcoType(getPage(mapVo));

		return JSONObject.parseObject(accEcoType);
		
	}
	/**
	*支出经济分类<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accecotype/deleteAccEcoType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccEcoType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("eco_id", id.split("@")[0]);//实际实体类变量
			mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
			mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
			mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            mapVo.put("acc_year", id.split("@")[4]);//实际实体类变量
            mapVo.put("eco_code", id.split("@")[5]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accEcoTypeJson = accEcoTypeService.deleteBatchAccEcoType(listVo);
	   return JSONObject.parseObject(accEcoTypeJson);
			
	}
	
	/**
	*支出经济分类<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accecotype/accEcoTypeUpdatePage", method = RequestMethod.GET)
	
	public String accEcoTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccEcoType accEcoType = new AccEcoType();
		accEcoType = accEcoTypeService.queryAccEcoTypeByCode(mapVo);
		mode.addAttribute("eco_id", accEcoType.getEco_id());
		mode.addAttribute("group_id", accEcoType.getGroup_id());
		mode.addAttribute("hos_id", accEcoType.getHos_id());
		mode.addAttribute("copy_code", accEcoType.getCopy_code());
		mode.addAttribute("acc_year", accEcoType.getAcc_year());
		mode.addAttribute("eco_code", accEcoType.getEco_code());
		mode.addAttribute("eco_name", accEcoType.getEco_name());
		mode.addAttribute("super_code", accEcoType.getSuper_code());
		mode.addAttribute("eco_level", accEcoType.getEco_level());
		mode.addAttribute("is_last", accEcoType.getIs_last());
		mode.addAttribute("spell_code", accEcoType.getSpell_code());
		mode.addAttribute("wbx_code", accEcoType.getWbx_code());
		mode.addAttribute("is_stop", accEcoType.getIs_stop());
		mode.addAttribute("note", accEcoType.getNote());
		return "hrp/acc/accecotype/accEcoTypeUpdate";
	}
	/**
	*支出经济分类<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accecotype/updateAccEcoType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccEcoType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
	   
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
	   
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("eco_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("eco_name").toString()));
	   
	   
		String accEcoTypeJson = accEcoTypeService.updateAccEcoType(mapVo);
		
		return JSONObject.parseObject(accEcoTypeJson);
	}
	/**
	*支出经济分类<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accecotype/importAccEcoType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccEcoType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accEcoTypeJson = accEcoTypeService.importAccEcoType(mapVo);
		
		return JSONObject.parseObject(accEcoTypeJson);
	}
	
	/**
	*支出经济分类<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accecotype/ExtendAccEcoType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ExtendAccEcoType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());

		String accEcoTypeJson = accEcoTypeService.extendAccEcoType(mapVo);

		return JSONObject.parseObject(accEcoTypeJson);
		
	}

}


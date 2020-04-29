/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.commonbuilder;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







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
import com.chd.hrp.acc.entity.AccFunType;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccFunTypeServiceImpl;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;

/**
* @Title. @Description.
* 支出功能分类
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccFunTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccFunTypeController.class);
	
	
	@Resource(name = "accFunTypeService")
	private final AccFunTypeServiceImpl accFunTypeService = null;
	
	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;
   
    
	/**
	*支出功能分类<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accfuntype/accFunTypeMainPage", method = RequestMethod.GET)
	public String accFunTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/accfuntype/accFunTypeMain";

	}
	/**
	*支出功能分类<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accfuntype/accFunTypeAddPage", method = RequestMethod.GET)
	public String accFunTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/accfuntype/accFunTypeAdd";

	}
	
	/**
	*支出功能分类<BR>
	*继承数据页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accfuntype/accFunTypeExtendPage", method = RequestMethod.GET)
	public String accFunTypeExtendPage(Model mode) throws Exception {

		return "hrp/acc/accfuntype/accFunTypeExtend";

	}
	
	/**
	*支出功能分类<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accfuntype/addAccFunType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fun_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fun_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        String resultJson = "";
        
        String rules = getRules(mapVo);//编码规则4-2-2....
        
        String fun_code = (String)mapVo.get("fun_code");//科目编码
        
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
        
        if(maxNumMap.containsKey(fun_code.length())){//编码匹配
        	 int fun_level = maxNumMap.get(fun_code.length());
        	 mapVo.put("fun_level", fun_level);
        	 
        	 if(fun_level==1){
        		 
        		 mapVo.put("super_code", "top");
        		 
        	 }else{
        		 
        		 int super_level =  fun_level - 1;//上级级次
            	 int subPosition = position.get(super_level);//上级级次位置
            	 String super_code = fun_code.substring(0,subPosition);//截取上级编码
            	 mapVo.put("super_code", super_code);
            	 
            	 List<Map<String, Object>> superCode = accFunTypeService.qureySurp_code(mapVo);
            	 
            	 if(superCode.size() == 0){
            		 
            		 resultJson = "{\"error\":\"输入支出分类的上级分类编码不存在，不允许添加，请添加上级支出分类后再操作！\"}";
            		 return JSONObject.parseObject(resultJson);
            	 }
            	 
        	 }
        }else{
        	resultJson =  "{\"error\":\"添加失败 不符合编码规则 请重新输入！\"}";
        	return JSONObject.parseObject(resultJson);
        }

       resultJson = accFunTypeService.addAccFunType(mapVo);

		return JSONObject.parseObject(resultJson);
		
	}
	
	public String getRules(Map<String, Object> mapVo)throws Exception{
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	    mapVo.put("proj_code", "ACC_FUN_TYPE");
	    
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
	*支出功能分类<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accfuntype/queryAccFunType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
	        mapVo.put("hos_id", SessionManager.getHosId());
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        mapVo.put("acc_year", SessionManager.getAcctYear());
		String accFunType = accFunTypeService.queryAccFunType(getPage(mapVo));

		return JSONObject.parseObject(accFunType);
		
	}
	/**
	*支出功能分类<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accfuntype/deleteAccFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccFunType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("fun_id", id.split("@")[0]);//实际实体类变量
            mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
            mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            mapVo.put("acc_year", id.split("@")[4]);//实际实体类变量
            mapVo.put("fun_code", id.split("@")[5]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accFunTypeJson = accFunTypeService.deleteBatchAccFunType(listVo);
	   return JSONObject.parseObject(accFunTypeJson);
			
	}
	
	/**
	*支出功能分类<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accfuntype/accFunTypeUpdatePage", method = RequestMethod.GET)
	
	public String accFunTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccFunType accFunType = new AccFunType();
		accFunType = accFunTypeService.queryAccFunTypeByCode(mapVo);
		mode.addAttribute("fun_id", accFunType.getFun_id());
		mode.addAttribute("group_id", accFunType.getGroup_id());
		mode.addAttribute("hos_id", accFunType.getHos_id());
		mode.addAttribute("copy_code", accFunType.getCopy_code());
		mode.addAttribute("acc_year", accFunType.getAcc_year());
		mode.addAttribute("fun_code", accFunType.getFun_code());
		mode.addAttribute("fun_name", accFunType.getFun_name());
		mode.addAttribute("super_code", accFunType.getSuper_code());
		mode.addAttribute("fun_level", accFunType.getFun_level());
		mode.addAttribute("is_last", accFunType.getIs_last());
		mode.addAttribute("spell_code", accFunType.getSpell_code());
		mode.addAttribute("wbx_code", accFunType.getWbx_code());
		mode.addAttribute("is_stop", accFunType.getIs_stop());
		mode.addAttribute("note", accFunType.getNote());
		return "hrp/acc/accfuntype/accFunTypeUpdate";
	}
	/**
	*支出功能分类<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accfuntype/updateAccFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fun_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fun_name").toString()));
	   
	   
		String accFunTypeJson = accFunTypeService.updateAccFunType(mapVo);
		
		return JSONObject.parseObject(accFunTypeJson);
	}
	/**
	*支出功能分类<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accfuntype/importAccFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accFunTypeJson = accFunTypeService.importAccFunType(mapVo);
		
		return JSONObject.parseObject(accFunTypeJson);
	}
	

	@RequestMapping(value = "/hrp/acc/accfuntype/extendAccFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());

		String accFunTypeJson = accFunTypeService.extendAccFunType(mapVo);

		return JSONObject.parseObject(accFunTypeJson);
	}
}


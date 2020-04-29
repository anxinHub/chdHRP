/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.autovouch;

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
import com.chd.base.exception.SysException;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccYearMonthMy97;
import com.chd.hrp.acc.entity.autovouch.AccBusiMap;
import com.chd.hrp.acc.entity.autovouch.AccBusiMeta;
import com.chd.hrp.acc.entity.autovouch.AccBusiTemplate;
import com.chd.hrp.acc.entity.autovouch.SysBusiTable;
import com.chd.hrp.acc.service.AccYearMonthService;
import com.chd.hrp.acc.service.autovouch.AccBusiTemplateService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccBusiTemplateController extends BaseController {

	private static Logger logger = Logger.getLogger(AccBusiTemplateController.class);

	@Resource(name = "accBusiTemplateService")
	private final AccBusiTemplateService accBusiTemplateService = null;
	
	@Resource(name = "accYearMonthService")
	private final AccYearMonthService accYearMonthService = null;
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/accBusiTemplateMainPage", method = RequestMethod.GET)
	public String accBusiTemplateMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("flag","acc_flag");//财务结账标识
        
		 //查询年月会计期间
        AccYearMonthMy97 accYearMonthMy97=new AccYearMonthMy97();
        accYearMonthMy97=accYearMonthService.queryYearMonthByMy97(mapVo);
        
        mode.addAttribute("minDate", accYearMonthMy97.getMinDate());
        mode.addAttribute("maxDate", accYearMonthMy97.getMaxDate());
        mode.addAttribute("curDate", accYearMonthMy97.getCurDate());
		return "hrp/acc/autovouch/accbusitemplate/accBusiTemplateMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accBusiTemplate = accBusiTemplateService.queryAccBusiTemplate(getPage(mapVo));

		return JSONObject.parseObject(accBusiTemplate);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiTypeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());


		String accBusiTemplate = accBusiTemplateService.queryAccBusiTypeTree(mapVo);

		return JSONObject.parseObject(accBusiTemplate);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/addAccBusiTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccBusiTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try{
			String accBusiTemplateJson = accBusiTemplateService.addAccBusiTemplate(mapVo);
			return JSONObject.parseObject(accBusiTemplateJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}


	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/delAccBusiTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAccBusiTemplate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("acc_year", ids[3]);

			mapVo.put("mod_code", ids[4]);

			mapVo.put("busi_type_code", ids[5]);

			mapVo.put("template_code", ids[6]);
			

			listVo.add(mapVo);

		}

		try{
			String prmGoalJson = accBusiTemplateService.delBatchAccBusiTemplate(listVo);
			return JSONObject.parseObject(prmGoalJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		


	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForMetaCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiRelaForMetaCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accBusiTemplate = accBusiTemplateService.queryAccBusiRelaForMetaCode(getPage(mapVo));

		return JSONObject.parseObject(accBusiTemplate);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiHosStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiHosStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accBusiTemplate = accBusiTemplateService.queryAccBusiHosStore(getPage(mapVo));

		return JSONObject.parseObject(accBusiTemplate);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiHosResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiHosResource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accBusiTemplate = accBusiTemplateService.queryAccBusiHosResource(getPage(mapVo));

		return JSONObject.parseObject(accBusiTemplate);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiTemplateDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBusiTemplateDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		

		String accBusiTemplateDetail = accBusiTemplateService.queryAccBusiTemplateDetail(getPage(mapVo));

		JSONObject json = JSONObject.parseObject(accBusiTemplateDetail);
		
		AccBusiTemplate abt = accBusiTemplateService.queryAccBusiTemplateByCode(mapVo);


		json.put("template_code", abt.getTemplate_code());

		json.put("template_name",abt.getTemplate_name());

		json.put("vouch_type_code" ,abt.getVouch_type_code());

		json.put("vouch_type_name" ,abt.getVouch_type_name());

		json.put("summary" ,abt.getSummary());

		json.put("is_detail_summary" ,abt.getIs_detail_summary());
		

		return json.toString();

	}

	//--------------------------设置模板开始---------------------------------
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForAccSubjSetPage", method = RequestMethod.GET)
	public String queryAccBusiRelaForAccSubjPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		mode.addAttribute("meta_code", mapVo.get("meta_code"));
		
		mode.addAttribute("table_id", mapVo.get("table_name"));
		
		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		
		mode.addAttribute("type_id", mapVo.get("type_id"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		mode.addAttribute("data",mapVo.get("data"));
		AccBusiMap accbm = accBusiTemplateService.queryAccBusiMapByCode(mapVo);
		
		if(accbm !=null){
			
			mode.addAttribute("subj_code", accbm.getSubj_code());
			
		}else{
			mode.addAttribute("subj_code", "");
		}

		return "hrp/acc/autovouch/accbusitemplate/accBusiRelaForAccSubjMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForAccSubjAutoPage", method = RequestMethod.GET)
	public String queryAccBusiRelaForAccSubjAutoPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("meta_code", mapVo.get("meta_code"));

		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		
		mode.addAttribute("table_id", mapVo.get("type_table"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("is_store", mapVo.get("is_store"));
		
		mapVo.put("table_id", mapVo.get("type_table"));
		
		SysBusiTable stb = accBusiTemplateService.querySysBusiTableByCode(mapVo);
		
		if(stb.getLevel_field() != null && !"".equals(stb.getLevel_field())){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("select ");

			sb.append(" max("+stb.getLevel_field()+")");

			sb.append(" from "+stb.getTable_id()) ;
			
			if(stb.getTable_level() == 1){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
			}else if(stb.getTable_level() == 2){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
			}else if(stb.getTable_level() == 3){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
				sb.append("and  copy_code ='"+SessionManager.getCopyCode()+"'");
				
			}else if(stb.getTable_level() == 4){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
				sb.append("and  copy_code ='"+SessionManager.getCopyCode()+"'");
				
				sb.append("and "+stb.getYear_field()+" = '"+mapVo.get("acc_year")+"'");
				
			}
			
			sb.append(" order by "+stb.getId_field());
			
			mapVo.put("max_sql", sb.toString());
			
			Integer inte = accBusiTemplateService.maxTypeLevel(mapVo);
			
			mode.addAttribute("level_field", inte);
			
		}else{
			
			mode.addAttribute("level_field", "");
			
		}

		

		return "hrp/acc/autovouch/accbusitemplate/accBusiRelaForAutoSetMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryBudgBusiRelaForAccSubjSetPage", method = RequestMethod.GET)
	public String queryBudgBusiRelaForAccSubjSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		mode.addAttribute("meta_code", mapVo.get("meta_code"));
		
		mode.addAttribute("table_id", mapVo.get("table_name"));
		
		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		
		mode.addAttribute("type_id", mapVo.get("type_id"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		AccBusiMap accbm = accBusiTemplateService.queryAccBusiMapByCode(mapVo);
		
		if(accbm !=null){
			
			mode.addAttribute("subj_code", accbm.getSubj_code());
			
		}else{
			mode.addAttribute("subj_code", "");
		}

		return "hrp/acc/autovouch/accbusitemplate/budgBusiRelaForAccSubjMain";
	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryBudgBusiRelaForAccSubjAutoPage", method = RequestMethod.GET)
	public String queryBudgBusiRelaForAccSubjAutoPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("meta_code", mapVo.get("meta_code"));

		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		
		mode.addAttribute("table_id", mapVo.get("type_table"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("is_store", mapVo.get("is_store"));
		
		mapVo.put("table_id", mapVo.get("type_table"));
		
		SysBusiTable stb = accBusiTemplateService.querySysBusiTableByCode(mapVo);
		
		if(stb.getLevel_field() != null && !"".equals(stb.getLevel_field())){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("select ");

			sb.append(" max("+stb.getLevel_field()+")");

			sb.append(" from "+stb.getTable_id()) ;
			
			if(stb.getTable_level() == 1){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
			}else if(stb.getTable_level() == 2){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
			}else if(stb.getTable_level() == 3){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
				sb.append("and  copy_code ='"+SessionManager.getCopyCode()+"'");
				
			}else if(stb.getTable_level() == 4){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
				sb.append("and  copy_code ='"+SessionManager.getCopyCode()+"'");
				
				sb.append("and "+stb.getYear_field()+" = '"+mapVo.get("acc_year")+"'");
				
			}
			
			sb.append(" order by "+stb.getId_field());
			
			mapVo.put("max_sql", sb.toString());
			
			Integer inte = accBusiTemplateService.maxTypeLevel(mapVo);
			
			mode.addAttribute("level_field", inte);
			
		}else{
			
			mode.addAttribute("level_field", "");
			
		}

		

		return "hrp/acc/autovouch/accbusitemplate/budgBusiRelaForAutoSetMain";
	}
	
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForAutoSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiRelaForAutoSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		SysBusiTable stb = accBusiTemplateService.querySysBusiTableByCode(mapVo);

		StringBuffer sb = new StringBuffer();
		
		sb.append("select ");

		sb.append(" a."+stb.getId_field()+" as  id_field,");
		
		if(stb.getNo_field() !=null && !"".equals(stb.getNo_field())){
			
			sb.append(" a."+stb.getNo_field()+",");
			
		}
		
		sb.append(" a."+stb.getCode_field()+" as  code_field,");

		sb.append(" a."+stb.getName_field()+" as  name_field,");
		
		sb.append(" s.subj_code as  subj_code, ");
		
		if("1".equals(mapVo.get("is_store")))
		{
		sb.append(" m.store_id as  store_id, ");		
		}
		
		sb.append(" s.subj_name_all as  subj_name ");

		sb.append(" from "+stb.getTable_id() +" a ") ;
		
		//拼装科目对应关系表
		sb.append(" left join acc_busi_map m on m.sub_type_id=to_char(a."+stb.getId_field()+") and m.group_id="+SessionManager.getGroupId()+" and m.hos_id="+SessionManager.getHosId()+" ");
		sb.append(" and m.copy_code='"+SessionManager.getCopyCode()+"' and m.acc_year='"+mapVo.get("acc_year")+"' and m.meta_code='"+mapVo.get("meta_code").toString()+"' ");
		sb.append(" left join acc_subj s on s.subj_code=m.subj_code and s.group_id="+SessionManager.getGroupId()+" and s.hos_id="+SessionManager.getHosId()+" ");
		sb.append(" and s.copy_code='"+SessionManager.getCopyCode()+"' and s.acc_year='"+mapVo.get("acc_year")+"' ");
		
		if(stb.getTable_level() == 1){
			
			sb.append(" where ");
			
			sb.append("a.group_id ='"+SessionManager.getGroupId()+"'");
					
			if(stb.getWhere_sql() !=null && !"".equals(stb.getWhere_sql())){
				
				sb.append("and  a."+stb.getWhere_sql()+"");
				
			}
			
		}else if(stb.getTable_level() == 2){
			
			sb.append(" where ");
			
			sb.append("a.group_id ='"+SessionManager.getGroupId()+"'");
			
			sb.append("and  a.hos_id ='"+SessionManager.getHosId()+"'");
			
            if(stb.getWhere_sql() !=null && !"".equals(stb.getWhere_sql())){
				
				sb.append("and  a."+stb.getWhere_sql()+"");
				
			}
			
		}else if(stb.getTable_level() == 3){
			
			sb.append(" where ");
			
			sb.append("a.group_id ='"+SessionManager.getGroupId()+"'");
			
			sb.append("and  a.hos_id ='"+SessionManager.getHosId()+"'");
			
			sb.append("and  a.copy_code ='"+SessionManager.getCopyCode()+"'");
			
            if(stb.getWhere_sql() !=null && !"".equals(stb.getWhere_sql())){
				
				sb.append("and  a."+stb.getWhere_sql()+"");
				
			}
			
		}else if(stb.getTable_level() == 4){
			
			sb.append(" where ");
			
			sb.append("a.group_id ='"+SessionManager.getGroupId()+"'");
			
			sb.append("and  a.hos_id ='"+SessionManager.getHosId()+"'");
			
			sb.append("and  a.copy_code ='"+SessionManager.getCopyCode()+"'");
			
			sb.append("and a."+stb.getYear_field()+" = '"+mapVo.get("acc_year")+"'");
			
            if(stb.getWhere_sql() !=null && !"".equals(stb.getWhere_sql())){
				
				sb.append("and  a."+stb.getWhere_sql()+"");
				
			}
			
		}
		//字段模糊搜索
		String code_field = String.valueOf(mapVo.get("code_field"));
		
		String level_field = String.valueOf(mapVo.get("level_field"));
		
		String is_last = String.valueOf(mapVo.get("is_last"));
		
		String is_set = String.valueOf(mapVo.get("is_set"));
		
		if(stb.getTable_level() > 0){
			
			if(code_field!=null && !"".equals(code_field) && !"null".equals(code_field)){
				
				sb.append("and (a."+stb.getCode_field()+" like '"+code_field+"%' or a."+stb.getName_field()+" like '"+code_field+"%')");
				
			}
			
			if(level_field!=null && !"".equals(level_field) && !"null".equals(level_field)){
				
				sb.append("and a."+stb.getLevel_field()+" = '"+level_field+"'");
				
			} 
			
           if( "1".equals(is_last)){
				
				sb.append("and a.is_last = '"+is_last+"'");
				
			}
           
           if( "1".equals(is_set)){
				
        	  
				sb.append("and a.is_last = 1");
				
				sb.append("and a."+stb.getId_field()+" not in ( select sub_type_id from  acc_busi_map where  group_id="+SessionManager.getGroupId()+" and hos_id="+SessionManager.getHosId()+" ");
				
				sb.append(" and copy_code='"+SessionManager.getCopyCode()+"' and acc_year='"+mapVo.get("acc_year")+"' ");
				
				sb.append(" and meta_code='"+mapVo.get("meta_code")+"' )");
			}
			
			
			
		}else{
			
			sb.append(" where ");
			
			if(code_field!=null && !"".equals(code_field) && !"null".equals(code_field)){
				
				sb.append("and (a."+stb.getCode_field()+" like '"+code_field+"%' or a."+stb.getName_field()+" like '%"+code_field+"%')");
				
			}
			
			if(level_field!=null && !"".equals(level_field) && !"null".equals(level_field)){
				
				sb.append("and a."+stb.getLevel_field()+" = '"+level_field+"'");
				
			}
			
		}

		
		sb.append(" order by a."+stb.getCode_field());
		
		mapVo.put("select_sql", sb.toString());
		
		String str = accBusiTemplateService.querySelectSql(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiRelaForAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		AccBusiMeta abm = accBusiTemplateService.queryAccBusiMetaByCode(mapVo);

		mapVo.put("where_sql", abm.getWhere_sql());

		String accBusiTemplate = accBusiTemplateService.queryAccBusiRelaForAccSubj(getPage(mapVo));

		return JSONObject.parseObject(accBusiTemplate);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/saveAccBusiMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBusiMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		
		mapVo.put("copy_code", SessionManager.getCopyCode());

		
		String accBusiMap="";
		try {

			accBusiMap = accBusiTemplateService.saveAccBusiMap(mapVo);

		} catch (Exception e) {
			accBusiMap="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		
		return JSONObject.parseObject(accBusiMap);

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/deleteAutoSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAutoSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		
		mapVo.put("copy_code", SessionManager.getCopyCode());

		
		String accBusiMap="";
		try {

			accBusiMap = accBusiTemplateService.delAccBusiMap(mapVo);

		} catch (Exception e) {
			accBusiMap="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		
		return JSONObject.parseObject(accBusiMap);

	}


	
	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/accBusiRelaForAccStoreSubjAutoPage", method = RequestMethod.GET)
	public String accBusiRelaForAccStoreSubjAutoPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mode.addAttribute("meta_code", mapVo.get("meta_code"));
		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		mode.addAttribute("table_id", mapVo.get("type_table"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("is_store", mapVo.get("is_store"));
		
		mapVo.put("table_id", mapVo.get("type_table"));
		
		SysBusiTable stb = accBusiTemplateService.querySysBusiTableByCode(mapVo);
		
		if(stb.getLevel_field() != null && !"".equals(stb.getLevel_field())){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("select ");

			sb.append(" max("+stb.getLevel_field()+")");

			sb.append(" from "+stb.getTable_id()) ;
			
			if(stb.getTable_level() == 1){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
			}else if(stb.getTable_level() == 2){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
			}else if(stb.getTable_level() == 3){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
				sb.append("and  copy_code ='"+SessionManager.getCopyCode()+"'");
				
			}else if(stb.getTable_level() == 4){
				
				sb.append(" where ");
				
				sb.append("group_id ='"+SessionManager.getGroupId()+"'");
				
				sb.append("and  hos_id ='"+SessionManager.getHosId()+"'");
				
				sb.append("and  copy_code ='"+SessionManager.getCopyCode()+"'");
				
				sb.append("and "+stb.getYear_field()+" = '"+mapVo.get("acc_year")+"'");
				
			}
			
			sb.append(" order by "+stb.getId_field());
			
			mapVo.put("max_sql", sb.toString());
			
			Integer inte = accBusiTemplateService.maxTypeLevel(mapVo);
			
			mode.addAttribute("level_field", inte);
			
		}else{
			
			mode.addAttribute("level_field", "");
			
		}


		return "hrp/acc/autovouch/accbusitemplate/accBusiRelaForStoreAutoSet";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForStoreAutoSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiRelaForStoreAutoSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accBusiTemplate = accBusiTemplateService.queryAccBusiRelaForStoreAutoSet(mapVo);

		return JSONObject.parseObject(accBusiTemplate);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/queryAccSubjForAutoSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjForAutoSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accBusiTemplate = accBusiTemplateService.queryAccSubjForAutoSet(mapVo);

		return JSONObject.parseObject(accBusiTemplate);
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/saveAccBusiMapByStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBusiMapByStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		try {
			retMap = accBusiTemplateService.saveAccBusiMapByStore(mapVo);
		} catch (Exception e) {
			retMap.put("error", "操作失败！");
			retMap.put("state", "false");
		}

		return retMap;
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitemplate/saveAccBusiMapByStoreBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBusiMapByStoreBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		try {
			retMap = accBusiTemplateService.saveAccBusiMapByStoreBatch(mapVo);
		} catch (Exception e) {
			retMap.put("error", "操作失败！");
			retMap.put("state", "false");
		}

		return retMap;
	}
}

package com.chd.hrp.sys.controller;

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
import com.chd.hrp.sys.service.PermDataService;

@Controller
public class PermDataController  extends BaseController{  
	private static Logger logger = Logger.getLogger(PermDataController.class);
	
	@Resource(name = "permDataService")
	private final PermDataService permDataService = null;
	
	@RequestMapping(value = "/hrp/sys/permdata/permDataMainPage", method = RequestMethod.GET)
	public String permDataMainPage(Model mode) throws Exception {

		return "hrp/sys/permdata/permDataMain";
	}
	
	@RequestMapping(value = "/hrp/sys/permdata/userMainPage", method = RequestMethod.GET)
	public String accUserMainPage(Model mode) throws Exception {

		return "hrp/sys/permdata/user";
	}
	
	@RequestMapping(value = "/hrp/sys/permdata/roleMainPage", method = RequestMethod.GET)
	public String roleMainPage(Model mode) throws Exception {

		return "hrp/sys/permdata/role";
	}
	
	@RequestMapping(value = "/hrp/sys/permdata/batchAddPermPage", method = RequestMethod.GET)
	public String accBatchAddPermPage(Model mode) throws Exception {

		return "hrp/sys/permdata/batchAddPerm";
	}
	
	@RequestMapping(value = "/hrp/sys/permdata/roleUserMainPage", method = RequestMethod.GET)
	public String roleUserMainPage(Model mode,String role_id) throws Exception {
		mode.addAttribute("role_id", role_id);
		return "hrp/sys/permdata/roleUser";
	}
	
	@RequestMapping(value = "/hrp/sys/permdata/queryPermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPermData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null || mapVo.get("hos_id").equals("")){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		StringBuffer sql = new StringBuffer();
		String obj_code = mapVo.get("obj_code").toString();
		String [] obj = obj_code.split("\\.");
		String TABLE_CODE = obj[0];
		String MOD_CODE = obj[1];
		String COLUMN_ID = obj[2];
		String COLUMN_CODE = obj[3];
		String COLUMN_NAME = obj[4];
		String IS_GROUP = obj[5];
		String IS_HOS = obj[6];
		String IS_COPY = obj[7];
		String IS_ACC_YEAR = obj[8];
		String [] wsql = obj_code.split("\\*\\*\\*\\*");
		String WHERE_SQL=null;
		if(wsql.length>1){
			WHERE_SQL = wsql[1];
		}
		
		//2019年4月22日 如果选择人员考勤类别的话走下面的sql(桐庐)
		if("HOS_EMP_YH".equals(TABLE_CODE)){
			
			sql.append("select a."+COLUMN_ID+" obj_id,a."+COLUMN_CODE+" obj_code,a."+COLUMN_NAME+" obj_name,nvl(b.is_read,0) is_read,nvl(b.is_write,0) is_write from hr_fiied_data a");
		}else{
			sql.append("select a."+COLUMN_ID+" obj_id,a."+COLUMN_CODE+" obj_code,a."+COLUMN_NAME+" obj_name,nvl(b.is_read,0) is_read,nvl(b.is_write,0) is_write from "+TABLE_CODE+" a");
		}
			
		if(mapVo.get("tip").toString().equals("1")){
			sql.append(" left join SYS_USER_PERM_DATA b on a."+COLUMN_ID+" = b.perm_code ");
			if(IS_GROUP.equals("1")){
				sql.append(" and a.group_id = b.group_id " );
			}
			if(IS_HOS.equals("1")){
				sql.append(" and a.hos_id = b.hos_id ");
			}
			if(IS_COPY.equals("1")){
				if(!"HR_TAB_STRUC".equals(TABLE_CODE)) {
					sql.append(" and a.copy_code = b.copy_code ");
				}
			}
			if(IS_ACC_YEAR.equals("1")){
				sql.append(" and a.acc_year = b.acc_year");
			}
			sql.append(" and b.user_id = "+mapVo.get("user_id").toString());
			sql.append(" and b.table_code = '"+ TABLE_CODE+"'");
		}else{
			sql.append(" left join SYS_ROLE_PERM_DATA b on a."+COLUMN_ID+" = b.perm_code ");
			if(IS_GROUP.equals("1")){
				sql.append(" and a.group_id = b.group_id " );
			}
			if(IS_HOS.equals("1")){
				sql.append(" and a.hos_id = b.hos_id ");
			}
			if(IS_COPY.equals("1")){
				if(!"HR_TAB_STRUC".equals(TABLE_CODE)) {
				sql.append(" and a.copy_code = b.copy_code ");
				}
			}
			if(IS_ACC_YEAR.equals("1")){
				sql.append(" and a.acc_year = b.acc_year");
			}
			sql.append(" and b.role_id = "+mapVo.get("role_id").toString());
			sql.append(" and b.table_code = '"+ TABLE_CODE+"'");
		}
		
		sql.append(" where 1 = 1");
		
		//2017年8月11日 添加职工 部门的拼音码五笔码检索
		if("HOS_DEPT_DICT".equals(TABLE_CODE)||"HOS_EMP_DICT".equals(TABLE_CODE)){
			if(mapVo.get("code").toString() != null && !"".equals(mapVo.get("code").toString())){
				sql.append(" and (a."+COLUMN_CODE+" like '%"+mapVo.get("code").toString()+"%'"
						+ " or a.SPELL_CODE  like '%"+mapVo.get("code").toString().toUpperCase()+"%'"
						+ " or a.WBX_CODE  like '%"+mapVo.get("code").toString().toUpperCase()+"%')");
			}
			if(mapVo.get("name").toString() != null && !"".equals(mapVo.get("name").toString())){
				sql.append(" and (a."+COLUMN_NAME+" like '%"+mapVo.get("name").toString()+"%'"
						+ " or a.SPELL_CODE  like '%"+mapVo.get("name").toString().toUpperCase()+"%'"
						+ "or a.WBX_CODE  like '%"+mapVo.get("name").toString().toUpperCase()+"%')");
			}
		}else{
			if(mapVo.get("code").toString() != null && !"".equals(mapVo.get("code").toString())){
				sql.append(" and a."+COLUMN_CODE+" like '%"+mapVo.get("code").toString()+"%'");
			}
			if(mapVo.get("name").toString() != null && !"".equals(mapVo.get("name").toString())){
				sql.append(" and a."+COLUMN_NAME+" like '%"+mapVo.get("name").toString()+"%'");
			}
		}
		
		//2019年4月22日 如果选择人员考勤类别的话走下面的sql(桐庐)
		if("HOS_EMP_YH".equals(TABLE_CODE)){
			sql.append(" and a.field_tab_code = '" + TABLE_CODE.toString()+"'");
		}
		
		if(IS_GROUP.equals("1")){
			sql.append(" and a.group_id = " + mapVo.get("group_id").toString());
		}
		if(IS_HOS.equals("1")){
			sql.append(" and a.hos_id = " + mapVo.get("hos_id").toString());
		}
		if(IS_COPY.equals("1")){
			if(!"HR_TAB_STRUC".equals(TABLE_CODE)) {
			sql.append(" and a.copy_code = '" + mapVo.get("copy_code").toString()+"'");
			}
		}
		if(IS_ACC_YEAR.equals("1")){
			sql.append(" and a.acc_year = '" + mapVo.get("acc_year").toString()+"'");
		}
		if(MOD_CODE.equals("00")){
			sql.append(" and a.is_stop = 0 ");
		}//2017/07/18
		//由于不知道为什么只给系统平台(mod_code等于00)加is_stop=0,但是绩效和奖金系统需查询未停用的,所以加上以下两个分支
		else if(MOD_CODE.equals("09") && "APHI_DEPT_DICT".equals(TABLE_CODE)){
			sql.append(" and a.is_stop = 0 ");
		}
		//由于物资类别需要查询的是未停用的 ，并且是物流模块，所以增加了一个判断
		else if(MOD_CODE.equals("04") && "MAT_TYPE_DICT".equals(TABLE_CODE)){
			sql.append(" and a.is_stop = 0 ");
		}
		
		else if( "BUDG_PAYMENT_ITEM_DICT".equals(TABLE_CODE)){
			sql.append(" and a.is_stop = 0 ");
		}
		if("REP_DEFINE".equals(TABLE_CODE)){
			sql.append(" and a.is_perm = 1 ");
		}
		if(WHERE_SQL != null && !"".equals(WHERE_SQL) && (WHERE_SQL.startsWith(" and ") || WHERE_SQL.startsWith(" AND "))){
			sql.append(WHERE_SQL);
		}
		if("REP_DEFINE".equals(TABLE_CODE)){
			sql.append(" order by report_group,obj_code ");
		}else {
			sql.append(" order by obj_code");
		}
		//sql.append(" order by b.perm_code");
		mapVo.put("sql", sql.toString());
		String accLederZcheck = permDataService.queryPermData(getPage(mapVo));  

		return JSONObject.parseObject(accLederZcheck);
	}
	@RequestMapping(value = "/hrp/sys/permdata/queryPermDataGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPermDataGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null || mapVo.get("hos_id").equals("")){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		
		StringBuffer sql = new StringBuffer();
		String obj_code = mapVo.get("obj_code").toString();
		String [] obj = obj_code.split("\\.");
		String TABLE_CODE = obj[0];
		String MOD_CODE = obj[1];
		String COLUMN_ID = obj[2];
		String COLUMN_CODE = obj[3];
		String COLUMN_NAME = obj[4];
		String IS_GROUP = obj[5];
		String IS_HOS = obj[6];
		String IS_COPY = obj[7];
		String IS_ACC_YEAR = obj[8];
		String [] wsql = obj_code.split("\\*\\*\\*\\*");
		String WHERE_SQL = wsql[1];
		
		sql.append("select a."+COLUMN_ID+" obj_id,a."+COLUMN_CODE+" obj_code,a."+COLUMN_NAME+" obj_name,nvl(b.is_read,0) is_read,nvl(b.is_write,0) is_write from "+TABLE_CODE+" a");
		if(mapVo.get("tip").toString().equals("1")){
			sql.append(" left join SYS_USER_PERM_DATA b on a."+COLUMN_ID+" = b.perm_code ");
			if(IS_GROUP.equals("1")){
				sql.append(" and a.group_id = b.group_id " );
			}
			if(IS_HOS.equals("1")){
				sql.append(" and a.hos_id = b.hos_id ");
			}
			if(IS_COPY.equals("1")){
				sql.append(" and a.copy_code = b.copy_code ");
			}
			if(IS_ACC_YEAR.equals("1")){
				sql.append(" and a.acc_year = b.acc_year");
			}
			sql.append(" and b.user_id = "+mapVo.get("user_id").toString());
			sql.append(" and b.table_code = '"+ TABLE_CODE+"'");
		}else{
			sql.append(" left join SYS_ROLE_PERM_DATA b on a."+COLUMN_ID+" = b.perm_code ");
			if(IS_GROUP.equals("1")){
				sql.append(" and a.group_id = b.group_id " );
			}
			if(IS_HOS.equals("1")){
				sql.append(" and a.hos_id = b.hos_id ");
			}
			if(IS_COPY.equals("1")){
				sql.append(" and a.copy_code = b.copy_code ");
			}
			if(IS_ACC_YEAR.equals("1")){
				sql.append(" and a.acc_year = b.acc_year");
			}
			sql.append(" and b.role_id = "+mapVo.get("role_id").toString());
			sql.append(" and b.table_code = '"+ TABLE_CODE+"'");
		}
		
		sql.append(" where 1 = 1");
		if(mapVo.get("code").toString() != null && !"".equals(mapVo.get("code").toString())){
			sql.append(" and a."+COLUMN_CODE+" like '%"+mapVo.get("code").toString()+"%'");
		}
		if(mapVo.get("name").toString() != null && !"".equals(mapVo.get("name").toString())){
			sql.append(" and a."+COLUMN_NAME+" like '%"+mapVo.get("name").toString()+"%'");
		}
		if(IS_GROUP.equals("1")){
			sql.append(" and a.group_id = " + mapVo.get("group_id").toString());
		}
		if(IS_HOS.equals("1")){
			sql.append(" and a.hos_id = " + mapVo.get("hos_id").toString());
		}
		if(IS_COPY.equals("1")){
			sql.append(" and a.copy_code = '" + mapVo.get("copy_code").toString()+"'");
		}
		if(IS_ACC_YEAR.equals("1")){
			sql.append(" and a.acc_year = '" + mapVo.get("acc_year").toString()+"'");
		}
		if(MOD_CODE.equals("00")){
			sql.append(" and a.is_stop = 0 ");
		}//2017/07/18
		//由于不知道为什么只给系统平台(mod_code等于00)加is_stop=0,但是绩效和奖金系统需查询未停用的,所以加上以下两个分支
		else if(MOD_CODE.equals("09") && "APHI_DEPT_DICT".equals(TABLE_CODE)){
			sql.append(" and a.is_stop = 0 ");
		}
		if("REP_DEFINE".equals(TABLE_CODE)){
			sql.append(" and a.is_perm = 1 ");
		}
		if(WHERE_SQL != null && !"".equals(WHERE_SQL) && (WHERE_SQL.startsWith(" and ") || WHERE_SQL.startsWith(" AND "))){
			sql.append(WHERE_SQL);
		}
		if("REP_DEFINE".equals(TABLE_CODE)){
			sql.append(" order by report_group,b.perm_code ");
		}else {
			sql.append(" order by b.perm_code");
		}
		mapVo.put("sql", sql.toString());
		String accLederZcheck = permDataService.queryPermData(getPage(mapVo));
		
		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/sys/permdata/addUserPermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUserPermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> entityMap = new ArrayList<Map<String, Object>>();
		
		for (String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
			
			mapVo.put("user_id", id.split("@")[0]);
			
			mapVo.put("table_code", id.split("@")[1]);
			
			mapVo.put("perm_code", id.split("@")[2]);
			
			mapVo.put("mod_code", id.split("@")[3]);
			
			if(!"0".equals(id.split("@")[6])){mapVo.put("copy_code", id.split("@")[6]);}
			
			else{mapVo.put("copy_code", "0");}
			
			if(!"0".equals(id.split("@")[7])){mapVo.put("acc_year", id.split("@")[7]);}
			
			else{mapVo.put("acc_year", "0");}
			
			if(!"0".equals(id.split("@")[8])){mapVo.put("hos_id", id.split("@")[8]);}
			
			else{mapVo.put("hos_id", SessionManager.getHosId());}
			
			
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			
			entityMap.add(mapVo);
			
			permDataService.deleteUserPermData(mapVo);
		}
		
		String accLederZcheck = permDataService.addUserPermData(entityMap);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/sys/permdata/addRolePermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRolePermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> entityMap = new ArrayList<Map<String, Object>>();
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			mapVo.put("role_id", id.split("@")[0]);
			mapVo.put("table_code", id.split("@")[1]);
			mapVo.put("perm_code", id.split("@")[2]);
			mapVo.put("mod_code", id.split("@")[3]);
			if(!"0".equals(id.split("@")[6])){
				mapVo.put("copy_code", id.split("@")[6]);
			}else{
				mapVo.put("copy_code", "0");
			}
			if(!"0".equals(id.split("@")[7])){
				mapVo.put("acc_year", id.split("@")[7]);
			}else{
				mapVo.put("acc_year", "0");
			}
			if(!"0".equals(id.split("@")[8])){mapVo.put("hos_id", id.split("@")[8]);}
			
			else{mapVo.put("hos_id", SessionManager.getHosId());}
			
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			entityMap.add(mapVo);
			permDataService.deleteRolePermData(mapVo); 
		}
		
		String accLederZcheck = permDataService.addRolePermData(entityMap);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	
	@RequestMapping(value = "/hrp/sys/permdata/deleteUserPermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserPermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String accLederZcheck = "";
		for (String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
			
			if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
			
			mapVo.put("user_id", id.split("@")[0]);
			
			mapVo.put("table_code", id.split("@")[1]);
			
			mapVo.put("perm_code", "");
			
			mapVo.put("mod_code", id.split("@")[3]);
			
			if(!"0".equals(id.split("@")[6])){mapVo.put("copy_code", id.split("@")[6]);}
			
			else{mapVo.put("copy_code", "");}
			
			if(!"0".equals(id.split("@")[7])){mapVo.put("acc_year", id.split("@")[7]);}
			
			else{mapVo.put("acc_year", "");}
			
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			
			accLederZcheck = permDataService.deleteUserPermData(mapVo);
			
		}

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/sys/permdata/deleteRolePermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRolePermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String accLederZcheck = "";
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			mapVo.put("role_id", id.split("@")[0]);
			mapVo.put("table_code", id.split("@")[1]);
			mapVo.put("perm_code", id.split("@")[2]);
			mapVo.put("perm_code", "");
			mapVo.put("mod_code", id.split("@")[3]);
			if(!"0".equals(id.split("@")[6])){
				mapVo.put("copy_code", id.split("@")[6]);
			}else{
				mapVo.put("copy_code", "");
			}
			if(!"0".equals(id.split("@")[7])){
				mapVo.put("acc_year", id.split("@")[7]);
			}else{
				mapVo.put("acc_year", "");
			}
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			accLederZcheck = permDataService.deleteRolePermData(mapVo); 
		}
		

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/sys/permdata/addBatchPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null || mapVo.get("hos_id").equals("")){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String reJson=null;
		try{
			reJson=permDataService.addBatchPerm(mapVo);
		}catch(Exception e){
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(reJson);
	}
}

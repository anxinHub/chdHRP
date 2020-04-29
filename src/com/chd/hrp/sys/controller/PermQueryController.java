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
import com.chd.hrp.sys.service.PermQueryService;

@Controller
public class PermQueryController  extends BaseController{
	private static Logger logger = Logger.getLogger(PermQueryController.class);
	
	@Resource(name = "permQueryService")
	private final PermQueryService permQueryService = null;
	
	@RequestMapping(value = "/hrp/sys/permquery/permQueryMainPage", method = RequestMethod.GET)
	public String permQueryMainPage(Model mode) throws Exception {

		return "hrp/sys/permquery/permQueryMain";
	}
	@RequestMapping(value = "/hrp/sys/permquery/permQueryMainGroupPage", method = RequestMethod.GET)
	public String permQueryMainGroupPage(Model mode) throws Exception {
		
		return "hrp/sys/permquery/permQueryMain";
	}
	
	@RequestMapping(value = "/hrp/sys/permquery/userMainPage", method = RequestMethod.GET)
	public String accUserMainPage(Model mode) throws Exception {

		return "hrp/sys/permquery/user";
	}
	
	@RequestMapping(value = "/hrp/sys/permquery/roleMainPage", method = RequestMethod.GET)
	public String roleMainPage(Model mode) throws Exception {

		return "hrp/sys/permquery/role";
	}
	
	@RequestMapping(value = "/hrp/sys/permquery/batchAddPermPage", method = RequestMethod.GET)
	public String accBatchAddPermPage(Model mode) throws Exception {

		return "hrp/sys/permquery/batchAddPerm";
	}
	
	@RequestMapping(value = "/hrp/sys/permquery/roleUserMainPage", method = RequestMethod.GET)
	public String roleUserMainPage(Model mode,String role_id) throws Exception {
		mode.addAttribute("role_id", role_id);
		return "hrp/sys/permquery/roleUser";
	}
	
	@RequestMapping(value = "/hrp/sys/permquery/queryPermQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPermQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
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
		
		sql.append("select a."+COLUMN_ID+" obj_id,a."+COLUMN_CODE+" obj_code,a."+COLUMN_NAME+" obj_name,b.is_read,b.is_write from "+TABLE_CODE+" a");
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
		}
		sql.append(" and (b.is_read=1  or  b.is_write=1) ");
		//sql.append(" order by b.perm_code");
		sql.append(" order by obj_code");
		mapVo.put("sql", sql.toString());
		String accLederZcheck = permQueryService.queryPermQuery(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	@RequestMapping(value = "/hrp/sys/permquery/queryPermQueryGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPermQueryGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
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
		
		sql.append("select a."+COLUMN_ID+" obj_id,a."+COLUMN_CODE+" obj_code,a."+COLUMN_NAME+" obj_name,b.is_read,b.is_write from "+TABLE_CODE+" a");
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
		}
		sql.append(" and b.is_read=1 and  b.is_write=1 ");
		sql.append(" order by b.perm_code");
		mapVo.put("sql", sql.toString());
		String accLederZcheck = permQueryService.queryPermQuery(getPage(mapVo));
		
		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/sys/permquery/addUserPermQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUserPermQuery(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> entityMap = new ArrayList<Map<String, Object>>();
		
		for (String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
			
			if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
			
			mapVo.put("user_id", id.split("@")[0]);
			
			mapVo.put("table_code", id.split("@")[1]);
			
			mapVo.put("perm_code", id.split("@")[2]);
			
			mapVo.put("mod_code", id.split("@")[3]);
			
			if(!"0".equals(id.split("@")[6])){mapVo.put("copy_code", id.split("@")[6]);}
			
			else{mapVo.put("copy_code", "0");}
			
			if(!"0".equals(id.split("@")[7])){mapVo.put("acc_year", id.split("@")[7]);}
			
			else{mapVo.put("acc_year", "0");}
			
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			
			entityMap.add(mapVo);
			
			permQueryService.deleteUserPermQuery(mapVo);
		}
		
		String accLederZcheck = permQueryService.addUserPermQuery(entityMap);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/sys/permquery/addRolePermQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRolePermQuery(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> entityMap = new ArrayList<Map<String, Object>>();
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
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			entityMap.add(mapVo);
			permQueryService.deleteRolePermQuery(mapVo); 
		}
		
		String accLederZcheck = permQueryService.addRolePermQuery(entityMap);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	
	@RequestMapping(value = "/hrp/sys/permquery/deleteUserPermQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserPermQuery(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
			
			accLederZcheck = permQueryService.deleteUserPermQuery(mapVo);
			
		}

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/sys/permquery/deleteRolePermQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRolePermQuery(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
			accLederZcheck = permQueryService.deleteRolePermQuery(mapVo); 
		}
		

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/sys/permquery/addBatchPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
		
		if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
		
		String sql = "select column_id  from SYS_PERM_DATA where table_code='"+mapVo.get("table_code")+"'";
		
		mapVo.put("sql", sql);
		
		String columnId = permQueryService.queryColumnIdByTableCode(mapVo);//查询字段
		
		mapVo.put("columnId", columnId);
		
		StringBuffer sqlWhere = new StringBuffer();
		
		String[] obj = String.valueOf(mapVo.get("obj")).split("\\.");
		
		sqlWhere.append(" where 1 = 1");

		if(("1").equals(obj[5])){
			sqlWhere.append(" and group_id = " + mapVo.get("group_id").toString());
		}
		if(("1").equals(obj[6])){
			sqlWhere.append(" and hos_id = " + mapVo.get("hos_id").toString());
		}
		if(("1").equals(obj[7])){
			sqlWhere.append(" and copy_code = '" + mapVo.get("copy_code").toString()+"'");
		}
		if(("1").equals(obj[8])){
			sqlWhere.append(" and acc_year = '" + mapVo.get("acc_year").toString()+"'");
		}
		if(mapVo.get("mod_code").toString().equals("00")){
			sqlWhere.append(" and is_stop = 0 ");
		}
		mapVo.put("sqlWhere", sqlWhere);
		
		String msg = "";
		
		if("1".equals(mapVo.get("tip"))){//如果tip=1 则添加用户权限
			
			mapVo.put("user_id", mapVo.get("perm_id"));
			
			permQueryService.deleteUserPermQuery(mapVo);
			
			msg = permQueryService.addBatchUserPermQuery(mapVo);
			
		}else{// 否则添加角色权限
			
			mapVo.put("role_id", mapVo.get("perm_id"));
			
			permQueryService.deleteRolePermQuery(mapVo);
			
			msg = permQueryService.addBatchRolePermQuery(mapVo);
		}
		
		
		
		return JSONObject.parseObject(msg);
	}
}

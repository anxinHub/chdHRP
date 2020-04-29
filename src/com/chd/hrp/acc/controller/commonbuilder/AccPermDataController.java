package com.chd.hrp.acc.controller.commonbuilder;

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
import com.chd.hrp.acc.entity.AccUserPermData;
import com.chd.hrp.acc.service.commonbuilder.AccPermDataService;

@Controller
public class AccPermDataController  extends BaseController{
	private static Logger logger = Logger.getLogger(AccPermDataController.class);
	
	@Resource(name = "accPermDataService")
	private final AccPermDataService accPermDataService = null;
	
	@RequestMapping(value = "/hrp/acc/accpermdata/accPermDataMainPage", method = RequestMethod.GET)
	public String accPermDataMainPage(Model mode) throws Exception {

		return "hrp/acc/accpermdata/accPermDataMain";
	}
	
	@RequestMapping(value = "/hrp/acc/accpermdata/accUserMainPage", method = RequestMethod.GET)
	public String accUserMainPage(Model mode) throws Exception {

		return "hrp/acc/accpermdata/accUser";
	}
	
	@RequestMapping(value = "/hrp/acc/accpermdata/accRoleMainPage", method = RequestMethod.GET)
	public String accRoleMainPage(Model mode) throws Exception {

		return "hrp/acc/accpermdata/accRole";
	}
	
	@RequestMapping(value = "/hrp/acc/accpermdata/accBatchAddPermPage", method = RequestMethod.GET)
	public String accBatchAddPermPage(Model mode) throws Exception {

		return "hrp/acc/accpermdata/accBatchAddPerm";
	}
	
	@RequestMapping(value = "/hrp/acc/accpermdata/accRoleUserMainPage", method = RequestMethod.GET)
	public String accRoleUserMainPage(Model mode,String role_id) throws Exception {
		mode.addAttribute("role_id", role_id);
		return "hrp/acc/accpermdata/accRoleUser";
	}
	
	@RequestMapping(value = "/hrp/acc/accpermdata/queryAccPermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPermData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
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
		//sql.append(" and a.is_stop = 0 ");
		sql.append(" order by b.perm_code");
		mapVo.put("sql", sql.toString());
		String accLederZcheck = accPermDataService.queryAccPermData(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/acc/accpermdata/addAccUserPermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccUserPermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> entityMap = new ArrayList<Map<String, Object>>();
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(mapVo.get("acc_year") == null){
				mapVo.put("acc_year", SessionManager.getAcctYear());
			}
			mapVo.put("user_id", id.split("@")[0]);
			mapVo.put("table_code", id.split("@")[1]);
			mapVo.put("perm_code", id.split("@")[2]);
			mapVo.put("mod_code", id.split("@")[3]);
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			entityMap.add(mapVo);
			accPermDataService.deleteAccUserPermData(mapVo);
		}
		
		String accLederZcheck = accPermDataService.addAccUserPermData(entityMap);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/accpermdata/addAccRolePermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccRolePermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> entityMap = new ArrayList<Map<String, Object>>();
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(mapVo.get("acc_year") == null){
				mapVo.put("acc_year", SessionManager.getAcctYear());
			}
			mapVo.put("role_id", id.split("@")[0]);
			mapVo.put("table_code", id.split("@")[1]);
			mapVo.put("perm_code", id.split("@")[2]);
			mapVo.put("mod_code", id.split("@")[3]);
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			entityMap.add(mapVo);
			accPermDataService.deleteAccRolePermData(mapVo); 
		}
		
		String accLederZcheck = accPermDataService.addAccRolePermData(entityMap);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	
	@RequestMapping(value = "/hrp/acc/accpermdata/deleteAccUserPermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccUserPermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String accLederZcheck = "";
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(mapVo.get("acc_year") == null){
				mapVo.put("acc_year", SessionManager.getAcctYear());
			}
			mapVo.put("user_id", id.split("@")[0]);
			mapVo.put("table_code", id.split("@")[1]);
			mapVo.put("perm_code", id.split("@")[2]);
			mapVo.put("mod_code", id.split("@")[3]);
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			accLederZcheck = accPermDataService.deleteAccUserPermData(mapVo);
		}

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/accpermdata/deleteAccRolePermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccRolePermData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String accLederZcheck = "";
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(mapVo.get("acc_year") == null){
				mapVo.put("acc_year", SessionManager.getAcctYear());
			}
			mapVo.put("role_id", id.split("@")[0]);
			mapVo.put("table_code", id.split("@")[1]);
			mapVo.put("perm_code", id.split("@")[2]);
			mapVo.put("mod_code", id.split("@")[3]);
			mapVo.put("is_read", id.split("@")[4].equals("true")?1:0);
			mapVo.put("is_write", id.split("@")[5].equals("true")?1:0);
			accLederZcheck = accPermDataService.deleteAccRolePermData(mapVo); 
		}

		return JSONObject.parseObject(accLederZcheck);
	}
}

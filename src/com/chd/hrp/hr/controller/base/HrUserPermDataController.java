package com.chd.hrp.hr.controller.base;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.base.HrUserPermData;
import com.chd.hrp.hr.service.base.HrUserPermDataService;

/**
 * 数据权限设置
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/hrp/hr/sysset")
public class HrUserPermDataController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrUserPermDataController.class);
	
	//引入service服务
	@Resource(name="hrUserPermDataService")
	private final HrUserPermDataService  hrUserPermDataService=null;
	
	/**
	 * 主页跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sysUserPermDataMainPage", method = RequestMethod.GET)
	public String permDataMainPage(Model mode) throws Exception {

		return "hrp/hr/base/permdata/permDataMain";
	}
/*	*//**
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/hrp/sys/permdata/permDataMainGroupPage", method = RequestMethod.GET)
	public String permDataMainGroupPage(Model mode) throws Exception {
		
		return "hrp/sys/permdata/permDataMain";
	}*/
	
	/**
	 * 跳转用户
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userMainPage", method = RequestMethod.GET)
	public String accUserMainPage(Model mode) throws Exception {

		return "hrp/hr/base/permdata/user";
	}
	/**
	 * 跳转角色
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/roleMainPage", method = RequestMethod.GET)
	public String roleMainPage(Model mode) throws Exception {

		return "hrp/hr/base/permdata/role";
	}
	/**
	 * 批量添加页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAddPermPage", method = RequestMethod.GET)
	public String accBatchAddPermPage(@RequestParam String paramVo, Model mode) throws Exception {
		mode.addAttribute("mapVo", paramVo);
		return "hrp/hr/base/permdata/batchAddPerm";
	}
	
	@RequestMapping(value = "/roleUserMainPage", method = RequestMethod.GET)
	public String roleUserMainPage(Model mode,String role_id) throws Exception {
		mode.addAttribute("role_id", role_id);
		return "hrp/hr/base/permdata/roleUser";
	}
	
	/**
	 * 查询所有用户
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllUser", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAllUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("type_codes", "0,1");
		String user = hrUserPermDataService.queryUser(getPage(mapVo));

		return JSONObject.parseObject(user);
		
	}	
	// 查询角色
	@RequestMapping(value = "/queryHosRole", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String role = hrUserPermDataService.queryRole(getPage(mapVo));

		return JSONObject.parseObject(role);
		
	}
	/**
	 * 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPermData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPermData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		StringBuffer sql = new StringBuffer();

		String TABLE_CODE = "HR_TABLE_STRUC";

		String COLUMN_ID = "TAB_CODE";
		String COLUMN_CODE = "TAB_CODE";
		String COLUMN_NAME = "TAB_NAME";
		String IS_GROUP = "1";
		String IS_HOS = "1";

		sql.append("select a." + COLUMN_ID + " obj_id,a." + COLUMN_CODE + " obj_code,a." + COLUMN_NAME
				+ " obj_name,b.is_read,b.is_write from " + TABLE_CODE + " a");

		if (mapVo.get("user_id") != null) {
			sql.append(" left join SYS_USER_PERM_DATA b on a." + COLUMN_ID + " = b.perm_code ");
			sql.append(" and b.user_id = " + mapVo.get("user_id").toString());
		} else {
			sql.append(" left join SYS_ROLE_PERM_DATA b on a." + COLUMN_ID + " = b.perm_code ");
			if (IS_GROUP.equals("1")) {
				sql.append(" and a.group_id = b.group_id ");
			}
			if (IS_HOS.equals("1")) {
				sql.append(" and a.hos_id = b.hos_id ");
			}
			if (mapVo.get("role_id") != null) {
				sql.append(" and b.role_id = " + mapVo.get("role_id").toString());

			}

		}
		sql.append(" where 1 = 1");

		if (IS_GROUP.equals("1")) {
			sql.append(" and a.group_id = " + mapVo.get("group_id").toString());
		}
		if (IS_HOS.equals("1")) {
			sql.append(" and a.hos_id = " + mapVo.get("hos_id").toString());
		}
		sql.append(" and a.type_tab_code = 'pf' and a.is_stop=0 ");

		if (mapVo.containsKey("tab_code")) {
			if (mapVo.get("tab_code") != null && mapVo.get("tab_code") != "") {
				sql.append(" and a." + COLUMN_CODE + " like '%" + mapVo.get("tab_code").toString().toUpperCase() + "%'");
			}
		}
		if (mapVo.containsKey("tab_name")) {
			if (mapVo.get("tab_name") != null && mapVo.get("tab_name") != "") {
				sql.append(" and a." + COLUMN_NAME + " like '%" + mapVo.get("tab_name").toString() + "%'");
			}
		}

		sql.append(" order by obj_code");
		mapVo.put("sql", sql.toString());
		String accLederZcheck = hrUserPermDataService.queryPermData(getPage(mapVo));

		return JSONObject.parseObject(accLederZcheck);
	}
	/**
	 * 新增用户权限
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUserPermData", method = RequestMethod.POST)
	@ResponseBody
	public String addUserPermData(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			List<HrUserPermData> listVo = JSONArray.parseArray(paramVo, HrUserPermData.class);
			
			for (HrUserPermData hrUserPermData : listVo) {
				hrUserPermData.setMod_code("06");
				hrUserPermData.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
				hrUserPermData.setHos_id(Long.parseLong(SessionManager.getHosId()));
				hrUserPermData.setCopy_code("0");
				hrUserPermData.setAcc_year("0");
				if(hrUserPermData.getIs_read() == null){
					hrUserPermData.setIs_read(0L);
				}
				if(hrUserPermData.getIs_write() == null){
					hrUserPermData.setIs_write(0L);
				}
			}
			hrUserPermDataService.deleteUserPermData(listVo);
			String accLederZcheck = hrUserPermDataService.addUserPermData(listVo);

			return accLederZcheck;
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	
	}
	/**
	 * 新增角色权限
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRolePermData", method = RequestMethod.POST)
	@ResponseBody
	public String addRolePermData(@RequestParam String paramVo, Model mode) throws Exception {
		try {
		List<HrUserPermData> listVo = JSONArray.parseArray(paramVo, HrUserPermData.class);
		
		for (HrUserPermData hrUserPermData : listVo) {
			hrUserPermData.setMod_code("06");
			hrUserPermData.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			hrUserPermData.setHos_id(Long.parseLong(SessionManager.getHosId()));
			hrUserPermData.setCopy_code(SessionManager.getCopyCode());
			hrUserPermData.setAcc_year(SessionManager.getAcctYear());
			if(hrUserPermData.getIs_read() == null){
				hrUserPermData.setIs_read(0L);
			}
			if(hrUserPermData.getIs_write() == null){
				hrUserPermData.setIs_write(0L);
			}
		}
		hrUserPermDataService.deleteRolePermData(listVo);
		String accLederZcheck = hrUserPermDataService.addRolePermData(listVo);

		return accLederZcheck;
	} catch (Exception e) {
		return "{\"error\":\""+e.getMessage()+"\"}";
	}
}
	
	/**
	 * 删除用户权限
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/deleteUserPermData", method = RequestMethod.POST)
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
			
			accLederZcheck = hrUserPermDataService.deleteUserPermData(mapVo);
			
		}

		return JSONObject.parseObject(accLederZcheck);
	}
	*//**
	 * 删除角色权限
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 *//*
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
			accLederZcheck = hrUserPermDataService.deleteRolePermData(mapVo); 
		}
		

		return JSONObject.parseObject(accLederZcheck);
	}*/
	/**
	 * 批量添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAddPerm", method = RequestMethod.POST)
	@ResponseBody
	public String addBatchPerm(@RequestParam String paramVo, Model mode) throws Exception {
		try {
		List<HrUserPermData> listVo = JSONArray.parseArray(paramVo, HrUserPermData.class);
		
		for (HrUserPermData hrUserPermData : listVo) {
			hrUserPermData.setMod_code("06");
			hrUserPermData.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			hrUserPermData.setHos_id(Long.parseLong(SessionManager.getHosId()));
			hrUserPermData.setCopy_code(SessionManager.getCopyCode());
			hrUserPermData.setAcc_year(SessionManager.getAcctYear());;
		}
		hrUserPermDataService.deleteRolePermData(listVo);
		String accLederZcheck = hrUserPermDataService.addRolePermData(listVo);

		return accLederZcheck;
	} catch (Exception e) {
		return "{\"error\":\""+e.getMessage()+"\"}";
	}
}
}

/**
 * 2014-12-31 LoginServiceImpl.java author:pengjin
 */
package com.chd.hrp.sys.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.startup.LoadMenuFile;
import com.chd.base.util.WisdomCloud;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.entity.MyAccYearMonth;
import com.chd.hrp.sys.dao.CopyMapper;
import com.chd.hrp.sys.dao.GroupPermMapper;
import com.chd.hrp.sys.dao.InfoDictMapper;
import com.chd.hrp.sys.dao.ModMapper;
import com.chd.hrp.sys.dao.PermMapper;
import com.chd.hrp.sys.dao.RolePermMapper;
import com.chd.hrp.sys.dao.UserMapper;
import com.chd.hrp.sys.dao.UserPermMapper;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.chd.hrp.sys.entity.GroupPerm;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.entity.Perm;
import com.chd.hrp.sys.entity.SysMenu;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.service.LoginService;
import com.chd.hrp.sys.service.ModStartService;
import com.chd.hrp.sys.service.base.SysBaseService;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);

	@Resource(name = "userMapper")
	private UserMapper userMapper = null;

	@Resource(name = "rolePermMapper")
	private RolePermMapper rolePermMapper = null;

	@Resource(name = "userPermMapper")
	private UserPermMapper userPermMapper = null;

	@Resource(name = "copyMapper")
	private CopyMapper copyMapper = null;

	@Resource(name = "modMapper")
	private ModMapper modMapper = null;

	@Resource(name = "groupPermMapper")
	private GroupPermMapper groupPermMapper = null;
 
	@Resource(name = "permMapper")
	private PermMapper permMapper = null;

	@Resource(name = "infoDictMapper")
	private InfoDictMapper infoDictMapper = null;

	@Resource(name = "userService")
	private final UserServiceImpl userService = null;

	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;

	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;
	
	@Resource(name = "modStartService")
	private final ModStartService modStartService = null; 

	// 登录
	@Override
	public String login(Map<String, Object> mapVo) throws DataAccessException {
		//List<Copy> list_copy = null;
		User user = userMapper.queryUserHosGroupByCode(mapVo);

		if (StringUtils.isEmpty(user)) {
			return "{\"loginMsg\":\"用户不存在.\"}";
		}

		if (user.getIs_stop() == 1) {
			return "{\"loginMsg\":\"用户已经停用.\"}";
		}

		try {

			WisdomCloud wc = new WisdomCloud();
			if (!user.getUser_pwd().equals(wc.encrypt((String) mapVo.get("user_pwd")))) {
				return "{\"loginMsg\":\"密码错误.\"}";
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"loginMsg\":\"密码错误.\"}";
		}

		String modCode = null;
		String copyCode = "";
		String copyName = "";
		String copyNature=null;
		// 处理用户登录权限
		if (mapVo.get("user_code").toString().equalsIgnoreCase("admin")) {
			//系统管理员
			modCode = "00";
		} else if (user.getType_code() == 0 || user.getType_code() == 1) {
			//0医院用户，1集团用户
			modCode=null;
			Map<String, Object> hos_user_map = new HashMap<String, Object>();
			hos_user_map.put("user_id", user.getUser_id());
			hos_user_map.put("group_id", user.getGroup_id());
			
			if (user.getType_code() == 1) {
				hos_user_map.put("hos_id", 0);
			}else{
				hos_user_map.put("hos_id", user.getHos_id());
			}

			List<Map<String,Object>> perm=copyMapper.queryCopyModByPerm(hos_user_map);

			if(perm==null || perm.size()==0){
				return "{\"loginMsg\":\"没有系统权限.\"}";
			}
			
			//取账套、模块
			for (Map<String,Object> p : perm) {
				if((copyCode==null || copyCode.equals("")) && p.get("copy_code").toString().equals(user.getCopy_code())){
					copyCode = user.getCopy_code();
					copyName = p.get("copy_name").toString();
					copyNature = p.get("nature_code").toString();
				}
				if((modCode==null || modCode.equals("")) && p.get("mod_code").toString().equals(user.getMod_code())){
					modCode = user.getMod_code();
				}
				if(copyCode!=null && !copyCode.equals("") && modCode!=null && !modCode.equals("")){
					break;
				}
			}
			
			if(copyCode==null || copyCode.equals("")){
				copyCode = perm.get(0).get("copy_code").toString();
				copyName = perm.get(0).get("copy_name").toString();
				copyNature = perm.get(0).get("nature_code").toString();
			}
			if(modCode==null || modCode.equals("")){
				modCode = perm.get(0).get("mod_code").toString();
			}
			

			//查询会计期间
			MyAccYearMonth accYearMonth=MyConfig.getAccYearMonth(user.getGroup_id().toString(),user.getHos_id().toString(),copyCode);
			if(accYearMonth==null || accYearMonth.getMinDate()==null || accYearMonth.getMinDate().equals("")){
				return "{\"loginMsg\":\"没有维护会计期间.\"}";
			}
			
		} else if (user.getType_code() == 3) {
			// 集团管理员
			modCode = "00";
			Map<String, Object> group_map = new HashMap<String, Object>();
			group_map.put("group_id", user.getGroup_id());
			List<GroupPerm> groupPerm = groupPermMapper.queryGroupPerm(group_map);
			if (groupPerm == null || groupPerm.size() == 0) {
				return "{\"loginMsg\":\"没有系统权限.\"}";
			} 
		} else if (user.getType_code() == 4) {
			// 医院管理员
			modCode = "00";
			Map<String, Object> hos_map = new HashMap<String, Object>();
			hos_map.put("group_id", user.getGroup_id());
			hos_map.put("hos_id", user.getHos_id());
			List<Perm> hosPerm = permMapper.queryPerm(hos_map);
			if (hosPerm == null || hosPerm.size() == 0) {
				return "{\"loginMsg\":\"没有系统权限.\"}";
			} 

		}
		// 用户最后登录系统模块，License为null，改为取第一个有权限的系统模块。
		if(LoadMenuFile.getModMap().size()==0){
			return "{\"loginMsg\":\"系统加载模块失败,请重新启动.\"}";
		}
		if (LoadMenuFile.getModMap().get(modCode) == null) {
			return "{\"loginMsg\":\"模块编码："+modCode+",License没有授权.\"}";
		}

		SessionManager.getSession().setAttribute("copy_code", copyCode);
		SessionManager.getSession().setAttribute("copy_name", copyName);
		SessionManager.getSession().setAttribute("copy_nature", copyNature);
		SessionManager.getSession().setAttribute("mod_code", modCode);
		SessionManager.getSession().setAttribute("mod_name", LoadMenuFile.getModMap().get(modCode));
		SessionManager.getSession().setAttribute("user_id", user.getUser_id());
		SessionManager.getSession().setAttribute("user_code", user.getUser_code());
		SessionManager.getSession().setAttribute("user_name", user.getUser_name());
		SessionManager.getSession().setAttribute("type_code", user.getType_code());
		SessionManager.getSession().setAttribute("emp_code", user.getEmp_code());
		SessionManager.getSession().setAttribute("emp_name", user.getEmp_name());
		SessionManager.getSession().setAttribute("hospital", LoadMenuFile.getHospital());
		SessionManager.getSession().setAttribute("hos_id", user.getHos_id());
		SessionManager.getSession().setAttribute("hos_code", user.getHos_code());
		SessionManager.getSession().setAttribute("hos_name", user.getHos_name());
		SessionManager.getSession().setAttribute("hos_simple", user.getHos_simple());
		SessionManager.getSession().setAttribute("group_id", user.getGroup_id());
		SessionManager.getSession().setAttribute("group_code", user.getGroup_code());
		SessionManager.getSession().setAttribute("group_name", user.getGroup_name());
		SessionManager.getSession().setAttribute("group_simple", user.getGroup_simple());
		SessionManager.getSession().setAttribute("acct_year", MyConfig.getCurAccYear());
		SessionManager.getSession().setAttribute("acc_mod_start_map", queryModStartMap());
		
		// 保存用户最后登录模块和账套
		Map<String, Object> modMap = new HashMap<String, Object>();
		modMap.put("user_id", user.getUser_id());
		modMap.put("mod_code", modCode);
		modMap.put("copy_code", copyCode);
		userService.updateUserMod(modMap);
		
		return "{isLogin:'ok'}";
	}
	
	
	// 工资条登录
	@Override
	public String singleLogin(Map<String, Object> mapVo) throws DataAccessException {
		//List<Copy> list_copy = null;
		User user = userMapper.queryUserHosGroupByCode(mapVo);

		if (StringUtils.isEmpty(user)) {
			return "{\"loginMsg\":\"用户不存在.\"}";
		}

		if (user.getIs_stop() == 1) {
			return "{\"loginMsg\":\"用户已经停用.\"}";
		}
		
		if (user.getEmp_code()==null || user.getEmp_code().equals("")) {
			return "{\"loginMsg\":\"职工不存在.\"}";
		}

		try {

			WisdomCloud wc = new WisdomCloud();
			if (!user.getUser_pwd().equals(wc.encrypt((String) mapVo.get("user_pwd")))) {
				return "{\"loginMsg\":\"密码错误.\"}";
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"loginMsg\":\"密码错误.\"}";
		}

		if(mapVo.get("user_code").toString().equalsIgnoreCase("admin")){
			return "{\"loginMsg\":\"没有系统权限.\"}";
		}else if(user.getType_code() == 3 || user.getType_code() == 4){
			return "{\"loginMsg\":\"没有系统权限.\"}";
		}
		
		SessionManager.getSession().setAttribute("user_id", user.getUser_id());
		SessionManager.getSession().setAttribute("user_code", user.getUser_code());
		SessionManager.getSession().setAttribute("user_name", user.getUser_name());
		SessionManager.getSession().setAttribute("type_code", user.getType_code());
		SessionManager.getSession().setAttribute("emp_code", user.getEmp_code());
		SessionManager.getSession().setAttribute("emp_name", user.getEmp_name());
		SessionManager.getSession().setAttribute("group_id", user.getGroup_id());
		SessionManager.getSession().setAttribute("hos_id", user.getHos_id());
		
		return "{isLogin:'ok'}";
	}
	
	// 单点登录
	@Override
	public Map<String,Object> dhcHrpLogin(Map<String, Object> mapVo) throws DataAccessException {
		
		String[] tokey=mapVo.get("tokey").toString().split(",");
		mapVo.put("user_code", tokey[0]);
		User user = userMapper.queryUserHosGroupByCode(mapVo);

		if (StringUtils.isEmpty(user)) {
			mapVo.put("loginMsg", "用户不存在");
			return mapVo;
		}

		if (user.getIs_stop() == 1) {
			mapVo.put("loginMsg", "用户已经停用");
			return mapVo;
		}
		
//		try {
//
//			WisdomCloud wc = new WisdomCloud();
//			if (!user.getUser_pwd().equals(wc.encrypt((String) mapVo.get("user_pwd")))) {
//				return "{\"loginMsg\":\"密码错误.\"}";
//			}
//
//		}catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return "{\"loginMsg\":\"密码错误.\"}";
//		}

		if(mapVo.get("user_code").toString().equalsIgnoreCase("admin")){
			mapVo.put("loginMsg", "没有系统权限");
			return mapVo;
		}else if(user.getType_code() == 3 || user.getType_code() == 4){
			mapVo.put("loginMsg", "没有系统权限");
			return mapVo;
		}
		
		String copyCode=null;
		String modCode=null;
		String copyName=null;
		String copyNature=null;
		Map<String, Object> hos_user_map = new HashMap<String, Object>();
		hos_user_map.put("user_id", user.getUser_id());
		hos_user_map.put("group_id", user.getGroup_id());
		
		if (user.getType_code() == 1) {
			hos_user_map.put("hos_id", 0);
		}else{
			hos_user_map.put("hos_id", user.getHos_id());
		}

		List<Map<String,Object>> perm=copyMapper.queryCopyModByPerm(hos_user_map);

		if(perm==null || perm.size()==0){
			mapVo.put("loginMsg", "没有系统权限");
		}
		
		//取账套、模块
		for (Map<String,Object> p : perm) {
			if((copyCode==null || copyCode.equals("")) && p.get("copy_code").toString().equals(user.getCopy_code())){
				copyCode = user.getCopy_code();
				copyName = p.get("copy_name").toString();
				copyNature = p.get("nature_code").toString();
			}
			if((modCode==null || modCode.equals("")) && p.get("mod_code").toString().equals(user.getMod_code())){
				modCode = user.getMod_code();
			}
			if(copyCode!=null && !copyCode.equals("") && modCode!=null && !modCode.equals("")){
				break;
			}
		}
		
		if(copyCode==null || copyCode.equals("")){
			copyCode = perm.get(0).get("copy_code").toString();
			copyName = perm.get(0).get("copy_name").toString();
			copyNature = perm.get(0).get("nature_code").toString();
		}
		if(modCode==null || modCode.equals("")){
			modCode = perm.get(0).get("mod_code").toString();
		}
		

		//查询会计期间
		MyAccYearMonth accYearMonth=MyConfig.getAccYearMonth(user.getGroup_id().toString(),user.getHos_id().toString(),copyCode);
		if(accYearMonth==null || accYearMonth.getMinDate()==null || accYearMonth.getMinDate().equals("")){
			mapVo.put("loginMsg", "没有维护会计期间");
			return mapVo;
		}
		
		SessionManager.getSession().setAttribute("copy_code", copyCode);
		SessionManager.getSession().setAttribute("copy_name", copyName);
		SessionManager.getSession().setAttribute("copy_nature", copyNature);
		SessionManager.getSession().setAttribute("mod_code", modCode);
		SessionManager.getSession().setAttribute("mod_name", LoadMenuFile.getModMap().get(modCode));
		SessionManager.getSession().setAttribute("user_id", user.getUser_id());
		SessionManager.getSession().setAttribute("user_code", user.getUser_code());
		SessionManager.getSession().setAttribute("user_name", user.getUser_name());
		SessionManager.getSession().setAttribute("type_code", user.getType_code());
		SessionManager.getSession().setAttribute("emp_code", user.getEmp_code());
		SessionManager.getSession().setAttribute("emp_name", user.getEmp_name());
		SessionManager.getSession().setAttribute("hospital", LoadMenuFile.getHospital());
		SessionManager.getSession().setAttribute("hos_id", user.getHos_id());
		SessionManager.getSession().setAttribute("hos_code", user.getHos_code());
		SessionManager.getSession().setAttribute("hos_name", user.getHos_name());
		SessionManager.getSession().setAttribute("hos_simple", user.getHos_simple());
		SessionManager.getSession().setAttribute("group_id", user.getGroup_id());
		SessionManager.getSession().setAttribute("group_code", user.getGroup_code());
		SessionManager.getSession().setAttribute("group_name", user.getGroup_name());
		SessionManager.getSession().setAttribute("group_simple", user.getGroup_simple());
		SessionManager.getSession().setAttribute("acct_year", MyConfig.getCurAccYear());
		SessionManager.getSession().setAttribute("acc_mod_start_map", queryModStartMap());
		
		mapVo.put("isLogin", "ok");
		return mapVo;
	}

	// 加载系统模块菜单
	@Override
	public String loadSystemModTree(Map<String, Object> mapVo) throws DataAccessException {
		
		String modCode = mapVo.get("mod_code").toString();
		String copy_code = mapVo.get("copy_code") == null ? "" : mapVo.get("copy_code").toString();
		
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{");
		jsonResult.append(getMod(copy_code));// 加载系统模块
		jsonResult.append("," + mainTree(modCode, copy_code));// 加载系统菜单
		jsonResult.append(",mod_code:'" + modCode + "'");
		jsonResult.append(",mod_name:'" + LoadMenuFile.getModMap().get(modCode) + "'");
		jsonResult.append(",user_id:'" + SessionManager.getUserId() + "'");
		jsonResult.append(",user_code:'" + SessionManager.getUserCode() + "'");
		jsonResult.append(",user_name:'" + SessionManager.getUserName() + "'");
		jsonResult.append(",type_code:'" + SessionManager.getTypeCode() + "'");
		jsonResult.append(",emp_code:'" + SessionManager.getEmpCode() + "'");
		jsonResult.append(",emp_name:'" + SessionManager.getEmpName() + "'");
		jsonResult.append(",acct_year:'" + SessionManager.getAcctYear() + "'");
		jsonResult.append(",hos_id:'" + mapVo.get("hos_id") + "'");
		jsonResult.append(",group_id:'" + mapVo.get("group_id") + "'");
		jsonResult.append(",group_name:'" + SessionManager.getGroupName() + "'");
		jsonResult.append(",copy_code:'" + copy_code + "'");
		jsonResult.append(",hospital:'" + SessionManager.getHosName() + "'");
		jsonResult.append(",copy_name:'" + SessionManager.getCopyName() + "'");
		jsonResult.append(",min_date:'" + MyConfig.getMinDate() + "'");
		jsonResult.append(",max_date:'" + MyConfig.getMaxDate() + "'");
		jsonResult.append(",copy_nature:'" + SessionManager.getCopyNature() + "'");
		
		//模块启用年月
		jsonResult.append(","+modStartService.querySysModStart(mapVo));
		
		jsonResult.append("}");
		return jsonResult.toString();

	}

	private List<Mod> queryModbyUserPerm(Map<String, Object> map) {

		return modMapper.queryModByPerm(map);
	}

	// 加载系统模块
	private String getMod(String copy_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", SessionManager.getUserId());
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", copy_code.equals("") ? SessionManager.getCopyCode() : copy_code);
		List<Mod> modList = new ArrayList<Mod>();
		// 超级管理员 集团管理员 医院管理员
		if (SessionManager.getTypeCode().equals("2") || SessionManager.getTypeCode().equals("3") || SessionManager.getTypeCode().equals("4")) {
			modList = new ArrayList<Mod>();
			Mod mod = new Mod();
			mod.setMod_code("00");
			mod.setMod_name("系统平台");
			mod.setParent_code("top");
			mod.setIs_sys(1);
			modList.add(mod);
		} else {
			// 集团用户和普通医院用户
			modList = queryModbyUserPerm(map);
		}
		if (modList == null || modList.size() == 0) {
			return "";
		}
		int row = 0;
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("mod:[");
		for (Mod mod : modList) {
			if (row == 0) {
				jsonResult.append("{");
			} else {
				jsonResult.append(",{");
			}
			row++;
			jsonResult.append("mod_code:'" + mod.getMod_code() + "',");
			jsonResult.append("mod_name:'" + mod.getMod_name() + "',");
			jsonResult.append("is_sys:'" + mod.getIs_sys() + "',");
			jsonResult.append("parent_code:'" + mod.getParent_code() + "'");
			jsonResult.append("}");
		}
		jsonResult.append("]");
		return jsonResult.toString();
	}

	// 加载系统菜单
	public String mainTree(String modCode, String copy_code) throws DataAccessException {
		
		List<SysMenu> list = LoadMenuFile.getMenuMap().get(modCode);
		if (list == null || list.size() == 0) {
			return "tree:[]";
		}
		
		String typeCode=SessionManager.getTypeCode();
		List<String> liUserPerm = null;
		if(typeCode.equals("0") || typeCode.equals("1")){
			//0医院用户、1集团用户
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", copy_code);
			mapVo.put("mod_code", modCode);
			mapVo.put("user_id", SessionManager.getUserId());
			//查询用户角色权限视图
			liUserPerm = userPermMapper.queryUserPermByLogin(mapVo);
			if(liUserPerm==null || liUserPerm.size()==0){
				return "tree:[]";
			}
			SessionManager.getSession().setAttribute("user_perm", liUserPerm);
		}
		
		StringBuilder jsonResult = new StringBuilder();
		int row=0;
		List<SysMenu> childList=null;
		jsonResult.append("tree:[");
		for (SysMenu menu : list) {
			//logger.debug("id："+menu.getId()+"，pid："+menu.getPid()+"，code："+menu.getCode()+"，name："+menu.getMenu_name()+"，url："+menu.getMenu_url()+"，permid："+menu.getPerm_id());
			//System.out.println("id："+menu.getId()+"，pid："+menu.getPid()+"，code："+menu.getCode()+"，name："+menu.getMenu_name()+"，url："+menu.getMenu_url()+"，permid："+menu.getPerm_id());
			if (menu.getPerm_id() != null) {
				continue;
			}
			
			String code=menu.getCode();
			if(typeCode.equals("2")){
				if(!code.equalsIgnoreCase("sysManager") && !code.equalsIgnoreCase("sysInfo")){
					//超级管理员
					continue;
				}
			}else if(typeCode.equals("3")){ 
				if(!code.equalsIgnoreCase("groupManager") && !code.equalsIgnoreCase("hosInfo") && !code.equalsIgnoreCase("hipManager")){
					//集团管理员
					continue;
				}
			}else if(typeCode.equals("4")){
				if(!code.equalsIgnoreCase("hosInfo") && !code.equalsIgnoreCase("hipManager")){
					//医院管理员
					continue;
				}
			}else{
				//0医院用户、1集团用户
				if(modCode.equals("00") && !code.equalsIgnoreCase("hosInfo") && !code.equalsIgnoreCase("hipManager")){
					//系统平台只显示hosInfo、hipManager菜单
					continue;
				}
				//检查功能权限
				childList=new ArrayList<SysMenu>();
				if(MyConfig.checkMenuPerm(list,liUserPerm,menu.getId(),childList).size()==0){
					continue;
				}
			}
			
			if (row == 0) {
				jsonResult.append("{");
			} else {
				jsonResult.append(",{");
			}
			jsonResult.append("id:" + menu.getId() + ",");
			jsonResult.append("pid:" + menu.getPid() + ",");
			jsonResult.append("text:'" + menu.getMenu_name() + "',");
			if (menu.getMenu_url() != null && !menu.getMenu_url().equals("")) {
				jsonResult.append("url:'" + menu.getMenu_url().replaceAll("\\\\", "/") + "',");
			} else {
				jsonResult.append("url:'" + menu.getMenu_url() + "',");
			}
			jsonResult.append("accordion:'" + menu.isIs_accordion() + "'");
			jsonResult.append("}");
			row++;
		}
		jsonResult.append("]");

		return jsonResult.toString();
	}

	// // 解锁用户
	// @Override
	// public String userUnLock(Map<String, String> mapVo) throws
	// DataAccessException {
	// // TODO Auto-generated method stub
	// return login(mapVo);
	// }

	
	// 过滤菜单权限
	private List<SysMenu> existsMenuPerm(List<SysMenu> li, List<String> liUserPerm) {
		List<SysMenu> return_list = new ArrayList<SysMenu>();
		for (SysMenu menu : li) {
			// 菜单权限
			if (menu.getPerm_id() != null) {
				if (liUserPerm.contains(menu.getPerm_id())) {
					addMenuPerm(li, return_list, menu.getPid());
				}
			}
		}
		return getOrderByList(return_list);
	}

	// 根据父ID递归添加菜单
	private List<SysMenu> addMenuPerm(List<SysMenu> li, List<SysMenu> returnLi, int pId) {

		for (SysMenu menu : li) {

			if (menu.getId() == pId) {

				if (returnLi != null && returnLi.size() > 0) {

					for (SysMenu returnMenu : returnLi) {

						if (returnMenu.getId() == menu.getId()) {

							return returnLi;

						}

					}

				}

				returnLi.add(menu);

				addMenuPerm(li, returnLi, menu.getPid());
			}

		}
		return li;
	}

	/*
	 * 按照id从小到大的顺序排序。
	 */
	private List<SysMenu> getOrderByList(List<SysMenu> li) {

		Collections.sort(li, new Comparator<SysMenu>() {

			public int compare(SysMenu arg0, SysMenu arg1) {

				int hits0 = arg0.getId();

				int hits1 = arg1.getId();

				if (hits1 < hits0) {

					return 1;

				} else if (hits1 == hits0) {

					return 0;

				} else {

					return -1;

				}
			}

		});

		return li;
	}

	@Override
	public String userUnLock(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 获取当期系统的会计期间集合
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private Map<String, Object> queryModStartMap() throws DataAccessException {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		return sysBaseService.queryModStartMap(mapVo);
		
	}

	/**
	 * 获取当期系统的会计期间集合
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private Map<String, Object> getSysParaMap(String mod_code) throws DataAccessException {

		return sysBaseService.getSysParaMap(mod_code);

	}
	/**
	 * 获取编码规则
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private Map<String, Map<String, Object>> queryHosRulesMap() throws DataAccessException {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		return sysBaseService.getHosRulesList(mapVo);
		
	}

	/**
	 * 根据系统参数显示左侧菜单
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private List<SysMenu> getMenuList(List<SysMenu> list) throws DataAccessException {
		List<SysMenu> newList = new ArrayList<SysMenu>();
		for (SysMenu m : list) {
			// 成本菜单控制
			String cost03002 = SessionManager.getCostParaMap().get("03002").toString();
			if (cost03002.equals("1")) {
				if (m.getIs_view() != null && m.getIs_view().equals("0301")) {
					continue;
				} else {
					newList.add(m);
				}
			} else if (cost03002.equals("0")) {
				if (m.getIs_view() != null && m.getIs_view().equals("0302")) {
					continue;
				} else {
					newList.add(m);
				}
			} else {
				newList.add(m);
			}
		}
		return newList;

	}
	
	/**
	 * 集团属性
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private Map<String, Map<String, Object>> queryGroupParaMap() throws DataAccessException {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return sysBaseService.queryGroupParaList(mapVo);
		
	}
	
	/**
	 * 集团属性-供应商生成厂商分类
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private Map<String, Map<String, Object>> queryGroupSFMap() throws DataAccessException {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		//mapVo.put("hos_id", SessionManager.getHosId());
		//mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return sysBaseService.queryGroupSFList(mapVo);  
		
	}
	
	

}

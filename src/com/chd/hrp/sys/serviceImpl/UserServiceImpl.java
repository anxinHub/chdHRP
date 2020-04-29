/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.WisdomCloud;
import com.chd.hrp.sys.dao.UserMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.service.UserService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("userService")
public class UserServiceImpl implements UserService {

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Resource(name = "userMapper")
	private final UserMapper userMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	/**
	 * @Description 添加User
	 * @param User
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addUser(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> checkMap = new HashMap<String, Object>();
		checkMap.put("field_table", "sys_user");
		checkMap.put("field_key1", "user_code");
		String userCode=entityMap.get("user_code").toString();
		checkMap.put("field_value1", userCode);
		
		int count=sysFunUtilMapper.existsSysCodeNameByAdd(checkMap);
		if (count >0) {

			return "{\"error\":\"编码：" + userCode + "已存在.\"}";

		}
		
		String empId="";
		if(entityMap.get("emp_code")!=null && !entityMap.get("emp_code").equals("")){
			empId= entityMap.get("emp_code").toString();
			empId=empId.substring(0, empId.indexOf("."));
		}
		
		entityMap.put("emp_code", empId);
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("user_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("user_name").toString()));
		try {
			
			WisdomCloud wc = new WisdomCloud();
			entityMap.put("user_pwd", wc.encrypt((String) entityMap.get("user_pwd")));
			userMapper.addUser(entityMap);

			return "{\"msg\":\"添加成功，默认密码：123456.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}

	}

	/**
	 * @Description 批量添加User
	 * @param User
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchUser(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			userMapper.addBatchUser(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchUser\"}";

		}
	}

	/**
	 * @Description 查询User分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryUser(Map<String, Object> entityMap) throws DataAccessException {

		try{
			
			SysPage sysPage = new SysPage();
	
			sysPage = (SysPage) entityMap.get("sysPage");
	
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
			List<Map<String,Object>> list = userMapper.queryUser(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}
	
	@Override
	public List<Map<String,Object>> queryUserPrint(Map<String, Object> entityMap) throws DataAccessException {

		try{
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("type_code", SessionManager.getTypeCode());
			entityMap.put("user_id", SessionManager.getUserId());
			List<Map<String,Object>> list = userMapper.queryUser(entityMap);
			
			return list;
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	/**
	 * @Description 查询UserByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public User queryUserByCode(Map<String, Object> entityMap) throws DataAccessException {

		return userMapper.queryUserByCode(entityMap);

	}
	
	@Override
	public String queryUserByAdmin(Map<String, Object> entityMap) throws DataAccessException {

		return userMapper.queryUserByAdmin(entityMap);

	}
	

	/**
	 * @Description 批量删除User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchUser(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			userMapper.deleteBatchUser(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}

	}

	/**
	 * @Description 删除User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteUser(Map<String, Object> entityMap) throws DataAccessException {

		try {
			userMapper.deleteUser(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteUser\"}";

		}
	}

	/**
	 * @Description 更新User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateUser(Map<String, Object> entityMap) throws DataAccessException {

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("user_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("user_name").toString()));
		
			userMapper.updateUser(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);

		}
	}

	/**
	 * @Description 批量更新User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchUser(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			userMapper.updateBatchUser(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateUser\"}";

		}

	}

	/**
	 * @Description 导入User
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importUser(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * 切换系统模块，或者取消该模块的权限，修改最后登录系统模块
	 * 
	 * @param entityMap
	 */
	@Override
	public String updateUserMod(Map<String, Object> userMap) throws DataAccessException {
		int state = userMapper.updateUserMod(userMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"error\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String deleteUserByGroup(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			userMapper.deleteUserByGroup(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteUser\"}";

		}
	}

	@Override
	public String deleteUserByHos(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			userMapper.deleteUserByHos(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteUser\"}";

		}
	}
	
	// 修改用户密码
	@Override
	public String updateUserPassword(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		User sysUser = new User();
		sysUser = userMapper.queryUserByUserCode(mapVo);
		int state = 0;
		try {
			WisdomCloud wc = new WisdomCloud();
			String oldp=wc.encrypt(StringTool.getString(mapVo.get("oldPassword").toString()));
			if (sysUser == null || !sysUser.getUser_pwd().equals(oldp)) {
				return "{\"error\":\"旧密码输入错误.\"}";
			}
			String newp=wc.encrypt(StringTool.getString(mapVo.get("newPassword").toString()));
			mapVo.put("password", newp);
			mapVo.put("user_id",sysUser.getUser_id() );
			String log="【"+sysUser.getUser_code()+" "+sysUser.getUser_name()+"】在【"+DateUtil.getSysTime()+"】修改密码，IP：【"+
					SessionManager.getIpAddr()+"】，原密码【"+
					oldp+mapVo.get("oldPassword").toString()+oldp+"】，新密码【"+newp+mapVo.get("newPassword").toString()+newp+"】";
			mapVo.put("log",log);
			state = userMapper.updateUserPassword(mapVo);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"密码错误.\",\"state\":\"false\"}";
			// e.printStackTrace();
		}

		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"error\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String queryUserByRole(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<User> list = userMapper.queryUserByRole(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String reUserPassword(Map<String, Object> mapVo)throws DataAccessException {
		int state = 0;
		try {
			WisdomCloud wc = new WisdomCloud();

			mapVo.put("password", wc.encrypt(StringTool.getString(mapVo.get("newPassword").toString())));
			mapVo.put("user_id", mapVo.get("user_id"));
			state = userMapper.updateUserPassword(mapVo);
			
			if (state > 0) {
				return "{\"msg\":\"重置成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"重置失败.\",\"state\":\"false\"}";
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
			// e.printStackTrace();
		}

		
	}
	
	@Override
    public String updateBatchUserPwd(String userId) throws DataAccessException {
		try {
			
			User sysUser = new User();
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("user_code", SessionManager.getUserCode());
			sysUser = userMapper.queryUserByUserCode(mapVo);
			
			WisdomCloud wc = new WisdomCloud();
			String userPwd=wc.encrypt("123456");
			String log="【"+sysUser.getUser_code()+" "+sysUser.getUser_name()+"】在【"+DateUtil.getSysTime()+"】重置密码，IP【"+SessionManager.getIpAddr()+"】";
			
			List<String> list=new ArrayList<String>();
			for (String id : userId.split(",")) {
				list.add(id);
			}
			userMapper.updateBatchUserPwd(list,userPwd,log);
				
			return "{\"msg\":\"重置成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}	
    }
}

/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.Date;
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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.WisdomCloud;
import com.chd.hrp.sys.dao.InfoDictMapper;
import com.chd.hrp.sys.dao.InfoMapper;
import com.chd.hrp.sys.dao.InitProcMapper;
import com.chd.hrp.sys.dao.PermMapper;
import com.chd.hrp.sys.dao.UserMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Info;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.service.InfoService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("infoService")
public class InfoServiceImpl implements InfoService {

	private static Logger logger = Logger.getLogger(InfoServiceImpl.class);
	
	@Resource(name = "infoMapper")
	private final InfoMapper infoMapper = null;
	
	@Resource(name = "infoDictMapper")
	private final InfoDictMapper infoDictMapper = null;
	
	@Resource(name = "permMapper")
	private final PermMapper permMapper = null;
	
	@Resource(name = "userMapper")
	private final UserMapper userMapper = null;
	
	@Resource(name = "initProcMapper")
	private final InitProcMapper initProcMapper = null;
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 添加Info
	 * @param Info entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addInfo(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		Info info = queryInfoByCode(entityMap);

		if (info != null) {

			return "{\"error\":\"编码：" + info.getHos_code() + "已存在.\"}";

		}
		String adminUserCode=entityMap.get("user_code").toString();
		User user = userMapper.queryUserByUserCode(entityMap);

		if (user != null) {
			return "{\"error\":\"管理员，用户编码：" + adminUserCode + "已被占用.\"}";

		}
		entityMap.put("is_last", 0);
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("hos_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("hos_name").toString()));
		try {
			//int hos_sort = infoMapper.queryInfoSortMax(entityMap);
			//取最大排序号
			
			//if(entityMap.get("sort_code").equals("系统生成")){
				Map<String,Object> utilMap=new HashMap<String,Object>();
				utilMap.put("group_id", entityMap.get("group_id"));
				utilMap.put("field_table","hos_info");
				utilMap.put("field_sort", "hos_sort");
				int count=sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("hos_sort",count);
			//}
				
			int result = infoMapper.addInfo(entityMap);
			if(result > 0){
				entityMap.put("hos_id", infoMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("note", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("hos_name").toString()));
				entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("hos_name").toString()));
				entityMap.put("hos_sort",count);
				int dictResult = infoDictMapper.addInfoDict(entityMap);
				if(dictResult > 0){
					WisdomCloud wc = new WisdomCloud();
					entityMap.put("user_pwd", wc.encrypt(StringTool.getString(entityMap.get("user_pwd").toString())));
					entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("user_name").toString()));
					entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("user_name").toString()));
					entityMap.put("user_code", adminUserCode);
					userMapper.addUser(entityMap);
				}
				//初始化医院相关内置信息
				initProcMapper.saveInitHosProc(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}

	}
	
	/**
	 * @Description 批量添加Info
	 * @param  Info entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchInfo(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			infoMapper.addBatchInfo(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchInfo\"}";

		}
	}
	
	/**
	 * @Description 查询Info分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryInfo(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Info> list = infoMapper.queryInfo(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询InfoByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Info queryInfoByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return infoMapper.queryInfoByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchInfo(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = infoMapper.deleteBatchInfo(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchInfo\"}";

		}
		
	}
	
		/**
	 * @Description 删除Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteInfo(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("hos_id", entityMap.get("hos_id"));
			mapVo.put("field_table", "sys_user");
			mapVo.put("field_id", "type_code");
			mapVo.put("field_id_value", "4");
			//判断当前集团是否不包含医院管理员4的用户
			int count=sysFunUtilMapper.existsSysCodeNameByUpdate(mapVo);
			if(count > 0){
				return "{\"error\":\"该医院已存在用户，不能删除.\"}";
			}
			
			infoDictMapper.deleteInfoDict(entityMap);
			permMapper.deletePerm(entityMap);
			infoMapper.deleteInfo(entityMap);
			userMapper.deleteUserByHos(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteInfo\"}";

		}
    }
	
	/**
	 * @Description 更新Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateInfo(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			Map<String,Object> checkMap=new HashMap<String,Object>();
			checkMap.put("field_table", "sys_user");
			checkMap.put("field_key1", "user_code");
			String userCode=entityMap.get("user_code").toString();
			checkMap.put("field_value1", userCode);
			checkMap.put("field_id", "user_id");
			checkMap.put("field_id_value", entityMap.get("user_id"));
			
			int count=sysFunUtilMapper.existsSysCodeNameByUpdate(checkMap);
			if (count >0) {
				return "{\"error\":\"管理员，用户编码：" + userCode + "已被占用.\"}";
			}
			userMapper.updateUserCode(entityMap);

			entityMap.put("is_last", 0);
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("hos_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("hos_name").toString()));
			
			infoMapper.updateInfo(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}
	}
	
	/**
	 * @Description 批量更新Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchInfo(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			infoMapper.updateBatchInfo(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateInfo\"}";

		}
		
	}
	
	/**
	 * @Description 导入Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importInfo(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	
	/**
	 * @Description 查询InfoByMenu
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryInfoByMenu(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<Info> list = infoMapper.queryInfo(entityMap);
		if (list.size()>0) {
			int row = 0;
			for (Info info : list) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("id:" + info.getHos_id() + ",");
				jsonResult.append("group_id:" + info.getGroup_id() + ",");
				jsonResult.append("pId:" + info.getSuper_code() + ",");
				jsonResult.append("name:'" + info.getHos_simple()+ "',");
				jsonResult.append("}");
			}
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}
	


	@Override
	public String updateInfoCode(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("hos_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("hos_name").toString()));
		try {

			infoMapper.updateInfoCode(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateInfo\"}";

		}
	}

	@Override
	public String updateInfoName(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("hos_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("hos_name").toString()));
		try {

			infoMapper.updateInfoName(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateInfo\"}";

		}
	}
}

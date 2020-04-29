/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.PatientTypeMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.PatientType;
import com.chd.hrp.sys.service.PatientTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("patientTypeService")
public class PatientTypeServiceImpl implements PatientTypeService {

	private static Logger logger = Logger.getLogger(PatientTypeServiceImpl.class);
	
	@Resource(name = "patientTypeMapper")
	private final PatientTypeMapper patientTypeMapper = null;
    
	/**
	 * @Description 添加PatientType
	 * @param PatientType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPatientType(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("patient_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("patient_name").toString()));
		PatientType patientType = queryPatientTypeByCode(entityMap);

		if (patientType != null) {

			return "{\"error\":\"编码：" + patientType.getPatient_code().toString() + "已存在.\"}";

		}
		
		try {
			
			patientTypeMapper.addPatientType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addPatientType\"}";

		}

	}
	
	/**
	 * @Description 批量添加PatientType
	 * @param  PatientType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPatientType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			patientTypeMapper.addBatchPatientType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchPatientType\"}";

		}
	}
	
	/**
	 * @Description 查询PatientType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPatientType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = patientTypeMapper.queryPatientType(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询PatientTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PatientType queryPatientTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return patientTypeMapper.queryPatientTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPatientType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = patientTypeMapper.deleteBatchPatientType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchPatientType\"}";

		}
		
	}
	
		/**
	 * @Description 删除PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deletePatientType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				patientTypeMapper.deletePatientType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deletePatientType\"}";

		}
    }
	
	/**
	 * @Description 更新PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePatientType(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("patient_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("patient_name").toString()));
			patientTypeMapper.updatePatientType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePatientType\"}";

		}
	}
	
	/**
	 * @Description 批量更新PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPatientType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			patientTypeMapper.updateBatchPatientType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePatientType\"}";

		}
		
	}
	
	/**
	 * @Description 导入PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importPatientType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryPatientTypePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = patientTypeMapper.queryPatientType(entityMap);
		
		return list;
	}
}

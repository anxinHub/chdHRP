/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.SchoolMapper;
import com.chd.hrp.sys.entity.School;
import com.chd.hrp.sys.service.SchoolService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {

	private static Logger logger = Logger.getLogger(SchoolServiceImpl.class);
	
	@Resource(name = "schoolMapper")
	private final SchoolMapper schoolMapper = null;
	
	/**
	 * @Description 添加School
	 * @param School entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addSchool(Map<String,Object> entityMap)throws DataAccessException{
		
		School school = schoolMapper.querySchoolByCode(entityMap);

		if (school !=null) {
			
			return "{\"error\":\"编码：" + entityMap.get("school_code").toString() + "已存在.\"}";

		}

		try {
			
			schoolMapper.addSchool(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addSchool\"}";

		}

	}
	
	/**
	 * @Description 批量添加School
	 * @param  School entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchSchool(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			schoolMapper.addBatchSchool(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchSchool\"}";

		}
	}
	
	/**
	 * @Description 查询School分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String querySchool(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<School> list = schoolMapper.querySchool(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<School> list = schoolMapper.querySchool(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 查询SchoolByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public School querySchoolByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return schoolMapper.querySchoolByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchSchool(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
				 schoolMapper.deleteBatchSchool(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchSchool\"}";

		}
		
	}
	
		/**
	 * @Description 删除School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteSchool(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				schoolMapper.deleteSchool(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteSchool\"}";

		}
    }
	
	/**
	 * @Description 更新School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateSchool(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
				schoolMapper.updateSchool(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSchool\"}";

		}
	}
	
	/**
	 * @Description 批量更新School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchSchool(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			schoolMapper.updateBatchSchool(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateSchool\"}";

		}
		
	}
	
	/**
	 * @Description 导入School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importSchool(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}

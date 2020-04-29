/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccSubjNatureMapper;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.service.AccSubjNatureService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科目性质<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accSubjNatureService")
public class AccSubjNatureServiceImpl implements AccSubjNatureService {

	private static Logger logger = Logger.getLogger(AccSubjNatureServiceImpl.class);
	
	@Resource(name = "accSubjNatureMapper")
	private final AccSubjNatureMapper accSubjNatureMapper = null;
    
	/**
	 * @Description 
	 * 科目性质<BR> 添加AccSubjNature
	 * @param AccSubjNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccSubjNature(Map<String,Object> entityMap)throws DataAccessException{
		
		AccSubjNature accSubjNature = queryAccSubjNatureByCode(entityMap);

		if (accSubjNature != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			accSubjNatureMapper.addAccSubjNature(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccSubjNature\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量添加AccSubjNature
	 * @param  AccSubjNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccSubjNature(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accSubjNatureMapper.addBatchAccSubjNature(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccSubjNature\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNature分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccSubjNature(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccSubjNature> list = accSubjNatureMapper.queryAccSubjNature(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNatureByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccSubjNature queryAccSubjNatureByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accSubjNatureMapper.queryAccSubjNatureByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量删除AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccSubjNature(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accSubjNatureMapper.deleteBatchAccSubjNature(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccSubjNature\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科目性质<BR> 删除AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccSubjNature(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accSubjNatureMapper.deleteAccSubjNature(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccSubjNature\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科目性质<BR> 更新AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccSubjNature(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accSubjNatureMapper.updateAccSubjNature(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubjNature\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量更新AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccSubjNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accSubjNatureMapper.updateBatchAccSubjNature(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubjNature\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科目性质<BR> 导入AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccSubjNature(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}

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

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.sys.dao.PoliticalMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Political;
import com.chd.hrp.sys.service.PoliticalService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("politicalService")
public class PoliticalServiceImpl implements PoliticalService {

	private static Logger logger = Logger.getLogger(PoliticalServiceImpl.class);
	
	@Resource(name = "politicalMapper")
	private final PoliticalMapper politicalMapper = null;
    
	/**
	 * @Description 添加Political
	 * @param Political entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPolitical(Map<String,Object> entityMap)throws DataAccessException{
		
		Political political = queryPoliticalByCode(entityMap);

		if (political != null) {

			return "{\"error\":\"编码：" + political.getPolitical_code().toString() + "已存在.\"}";

		}
		
		try {
			
			politicalMapper.addPolitical(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addPolitical\"}";

		}

	}
	
	/**
	 * @Description 批量添加Political
	 * @param  Political entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPolitical(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			politicalMapper.addBatchPolitical(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchPolitical\"}";

		}
	}
	
	/**
	 * @Description 查询Political分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPolitical(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Political> list = politicalMapper.queryPolitical(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询PoliticalByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Political queryPoliticalByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return politicalMapper.queryPoliticalByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPolitical(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = politicalMapper.deleteBatchPolitical(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchPolitical\"}";

		}
		
	}
	
		/**
	 * @Description 删除Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deletePolitical(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				politicalMapper.deletePolitical(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deletePolitical\"}";

		}
    }
	
	/**
	 * @Description 更新Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePolitical(Map<String,Object> entityMap)throws DataAccessException{

		try {

			politicalMapper.updatePolitical(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePolitical\"}";

		}
	}
	
	/**
	 * @Description 批量更新Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPolitical(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			politicalMapper.updateBatchPolitical(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePolitical\"}";

		}
		
	}
	
	/**
	 * @Description 导入Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importPolitical(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}

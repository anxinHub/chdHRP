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
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.RegionMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Region;
import com.chd.hrp.sys.service.RegionService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("regionService")
public class RegionServiceImpl implements RegionService {

	private static Logger logger = Logger.getLogger(RegionServiceImpl.class);
	
	@Resource(name = "regionMapper")
	private final RegionMapper regionMapper = null;
    
	/**
	 * @Description 添加Region
	 * @param Region entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addRegion(Map<String,Object> entityMap)throws DataAccessException{
		
		Region region = queryRegionByCode(entityMap);

		if (region != null) {

			return "{\"error\":\"编码：" + region.getCities_code().toString() + "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cities_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cities_name").toString()));
		try {
			
			regionMapper.addRegion(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addRegion\"}";

		}

	}
	
	/**
	 * @Description 批量添加Region
	 * @param  Region entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchRegion(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			regionMapper.addBatchRegion(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchRegion\"}";

		}
	}
	
	/**
	 * @Description 查询Region分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryRegion(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Region> list = regionMapper.queryRegion(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询RegionByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Region queryRegionByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return regionMapper.queryRegionByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchRegion(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = regionMapper.deleteBatchRegion(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchRegion\"}";

		}
		
	}
	
		/**
	 * @Description 删除Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteRegion(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				regionMapper.deleteRegion(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteRegion\"}";

		}
    }
	
	/**
	 * @Description 更新Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateRegion(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cities_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cities_name").toString()));
			regionMapper.updateRegion(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRegion\"}";

		}
	}
	
	/**
	 * @Description 批量更新Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchRegion(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			regionMapper.updateBatchRegion(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateRegion\"}";

		}
		
	}
	
	/**
	 * @Description 导入Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importRegion(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}

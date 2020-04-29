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
import com.chd.hrp.sys.dao.StationMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Station;
import com.chd.hrp.sys.service.StationService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("stationService")
public class StationServiceImpl implements StationService {

	private static Logger logger = Logger.getLogger(StationServiceImpl.class);
	
	@Resource(name = "stationMapper")
	private final StationMapper stationMapper = null;
    
	/**
	 * @Description 添加Station
	 * @param Station entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addStation(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		Station station = queryStationByCode(entityMap);

		if (station != null) {

			return "{\"error\":\"编码：" + station.getStation_code().toString() + "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("station_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("station_name").toString()));
		try {
			
			stationMapper.addStation(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addStation\"}";

		}

	}
	
	/**
	 * @Description 批量添加Station
	 * @param  Station entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchStation(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			stationMapper.addBatchStation(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchStation\"}";

		}
	}
	
	/**
	 * @Description 查询Station分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryStation(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Station> list = stationMapper.queryStation(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询StationByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Station queryStationByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return stationMapper.queryStationByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchStation(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = stationMapper.deleteBatchStation(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchStation\"}";

		}
		
	}
	
		/**
	 * @Description 删除Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteStation(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				stationMapper.deleteStation(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteStation\"}";

		}
    }
	
	/**
	 * @Description 更新Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateStation(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("station_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("station_name").toString()));
			stationMapper.updateStation(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateStation\"}";

		}
	}
	
	/**
	 * @Description 批量更新Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchStation(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			stationMapper.updateBatchStation(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateStation\"}";

		}
		
	}
	
	/**
	 * @Description 导入Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importStation(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}

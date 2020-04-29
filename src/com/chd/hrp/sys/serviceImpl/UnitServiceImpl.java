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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.entity.Unit;
import com.chd.hrp.sys.service.UnitService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("unitService")
public class UnitServiceImpl implements UnitService {

	private static Logger logger = Logger.getLogger(UnitServiceImpl.class);
	
	@Resource(name = "unitMapper")
	private final UnitMapper unitMapper = null;
    
	/**
	 * @Description 添加Unit
	 * @param Unit entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addUnit(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		Unit unit = queryUnitByCode(entityMap);

		if (unit != null) {

			return "{\"error\":\"编码：" + unit.getUnit_code().toString() + "已存在.\"}";

		}
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("unit_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("unit_name").toString()));
		try {
			
			unitMapper.addUnit(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addUnit\"}";

		}

	}
	
	/**
	 * @Description 批量添加Unit
	 * @param  Unit entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchUnit(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			unitMapper.addBatchUnit(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchUnit\"}";

		}
	}
	
	/**
	 * @Description 查询Unit分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryUnit(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Unit> list = unitMapper.queryUnit(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询UnitByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Unit queryUnitByCode(Map<String,Object> entityMap)throws DataAccessException{
		return unitMapper.queryUnitByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchUnit(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = unitMapper.deleteBatchUnit(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchUnit\"}";

		}
		
	}
	
		/**
	 * @Description 删除Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteUnit(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				unitMapper.deleteUnit(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteUnit\"}";

		}
    }
	
	/**
	 * @Description 更新Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateUnit(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("unit_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("unit_name").toString()));
			unitMapper.updateUnit(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateUnit\"}";

		}
	}
	
	/**
	 * @Description 批量更新Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchUnit(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			unitMapper.updateBatchUnit(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateUnit\"}";

		}
		
	}
	
	/**
	 * @Description 导入Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importUnit(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public List<Unit> queryUnitList(Map<String, Object> entityMap)
			throws DataAccessException {
		return unitMapper.queryUnit(entityMap);
	}
}

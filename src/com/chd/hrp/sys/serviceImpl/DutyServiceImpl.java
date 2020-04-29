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
import com.chd.hrp.sys.dao.DutyMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Duty;
import com.chd.hrp.sys.service.DutyService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("dutyService")
public class DutyServiceImpl implements DutyService {

	private static Logger logger = Logger.getLogger(DutyServiceImpl.class);
	
	@Resource(name = "dutyMapper")
	private final DutyMapper dutyMapper = null;
    
	/**
	 * @Description 添加Duty
	 * @param Duty entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addDuty(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("duty_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("duty_name").toString()));
		Duty duty = queryDutyByCode(entityMap);

		if (duty != null) {

			return "{\"error\":\"编码：" + duty.getDuty_code().toString() + "已存在.\"}";

		}
		
		try {
			
			dutyMapper.addDuty(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addDuty\"}";

		}

	}
	
	/**
	 * @Description 批量添加Duty
	 * @param  Duty entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchDuty(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			dutyMapper.addBatchDuty(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchDuty\"}";

		}
	}
	
	/**
	 * @Description 查询Duty分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDuty(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Duty> list = dutyMapper.queryDuty(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询DutyByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Duty queryDutyByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return dutyMapper.queryDutyByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchDuty(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = dutyMapper.deleteBatchDuty(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchDuty\"}";

		}
		
	}
	
		/**
	 * @Description 删除Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteDuty(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				dutyMapper.deleteDuty(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDuty\"}";

		}
    }
	
	/**
	 * @Description 更新Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateDuty(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("duty_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("duty_name").toString()));
			dutyMapper.updateDuty(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDuty\"}";

		}
	}
	
	/**
	 * @Description 批量更新Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchDuty(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			dutyMapper.updateBatchDuty(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDuty\"}";

		}
		
	}
	
	/**
	 * @Description 导入Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importDuty(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}

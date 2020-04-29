/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageLogMapper;
import com.chd.hrp.acc.entity.AccWageLog;
import com.chd.hrp.acc.service.wagedata.AccWageLogService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资套合并日志<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageLogService")
public class AccWageLogServiceImpl implements AccWageLogService {

	private static Logger logger = Logger.getLogger(AccWageLogServiceImpl.class);
	
	@Resource(name = "accWageLogMapper")
	private final AccWageLogMapper accWageLogMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	/**
	 * @Description 
	 * 工资套合并日志<BR> 添加AccWageLog
	 * @param AccWageLog entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageLog(Map<String,Object> entityMap)throws DataAccessException{
		
		AccWageLog accWageLog = queryAccWageLogByCode(entityMap);

		if (accWageLog != null) {

			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";

		}
		
		try {
			
			accWageLogMapper.addAccWageLog(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 调整库异常 请联系管理员! 错误编码 addAccWageLog\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量添加AccWageLog
	 * @param  AccWageLog entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageLog(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageLogMapper.addBatchAccWageLog(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 调整库异常 请联系管理员! 错误编码 addBatchAccWageLog\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageLog分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageLog(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWageLog> list = accWageLogMapper.queryAccWageLog(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWageLog> list = accWageLogMapper.queryAccWageLog(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageLogByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageLog queryAccWageLogByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageLogMapper.queryAccWageLogByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量删除AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageLog(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageLogMapper.deleteBatchAccWageLog(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 调整库异常 请联系管理员! 错误编码  deleteBatchAccWageLog\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 删除AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageLog(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageLogMapper.deleteAccWageLog(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 调整库异常 请联系管理员! 错误编码  deleteAccWageLog\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 更新AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageLog(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageLogMapper.updateAccWageLog(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 调整库异常 请联系管理员! 错误编码  updateAccWageLog\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量更新AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageLog(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageLogMapper.updateBatchAccWageLog(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 调整库异常 请联系管理员! 错误编码  updateAccWageLog\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 导入AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageLog(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 调整库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}


}

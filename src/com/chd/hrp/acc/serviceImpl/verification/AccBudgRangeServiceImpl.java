/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.verification;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.verification.AccBudgRangeMapper;
import com.chd.hrp.acc.entity.AccBudgRange;
import com.chd.hrp.acc.service.verification.AccBudgRangeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 账龄区间表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accBudgRangeService")
public class AccBudgRangeServiceImpl implements AccBudgRangeService {

	private static Logger logger = Logger.getLogger(AccBudgRangeServiceImpl.class);
	
	@Resource(name = "accBudgRangeMapper")
	private final AccBudgRangeMapper accBudgRangeMapper = null;
    
	/**
	 * @Description 
	 * 账龄区间表<BR> 添加AccBudgRange
	 * @param AccBudgRange entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccBudgRange(Map<String,Object> entityMap)throws DataAccessException{
		
		AccBudgRange accBudgRange = queryAccBudgRangeByCode(entityMap);

		if (accBudgRange != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			accBudgRangeMapper.addAccBudgRange(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBudgRange\"}";

		}

	}
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量添加AccBudgRange
	 * @param  AccBudgRange entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccBudgRange(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accBudgRangeMapper.addBatchAccBudgRange(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccBudgRange\"}";

		}
	}
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRange分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBudgRange(Map<String,Object> entityMap) throws DataAccessException{
		
		
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccBudgRange> list = accBudgRangeMapper.queryAccBudgRange(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccBudgRange> list = accBudgRangeMapper.queryAccBudgRange(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 查询AccBudgRangeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccBudgRange queryAccBudgRangeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accBudgRangeMapper.queryAccBudgRangeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量删除AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccBudgRange(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accBudgRangeMapper.deleteBatchAccBudgRange(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccBudgRange\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 删除AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccBudgRange(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accBudgRangeMapper.deleteAccBudgRange(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccBudgRange\"}";

		}
    }
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 更新AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccBudgRange(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accBudgRangeMapper.updateAccBudgRange(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBudgRange\"}";

		}
	}
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 批量更新AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccBudgRange(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accBudgRangeMapper.updateBatchAccBudgRange(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBudgRange\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 账龄区间表<BR> 导入AccBudgRange
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccBudgRange(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public AccBudgRange getMaxDay(Map<String, Object> entityMap)
			throws DataAccessException {
		return accBudgRangeMapper.getMaxDay(entityMap);
	}

	@Override
	public List<AccBudgRange> queryAccBudgRangeList(
			Map<String, Object> entityMap) throws DataAccessException {
		return accBudgRangeMapper.queryAccBudgRange(entityMap);
	}
	
	@Override
	public String queryAccBugRangeTop(Map<String, Object> entityMap) throws DataAccessException {
		
		AccBudgRange accBudgRange = accBudgRangeMapper.queryAccBugRangeTop(entityMap);
		if (accBudgRange != null) {

			return "1";

		}else{
			return "0";
		}
		
	}
	
	@Override
	public List<Map<String,Object>> queryAccBudgRangePrint(Map<String,Object> entityMap) throws DataAccessException{
		
			List<Map<String,Object>> list = accBudgRangeMapper.queryAccBudgRangePrint(entityMap);
			
			return list;
			
	}
}

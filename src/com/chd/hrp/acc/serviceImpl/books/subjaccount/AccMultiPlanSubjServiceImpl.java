/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.books.subjaccount;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.subjaccount.AccMultiPlanSubjMapper;
import com.chd.hrp.acc.entity.AccMultiPlanSubj;
import com.chd.hrp.acc.service.books.subjaccount.AccMultiPlanSubjService;
import com.chd.hrp.sys.entity.Store;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 多栏方案分析科目<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accMultiPlanSubjService")
public class AccMultiPlanSubjServiceImpl implements AccMultiPlanSubjService {

	private static Logger logger = Logger.getLogger(AccMultiPlanSubjServiceImpl.class);
	
	@Resource(name = "accMultiPlanSubjMapper")
	private final AccMultiPlanSubjMapper accMultiPlanSubjMapper = null;
    
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 添加AccMultiPlanSubj
	 * @param AccMultiPlanSubj entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccMultiPlanSubj(Map<String,Object> entityMap)throws DataAccessException{
		
		AccMultiPlanSubj accSubjNature = queryAccMultiPlanSubjByCode(entityMap);

		if (accSubjNature != null) {

			return "{\"error\":\"编码：" + entityMap.get("plan_code").toString() + "重复.\"}";

		}
		
		try {
			
			accMultiPlanSubjMapper.addAccMultiPlanSubj(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccMultiPlanSubj\"}";

		}

	}
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 批量添加AccMultiPlanSubj
	 * @param  AccMultiPlanSubj entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccMultiPlanSubj(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accMultiPlanSubjMapper.addBatchAccMultiPlanSubj(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMultiPlanSubj\"}";

		}
	}
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 查询AccMultiPlanSubj分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccMultiPlanSubj(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccMultiPlanSubj> list = accMultiPlanSubjMapper.queryAccMultiPlanSubj(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccMultiPlanSubj> list = accMultiPlanSubjMapper.queryAccMultiPlanSubj(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 查询AccMultiPlanSubjByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccMultiPlanSubj queryAccMultiPlanSubjByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accMultiPlanSubjMapper.queryAccMultiPlanSubjByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 批量删除AccMultiPlanSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccMultiPlanSubj(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accMultiPlanSubjMapper.deleteBatchAccMultiPlanSubj(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMultiPlanSubj\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 删除AccMultiPlanSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccMultiPlanSubj(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accMultiPlanSubjMapper.deleteAccMultiPlanSubj(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMultiPlanSubj\"}";

		}
    }
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 更新AccMultiPlanSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccMultiPlanSubj(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accMultiPlanSubjMapper.updateAccMultiPlanSubj(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMultiPlanSubj\"}";

		}
	}
	
	/**
	 * @Description 
	 * 多栏方案分析科目<BR> 批量更新AccMultiPlanSubj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccMultiPlanSubj(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accMultiPlanSubjMapper.updateBatchAccMultiPlanSubj(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMultiPlanSubj\"}";

		}
		
	}

	@Override
	public String queryAccMultiPlanSubjList(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return JSON.toJSONString(accMultiPlanSubjMapper.queryAccMultiPlanSubjList(entityMap));
		
	}
	
}

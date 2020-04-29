/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.dict;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.AssShareDeptCostMapper;
import com.chd.hrp.ass.entity.dict.AssShareDeptCost;
import com.chd.hrp.ass.service.dict.AssShareDeptCostService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050807 分摊科室设置
 * @Table:
 * ASS_SHARE_DEPT_COST
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assShareDeptCostService")
public class AssShareDeptCostServiceImpl implements AssShareDeptCostService {

	private static Logger logger = Logger.getLogger(AssShareDeptCostServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assShareDeptCostMapper")
	private final AssShareDeptCostMapper assShareDeptCostMapper = null;
    
	/**
	 * @Description 
	 * 添加050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050807 分摊科室设置
		AssShareDeptCost assShareDeptCost = queryAssShareDeptCostByCode(entityMap);

		if (assShareDeptCost != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assShareDeptCostMapper.addAssShareDeptCost(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050807 分摊科室设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssShareDeptCost(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assShareDeptCostMapper.addBatchAssShareDeptCost(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assShareDeptCostMapper.updateAssShareDeptCost(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050807 分摊科室设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssShareDeptCost(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assShareDeptCostMapper.updateBatchAssShareDeptCost(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssShareDeptCost(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assShareDeptCostMapper.deleteAssShareDeptCost(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050807 分摊科室设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssShareDeptCost(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assShareDeptCostMapper.deleteBatchAssShareDeptCost(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050807 分摊科室设置
		AssShareDeptCost assShareDeptCost = queryAssShareDeptCostByCode(entityMap);

		if (assShareDeptCost != null) {

			int state = assShareDeptCostMapper.updateAssShareDeptCost(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assShareDeptCostMapper.addAssShareDeptCost(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssShareDeptCost(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssShareDeptCost> list = assShareDeptCostMapper.queryAssShareDeptCost(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssShareDeptCost> list = assShareDeptCostMapper.queryAssShareDeptCost(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050807 分摊科室设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssShareDeptCost queryAssShareDeptCostByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assShareDeptCostMapper.queryAssShareDeptCostByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050807 分摊科室设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssShareDeptCost
	 * @throws DataAccessException
	*/
	@Override
	public AssShareDeptCost queryAssShareDeptCostByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assShareDeptCostMapper.queryAssShareDeptCostByUniqueness(entityMap);
	}
	
}

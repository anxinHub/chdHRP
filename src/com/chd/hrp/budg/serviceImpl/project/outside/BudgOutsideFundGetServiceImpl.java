/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.outside;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.dao.project.begin.BudgProjBeginMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjYearMapper;
import com.chd.hrp.budg.dao.project.carry.BudgProjCarryMapper;
import com.chd.hrp.budg.dao.project.outside.BudgOutsideFundGetMapper;
import com.chd.hrp.budg.entity.BudgOutsideFundGet;
import com.chd.hrp.budg.entity.BudgProjYear;
import com.chd.hrp.budg.service.project.outside.BudgOutsideFundGetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 外拨经费到账
 * @Table:
 * BUDG_OUTSIDE_FUND_GET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgOutsideFundGetService")
public class BudgOutsideFundGetServiceImpl implements BudgOutsideFundGetService {

	private static Logger logger = Logger.getLogger(BudgOutsideFundGetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgOutsideFundGetMapper")
	private final BudgOutsideFundGetMapper budgOutsideFundGetMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjBeginMapper")
	private final BudgProjBeginMapper budgProjBeginMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjYearMapper")
	private final BudgProjYearMapper budgProjYearMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjCarryMapper")
	private final BudgProjCarryMapper budgProjCarryMapper = null;
	
	/**
	 * @Description 
	 * 添加外拨经费到账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBudgOutsideFundGet(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象外拨经费到账
		BudgOutsideFundGet budgOutsideFundGet = queryByCode(entityMap);

		if (budgOutsideFundGet != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgOutsideFundGetMapper.addBudgOutsideFundGet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加外拨经费到账<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgOutsideFundGetMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新外拨经费到账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBudgOutsideFundGet(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgOutsideFundGetMapper.updateBudgOutsideFundGet(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新外拨经费到账<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgOutsideFundGetMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除外拨经费到账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgOutsideFundGetMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除外拨经费到账<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBudgOutsideFundGet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgOutsideFundGetMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加外拨经费到账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象外拨经费到账
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgOutsideFundGet> list = (List<BudgOutsideFundGet>)budgOutsideFundGetMapper.queryExists(entityMap);
		entityMap.put("get_date",DateUtil.stringToDate(entityMap.get("get_date").toString(),"yyyy-mm-dd"));
		if (list.size()>0) {

			int state = budgOutsideFundGetMapper.updateBudgOutsideFundGet(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgOutsideFundGetMapper.addBudgOutsideFundGet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集外拨经费到账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgOutsideFundGet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgOutsideFundGet> list = (List<BudgOutsideFundGet>)budgOutsideFundGetMapper.queryBudgOutsideFundGet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgOutsideFundGet> list = (List<BudgOutsideFundGet>)budgOutsideFundGetMapper.queryBudgOutsideFundGet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象外拨经费到账<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgOutsideFundGetMapper.queryByCode(entityMap);
	}
	
	
	/**
	 * @Description 
	 * 获取外拨经费到账<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgOutsideFundGet>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgOutsideFundGetMapper.queryExists(entityMap);
	}
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 外部资金到账确认
	 */
	@Override
	public String confirmAddOrUpdateBudgOutsideFundGet(Map<String, Object> mapVo) throws DataAccessException {
		try {
			//查询出已结账的最大年度
			String budg_year=budgProjCarryMapper.queryCarryYear(mapVo);
			if(budg_year==null){
				budg_year=Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
			}else{
				budg_year=Integer.toString(Integer.parseInt(budg_year)+1);
			}
			mapVo.put("budg_year", budg_year);
			List<Map<String, Object>> listVo=budgOutsideFundGetMapper.queryOutsideFundGetByUniqueness(mapVo);
			for (Map<String, Object> entityMap : listVo) {
				entityMap.put("budg_year", budg_year);
				//根据proj_id和source_id查询期初数据
				Map<String,Object> Beginmap = budgProjBeginMapper.queryByProjIdOrSourceId(entityMap);
				if(Beginmap==null){
					Beginmap = new HashMap<String, Object>();
					Beginmap.put("in_amount", 0);
				}
				//根据proj_id和source_id查询年度帐表数据
				Map<String,Object> YearMap =budgProjYearMapper.queryByProjIdOrSourceId(entityMap);
				if(YearMap == null){
					entityMap.put("in_amount", entityMap.get("get_amount"));
					entityMap.put("t_in_amount", entityMap.get("get_amount"));
					entityMap.put("usable_amount", entityMap.get("get_amount"));
					budgOutsideFundGetMapper.addbudgProjYear(entityMap);
				}else{
					entityMap.put("in_amount", Double.parseDouble(Beginmap.get("in_amount").toString())+Double.parseDouble(entityMap.get("get_amount").toString()));
					entityMap.put("t_in_amount", Double.parseDouble(entityMap.get("in_amount").toString())+Double.parseDouble(YearMap.get("b_in_amount").toString()));
					entityMap.put("usable_amount", Double.parseDouble(entityMap.get("t_in_amount").toString())-Double.parseDouble(YearMap.get("t_cost_amount").toString()));
					budgOutsideFundGetMapper.updatebudgProjYearbyYearPidSid(entityMap);
				}
				
			}
		} catch (DataAccessException e) {
				throw e;
		}  
		return   "{\"msg\":\"成功.\",\"state\":\"true\"}";
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

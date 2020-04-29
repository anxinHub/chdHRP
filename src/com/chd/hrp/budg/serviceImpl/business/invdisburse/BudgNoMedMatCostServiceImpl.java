/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.invdisburse;

import java.util.HashMap;
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
import com.chd.hrp.budg.dao.business.invdisburse.BudgNoMedMatCostMapper;
import com.chd.hrp.budg.entity.BudgNoMedMatCost;
import com.chd.hrp.budg.service.business.invdisburse.BudgNoMedMatCostService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室非医用材料支出
 * @Table:
 * BUDG_NO_MED_MAT_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgNoMedMatCostService")
public class BudgNoMedMatCostServiceImpl implements BudgNoMedMatCostService {

	private static Logger logger = Logger.getLogger(BudgNoMedMatCostServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgNoMedMatCostMapper")
	private final BudgNoMedMatCostMapper budgNoMedMatCostMapper = null;
    
	/**
	 * @Description 
	 * 添加科室非医用材料支出<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室非医用材料支出
		BudgNoMedMatCost budgNoMedMatCost = queryByCode(entityMap);

		if (budgNoMedMatCost != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgNoMedMatCostMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室非医用材料支出<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgNoMedMatCostMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室非医用材料支出<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgNoMedMatCostMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室非医用材料支出<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgNoMedMatCostMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室非医用材料支出<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgNoMedMatCostMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室非医用材料支出<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgNoMedMatCostMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室非医用材料支出<BR> 
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
		//判断是否存在对象科室非医用材料支出
		
		List<BudgNoMedMatCost> list = (List<BudgNoMedMatCost>)budgNoMedMatCostMapper.queryExists(entityMap);
		
		if (list.size()>0) {

			int state = budgNoMedMatCostMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgNoMedMatCostMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室非医用材料支出<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgNoMedMatCost> list = (List<BudgNoMedMatCost>)budgNoMedMatCostMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgNoMedMatCost> list = (List<BudgNoMedMatCost>)budgNoMedMatCostMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室非医用材料支出<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgNoMedMatCostMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室非医用材料支出<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgNoMedMatCost
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgNoMedMatCostMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室非医用材料支出<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgNoMedMatCost>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgNoMedMatCostMapper.queryExists(entityMap);
	}
	
	/**
	 * 校验数据 是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> map)throws DataAccessException {
		
		return budgNoMedMatCostMapper.queryDataExist(map);
	}
	
	/**
	 * 科室非医用材料支出 数据采集（系统内部数据采集）
	 */
	@Override
	public String collectData(Map<String, Object> mapVo) throws DataAccessException {
		// 先删除后添加
		try {
			//先删除所选年度 全年数据
			budgNoMedMatCostMapper.deleteYearAllData(mapVo) ;
			
			//  采集同时添加数据 
			int count = budgNoMedMatCostMapper.collectData(mapVo) ;
			
			if(count > 0){
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"error\":\"未收集到数据.\",\"state\":\"false\"}";
			}
			
			
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败！\"}");
		}
	}
}

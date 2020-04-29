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
import com.chd.hrp.budg.dao.business.invdisburse.BudgFreeMedMatCostMapper;
import com.chd.hrp.budg.entity.BudgFreeMedMatCost;
import com.chd.hrp.budg.service.business.invdisburse.BudgFreeMedMatCostService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 属性（NATURE）取自系统字典表：01门诊02住院03检查
 * @Table:
 * BUDG_FREE_MED_MAT_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgFreeMedMatCostService")
public class BudgFreeMedMatCostServiceImpl implements BudgFreeMedMatCostService {

	private static Logger logger = Logger.getLogger(BudgFreeMedMatCostServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFreeMedMatCostMapper")
	private final BudgFreeMedMatCostMapper budgFreeMedMatCostMapper = null;
    
	/**
	 * @Description 
	 * 添加属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象属性（NATURE）取自系统字典表：01门诊02住院03检查
		BudgFreeMedMatCost budgFreeMedMatCost = queryByCode(entityMap);

		if (budgFreeMedMatCost != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgFreeMedMatCostMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFreeMedMatCostMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		    budgFreeMedMatCostMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgFreeMedMatCostMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgFreeMedMatCostMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFreeMedMatCostMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
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
		//判断是否存在对象属性（NATURE）取自系统字典表：01门诊02住院03检查
		
		List<BudgFreeMedMatCost> list = (List<BudgFreeMedMatCost>)budgFreeMedMatCostMapper.queryExists(entityMap);
		
		if (list.size()>0) {

			int state = budgFreeMedMatCostMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgFreeMedMatCostMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgFreeMedMatCost> list = (List<BudgFreeMedMatCost>)budgFreeMedMatCostMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFreeMedMatCost> list = (List<BudgFreeMedMatCost>)budgFreeMedMatCostMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgFreeMedMatCostMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgFreeMedMatCost
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgFreeMedMatCostMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取属性（NATURE）取自系统字典表：01门诊02住院03检查<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgFreeMedMatCost>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgFreeMedMatCostMapper.queryExists(entityMap);
	}
	/**
	 * 校验数据 是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgFreeMedMatCostMapper.queryDataExist(mapVo);
	}
	
	/**
	 * 科室非收费医用材料支出 数据采集（系统内部数据采集）
	 */
	@Override
	public String collectData(Map<String, Object> mapVo) throws DataAccessException {
		// 先删除后添加
		try {
			//先删除所选年度 全年数据
			budgFreeMedMatCostMapper.deleteYearAllData(mapVo) ;
			
			//  采集同时添加数据 
			int count = budgFreeMedMatCostMapper.collectData(mapVo) ;
			

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

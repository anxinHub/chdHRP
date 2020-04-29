/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.budgcontrol;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.project.budgcontrol.BudgProjExeMapper;
import com.chd.hrp.budg.entity.BudgProjExe;
import com.chd.hrp.budg.service.project.budgcontrol.BudgProjExeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_PROJ_EXE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjExeService")
public class BudgProjExeServiceImpl implements BudgProjExeService {

	private static Logger logger = Logger.getLogger(BudgProjExeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjExeMapper")
	private final BudgProjExeMapper budgProjExeMapper = null;
    
	/**
	 * @Description 
	 * 添加     
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		try {
			
			//查询  预算系统启用年度；
			String startYearMonth = budgProjExeMapper.queryBudgModStartYearMonth(entityMap) ;
			
			if( startYearMonth != null && !"".equals(String.valueOf(startYearMonth))){
				
				if(Integer.parseInt(startYearMonth) > 
					Integer.parseInt(String.valueOf(entityMap.get("year"))+String.valueOf(entityMap.get("month")))){
					
					return "{\"error\":\"添加失败！所填数据年度在预算系统启用之前.\"}";
				}
			}else{
				
				return "{\"msg\":\"添加失败！预算系统未启用.\"}";
			}
			
			// 根据 所填数据 年度 查询该预算年度是否 已结转 （已结转 则不允许添加） 
			
			String is_carried = budgProjExeMapper.queryIsCarried(entityMap);
			
			if("1".endsWith(is_carried)){
				
				return "{\"error\":\"所填数据年度已结转,不允许添加、修改账表.\"}";
			}
			
			//根据主键 判断数据是否存在
			int count  = budgProjExeMapper.queryDataExist(entityMap);
			
			if(count > 0 ){
				return "{\"error\":\"数据已存在.\"}";
			}
			
			//查询  年度项目预算  是否存在  不存在不能添加
			int flag  = budgProjExeMapper.queryBudgDataExist(entityMap);
			
			if(flag == 0){
				
				return "{\"error\":\"所填数据年度项目预算不存在,不能添加.\"}";
			}
			
			// 添加  项目预算执行  数据
			int state = budgProjExeMapper.add(entityMap);
			
			//修改
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
						+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
						+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败！！\"}");
			
			//return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加     
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjExeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新      
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			
			int state = budgProjExeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新     
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjExeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除     
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgProjExeMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 ! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除     
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjExeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 ! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加     
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
		//判断是否存在对象是否停用（IS_STOP):0否，1是 性质（CHARGE_STAN_NATURE）取自系统字典表：01医院02科室
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgProjExe> list = (List<BudgProjExe>)budgProjExeMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgProjExeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgProjExeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 ! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集      
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjExeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjExeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象     
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjExeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取     
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgProjExe
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjExeMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取     
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return 
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjExeMapper.queryExists(entityMap);
	}
	
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 项目下拉框  添加使用
	 */
	@Override
	public String queryBudgProj(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjExeMapper.queryBudgProj(entityMap, rowBounds));
	}
	
	
	/**
	 * 资金来源 下拉框 添加使用
	 */
	@Override
	public String queryBudgSource(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjExeMapper.queryBudgSource(entityMap, rowBounds));
	}
	
	/**
	 * 支出项目 下拉框  添加使用
	 */
	@Override
	public String queryBudgPaymentItem(Map<String, Object> entityMap) throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgProjExeMapper.queryBudgPaymentItem(entityMap, rowBounds));
	}
	
	/**
	 * 确认
	 */
	@Override
	public String confirmBudgProjExe(Map<String, Object> mapVo) throws DataAccessException {
		try {
			
			// 根据 确认年度 查询该预算年度是否 已结转 （已结转 则不允许确认） 
			
			String is_carried = budgProjExeMapper.queryIsCarried(mapVo);
			
			if("1".endsWith(is_carried)){
				
				return "{\"msg\":\"操作失败！"+mapVo.get("year")+"年度预算已结转.\"}";
			}
			
			//汇总 年度项目预算数据
			List<Map<String,Object>> list = budgProjExeMapper.queryBudgProjYearData(mapVo);
			
			// 汇总 存在对应年度项目预算明细数据的 执行数据
			List<Map<String,Object>> detailList = budgProjExeMapper.queryBudgProjDetailYearExistData(mapVo);
			
			// 汇总 存在对应年度项目预算明细数据的 执行数据
			List<Map<String,Object>> detailListNE = budgProjExeMapper.queryBudgProjDetailYearNotExistData(mapVo);
			
			// 确认 修改账表  年度项目预算
			 budgProjExeMapper.updateBudgProjYear(list);
			
			 
			// 确认 修改账表  年度项目预算明细 
			 if(detailList.size()>0){
				 
				 budgProjExeMapper.updateBudgProjYearDetail(detailList);
				 
			 }
			 
			 // 确认 添加账表  年度项目预算明细 
			 if(detailListNE.size() > 0){
				 
				 budgProjExeMapper.addBudgProjYearDetail(detailListNE);
			 }
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败！！\"}");
			
			//return "{\"error\":\"添加失败! 方法 add\"}";

		}
	}
	
}

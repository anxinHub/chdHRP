/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.reportforms.monitoring;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgincome.reportforms.monitoring.BudgMedElseIncomeExeMapper;
import com.chd.hrp.budg.service.budgincome.reportforms.monitoring.BudgMedElseIncomeExeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医院医疗收入预算执行监控
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedElseIncomeExeService")
public class BudgMedElseIncomeExeServiceImpl implements BudgMedElseIncomeExeService {

	private static Logger logger = Logger.getLogger(BudgMedElseIncomeExeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedElseIncomeExeMapper")
	private final BudgMedElseIncomeExeMapper budgMedElseIncomeExeMapper = null;
    
	/**
	 * @Description 
	 * 查询结果集医疗其他收入执行数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		// 查询 所查 预算年度 是否有 执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
		int count =  budgMedElseIncomeExeMapper.queryExecuteDataExist(entityMap) ;
		
		if(count == 0 ){
			entityMap.put("flag",-1);
		}
		
		// 查询 所查 预算年度 是否有 数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有数据) 解决  没有数据，前台合计行 展现数据 窜行问题)
		int num = budgMedElseIncomeExeMapper.queryBudgDataExist(entityMap) ;
		
		if(num == 0 ){
			entityMap.put("budg_flag",-1);
		}
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedElseIncomeExeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedElseIncomeExeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医疗收入执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedElseIncomeExeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗收入执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedInHosYearMon
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedElseIncomeExeMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗收入执行数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedInHosYearMon>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedElseIncomeExeMapper.queryExists(entityMap);
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

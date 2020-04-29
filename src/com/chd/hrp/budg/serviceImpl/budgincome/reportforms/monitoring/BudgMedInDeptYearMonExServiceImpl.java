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
import com.chd.hrp.budg.dao.budgincome.reportforms.monitoring.BudgMedInDeptYearMonExMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeExecute;
import com.chd.hrp.budg.service.budgincome.reportforms.monitoring.BudgMedInDeptYearMonExService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室医疗收入预算执行监控
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedInDeptYearMonExService")
public class BudgMedInDeptYearMonExServiceImpl implements BudgMedInDeptYearMonExService {

	private static Logger logger = Logger.getLogger(BudgMedInDeptYearMonExServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedInDeptYearMonExMapper")
	private final BudgMedInDeptYearMonExMapper budgMedInDeptYearMonExMapper = null;
    
	

	@Override
	public String queryBudgMedInDeptYearMonEx(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		// 查询 所查 预算年度 是否有 执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
		int count =  budgMedInDeptYearMonExMapper.queryExecuteDataExist(entityMap) ;
		
		if(count == 0 ){
			entityMap.put("flag",-1);
		}
		
		// 查询 所查 预算年度 是否有 数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有数据) 解决  没有数据，前台合计行 展现数据 窜行问题)
		int num = budgMedInDeptYearMonExMapper.queryBudgDataExist(entityMap) ;
		if(num == 0 ){
			entityMap.put("budg_flag",-1);
		}
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgMedInDeptYearMonExMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgMedInDeptYearMonExMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}



	@Override
	public List<Map<String, Object>> queryMedInDeptYearMonExPrintDate(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>)budgMedInDeptYearMonExMapper.query(entityMap);
		return list;
	}
	
}

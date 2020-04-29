/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostOrderDeptEarningsAnalysisMapper;
import com.chd.hrp.cost.entity.CostOrderDeptEarnings;
import com.chd.hrp.cost.service.CostOrderDeptEarningsAnalysisService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室收入数据明细表_开单收入趋势分析<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costOrderDeptEarningsAnalysisService")
public class CostOrderDeptEarningsAnalysisServiceImpl implements CostOrderDeptEarningsAnalysisService {

	private static Logger logger = Logger.getLogger(CostOrderDeptEarningsAnalysisServiceImpl.class);
	
	@Resource(name = "costOrderDeptEarningsAnalysisMapper")
	private final CostOrderDeptEarningsAnalysisMapper costOrderDeptEarningsAnalysisMapper = null;

	
	/**
	 * @Description 
	 * 科室收入数据明细表_开单收入趋势分析<BR> 查询CostOrderIncomeTrend分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostOrderDeptEarnings(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		/*if(!"".equals(entityMap.get("acct_year_type").toString())){
			
			entityMap=query(entityMap);
			
		}*/
		
		if (sysPage.getTotal()==-1){
			
			List<CostOrderDeptEarnings> list = costOrderDeptEarningsAnalysisMapper.queryCostOrderDeptEarnings(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostOrderDeptEarnings> list = costOrderDeptEarningsAnalysisMapper.queryCostOrderDeptEarnings(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	
	public Map<String, Object> query(Map<String, Object> map) throws DataAccessException{
		
		String year_month = map.get("acct_year_type").toString();
		
		Integer first_year=0,last_year = 0;
		
		if("0".equals(year_month)){
			
			first_year = Integer.parseInt(map.get("first_year").toString());
			
			last_year = Integer.parseInt(map.get("last_year").toString());
			
			if(first_year<last_year){
				
				StringBuilder sql = new StringBuilder();
				
				for (int i = 0; i <(last_year-first_year)+1; i++) {
					
					sql.append(",sum(decode(t.year_month,'"+(first_year+i)+"' ,money,0)) as money"+(i+1)+"");
					
				}
				
				map.put("sql", sql);
				
			}else{
				
				StringBuilder sql = new StringBuilder();
				
				for (int i = 0; i <(first_year-last_year)+1; i++) {
					
					sql.append(",sum(decode(t.year_month,'"+(last_year+i)+"' ,money,0)) as money"+(i+1)+"");
					
				}
				
				map.put("sql", sql);
				
			}
			
		}else{
			
			first_year = Integer.parseInt(map.get("first_year").toString());
			
			last_year = Integer.parseInt(map.get("last_year").toString());
			
			if(first_year<last_year){
				
				StringBuilder sql = new StringBuilder();
				
				for (int i = 0; i <(last_year-first_year)+1; i++) {
					
					sql.append(",sum(decode(t.year_month,'"+map.get("acc_year").toString()+""+(first_year+i)+"' ,money,0)) as money"+(i+1)+"");
					
				}
				
				map.put("sql", sql);
				
			}else{
				
				StringBuilder sql = new StringBuilder();
				
				for (int i = 0; i <(first_year-last_year)+1; i++) {
					
					sql.append(",sum(decode(t.year_month,'"+map.get("acc_year").toString()+""+(last_year+i)+"' ,money,0)) as money"+(i+1)+"");
					
				}
				
				map.put("sql", sql);
				
			}
			
		}
		
		return map;
		
	}
    
	
}

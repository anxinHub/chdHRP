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
import com.chd.hrp.cost.dao.CostOrderIncomeTrendAnalysisMapper;
import com.chd.hrp.cost.entity.CostOrderIncomeTrend;
import com.chd.hrp.cost.service.CostOrderIncomeTrendAnalysisService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室收入数据明细表_开单收入趋势分析<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costOrderIncomeTrendAnalysisService")
public class CostOrderIncomeTrendAnalysisServiceImpl implements CostOrderIncomeTrendAnalysisService {

	private static Logger logger = Logger.getLogger(CostOrderIncomeTrendAnalysisServiceImpl.class);
	
	@Resource(name = "costOrderIncomeTrendAnalysisMapper")
	private final CostOrderIncomeTrendAnalysisMapper costOrderIncomeTrendAnalysisMapper = null;

	
	/**
	 * @Description 
	 * 科室收入数据明细表_开单收入趋势分析<BR> 查询CostOrderIncomeTrend分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostOrderIncomeTrend(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(!"".equals(entityMap.get("acct_year_type").toString())){
			
			entityMap=query(entityMap);
			
		}
		
		if (sysPage.getTotal()==-1){
			
			List<CostOrderIncomeTrend> list = costOrderIncomeTrendAnalysisMapper.queryCostOrderIncomeTrend(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostOrderIncomeTrend> list = costOrderIncomeTrendAnalysisMapper.queryCostOrderIncomeTrend(entityMap, rowBounds);
			
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


	@Override
	public String queryCostTrendAnalysis(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(!"".equals(entityMap.get("acct_year_type").toString())){
			
			entityMap=query(entityMap);
			
		}
		
		if (sysPage.getTotal()==-1){
			
			List<CostOrderIncomeTrend> list = costOrderIncomeTrendAnalysisMapper.queryCostTrendAnalysis(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostOrderIncomeTrend> list = costOrderIncomeTrendAnalysisMapper.queryCostTrendAnalysis(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}


	@Override
	public String queryOrderIncomeTitle(Map<String, Object> mapVo) throws DataAccessException {

		StringBuilder json = new StringBuilder();
		
		int first_num = Integer.parseInt(mapVo.get("first_year").toString());
		
		int last_num = Integer.parseInt(mapVo.get("last_year").toString());
		 
		 int count  = Math.abs(first_num-last_num);
		 
		 json.append("{Rows:[");
		 
		 if(!"0".equals(mapVo.get("acct_year_type"))){
			 
			 for (int i = 0; i <= count; i++) {
				 
				 json.append("{");
				 
				 json.append("\"acc_year\":" + "\"" +mapVo.get("acc_year").toString()+"年"+(first_num+i)+"月"+ "\"");
				 
				 json.append("},");

			 }
			 
			 json.setCharAt(json.length() - 1, ' ');
			 
		 }else{
			 
			 for (int i = 0; i <= count; i++) {
				 
				 json.append("{");
				 
				 json.append("\"acc_year\":" + "\"" +(first_num+i)+ "\"");
				 
				 
				 json.append("},");
				 
				}
			 
			 json.setCharAt(json.length() - 1, ' ');
			 
		 }
		 json.append("]}");
		 
		return json.toString();
	}
    
	
}

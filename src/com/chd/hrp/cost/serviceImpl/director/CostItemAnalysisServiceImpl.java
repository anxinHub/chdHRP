package com.chd.hrp.cost.serviceImpl.director;

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
import com.chd.hrp.cost.dao.director.CostItemAnalysisMapper;
import com.chd.hrp.cost.service.director.CostItemAnalysisService;
import com.github.pagehelper.PageInfo;

@Service("costItemAnalysisService")
public class CostItemAnalysisServiceImpl implements CostItemAnalysisService{
	
	private static Logger logger = Logger.getLogger(CostItemAnalysisServiceImpl.class);
	
	@Resource(name = "costItemAnalysisMapper")
	private final CostItemAnalysisMapper costItemAnalysisMapper = null;

	@Override
	public String queryCostProjectTrend(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<String> dataList= DateUtil.getMonthList(entityMap.get("year_month_begin").toString(),entityMap.get("year_month_end").toString());

        StringBuilder sBuilder = new StringBuilder();
		
		for (String v : dataList) {
			
			sBuilder.append(",sum(decode(year_month,"+v+",all_amount,0)) t"+v+"");
			sBuilder.append(",divide(sum(decode(year_month, "+v+", all_amount, 0)),sum(decode(year_month, "+entityMap.get("year_month_begin").toString()+", all_amount, 0)))  *100  t"+v+"v");
		 }
		

		entityMap.put("selectHead", sBuilder.toString());
		
		   SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
	
			if (sysPage.getTotal() == -1) {
	
				List<Map<String, Object>> list = costItemAnalysisMapper.queryCostProjectTrend(entityMap);
	
				return ChdJson.toJsonLower(list);
	
			} else {
	
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
				List<Map<String, Object>> list =  costItemAnalysisMapper.queryCostProjectTrend(entityMap, rowBounds);
	
				PageInfo page = new PageInfo(list);
	
				return ChdJson.toJsonLower(list,page.getTotal());
	
			}
	}

	/**
	 * 成本项目分析打印
	 */
	
	@Override
	public List<Map<String, Object>> queryCostProjectTrendPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<String> dataList= DateUtil.getMonthList(entityMap.get("year_month_begin").toString(),entityMap.get("year_month_end").toString());

        StringBuilder sBuilder = new StringBuilder();
		
		for (String v : dataList) {
			
			sBuilder.append(",to_char(round(sum(decode(year_month,"+v+",all_amount,0)),2),'fm9999990.00') t"+v+"");
			sBuilder.append(",to_char(round(divide(sum(decode(year_month, "+v+", all_amount, 0)),sum(decode(year_month, "+entityMap.get("year_month_begin").toString()+", all_amount, 0))) *100,2),'fm9999990.00') || '%'  t"+v+"v");
		 }
		

		entityMap.put("selectHead", sBuilder.toString());
		
		List<Map<String, Object>> list=costItemAnalysisMapper.queryCostProjectTrendPrint(entityMap);
		return list;
	}
	
	
	@Override
	public String queryCostProjectTrendEcharts(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costItemAnalysisMapper.queryCostProjectTrendEcharts(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costItemAnalysisMapper.queryCostProjectTrendEcharts(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	

	@Override
	public String queryCostProjectCompare(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
       SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costItemAnalysisMapper.queryCostProjectCompare(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costItemAnalysisMapper.queryCostProjectCompare(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	@Override
	public List<Map<String, Object>> queryCostProjectComparePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list =  costItemAnalysisMapper.queryCostProjectCompare(entityMap);
		return list;
	}

}

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
import com.chd.hrp.cost.dao.director.CostRevenueAnalysisMapper;
import com.chd.hrp.cost.service.director.CostRevenueAnalysisService;
import com.github.pagehelper.PageInfo;


@Service("costRevenueAnalysisService")
public class CostRevenueAnalysisServiceImpl implements CostRevenueAnalysisService{
	
	private static Logger logger = Logger.getLogger(CostRevenueAnalysisServiceImpl.class);
	
	@Resource(name = "costRevenueAnalysisMapper")
	private final CostRevenueAnalysisMapper costRevenueAnalysisMapper = null;

	@Override
	public String queryCostDeptRevenueConstitute(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueConstitute(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costRevenueAnalysisMapper.queryCostDeptRevenueConstitute(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	/**
	 *科室收入分析打印
	 */
	
	@Override
	public List<Map<String, Object>> queryCostDeptRevenueConstitutePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=costRevenueAnalysisMapper.queryCostDeptRevenueConstitutePrint(entityMap);
		return list;
	}
	
	@Override
	public String queryCostDeptRevenueTrend(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<String> dataList= DateUtil.getMonthList(entityMap.get("year_month_begin").toString(),entityMap.get("year_month_end").toString());

        StringBuilder sBuilder = new StringBuilder();
		
		for (String v : dataList) {
			
			sBuilder.append(",sum(decode(year_month,"+v+",money,0)) t"+v+"");
			sBuilder.append(",divide(sum(decode(year_month, "+v+", money, 0)),sum(decode(year_month, "+entityMap.get("year_month_begin").toString()+", money, 0)))  *100  t"+v+"v");
		 }
		

		entityMap.put("selectHead", sBuilder.toString());
		
		  SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueTrend(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costRevenueAnalysisMapper.queryCostDeptRevenueTrend(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}



	@Override
	public List<Map<String, Object>> queryCostDeptRevenueTrendPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<String> dataList= DateUtil.getMonthList(entityMap.get("year_month_begin").toString(),entityMap.get("year_month_end").toString());

        StringBuilder sBuilder = new StringBuilder();
		
		for (String v : dataList) {
			
			sBuilder.append(",sum(decode(year_month,"+v+",money,0)) t"+v+"");
			sBuilder.append(",divide(sum(decode(year_month, "+v+", money, 0)),sum(decode(year_month, "+entityMap.get("year_month_begin").toString()+", money, 0)))  *100  t"+v+"v");
		 }

		entityMap.put("selectHead", sBuilder.toString());
		
		List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueTrend(entityMap);

		return list;
	}


	@Override
	public String queryCostDeptRevenueTrendEcharts(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueTrendEcharts(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costRevenueAnalysisMapper.queryCostDeptRevenueTrendEcharts(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}
	
	@Override
	public String queryCostDeptRevenueCompare(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueCompare(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costRevenueAnalysisMapper.queryCostDeptRevenueCompare(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}

	@Override
	public List<Map<String, Object>> queryCostDeptRevenueComparePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueCompare(entityMap);
		return list;
	}
	
	
	@Override
	public String queryCostDeptRevenueOpeningOrder(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueOpeningOrder(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costRevenueAnalysisMapper.queryCostDeptRevenueOpeningOrder(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}


	@Override
	public List<Map<String, Object>> queryCostDeptRevenueOpeningOrderPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueOpeningOrder(entityMap);
		return list;
	}
	
	
	@Override
	public String queryCostDeptRevenueImplement(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costRevenueAnalysisMapper.queryCostDeptRevenueImplement(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  costRevenueAnalysisMapper.queryCostDeptRevenueImplement(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list,page.getTotal());

		}
	}
	
	@Override
	public List<Map<String, Object>> queryCostDeptRevenueImplementPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list =  costRevenueAnalysisMapper.queryCostDeptRevenueImplement(entityMap);
		return list;
	}
}

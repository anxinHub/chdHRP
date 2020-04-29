package com.chd.hrp.cost.serviceImpl.analysis;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.cost.dao.analysis.AnalysisMapper;
import com.chd.hrp.cost.service.analysis.AnalysisService;
import com.chd.hrp.htcg.entity.info.HtcgRecipeType;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 构成分析服务类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com 
* @Version: 1.0
*/


@Service("analysisService")
public class AnalysisServiceImpl implements AnalysisService{

	private static Logger logger = Logger.getLogger(AnalysisServiceImpl.class);

	@Resource(name = "analysisMapper")
	private final AnalysisMapper analysisMapper = null;

	@Override
	public String queryAnalysisC0201(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0201(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0201(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public String queryAnalysisC0203(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0203(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0203(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public String queryAnalysisC0204(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0204(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0204(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public String queryAnalysisC0205(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0205(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0205(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	@Override
	public String queryAnalysisC0303(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0303(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0303(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	@Override
	public String queryAnalysisC0304(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0304(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0304(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String queryAnalysisC0401(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0401(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0401(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryCostDeptReport_2(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.queryCostDeptReport_2(entityMap);

			return ChdJson.toJsonLower(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.queryCostDeptReport_2(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}
	
	@Override
	public String queryCostDeptReportThead(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list  = analysisMapper.queryCostDeptReportThead(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
	
	
	@Override
	public String queryCostDeptReport(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listThead  = analysisMapper.queryCostDeptReportThead(entityMap);
		entityMap.put("listThead", listThead);
		entityMap.put("cost_item_name", 1);
		List<Map<String, Object>> listTheadSum  = analysisMapper.queryCostDeptReportThead(entityMap);
		entityMap.put("listTheadSum", listTheadSum);
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.queryCostDeptReport(entityMap);

			return ChdJson.toJsonLower(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.queryCostDeptReport(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryCostDeptReport_1(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.queryCostDeptReport_1(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.queryCostDeptReport_1(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public String querychengbenMain(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.querychengbenMain(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.querychengbenMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public String querychengbenMain2(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.querychengbenMain2(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.querychengbenMain2(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	@Override
	public String querychengbenMain3(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.querychengbenMain3(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.querychengbenMain3(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public String queryCostDeptCost(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.queryCostDeptCost(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.queryCostDeptCost(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public String queryCostFLCost(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.queryCostFLCost(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.queryCostFLCost(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	
	
	/*
	 * (1)医院各类科室直接成本表打印
	 * */
	@Override
	public  List<Map<String, Object>>  queryCostDeptReport_1print(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = analysisMapper.queryCostDeptReport_1(entityMap);

		return list;
	}

	/*
	 * 
	 * (2)医院各类科室直接成本明细表打印
	 * */
	@Override
	public List<Map<String, Object>> queryCostDeptReportprint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = analysisMapper.queryCostDeptReport(entityMap);
		
		return list;
	}


	/*
	 * (3)医院临床服务类科室医疗成本分析表打印
	 * */
	@Override
	public List<Map<String, Object>> querychengbenMainprint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = analysisMapper.querychengbenMain(entityMap);
		
		return list;
	}


	/*
	 * (4)医院临床服务类科室医疗全成本分析表（财政）打印 
	 * */
	@Override
	public List<Map<String, Object>> querychengbenMain2print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = analysisMapper.querychengbenMain2(entityMap);
		return list;
	}

	/*
	 * (5)医院临床服务类科室全成本分析表打印
	 * */
	@Override
	public List<Map<String, Object>> querychengbenMain3print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = analysisMapper.querychengbenMain3(entityMap);
		return list;
	}


	/*
	 * (6)医院临床服务类科室全成本分析明细表打印
	 * */
	@Override
	public List<Map<String, Object>> queryCostDeptReport_2print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = analysisMapper.queryCostDeptReport_2(entityMap);
		return list;
	}

	/*
	 * (7)医院临床服务类科室各级成本构成分析表打印
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0201print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0201(entityMap);
		
		return list;
	}

	/*
	 * (8)临床服务类科室医疗成本构成分析明细表打印
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0203print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
	List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0203(entityMap);
		
		return list;
	}

	/*
	 * (9)临床服务类科室医疗全成本构成分析明细表打印
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0204print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0204(entityMap);
		return list;
	}

	
	/*
	 * (10)医院临床服务类科室全成本构成分析明细表打印
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0205print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0205(entityMap);
		return list;
	}

	
	/*
	 * (11)医院医疗成本分类构成表打印
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0303print(
			Map<String, Object> entityMap) throws DataAccessException {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0303(entityMap);
		return list;
	}

	
	/*
	 * (12)医院医疗成本分类构成明细表打印
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0304print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0304(entityMap);
		return list;
	}

	
	/*
	 * (13)医院科室医疗成本习性分析表打印
	 * */
	@Override
	public List<Map<String, Object>> queryAnalysisC0401print(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = (List<Map<String, Object>>) analysisMapper.queryAnalysisC0401(entityMap);
		return list;
	}

	/*
	 * 科室医疗成本分摊表
	 * */
	@Override
	public List<Map<String, Object>>  queryCostDeptCostprint(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list =  analysisMapper.queryCostDeptCost(entityMap);
		
		return list;
	}

	/*
	 * 医疗成本分类分摊表
	 * */
	@Override
	public List<Map<String, Object>>  queryCostFLCostprint(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list =  analysisMapper.queryCostFLCost(entityMap);
		return list;
	}

	@Override
	public String queryCostGeneralHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.queryCostGeneralHos(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.queryCostGeneralHos(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryCostGeneralHosMed(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = analysisMapper.queryCostGeneralHosMed(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = analysisMapper.queryCostGeneralHosMed(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryCostGeneralDetailHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostGeneralDetailHos(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());

		
	}

	@Override
	public String queryCostGeneralDetailMedHos(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostGeneralDetailMedHos(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String queryCostVolumeProfit(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostVolumeProfit(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String queryCostVolumeProfitDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostVolumeProfitDetail(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}
	
	@Override
	public String queryCostVolumeProfitDetailChart(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostVolumeProfitDetailChart(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String queryCostBreakeven(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostBreakeven(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String queryCostBreakevenDetailIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostBreakevenDetailIncome(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String queryCostBreakevenDetailCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostBreakevenDetailCost(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String queryCostRingRatio(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<String> dataList= DateUtil.getMonthList(entityMap.get("year_month_begin").toString(),entityMap.get("year_month_end").toString());

        StringBuilder sBuilder = new StringBuilder();
		
		for (String v : dataList) {
			
			sBuilder.append(",sum(decode(year_month,"+v+",t_1,0)) t_1_"+v+"");
			sBuilder.append(",sum(decode(year_month,"+v+",t_2,0)) t_2_"+v+"");
			sBuilder.append(",sum(decode(year_month,"+v+",t_3,0)) t_3_"+v+"");
			
		 }

		entityMap.put("selectHead", sBuilder.toString());
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostRingRatio(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
		
	}

	@Override
	public String queryCostRingRatioDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<String> dataList= DateUtil.getMonthList(entityMap.get("year_month_begin").toString(),entityMap.get("year_month_end").toString());

        StringBuilder sBuilder = new StringBuilder();
		
        StringBuilder munBuilderT_1 = new StringBuilder();
        
        StringBuilder munBuilderT_2 = new StringBuilder();
        
        StringBuilder munBuilderT_3 = new StringBuilder();
        
        munBuilderT_1.append(",");
        munBuilderT_2.append(",");
        munBuilderT_3.append(",");
        for (int i = 0; i < dataList.size(); i++) {
			
        	String v = dataList.get(i);
        	
        	if(dataList.size() - i == 1){
        		
        		munBuilderT_1.append("sum(decode(year_month,"+v+",t_1,0)) t_1_sum");
        		munBuilderT_2.append("sum(decode(year_month,"+v+",t_2,0)) t_2_sum");
        		munBuilderT_3.append("sum(decode(year_month,"+v+",t_3,0)) t_3_sum");
        		
        	}else {
        		
			    munBuilderT_1.append("sum(decode(year_month,"+v+",t_1,0)) + ");
			    munBuilderT_2.append("sum(decode(year_month,"+v+",t_2,0)) + ");
			    munBuilderT_3.append("sum(decode(year_month,"+v+",t_3,0)) + ");
			
        	}
        	
        	sBuilder.append(",sum(decode(year_month,"+v+",t_1,0)) t_1_"+v+"");
			sBuilder.append(",sum(decode(year_month,"+v+",t_2,0)) t_2_"+v+"");
			sBuilder.append(",sum(decode(year_month,"+v+",t_3,0)) t_3_"+v+"");
        	
		}

		
        sBuilder.append(munBuilderT_1.append(munBuilderT_2.append(munBuilderT_3)));
        
		entityMap.put("selectHead", sBuilder.toString());
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = analysisMapper.queryCostRingRatioDetail(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String queryCostRingRatioChart(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		

		List<Map<String, Object>> list = analysisMapper.queryCostRingRatioChart(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryCostClinicalDeptIncomeItemColumns(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);

		return ChdJson.toJsonLower(list);
	}
	
	@Override
	public String queryCostClinicalDeptIncomeAnalysis(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//需要行转列的数据
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
	
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostClinicalDeptIncomeAnalysis(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}
	
	@Override
	public List<Map<String, Object>>  queryCostClinicalDeptIncomeAnalysisPrint(Map<String, Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//需要行转列的数据
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
	
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostClinicalDeptIncomeAnalysis(entityMap);
		
		return list;
	}
	
	@Override
	public String queryCostClinicalDeptIncomeAnalysisAppl(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		StringBuilder Superior = new StringBuilder(); //上级汇总
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			Superior.append("(select \n");
			
			Superior.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))\n");
			
			Superior.append("from result p\n");
			
			Superior.append("start with p.appl_dept_code = a.appl_dept_code\n");
			
			Superior.append("connect by p.super_code = prior p.appl_dept_code\n");
			
			Superior.append(")item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");
				
				Superior.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		entityMap.put("Superior", Superior.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostClinicalDeptIncomeAnalysisAppl(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}
	
	@Override
	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysisApplPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		StringBuilder Superior = new StringBuilder(); //上级汇总
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			Superior.append("(select \n");
			
			Superior.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))\n");
			
			Superior.append("from result p\n");
			
			Superior.append("start with p.appl_dept_code = a.appl_dept_code\n");
			
			Superior.append("connect by p.super_code = prior p.appl_dept_code\n");
			
			Superior.append(")item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");
				
				Superior.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		entityMap.put("Superior", Superior.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostClinicalDeptIncomeAnalysisAppl(entityMap);
		
		return list;
	}
	@Override
	public String queryCostCustomDeptIncomeAnalysisAppl(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		
		List<Map<String, Object>> list = analysisMapper.queryCostCustomDeptIncomeAnalysisAppl(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}
	
	@Override
	public List<Map<String, Object>> queryCostCustomDeptIncomeAnalysisApplPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		
		List<Map<String, Object>> list = analysisMapper.queryCostCustomDeptIncomeAnalysisAppl(entityMap);
		
		return list;
	}
	
	@Override
	public String queryCostCustomDeptIncomeAnalysisExec(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		
		List<Map<String, Object>> list = analysisMapper.queryCostCustomDeptIncomeAnalysisExec(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}
	
	@Override
	public List<Map<String, Object>>  queryCostCustomDeptIncomeAnalysisExecPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		
		List<Map<String, Object>> list = analysisMapper.queryCostCustomDeptIncomeAnalysisExec(entityMap);
		

		return list;
	}
	@Override
	public String queryCostClinicalDeptIncomeAnalysisExec(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段 
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		StringBuilder Superior = new StringBuilder(); //上级汇总
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			Superior.append("(select \n");
			
			Superior.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))\n");
			
			Superior.append("from result p\n");
			
			Superior.append("start with p.exec_dept_code = a.exec_dept_code\n");
			
			Superior.append("connect by p.super_code = prior p.exec_dept_code\n");
			
			Superior.append(")item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");
				
				Superior.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		entityMap.put("Superior", Superior.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostClinicalDeptIncomeAnalysisExec(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}
	
	@Override
	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysisExecPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段 
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		StringBuilder Superior = new StringBuilder(); //上级汇总
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			Superior.append("(select \n");
			
			Superior.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))\n");
			
			Superior.append("from result p\n");
			
			Superior.append("start with p.exec_dept_code = a.exec_dept_code\n");
			
			Superior.append("connect by p.super_code = prior p.exec_dept_code\n");
			
			Superior.append(")item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");
				
				Superior.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		entityMap.put("Superior", Superior.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostClinicalDeptIncomeAnalysisExec(entityMap);

		return list;
	}
	

	@Override
	public String queryCostDeptIncomeAnalysisAchievementsAppl(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		StringBuilder Superior = new StringBuilder(); //上级汇总
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			Superior.append("(select \n");
			
			Superior.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))\n");
			
			Superior.append("from result p\n");
			
			Superior.append("start with p.appl_dept_code = a.appl_dept_code\n");
			
			Superior.append("connect by p.super_code = prior p.appl_dept_code\n");
			
			Superior.append(")item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");
				
				Superior.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		entityMap.put("Superior", Superior.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostDeptIncomeAnalysisAchievementsAppl(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public List<Map<String, Object>>  queryCostDeptIncomeAnalysisAchievementsApplPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub


		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		StringBuilder field = new StringBuilder(); //动态显示查询字段
		
		StringBuilder field_by = new StringBuilder(); //动态显示汇总字段
		
		StringBuilder total_column = new StringBuilder(); //动态显示每一列合计字段
		
		StringBuilder total_row = new StringBuilder(); //动态显示每一行合计字段
		
		StringBuilder pivot = new StringBuilder();//行转列的数据
		
		StringBuilder Superior = new StringBuilder(); //上级汇总
		
		List<Map<String, Object>> incomeItemColumnslist = analysisMapper.queryCostClinicalDeptIncomeItemColumns(entityMap);
		
		int i = 0;
		
		field_by.append("(");
		
		total_row.append("sum(");
		
		for (Map<String, Object> Map:incomeItemColumnslist) {
			
			i= ++i;
			
			field.append("item"+Map.get("INCOME_ITEM_CODE").toString()+"");
			
			field_by.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			total_column.append("sum(item"+Map.get("INCOME_ITEM_CODE").toString()+")");
			
			total_row.append("nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0)");
			
			pivot.append("'");
			
			pivot.append(Map.get("INCOME_ITEM_CODE"));
			
			pivot.append("'");
			
			pivot.append("\t");
			
			pivot.append("item"+Map.get("INCOME_ITEM_CODE").toString());
			
			Superior.append("(select \n");
			
			Superior.append("sum(nvl(item"+Map.get("INCOME_ITEM_CODE").toString()+",0))\n");
			
			Superior.append("from result p\n");
			
			Superior.append("start with p.appl_dept_code = a.appl_dept_code\n");
			
			Superior.append("connect by p.super_code = prior p.appl_dept_code\n");
			
			Superior.append(")item"+Map.get("INCOME_ITEM_CODE").toString());
			
			if(i!=incomeItemColumnslist.size()){

				field.append(",");
				
				field_by.append("+");
				
				total_column.append(",");
				
				total_row.append("+");
				
				pivot.append(",");
				
				Superior.append(",");

			}
		 }
		
		field_by.append(")\titem9999");
		
		total_row.append(")");
		
		entityMap.put("field", field.toString());
		
		entityMap.put("field_by", field_by.toString());
		
		entityMap.put("total_column", total_column.toString());
		
		entityMap.put("total_row", total_row.toString());
		
		entityMap.put("pivot", pivot.toString());
		
		entityMap.put("Superior", Superior.toString());
		
		List<Map<String, Object>> list = analysisMapper.queryCostDeptIncomeAnalysisAchievementsAppl(entityMap);

		return list;
	}


}

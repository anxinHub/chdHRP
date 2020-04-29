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
import com.chd.hrp.cost.controller.director.CostBreakevenPointAnalysisController;
import com.chd.hrp.cost.dao.director.CostBreakevenPointAnalysisMapper;
import com.chd.hrp.cost.service.director.CostBreakevenPointAnalysisService;
import com.github.pagehelper.PageInfo;

@Service("costBreakevenPointAnalysisService")
public class CostBreakevenPointAnalysisServiceImpl implements CostBreakevenPointAnalysisService {

	private static Logger logger = Logger.getLogger(CostBreakevenPointAnalysisServiceImpl.class);

	@Resource(name = "costBreakevenPointAnalysisMapper")
	private final CostBreakevenPointAnalysisMapper costBreakevenPointAnalysisMapper = null;

	@Override
	public String queryCostBreakevenPointClinic(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper.queryCostBreakevenPointClinic(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper.queryCostBreakevenPointClinic(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	/**
	 * 门诊保本点打印
	 */
	
	@Override
	public List<Map<String, Object>> queryCostBreakevenPointClinicPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=costBreakevenPointAnalysisMapper.queryCostBreakevenPointClinicPrint(entityMap);
		return list;
	}

	/**
	 * 住院保本点打印
	 */
	
	@Override
	public List<Map<String, Object>> queryCostBreakevenPointInhosPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=costBreakevenPointAnalysisMapper.queryCostBreakevenPointInhosPrint(entityMap);
		return list;
	}

	@Override
	public String queryCostBreakevenPointInhos(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper.queryCostBreakevenPointInhos(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper.queryCostBreakevenPointInhos(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	

	@Override
	public String queryCostBreakevenPointClinicCalculation(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper
					.queryCostBreakevenPointClinicCalculation(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper
					.queryCostBreakevenPointClinicCalculation(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryCostBreakevenPointInhosCalculation(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper
					.queryCostBreakevenPointInhosCalculation(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper
					.queryCostBreakevenPointInhosCalculation(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryCostBreakevenPointMedical(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper.queryCostBreakevenPointMedical(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper.queryCostBreakevenPointMedical(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryCostBreakevenPointMedicalCalculation(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper
					.queryCostBreakevenPointMedicalCalculation(entityMap);
			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costBreakevenPointAnalysisMapper
					.queryCostBreakevenPointMedicalCalculation(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

}

package com.chd.hrp.hr.serviceImpl.scientificresearch;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.scientificresearch.HrSRStatisticsMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrPaperSet;
import com.chd.hrp.hr.entity.scientificresearch.HrResearchTotSet;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedData;
import com.chd.hrp.hr.service.scientificresearch.HrSRStatisticsService;
import com.github.pagehelper.PageInfo;

@Service("hrSRStatisticsService")
public class HrSRStatisticsServiceImpl implements HrSRStatisticsService {

	@Resource(name = "hrSRStatisticsMapper")
	private final HrSRStatisticsMapper hrSRStatisticsMapper = null;
	
	/**
	 * 拼接行转列SQL
	 * @param entityMap
	 */
	private void getSQL(Map<String, Object> entityMap){
		List<HrFiiedData> fiiedData = hrSRStatisticsMapper.queryFiiedDataByTab(entityMap);
		if(fiiedData != null && fiiedData.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (HrFiiedData data : fiiedData) {
				sb.append("'").append(data.getField_col_code()).append("'");
				sb.append(" as ");
				sb.append("col").append(data.getField_col_code()).append(",");
			}

			entityMap.put("sql", sb.deleteCharAt(sb.length() - 1).toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryHonorStatistics(Map<String, Object> entityMap) {

		getSQL(entityMap);
		
		SysPage sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryHonorStatistics(entityMap);
			return ChdJson.toJson(datalist);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryHonorStatistics(entityMap, rowBounds);
			PageInfo page = new PageInfo(datalist);
			return ChdJson.toJson(datalist, page.getTotal());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryStatusStatistics(Map<String, Object> entityMap) {
		getSQL(entityMap);
		
		SysPage sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryStatusStatistics(entityMap);
			return ChdJson.toJson(datalist);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryStatusStatistics(entityMap, rowBounds);
			PageInfo page = new PageInfo(datalist);
			return ChdJson.toJson(datalist, page.getTotal());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryScientificStatistics(Map<String, Object> entityMap) {
		getSQL(entityMap);
		
		SysPage sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryScientificStatistics(entityMap);
			return ChdJson.toJson(datalist);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryScientificStatistics(entityMap, rowBounds);
			PageInfo page = new PageInfo(datalist);
			return ChdJson.toJson(datalist, page.getTotal());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryPaperStatistics(Map<String, Object> entityMap) {
		List<HrFiiedData> fiiedData = hrSRStatisticsMapper.queryFiiedDataByTab(entityMap);
		if(fiiedData != null && fiiedData.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (HrFiiedData data : fiiedData) {
				
				entityMap.put("paper_type_code", data.getField_col_code());
				List<HrPaperSet> paperSetList = hrSRStatisticsMapper.queryHrPaperSetByPaperType(entityMap);
				for (HrPaperSet hrPaperSet : paperSetList) {
					sb.append("'").append(data.getField_col_code()).append(hrPaperSet.getAffect_para()).append("'");
					sb.append(" as ");
					sb.append("col").append(data.getField_col_code()).append(hrPaperSet.getAffect_para()).append(",");
				}
			}
            if (sb.length()>0) {
            	entityMap.put("sql", sb.deleteCharAt(sb.length() - 1).toString());
			}
		
		}
		
		if(entityMap.containsKey("sql")){
			SysPage sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryPaperStatistics(entityMap);
				return ChdJson.toJson(datalist);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> datalist = hrSRStatisticsMapper.queryPaperStatistics(entityMap, rowBounds);
				PageInfo page = new PageInfo(datalist);
				return ChdJson.toJson(datalist, page.getTotal());
			}
			
		}else{
			return "{\"error\":\"请先设置学术论文积分\",\"state\":\"false\"}";
		}
		
	}

	@Override
	public List<Map<String, Object>> queryDynamicTabHead(Map<String, Object> entityMap) {
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = hrSRStatisticsMapper.queryScoreSet(entityMap);
		for (Map<String, Object> map : list) {
			Map<String, Object> reMap = new LinkedHashMap<String, Object>();
			reMap.put("name", "COL" + map.get("field_col_code"));
			reMap.put("display", map.get("field_col_name") + "(" + map.get("score") + "分)");
			reMap.put("width", "150");
			reMap.put("align", "center");
			reList.add(reMap);
		}
		return reList;
	}
	
	@Override
	public List<Map<String, Object>> queryScientificDynamicTabHead(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		List<HrFiiedData> list = hrSRStatisticsMapper.queryFieldDataParent(entityMap);
		for (HrFiiedData data : list) {
			Map<String, Object> parentMap = new LinkedHashMap<String, Object>();
			parentMap.put("display", data.getField_col_name());
			entityMap.put("super_col_code", data.getField_col_code());
			parentMap.put("columns", queryDynamicTabHead(entityMap));
			reList.add(parentMap);
		}
		return reList;
	}
	
	@Override
	public List<Map<String, Object>> queryPaperDynamicTabHead(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		List<HrFiiedData> fiiedData = hrSRStatisticsMapper.queryFiiedDataByTab(entityMap);
		for (HrFiiedData hrFiiedData : fiiedData) {
			Map<String, Object> typeMap = new LinkedHashMap<String, Object>();
			typeMap.put("display", hrFiiedData.getField_col_name());
			entityMap.put("paper_type_code", hrFiiedData.getField_col_code());
			typeMap.put("columns", getPaperDynamicChildTabHead(entityMap));
			reList.add(typeMap);
		}
		return reList;
	}
	
	private List<Map<String, Object>> getPaperDynamicChildTabHead(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		List<HrPaperSet> paperSetList = hrSRStatisticsMapper.queryHrPaperSetByPaperType(entityMap);
		for (HrPaperSet hrPaperSet : paperSetList) {
			Map<String, Object> reMap = new LinkedHashMap<String, Object>();
			reMap.put("name", "COL" + entityMap.get("paper_type_code") + hrPaperSet.getAffect_para());
			reMap.put("display", hrPaperSet.getAffect_para_name() + "(" + hrPaperSet.getScore() + "分)");
			reMap.put("width", "150");
			reMap.put("align", "center");
			reList.add(reMap);
		}
		return reList;
	}

	@Override
	public HrResearchTotSet queryScoreStandard(Map<String, Object> entityMap) {
		return hrSRStatisticsMapper.queryScoreStandard(entityMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String querySRStatistics(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.querySRStatistics(entityMap);
			return ChdJson.toJson(datalist);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> datalist = hrSRStatisticsMapper.querySRStatistics(entityMap, rowBounds);
			PageInfo page = new PageInfo(datalist);
			return ChdJson.toJson(datalist, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryHonorStatisticsByPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("field_tab_code", "DIC_ACADE_STATUS");
		List<HrFiiedData> fiiedData = hrSRStatisticsMapper.queryFiiedDataByTab(entityMap);
		if(fiiedData != null && fiiedData.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (HrFiiedData data : fiiedData) {
				sb.append("'").append(data.getField_col_code()).append("'");
				sb.append(" as ");
				sb.append("col").append(data.getField_col_code()).append(",");
			}

			entityMap.put("sql", sb.deleteCharAt(sb.length() - 1).toString());
		}
	
		List<Map<String,Object>> list = ChdJson.toListLower(hrSRStatisticsMapper.queryHonorStatisticsByPrint(entityMap));
		return list;
	}

	@Override
	public List<Map<String, Object>> queryStatusStatisticsByPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("field_tab_code", "DIC_PROJ_LEVEL");
		List<HrFiiedData> fiiedData = hrSRStatisticsMapper.queryFiiedDataByTab(entityMap);
		if(fiiedData != null && fiiedData.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (HrFiiedData data : fiiedData) {
				sb.append("'").append(data.getField_col_code()).append("'");
				sb.append(" as ");
				sb.append("col").append(data.getField_col_code()).append(",");
			}

			entityMap.put("sql", sb.deleteCharAt(sb.length() - 1).toString());
		}
	
	
		
		List<Map<String,Object>> list = ChdJson.toListLower(hrSRStatisticsMapper.queryStatusStatisticsByPrint(entityMap));
		 return list;
	}

	@Override
	public List<Map<String, Object>> queryPaperDynamicTabHeadByPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("field_tab_code", "DIC_PAPER_TYPE");
		List<HrFiiedData> fiiedData = hrSRStatisticsMapper.queryFiiedDataByTab(entityMap);
		if(fiiedData != null && fiiedData.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (HrFiiedData data : fiiedData) {
				sb.append("'").append(data.getField_col_code()).append("'");
				sb.append(" as ");
				sb.append("col").append(data.getField_col_code()).append(",");
			}

			entityMap.put("sql", sb.deleteCharAt(sb.length() - 1).toString());
		}
	
		 List<Map<String,Object>> list = ChdJson.toListLower(hrSRStatisticsMapper.queryPaperDynamicTabHeadByPrint(entityMap));
		 return list;
	}

	@Override
	public List<Map<String, Object>> querySRStatisticsByPrint(Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrSRStatisticsMapper.querySRStatisticsByPrint(entityMap));
		 return list;
	}

	@Override
	public List<Map<String, Object>> queryScientificStatisticsByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("field_tab_code", "DIC_PROJ_LEVEL");
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());

		List<HrFiiedData> fiiedData = hrSRStatisticsMapper.queryFiiedDataByTab(entityMap);
		if(fiiedData != null && fiiedData.size() > 0){
			StringBuilder sb = new StringBuilder();
			for (HrFiiedData data : fiiedData) {
				sb.append("'").append(data.getField_col_code()).append("'");
				sb.append(" as ");
				sb.append("col").append(data.getField_col_code()).append(",");
			}

			entityMap.put("sql", sb.deleteCharAt(sb.length() - 1).toString());
		}
	
		 List<Map<String,Object>> list = ChdJson.toListLower(hrSRStatisticsMapper.queryScientificStatisticsByPrint(entityMap));
		 return list;
	}

}

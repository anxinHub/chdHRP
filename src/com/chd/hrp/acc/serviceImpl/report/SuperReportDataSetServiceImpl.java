package com.chd.hrp.acc.serviceImpl.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.report.SuperReportDataSetMapper;
import com.chd.hrp.acc.service.report.SuperReportDataSetService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

@Service("superReportDataSetService")
public class SuperReportDataSetServiceImpl implements SuperReportDataSetService {

	private static Logger logger = Logger.getLogger(SuperReportDataSetServiceImpl.class);
	
	@Autowired
	private SuperReportDataSetMapper dataSetMapper;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	public String saveSuperReportDSParaValues(Map<String, Object> map) throws DataAccessException {
		try{
			dataSetMapper.saveSuperReportDSParaValues(map);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}
	@Override
	public String saveReportQuery(Map<String, Object> map) throws DataAccessException {
		try{
			dataSetMapper.deleteReportQuery(map);
			if(!map.get("params").toString().equals("[]"))
			dataSetMapper.saveReportQuery(map);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}
	@Override
	public String querySuperReportDsManager(Map<String, Object> map) throws DataAccessException {

		StringBuffer sb = new StringBuffer();
		sb.append("{Rows:[");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = dataSetMapper.querySuperReportDsByMod(map);

		// 拼装级次
		if (list != null && list.size() > 0) {
			// 系统模块（一级）
			int row = 0;
			Map<String, Object> modMap = new HashMap<String, Object>();
			for (Map<String, Object> dsModMap : list) {
				if (modMap.get(dsModMap.get("mod_code").toString()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					modMap.put(dsModMap.get("mod_code").toString(), dsModMap.get("mod_code").toString());
					sb.append("id:\"" + dsModMap.get("mod_code").toString() + "\",");
					sb.append("pId:\"top\",");
					sb.append("name:\"" + dsModMap.get("mod_name") + "\",");
					sb.append("title:\"" + dsModMap.get("mod_name") + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}
			
			// 分组（二级）
			row = 0;
			Map<String, Object> groupMap = new HashMap<String, Object>();
			for (Map<String, Object> dsMap : list) {
				if (groupMap.get(dsMap.get("ds_group").toString()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					groupMap.put(dsMap.get("ds_group").toString(), "ds_"+row);
					sb.append("id:\"ds_" + row + "\",");
					sb.append("pId:\"" + modMap.get(dsMap.get("mod_code").toString()) + "\",");
					sb.append("name:\"" + dsMap.get("ds_group") + "\",");
					sb.append("title:\"" + dsMap.get("ds_group") + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}

			// 数据集（三级）
			row = 0;
			for (Map<String, Object> rep : list) {
				if (groupMap.get(rep.get("ds_group").toString()) != null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					sb.append("id:\"" + rep.get("ds_code") + "\",");
					sb.append("ds_type:\"" + rep.get("ds_type") + "\",");
					sb.append("pId:\"" + groupMap.get(rep.get("ds_group").toString()) + "\",");
					sb.append("name:\"" + rep.get("ds_name") + "\",");
					sb.append("title:\"" + rep.get("ds_name") + "\",");
					sb.append("mod_code:\"" + rep.get("mod_code") + "\",");
					sb.append("sqlcontent:\"" + rep.get("sqlcontent") + "\",");
					sb.append("state:\"" + rep.get("state") + "\",");
					sb.append("ds_class:\"" + rep.get("ds_class") + "\",");
					sb.append("ds_group:\"" + rep.get("ds_group") + "\",");
					sb.append("sort_code:\"" + rep.get("sort_code") + "\",");
					sb.append("is_sys:\"" + rep.get("is_sys") + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}
		}
		sb.append("]}");
		return sb.toString();
	}

	@Override
	public String querySuperReportDSColoums(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = dataSetMapper.querySuperReportDSColoums(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String saveSuperReportDs(Map<String, Object> mapVo) {
		try {
			String type = mapVo.get("operation").toString();
			if ("insert".equals(type)) {
				List<Map<String,Object>> list = dataSetMapper.querySuperReportDsByDSCode(mapVo);
				if (!list.isEmpty()) {
					throw new SysException("数据集编码不能重复");
				}
			}
			if ("update".equals(type)) {
				deleteSuperReportDs(mapVo);
			}else{

				
				// 取最大排序号
				mapVo.put("field_table", "REP_DATASET");
				mapVo.put("field_sort", "sort_code");
				mapVo.put("field_key1", "mod_code");
				mapVo.put("field_value1", mapVo.get("mod_code"));
				int count = sysFunUtilMapper.querySysMaxSort(mapVo);
				mapVo.put("sort_code", count);
				//用户自己添加的为非系统内置
				mapVo.put("is_sys", 0);
			}
			mapVo.put("sort_code", 1);
			mapVo.put("is_sys", 0);
			dataSetMapper.saveSuperReportDs(mapVo);

			//解析列数据
			List<Map<String, Object>> cList = new ArrayList<Map<String,Object>>();
			JSONArray cJson = JSONArray.parseArray(String.valueOf(mapVo.get("cGrid")));
			Iterator cIterator = cJson.iterator();
			JSONObject cJsonObj = null;
			Map<String, Object> cMap = null;
			int index = 1;
			while(cIterator.hasNext()){
				cJsonObj = JSONObject.parseObject(cIterator.next().toString());

				cMap = new HashMap<String, Object>();
				cMap.put("col_code", cJsonObj.getString("col_code"));
				cMap.put("col_name", cJsonObj.getString("col_name"));
				cMap.put("col_type", cJsonObj.getString("col_type"));
				cMap.put("col_length", cJsonObj.getString("col_length"));
				cMap.put("bak", cJsonObj.getString("bak"));
				cMap.put("sort_code", index);
				cList.add(cMap);
				index ++;
			}
			
			//解析参数数据
			List<Map<String, Object>> pList = new ArrayList<Map<String,Object>>();
			JSONArray pJson = JSONArray.parseArray(String.valueOf(mapVo.get("pGrid")));
			Iterator pIterator = pJson.iterator();
			JSONObject pJsonObj = null;
			Map<String, Object> pMap = null;
			index = 1;
			while(pIterator.hasNext()){
				pJsonObj = JSONObject.parseObject(pIterator.next().toString());

				pMap = new HashMap<String, Object>();
				pMap.put("para_tm", pJsonObj.getString("para_tm"));
				pMap.put("para_code", pJsonObj.getString("para_code"));
				pMap.put("para_name", pJsonObj.getString("para_name"));
				pMap.put("para_type", pJsonObj.getString("para_type"));
				pMap.put("dict_code", pJsonObj.getString("dict_code"));
				pMap.put("para_json", pJsonObj.getString("para_json"));
				pMap.put("sort_code", index);
				pMap.put("is_stop", pJsonObj.getString("is_stop"));
				pList.add(pMap);
				index ++;
			}
			/*String object = (String) mapVo.get("grid");
			@SuppressWarnings("rawtypes")
			List<HashMap> list = JSONObject.parseArray(object, HashMap.class);*/
			if (!cList.isEmpty()) {
				mapVo.put("list", cList);
				dataSetMapper.saveSuperReportDSColoums(mapVo);
			}
			if(!pList.isEmpty()){
				mapVo.put("list", pList);
				dataSetMapper.saveRepDSParam(mapVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String deleteSuperReportDs(Map<String, Object> mapVo) {
		try {
			dataSetMapper.deleteSuperReportDs(mapVo);
			dataSetMapper.deleteSuperReportDSColoums(mapVo);
			dataSetMapper.deleteRepDSPara(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String querySuperReportDSParas(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = dataSetMapper.querySuperReportDSParas(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String querySuperReportDsSql(Map<String, Object> mapVo) throws DataAccessException{
		
		return dataSetMapper.querySuperReportDsSql(mapVo);
	}
	@Override
	public int deleteSuperReportDsParaValues(Map<String, Object> mapVo) {
		try {			
			return dataSetMapper.deleteSuperReportDsParaValues(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}
	@Override
	public List<Map<String, Object>> querySuperReportDSParaValues(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> list = dataSetMapper.querySuperReportDSparaValue(mapVo);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e.getCause());
		}
	}
}

package com.chd.hrp.acc.serviceImpl.autovouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.acc.dao.autovouch.AccFinancialAccountingComparisonMapper;
import com.chd.hrp.acc.service.autovouch.AccFinancialAccountingComparisonService;
import com.github.pagehelper.PageInfo;

@Service("accFinancialAccountingComparisonService")
public class AccFinancialAccountingComparisonServiceImpl implements AccFinancialAccountingComparisonService{

	private static Logger logger = Logger.getLogger(AccFinancialAccountingComparisonServiceImpl.class);
	
	@Resource(name = "accFinancialAccountingComparisonMapper")
	private final AccFinancialAccountingComparisonMapper accFinancialAccountingComparisonMapper = null;

	@Override
	public String queryFinancialAccountingComparison(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 String show_way = "1";
		 if(mapVo.get("show_way") != null && !"".equals(mapVo.get("show_way"))){
			 show_way = mapVo.get("show_way").toString();
		 }
		 
		 //按资金来源查询需要提供资金来源的check_type_id  
		 if(show_way.equals("2")){
			 mapVo.put("check_type_id", accFinancialAccountingComparisonMapper.getSourceCheckId(mapVo));
		 }
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accFinancialAccountingComparisonMapper.queryFinancialAccountingComparison(mapVo);
				
				if(show_way.equals("1")){
					return ChdJson.toJson(list);
				}else{
					List<Map<String, Object>> sourceList = new ArrayList<Map<String,Object>>();
					Map<String, Integer> existsMap = new HashMap<String, Integer>();
					Map<String, Object> tmpMap = null;
					String key = "";
					int index = 0;
					//解析结果集，转换为以资金来源为键，预算科目为值以便前台展示
					for(Map<String, Object> dataMap : list){
						key = dataMap.get("SUBJ_CODE").toString();
						if(existsMap.containsKey(key)){
							tmpMap = sourceList.get(existsMap.get(key));

							if(dataMap.get("SOURCE_ID") != null && !"".equals(dataMap.get("SOURCE_ID").toString())){
								tmpMap.put("id_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("SUBJ_CODE_K"));
								tmpMap.put("name_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("YUSUAN"));
							}
						}else{
							tmpMap = new HashMap<String, Object>();
							
							tmpMap.put("SUBJ_CODE", dataMap.get("SUBJ_CODE"));
							tmpMap.put("CAIWU", dataMap.get("CAIWU"));
							if(dataMap.get("SOURCE_ID") != null && !"".equals(dataMap.get("SOURCE_ID").toString())){
								tmpMap.put("id_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("SUBJ_CODE_K"));
								tmpMap.put("name_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("YUSUAN"));
							}
							
							sourceList.add(tmpMap);
							existsMap.put(key, index);
							index ++;
						}
					}
					
					return ChdJson.toJson(sourceList);
				}
		}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accFinancialAccountingComparisonMapper.queryFinancialAccountingComparison(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
		        if(show_way.equals("1")){
		        	return ChdJson.toJson(list, page.getTotal());
		        }else{
					List<Map<String, Object>> sourceList = new ArrayList<Map<String,Object>>();
					Map<String, Integer> existsMap = new HashMap<String, Integer>();
					Map<String, Object> tmpMap = null;
					String key = "";
					int index = 0;
					//解析结果集，转换为以资金来源为键，预算科目为值以便前台展示
					for(Map<String, Object> dataMap : list){
						key = dataMap.get("SUBJ_CODE").toString();
						if(existsMap.containsKey(key)){
							tmpMap = sourceList.get(existsMap.get(key));

							if(dataMap.get("SOURCE_ID") != null && !"".equals(dataMap.get("SOURCE_ID").toString())){
								tmpMap.put("id_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("SUBJ_CODE_K"));
								tmpMap.put("name_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("YUSUAN"));
							}
						}else{
							tmpMap = new HashMap<String, Object>();
							
							tmpMap.put("SUBJ_CODE", dataMap.get("SUBJ_CODE"));
							tmpMap.put("CAIWU", dataMap.get("CAIWU"));
							if(dataMap.get("SOURCE_ID") != null && !"".equals(dataMap.get("SOURCE_ID").toString())){
								tmpMap.put("id_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("SUBJ_CODE_K"));
								tmpMap.put("name_" + dataMap.get("SOURCE_ID").toString(), dataMap.get("YUSUAN"));
							}
							
							sourceList.add(tmpMap);
							existsMap.put(key, index);
							index ++;
						}
					}
					
		        	return ChdJson.toJson(sourceList, page.getTotal());
		        }
		}
		
	}

	@Override
	public List<Map<String, Object>> queryFinancialAccountingComparisonSubjC(
			Map<String, Object> mapVo) {
		return accFinancialAccountingComparisonMapper.queryFinancialAccountingComparisonSubjC(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryFinancialAccountingComparisonSubjK(
			Map<String, Object> mapVo) {
		return accFinancialAccountingComparisonMapper.queryFinancialAccountingComparisonSubjK(mapVo);
	}

	@Override
	public String addFinancialAccountingComparison(Map<String, Object> mapVo) {
		
		try {
			
			int deleteCount = accFinancialAccountingComparisonMapper.deleteFinancialAccountingComparison(mapVo);
			
			int addCount = accFinancialAccountingComparisonMapper.addFinancialAccountingComparison(mapVo);
			if(addCount == 0){
				new SysException();
			}
			return "{\"msg\":\"设置成功！\",\"state\":true}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteFinancialAccountingComparison(Map<String, Object> mapVo) {
		
		try {
			
			int deleteCount = accFinancialAccountingComparisonMapper.deleteFinancialAccountingComparison(mapVo);
			
			return "{\"msgs\":\"删除成功！\",\"state\":true}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public List<Map<String, Object>> queryFinancialAccountingComparisonPrint(
			Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		return accFinancialAccountingComparisonMapper.queryFinancialAccountingComparison(mapVo);
	}

	@Override
	public String importFinancialAccountingComparison(Map<String, Object> mapVo) {
		int addCount = 0;
		try {

			List<Map> addList = new ArrayList<Map>();
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if(list != null && list.size() > 0){
				for(Map<String, List<String>> map : list){
					Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("ACC_SUBJ_CODE", map.get("ACC_SUBJ_CODE").get(1));
						saveMap.put("BUDG_SUBJ_CODE", map.get("BUDG_SUBJ_CODE").get(1));
						addList.add(saveMap);
				}
				if (addList.size() > 0) {
					accFinancialAccountingComparisonMapper.deleteimportFinancial(addList,mapVo);
					addCount = accFinancialAccountingComparisonMapper.importFinancialAccountingComparison(addList,mapVo);
					if (addCount == 0) {
						throw new SysException("导入失败,请重试!");
					}
					List<String> reStr = accFinancialAccountingComparisonMapper.queryFinancialCode(mapVo);
					if(reStr == null || reStr.size() > 0){
						throw new SysException("以下科目不存在："+reStr.toString());
					}
				}
			}
			return "{\"msg\":\"成功导入 "+addCount+" 条数据!\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage(),e);
		}
	}
	
	@Override
	public String updateSmartSubj(Map<String, Object> mapVo) {
		
		try {

			int reCount=accFinancialAccountingComparisonMapper.updateSmartSubj(mapVo);
			
			return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage(),e);
		}
	}
	
	//查询资金来源
	@Override
	public List<Map<String, Object>> queryHosSource(Map<String, Object> mapVo) throws DataAccessException{
		return accFinancialAccountingComparisonMapper.queryHosSource(mapVo);
	}
	
	//按资金来源设置
	@Override
	public Map<String, Object> addFinancialAccountingComparisonBySource(Map<String, Object> mapVo) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			//解析明细数据
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray(String.valueOf(mapVo.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> detailMap = null;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				if(jsonObj.getString("subj_code_k") == null || "".equals(jsonObj.getString("subj_code_k"))){
					continue;
				}
				detailMap = new HashMap<String, Object>();
				detailMap.put("source_id", jsonObj.getString("source_id"));
				detailMap.put("subj_code_k", jsonObj.getString("subj_code_k"));
				
				list.add(detailMap);
			}
			//先删除后添加
			accFinancialAccountingComparisonMapper.deleteFinancialAccountingComparison(mapVo);
			if(list.size() > 0){
				accFinancialAccountingComparisonMapper.addBatchFinancialAccountingComparison(mapVo, list);
			}
			retMap.put("state", true);
			retMap.put("msg", "设置成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
}

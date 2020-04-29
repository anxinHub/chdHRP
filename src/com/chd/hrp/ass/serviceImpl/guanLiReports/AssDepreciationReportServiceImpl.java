/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.guanLiReports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.guanLiReports.AssDepreciationReportMapper;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.guanLiReports.AssDepreciationReportService;
import com.github.pagehelper.PageInfo;

/**
 * 资产折旧报表
 * @author fhqfm
 *
 */ 

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("assDepreciationReportService")
public class AssDepreciationReportServiceImpl implements AssDepreciationReportService {

	private static Logger logger = Logger.getLogger(AssDepreciationReportServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDepreciationReportMapper")
	private final AssDepreciationReportMapper assDepreciationReportMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Override
	public String queryAssDepreciationAnalyse(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("type_level", entityMap.get("type_level") == null ? 1 :entityMap.get("type_level"));
		
		//科室分类
		List<Map<String,Object>> HosDeptKinds = assDepreciationReportMapper.queryHosDeptKinds(entityMap);
		
		//医疗成本合计
		Map<String,Object> deptKind1 = new HashMap<String,Object>();
		deptKind1.put("kind_code", "medicalCostTotal");
		deptKind1.put("kind_name", "医疗成本合计");
		
		//总合计
		Map<String,Object> deptKind2 = new HashMap<String,Object>();
		deptKind2.put("kind_code", "allTotal");
		deptKind2.put("kind_name", "总合计");
		
		HosDeptKinds.add(deptKind1);
		HosDeptKinds.add(deptKind2);
		
		
		//资产分类
		List<Map<String,Object>> assTypeDicts = assDepreciationReportMapper.queryAssTypeDicts(entityMap);
		
		//资金来源
		List<Map<String,Object>> hosSources = assDepreciationReportMapper.queryHosSources(entityMap);
		
		StringBuffer jsonStr=new StringBuffer();
		jsonStr.append("{\"Rows\":[");
		
		//
		for (Map<String, Object> deptKind : HosDeptKinds) {
			entityMap.put("kind_code", deptKind.get("kind_code"));
			
			for (Map<String, Object> assType : assTypeDicts) {
				assType.remove("price");
				assType.remove("now_depre_amount");
				assType.remove("add_depre_amount");
			}
			
			List<Map<String, Object>> list = null;
			List<Map<String, Object>> addPriceList = null;
			List<Map<String, Object>> delPriceList = null;
			List<Map<String, Object>> notDepreList = null;
			if("allTotal".equals(deptKind.get("kind_code").toString())){ 
				list = assDepreciationReportMapper.queryAssDepreciationAnalyseAllTotal(entityMap);
				addPriceList = assDepreciationReportMapper.queryAssDepreciationAnalyseAllTotalAddPrice(entityMap);
				delPriceList = assDepreciationReportMapper.queryAssDepreciationAnalyseAllTotalDelPrice(entityMap);
				notDepreList = assDepreciationReportMapper.queryAssDepreciationAnalyseNotDepre(entityMap);
			}else if("medicalCostTotal".equals(deptKind.get("kind_code").toString())){
				list = assDepreciationReportMapper.queryAssDepreciationAnalyseMedicalCostTotal(entityMap);
				addPriceList = assDepreciationReportMapper.queryAssDepreciationAnalyseMCostTotalAddPrice(entityMap);
				delPriceList = assDepreciationReportMapper.queryAssDepreciationAnalyseMCostTotalDelPrice(entityMap);
			}else{
				list = assDepreciationReportMapper.queryAssDepreciationAnalyse(entityMap);
				addPriceList = assDepreciationReportMapper.queryAssDepreciationAnalyseAddPrice(entityMap);
				delPriceList = assDepreciationReportMapper.queryAssDepreciationAnalyseDelPrice(entityMap);
			}
			
			
			
			Map<String, Object> keyMap = new HashMap<String, Object>();
			for(Map<String, Object> temp : addPriceList){
				if(temp.get("kind_code") == null){
					continue;
				}
				keyMap.put(temp.get("kind_code").toString()+temp.get("source_id").toString()+temp.get("ass_type_code").toString(), temp.get("price") == null ? "0":temp.get("price").toString());
			}
			
			Map<String, Object> delKeyMap = new HashMap<String, Object>();
			for(Map<String, Object> temp : delPriceList){
				if(temp.get("kind_code") == null){
					continue;
				}
				delKeyMap.put(temp.get("kind_code").toString()+temp.get("source_id").toString()+temp.get("ass_type_code").toString(), temp.get("price") == null ? "0":temp.get("price").toString());
			}
			
			
			Map<String, Object> notDepreKeyMap = new HashMap<String, Object>();
			if(notDepreList != null){
				for(Map<String, Object> temp : notDepreList){
					if(temp.get("kind_code") == null){
						continue;
					}
					notDepreKeyMap.put(temp.get("kind_code").toString()+temp.get("source_id").toString()+temp.get("ass_type_code").toString(), temp.get("price") == null ? "0":temp.get("price").toString());
				}
			}
			
			
			
			//原值
			Double total_price = 0.0;//原值总合计
			for (Map<String, Object> source : hosSources) {
				jsonStr.append("{");
				jsonStr.append("'kind_name':'"+deptKind.get("kind_name")+"',");
				jsonStr.append("'p_name':'原值',");
				jsonStr.append("'source_name':'"+source.get("source_name")+"',");
				//List<Map<String, Object>> list = assDepreciationReportMapper.queryAssDepreciationAnalyse(entityMap);
				Double total = 0.0;
				for (Map<String, Object> assType : assTypeDicts) {
					assType.put("price", assType.get("price") == null ? 0 : assType.get("price"));
					if(list != null && list.size() > 0){
						for (Map<String, Object> map : list) {
							if(map != null && map.get("source_id") != null && source.get("source_id").toString().equals(map.get("source_id").toString())){
								if(assType.get("ass_type_code").toString().equals(map.get("ass_type_code").toString())){
									//提前当前月新增资产的原值
									Double addPrice = Double.parseDouble(keyMap.get(deptKind.get("kind_code").toString()+source.get("source_id").toString()+map.get("ass_type_code").toString()) == null ? "0":keyMap.get(deptKind.get("kind_code").toString()+source.get("source_id").toString()+map.get("ass_type_code").toString()).toString());
									
									Double delPrice = Double.parseDouble(delKeyMap.get(deptKind.get("kind_code").toString()+source.get("source_id").toString()+map.get("ass_type_code").toString()) == null ? "0":delKeyMap.get(deptKind.get("kind_code").toString()+source.get("source_id").toString()+map.get("ass_type_code").toString()).toString());
									
									Double notDeprePrice = Double.parseDouble(notDepreKeyMap.get(deptKind.get("kind_code").toString()+source.get("source_id").toString()+map.get("ass_type_code").toString()) == null ? "0":notDepreKeyMap.get(deptKind.get("kind_code").toString()+source.get("source_id").toString()+map.get("ass_type_code").toString()).toString());
									
									jsonStr.append("'ass_"+assType.get("ass_type_code").toString()+"':'"+(Double.parseDouble(map.get("price").toString()) + addPrice - delPrice + notDeprePrice)+"',");
									total += (Double.parseDouble(map.get("price").toString())  - delPrice + notDeprePrice);
									assType.put("price", Double.parseDouble(assType.get("price").toString()) + Double.parseDouble(map.get("price").toString()) + addPrice - delPrice + notDeprePrice);
								}
							}
						}
					}
				}
				total_price += total;
				jsonStr.append("'total':'"+ total +"',");
				jsonStr.append("}");
			}
			
			//小计
			jsonStr.append("{");
			jsonStr.append("'kind_name':'"+deptKind.get("kind_name")+"',");
			jsonStr.append("'p_name':'原值',");
			jsonStr.append("'source_name':'小计',");
			for (Map<String, Object> assType : assTypeDicts) {
				jsonStr.append("'ass_"+assType.get("ass_type_code").toString()+"':'"+ Double.parseDouble(assType.get("price").toString()) +"',");
			}
			jsonStr.append("'total':'"+ total_price +"',");
			jsonStr.append("}");
			
			
			
			//折旧
			Double total_depre = 0.0;//折旧总合计
			for (Map<String, Object> source : hosSources) {
				jsonStr.append("{");
				jsonStr.append("'kind_name':'"+deptKind.get("kind_name")+"',");
				jsonStr.append("'p_name':'折旧',");
				jsonStr.append("'source_name':'"+source.get("source_name")+"',");
				//List<Map<String, Object>> list = assDepreciationReportMapper.queryAssDepreciationAnalyse(entityMap);
				Double total = 0.0;
				for (Map<String, Object> assType : assTypeDicts) {
					assType.put("now_depre_amount", assType.get("now_depre_amount") == null ? 0 : assType.get("now_depre_amount"));
					for (Map<String, Object> map : list) {
						if(map != null && map.get("source_id") != null && source.get("source_id").toString().equals(map.get("source_id").toString())){
							
							if(assType.get("ass_type_code").toString().equals(map.get("ass_type_code").toString())){
								jsonStr.append("'ass_"+assType.get("ass_type_code").toString()+"':'"+map.get("now_depre_amount")+"',");
								total += Double.parseDouble(map.get("now_depre_amount").toString());
								assType.put("now_depre_amount", Double.parseDouble(assType.get("now_depre_amount").toString()) + Double.parseDouble(map.get("now_depre_amount").toString()));
							}
						}
					}
					
				}
				total_depre += total;
				jsonStr.append("'total':'"+ total +"',");
				jsonStr.append("}");
			}
			
			//小计
			jsonStr.append("{");
			jsonStr.append("'kind_name':'"+deptKind.get("kind_name")+"',");
			jsonStr.append("'p_name':'折旧',");
			jsonStr.append("'source_name':'小计',");
			for (Map<String, Object> assType : assTypeDicts) {
				jsonStr.append("'ass_"+assType.get("ass_type_code").toString()+"':'"+ Double.parseDouble(assType.get("now_depre_amount").toString()) +"',");
			}
			jsonStr.append("'total':'"+ total_depre +"',");
			jsonStr.append("}");
			
			
			if(entityMap.get("is_show") != null && "1".equals(entityMap.get("is_show").toString())){
				//累计折旧
				Double total_add_depre = 0.0;//折旧总合计
				for (Map<String, Object> source : hosSources) {
					jsonStr.append("{");
					jsonStr.append("'kind_name':'"+deptKind.get("kind_name")+"',");
					jsonStr.append("'p_name':'累计折旧',");
					jsonStr.append("'source_name':'"+source.get("source_name")+"',");
					//List<Map<String, Object>> list = assDepreciationReportMapper.queryAssDepreciationAnalyse(entityMap);
					Double total = 0.0;
					for (Map<String, Object> assType : assTypeDicts) {
						assType.put("add_depre_amount", assType.get("add_depre_amount") == null ? 0 : assType.get("add_depre_amount"));
						for (Map<String, Object> map : list) {
							if(map != null && map.get("source_id") != null && source.get("source_id").toString().equals(map.get("source_id").toString())){
								
								if(assType.get("ass_type_code").toString().equals(map.get("ass_type_code").toString())){
									jsonStr.append("'ass_"+assType.get("ass_type_code").toString()+"':'"+map.get("add_depre_amount")+"',");
									total += Double.parseDouble(map.get("add_depre_amount").toString());
									assType.put("add_depre_amount", Double.parseDouble(assType.get("add_depre_amount").toString()) + Double.parseDouble(map.get("add_depre_amount").toString()));
								}
							}
						}
						
					}
					total_add_depre += total;
					jsonStr.append("'total':'"+ total +"',");
					jsonStr.append("}");
				}
				
				//小计
				jsonStr.append("{");
				jsonStr.append("'kind_name':'"+deptKind.get("kind_name")+"',");
				jsonStr.append("'p_name':'累计折旧',");
				jsonStr.append("'source_name':'小计',");
				for (Map<String, Object> assType : assTypeDicts) {
					jsonStr.append("'ass_"+assType.get("ass_type_code").toString()+"':'"+ Double.parseDouble(assType.get("add_depre_amount").toString()) +"',");
				}
				jsonStr.append("'total':'"+ total_add_depre +"',");
				jsonStr.append("}");
			}
		}
		
		//医疗成本合计
		
		//总合计
		
		jsonStr.append("],\"state\":\"true\"}");  
		return jsonStr.toString();
		//return ChdJson.toJson(list);
		
		
	}
	
	@Override
	public List<Map<String,Object>> queryAssDepreciationAnalysePrint(Map<String, Object> entityMap) throws DataAccessException {
		String str = queryAssDepreciationAnalyse(entityMap);
		List<Map<String, Object>> list = null;
		try {
			Map map = JSONObject.parseObject(str, Map.class);
			list = (List<Map<String, Object>>) map.get("Rows");
		} catch (Exception e) {
		}
		
		return list;
	}


	@Override
	public String queryAssDepreciationSummary(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = assDepreciationReportMapper.queryAssDepreciationSummary(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = assDepreciationReportMapper.queryAssDepreciationSummary(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}


	@Override
	public String queryAssDepreciationInfo(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = assDepreciationReportMapper.queryAssDepreciationInfo(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = assDepreciationReportMapper.queryAssDepreciationInfo(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	private static boolean isNumeric(final CharSequence cs) {
        if (cs.length() == 0) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


	@Override
	public String queryAssTypeHead(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = assDepreciationReportMapper.queryAssTypeDicts(entityMap);
		return ChdJson.toJson(list);
	}



	@Override
	public List<Map<String, Object>> queryAssDeprePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDepreciationReportMapper.queryAssDeprePrint(entityMap);
	
		return list;
	}


	@Override
	public List<Map<String, Object>> queryAssInfoPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDepreciationReportMapper.queryAssInfoPrint(entityMap);
	
		return list;
	}
	
	
}

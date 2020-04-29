/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.autovouch.AccBusiMapMapper;
import com.chd.hrp.acc.dao.autovouch.AccBusiTemplateDetailMapper;
import com.chd.hrp.acc.dao.autovouch.AccBusiTemplateMapper;
import com.chd.hrp.acc.dao.autovouch.AccBusiTypeMapper;
import com.chd.hrp.acc.entity.autovouch.AccBusiMap;
import com.chd.hrp.acc.entity.autovouch.AccBusiMeta;
import com.chd.hrp.acc.entity.autovouch.AccBusiTemplate;
import com.chd.hrp.acc.entity.autovouch.AccBusiTemplateDetail;
import com.chd.hrp.acc.entity.autovouch.AccBusiType;
import com.chd.hrp.acc.entity.autovouch.SysBusiTable;
import com.chd.hrp.acc.service.autovouch.AccBusiTemplateService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 客户字典属性表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBusiTemplateService")
public class AccBusiTemplateServiceImpl implements AccBusiTemplateService {

	private static Logger logger = Logger.getLogger(AccBusiTemplateServiceImpl.class);

	@Resource(name = "accBusiTemplateMapper")
	private final AccBusiTemplateMapper accBusiTemplateMapper = null;

	@Resource(name = "accBusiTemplateDetailMapper")
	private final AccBusiTemplateDetailMapper accBusiTemplateDetailMapper = null;

	@Resource(name = "accBusiTypeMapper")
	private final AccBusiTypeMapper accBusiTypeMapper = null;
	
	@Resource(name = "accBusiMapMapper")
	private final AccBusiMapMapper accBusiMapMapper = null;

	@Override
	public String queryAccBusiTemplate(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AccBusiTemplate> list = accBusiTemplateMapper.queryAccBusiTemplate(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AccBusiTemplate> list = accBusiTemplateMapper.queryAccBusiTemplate(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryAccBusiTypeTree(Map<String, Object> entityMap) throws DataAccessException {

		List<AccBusiType> list = accBusiTypeMapper.queryAccBusiTypeTree(entityMap);

		return ChdJson.toJson(list);
	}

	@Override
	public String queryAccBusiHosResource(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> list = accBusiTemplateMapper.queryAccBusiHosResource(entityMap);

		return ChdJson.toJsonLower(list);

	}
	@Override
	public String addAccBusiTemplate(Map<String, Object> entityMap) throws DataAccessException {

		String saveFlag = (String)entityMap.get("saveFlag");
		
		if("true".equals(saveFlag)){
			
			AccBusiTemplate abt = accBusiTemplateMapper.queryAccBusiTemplateByCode(entityMap);

			if (abt != null) {

				return "{\"error\":\"保存失败，已经存在此模板编码\"}";

			}
			
		}

		try {

			List<Map<String, Object>> template_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			
			
			JSONArray template_detail__json_acc = JSONArray.parseArray((String) entityMap.get("acc_busi_template_detail_acc"));// 解析明细数据
			JSONArray template_detail__json_budg = JSONArray.parseArray((String) entityMap.get("acc_busi_template_detail_budg"));

			Iterator template_detail_it_acc = template_detail__json_acc.iterator();
			Iterator template_detail_it_budg = template_detail__json_budg.iterator();

			try {

				while (template_detail_it_acc.hasNext()) {

					Map<String, Object> mapDetailVo = new HashMap<String, Object>();

					mapDetailVo.put("group_id", entityMap.get("group_id"));

					mapDetailVo.put("hos_id", entityMap.get("hos_id"));

					mapDetailVo.put("copy_code", entityMap.get("copy_code"));

					mapDetailVo.put("acc_year", entityMap.get("acc_year"));

					mapDetailVo.put("mod_code", entityMap.get("mod_code"));

					mapDetailVo.put("busi_type_code", entityMap.get("busi_type_code"));

					mapDetailVo.put("template_code", entityMap.get("template_code"));
					
					mapDetailVo.put("kind_code", "01");
					
					mapDetailVo.put("cal", "");

					JSONObject jsonObj = JSONObject.parseObject(template_detail_it_acc.next().toString());

					if (validateJSON(String.valueOf(jsonObj.get("vouch_row")))) {
						
						mapDetailVo.put("vouch_row", jsonObj.get("vouch_row").toString());
						
					} else {
						
						break;
						
					}

					if (validateJSON(String.valueOf(jsonObj.get("meta_code")))) {
						
						mapDetailVo.put("meta_code", jsonObj.get("meta_code").toString());
						
					} else {
						
						mapDetailVo.put("meta_code", "");
						
					}

					if (validateJSON(String.valueOf(jsonObj.get("direction")))) {
						
						mapDetailVo.put("direction", Integer.parseInt(jsonObj.get("direction").toString()));
						
					} else {
						
						mapDetailVo.put("direction", 0);
						
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("sort_code")))) {
						
						mapDetailVo.put("sort_code", jsonObj.get("sort_code").toString());
						
					} else {
						
						mapDetailVo.put("sort_code", "");
						
					}
					

					if ("1".equals(entityMap.get("is_detail_summary").toString())) {

						if (validateJSON(String.valueOf(jsonObj.get("summary")))) {
							
							mapDetailVo.put("summary", jsonObj.get("summary").toString());
							
						} else {
							
							mapDetailVo.put("summary", "");
							
						}

					} else {

						mapDetailVo.put("summary", entityMap.get("summary"));

					}

					template_detail_batch.add(mapDetailVo);

				}
				
				
				while (template_detail_it_budg.hasNext()) {

					Map<String, Object> mapDetailVo = new HashMap<String, Object>();

					mapDetailVo.put("group_id", entityMap.get("group_id"));

					mapDetailVo.put("hos_id", entityMap.get("hos_id"));

					mapDetailVo.put("copy_code", entityMap.get("copy_code"));

					mapDetailVo.put("acc_year", entityMap.get("acc_year"));

					mapDetailVo.put("mod_code", entityMap.get("mod_code"));

					mapDetailVo.put("busi_type_code", entityMap.get("busi_type_code"));

					mapDetailVo.put("template_code", entityMap.get("template_code"));
					
					mapDetailVo.put("sort_code", "");
					
					mapDetailVo.put("kind_code", "02");
					

					JSONObject jsonObj = JSONObject.parseObject(template_detail_it_budg.next().toString());

					if (validateJSON(String.valueOf(jsonObj.get("vouch_row")))) {
						
						mapDetailVo.put("vouch_row", jsonObj.get("vouch_row").toString());
						
					} else {
						
						break;
						
					}

					if (validateJSON(String.valueOf(jsonObj.get("meta_code")))) {
						
						mapDetailVo.put("meta_code", jsonObj.get("meta_code").toString());
						
					} else {
						
						mapDetailVo.put("meta_code", "");
						
					}
					
					if (validateJSON(String.valueOf(jsonObj.get("cal")))) {
						
						mapDetailVo.put("cal", jsonObj.get("cal").toString());
						
					} else {
						
						mapDetailVo.put("cal", "");
						
					}

					if (validateJSON(String.valueOf(jsonObj.get("direction")))) {
						
						mapDetailVo.put("direction", Integer.parseInt(jsonObj.get("direction").toString()));
						
					} else {
						
						mapDetailVo.put("direction", 0);
						
					}

					if ("1".equals(entityMap.get("is_detail_summary").toString())) {

						if (validateJSON(String.valueOf(jsonObj.get("summary")))) {
							
							mapDetailVo.put("summary", jsonObj.get("summary").toString());
							
						} else {
							
							mapDetailVo.put("summary", "");
							
						}

					} else {

						mapDetailVo.put("summary", entityMap.get("summary"));

					}

					template_detail_batch.add(mapDetailVo);

				}
				
			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"保存失败\"}";

			}
			
			if("true".equals(saveFlag)){
				
				accBusiTemplateMapper.addAccBusiTemplate(entityMap);
				
			}else{
				
				accBusiTemplateMapper.updateAccBusiTemplate(entityMap);
				
			}
			

			List<Map<String, Object>> listDeleteVo = new ArrayList<Map<String, Object>>();

			listDeleteVo.add(entityMap);

			accBusiTemplateDetailMapper.deleteBatchAccBusiTemplateDetail(listDeleteVo);

			accBusiTemplateDetailMapper.addBatchAccBusiTemplateDetail(template_detail_batch);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (DataAccessException e) {
			throw new SysException(e.getMessage());
		}
	}

	public boolean validateJSON(String str) {

		if (str != null && !"null".equals(str) && !"".equals(str)) {

			return true;

		}

		return false;

	}

	@Override
	public String delBatchAccBusiTemplate(List<Map<String, Object>> entityMap) throws DataAccessException {

		try {

			accBusiTemplateDetailMapper.deleteBatchAccBusiTemplateDetail(entityMap);

			accBusiTemplateMapper.deleteBatchAccBusiTemplate(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String queryAccBusiRelaForMetaCode(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> list = accBusiTemplateMapper.queryAccBusiRelaForMetaCode(entityMap);

		return ChdJson.toJsonLower(list);

	}

	@Override
	public String queryAccBusiHosStore(Map<String, Object> entityMap) throws DataAccessException {
		
		//原始条件
		if(entityMap.get("whereSql") == null || "".equals(entityMap.get("whereSql").toString())){
			entityMap.put("whereSql", " and is_med=1");
		}
		
		List<Map<String,Object>> list = accBusiTemplateMapper.queryAccBusiHosStore(entityMap);

		return ChdJson.toJsonLower(list);

	}

	@Override
	public String queryAccBusiTemplateDetail(Map<String, Object> entityMap) throws DataAccessException {
		JSONObject jsonObj = new JSONObject();
		List<AccBusiTemplateDetail> listAcc = accBusiTemplateDetailMapper.queryAccBusiTemplateDetailAcc(entityMap);
		
		
		JSONObject jsonObjAcc = new JSONObject();
		jsonObjAcc.put("Rows", listAcc);
		jsonObjAcc.put("Total", listAcc.size());
		
		
		List<AccBusiTemplateDetail> listBudg = accBusiTemplateDetailMapper.queryAccBusiTemplateDetailBudg(entityMap);
		JSONObject jsonObjBudg = new JSONObject();
		jsonObjBudg.put("Rows", listBudg);
		jsonObjBudg.put("Total", listBudg.size());
		
		
		jsonObj.put("accData", jsonObjAcc);
		
		jsonObj.put("budgData", jsonObjBudg);
		
		
		return jsonObj.toJSONString();
	}

	@Override
	public AccBusiTemplate queryAccBusiTemplateByCode(Map<String, Object> entityMap) throws DataAccessException {

		return accBusiTemplateMapper.queryAccBusiTemplateByCode(entityMap);
	}

	@Override
	public String queryAccBusiRelaForAccSubj(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String,Object>> list = accBusiTemplateMapper.queryAccBusiRelaForAccSubj(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public AccBusiMeta queryAccBusiMetaByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return accBusiTemplateMapper.queryAccBusiMetaByCode(entityMap);
	}

	//保存类别会计科目对应关系
	@Override
	public String saveAccBusiMap(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//mapVo.put("subj_id", mapVo.get("old_subj_id"));
			
			
			Map<String,Object> newMap = accBusiMapMapper.querySysBbusiTable(entityMap);
            //判断字典表是否有级次，如果有级次走批量调整
			if(newMap!=null && newMap.get("LEVEL_FIELD") !=null && !"".equals(newMap.get("LEVEL_FIELD")) && !"null".equals(newMap.get("LEVEL_FIELD")))
			
			{
				entityMap.put("id_field2", newMap.get("ID_FIELD"));
				
				entityMap.put("code_field2", newMap.get("CODE_FIELD"));
				
				List<Map<String,Object>> list = accBusiMapMapper.queryTableIdSql(entityMap);
				
				for(Map<String,Object> item: list){
					
					//动态查询出来的列头，数据类型变化成对象，要转化
					item.put("type_id", String.valueOf(item.get("type_id")));
					item.put("sub_type_id", String.valueOf(item.get("sub_type_id")));
					
					if(item.get("out_code") == null){
						item.put("out_code", "");
					}
					if(item.get("source_nature_code") == null){
						item.put("source_nature_code", "");
					}
					if(item.get("store_id") == null){
						item.put("store_id", "");
					}
					
				}
				accBusiMapMapper.deleteBatchAccBusiMap(list);
				
				accBusiMapMapper.addBatchAccBusiMap(list);
				
				
				
			}
			else 
			{
			accBusiMapMapper.deleteAccBusiMap(entityMap);
			
			accBusiMapMapper.addAccBusiMap(entityMap);
			}

			return "{\"msg\":\"配置成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public AccBusiMap queryAccBusiMapByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return accBusiMapMapper.queryAccBusiMapByCode(entityMap);
		
	}

	@Override
	public String delAccBusiMap(Map<String, Object> entityMap) throws DataAccessException {
		
		try {

			accBusiMapMapper.deleteAccBusiMap(entityMap);

			return "{\"msgs\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public SysBusiTable querySysBusiTableByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return accBusiMapMapper.querySysBusiTableByCode(entityMap);
	}

	@Override
	public String querySysBusiTable(Map<String, Object> entityMap) throws DataAccessException {
		
		List<SysBusiTable> list = accBusiMapMapper.querySysBusiTable(entityMap);

		return ChdJson.toJson(list);
	}

	@Override
	public String querySelectSql(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String,Object>> list = (List<Map<String,Object>>)accBusiMapMapper.querySelectSql(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public Integer maxTypeLevel(Map<String, Object> entityMap) throws DataAccessException {
		
		return accBusiMapMapper.maxTypeLevel(entityMap);
		
	}

	@Override
	public String queryAccBusiRelaForStoreAutoSet(Map<String, Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = new LinkedList<Map<String,Object>>();
		
		//根据table_id查询表sys_busi_table得到类别对应的字段
		SysBusiTable stb = accBusiMapMapper.querySysBusiTableByCode(map);
		//拼装主sql语句
		map.put("t_id_field", stb.getId_field());
		map.put("t_no_field", stb.getNo_field());
		map.put("t_code_field", stb.getCode_field());
		map.put("t_name_field", stb.getName_field());
		map.put("t_level_field", stb.getLevel_field());
		map.put("t_year_field", stb.getYear_field());
		map.put("t_table_level", stb.getTable_level());
		map.put("t_where_sql", stb.getWhere_sql());
		
		List<Map<String, Object>> dataList = new LinkedList<Map<String,Object>>();
		dataList = accBusiTemplateMapper.queryAccBusiRelaForStoreAutoSet(map);
		
		Map<String, Integer> existsMap = new HashMap<String, Integer>();
		Map<String, Object> tmpMap = null;
		String id_field = "";
		int index = 0;
		//解析结果集，转换为以库房为键，科目为值以便前台展示
		for(Map<String, Object> dataMap : dataList){
			id_field = dataMap.get("ID_FIELD").toString();
			if(existsMap.containsKey(id_field)){
				tmpMap = list.get(existsMap.get(id_field));

				if(dataMap.get("STORE_ID") != null && !"".equals(dataMap.get("STORE_ID").toString())){
					tmpMap.put(dataMap.get("STORE_ID").toString(), dataMap.get("SUBJ_CODE"));
					tmpMap.put(dataMap.get("STORE_ID").toString()+"_name", dataMap.get("SUBJ_NAME"));
				}
			}else{
				tmpMap = new HashMap<String, Object>();
				
				tmpMap.put("id_field", dataMap.get("ID_FIELD"));
				tmpMap.put("no_field", dataMap.get("NO_FIELD"));
				tmpMap.put("code_field", dataMap.get("CODE_FIELD"));
				tmpMap.put("name_field", dataMap.get("NAME_FIELD"));
				
				tmpMap.put("group_id", dataMap.get("GROUP_ID"));
				tmpMap.put("hos_id", dataMap.get("HOS_ID"));
				tmpMap.put("copy_code", dataMap.get("COPY_CODE"));
				tmpMap.put("acc_year", dataMap.get("ACC_YEAR"));
				tmpMap.put("mod_code", dataMap.get("MOD_CODE"));
				tmpMap.put("meta_code", dataMap.get("META_CODE"));
				tmpMap.put("sub_type_id", dataMap.get("SUB_TYPE_ID"));
				if(dataMap.get("STORE_ID") != null && !"".equals(dataMap.get("STORE_ID").toString())){
					tmpMap.put(dataMap.get("STORE_ID").toString(), dataMap.get("SUBJ_CODE"));
					tmpMap.put(dataMap.get("STORE_ID").toString()+"_name", dataMap.get("SUBJ_NAME"));
				}
				
				list.add(tmpMap);
				existsMap.put(id_field, index);
				index ++;
			}
		}
		
		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryAccSubjForAutoSet(Map<String, Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String,Object>> list = accBusiTemplateMapper.queryAccSubjForAutoSet(map);

		return ChdJson.toJsonLower(list);
	}

	//根据保存类别库房科目对应关系
	@Override
	public Map<String, Object> saveAccBusiMapByStore(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			//根据table_id查询表sys_busi_table得到类别对应的字段
			SysBusiTable stb = accBusiMapMapper.querySysBusiTableByCode(map);
			//拼装主sql语句
			map.put("t_id_field", stb.getId_field());
			map.put("t_no_field", stb.getNo_field());
			map.put("t_code_field", stb.getCode_field());
			map.put("t_name_field", stb.getName_field());
			map.put("t_level_field", stb.getLevel_field());
			map.put("t_year_field", stb.getYear_field());
			map.put("t_table_level", stb.getTable_level());
			map.put("t_where_sql", stb.getWhere_sql());

			//解析明细数据
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray(String.valueOf(map.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> detailMap = null;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());

				detailMap = new HashMap<String, Object>();
				detailMap.put("type_id", jsonObj.getString("type_id"));
				detailMap.put("code_field", jsonObj.getString("code_field"));
				detailMap.put("store_id", jsonObj.getString("store_id"));
				detailMap.put("subj_code", jsonObj.getString("subj_code"));
				
				list.add(detailMap);
			}
			
			if(list.size() > 0){
				//先删除后添加
				accBusiTemplateMapper.deleteAccBusiMapByStore(map, list);
				accBusiTemplateMapper.addAccBusiMapByStore(map, list);
			}
			
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}

	//根据保存类别库房科目对应关系
	@Override
	public Map<String, Object> saveAccBusiMapByStoreBatch(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			//根据table_id查询表sys_busi_table得到类别对应的字段
			SysBusiTable stb = accBusiMapMapper.querySysBusiTableByCode(map);
			//拼装主sql语句
			map.put("t_id_field", stb.getId_field());
			map.put("t_no_field", stb.getNo_field());
			map.put("t_code_field", stb.getCode_field());
			map.put("t_name_field", stb.getName_field());
			map.put("t_level_field", stb.getLevel_field());
			map.put("t_year_field", stb.getYear_field());
			map.put("t_table_level", stb.getTable_level());
			map.put("t_where_sql", stb.getWhere_sql());

			//解析仓库科目数据
			List<Map<String, Object>> storeSubjList = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray(String.valueOf(map.get("storeSubjData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> detailMap = null;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				//过滤未勾选复选框的数据
				if("false".equals(jsonObj.getString("is_check"))){
					continue;
				}
				detailMap = new HashMap<String, Object>();
				detailMap.put("store_id", jsonObj.getString("store_id"));
				detailMap.put("subj_code", jsonObj.getString("subj_code"));
				
				storeSubjList.add(detailMap);
			}
			
			//解析表格勾选数据结合上面仓库科目数据得最终数据
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			json = JSONArray.parseArray(String.valueOf(map.get("checkData")));
			iterator = json.iterator();
			jsonObj = null;
			detailMap = null;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(JSONObject.parseObject(iterator.next().toString()).getString("rowData"));
				for(Map<String, Object> tmpMap : storeSubjList){
					detailMap = new HashMap<String, Object>();
					detailMap.put("type_id", jsonObj.getString("type_id"));
					detailMap.put("code_field", jsonObj.getString("code_field"));
					detailMap.put("store_id", tmpMap.get("store_id"));
					detailMap.put("subj_code", tmpMap.get("subj_code"));
					list.add(detailMap);
				}
			}
			
			if(list.size() > 0){
				//先删除后添加
				accBusiTemplateMapper.deleteAccBusiMapByStore(map, list);
				accBusiTemplateMapper.addAccBusiMapByStore(map, list);
			}
			
			retMap.put("msg", "操作成功！");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
	}
}

package com.chd.hrp.htcg.serviceImpl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htcg.dao.info.HtcgRecipeTypeMapper;
import com.chd.hrp.htcg.dao.model.HtcgDrgsPmodelMapper;
import com.chd.hrp.htcg.entity.info.HtcgRecipeType;
import com.chd.hrp.htcg.service.model.HtcgDrgsPmodelService;
import com.github.pagehelper.PageInfo;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Service("htcgDrgsPmodelService")
public class HtcgDrgsPmodelServiceImpl implements HtcgDrgsPmodelService {

	private static Logger logger = Logger.getLogger(HtcgDrgsPmodelServiceImpl.class);

	@Resource(name = "htcgDrgsPmodelMapper")
	private final HtcgDrgsPmodelMapper htcgDrgsPmodelMapper = null;

	@Resource(name = "htcgRecipeTypeMapper")
	private final HtcgRecipeTypeMapper htcgRecipeTypeMapper = null;
	
	
	//查询动态项目分类
	@Override
	public String queryHtcgRecipeType(Map<String, Object> entityMap)
			throws DataAccessException {
		List<HtcgRecipeType> recipeTypes = htcgRecipeTypeMapper.queryHtcgRecipeType(entityMap);
		return ChdJson.toJson(recipeTypes);
	}
	
	//生成
	@Override
	public String initHtcgDrgsPmodel(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcgDrgsPmodelMapper.initHtcgDrgsPmodel(entityMap);
			return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}

	}
	
	//查询
	@Override
	public String queryHtcgDrgsPmodel(Map<String, Object> entityMap) throws DataAccessException {
		
		List<HtcgRecipeType> recipeTypes = htcgRecipeTypeMapper.queryHtcgRecipeType(entityMap);
		entityMap.put("recipeTypes", recipeTypes);
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = htcgDrgsPmodelMapper.queryHtcgDrgsPmodel(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = htcgDrgsPmodelMapper.queryHtcgDrgsPmodel(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String saveHtcgDrgsPmodel(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	    try {
	    	
	    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	    	HashMap<String, Object> modelNoteMap = new HashMap<String, Object>();
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				JSONArray detailDataJsonArr = JSONArray.parseArray((String) entityMap.get("detailData"));
				Iterator detailDataJsonArrIt = detailDataJsonArr.iterator();
				while (detailDataJsonArrIt.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(detailDataJsonArrIt.next().toString());
					if(null != jsonObj.get("GROUP_ID")){
						StringBuffer modelNoteKey = new StringBuffer();
						Map<String,Object> detailMap = new HashMap<String,Object>();
						modelNoteKey.append(jsonObj.get("GROUP_ID").toString());
						modelNoteKey.append(jsonObj.get("HOS_ID").toString());
						modelNoteKey.append(jsonObj.get("COPY_CODE").toString());
						modelNoteKey.append(jsonObj.get("PERIOD_TYPE_CODE").toString());
						modelNoteKey.append(jsonObj.get("ACC_YEAR").toString());
						modelNoteKey.append(jsonObj.get("ACC_MONTH").toString());
						modelNoteKey.append(jsonObj.get("SCHEME_CODE").toString());
						modelNoteKey.append(jsonObj.get("DRGS_CODE").toString());
						modelNoteKey.append(jsonObj.get("CLP_P_STEP").toString());
						if(!modelNoteMap.containsKey(modelNoteKey.toString()) && (null != jsonObj.get("MAIN_TREAT") || null != jsonObj.get("MAIN_NURSE"))){
							detailMap.put("group_id", jsonObj.get("GROUP_ID"));
							detailMap.put("hos_id", jsonObj.get("HOS_ID"));
							detailMap.put("copy_code", jsonObj.get("COPY_CODE"));
							detailMap.put("period_type_code", jsonObj.get("PERIOD_TYPE_CODE"));
							detailMap.put("acc_year", jsonObj.get("ACC_YEAR"));
							detailMap.put("acc_month", jsonObj.get("ACC_MONTH"));
							detailMap.put("scheme_code", jsonObj.get("SCHEME_CODE"));
							detailMap.put("drgs_code", jsonObj.get("DRGS_CODE"));
							detailMap.put("clp_p_step", jsonObj.get("CLP_P_STEP"));
							detailMap.put("main_treat", jsonObj.get("MAIN_TREAT") == null?"":jsonObj.get("MAIN_TREAT"));
							detailMap.put("main_nurse", jsonObj.get("MAIN_NURSE") == null?"":jsonObj.get("MAIN_NURSE"));
							modelNoteMap.put(modelNoteKey.toString(), modelNoteKey);
							list.add(detailMap);
						}
					}
					
				}
				
				if(list.size() > 0){
					htcgDrgsPmodelMapper.deleteBatchHtcgDrgsPmodelNote(list);
					htcgDrgsPmodelMapper.addBatchHtcgDrgsPmodelNote(list);
				}
				
			}
	    	return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgDrgsPmodel(List<Map<String,Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			    htcgDrgsPmodelMapper.deleteBatchHtcgDrgsPmodelNote(list);
		    	return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

}

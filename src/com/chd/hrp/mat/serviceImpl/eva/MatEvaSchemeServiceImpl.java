package com.chd.hrp.mat.serviceImpl.eva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.eva.MatEvaSchemeMapper;
import com.chd.hrp.mat.service.eva.MatEvaSchemeService;

@Service("matEvaSchemeService")
public class MatEvaSchemeServiceImpl implements MatEvaSchemeService{

	private static Logger logger = Logger.getLogger(MatEvaSchemeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matEvaSchemeMapper")
	private final MatEvaSchemeMapper matEvaSchemeMapper = null;

	//评价方案查询
	@Override
	public Map<String, Object> queryMatEvaScheme(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = matEvaSchemeMapper.queryMatEvaScheme(mapVo);
		if(resultList.size() == 0){
			return null;
		}else{
			return resultList.get(0);
		}	
	}
	
	//评价方案明细查询
	@Override
	public String queryMatEvaSchemeDetail(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultMap = matEvaSchemeMapper.queryMatEvaSchemeDetail(mapVo);
		return ChdJson.toJson(resultMap);
	}
	
	//保存评价方案指标
	@Override
	public String saveMatEvaScheme(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			// 判断是否保存过主表信息
			List<Map<String, Object>> existList = matEvaSchemeMapper.queryMatEvaScheme(mapVo);
			if(existList.size() == 0){
				
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
				// 保存主表数据
				matEvaSchemeMapper.addMatEvaScheme(mapVo);
			}else{
				matEvaSchemeMapper.updateMatEvaScheme(mapVo);
			}
			
			if(mapVo.get("detailData") != null && !mapVo.get("detailData").equals("[]")){
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)mapVo.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("target_code")) && jsonObj.get("target_code") != null){
						Map<String,Object> detailMap = new HashMap<String, Object>();
						detailMap.put("group_id", mapVo.get("group_id"));
						detailMap.put("hos_id", mapVo.get("hos_id"));
						detailMap.put("copy_code", mapVo.get("copy_code"));
						detailMap.put("scheme_code", mapVo.get("scheme_code"));
						detailMap.put("target_code", jsonObj.get("target_code"));
						detailMap.put("full_score", jsonObj.get("target_full_score"));
						detailMap.put("weight", jsonObj.get("weight"));
						
						detailList.add(detailMap);
					}	
				}
				
				matEvaSchemeMapper.updateMatEvaSchemeDetail(detailList);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String addMatEvaSchemeDetail(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			matEvaSchemeMapper.addMatEvaSchemeDetail(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
}

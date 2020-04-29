package com.chd.hrp.mat.serviceImpl.eva;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.dao.eva.MatEvaSchemeMapper;
import com.chd.hrp.mat.dao.eva.MatEvaSupMapper;
import com.chd.hrp.mat.service.eva.MatEvaSchemeService;
import com.chd.hrp.mat.service.eva.MatEvaSupService;
import com.github.pagehelper.PageInfo;

@Service("matEvaSupService")
public class MatEvaSupServiceImpl implements MatEvaSupService{

	private static Logger logger = Logger.getLogger(MatEvaSupServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matEvaSupMapper")
	private final MatEvaSupMapper matEvaSupMapper = null;

	//评价方案查询
	@Override
	public String queryMatEvaSupInfoLeft(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matEvaSupMapper.queryMatEvaSupInfoLeft(mapVo);
			return ChdJson.toJson(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matEvaSupMapper.queryMatEvaSupInfoLeft(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	//评价方案明细查询
	@Override
	public String queryMatEvaSupMainRight(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matEvaSupMapper.queryMatEvaSupMainRight(mapVo);
			return ChdJson.toJson(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matEvaSupMapper.queryMatEvaSupMainRight(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	// 改变评价状态
	@Override
	public String changeMatEvaSupState(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try{

			matEvaSupMapper.changeMatEvaSupState(mapVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	//删除评价
	@Override
	public String deleteMatEvaSup(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			
			matEvaSupMapper.deleteMatEvaTargetDudect(mapVo);
			matEvaSupMapper.deleteMatEvaSupTarget(mapVo);
			matEvaSupMapper.deleteMatEvaSup(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	// 供应商评价 - 评价指标查询
	@Override
	public String queryMatEvaSupTarget(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> resultList = matEvaSupMapper.queryMatEvaSupTarget(mapVo);
		return ChdJson.toJson(resultList);
	}

	@Override
	public List<Map<String, Object>> queryMatEvaSupMainRightByCode(
			Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return matEvaSupMapper.queryMatEvaSupMainRight(mapVo);
	}
	
	// 供应商评价 - 扣分项查询
	@Override
	public String queryMatEvaSupTargetDeduct(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = matEvaSupMapper.queryMatEvaSupTargetDeduct(mapVo);
		return ChdJson.toJson(resultList);
	}
	
	@Override
	public String queryMatEvaSupTargetForAdd(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resultList = matEvaSupMapper.queryMatEvaSupTargetForAdd(mapVo);
		return ChdJson.toJson(resultList);
	}

	@Override
	public String addOrUpdateMatEvaSup(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		Boolean isAdd = false;
		
		if(mapVo.get("eva_code") == ""){
			isAdd = true;
			mapVo.put("eva_code", UUIDLong.absStringUUID());
			mapVo.put("state", 1);
		}
		// 组装明细数据
		JSONArray json = JSONArray.parseArray((String)mapVo.get("detailData"));
		List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			Map<String,Object> detailMap = new HashMap<String,Object>();
			detailMap.put("group_id", mapVo.get("group_id"));
			detailMap.put("hos_id", mapVo.get("hos_id"));
			detailMap.put("copy_code", mapVo.get("copy_code"));
			detailMap.put("eva_code", mapVo.get("eva_code"));
			detailMap.put("target_code", jsonObj.get("target_code"));
			detailMap.put("full_score", jsonObj.get("full_score"));
			detailMap.put("weight", jsonObj.get("weight"));
			if(jsonObj.get("get_score").toString().contains("-")){
				detailMap.put("scale_no", jsonObj.get("get_score").toString().split("-")[2]);
				detailMap.put("scale_code", jsonObj.get("get_score").toString().split("-")[1]);
				detailMap.put("scale_point", jsonObj.get("get_score").toString().split("-")[0]);
				detailMap.put("get_score", "0");
			}else{ 
				detailMap.put("scale_no", "");
				detailMap.put("scale_code", "");
				detailMap.put("scale_point", "");
				detailMap.put("get_score", jsonObj.get("get_score").equals("") ? "0" : jsonObj.get("get_score"));
			}
			detailMap.put("eva_content", jsonObj.get("eva_content"));
			detailMap.put("sort_code", "");
			detailMap.put("note", ""); 
			
			detailList.add(detailMap);
		}
		
		// 不存在添加
		try{
			
			if(isAdd){
				matEvaSupMapper.addMatEvaSupMain(mapVo);
				matEvaSupMapper.addMatEvaSupTarget(detailList);
			}else{

				matEvaSupMapper.updateMatEvaSupMain(mapVo);
				matEvaSupMapper.updateMatEvaSupTarget(detailList); 
			}
			
			if(mapVo.get("order_ids") != null && mapVo.get("order_ids") != ""){
				matEvaSupMapper.addMatEvaSupBillRela(mapVo);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"eva_code\":\""+mapVo.get("eva_code")+"\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	

	@Override
	public String addMatEvaTargetDudect(Map<String, Object> mapVo) 
			throws DataAccessException{
		// TODO Auto-generated method stub
		// 组装明细数据
		JSONArray json = JSONArray.parseArray((String)mapVo.get("detailData"));
		List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
		Double deductScore = 0.00;
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			 
			if(jsonObj.containsKey("deduct_code") && !jsonObj.get("deduct_code").equals("")){
				deductScore = deductScore + Double.valueOf(jsonObj.containsKey("deduct_score") ? jsonObj.get("deduct_score").toString() : "0");
				
				Map<String,Object> detailMap = new HashMap<String,Object>();
				detailMap.put("group_id", mapVo.get("group_id"));
				detailMap.put("hos_id", mapVo.get("hos_id"));
				detailMap.put("copy_code", mapVo.get("copy_code"));
				detailMap.put("eva_code", mapVo.get("eva_code"));
				detailMap.put("target_code", mapVo.get("target_code"));
				detailMap.put("deduct_code", jsonObj.get("deduct_code"));		
				detailMap.put("deduct_score", jsonObj.containsKey("deduct_score") ? jsonObj.get("deduct_score").toString() : "0");
				detailMap.put("deduct_desc", jsonObj.get("deduct_desc"));
				detailMap.put("note", "");
				
				detailList.add(detailMap);
			}
		}
		
		try{
			
			// 更新评价表的得分
			matEvaSupMapper.updateMatEvaSupMain(mapVo);
			// 删除掉所有扣分明细
			matEvaSupMapper.deleteMatEvaTargetDudect(mapVo);
			// 保存此次所有明细
			if(detailList.size() > 0){
				matEvaSupMapper.addMatEvaTargetDudect(detailList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"eva_code\":\""+mapVo.get("eva_code")+"\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	// 查询标度
	@Override
	public String queryMatEvaTargetScale(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(matEvaSupMapper.queryMatEvaTargetScale(mapVo));
	}

	// 扣分项查询
	@Override
	public String queryMatEvaTargetDeduct(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(matEvaSupMapper.queryMatEvaTargetDeduct(mapVo));
	}
}

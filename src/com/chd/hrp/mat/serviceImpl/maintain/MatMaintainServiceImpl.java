package com.chd.hrp.mat.serviceImpl.maintain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.dao.storage.maintain.MatMaintainMapper;
import com.chd.hrp.mat.service.maintain.MatMaintainService;

@Service("matMaintainService")
public class MatMaintainServiceImpl implements MatMaintainService {

	private static Logger logger=Logger.getLogger(MatMaintainServiceImpl.class);
	//引入DAO操作
	@Resource(name="matMaintainMapper")
	private final MatMaintainMapper matMaintainMapper=null;
	@Override
	public String queryStoreExistInvForMaintain(Map<String, Object> mapVo) {
		return ChdJson.toJson(matMaintainMapper.queryStoreExistInvForMaintain(mapVo));
	}
	
	/**
	 * 查询对应仓库,对应物资类型的库存材料添加养护记录明细表和主表
	 */
	@Override
	public String addMatMaintainMainAndDetailByStoreAndMatType(Map<String, Object> mapVo) {
		try {
			Map<String, Object> mapMain=new HashMap<String, Object>();
			long matMaintainMainID=matMaintainMapper.queryMatMaintainMainSeq();
			mapMain.put("mt_id", matMaintainMainID);
			mapMain.put("mt_no", "YH"+matMaintainMainID);
			mapMain.put("group_id", mapVo.get("group_id"));
			mapMain.put("hos_id", mapVo.get("hos_id"));
			mapMain.put("copy_code", mapVo.get("copy_code"));
			mapMain.put("user_id", mapVo.get("user_id"));
			mapMain.put("maker", mapVo.get("user_id"));
			mapMain.put("make_date", mapVo.get("create_date"));
			mapMain.put("brief", mapVo.get("brief"));
			if (mapVo.get("store_id")!=null && mapVo.get("store_no")!=null) {
				mapMain.put("store_id", mapVo.get("store_id"));
				mapMain.put("store_no", mapVo.get("store_no"));
			}else{
				return "{\"msg\":\"添加失败,缺少仓库信息.\",\"state\":\"false\"}";
			}
			
			//------------------------------持久化数据------------------------------
			matMaintainMapper.addMatMaintainMain(mapMain);
			matMaintainMapper.addMatMaintainMainAndDetailByStoreAndMatType(mapMain);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				mapMain.get("group_id").toString()+","+
				mapMain.get("hos_id").toString()+","+
				mapMain.get("copy_code").toString()+","+
				mapMain.get("mt_id").toString()+","
				+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{error:出错}");
		}
	}
	
	
	/**
	 * 根据页面传递的主表信息,和明细表数据添加养护记录主表,明细表
	 */
	@Override
	public String addMatMaintainMainAndDetail(Map<String, Object> mapVo) {
		try {
			Map<String, Object> mapMain=new HashMap<String, Object>();
			long matMaintainMainID=matMaintainMapper.queryMatMaintainMainSeq();
			mapMain.put("mt_id", matMaintainMainID);
			mapMain.put("mt_no", "YH"+matMaintainMainID);
			
			//----------------------------填充明细数据---------------------------------
			List<Map<String, Object>> maintain_detail_list=new ArrayList<Map<String,Object>>();//存放明细
			
			JSONArray maintain_detail_jsonArray=JSONArray.parseArray((String) mapVo.get("maintain_detail_data"));
			Iterator maintain_detail_iter = maintain_detail_jsonArray.iterator();
			while(maintain_detail_iter.hasNext()){
				Map<String, Object> maintain_detail = JSONObject.parseObject(maintain_detail_iter.next().toString(), Map.class);
				maintain_detail.put("mt_detail_id", matMaintainMapper.queryMatMaintainDetailSeq());
				if(maintain_detail.get("mt_inva_date")!=null){
					SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
					String date=fmt.format(DateUtil.stringToDate(maintain_detail.get("mt_inva_date").toString(), "yyyy-MM-dd"));
					maintain_detail.put("mt_inva_date", date);
				}
				maintain_detail.putAll(mapMain);
				maintain_detail_list.add(maintain_detail);
			}	
			//---------------------------填充主表数据-----------------------------------------
			mapMain.put("group_id", mapVo.get("group_id"));
			mapMain.put("hos_id", mapVo.get("hos_id"));
			mapMain.put("copy_code", mapVo.get("copy_code"));
			mapMain.put("maker", mapVo.get("user_id"));
			mapMain.put("make_date", mapVo.get("create_date"));
			mapMain.put("brief", mapVo.get("brief"));
			if (mapVo.get("store_id")!=null && mapVo.get("store_no")!=null) {
				mapMain.put("store_id", mapVo.get("store_id"));
				mapMain.put("store_no", mapVo.get("store_no"));
			}else{
				return "{\"msg\":\"添加失败,缺少仓库信息.\",\"state\":\"false\"}";
			}
			if (mapVo.get("mat_type_id")!=null && mapVo.get("mat_type_id")!=null) {
				mapMain.put("mat_type_id", mapVo.get("mat_type_id"));
				mapMain.put("mat_type_no", mapVo.get("mat_type_no"));
			}else{
				return "{\"msg\":\"添加失败,缺少材料分类信息.\",\"state\":\"false\"}";
			}
			
			//------------------------------持久化数据------------------------------
			matMaintainMapper.addMatMaintainMain(mapMain);
			matMaintainMapper.addMatMaintainDetailBatch(maintain_detail_list);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				mapMain.get("group_id").toString()+","+
				mapMain.get("hos_id").toString()+","+
				mapMain.get("copy_code").toString()+","+
				mapMain.get("mt_id").toString()+","
				+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{error:出错}");
		}
	}
	/**
	 * 通过主键查询养护记录主表单条数据
	 */
	@Override
	public Map<String, Object> queryMatMaintainMainByID(Map<String, Object> mapVo) {
		return matMaintainMapper.queryMatMaintainMainByID(mapVo);
	}
	/**
	 * 通过主表id查询对应明细信息
	 */
	@Override
	public String queryMatMaintainDetailByMtID(Map<String, Object> mapVo) {
		return ChdJson.toJson(matMaintainMapper.queryMatMaintainDetailByMtID(mapVo));
	}
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryMatMaintainDetailByMtIDForPrint(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = null;
		try {
			list=(List<Map<String, Object>>) matMaintainMapper.queryMatMaintainDetailByMtID(mapVo);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} catch (NoTransactionException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return list;
	}
	/**
	 * 修改养护记录主表从表数据
	 */
	@Override
	public String updateMatMaintainMainAndDetail(Map<String, Object> mapVo) {
		try {
			//----修改主表的备注
			if (mapVo.get("brief_db").toString().equals(mapVo.get("brief").toString())) {
				Map<String, Object> mainMap=new HashMap<String, Object>();
				mainMap.put("brief", mapVo.get("brief").toString());
				mainMap.put("mt_id", mapVo.get("mt_id").toString());
				matMaintainMapper.updateMatMaintainMain(mainMap);
			}
			//-----组装明细信息------------------------------------------------------------
			List<Map<String, Object>> detailList=new ArrayList<Map<String,Object>>();
			JSONArray detailJsonArr = JSONArray.parseArray(mapVo.get("maintain_detail_data").toString());
			Iterator detailArrIter = detailJsonArr.iterator();
			while(detailArrIter.hasNext()){
				Map<String, Object> detailMap = JSONObject.parseObject(detailArrIter.next().toString(),Map.class);
				if(detailMap.get("mt_inva_date")!=null){
					SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
					String date=fmt.format(DateUtil.stringToDate(detailMap.get("mt_inva_date").toString(), "yyyy-MM-dd"));
					detailMap.put("mt_inva_date", date);
				}
				detailList.add(detailMap);
			}
			//---明细持久化-----------------------------------------------------------------
			matMaintainMapper.deleteMatMaintainDetailBatch(detailList);//删除旧明细
			matMaintainMapper.addMatMaintainDetailBatch(detailList);//添加新明细
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{error:出错}");
		}
	}
	/**
	 * 查询养护记录主表信息
	 */
	@Override
	public String queryMatMaintainMain(Map<String, Object> mapVo) {
		return ChdJson.toJson(matMaintainMapper.queryMatMaintainMain(mapVo));
	}
	/**
	 * 删除养护记录主表和对应的明细表
	 */
	@Override
	public String deleteMatMaintainMainAndDetail(List<Map<String, Object>> entityList) {

		try {
			//删除明细
			matMaintainMapper.deleteMatMaintainDetail(entityList);
			//删除主表
			matMaintainMapper.deleteMatMaintainMain(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
}

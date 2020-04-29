/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.info.basic.MatSafeChangeMapper;
import com.chd.hrp.mat.service.base.MatNoManageService;
import com.chd.hrp.mat.service.info.basic.MatSafeChangeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 安全库存调整<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("matSafeChangeService")
public class MatSafeChangeServiceImpl implements MatSafeChangeService {

	private static Logger logger = Logger.getLogger(MatSafeChangeServiceImpl.class);
	
	@Resource(name = "matSafeChangeMapper")
	private final MatSafeChangeMapper matSafeChangeMapper = null;
	@Resource(name="matNoManageService")
	private final MatNoManageService matNoManageService = null;
	
	//列表查询
	@Override
	public String queryMatSafeChangeList(Map<String,Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		//转换日期
		if(map.get("begin_date") != null && !"".equals(map.get("begin_date").toString())){
			map.put("begin_date", DateUtil.stringToDate(map.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(map.get("end_date") != null && !"".equals(map.get("end_date").toString())){
			map.put("end_date", DateUtil.stringToDate(map.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = matSafeChangeMapper.queryMatSafeChangeList(map, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	
	//主表精准查询
	@Override
	public Map<String, Object> queryMatSafeChangeById(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			retMap = matSafeChangeMapper.queryMatSafeChangeById(map);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("系统繁忙，请稍后再试");
		}
		
		return retMap;
	}
	
	//明细精准查询
	@Override
	public String queryMatSafeChangeDetailById(Map<String,Object> map) throws DataAccessException{
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = matSafeChangeMapper.queryMatSafeChangeDetailById(map);
		
		return ChdJson.toJson(list);
	}

	//保存
	@Override
	public Map<String, Object> saveMatSafeChange(Map<String,Object> map)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			if(map.get("change_id") != null && !"".equals(map.get("change_id").toString())){
				//校验状态，已审核不能修改
				map.put("exists_state", 1);
				int count = matSafeChangeMapper.existsMatSafeChangeState(map);
				if(count > 0){
					retMap.put("state", "false");
					retMap.put("error", "单据已审核不能修改！");
					return retMap;
				}
			}
			
			boolean is_add = false;  //判断是添加还是修改
			
			//是否生成单号
			if(map.get("change_no") == null || "系统生成".equals(map.get("change_no").toString())){
				map.put("change_id", UUIDLong.absStringUUID());
				map.put("change_no", matNoManageService.getMatSafeChangeNextNo());
				
				map.put("maker", SessionManager.getUserId());
				map.put("make_date", new Date());
				map.put("state", 1);
				is_add = true;
			}
			
			//解析明细数据
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray(String.valueOf(map.get("allData")));
			Iterator<Object> iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> detailMap = null;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				if(jsonObj.get("inv_id") == null || "".equals(jsonObj.get("inv_id"))){
					//材料为空视为空行
					continue;
				}
				detailMap = new HashMap<String, Object>();
				if(jsonObj.get("detail_id") != null && !"".equals(jsonObj.get("detail_id"))){
					detailMap.put("detail_id", jsonObj.getString("detail_id"));
				}else{
					detailMap.put("detail_id", UUIDLong.absStringUUID());
				}
				detailMap.put("inv_id", jsonObj.getString("inv_id"));
				detailMap.put("period", jsonObj.getInteger("period"));
				detailMap.put("period_num", jsonObj.getFloat("period_num"));
				detailMap.put("high_limit", jsonObj.getFloat("high_limit"));
				detailMap.put("secu_limit", jsonObj.getFloat("secu_limit"));
				detailMap.put("low_limit", jsonObj.getFloat("low_limit"));
				detailMap.put("ps_period", jsonObj.getFloat("ps_period"));
				detailMap.put("cg_period", jsonObj.getFloat("cg_period"));
				detailMap.put("min_pur", jsonObj.getFloat("min_pur"));
				detailMap.put("rxhl_period", jsonObj.getFloat("rxhl_period"));
				detailMap.put("note", jsonObj.getString("note"));
				
				detailList.add(detailMap);
			}
			
			if(detailList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "请添加分录信息");
				return retMap;
			}
			
			if(is_add){
				//添加
				matSafeChangeMapper.addMatSafeChange(map);
			}else{
				//修改
				matSafeChangeMapper.updateMatSafeChangeById(map);
				//删除明细
				matSafeChangeMapper.deleteMatSafeChangeDetailById(map);
			}
			
			//新增明细
			matSafeChangeMapper.addMatSafeChangeDetail(map, detailList);

			retMap.put("msg", "操作成功");
			retMap.put("change_id", map.get("change_id"));
			retMap.put("change_no", map.get("change_no"));
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//删除
	@Override
	public Map<String, Object> deleteMatSafeChange(Map<String, Object> map)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			if(map.get("ids") == null || "".equals(map.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择数据");
				return retMap;
			}
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析出单据ID
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("change_id", id);
				list.add(tmpMap);
			}

			//校验发票状态，已审核不能删除
			map.put("exists_state", 1);
			int count = matSafeChangeMapper.existsMatSafeChangeStateBatch(map, list);
			if(count > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在不是未审核的单据不能删除！");
				return retMap;
			}
			
			matSafeChangeMapper.deleteMatSafeChange(map, list);
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//修改审核状态
	@Override
	public Map<String, Object> updateMatSafeChangeState(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析出单据ID
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("change_id", id);
				list.add(tmpMap);
			}
			
			//校验状态
			int count = matSafeChangeMapper.existsMatSafeChangeStateBatch(map, list);
			if(count > 0){
				retMap.put("state", "false");
				if(map.get("state") != null && "2".equals(map.get("state").toString())){
					retMap.put("error", "存在不是已审核的单据，不能消审！");
				}else{
					retMap.put("error", "存在不是未审核的单据，不能审核！");
				}
				return retMap;
			}
			
			//修改状态
			matSafeChangeMapper.updateMatSafeChangeState(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
			retMap.put("checker_name", map.get("checker") == null ? "" : SessionManager.getUserName());
			retMap.put("check_date", map.get("check_date") == null ? "" : DateUtil.dateToString((Date) map.get("check_date"), "yyyy-MM-dd"));
		}catch (Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//确认
	@Override
	public Map<String, Object> confirmMatSafeChange(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try{
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("state", 3);
			map.put("exists_state", 2);
			map.put("confirmer", SessionManager.getUserId());
			map.put("confirm_date", new Date());
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmpMap = null;
			//解析出单据ID
			for ( String id: map.get("ids").toString().split(",")) {
				tmpMap = new HashMap<String, Object>();
				//表的主键
				tmpMap.put("change_id", id);
				list.add(tmpMap);
			}
			
			//校验状态
			int count = matSafeChangeMapper.existsMatSafeChangeStateBatch(map, list);
			if(count > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在不是已审核的单据，不能确认！");
				return retMap;
			}

			//更新安全库存表
			matSafeChangeMapper.updateMatStoreInvBySafeChange(map, list);
			//修改状态
			matSafeChangeMapper.confirmMatSafeChange(map, list);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
			retMap.put("confirmer_name", map.get("checker") == null ? "" : SessionManager.getUserName());
			retMap.put("confirm_date", map.get("check_date") == null ? "" : DateUtil.dateToString((Date) map.get("check_date"), "yyyy-MM-dd"));
		}catch (Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//材料列表查询
	@Override
	public String queryMatInvBySafeChange(Map<String,Object> map) throws DataAccessException{

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = matSafeChangeMapper.queryMatInvBySafeChange(map, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	
	//模板打印
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public Map<String,Object> queryMatSafeChangeByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException{
		try {
			Map<String,Object> reMap=new HashMap<String,Object>();
			
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map = matSafeChangeMapper.queryMatSafeChangePrintByTemlateBatch(entityMap);
				reMap.put("main", map);
			}else{
				Map<String,Object> map=matSafeChangeMapper.queryMatSafeChangePrintByTemlate(entityMap);
				reMap.put("main", map);
			}
			
			List<Map<String,Object>> list = matSafeChangeMapper.queryMatSafeChangeDetailPrintByTemlate(entityMap);
			reMap.put("detail", list);
			
			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
}

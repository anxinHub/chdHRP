package com.chd.hrp.ass.serviceImpl.check.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.ass.dao.check.mobile.AssCheckMobileMapper;
import com.chd.hrp.ass.dao.inspection.AssInspectionMainMapper;
import com.chd.hrp.ass.dao.repair.assmyinformrepair.AssMyInformRepairMapper;
import com.chd.hrp.ass.entity.check.other.AssCheckApDetailOther;
import com.chd.hrp.ass.service.check.mobile.AssCheckMobileService;
import com.chd.hrp.ass.service.repair.assmyinformrepair.AssMyInformRepairService;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.github.pagehelper.PageInfo;

@Service("assCheckMobileService")
public class AssCheckMobileServiceImpl implements AssCheckMobileService {


	private static Logger logger = Logger.getLogger(AssCheckMobileServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assCheckMobileMapper")
	private final AssCheckMobileMapper assCheckMobileMapper = null;
	
	@Resource(name = "assMyInformRepairService")
	private final AssMyInformRepairService assMyInformRepairService = null;
	
	@Resource(name = "assMyInformRepairMapper")
	private final AssMyInformRepairMapper assMyInformRepairMapper=null;
	

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assCheckMobileMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			assCheckMobileMapper.deleteBatch(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assCheckMobileMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assCheckMobileMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assCheckMobileMapper.generate(entityMap);

			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 方法 generate\"}";

		}
	}
	
	@Override
	public String repairsGenerate(Map<String, Object> entity) throws DataAccessException {
		try {
			String num = assMyInformRepairService.queryMaxNo(entity);
			Long rep_code =Long.parseLong(num);
			List<Map<String, Object>> entityList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) assCheckMobileMapper.queryRepairs(entity));
			
			List<Map<String, Object>> rep_batch = new ArrayList<Map<String, Object>>();
			String rep_code_no = null;
			int rep=1;
			for(Map<String, Object> map : entityList){
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String is_up=map.get("is_up").toString();
				if(is_up.equals("0")){
					entityMap.put("group_id", map.get("group_id"));
					entityMap.put("hos_id", map.get("hos_id"));
					entityMap.put("copy_code", map.get("copy_code"));
					
					entityMap.put("rep_user", map.get("rep_user_id"));
					entityMap.put("rep_dept", map.get("rep_dept"));
					entityMap.put("ass_card_no", map.get("ass_card_no"));
					entityMap.put("app_time", DateUtil.stringToDate(map.get("app_time").toString(), "yyyy-MM-dd  HH:mm:ss"));
					
					int falg=assCheckMobileMapper.existsAssRepairs(entityMap);
					if(falg==0){
						rep++;
						rep_code = rep_code+rep;
						rep_code_no=rep_code_no+String.valueOf(rep_code)+",";
						entityMap.put("rep_code", rep_code);
						entityMap.put("fau_code", map.get("fau_code"));
						entityMap.put("loc_code", map.get("loc_code"));
						entityMap.put("eme_status", map.get("eme_status"));
						entityMap.put("phone", map.get("phone"));
						entityMap.put("fau_note", map.get("fau_note"));
						entityMap.put("ass_name", map.get("ass_name"));
						entityMap.put("is_urg", map.get("is_urg"));
						entityMap.put("state", map.get("state"));
						entityMap.put("flag", map.get("flag"));
						entityMap.put("is_any", map.get("is_any"));
						
						rep_batch.add(entityMap);
					}
				}
				
			}
			
			if(rep_batch.size()>0){
				assCheckMobileMapper.repairsGenerate(rep_batch);
				//添加完数据后直接执行发送操作，派发给对应工程师
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("rep_code", rep_code_no.substring(4, rep_code_no.length()-1));
				map.put("group_id", rep_batch.get(0).get("group_id"));
				map.put("hos_id", rep_batch.get(0).get("hos_id"));
				map.put("copy_code", rep_batch.get(0).get("copy_code"));
				assMyInformRepairService.saveAssMyRepair(map);
				
			}
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 方法 generate\"}";

		}
	}
	
	@Override
	public String pollingGenerate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> entityList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) assCheckMobileMapper.queryPolling(entityMap));
			Long ins_id=null;
			List<Map<String, Object>> batchMain = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> batchDetail = new ArrayList<Map<String, Object>>();
			
			for(Map<String, Object> map : entityList){
				Map<String, Object> entMap = new HashMap<String, Object>();
				ins_id = assCheckMobileMapper.queryPollingSequence();
				String data=(map.get("create_date").toString()).substring(0, 10).replace("-", "");
				entMap.put("group_id", map.get("group_id"));
				entMap.put("hos_id", map.get("hos_id"));
				entMap.put("copy_code", map.get("copy_code"));
				entMap.put("ins_id", ins_id);
				entMap.put("ins_no", "XJ"+data);
				entMap.put("ins_name", "PDAXJ"+data);
				entMap.put("ass_year", map.get("ass_year"));
				entMap.put("ass_month", map.get("ass_month"));
				entMap.put("ass_nature", map.get("ass_nature"));
				entMap.put("dept_id", map.get("dept_id"));
				entMap.put("dept_no", map.get("dept_no"));
				entMap.put("create_emp", map.get("create_emp"));
				entMap.put("create_date", map.get("create_date"));
				entMap.put("state", "0");
				
				int falg=assCheckMobileMapper.queryPollingByCode(entMap);
				if(falg==0){
					List<Map<String, Object>> entityListDetail = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) assCheckMobileMapper.queryPollingDetail(entMap));
					for(Map<String, Object> mapD : entityListDetail){
						Map<String, Object> mapDetail=new HashMap<String, Object>();
						mapDetail.put("group_id", mapD.get("group_id"));
						mapDetail.put("hos_id", mapD.get("hos_id"));
						mapDetail.put("copy_code", mapD.get("copy_code"));
						mapDetail.put("ins_id", ins_id);
						mapDetail.put("ass_card_no", mapD.get("ass_card_no"));
						mapDetail.put("state", mapD.get("state"));
						
						batchDetail.add(mapDetail);
					}
					
					assCheckMobileMapper.addAssInspectionPollMain(entMap);
				}
				
			}
			
			if(batchDetail.size()>0){
				assCheckMobileMapper.addAssInspectionPollDetail(batchDetail);
			}
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 方法 generate\"}";

		}
	}
	

	@Override
	public String maintainGenerate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> entityList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) assCheckMobileMapper.queryMainTain(entityMap));
			Long rec_id=null;
			List<Map<String, Object>> batchDetail = new ArrayList<Map<String, Object>>();
			
			for(Map<String, Object> map : entityList){
				Map<String, Object> entMap = new HashMap<String, Object>();
				rec_id = assCheckMobileMapper.queryMainTainSequence();
				String data=(map.get("create_date").toString()).substring(0, 10).replace("-", "");
				entMap.put("group_id", map.get("group_id"));
				entMap.put("hos_id", map.get("hos_id"));
				entMap.put("copy_code", map.get("copy_code"));
				entMap.put("rec_id", rec_id);
				entMap.put("rec_no", "BY"+data);
				entMap.put("ass_year", map.get("ass_year"));
				entMap.put("ass_month", map.get("ass_month"));
				entMap.put("ass_nature", map.get("ass_nature"));
				entMap.put("fact_exec_emp", map.get("create_emp"));
				entMap.put("fact_exec_date", map.get("create_date"));
				entMap.put("maintain_hours", map.get("maintain_hours"));
				entMap.put("maintain_money", map.get("maintain_money"));
				entMap.put("maintain_dept_id", map.get("dept_id"));
				entMap.put("maintain_dept_no", map.get("dept_no"));
				entMap.put("create_emp", map.get("create_emp"));
				entMap.put("create_date", map.get("create_date"));
				entMap.put("state", "0");
				
				int falg=assCheckMobileMapper.queryMainTainByCode(entMap);
				if(falg==0){
					List<Map<String, Object>> entityListDetail = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) assCheckMobileMapper.queryMainTainDetail(entMap));
					for(Map<String, Object> mapD : entityListDetail){
						Map<String, Object> mapDetail=new HashMap<String, Object>();
						mapDetail.put("group_id", mapD.get("group_id"));
						mapDetail.put("hos_id", mapD.get("hos_id"));
						mapDetail.put("copy_code", mapD.get("copy_code"));
						mapDetail.put("rec_id", rec_id);
						mapDetail.put("ass_card_no", mapD.get("ass_card_no"));
						mapDetail.put("maintain_hours", mapD.get("maintain_hours"));
						mapDetail.put("maintain_money", mapD.get("maintain_money"));
						mapDetail.put("maintain_money", mapD.get("maintain_money"));
						mapDetail.put("maintain_unit", mapD.get("maintain_unit"));
						
						batchDetail.add(mapDetail);
					}
					
					assCheckMobileMapper.addAssInspectionMTMain(entMap);
				}
				
			}
			
			if(batchDetail.size()>0){
				assCheckMobileMapper.addAssInspectionMTDetail(batchDetail);
			}
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 方法 generate\"}";

		}
	}

	@Override
	public String measureGenerate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> entityList = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) assCheckMobileMapper.queryMeasure(entityMap));
			Long rec_id=null;
			List<Map<String, Object>> batchDetail = new ArrayList<Map<String, Object>>();
			
			for(Map<String, Object> map : entityList){
				Map<String, Object> entMap = new HashMap<String, Object>();
				rec_id = assCheckMobileMapper.queryMeasureSequence();
				String data=(map.get("create_date").toString()).substring(0, 10).replace("-", "");
				entMap.put("group_id", map.get("group_id"));
				entMap.put("hos_id", map.get("hos_id"));
				entMap.put("copy_code", map.get("copy_code"));
				entMap.put("rec_id", rec_id);
				entMap.put("seq_no", "JL"+data);
				entMap.put("ass_nature", map.get("ass_nature"));
				entMap.put("measure_money", map.get("measure_money"));
				entMap.put("other_money", map.get("other_money"));
				entMap.put("measure_hours", map.get("measure_hours"));
				entMap.put("measure_kind", map.get("measure_kind"));
				entMap.put("measure_type", map.get("measure_type"));
				entMap.put("measure_result", map.get("measure_result"));
				entMap.put("inner_measure_dept_id", map.get("dept_id"));
				entMap.put("inner_measure_dept_no", map.get("dept_no"));
				entMap.put("create_emp", map.get("create_emp"));
				entMap.put("create_date", map.get("create_date"));
				entMap.put("state", "0");
				
				int falg=assCheckMobileMapper.queryMeasureByCode(entMap);
				if(falg==0){
					List<Map<String, Object>> entityListDetail = JsonListMapUtil.toListMapLower((List<Map<String, Object>>) assCheckMobileMapper.queryMeasureDetail(entMap));
					for(Map<String, Object> mapD : entityListDetail){
						Map<String, Object> mapDetail=new HashMap<String, Object>();
						mapDetail.put("group_id", mapD.get("group_id"));
						mapDetail.put("hos_id", mapD.get("hos_id"));
						mapDetail.put("copy_code", mapD.get("copy_code"));
						mapDetail.put("rec_id", rec_id);
						mapDetail.put("ass_card_no", mapD.get("ass_card_no"));
						mapDetail.put("measure_result", map.get("measure_result"));
						
						batchDetail.add(mapDetail);
					}
					
					assCheckMobileMapper.addAssInspectionMeasureMain(entMap);
				}
				
			}
			
			if(batchDetail.size()>0){
				assCheckMobileMapper.addAssInspectionMeasureDetail(batchDetail);
			}
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 方法 generate\"}";

		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public String queryMoblieContrast(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assCheckMobileMapper.queryMoblieContrast(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assCheckMobileMapper.queryMoblieContrast(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@SuppressWarnings("unchecked")
    @Override
	public String queryCheckContrast(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (entityMap.get("check_flag") == null || entityMap.get("check_flag").equals("04")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrast(entityMap);
			} else if (entityMap.get("check_flag").equals("01")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrastDept(entityMap);
			} else if (entityMap.get("check_flag").equals("02")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrastStore(entityMap);
			} else if (entityMap.get("check_flag").equals("03")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrastHouseLand(entityMap);
			}

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (entityMap.get("check_flag") == null || entityMap.get("check_flag").equals("04")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrast(entityMap, rowBounds);
			} else if (entityMap.get("check_flag").equals("01")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrastDept(entityMap, rowBounds);
			} else if (entityMap.get("check_flag").equals("02")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrastStore(entityMap, rowBounds);
			} else if (entityMap.get("check_flag").equals("03")) {
				list = (List<Map<String, Object>>) assCheckMobileMapper.queryCheckContrastHouseLand(entityMap, rowBounds);
			}

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

}

package com.chd.hrp.hr.serviceImpl.attendancemanagement.attendresult;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultExamineMapper;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendExamineService;
import com.github.pagehelper.PageInfo;

@Service("hrAttendExamineService")
public class HrAttendExamineServiceImpl implements HrAttendExamineService{
	
	private static Logger logger = Logger.getLogger(HrAttendResultManageServiceImpl.class);
	
	@Resource(name = "hrAttendResultExamineMapper")
	private final HrAttendResultExamineMapper hrAttendResultExamineMapper = null;
	@Override
	public String queryAttendExamine(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String,Object>> list = hrAttendResultExamineMapper.queryAttendExamine(entityMap, rowBounds);
		
		@SuppressWarnings("rawtypes")
		PageInfo page = new PageInfo(list);
		return ChdJson.toJsonLower(list, page.getTotal());
	}

	@Override
	public String printAttendExamine(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return hrAttendResultExamineMapper.printAttendExamine(entityMap);
	}

	@Override
	public  Map<String, Object> submitAttendExamine(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择操作数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			List<Map<String, Object>> listV= new ArrayList<Map<String, Object>>();
			if(list.size()>0){
				for (Map<String, Object> map : list) {
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("year_month", entityMap.get("year_month"));
					addMap.put("dept_id_c", map.get("dept_id_c"));
					addMap.put("state", map.get("state"));

					addMap.put("exists_state", "1");
				int flag = hrAttendResultExamineMapper.existsByState(addMap);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已提交的职工，请重新选择！");
					return retMap;
				}
				addMap.put("state", 2);
				addMap.put("checker", SessionManager.getUserId());
				addMap.put("check_date", new Date());
				listV.add(addMap);
				
			
		
				}}
			if(listV.size()>0){
				for (Map<String, Object> map : listV) {
					//提交或去掉提交
					hrAttendResultExamineMapper.updateStateByCheck(map);	
				}
			}
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}

	

	@Override
	public Map<String, Object>  summaryAttendExamine(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择操作数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			List<Map<String, Object>> listV= new ArrayList<Map<String, Object>>();
			if(list.size()>0){
				for (Map<String, Object> map : list) {
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("year_month", entityMap.get("year_month"));
					addMap.put("dept_id_c", map.get("dept_id_c"));
					addMap.put("state", map.get("state"));

			//状态判断
			if(entityMap.get("state") != null && "2".equals(entityMap.get("state").toString())){

				addMap.put("exists_state", "2");
				int flag = hrAttendResultExamineMapper.existsByState(addMap);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已审核的职工，请重新选择！");
					return retMap;
				}
				addMap.put("checker", SessionManager.getUserId());
				addMap.put("check_date", new Date());
			}else if(entityMap.get("state") != null && "1".equals(entityMap.get("state").toString())){

				entityMap.put("exists_state", "1");
				int flag = hrAttendResultExamineMapper.existsByState(addMap);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已提交的职工，请重新选择！");
					return retMap;
				}
				addMap.put("state", 2);
				addMap.put("checker", SessionManager.getUserId());
				addMap.put("check_date", new Date());
			}else{

				entityMap.put("exists_state", "0");
				int flag = hrAttendResultExamineMapper.existsByState(addMap);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已提交的职工，请重新选择！");
					return retMap;
				}
				addMap.put("state", 1);
				addMap.put("checker", SessionManager.getUserId());
				addMap.put("check_date", new Date());
			
				listV.add(addMap);
			}
		
				}}
			if(listV.size()>0){
				for (Map<String, Object> map : listV) {
					//提交或去掉提交
					hrAttendResultExamineMapper.updateStateBySubmit(entityMap);
				}
			}
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}


	@Override
	public Map<String, Object> checkOrUnCheckResultExamine(
			Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择操作数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			List<Map<String, Object>> listV= new ArrayList<Map<String, Object>>();
			if(list.size()>0){
				for (Map<String, Object> map : list) {
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("year_month", entityMap.get("year_month"));
					addMap.put("dept_id_c", map.get("dept_id_c"));
					addMap.put("state", map.get("state"));

					addMap.put("exists_state", "0");
						int flag = hrAttendResultExamineMapper.existsByState(addMap);
						if(flag > 0){
							retMap.put("state", "false");
							retMap.put("error", "存在考勤数据状态不是未提交的职工，请重新选择！");
							return retMap;
						}
						addMap.put("state", 1);
						addMap.put("oper", SessionManager.getUserId());
						addMap.put("oper_date", new Date());
					
						
						listV.add(addMap);
					
				
				}
				
			}
			if(listV.size()>0){
				for (Map<String, Object> map : listV) {
					//提交
					hrAttendResultExamineMapper.updateStateBySubmit(map);

				}
			}
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}

	@Override
	public Map<String, Object> confirmAttendExamine(
			Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择操作数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			List<Map<String, Object>> listV= new ArrayList<Map<String, Object>>();
			if(list.size()>0){
				for (Map<String, Object> map : list) {
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("year_month", entityMap.get("year_month"));
					addMap.put("dept_id_c", map.get("dept_id_c"));
					addMap.put("state", map.get("state"));
					addMap.put("exists_state", "0");
				int flag = hrAttendResultExamineMapper.existsByState(addMap);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已提交的职工，请重新选择！");
					return retMap;
				}
				addMap.put("state", 2);
				addMap.put("oper", SessionManager.getUserId());
				addMap.put("oper_date", new Date());
			
				listV.add(addMap);
			
		
				}
				}
			if(listV.size()>0){
				for (Map<String, Object> map : listV) {
					//提交或去掉提交
					hrAttendResultExamineMapper.updateStateBySubmit(map);

				}
			}
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}

	@Override
	public Map<String, Object> unCheckAttendExamine(
			Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择操作数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			List<Map<String, Object>> listV= new ArrayList<Map<String, Object>>();

			if(list.size()>0){
				for (Map<String, Object> map : list) {
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("year_month", entityMap.get("year_month"));
					addMap.put("dept_id_c", map.get("dept_id_c"));
					addMap.put("state", map.get("state"));

					addMap.put("exists_state", "1");
				int flag = hrAttendResultExamineMapper.existsByState(addMap);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已提交的职工，请重新选择！");
					return retMap;
				}
				addMap.put("state", 0);
				addMap.put("oper", null);
				addMap.put("oper_date", null);
		        listV.add(addMap);
			
			
				}
				}
			if(listV.size()>0){
				for (Map<String, Object> map : listV) {
					//提交或去掉提交
					hrAttendResultExamineMapper.updateStateBySubmit(map);

				}
			}
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}

	@Override
	public Map<String, Object> unSubmitAttendExamine(
			Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {

			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择操作数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			List<Map<String, Object>> listV= new ArrayList<Map<String, Object>>();

			if(list.size()>0){
				for (Map<String, Object> map : list) {
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("year_month", entityMap.get("year_month"));
					addMap.put("dept_id_c", map.get("dept_id_c"));
					addMap.put("state", map.get("state"));
					addMap.put("exists_state", "2");
				int flag = hrAttendResultExamineMapper.existsByState(addMap);
				if(flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "存在考勤数据状态不是已上报的职工，请重新选择！");
					return retMap;
				}
				addMap.put("state", 1);
				addMap.put("checker", null);
				addMap.put("check_date", null);
			
				listV.add(addMap);
		
				}
				}
			if(listV.size()>0){
				for (Map<String, Object> map : listV) {
					//提交或去掉提交
					hrAttendResultExamineMapper.updateStateByCheck(map);

				}
			}
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}

	@Override
	public Map<String, Object> deleteBatchAttendResultExamineManage(
			Map<String, Object> entityMap) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {
			if(entityMap.get("ids") == null || "".equals(entityMap.get("ids").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择删除的数据！");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			String ids = entityMap.get("ids").toString();
			List<Map<String, Object>> list = JSON.parseObject(ids, new TypeReference<List<Map<String,Object>>>(){});
			if(list.size()>0){
				for (Map<String, Object> map : list) {
					entityMap.put("dept_id_c", map.get("dept_id_c"));
			//状态判断
			entityMap.put("check_state", "1");
			int flag = hrAttendResultExamineMapper.existsByState(entityMap);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在考勤数据已提交的职工，请重新选择！");
				return retMap;
			}
			//删除数据
			hrAttendResultExamineMapper.deleteAttendExamineResult(entityMap);
				}
				}
			retMap.put("msg", "删除成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("删除失败");
		}
		
		return retMap;
	}

}

package com.chd.hrp.hr.serviceImpl.attendancemanagement.leave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrApplyingLeavesMapper;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrAttdentVacalBalanceMapper;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrCheckLeaveMapper;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrTerminateleaveMapper;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrCheckLeaveService;
import com.github.pagehelper.PageInfo;


/**
 * 职工审核 核定
 * 
 * @author Administrator
 *
 */
@Service("hrCheckLeaveService")
public class HrCheckLeaveServiceImpl implements HrCheckLeaveService {
	private static Logger logger = Logger.getLogger(HrCheckLeaveServiceImpl.class);

	@Resource(name = "hrCheckLeaveMapper")
	private final HrCheckLeaveMapper hrCheckLeaveMapper = null;
	
	@Resource(name="hrApplyingLeavesMapper")
	private final HrApplyingLeavesMapper hrApplyingLeavesMapper=null;
	
	@Resource(name = "hrTerminateleaveMapper")
	private final HrTerminateleaveMapper hrTerminateleaveMapper = null;
	
	@Resource(name = "hrAttdentVacalBalanceMapper")
	private final HrAttdentVacalBalanceMapper hrAttdentVacalBalanceMapper = null;
	

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 查询
	 */
	@Override
	public String queryCheckLeave(Map<String, Object> entityMap) throws DataAccessException {
	
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if (sysPage.getTotal() == -1) {
			if("1".equals(entityMap.get("xj_type"))){
				//销假
				list= ChdJson.toListLower((List<Map<String, Object>>) hrTerminateleaveMapper.query2(entityMap));
			}else{
				//休假
				list= ChdJson.toListLower((List<Map<String, Object>>) hrApplyingLeavesMapper.query(entityMap));
			}
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			if("1".equals(entityMap.get("xj_type"))){
				//销假
				list= ChdJson.toListLower((List<Map<String, Object>>) hrTerminateleaveMapper.query2(entityMap, rowBounds));
			}else{
				//休假
				list= ChdJson.toListLower((List<Map<String, Object>>) hrApplyingLeavesMapper.query(entityMap, rowBounds));
			}
			
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	
	}
	
	/**
	 * 审核
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String auditHrApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("checker", SessionManager.getUserId());
			entityMap.put("attend_xjstate", 2);
			entityMap.put("check_date", new Date());
			entityMap.put("back_reason", " ");
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));

			if ("0".equals(entityMap.get("xj_type"))) {
				hrApplyingLeavesMapper.updateApplyState(entityMap);
			} else {
				entityMap.put("attend_xxjapply_codes", entityMap.get("attend_xjapply_codes"));
				hrTerminateleaveMapper.updateXJState(entityMap);
			}
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 销审
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String unAuditHrApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("checker", "");
			entityMap.put("attend_xjstate", 1);
			entityMap.put("check_date", "");
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));

			if ("0".equals(entityMap.get("xj_type"))) {
				hrApplyingLeavesMapper.updateApplyState(entityMap);
			} else {
				entityMap.put("attend_xxjapply_codes", entityMap.get("attend_xjapply_codes"));
				hrTerminateleaveMapper.updateXJState(entityMap);
			}
			
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 退回
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String backHrApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			entityMap.put("attend_xjstate", 0);
			entityMap.put("attend_xjloginer", "");
			entityMap.put("attend_xjreg_operdate", "");
			entityMap.put("back_reason", "审核未通过！");
			
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));

			if ("0".equals(entityMap.get("xj_type"))) {
				hrApplyingLeavesMapper.updateApplyState(entityMap);
			} else {
				entityMap.put("attend_xxjapply_codes", entityMap.get("attend_xjapply_codes"));
				hrTerminateleaveMapper.updateXJState(entityMap);
			}
			return "{\"msg\":\"退回成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 核定
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String checkHrApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			entityMap.put("attend_xjstate", 3);
			entityMap.put("confirmer", SessionManager.getUserId());
			entityMap.put("confirm_date", new Date());
			
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));
			
			if ("0".equals(entityMap.get("xj_type"))) {
				
				//休假管理表插入数据
				hrApplyingLeavesMapper.insertApplyHrEmpHoliday(entityMap);
				
				//修改结余表
				entityMap.put("attend_year", MyConfig.getCurAccYear());
				 
				//查询休假单休假天数，会写帐表数据（局部更新）
				List<Map<String,Object>> xjList = ChdJson.toListLower(hrApplyingLeavesMapper.queryXjApplication(entityMap));
				for(Map<String,Object> map:xjList){
					entityMap.put("bala_amt", Float.parseFloat(map.get("attend_xjdays").toString())*(-1));
					entityMap.put("attend_code", map.get("attend_code"));
					entityMap.put("emp_id", map.get("emp_id"));
					entityMap.put("dec_amt", map.get("attend_xjdays").toString());
					hrAttdentVacalBalanceMapper.update(entityMap);
				}
				
				//更改状态
				hrApplyingLeavesMapper.updateApplyState(entityMap);
			} else {
				entityMap.put("attend_xxjapply_codes", entityMap.get("attend_xjapply_codes"));
				List<Map<String,Object>> xxList= ChdJson.toListLower(hrTerminateleaveMapper.queryXJHrEmpHoliday(entityMap));
				if(xxList.size() > 0){
					//修改额度
					hrTerminateleaveMapper.updateBatchXJHrEmpHoliday(xxList);
				}
				
				//修改结余表
				entityMap.put("attend_year", MyConfig.getCurAccYear()); 
				List<Map<String,Object>> xjList = ChdJson.toListLower(hrTerminateleaveMapper.queryXXjInfo(entityMap));
				for(Map<String,Object> map:xjList){
					entityMap.put("bala_amt", map.get("dec_amt").toString());
					entityMap.put("attend_code", map.get("attend_code"));
					entityMap.put("emp_id", map.get("emp_id"));
					entityMap.put("dec_amt", Float.parseFloat(map.get("dec_amt").toString())*(-1));
					hrAttdentVacalBalanceMapper.update(entityMap);
				}
				//更改状态
				hrTerminateleaveMapper.updateXJState(entityMap);
			}
			return "{\"msg\":\"核定成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 取消核定
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String uncheckHrHrApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			entityMap.put("attend_xjstate", 2);
			entityMap.put("confirmer", "");
			entityMap.put("confirm_date", "");
			
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));
			entityMap.put("attend_year", SessionManager.getAcctYear());
			
			String[] codeArr = codes.replace("'", "").split(",");
			List<String> codeList = new ArrayList<String>();
			for(String s : codeArr){
				codeList.add(s);
			}
			List<Map<String, Object>> resultMapList = hrTerminateleaveMapper
				.queryXxjapplyByXjapplyCode(entityMap, codeList);
			if(resultMapList.size() > 0){
				StringBuilder sb = new StringBuilder();
				sb.append("申请编号");
				for(Map<String, Object> resultMap : resultMapList){
					sb.append("<br>").append(resultMap.get("attend_xjapply_code").toString());
				}
				sb.append("<br>").append("已经添加了销假申请，不能取消核定，请重新选择");
				return "{\"msg\":\"" + sb.toString() + ".\",\"state\":\"true\"}";
			}
			
			if ("0".equals(entityMap.get("xj_type"))) {
				//更改状态
				hrApplyingLeavesMapper.updateApplyState(entityMap);
				//休假管理表插入数据
				hrApplyingLeavesMapper.deleteApplyHrEmpHoliday(entityMap);
				//修改帐表，冲回数据
				//查询休假单休假天数，会写帐表数据（局部更新）
				List<Map<String,Object>> xjList = ChdJson.toListLower(hrApplyingLeavesMapper.queryXjApplication(entityMap));
				//Map<String,Object> balanceMap=new HashMap<String,Object>();
				for(Map<String,Object> map:xjList){
					entityMap.put("bala_amt", map.get("attend_xjdays").toString());
					entityMap.put("attend_code", map.get("attend_code"));
					entityMap.put("emp_id", map.get("emp_id"));
					entityMap.put("dec_amt", Float.parseFloat(map.get("attend_xjdays").toString())*(-1));
					entityMap.put("attend_year", map.get("attend_year"));
					
					hrAttdentVacalBalanceMapper.update(entityMap);
				}
			} else {
				entityMap.put("attend_xxjapply_codes", entityMap.get("attend_xjapply_codes"));
				//更改状态
				hrTerminateleaveMapper.updateXJState(entityMap);
				entityMap.put("attend_xxjapply_code", "");
				entityMap.put("attend_xxj_backtime", "");
				entityMap.put("attend_xxjdays", "");
				entityMap.put("attend_left_ed", "");
				//休假管理表插入数据
				hrTerminateleaveMapper.updateXJHrEmpHoliday(entityMap);
				
				List<Map<String,Object>> xjList = ChdJson.toListLower(hrTerminateleaveMapper.queryXXjInfo(entityMap));
				for(Map<String,Object> map:xjList){
					entityMap.put("dec_amt", map.get("dec_amt").toString());
					entityMap.put("attend_code", map.get("attend_code"));
					entityMap.put("emp_id", map.get("emp_id"));
					entityMap.put("bala_amt", Float.parseFloat(map.get("dec_amt").toString())*(-1));
					entityMap.put("attend_year", SessionManager.getAcctYear());
					hrAttdentVacalBalanceMapper.update(entityMap);
				}
			}
			return "{\"msg\":\"取消核定成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 作废
	 */
	@Override
	public String cancelFXJApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_xjstate", 4); 
			entityMap.put("cancel_date", new Date());
			
			//写结存表
			entityMap.put("attend_year", MyConfig.getCurAccYear());
			
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));
			//删除休假管理表
			hrApplyingLeavesMapper.deleteApplyHrEmpHoliday(entityMap);
			
			
			//存在销假数据
			List<Map<String,Object>> xxjList = ChdJson.toListLower(hrApplyingLeavesMapper.queryCancleXXjInfo(entityMap));
			if(xxjList.size() > 0){
				hrAttdentVacalBalanceMapper.updateBatch(xxjList,entityMap);
			}else{
				//不存在销假数据 则只查休假表
				List<Map<String,Object>> xjList = ChdJson.toListLower(hrApplyingLeavesMapper.queryCancleXjInfo(entityMap));
				if(xjList.size() > 0){
					hrAttdentVacalBalanceMapper.updateBatch(xjList,entityMap);
				}
			}
			
			//更改申请单状态
			hrApplyingLeavesMapper.updateApplyState(entityMap);
			
			return "{\"msg\":\"作废成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
}

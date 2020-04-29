package com.chd.hrp.hr.serviceImpl.transfer;

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

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.transfer.HrDeptTransferMapper;
import com.chd.hrp.hr.entity.nursing.HrNursingPromotion;
import com.chd.hrp.hr.entity.transfer.HrDeptTransfer;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.transfer.HrDeptTransferService;
import com.github.pagehelper.PageInfo;
import com.chd.base.util.DateUtil;

/**
 * 部门调动
 * 
 * @author Administrator
 *
 */
@Service("hrDeptTransferService")
public class HrDeptTransferServiceImpl implements HrDeptTransferService {
	private static Logger logger = Logger.getLogger(HrDeptTransferServiceImpl.class);

	@Resource(name = "hrDeptTransferMapper")
	private final HrDeptTransferMapper hrDeptTransferMapper = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 调岗增加
	 */
	@Override
	public String addHrDeptTransfer(Map<String, Object> entityMap) throws DataAccessException {

	
		try {
		
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_DEPT_ADJUST");
			mapVo.put("prm_perfixe", "HDA");
			String adjust_code = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("adjust_code", adjust_code);
			entityMap.put("maker", SessionManager.getUserId());
			entityMap.put("make_date",DateUtil.getSysDate());
			entityMap.put("adjust_state", 0);
			@SuppressWarnings("unused")
			
			int state = hrDeptTransferMapper.add(entityMap);
			
			hrBaseService.updateAndQueryHrBillNo(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 调岗修改
	 */
	@Override
	public String updateHrDeptTransfer(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> valueMap = new HashMap<String, Object>();
			entityMap.put("Table_code", "hos_emp");
			valueMap.put("dept_no", entityMap.get("aft_dept_no"));
			valueMap.put("dept_id", entityMap.get("aft_dept_id"));
			list.add(valueMap);
			String str=hrBaseService.updateTable(entityMap, list);
			int state = hrDeptTransferMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 调岗删除
	 */
	@Override
	public String deleteHrDeptTransfer(Map<String, Object> entityMap) throws DataAccessException {

		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			String codes = entityMap.get("adjust_code").toString();
			entityMap.put("adjust_code", codes.substring(0, codes.length() - 1));
			hrDeptTransferMapper.deleteDeptTransfer(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 调岗查询
	 */
	@Override
	public String queryHrDeptTransfer(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrDeptTransfer> list = (List<HrDeptTransfer>) hrDeptTransferMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrDeptTransfer> list = (List<HrDeptTransfer>) hrDeptTransferMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrDeptTransfer queryByCodeDeptTransfer(Map<String, Object> entityMap) throws DataAccessException {

		return hrDeptTransferMapper.queryByCode(entityMap);
	}

	/**
	 * 审核部门调动
	 */
	@Override
	public String auditDeptTransfer(List<HrDeptTransfer> list) throws DataAccessException {
		try{
			Map<String, Object> entityMap = new HashMap<String, Object>();
		List<Map<String, Object>> listq = new ArrayList<Map<String,Object>>();
	
		entityMap.put("checker", SessionManager.getUserId());
		entityMap.put("adjust_state", 1);
		entityMap.put("check_date", new Date());
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		
		entityMap.put("Table_code", "hos_emp");
		for (HrDeptTransfer hrDeptTransfer : list) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("dept_no", hrDeptTransfer.getAft_dept_no());
			valueMap.put("dept_id", hrDeptTransfer.getAft_dept_id());
			valueMap.put("bdept_id", hrDeptTransfer.getBef_dept_id());
			valueMap.put("emp_code", hrDeptTransfer.getEmp_code());
			valueMap.put("emp_id", hrDeptTransfer.getEmp_id());
			valueMap.put("year_month", DateUtil.dateToString(hrDeptTransfer.getAdjust_date()).substring(0, 7).replaceAll("-", ""));
			listq.add(valueMap);
		}
	
		hrDeptTransferMapper.updateEmpTable(entityMap, listq);
		hrDeptTransferMapper.updateEmpDictTable(entityMap, listq);
		 hrDeptTransferMapper.auditDeptTransfer(list,entityMap);
		 //调动考勤科室
		 hrDeptTransferMapper.updateAttend(entityMap, listq);
		 hrDeptTransferMapper.updateAttendD(entityMap, listq);
		 hrDeptTransferMapper.updateAttendManage(entityMap, listq);
	
		 
		return "{\"msg\":\"审核成功.\",\"state\":\"true\"}"; 
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 销审部门调动
	 */
	@Override
	public String reAuditdepttransfer(List<HrDeptTransfer> list) throws DataAccessException {
		try{
			Map<String, Object> entityMap = new HashMap<String, Object>();
		List<Map<String, Object>> listq = new ArrayList<Map<String,Object>>();
		entityMap.put("checker", "");
		entityMap.put("adjust_state", 0);
		entityMap.put("check_date","");
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		
		entityMap.put("Table_code", "hos_emp");
		for (HrDeptTransfer hrDeptTransfer : list) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("dept_no", hrDeptTransfer.getBef_dept_no());
			valueMap.put("dept_id", hrDeptTransfer.getBef_dept_id());
			valueMap.put("bdept_id", hrDeptTransfer.getAft_dept_id());
			valueMap.put("emp_code", hrDeptTransfer.getEmp_code());
			valueMap.put("emp_id", hrDeptTransfer.getEmp_id());
			valueMap.put("year_month", DateUtil.dateToString(hrDeptTransfer.getAdjust_date()).substring(0, 7).replaceAll("-", ""));

			listq.add(valueMap);
		}
		hrDeptTransferMapper.updateEmpTable(entityMap, listq);
		hrDeptTransferMapper.updateEmpDictTable(entityMap, listq);
		 hrDeptTransferMapper.auditDeptTransfer(list,entityMap);
		 //调动考勤科室
		 hrDeptTransferMapper.updateAttend(entityMap, listq);
		 hrDeptTransferMapper.updateAttendD(entityMap, listq);
		 hrDeptTransferMapper.updateAttendManage(entityMap, listq);
		return "{\"msg\":\"审核成功.\",\"state\":\"true\"}"; 
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String queryHosEmpDept(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> list = hrDeptTransferMapper.queryHosEmpDept(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public List<Map<String, Object>> queryDeptTransByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
			 List<Map<String,Object>> list = ChdJson.toListLower(hrDeptTransferMapper.queryDeptTransByPrint(entityMap));
			 return list;
	}

}

package com.chd.hrp.hr.serviceImpl.attendancemanagement.accrest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.attendancemanagement.accrest.HrAccRestInitMapper;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrAttdentVacalBalanceMapper;
import com.chd.hrp.hr.dao.attendancemanagement.overtime.HrOvertimeMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.entity.attendancemanagement.accrest.HrAccRestInit;
import com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestInitService;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.Emp;
import com.chd.hrp.sys.entity.ModStart;
import com.github.pagehelper.PageInfo;

@Service("hrAccRestInitService")
public class HrAccRestInitServiceImpl  implements HrAccRestInitService{
	private static Logger logger = Logger.getLogger(HrAccRestInitServiceImpl.class);
	@Resource(name = "hrAccRestInitMapper")
	private final HrAccRestInitMapper hrAccRestInitMapper = null;
	@Resource(name = "hrOvertimeMapper")
	private final HrOvertimeMapper hrOvertimeMapper = null;
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	@Resource(name = "hrAttdentVacalBalanceMapper")
	private final HrAttdentVacalBalanceMapper hrAttdentVacalBalanceMapper = null;
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;

	
	/**
	 * 保存
	 */
	@Override
	public String addAccRestInit(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HrAccRestInit> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("gridData")),
					HrAccRestInit.class);
			// 判断是否结账，如果已结账则不让修改
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_year", MyConfig.getCurAccYear());
			int is_flag = hrAccRestInitMapper.existsTermendByYear(entityMap);
			if (is_flag > 0) {
				return "{\"error\":\"已结账，无法设置.\",\"state\":\"false\"}";
			}
			// 获取系统启用年
			entityMap.put("mod_code", "06");// 06:人力资源管理系统
			ModStart modStart = modStartMapper.existsModStartByCode(entityMap);
			if (modStart != null){
				entityMap.put("attend_year", modStart.getStart_year());
			} else {
				return "{\"error\":\"模块未启用，无法设置.\",\"state\":\"false\"}";
			}

			List<Map<String, Object>> attendList = ChdJson.toListLower(hrOvertimeMapper.getAttendCodeOfJx(entityMap));
			if (attendList.size() > 0) {
				entityMap.put("attend_code", attendList.get(0).get("attend_code").toString());
			} else {
				return "{\"error\":\"未设置积休考勤项，初始化失败.\",\"state\":\"false\"}";
			}
			float bac=0f;
			List<Map<String, Object>> baList = null;
			for (HrAccRestInit hrAccRestInit : alllistVo) {
				if (hrAccRestInit.getEmp_id() == null || hrAccRestInit.getEmp_id().toString() == " ") {
					return "{\"error\":\"请选择人员.\",\"state\":\"false\"}";
				}
				hrAccRestInit.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrAccRestInit.setHos_id(Integer.parseInt(SessionManager.getHosId()));
//				hrAccRestInit.setDept_id(Integer.parseInt(entityMap.get("dept_id").toString()));
				entityMap.put("limit", hrAccRestInit.getAttend_accdays() == null ? 0 : hrAccRestInit.getAttend_accdays());
				entityMap.put("emp_id", hrAccRestInit.getEmp_id());

				baList = hrAttdentVacalBalanceMapper.queryHrAttdentVacalBal(entityMap);
				if (baList != null && baList.size() > 0) {
					bac=Float.parseFloat(entityMap.get("limit").toString())-Float.parseFloat(baList.get(0).get("LIMIT").toString());
					entityMap.put("bala_amt", bac);				
					hrAttdentVacalBalanceMapper.update(entityMap);
				} else {
					entityMap.put("bala_amt", entityMap.get("limit").toString());	
					hrAttdentVacalBalanceMapper.add(entityMap);
				}
			}

			hrAccRestInitMapper.deleteAccRest(alllistVo);

			// 增加积休初始表
			hrAccRestInitMapper.addBatch(alllistVo);
			// 写入帐表初始化数据

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryAccRestInit(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		//获取系统启用年份
		if (sysPage.getTotal() == -1) {

			List<HrAccRestInit> list = (List<HrAccRestInit>) hrAccRestInitMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAccRestInit> list = (List<HrAccRestInit>) hrAccRestInitMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String setAccJxMax(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			//1.获取是否有加班数据
			//2.没有加班数据，判断是新增还是修改
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id",SessionManager.getHosId());
			entityMap.put("attend_year",SessionManager.getAcctYear());
			entityMap.put("check_state", 2);
			int count=hrOvertimeMapper.notExistsByState(entityMap);
			if(count>0){
				return "{\"error\":\"已存在加班数据，不能修改.\",\"state\":\"false\"}";
			}else{
				
				entityMap.put("attend_acc_isnot", 1);
				hrAccRestInitMapper.delete(entityMap);
				hrAccRestInitMapper.add(entityMap);
			}
			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String queryAttendAcctop(Map<String, Object> map) throws DataAccessException{
		try {
			Map<String, Object> resultMap = hrAccRestInitMapper.queryAttendCodeByPK(map);
			return resultMap == null ? null : resultMap.get("attend_acctop").toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importRestInit(Map<String, Object> entityMap) {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("emp_code", map.get("emp_code").get(1));
					Emp empMap = hrAccRestInitMapper.queryByCode(saveMap);
					//过滤空行
					if("".equals(map.get("emp_code").get(1)) || "".equals(map.get("emp_name").get(1))){
						continue;
					}
					if("null".equals(map.get("emp_code").get(1)) || "null".equals(map.get("emp_name").get(1))){
						continue;
					}
					
					saveMap.put("emp_id", empMap.getEmp_id());
					saveMap.put("emp_name", map.get("emp_name").get(1));
					saveMap.put("attend_accdays", map.get("attend_accdays").get(1));
					saveMap.put("attend_accnote", map.get("attend_accnote").get(1));
					
					int count=hrAccRestInitMapper.queryInitByCode(saveMap);
					if(count > 0){
						failureMsg.append("<br/>职工名称: "+map.get("emp_name").get(1)+" 已存在或不存在该职工; ");
						failureNum++;
						continue; 
					}
					
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrAccRestInitMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

}

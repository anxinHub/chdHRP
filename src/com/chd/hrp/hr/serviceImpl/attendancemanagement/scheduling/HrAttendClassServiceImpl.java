package com.chd.hrp.hr.serviceImpl.attendancemanagement.scheduling;

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
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrAttendClassMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClass;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendClassService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.serviceImpl.attendancemanagement.leave.HrApplyingLeavesServiceImpl;
import com.github.pagehelper.PageInfo;


@Service("hrAttendClassService")
public class HrAttendClassServiceImpl  implements HrAttendClassService{
	private static Logger logger = Logger.getLogger(HrAttendClassServiceImpl.class);
	
@Resource(name="hrAttendClassMapper")
private final	HrAttendClassMapper hrAttendClassMapper =null;
@Resource(name = "hrBaseService")
private final HrBaseService hrBaseService = null;
	
	@Override
	public String addAttendClass(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			String attend_areacode="";
		
			List<HrAttendClass> list =hrAttendClassMapper.queryByCodeClass(entityMap);
			if (list!=null) {
			for (HrAttendClass hrAttendClass : list) {

				if (hrAttendClass.getAttend_classcode().equals(entityMap.get("attend_classcode"))) {
					return "{\"error\":\"班次编码：" +hrAttendClass.getAttend_classcode().toString() + "已存在.\"}";
				}
				if (hrAttendClass.getAttend_classname().equals(entityMap.get("attend_classname"))) {
					return "{\"error\":\"班次名称：" +hrAttendClass.getAttend_classname() + "已存在.\"}";
				}
				if (hrAttendClass.getAttend_classsname().equals(entityMap.get("attend_classsname"))) {
					return "{\"error\":\"班次简称：" +hrAttendClass.getAttend_classsname().toString() + "已存在.\"}";
				}
			}
			
			}
				hrAttendClassMapper.add(entityMap);
			return "{\"state\":\"true\",\"attend_classcode\":\"" + attend_areacode+ "\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}

	@Override
	public String deleteAttendClass(List<HrAttendClass> listVo)
			throws DataAccessException {
		try {
			
			
			hrAttendClassMapper.deleteAttendClass(listVo);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
			
		}

	@Override
	public String queryAttendClass(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAttendClass> list = (List<HrAttendClass>) hrAttendClassMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAttendClass> list = (List<HrAttendClass>) hrAttendClassMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String queryAttendAreacode(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrAttendClassMapper.queryAttendAreacode(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public HrAttendClass queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrAttendClassMapper.queryByCode(entityMap);
	}

	

	@Override
	public String updateAttendClass(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			List<HrAttendClass> list =hrAttendClassMapper.queryByCodeClassUpdate(entityMap);
			if (list!=null) {
			for (HrAttendClass hrAttendClass : list) {

				if (hrAttendClass.getAttend_classname().equals(entityMap.get("attend_classname"))) {
					return "{\"error\":\"班次名称：" +hrAttendClass.getAttend_classname() + "已存在.\"}";
				}
				if (hrAttendClass.getAttend_classsname().equals(entityMap.get("attend_classsname"))) {
					return "{\"error\":\"班次简称：" +hrAttendClass.getAttend_classsname().toString() + "已存在.\"}";
				}
			}
			}
			hrAttendClassMapper.update(entityMap);
			return "{\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}

	@Override
	public int queryAttendScheduling(HrAttendClass hrAttendClass) {
     int state= hrAttendClassMapper.queryAttendScheduling(hrAttendClass);
		return state;
	
	}

	@Override
	public List<Map<String, Object>> queryAttendClassByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = ChdJson.toListLower(hrAttendClassMapper.queryAttendClassByPrint(entityMap));
		 return list;
	}

}

package com.chd.hrp.hr.serviceImpl.attendancemanagement.scheduling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrAttendAreaMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendAreaService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.serviceImpl.attendancemanagement.leave.HrApplyingLeavesServiceImpl;
import com.github.pagehelper.PageInfo;


@Service("hrAttendAreaService")
public class HrAttendAreaServiceImpl  implements HrAttendAreaService{
	private static Logger logger = Logger.getLogger(HrAttendAreaServiceImpl.class);
	
@Resource(name="hrAttendAreaMapper")
private final	HrAttendAreaMapper hrAttendAreaMapper =null;

@Resource(name = "hrBaseService")
private final HrBaseService hrBaseService = null;
	
	@Override
	public String addAttendArea(Map<String, Object> entityMap)
			throws DataAccessException {
		
			String attend_areacode="";
			List<HrAttendArea> listVo=JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrAttendArea.class);
			List<HrAttendArea> list =hrAttendAreaMapper.queryByCodeArea(entityMap);
			if (list!=null || list.size() > 0) {
				for (HrAttendArea hrAttendArea2 : list) {
					if (hrAttendArea2.getAttend_areacode().equals(entityMap.get("attend_areacode"))) {
						return "{\"error\":\"编码：" +hrAttendArea2.getAttend_areacode().toString() + "已存在.\"}";
					}
					if (hrAttendArea2.getAttend_area_name().equals(entityMap.get("attend_area_name"))) {
						return "{\"error\":\"名称：" +entityMap.get("attend_area_name").toString() + "已存在.\"}";
					}
				}
			}
			try {
			int state=hrAttendAreaMapper.add(entityMap);
				hrAttendAreaMapper.addBatchDateil(listVo,entityMap);

				  List<String>  exisPb=hrAttendAreaMapper.queryAttendArea(listVo,entityMap);
				 if(exisPb!=null && exisPb.size()>0){ throw new
				 SysException("该科室已存在被："+exisPb.toString()+"选中"); }
				 
			return "{\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}

	@Override
	public String deleteAttendArea(List<HrAttendArea> listVo)
			throws DataAccessException {
	try {
		//删除明细
		hrAttendAreaMapper.deleteAttendArea(listVo);
		//删除主表
		 hrAttendAreaMapper.deleteBatchArea(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
		throw new SysException(e.getMessage());
	}
		
	}

	@Override
	public String queryAttendArea(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAttendArea> list = (List<HrAttendArea>) hrAttendAreaMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAttendArea> list = (List<HrAttendArea>) hrAttendAreaMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String queryAreaDept(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrAttendAreaMapper.queryAreaDept(entityMap);
		return JSON.toJSONString(list);
	}

	@Override
	public HrAttendArea queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrAttendAreaMapper.queryByCode(entityMap);
	}

	@Override
	public String updateAttendArea(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			List<HrAttendArea> listVo=JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrAttendArea.class);
			List<HrAttendArea> list =hrAttendAreaMapper.queryByCodeAreaByName(entityMap);
			if (list!=null || list.size() > 0) {
				for (HrAttendArea hrAttendArea2 : list) {
				
					if (hrAttendArea2.getAttend_area_name().equals(entityMap.get("attend_area_name"))) {
						return "{\"error\":\"名称：" +entityMap.get("attend_area_name").toString() + "已存在.\"}";
					}
				}
			}
			
			
			hrAttendAreaMapper.update(entityMap);
			
			
			hrAttendAreaMapper.deleteBatchAttendArea(listVo,entityMap);
			hrAttendAreaMapper.addBatchDateil(listVo,entityMap);
			 List<String>  exisPb=hrAttendAreaMapper.queryAttendArea(listVo,entityMap);
			 if(exisPb!=null && exisPb.size()>0){ throw new
			 SysException("该科室已存在被："+exisPb.toString()+"选中"); }
			return "{\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		
	}
	@Override
	public int queryAreacode(HrAttendArea hrAttendArea)
			throws DataAccessException {
	int state=	hrAttendAreaMapper.queryAreacode(hrAttendArea);
		return state;
	}

	@Override
	public String queryAreaDeptCheck(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrAttendAreaMapper.queryAreaDeptCheck(entityMap);
		return JSON.toJSONString(list);
	}

	@Override
	public List<Map<String, Object>> queryAttendAreaByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrAttendAreaMapper.queryAttendAreaByPrint(entityMap));
		 return list;
	}
}

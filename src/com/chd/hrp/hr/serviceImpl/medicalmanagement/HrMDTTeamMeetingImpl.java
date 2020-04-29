/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.medicalmanagement.HrMDTMeetDetailMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrMDTTeamMeetingMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrMDTMeetDetail;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetMdt;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrMDTTeamMeetingService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * MDT团队会议记录
 * @Table:
 * HR_MEET_MDT MDT团队会议
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */
@Service("hrMDTTeamMeetingService")
public class HrMDTTeamMeetingImpl implements HrMDTTeamMeetingService{
	private static Logger logger = Logger.getLogger(HrMDTTeamMeetingImpl.class);
	//引入DAO操作
	@Resource(name = "hrMDTTeamMeetingMapper")
	private final HrMDTTeamMeetingMapper hrMDTTeamMeetingMapper = null;
	@Resource(name = "hrMDTMeetDetailMapper")
	private final HrMDTMeetDetailMapper hrMDTMeetDetailMapper = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	@Override
	public String addhrMDTTeamMeeting(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			int state = 0;
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_MEET_MDT");
			mapVo.put("prm_perfixe", "MDT");
			String apply_no = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("rc_no", apply_no);
			List<HrMDTMeetDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("para")),
					HrMDTMeetDetail.class);
			HrMeetMdt hrMeetMdt	= hrMDTTeamMeetingMapper.queryByCode(entityMap);
			if(hrMeetMdt == null){
				state = hrMDTTeamMeetingMapper.addHrMeetMdt(entityMap);
				if(alllistVo != null && alllistVo.size() > 0) {
					for (HrMDTMeetDetail hrMDTMeetDetail : alllistVo) {
						hrMDTMeetDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
						hrMDTMeetDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
						hrMDTMeetDetail.setRc_date(DateUtil.stringToDate(entityMap.get("rc_date").toString(), "yyyy-MM-dd"));
						hrMDTMeetDetail.setRc_no(entityMap.get("rc_no").toString());
						hrMDTMeetDetail.setTeam_name(entityMap.get("team_name").toString());
						hrMDTMeetDetail.setTitle(entityMap.get("title").toString());
					}
					hrMDTMeetDetailMapper.addBatch(alllistVo);
				}
				if (state > 0) {
					hrBaseService.updateAndQueryHrBillNo(mapVo);
				}
				return "{\"state\":\"true\"}";
			}else{
				return "{\"error\":\"请勿重复保存！\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryHrMDTTeamMeeting(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			@SuppressWarnings("unchecked")
			List<HrMeetMdt> list = (List<HrMeetMdt>) hrMDTTeamMeetingMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			@SuppressWarnings("unchecked")
			List<HrMeetMdt> list = (List<HrMeetMdt>) hrMDTTeamMeetingMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings({ "rawtypes", "unchecked" })
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public HrMeetMdt queryByCodeHrMDTTeamMeeting(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrMDTTeamMeetingMapper.queryByCode(entityMap);
	}

	@Override
	public String queryMeetDetail(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			@SuppressWarnings("unchecked")
			List<HrMDTMeetDetail> list = (List<HrMDTMeetDetail>) hrMDTMeetDetailMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			@SuppressWarnings("unchecked")
			List<HrMDTMeetDetail> list = (List<HrMDTMeetDetail>) hrMDTMeetDetailMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings({ "rawtypes", "unchecked" })
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String updateHrMDTTeamMeeting(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			List<HrMDTMeetDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("para")),
					HrMDTMeetDetail.class);
				//1.更新MDT团队会议信息
				@SuppressWarnings("unused")
				int state = hrMDTTeamMeetingMapper.update(entityMap);
				//2.根据条件删除MDT团队会议明细信息
				hrMDTMeetDetailMapper.delete(entityMap);
				//3.更新MDT团队会议明细信息
				if(alllistVo != null && alllistVo.size() > 0) {
					for (HrMDTMeetDetail hrMDTMeetDetail : alllistVo) {
						hrMDTMeetDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
						hrMDTMeetDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
						hrMDTMeetDetail.setRc_date(DateUtil.stringToDate(entityMap.get("rc_date").toString(), "yyyy-MM-dd"));
						hrMDTMeetDetail.setRc_no(entityMap.get("rc_no").toString());
						hrMDTMeetDetail.setTeam_name(entityMap.get("team_name").toString());
						hrMDTMeetDetail.setTitle(entityMap.get("title").toString());
					}
					hrMDTMeetDetailMapper.addBatch(alllistVo);
				}
				return "{\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String deleteHrMDTTeamMeeting(List<HrMeetMdt> entityList) 
		throws DataAccessException {
		try {
		if(entityList!=null){
			Map<String,Object> mapVo=new HashMap<String,Object>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			hrMDTMeetDetailMapper.deleteHrMDTMeetDetail(entityList,mapVo);
			hrMDTTeamMeetingMapper.deleteHrMDTTeamMeeting(entityList,mapVo);
		}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";


		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public HrMeetMdt queryByCode(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		return hrMDTTeamMeetingMapper.queryByCode(hrMeetMdt);
	}

	@Override
	public String confirmHrMDTTeamMeeting(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		try {
	        
			hrMDTTeamMeetingMapper.changeStateHrMDTTeamMeeting(hrMeetMdt);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String reConfirmHrMDTTeamMeeting(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		try {
	        
			hrMDTTeamMeetingMapper.changeStateHrMDTTeamMeeting(hrMeetMdt);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String auditHrMDTTeamMeeting(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		try {
	        
			hrMDTTeamMeetingMapper.changeStateHrMDTTeamMeeting(hrMeetMdt);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String unauditHrMDTTeamMeeting(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		try {
	        
			hrMDTTeamMeetingMapper.changeStateHrMDTTeamMeeting(hrMeetMdt);
			
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String dispassHrMDTTeamMeeting(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		try {
	        
			hrMDTTeamMeetingMapper.changeStateHrMDTTeamMeeting(hrMeetMdt);
			
			return "{\"msg\":\"未通过成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public HrMeetMdt queryByCodeAdd(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrMDTTeamMeetingMapper.queryByCodeAdd(entityMap);
	}

	@Override
	public String confirmHrMDTTeamMeetingAdd(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		try {
	        
			hrMDTTeamMeetingMapper.changeStateHrMDTTeamMeeting(hrMeetMdt);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String reConfirmHrMDTTeamMeetingAdd(HrMeetMdt hrMeetMdt)
				throws DataAccessException {
		try {
	        
			hrMDTTeamMeetingMapper.changeStateHrMDTTeamMeeting(hrMeetMdt);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public HrMeetMdt queryByCodeDate(HrMeetMdt hrMeetMdt)
			throws DataAccessException {
		return hrMDTTeamMeetingMapper.queryByCodeDate(hrMeetMdt);
	}

	@Override
	public List<Map<String, Object>> queryMDTTeamMeetingByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String, Object>> list=ChdJson.toListLower(hrMDTTeamMeetingMapper.queryMDTTeamMeetingByPrint(entityMap));
		return list;
	}

	@Override
	public Map<String, Object> queryEmp(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrMDTTeamMeetingMapper.queryEmp(entityMap);
	}
	
}

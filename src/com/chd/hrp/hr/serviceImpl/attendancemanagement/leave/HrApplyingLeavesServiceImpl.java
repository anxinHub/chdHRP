package com.chd.hrp.hr.serviceImpl.attendancemanagement.leave;

import java.text.SimpleDateFormat;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.accrest.HrAccRestInitMapper;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrAttendItemMapper;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrOfficialHolidayMapper;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrApplyingLeavesMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrApplyingLeaves;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrApplyingLeavesService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 职工休假申请
 * 
 * @author Administrator
 *
 */
@Service("hrApplyingLeavesService")
public class HrApplyingLeavesServiceImpl implements HrApplyingLeavesService {
	private static Logger logger = Logger.getLogger(HrApplyingLeavesServiceImpl.class);

	@Resource(name = "hrApplyingLeavesMapper")
	private final HrApplyingLeavesMapper hrApplyingLeavesMapper = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	@Resource(name = "hrOfficialHolidayMapper")
	private final HrOfficialHolidayMapper hrOfficialHolidayMapper = null;
	@Resource(name = "hrAttendItemMapper")
	private final HrAttendItemMapper hrAttendItemMapper = null;
	@Resource(name = "hrAccRestInitMapper")
	private final HrAccRestInitMapper hrAccRestInitMapper = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 职工休假申请增加
	 */
	@Override
	public String addApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_ATTEND_XJAPPLY");
			mapVo.put("prm_perfixe", "XX");
			String attend_xjapply_code = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("attend_xjapply_code", attend_xjapply_code);
			entityMap.put("attend_xjstate", 0);
			entityMap.put("dept_source", MyConfig.getSysPara("06103"));
			HrApplyingLeaves	hrApplyingLeaves=hrApplyingLeavesMapper.queryByCodeLeaves(entityMap);
			if(hrApplyingLeaves!=null){
				return "{\"error\":\"" +hrApplyingLeaves.getEmp_name()+"在  "+ a.format(hrApplyingLeaves.getAttend_xjbegdate())+" 至 " +a.format(hrApplyingLeaves.getAttend_xjenddate())+ " 时间区间内已存在休假.\"}";
			}
			int state = hrApplyingLeavesMapper.add(entityMap);
			hrBaseService.updateAndQueryHrBillNo(mapVo);
			return "{\"state\":\"true\",\"attend_xjapply_code\":\"" + attend_xjapply_code+ "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 职工休假申请修改
	 */
	@Override
	public String updateApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException {
		try {
			@SuppressWarnings("unused")
			int state = hrApplyingLeavesMapper.update(entityMap);
			return "{\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 职工休假申请查询
	 */
	@Override
	public String queryApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String,Object>> list = ChdJson.toListLower((List<Map<String,Object>>) hrApplyingLeavesMapper.query(entityMap));
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson.toListLower((List<Map<String,Object>>) hrApplyingLeavesMapper.query(entityMap, rowBounds));
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//打印
	@Override
	public List<Map<String, Object>> queryApplyingLeavesByPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("dept_source", MyConfig.getSysPara("06103"));
		return hrApplyingLeavesMapper.queryApplyingLeavesByPrint(entityMap);
	}
	
	/**
	 * 跳转修改页面
	 */
	@Override
	public HrApplyingLeaves queryByCodeApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException {
		return hrApplyingLeavesMapper.queryByCode(entityMap);
	}

	/**
	 * 删除
	 */
	@Override
	public String deleteApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));
			
			hrApplyingLeavesMapper.deleteApplyingLeaves(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 提交申请
	 */
	@Override
	public String confirmApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_xjstate", 1);
			entityMap.put("attend_xjloginer", SessionManager.getUserId());
			entityMap.put("attend_xjreg_operdate", new Date());
			
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));
			
			hrApplyingLeavesMapper.updateApplyState(entityMap);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 取消提交
	 */
	@Override
	public String reConfirmApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_xjstate", 0);
			entityMap.put("attend_xjloginer", "");
			entityMap.put("attend_xjreg_operdate", "");
			
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));
			
			hrApplyingLeavesMapper.updateApplyState(entityMap);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		throw new SysException(e.getMessage());
		}
		
	}
	
	/**
	 * 作废
	 */
	@Override
	public String cancelFApplyingLeaves(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_xjstate", 4);
			entityMap.put("cancel_date", new Date());
			
			String codes = entityMap.get("attend_xjapply_codes").toString();
			entityMap.put("attend_xjapply_codes", codes.substring(0, codes.length() - 1));
			
			hrApplyingLeavesMapper.updateApplyState(entityMap);
			return "{\"msg\":\"作废成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
    /**
     * 查询休假额度
     */
	@Override
	public String queryAttendSum(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> attendSum=new HashMap<String, Object>();
		/**
		 * 判断是否积休
		 */
		HrAttendItem	hrAttendItem = hrAttendItemMapper.queryByCode(entityMap);
		if(hrAttendItem.getIs_jx()==1){
			int init=hrAccRestInitMapper.queryinit(entityMap);
			int addOverTime=hrAccRestInitMapper.queryOverTime(entityMap);
		attendSum=hrApplyingLeavesMapper.queryAttendSumByJX(entityMap);
		attendSum.put("attend_ed", init+addOverTime);
		attendSum.put("residue_days", (init+addOverTime)-Integer.parseInt(attendSum.get("xjdays").toString()));
		
		}else{
			attendSum=hrApplyingLeavesMapper.queryAttendSum(entityMap);
		}
		return JSONArray.toJSONString(attendSum);
	}
   
	/**
	 * 查询休假历史记录
	 */
	@Override
	public String queryHistroy(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrApplyingLeaves> list = (List<HrApplyingLeaves>) hrApplyingLeavesMapper.queryHistroy(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrApplyingLeaves> list = (List<HrApplyingLeaves>) hrApplyingLeavesMapper.queryHistroy(entityMap, rowBounds);
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String countXJDays(Map<String, Object> map) throws DataAccessException {
		try{
			double all = 1.0;
			double half = 0.5;
			long hour24 = 24 * 60 * 60 * 1000L;
			String beginDate = map.get("beginDate").toString();// 开始休假时间（年月日）
			String endDate = map.get("endDate").toString();// 结束休假时间（年月日）
			String beginTime = map.get("beginTime").toString();// 开始休假时间（上下午）（0：上，1：下）
			String endTime = map.get("endTime").toString();// 结束休假时间（上下午）
			List<Map<String, Object>> resultMapList = hrOfficialHolidayMapper.queryOfficialHolidayByDateDrea(map);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 先不算两头天数
			long daysL = ((sdf.parse(endDate).getTime() - sdf.parse(beginDate).getTime()) / hour24) - 1;
			int size = resultMapList.size();
			double days = Double.parseDouble("" + daysL);
			
			// 时间区域内没有非工作日，或时间区域两端不是非工作日则加上   上下午 合成的天数
			if(resultMapList.size() > 0 && beginDate.equals(resultMapList.get(0).get("attend_date").toString())){
				size--;
			}else{
				if(beginTime.equals("0")){
					days += all;
				}else{
					days += half;
				}
			}
			if(resultMapList.size() > 0 && endDate.equals(resultMapList.get(resultMapList.size() - 1).get("attend_date").toString())){
				size--;
			}else{
				if(endTime.equals("0")){
					days += half;
				}else{
					days += all;
				}
			}
			
			// 减掉非工作日时间
			if(size > 0 && size > days){
				days = 0;
			}else if(size > 0){
				days -= size;
			}
			/**
			 * 判断是否积休
			 */
			HrAttendItem	hrAttendItem = hrAttendItemMapper.queryByCode(map);
			if(hrAttendItem.getIs_jx()==1){
				int init=hrAccRestInitMapper.queryinit(map);
				int addOverTime=hrAccRestInitMapper.queryOverTime(map);
				if(days <= init+addOverTime){
					days =days;
				}else{
					return "{\"error\":\"休假申请天数超过积休初始化最大额度.\",\"state\":\"false\"}";
				}
			}
			return "{\"days\":\"" + Double.toString(days) + "\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}

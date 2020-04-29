package com.chd.hrp.hr.serviceImpl.attendancemanagement.overtime;

import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.attendancemanagement.overtime.HrOvertimeMapper;
import com.chd.hrp.hr.service.attendancemanagement.overtime.HrOvertimeService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 期初加班登记
 * 
 * @author Administrator
 *
 */
@Service("hrOvertimeService")
public class HrOvertimeServiceImpl implements HrOvertimeService {
	private static Logger logger = Logger.getLogger(HrOvertimeServiceImpl.class);

	@Resource(name = "hrOvertimeMapper")
	private final HrOvertimeMapper hrOvertimeMapper = null;
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	/**
	 * 期初加班登记增加
	 */
	@Override
	public Map<String, Object> addOvertime(Map<String, Object> entityMap) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			//转换日期
			if(entityMap.get("overtime_date") != null && !"".equals(entityMap.get("overtime_date").toString())){
				entityMap.put("attend_year", entityMap.get("overtime_date").toString().substring(0, 4));
				entityMap.put("overtime_date", DateUtil.stringToDate(entityMap.get("overtime_date").toString(), "yyyy-MM-dd"));
			}else{
				retMap.put("state", "false");
				retMap.put("error", "加班日期不能为空！");
				return retMap;
			}
			//获取单号
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("bill_code", "HR_ATTEND_OVERTIME");
			mapVo.put("prm_perfixe", "JB");
			String overtime_code = hrBaseService.QueryHrBillNo(mapVo);
			
			entityMap.put("overtime_code", overtime_code);
			entityMap.put("state", 0);
			entityMap.put("create_date", new Date());
			
			//添加数据
			hrOvertimeMapper.add(entityMap);
			//更新流水号
			hrBaseService.updateAndQueryHrBillNo(mapVo);
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}

	/**
	 * 期初加班登记修改
	 */
	@Override
	public Map<String, Object>  updateOvertime(Map<String, Object> entityMap) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			//校验状态
			entityMap.put("check_state", "0");
			entityMap.put("overtime_codes", "\'"+entityMap.get("overtime_code").toString()+"\'");
			int flag = hrOvertimeMapper.notExistsByState(entityMap);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在不是新建状态的数据，请重新选择");
			}

			//转换日期
			if(entityMap.get("overtime_date") != null && !"".equals(entityMap.get("overtime_date").toString())){
				entityMap.put("attend_year", entityMap.get("overtime_date").toString().substring(0, 4));
				entityMap.put("overtime_date", DateUtil.stringToDate(entityMap.get("overtime_date").toString(), "yyyy-MM-dd"));
			}else{
				retMap.put("state", "false");
				retMap.put("error", "加班日期不能为空！");
				return retMap;
			}
			
			//更新数据
			hrOvertimeMapper.update(entityMap);

			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	/**
	 * 提交
	 */
	@Override
	public Map<String, Object>  confirmOvertime(Map<String,Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			//校验状态
			entityMap.put("check_state", "0");
			int flag = hrOvertimeMapper.notExistsByState(entityMap);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在不是新建状态的数据，请重新选择");
			}
			
			entityMap.put("state", 1);
			entityMap.put("oper", SessionManager.getUserId());
			entityMap.put("oper_date", new Date());
			
			//提交
			hrOvertimeMapper.updateStateByConfirm(entityMap);
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\""+ e.getMessage() +"\"}");
		}
		
		return retMap;
	}
	
	/**
	 * 撤回
	 */
	@Override
	public Map<String, Object>  reConfirmOvertime(Map<String,Object> entityMap) throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			//校验状态
			entityMap.put("check_state", "1");
			int flag = hrOvertimeMapper.notExistsByState(entityMap);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在不是已提交状态的数据，请重新选择");
			}
			entityMap.put("state", 0);
			entityMap.put("oper", null);
			entityMap.put("oper_date", null);
			
			hrOvertimeMapper.updateStateByConfirm(entityMap);
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\""+ e.getMessage() +"\"}");
		}
		
		return retMap;
	}
	
	/**
	 * 删除
	 */
	@Override
	public Map<String, Object>  deleteOvertime(Map<String,Object> entityMap)throws DataAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			//校验状态
			entityMap.put("check_state", "0");
			int flag = hrOvertimeMapper.notExistsByState(entityMap);
			if(flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "存在不是新建状态的数据，请重新选择");
			}
			
			//删除
			hrOvertimeMapper.deleteHrOvertime(entityMap);
			
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}

	/**
	 * 查询
	 */
	@Override
	public String queryOvertime(Map<String, Object> entityMap)throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());
		
		//转换日期
		if(entityMap.get("create_beg_date") != null && !"".equals(entityMap.get("create_beg_date").toString())){
			entityMap.put("create_beg_date", DateUtil.stringToDate(entityMap.get("create_beg_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("create_end_date") != null && !"".equals(entityMap.get("create_end_date").toString())){
			entityMap.put("create_end_date", DateUtil.stringToDate(entityMap.get("create_end_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("overtime_beg_date") != null && !"".equals(entityMap.get("overtime_beg_date").toString())){
			entityMap.put("overtime_beg_date", DateUtil.stringToDate(entityMap.get("overtime_beg_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("overtime_end_date") != null && !"".equals(entityMap.get("overtime_end_date").toString())){
			entityMap.put("overtime_end_date", DateUtil.stringToDate(entityMap.get("overtime_end_date").toString(), "yyyy-MM-dd"));
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String,Object>> list = hrOvertimeMapper.queryOvertime(entityMap, rowBounds);
		@SuppressWarnings("rawtypes")
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	//打印
	@Override
	public List<Map<String, Object>> queryOvertimeByPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("dept_source", MyConfig.getSysPara("06103"));
		return hrOvertimeMapper.queryOvertimeByPrint(entityMap);
	}
	
	/**
	 * 跳转修改页面
	 */
	@Override
	public Map<String, Object> queryByCodeOvertime(Map<String, Object> entityMap)throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("dept_source", MyConfig.getSysPara("06103"));
		return hrOvertimeMapper.queryByCode(entityMap);
	}
}

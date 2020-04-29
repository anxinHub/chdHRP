package com.chd.hrp.hr.serviceImpl.attendancemanagement.leave;

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
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrTerminateleaveMapper;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrTerminateleave;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrTerminateleaveService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 职工休假申请
 * 
 * @author Administrator
 *
 */
@Service("hrTerminateleaveService")
public class HrTerminateleaveServiceImpl implements HrTerminateleaveService {
	private static Logger logger = Logger.getLogger(HrTerminateleaveServiceImpl.class);

	@Resource(name = "hrTerminateleaveMapper")
	private final HrTerminateleaveMapper hrTerminateleaveMapper = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 职工休假申请增加
	 */
	@Override
	public String addTerminateleave(Map<String, Object> entityMap) throws DataAccessException {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_ATTEND_XXJAPPLY");
			mapVo.put("prm_perfixe", "XJ");
			mapVo.put("dept_source", MyConfig.getSysPara("06103"));
			String attend_xjapply_code = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("attend_xxjapply_code", attend_xjapply_code);
			entityMap.put("attend_xjstate", 0);
			entityMap.put("dept_source", MyConfig.getSysPara("06103"));
			HrTerminateleave hrTerminateleave =hrTerminateleaveMapper.queryByCode(entityMap);
			if (hrTerminateleave!=null) {
				if (hrTerminateleave.getAttend_xjapply_code().equals(entityMap.get("attend_xjapply_code"))) {
					return "{\"error\":\"休假申请：" +hrTerminateleave.getAttend_xjapply_code().toString() + "已销假.\"}";
				}
				
			}
			int state = hrTerminateleaveMapper.add(entityMap);
			hrBaseService.updateAndQueryHrBillNo(mapVo);
			return "{\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 职工休假申请修改
	 */
	@Override
	public String updateTerminateleave(Map<String, Object> entityMap) throws DataAccessException {
		try {
			@SuppressWarnings("unused")
			int state = hrTerminateleaveMapper.update(entityMap);
			return "{\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 删除
	 */
	@Override
	public String deleteTerminateleave(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			String codes = entityMap.get("attend_xxjapply_codes").toString();
			entityMap.put("attend_xxjapply_codes", codes.substring(0, codes.length() - 1));
			
			hrTerminateleaveMapper.deleteTerminateleave(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 职工休假申请查询
	 */
	@Override
	public String queryTerminateleave(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("dept_source", MyConfig.getSysPara("06103"));
		if (sysPage.getTotal() == -1) {
			List<Map<String,Object>> list = ChdJson.toListLower((List<Map<String,Object>>)hrTerminateleaveMapper.query(entityMap));
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson.toListLower((List<Map<String,Object>>) hrTerminateleaveMapper.query(entityMap, rowBounds));
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 *打印 
	 */
	@Override
	public List<Map<String, Object>> queryTerminateleaveByPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("dept_source", MyConfig.getSysPara("06103"));
		return hrTerminateleaveMapper.queryTerminateleaveByPrint(entityMap);
	}
	
	/**
	 * 跳转修改页面
	 */
	@Override
	public Map<String, Object> queryByCodeTerminateleave(Map<String, Object> entityMap) throws DataAccessException {
		return hrTerminateleaveMapper.queryByCodeTerminateleave(entityMap);
	}

	/**
	 * 提交
	 */
	@Override
	public String confirmTerminateleave(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_xjstate", 1);
			entityMap.put("attend_xjloginer", SessionManager.getUserId());
			entityMap.put("attend_xjreg_operdate", new Date());
			
			String codes = entityMap.get("attend_xxjapply_codes").toString();
			entityMap.put("attend_xxjapply_codes", codes.substring(0, codes.length() - 1));
			
			hrTerminateleaveMapper.updateXJState(entityMap);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 取消提交职工销假申请
	 */
	@Override
	public String reConfirmTerminateleave(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_xjstate", 0);
			entityMap.put("attend_xjloginer", "");
			entityMap.put("attend_xjreg_operdate", "");
			
			String codes = entityMap.get("attend_xxjapply_codes").toString();
			entityMap.put("attend_xxjapply_codes", codes.substring(0, codes.length() - 1));
			
			hrTerminateleaveMapper.updateXJState(entityMap);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryApplyCode(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrTerminateleaveMapper.queryApplyCode(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryApplying(Map<String, Object> entityMap) throws DataAccessException {
		return JSONArray.toJSONString(hrTerminateleaveMapper.queryApplying(entityMap));
	}

}

package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.medicalmanagement.HrFurstdApplicationMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechExecMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechRefMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdApplication;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechRef;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrFurstdApplicationService;
import com.github.pagehelper.PageInfo;

/**
 * 进修申请
 * 
 * @author Administrator
 *
 */
@Service("hrFurstdApplicationService")
public class HrFurstdApplicationServiceImpl implements HrFurstdApplicationService {
	private static Logger logger = Logger.getLogger(HrFurstdApplicationServiceImpl.class);

	@Resource(name = "hrFurstdApplicationMapper")
	private final HrFurstdApplicationMapper hrFurstdApplicationMapper = null;

	@Resource(name = "hrEmpTechExecMapper")
	private final HrEmpTechExecMapper hrEmpTechExecMapper = null;
	
	//引入DAO操作
	@Resource(name = "hrEmpTechRefMapper")
	private final HrEmpTechRefMapper hrEmpTechRefMapper = null;
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
    
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 进修申请增加
	 */
	@Override
	public String addFurstdApplication(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_FURSTD_APP");
			mapVo.put("prm_perfixe", "FU");
			String apply_no = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("app_no", apply_no);
			HrFurstdApplication hrFurstdApplication	=hrFurstdApplicationMapper.queryByCode(entityMap);
			if(hrFurstdApplication==null){
				int state = hrFurstdApplicationMapper.add(entityMap);
                 if (state!=0) {
                	 hrBaseService.updateAndQueryHrBillNo(mapVo);
				}
				return "{\"state\":\"true\"}";
			}else{
				return "{\"error\":\"" +hrFurstdApplication.getEmp_name()+"在  "+ a.format(hrFurstdApplication.getApp_date())+  " 时间内已存在申请.\"}";
			}
			

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 进修申请修改
	 */
	@Override
	public String updateFurstdApplication(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrFurstdApplicationMapper.update(entityMap);

			return "{\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 进修申请删除
	 */
	@Override
	public String deleteFurstdApplication(List<HrFurstdApplication> entityList)
			throws DataAccessException {

		try {
			if (entityList!=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrFurstdApplicationMapper.deleteFurstdApplication(entityList,mapVo);
			}
		
		

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 进修申请查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryFurstdApplication(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrFurstdApplication> list = (List<HrFurstdApplication>) hrFurstdApplicationMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrFurstdApplication> list = (List<HrFurstdApplication>) hrFurstdApplicationMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrFurstdApplication queryByCodeFurstdApplication(
			Map<String, Object> entityMap) throws DataAccessException {
		return hrFurstdApplicationMapper.queryByCode(entityMap);
	}

	
	@Override
	public String confirmHrFurstdApplication(HrFurstdApplication hrNursingPromotion)
			throws DataAccessException {
		try {
	         
			hrFurstdApplicationMapper.confirmHrFurstdApplication(hrNursingPromotion);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String reConfirmHrHrFurstdApplication(HrFurstdApplication hrNursingPromotion)
			throws DataAccessException {
		try {
	         
			hrFurstdApplicationMapper.confirmHrFurstdApplication(hrNursingPromotion);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

@Override
public String auditHrFurstdApplication(HrFurstdApplication hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrFurstdApplicationMapper.confirmHrFurstdApplication(hrNursingPromotion);
		
		return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String unauditHrHrFurstdApplication(HrFurstdApplication hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrFurstdApplicationMapper.confirmHrFurstdApplication(hrNursingPromotion);
		
		return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String dispassHrHrFurstdApplication(HrFurstdApplication hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrFurstdApplicationMapper.confirmHrFurstdApplication(hrNursingPromotion);
		
		return "{\"msg\":\"未通过成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String queryHosEmp(Map<String, Object> entityMap) throws DataAccessException {
	Map<String, Object> list = hrFurstdApplicationMapper.queryHosEmp(entityMap);
	return JSONArray.toJSONString(list);
}

@Override
public List<Map<String, Object>> queryFurstdApplicationByPrint(
		Map<String, Object> entityMap) throws DataAccessException {
	 List<Map<String,Object>> list = ChdJson.toListLower(hrFurstdApplicationMapper.queryFurstdApplicationByPrint(entityMap));
	 return list;
}}

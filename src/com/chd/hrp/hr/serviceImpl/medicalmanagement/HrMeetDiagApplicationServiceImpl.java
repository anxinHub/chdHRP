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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.medicalmanagement.HrMeetDiagApplicationDetailMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrMeetDiagApplicationMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechExecMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechRefMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagAppDetail;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagApplication;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechRef;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrMeetDiagApplicationService;
import com.github.pagehelper.PageInfo;

/**
 * 全院大会诊申请
 * 
 * @author Administrator
 *
 */
@Service("hrMeetDiagApplicationService")
public class HrMeetDiagApplicationServiceImpl implements HrMeetDiagApplicationService {
	private static Logger logger = Logger
			.getLogger(HrMeetDiagApplicationServiceImpl.class);

	@Resource(name = "hrMeetDiagApplicationMapper")
	private final HrMeetDiagApplicationMapper hrMeetDiagApplicationMapper = null;

	@Resource(name = "hrMeetDiagApplicationDetailMapper")
	private final HrMeetDiagApplicationDetailMapper hrMeetDiagApplicationDetailMapper = null; 
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 全院大会诊申请增加
	 */
	@Override
	public String addMeetDiagApplication(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			int state =0;
			Map<String, Object> mapVo =new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_MEET_DIAG_APP");
			mapVo.put("prm_perfixe", "APP");
			String bill_no = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("bill_no", bill_no);
			HrMeetDiagApplication hrMeetDiagApplication	=hrMeetDiagApplicationMapper.queryByCode(entityMap);
		//	@SuppressWarnings("unused")
			List<HrMeetDiagAppDetail> listVo=JSONArray.parseArray(String.valueOf(entityMap.get("Param").toString()), HrMeetDiagAppDetail.class);
			if(hrMeetDiagApplication==null){
				state = hrMeetDiagApplicationMapper.add(entityMap);
				SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
				if (listVo!=null && listVo.size()>0) {
					for (HrMeetDiagAppDetail hrMeetDiagAppDetail : listVo) {
						hrMeetDiagAppDetail.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
						hrMeetDiagAppDetail.setHos_id(Integer.parseInt(SessionManager.getHosId()));;
						hrMeetDiagAppDetail.setBill_no(entityMap.get("bill_no").toString());
						hrMeetDiagAppDetail.setApp_date(DateUtil.stringToDate(entityMap.get("app_date").toString(), "yyyy-MM-dd"));
						hrMeetDiagAppDetail.setDept_id(Integer.parseInt(entityMap.get("dept_id").toString()));
						hrMeetDiagAppDetail.setCase_no(entityMap.get("case_no").toString());
					}
					state=hrMeetDiagApplicationDetailMapper.addBatch(listVo);
			
		
					
				}
				hrBaseService.updateAndQueryHrBillNo(mapVo);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"bill_no\":\"" + bill_no+ "\"}";
				
			}else{
				return "{\"error\":\"请勿重复保存！\",\"state\":\"true\"}";
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 全院大会诊申请修改
	 */
	@Override
	public String updateMeetDiagApplication(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			List<HrMeetDiagAppDetail> listVo=JSONArray.parseArray(String.valueOf(entityMap.get("Param")), HrMeetDiagAppDetail.class);
			if(listVo!=null && listVo.size()>0){
				for (HrMeetDiagAppDetail hrMeetDiagAppDetail : listVo) {
					hrMeetDiagAppDetail.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrMeetDiagAppDetail.setHos_id(Integer.parseInt(SessionManager.getHosId()));;
					hrMeetDiagAppDetail.setBill_no(entityMap.get("bill_no").toString());
					hrMeetDiagAppDetail.setApp_date(DateUtil.stringToDate(entityMap.get("app_date").toString(), "yyyy-MM-dd"));
					hrMeetDiagAppDetail.setDept_id(Integer.parseInt(entityMap.get("dept_id").toString()));
					hrMeetDiagAppDetail.setCase_no(entityMap.get("case_no").toString());
				}
				hrMeetDiagApplicationDetailMapper.updateBatch(listVo);
			}
		
		
			int state = hrMeetDiagApplicationMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 全院大会诊申请删除
	 */
	@Override
	public String deleteMeetDiagApplication(List<HrMeetDiagApplication> entityList)
			throws DataAccessException {

		try {
			List<Map<String, Object>> entityLis= new ArrayList<Map<String,Object>>();
		if(entityList!=null){
			Map<String,Object> mapVo=new HashMap<String,Object>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			
	         hrMeetDiagApplicationDetailMapper.deleteDetailBatch(entityList,mapVo);
			hrMeetDiagApplicationMapper.deleteMeetDiagApplication(entityList,mapVo);
		}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 全院大会诊申请查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryMeetDiagApplication(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrMeetDiagApplication> list = (List<HrMeetDiagApplication>) hrMeetDiagApplicationMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrMeetDiagApplication> list = (List<HrMeetDiagApplication>) hrMeetDiagApplicationMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrMeetDiagApplication queryByCodeMeetDiagApplication(
			Map<String, Object> entityMap) throws DataAccessException {
		return hrMeetDiagApplicationMapper.queryByCode(entityMap);
	}

/*	@Override
	public String queryMeetDiagApplicationTree(Map<String, Object> entityMap)
			throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrMeetDiagApplication> storeTypeList = (List<HrMeetDiagApplication>) hrMeetDiagApplicationMapper
				.query(entityMap);
		for (HrMeetDiagApplication storeType : storeTypeList) {
			
			 * treeJson.append( "{'id':'" + storeType.getKind_code() +
			 * "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
			 
		}
		treeJson.append("]");
		return treeJson.toString();
	}
*/
	@Override
	public String confirmMeetDiag(HrMeetDiagApplication hrNursingPromotion)
			throws DataAccessException {
		try {
	         
			hrMeetDiagApplicationMapper.confirmHrMeetDiag(hrNursingPromotion);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String reConfirmMeetDiag(HrMeetDiagApplication hrNursingPromotion)
			throws DataAccessException {
		try {
	         
			hrMeetDiagApplicationMapper.reConfirmHrHrMeetDiag(hrNursingPromotion);
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
   /**
    * 查询是否存在
    */
	@Override
	public HrMeetDiagApplication queryByCode(HrMeetDiagApplication hrNursingPromotion)
			throws DataAccessException {
		return hrMeetDiagApplicationMapper.queryByCode(hrNursingPromotion);
	}

@Override
public HrMeetDiagApplication queryByCodeAdd(Map<String, Object> entityMap)
		throws DataAccessException {
	return hrMeetDiagApplicationMapper.queryByCodeAdd(entityMap);
	}

@Override
public String confirmMeetDiagAdd(HrMeetDiagApplication MeetDiagApplication)
		throws DataAccessException {
	try {
        
		hrMeetDiagApplicationMapper.confirmHrMeetDiag(MeetDiagApplication);
		
		return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String reConfirmMeetDiagAdd(HrMeetDiagApplication MeetDiagApplication)
		throws DataAccessException {
	try {
        
		hrMeetDiagApplicationMapper.reConfirmHrHrMeetDiag(MeetDiagApplication);
		
		return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String auditMeetDiag(HrMeetDiagApplication hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrMeetDiagApplicationMapper.auditHrMeetDiag(hrNursingPromotion);
		
		return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String unauditMeetDiag(HrMeetDiagApplication hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrMeetDiagApplicationMapper.unauditHrHrMeetDiag(hrNursingPromotion);
		
		return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String dispassMeetDiag(HrMeetDiagApplication hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrMeetDiagApplicationMapper.dispassHrHrMeetDiag(hrNursingPromotion);
		
		return "{\"msg\":\"未通过成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String queryHistroy(Map<String, Object> entityMap) throws DataAccessException {

	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");

	if (sysPage.getTotal() == -1) {

		List<HrMeetDiagApplication> list = (List<HrMeetDiagApplication>) hrMeetDiagApplicationMapper
				.queryHistroy(entityMap);

		return ChdJson.toJson(list);

	} else {

		RowBounds rowBounds = new RowBounds(sysPage.getPage(),
				sysPage.getPagesize());

		List<HrMeetDiagApplication> list = (List<HrMeetDiagApplication>) hrMeetDiagApplicationMapper
				.queryHistroy(entityMap, rowBounds);

		@SuppressWarnings("rawtypes")
		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

}

@Override
public String queryMeetDetail(Map<String, Object> entityMap)
		throws DataAccessException {

	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");

	if (sysPage.getTotal() == -1) {

		List<HrMeetDiagAppDetail> list = (List<HrMeetDiagAppDetail>) hrMeetDiagApplicationDetailMapper.query(entityMap);

		return ChdJson.toJson(list);

	} else {

		RowBounds rowBounds = new RowBounds(sysPage.getPage(),
				sysPage.getPagesize());

		List<HrMeetDiagAppDetail> list = (List<HrMeetDiagAppDetail>) hrMeetDiagApplicationDetailMapper
				.query(entityMap, rowBounds);

		@SuppressWarnings("rawtypes")
		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

}

@Override
public List<Map<String, Object>> queryMeetDiagAppByPrint(
		Map<String, Object> entityMap) throws DataAccessException {

	 List<Map<String,Object>> list = ChdJson.toListLower(hrMeetDiagApplicationMapper.queryMeetDiagAppByPrint(entityMap));
	 return list;
}







}

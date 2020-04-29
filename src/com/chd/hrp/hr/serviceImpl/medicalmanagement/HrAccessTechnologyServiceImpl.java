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
import com.chd.hrp.hr.dao.medicalmanagement.HrAccessTechnologyMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechExecMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrEmpTechRefMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechRef;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrAccessTechnologyService;
import com.github.pagehelper.PageInfo;

/**
 * 技术准入
 * 
 * @author Administrator
 *
 */
@Service("hrAccessTechnologyService")
public class HrAccessTechnologyServiceImpl implements HrAccessTechnologyService {
	private static Logger logger = Logger
			.getLogger(HrAccessTechnologyServiceImpl.class);

	@Resource(name = "hrAccessTechnologyMapper")
	private final HrAccessTechnologyMapper hrAccessTechnologyMapper = null;

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
	 * 技术准入增加
	 */
	@Override
	public String addAccessTechnology(Map<String, Object> entityMap)
			throws DataAccessException {

		try {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_EMP_TECH_REC");
			mapVo.put("prm_perfixe", "TE");
			String apply_no = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("app_no", apply_no);
			HrAccessTechnology hrAccessTechnology	=hrAccessTechnologyMapper.queryByCode(entityMap);
			if(hrAccessTechnology==null){
				int state = hrAccessTechnologyMapper.add(entityMap);
				
				List<HrEmpTechExec> alllisLefttVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramLeftVo")), HrEmpTechExec.class);
				if(alllisLefttVo!= null){
					HrAccessTechnology accessTechnology = hrAccessTechnologyMapper.queryByCodeExec(entityMap);
					if (accessTechnology != null) {
						
						accessTechnology.setCase_num(alllisLefttVo.size());
						
						hrAccessTechnologyMapper.updateCaseNuM(accessTechnology);
						
						 hrEmpTechExecMapper.addTechExec(alllisLefttVo,entityMap);

					
					} 
				}
					
					List<HrEmpTechRef> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")), HrEmpTechRef.class);
				if(alllistVo!=null){
						HrAccessTechnology accessTechnology1 = hrAccessTechnologyMapper.queryByCodeExec(entityMap);
						if (accessTechnology1 != null) {
							
							 hrEmpTechRefMapper.addTechRef(alllistVo,entityMap);

						
						}
				}
                 if (state!=0) {
                	 hrBaseService.updateAndQueryHrBillNo(mapVo);
				}
     			return "{\"state\":\"true\",\"app_no\":\"" + apply_no+ "\"}";
			}else{
				return "{\"error\":\"请勿重复保存！\",\"state\":\"true\"}";
			}
			

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 技术准入修改
	 */
	@Override
	public String updateAccessTechnology(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrAccessTechnologyMapper.update(entityMap);


			List<HrEmpTechExec> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramLeftVo")),HrEmpTechExec.class);
			if(alllistVo!=null){
				HrAccessTechnology accessTechnology = hrAccessTechnologyMapper.queryByCodeExec(entityMap);
				accessTechnology.setCase_num(alllistVo.size());
				hrAccessTechnologyMapper.updateCaseNuM(accessTechnology);
				
				 List<HrEmpTechExec> alllist =hrEmpTechExecMapper.queryHrEmpTechExec(entityMap);
				if(alllist.size()>0){
					 hrEmpTechExecMapper.updateBatchTechExec(alllistVo,entityMap);
				}else{
					hrEmpTechExecMapper.addTechExec(alllistVo,entityMap);
				}
			}
			

			List<HrEmpTechRef> alllistVo1 = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrEmpTechRef.class);

if(alllistVo1!=null){
	hrEmpTechRefMapper.deleteHrTechRef(alllistVo1,entityMap);
	 hrEmpTechRefMapper.addTechRef(alllistVo1,entityMap);
}
			
			return "{\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 技术准入删除
	 */
	@Override
	public String deleteAccessTechnology(List<HrAccessTechnology> entityList)
			throws DataAccessException {

		try {
			List<Map<String, Object>> entityLis= new ArrayList<Map<String,Object>>();
			for (HrAccessTechnology hrAccessTechnology : entityList) {/*
				
				Map<String, Object> entity= new HashMap<String, Object>();
				entity.put("app_no", hrAccessTechnology.getApp_no());
				entity.put("emp_id", hrAccessTechnology.getEmp_id());
				entity.put("group_id",hrAccessTechnology.getGroup_id());
				entity.put("hos_id", hrAccessTechnology.getHos_id());
				entityLis.add(entity);
				
			*/}
			Map<String,Object> mapVo=new HashMap<String,Object>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			hrEmpTechExecMapper.deleteHrTechExe(entityList,mapVo);
			hrEmpTechRefMapper.deleteHrTechRef(entityList,mapVo);
			hrAccessTechnologyMapper.deleteAccessTechnology(entityList,mapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 技术准入查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAccessTechnology(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAccessTechnology> list = (List<HrAccessTechnology>) hrAccessTechnologyMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrAccessTechnology> list = (List<HrAccessTechnology>) hrAccessTechnologyMapper
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
	public HrAccessTechnology queryByCodeAccessTechnology(
			Map<String, Object> entityMap) throws DataAccessException {
		return hrAccessTechnologyMapper.queryByCode(entityMap);
	}

	@Override
	public String queryAccessTechnologyTree(Map<String, Object> entityMap)
			throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrAccessTechnology> storeTypeList = (List<HrAccessTechnology>) hrAccessTechnologyMapper
				.query(entityMap);
		for (HrAccessTechnology storeType : storeTypeList) {
			/*
			 * treeJson.append( "{'id':'" + storeType.getKind_code() +
			 * "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
			 */
		}
		treeJson.append("]");
		return treeJson.toString();
	}

	@Override
	public String confirmHrTechRec(HrAccessTechnology hrNursingPromotion)
			throws DataAccessException {
		try {
	         
			hrAccessTechnologyMapper.reConfirmHrHrTechRec(hrNursingPromotion);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String reConfirmHrHrTechRec(HrAccessTechnology hrNursingPromotion)
			throws DataAccessException {
		try {
	         
			hrAccessTechnologyMapper.reConfirmHrHrTechRec(hrNursingPromotion);
			
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
	public HrAccessTechnology queryByCode(HrAccessTechnology hrNursingPromotion)
			throws DataAccessException {
		return hrAccessTechnologyMapper.queryByCode(hrNursingPromotion);
	}

@Override
public HrAccessTechnology queryByCodeAdd(Map<String, Object> entityMap)
		throws DataAccessException {
	return hrAccessTechnologyMapper.queryByCodeAdd(entityMap);
	}

@Override
public String confirmHrTechRecAdd(HrAccessTechnology accessTechnology)
		throws DataAccessException {
	try {
        
		hrAccessTechnologyMapper.reConfirmHrHrTechRec(accessTechnology);
		
		return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String reConfirmHrHrTechRecAdd(HrAccessTechnology accessTechnology)
		throws DataAccessException {
	try {
        
		hrAccessTechnologyMapper.reConfirmHrHrTechRec(accessTechnology);
		
		return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String auditHrTechRec(HrAccessTechnology hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrAccessTechnologyMapper.reConfirmHrHrTechRec(hrNursingPromotion);
		
		return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String unauditHrHrTechRec(HrAccessTechnology hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrAccessTechnologyMapper.reConfirmHrHrTechRec(hrNursingPromotion);
		
		return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public String dispassHrHrTechRec(HrAccessTechnology hrNursingPromotion)
		throws DataAccessException {
	try {
        
		hrAccessTechnologyMapper.reConfirmHrHrTechRec(hrNursingPromotion);
		
		return "{\"msg\":\"未通过成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());

	}

}

@Override
public List<Map<String, Object>> queryAccessTechnologyByPrint(
		Map<String, Object> entityMap) throws DataAccessException {

	 List<Map<String,Object>> list = ChdJson.toListLower(hrAccessTechnologyMapper.queryAccessTechnologyByPrint(entityMap));
	 return list;
}





}

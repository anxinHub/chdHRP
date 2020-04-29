package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiDeptKindTargetDataMapper;
import com.chd.hrp.hpm.entity.AphiDeptKindTargetData;
import com.chd.hrp.hpm.entity.AphiHospTargetData;
import com.chd.hrp.hpm.service.AphiDeptKindTargetDataService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptKindTargetDataService")
public class AphiDeptKindTargetDataServiceImpl implements AphiDeptKindTargetDataService {

	private static Logger logger = Logger.getLogger(AphiDeptKindTargetDataServiceImpl.class);

	@Resource(name = "aphiDeptKindTargetDataMapper")
	private final AphiDeptKindTargetDataMapper aphiDeptKindTargetDataMapper = null;

	/**
	 * 
	 */
	@Override
	public String addDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {

//			AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//			if(dba != null){
//				
//				if(dba.getIs_audit() == 1){
//					
//					return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
//					
//				}
//				if(dba.getIs_grant()== 1){
//					
//					return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
//					
//				}
//				
//			}

			aphiDeptKindTargetDataMapper.addDeptKindTargetData(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addHospTargetData\"}";

		}

	}

	/**
	 * 
	 */
	@Override
	public String queryDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiDeptKindTargetDataMapper.queryDeptKindTargetData(entityMap, rowBounds), sysPage.getTotal());
		
	}

	/**
	 * 
	 */
	@Override
	public AphiDeptKindTargetData queryDeptKindTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiDeptKindTargetDataMapper.queryDeptKindTargetDataByCode(entityMap);
		
	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptKindTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException {
		try {
			
			if(codes.length()>0){
				
				String code = codes.split(",")[0];
				
				String[] code_array = code.split(";");
				
				String acct_year_month = code_array[2];

				entityMap.put("acct_year", acct_year_month.substring(0, 4));

				entityMap.put("acct_month", acct_year_month.substring(4, 6));
				
				List<AphiDeptKindTargetData> auditlist = aphiDeptKindTargetDataMapper.getDeptKindTargetValue(entityMap);

				if (auditlist.size() > 0) {

					return "{\"error\":\"该月指标已审核，不能进行删除\"}";

				}

//				AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//				if (dba != null) {
//
//					if (dba.getIs_audit() == 1) {
//
//						return "{\"error\":\"本月奖金已审核,不能删除本月数据\"}";
//
//					}
//					if (dba.getIs_grant() == 1) {
//
//						return "{\"error\":\"本月奖金已发放,不能删除本月数据\"}";
//
//					}
//
//				}
				
			}

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");

					entityMap.put("target_code", code_array[0]);
					
					entityMap.put("dept_kind_code", code_array[1]);
					
					String acct_year_month = code_array[2];

					entityMap.put("acct_year", acct_year_month.substring(0, 4));

					entityMap.put("acct_month", acct_year_month.substring(4, 6));

					int count = aphiDeptKindTargetDataMapper.deleteDeptKindTargetData(entityMap);

					if (count == 0) {
						return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
					}

				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";

		}

	}

	/**
	 * 
	 */
	@Override
	public String updateDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
//		AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//		if (dba != null) {
//
//			if (dba.getIs_audit() == 1) {
//
//				return "{\"error\":\"本月奖金已审核,不能修改本月数据\"}";
//
//			}
//			if (dba.getIs_grant() == 1) {
//
//				return "{\"error\":\"本月奖金已发放,不能修改本月数据\"}";
//
//			}
//
//		}

		try {

			 aphiDeptKindTargetDataMapper.updateDeptKindTargetData(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}";

		}

	}

	@Override
	public String initDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException {

		String year_month = (String) entityMap.get("acct_yearm");

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);

//		AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//		if (dba != null) {
//
//			if (dba.getIs_audit() == 1) {
//
//				return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
//
//			}
//			if (dba.getIs_grant() == 1) {
//
//				return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
//
//			}
//
//		}

		List<AphiDeptKindTargetData> auditlist = aphiDeptKindTargetDataMapper.getDeptKindTargetValue(entityMap);

		if (auditlist.size() > 0) {

			return "{\"error\":\"该月指标已审核，不能进行生成\"}";

		}


		String rbtnl = (String) entityMap.get("rbtnl");

		if ("all".equals(rbtnl)) {

			try {

				deleteBatchDeptKindTargetData(entityMap);

				List<AphiDeptKindTargetData> targetlist = aphiDeptKindTargetDataMapper.queryTargetData(entityMap);//查询所有指标

				List<AphiDeptKindTargetData> deptKindList = aphiDeptKindTargetDataMapper.queryDeptKind(entityMap);//查询所有科室分类
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				for(AphiDeptKindTargetData dktdt : targetlist){
					
					for(AphiDeptKindTargetData dktdd : deptKindList){
						
						Map<String, Object> map = new HashMap<String, Object>();

						map.put("group_id", entityMap.get("group_id"));
						
						map.put("hos_id", entityMap.get("hos_id"));

						map.put("copy_code", entityMap.get("copy_code"));

						map.put("acct_year", entityMap.get("acct_year"));

						map.put("acct_month", entityMap.get("acct_month"));

						map.put("target_code", dktdt.getTarget_code());
						
						map.put("dept_kind_code", dktdd.getDept_kind_code());
						
						map.put("target_value", 0);
						map.put("is_audit", 0);
						map.put("user_code", "");
						map.put("audit_time", "");

						list.add(map);
						
					}
					
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size()>0) {

					for(int i=0;i<list.size();i++){
						
						batchList.add(list.get(i));
						
						if(i%100 == 0){
							
							aphiDeptKindTargetDataMapper.addBatchDeptKindTargetData(list);
							
							batchList.removeAll(batchList);
						}
						
					}

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				}

				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";

			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);

				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptKindTargetData\"}";
			}

		} else if("check".equals(rbtnl)) {

			try {
				
				Map<String, String> map = new HashMap<String, String>();
				
				List<AphiDeptKindTargetData> deptKindTargetDataList = aphiDeptKindTargetDataMapper.queryDeptKindTargetData(entityMap);//查询所有数据

				for(AphiDeptKindTargetData dktd : deptKindTargetDataList){
					
					map.put(dktd.getTarget_code()+dktd.getDept_kind_code(), dktd.getTarget_code()+dktd.getDept_kind_code());
					
				}
				
				List<AphiDeptKindTargetData> targetlist = aphiDeptKindTargetDataMapper.queryTargetData(entityMap);//查询所有指标

				List<AphiDeptKindTargetData> deptKindList = aphiDeptKindTargetDataMapper.queryDeptKind(entityMap);//查询所有科室分类
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				for(AphiDeptKindTargetData dktdt : targetlist){
					
					for(AphiDeptKindTargetData dktdd : deptKindList){
						
						if(map.get(dktdt.getTarget_code()+dktdd.getDept_kind_code()) == null){
							
							Map<String, Object> incrementMap = new HashMap<String, Object>();

							incrementMap.put("group_id", entityMap.get("group_id"));
							
							incrementMap.put("hos_id", entityMap.get("hos_id"));

							incrementMap.put("copy_code", entityMap.get("copy_code"));

							incrementMap.put("acct_year", entityMap.get("acct_year"));

							incrementMap.put("acct_month", entityMap.get("acct_month"));

							incrementMap.put("target_code", dktdt.getTarget_code());
							
							incrementMap.put("dept_kind_code", dktdd.getDept_kind_code());
							
							incrementMap.put("target_value", 0);
							incrementMap.put("is_audit", 0);
							incrementMap.put("user_code", "");
							incrementMap.put("audit_time", "");

							list.add(incrementMap);
							
						}
						
					}
					
				}


				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size()>0) {

					for(int i=0;i<list.size();i++){
						
						batchList.add(list.get(i));
						
						if(i%100 == 0){
							
							aphiDeptKindTargetDataMapper.addBatchDeptKindTargetData(list);
							
							batchList.removeAll(batchList);
						}
						
					}

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";

			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);

				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptKindTargetData\"}";

			}

		}else{
			
			try{
				
				deleteBatchDeptKindTargetData(entityMap);

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				Map<String, Object> inheritMap = new HashMap<String, Object>();

				inheritMap.put("group_id", entityMap.get("group_id"));
				
				inheritMap.put("hos_id", entityMap.get("hos_id"));

				inheritMap.put("copy_code", entityMap.get("copy_code"));

				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));

				inheritMap.put("acct_year", preYearMonth.substring(0, 4));

				inheritMap.put("acct_month", preYearMonth.substring(4, 6));

				List<AphiDeptKindTargetData> deptKindTargetDataList = aphiDeptKindTargetDataMapper.queryDeptKindTargetData(inheritMap);// 查询所有数据

				Map<String, String> deptKindTargetDataMap = new HashMap<String, String>();
				
				for(AphiDeptKindTargetData dktd : deptKindTargetDataList){
					
					Map<String, Object> incrementMap = new HashMap<String, Object>();

					incrementMap.put("group_id", entityMap.get("group_id"));
					
					incrementMap.put("hos_id", entityMap.get("hos_id"));

					incrementMap.put("copy_code", entityMap.get("copy_code"));

					incrementMap.put("acct_year", entityMap.get("acct_year"));

					incrementMap.put("acct_month", entityMap.get("acct_month"));

					incrementMap.put("target_code", dktd.getTarget_code());
					
					incrementMap.put("dept_kind_code", dktd.getDept_kind_code());
					
					incrementMap.put("target_value", 0);
					incrementMap.put("is_audit", 0);
					incrementMap.put("user_code", "");
					incrementMap.put("audit_time", "");

					list.add(incrementMap);
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size()>0) {

					for(int i=0;i<list.size();i++){
						
						batchList.add(list.get(i));
						
						if(i%100 == 0){
							
							aphiDeptKindTargetDataMapper.addBatchDeptKindTargetData(list);
							
							batchList.removeAll(batchList);
						}
						
					}

					return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";

				}
				
				return "{\"msg\":\"没有继承数据.\",\"state\":\"false\"}";

			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);

				return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  initDeptKindTargetData\"}";
			}
		}

	}

	@Override
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiDeptKindTargetDataMapper.getTargetCode(entityMap);
		
	}

	@Override
	public String queryDeptKindTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException {
		
		List<AphiDeptKindTargetData> list = aphiDeptKindTargetDataMapper.queryDeptKindTargetData(entityMap);
		
		if (list.size() > 0) {
			
			return "{\"msg\":\"\",\"state\":\"false\"}";
			
		} else {
			
			return "{\"msg\":\"\",\"state\":\"true\"}";
			
		}
	}

	@Override
	public String deleteBatchDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiDeptKindTargetDataMapper.deleteDeptKindTargetData(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"\",\"state\":\"false\"}";
			
		}
	}

	@Override
	public String shenhe(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiDeptKindTargetDataMapper.shenhe(entityMap);
		if (state > 0) {
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"审核失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String addBatchDeptKindTargetData(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiDeptKindTargetDataMapper.addBatchDeptKindTargetData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	@Override
	public String getDeptKindTargetValue(Map<String, Object> entityMap) throws DataAccessException {

		String year_month = (String) entityMap.get("acct_yearm");

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);

		List<AphiDeptKindTargetData> list = aphiDeptKindTargetDataMapper.getDeptKindTargetValue(entityMap);

		if (list.size() > 0) {

			return "{\"state\":\"true\"}";

		}

		return "{\"state\":\"false\"}";

	}

	@Override
	public List<Map<String, Object>> queryDeptKindTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if (entityMap.get("acct_yearm") != null && !"".equals(entityMap.get("acct_yearm"))) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);

		}
		
		List<Map<String, Object>> list = aphiDeptKindTargetDataMapper.queryDeptKindTargetDataPrint(entityMap);
		
		return list;
	}
}

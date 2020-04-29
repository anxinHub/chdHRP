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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.dao.AphiDeptTargetDataMapper;
import com.chd.hrp.hpm.dao.AphiSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiTargetMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
import com.chd.hrp.hpm.entity.AphiHospTargetData;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.service.AphiDeptTargetDataService;
import com.github.pagehelper.PageInfo;

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

@Service("aphiDeptTargetDataService")
public class AphiDeptTargetDataServiceImpl implements AphiDeptTargetDataService {

	private static Logger logger = Logger.getLogger(AphiDeptTargetDataServiceImpl.class);

	@Resource(name = "aphiDeptTargetDataMapper")
	private final AphiDeptTargetDataMapper aphiDeptTargetDataMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	@Resource(name = "aphiTargetMapper")
	private final AphiTargetMapper aphiTargetMapper = null;
	
	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;
	
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;
	
	@Resource(name = "aphiSchemeConfMapper")
	private final AphiSchemeConfMapper aphiSchemeConfMapper = null;
	/**
	 * 
	 */
	@Override
	public String addDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {

		try {

//			AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//			if (dba != null) {
//
//				if (dba.getIs_audit() == 1) {
//
//					return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
//
//				}
//				if (dba.getIs_grant() == 1) {
//
//					return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
//
//				}
//
//			}

			aphiDeptTargetDataMapper.addDeptTargetData(entityMap);

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
	public String queryDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptTargetData> list = aphiDeptTargetDataMapper.queryDeptTargetData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptTargetData> list = aphiDeptTargetDataMapper.queryDeptTargetData(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 
	 */
	@Override
	public AphiDeptTargetData queryDeptTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptTargetDataMapper.queryDeptTargetDataByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes.length() > 0) {

				String code = codes.split(",")[0];

				String[] code_array = code.split(";");

				String acct_year_month = code_array[3];

				entityMap.put("acct_year", acct_year_month.substring(0, 4));

				entityMap.put("acct_month", acct_year_month.substring(4, 6));

				

			}

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");

					entityMap.put("target_code", code_array[0]);

					entityMap.put("dept_id", code_array[1]);
					
					entityMap.put("dept_no", code_array[2]);

					String acct_year_month = code_array[3];

					entityMap.put("acct_year", acct_year_month.substring(0, 4));

					entityMap.put("acct_month", acct_year_month.substring(4, 6));

					List<AphiDeptTargetData> auditlist = aphiDeptTargetDataMapper.getDeptTargetValue(entityMap);

					if (auditlist.size() > 0) {

						return "{\"error\":\"存在审核的指标，不能删除\"}";

					}

				}
			}

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");

					entityMap.put("target_code", code_array[0]);

					entityMap.put("dept_id", code_array[1]);
					
					entityMap.put("dept_no", code_array[2]);

					String acct_year_month = code_array[3];

					entityMap.put("acct_year", acct_year_month.substring(0, 4));

					entityMap.put("acct_month", acct_year_month.substring(4, 6));

					int count = aphiDeptTargetDataMapper.deleteDeptTargetData(entityMap);

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
	public String updateDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {

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

			aphiDeptTargetDataMapper.updateDeptTargetData(entityMap);
			
			//提示信息 msg改为msg1不提示
			return "{\"msg1\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}";

		}

	}

	@Override
	public String initDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
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

		List<AphiDeptTargetData> auditlist = aphiDeptTargetDataMapper.getDeptTargetValue(entityMap);

		if (auditlist.size() > 0) {

			return "{\"error\":\"该月指标已审核，不能进行生成\"}";

		}
		
		// 根据年月查询方案序号
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		if(sc == null){
			return "{\"warn\":\"当前核算年月未配置方案 \"}";
		}
		
		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());

		String rbtnl = (String) entityMap.get("rbtnl");

		if ("all".equals(rbtnl)) {

			try {

				deleteBatchDeptTargetData(entityMap);

				List<AphiDeptTargetData> targetlist = aphiDeptTargetDataMapper.queryTargetData(entityMap);// 查询所有指标
				
				List<AphiDeptTargetData> deptKindList = aphiDeptTargetDataMapper.queryDeptBySchemeSeqNo(entityMap);// 查询科室

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				for (AphiDeptTargetData dktdt : targetlist) {

					for (AphiDeptTargetData dktdd : deptKindList) {

						Map<String, Object> map = new HashMap<String, Object>();

						map.put("group_id", entityMap.get("group_id"));
						
						map.put("hos_id", entityMap.get("hos_id"));

						map.put("copy_code", entityMap.get("copy_code"));

						map.put("acct_year", entityMap.get("acct_year"));

						map.put("acct_month", entityMap.get("acct_month"));

						map.put("target_code", dktdt.getTarget_code());

						map.put("target_value", 0);

						map.put("dept_id", dktdd.getDept_id());
						
						map.put("dept_no",dktdd.getDept_no());

						map.put("is_audit", 0);
						
						map.put("audit_time", "");
						
						map.put("user_code", "");

						list.add(map);

					}

				}

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						batchList.add(list.get(i));

						if (i % 500 == 0) {

							aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);

							batchList.removeAll(batchList);
						}

					}

					if(batchList.size() > 0){
						
						aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);
					}

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				}

				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptKindTargetData\"}";
			}

		} else if ("check".equals(rbtnl)) {

			try {

				Map<String, String> map = new HashMap<String, String>();

				List<AphiDeptTargetData> deptTargetDataList = aphiDeptTargetDataMapper.queryDeptTargetData(entityMap);// 查询所有数据

				for (AphiDeptTargetData dktd : deptTargetDataList) {

					if (dktd.getTarget_code() != null && !"".equals(dktd.getTarget_code())) {

						map.put(dktd.getTarget_code() + dktd.getDept_id(), dktd.getTarget_code() + dktd.getDept_id());

					}

				}

				List<AphiDeptTargetData> targetlist = aphiDeptTargetDataMapper.queryTargetData(entityMap);// 查询所有指标

				List<AphiDeptTargetData> deptKindList = aphiDeptTargetDataMapper.queryDeptBySchemeSeqNo(entityMap);// 查询科室

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				for (AphiDeptTargetData dktdt : targetlist) {

					for (AphiDeptTargetData dktdd : deptKindList) {

						if (map.get(dktdt.getTarget_code() + dktdd.getDept_id()) == null) {

							Map<String, Object> incrementMap = new HashMap<String, Object>();

							incrementMap.put("group_id", entityMap.get("group_id"));
							
							incrementMap.put("hos_id", entityMap.get("hos_id"));

							incrementMap.put("copy_code", entityMap.get("copy_code"));

							incrementMap.put("acct_year", entityMap.get("acct_year"));

							incrementMap.put("acct_month", entityMap.get("acct_month"));

							incrementMap.put("target_code", dktdt.getTarget_code());

							incrementMap.put("dept_id", dktdd.getDept_id());
							
							incrementMap.put("dept_no", dktdd.getDept_no());

							incrementMap.put("is_audit", 0);
							
							incrementMap.put("audit_time", "");
							
							incrementMap.put("user_code", "");
							
							incrementMap.put("target_value", 0);

							list.add(incrementMap);

						}

					}

				}

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						batchList.add(list.get(i));

						if (i % 500 == 0) {

							aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);

							batchList.removeAll(batchList);
						}

					}

					if(batchList.size() > 0){
						
						aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);
					}

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				}

				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptKindTargetData\"}";

			}

		} else if("inherit_value".equals(rbtnl)){//继承上月数据(带值)

			try {

				deleteBatchDeptTargetData(entityMap);

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				Map<String, Object> inheritMap = new HashMap<String, Object>();

				inheritMap.put("group_id", entityMap.get("group_id"));
				
				inheritMap.put("hos_id", entityMap.get("hos_id"));

				inheritMap.put("copy_code", entityMap.get("copy_code"));
				
				inheritMap.put("user_id", entityMap.get("user_id"));

				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));

				inheritMap.put("acct_year", preYearMonth.substring(0, 4));

				inheritMap.put("acct_month", preYearMonth.substring(4, 6));

				List<AphiDeptTargetData> deptKindTargetDataList = aphiDeptTargetDataMapper.queryDeptTargetData(inheritMap);// 查询所有数据


				for (AphiDeptTargetData dktd : deptKindTargetDataList) {

					Map<String, Object> incrementMap = new HashMap<String, Object>();

					if (dktd.getTarget_code() != null && !"".equals(dktd.getTarget_code())) {

						incrementMap.put("group_id", entityMap.get("group_id"));
						
						incrementMap.put("hos_id", entityMap.get("hos_id"));

						incrementMap.put("copy_code", entityMap.get("copy_code"));

						incrementMap.put("acct_year", entityMap.get("acct_year"));

						incrementMap.put("acct_month", entityMap.get("acct_month"));

						incrementMap.put("target_code", dktd.getTarget_code());

						incrementMap.put("target_value", dktd.getTarget_value());

						incrementMap.put("dept_id", dktd.getDept_id());
						
						incrementMap.put("dept_no", dktd.getDept_no());

						incrementMap.put("is_audit", 0);
						
						incrementMap.put("audit_time", "");
						
						incrementMap.put("user_code", "");
						
						//incrementMap.put("target_value", 0);

						list.add(incrementMap);
					}

				}

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						batchList.add(list.get(i));

						if (i % 500 == 0) {

							aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);

							batchList.removeAll(batchList);
						}

					}
					
					if(batchList.size() > 0){
						
						aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);
					}


					return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";

				}

				return "{\"msg\":\"没有继承数据.\",\"state\":\"false\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  initDeptKindTargetData\"}";
			}
		}else{//继承上月数据-不带值
			
			try {

				deleteBatchDeptTargetData(entityMap);

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				Map<String, Object> inheritMap = new HashMap<String, Object>();

				inheritMap.put("group_id", entityMap.get("group_id"));
				
				inheritMap.put("hos_id", entityMap.get("hos_id"));

				inheritMap.put("copy_code", entityMap.get("copy_code"));
				
				inheritMap.put("user_id", entityMap.get("user_id"));

				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));

				inheritMap.put("acct_year", preYearMonth.substring(0, 4));

				inheritMap.put("acct_month", preYearMonth.substring(4, 6));

				List<AphiDeptTargetData> deptKindTargetDataList = aphiDeptTargetDataMapper.queryDeptTargetData(inheritMap);// 查询所有数据

				for (AphiDeptTargetData dktd : deptKindTargetDataList) {

					Map<String, Object> incrementMap = new HashMap<String, Object>();

					if (dktd.getTarget_code() != null && !"".equals(dktd.getTarget_code())) {

						incrementMap.put("group_id", entityMap.get("group_id"));
						
						incrementMap.put("hos_id", entityMap.get("hos_id"));

						incrementMap.put("copy_code", entityMap.get("copy_code"));

						incrementMap.put("acct_year", entityMap.get("acct_year"));

						incrementMap.put("acct_month", entityMap.get("acct_month"));

						incrementMap.put("target_code", dktd.getTarget_code());

						incrementMap.put("target_value",0);

						incrementMap.put("dept_id", dktd.getDept_id());
						
						incrementMap.put("dept_no", dktd.getDept_no());

						incrementMap.put("is_audit", 0);
						
						incrementMap.put("audit_time", "");
						
						incrementMap.put("user_code", "");
						
						//incrementMap.put("target_value", 0);

						list.add(incrementMap);
					}

				}

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						batchList.add(list.get(i));

						if (i % 500 == 0) {

							aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);

							batchList.removeAll(batchList);
						}

					}
					
					if(batchList.size() > 0){
						
						aphiDeptTargetDataMapper.addBatchDeptTargetData(batchList);
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

		return aphiDeptTargetDataMapper.getTargetCode(entityMap);

	}

	@Override
	public String queryDeptTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<AphiDeptTargetData> list = aphiDeptTargetDataMapper.queryDeptTargetData(entityMap);

		if (list.size() > 0) {

			return "{\"msg\":\"\",\"state\":\"false\"}";

		} else {

			return "{\"msg\":\"\",\"state\":\"true\"}";

		}
	}

	@Override
	public String deleteBatchDeptTargetData(Map<String, Object> entityMap) throws DataAccessException {

		int state = aphiDeptTargetDataMapper.deleteDeptTargetData(entityMap);

		if (state > 0) {

			return "{\"msg\":\"\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"\",\"state\":\"false\"}";

		}
	}

	@Override
	public String shenhe(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			//2017/07/28 绍兴需求:在绩效工资审核页面中已审核,此处不允许反审
			if("0".equals(entityMap.get("audit_state"))){
				
//				AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//				
//				if(adba != null && "1".equals(String.valueOf(adba.getIs_audit()))){
//					
//					return "{\"warn\":\"当前年月指标在绩效工资审核中已经审核,不能反审.\",\"state\":\"false\"}";
//				}
			}
			
			String checkIds = String.valueOf(entityMap.get("checkIds"));

			for (String ids : checkIds.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String target_code = ids.split(";")[0];

				String dept_id = ids.split(";")[1];
				
				String dept_no = ids.split(";")[2];

				String acct_yearm = ids.split(";")[3];

				String is_audit = ids.split(";")[4];
				
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				
				mapVo.put("target_code", target_code);
				mapVo.put("dept_id", dept_id);
				mapVo.put("dept_no", dept_no);
				mapVo.put("acct_year", acct_yearm.substring(0, 4));
				mapVo.put("acct_month", acct_yearm.substring(4));

				
				mapVo.put("is_audit", is_audit);

				if ("1".equals(is_audit)) {

					mapVo.put("user_code", SessionManager.getUserCode());

					mapVo.put("audit_time", DateUtil.getCurrenDate("yyyy/MM/dd"));

				} else {

					mapVo.put("user_code", "");

					mapVo.put("audit_time", "");

				}

				aphiDeptTargetDataMapper.shenhe(mapVo);
			}


			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\"}");
		}
	}

	@Override
	public String addBatchDeptTargetData(List<Map<String, Object>> entityMap) throws DataAccessException {

		int state = aphiDeptTargetDataMapper.addBatchDeptTargetData(entityMap);

		if (state > 0) {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}

		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";

	}

	@Override
	public String getDeptTargetValue(Map<String, Object> entityMap) throws DataAccessException {

		String year_month = (String) entityMap.get("acct_yearm");

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);

		List<AphiDeptTargetData> list = aphiDeptTargetDataMapper.getDeptTargetValue(entityMap);

		if (list.size() > 0) {

			return "{\"state\":\"true\"}";

		}

		return "{\"state\":\"false\"}";
	}

	@Override
	public List<AphiDeptTargetData> queryDeptTargetDataByTargetCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptTargetDataMapper.queryDeptTargetData(entityMap);
	}

	@Override
	public String queryDeptTargetView(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		StringBuffer deptSql = new StringBuffer();
		StringBuffer targetSql = new StringBuffer();
		
		if(entityMap.get("dept") != null && !"".equals(entityMap.get("dept"))){
			
			deptSql.append("AND adtd.dept_id|| ',' || adtd.dept_no in (");
			String [] dept_id_nos = entityMap.get("dept").toString().split("@");
			for(String dept_id_no:dept_id_nos){
				
				deptSql.append("'" + dept_id_no + "',");
				
			}
			deptSql = deptSql.deleteCharAt(deptSql.length() - 1).append(")");
			entityMap.put("deptSql", deptSql.toString());
		}
		
		if(entityMap.get("target") != null && !"".equals(entityMap.get("target"))){
			
			targetSql.append("AND adtd.target_code in (");
			String [] targetCodes = entityMap.get("target").toString().split("@");
			for(String targetCode:targetCodes){
				
				targetSql.append("'" + targetCode + "',");
				
			}
			targetSql = targetSql.deleteCharAt(targetSql.length() - 1).append(")");
			entityMap.put("targetSql", targetSql.toString());
		}

		List<AphiDeptTargetData> dtdList = aphiDeptTargetDataMapper.queryDeptTargetViewGrid(entityMap);

		if (dtdList.size() > 0) {

			StringBuffer sql_case = new StringBuffer();
			
			StringBuffer money_count = new StringBuffer();

			for (int i = 0; i < dtdList.size(); i++) {

				AphiDeptTargetData target = (AphiDeptTargetData) dtdList.get(i);

				sql_case.append(""
						+ "sum(nvl((case when adtd.target_code = '" + target.getTarget_code() + "' then adtd.target_value end),0)) as  "+target.getTarget_code()+ ",");
				
				money_count.append("sum(nvl((case when adtd.target_code = '" + target.getTarget_code() + "' then adtd.target_value end),0)) +");

			}
			
			if(money_count.length() > 0){
				sql_case.append(money_count.deleteCharAt(money_count.length()-1) + " as bonus_money,");
			}
			
			entityMap.put("sql_case", sql_case);

		}
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = aphiDeptTargetDataMapper.queryDeptTargetView(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = aphiDeptTargetDataMapper.queryDeptTargetView(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}

	@Override
	public String queryDeptTargetViewGrid(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		StringBuffer deptSql = new StringBuffer();
		StringBuffer targetSql = new StringBuffer();
		
		if(entityMap.get("dept") != null && !"".equals(entityMap.get("dept"))){
			
			deptSql.append("AND adtd.dept_id|| ',' || adtd.dept_no in (");
			String [] dept_id_nos = entityMap.get("dept").toString().split("@");
			for(String dept_id_no:dept_id_nos){
				
				deptSql.append("'" + dept_id_no + "',");
				
			}
			deptSql = deptSql.deleteCharAt(deptSql.length() - 1).append(")");
			entityMap.put("deptSql", deptSql.toString());
		}
		
		if(entityMap.get("target") != null && !"".equals(entityMap.get("target"))){
			
			targetSql.append("AND adtd.target_code in (");
			String [] targetCodes = entityMap.get("target").toString().split("@");
			for(String targetCode:targetCodes){
				
				targetSql.append("'" + targetCode + "',");
				
			}
			targetSql = targetSql.deleteCharAt(targetSql.length() - 1).append(")");
			entityMap.put("targetSql", targetSql.toString());
		}

		List<AphiDeptTargetData> dtdList = aphiDeptTargetDataMapper.queryDeptTargetViewGrid(entityMap);

		StringBuffer columns= new StringBuffer();

		Integer size = dtdList.size();

		columns.append("[");

		columns.append("{display: \'科室编码\', name: \'dept_code\', align: \'left\',width:120},");

		columns.append("{display: \'科室名称\', name: \'dept_name\', align: \'left\',width:180},");

		for (int i = 0; i < size; i++) {

			AphiDeptTargetData dtd = dtdList.get(i);

			columns.append("{display : \'"+ dtd.getTarget_name()+ "\',name : \'"+ dtd.getTarget_code().toLowerCase()+"\',align : \'right\',width:160,totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}},"
							+ "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata." + dtd.getTarget_code().toLowerCase()
							+ " ==null ? 0 : rowdata." + dtd.getTarget_code().toLowerCase() + ",2,1);" + "}" + "},");

		}

		columns.append("{ display: \'合计\', name: \'bonus_money\', align: \'right\',width:160,totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}},"
						+ "render: function (rowdata , rowindex , value){"
						+ "return formatNumber(rowdata.bonus_money ==null ? 0 : rowdata.bonus_money,2,1);"
						+ "}" + "}");
		
		columns.append("]");
		
		return columns.toString();
	}

	@Override
	public String importDeptTargerData(Map<String, Object> map) throws DataAccessException {
		
			try{
			
			//1.判断表头是否为空
			String columns=map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=map.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("dept_kind_code",map.get("dept_kind_code"));
			
			// 3.查询 基本指标字典 List
			List<AphiTarget> targetList = aphiTargetMapper.queryPrmTarget(entityMap);
			//用于存储查询targetList中的AphiTarget对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, AphiTarget> targetMap = new HashMap<String, AphiTarget>();
			//将指标List存入Map   键:target_code 值:AphiTarget
			for(AphiTarget target : targetList){
				targetMap.put(target.getTarget_code(), target);
				targetMap.put(target.getTarget_name(), target);
			}
			
			//4.判断表头中指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的指标
			Map<String,String> targetColumnMap = new HashMap<String,String>();//用于存储表头中的指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					if("年度".equals(key) || "月份".equals(key) || "科室名称".equals(key) || "科室编码".equals(key) || "科室".equals(key) ||"科室名称或科室编码".equals(key)){
						continue;
					}
					
					targetColumnMap.put(key, key);
					if(targetMap.get(key) == null){
						sb.append("指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(targetColumnMap == null || targetColumnMap.size() == 0){
				return "{\"error\":\"表头中未存在指标或未填写任何指标值\",\"state\":\"false\"}";
			}
			
			//表头含有不存在指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}
			
			//5.1 查询当前选择的核算年月 查询当前年月所有科室指标数据
			entityMap.put("acct_year", String.valueOf(map.get("acct_yearm")).substring(0,4));
			entityMap.put("acct_month", String.valueOf(map.get("acct_yearm")).substring(4,6));
			List<AphiDeptTargetData> adtdList = aphiDeptTargetDataMapper.queryAphiDeptTargetDataAll(entityMap);
			//以年、月、科室id、科室变更no、指标编码作为键,adtd对象作为值,判断数据是否存在
			Map<String,AphiDeptTargetData> adtdMap = new HashMap<String,AphiDeptTargetData>();
			for(AphiDeptTargetData adtd : adtdList){
				
				String key = String.valueOf(adtd.getAcct_year()) +
						String.valueOf(adtd.getAcct_month()) + 
						String.valueOf(adtd.getDept_id()) + 
						String.valueOf(adtd.getDept_no()) + 
						String.valueOf(adtd.getTarget_code());
				
				adtdMap.put(key, adtd);
			}
			
			
			//5.2查询科室
			
			//查询所有绩效科室
			List<AphiDeptDict> deptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptDict> deptDictMap = new HashMap<String,AphiDeptDict>();
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(AphiDeptDict deptDict : deptDictList){
				deptDictMap.put(deptDict.getDept_name(), deptDict);
				deptDictMap.put(deptDict.getDept_code(), deptDict);
			}
			
			//按科室与系统平台对应关系查询科室  List
			List<AphiDept> deptList = aphiDeptMapper.queryAphiDeptRelaByMaping(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDept> deptMap = new HashMap<String,AphiDept>();
			//将科室List存入Map   键:dept_name 值:AphiDept
			for(AphiDept dept : deptList){
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}
			
			//按科室与其它平台对应关系查询科室  List
			List<AphiDeptHip> deptHipList = aphiDeptHipMapper.queryAphiDeptRelaByMapingHip(entityMap);			
			//用于存储查询deptHipList中的AphiDeptHip对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptHip> deptHipMap = new HashMap<String,AphiDeptHip>();
			//将科室List存入Map   键:dept_name 值:AphiDeptHip
			for(AphiDeptHip deptHip : deptHipList){
				deptHipMap.put(deptHip.getDept_name(), deptHip);
				deptHipMap.put(deptHip.getDept_code(), deptHip);
			}
			
			
			//6.组装数据
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			//存储添加数据List
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			//存储修改数据List
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			for(Map.Entry<String, String> entry:targetColumnMap.entrySet()){//遍历指标
				
				//遍历导入数据
				for(Map<String,List<String>> item : liData ){
					
					List<String> acct_year = item.get("年度") ;
					List<String> acct_month = item.get("月份") ;
					List<String> dept_name = item.get("科室名称或科室编码") ;
					if (dept_name == null) {
						dept_name = item.get("科室名称")==null? item.get("科室编码"):item.get("科室名称");
					}
					List<String> target_code = item.get(entry.getKey()) ;//指标
					
					
					if(acct_year.get(1) == null){
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":\"" +acct_year.get(0) +"\"}";
					}else{
						if(!entityMap.get("acct_year").equals(acct_year.get(1))){
							return "{\"warn\":\"年度与当前选择的核算年月中年度不一致！\",\"state\":\"false\",\"row_cell\":\"" +acct_year.get(0) +"\"}";
						}
					}
					
					if(acct_month.get(1) == null){
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) +"\"}";
					}else{
						if(!entityMap.get("acct_month").equals(acct_month.get(1))){
							return "{\"warn\":\"月份与当前选择的核算年月中月份不一致！\",\"state\":\"false\",\"row_cell\":\"" +acct_month.get(0) +"\"}";
						}
					}
					
					if(target_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
					}
					
					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室为空！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(1) + "\"}";
					}else{
						if(deptMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if(target_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
					}else{
						 try{
							 Double.parseDouble((target_code.get(1)));//校验是否为数值
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + target_code.get(1) + ",指标值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
						 }
					}
					
					//以年度|月份|指标编码|科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + "|" +acct_month.get(1) + "|" + targetMap.get(entry.getKey()).getTarget_code() + "|" + dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acct_year.get(1)+"年度," + acct_month.get(1)+"月份," + dept_name.get(1)+"科室," + entry.getKey()+"指标<br/>");
					}else{
						exitMap.put(key, key);
					}
					
					
					//添加数据Map
					Map<String,Object> addMap = new HashMap<String,Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					addMap.put("acct_year", acct_year.get(1));
					addMap.put("acct_month", acct_month.get(1));
					addMap.put("target_code",targetMap.get(entry.getKey()).getTarget_code());
					
					//系统平台科室
					if(deptMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptMap.get(dept_name.get(1)).getDept_no());
					}
					
					//其它平台科室
					if(deptHipMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptHipMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptHipMap.get(dept_name.get(1)).getDept_no());
					}
					
					//绩效科室
					if(deptDictMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptDictMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptDictMap.get(dept_name.get(1)).getDept_no());
					}
					
					//addMap.put("target_name", dictMap.get(target_code.get(1)).getTarget_name());
					addMap.put("target_value", target_code.get(1));
					addMap.put("is_audit", 0);
					addMap.put("user_code", "");
					addMap.put("is_audit","0");
					addMap.put("audit_time","");
					
					//根据年+月+科室id+科室变更no+指标编码 作为键 判断数据库中是否存在数据
					String is_exit_key = 
							String.valueOf(addMap.get("acct_year")) +
							String.valueOf(addMap.get("acct_month")) +
							String.valueOf(addMap.get("dept_id")) +
							String.valueOf(addMap.get("dept_no")) +
							String.valueOf(addMap.get("target_code")) ;
					
					
					AphiDeptTargetData adtd = adtdMap.get(is_exit_key);
					if(adtd == null){//不存在,添加
						
						addList.add(addMap);
					}else{
						
						if( !"1".equals(String.valueOf(adtd.getIs_audit())) ){//存在,如果未审核,添加到修改List
							
							updateList.add(addMap);
						}else {
							return "{\"warn\":\"指标已审核，请消审后重新导入！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
						}
					}
				}
			}
			
			if( err_sb.length() > 0){//重复数据是否存在
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			if(addList.size() > 0){
				
				List<Map<String,Object>> tempAddList = new ArrayList<Map<String,Object>>();
				
				for (int i = 0; i < addList.size(); i++) {
					
					tempAddList.add(addList.get(i));

					if ((i+1) % 500 == 0) {
						
						aphiDeptTargetDataMapper.addBatchDeptTargetData(tempAddList);
						tempAddList.removeAll(tempAddList);
					}
				}
				
				aphiDeptTargetDataMapper.addBatchDeptTargetData(tempAddList);
			}
			
			List<Map<String,Object>> tempUpdateList = new ArrayList<Map<String,Object>>();
			if(updateList.size() > 0){
				
				for (int i = 0; i < updateList.size(); i++) {
					
					tempUpdateList.add(updateList.get(i));

					if ((i+1) % 500 == 0) {
						
						aphiDeptTargetDataMapper.updateBatchDeptTargetData(tempUpdateList);
						tempUpdateList.removeAll(tempUpdateList);
					}
				}
				aphiDeptTargetDataMapper.updateBatchDeptTargetData(tempUpdateList);
			}
				
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
		
	}

	@Override
	public List<Map<String, Object>> queryDeptTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}

		if (entityMap.get("acct_yearm") != null&& !"".equals(entityMap.get("acct_yearm"))) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}
		
		
		if("false".equals(entityMap.get("is_show_zero"))){//不显示指标值为0 的数据
			entityMap.put("is_show_zero", "0");
		}
		
		List<Map<String, Object>> list = aphiDeptTargetDataMapper.queryDeptTargetDataPrint(entityMap);
		
		return list;
	}
}

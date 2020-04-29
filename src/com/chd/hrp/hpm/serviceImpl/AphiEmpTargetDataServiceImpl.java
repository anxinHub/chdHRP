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

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiEmpDictMapper;
import com.chd.hrp.hpm.dao.AphiEmpTargetDataMapper;
import com.chd.hrp.hpm.dao.AphiTargetMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;
import com.chd.hrp.hpm.entity.AphiEmpDict;
import com.chd.hrp.hpm.entity.AphiEmpTargetData;
import com.chd.hrp.hpm.entity.AphiHospTargetData;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.service.AphiEmpTargetDataService;

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

@Service("aphiEmpTargetDataService")
public class AphiEmpTargetDataServiceImpl implements AphiEmpTargetDataService {
	
	private static Logger logger = Logger.getLogger(AphiEmpTargetDataServiceImpl.class);
	
	@Resource(name = "aphiEmpTargetDataMapper")
	private final AphiEmpTargetDataMapper aphiEmpTargetDataMapper = null;
	
	@Resource(name = "aphiEmpDictMapper")
	private final AphiEmpDictMapper aphiEmpDictMapper = null;
	
	@Resource(name = "aphiTargetMapper")
	private final AphiTargetMapper aphiTargetMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
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
			
			aphiEmpTargetDataMapper.addEmpTargetData(entityMap);
			
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
	public String queryEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiEmpTargetDataMapper.queryEmpTargetData(entityMap, rowBounds), sysPage.getTotal());
		
	}
	
	/**
	 * 
	 */
	@Override
	public AphiEmpTargetData queryEmpTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiEmpTargetDataMapper.queryEmpTargetDataByCode(entityMap);
		
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteEmpTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException {
		
		try {
			
			if (codes.length() > 0) {
				
				String code = codes.split(",")[0];
				
				String[] code_array = code.split(";");
				
				String acct_year_month = code_array[3];
				
				entityMap.put("acct_year", acct_year_month.substring(0, 4));
				
				entityMap.put("acct_month", acct_year_month.substring(4, 6));
				
				List<AphiEmpTargetData> auditlist = aphiEmpTargetDataMapper.getEmpTargetValue(entityMap);
				
				if (auditlist.size() > 0) {
					
					return "{\"error\":\"该月指标已审核，不能进行删除\"}";
					
				}
				
				// AphiDeptBonusAudit dba =
				// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
				//
				// if (dba != null) {
				//
				// if (dba.getIs_audit() == 1) {
				//
				// return "{\"error\":\"本月奖金已审核,不能删除本月数据\"}";
				//
				// }
				// if (dba.getIs_grant() == 1) {
				//
				// return "{\"error\":\"本月奖金已发放,不能删除本月数据\"}";
				//
				// }
				//
				// }
				
			}
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_codeArray = codes.split(",");
				
				for (String code : code_codeArray) {
					
					String[] code_array = code.split(";");
					
					entityMap.put("target_code", code_array[0]);
					entityMap.put("emp_id", code_array[1]);
					entityMap.put("emp_no", code_array[2]);
					String acct_year_month = code_array[3];
					
					entityMap.put("acct_year", acct_year_month.substring(0, 4));
					
					entityMap.put("acct_month", acct_year_month.substring(4, 6));
					
					int count = aphiEmpTargetDataMapper.deleteEmpTargetData(entityMap);
					
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
			
			throw new SysException("{\"error\":\"操作失败\"}");
			
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String updateEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		// AphiDeptBonusAudit dba =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		//
		// if (dba != null) {
		//
		// if (dba.getIs_audit() == 1) {
		//
		// return "{\"error\":\"本月奖金已审核,不能修改本月数据\"}";
		//
		// }
		// if (dba.getIs_grant() == 1) {
		//
		// return "{\"error\":\"本月奖金已发放,不能修改本月数据\"}";
		//
		// }
		//
		// }
		
		try {
			
			aphiEmpTargetDataMapper.updateEmpTargetData(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}";
			
		}
	}
	
	@Override
	public String initEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("acct_yearm");
		
		String acct_year = year_month.substring(0, 4);
		
		String acct_month = year_month.substring(4, 6);
		
		entityMap.put("acct_year", acct_year);
		
		entityMap.put("acct_month", acct_month);
		
		// AphiDeptBonusAudit dba =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		//
		// if (dba != null) {
		//
		// if (dba.getIs_audit() == 1) {
		//
		// return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
		//
		// }
		// if (dba.getIs_grant() == 1) {
		//
		// return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
		//
		// }
		//
		// }
		
		List<AphiEmpTargetData> auditlist = aphiEmpTargetDataMapper.getEmpTargetValue(entityMap);
		
		if (auditlist.size() > 0) {
			
			return "{\"error\":\"该月指标已审核，不能进行生成\"}";
			
		}
		
		String rbtnl = (String) entityMap.get("rbtnl");
		
		if ("all".equals(rbtnl)) {
			
			try {
				
				aphiEmpTargetDataMapper.deleteEmpTargetData(entityMap);
				
				List<AphiEmpTargetData> targetlist = aphiEmpTargetDataMapper.queryTargetData(entityMap);// 查询所有指标
				
				List<AphiEmpTargetData> deptKindList = aphiEmpTargetDataMapper.queryEmp(entityMap);// 查询所有科室分类
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				for (AphiEmpTargetData dktdt : targetlist) {
					
					for (AphiEmpTargetData dktdd : deptKindList) {
						
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("group_id", entityMap.get("group_id"));
						
						map.put("hos_id", entityMap.get("hos_id"));
						
						map.put("copy_code", entityMap.get("copy_code"));
						
						map.put("acct_year", entityMap.get("acct_year"));
						
						map.put("acct_month", entityMap.get("acct_month"));
						
						map.put("target_code", dktdt.getTarget_code());
						
						map.put("emp_code", dktdd.getEmp_code());
						
						map.put("target_value", 0);
						map.put("is_audit", 0);
						map.put("user_code", "");
						map.put("audit_time", "");
						
						list.add(map);
						
						// aphiEmpTargetDataMapper.addEmpTargetData(map);
					}
					
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiEmpTargetDataMapper.addBatchEmpTargetData(batchList);
							
							batchList.removeAll(batchList);
						}
						
					}
					
					aphiEmpTargetDataMapper.addBatchEmpTargetData(batchList);
					
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
				
				List<AphiEmpTargetData> deptKindTargetDataList = aphiEmpTargetDataMapper.queryEmpTargetData(entityMap);// 查询所有数据
				
				for (AphiEmpTargetData dktd : deptKindTargetDataList) {
					
					map.put(dktd.getTarget_code() + dktd.getDept_code(), dktd.getTarget_code() + dktd.getDept_code());
					
				}
				
				List<AphiEmpTargetData> targetlist = aphiEmpTargetDataMapper.queryTargetData(entityMap);// 查询所有指标
				
				List<AphiEmpTargetData> deptKindList = aphiEmpTargetDataMapper.queryEmp(entityMap);// 查询所有科室分类
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				for (AphiEmpTargetData dktdt : targetlist) {
					
					for (AphiEmpTargetData dktdd : deptKindList) {
						
						if (map.get(dktdt.getTarget_code() + dktdd.getDept_code()) == null) {
							
							Map<String, Object> incrementMap = new HashMap<String, Object>();
							
							incrementMap.put("group_id", entityMap.get("group_id"));
							
							incrementMap.put("hos_id", entityMap.get("hos_id"));
							
							incrementMap.put("copy_code", entityMap.get("copy_code"));
							
							incrementMap.put("acct_year", entityMap.get("acct_year"));
							
							incrementMap.put("acct_month", entityMap.get("acct_month"));
							
							incrementMap.put("target_code", dktdt.getTarget_code());
							
							incrementMap.put("emp_code", dktdd.getEmp_code());
							
							incrementMap.put("target_value", 0);
							incrementMap.put("is_audit", 0);
							incrementMap.put("user_code", "");
							incrementMap.put("audit_time", "");
							
							list.add(incrementMap);
							
						}
						
					}
					
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiEmpTargetDataMapper.addBatchEmpTargetData(batchList);
							
							batchList.removeAll(batchList);
						}
						
					}
					
					aphiEmpTargetDataMapper.addBatchEmpTargetData(batchList);
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
					
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptKindTargetData\"}";
				
			}
			
		} else {
			
			try {
				
				deleteBatchEmpTargetData(entityMap);
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> inheritMap = new HashMap<String, Object>();
				
				inheritMap.put("comp_code", entityMap.get("comp_code"));
				
				inheritMap.put("copy_code", entityMap.get("copy_code"));
				
				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));
				
				inheritMap.put("acct_year", preYearMonth.substring(0, 4));
				
				inheritMap.put("acct_month", preYearMonth.substring(4, 6));
				
				List<AphiEmpTargetData> deptKindTargetDataList = aphiEmpTargetDataMapper.queryEmpTargetData(inheritMap);// 查询所有数据
				
				Map<String, String> deptKindTargetDataMap = new HashMap<String, String>();
				
				for (AphiEmpTargetData dktd : deptKindTargetDataList) {
					
					Map<String, Object> incrementMap = new HashMap<String, Object>();
					
					incrementMap.put("group_id", entityMap.get("group_id"));
					
					incrementMap.put("hos_id", entityMap.get("hos_id"));
					
					incrementMap.put("copy_code", entityMap.get("copy_code"));
					
					incrementMap.put("acct_year", entityMap.get("acct_year"));
					
					incrementMap.put("acct_month", entityMap.get("acct_month"));
					
					incrementMap.put("target_code", dktd.getTarget_code());
					
					incrementMap.put("emp_code", dktd.getDept_code());
					
					incrementMap.put("target_value", 0);
					incrementMap.put("is_audit", 0);
					incrementMap.put("user_code", "");
					incrementMap.put("audit_time", "");
					
					list.add(incrementMap);
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiEmpTargetDataMapper.addBatchEmpTargetData(batchList);
							
							batchList.removeAll(batchList);
						}
						
					}
					
					aphiEmpTargetDataMapper.addBatchEmpTargetData(batchList);
					
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
		
		return aphiEmpTargetDataMapper.getTargetCode(entityMap);
		
	}
	
	@Override
	public String queryEmpTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<AphiEmpTargetData> list = aphiEmpTargetDataMapper.queryEmpTargetData(entityMap);
		if (list.size() > 0) {
			return "{\"msg\":\"\",\"state\":\"false\"}";
		} else {
			return "{\"msg\":\"\",\"state\":\"true\"}";
		}
	}
	
	@Override
	public String deleteBatchEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiEmpTargetDataMapper.deleteEmpTargetData(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"\",\"state\":\"false\"}";
			
		}
	}
	
	@Override
	public String shenhe(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			aphiEmpTargetDataMapper.shenhe(entityList);
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"审核失败.\",\"state\":\"false\"}");
		}
	}
	
	@Override
	public String addBatchEmpTargetData(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiEmpTargetDataMapper.addBatchEmpTargetData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
		}
	}
	
	@Override
	public String getEmpTargetValue(Map<String, Object> entityMap) throws DataAccessException {
		
		String year_month = (String) entityMap.get("acct_yearm");
		
		String acct_year = year_month.substring(0, 4);
		
		String acct_month = year_month.substring(4, 6);
		
		entityMap.put("acct_year", acct_year);
		
		entityMap.put("acct_month", acct_month);
		
		List<AphiEmpTargetData> list = aphiEmpTargetDataMapper.getEmpTargetValue(entityMap);
		
		if (list.size() > 0) {
			
			return "{\"state\":\"true\"}";
			
		}
		
		return "{\"state\":\"false\"}";
	}
	
	@Override
	public String importEmpTargetData(Map<String, Object> map) throws DataAccessException {
		
		try {
			
			String content = map.get("content").toString();
			
			List<Map<String, List<String>>> allDataList = SpreadTableJSUtil.toListMap(content, 1);
			
			if (allDataList == null || allDataList.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			// 查询 职工字典
			List<AphiEmpDict> empList = aphiEmpDictMapper.queryAphiEmpDictList(entityMap);
			
			// 用于存储查询empList中的AphiEmpDict对象,以键值对的形式存储,用于判断职工是否存在
			Map<String, AphiEmpDict> empMap = new HashMap<String, AphiEmpDict>();
			
			// 查询 指标字典
			List<AphiTarget> targetList = aphiTargetMapper.queryPrmTarget(entityMap);
			
			// 用于存储查询empList中的AphiEmpDict对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, AphiTarget> targetMap = new HashMap<String, AphiTarget>();
			
			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			
			// 存储保存数据List
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
			
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			// 将指标List存入Map 键:emp_code 值:AphiEmpDict
			for (AphiEmpDict emp : empList) {
				empMap.put(emp.getEmp_code(), emp);
			}
			
			// 将职工List存入Map 键:targetCode 值:AphiTarget
			for (AphiTarget target : targetList) {
				targetMap.put(target.getTarget_code(), target);
			}
			
			// 遍历导入数据
			for (Map<String, List<String>> item : allDataList) {
				
				List<String> acct_year = item.get("核算年度");
				List<String> acct_month = item.get("核算月份");
				List<String> emp_code = item.get("职工编码");
				List<String> target_code = item.get("指标编码");
				List<String> target_value = item.get("指标值");
				
				// 判断空行 此处判断不严谨,未全面测试,此处判断只是测试中看到的一种情况
				if (acct_year == null && acct_month.get(1) == null && emp_code.get(1) == null) {
					break;
				}
				
				if (acct_year.get(1) == null) {
					return "{\"warn\":\"核算年度为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_year.get(0) + "\"}";
				}
				
				if (acct_month.get(1) == null) {
					return "{\"warn\":\"核算月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
				}
				
				if (emp_code.get(1) == null) {
					return "{\"warn\":\"职工编码为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
				} else {
					if (empMap.get(emp_code.get(1)) == null) {
						return "{\"warn\":\"职工编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
					}
				}
				
				if (target_code.get(1) == null) {
					return "{\"warn\":\"指标编码为空！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
				} else {
					if (targetMap.get(target_code.get(1)) == null) {
						return "{\"warn\":\"指标编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
					}
				}
				
				if (target_value.get(1) == null) {
					return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\"" + target_value.get(0) + "\"}";
				}
				
				// 以核算年度+核算月份+职工编码+指标编码为键值,判断导入数据是否重复
				String key = acct_year.get(1) + acct_month.get(1) + emp_code.get(1) + target_code.get(1);
				if (exitMap.get(key) != null) {
					err_sb.append(acct_year.get(1) + "核算年度," + acct_month.get(1) + "核算月份," + emp_code.get(1) + "职工编码," + target_code.get(1) + "指标编码 ");
				} else {
					exitMap.put(key, key);
				}
				
				// 添加数据Map
				Map<String, Object> addMap = new HashMap<String, Object>();
				addMap.put("group_id", SessionManager.getGroupId());
				addMap.put("hos_id", SessionManager.getHosId());
				addMap.put("copy_code", SessionManager.getCopyCode());
				addMap.put("acct_year", acct_year.get(1));
				addMap.put("acct_month", acct_month.get(1));
				addMap.put("emp_id", empMap.get(emp_code.get(1)).getEmp_id());
				addMap.put("emp_no", empMap.get(emp_code.get(1)).getEmp_no());
				addMap.put("emp_code", emp_code.get(1));
				addMap.put("target_code", target_code.get(1));
				addMap.put("target_value", Double.parseDouble(target_value.get(1)));
				addMap.put("is_audit", 0);
				addMap.put("user_code", "");
				addMap.put("audit_time", "");
				
				addList.add(addMap);
				
			}
			
			if (err_sb.length() > 0) {// 重复数据是否存在
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {
				
				aphiEmpTargetDataMapper.addBatchEmpTargetData(addList);
				
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			
		} catch (NumberFormatException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"指标值格式错误 \"}");
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}
	
	@Override
	public List<Map<String, Object>> queryEmpTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}		
		if (MyConfig.getSysPara("09001") == null) {
			entityMap.put("para_value", 0);
		} else {
			entityMap.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		if (!"".equals(entityMap.get("acct_yearm")) && entityMap.get("acct_yearm") != null) {
			
			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);
			
			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
			
		}
		
		List<Map<String, Object>> list = aphiEmpTargetDataMapper.queryEmpTargetDataPrint(entityMap);
		
		return list;
	}
}

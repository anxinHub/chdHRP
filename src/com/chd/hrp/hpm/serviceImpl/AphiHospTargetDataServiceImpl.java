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
import com.chd.hrp.hpm.dao.AphiHospTargetDataMapper;
import com.chd.hrp.hpm.entity.AphiHospTargetData;
import com.chd.hrp.hpm.service.AphiHospTargetDataService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiHospTargetDataService")
public class AphiHospTargetDataServiceImpl implements AphiHospTargetDataService {
	
	private static Logger logger = Logger.getLogger(AphiHospTargetDataServiceImpl.class);
	
	@Resource(name = "aphiHospTargetDataMapper")
	private final AphiHospTargetDataMapper aphiHospTargetDataMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addHospTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			// AphiDeptBonusAudit dba =
			// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
			//
			// if(dba != null){
			//
			// if(dba.getIs_audit() == 1){
			//
			// return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
			//
			// }
			// if(dba.getIs_grant()== 1){
			//
			// return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
			//
			// }
			//
			// }
			
			aphiHospTargetDataMapper.addHospTargetData(entityMap);
			
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
	public String queryHospTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiHospTargetDataMapper.queryHospTargetData(entityMap, rowBounds), sysPage.getTotal());
		
	}
	
	/**
	 * 
	 */
	@Override
	public AphiHospTargetData queryHospTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiHospTargetDataMapper.queryHospTargetDataByCode(entityMap);
		
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteHospTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException {
		
		if (codes.length() > 0) {
			
			String code = codes.split(",")[0];
			
			String[] code_array = code.split(";");
			
			String acct_year_month = code_array[1];
			
			entityMap.put("acct_year", acct_year_month.substring(0, 4));
			
			entityMap.put("acct_month", acct_year_month.substring(4, 6));
			
			List<AphiHospTargetData> auditlist = aphiHospTargetDataMapper.getHospTargetValue(entityMap);
			
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
		
		try {
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_codeArray = codes.split(",");
				
				for (String code : code_codeArray) {
					
					String[] code_array = code.split(";");
					
					entityMap.put("target_code", code_array[0]);
					
					String acct_year_month = code_array[1];
					
					entityMap.put("acct_year", acct_year_month.substring(0, 4));
					
					entityMap.put("acct_month", acct_year_month.substring(4, 6));
					
					aphiHospTargetDataMapper.deleteHospTargetData(entityMap);
					
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
	public String updateHospTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
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
			
			aphiHospTargetDataMapper.updateHospTargetData(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}";
			
		}
		
	}
	
	@Override
	public String initHospTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
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
		
		List<AphiHospTargetData> auditlist = aphiHospTargetDataMapper.getHospTargetValue(entityMap);
		
		if (auditlist.size() > 0) {
			
			return "{\"error\":\"该月指标已审核，不能进行生成\"}";
			
		}
		
		String rbtnl = (String) entityMap.get("rbtnl");
		
		if ("all".equals(rbtnl)) {
			
			try {
				
				deleteBatchHospTargetData(entityMap);
				
				List<AphiHospTargetData> targetList = aphiHospTargetDataMapper.queryTargetData(entityMap);
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				for (AphiHospTargetData htd : targetList) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", entityMap.get("group_id"));
					
					map.put("hos_id", entityMap.get("hos_id"));
					
					map.put("copy_code", entityMap.get("copy_code"));
					
					map.put("acct_year", entityMap.get("acct_year"));
					
					map.put("acct_month", entityMap.get("acct_month"));
					
					map.put("target_code", htd.getTarget_code());
					
					map.put("target_value", 0);
					
					map.put("is_audit", 0);
					
					map.put("user_code", "");
					
					map.put("audit_time", "");
					
					list.add(map);
					
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiHospTargetDataMapper.addBatchHospTargetData(list);
							
							batchList.removeAll(batchList);
						}
						
					}
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
					
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initHospTargetData\"}";
			}
			
		} else if ("check".equals(rbtnl)) {
			
			try {
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				List<AphiHospTargetData> hospList = aphiHospTargetDataMapper.queryHospTargetData(entityMap);
				
				Map<String, String> hospMap = new HashMap<String, String>();
				
				for (AphiHospTargetData htd : hospList) {
					
					hospMap.put(htd.getTarget_code(), htd.getTarget_code());
					
				}
				
				List<AphiHospTargetData> targetList = aphiHospTargetDataMapper.queryTargetData(entityMap);
				
				for (AphiHospTargetData htd : targetList) {
					
					if (hospMap.get(htd.getTarget_code()) == null) {
						
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("group_id", entityMap.get("group_id"));
						
						map.put("hos_id", entityMap.get("hos_id"));
						
						map.put("copy_code", entityMap.get("copy_code"));
						
						map.put("acct_year", entityMap.get("acct_year"));
						
						map.put("acct_month", entityMap.get("acct_month"));
						
						map.put("target_code", htd.getTarget_code());
						
						map.put("target_value", 0);
						
						map.put("is_audit", 0);
						
						map.put("user_code", "");
						
						map.put("audit_time", "");
						
						list.add(map);
						
					}
					
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiHospTargetDataMapper.addBatchHospTargetData(list);
							
							batchList.removeAll(batchList);
						}
						
					}
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
					
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initHospTargetData\"}";
				
			}
			
		} else {// 继承数据
		
			try {
				
				deleteBatchHospTargetData(entityMap);// 首先清空本月数据
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> inheritMap = new HashMap<String, Object>();
				
				inheritMap.put("group_id", entityMap.get("group_id"));
				
				inheritMap.put("hos_id", entityMap.get("hos_id"));
				
				inheritMap.put("copy_code", entityMap.get("copy_code"));
				
				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));
				
				inheritMap.put("acct_year", preYearMonth.substring(0, 4));
				
				inheritMap.put("acct_month", preYearMonth.substring(4, 6));
				
				List<AphiHospTargetData> hospList = aphiHospTargetDataMapper.queryHospTargetData(inheritMap);
				
				// Map<String, String> hospMap = new HashMap<String, String>();
				
				for (AphiHospTargetData htd : hospList) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", entityMap.get("group_id"));
					
					map.put("hos_id", entityMap.get("hos_id"));
					
					map.put("copy_code", entityMap.get("copy_code"));
					
					map.put("acct_year", entityMap.get("acct_year"));
					
					map.put("acct_month", entityMap.get("acct_month"));
					
					map.put("target_code", htd.getTarget_code());
					
					map.put("target_value", htd.getTarget_value());
					
					map.put("is_audit", 0);
					
					map.put("user_code", "");
					
					map.put("audit_time", "");
					
					list.add(map);
					
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiHospTargetDataMapper.addBatchHospTargetData(list);
							
							batchList.removeAll(batchList);
						}
						
					}
					
					return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
					
				}
				
				return "{\"msg\":\"没有继承数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  initHospTargetData\"}";
				
			}
			
		}
		
	}
	
	@Override
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiHospTargetDataMapper.getTargetCode(entityMap);
	}
	
	@Override
	public String queryHospTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException {
		
		List<AphiHospTargetData> list = aphiHospTargetDataMapper.queryHospTargetDataByDay(entityMap);
		if (list.size() > 0) {
			return "{\"msg\":\"\",\"state\":\"false\"}";
		} else {
			return "{\"msg\":\"\",\"state\":\"true\"}";
		}
	}
	
	@Override
	public String deleteBatchHospTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiHospTargetDataMapper.deleteHospTargetData(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"\",\"state\":\"false\"}";
			
		}
	}
	
	@Override
	public String shenhe(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiHospTargetDataMapper.shenhe(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"审核失败.\",\"state\":\"false\"}";
			
		}
	}
	
	@Override
	public String addBatchHospTargetData(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		int state = aphiHospTargetDataMapper.addBatchHospTargetData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
		}
	}
	
	@Override
	public List<Map<String, Object>> queryHospTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if (!"".equals(entityMap.get("acct_yearm")) && entityMap.get("acct_yearm") != null) {
			
			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);
			
			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
			
		}
		
		List<Map<String, Object>> list = aphiHospTargetDataMapper.queryHospTargetDataPrint(entityMap);
		return list;
	}
	
}

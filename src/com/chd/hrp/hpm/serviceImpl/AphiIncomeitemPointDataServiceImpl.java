package com.chd.hrp.hpm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiDeptTargetDataMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPointDataMapper;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
import com.chd.hrp.hpm.entity.AphiIncomeitemPoint;
import com.chd.hrp.hpm.entity.AphiIncomeitemPointData;
import com.chd.hrp.hpm.entity.AphiPointValue;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiIncomeitemPointDataService;

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

@Service("aphiIncomeitemPointDataService")
public class AphiIncomeitemPointDataServiceImpl implements AphiIncomeitemPointDataService {
	
	private static Logger logger = Logger.getLogger(AphiIncomeitemPointDataServiceImpl.class);
	
	@Resource(name = "aphiIncomeitemPointDataMapper")
	private final AphiIncomeitemPointDataMapper aphiIncomeitemPointDataMapper = null;
	
	@Resource(name = "aphiDeptTargetDataMapper")
	private final AphiDeptTargetDataMapper aphiDeptTargetDataMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
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
			
			aphiIncomeitemPointDataMapper.addIncomeitemPointData(entityMap);
			
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
	public String queryIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		AphiSchemeConf sc = aphiIncomeitemPointDataMapper.getSchemeSeqNo(entityMap);// 通过年月获得scheme_seq_no
		
		if (sc != null) {
			
			entityMap.put("scheme_seq_no", sc.getScheme_seq_no());
			
		}
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiIncomeitemPointDataMapper.queryIncomeitemPointData(entityMap, rowBounds), sysPage.getTotal());
	}
	
	/**
	 * 
	 */
	@Override
	public AphiIncomeitemPointData queryIncomeitemPointDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiIncomeitemPointDataMapper.queryIncomeitemPointDataByCode(entityMap);
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteIncomeitemPointData(Map<String, Object> entityMap, String codes) throws DataAccessException {
		
		try {
			
			if (codes.length() > 0) {
				
				String code = codes.split(",")[0];
				
				String[] code_array = code.split(";");
				
				// entityMap.put("dept_code", code_array[0]);
				
				// entityMap.put("income_item_code", code_array[1]);
				
				entityMap.put("acct_year", code_array[2]);
				
				entityMap.put("acct_month", code_array[3]);
				
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
					
					entityMap.put("dept_code", code_array[0]);
					
					entityMap.put("income_item_code", code_array[1]);
					
					entityMap.put("acct_year", code_array[2]);
					
					entityMap.put("acct_month", code_array[3]);
					
					aphiIncomeitemPointDataMapper.deleteIncomeitemPointData(entityMap);
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
	public String updateIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException {
		
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
			
			aphiIncomeitemPointDataMapper.updateIncomeitemPointData(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}";
			
		}
	}
	
	@Override
	public String createIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException {
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
		
		String rbtnl = (String) entityMap.get("rbtnl");
		
		if ("all".equals(rbtnl)) {
			
			try {
				
				deleteIncomeitemPointData(entityMap);
				
				List<AphiIncomeitemPointData> pointList = aphiIncomeitemPointDataMapper.queryIncomeitemPointDept(entityMap);
				
				List<AphiDeptTargetData> targetList = aphiDeptTargetDataMapper.queryDept(entityMap);
				
				if (pointList.size() > 0 && targetList.size() > 0) {
					
					for (int i = 0; i < targetList.size(); i++) {
						
						AphiDeptTargetData deptTargetData = targetList.get(i);
						
						entityMap.put("dept_id", deptTargetData.getDept_id());
						
						for (int j = 0; j < pointList.size(); j++) {
							
							AphiIncomeitemPointData incomeitemPointData = pointList.get(j);
							
							entityMap.put("income_item_code", incomeitemPointData.getIncome_item_code());
							
							aphiIncomeitemPointDataMapper.addIncomeitemPointData(entityMap);
							
						}
						
					}
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
					
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptTargetData\"}";
			}
			
		} else if ("check".equals(rbtnl)) {
			
			try {
				
				List<AphiIncomeitemPointData> listPointData = aphiIncomeitemPointDataMapper.queryIncomeitemPointData(entityMap);
				
				Map<String, String> mapPointData = new HashMap<String, String>();
				
				for (AphiIncomeitemPointData ipd : listPointData) {
					
					mapPointData.put(ipd.getDept_id() + ipd.getIncome_item_code(), ipd.getDept_id() + ipd.getIncome_item_code());
					
				}
				
				List<AphiIncomeitemPointData> list = aphiIncomeitemPointDataMapper.queryIncomeitemPointDept(entityMap);
				
				List<AphiDeptTargetData> targetList = aphiDeptTargetDataMapper.queryDept(entityMap);
				
				if (list.size() > 0 && targetList.size() > 0) {
					
					for (int i = 0; i < targetList.size(); i++) {
						
						AphiDeptTargetData deptTargetData = targetList.get(i);
						
						entityMap.put("dept_id", deptTargetData.getDept_id());
						
						for (int j = 0; j < list.size(); j++) {
							
							AphiIncomeitemPointData incomeitemPointData = list.get(j);
							
							if (mapPointData.get(deptTargetData.getDept_id() + incomeitemPointData.getIncome_item_code()) == null) {
								
								entityMap.put("income_item_code", incomeitemPointData.getIncome_item_code());
								
								aphiIncomeitemPointDataMapper.addIncomeitemPointData(entityMap);
								
							}
							
						}
						
					}
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
					
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptTargetData\"}";
				
			}
			
		} else {
			
			try {
				
				deleteIncomeitemPointData(entityMap);
				
				Map<String, Object> inheritMap = new HashMap<String, Object>();
				
				inheritMap.put("comp_code", entityMap.get("comp_code"));
				
				inheritMap.put("copy_code", entityMap.get("copy_code"));
				
				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));
				
				inheritMap.put("acct_year", preYearMonth.substring(0, 4));
				
				inheritMap.put("acct_month", preYearMonth.substring(4, 6));
				
				inheritMap.put("user_id", SessionManager.getUserId());
				
				List<AphiIncomeitemPointData> list = aphiIncomeitemPointDataMapper.queryIncomeitemPointData(inheritMap);
				
				if (list.size() > 0) {
					
					for (AphiIncomeitemPointData wc : list) {
						
						entityMap.put("dept_id", wc.getDept_id());
						
						entityMap.put("income_item_code", wc.getIncome_item_code());
						
						entityMap.put("income_item_code", wc.getIncome_item_code());
						
						entityMap.put("work_amount", wc.getWork_amount());
						
						entityMap.put("work_point", wc.getWork_point());
						
						aphiIncomeitemPointDataMapper.addIncomeitemPointData(entityMap);
						
					}
					
					return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";
					
				} else {
					
					return "{\"msg\":\"没有要继承的数据.\",\"state\":\"false\"}";
					
				}
				
			} catch (Exception e) {
				
				return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  initIncomeitemData\"}";
			}
			
		}
		
	}
	
	@Override
	public List<AphiIncomeitemPoint> getIncomeItemPointSeq(Map<String, Object> entityMap) throws DataAccessException {
		return aphiIncomeitemPointDataMapper.getIncomeItemPointSeq(entityMap);
	}
	
	@Override
	public String calculate(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			String paras = (String) entityMap.get("paras");
			
			String acct_year = paras.substring(0, 4);
			
			String acct_month = paras.substring(4, 6);
			
			entityMap.put("acct_year", acct_year);
			
			entityMap.put("acct_month", acct_month);
			
			AphiSchemeConf sc = aphiIncomeitemPointDataMapper.getSchemeSeqNo(entityMap);// 通过年月获得scheme_seq_no
			
			entityMap.put("scheme_seq_no", sc.getScheme_seq_no());
			
			AphiPointValue pointValue = aphiIncomeitemPointDataMapper.getPointValueSeq(entityMap);// 根据科室编码,项目编码和方案序列获得权重
			
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			
			List<AphiIncomeitemPointData> list = aphiIncomeitemPointDataMapper.queryIncomeitemPointData(entityMap);
			
			long work_point = 0;
			
			for (int i = 0; i < list.size(); i++) {
				
				AphiIncomeitemPointData ipd = list.get(i);
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("comp_code", entityMap.get("comp_code"));
				
				map.put("copy_code", entityMap.get("copy_code"));
				
				map.put("acct_year", entityMap.get("acct_year"));
				
				map.put("acct_month", entityMap.get("acct_month"));
				
				map.put("dept_id", ipd.getDept_id());
				
				map.put("income_item_code", ipd.getIncome_item_code());
				
				work_point = (long) (ipd.getWork_amount() * ipd.getImcome_point() * pointValue.getPoint_value());// 计算order_ret_money
				
				map.put("work_point", work_point);
				
				aphiIncomeitemPointDataMapper.calculate(map);
				
			}
			
			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			return "{\"error\":\"计算失败 数据库异常 请联系管理员! 错误编码  calculate\"}";
		}
		
		// int state = aphiIncomeitemPointDataMapper.calculate(entityMap);
		// if (state > 0) {
		// return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
		// } else {
		// return "{\"msg\":\"计算失败.\",\"state\":\"false\"}";
		// }
	}
	
	@Override
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException {
		return aphiIncomeitemPointDataMapper.getSchemeSeqNo(entityMap);
	}
	
	@Override
	public AphiIncomeitemPoint getIncomeItemPointSeqByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiIncomeitemPointDataMapper.getIncomeItemPointSeqByCode(entityMap);
	}
	
	@Override
	public AphiPointValue getPointValueSeq(Map<String, Object> entityMap) throws DataAccessException {
		return aphiIncomeitemPointDataMapper.getPointValueSeq(entityMap);
	}
	
	@Override
	public String deleteIncomeitemPointData(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiIncomeitemPointDataMapper.deleteIncomeitemPointData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
	}
	
}

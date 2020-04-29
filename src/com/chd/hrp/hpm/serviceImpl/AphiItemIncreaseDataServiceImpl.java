package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiItemIncreaseDataMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiItemIncreaseData;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiItemIncreaseDataService;

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

@Service("aphiItemIncreaseDataService")
public class AphiItemIncreaseDataServiceImpl implements AphiItemIncreaseDataService {
	
	private static Logger logger = Logger.getLogger(AphiItemIncreaseDataServiceImpl.class);
	
	@Resource(name = "aphiItemIncreaseDataMapper")
	private final AphiItemIncreaseDataMapper aphiItemIncreaseDataMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiItemIncreaseDataMapper.addItemIncreaseData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String queryItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiItemIncreaseDataMapper.queryItemIncreaseData(entityMap, rowBounds), sysPage.getTotal());
	}
	
	/**
	 * 
	 */
	@Override
	public AphiItemIncreaseData queryItemIncreaseDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiItemIncreaseDataMapper.queryItemIncreaseDataByCode(entityMap);
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteItemIncreaseData(Map<String, Object> entityMap, String codes) throws DataAccessException {
		
		try {
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_codeArray = codes.split(",");
				
				for (String code : code_codeArray) {
					
					String[] code_array = code.split(";");
					
					entityMap.put("item_code", code_array[0]);
					
					entityMap.put("acct_year", code_array[1]);
					
					entityMap.put("acct_month", code_array[2]);
					
					// AphiDeptBonusAudit deptBonusAudit =
					// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
					// if(deptBonusAudit !=null){
					//
					// if( deptBonusAudit.getIs_audit()== 1){
					//
					// return
					// "{\"msg\":\"本月奖金已审核后。数据不能删除.\",\"state\":\"true\"}";
					//
					// }
					// }
					
					aphiItemIncreaseDataMapper.deleteItemIncreaseData(entityMap);
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
	public String updateItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException {
		
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
		// }
		// }
		try {
			
			aphiItemIncreaseDataMapper.updateItemIncreaseData(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}";
			
		}
	}
	
	@Override
	public String initItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException {
		
		String codes = (String) entityMap.get("checkIds");
		
		String[] code_array = codes.split(",");
		
		String year_month = code_array[0];
		
		String acct_year = year_month.substring(0, 4);
		
		String acct_month = year_month.substring(4, 6);
		
		entityMap.put("acct_year", acct_year);
		
		entityMap.put("acct_month", acct_month);
		
		if (code_array.length > 1) {
			
			String item_code = code_array[1];
			
			entityMap.put("item_code", item_code);
			
		}
		
		String rbtnl = (String) entityMap.get("rbtnl");
		
		if ("all".equals(rbtnl)) {
			
			try {
				
				deleteItemIncreaseData(entityMap);
				
				List<AphiItemIncreaseData> incomeList = aphiItemIncreaseDataMapper.queryItemIncreaseDept(entityMap);
				
				if (incomeList.size() > 0) {
					
					List<AphiItemIncreaseData> lastMoneyList = aphiItemIncreaseDataMapper.queryItemIncreaseLastMoney(entityMap);
					
					AphiItemIncreaseData itemIncreaseData = lastMoneyList.get(0);
					
					if (!("").equals(itemIncreaseData.getLast_money())) {
						
						entityMap.put("last_money", itemIncreaseData.getLast_money());
						
					} else {
						
						entityMap.put("last_money", 0);
						
					}
					
					aphiItemIncreaseDataMapper.addBatchItemIncreaseData(incomeList);
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initItemIncreaseData\"}";
			}
			
		} else {
			
			try {
				
				entityMap.put("sql", "select item_code from aphi_item_increase_data ahtd where ahtd.acct_month=#{acct_month} and ahtd.acct_year=#{acct_year}");
				
				List<AphiItemIncreaseData> list = aphiItemIncreaseDataMapper.queryItemIncreaseDept(entityMap);
				
				if (list.size() > 0) {
					
					List<AphiItemIncreaseData> lastMoneyList = aphiItemIncreaseDataMapper.queryItemIncreaseLastMoney(entityMap);
					
					AphiItemIncreaseData itemIncreaseData = lastMoneyList.get(0);
					
					if (!("").equals(itemIncreaseData.getLast_money())) {
						
						entityMap.put("last_money", itemIncreaseData.getLast_money());
						
					} else {
						
						entityMap.put("last_money", 0);
						
					}
					
					aphiItemIncreaseDataMapper.addBatchItemIncreaseData(list);
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
					
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initItemIncreaseData\"}";
				
			}
			
		}
		
	}
	
	@Override
	public List<AphiItemIncreasePercConf> getItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException {
		return aphiItemIncreaseDataMapper.getItemIncreasePercConf(entityMap);
	}
	
	@Override
	public List<AphiItemIncreaseData> queryItemIncreaseDataByDay(Map<String, Object> entityMap) throws DataAccessException {
		return aphiItemIncreaseDataMapper.queryItemIncreaseDataByDay(entityMap);
	}
	
	@Override
	public List<AphiDeptBonusData> queryDeptBonusData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiItemIncreaseDataMapper.queryDeptBonusData(entityMap);
	}
	
	@Override
	public String calculate(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiItemIncreaseDataMapper.calculate(entityMap);
		if (state > 0) {
			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"计算失败.\",\"state\":\"false\"}";
		}
	}
	
	@Override
	public AphiItemIncreasePercConf getItemIncreasePercConfByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiItemIncreaseDataMapper.getItemIncreasePercConfByCode(entityMap);
	}
	
	@Override
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException {
		return aphiItemIncreaseDataMapper.getSchemeSeqNo(entityMap);
	}
	
	@Override
	public String addBatchItemIncreaseData(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiItemIncreaseDataMapper.addBatchItemIncreaseData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
		}
	}
	
	@Override
	public String getItemIncreaseDataValue(Map<String, Object> entityMap) throws DataAccessException {
		
		String acct_year_month = (String) entityMap.get("acct_yearm");
		
		entityMap.put("acct_year", acct_year_month.substring(0, 4));
		
		entityMap.put("acct_month", acct_year_month.substring(4, 6));
		
		// List<AphiDeptBonusAudit> deptBonusAuditList =
		// aphiDeptBonusAuditMapper.deptBonusIsAudit(entityMap);
		//
		// if(deptBonusAuditList.size()>0){
		//
		// return "{\"state\":\"true\"}";
		//
		// }else{
		//
		// return "{\"state\":\"false\"}";
		//
		// }
		return "{\"state\":\"true\"}";
	}
	
	@Override
	public String deleteItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiItemIncreaseDataMapper.deleteItemIncreaseData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
	}
}

package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiCostitemIncreaseDataMapper;
import com.chd.hrp.hpm.entity.AphiCostitemIncreaseData;
import com.chd.hrp.hpm.entity.AphiCostitemPerc;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiCostitemIncreaseDataService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("aphiCostitemIncreaseDataService")
public class AphiCostitemIncreaseDataServiceImpl implements AphiCostitemIncreaseDataService {

	private static Logger logger = Logger.getLogger(AphiCostitemIncreaseDataServiceImpl.class);
	
	@Resource(name = "aphiCostitemIncreaseDataMapper")
	private final AphiCostitemIncreaseDataMapper aphiCostitemIncreaseDataMapper = null;
	/**
	 * 
	 */
	@Override
	public String addCostitemIncreaseData(Map<String,Object> entityMap)throws DataAccessException{
	    int state = aphiCostitemIncreaseDataMapper.addCostitemIncreaseData(entityMap);
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
	public String queryCostitemIncreaseData(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiCostitemIncreaseDataMapper.queryCostitemIncreaseData(entityMap, rowBounds), sysPage.getTotal());
	}
	
	/**
	 * 
	 */
	@Override
	public AphiCostitemIncreaseData queryCostitemIncreaseDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiCostitemIncreaseDataMapper.queryCostitemIncreaseDataByCode(entityMap);
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteCostitemIncreaseData(Map<String,Object> entityMap,String codes)throws DataAccessException{

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");

					entityMap.put("cost_item_code", code_array[0]);
					
					entityMap.put("acct_year", code_array[1]);
					
					entityMap.put("acct_month", code_array[2]);
					
//					AphiDeptBonusAudit  deptBonusAudit = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//					 if(deptBonusAudit !=null){
//						 
//						 if( deptBonusAudit.getIs_audit()== 1){
//							 
//							 return "{\"msg\":\"本月奖金已审核后。数据不能删除.\",\"state\":\"true\"}";
//							 
//						 }
//					 }
					
					
					 aphiCostitemIncreaseDataMapper.deleteCostitemIncreaseData(entityMap);
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
	public String updateCostitemIncreaseData(Map<String,Object> entityMap)throws DataAccessException{
		int state = aphiCostitemIncreaseDataMapper.updateCostitemIncreaseData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String initCostitemIncreaseData(Map<String, Object> entityMap)
			throws DataAccessException {

		String codes= (String)entityMap.get("checkIds");
		
		String[] code_array = codes.split(",");
		
		String year_month = code_array[0];
		
		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);
		
		if(code_array.length>1){
			
			String cost_item_code = code_array[1];
			
			entityMap.put("cost_item_code", cost_item_code);
			
		}
		
		String rbtnl = (String)entityMap.get("rbtnl");

		if("all".equals(rbtnl)){
			
			try {
				
				deleteCostitemIncreaseData(entityMap);
				
				List<AphiCostitemIncreaseData> incomeList =aphiCostitemIncreaseDataMapper.queryCostitemIncreaseData(entityMap);
				
				if(incomeList.size()>0){
					aphiCostitemIncreaseDataMapper.addBatchCostitemIncreaseData(incomeList);
					
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				}
				
				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				
			} catch (Exception e) {
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initCostitemIncreaseData\"}";
			}
			
		}else{
			
			try {
				
				entityMap.put("sql", "select cost_item_code from Aphi_CostItem_increase_data ahtd where ahtd.acct_month=#{acct_month} and ahtd.acct_year=#{acct_year}");
				
				
				List<AphiCostitemIncreaseData> list = aphiCostitemIncreaseDataMapper.queryCostitemIncreaseData(entityMap);
				
					if(list.size()>0){
						
						aphiCostitemIncreaseDataMapper.addBatchCostitemIncreaseData(list);
						
						return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
						
					}
					
					return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
			
			} catch (Exception e) {
				
				return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initCostitemIncreaseData\"}";
				
			}
			
		}

	}

	@Override
	public List<AphiCostitemPerc> getCostItemPercSeq(Map<String, Object> entityMap)
			throws DataAccessException {
		return aphiCostitemIncreaseDataMapper.getCostItemPercSeq(entityMap);
	}

	@Override
	public String calculate(Map<String, Object> entityMap)
			throws DataAccessException {
		int state = aphiCostitemIncreaseDataMapper.calculate(entityMap);
		if (state > 0) {
			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"计算失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap)
			throws DataAccessException {
		return aphiCostitemIncreaseDataMapper.getSchemeSeqNo(entityMap);
	}

	@Override
	public AphiCostitemPerc getCostItemPercSeqByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return aphiCostitemIncreaseDataMapper.getCostItemPercSeqByCode(entityMap);
	}

	@Override
	public String addBatchCostitemIncreaseData(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiCostitemIncreaseDataMapper.addBatchCostitemIncreaseData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
		}
	}
	
	@Override
	public String getCostitemIncreaseValue(Map<String, Object> entityMap) throws DataAccessException {

		String acct_year_month = (String)entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_year_month.substring(0, 4));
		
		entityMap.put("acct_month", acct_year_month.substring(4, 6));
		
//		List<AphiDeptBonusAudit> deptBonusAuditList = aphiDeptBonusAuditMapper.deptBonusIsAudit(entityMap);
//		
//		if(deptBonusAuditList.size()>0){
//			
//			return "{\"state\":\"true\"}";
//			
//		}else{
//			
//			return "{\"state\":\"false\"}";
//
//		}
		
		return "{\"state\":\"true\"}";
		
	}

	@Override
	public String deleteCostitemIncreaseData(Map<String, Object> entityMap)
			throws DataAccessException {
		
		int state = aphiCostitemIncreaseDataMapper.deleteCostitemIncreaseData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
	}
}

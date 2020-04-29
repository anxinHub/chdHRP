package com.chd.hrp.hpm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiDeptTargetDataMapper;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
import com.chd.hrp.hpm.service.report.AphiDeptTargetDataReportService;
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

@Service("aphiDeptTargetDataReportService")
public class AphiDeptTargetDataReportServiceImpl implements AphiDeptTargetDataReportService {
	
	@Resource(name = "aphiDeptTargetDataMapper")
	private final AphiDeptTargetDataMapper aphiDeptTargetDataMapper = null;

	@Override
	public String queryDeptTargetDataReport(Map<String, Object> entityMap) throws DataAccessException {
		
//		AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//		
//		if(adba == null || !"1".equals(String.valueOf(adba.getIs_grant()))){
//			
//			return "{\"warn\":\"当前核算年月绩效工资未发放 \"}";
//		}
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

	@Override
	public List<Map<String, Object>> queryDeptTargetDataReportPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (!"".equals(entityMap.get("acct_yearm")) && entityMap.get("acct_yearm") != null) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);

		}
			
		if("false".equals(entityMap.get("is_show_zero"))){//不显示指标值为0 的数据
				
			entityMap.put("is_show_zero", "0");
		}

		List<Map<String, Object>> list = aphiDeptTargetDataMapper.queryDeptTargetDataPrint(entityMap);
		
		return list ; 

	}

}

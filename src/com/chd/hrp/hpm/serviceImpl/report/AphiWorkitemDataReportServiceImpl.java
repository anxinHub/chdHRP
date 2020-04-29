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
import com.chd.hrp.hpm.dao.AphiSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemDataMapper;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.entity.AphiWorkitemData;
import com.chd.hrp.hpm.service.report.AphiWorkitemDataReportService;
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

@Service("aphiWorkitemDataReportService")
public class AphiWorkitemDataReportServiceImpl implements AphiWorkitemDataReportService {
	
	@Resource(name = "aphiSchemeConfMapper")
	private final AphiSchemeConfMapper aphiSchemeConfMapper = null;
	
	@Resource(name = "aphiWorkitemDataMapper")
	private final AphiWorkitemDataMapper aphiWorkitemDataMapper = null;
	

	@Override
	public String queryWorkitemDataReport(Map<String, Object> entityMap) throws DataAccessException {
		
//		AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//		
//		if(adba == null || !"1".equals(String.valueOf(adba.getIs_grant()))){
//			
//			return "{\"warn\":\"当前核算年月绩效工资未发放 \"}";
//		}
		
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		if(sc == null){
			return "{\"warn\":\"当前年月未配置方案\"}";
		}
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiWorkitemData> list = aphiWorkitemDataMapper.queryWorkitemData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiWorkitemData> list = aphiWorkitemDataMapper.queryWorkitemData(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}


	@Override
	public List<Map<String, Object>> queryWorkitemDataReportPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (!"".equals(entityMap.get("acct_yearm")) &&entityMap.get("acct_yearm") != null) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}
		
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		
		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());

		List<Map<String, Object>> list = aphiWorkitemDataMapper.queryWorkitemDataPrint(entityMap);

		return list;

	}

}

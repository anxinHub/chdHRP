package com.chd.hrp.hpm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.hrp.hpm.dao.AphiDistributionBonusComparativeMapper;
import com.chd.hrp.hpm.service.report.AphiDistributionBonusComparativeService;

@Service("aphiDistributionBonusComparativeService")
public class AphiDistributionBonusComparativeServiceImpl implements AphiDistributionBonusComparativeService {
	
	private static Logger logger = Logger.getLogger(AphiDistributionBonusComparativeServiceImpl.class);
	
	@Resource(name="aphiDistributionBonusComparativeMapper")
	private AphiDistributionBonusComparativeMapper  aphiDistributionBonusComparativeMapper = null;
	@Override
	public String queryDistributionBonusComparative(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage=(SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		

		if("02".equals(entityMap.get("nalytical"))){
			
			List deptBonuSDataList = aphiDistributionBonusComparativeMapper.queryDistributionBonusComparativeMapperByNature(entityMap, rowBounds);
			
			StringBuilder json = new StringBuilder();

			json.append("{Rows:[");

			if (deptBonuSDataList != null && deptBonuSDataList.size() > 0) {

				for (int i = 0; i < deptBonuSDataList.size(); i++) {

					Map<String, Object> map = (Map) deptBonuSDataList.get(i);

					json.append("{");

					for (Map.Entry<String, Object> entry : map.entrySet()) {

						if (!"rownum".equals(entry.getKey())) {
							//json.append("acct_year:" + "\"" + entityMap.get("acct_year_end")+entityMap.get("acct_month_end")  + "\",");
							json.append("\"" + entry.getKey() + "\":" + "\"" + entry.getValue() + "\",");

						}

					}

					json.append("},");

				}
				json.setCharAt(json.length() - 1, ']');

				if (sysPage.getTotal() == -1) {

					json.append(",Total:" + deptBonuSDataList.size() + "}");

				} else {

					json.append(",Total:" + sysPage.getTotal() + "}");

				}

			} else {

				json.append("],Total:0}");

			}

			return json.toString();
		
		}else{
			
			List deptBonuSDataList = aphiDistributionBonusComparativeMapper.queryDistributionBonusComparativeMapperByKind(entityMap, rowBounds);
			
			StringBuilder json = new StringBuilder();

			json.append("{Rows:[");

			if (deptBonuSDataList != null && deptBonuSDataList.size() > 0) {

				for (int i = 0; i < deptBonuSDataList.size(); i++) {

					Map<String, Object> map = (Map) deptBonuSDataList.get(i);

					json.append("{");

					for (Map.Entry<String, Object> entry : map.entrySet()) {

						if (!"rownum".equals(entry.getKey())) {
							//json.append("acct_year:" + "\"" + entityMap.get("acct_year_end")+entityMap.get("acct_month_end")  + "\",");
							json.append("\"" + entry.getKey() + "\":" + "\"" + entry.getValue() + "\",");

						}

					}

					json.append("},");

				}
				json.setCharAt(json.length() - 1, ']');

				if (sysPage.getTotal() == -1) {

					json.append(",Total:" + deptBonuSDataList.size() + "}");

				} else {

					json.append(",Total:" + sysPage.getTotal() + "}");

				}

			} else {

				json.append("],Total:0}");

			}

			return json.toString();
			
		}
	}

}

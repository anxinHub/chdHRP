package com.chd.hrp.hr.serviceImpl.record;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.record.HrStatisticConditionMapper;
import com.chd.hrp.hr.entity.sysstruc.HrStoreCondition;
import com.chd.hrp.hr.service.record.HrStatisticConditionService;

@Service("hrStatisticConditionService")
public class HrStatisticConditionServiceImpl implements HrStatisticConditionService {
	
	@Resource(name = "hrStatisticConditionMapper")
	private final HrStatisticConditionMapper hrStatisticConditionMapper = null;

	@Override
	public String queryHrStatisticSetCondition(Map<String, Object> entityMap) throws DataAccessException {
		List<HrStoreCondition> list = hrStatisticConditionMapper.queryHrStatisticSetCondition(entityMap);
	/*	for (Map<String, Object> map : conditions) {
			map.put("field_col_name", map.get("col_value"));
		}
		for (int i = 0; i < conditions.size(); i++) {
			String colNameSql = conditions.get(i).get("FIELD_COL_NAME").toString();
			if(colNameSql==null) {
				continue;
			}
			if(colNameSql.contains("select")) {
				colNameSql = colNameSql.replace("@group_id","'"+SessionManager.getGroupId()+"'" );
				colNameSql = colNameSql.replace("@hos_id","'"+SessionManager.getHosId()+"'" );
				colNameSql = "  select field_col_name from (  "+ colNameSql + "  ) where field_col_code = '"+conditions.get(i).get("col_value") +"'";
				String field_Col_Name=hrStatisticConditionMapper.queryColNam(colNameSql);
				conditions.get(i).put("field_col_name",field_Col_Name);
			}
		}*/
		for (int i = 0; i < list.size(); i++) {
			String colNameSql = list.get(i).getField_col_name();
			if (colNameSql == null) {
				continue;
			}
			if (colNameSql.contains("select")) {
				colNameSql = colNameSql.replace("@group_id", "'"
						+ SessionManager.getGroupId() + "'");
				colNameSql = colNameSql.replace("@hos_id",
						"'" + SessionManager.getHosId() + "'");
				colNameSql = "  select field_col_name from (  " + colNameSql
						+ "  ) where field_col_code = '"
						+ list.get(i).getCol_value() + "'";
				list.get(i).setField_col_name(
						hrStatisticConditionMapper.queryColNam(colNameSql));
			}
		}
		return ChdJson.toJson(list);
	}

}

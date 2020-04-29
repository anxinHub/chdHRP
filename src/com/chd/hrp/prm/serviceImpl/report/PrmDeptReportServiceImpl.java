/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.report.PrmDeptReportMapper;
import com.chd.hrp.prm.service.report.PrmDeptReportService;

/**
 * 
 * @Description: 此处没有使用任何其他附加的
 * @Table: APHI_DEPT_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDeptReportService")
public class PrmDeptReportServiceImpl implements PrmDeptReportService {

	private static Logger logger = Logger.getLogger(PrmDeptReportServiceImpl.class);

	@Resource(name = "prmDeptReportMapper")
	private final PrmDeptReportMapper prmDeptReportMapper = null;

	/**
	 * @Description 查询报表Option<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptReportGrid(Map<String, Object> entityMap) throws DataAccessException {
		
		String dim_code = entityMap.get("dim_code").toString();
		
		String kpi_code = entityMap.get("kpi_code").toString();
		
		if("".equals(kpi_code) && !"".equals(dim_code)){
			
			entityMap.put("kpi_code", dim_code);
			
		}
		
		if("".equals(kpi_code) && "".equals(dim_code)){
			
			entityMap.put("sql", " and pdks.super_kpi_code='TOP'");
			
		}
		
		String arr_months = entityMap.get("arr_months").toString();
		
		String[] str = arr_months.split(","); 
		
		StringBuffer sqlCase = new StringBuffer();
		
		for(int i = 0; i<str.length; i++){

			sqlCase.append("case when (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.kpi_value end)) >0 ");
			sqlCase.append("then (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.kpi_value end)) || pdks.unit_name ");
			sqlCase.append("else (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.kpi_value end)) || '' ");
			sqlCase.append("end as kpi"+str[i]+"  , ");

			sqlCase.append("max(case when pdks.acc_year || pdks.acc_month = '"+str[i]+"' then pdks.led_path end) as led"+str[i]+",");
			
			sqlCase.append("case when pdks.nature_code='01' then ");
			sqlCase.append("case when (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.goal_value end)) > 0 then ");
			sqlCase.append("'≥' ||(sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.goal_value end) || pdks.unit_name)  ");
			sqlCase.append("else (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.goal_value end) || '') end ");
			sqlCase.append("else "); 
			sqlCase.append("case when (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.goal_value end)) > 0 then ");
			sqlCase.append("'<' || (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.goal_value end) || pdks.unit_name) ");
			sqlCase.append("else (sum(case when (pdks.acc_year || pdks.acc_month) = '"+str[i]+"' then pdks.goal_value end) || '') end  ");
			sqlCase.append("end ");
			sqlCase.append("as goal"+str[i]+",");
			
		}
		
		entityMap.put("sqlCase", sqlCase);

		List<Map<String, Object>> list = prmDeptReportMapper.queryPrmDeptReportGrid(entityMap);

		return ChdJson.toJsonLower(list);
	}

}

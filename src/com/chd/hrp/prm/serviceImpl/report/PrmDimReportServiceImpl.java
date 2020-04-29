/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl.report;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.report.PrmDimReportMapper;
import com.chd.hrp.prm.service.report.PrmDimReportService;

/**
 * 
 * @Description: 此处没有使用任何其他附加的
 * @Table: APHI_DEPT_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmDimReportService")
public class PrmDimReportServiceImpl implements PrmDimReportService {

	private static Logger logger = Logger.getLogger(PrmDimReportServiceImpl.class);

	@Resource(name = "prmDimReportMapper")
	private final PrmDimReportMapper prmDimReportMapper = null;

	/**
	 * @Description 查询报表Option<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDimReportOption(Map<String, Object> entityMap) throws DataAccessException {

		StringBuffer option = new StringBuffer();

		option.append("{");

		option.append("\"title\":[\"台州市中心医院\"]");
		
		option.append(",\"xAxis\":['1月份','2月份','3月份','4月份','5月份','6月份','7月份','8月份','9月份','10月份','11月份','12月份']");
		
		List<Map<String,Object>> list = getECharReportList(entityMap);
		
		String kpi_name = entityMap.get("kpi_name").toString();
		
		String dim_name = entityMap.get("dim_name").toString();

		if(!"".equals(kpi_name)){kpi_name = kpi_name.split(" ")[1];}

		if("".equals(kpi_name) && !"".equals(dim_name)){kpi_name = kpi_name.split(" ")[1];}

		option.append(",\"legendData\":[");
		
		for(int i=0; i<list.size()-1;i++){
			
			Map<String,Object> map = list.get(i);
			
			if(i!=0){option.append(",");}
			
			option.append("'"+kpi_name+map.get("acc_year")+"',");
			
		}
		option.append("]");
		
		Map<String,Object> seriesMap = list.get(0);
		
		option.append(",\"series\":[{");
		option.append("name: '"+kpi_name+seriesMap.get("acc_year")+"',");
		option.append("type: 'bar',z: '1',barWidth: '25%',");
		option.append("itemStyle: {normal: {color: 'rgba(117,195,39,1)'}},");
		option.append("data:");
		option.append("["+seriesMap.get("acc_month01")+", "+seriesMap.get("acc_month02")+","
				+ " "+seriesMap.get("acc_month03")+", "+seriesMap.get("acc_month04")+","
				+ " "+seriesMap.get("acc_month05")+", "+seriesMap.get("acc_month06")+","
				+ " "+seriesMap.get("acc_month07")+", "+seriesMap.get("acc_month08")+","
				+ " "+seriesMap.get("acc_month09")+", "+seriesMap.get("acc_month10")+","
				+ " "+seriesMap.get("acc_month11")+", "+seriesMap.get("acc_month12")+"]");
		option.append("},");
		
		if(list.size()>2){
			
			for(int i=1; i<list.size()-1;i++){
				
				Map<String,Object> map = list.get(i);
				
				option.append("{");
				option.append("name: '"+kpi_name+map.get("acc_year")+"',");
				option.append("type: 'bar',z: '1',barWidth: '25%',");
				option.append("itemStyle: {normal: {color: 'rgba(189,215,238,1)'}},");
				option.append("data:");
				option.append("["+map.get("acc_month01")+", "+map.get("acc_month02")+","
						+ " "+map.get("acc_month03")+", "+map.get("acc_month04")+","
						+ " "+map.get("acc_month05")+", "+map.get("acc_month06")+","
						+ " "+map.get("acc_month07")+", "+map.get("acc_month08")+","
						+ " "+map.get("acc_month09")+", "+map.get("acc_month10")+","
						+ " "+map.get("acc_month11")+", "+map.get("acc_month12")+"]");
				option.append("},");
			}
			
			
		}
		
		Map<String,Object> goalMap = list.get(list.size()-1);
		
		option.append("{");
		option.append("name:'目标值',type:'line',z: 2,");
		option.append("itemStyle: {normal: {color: 'rgba(255,153,0,1)'}},");
		option.append("data:");
		option.append("["+goalMap.get("acc_month01")+", "+goalMap.get("acc_month02")+","
				+ " "+goalMap.get("acc_month03")+", "+goalMap.get("acc_month04")+","
				+ " "+goalMap.get("acc_month05")+", "+goalMap.get("acc_month06")+","
				+ " "+goalMap.get("acc_month07")+", "+goalMap.get("acc_month08")+","
				+ " "+goalMap.get("acc_month09")+", "+goalMap.get("acc_month10")+","
				+ " "+goalMap.get("acc_month11")+", "+goalMap.get("acc_month12")+"]");
		option.append("}");
		
		option.append("]");

		option.append("}");

		return option.toString();
	}
	
	public List<Map<String,Object>> getDimReportList(Map<String, Object> entityMap){
		
		//查询条件
		String arr_years = entityMap.get("arr_years").toString();
		
		String start_year_month = entityMap.get("start_year_month").toString();
		
		String end_year_month = entityMap.get("end_year_month").toString();
		
		String goal_code = entityMap.get("goal_code").toString();
		
		String dim_code = entityMap.get("dim_code").toString();
		
		String kpi_code = entityMap.get("kpi_code").toString();
		
		String dept_kind_code = entityMap.get("dept_kind_code").toString();
		
		String dept_id = entityMap.get("dept_id").toString();
		
		String dept_no = entityMap.get("dept_no").toString();
		
		if("".equals(kpi_code) && !"".equals(dim_code)){entityMap.put("kpi_code", dim_code);}


		//判断年月
		String[] str_years = arr_years.split(","); 
		
		Collections.reverse(Arrays.asList(str_years));
		
		StringBuffer sql = new StringBuffer();
		
		for(int i=0; i<str_years.length; i++){
			
			if(i !=0){sql.append(" union all ");}
			
			sql.append("select ");
			sql.append(""+str_years[i]+" as acc_year, ");
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month01, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month02, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month03, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month04, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month05, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month06, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month07, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month08, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month09, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month10, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month11, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) * 100 || hu.unit_name ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) * 100 || '' end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) || hu.unit_name  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) || '' end ");
			sql.append("end   ");
			sql.append("as acc_month12 ");
			
			sql.append("from PRM_DEPT_KPI_VALUE p"+str_years[i]+"   ");
			sql.append("left join prm_dept_dict pdd on p"+str_years[i]+".group_id = pdd.group_id ");
			sql.append("and p"+str_years[i]+".hos_id = pdd.hos_id   ");
			sql.append("and p"+str_years[i]+".dept_id = pdd.dept_id ");
			sql.append("and p"+str_years[i]+".dept_no = pdd.dept_no ");
			sql.append("left join PRM_KPI_LIB pkl on p"+str_years[i]+".group_id = pkl.group_id ");
			sql.append("and p"+str_years[i]+".hos_id = pkl.hos_id ");
			sql.append("and p"+str_years[i]+".copy_code = pkl.copy_code ");
			sql.append("and p"+str_years[i]+".kpi_code = pkl.kpi_code ");
			sql.append("left join  hos_unit hu on pkl.unit_code = hu.unit_code ");
			sql.append("and pkl.group_id = hu.group_id ");
			sql.append("and pkl.hos_id = hu.hos_id ");
			sql.append("where ");
			sql.append("p"+str_years[i]+".group_id = '"+entityMap.get("group_id")+"'     ");
			sql.append("and p"+str_years[i]+".hos_id = '"+entityMap.get("hos_id")+"'   ");
			sql.append("and p"+str_years[i]+".copy_code = '"+entityMap.get("copy_code")+"'");
			
			if(!"".equals(goal_code)){sql.append(" and p"+str_years[i]+".goal_code = '"+goal_code+"'");}
			
			if(!"".equals(kpi_code)){sql.append(" and p"+str_years[i]+".kpi_code = '"+kpi_code+"'");}
			
			if(!"".equals(dept_id)){sql.append(" and p"+str_years[i]+".dept_id = '"+dept_id+"'");}
			
			if(!"".equals(dept_no)){sql.append(" and p"+str_years[i]+".dept_no = '"+dept_no+"'");}
			
			if(!"".equals(dept_kind_code)){sql.append(" and pdd.dept_kind_code = '"+dept_kind_code+"'");}
			
			if(!"".equals(kpi_code) && "".equals(dim_code)){sql.append(" and p"+str_years[i]+".super_kpi_code = 'TOP'");}
			
			sql.append(" and p"+str_years[i]+".acc_year = '"+str_years[i]+"'");
			
			sql.append("group by pkl.unit_code,hu.unit_name");

		}
		sql.append(" union all ");
		sql.append("select ");
		sql.append("0 as acc_year,  ");
		
		sql.append("case when round(avg(case when pds.acc_month = '01' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '01' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '01' then goal_value end),2) || '' end ");
		sql.append("as acc_month01 ,");
		
		sql.append("case when round(avg(case when pds.acc_month = '02' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '02' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '02' then goal_value end),2) || '' end ");
		sql.append("as acc_month02 ,");
		
		sql.append("case when round(avg(case when pds.acc_month = '03' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '03' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '03' then goal_value end),2) || '' end ");
		sql.append("as acc_month03 ,");

		sql.append("case when round(avg(case when pds.acc_month = '04' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '04' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '04' then goal_value end),2) || '' end ");
		sql.append("as acc_month04 ,");

		sql.append("case when round(avg(case when pds.acc_month = '05' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '05' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '05' then goal_value end),2) || '' end ");
		sql.append("as acc_month05 ,");

		sql.append("case when round(avg(case when pds.acc_month = '06' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '06' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '06' then goal_value end),2) || '' end ");
		sql.append("as acc_month06 ,");

		sql.append("case when round(avg(case when pds.acc_month = '07' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '07' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '07' then goal_value end),2) || '' end ");
		sql.append("as acc_month07 ,");

		sql.append("case when round(avg(case when pds.acc_month = '08' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '08' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '08' then goal_value end),2) || '' end ");
		sql.append("as acc_month08 ,");

		sql.append("case when round(avg(case when pds.acc_month = '09' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '09' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '09' then goal_value end),2) || '' end ");
		sql.append("as acc_month09 ,");

		sql.append("case when round(avg(case when pds.acc_month = '10' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '10' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '10' then goal_value end),2) || '' end ");
		sql.append("as acc_month10 ,");

		sql.append("case when round(avg(case when pds.acc_month = '11' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '11' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '11' then goal_value end),2) || '' end ");
		sql.append("as acc_month11 ,");

		sql.append("case when round(avg(case when pds.acc_month = '12' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '12' then goal_value end),2) || hu.unit_name  ");
		sql.append("else round(avg(case when pds.acc_month = '12' then goal_value end),2) || '' end ");
		sql.append("as acc_month12 ");

		sql.append("from PRM_DEPT_SCHEME pds ");
		sql.append("left join prm_dept_dict pdd on pds.group_id = pdd.group_id ");
		sql.append("and pds.hos_id = pdd.hos_id ");
		sql.append("and pds.dept_id = pdd.dept_id ");
		sql.append("and pds.dept_no = pdd.dept_no  ");
		sql.append("left join PRM_KPI_LIB pkl on pds.group_id = pkl.group_id ");
		sql.append("and pds.hos_id = pkl.hos_id ");
		sql.append("and pds.copy_code = pkl.copy_code ");
		sql.append("and pds.kpi_code = pkl.kpi_code ");
		sql.append("left join  hos_unit hu on pkl.unit_code = hu.unit_code ");
		sql.append("and pkl.group_id = hu.group_id ");
		sql.append("and pkl.hos_id = hu.hos_id ");
		sql.append("where ");
		sql.append("pds.group_id = '"+entityMap.get("group_id")+"'     ");
		sql.append("and pds.hos_id = '"+entityMap.get("hos_id")+"'   ");
		sql.append("and pds.copy_code = '"+entityMap.get("copy_code")+"'");
		
		if(!"".equals(goal_code)){sql.append(" and pds.goal_code = '"+goal_code+"'");}
		
		if(!"".equals(kpi_code)){sql.append(" and pds.kpi_code = '"+kpi_code+"'");}
		
		if(!"".equals(dept_id)){sql.append(" and pds.dept_id = '"+dept_id+"'");}
		
		if(!"".equals(dept_no)){sql.append(" and pds.dept_no = '"+dept_no+"'");}
		
		if(!"".equals(dept_kind_code)){sql.append(" and pdd.dept_kind_code = '"+dept_kind_code+"'");}
		
		if(!"".equals(kpi_code) && "".equals(dim_code)){sql.append(" and pds.super_kpi_code = 'TOP'");}

		sql.append(" and pds.acc_year = '"+str_years[0]+"'");
		
		sql.append(" group by hu.unit_name ");
		
		sql.append(" order by acc_year desc");
		
		entityMap.put("sql", sql);
		
		List<Map<String,Object>> list = prmDimReportMapper.queryPrmDimReportBottomGrid(entityMap);
		
		return list;
	}
	
	public List<Map<String,Object>> getECharReportList(Map<String, Object> entityMap){
		
		//查询条件
		String arr_years = entityMap.get("arr_years").toString();
		
		String start_year_month = entityMap.get("start_year_month").toString();
		
		String end_year_month = entityMap.get("end_year_month").toString();
		
		String goal_code = entityMap.get("goal_code").toString();
		
		String dim_code = entityMap.get("dim_code").toString();
		
		String kpi_code = entityMap.get("kpi_code").toString();
		
		String dept_kind_code = entityMap.get("dept_kind_code").toString();
		
		String dept_id = entityMap.get("dept_id").toString();
		
		String dept_no = entityMap.get("dept_no").toString();
		
		if("".equals(kpi_code) && !"".equals(dim_code)){entityMap.put("kpi_code", dim_code);}


		//判断年月
		String[] str_years = arr_years.split(","); 
		
		Collections.reverse(Arrays.asList(str_years));
		
		StringBuffer sql = new StringBuffer();
		
		for(int i=0; i<str_years.length; i++){
			
			if(i !=0){sql.append(" union all ");}
			
			sql.append("select ");
			sql.append(""+str_years[i]+" as acc_year, ");
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '01' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month01, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '02' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month02, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '03' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month03, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '04' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month04, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '05' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month05, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '06' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month06, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '07' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month07, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '08' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month08, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '09' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month09, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '10' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month10, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '11' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month11, ");
			
			sql.append("case when pkl.unit_code = '72' then  ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) * 100 ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) * 100 end ");
			sql.append("else ");
			sql.append("case when round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) >0 then  ");
			sql.append("round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2)  ");
			sql.append("else round(avg(case when p"+str_years[i]+".acc_month = '12' then kpi_value end),2) end ");
			sql.append("end   ");
			sql.append("as acc_month12 ");
			
			sql.append("from PRM_DEPT_KPI_VALUE p"+str_years[i]+"   ");
			sql.append("left join prm_dept_dict pdd on p"+str_years[i]+".group_id = pdd.group_id ");
			sql.append("and p"+str_years[i]+".hos_id = pdd.hos_id   ");
			sql.append("and p"+str_years[i]+".dept_id = pdd.dept_id ");
			sql.append("and p"+str_years[i]+".dept_no = pdd.dept_no ");
			sql.append("left join PRM_KPI_LIB pkl on p"+str_years[i]+".group_id = pkl.group_id ");
			sql.append("and p"+str_years[i]+".hos_id = pkl.hos_id ");
			sql.append("and p"+str_years[i]+".copy_code = pkl.copy_code ");
			sql.append("and p"+str_years[i]+".kpi_code = pkl.kpi_code ");
			sql.append("left join  hos_unit hu on pkl.unit_code = hu.unit_code ");
			sql.append("and pkl.group_id = hu.group_id ");
			sql.append("and pkl.hos_id = hu.hos_id ");
			sql.append("where ");
			sql.append("p"+str_years[i]+".group_id = '"+entityMap.get("group_id")+"'     ");
			sql.append("and p"+str_years[i]+".hos_id = '"+entityMap.get("hos_id")+"'   ");
			sql.append("and p"+str_years[i]+".copy_code = '"+entityMap.get("copy_code")+"'");
			
			/*and exists(
					select 1 from sys_user_perm_data sys where sys.group_id = #{group_id}
					and sys.hos_id = #{hos_id} and sys.copy_code = #{copy_code}
					and sys.user_id = #{user_id}
					and sys.mod_code = '07' and sys.table_code = 'PRM_DEPT_DICT'
					and sys.perm_code = a.dept_id and sys.is_read = 1 and sys.is_write = 1
				)*/
			sql.append("and exists( ");
			sql.append("select 1 from sys_user_perm_data sys where sys.group_id = '" + entityMap.get("group_id") + "' ");
			sql.append("and sys.hos_id = #{hos_id} and sys.copy_code = '" + entityMap.get("hos_id")+"' ");
			sql.append("and sys.user_id = '" + entityMap.get("user_id") + "' ");
			sql.append("and sys.mod_code = '07' and sys.table_code = 'PRM_DEPT_DICT' ");
			sql.append("and sys.perm_code = p"+ str_years[i] +".dept_id and sys.is_read = 1 and sys.is_write = 1 ");
			sql.append(")");
			
			if(!"".equals(goal_code)){sql.append(" and p"+str_years[i]+".goal_code = '"+goal_code+"'");}
			
			if(!"".equals(kpi_code)){sql.append(" and p"+str_years[i]+".kpi_code = '"+kpi_code+"'");}
			
			if(!"".equals(dept_id)){sql.append(" and p"+str_years[i]+".dept_id = '"+dept_id+"'");}
			
			if(!"".equals(dept_no)){sql.append(" and p"+str_years[i]+".dept_no = '"+dept_no+"'");}
			
			if(!"".equals(dept_kind_code)){sql.append(" and pdd.dept_kind_code = '"+dept_kind_code+"'");}
			
			if(!"".equals(kpi_code) && "".equals(dim_code)){sql.append(" and p"+str_years[i]+".super_kpi_code = 'TOP'");}
			
			sql.append(" and p"+str_years[i]+".acc_year = '"+str_years[i]+"'");
			
			sql.append("group by pkl.unit_code ");

		}
		sql.append(" union all ");
		sql.append("select ");
		sql.append("0 as acc_year,  ");
		
		sql.append("case when round(avg(case when pds.acc_month = '01' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '01' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '01' then goal_value end),2) end ");
		sql.append("as acc_month01 ,");
		
		sql.append("case when round(avg(case when pds.acc_month = '02' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '02' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '02' then goal_value end),2) end ");
		sql.append("as acc_month02 ,");
		
		sql.append("case when round(avg(case when pds.acc_month = '03' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '03' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '03' then goal_value end),2) end ");
		sql.append("as acc_month03 ,");

		sql.append("case when round(avg(case when pds.acc_month = '04' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '04' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '04' then goal_value end),2) end ");
		sql.append("as acc_month04 ,");

		sql.append("case when round(avg(case when pds.acc_month = '05' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '05' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '05' then goal_value end),2) end ");
		sql.append("as acc_month05 ,");

		sql.append("case when round(avg(case when pds.acc_month = '06' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '06' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '06' then goal_value end),2) end ");
		sql.append("as acc_month06 ,");

		sql.append("case when round(avg(case when pds.acc_month = '07' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '07' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '07' then goal_value end),2) end ");
		sql.append("as acc_month07 ,");

		sql.append("case when round(avg(case when pds.acc_month = '08' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '08' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '08' then goal_value end),2) end ");
		sql.append("as acc_month08 ,");

		sql.append("case when round(avg(case when pds.acc_month = '09' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '09' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '09' then goal_value end),2) end ");
		sql.append("as acc_month09 ,");

		sql.append("case when round(avg(case when pds.acc_month = '10' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '10' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '10' then goal_value end),2) end ");
		sql.append("as acc_month10 ,");

		sql.append("case when round(avg(case when pds.acc_month = '11' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '11' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '11' then goal_value end),2) end ");
		sql.append("as acc_month11 ,");

		sql.append("case when round(avg(case when pds.acc_month = '12' then goal_value end),2) >0 then "); 
		sql.append("round(avg(case when pds.acc_month = '12' then goal_value end),2)  ");
		sql.append("else round(avg(case when pds.acc_month = '12' then goal_value end),2) end ");
		sql.append("as acc_month12 ");

		sql.append("from PRM_DEPT_SCHEME pds ");
		sql.append("left join prm_dept_dict pdd on pds.group_id = pdd.group_id ");
		sql.append("and pds.hos_id = pdd.hos_id ");
		sql.append("and pds.dept_id = pdd.dept_id ");
		sql.append("and pds.dept_no = pdd.dept_no  ");
		sql.append("left join PRM_KPI_LIB pkl on pds.group_id = pkl.group_id ");
		sql.append("and pds.hos_id = pkl.hos_id ");
		sql.append("and pds.copy_code = pkl.copy_code ");
		sql.append("and pds.kpi_code = pkl.kpi_code ");
		sql.append("left join  hos_unit hu on pkl.unit_code = hu.unit_code ");
		sql.append("and pkl.group_id = hu.group_id ");
		sql.append("and pkl.hos_id = hu.hos_id ");
		sql.append("where ");
		sql.append("pds.group_id = '"+entityMap.get("group_id")+"'     ");
		sql.append("and pds.hos_id = '"+entityMap.get("hos_id")+"'   ");
		sql.append("and pds.copy_code = '"+entityMap.get("copy_code")+"'");
		
		sql.append("and exists( ");
		sql.append("select 1 from sys_user_perm_data sys where sys.group_id = '" + entityMap.get("group_id") + "' ");
		sql.append("and sys.hos_id = #{hos_id} and sys.copy_code = '" + entityMap.get("hos_id")+"' ");
		sql.append("and sys.user_id = '" + entityMap.get("user_id") + "' ");
		sql.append("and sys.mod_code = '07' and sys.table_code = 'PRM_DEPT_DICT' ");
		sql.append("and sys.perm_code = pds.dept_id and sys.is_read = 1 and sys.is_write = 1 ");
		sql.append(")");
		
		if(!"".equals(goal_code)){sql.append(" and pds.goal_code = '"+goal_code+"'");}
		
		if(!"".equals(kpi_code)){sql.append(" and pds.kpi_code = '"+kpi_code+"'");}
		
		if(!"".equals(dept_id)){sql.append(" and pds.dept_id = '"+dept_id+"'");}
		
		if(!"".equals(dept_no)){sql.append(" and pds.dept_no = '"+dept_no+"'");}
		
		if(!"".equals(dept_kind_code)){sql.append(" and pdd.dept_kind_code = '"+dept_kind_code+"'");}
		
		if(!"".equals(kpi_code) && "".equals(dim_code)){sql.append(" and pds.super_kpi_code = 'TOP'");}

		sql.append(" and pds.acc_year = '"+str_years[0]+"'");
		
		sql.append(" group by hu.unit_name ");
		
		entityMap.put("sql", sql);
		
		List<Map<String,Object>> list = prmDimReportMapper.queryPrmECharReportBottomGrid(entityMap);
		
		return list;
	}

	@Override
	public String queryPrmDimReportBottomGrid(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> list = getDimReportList(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String queryPrmDimReportRightGrid(Map<String, Object> entityMap) throws DataAccessException {
		
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


		List<Map<String,Object>> list = prmDimReportMapper.queryPrmDimReportRightGrid(entityMap);
		
		return ChdJson.toJsonLower(list);
	}

}

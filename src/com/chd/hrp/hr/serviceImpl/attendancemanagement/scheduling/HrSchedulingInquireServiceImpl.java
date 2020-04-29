package com.chd.hrp.hr.serviceImpl.attendancemanagement.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrSchedulingInquireMapper;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingInquireService;
import com.github.pagehelper.PageInfo;


@Service("hrSchedulingInquireService")
public class HrSchedulingInquireServiceImpl  implements HrSchedulingInquireService{
	private static Logger logger = Logger.getLogger(HrSchedulingInquireServiceImpl.class);
	
	@Resource(name="hrSchedulingInquireMapper")
	private final	HrSchedulingInquireMapper hrSchedulingInquireMapper =null;
	
	
	
	@Override
	public String queryEmpPbByDept(Map<String, Object> entityMap)throws DataAccessException {
		
		
		
		
		
		/*StringBuffer sql = new StringBuffer();
		StringBuffer sqla = new StringBuffer();
		StringBuffer sqlb = new StringBuffer();
		List<Map<String, Object>> month= new ArrayList<Map<String,Object>>();
		
		month=hrSchedulingInquireMapper.queryMonth(entityMap);
		for (Map<String, Object> map : month) {
			sqla.append("max(case when to_char(pb2.attend_pbdate,'yyyy-MM-dd') ="+ "'"+map.get("mon")+"'" +" then he.emp_name end) as "  +"\""+ map.get("mon").toString()+ "\""+",");
			
			sqlb.append("WM_CONCAT(" +"\""+ map.get("mon").toString()+ "\""+ ") " +"\""+ map.get("mon").toString()+ "\""+",");
		}
		
		
		sql.append("with tmp1 as(select pb2.attend_classcode,ha.attend_classsname|| '/' || to_char(ha.attend_begtime1, 'hh:mi') || '-' ||"
	        +" to_char(ha.attend_endtime1, 'hh:mi')  || '/' ||"
	        +" to_char(ha.attend_begtime2, 'hh:mi') || '-' ||"
	        +"  to_char(ha.attend_endtime2, 'hh:mi') || '/' ||"
	        +"  to_char(ha.attend_begtime3, 'hh:mi') || '-' ||"
	         +" to_char(ha.attend_endtime3, 'hh:mi') as betime,"
				+ sqla 
				+ " pb2.emp_id"
				+ " from HR_ATTEND_PB2 pb2  left join hos_emp he on pb2.emp_id=he.emp_id and pb2.group_id=he.group_id and pb2.hos_id=he.hos_id       left join HR_ATTEND_CLASS ha "
				+ "  on pb2.attend_classcode=ha.attend_classcode "
	     + "  and pb2.group_id = ha.group_id"
	      + " and pb2.hos_id = ha.hos_id left join hos_dept_dict hd on pb2.dept_id=hd.dept_id and pb2.group_id=hd.group_id and pb2.hos_id=hd.hos_id where pb2.attend_classcode!='0' and"
	      + " hd.dept_code= '"+entityMap.get("dept_id").toString()
	       + "'and hd.group_id= '"+entityMap.get("group_id").toString()
	        + "' and  hd.hos_id= '"+entityMap.get("hos_id").toString()
	      + "' group by pb2.attend_classcode,pb2.emp_id,ha.attend_classsname,ha.attend_begtime1,ha.attend_endtime1,ha.attend_begtime2,ha.attend_endtime2,ha.attend_begtime3,ha.attend_endtime3 ),"
				+ " tmp2 as ("
				+ "  select  "
				+ sqlb
				+"attend_classcode,betime"
				+ " from tmp1 group by attend_classcode,betime )"
				+ " select * from tmp2");
		entityMap.put("sql", sql.toString());
		
		SysPage sysPage = new SysPage();
	
		sysPage = (SysPage) entityMap.get("sysPage");
	
		if (sysPage.getTotal() == -1) {
	
			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSchedulingInquireMapper.queryDept(entityMap);
	
			return ChdJson.toJson(list);
	
		} else {
	
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSchedulingInquireMapper.queryDept(entityMap, rowBounds);
	
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
	
			return ChdJson.toJson(list, page.getTotal());
	
		}*/
		
		return null;
	
	}
	
	
	
	@Override
	public List<Map<String, Object>> queryMonth(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = hrSchedulingInquireMapper.queryMonth(entityMap);
		for (Map<String, Object> map : list) {
			Map<String, Object> reMap = new LinkedHashMap<String, Object>();
			reMap.put("name",  map.get("field_col_code"));
			reMap.put("display", map.get("field_col_name"));
			reMap.put("width", "150");
			reMap.put("align", "center");
			reList.add(reMap);
		}
		return reList;
	}
	
	
	
	@Override
	public String queryEmpPb(Map<String, Object> entityMap) throws DataAccessException {
	
			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSchedulingInquireMapper.queryEmpPb(entityMap);
	             /* for (int i = 0; i < list.size()-1; i++) {
	            	  
	            	  Map<String, Object>  da = new HashMap<String, Object>(); 
	            	  da=list.get(i);
	            	  list.remove(i);
	            	  String date=da.get("attend_pbdate").toString();
	            	  String date1=date.substring(8, 10);
	            	  String date2=date.substring(0, 5);
	            	  String date3=date.substring(5, 8);
	            	  int d1=Integer.parseInt(date1);
	            	  String d4=null;
	            	  int d2;
	            	  if (1<= d1 && d1 <= 9) {
	            		  d4= date2+date3+d1;
					}
	            	  da.put("attend_pbdate", d4);
	            	  list.add(da);
				}
			*/
			return ChdJson.toJson(list);
	
	}
	
	
	
	@Override
	public String queryAllPB(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) hrSchedulingInquireMapper.queryAllPB(entityMap);
	
		return ChdJson.toJson(list);
	}
}

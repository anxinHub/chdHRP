package com.chd.hrp.hr.serviceImpl.attendancemanagement.attendresult;

import java.util.ArrayList;
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.hr.dao.attendancemanagement.attendresult.HrAttendResultSummaryMapper;
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultSummaryService;
import com.github.pagehelper.PageInfo;


@Service("hrAttendResultSummaryService")
public class HrAttendResultSummaryServiceImpl implements HrAttendResultSummaryService{
	
	private static Logger logger = Logger.getLogger(HrAttendResultSummaryServiceImpl.class);
	
	@Resource(name = "hrAttendResultSummaryMapper")
	private final HrAttendResultSummaryMapper hrAttendResultSummaryMapper = null;
	
	

	@Override
	public String queryAttendResultSummary(Map<String, Object> entityMap)
			throws DataAccessException {
		

		
 		List<Map<String,Object>> list =  new ArrayList<Map<String,Object>>() ;
		/*	SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");*/
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			
			List<Map<String,Object>> itemList=hrAttendResultSummaryMapper.queryItemList(entityMap);
			Map<String,Object> ietmMap =new HashMap<String, Object>();
			StringBuffer begSql= new StringBuffer();
			StringBuffer selectSql= new StringBuffer();
			StringBuffer countSql= new StringBuffer();
			StringBuffer countselectSql= new StringBuffer();
			begSql.append("with ");
			String e1=null;
			String e2=null;
			String key=null;
			StringBuffer sele1=new  StringBuffer();
			StringBuffer sele2=new  StringBuffer();
			StringBuffer selecBy=new StringBuffer();
			selecBy.append("select t.* from (");
			if(itemList.size()>0){
			for (Map<String, Object> map : itemList) {
				
				
				StringBuffer sql= new StringBuffer();
				
				StringBuffer str= new StringBuffer();
				StringBuffer counSql= new StringBuffer();
				key=map.get("ATTEND_ITEM").toString();
				//countSql.append("coun"+map.get("ATTEND_ITEM").toString()+" as  (");
				counSql.append("select ' ' as year_month, '合计' as dept_name，10 dept_id_c,");
				counSql.append("sum ( doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+" ) "+ "doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",sum (nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+" )"+ "nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",sum ( other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+")"+ "other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+" from (");
				counSql.append("select * from ( select  ham.year_month,count(he.emp_id) emcount,hd.dept_name,");
				sql.append("select * from ( select  ham.year_month,count(he.emp_id) emcount,hd.dept_name,");
				if(ietmMap.containsKey(key)){
					str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '01' as"+" doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");	
				}else{
				  //	case  when he.yh_code = 01 then 3 || '' ||  he.yh_code when he.yh_code = 02 then 3 || '' || he.yh_code ELSE '303' END yh_code
				sql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				sql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				sql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END yh_code ,");
		
				counSql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				counSql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				counSql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END yh_code ,");
		
					//sql.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code as yh_code ,");
				//counSql.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code as yh_code ,");
				str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '01' as"+" doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '02' as"+" nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '03' as"+" other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				
				}
				ietmMap.put(key, sql);
				if(sele1.length()==0){
					e1=map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
					e2="coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
					sele1.append("select "+e1+".year_month , "+e1+".dept_name , "+e1+".dept_id_c , "+e1+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e1+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e1+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
					sele2.append("select "+e2+".year_month , "+e2+".dept_name , "+e2+".dept_id_c , "+e2+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e2+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e2+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");	
				}else{
				sele1.append(map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				sele2.append("coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+"coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+"coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				}
				//sql.deleteCharAt(index)(sql.length()-1, sql.length()-1);
				sql.deleteCharAt(sql.length() - 1);
				str.deleteCharAt(str.length() - 1);
				
			sql.append(", he.dept_id dept_id_c   from hos_emp he left join  hr_attend_result_manage ham  on ham.group_id=he.group_id and ham.hos_id=he.hos_id and ham.emp_id=he.emp_id   left join hos_dept hd  on he.dept_id=hd.dept_id  and he.group_id = hd.group_id  and he.hos_id = hd.hos_id"+
		" where ham.group_id="+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+" and ham."+map.get("ATTEND_ITEM").toString()+"!=0"+
		" and exists (select 1 from sys_user_perm_data b where he.group_id = b.group_id  and he.hos_id = b.hos_id  and to_char(he.dept_id) = b.perm_code and user_id ="+entityMap.get("user_id")+
		" and TABLE_CODE = upper('HOS_DEPT_DICT') and (b.is_read = 1 or b.is_write = 1))");
		if(!entityMap.get("kind_code").toString().equals("")){
			sql.append("and he.kind_code in ("+entityMap.get("kind_code").toString()+")");
		}
			sql.append(	"    group by ham.year_month,hd.dept_name, he.dept_id , ");
			sql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			sql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			sql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END  )");
			
			counSql.append("he.dept_id dept_id_c  from hos_emp he left join  hr_attend_result_manage ham  on ham.group_id=he.group_id and ham.hos_id=he.hos_id and ham.emp_id=he.emp_id   left join hos_dept hd  on he.dept_id=hd.dept_id  and he.group_id = hd.group_id  and he.hos_id = hd.hos_id"+
					" where ham.group_id="+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+" and ham."+map.get("ATTEND_ITEM").toString()+"!=0"+
					" and exists (select 1 from sys_user_perm_data b where he.group_id = b.group_id  and he.hos_id = b.hos_id  and to_char(he.dept_id) = b.perm_code and user_id ="+entityMap.get("user_id")+
							" and TABLE_CODE = upper('HOS_DEPT_DICT') and (b.is_read = 1 or b.is_write = 1))");
							if(!entityMap.get("kind_code").toString().equals("")){
								counSql.append("and he.kind_code in ("+entityMap.get("kind_code").toString()+")");
							}
			counSql.append("    group by ham.year_month,hd.dept_name, he.dept_id, ");
			
			counSql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			counSql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			counSql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END  )");
			
			counSql.append("pivot(max(emcount) for yh_code in("+str+") ))),");
			sql.append("pivot(max(emcount) for yh_code in("+str+") )),");
			
			begSql.append(map.get("ATTEND_ITEM").toString()+" as  ("+sql);
			
			countSql.append("coun"+map.get("ATTEND_ITEM").toString()+" as  ("+counSql);
			
			if(selectSql.length()==0){
				e1=map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
				e2="coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
				selectSql.append(" from " +map.get("ATTEND_ITEM")+" "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length()));
				countselectSql.append(" from coun" +map.get("ATTEND_ITEM")+" coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length()));
				
				selectSql.append(" left join count1 c1 on "+e1+".dept_id_c = c1.dept_id_c");
				countselectSql.append(" left join count2 c2 on "+e2+".dept_name = c2.dept_name");
			}
			else{
				
				selectSql.append(" left join "+map.get("ATTEND_ITEM").toString() + "  "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+" on "+e1+".dept_id_c= "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".dept_id_c ");
				countselectSql.append(" left join coun"+map.get("ATTEND_ITEM").toString() + "  coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+" on "+e2+".dept_name= coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".dept_name ");
			}
			
				//selectSql.append("left join "+map.get("ATTEND_ITEM").toString() + "  "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+" on e1.dept_id_c= "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".dept_id_c ");

			
			}
			
			begSql.append(countSql);
			begSql.append(" count1 as (select year_month,dept_name,dept_id_c  ,doct,nurse,other,(nvl(doct,0)+nvl(nurse,0)+nvl(other,0))  as countall from (select *  from (select  ham.year_month, count(ham.emp_id) emcount, hd.dept_name,                  case when he.yh_code = 01 then he.yh_code when he.yh_code = 02 then he.yh_code else  '03'  END yh_code,ham.dept_id_c from hos_emp he "+
	            "left join  hr_attend_result_manage ham on ham.group_id = he.group_id  and ham.hos_id = he.hos_id and ham.emp_id = he.emp_id"+
	            " left join hos_dept hd on he.dept_id = hd.dept_id and ham.group_id = hd.group_id"+
	             " and ham.hos_id = hd.hos_id where ham.group_id = "+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+
	             "and exists (select 1 from sys_user_perm_data b where he.group_id = b.group_id  and he.hos_id = b.hos_id  and to_char(he.dept_id) = b.perm_code and user_id ="+entityMap.get("user_id")+
					" and TABLE_CODE = upper('HOS_DEPT_DICT') and (b.is_read = 1 or b.is_write = 1))");
					if(!entityMap.get("kind_code").toString().equals("")){
						begSql.append("and he.kind_code in ("+entityMap.get("kind_code").toString()+")");
					}
					begSql.append( "group by  ham.year_month, hd.dept_name, ham.dept_id_c,case when he.yh_code = 01 then  he.yh_code when he.yh_code = 02 then he.yh_code else '03' END ) pivot(max(emcount) for yh_code in( '01' as doct, '02' as nurse, '03' as other)) )),");
			
			begSql.append("   count2 as (select ' 'as  year_month, '合计' as dept_name，10 dept_id_c, doct,  nurse,  other,(nvl(doct,0)+nvl(nurse,0)+nvl(other,0))  as countall from(select *  from (select count(he.emp_id) emcount, case when he.yh_code = 01 then"+
	                  " he.yh_code  when he.yh_code = 02 then he.yh_code else '03' END yh_code from hos_emp he  "+
		            "left join  hr_attend_result_manage ham on ham.group_id = he.group_id  and ham.hos_id = he.hos_id and ham.emp_id = he.emp_id"+
		            " left join hos_dept hd on he.dept_id = hd.dept_id and he.group_id = hd.group_id"+
		             " and he.hos_id = hd.hos_id where ham.group_id = "+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+
		             " and exists (select 1 from sys_user_perm_data b where he.group_id = b.group_id  and he.hos_id = b.hos_id  and to_char(he.dept_id) = b.perm_code and user_id ="+entityMap.get("user_id")+
						" and TABLE_CODE = upper('HOS_DEPT_DICT') and (b.is_read = 1 or b.is_write = 1))");
			if(!entityMap.get("kind_code").toString().equals("")){
				begSql.append("and he.kind_code in ("+entityMap.get("kind_code").toString()+")");
			}
			begSql.append(
		           "group by case when he.yh_code = 01 then  he.yh_code when he.yh_code = 02 then he.yh_code else '03' END ) pivot(max(emcount) for yh_code in( '01' as doct, '02' as nurse, '03' as other)))),");
				
			sele1.append("c1.doct,c1.nurse,c1.other,c1.countall");
			sele2.append("c2.doct,c2.nurse,c2.other,c2.countall");
			
			sele1.append(selectSql);
			sele2.append(countselectSql);
			begSql.deleteCharAt(begSql.length()-1);
			
			sele1.append(" union all " +sele2);
			selecBy.append(sele1.toString()+" ) t order by t.dept_id_c desc");
			begSql.append(selecBy.toString());
		
			entityMap.put("begSql", begSql);
			/*entityMap.put("str", str);*/
		/*entityMap.put("str", str);*/
		//RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		list = hrAttendResultSummaryMapper.queryAttendResultSummary(entityMap);
		return ChdJson.toJsonLower(list);
		
		}else{
			return ChdJson.toJsonLower(list);
		}
		
	}



	@Override
	public List<Map<String, Object>> printAttendSummary(
			Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String,Object>> list =  new ArrayList<Map<String,Object>>() ;
		/*	SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");*/
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("user_id", SessionManager.getUserId());
			
			List<Map<String,Object>> itemList=hrAttendResultSummaryMapper.queryItemList(entityMap);
			Map<String,Object> ietmMap =new HashMap<String, Object>();
			StringBuffer begSql= new StringBuffer();
			StringBuffer selectSql= new StringBuffer();
			StringBuffer countSql= new StringBuffer();
			StringBuffer countselectSql= new StringBuffer();
			begSql.append("with ");
			String e1=null;
			String e2=null;
			String key=null;
			StringBuffer sele1=new  StringBuffer();
			StringBuffer sele2=new  StringBuffer();
			StringBuffer selecBy=new StringBuffer();
			selecBy.append("select t.* from (");
			if(itemList.size()>0){
			for (Map<String, Object> map : itemList) {
				
				
				StringBuffer sql= new StringBuffer();
				
				StringBuffer str= new StringBuffer();
				StringBuffer counSql= new StringBuffer();
				key=map.get("ATTEND_ITEM").toString();
				//countSql.append("coun"+map.get("ATTEND_ITEM").toString()+" as  (");
				counSql.append("select ' ' as year_month, '合计' as dept_name，10 dept_id_c,");
				counSql.append("sum ( doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+" ) "+ "doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",sum (nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+" )"+ "nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",sum ( other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+")"+ "other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+" from (");
				counSql.append("select * from ( select  ham.year_month,count(he.emp_id) emcount,hd.dept_name,");
				sql.append("select * from ( select  ham.year_month,count(he.emp_id) emcount,hd.dept_name,");
				if(ietmMap.containsKey(key)){
					str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '01' as"+" doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");	
				}else{
				  //	case  when he.yh_code = 01 then 3 || '' ||  he.yh_code when he.yh_code = 02 then 3 || '' || he.yh_code ELSE '303' END yh_code
				sql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				sql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				sql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END yh_code ,");
		
				counSql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				counSql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
				counSql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END yh_code ,");
		
					//sql.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code as yh_code ,");
				//counSql.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code as yh_code ,");
				str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '01' as"+" doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '02' as"+" nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				str.append(map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''|| '03' as"+" other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				
				}
				ietmMap.put(key, sql);
				if(sele1.length()==0){
					e1=map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
					e2="coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
					sele1.append("select "+e1+".year_month , "+e1+".dept_name , "+e1+".dept_id_c , "+e1+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e1+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e1+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
					sele2.append("select "+e2+".year_month , "+e2+".dept_name , "+e2+".dept_id_c , "+e2+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e2+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+e2+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");	
				}else{
				sele1.append(map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				sele2.append("coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".doct"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+"coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".nurse"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+","+"coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".other"+ map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+",");
				}
				//sql.deleteCharAt(index)(sql.length()-1, sql.length()-1);
				sql.deleteCharAt(sql.length() - 1);
				str.deleteCharAt(str.length() - 1);
			sql.append(", he.dept_id dept_id_c   from hos_emp he left join  hr_attend_result_manage ham  on ham.group_id=he.group_id and ham.hos_id=he.hos_id and ham.emp_id=he.emp_id   left join hos_dept hd  on he.dept_id=hd.dept_id  and he.group_id = hd.group_id  and he.hos_id = hd.hos_id"+
		" where ham.group_id="+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+" and ham."+map.get("ATTEND_ITEM").toString()+"!=0"+
		"    group by ham.year_month,hd.dept_name, he.dept_id , ");
			sql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			sql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			sql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END  )");
			
			counSql.append("he.dept_id dept_id_c  from hos_emp he left join  hr_attend_result_manage ham  on ham.group_id=he.group_id and ham.hos_id=he.hos_id and ham.emp_id=he.emp_id   left join hos_dept hd  on he.dept_id=hd.dept_id  and he.group_id = hd.group_id  and he.hos_id = hd.hos_id"+
					" where ham.group_id="+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+" and ham."+map.get("ATTEND_ITEM").toString()+"!=0"+
					"    group by ham.year_month,hd.dept_name, he.dept_id, ");
			
			counSql.append(" case  when he.yh_code = 01 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			counSql.append("   when he.yh_code = 02 then "+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"||''||he.yh_code");
			counSql.append(" else '"+map.get("ATTEND_ITEM").toString().substring(4, map.get("ATTEND_ITEM").toString().length())+"03' END  )");
			
			counSql.append("pivot(max(emcount) for yh_code in("+str+") ))),");
			sql.append("pivot(max(emcount) for yh_code in("+str+") )),");
			
			begSql.append(map.get("ATTEND_ITEM").toString()+" as  ("+sql);
			
			countSql.append("coun"+map.get("ATTEND_ITEM").toString()+" as  ("+counSql);
			
			if(selectSql.length()==0){
				e1=map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
				e2="coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length());
				selectSql.append(" from " +map.get("ATTEND_ITEM")+" "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length()));
				countselectSql.append(" from coun" +map.get("ATTEND_ITEM")+" coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length()));
				
				selectSql.append(" left join count1 c1 on "+e1+".dept_id_c = c1.dept_id_c");
				countselectSql.append(" left join count2 c2 on "+e2+".dept_name = c2.dept_name");
			}
			else{
				
				selectSql.append(" left join "+map.get("ATTEND_ITEM").toString() + "  "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+" on "+e1+".dept_id_c= "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".dept_id_c ");
				countselectSql.append(" left join coun"+map.get("ATTEND_ITEM").toString() + "  coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+" on "+e2+".dept_name= coun"+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".dept_name ");
			}
			
				//selectSql.append("left join "+map.get("ATTEND_ITEM").toString() + "  "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+" on e1.dept_id_c= "+map.get("ATTEND_ITEM").toString().substring(3, map.get("ATTEND_ITEM").toString().length())+".dept_id_c ");

			
			}
			
			begSql.append(countSql);
			begSql.append(" count1 as (select year_month,dept_name,dept_id_c  ,doct,nurse,other,(nvl(doct,0)+nvl(nurse,0)+nvl(other,0))  as countall from (select *  from (select  ham.year_month, count(ham.emp_id) emcount, hd.dept_name,                  case when he.yh_code = 01 then he.yh_code when he.yh_code = 02 then he.yh_code else  '03'  END yh_code,ham.dept_id_c from hos_emp he "+
	            "left join  hr_attend_result_manage ham on ham.group_id = he.group_id  and ham.hos_id = he.hos_id and ham.emp_id = he.emp_id"+
	            " left join hos_dept hd on he.dept_id = hd.dept_id and ham.group_id = hd.group_id"+
	             " and ham.hos_id = hd.hos_id where ham.group_id = "+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+
	            
	           "group by  ham.year_month, hd.dept_name, ham.dept_id_c,case when he.yh_code = 01 then  he.yh_code when he.yh_code = 02 then he.yh_code else '03' END ) pivot(max(emcount) for yh_code in( '01' as doct, '02' as nurse, '03' as other)) )),");
			
			begSql.append("   count2 as (select ' 'as  year_month, '合计' as dept_name，10 dept_id_c, doct,  nurse,  other,(nvl(doct,0)+nvl(nurse,0)+nvl(other,0))  as countall from(select *  from (select count(he.emp_id) emcount, case when he.yh_code = 01 then"+
	                  " he.yh_code  when he.yh_code = 02 then he.yh_code else '03' END yh_code from hos_emp he  "+
		            "left join  hr_attend_result_manage ham on ham.group_id = he.group_id  and ham.hos_id = he.hos_id and ham.emp_id = he.emp_id"+
		            " left join hos_dept hd on he.dept_id = hd.dept_id and he.group_id = hd.group_id"+
		             " and he.hos_id = hd.hos_id where ham.group_id = "+entityMap.get("group_id")+" and ham.hos_id ="+entityMap.get("hos_id")+" and ham.year_month ="+entityMap.get("year_month")+
		            
		           "group by case when he.yh_code = 01 then  he.yh_code when he.yh_code = 02 then he.yh_code else '03' END ) pivot(max(emcount) for yh_code in( '01' as doct, '02' as nurse, '03' as other)))),");
				
			sele1.append("c1.doct,c1.nurse,c1.other,c1.countall");
			sele2.append("c2.doct,c2.nurse,c2.other,c2.countall");
			
			sele1.append(selectSql);
			sele2.append(countselectSql);
			begSql.deleteCharAt(begSql.length()-1);
			
			sele1.append(" union all " +sele2);
			selecBy.append(sele1.toString()+" ) t order by t.dept_id_c desc");
			begSql.append(selecBy.toString());
		
			entityMap.put("begSql", begSql);
			/*entityMap.put("str", str);*/
			//RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = hrAttendResultSummaryMapper.queryAttendResultSummary(entityMap);
			return list;
		}else{
			return list;
		}
		
	}

}

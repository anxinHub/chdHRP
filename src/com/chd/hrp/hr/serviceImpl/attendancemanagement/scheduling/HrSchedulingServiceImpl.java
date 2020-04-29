package com.chd.hrp.hr.serviceImpl.attendancemanagement.scheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrSchedulingMapper;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.github.pagehelper.PageInfo;

//排班处理
@Service("hrSchedulingService")
public class HrSchedulingServiceImpl implements HrSchedulingService {
	private static Logger logger = Logger
			.getLogger(HrSchedulingServiceImpl.class);

	@Resource(name = "hrSchedulingMapper")
	private final HrSchedulingMapper hrSchedulingMapper = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;


	@Override
	public String deleteScheduling(Map<String, Object> map)throws DataAccessException {
		try {
//			// 删除明细
//			hrSchedulingMapper.deleteSchedulingBatch(listVo);
//			// 删除主表
//			hrSchedulingMapper.deleteBatchArea(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	/**
	 * 复制上周/月
	 */
	@Override
	public String copyWeek(Map<String, Object> mapVo)throws DataAccessException {
		
		try {
			
			mapVo.put("pb_qj_str", "'"+mapVo.get("pb_qj").toString()+"'");
			mapVo.put("state", "1");
			if(hrSchedulingMapper.queryPbIsCheck(mapVo)>0){
				return "{\"error\":\"已审签.\",\"state\":\"false\"}";
			}
			
			if(isFengCun(mapVo)){
				return "{\"error\":\"已封存.\",\"state\":\"false\"}";
			}
			
			
			//查询需要复制的数据
			List<Map<String, Object>> list = hrSchedulingMapper.queryPBCopy(mapVo);
			StringBuilder sb=new StringBuilder();
			mapVo.put("flag", "copy");
			sb.append("{"+getPbJson(list,mapVo));
			sb.append("}");
			
			return sb.toString();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 日期转星期
	 * 
	 * @param date
	 * @return
	 *//*
	public static String dateToWeek(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			// datet = f.parse(date);
			cal.setTime(datet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	*//****
	 * 传入具体日期 ，返回具体日期增加一个月。
	 * 
	 * @param date
	 *            日期(2017-04-13)
	 * @return 2017-05-13
	 * @throws ParseException
	 *//*
	private String subMonth(String date) throws DataAccessException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		try {
			Date dt = sdf.parse(date);
			rightNow.setTime(dt);
			rightNow.add(Calendar.MONTH, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}*/

	/**
	 * 查询排班
	 */
	@Override
	public String queryPB(Map<String, Object> entityMap)throws DataAccessException {

		try{
			//查询排班明细数据
			List<Map<String, Object>> list = hrSchedulingMapper.queryPB(entityMap);
			
			StringBuilder sb=new StringBuilder();
			sb.append("{"+getPbJson(list,entityMap));
			
			//查询排班审核数据
			Map<String, Object> checkMap=hrSchedulingMapper.queryPBCheck(entityMap);
			
			int state=0;
			String stateName="未审签";
			int isFc=0;
			String fcName="未封存";
			String pbQj="";
			if(checkMap!=null && checkMap.size()>0){
				
				pbQj=checkMap.get("pb_qj").toString();
				sb.append(",\"pb_gz\":"+checkMap.get("pb_gz"));
				sb.append(",\"db_gz\":"+checkMap.get("db_gz"));
				state=Integer.parseInt(checkMap.get("state").toString());
				if(state==1){
					stateName="已审签";
				}
				
				sb.append(",\"check_user_id\":\""+(checkMap.get("check_user_id")==null?"":checkMap.get("check_user_id").toString())+"\"");
				sb.append(",\"check_user_name\":\""+(checkMap.get("check_user_name")==null?"":checkMap.get("check_user_name"))+"\"");
				sb.append(",\"check_date\":\""+(checkMap.get("check_date")==null?"":checkMap.get("check_date"))+"\"");
				sb.append(",\"create_user_id\":\""+checkMap.get("create_user_id")+"\"");
				sb.append(",\"create_user_name\":\""+checkMap.get("create_user_name")+"\"");
				sb.append(",\"create_date\":\""+checkMap.get("create_date")+"\"");
				sb.append(",\"note\":\""+(checkMap.get("note")==null?"":checkMap.get("note"))+"\"");
				
				checkMap.put("group_id", entityMap.get("group_id"));
				checkMap.put("hos_id", entityMap.get("hos_id"));
				checkMap.put("begin_date", entityMap.get("begin_date").toString());
				checkMap.put("end_date", entityMap.get("end_date").toString());//取最后一天
				if(isFengCun(checkMap)){
					isFc=1;
					fcName="已封存";
				}
				
			}
			
			sb.append(",\"pb_qj\":\""+pbQj+"\"");
			sb.append(",\"is_fc\":"+isFc+"");
			sb.append(",\"fc_name\":\""+fcName+"\"");
			sb.append(",\"state\":"+state);
			sb.append(",\"state_name\":\""+stateName+"\"");
			
			sb.append("}");
			return sb.toString();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	
	/**
	 * 查询排班-打印
	 */
	@Override
	public List<Map<String,Object>> queryPBprint(Map<String, Object> entityMap)throws DataAccessException {

		try{
			entityMap.put("dept_source", MyConfig.getSysPara("06103"));
			
			//查询排班明细数据
			List<Map<String, Object>> list = hrSchedulingMapper.queryPB(entityMap);
			
			List<Map<String, Object>> reList=new ArrayList<Map<String, Object>>();
			Map<String,Object> reMap=null;
			Map<String,Object> empMap=new HashMap<String,Object>();
			
			if(list!=null && list.size()>0){
				for (Map<String,Object> emp : list) {
					
					if(empMap.get(emp.get("emp_id").toString())==null){
						
						//组装职工数据
						if(empMap.size()>0){
							reList.add(reMap);
						}
						
						reMap=new HashMap<String,Object>();
						reMap.put("emp_name",emp.get("emp_name"));
						reMap.put("dept_name",emp.get("dept_name"));
						reMap.put("note",(emp.get("note")==null?"":emp.get("note")));
						if(emp.get("level_name")!=null){
							reMap.put("level_name",emp.get("level_name"));
						}
					}
					
					empMap.put(emp.get("emp_id").toString(), emp.get("emp_name"));
					String clasCode= emp.get("attend_classcode").toString();
					if(clasCode.equals("0")){
						continue;
					}
					
					
					//组装排班数据			
					String bcNo=emp.get("bc_no").toString();
					String pb_date=emp.get("pb_date").toString();
					
					reMap.put("c"+bcNo+"_"+pb_date,emp.get("attend_classsname"));
					reMap.put("d"+bcNo+"_"+pb_date,(emp.get("cq_dept_name")==null?"":emp.get("cq_dept_name").toString()));
					
				}
				
				if(empMap.size()>0){
					reList.add(reMap);
				}
			}
			
			
			//查询排班审核数据
			//Map<String, Object> checkMap=hrSchedulingMapper.queryPBCheck(entityMap);
			
			
			return reList;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	
	private boolean isFengCun(Map<String,Object> checkMap)throws Exception{
		
		boolean isFc=false;
		//查询封存设置
		Map<String, Object> fcMap=hrSchedulingMapper.queryset(checkMap);
		if(fcMap==null || fcMap.size()==0){
			return isFc;
		}
		
		//是否自动封存
		if(fcMap.get("attend_pbisfile").toString().equals("0")){
			return isFc;
		}
		
			
		String fcVal=fcMap.get("attend_pbfle_val").toString();
		String begDate=checkMap.get("begin_date").toString()+" 23:59:59";//取第一天
		String endDate=checkMap.get("end_date").toString()+" 23:59:59";//取最后一天
		Long today=new Date().getTime();
		if(fcMap.get("attend_pbfile_set").toString().equalsIgnoreCase("D")){
			//按固定天数封存
			if(new Date().getTime()>DateUtil.getAddDate(endDate,Long.parseLong(fcVal))){
				//当前时间>最后一天+固定天数
				isFc=true;
			}
		}else{
			//按固定周期封存
			if(Integer.parseInt(fcMap.get("attend_pbrule").toString()) == 0){
				// 排班规则：周
				int wk=DateUtil.getCurWeek(new Date());//取当前星期,1,2,3,4,5,6,7
				if(today>DateUtil.getAddDate(endDate,Long.parseLong("7"))){
					//超过一周
					isFc=true;
				}else if(DateUtil.getAddDate(begDate,Long.parseLong(fcVal))< today && today < DateUtil.getAddDate(endDate,Long.parseLong("7"))){
					if(wk > Integer.parseInt(fcVal)){
						isFc=true;
						}
				}
			}else{
				// 排班规则：月
				// 当日号 > 规则号  则  true
				if(Integer.parseInt(DateUtil.getToDayD()) > Integer.parseInt(fcVal)){
					isFc = true;
				}
			}
		}
			
		return isFc;
	}


	/**
	 * 保存排班
	 */
	@Override
	public String savePB(Map<String, Object> mapVo)throws DataAccessException {
		
		try {
			
			String beginDate=mapVo.get("begin_date").toString();
			String endDate=mapVo.get("end_date").toString();
			String beginDateStr=beginDate.substring(2, beginDate.length()).replace("-", "");
			String endDateStr=endDate.substring(2, endDate.length()).replace("-", "");
			String dbGz=mapVo.get("db_gz").toString();
			//重新生成排班周期
			String newPbQj=mapVo.get("attend_areacode").toString()+"-"+beginDateStr+"-"+endDateStr;
			String PbQjStr="'"+newPbQj+"'";
			if(mapVo.get("pb_qj")!=null && !newPbQj.equalsIgnoreCase(mapVo.get("pb_qj").toString())){
				//如果修改过排班规则，需要验证所有的排班周期是否审签
				PbQjStr=PbQjStr+",'"+mapVo.get("pb_qj").toString()+"'";
			}
			mapVo.put("pb_qj_str", PbQjStr);
			mapVo.put("state", "1");
			if(hrSchedulingMapper.queryPbIsCheck(mapVo)>0){
				return "{\"error\":\"已审签.\",\"state\":\"false\"}";
			}
			
			if(mapVo.get("pb_qj")!=null && !mapVo.get("pb_qj").equals("")){
				//有排班数据才需要验证是否已封存
				if(isFengCun(mapVo)){
					return "{\"error\":\"已封存.\",\"state\":\"false\"}";
				}
			}
			
			List<Map> list = JSONArray.parseArray(mapVo.get("paramVo").toString(),Map.class);
			if(list==null || list.size()==0){
				return "{\"error\":\"没有排班数据.\",\"state\":\"false\"}";
			}
			
			mapVo.remove("paramVo");
			//{d1id_2018-08-01=319, note=备注, d2_2018-08-01=小部门, c2_2018-08-01=测试, d2id_2018-08-01=322, emp_name=亦童2381, dept_name=眼科, d1_2018-08-01=骨1科, c2id_2018-08-01=00004, c1id_2018-08-01=00003, _rowIndxPage=0, c1_2018-08-01=中, id=1, _rowIndx=0, dept_id=1534, emp_id=10438}
			List<Map<String,Object>> pbLis=new ArrayList<Map<String,Object>>();
			List<String> checkList=new ArrayList<String>();
			List<String> empList=new ArrayList<String>();
			List<String> deptList=new ArrayList<String>();
			Map<String,Object> pbMap=null;
			int rowIdex=0;
			
			for(Map<String, Object> map: list){
				
				String empId=map.get("emp_id").toString();
				String deptId=map.get("dept_id").toString();
				String note=map.get("note")==null?"":map.get("note").toString();
				boolean isBc=false;
				rowIdex++;
				
				for(Map.Entry<String, Object> entry:map.entrySet()){
					String key=entry.getKey();
					if(key.startsWith("c1id") || key.startsWith("c2id") || key.startsWith("c3id")){
						if(entry.getValue()==null || entry.getValue().equals("")){
							continue;
						}
						
						if(dbGz.equals("1") && (key.startsWith("c2id") || key.startsWith("c3id"))){
							//一班倒
							continue;
						}else if(dbGz.equals("2") && key.startsWith("c3id")){
							//两班倒
							continue;
						}
						
						isBc=true;
						String cqDeptId=deptId;
						if(map.get(key.replace("c", "d"))!=null && !map.get(key.replace("c", "d")).toString().equals("")){
							cqDeptId=map.get(key.replace("c", "d")).toString();//出勤科室
						}
						
						String pbDate=key.split("_")[1];
						String classcode=entry.getValue().toString();
						pbMap=new HashMap<String,Object>();
						pbMap.put("emp_id", empId);
						pbMap.put("pb_date", pbDate);
						pbMap.put("attend_classcode", classcode);
						pbMap.put("dept_id", deptId);
						pbMap.put("cq_dept_id", cqDeptId);
						pbMap.put("note",note);
						pbMap.put("bc_no",key.split("_")[0].replace("c", "").replace("id", ""));
						pbMap.put("sort_code", rowIdex);
						
						for(Map<String, Object> m :pbLis){
							if(empId.equals(m.get("emp_id").toString()) && pbDate.equals(m.get("pb_date").toString()) && classcode.equals(m.get("attend_classcode").toString())){
								//存在同一个职工、同一天、同班次的数据
								checkList.add(map.get("emp_name").toString()+"："+pbDate);
							}
						}
						
						pbLis.add(pbMap);
						
					}
				}
				
				//没有任何班次，也需要添加职工
				if(!isBc){
					pbMap=new HashMap<String,Object>();
					pbMap.put("emp_id", empId);
					pbMap.put("pb_date", beginDate);
					pbMap.put("attend_classcode", "0");
					pbMap.put("dept_id", deptId);
					pbMap.put("cq_dept_id","");
					pbMap.put("note",note);
					pbMap.put("bc_no","1");
					pbMap.put("sort_code", rowIdex);
					pbLis.add(pbMap);
				}
				
				empList.add(empId);
				deptList.add(deptId);
			}
			
			if(pbLis==null || pbLis.size()==0){
				return "{\"error\":\"没有排班数据.\",\"state\":\"false\"}";
			}
			
			//检查接收的数据里面是否存在重复
			if(checkList!=null && checkList.size()>0){
				return "{\"error\":\"一天存在相同班次："+checkList.toString()+"\",\"state\":\"false\"}";
			}
			
			
			mapVo.put("emp_id", StringUtils.join(empList, ","));

			mapVo.put("dept_id", StringUtils.join(deptList, ","));
			//删除排班审核表、明细表
			hrSchedulingMapper.deletePB(mapVo);
			
		
			mapVo.put("pb_qj", newPbQj);//1001-1807301-80805
			String createDate=DateUtil.dateToString(new Date());
			mapVo.put("create_date", createDate);
			
			//添加排班审核表
			hrSchedulingMapper.insertPBCheck(mapVo);
			
			//添加排班明细表
			hrSchedulingMapper.insertPB(mapVo,pbLis);
			
			//检查数据库里面是否存在重复
			checkList=hrSchedulingMapper.queryPbExisits(mapVo);
			if(checkList!=null && checkList.size()>0){
				throw new SysException("在其他区域已经排过班次："+checkList.toString());
			}
			
			StringBuilder sb=new StringBuilder();
			sb.append("{\"pb_qj\":\""+newPbQj+"\"");
			sb.append(",\"is_fc\":0");
			sb.append(",\"fc_name\":\"未封存\"");
			sb.append(",\"state\":0");
			sb.append(",\"state_name\":\"未审签\"");
			sb.append(",\"create_user_id\":\""+mapVo.get("user_id")+"\"");
			sb.append(",\"create_user_name\":\""+SessionManager.getUserName()+"\"");
			sb.append(",\"create_date\":\""+createDate+"\"");
			sb.append(",\"msg\":\"保存成功.\",\"state\":\"true\"}");
			
			return sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	//组装排班数据
	private String  getPbJson(List<Map<String, Object>> list,Map<String,Object> map)throws Exception{

		//{"Rows":[{"emp_id:"1","param":[{},{}]},{"emp_id:"2","param":[{},{}]}]}
		
		if(list==null || list.size()==0){
			return "\"Rows\":[]";
		}
		String flag=map.get("flag")==null?"":map.get("flag").toString();
		StringBuilder sb=new StringBuilder();
		sb.append("\"Rows\":[");
		Map<String,Object> empMap=new HashMap<String,Object>();
		
		for (Map<String,Object> emp : list) {
			
			String deptId=emp.get("dept_id").toString();
			if(empMap.get(emp.get("emp_id").toString())==null){
				//组装职工数据
				if(empMap.size()>0){
					sb.setCharAt(sb.length()-1, '}');
					sb.append(",");
				}
				sb.append("{\"id\":"+emp.get("sort_code")+",");
				sb.append("\"emp_id\":\""+emp.get("emp_id").toString()+"\",");
				sb.append("\"emp_name\":\""+emp.get("emp_name")+"\",");
				sb.append("\"dept_id\":\""+deptId+"\",");
				sb.append("\"dept_name\":\""+emp.get("dept_name")+"\",");
				sb.append("\"note\":\""+(emp.get("note")==null?"":emp.get("note"))+"\",");
				if(emp.get("level_name")!=null){
					sb.append("\"level_name\":\""+emp.get("level_name")+"\",");
				}
			}
			
			empMap.put(emp.get("emp_id").toString(), emp.get("emp_name"));
			String clasCode= emp.get("attend_classcode").toString();
			if(clasCode.equals("0")){
				continue;
			}
			
			String pb_date=emp.get("pb_date").toString();
			if(flag.equals("copy")){
				
				if(map.get("pb_gz").toString().equals("0")){
					//复制上周功能，根据星期几来判断，替换成日期范围里面的日期
					pb_date=DateUtil.getDaysByWeek(pb_date,map.get("begin_date").toString(),map.get("end_date").toString());
				}else{
					//复制上月功能，根据几号来判断，替换成日期范围里面的日期
					pb_date=DateUtil.getDaysByMonth(pb_date,map.get("begin_date").toString(),map.get("end_date").toString());
				}
				
				if(pb_date==null){
					continue;
				}
			}
			
			//组装排班数据
			String bcNo=emp.get("bc_no").toString();
			String cqDeptName="";
			sb.append("\"c"+bcNo+"id_"+pb_date+"\":\""+clasCode+"\",");
			if(emp.get("cq_dept_id")!=null && !deptId.equals(emp.get("cq_dept_id").toString())){
				//出勤科室!=所属科室
				sb.append("\"d"+bcNo+"id_"+pb_date+"\":\""+emp.get("cq_dept_id").toString()+"\",");
				sb.append("\"d"+bcNo+"_"+pb_date+"\":\""+emp.get("cq_dept_name").toString()+"\",");
				
				if(flag.equals("deptQuery")){
					//按科室排班查询，出勤科室直接显示在班次列
					cqDeptName="<br/>"+emp.get("cq_dept_name").toString();
				}
			}
			sb.append("\"c"+bcNo+"_"+pb_date+"\":\""+emp.get("attend_classsname")+cqDeptName+"\",");
			
			
		}
		if(empMap.size()>0){
			sb.setCharAt(sb.length()-1, '}');
		}
		sb.append("]");
		
		return sb.toString();
	
	}
   
    /**
     * 删除人员
     */
	@Override
	public String deleteSchedulingEmp(Map<String, Object> mapVo)throws DataAccessException {
		
		try{
			
			mapVo.put("pb_qj_str", "'"+mapVo.get("pb_qj").toString()+"'");
			mapVo.put("state", "1");
			if(hrSchedulingMapper.queryPbIsCheck(mapVo)>0){
				return "{\"error\":\"已审签.\",\"state\":\"false\"}";
			}
			
			if(isFengCun(mapVo)){
				return "{\"error\":\"已封存.\",\"state\":\"false\"}";
			}
			
			hrSchedulingMapper.deleteSchedulingEmp(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * 审签
	 */
	@Override
	public String auditCountersign(Map<String,Object> mapVo)throws DataAccessException {
		
		try{
			mapVo.put("pb_qj_str", "'"+mapVo.get("pb_qj").toString()+"'");
			if(hrSchedulingMapper.queryPbIsCheck(mapVo)>0){
				if(mapVo.get("state").toString().equals("1")){
					return "{\"error\":\"已审签状态不能审签.\",\"state\":\"false\"}";
				}else{
					return "{\"error\":\"未审签状态不能销审.\",\"state\":\"false\"}";
				}
			}
			
			String cehckDate=DateUtil.dateToString(new Date());
			mapVo.put("check_date", cehckDate);
			
			hrSchedulingMapper.auditCountersign(mapVo);
			
			StringBuilder sb=new StringBuilder();
			sb.append("{\"msg\":\"操作成功.\"");
			sb.append(",\"state\":\"true\"");
			sb.append(",\"check_user_id\":\""+mapVo.get("user_id")+"\"");
			sb.append(",\"check_user_name\":\""+SessionManager.getUserName()+"\"");
			sb.append(",\"check_date\":\""+cehckDate+"\"}");
			return sb.toString();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 按排班区域查询部门
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryDeptTreeByArea(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrSchedulingMapper.queryDeptTreeByArea(entityMap);
		Map<String, Object> top = new HashMap<String, Object>();
		top.put("id", 0);
		top.put("pId", "");
		top.put("DEPT_ID", 0);
		if(SessionManager.getTypeCode().equals("0")){
			top.put("name", SessionManager.getHosSimple());
		}else{
			top.put("name", SessionManager.getGroupSimple());
		}
		
		list.add(top);
		return JSON.toJSONString(list);
	}
	
	/**
	 * 按排班区域查询职工
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryEmpByArea(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = hrSchedulingMapper.queryEmpByArea(entityMap,rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}
	
	/**
	 * 按科室查询排班数据
	 */
	@Override
	public String queryPbByDept(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			//查询排班明细数据
			List<Map<String, Object>> list = hrSchedulingMapper.queryPbByDept(entityMap);
			entityMap.put("flag", "deptQuery");
			StringBuilder sb=new StringBuilder();
			sb.append("{"+getPbJson(list,entityMap));
			
			//查询排班审核数据
			Map<String, Object> checkMap=hrSchedulingMapper.queryPBCheck(entityMap);
			
			int state=0;
			String stateName="未审签";
			int isFc=0;
			String fcName="未封存";
			String pbQj="";
			if(checkMap!=null && checkMap.size()>0){
				
				pbQj=checkMap.get("pb_qj").toString();
				sb.append(",\"pb_gz\":"+checkMap.get("pb_gz"));
				sb.append(",\"db_gz\":"+checkMap.get("db_gz"));
				state=Integer.parseInt(checkMap.get("state").toString());
				if(state==1){
					stateName="已审签";
				}
				
				sb.append(",\"check_user_id\":\""+(checkMap.get("check_user_id")==null?"":checkMap.get("check_user_id").toString())+"\"");
				sb.append(",\"check_user_name\":\""+(checkMap.get("check_user_name")==null?"":checkMap.get("check_user_name"))+"\"");
				sb.append(",\"check_date\":\""+(checkMap.get("check_date")==null?"":checkMap.get("check_date"))+"\"");
				sb.append(",\"create_user_id\":\""+checkMap.get("create_user_id")+"\"");
				sb.append(",\"create_user_name\":\""+checkMap.get("create_user_name")+"\"");
				sb.append(",\"create_date\":\""+checkMap.get("create_date")+"\"");
				sb.append(",\"note\":\""+(checkMap.get("note")==null?"":checkMap.get("note"))+"\"");
				
				checkMap.put("group_id", entityMap.get("group_id"));
				checkMap.put("hos_id", entityMap.get("hos_id"));
				checkMap.put("begin_date", entityMap.get("begin_date").toString()+" 23:59:59");//取最后一天
				checkMap.put("end_date", entityMap.get("end_date").toString()+" 23:59:59");//取最后一天
				if(isFengCun(checkMap)){
					isFc=1;
					fcName="已封存";
				}
				
			}
			
			sb.append(",\"pb_qj\":\""+pbQj+"\"");
			sb.append(",\"is_fc\":"+isFc+"");
			sb.append(",\"fc_name\":\""+fcName+"\"");
			sb.append(",\"state\":"+state);
			sb.append(",\"state_name\":\""+stateName+"\"");
			
			sb.append("}");
		
			return sb.toString();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	/**
	 * 按科室查询排班数据-打印
	 */
	@Override
	public List<Map<String,Object>> queryPbByDeptPrint(Map<String, Object> entityMap)throws DataAccessException {

		try{

			entityMap.put("dept_source", MyConfig.getSysPara("06103"));
			//查询排班明细数据
			List<Map<String, Object>> list = hrSchedulingMapper.queryPbByDept(entityMap);
			
			List<Map<String, Object>> reList=new ArrayList<Map<String, Object>>();
			Map<String,Object> reMap=null;
			Map<String,Object> empMap=new HashMap<String,Object>();
			
			if(list!=null && list.size()>0){
				for (Map<String,Object> emp : list) {
					
					if(empMap.get(emp.get("emp_id").toString())==null){
						
						//组装职工数据
						if(empMap.size()>0){
							reList.add(reMap);
						}
						
						reMap=new HashMap<String,Object>();
						reMap.put("emp_name",emp.get("emp_name"));
						reMap.put("dept_name",emp.get("dept_name"));
						reMap.put("note",(emp.get("note")==null?"":emp.get("note")));
						if(emp.get("level_name")!=null){
							reMap.put("level_name",emp.get("level_name"));
						}
					}
					
					empMap.put(emp.get("emp_id").toString(), emp.get("emp_name"));
					String clasCode= emp.get("attend_classcode").toString();
					if(clasCode.equals("0")){
						continue;
					}
					
					
					//组装排班数据			
					String bcNo=emp.get("bc_no").toString();
					String pb_date=emp.get("pb_date").toString();
					if(!emp.get("dept_id").toString().equals(emp.get("cq_dept_id").toString())){
						reMap.put("c"+bcNo+"_"+pb_date,emp.get("attend_classsname")+" "+(emp.get("cq_dept_name")==null?"":emp.get("cq_dept_name").toString()));
					}else{
						reMap.put("c"+bcNo+"_"+pb_date,emp.get("attend_classsname"));
					}
					
					reMap.put("d"+bcNo+"_"+pb_date,(emp.get("cq_dept_name")==null?"":emp.get("cq_dept_name").toString()));
					
				}
				
				if(empMap.size()>0){
					reList.add(reMap);
				}
			}
						
			return reList;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String extend(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String month = "", year = "";
			int begin_day ;
			String beginDate=entityMap.get("begin_date").toString();
			String endDate=entityMap.get("end_date").toString();
			String byear_month = beginDate.substring(0, 7);
			String endbyear_month=entityMap.get("sel_end_date").toString().substring(0, 7);
			String beginDateStr=beginDate.substring(2, beginDate.length()).replace("-", "");
			String endDateStr=endDate.substring(2, endDate.length()).replace("-", "");
			String dbGz=entityMap.get("db_gz").toString();
			int end_day =0;
			int endEnd_day=0;
			//重新生成排班周期
			String newPbQj=entityMap.get("attend_areacode").toString()+"-"+beginDateStr+"-"+endDateStr;
			String PbQjStr=newPbQj;
			entityMap.put("pb_qj_str", PbQjStr);
			entityMap.put("state", "1");
			entityMap.put("note", "");
			entityMap.put("pb_qj", newPbQj);//1001-1807301-80805
			String createDate=DateUtil.dateToString(new Date());
			entityMap.put("create_date", createDate);
			List<String> pb_date=new ArrayList<String>();
			List<String> sel_pb_date= new ArrayList<String>();
			if(entityMap.get("pb_gz").toString().equals("0")){
				//换算所有 上周开始 结束日期
				 pb_date=DateUtil.getDaysByBeinEnd(entityMap.get("begin_date").toString(),entityMap.get("end_date").toString());
			
				
				 sel_pb_date=DateUtil.getDaysByBeinEnd(entityMap.get("sel_begin_date").toString(),entityMap.get("sel_end_date").toString());
				
			}else{
				//换算本月有多少天
				if(byear_month.endsWith("01") || byear_month.endsWith("03") || byear_month.endsWith("05") || 
						byear_month.endsWith("07") || byear_month.endsWith("08") || byear_month.endsWith("10") || 
						byear_month.endsWith("12")){
					
					end_day = 31;
				}else if(byear_month.endsWith("02")){
					if(Integer.parseInt(byear_month.substring(0, 4)) % 4 == 0){

						end_day = 29;
					}else{

						end_day = 28;
					}
				}else{

					end_day = 30;
				}
				//换算上月有多少天
				if(endbyear_month.endsWith("01") || endbyear_month.endsWith("03") || endbyear_month.endsWith("05") || 
						endbyear_month.endsWith("07") || endbyear_month.endsWith("08") || endbyear_month.endsWith("10") || 
						endbyear_month.endsWith("12")){
					
					endEnd_day = 31;
				}else if(endbyear_month.endsWith("02")){
					if(Integer.parseInt(endbyear_month.substring(0, 4)) % 4 == 0){
						endEnd_day = 29;
					}else{
						endEnd_day = 28;
					}
				}else{
					endEnd_day = 30;
				}
				if(endEnd_day>end_day){
					endDate=byear_month+"-"+end_day;
					endbyear_month=endbyear_month+"-"+end_day;
				}else if (endEnd_day<end_day){
					endDate=byear_month+"-"+endEnd_day;
					endbyear_month=endbyear_month+"-"+endEnd_day;
				}
				
				pb_date=DateUtil.getDaysByBeinEnd(entityMap.get("begin_date").toString(),endDate);
				
				
				sel_pb_date=DateUtil.getDaysByBeinEnd(entityMap.get("sel_begin_date").toString(),endbyear_month);
			}
			
			List<Map<String, Object>> pb_dateList= new ArrayList<Map<String,Object>>();
				
			for(int i=0;i<pb_date.size();i++){
				Map<String, Object> pb_dateMap= new HashMap<String, Object>();
				pb_dateMap.put("pb_date",pb_date.get(i));
				pb_dateMap.put("sel_pb_date",sel_pb_date.get(i));
				pb_dateList.add(pb_dateMap);
				}
			List<String> checkList=new ArrayList<String>();
		
			 checkList=hrSchedulingMapper.queryCount(entityMap);
			/*if(checkList!=null && checkList.size()>0){
				throw new SysException("已经存在班次："+checkList.toString());
			}*/
			if(entityMap.get("is_bh").equals("1")){
				hrSchedulingMapper.extendBh(entityMap,pb_dateList);
			}else{
				hrSchedulingMapper.extendNotBh(entityMap,pb_dateList);
			}
			 checkList=hrSchedulingMapper.queryinsertPBCheck(entityMap);
				if( checkList.size()==0){
					//添加排班审核表
					hrSchedulingMapper.insertPBCheck(entityMap);
				}
		
			StringBuilder sb=new StringBuilder();
			sb.append("{\"pb_qj\":\""+newPbQj+"\"");
			sb.append(",\"is_fc\":0");
			sb.append(",\"fc_name\":\"未封存\"");
			sb.append(",\"state\":0");
			sb.append(",\"state_name\":\"未审签\"");
			sb.append(",\"create_user_id\":\""+entityMap.get("user_id")+"\"");
			sb.append(",\"create_user_name\":\""+SessionManager.getUserName()+"\"");
			sb.append(",\"create_date\":\""+createDate+"\"");
			sb.append(",\"msg\":\"继承成功.\",\"state\":\"true\"}");
			
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	
	}

	/**
	 * 查询排班统计
	 */
	@Override
	public String queryPBStatistics(Map<String, Object> mapVo) throws DataAccessException {
		mapVo = queryStatisticsParamRewrite(mapVo);
		if(null == mapVo.get("dept_source")){
			mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		}
		List<Map<String, Object>> list = queryPBStatisticsList(mapVo);
		PageInfo page = new PageInfo(list);
		list = JsonListMapUtil.toListMapLower(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	/**
	 * 打印排班统计
	 */
	@Override
	public List<Map<String, Object>> queryPBStatisticsPrint(Map<String, Object> mapVo) throws DataAccessException {
		mapVo = queryStatisticsParamRewrite(mapVo);
    	mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		return JsonListMapUtil.toListMapLower(queryPBStatisticsList(mapVo));
	}
	
	/**
	 * 查询排班统计（年、月、职工）
	 */
	private List<Map<String, Object>> queryPBStatisticsList(Map<String, Object> mapVo) {
//		// 排班情况（年-月-职工）
//		List<Map<String, Object>> empPBList = (List<Map<String, Object>>) hrSchedulingMapper.queryPBStatisticsEmpPB(mapVo);
//		empPBList = JsonListMapUtil.toListMapLower(empPBList);
//		// 排班统计（年-月-日-职工）
//		List<Map<String, Object>> pbList = (List<Map<String, Object>>) hrSchedulingMapper.queryPBStatistics(mapVo);
//		pbList = JsonListMapUtil.toListMapLower(pbList);
//		
//		// key:年月职工，value:key中 每日排班出勤情况 集合
//		Map<String, List<Map<String, Object>>> pbMap = new HashMap<String, List<Map<String, Object>>>();
//		for (Map<String, Object> row : pbList) {
//			if (pbMap.get(row.get("row_group").toString()) != null) {// row_group: pbList集合“年月职工”组标记
//				pbMap.get(row.get("row_group").toString()).add(row);
//			} else {
//				List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
//				groupList.add(row);
//				pbMap.put(row.get("row_group").toString(), groupList);
//			}
//		}
//
//		// 横向添加到排班情况
//		for (Map<String, Object> map : empPBList) {
//			if (pbMap.get(map.get("row_flag").toString()) != null) {// row_flag: empPBList集合行标记
//				List<Map<String, Object>> rowGroup = (List<Map<String, Object>>) pbMap.get(map.get("row_flag").toString());
//				for (Map<String, Object> row : rowGroup) {
//					map.put("emp_name", row.get("emp_name"));
//					map.put("dept_name", row.get("dept_name"));
//					map.put("pb_dept_" + Integer.valueOf(row.get("pb_day").toString()), row.get("pb_dept"));// 排班部门
//					map.put("cq_dept_" + Integer.valueOf(row.get("pb_day").toString()), row.get("cq_dept"));// 出勤部门
//				}
//			}
//		}
//		return empPBList;
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		if(null == mapVo.get("dept_source")){
			mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		}
		List<Map<String, Object>> list;
		if (sysPage == null || sysPage.getTotal() == -1) {
			list = (List<Map<String, Object>>) hrSchedulingMapper.queryPBStatistics(mapVo);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = (List<Map<String, Object>>) hrSchedulingMapper.queryPBStatistics(mapVo, rowBounds);
		}
		return list;
	}
	
	/**
	 * 查询排班统计，参数重写
	 */
	private Map<String, Object> queryStatisticsParamRewrite(Map<String, Object> param){
		if (param.get("start_date") != null && param.get("start_date").toString() != "") {
			param.put("start_date", DateUtil.stringToDate(param.get("start_date").toString(), "yyyy-MM-dd"));
		}
		if (param.get("end_date") != null && param.get("end_date").toString() != "") {
			param.put("end_date", DateUtil.stringToDate(param.get("end_date").toString(), "yyyy-MM-dd"));
		}
		if (param.get("dept_id") != null && param.get("dept_id").toString() != "") {
			String deptId = param.get("dept_id").toString().split("@")[1];
			param.put("dept_id", deptId);
		}
		return param;
	}
}

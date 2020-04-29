package com.chd.hrp.hr.serviceImpl.attendancemanagement.overtime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrAttdentVacalBalanceMapper;
import com.chd.hrp.hr.dao.attendancemanagement.overtime.HrOvertimeMapper;
import com.chd.hrp.hr.service.attendancemanagement.overtime.HrOvertimeCheckService;
import com.github.pagehelper.PageInfo;



/**
 * 加班项目设置
 * 
 * @author Administrator
 *
 */
@Service("hrOvertimeCheckService")
public class HrOvertimeCheckServiceImpl implements HrOvertimeCheckService {
	private static Logger logger = Logger.getLogger(HrOvertimeCheckServiceImpl.class);
	
	@Resource(name = "hrOvertimeMapper")
	private final HrOvertimeMapper hrOvertimeMapper = null;
	
	@Resource(name = "hrAttdentVacalBalanceMapper")
	private final HrAttdentVacalBalanceMapper hrAttdentVacalBalanceMapper = null;
	
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 加班项目设置查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryCheckLeave(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("user_id", SessionManager.getUserId());

		entityMap.put("dept_source", MyConfig.getSysPara("06103"));
		
		//转换日期
		if(entityMap.get("create_beg_date") != null && !"".equals(entityMap.get("create_beg_date").toString())){
			entityMap.put("create_beg_date", DateUtil.stringToDate(entityMap.get("create_beg_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("create_end_date") != null && !"".equals(entityMap.get("create_end_date").toString())){
			entityMap.put("create_end_date", DateUtil.stringToDate(entityMap.get("create_end_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("overtime_beg_date") != null && !"".equals(entityMap.get("overtime_beg_date").toString())){
			entityMap.put("overtime_beg_date", DateUtil.stringToDate(entityMap.get("overtime_beg_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("overtime_end_date") != null && !"".equals(entityMap.get("overtime_end_date").toString())){
			entityMap.put("overtime_end_date", DateUtil.stringToDate(entityMap.get("overtime_end_date").toString(), "yyyy-MM-dd"));
		}
		
		entityMap.put("is_check", 1);

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String,Object>> list = hrOvertimeMapper.queryOvertime(entityMap, rowBounds);
		@SuppressWarnings("rawtypes")
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 审核
	 */
	@Override
	public String auditOvertimeCheck(Map<String,Object> entityMap) throws DataAccessException {
		try{
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_year", MyConfig.getCurAccYear());
			
			List<Map<String,Object>> updateOList = new ArrayList<Map<String,Object>>();//更新加班表
			List<Map<String,Object>> insertVList = new ArrayList<Map<String,Object>>();//插入账表
		 
			String codes = entityMap.get("overtime_codes").toString();
			entityMap.put("attend_overtime_codes", codes.substring(0, codes.length() - 1));
			
			//获取积休额度上线
			Map<String,Object> topMap = hrOvertimeMapper.queryHosJxNum(entityMap);		
			if(topMap == null || topMap.size() == 0){
				return "{\"error\":\"请设置积休上限.\",\"state\":\"false\"}";
			}
			Float topNum = Float.parseFloat(topMap.get("ATTEND_ACCTOP").toString());//全院控制的积休天数
			
			
			//取出考勤项目为积休的考勤项目
			List<Map<String,Object>> attendList = ChdJson.toListLower(hrOvertimeMapper.getAttendCodeOfJx(entityMap));
			//获取加班单据数据
			List<Map<String,Object>> overList = ChdJson.toListLower(hrOvertimeMapper.queryOvertimeData(entityMap));
			Map<String,Object> uMap =null;
			Map<String,Object> iMap = null;
			float jbdays=0f;
			float bal=0f;
			float add_valid_amt=0f;
			float add_invalid_amt=0f;
			String attend_jx_code=null;
			if(attendList.size()>0){
				attend_jx_code=attendList.get(0).get("attend_code").toString();
			}else{
				return "{\"error\":\"请设置积休对应的考勤项目.\",\"state\":\"false\"}";
			}
			for(Map<String,Object> mapO : overList){
				jbdays=Float.parseFloat(mapO.get("days").toString());
				//获取目前休假额度
				entityMap.put("attend_code",attend_jx_code );
				entityMap.put("emp_id", mapO.get("emp_id"));
				List<Map<String,Object>> bList=ChdJson.toListLower(hrAttdentVacalBalanceMapper.queryHrAttdentVacalBal(entityMap));
				if(bList!=null&&bList.size()>0){
					iMap=bList.get(0);
					bal=Float.parseFloat(iMap.get("bala_amt").toString());
					//更新balance
					iMap.put("group_id", entityMap.get("group_id"));
					iMap.put("hos_id", entityMap.get("hos_id"));
					iMap.put("attend_year", entityMap.get("attend_year"));
					iMap.put("emp_id", mapO.get("emp_id"));
					iMap.put("attend_code",attend_jx_code);
					iMap.put("dec_amt", 0);
					if(topNum>=(jbdays+bal)){
						add_valid_amt=jbdays;
						add_invalid_amt=0;
						iMap.put("add_valid_amt", add_valid_amt);					 
						iMap.put("bala_amt",jbdays );
					}else{
						add_valid_amt=topNum-bal;
						add_invalid_amt=(jbdays+bal)-topNum;
						iMap.put("add_valid_amt", add_valid_amt);
						iMap.put("add_invalid_amt", add_invalid_amt);
						iMap.put("bala_amt",topNum-bal);
					}
					hrAttdentVacalBalanceMapper.update(iMap);
				}else{
					//创建balance表
					iMap=new HashMap<String,Object>();
					iMap.put("group_id", entityMap.get("group_id"));
					iMap.put("hos_id", entityMap.get("hos_id"));
					iMap.put("attend_year", entityMap.get("attend_year"));
					iMap.put("emp_id", mapO.get("emp_id"));
					iMap.put("attend_code", attend_jx_code);
					iMap.put("limit", 0);
					iMap.put("dec_amt", 0);
					iMap.put("del_amt", 0);
					if(topNum>=jbdays){		
						add_valid_amt=jbdays;
						add_invalid_amt=0;
						iMap.put("add_valid_amt", add_valid_amt);
						iMap.put("add_invalid_amt",  add_invalid_amt);
						iMap.put("bala_amt",jbdays );
					}else{
						add_valid_amt=topNum-bal;
						add_invalid_amt=(jbdays+bal)-topNum;
						iMap.put("add_valid_amt", add_valid_amt);
						iMap.put("add_invalid_amt", add_invalid_amt);
						iMap.put("bala_amt",topNum);
					}
					insertVList.add(iMap);
					
				}
				uMap=new HashMap<String,Object>();
				uMap.put("add_valid_amt", add_valid_amt);
				uMap.put("overtime_code", mapO.get("overtime_code"));
				uMap.put("add_invalid_amt",  add_invalid_amt);
				
				updateOList.add(uMap);
				//1.获取加班实际天数
				//2.修改加班申请表状态
				//3.计算加班产生的积休有效天数
				//4.修改帐表的增加值
				
			}
			//更新加班表
			if(updateOList.size() > 0){
				entityMap.put("state", 2);
				entityMap.put("checker", SessionManager.getUserId());
				entityMap.put("check_date", new Date());
				
				hrOvertimeMapper.updateBatchStateByCheck(updateOList, entityMap);
			}
			if(insertVList.size()>0){
				hrAttdentVacalBalanceMapper.addBatchBalance(insertVList,entityMap);
			}
			 
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	
	/**
	 * 销审
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String unAuditOvertimeCheck(Map<String,Object> entityMap) throws DataAccessException {
		try{
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("attend_year", MyConfig.getCurAccYear());
			
			//根据传递单号查询账表信息
			//List<Map<String,Object>> banlaceList = ChdJson.toListLower(hrOvertimeMapper.queryBanlaceByOverCode(entityMap));
			
			entityMap.put("state", 1);
			entityMap.put("checker", "");
			entityMap.put("check_date", "");
			entityMap.put("add_valid_amt", 0);
			entityMap.put("add_invalid_amt", 0);
			//更新状态
			hrOvertimeMapper.updateStateByCheck(entityMap);
			List<Map<String,Object>> attendList = ChdJson.toListLower(hrOvertimeMapper.getAttendCodeOfJx(entityMap));
			String attend_jx_code=null;
			if(attendList.size()>0)
				attend_jx_code=attendList.get(0).get("attend_code").toString();
			//更新账表
			List<Map<String,Object>> overList = ChdJson.toListLower(hrOvertimeMapper.queryOvertimeData(entityMap));
			for(Map<String,Object> mapO : overList){
				
				entityMap.put("emp_id", mapO.get("emp_id"));
				entityMap.put("attend_code", attend_jx_code);
				entityMap.put("add_valid_amt", Float.parseFloat(mapO.get("add_valid_amt").toString())*(-1));
				entityMap.put("add_invalid_amt",  Float.parseFloat(mapO.get("add_invalid_amt").toString())*(-1));
				entityMap.put("bala_amt",Float.parseFloat(mapO.get("add_valid_amt").toString())*(-1));
				hrAttdentVacalBalanceMapper.update(entityMap);
			}
//			if(banlaceList.size() > 0){
//				hrAttdentVacalBalanceMapper.updateBatch(banlaceList,entityMap);
//			}
			
			
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 退回
	 */
	@Override
	public String reAuditOvertimeCheck(Map<String,Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("state", 0);
			entityMap.put("oper", "");
			entityMap.put("oper_date", "");
			entityMap.put("back_reason", "审核不通过！");
			
			hrOvertimeMapper.updateStateByBack(entityMap);
			return "{\"msg\":\"退回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		throw new SysException(e.getMessage());
		}
	}


}

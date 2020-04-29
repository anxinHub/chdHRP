/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.termend.monthend;

import java.util.Date;
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
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckItemMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.dao.termend.AccTermendTemplateMapper;
import com.chd.hrp.acc.dao.termend.monthend.AccClosingMapper;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.service.termend.monthend.AccClosingService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.service.base.SysConfigService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 结账<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accClosingService")
public class AccClosingServiceImpl implements AccClosingService {

	private static Logger logger = Logger.getLogger(AccClosingServiceImpl.class);
	
	@Resource(name = "accClosingMapper")
	private final AccClosingMapper accClosingMapper = null;
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;
	@Resource(name = "accCheckItemMapper")
	private final AccCheckItemMapper accCheckItemMapper = null;
	@Resource(name = "accTermendTemplateMapper")
	private final AccTermendTemplateMapper accTermendTemplateMapper = null;
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;
	
	@Override
	public String queryAccClosing(Map<String, Object> entityMap) throws DataAccessException {
		List<AccYearMonth> list = accClosingMapper.queryAccClosing(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String queryAccClosingCheckStart(Map<String, Object> entityMap) throws DataAccessException {
		int flag = 0;
		try {
			//添加校验数据表
			entityMap.remove("table_name");
			entityMap.put("table_name", "acc_year_month");
			//添加是否结账状态
			entityMap.remove("cvalue");
			entityMap.put("cvalue", "1");
			if("1".equals(entityMap.get("check_wage").toString())){
				//检查工资是否结账
				entityMap.remove("cname");
				entityMap.put("cname", "wage_flag");
				flag = accClosingMapper.queryAccClosingCheck(entityMap);
				if(flag == 0){
					return "{\"error\":\"工资管理没有结账！\"}";
				}
			}
			if("1".equals(entityMap.get("check_cash").toString())){
				//检查出纳是否结账
				entityMap.remove("cname");
				entityMap.put("cname", "cash_flag");
				flag = accClosingMapper.queryAccClosingCheck(entityMap);
				if(flag == 0){
					return "{\"error\":\"出纳账管理没有结账！\"}";
				}
			}
			return "{\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"结账校验失败 数据库异常 请联系管理员! 错误编码  queryAccClosingCheckStart\"}";
		}
	}

	@Override
	public String queryAccClosingCheckVouch(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouch> list = accClosingMapper.queryAccClosingCheckVouch(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	@Override
	public String queryAccClosingCheckLederToCheck(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouch> list = accClosingMapper.queryAccClosingCheckLederToCheck(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryAccClosingCheckLederToVouch(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccVouch> list = accClosingMapper.queryAccClosingCheckLederToVouch(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String getAccClosingYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		String year_month = accYearMonthMapper.queryAccYearMonthNow(entityMap);
		return "{\"year_month\":\""+year_month+"\"}";
	}
	
	@Override
	public String getAccClosingCheckNamesBySubj(Map<String, Object> entityMap) throws DataAccessException {
		String checkNames = accClosingMapper.getAccClosingCheckNamesBySubj(entityMap);
		return checkNames;
	}
	
	@Override
	public String confirmAccClosing (Map<String, Object> entityMap) throws DataAccessException{

		//获取下一个会计期间
		String acc_year_next = "", acc_month_next = "";
		
		try {
			//判断本期间是否已结账
			entityMap.put("flag_filed", "acc_flag");
			Integer acc_flag = accClosingMapper.existsAccClosing(entityMap);
			if(acc_flag == 0){
				return "{\"error\":\"【"+entityMap.get("acc_year")+entityMap.get("acc_month")+"】已结账，不能结账，请刷新页面重试！\",\"state\":\"false\"}";
			}else if(acc_flag > 1){
				return "{\"error\":\"【"+entityMap.get("acc_year")+entityMap.get("acc_month")+"】不是当前会计期间，不能结账，请刷新页面重试！\",\"state\":\"false\"}";
			}
			
			Map<String,Object> map = new HashMap<String, Object>();
			
			if("12".equals(entityMap.get("acc_month"))){
				//如果为12月份,则需要建立下年的年初账即00月份
				acc_year_next = String.valueOf(Integer.valueOf(entityMap.get("acc_year").toString())+1);
				acc_month_next = "01";
			}else{
				acc_year_next = entityMap.get("acc_year").toString();
				acc_month_next = "0"+String.valueOf(Integer.valueOf(entityMap.get("acc_month").toString())+1);
				acc_month_next = acc_month_next.substring(acc_month_next.length()-2, acc_month_next.length());
			}
			entityMap.put("acc_year_next", acc_year_next);
			entityMap.put("acc_month_next", acc_month_next);
			
			map.put("acc_year_next", acc_year_next);
			map.put("acc_month_next", acc_month_next);
			map.put("acc_year", acc_year_next);
			map.put("acc_month", acc_month_next);
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			
			
				List<AccYearMonth> lists = accYearMonthMapper.queryAccYearMonth(map);
				
				if(lists.size()<=0){
					
					return "{\"error\":\"【"+entityMap.get("acc_year_next")+"】年未发现会计期间！\",\"state\":\"false\"}";
				}
			
				List<AccSubj> list = accSubjMapper.queryAccSubj(map);
				
				if(list.size()<=0){
					
					return "{\"error\":\"【"+entityMap.get("acc_year_next")+"】年未发现会计科目！\",\"state\":\"false\"}";
				}
				
				//有未记账凭证不能结账
				Integer flag = accClosingMapper.queryAccClosingUnAccountVouch(entityMap);
				if(flag != null && flag > 0 ){
					return "{\"error\":\"本月有未记账凭证，不能结账！\",\"state\":\"false\"}";
				}
				//建立下一个会计期间期初账
				accClosingMapper.addAccClosingNextLeder(entityMap);
				//获取核算项字段
				Map<String,Object> utilMap=new HashMap<String,Object>();
				utilMap.put("group_id", entityMap.get("group_id"));
				utilMap.put("hos_id", entityMap.get("hos_id"));
				utilMap.put("copy_code", entityMap.get("copy_code"));
				entityMap.put("strCheck", accTermendTemplateMapper.queryAllSubjCheckColumn(utilMap));
				//建立下一个会计期间期初辅助账
				accClosingMapper.addAccClosingNextCheckLeder(entityMap);
				//年结
				if(("01").equals(acc_month_next)){
					//复制未核销的往来账
					//获取核算项字段
					utilMap.put("table", "a");//使用table参数可以获得a.zcheck1,a.zcheck2格式的辅助核算字段
					entityMap.put("strCheck1", accTermendTemplateMapper.queryAllSubjCheckColumn(utilMap));
					accClosingMapper.copyAccClosingVeriVouchCkeck(entityMap);
				}
				//修改期间表(acc_year_month)会计结账标志
				entityMap.put("acc_flag", "1");//结账标志
				entityMap.put("acc_user", SessionManager.getUserId());//结账用户ID
				entityMap.put("acc_date", new Date());//结账时间(取系统时间)
				accClosingMapper.updateAccClosing(entityMap);
				
				
				//加载会计期间
				sysConfigService.queryYearMonthInit(entityMap);
				
				/*
				//重写session数据
				Map<String, Object> accMap = new HashMap<String, Object>();
				accMap.put("group_id", entityMap.get("group_id"));
				accMap.put("hos_id", entityMap.get("hos_id"));
				accMap.put("copy_code", entityMap.get("copy_code"));
				accMap.put("acc_year", acc_year_next);
				SessionManager.getSession().setAttribute("acc_year_month_map", sysBaseService.queryAccYearMonthMap(accMap));
				//SessionManager.getSession().setAttribute("acc_year_month", SessionManager.getSysYearMonth("acc_flag"));
				SessionManager.getSession().setAttribute("acc_year_month", acc_year_next+acc_month_next);
				SessionManager.getSession().setAttribute("acc_year", acc_year_next);*/
				
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"结账失败\"}");
			//return "{\"error\":\"结账失败 数据库异常 请联系管理员! 错误编码  confirmAccClosing\"}";
		}
		
		return "{\"msg\":\"结账完毕\",\"state\":\"true\",\"year_month\":\""+acc_year_next + acc_month_next+"\"}";
	}
	
	@Override
	public String reconfirmAccClosing (Map<String, Object> entityMap) throws DataAccessException{
		//获取下一个会计期间
		String acc_year_next = "", acc_month_next = "";
		try {
			
			if("01".equals(entityMap.get("acc_month"))){
				//如果为12月份,则需要建立下年的年初账即00月份
				acc_year_next = String.valueOf(Integer.valueOf(entityMap.get("acc_year").toString())-1);
				acc_month_next = "12";
			}else{
				acc_year_next = entityMap.get("acc_year").toString();
				acc_month_next = "0"+String.valueOf(Integer.valueOf(entityMap.get("acc_month").toString())-1);
				acc_month_next = acc_month_next.substring(acc_month_next.length()-2, acc_month_next.length());
			}
			//校验是否可以反结账 
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("group_id", entityMap.get("group_id"));
			checkMap.put("hos_id", entityMap.get("hos_id"));
			checkMap.put("copy_code", entityMap.get("copy_code"));
			checkMap.put("acc_year", acc_year_next);
			checkMap.put("acc_month", acc_month_next);
			checkMap.put("flag_filed", "acc_flag");
			Integer acc_flag = accClosingMapper.existsAccClosing(checkMap);
			if(acc_flag > 0){
				return "{\"error\":\"【"+checkMap.get("acc_year")+checkMap.get("acc_month")+"】未结账，不能反结，请刷新页面重试！\",\"state\":\"false\"}";
			}
			//当前期间为会计初始期间不能反结账
			entityMap.remove("table_name");
			entityMap.remove("cvalue");
			entityMap.remove("cname");
			entityMap.put("table_name", "sys_mod_start");
			entityMap.put("cname", "mod_code");
			entityMap.put("cvalue", "01");
			Integer flag = accClosingMapper.queryAccClosingCheck(entityMap);
			if(flag != null && flag > 0){
				return "{\"error\":\"当前期间为初始会计期间不能反结账！\"}";
			}
			//当前期间记账后不能进行反结账
			entityMap.remove("table_name");
			entityMap.remove("cvalue");
			entityMap.remove("cname");
			entityMap.put("table_name", "acc_vouch");
			entityMap.put("cname", "state");
			entityMap.put("cvalue", "99");
			flag = accClosingMapper.queryAccClosingCheck(entityMap);
			if(flag != null && flag > 0){
				return "{\"error\":\"当前期间存在已记账凭证不能进行反结账！\"}";
			}
			//若为年度反结账则需要判断当前年度初始往来账是否存在核销记录
			if("01".equals(entityMap.get("acc_month"))){
				entityMap.remove("table_name");
				entityMap.remove("cname");
				entityMap.remove("cvalue");
				entityMap.remove("cname1");
				entityMap.remove("cvalue1");
				entityMap.put("table_name", "acc_vouch_check");
				entityMap.put("cname", "is_init");
				entityMap.put("cvalue", "1");
				entityMap.put("cname1", "bal_amt");
				entityMap.put("cvalue1", "0");
				flag = accClosingMapper.queryAccClosingCheck(entityMap);
				if(flag > 0){
					return "{\"error\":\"当前年度的往来初始账存在核销记录不能进行反结账！\"}";
				}
			}
			//删除当前会计期间账
			entityMap.remove("table_name");
			entityMap.put("table_name", "acc_leder");
			accClosingMapper.deleteAccClosing(entityMap);
			
			//删除当前会计期间辅助账
			entityMap.remove("table_name");
			entityMap.put("table_name", "acc_leder_check");
			accClosingMapper.deleteAccClosing(entityMap);
			
			//如果为年度反结则清空结转的往来数据
			if("01".equals(entityMap.get("acc_month"))){
				entityMap.remove("table_name");
				entityMap.put("table_name", "acc_vouch_check");
				accClosingMapper.deleteAccClosing(entityMap);
			}
			//修改期间表(acc_year_month)上一期间会计结账标志
			entityMap.put("acc_flag", 0);//结账标志
			entityMap.put("acc_user", null);//结账用户ID
			entityMap.put("acc_date", null);//结账时间(取系统时间)
			entityMap.put("acc_year", acc_year_next);
			entityMap.put("acc_month", acc_month_next);
			accClosingMapper.updateAccClosing(entityMap);
			
			//加载会计期间
			sysConfigService.queryYearMonthInit(entityMap);
			
			//重写session数据
			/*Map<String, Object> accMap = new HashMap<String, Object>();
			accMap.put("group_id", entityMap.get("group_id"));
			accMap.put("hos_id", entityMap.get("hos_id"));
			accMap.put("copy_code", entityMap.get("copy_code"));
			accMap.put("acc_year", acc_year_next);
			SessionManager.getSession().setAttribute("acc_year_month_map", sysBaseService.queryAccYearMonthMap(accMap));
			//SessionManager.getSession().setAttribute("acc_year_month", SessionManager.getSysYearMonth("acc_flag"));
			SessionManager.getSession().setAttribute("acc_year_month", acc_year_next + acc_month_next);
			SessionManager.getSession().setAttribute("acc_year", acc_year_next);*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"反结账失败\"}");
			//return "{\"error\":\"反结账失败 数据库异常 请联系管理员! 错误编码  reconfirmAccClosing\"}";
		}
		
		return "{\"msg\":\"反结账完毕\",\"state\":\"true\",\"year_month\":\""+acc_year_next + acc_month_next+"\"}";
	}

	@Override
	public String queryAccClosingCountVouch(Map<String, Object> entityMap) throws DataAccessException {
		String conutMsg = accClosingMapper.queryAccClosingCountVouch(entityMap);
		return conutMsg;
	}
}

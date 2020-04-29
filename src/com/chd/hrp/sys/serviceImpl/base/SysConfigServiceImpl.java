package com.chd.hrp.sys.serviceImpl.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.entity.MyAccYearMonth;
import com.chd.hrp.sys.service.base.SysConfigService;

/*
 * 系统全局变量
*/
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService{

	private static Logger logger = Logger.getLogger(SysConfigService.class);
	
	// 引入DAO操作
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;
	
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;
	
	@Autowired
	ServletContext servletContext;
	
	/*
	 * 会计期间，格式：Map<集团医院账套，MyAccYearMonth>
	*/
	@Override
	public synchronized void queryYearMonthInit(Map<String, Object> mapVo) {
		
		//查询会计期间
		List<Map<String,Object>> list=accYearMonthMapper.queryYearMonthByInit(mapVo);
		
		Map<String,MyAccYearMonth> map1=(Map<String, MyAccYearMonth>) servletContext.getAttribute("acc_year_month");
		if(map1==null){
			map1=new HashMap<String,MyAccYearMonth>();
		}
		
		if(list!=null && list.size()>0){
			MyAccYearMonth mym=null;
			for(Map<String,Object> map :list){
				mym=new MyAccYearMonth();
				mym.setMinDate(map.get("min_date").toString());
				mym.setMaxDate(map.get("max_date").toString());
				mym.setCurYearMonthAcc(map.get("cur_acc")==null?null:map.get("cur_acc").toString());
				mym.setCurYearMonthWage(map.get("cur_wage")==null?null:map.get("cur_wage").toString());
				mym.setCurYearMonthCash(map.get("cur_cash")==null?null:map.get("cur_cash").toString());
				mym.setCurYearMonthMat(map.get("cur_mat")==null?null:map.get("cur_mat").toString());
				mym.setCurYearMonthMed(map.get("cur_med")==null?null:map.get("cur_med").toString());
				mym.setCurYearMonthAss(map.get("cur_ass")==null?null:map.get("cur_ass").toString());
				mym.setCurYearMonthIss(map.get("cur_iss")==null?null:map.get("cur_iss").toString());
				mym.setCurYearMonthCost(map.get("cur_cost")==null?null:map.get("cur_cost").toString());
				String key=map.get("group_id").toString()+map.get("hos_id").toString()+map.get("copy_code").toString();
				map1.put(key,mym);
				
			}
		}
		servletContext.setAttribute("acc_year_month",map1);

	}
	
	
	/*
	 * 系统参数，格式：Map<集团医院账套，MyAccYearMonth>
	*/
	@Override
	public synchronized  void querySysParaInit(Map<String,Object> map) {
		
		//查询系统参数
		List<Map<String,Object>> list=accParaMapper.queryAccParaInit(map);
		
		Map<String,Object> appMap=(Map<String, Object>) servletContext.getAttribute("sys_para");
		if(appMap==null){
			appMap=new HashMap<String,Object>();
		}
		
		String key=null;
		if(list!=null && list.size()>0){
			for(Map<String,Object> m :list){
				
				key=m.get("group_id").toString()+m.get("hos_id").toString()+m.get("copy_code").toString()+m.get("para_code").toString();
				appMap.put(key,m.get("para_value"));
			}
		}
		servletContext.setAttribute("sys_para",appMap);

	}
	
}

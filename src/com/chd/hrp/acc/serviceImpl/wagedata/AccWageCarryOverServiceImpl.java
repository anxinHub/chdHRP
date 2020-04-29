/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.hrp.acc.dao.wagedata.AccWageCarryOverMapper;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.service.wagedata.AccWageCarryOverService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.service.base.SysConfigService;

/**
* @Title. @Description.
* 结转工资<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageCarryOverService")
public class AccWageCarryOverServiceImpl implements AccWageCarryOverService {

	private static Logger logger = Logger.getLogger(AccWageCarryOverServiceImpl.class);
	
	@Resource(name = "accWageCarryOverMapper")
	private final AccWageCarryOverMapper accWageCarryOverMapper = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;
	
	/**
	 * @Description 
	 * 结转工资<BR> 更新AccYearMonth
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccYearMonth(Map<String,Object> entityMap)throws DataAccessException{
		
		List<AccYearMonth> list = new ArrayList<AccYearMonth>();
		
		try {

				String now_time="";
				
				StringBuffer sql= new StringBuffer();
				
				if("1".equals(entityMap.get("wage_flag"))){
					
					sql.append("select acc_year,acc_month from acc_year_month where group_id = "+entityMap.get("group_id"));
					
					sql.append(" and hos_id ="+entityMap.get("hos_id")+" and copy_code = "+entityMap.get("copy_code"));
					
					sql.append(" and (wage_flag=0 or wage_flag is null) order by begin_date ");
					
					entityMap.put("acc_sql", sql);
					
					list=accWageCarryOverMapper.queryNextAccYearMonth(entityMap);
					
					if(list.size()>1){
						
						accWageCarryOverMapper.updateAccYearMonth(entityMap);
						
						//now_time=accWageCarryOverMapper.queryAccYearMonthNow(entityMap);
						
						now_time=list.get(1).getAcc_year()+"年"+list.get(1).getAcc_month()+"月";
						
						//加载会计期间
						sysConfigService.queryYearMonthInit(entityMap);
						
						/*entityMap.put("acc_year", list.get(1).getAcc_year());
						
						SessionManager.getSession().setAttribute("acc_year_month_map", queryAccYearMonth());
						
						SessionManager.getSession().setAttribute("acc_mod_start_map", queryModStartMap());
						
						SessionManager.getSession().setAttribute("wage_year_month", SessionManager.getSysYearMonth("wage_flag"));*/
					
						return "{\"msg\":\"结转成功.\",\"state\":\"true\",\"date\":\""+now_time+"\"}";
						
					}
					
					return "{\"msg\":\"请维护会计期间.\",\"state\":\"false\"}";
				
				}else{
					
					sql.append("select acc_year,acc_month from acc_year_month where group_id = "+entityMap.get("group_id"));
					
					sql.append(" and hos_id ="+entityMap.get("hos_id")+" and copy_code = "+entityMap.get("copy_code"));
					
					sql.append(" and wage_flag=1 order by begin_date desc ");
					
					entityMap.put("acc_sql", sql);
					
					list=accWageCarryOverMapper.queryNextAccYearMonth(entityMap);
					
					if(list.size()>0){
						
						accWageCarryOverMapper.updateAccYearMonth(entityMap);
						
						//加载会计期间
						sysConfigService.queryYearMonthInit(entityMap);
						
						entityMap.put("acc_year", list.get(0).getAcc_year());
						
						/*SessionManager.getSession().setAttribute("acc_year_month_map", queryAccYearMonth());
						
						SessionManager.getSession().setAttribute("acc_mod_start_map", queryModStartMap());
						
						SessionManager.getSession().setAttribute("wage_year_month", SessionManager.getSysYearMonth("wage_flag"));*/
					
						
						if(list.size()==1){
							
							return "{\"msg\":\"反结成功.\",\"state\":\"true\"}";
						}
						
						now_time=list.get(1).getAcc_year()+"年"+list.get(1).getAcc_month()+"月";
						
						return "{\"msg\":\"反结成功.\",\"state\":\"true\",\"date\":\""+now_time+"\"}";
						
					}
					
					return "{\"msg\":\"请维护会计期间.\",\"state\":\"false\"}";
				
				}
				
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccYearMonth\"}";

		}
	}
	
	@Override
	public String queryAccYearMonthNow(Map<String, Object> entityMap) {
		
		return accWageCarryOverMapper.queryAccYearMonthNow(entityMap);
	}
	
	public String queryAccYearMonthLast(Map<String, Object> entityMap) {
		
		return accWageCarryOverMapper.queryAccYearMonthLast(entityMap);
	}
	
	/**
	 * 获取当期系统的会计期间集合
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private Map<String, Object> queryAccYearMonth() throws DataAccessException {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//mapVo.put("acc_year", SessionManager.getAcctYear());

		return sysBaseService.queryAccYearMonthMap(mapVo);

	}
	/**
	 * 获取当期系统的会计期间集合
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	private Map<String, Object> queryModStartMap() throws DataAccessException {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//mapVo.put("acc_year", SessionManager.getAcctYear());
		
		return sysBaseService.queryModStartMap(mapVo);
		
	}
}

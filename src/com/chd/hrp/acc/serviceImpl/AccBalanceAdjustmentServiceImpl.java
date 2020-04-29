package com.chd.hrp.acc.serviceImpl;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.dao.tell.AccTellDayMapper;
import com.chd.hrp.acc.dao.tell.AccTellMapper;
import com.chd.hrp.acc.entity.AccBankCheck;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.entity.AccTellDay;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.service.AccBalanceAdjustmentService;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.base.SysConfigService;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;



@Service("accBalanceAdjustmentService")
public class AccBalanceAdjustmentServiceImpl implements AccBalanceAdjustmentService{

	private static Logger logger = Logger.getLogger(AccBalanceAdjustmentServiceImpl.class);
	
	@Resource(name = "accTellMapper")
	private final AccTellMapper accTellMapper = null;
	
	@Resource(name = "accTellDayMapper")
	private final AccTellDayMapper accTellDayMapper = null;
	
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;
	
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;
	@Override
	public String addAccBalanceAdjustment(Map<String,Object> entityMap)throws DataAccessException{
		
		AccBankCheck AccBalanceAdjustment = queryAccBalanceAdjustmentByCode(entityMap);

		if (AccBalanceAdjustment != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			//AccBankCheckMapper.addAccBankCheck(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBankCheck\"}";

		}

	}
	
	@Override
	public String addBatchAccBalanceAdjustment(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			//AccBankCheckMapper.addBatchAccBankCheck(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccBankCheck\"}";

		}
	}
	
	/**
	 *查询 当前出纳日 上次结账日 
	 **/
	@Override
	public String queryAccBalanceAdjustment(Map<String,Object> entityMap) throws DataAccessException{
		
		//List<AccTellDay> accTellDay = accTellDayMapper.queryAccTellDay(entityMap);
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		//当前出纳日
		if("0".equals(entityMap.get("cash_flag"))){
			
			List<AccTellDay> accTellDay = accTellDayMapper.queryAccTellDay(entityMap);
			
			if(accTellDay.size()>0){
				
				return sdf.format(accTellDay.get(0).getCash_date());
				
			}
			
		}
		
		
		//上次结账日
		if("1".equals(entityMap.get("cash_flag"))){
			
			List<AccTellDay> accAccountDay = accTellDayMapper.queryAccTellDay(entityMap);
			
			if(accAccountDay.size()>0){
				
				return sdf.format(accAccountDay.get(accAccountDay.size()-1).getCash_date());
				
			}
			
		}
		
		return null;
	}
	
	@Override
	public String queryAccBalanceAdjustmentByMonth(Map<String,Object> entityMap) throws DataAccessException{
		//当前出纳月
		if("0".equals(entityMap.get("cash_flag"))){
			
			List<AccYearMonth> list =	accYearMonthMapper.queryAccYearMonth(entityMap);
			
			if(list.size()>0){
				
				String date = list.get(0).getAcc_year()+"年"+list.get(0).getAcc_month()+"月";
				
				return date;
			}
			
		}
		//上次结账月
		if("1".equals(entityMap.get("cash_flag"))){
			
			List<AccYearMonth> list =	accYearMonthMapper.queryAccYearMonth(entityMap);
			
			if(list.size()>0){
				
				String time = list.get(list.size()-1).getAcc_year()+"年"+list.get(list.size()-1).getAcc_month()+"月";
				
				return time;
				
			}
		}
		return null;
	}
	
	@Override
	public AccBankCheck queryAccBalanceAdjustmentByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return null;//AccBankCheckMapper.queryAccBankCheckByCode(entityMap);
		
	}
	
	@Override
	public String deleteBatchAccBalanceAdjustment(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = 0;//AccBankCheckMapper.deleteBatchAccBankCheck(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccBankCheck\"}";

		}
		
	}
	
	/**
	*<BR>
	*日结：出纳反结
	*/
	@Override
  public String queryUndailyAccBalanceAdjustment(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			entityMap.put("acc_year", entityMap.get("acc_Year_Month").toString().substring(0, 4));
			
			entityMap.put("acc_month", entityMap.get("acc_Year_Month").toString().substring(5, 7));
			
			entityMap.put("cash_flag", "1");
			
			AccYearMonth accYearMonth = accYearMonthMapper.queryAccYearMonthByCode(entityMap);//查询是否月结
			
			if(accYearMonth != null){//判断是否月结
				
				return "{\"error\":\"反结失败,当前出纳日期所在的会计期间已月结.\"}";
				
			}
			
			entityMap.remove("cash_flag");
			
			entityMap.put("cash_flag", "0");
			
			entityMap.put("mod_code", "0101");
			
			ModStart modStart = modStartService.queryModStartByCode(entityMap);
			
			String modStartTime = modStart.getStart_year()+"-"+modStart.getStart_month()+"-01";
			
			if(entityMap.get("acc_Year_Month").toString().equals(modStartTime)){//判断是否小于等于出纳账系统启用时间
				
				return "{\"error\":\"反结失败,反结日期不能小于等于出纳账系统启用时间.\"}";
				
			}
			
			entityMap.put("cash_date",entityMap.get("acc_Year_Month").toString().replaceAll("-","/"));
			
			entityMap.put("create_user", SessionManager.getUserCode());
			
			entityMap.put("create_date", sdf.format(new Date()));
			
			entityMap.put("cash_state", "1");
			
			accTellDayMapper.updateAccTellDay(entityMap);//修改结账标记
			
			entityMap.remove("cash_flag");
			
			entityMap.put("cash_flag", "1");
			
			entityMap.remove("acc_month");
			
			List<AccTellDay> list = accTellDayMapper.queryAccTellDay(entityMap);//查询当月已结账的所有数据
			
			String cashDate = sdf.format(list.get(list.size()-1).getCash_date());//取上次结账日
						
			return "{\"msg\":\"反结成功.\",\"state\":\"true\",\"cashDate\":\""+cashDate+"\"}";
						
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"反结失败 数据库异常 请联系管理员! 错误编码  queryDailyAccBalanceAdjustment\"}";

		}
    }
	
	/**
	*<BR>
	*日结：出纳日结
	*/
	@Override
	  public String queryDailyAccBalanceAdjustment(Map<String, Object> entityMap) throws DataAccessException {
			
			try {
				/*
				 * 没有确认的数据不允许日结
				 */
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//获取会计年度
				String acc_year = entityMap.get("acc_time").toString().substring(0, 4);
				//当前出纳日期
				String acc_date = entityMap.get("acc_time").toString();
				//取当前月
				String now_month = acc_date.substring(5, 7).toString();
				
				entityMap.put("acc_year", acc_year);
				
				entityMap.put("is_con", "0");
				
				List<AccTell> accTellList = accTellMapper.queryAccTell(entityMap);//查询是否确认
				
				if(accTellList.size() > 0){
					
					return "{\"error\":\"当前出纳日期的出纳账已确认后才能进行日结.\"}";
				}
				
				/*
				 * 如果是当年会计年度最后一天，查询下年期间是否添加，不存在不允许日结
				 */
				List<AccYearMonth> accYearMonthList = accYearMonthMapper.queryAccYearMonth(entityMap);//查询当前年度会计期间
				
				String accEndDate = accYearMonthList.get(accYearMonthList.size()-1).getEnd_date();//当前年度会计期间最后一天
				
				if(acc_date.equals(accEndDate)){
					
					//获取下年
					int next_year = Integer.parseInt(acc_year)+1;
					
					entityMap.put("acc_year",next_year);
					
					List<AccYearMonth> nextYearMonthList = accYearMonthMapper.queryAccYearMonth(entityMap);//查询下年会计期间是否已经添加
					
					if(nextYearMonthList.size() == 0){
						
						return "{\"error\":\"下年度会计期间不存在,本年度会计期间最后一天不允许日结.\"}";
						
					}
				}
				
				
				/*
				 * 日结,修改结账标记
				 */
				entityMap.put("cash_flag", "1");
				
				entityMap.put("cash_state", "0");
				
				entityMap.put("acc_month", now_month);
				
				entityMap.put("create_user", SessionManager.getUserCode());
				
				entityMap.put("create_date", sdf.format(new Date()));
				
				entityMap.put("cash_date",acc_date.replaceAll("-","/"));
				
				AccYearMonth accYearMonth = accYearMonthMapper.queryAccYearMonthByCode(entityMap);
				
				if(accYearMonth != null){
					
					return "{\"error\":\"已月结的数据不允许日结.\"}";
				}
				
				accTellDayMapper.updateAccTellDay(entityMap);//修改结账标记
				
				/*
				 * 如果是月末,添加下月出纳日结初始数据
				 */
				//获取当月最大天数
				String max_day = DateUtil.getMaxMonthDate(acc_year, now_month);
				
				//获取当月月末
				String monthLastDay = acc_year+"-"+now_month+"-"+max_day;
				
				//如果当前出纳日期等于本月最后一天
				if(acc_date.equals(monthLastDay)){
					entityMap.put("cash_state", 0);
					
					entityMap.put("cash_flag", 1); 
					//修改期间结账标记
					accYearMonthMapper.updateAccYearMonth(entityMap);
					//当前年月
					String now_yearMonth = acc_year+now_month;
					//获取下月
					String next_yearMonth = DateUtil.getNextYear_Month(now_yearMonth);
					
					String year = next_yearMonth.substring(0, 4);
					
					String month = next_yearMonth.substring(4);
					
					entityMap.put("acc_year", year);
					
					entityMap.put("acc_month", month);
					
					//获取下月是否有出纳日结初始数据
					entityMap.remove("cash_flag");
					
					List<AccTellDay> accTellDayList = accTellDayMapper.queryAccTellDay(entityMap);
					//如果没有下月出纳日结初始数据,就添加
					if(accTellDayList.size() == 0){
						
						entityMap.put("year", year);
						
						entityMap.put("month", month);
						
						addBatchAccTellDay(entityMap);//批量添加下月初始数据
					}
			        
			        //当前出纳日期 回显
			        List<AccTellDay> nextMonthlist = accTellDayMapper.queryAccTellDay(entityMap);//查询当前出纳日：取第一天
			        
			        String accDate = sdf.format(nextMonthlist.get(0).getCash_date());
					
					return "{\"msg\":\"日结成功.\",\"state\":\"true\",\"date\":\""+accDate+"\"}";
					
				}
				entityMap.put("cash_flag", "0");
				
				List<AccTellDay> accTellDaylist = accTellDayMapper.queryAccTellDay(entityMap);
				
				String accDate = sdf.format(accTellDaylist.get(0).getCash_date());
				
				return "{\"msg\":\"日结成功.\",\"state\":\"true\",\"date\":\""+accDate+"\"}";
						
			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"日结失败 数据库异常 请联系管理员! 错误编码  queryUndailyAccBalanceAdjustment\"}";

			}
	    }
	

	@Override
	public String updateAccBalanceAdjustment(Map<String,Object> entityMap)throws DataAccessException{

		try {

			//AccBankCheckMapper.updateAccBankCheck(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankCheck\"}";

		}
	}
	
	
	@Override
	public String updateBatchAccBalanceAdjustment(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			//AccBankCheckMapper.updateBatchAccBankCheck(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccBalanceAdjustment\"}";

		}
		
	}
	
		/**
		*<BR>
		*月结：出纳月结
		*/
	@Override
	public String queryDailyAccBalanceAdjustmentByMonth(Map<String, Object> entityMap) throws DataAccessException {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			/*
			 * 查询当初出纳月是否有未确认数据
			 **/
			
			// 获取会计年度
			String acc_year = entityMap.get("acc_year").toString();
			// 当前月份
			String nowMonth = entityMap.get("acc_month").toString();

			entityMap.put("now_accYearMonth", acc_year+"/"+nowMonth);

			entityMap.put("is_con", "0");

			List<AccTell> accTellList = accTellMapper.queryAccTell(entityMap);// 查询是否确认

			if (accTellList.size() > 0) {

				return "{\"error\":\"该月含有未确认的出纳账,不能月结.\"}";
			}
			
			/*
			 *如果当前月是当年最后一个月,查询下年会计期间是否已经添加,如果不存在，不允许月结
			 **/
			if ("12".equals(nowMonth)) {

				// 获取下年
				int next_year = Integer.parseInt(acc_year) + 1;

				entityMap.put("acc_year", next_year);

				List<AccYearMonth> nextYearMonthList = accYearMonthMapper.queryAccYearMonth(entityMap);// 查询下年会计期间是否已经添加

				if (nextYearMonthList.size() == 0) {

					return "{\"error\":\"下年度会计期间不存在,本年度会计期间最后一月不允许月结.\"}";

				}

			}
			
			entityMap.put("acc_year", acc_year);
			
			entityMap.put("create_user", SessionManager.getUserCode());
			
			entityMap.put("create_date", sdf.format(new Date()));
			
			entityMap.put("cash_flag", "1");
			
			entityMap.put("cash_state", "0");
			/*
			 *修改 当年会计期间结账标记 操作表AccYearMonth
			 *修改 当年出纳月 出纳日结结账标记 操作表AccTellDay
			 */
			accYearMonthMapper.updateAccYearMonth(entityMap);// 更新会计期间中出纳结账标记、出纳结账时间和出纳结账人

			accTellDayMapper.updateAccTellDay(entityMap);// 更新日结中该月所有出纳账的结账标记
			
			/*
			 *获取下月是否有初始数据
			 */
			String nowYearMonth = acc_year+nowMonth;
			//获取下月
			String next_yearMonth = DateUtil.getNextYear_Month(nowYearMonth);
			
			String year = next_yearMonth.substring(0, 4);
			
			String month = next_yearMonth.substring(4);
			
			entityMap.put("acc_year", year);
			
			entityMap.put("acc_month", month);
			
			//获取下月是否有出纳日结初始数据
			entityMap.remove("cash_flag");
			
			List<AccTellDay> accTellDayList = accTellDayMapper.queryAccTellDay(entityMap);
			//如果没有下月出纳日结初始数据,就添加
			if(accTellDayList.size() == 0){
				
				entityMap.put("year", year);
				
				entityMap.put("month", month);
				
				addBatchAccTellDay(entityMap);//批量添加下月初始数据
			}
			entityMap.put("cash_flag", "0");
			
			entityMap.remove("acc_month");
			
			List<AccYearMonth> list = accYearMonthMapper.queryAccYearMonth(entityMap);// 查询

			String now_month = list.get(0).getAcc_year() + "年"+ list.get(0).getAcc_month() + "月";
			//加载会计期间
			sysConfigService.queryYearMonthInit(entityMap);
			return "{\"msg\":\"月结成功.\",\"state\":\"true\",\"now_month\":\""+ now_month + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"月结失败 数据库异常 请联系管理员! 错误编码  queryDailyAccBalanceAdjustmentByMonth\"}";

		}
	}
		
		/**
		*<BR>
		*月结：出纳反结
		*/
		@Override
		  public String queryUndailyAccBalanceAdjustmentByMonth(Map<String, Object> entityMap) throws DataAccessException {
				
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					/*
					 * 查询当初出纳月是否有未确认数据
					 **/
					// 获取会计年度
					String acc_year = entityMap.get("acc_year").toString();
					// 当前月份
					String nowMonth = entityMap.get("acc_month").toString();
					//当前年月
					String now_accYearMonth = acc_year+"/"+nowMonth;
					
					entityMap.put("now_accYearMonth",now_accYearMonth);

					entityMap.put("is_con", "0");

					List<AccTell> accTellList = accTellMapper.queryAccTell(entityMap);// 查询是否确认

					if (accTellList.size() > 0) {

						return "{\"error\":\"该月含有未确认的出纳账,不能反结.\"}";
					}
					
					/*
					 *如果上次结账月等于出纳账系统启用时间,不能反结 
					 **/
					
					entityMap.put("mod_code", "0101");
					
					ModStart modStart = modStartService.queryModStartByCode(entityMap);//出纳账系统启用时间
					
					String modStartTime = modStart.getStart_year()+"/"+modStart.getStart_month();
					
					if(entityMap.get("now_accYearMonth").toString().equals(modStartTime)){//判断是否小于等于出纳账系统启用时间
						
						return "{\"error\":\"反结失败,反结日期不能小于等于出纳账系统启用时间.\"}";
						
					}
					
					/*
					 * 修改会计期间结账标记 操作表AccYearMonth
					 * 修改出纳日结结账标记 操作表 AccTellDay
					 */
					entityMap.put("create_user", "");
					
					entityMap.put("cash_flag", "0");
					
					entityMap.put("cash_state", "1");
					
					entityMap.put("create_date","");

					accYearMonthMapper.updateAccYearMonth(entityMap);//更新会计期间中出纳结账标记、出纳结账时间和出纳结账人
						
					accTellDayMapper.updateAccTellDay(entityMap);//更新日结中该月所有出纳账的结账标记
					
					entityMap.remove("cash_flag");
					
					entityMap.remove("acc_month");
					
					entityMap.put("cash_flag", "1");
					
					List<AccYearMonth> list = accYearMonthMapper.queryAccYearMonth(entityMap);//查询上次结账月
					String pre_month = "";
					if(list.size()>0){
						 pre_month = list.get(list.size()-1).getAcc_year()+"年"+list.get(list.size()-1).getAcc_month()+"月";
					}else{
						 pre_month = Integer.parseInt(entityMap.get("now_accYearMonth").toString().split("/")[0])-1+"年12月";
					}
					//加载会计期间
					sysConfigService.queryYearMonthInit(entityMap);
					return "{\"msg\":\"反结成功.\",\"state\":\"true\",\"pre_month\":\""+pre_month+"\"}";
							
				}
				catch (Exception e) {

					logger.error(e.getMessage(), e);

					return "{\"error\":\"反结失败 数据库异常 请联系管理员! 错误编码  queryUndailyAccBalanceAdjustmentByMonth\"}";

				}
		    }
	
		public void addBatchAccTellDay(Map<String, Object> entityMap) throws Exception{
			String year = "";
			String month = "";
			if(entityMap.get("year") != null && !"".equals(entityMap.get("year").toString())){
				
				year = entityMap.get("year").toString();
			}
			
			if(entityMap.get("month") != null && !"".equals(entityMap.get("month").toString())){
				
				month = entityMap.get("month").toString();
			}
			
			//获取最大天数
			int maxDay = Integer.parseInt(DateUtil.getMaxMonthDate(year,month));
			
			List<Map<String,Object>> listAccTellDay = new ArrayList<Map<String,Object>>();
	        
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
	        
	        //添加下月数据
	        for (int day = 1; day <= maxDay; day++) {
	        	
	        	Map<String,Object> paraMap = new HashMap<String,Object>();
	        	paraMap.put("group_id", entityMap.get("group_id").toString());
	    		paraMap.put("hos_id", entityMap.get("hos_id").toString());
	    		paraMap.put("copy_code", entityMap.get("copy_code"));
	    		paraMap.put("acc_year", year);
	    		paraMap.put("acc_month", month);
				paraMap.put("cash_date", sf.parse(year+"/"+month+"/"+day));
	    		paraMap.put("cash_flag", 0);
	    		paraMap.put("create_user", "");
	    		paraMap.put("create_date", "");
	    		listAccTellDay.add(paraMap);
			}
	        accTellDayMapper.addBatchAccTellDay(listAccTellDay);
			
		}
		
}

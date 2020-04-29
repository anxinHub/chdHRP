/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.text.SimpleDateFormat;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.AccYearMapper;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.dao.tell.AccBankCheckMapper;
import com.chd.hrp.acc.dao.tell.AccTellDayMapper;
import com.chd.hrp.acc.entity.AccYear;
import com.chd.hrp.sys.dao.ModMapper;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.ModStartService;
import com.chd.hrp.sys.service.base.SysConfigService;

/**
* @Title. @Description.
* 系统启用<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("modStartService")
public class ModStartServiceImpl implements ModStartService {

	private static Logger logger = Logger.getLogger(ModStartServiceImpl.class);
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "accYearMapper")
	private final AccYearMapper accYearMapper = null;
	
	@Resource(name = "accBankCheckMapper")
	private final AccBankCheckMapper accBankCheckMapper = null;
	
	@Resource(name = "accTellDayMapper")
	private final AccTellDayMapper accTellDayMapper = null;
	
	@Resource(name = "modMapper")
	private final ModMapper modMapper = null;
    
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;
	
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;
	
	/**
	 * @Description 
	 * 系统启用<BR> 添加ModStart
	 * @param ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addModStart(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("cash_flag", "0");
		
		/*
		 * 调用验证业务数据是否存在方法
		 * 返回值:0 无业务数据，可以启用
		 * 		  1 系统存在业务数据，不能启用
		 * 		  2 会计年度不存在，不能启用
		 * 	      3 银行对账单中存在业务数据，出纳账管理不能启用
		 * 		  4 有日结账数据存在，出纳账管理不能启用
		 */
		
		int result =queryModStartData(entityMap);
		if(result == 2){
			return "{\"msg\":\"会计年度不存在.\",\"state\":\"false\"}";
		}
		
		/*if(result == 1){
			return "{\"msg\":\"系统有数据已存在，启用失败\",\"state\":\"true\",\"val\":\"1\"}";
		}
		if(result == 5){
			return "{\"msg\":\"财务管理系统没有启用,不能启用子级系统.\",\"state\":\"false\"}";
		}
		if(result == 3){
			return "{\"msg\":\"当前系统启用时间不能早于财务管理系统.\",\"state\":\"false\"}";
		}
		if(result == 1){
			return "{\"msg\":\"有日结数据不能修改启用时间.\",\"state\":\"false\"}";
		}*/
		
		//ModStart modStart = queryModStartByCode(entityMap);
		try {
			/*if (modStart != null) {
				if (modStart.getCreate_user() == null) {*/
					//根据模块删除系统启用时间,然后再添加
					modStartMapper.deleteModStart(entityMap);
					modStartMapper.addModStart(entityMap);
					
					//更新启动日期之前的会计期间
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("year_month", entityMap.get("start_year").toString() + entityMap.get("start_month").toString());
					if("01".equals(entityMap.get("mod_code"))){
						map.put("flag", "acc_flag");
						map.put("user", "acc_user");
						map.put("date", "acc_date");
					}else if("0101".equals(entityMap.get("mod_code"))){
                        map.put("flag", "cash_flag");
						map.put("user", "cash_user");
						map.put("date", "cash_date");
					}else if("0102".equals(entityMap.get("mod_code"))){
                        map.put("flag", "wage_flag");
						map.put("user", "wage_user");
						map.put("date","wage_date");
					}
					
					map.put("user_id", entityMap.get("user_id"));
					accYearMonthMapper.updateModAccYearMonth(map);
					
					//启用出纳账管理时需要对日结表进行初始化操作
					if("0101".equals(entityMap.get("mod_code"))){
						//根据日期删除日结表中的数据
						/*accTellDayMapper.deleteAccTellDay(entityMap);*/
						String start_year = entityMap.get("acc_year").toString();
						String start_month = entityMap.get("start_month").toString();
						int lastDay = Integer.parseInt(DateUtil.getMaxMonthDate(start_year, start_month));
				        List<Map<String,Object>> listAccTellDay = new ArrayList<Map<String,Object>>();
				        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				        for (int day = 1; day <= lastDay; day++) {
				        	Map<String,Object> paraMap = new HashMap<String,Object>();
				        	paraMap.put("group_id", entityMap.get("group_id").toString());
				    		paraMap.put("hos_id", entityMap.get("hos_id").toString());
				    		paraMap.put("copy_code", entityMap.get("copy_code"));
				    		paraMap.put("acc_year", entityMap.get("acc_year").toString());
				    		paraMap.put("acc_month", entityMap.get("start_month").toString());
							paraMap.put("cash_date", sdf.parse(start_year+"/"+start_month+"/"+day));
				    		paraMap.put("cash_flag", 0);
				    		paraMap.put("create_user", "");
				    		paraMap.put("create_date", "");
				    		listAccTellDay.add(paraMap);
						}
				        
				        accTellDayMapper.addBatchAccTellDay(listAccTellDay);
				        //修改当前系统模块会计期间结账标记:小于当年会计年度、会计月份的期间为已结账状态
						Map<String,Object> accYearMonthMap = new HashMap<String,Object>();
						
						accYearMonthMap.put("group_id", SessionManager.getGroupId());
						accYearMonthMap.put("hos_id", SessionManager.getHosId());
						accYearMonthMap.put("copy_code", SessionManager.getCopyCode());
						
						accYearMonthMap.put("flag", "ACC_FLAG");//当前系统结账标记字段名称
						accYearMonthMap.put("user", "ACC_USER");//当前系统结账人字段名称
						accYearMonthMap.put("user_id", SessionManager.getUserId());//结账人Id
						accYearMonthMap.put("date", "ACC_DATE");//当前系统结账日期字段名称
						
						String year_month = String.valueOf(entityMap.get("start_year")) + String.valueOf(entityMap.get("start_month"));
						accYearMonthMap.put("year_month",year_month);
						
						accYearMonthMapper.updateModAccYearMonth(accYearMonthMap);
						
						
						
				        return "{\"msg\":\"启用成功.\",\"state\":\"true\"}";
				        
					}
					//加载会计期间
					sysConfigService.queryYearMonthInit(entityMap);
					
					return "{\"msg\":\"启用成功.\",\"state\":\"true\"}";
				/*} else {
					return "{\"msg\":\"已经启用成功.\",\"state\":\"true\"}";
				}
			}*/
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"系统异常 请联系管理员! 错误编码 addModStart\"}";
		}

	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量添加ModStart
	 * @param  ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchModStart(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			modStartMapper.addBatchModStart(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchModStart\"}";

		}
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStart分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryModStart(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return ChdJson.toJson(modStartMapper.queryModStart(entityMap, rowBounds), sysPage.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStartByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public ModStart queryModStartByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return modStartMapper.queryModStartByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量删除ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchModStart(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = modStartMapper.deleteBatchModStart(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchModStart\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 删除ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteModStart(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				modStartMapper.deleteModStart(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteModStart\"}";

		}
    }
	
	/**
	 * @Description 
	 * 系统启用<BR> 更新ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateModStart(Map<String,Object> entityMap)throws DataAccessException{

		try {

			modStartMapper.updateModStart(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateModStart\"}";

		}
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量更新ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchModStart(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			modStartMapper.updateBatchModStart(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateModStart\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 导入ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importModStart(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * 系统启用时查询业务表中是否有数据存在
	 */
	public Integer queryModStartData(Map<String, Object> entityMap)
			throws DataAccessException {
		
		AccYear accYear = accYearMapper.queryAccYearByCode(entityMap);
		
		if(accYear == null){
			
			return 2;
			
		}
		
		if("01".equals(entityMap.get("mod_code"))){
			
			Integer accLeder = modStartMapper.queryAccLeder(entityMap);
			
			if(accLeder>0){
				
				return 1;
			}
					
			Integer accVouch = modStartMapper.queryAccVouch(entityMap);
			
			if(accVouch>0){
				
				return 1;
			}
			
			Integer accVouchCheck = modStartMapper.queryAccVouchCheck(entityMap);
			
			if(accVouchCheck>0){
				
				return 1;
			}
			
			Integer accTell = modStartMapper.queryAccTell(entityMap);
			
			if(accTell>0){
				
				return 1;
			}
			
			return 0;
			
		}
		if("0101".equals(entityMap.get("mod_code"))){
			
			Mod  mod =  modMapper.queryParentByModCode(entityMap);
			//启用出纳账管理校验父级系统是否启用
			if(!"".equals(mod.getStart_year())&& null!= mod.getStart_year()){
				
					//判断启用出纳账管理系统日期是否大于财务管理系统启用日期
					if(Integer.parseInt(mod.getStart_year())<=Integer.parseInt(entityMap.get("acc_year").toString())
					&&Integer.parseInt(mod.getStart_month())>Integer.parseInt(entityMap.get("start_month").toString())){
						
						return 3;
					}
					//查询银行对账单中是否存在业务数据
					int bankCheck = modStartMapper.queryAccBankCheck(entityMap);
					
					if(bankCheck>0){
						
						return 1;
					}
					
			}else{
			
			return 5;
			
			}
			
			return 0;
		}
		if("0102".equals(entityMap.get("mod_code"))){	 
		Mod  mod = modMapper.queryParentByModCode(entityMap);
		//启用出纳账管理校验父级系统是否启用
		if(!"".equals(mod.getStart_year())&& null!= mod.getStart_year()){
			//判断启用出纳账管理系统日期是否大于财务管理系统启用日期
			
			if(Integer.parseInt(mod.getStart_year())<=Integer.parseInt(entityMap.get("acc_year").toString())
			&&Integer.parseInt(mod.getStart_month())>Integer.parseInt(entityMap.get("start_month").toString())){
				
				return 3;
			}
			//查询工资发放表中是否有业务数据存在
			int bankCheck = modStartMapper.queryAccWagePay(entityMap);
			
			if(bankCheck>0){
				
				return 1;
			}
			
		}else{
			
		return 5;
		
		}
		return 0;
	 }
		return null;
	}

	public String queryModStartByModeCode(Map<String, Object> mapVo) {
		
		return modStartMapper.queryModStartByModeCode(mapVo);
	}
	
	@Override
	public String querySysModStart(Map<String,Object> entityMap)throws DataAccessException{
		
		List<Map<String,Object>> reList=new ArrayList<Map<String,Object>>();
		String modCode=entityMap.get("mod_code").toString();
		StringBuilder resJson=new StringBuilder();
		resJson.append("mod_start:{");
		if(modCode.equals("00")  || modCode.equals("07") || modCode.equals("09")){
			
			//系统平台、人力资源管理系统、绩效考核管理系统、绩效核算系统
			resJson.append("\"00\":\"000000\"");
			//resJson.append(",\"06\":\"000000\"");
			resJson.append(",\"07\":\"000000\"");
			resJson.append(",\"09\":\"000000\"");
			
		}else{
			reList=modStartMapper.querySysModStart(entityMap);
			
			if(reList!=null && reList.size()>0){
				//模块启用年月
				for(Map<String,Object> map:reList){
					resJson.append("'" + map.get("mod_code") + "':'" + map.get("mod_start") + "',");
				}
				resJson.setCharAt(resJson.length()-1, ' ');
				
			}
		}
	
		resJson.append("}");
		return resJson.toString();
		
	}
}

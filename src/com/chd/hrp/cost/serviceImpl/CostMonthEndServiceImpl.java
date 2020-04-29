/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostMonthEndMapper;
import com.chd.hrp.cost.dao.analysis.CostAnalysisProcMapper;
import com.chd.hrp.cost.entity.CostMonthEnd;
import com.chd.hrp.cost.service.CostMonthEndService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室成本核算月结表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costMonthEndService")
public class CostMonthEndServiceImpl implements CostMonthEndService {

	private static Logger logger = Logger.getLogger(CostMonthEndServiceImpl.class);
	
	@Resource(name = "costMonthEndMapper")
	private final CostMonthEndMapper costMonthEndMapper = null;
	
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	@Resource(name = "costAnalysisProcMapper")
	private final CostAnalysisProcMapper costAnalysisProcMapper = null;
    
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 添加CostMonthEnd
	 * @param CostMonthEnd entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException{
		
		/*CostMonthEnd costMonthEnd = costMonthEndMapper.queryCostMonthEndByCode(entityMap);

		if (costMonthEnd != null) {

			return "{\"error\":\"该年月已存在.\"}";

		}*/ 
		
		try {
			
			costMonthEndMapper.addCostMonthEnd(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostMonthEnd\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量添加CostMonthEnd
	 * @param  CostMonthEnd entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostMonthEnd(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costMonthEndMapper.addBatchCostMonthEnd(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostMonthEnd\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 查询CostMonthEnd分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostMonthEnd(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostMonthEnd> list = costMonthEndMapper.queryCostMonthEnd(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostMonthEnd> list = costMonthEndMapper.queryCostMonthEnd(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 查询CostMonthEndByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostMonthEnd queryCostMonthEndByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		RowBounds rowBounds = new RowBounds(0, 1);
		
		List<CostMonthEnd> list = costMonthEndMapper.queryLastCostMonthEnd(entityMap, rowBounds);
		
		if(list.size()>0){
			
			CostMonthEnd costMonthEnd = list.get(0);
			
			return costMonthEnd;
			
		}

		return  new CostMonthEnd();
		
	}
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量删除CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostMonthEnd(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costMonthEndMapper.deleteBatchCostMonthEnd(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostMonthEnd\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 删除CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostMonthEnd(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costMonthEndMapper.deleteCostMonthEnd(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostMonthEnd\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 更新CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostMonthEnd(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costMonthEndMapper.updateCostMonthEnd(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMonthEnd\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室成本核算月结表<BR> 批量更新CostMonthEnd
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostMonthEnd(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costMonthEndMapper.updateBatchCostMonthEnd(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMonthEnd\"}";

		}
		
	}

	@Override
	public String queryCostYearMonth(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return ChdJson.toJson(costMonthEndMapper.queryCostYearMonth(entityMap));
	}

	@Override
	public Map<String, Object> queryCostCurYearMonth(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return costMonthEndMapper.queryCostCurYearMonth(entityMap);
	}

	/**
	 * @Description 
	 * 月末结转
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostFinalCharge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			//获取下个物流期间
			Integer month=Integer.parseInt(entityMap.get("month").toString());
			Integer year=Integer.parseInt(entityMap.get("year").toString());
			
			Integer next_month=month;
			Integer next_year=year;
			
			if(month==12){
				next_month=1;
				next_year=year+1;
				//验证下年期间是否存在
				//判断存不存在此会计期间，如果不存在，提示请配置。
			}else{
				next_month=month+1;
				next_year=year;
			}
			
	
			//判断下一期间是否存在
			Map<String, Object> existsMap = new HashMap<String, Object>();
			
			existsMap.put("group_id", entityMap.get("group_id"));
			existsMap.put("hos_id", entityMap.get("hos_id"));
			existsMap.put("copy_code", entityMap.get("copy_code"));
			existsMap.put("year", next_year);
			existsMap.put("month", (next_month.toString().length()==1)?('0'+next_month.toString()):next_month.toString());
			
			int flag = matCommonMapper.existsAccYearMonthCheck(existsMap);
			if(flag == 0){
				return "{\"error\":\"结转失败，下月期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			entityMap.put("acc_year", entityMap.get("year").toString());
			
			entityMap.put("acc_month", entityMap.get("month").toString());
			
			costMonthEndMapper.updateCostFinalCharge(entityMap);
			

			return "{\"msg\":\"结转成功.\",\"state\":\"true\",\"acc_year\":\""+existsMap.get("year")+"\",\"acc_month\":\""+existsMap.get("month")+"\",\"last_year\":\""+entityMap.get("year")+"\",\"last_month\":\""+entityMap.get("month")+"\"}";
			
		} catch (Exception e) {
			// TODO: handle exceptionlogger.error(e.getMessage(), e);
			return "{\"error\":\"结转失败 数据库异常 请联系管理员! 方法 updateCostFinalCharge\"}";
		}

	}

	
	/**
	 * @Description 
	 * 月末反结转
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostFinalUnCharge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			
			//获取上个物流期间
			Integer last_year=Integer.parseInt(entityMap.get("last_year").toString());
			
			Integer last_month=Integer.parseInt(entityMap.get("last_month").toString());
			
			
			Integer before_month=last_month;
			Integer before_year=last_year;
			
			if(last_month==1){
				before_month=12;
				before_year=last_year-1;
				//验证下年期间是否存在
				//判断存不存在此会计期间，如果不存在，提示请配置。
			}else{
				before_month=last_month-1;
				before_year=last_year;
			}

			//判断上一期间是否存在
			Map<String, Object> existsMap = new HashMap<String, Object>();
			
			existsMap.put("group_id", entityMap.get("group_id"));
			existsMap.put("hos_id", entityMap.get("hos_id"));
			existsMap.put("copy_code", entityMap.get("copy_code"));
			existsMap.put("year", before_year);
			existsMap.put("month", (before_month.toString().length()==1)?('0'+before_month.toString()):before_month.toString());
			
			int flag = matCommonMapper.existsAccYearMonthCheck(existsMap);
			if(flag == 0){
				//return "{\"error\":\"结转失败，下月期间不存在请配置！\",\"state\":\"false\"}";
				existsMap.put("year", "");
				existsMap.put("month", "");
			}
			

			
			entityMap.put("acc_year", entityMap.get("last_year").toString());
			
			entityMap.put("acc_month", entityMap.get("last_month").toString());
		/*	//删除科室医院全成本比较分析表
			costMonthEndMapper.deleteCost(entityMap);*/
			costMonthEndMapper.updateCostFinalUnCharge(entityMap);
			
			return "{\"msg\":\"反结成功.\",\"state\":\"true\",\"acc_year\":\""+entityMap.get("last_year")+"\",\"acc_month\":\""+entityMap.get("last_month")+"\",\"last_year\":\""+existsMap.get("year")+"\",\"last_month\":\""+existsMap.get("month")+"\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"反结失败 数据库异常 请联系管理员! 方法 updateCostFinalUnCharge\"}";
		}
	
	}
	@Override
	public String saveCostAnalysisProc(Map<String, Object> entityMap) throws DataAccessException {

		if(entityMap.get("acc_year") ==null){
			return "{\"msg\":\"年度不能为空.\",\"state\":\"true\"}";
		}
		if(entityMap.get("acc_month") ==null){
			return "{\"msg\":\"月份不能为空.\",\"state\":\"true\"}";
		}
		
		try {

			List<Map<String, Object>> list = (List<Map<String, Object>>) costAnalysisProcMapper.saveCostAnalysisProc(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}  
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败\"}";

		}
	}

}

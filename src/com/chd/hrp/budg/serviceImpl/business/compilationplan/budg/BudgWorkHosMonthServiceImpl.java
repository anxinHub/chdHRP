/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationplan.budg;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.compilationplan.budg.BudgWorkHosMonthMapper;
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosMonthService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医院月份业务预算
 * @Table:
 * BUDG_WORK_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("budgWorkHosMonthService")
public class BudgWorkHosMonthServiceImpl implements BudgWorkHosMonthService {

	private static Logger logger = Logger.getLogger(BudgWorkHosMonthServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWorkHosMonthMapper")
	private final BudgWorkHosMonthMapper budgWorkHosMonthMapper = null;
    
	/**
	 * @Description 
	 * 添加医院月份业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//校验数据是否存在
		int count = budgWorkHosMonthMapper.queryDataExist(entityMap);

		if (count > 0 ) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgWorkHosMonthMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医院月份业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			String str = "";
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item : entityList){
				
				int count = budgWorkHosMonthMapper.queryDataExist(item);
				
				if (count > 0 ) {

					str += item.get("year")+"年 预算指标:"+item.get("index_code")+item.get("month")+"月;"	;
				}
			}
			if(str != ""){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"数据已存在.\",\"state\":\"false\"}";
			
			}else{
				
				for(int i= 0 ; i< entityList.size() ; i++){
					
					addList.add(entityList.get(i));
					
					if(i%1000 == 0 ){
						
						budgWorkHosMonthMapper.addBatch(addList);
						
						addList.clear();
						
					}else if ( i == (entityList.size()-1) && addList.size() > 0 ){
						
						budgWorkHosMonthMapper.addBatch(addList);
							
							addList.clear();
						
					}
				}
			
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
			
			// "{\"error\":\"添加失败! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新医院月份业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgWorkHosMonthMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医院月份业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgWorkHosMonthMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除医院月份业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgWorkHosMonthMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医院月份业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWorkHosMonthMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加医院月份业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象医院月份业务预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkHosMonth> list = (List<BudgWorkHosMonth>)budgWorkHosMonthMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWorkHosMonthMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWorkHosMonthMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医院月份业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosMonthMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosMonthMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医院月份业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkHosMonthMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院月份业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWorkHosMonth
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkHosMonthMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院月份业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkHosMonth>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkHosMonthMapper.queryExists(entityMap);
	}
	
	@Override
	public String queryHosYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.putAll(mapVo);
		
		//年度处理
		map.put("year", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);

		return budgWorkHosMonthMapper.queryHosYearLastYearWork(map) ;
	}
	
	@Override
	public int qureyBudgIndexExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkHosMonthMapper.qureyBudgIndexExist(mapVo);
		
	}
	
	@Override
	public String collectBudgWorkHosMonthUp(Map<String, Object> mapVo) throws DataAccessException {
		
		// 汇总科室月份数据
		List<Map<String,Object>> list = budgWorkHosMonthMapper.queryCollectData(mapVo) ;
		
		if(list.size() == 0 ){
			return "{\"error\":\"未查询到计算数据,请核对所选年度的【科室月份业务预算】数据.\"}";
		}
		
		//更新用List
		List<Map<String,Object>> updateList =  new ArrayList<Map<String,Object>>();
		
		//添加用List
		List<Map<String,Object>> addList =  new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : list ){
			
			item.put("remark", "");
			item.put("grow_rate", "");
			item.put("resolve_rate", "");
			
			int count = budgWorkHosMonthMapper.queryDataExist(item);
			
			if(count > 0){
				
				updateList.add(item);
				
			}else{
				
				addList.add(item);
			}
		}
		try {
			if(updateList.size() > 0 ){
				budgWorkHosMonthMapper.updateBatch(updateList);
			}
			
			if(addList.size() > 0 ){
				budgWorkHosMonthMapper.addBatch(addList);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败!\",\"state\":\"false\"}");
			//return "{\"error\":\"操作失败! 方法 collectBudgWorkHosMonthUp\"}";
		}
		
	}
	
	/**
	 * @Description 
	 * 预算分解 页面 查询数据 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryResolveData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosMonthMapper.queryResolveData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkHosMonthMapper.queryResolveData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * 分解计算
	 */
	@Override
	public String resolveData(Map<String, Object> mapVo) throws DataAccessException {
		try {
			
			// 查询  要分解计算的 医院月份数据
			List<Map<String,Object>> list = budgWorkHosMonthMapper.queryResolveData(mapVo) ;
			
			if(list.size() == 0){
				
				return "{\"error\":\"未查询到计算数据,请核对所选年度的【医院月份业务预算】数据.\"}";
				
			}else{
				
				for(Map<String,Object> item : list){
					
					String resolve_way= String.valueOf(item.get("resolve_way"));
					
					if("01".equals(resolve_way)){ //取值方法为历史数据比例分解时，分解比例=上年本月该指标业务量/上年该指标业务量之和

						//判断上年业务总量是否为空
						if(item.get("sumYearLastWork") == null){
							return "{\"error\":\""+item.get("year")+"年度  "+item.get("index_code")+ item.get("index_name")+"指标,参考年度医院月份业务量未维护,请维护后操作\"}";
						}
						
						//判断上年业务量是否为空
						if(item.get("sumValue") == null || "".equals(item.get("sumValue"))){
							
							return "{\"error\":\""+item.get("year")+"年度  "+item.get("index_code")+ item.get("index_name")+"指标,参考年度月份月份业务量未维护,请维护后操作\"}";
						}else{
						
							// 计算 分解比例
							double  resolve_rate = Double.parseDouble(String.valueOf(item.get("sumValue")))/Double.parseDouble(String.valueOf(item.get("sumYearLastWork"))) * 100 ;
							//分解比例  保留  小数点后两位数字 四舍五入		
							BigDecimal  b  =   new   BigDecimal(resolve_rate);
							
							double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
							
							//更新 分解比例
							item.put("resolve_rate", rate);
							
							if(item.get("yearValue") == null){
								return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+ item.get("index_name")+"指标,医院年度业务量未维护,请维护后操作\"}";
							}
							//计算  计算计算值
							double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * resolve_rate /100;
							
							BigDecimal  count  =   new   BigDecimal(countVlaue);
							
							double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
							
							//更新 计算值 、预算值
							item.put("count_value", value);
							
							item.put("budg_value", value);
							
							// 空值  处理
							if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
								
								item.put("grow_rate", "");
							}
							if(item.get("last_month_carried") == null || "".equals(item.get("grow_value"))){
								item.put("last_month_carried", "");
							}
							
							if(item.get("carried_next_month") == null || "".equals(item.get("carried_next_month"))){
								item.put("carried_next_month", "");
							}
							
							if(item.get("remark") == null || "".equals(item.get("remark"))){
								item.put("remark", "");
							}
						}
						
					}
					
					if("02".equals(resolve_way)){
						
						// 取值方法为历史数据*增减比例时，分解比例=本月该指标上年业务量*（1+增减比例）/各月该指标上年业务量*（1+增减比例）之和
						
						// 统计 该指标上年业务量*（1+增减比例）之和
						double sum = 0.0; 
						
						String err = "";
						
						String str = "";
						
						for(Map<String,Object> map : list){
							
							//判断上年业务量是否为空
							if(map.get("last_year_workload") == null || "".equals(map.get("last_year_workload"))){
								str += map.get("year")  + "年度"+ map.get("index_code") + map.get("index_name")+"指标" +map.get("month")+"月;" ;
							}
							if(map.get("grow_rate") == null){
								err += map.get("year")  + "年度"+ map.get("index_code") + map.get("index_name")+"指标" +map.get("month")+"月;" ;
							}else{
								
								sum += Double.parseDouble(String.valueOf(map.get("last_year_workload"))) * ( 1 + Double.parseDouble(String.valueOf(map.get("grow_rate")))/100) ;
							}
							
						}
						
						if(str != ""){
							return "{\"error\":\"以下数据:【"+str+"】,上年业务量未维护,请维护后操作\"}";
						}
						
						if(err != ""){
							
							return "{\"error\":\"以下数据:【"+err+"】增长比例未维护,不允许计算！请维护完增长比例后再点击计算.\"}";
							
						}else{
							//计算分解比例、计算值、预算值
							//本科室上年业务量*（1+增减比例）
							if(item.get("grow_rate") == null){
								
								item.put("grow_rate", 0);
								
								//return "{\"error\":\"取值方法为历史数据*增减比例,增减比例未维护,不允许计算！请维护完增减比例后再点击计算.\"}";
									
								
							}
							double work = Double.parseDouble(String.valueOf(item.get("last_year_workload"))) * ( 1 + Double.parseDouble(String.valueOf(item.get("grow_rate")))/100);
							
							double resolve_rate = 0.00 ;
							// 计算 分解比例
							if(sum !=0.0){
								 resolve_rate = work / sum * 100 ;
							}
							
							
							//分解比例  保留  小数点后两位数字 四舍五入	
							BigDecimal  b  =   new   BigDecimal(resolve_rate);
							
							double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
							
							//更新 分解比例
							item.put("resolve_rate", rate);
							
							if(item.get("yearValue") == null){
								return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+"指标,医院年度业务量未维护,请维护后操作\"}";
							}
							//计算  计算计算值
							double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * resolve_rate/100;
							
							BigDecimal  count  =   new   BigDecimal(countVlaue);
							
							double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
							
							//更新 计算值 、预算值
							item.put("count_value", value);
							item.put("budg_value", value);
							
							// 空值  处理
							if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
								
								item.put("grow_rate", "");
							}
							if(item.get("last_month_carried") == null || "".equals(item.get("grow_value"))){
								item.put("last_month_carried", "");
							}
							
							if(item.get("carried_next_month") == null || "".equals(item.get("carried_next_month"))){
								item.put("carried_next_month", "");
							}
							
							if(item.get("remark") == null || "".equals(item.get("remark"))){
								item.put("remark", "");
							}

						}
						
					}
					
					if("03".equals(resolve_way)){
						
						//  取值方法为全面贯彻时，分解比例=1
						
						// 计算 分解比例
						
						double resolve_rate = 100 ;
						
						//更新 分解比例
						item.put("resolve_rate", resolve_rate);
						
						if(item.get("yearValue") == null){
							return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+"指标,医院年度业务量未维护,请维护后操作\"}";
						}
						//计算  计算计算值
						
						double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * resolve_rate /100;
						
						BigDecimal  count  =   new   BigDecimal(countVlaue);
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						//更新 计算值 、预算值
						item.put("count_value", value);
						item.put("budg_value", value);
						
						// 空值  处理
						if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
							
							item.put("grow_rate", "");
						}
						if(item.get("last_month_carried") == null || "".equals(item.get("grow_value"))){
							item.put("last_month_carried", "");
						}
						
						if(item.get("carried_next_month") == null || "".equals(item.get("carried_next_month"))){
							item.put("carried_next_month", "");
						}
						
						if(item.get("remark") == null || "".equals(item.get("remark"))){
							item.put("remark", "");
						}
					}
					
					if("04".equals(resolve_way)){
						
						//  取值方法为平均分摊时，分解比例=1/12
						
						// 计算 分解比例
						double resolve_rate = 1/Double.parseDouble("12") * 100 ;
						
						//分解比例  保留  小数点后两位数字 四舍五入	
						BigDecimal  b  =   new   BigDecimal(resolve_rate);
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
							
						if(item.get("yearValue") == null){
							return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+"指标,医院年度业务量未维护,请维护后操作\"}";
						}
						double value = 0.0 ;
						//计算  计算计算值
						if("12".equals(item.get("month"))){//12月份时  补偿因计算时保留2位小数 造成的数据误差（计算值 = 科室年度预算 - 前11个月的计算值之和）
							
							//更新 分解比例
							item.put("resolve_rate", 100 - (11*rate)) ;
							
							double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * rate /100;
							
							BigDecimal  count  =   new   BigDecimal(countVlaue);
							
							value =   Double.parseDouble(String.valueOf(item.get("yearValue"))) - 11*count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
							
						}else{
							
							//更新 分解比例
							item.put("resolve_rate", rate);
							
							double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * rate /100;
							
							BigDecimal  count  =   new   BigDecimal(countVlaue);
							
							value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						}
						
						//更新 计算值 、预算值
						item.put("count_value", value);
						item.put("budg_value", value);
						
						// 空值  处理
						if(item.get("last_year_workload") == null || "".equals(item.get("last_year_workload"))){
							
							item.put("last_year_workload", "");
						}
						if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
							
							item.put("grow_rate", "");
						}
						if(item.get("last_month_carried") == null || "".equals(item.get("grow_value"))){
							item.put("last_month_carried", "");
						}
						
						if(item.get("carried_next_month") == null || "".equals(item.get("carried_next_month"))){
							item.put("carried_next_month", "");
						}
						
						if(item.get("remark") == null || "".equals(item.get("remark"))){
							item.put("remark", "");
						}
							
					}
					
					
					if("05".equals(resolve_way)){//  取值方法为手工录入时，分解比例手动维护 
						
						// 根据 月份、自定义分解系数 计算分解比例(医院月)
						Map<String,Object> rateMap = budgWorkHosMonthMapper.queryResolveDataRate(item);
						
						double rate = 0.00 ;
						if(rateMap != null){
							rate = Double.parseDouble(String.valueOf(rateMap.get("resolve_rate"))) * 100 ;
							
							//分解比例  保留  小数点后两位数字 四舍五入	
							BigDecimal  b  =   new   BigDecimal(rate);
							
							double resolve_rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
							
							item.put("resolve_rate", resolve_rate);
							
						}else{
							
							return "{\"error\":\"自定义分解系数:【"+item.get("resolve_data")+"】明细数据未维护！请维护完分解比例后再点击计算.\"}";
							
						}
						if(item.get("yearValue") == null){
							return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+"指标"+item.get("dept_code") + item.get("dept_name")+"科室,科室年度业务量未维护,请维护后操作\"}";
						}
						//计算  计算计算值
						
						double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * rate /100;
						
						BigDecimal  count  =   new   BigDecimal(countVlaue);
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						//更新 计算值 、预算值
						item.put("count_value", value);
						item.put("budg_value", value);
						
						// 空值  处理
						if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
							
							item.put("grow_rate", "");
						}
						if(item.get("last_month_carried") == null || "".equals(item.get("grow_value"))){
							item.put("last_month_carried", "");
						}
						
						if(item.get("carried_next_month") == null || "".equals(item.get("carried_next_month"))){
							item.put("carried_next_month", "");
						}
						
						if(item.get("remark") == null || "".equals(item.get("remark"))){
							item.put("remark", "");
						}
					}
				}
				
				budgWorkHosMonthMapper.updateBatch(list);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
					
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败!\",\"state\":\"false\"}");
			//return "{\"error\":\"操作失败! 方法 collectBudgWorkHosMonthUp\"}";
		}
		
	}
	
	
	/**
	 * 根据主键  查询数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo)	throws DataAccessException {
		
		return budgWorkHosMonthMapper.queryDataExist(mapVo);
		
	}
	
	/**
	 * 增量生成时 查询要生成的数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo)	throws DataAccessException {
		
		
		return budgWorkHosMonthMapper.queryData(mapVo);
	}
	
	/**
	 * 批量修改 预算值
	 */
	@Override
	public String updateBatchBudgValue(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			  budgWorkHosMonthMapper.updateBatchBudgValue(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败! 方法 updateBatch\"}";

			}	
	}
	
	@Override
	public List<Map<String, Object>> getPrintData(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>) budgWorkHosMonthMapper.query(mapVo);
	
		return list;
	}
	@Override
	public List<Map<String, Object>> getResolvePrintData(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>) budgWorkHosMonthMapper.queryResolveData(mapVo);
	
		return list;
	}
	
}

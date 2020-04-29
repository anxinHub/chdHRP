/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.toptodown.deptmonthinbudg;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgincome.toptodown.deptmonthinbudg.BudgMedIncomeDeptMonthMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptMonth;
import com.chd.hrp.budg.service.budgincome.toptodown.deptmonthinbudg.MedInDMBudgService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("medInDMBudgService")
public class MedInDMBudgServiceImpl implements MedInDMBudgService {

	private static Logger logger = Logger.getLogger(MedInDMBudgServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedIncomeDeptMonthMapper")
	private final BudgMedIncomeDeptMonthMapper budgMedIncomeDeptMonthMapper = null;
    
	
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			if("1".equals(item.get("flag"))){ //添加
				addList.add(item) ;
			}else{ //修改
				updateList.add(item) ;
			}
		}
		try {
			if(addList.size()>0){
				String str = addBatch(addList);
				return str;
			}
			if(updateList.size()>0){
				
				int state = budgMedIncomeDeptMonthMapper.updateBatch(updateList);
				
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
		
	}
	
	/**
	 * @Description 
	 * 添加科室月份医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		//获取对象科室月份医疗收入预算
		BudgMedIncomeDeptMonth budgMedIncomeDeptMonth = queryByCode(entityMap);

		if (budgMedIncomeDeptMonth != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgMedIncomeDeptMonthMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室月份医疗收入预算<BR> 
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
				
				int count = budgMedIncomeDeptMonthMapper.queryDataExist(item);
				
				if (count > 0 ) {

					str += item.get("year")+"年 科室:"+item.get("dept_name")+" 科目:"+item.get("subj_name")+" "+item.get("month")+"月;"	;
				}
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", item.get("group_id"))   ;
				mapVo.put("hos_id", item.get("hos_id"))   ;
				mapVo.put("copy_code", item.get("copy_code"))   ;
				//获取上年年份 用于查询上年收入
				Integer lastYear = Integer.valueOf((String) item.get("year"))-1;
				mapVo.put("year", lastYear)   ;
				mapVo.put("month", item.get("month"))   ;
				mapVo.put("dept_id", item.get("dept_id"));
				mapVo.put("subj_code", item.get("subj_code"))   ;
				
				
				//调用方法查询该科目下该科室每月上年收入
				/*String last_year_income = budgMedIncomeDeptMonthMapper.queryDMLastYearIncome(mapVo);
				if(last_year_income==null){*/
					item.put("last_year_income", "");
					//return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_name")+" 科目:"+item.get("subj_name")+" "+item.get("month")+"月;上年收入未维护,请维护后操作"+"\",\"state\":\"true\"}";
				/*}else{
					item.put("last_year_income", last_year_income);
				}*/
				
			}
			if(!"".equals(str)){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"已有数据存在.\",\"state\":\"false\"}";
			
			}else{
				
				for(int i= 0 ; i< entityList.size() ; i++){
					
					addList.add(entityList.get(i));
					
					if(i%1000 == 0 ){
						
						budgMedIncomeDeptMonthMapper.addBatch(addList);
						
						addList.clear();
						
					}else if ( i == (entityList.size()-1) && addList.size() > 0 ){
						
							budgMedIncomeDeptMonthMapper.addBatch(addList);
							
							addList.clear();
						
					}
				}
				
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室月份医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgMedIncomeDeptMonthMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室月份医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgMedIncomeDeptMonthMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室月份医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgMedIncomeDeptMonthMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室月份医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgMedIncomeDeptMonthMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室月份医疗收入预算<BR> 
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
		//判断是否存在对象科室月份医疗收入预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedIncomeDeptMonth> list = (List<BudgMedIncomeDeptMonth>)budgMedIncomeDeptMonthMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMedIncomeDeptMonthMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMedIncomeDeptMonthMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室月份医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedIncomeDeptMonth> list = (List<BudgMedIncomeDeptMonth>)budgMedIncomeDeptMonthMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeDeptMonth> list = (List<BudgMedIncomeDeptMonth>)budgMedIncomeDeptMonthMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室月份医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeDeptMonthMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室月份医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedIncomeDeptMonth
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeDeptMonthMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室月份医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedIncomeDeptMonth>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeDeptMonthMapper.queryExists(entityMap);
	}
	/**
	 * 导入时 查询数据是否已存在  （专用  勿动）
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgMedIncomeDeptMonthMapper.queryDataExist(mapVo);
	}
	
	/**
	 * @Description 
	 * 查询数据 科室月份医疗收入预算分解维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@Override
	public String queryResolve(Map<String, Object> entityMap)
				throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedIncomeDeptMonth> list = (List<BudgMedIncomeDeptMonth>)budgMedIncomeDeptMonthMapper.queryResolve(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeDeptMonth> list = (List<BudgMedIncomeDeptMonth>)budgMedIncomeDeptMonthMapper.queryResolve(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	//查询 该科目下该科室的指定月份的上年收入
	@Override
	public String queryDMLastYearIncome(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return budgMedIncomeDeptMonthMapper.queryDMLastYearIncome(mapVo);
	}
	
	//预算分级维护  计算方法
	@Override
	public String collectMedInDMBudgUp(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			//定义字符串  接收错误信息
			String str = "";
			
			// 查询 所选年度、科目的计算数据
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgMedIncomeDeptMonthMapper.queryCollectData(mapVo);
			
			if(list.size() == 0){
				
				return "{\"error\":\"未查询到计算数据,请核对所选年度、指标的【科室年度至科室月份医疗收入预算分解方案】数据.\"}";
			}
			
			String resolve_way = "";
			
			for(Map<String,Object> item : list){
				
				resolve_way= String.valueOf(item.get("resolve_way"));
				
				break ;
			}
			
			if("01".equals(resolve_way)){ //取值方法为历史数据比例分解时，分解比例=本科室上年月收入/所有科室上年月收入之和

				
				for(Map<String,Object> item : list){
					
					
					//查询 所选年度、科目、科室  参考年限所有月份的收入 总和

					Map<String,Object> sumLastIncome = budgMedIncomeDeptMonthMapper.querySumLastYearIncome(item);
					//判断 参考年限收入 总和是否为空
					if(sumLastIncome == null){
						return "{\"error\":\""+item.get("year")+"年度  "+item.get("subj_code") + item.get("subj_name")+"科目"+item.get("dept_code") + item.get("dept_name")+"科室,参考年度所有科室月份医疗收入预算未维护,请维护后操作\"}";
					}
					
					//判断 参考年限医疗收入是否为空
					if(item.get("sumValue") == null || "".equals(item.get("sumValue"))){
						str += item.get("year")  + "年度"+ item.get("subj_code") + item.get("subj_name")+"科目" +item.get("dept_code") + item.get("dept_name")+"科室" +item.get("month")+"月;" ;
					}else{
						// 计算 分解比例
						double  resolve_rate =  0 ;
						
						if(!"0".equals(String.valueOf(sumLastIncome.get("sumLastIncome")))){
							resolve_rate = Double.parseDouble(String.valueOf(item.get("sumValue")))/Double.parseDouble(String.valueOf(sumLastIncome.get("sumLastIncome"))) * 100 ;
						}
						
						//分解比例  保留  小数点后两位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(String.valueOf(resolve_rate));
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						//判断本科目下本科室年度收入预算值是否为空
						if(item.get("hyValue")== null){
							return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+"科室年度医疗预算未维护,请维护后操作"+"\",\"state\":\"true\"}";
						}
						//获取本科目下本科室年度收入预算值
						double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
						
						
						//计算  计算计算值
						double countVlaue = Double.parseDouble(String.valueOf(hyValue)) * resolve_rate /100;
						
						BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
						
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
					
				if(str != ""){
					
					return "{\"error\":\"以下数据:【"+str+"】,参考年度医疗收入日不存在,请维护后操作\",\"state\":\"false\"}";
					
				}
				
				budgMedIncomeDeptMonthMapper.updateBatch(list);
				
			}
			
			if("02".equals(resolve_way)){
				
				// 取值方法为历史数据*增减比例时，分解比例=结核科室上年月份收入*（1+增减比例）/5个科室上年月份收入*（1+增减比例）之和
				
				
				
				String err = "";
				
				for(Map<String,Object> item : list){
					
					//判断上年月份收入是否为空
					if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
						str += item.get("year")  + "年度"+ item.get("subj_code") + item.get("subj_name")+"科目" +item.get("dept_code") + item.get("dept_name")+"科室" +item.get("month")+"月;" ;
					}
					if(item.get("grow_rate") == null){
						err += item.get("year")  + "年度"+ item.get("subj_code") + item.get("subj_name")+"科目" +item.get("dept_code") + item.get("dept_name")+"科室" +item.get("month")+"月;" ;
					}
					
				}
				
				if(str != ""){
					return "{\"error\":\"以下数据:【"+str+"】,科室上年医疗收入未维护,请维护后操作\"}";
				}
				
				if(err != ""){
					
					return "{\"error\":\"以下数据:【"+err+"】增长比例未维护,不允许计算！请维护完增长比例后再点击计算.\"}";
					
				}else{
					//计算分解比例、计算值、预算值
					
					for(Map<String,Object> item : list){
						
						String deptCode = String.valueOf(item.get("dept_code")) ;
						double sum = 0.0; 
						for(Map<String,Object> sumMap : list){
						
								sum += Double.parseDouble(String.valueOf(sumMap.get("last_year_income"))) * ( 1 + Double.parseDouble(String.valueOf(sumMap.get("grow_rate")))/100) ; 
							
						}
						
						//本科室上年月份收入*（1+增减比例）
						if(item.get("grow_rate") == null){
							
							item.put("grow_rate", 0);
							
							//return "{\"error\":\"取值方法为历史数据*增减比例,增减比例未维护,不允许计算！请维护完增减比例后再点击计算.\"}";
								
							
						}
						//根据上年收入 与今年增减比例  计算出今年分解比例对应值
						double income = Double.parseDouble(String.valueOf(item.get("last_year_income"))) * ( 1 + Double.parseDouble(String.valueOf(item.get("grow_rate")))/100);
						
						// 计算 分解比例
						
						double resolve_rate = income / sum * 100 ;
						
						//分解比例  保留  小数点后两位数字 四舍五入	
						BigDecimal  b  =   new   BigDecimal(String.valueOf(resolve_rate));
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						//判断本科目下本科室年度收入预算值是否为空
						if(item.get("hyValue")==null){
							return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+"科室年度医疗预算未维护,请维护后操作"+"\",\"state\":\"true\"}";
						}
						
						//获取本科目下本科室年度收入预算值
						double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
						
						
						//计算  计算计算值
						
						double countVlaue = Double.parseDouble(String.valueOf(hyValue)) * rate /100;
						
						BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						//更新 计算值 、预算值
						item.put("count_value", value);
						item.put("budg_value", value);
						
						// 空值  处理
						if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
							
							item.put("last_year_income", "");
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
					budgMedIncomeDeptMonthMapper.updateBatch(list);
				}
				
				
					
			}
			
			if("03".equals(resolve_way)){
				
				//  取值方法为全面贯彻时，分解比例=1
				
				//计算分解比例、计算值、预算值
				for(Map<String,Object> item : list){
					
					// 计算 分解比例
					
					double resolve_rate = 100 ;
					
					//更新 分解比例
					item.put("resolve_rate", resolve_rate);
					
					//判断本科目下本科室年度收入预算值是否为空
					if(item.get("hyValue") == null){
						return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+"科室年度医疗预算未维护,请维护后操作"+"\",\"state\":\"true\"}";
					}
					
					//获取本科目下本科室年度收入预算值
					double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
					
					
					//计算  计算计算值
					
					double countVlaue = Double.parseDouble(String.valueOf(hyValue)) * resolve_rate /100;
					
					BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
					
					double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					//更新 计算值 、预算值
					item.put("count_value", value);
					item.put("budg_value", value);
					
					// 空值  处理
					if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
						
						item.put("last_year_income", "");
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
				
				budgMedIncomeDeptMonthMapper.updateBatch(list);
					
			}
			
			if("04".equals(resolve_way)){
				
				//  取值方法为平均分摊时，分解比例=1/12
				
				
				//计算分解比例、计算值、预算值
				for(Map<String,Object> item : list){
					
					//使用BigDecemal类封装  便于double类型除法计算   且取高精度
					BigDecimal a = new BigDecimal(Double.valueOf(1));//相当于BigDecimal类型中double类型 的1 
					BigDecimal b = new BigDecimal(Double.valueOf(12));//相当于BigDecimal类型中double类型 的12
					
					// 计算 分解比例   取高精度的 1/12 
					double resolve_rate = a.divide(b, 3, BigDecimal.ROUND_HALF_UP).doubleValue()*100;
					
					//更新 分解比例
					item.put("resolve_rate", resolve_rate);
					
					//判断本科目下本科室年度收入预算值是否为空
					if(item.get("hyValue")==null){
						return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+"科室年度医疗预算未维护,请维护后操作"+"\",\"state\":\"true\"}";
					}
					
					//获取本科目下本科室年度收入预算值
					double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
					
					
					//计算  计算值
					double countVlaue = 0.0;
					
					//当月份为12月时   12月的计算值 = 整年预算值  * 剔除掉前11个月占比总和后的比例 
					if("12".equals(item.get("month"))){
						countVlaue = hyValue * (1 - (resolve_rate * 11) / 100);
					}else{
						//不是12月份   计算值 = 整年预算值 * rate 
						countVlaue = Double.parseDouble(String.valueOf(item.get("hyValue"))) * resolve_rate /100;
					}
					
					BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
					
					double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					//更新 计算值 、预算值
					item.put("count_value", value);
					item.put("budg_value", value);
					
					// 空值  处理
					if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
						
						item.put("last_year_income", "");
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
				
				budgMedIncomeDeptMonthMapper.updateBatch(list);
					
			}
			
			
			if("05".equals(resolve_way)){
				
				//  取值方法为手工录入时，分解比例手动维护 
				
				//计算分解比例、计算值、预算值
				for(Map<String,Object> item : list){
					
					// 根据 科室、月份、自定义分解系数 计算分解比例(科室月)
					Map<String,Object> rateMap = budgMedIncomeDeptMonthMapper.queryResolveDataRate(item);
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
					
					//判断本科目下本科室年度收入预算值是否为空
					if(item.get("hyValue") == null){
						return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+"科室年度医疗预算未维护,请维护后操作"+"\",\"state\":\"true\"}";
					}
					//获取本科目下本科室年度收入预算值
					double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
					
					
					//计算  计算计算值
					
					double countVlaue = Double.parseDouble(String.valueOf(item.get("hyValue"))) * rate /100;
					
					BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
					
					double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					//更新 计算值 、预算值
					item.put("count_value", value);
					item.put("budg_value", value);
					
					// 空值  处理
					if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
						
						item.put("last_year_income", "");
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
				
				budgMedIncomeDeptMonthMapper.updateBatch(list);
					
			}


			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}") ;
		}
	}
	
	@Override
	public String queryDeptyearValue(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String, Object>  map = budgMedIncomeDeptMonthMapper.queryDeptyearValue(mapVo) ;
		
		if(map != null ){
			return ChdJson.toJson(map);
		}else{
			
			return null;
		}
	}
	
	/**
	 * 增量生成时 查询要生成的数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgMedIncomeDeptMonthMapper.queryData(mapVo);
	}

	/**
	 * 批量修改 预算值
	 */
	@Override
	public String updateBatchBudgValue(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			budgMedIncomeDeptMonthMapper.updateBatchBudgValue(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败! 方法 updateBatch\"}";

			}	
	}

	@Override
	public String addGenerateBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			String str = "";
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for(int i= 0 ; i< entityList.size() ; i++){
				
				addList.add(entityList.get(i));
				
				if(i%1000 == 0 ){
					
					budgMedIncomeDeptMonthMapper.addBatch(addList);
					
					addList.clear();
					
				}else if ( i == (entityList.size()-1) && addList.size() > 0 ){
					
						budgMedIncomeDeptMonthMapper.addBatch(addList);
						
						addList.clear();
					
				}
			}
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}

	@Override
	public List<Map<String, Object>> queryMedInDMBudgUpPrintDate(
			Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedIncomeDeptMonthMapper.query(mapVo);
		return list;

	}

	@Override
	public List<Map<String, Object>> queryMedInHYBudgUpData(
			Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedIncomeDeptMonthMapper.query(mapVo);
		return list;

	}

	@Override
	public List<Map<String, Object>> queryDeptMontBudgUpData(
			Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedIncomeDeptMonthMapper.query(mapVo);
		return list;

	}

}

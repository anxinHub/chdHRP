/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.toptodown.hosyearinbudg;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.chd.hrp.budg.dao.budgincome.budghosresolverate.BudgHosResolveRateMapper;
import com.chd.hrp.budg.dao.budgincome.toptodown.hosyearinbudg.BudgMedIncomeHosYearMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeHosYear;
import com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgMedIncomeHosYearService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedIncomeHosYearService")
public class BudgMedIncomeHosYearServiceImpl implements BudgMedIncomeHosYearService {

	private static Logger logger = Logger.getLogger(BudgMedIncomeHosYearServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedIncomeHosYearMapper")
	private final BudgMedIncomeHosYearMapper budgMedIncomeHosYearMapper = null;
    
	@Resource(name="budgHosResolveRateMapper")
	private final BudgHosResolveRateMapper budgHosResolveRateMapper = null;
	
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
				//查询添加数据是否已存在
				String  str = budgMedIncomeHosYearMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					budgMedIncomeHosYearMapper.addBatch(addList);
					
				}else{
					
					return "{\"msg\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				budgMedIncomeHosYearMapper.updateBatch(updateList);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	
	
	/**
	 * @Description 
	 * 添加医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医院年度医疗收入预算
		BudgMedIncomeHosYear budgMedIncomeHosYear = queryByCode(entityMap);

		if (budgMedIncomeHosYear != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgMedIncomeHosYearMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医院年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String str="";
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for (Map<String, Object> item : entityList) {
				Map<String,Object> mapVo = budgMedIncomeHosYearMapper.queryByCode(item);
				if(mapVo!=null){
					 str += item.get("subj_code")+";";
				}
			}
			
			//判断str是否为空,为空正常添加   不为空则返回错误信息
			if(str!=""){
				return "{\"error\":\"所选年以下度科目编码:"+str+"数据已存在.\",\"state\":\"false\"}";
			}else{
				for (int i = 0; i < entityList.size(); i++) {
					addList.add(entityList.get(i));
						if(i%1000==0){
							budgHosResolveRateMapper.addBatch(addList);
							budgMedIncomeHosYearMapper.addBatch(addList);
							addList.clear();
						}else if (i==(entityList.size()-1) && addList.size()>0 ){
							budgHosResolveRateMapper.addBatch(addList);
							budgMedIncomeHosYearMapper.addBatch(addList);
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
	 * 更新医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgMedIncomeHosYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医院年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
		
		  budgHosResolveRateMapper.updateBatch(entityList);
		  budgMedIncomeHosYearMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\"}");
			

		}	
		
	}
	/**
	 * @Description 
	 * 删除医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgMedIncomeHosYearMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医院年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgHosResolveRateMapper.deleteBatch(entityList);
			
			budgMedIncomeHosYearMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加医院年度医疗收入预算<BR> 
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
		//判断是否存在对象医院年度医疗收入预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedIncomeHosYear> list = (List<BudgMedIncomeHosYear>)budgMedIncomeHosYearMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMedIncomeHosYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMedIncomeHosYearMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医院年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedIncomeHosYear> list = (List<BudgMedIncomeHosYear>)budgMedIncomeHosYearMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeHosYear> list = (List<BudgMedIncomeHosYear>)budgMedIncomeHosYearMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医院年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeHosYearMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedIncomeHosYear
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeHosYearMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedIncomeHosYear>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeHosYearMapper.queryExists(entityMap);
	}
	
	/**
	 * 查询上年收入
	 */
	@Override
	public String queryLastYearIncome(Map<String, Object> entityMap) {
		Map<String, Object> mapVo=budgMedIncomeHosYearMapper.queryLastYearIncome(entityMap);
		return ChdJson.toJson(mapVo);
	}
	
	/**
	 * 查询要生成的数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgMedIncomeHosYearMapper.queryData(mapVo);
	}

	/**
	 *  生成数据
	 */
	@Override
	public String addGenerateData(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
			
			for (Map<String, Object> item : entityList) {
				
				if("01".equals(item.get("resolve_or_sum"))){
					
					item.put("grow_rate", "");
					item.put("resolve_rate", "");
					rateList.add(item);
						
				}
			
			}
			
			if(rateList.size() >0 ){
				budgHosResolveRateMapper.deleteBatch(entityList);
				budgHosResolveRateMapper.addBatch(rateList);
			}
			budgMedIncomeHosYearMapper.deleteBatch(entityList);
			budgMedIncomeHosYearMapper.addBatch(entityList);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");

		}
	}
	
	/**
	 * 计算
	 */
	@Override
	public String collect(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgMedIncomeHosYearMapper.queryCollectData(mapVo);
			
			List<Map<String,Object>> resolveList = new ArrayList<Map<String,Object>>() ;//分解或汇总 为分解时 用List
			
			List<Map<String,Object>> sumList = new ArrayList<Map<String,Object>>() ;//分解或汇总 为汇总时 用List
			
			
			for(Map<String,Object> item : list){
				
				
				if("01".equals(item.get("resolve_or_sum"))){//分解
					
					
					//根据科目 查询 其同级独立核算科目 及同级 汇总科目 的预算之和
					//double sumValue = budgMedIncomeHosYearMapper.querySumValue(item); 
					
					double sumValue = 0.00 ;
					
					if("1".equals(String.valueOf(item.get("subj_level")))){ // 顶级科目  没有父级科目   编制方案不能为分解 
						// 顶级科目  没有父级科目   编制方案不能为分解   直接返回错误提示
						return "{\"error\":\"科目:【"+item.get("subj_code")+item.get("subj_name") +"】为顶级科目,编制方案不能设置为分解.\",\"state\":\"false\"}";
					}else{
						//根据科目 查询 其同级独立核算科目 及同级 汇总科目  预算数据
						List<Map<String,Object>>  subjList = budgMedIncomeHosYearMapper.querySubjList(item) ;
						
						for(Map<String,Object> map : subjList){
							
							if("-1".equals(String.valueOf(map.get("budg_value")))){
								if("1".equals(String.valueOf(map.get("is_single_count")))){
									// 独立核算科目  直接 提示 到相应 独立核算页面 操作 
									return "{\"error\":\"科目:【"+map.get("subj_code")+map.get("subj_name") +"】为独立核算科目,请参考编制方案到相应独立核算页面维护预算值.\",\"state\":\"false\"}";
								}else{
									// 递归 调用 计算 科目(分解科目、汇总科目) 预算值
									Map<String, Object> budgValue = countBudgValue(map) ;
									
									if(budgValue.get("error") != null){
										
										 return "{\"error\":\""+budgValue.get("error")+"\",\"state\":\"false\"}";
										 
									}else{
										
										sumValue +=  Double.parseDouble(String.valueOf(budgValue.get("countValue"))) ;
									}
									
								}
							}else{
								
								sumValue += Double.parseDouble(String.valueOf(map.get("budg_value"))) ;
								
							}
							
						}
					}
					
					Double parentValue = 0.00 ;
					
					if("-1".equals(String.valueOf(item.get("parentValue")))){ 
						
						if(!"1".equals(String.valueOf(item.get("subj_level")))&&!"2".equals(String.valueOf(item.get("subj_level")))){// 只计算非顶级科目 。 顶级科目 没有 父级科目  parentValue 始终为0
							// 父级科目 预算值 未做 时 先查询 父级科目信息 在调用 递归 计算其预算值 
							// 父级科目信息
							Map<String,Object> parentSubj = budgMedIncomeHosYearMapper.queryParentSubj(item) ;
							if(parentSubj!=null){
								if("1".equals(String.valueOf(parentSubj.get("is_single_count")))){
									// 独立核算科目  直接 提示 到相应 独立核算页面 操作 
									return "{\"error\":\"科目:【"+parentSubj.get("subj_code")+parentSubj.get("subj_name") +"】为独立核算科目,请参考编制方案到相应独立核算页面维护预算值.\",\"state\":\"false\"}";
								}else{
									
									if("02".equals(String.valueOf(parentSubj.get("resolve_or_sum")))){
										
										return "{\"error\":\"科目:【"+item.get("subj_code")+item.get("subj_name") +"】编制方案设置不合理,其父级科目编制方案为汇总,其编制方案不能为分解,请修改其编制方案.\",\"state\":\"false\"}";
									}else{
										
										// 递归 调用 计算 科目(分解科目、汇总科目) 预算值
										Map<String, Object> budgValue = countBudgValue(parentSubj) ;
										
										if(budgValue.get("error") != null){
											
											 return "{\"error\":\""+budgValue.get("error")+"\",\"state\":\"false\"}";
											 
										}else{
											
											parentValue =  Double.parseDouble(String.valueOf(budgValue.get("countValue"))) ;
										}
										
									}
									
								}
							}else{
								return "{\"error\":\"科目:【"+item.get("subj_code")+item.get("subj_name") +"】没有上级科目或上级科目预算值未维护.\",\"state\":\"false\"}";

							}
						
						}
						
					}else{
						
						parentValue = Double.parseDouble(String.valueOf(item.get("parentValue"))) ;
					}
					
					// 计算分解预算科目的预算之和
					
					double sumCountValue = parentValue  -  sumValue ;
					
					// 分解方法  01:历史数据比例分解   02:历史数据*增减比例分解   03:全面贯彻   04:平均分摊   05:手工维护分解系数
					
					if("01".equals(item.get("resolve_way"))){//01:历史数据比例分解
						
						// 计算 分解比例
						double  resolve_rate = 0;
						if(item.get("last_year_income").toString().endsWith("0")){
							return "{\"error\":\"科目:【"+item.get("subj_code")+item.get("subj_name") +"】上年收入不存在.\",\"state\":\"false\"}";

						}
						if(!"0".equals(String.valueOf(item.get("sumLastIncome")))){
							resolve_rate =Double.parseDouble(String.valueOf(item.get("last_year_income")))/ Double.parseDouble(String.valueOf(item.get("sumLastIncome"))) * 100 ;
						}
								
						//分解比例  保留  小数点后两位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(String.valueOf(resolve_rate));
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						item.put("grow_rate", "");
						item.put("resolve_rate", rate);
				
						//计算  计算值
						double countVlaue = sumCountValue * resolve_rate /100;
						
						BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						item.put("count_value", value);
						
						item.put("budg_value", value);
						
						resolveList.add(item) ;
						
					}else if("02".equals(item.get("resolve_way"))){ //02:历史数据*增减比例分解

						//上年其同级分解科目的执行数据之和(包含自身)
						Double sumLastIncome = Double.parseDouble(String.valueOf(item.get("sumLastIncome")));
						
						if(item.get("grow_rate") == null){
							item.put("grow_rate", 0.00);
						}
						
						double sumAll=  sumLastIncome * (1+Double.parseDouble(String.valueOf(item.get("grow_rate")))/100);
						
							
						//计算  本科目上年收入*(1+增减比例/100)
						double income = Double.parseDouble(String.valueOf(item.get("last_year_income"))) * ( 1 + Double.parseDouble(String.valueOf(item.get("grow_rate")))/100);
						
						//计算 分解比例
						double resolve_rate=income/sumAll*100;//数据库存储为数字  不是百分比  *100
						
						//分解比例  保留  小数点后两位数字 四舍五入	
						BigDecimal  b  =   new   BigDecimal(String.valueOf(resolve_rate));
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						//计算  计算值
						double countVlaue = sumCountValue * resolve_rate /100;
						
						BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						item.put("count_value", value);
						
						item.put("budg_value", value);
						
						resolveList.add(item) ;
							
							
					}else if("03".equals(item.get("resolve_way"))){ //03:全面贯彻    分解比例为100%
						
						//确定分解比例
						double rate = 100;
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						//计算  计算值
						double countVlaue = sumCountValue * rate /100;
						
						BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						item.put("count_value", value);
						
						item.put("budg_value", value);
						
						resolveList.add(item) ;
						
					}else if("04".equals(item.get("resolve_way"))){ // 04:平均分摊  与其有相同上级科目的同级科目  平均分摊
						
						// 根据 参数 查询  与其上级科目的的一级所有子科目  数量 
						int count = budgMedIncomeHosYearMapper.querySubjCount(item);		
						
						// 计算 分解比例
						double  resolve_rate = 1/Double.parseDouble(String.valueOf(count)) ;
						//分解比例  保留  小数点后两位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(String.valueOf(resolve_rate));
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						item.put("grow_rate", "");
						item.put("resolve_rate", rate);
				
						//计算  计算值
						double countVlaue = sumCountValue * resolve_rate;
						
						BigDecimal  value  =   new   BigDecimal(String.valueOf(countVlaue));
						
						double count_value =   value.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						item.put("count_value", count_value);
						
						item.put("budg_value", count_value);
						
						resolveList.add(item) ;
						
					}else if("05".equals(item.get("resolve_way"))){ // 05:手工维护分解系数
						
						if(item.get("resolve_rate") == null){
							
							return "{\"error\":\"科目:【"+item.get("subj_code")+item.get("subj_name")+"】分解方法为手工维护分解系数.分解比例未维护\",\"state\":\"flase\"}" ;
							
						}else{
							//计算  计算值
							double countVlaue = sumCountValue * Double.parseDouble(String.valueOf(item.get("resolve_rate"))) /100;
							
							BigDecimal  value  =   new   BigDecimal(String.valueOf(countVlaue));
							
							double count_value =   value.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
							
							item.put("count_value", count_value);
							
							item.put("budg_value", count_value);
							
							resolveList.add(item) ;
						}
					}
						
				}else if("02".equals(item.get("resolve_or_sum"))){
					
					//根据 科目 汇总其所有下级科目预算
					//double sumValue = budgMedIncomeHosYearMapper.querySumSubjValue(item) ;
					
					double sumValue = 0 ;
					
					// 根据所传科目 查询 所有下一级科目信息 及 预算数据
					List<Map<String,Object>> childList = budgMedIncomeHosYearMapper.queryChildSubj(item) ;
					
					for(Map<String,Object> map : childList){
						
						if("-1".equals(String.valueOf(map.get("budg_value")))){
							
							if("1".equals(String.valueOf(map.get("is_single_count")))){
								// 独立核算科目  返回错误提示 到相应 独立核算页面 操作 
								Map<String, Object> returnMap = new HashMap<String,Object>();
								
								return "{\"error\":\"科目:【"+map.get("subj_code")+map.get("subj_name") +"】为独立核算科目,请参考编制方案到相应独立核算页面维护预算值.\",\"state\":\"flase\"}" ;
								
							}else{
								
								if("01".equals(String.valueOf(map.get("resolve_or_sum")))){
									// 父级科目编制方案为汇总,其编制方案不能为分解 
									Map<String, Object> returnMap = new HashMap<String,Object>();
									
									return "{\"error\":\"科目:【"+map.get("subj_code")+map.get("subj_name") +"】编制方案设置不合理,其父级科目编制方案为汇总,其编制方案不能为分解,请修改其编制方案.\",\"state\":\"flase\"}";
									
								}else{
									
									Map<String,Object> returnMap  = countBudgValue(map) ;
									
									if(returnMap.get("error") != null){
										
										return "{\"error\":\""+returnMap.get("error")+"\",\"state\":\"flase\"}" ;
										
									}else{
										
										sumValue += Double.parseDouble(String.valueOf(returnMap.get("countValue"))) ;
										
									}
									
								}
							}
							
						}else{
							
							sumValue += Double.parseDouble(String.valueOf(map.get("budg_value"))) ;
							
						}
						
					}
					
					
					if(sumValue == 0){// 汇总值 为 0 说明 科目为末级科目   编制方案 不能 为汇总
						
						Map<String, Object> returnMap = new HashMap<String,Object>();
						
						return "{\"error\":\"科目:【"+item.get("subj_code")+item.get("subj_name") +"】为末级科目,不能汇总。编制方案设置错误,请修改该科目编制方案.\",\"state\":\"flase\"}";
						
					}else{
						
						item.put("count_value", sumValue);
						
						item.put("budg_value", sumValue);
						
						sumList.add(item) ;
					}
					
				}
				
			}
			
			if(resolveList.size() > 0 ){//分解时 更新 医院年度医疗收入预算表 计算值、预算值 及 医院医疗收入科目分解比例维护 表 分解比例
				
				//更新 医院年度医疗收入预算  计算值、预算值
				budgMedIncomeHosYearMapper.updateValue(resolveList) ;
				
				// 更新 医院医疗收入科目分解比例维护表 分解比例
				budgMedIncomeHosYearMapper.updateResolveRate(resolveList) ;
			}
			
			if(sumList.size() > 0 ){//汇总时 只更新  医院年度医疗收入预算表 计算值、预算值
				
				//更新 医院年度医疗收入预算  计算值、预算值
				budgMedIncomeHosYearMapper.updateValue(sumList) ;
				
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
	}
	
	/**
	 * 分解计算  递归调用  计算所传科目(分解科目、汇总科目 ) 预算值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object>  countBudgValue(Map<String, Object> mapVo) throws DataAccessException {
		
		if("01".equals(mapVo.get("resolve_or_sum"))){
			
			// 查询  计算 所传科目 预算 所需 数据
			Map<String, Object> dataMap = budgMedIncomeHosYearMapper.queryCountData(mapVo);
			
			double sumValue = 0.00 ;
			
			
			if("1".equals(String.valueOf(dataMap.get("subj_level")))){ // 顶级科目  没有父级科目   编制方案不能为分解 
				// 顶级科目  没有父级科目   编制方案不能为分解   直接返回错误提示
				Map<String, Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("error", "科目:【"+dataMap.get("subj_code")+dataMap.get("subj_name") +"】为顶级科目,编制方案不能设置为分解.");
				
				return returnMap ;
			}else{
				//根据科目 查询 其同级独立核算科目 及同级 汇总科目  预算数据
				List<Map<String,Object>>  subjList = budgMedIncomeHosYearMapper.querySubjList(dataMap) ;
				
				for(Map<String,Object> map : subjList){
					
					if("-1".equals(String.valueOf(map.get("budg_value")))){
						if("1".equals(String.valueOf(map.get("is_single_count")))){
							// 独立核算科目  返回错误提示 到相应 独立核算页面 操作 
							Map<String, Object> returnMap = new HashMap<String,Object>();
							
							returnMap.put("error", "科目:【"+map.get("subj_code")+map.get("subj_name") +"】为独立核算科目,请参考编制方案到相应独立核算页面维护预算值.");
							
							return returnMap ;
						}else{
							// 递归 调用 计算 科目(分解科目、汇总科目) 预算值
							Map<String, Object> valueMap = countBudgValue(map) ;
							
							if(valueMap.get("error") != null){
								
								return valueMap ;
								
							}else{
								
								sumValue +=  Double.parseDouble(String.valueOf(valueMap.get("countValue"))) ;
							}
							
						}
					}else{
						
						sumValue += Double.parseDouble(String.valueOf(map.get("budg_value"))) ;
						
					}
					
				}
			}
			
			
			Double parentValue = 0.00 ;
			
			if("-1".equals(String.valueOf(dataMap.get("parentValue")))){ 
				
				if(!"1".equals(String.valueOf(dataMap.get("subj_level")))&&!"2".equals(String.valueOf(dataMap.get("subj_level")))){ //只计算非顶级科目 。 顶级科目 没有 父级科目  parentValue 始终为0
					// 父级科目 预算值 为 0 时 先查询 父级科目信息 在调用 递归 计算其预算值 
					// 父级科目信息
					Map<String,Object> parentSubj = budgMedIncomeHosYearMapper.queryParentSubj(dataMap) ;
					
					if("1".equals(String.valueOf(parentSubj.get("is_single_count")))){
						// 独立核算科目  返回错误提示 到相应 独立核算页面 操作 
						Map<String, Object> returnMap = new HashMap<String,Object>();
						
						returnMap.put("error", "科目:【"+dataMap.get("subj_code")+dataMap.get("subj_name") +"】为独立核算科目,请参考编制方案到相应独立核算页面维护预算值.");
						
						return returnMap ;
					}else{
						
						if("02".equals(String.valueOf(parentSubj.get("resolve_or_sum")))){
							// 父级科目编制方案为汇总,其编制方案不能为分解 
							Map<String, Object> returnMap = new HashMap<String,Object>();
							
							returnMap.put("error", "科目:【"+dataMap.get("subj_code")+dataMap.get("subj_name") +"】编制方案设置不合理,其父级科目编制方案为汇总,其编制方案不能为分解,请修改其编制方案.");
							
							return returnMap ;
						}else{
							
							Map<String, Object> valueMap = new HashMap<String,Object>();
							
							valueMap = countBudgValue(parentSubj) ;
							
							if(valueMap.get("error") != null){
								
								return valueMap ;
								
							}else{
								
								parentValue = Double.parseDouble(String.valueOf(valueMap.get("countValue")));
							}
						}
					}
				}
				
				
			}else{
				
				parentValue = Double.parseDouble(String.valueOf(dataMap.get("parentValue"))) ;
			}
			
			// 计算分解预算科目的预算之和
			
			double sumCountValue = parentValue  -  sumValue ;
			
			// 分解方法  01:历史数据比例分解   02:历史数据*增减比例分解   03:全面贯彻   04:平均分摊   05:手工维护分解系数
			
			if("01".equals(dataMap.get("resolve_way"))){//01:历史数据比例分解
				
				// 计算 分解比例
				double  resolve_rate = 0;
				
				if(!"0".equals(String.valueOf(dataMap.get("sumLastIncome")))){
					resolve_rate =Double.parseDouble(String.valueOf(dataMap.get("last_year_income")))/ Double.parseDouble(String.valueOf(dataMap.get("sumLastIncome"))) * 100 ;
				}
						
		
				//计算  计算值
				double countVlaue = sumCountValue * resolve_rate /100;
				
				BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
				
				double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
				
				Map<String, Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("countValue", value) ;
				
				return returnMap ;
				
			}else if("02".equals(dataMap.get("resolve_way"))){ //02:历史数据*增减比例分解

				//上年其同级分解科目的执行数据之和(包含自身)
				Double sumLastIncome = Double.parseDouble(String.valueOf(dataMap.get("sumLastIncome")));
				
				if(dataMap.get("grow_rate") == null){
					dataMap.put("grow_rate", 0.00);
				}
				
				double sumAll=  sumLastIncome * (1+Double.parseDouble(String.valueOf(dataMap.get("grow_rate")))/100);
				
					
				//计算  本科目上年收入*(1+增减比例/100)
				double income = Double.parseDouble(String.valueOf(dataMap.get("last_year_income"))) * ( 1 + Double.parseDouble(String.valueOf(dataMap.get("grow_rate")))/100);
				
				//计算 分解比例
				double resolve_rate=income/sumAll*100;//数据库存储为数字  不是百分比  *100
				
				//计算  计算值
				double countVlaue = sumCountValue * resolve_rate /100;
				
				BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
				
				double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
				
				Map<String, Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("countValue", value) ;
				
				return returnMap ;
					
			}else if("03".equals(dataMap.get("resolve_way"))){ //03:全面贯彻    分解比例为100%
				
				//确定分解比例
				double rate = 100;
				
				//计算  计算值
				double countVlaue = sumCountValue * rate /100;
				
				BigDecimal  count  =   new   BigDecimal(String.valueOf(countVlaue));
				
				double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
				
				
				Map<String, Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("countValue", value) ;
				
				return returnMap ;
				
			}else if("04".equals(dataMap.get("resolve_way"))){ // 04:平均分摊  与其有相同上级科目的同级科目  平均分摊
				
				// 根据 参数 查询  与其上级科目的的一级所有子科目  数量 
				int count = budgMedIncomeHosYearMapper.querySubjCount(dataMap);		
				
				// 计算 分解比例
				double  resolve_rate = 1/Double.parseDouble(String.valueOf(count)) ;
				
				//计算  计算值
				double countVlaue = sumCountValue * resolve_rate /100;
				
				BigDecimal  value  =   new   BigDecimal(String.valueOf(countVlaue));
				
				double count_value =   value.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
				
				Map<String, Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("countValue", count_value) ;
				
				return returnMap ;
				
			}else{ // 05:手工维护分解系数
				
				if(dataMap.get("resolve_rate") == null){
					
					// 05:手工维护分解系数 时  分解系数未维护 返回错误提示
					Map<String, Object> returnMap = new HashMap<String,Object>();
					
					returnMap.put("error", "科目:【"+dataMap.get("subj_code")+dataMap.get("subj_name")+"】分解方法为手工维护分解系数.分解比例未维护.") ;
					
					return returnMap ;
					
				}else{
					//计算  计算值
					double countVlaue = sumCountValue * Double.parseDouble(String.valueOf(dataMap.get("resolve_rate"))) /100;
					
					BigDecimal  value  =   new   BigDecimal(String.valueOf(countVlaue));
					
					double count_value =   value.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					Map<String, Object> returnMap = new HashMap<String,Object>();
					
					returnMap.put("countValue", count_value) ;
					
					return returnMap ;
				}
			}
				
		}else{// 汇总
			
			double sumValue = 0 ;
			
			// 根据所传科目 查询 所有下一级科目信息 及 预算数据
			List<Map<String,Object>> list = budgMedIncomeHosYearMapper.queryChildSubj(mapVo) ;
			
			for(Map<String,Object> map : list){
				
				
				if("-1".equals(String.valueOf(map.get("budg_value")))){
					
					if("1".equals(String.valueOf(map.get("is_single_count")))){
						// 独立核算科目  返回错误提示 到相应 独立核算页面 操作 
						Map<String, Object> returnMap = new HashMap<String,Object>();
						
						returnMap.put("error", "科目:【"+map.get("subj_code")+map.get("subj_name") +"】为独立核算科目,请参考编制方案到相应独立核算页面维护预算值.");
						
						return returnMap ;
					}else{
						
						if("01".equals(String.valueOf(map.get("resolve_or_sum")))){
							// 父级科目编制方案为汇总,其编制方案不能为分解 
							Map<String, Object> returnMap = new HashMap<String,Object>();
							
							returnMap.put("error", "科目:【"+map.get("subj_code")+map.get("subj_name") +"】编制方案设置不合理,其父级科目编制方案为汇总,其编制方案不能为分解,请修改其编制方案.");
							
							return returnMap ;
						}else{
							
							Map<String,Object> returnMap  = countBudgValue(map) ;
							
							if(returnMap.get("error") != null){
								
								return returnMap ;
								
							}else{
								
								sumValue += Double.parseDouble(String.valueOf(returnMap.get("countValue"))) ;
								
							}
							
						}
					}
					
				}else{
					
					sumValue += Double.parseDouble(String.valueOf(map.get("budg_value"))) ;
					
				}
				
			}
			
			if(sumValue == 0){// 汇总值 为 0 说明 科目为末级科目   编制方案 不能 为汇总
				
				Map<String, Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("error", "科目:【"+mapVo.get("subj_code")+mapVo.get("subj_name") +"】为末级科目,不能汇总。编制方案设置错误,请修改该科目编制方案.");
				
				return returnMap ;
			}else{
				
				Map<String, Object> returnMap = new HashMap<String,Object>();
				
				returnMap.put("countValue", sumValue);
				
				return returnMap ;
			}
			
		}
	}
	
	/**
	 * 科室汇总
	 */
	@Override
	public String sumDeptBudgValue(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			// 汇总科室意见 数据
			// 1.汇总各级科目科室年预算值，更新对应医院年预算值
			List<Map<String,Object>> sumList = budgMedIncomeHosYearMapper.sumDeptBudgValue(mapVo);
			
			if(!sumList.isEmpty() || sumList.size() > 0 ){
				budgMedIncomeHosYearMapper.updateHosBudgValue(sumList);
			}else{
				return "{\"error\":\"没有需要汇总的数据,请检查数据后操作!\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败! 方法 sumDeptSuggest\"}";
		}
	}


	@Override
	public List<List<String>> queryExportData(Map<String, Object> entityMap) throws DataAccessException {
		List<List<String>> reList = new ArrayList<List<String>>();
		List<Map<String,Object>> list = (List<Map<String,Object>>) budgMedIncomeHosYearMapper.query(entityMap);
		for (Map<String,Object> o : list) {
			List<String> l = new ArrayList<String>();
			l.add(o.get("year").toString());
			l.add(o.get("subj_code").toString());
			l.add(o.get("subj_name").toString());
			l.add(formatNum(o.get("last_year_income")));
			l.add(formatNum(o.get("budg_value")));
			l.add(o.get("remark") == null ? "" :o.get("remark").toString());
			reList.add(l);
		}
		return reList;
	}
	
	//格式化数字 保留两位小数
	private String formatNum(Object num){
		double value = num == null ? 0 :Double.parseDouble(num.toString());
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		
		return bd.toString();
	}


	@Override
	public List<Map<String, Object>> getPrintData(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedIncomeHosYearMapper.query(mapVo);
		return list;

	}


	@Override
	public String generateResolveRate(List<Map<String, Object>> entityList)
			throws DataAccessException {
		
		try {
			
			List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
			
		
			budgMedIncomeHosYearMapper.deleteBatchResolveRate(entityList);
			budgMedIncomeHosYearMapper.addBatchResolveRate(entityList);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");

		}
	}


	@Override
	public List<Map<String,Object>> queryIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgMedIncomeHosYearMapper.query(entityMap);
		return list;
	}
	
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.toptodown.deptyearinbudg;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgincome.toptodown.deptyearinbudg.MedInDYBudgMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptMonth;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptYear;
import com.chd.hrp.budg.service.budgincome.toptodown.deptyearinbudg.MedInDYBudgService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("medInDYBudgService")
public class MedInDYBudgServiceImpl implements MedInDYBudgService {

	private static Logger logger = Logger.getLogger(MedInDYBudgServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medInDYBudgMapper")
	private final MedInDYBudgMapper medInDYBudgMapper = null;
    
	/**
	 * @Description 
	 * 添加科室年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室年度医疗收入预算
		BudgMedIncomeDeptYear budgMedIncomeDeptYear = queryByCode(entityMap);

		if (budgMedIncomeDeptYear != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medInDYBudgMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();//科室年度业务概率预算  用List
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				//批量 查询 添加数据是否已存在
				String  str = medInDYBudgMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = medInDYBudgMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				
				int state = medInDYBudgMapper.updateBatch(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 批量添加科室年度医疗收入预算<BR> 
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
				
				BudgMedIncomeDeptYear budgMedIncomeDeptYear = queryByCode(item);
				
				if (budgMedIncomeDeptYear != null) {

					str += item.get("year")+"年 科室:"+item.get("dept_name")+" 科目:"+item.get("subj_name")	+";";
				}
			}
			
			if(str != ""){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"已有数据存在.\",\"state\":\"false\"}";
			
			}else{
				
				for(int i= 0 ; i< entityList.size() ; i++){
					entityList.get(i).put("state", "0");
					entityList.get(i).put("reason", "");
					entityList.get(i).put("refer_value", "");
					addList.add(entityList.get(i));
					
					if(i%1000 == 0 ){
						
						medInDYBudgMapper.addBatch(addList);
						
						addList.clear();
						
					}else if ( i == (entityList.size()-1) && addList.size() > 0 ){
						
						medInDYBudgMapper.addBatch(addList);
						
						addList.clear();
					}
					
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medInDYBudgMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medInDYBudgMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medInDYBudgMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室年度医疗收入预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList) throws DataAccessException{
		
		try {
			
			medInDYBudgMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室年度医疗收入预算<BR> 
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
		//判断是否存在对象科室年度医疗收入预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedIncomeDeptYear> list = (List<BudgMedIncomeDeptYear>)medInDYBudgMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = medInDYBudgMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medInDYBudgMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedIncomeDeptYear> list = (List<BudgMedIncomeDeptYear>)medInDYBudgMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeDeptYear> list = (List<BudgMedIncomeDeptYear>)medInDYBudgMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medInDYBudgMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMedIncomeDeptYear
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medInDYBudgMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室年度医疗收入预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedIncomeDeptYear>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap) throws DataAccessException{
		return medInDYBudgMapper.queryExists(entityMap);
	}
	@Override
	public String queryBudgDept(Map<String, Object> mapVo)	throws DataAccessException {
		
		RowBounds rowBoundsALL = new RowBounds(0, 20);
		
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(medInDYBudgMapper.queryBudgDept(mapVo, rowBounds));
	}
	@Override
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException {
		return medInDYBudgMapper.queryDeptExist(mapVo);
	}
	
	//预算分级维护  计算方法
	@Override
	public String collectMedInDYBudgUp(Map<String, Object> mapVo)throws DataAccessException {
		
		try {
			//定义字符串  接收错误信息
			String str = "";
			//查询所选科室 科目
			List<Map<String,Object>> list = (List<Map<String, Object>>) medInDYBudgMapper.queryCountData(mapVo);
			
			if(list.size() == 0){
				
				return "{\"error\":\"未查询到计算数据,请核对所选年度、科目的【医院年度至科室年度医疗收入预算分解方案】数据.\"}";
				
			}
			
			String resolve_way ="";
			for (Map<String, Object> item : list) {
				resolve_way=String.valueOf(item.get("resolve_way"));
				break;
			}
			
			//按历史数据比例分解     上年收入除以该科目所有科室上年收入总和
			if("01".equals(resolve_way)){
				
				for (Map<String, Object> item : list) {
					
					//查询所选年度  科目 下所有科室上年收入总和
					Map<String,Object> sumLastYearIncome = medInDYBudgMapper.querySumLastYearIncome(item);
					
					if(sumLastYearIncome == null){
						return "{\"error\":\"该科目参考年限收入为空,请维护收入执行数据表后操作.\"}";
					}

					
					//判断上年业务量是否为空
					if(item.get("sumValue") == null || "".equals(item.get("sumValue"))){
						str += item.get("year")  + "年度"+ item.get("subj_code") + item.get("subj_name")+"科目" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}else{
						//计算 分解比例
						double resolve_rate=Double.parseDouble(String.valueOf(item.get("sumValue")))/Double.parseDouble(String.valueOf(sumLastYearIncome.get("sumLastIncome")))*100;//数据库存储double数字  *100
						//分解比例  保留  小数点后两位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(String.valueOf(resolve_rate));
						double rate =  b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						//判断本科目下本科室年度收入预算值是否为空
						if(item.get("hyValue") == null ){
							return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+" 的医院年度医疗收入预算未维护,请维护后操作"+"\",\"state\":\"true\"}";
						}
						
						//获取本科目下本科室年度收入预算值
						double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
						
						
						//计算 计算值(计算值为年度预算值*分解比例)
						double count_value=Double.parseDouble(String.valueOf(hyValue))*resolve_rate/100;//除以100 按百分比计算
						
						BigDecimal countValue = new BigDecimal(String.valueOf(count_value));
						double value=countValue.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						
						//更新计算值和预算值
						item.put("count_value", value);
						item.put("budg_value", value);
						
						//表中其余字段数据处理
						if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
							
							item.put("grow_rate", "");
						}
						if(item.get("remark") == null || "".equals(item.get("remark"))){
							
							item.put("remark", "");
						}
						if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
							
							item.put("last_year_income", "");
						}
						if(item.get("state") == null || "".equals(item.get("state"))){
							
							item.put("state", "");
						}
						if(item.get("refer_value") == null || "".equals(item.get("refer_value"))){
							
							item.put("refer_value", "");
						}
						if(item.get("reason") == null || "".equals(item.get("reason"))){
							
							item.put("reason", "");
						}
					}
				}
					
				if(str != ""){
					
					return "{\"error\":\"以下数据:【"+str+"】,参考年限业务量不存在,请维护后操作\",\"state\":\"false\"}";
					
				}else{
					medInDYBudgMapper.updateBatch(list);
				}
				
			}
			
			//历史数据*增长比例    本科目下本科室上年收入*(1+增减比例/100)/本科目下所有科室上年收入*(1+增减比例/100)的总和
			if("02".equals(resolve_way)){
				
				String err = "";//记录增长比例未维护  错误信息
				
				String yearValue = "";//记录医院年度医疗收入预算不存在  错误信息
				
				
				for(Map<String,Object> item : list){
					
					if(item.get("grow_rate") == null){
						err += item.get("year")  + "年度"+ item.get("subj_code") + item.get("subj_name")+"科目" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}
					
					if(item.get("last_year_income") == null){
						str += item.get("year")  + "年度"+ item.get("subj_code") + item.get("subj_name")+"科目" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}
					
					if(item.get("hyValue") == null ){
						
						yearValue += item.get("year")  + "年度"+ item.get("subj_code") + item.get("subj_name")+"科目" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}
				}
				
				if(err != ""){
					
					return "{\"error\":\"以下数据:【"+err+"】增长比例未维护,不允许计算！请维护完增长比例后再点击计算.\"}";
					
				}else if( str != "" ){
					
					return "{\"error\":\"以下数据:【"+str+"】上年医疗收入未维护,不允许计算！请维护上年医疗收入后再点击计算.\"}";
					
				}else if(yearValue != ""){
					
					return "{\"error\":\"以下数据:【"+yearValue+"】医院年度医疗收入预算不存在.\"}";
					
				}else{
					//计算 本科目下所有科室上年收入*(1+增减比例/100)的总和
					double sumAll=0;
					for (Map<String, Object> item : list) {
						sumAll+=Double.parseDouble(String.valueOf(item.get("last_year_income"))) * (1+Double.parseDouble(String.valueOf(item.get("grow_rate")))/100);
					}
					
					//判断增减比例是否为空  为空则按0处理
					for (Map<String, Object> item : list) {
						if(item.get("grow_rate")==null){
							item.put("grow_rate", 0);
						}
						//计算  本科目下本科室上年收入*(1+增减比例/100)
						double income = Double.parseDouble(String.valueOf(item.get("last_year_income"))) * ( 1 + Double.parseDouble(String.valueOf(item.get("grow_rate")))/100);
						
						//计算 分解比例
						double resolve_rate=income/sumAll*100;//数据库存储为数字  不是百分比  *100
						
						//分解比例  保留  小数点后两位数字 四舍五入	
						BigDecimal  b  =   new   BigDecimal(String.valueOf(resolve_rate));
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						//判断本科目下本科室年度收入预算值是否为空
						if(item.get("hyValue") == null ){
							return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+" 的医院年度医疗收入预算未维护,请维护后操作"+"\",\"state\":\"false\"}";
						}
						
						//获取本科目下本科室年度收入预算值
						double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
						
						//计算  计算值  预算值
						double count_value = Double.parseDouble(String.valueOf(hyValue)) * resolve_rate /100;
						
						BigDecimal  count  =   new   BigDecimal(String.valueOf(count_value));
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						//更新 计算值 、预算值
						item.put("count_value", value);
						item.put("budg_value", value);
						
						//表中其余字段数据处理
						if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
							
							item.put("grow_rate", "");
						}
						if(item.get("remark") == null || "".equals(item.get("remark"))){
							
							item.put("remark", "");
						}
						if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
							
							item.put("last_year_income", "");
						}
						if(item.get("state") == null || "".equals(item.get("state"))){
							
							item.put("state", "");
						}
						if(item.get("refer_value") == null || "".equals(item.get("refer_value"))){
							
							item.put("refer_value", "");
						}
						if(item.get("reason") == null || "".equals(item.get("reason"))){
							
							item.put("reason", "");
						}
					}
					medInDYBudgMapper.updateBatch(list);
				}
			}
			
			//全面贯彻   分解比例为100%
			if("03".equals(resolve_way)){
				//确定分解比例
				double resolve_rate=100;
				for (Map<String, Object> item : list) {
					//更新分解比例
					item.put("resolve_rate", resolve_rate);
					
					//判断本科目下本科室年度收入预算值是否为空
					if(item.get("hyValue") == null ){
						return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+" 的医院年度医疗收入预算未维护,请维护后操作"+"\",\"state\":\"false\"}";
					}
					
					//获取本科目下本科室年度收入预算值
					double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
					
					//计算  计算值  预算值
					double count_value= Double.parseDouble(String.valueOf(hyValue)) * resolve_rate /100;
					
					BigDecimal  count  =   new   BigDecimal(String.valueOf(count_value));
					
					double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					//更新计算值   预算值
					item.put("count_value", value);
					item.put("budg_value", value);
					
					//表中其余字段数据处理
					if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
						
						item.put("grow_rate", "");
					}
					if(item.get("remark") == null || "".equals(item.get("remark"))){
						
						item.put("remark", "");
					}
					if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
						
						item.put("last_year_income", "");
					}
					if(item.get("state") == null || "".equals(item.get("state"))){
						
						item.put("state", "");
					}
					if(item.get("refer_value") == null || "".equals(item.get("refer_value"))){
						
						item.put("refer_value", "");
					}
					if(item.get("reason") == null || "".equals(item.get("reason"))){
						
						item.put("reason", "");
					}
				}
				
				medInDYBudgMapper.updateBatch(list);
			}
			
			//平均分摊    分解比例为1/同级科室总和
			if("04".equals(resolve_way)){
				int sumDept=0;
				if(mapVo.get("subj_code").equals("")||mapVo.get("subj_code")==null){
					//查询科目下所有末级科室总和
					 sumDept = medInDYBudgMapper.querySumDeptAll(mapVo);
				}else{
					//查询科目下所有末级科室总和
					 sumDept = medInDYBudgMapper.querySumDept(mapVo);
				}
			
				
				//使用BigDecemal类封装  便于double类型除法计算   且取高精度
				BigDecimal a = new BigDecimal(Double.valueOf(1));
				BigDecimal sum = new BigDecimal(Double.valueOf(sumDept));
				
				
				//计算分解比例
				double resolve_rate = a.divide(sum, 3 , BigDecimal.ROUND_HALF_UP).doubleValue()*100;
				
				//使用普通for循环  借助角标位  确定最后一位的科室
				for (int i = 0; i < list.size(); i++) {
					
					Map<String, Object> item = list.get(i);
					
					//更新分解比例
					item.put("resolve_rate", resolve_rate);
					
					//判断本科目下本科室年度收入预算值是否为空
					if(item.get("hyValue") == null ){
						return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+" 的医院年度医疗收入预算未维护,请维护后操作"+"\",\"state\":\"false\"}";
					}
					
					//获取本科目下本科室年度收入预算值
					double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
					
					//初始化count_value  作为计算值
					double count_value= 0.0;
					
					//计算计算值   预算值
					//如果当前科目为集合中最后一个科目  则 将分解比例四舍五入造成的误差值全部在最后一个科目补上  
					if(i == (list.size()-1)){
						
						// 计算值 = 医院年度预算收入 * 剔除其他科室占比总和后的比例
						count_value = hyValue * (1 - (sumDept - 1) * resolve_rate/100);//数据库存储的分解比例是去掉百分号的值  并未/100 
					}else{
						
						//否则  按计算出来的分解比例计算   计算值 = 医院年度预算收入 * 分解比例
						count_value= Double.parseDouble(String.valueOf(hyValue)) * resolve_rate /100;
					}
					
					BigDecimal  c  =   new   BigDecimal(String.valueOf(count_value));
					
					double value =   c.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					//更新计算值   预算值
					item.put("count_value", value);
					item.put("budg_value", value);
					
					//表中其余字段数据处理
					if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
						
						item.put("grow_rate", "");
					}
					if(item.get("remark") == null || "".equals(item.get("remark"))){
						
						item.put("remark", "");
					}
					if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
						
						item.put("last_year_income", "");
					}
					if(item.get("state") == null || "".equals(item.get("state"))){
						
						item.put("state", "");
					}
					if(item.get("refer_value") == null || "".equals(item.get("refer_value"))){
						
						item.put("refer_value", "");
					}
					if(item.get("reason") == null || "".equals(item.get("reason"))){
						
						item.put("reason", "");
					}
				}
				medInDYBudgMapper.updateBatch(list);
			}
			
			//手动维护
			if("05".equals(resolve_way)){
				//分解比例手动维护   
				for (Map<String, Object> item : list) {

					// 根据 科室、自定义分解系数 计算分解比例(科室年)
					Map<String,Object> rateMap = medInDYBudgMapper.queryResolveDataRate(item);
					
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
						return "{\"error\":\""+item.get("year")+"年 科室:"+item.get("dept_code")+" 科目:"+item.get("subj_code")+" 的医院年度医疗收入预算未维护,请维护后操作"+"\",\"state\":\"false\"}";
					}
					//获取本科目下本科室年度收入预算值
					double hyValue = Double.parseDouble(String.valueOf(item.get("hyValue")));
					
					
					//计算计算值   预算值
					double count_value = Double.parseDouble(String.valueOf(hyValue)) * rate/100;
					
					BigDecimal countValue= new BigDecimal(String.valueOf(count_value));
					
					double value = countValue.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					item.put("count_value", value);
					item.put("budg_value", value);
					
					//表中其余字段数据处理
					if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
						
						item.put("grow_rate", "");
					}
					if(item.get("remark") == null || "".equals(item.get("remark"))){
						
						item.put("remark", "");
					}
					if(item.get("last_year_income") == null || "".equals(item.get("last_year_income"))){
						
						item.put("last_year_income", "");
					}
					if(item.get("state") == null || "".equals(item.get("state"))){
						
						item.put("state", "");
					}
					if(item.get("refer_value") == null || "".equals(item.get("refer_value"))){
						
						item.put("refer_value", "");
					}
					if(item.get("reason") == null || "".equals(item.get("reason"))){
						
						item.put("reason", "");
					}
				}
				medInDYBudgMapper.updateBatch(list);
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员!\"}");
		}
	}
	
	@Override
	public String queryDeptYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String,Object> wayMap = new HashMap<String,Object>();
		
		if(mapVo.get("edit_method") != null){
			 wayMap = medInDYBudgMapper.queryGetWay(mapVo);
		}
		
		Map<String, Object>  map = medInDYBudgMapper.queryDeptYearLastYearWork(mapVo) ;
		
		if(map != null ){
			if(wayMap != null ){
				map.putAll(wayMap);
			}
			return ChdJson.toJson(map);
		}else{
			return ChdJson.toJson(wayMap);
		}
	}
	
	/**
	 * @Description 
	 * 下达 撤回  取消确认科室年度业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String issuedOrRetract(List<Map<String,Object>> entityList)throws DataAccessException{
		String issuedStr = "";
		String retractStr = "";
		String cancelConfirmStr = "";
		try {
			for(Map<String,Object> item: entityList){
				if(item.get("budg_value").toString().equals("null")||item.get("budg_value")==null){
					return "{\"error\":\"预算值不能为空!\",\"state\":\"true\"}";
				}
				String state = medInDYBudgMapper.queryState(item);
				//下发操作 状态判断 下发后 状态为01
				if("1".equals(item.get("flag"))){
					if(state != null && !"03".equals(state)&&!"0".equals(state)){
						issuedStr += "科目 : "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				//撤回操作  状态判断  撤回后 状态为空
				if("2".equals(item.get("flag"))){
					if(!"01".equals(state)){
						retractStr += "科目: "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				
				//取消确认操作  状态判断  取消确认后 状态为01 下发
				if("3".equals(item.get("flag"))){
					if(!"02".equals(state) && !"03".equals(state) ){
						cancelConfirmStr += "科目: "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
			}
			
			if(issuedStr != ""){
				return "{\"error\":\""+issuedStr+"不可重复下发!\",\"state\":\"true\"}";
			}
			
			if(retractStr != ""){
				return "{\"error\":\""+retractStr+"不是下发状态,不可撤回!\",\"state\":\"true\"}";
			}
			
			if(cancelConfirmStr != ""){
				return "{\"error\":\""+cancelConfirmStr+"不是确认（通过或不通过）状态,不可取消确认!\",\"state\":\"true\"}";
			}
			medInDYBudgMapper.issuedOrRetractUpdate(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	
	/**
	 * @Description 
	 * 确认 (通过,不通过)
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String passOrDisPass(List<Map<String,Object>> entityList)throws DataAccessException{
		String 	confirmStr = "";
		try {
			for(Map<String,Object> item: entityList){
				
				String state = medInDYBudgMapper.queryState(item);
				//确认操作  状态判断  确认后 状态为 通过 02  不通过 03
				if(!"01".equals(state)){
					confirmStr += "指标: "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
				}
			}
			
			if(confirmStr != ""){
				return "{\"msg\":\""+confirmStr+"不是下发状态,不可确认!\",\"state\":\"true\"}";
			}
			medInDYBudgMapper.passOrDisPassUpdate(entityList);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}	
	/**
	 * 生成查询数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException {
		return medInDYBudgMapper.queryData(mapVo);
	}
}

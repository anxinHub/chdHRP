/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.downtotop.deptyearbudg;

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
import com.chd.hrp.budg.dao.budgincome.budgdeptresolverate.BudgDeptResolveRateMapper;
import com.chd.hrp.budg.dao.budgincome.budghosresolverate.BudgHosResolveRateMapper;
import com.chd.hrp.budg.dao.budgincome.downtotop.deptyearbudg.BudgMedIncomeDeptYearMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptYear;
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgMedIncomeDeptYearService;
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
 


@Service("budgMedIncomeDeptYearService")
public class BudgMedIncomeDeptYearServiceImpl implements BudgMedIncomeDeptYearService {

	private static Logger logger = Logger.getLogger(BudgMedIncomeDeptYearServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedIncomeDeptYearMapper")
	private final BudgMedIncomeDeptYearMapper budgMedIncomeDeptYearMapper = null;
	
	@Resource(name="budgDeptResolveRateMapper")
	private final BudgDeptResolveRateMapper budgDeptResolveRateMapper = null;
    
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
			
			int state = budgMedIncomeDeptYearMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	
	/**
	 * 保存数据 
	 */
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException{
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		String str="";
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				addList.add(item) ;
				
			}else{ //修改
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				for(Map<String, Object> item : addList) {
					Map<String,Object> mapVo = budgMedIncomeDeptYearMapper.queryByCode(item);
					if(mapVo!=null){
						 str += item.get("subj_code")+";";
					}
				}
				//判断str是否为空,为空正常添加   不为空则返回错误信息
				if(!"".equals(str)){
					return "{\"error\":\"所选年以下度科目编码:"+str+"数据已存在.\",\"state\":\"false\"}";
				}else{
					budgDeptResolveRateMapper.addBatch(addList);
					budgMedIncomeDeptYearMapper.addBatch(addList);
				}
			}
			
			if(updateList.size()>0){
				
				budgDeptResolveRateMapper.updateBatch(updateList);
			    budgMedIncomeDeptYearMapper.updateBatch(updateList);
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
			
			String str="";
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for (Map<String, Object> item : entityList) {
				Map<String,Object> mapVo = budgMedIncomeDeptYearMapper.queryByCode(item);
				if(mapVo!=null){
					 str += item.get("subj_code")+";";
				}
			}
			//判断str是否为空,为空正常添加   不为空则返回错误信息
			if(!"".equals(str)){
				return "{\"error\":\"所选年以下度科目编码:"+str+"数据已存在.\",\"state\":\"false\"}";
			}else{
				for (int i = 0; i < entityList.size(); i++) {
					addList.add(entityList.get(i));
						if(i%1000==0){
							budgDeptResolveRateMapper.addBatch(addList);
							budgMedIncomeDeptYearMapper.addBatch(addList);
							addList.clear();
						}else if (i==(entityList.size()-1) && addList.size()>0 ){
							budgDeptResolveRateMapper.addBatch(addList);
							budgMedIncomeDeptYearMapper.addBatch(addList);
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
	 * 更新科室年度医疗收入预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			budgMedIncomeDeptYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

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
			
			budgDeptResolveRateMapper.updateBatch(entityList);
			
		    budgMedIncomeDeptYearMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

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
			
			int state = budgMedIncomeDeptYearMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

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
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgDeptResolveRateMapper.deleteBatch(entityList);
			 
			budgMedIncomeDeptYearMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");

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
		
		List<BudgMedIncomeDeptYear> list = (List<BudgMedIncomeDeptYear>)budgMedIncomeDeptYearMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMedIncomeDeptYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMedIncomeDeptYearMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

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
			
			List<BudgMedIncomeDeptYear> list = (List<BudgMedIncomeDeptYear>)budgMedIncomeDeptYearMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeDeptYear> list = (List<BudgMedIncomeDeptYear>)budgMedIncomeDeptYearMapper.query(entityMap, rowBounds);
			
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
		return budgMedIncomeDeptYearMapper.queryByCode(entityMap);
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
		return budgMedIncomeDeptYearMapper.queryByUniqueness(entityMap);
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
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeDeptYearMapper.queryExists(entityMap);
	}
	
	/**
	 * 查询上年收入
	 */
	@Override
	public String queryLastYearIncome(Map<String, Object> entityMap) {
		Map<String, Object> mapVo=budgMedIncomeDeptYearMapper.queryLastYearIncome(entityMap);
		if(mapVo == null || "".equals(mapVo)){
			return "{\"error\":\"该科目下该科室上年收入未维护,或该数据不存在,请维护或检查后操作.\"}";
		}
		return ChdJson.toJson(mapVo);
	}
	
	/**
	 * @Description 
	 * 提交  撤回  取消审核  科室年度业务预算(自下而上) (用于区别返回提示信息)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String reviewOrUnreview(List<Map<String,Object>> entityList)throws DataAccessException{
		String issuedStr = "";
		String retractStr = "";
		String cancelConfirmStr = "";
		try {
			for(Map<String,Object> item: entityList){
				String state = budgMedIncomeDeptYearMapper.queryState(item);
				//下发操作 状态判断 下发后 状态为01
				if("1".equals(item.get("flag"))){
					if(state != null && !"03".equals(state)){
						issuedStr += "指标 : "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				//撤回操作  状态判断  撤回后 状态为空
				if("2".equals(item.get("flag"))){
					if(!"01".equals(state)){
						retractStr += "指标: "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				//取消确认操作  状态判断  取消确认后 状态为01 下发
				if("3".equals(item.get("flag"))){
					if(!"02".equals(state) && !"03".equals(state) ){
						cancelConfirmStr += "指标: "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
			}
			
			if(issuedStr != ""){
				return "{\"msg\":\""+issuedStr+"不可重复提交!\",\"state\":\"true\"}";
			}
			
			if(retractStr != ""){
				return "{\"msg\":\""+retractStr+"不是提交状态,不可撤回!\",\"state\":\"true\"}";
			}
			
			if(cancelConfirmStr != ""){
				return "{\"msg\":\""+cancelConfirmStr+"不是审核（通过或不通过）状态,不可取消审核!\",\"state\":\"true\"}";
			}
			
			budgMedIncomeDeptYearMapper.issuedOrRetractUpdate(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	
	/**
	 * @Description 
	 * 确认 (通过,不通过) 科室年度业务预算(自下而上) (用于区别返回提示信息)
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String passOrDisPassDown(List<Map<String,Object>> entityList)throws DataAccessException{
		String 	confirmStr = "";
		try {
			for(Map<String,Object> item: entityList){
				
				String state = budgMedIncomeDeptYearMapper.queryState(item);
				//确认操作  状态判断  确认后 状态为 通过 02  不通过 03
				if(!"01".equals(state)){
					confirmStr += "指标: "+item.get("subj_name")+",科室: "+item.get("dept_name")+"  "; 
				}
			}
			
			if(confirmStr != ""){
				return "{\"msg\":\""+confirmStr+"不是提交状态,不可审核!\",\"state\":\"true\"}";
			}
			
			budgMedIncomeDeptYearMapper.passOrDisPassUpdate(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	
	/**
	 * 计算
	 */
	@Override
	public String collect(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			// 查询计算数据
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgMedIncomeDeptYearMapper.queryCollectData(mapVo);
			
			List<Map<String,Object>> resolveList = new ArrayList<Map<String,Object>>() ;//分解或汇总 为分解时 用List
			
			List<Map<String,Object>> sumList = new ArrayList<Map<String,Object>>() ;//分解或汇总 为汇总时 用List
			
			
			for(Map<String,Object> item : list){
				
				
				if("01".equals(item.get("resolve_or_sum"))){
					
					//根据科目 查询 其同级独立核算科目的预算之和
					double sumValue = budgMedIncomeDeptYearMapper.querySumValue(item);
					
					// 计算 非独立预算科目的预算之和
					
					double sumCountValue =  Double.parseDouble(String.valueOf(item.get("parentValue"))) -  sumValue ;
					
					// 分解方法  01:历史数据比例分解   02:历史数据*增减比例分解   03:全面贯彻   04:平均分摊   05:手工维护分解系数
					
					if("01".equals(item.get("resolve_way"))){//01:历史数据比例分解
						
						// 计算 分解比例
						double  resolve_rate = Double.parseDouble(String.valueOf(item.get("last_year_income")))/ Double.parseDouble(String.valueOf(item.get("sumLastIncome"))) * 100 ;
						//分解比例  保留  小数点后两位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(resolve_rate);
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						item.put("grow_rate", "");
						item.put("resolve_rate", rate);
				
						//计算  计算值
						double countVlaue = sumCountValue * resolve_rate /100;
						
						BigDecimal  count  =   new   BigDecimal(countVlaue);
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						item.put("count_value", value);
						
						item.put("budg_value", value);
						
						resolveList.add(item) ;
						
					}else if("02".equals(item.get("resolve_way"))){ //02:历史数据*增减比例分解

						//上年其同级非独立核算项目的执行数据之和(包含自身)
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
						
						BigDecimal  count  =   new   BigDecimal(countVlaue);
						
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
						
						BigDecimal  count  =   new   BigDecimal(countVlaue);
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						item.put("count_value", value);
						
						item.put("budg_value", value);
						
						resolveList.add(item) ;
						
					}else if("04".equals(item.get("resolve_way"))){ // 04:平均分摊  与其有相同上级科目的同级科目  平均分摊
						
						// 根据 参数 查询  与其上级科目的的一级所有子科目  数量 
						int count = budgMedIncomeDeptYearMapper.querySubjCount(item);		
						
						// 计算 分解比例
						double  resolve_rate = 1/Double.parseDouble(String.valueOf(count)) ;
						//分解比例  保留  小数点后两位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(resolve_rate);
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						item.put("grow_rate", "");
						item.put("resolve_rate", rate);
				
						//计算  计算值
						double countVlaue = sumCountValue * resolve_rate /100;
						
						BigDecimal  value  =   new   BigDecimal(countVlaue);
						
						double count_value =   value.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						item.put("count_value", count_value);
						
						item.put("budg_value", count_value);
						
						resolveList.add(item) ;
						
					}else if("05".equals(item.get("resolve_way"))){ // 05:手工维护分解系数
						
						if(item.get("resolve_rate") == null){
							
							return "{\"error\":\"科目:"+item.get("subj_code")+item.get("subj_name")+"分解方法为手工维护分解系数.分解比例未维护\",\"state\":\"flase\"}" ;
							
						}else{
							//计算  计算值
							double countVlaue = sumCountValue * Double.parseDouble(String.valueOf(item.get("resolve_rate"))) /100;
							
							BigDecimal  value  =   new   BigDecimal(countVlaue);
							
							double count_value =   value.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
							
							item.put("count_value", count_value);
							
							item.put("budg_value", count_value);
							
							resolveList.add(item) ;
						}
					}
						
				}else if("02".equals(item.get("resolve_or_sum"))){
					
					//根据 参数 汇总其所有末级科目预算
					double sumValue = budgMedIncomeDeptYearMapper.querySumSubjValue(item) ;
					
					item.put("count_value", sumValue);
					
					item.put("budg_value", sumValue);
					
					sumList.add(item) ;
					
				}
				
			}
			
			if(resolveList.size() > 0 ){//分解时 更新 科室年度医疗收入预算表 计算值、预算值 及 科室医疗收入科目分解比例维护 表 分解比例
				
				//更新 科室年度医疗收入预算  计算值、预算值
				budgMedIncomeDeptYearMapper.updateValue(resolveList) ;
				
				// 更新 科室医疗收入科目分解比例维护表 分解比例
				budgMedIncomeDeptYearMapper.updateResolveRate(resolveList) ;
			}
			
			if(sumList.size() > 0 ){//汇总时 只更新  可开始年度医疗收入预算表 计算值、预算值
				
				//更新 科室年度医疗收入预算  计算值、预算值
				budgMedIncomeDeptYearMapper.updateValue(sumList) ;
				
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
	}

	/**
	 * 查询要生成的数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgMedIncomeDeptYearMapper.queryData(mapVo);
	}

	/**
	 * 分解计算  生成数据
	 */
	@Override
	public String addGenerateData(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
			
			for (Map<String, Object> item : entityList) {
				
				if("01".equals(item.get("resolve_or_sum"))){
					
					rateList.add(item);
						
				}
			
			}
			
			if(rateList.size() >0 ){
				
				budgDeptResolveRateMapper.addBatch(rateList);
			}
			
			budgMedIncomeDeptYearMapper.addBatch(entityList);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

		}
	}

	@Override
	public List<Map<String, Object>> queryBudgMedIncomeDeptYear(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) budgMedIncomeDeptYearMapper.query(entityMap);
	}

	@Override
	public String generateResolveRateDept(List<Map<String, Object>> entityList)
			throws DataAccessException {
		
		try {
			
			budgDeptResolveRateMapper.deleteBatchgenerateResolveRateDept(entityList);
			
			budgDeptResolveRateMapper.addBatchgenerateResolveRateDept(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}

	@Override
	public List<Map<String, Object>> queryDataDept(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>)budgMedIncomeDeptYearMapper.query(entityMap);

		return list;
	}
}

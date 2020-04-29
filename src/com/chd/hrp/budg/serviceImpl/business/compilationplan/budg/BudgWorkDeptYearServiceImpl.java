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
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.budg.dao.business.budgeworkdeptrate.BudgWorkDeptYearRateMapper;
import com.chd.hrp.budg.dao.business.compilationplan.budg.BudgWorkDeptYearMapper;
import com.chd.hrp.budg.dao.business.compilationplan.budg.BudgWorkHosYearMapper;
import com.chd.hrp.budg.entity.BudgWorkDeptYear;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptYearService;
import com.chd.hrp.budg.service.common.BudgCountProcessService;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
import com.chd.hrp.budg.service.common.BudgFunProcessService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Number;

/**
 * 
 * @Description:
 * 科室年度业务预算
 * @Table:
 * BUDG_WORK_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWorkDeptYearService")
public class BudgWorkDeptYearServiceImpl implements BudgWorkDeptYearService {

	private static Logger logger = Logger.getLogger(BudgWorkDeptYearServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWorkDeptYearMapper")
	private final BudgWorkDeptYearMapper budgWorkDeptYearMapper = null;
	
	@Resource(name = "budgWorkDeptYearRateMapper")
	private final BudgWorkDeptYearRateMapper budgWorkDeptYearRateMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgWorkHosYearMapper")
	private final BudgWorkHosYearMapper budgWorkHosYearMapper = null;
	
	@Resource(name = "budgFormulaSetService") //计算公式service
	private final BudgFormulaSetService budgFormulaSetService = null;
	
	@Resource(name = "budgCountProcessService") //计算过程 service
	private final BudgCountProcessService budgCountProcessService = null;
	
	@Resource(name = "budgFunProcessService") //函数取值过程 service
	private final BudgFunProcessService budgFunProcessService = null;
	
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
			
			if(item.get("rateList") == null){
				continue ;
			}else{
				List<Map<String,Object>> list = (List<Map<String, Object>>)item.get("rateList");
				for(Map<String,Object> itemRate: list){
					rate.add(itemRate);
				}
			}
			
		}
		
		try {
			
			if(addList.size()>0){
				//批量 查询 添加数据是否已存在
				String  str = budgWorkDeptYearMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgWorkDeptYearMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				
				int state = budgWorkDeptYearMapper.updateBatch(updateList);
				
			}
			
			if(rate.size()>0){
				
				budgWorkDeptYearRateMapper.deleteBatch(listVo);
				
				budgWorkDeptYearRateMapper.addBatch(rate);
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 添加科室年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		int count = budgWorkDeptYearMapper.queryDataExist(entityMap);
		
		if (count > 0 ) {

			return "{\"message\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		if(entityMap.get("rateList") != null){
			
			list = (List<Map<String, Object>>)entityMap.get("rateList");
		}
		try {
			
			int state = budgWorkDeptYearMapper.add(entityMap);
			
			if(list.size()>0){
				
				budgWorkDeptYearRateMapper.addBatch(list);
				
			}
			
			return "{\"message\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 ! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加科室年度业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		
		try {
			String str = "";
			
			
			List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item : entityList){
				
				int count = budgWorkDeptYearMapper.queryDataExist(item);
				
				if (count > 0 ) {

					str += item.get("year")+"年 指标编码:"+item.get("index_code")+" 科室:"+item.get("dept_name")+";"	;
				}
				
				if(item.get("rateList") != null){
					List<Map<String,Object>> list = (List<Map<String, Object>>)item.get("rateList");
					for(Map<String,Object> itemRate: list){
						rate.add(itemRate);
					}
				}
			}
			if(str != ""){
				
				return "{\"error\":\"添加失败！"+str.substring(0, str.length()-1)+""+"数据已存在.\",\"state\":\"false\"}";
			
			}else{
				
				if(rate.size()>0){
					
					budgWorkDeptYearRateMapper.addBatch(rate);
					
				}
				
				budgWorkDeptYearMapper.addBatch(entityList);
						
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 ! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新科室年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgWorkDeptYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 ! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新科室年度业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item: entityList){
				
				if(item.get("rateList") == null){
					break ;
				}else{
					List<Map<String,Object>> list = (List<Map<String, Object>>)item.get("rateList");
					for(Map<String,Object> itemRate: list){
						rate.add(itemRate);
					}
				}
			}
			
			if(rate.size()>0){
				
				budgWorkDeptYearRateMapper.addBatch(rate);
			}
			
			budgWorkDeptYearMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"修改失败\"}");
			
			//return "{\"error\":\"更新失败 ! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除科室年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgWorkDeptYearMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 ! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除科室年度业务预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//校验 申请单 是否是新建状态
			int count = budgWorkDeptYearMapper.queryIsExistNoNewBuilt(entityList);
			
			String err = "";//记录返回的错误信息
			
			if(count > 0){
				
				err += "提交记录已不是新建状态";
			}
			
			if("".equals(err)){
				List<Map<String,Object>> rate = new ArrayList<Map<String,Object>>();
				
				for(Map<String,Object> item: entityList){
					
					if(!"1".equals(item.get("is_prob"))){
						break ;
					}else{
						rate.add(item);
					}
				}
				
				if(rate.size()>0){
					
					budgWorkDeptYearRateMapper.deleteBatch(rate);
				}
				
				budgWorkDeptYearMapper.deleteBatch(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}else{
				
				return "{\"error\":\"删除失败."+err+"\",\"state\":\"false\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
			
			//return "{\"error\":\"删除失败 ! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加科室年度业务预算<BR> 
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
		//判断是否存在对象科室年度业务预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkDeptYear> list = (List<BudgWorkDeptYear>)budgWorkDeptYearMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWorkDeptYearMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWorkDeptYearMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 ! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室年度业务预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkDeptYearMapper.query(entityMap);
			
			if("04".equals(entityMap.get("edit_method"))){
				
				for(Map<String,Object> item: list){
					
					List<Map<String,Object>> detailList = (List<Map<String,Object>>)budgWorkDeptYearRateMapper.query(item);
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkDeptYearMapper.query(entityMap, rowBounds);
			
			if("04".equals(entityMap.get("edit_method"))){
				
				for(Map<String,Object> item: list){
					
					List<Map<String,Object>> detailList = (List<Map<String,Object>>)budgWorkDeptYearRateMapper.query(item);
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 查询结果  预算分解页面
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgWorkDeptYearResolve(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkDeptYearMapper.queryBudgWorkDeptYearResolve(entityMap);
			
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkDeptYearMapper.queryBudgWorkDeptYearResolve(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象科室年度业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkDeptYearMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室年度业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWorkDeptYear
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkDeptYearMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室年度业务预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkDeptYear>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkDeptYearMapper.queryExists(entityMap);
	}
	
	@Override
	public String queryDeptYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String,Object> wayMap = new HashMap<String,Object>();
		
		if(mapVo.get("edit_method") != null){
			
			 wayMap = budgWorkDeptYearMapper.queryGetWay(mapVo);
			
		}
		
		Map<String, Object>  map = budgWorkDeptYearMapper.queryDeptYearLastYearWork(mapVo) ;
		
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
	 * 根据 指标 查询 其取值方法
	 * @param mapVo
	 * @return
	 */
	@Override
	public Map<String, Object> queryGetWay(Map<String, Object> mapVo) {
		
		return budgWorkDeptYearMapper.queryGetWay(mapVo);
	}
	
	/**
	 * 计算
	 */
	@Override
	public String collectBudgWorkDeptYearUp(Map<String, Object> mapVo) throws DataAccessException {
		
		//定义字符串  接收上年业务量的 错误信息
		String str = "";
		
		try {
			// 查询 所选年度、指标 的 科室年度业务预算
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgWorkDeptYearMapper.queryCollectData(mapVo);
			
			if(list.size() == 0){
				
				return "{\"error\":\"未查询到计算数据,请核对所选年度、指标的【科室年度业务预算编制方案】数据.\"}";
				
			}
			
			String resolve_way = "";
			
			for(Map<String,Object> item : list){
				
				resolve_way= String.valueOf(item.get("resolve_way"));
				
				break ;
			}
			
			if("01".equals(resolve_way)){ //取值方法为历史数据比例分解时，分解比例=本科室上年业务量/所有科室上年业务量之和

				int deptCount = budgWorkDeptYearMapper.queryIndexDeptCount(mapVo) ;
				
				int i = 1 ;
				
				double dept_rate  = 0;
				double dept_value = 0;
				//查询 所选年度、指标 的 所有科室上年业务量 总和

				Map<String,Object> sumLastWork = budgWorkDeptYearMapper.querySumLastYearWork(mapVo);
				
				//判断上年业务总量是否为空
				if(sumLastWork == null){
					return "{\"error\":\"年度为:"+mapVo.get("year")+"  指标为"+mapVo.get("index_code")+"的数据,所有科室参考年限业务量未维护,请维护后操作\"}";
				}
				
				for(Map<String,Object> item : list){
					
					
						
					//判断上年业务量是否为空
					if(item.get("sumValue") == null || "".equals(item.get("sumValue"))){
						str += item.get("year")  + "年度"+ item.get("index_code") + item.get("index_name")+"指标" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}else{
						
						if(i == deptCount){//最后一个科室，补偿因保留小数位数计算造成的 计算误差
							
							//更新 分解比例
							item.put("resolve_rate", 100.00 - dept_rate);
							
							double remainder_value =  Double.parseDouble(String.valueOf(item.get("yearValue")))- dept_value ; 
							
							//更新 计算值 预算值
							item.put("count_value", remainder_value);
							item.put("budg_value", remainder_value);
							
						}else{
							
						// 计算 分解比例
						double  resolve_rate = Double.parseDouble(String.valueOf(item.get("sumValue")))/Double.parseDouble(String.valueOf(sumLastWork.get("sumLastWorkload"))) * 100 ;
						//分解比例  保留  小数点后两位数字 四舍五入		
						BigDecimal  b  =   new   BigDecimal(resolve_rate);
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						dept_rate = dept_rate + rate;
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						if(item.get("yearValue") == null){
							return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+"指标,医院年度业务量未维护,请维护后操作\"}";
						}
						//计算  计算计算值
						double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * resolve_rate /100;
						
						BigDecimal  count  =   new   BigDecimal(countVlaue);
						
						double value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						dept_value = dept_value + value;
						//更新 计算值 、预算值
						item.put("count_value", value);
						item.put("budg_value", value);
						
						i= ++i;
						
						}
						// 空值  处理
						if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
							item.put("grow_rate", "");
						}
						if(item.get("grow_value") == null || "".equals(item.get("grow_value"))){
							item.put("grow_value", "");
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
						
						if(item.get("remark") == null || "".equals(item.get("remark"))){
							item.put("remark", "");
						}
					}
					
				}
				if(str != ""){
					
					return "{\"message\":\"以下数据:【"+str+"】,参考年限业务量不存在,请维护后操作\",\"state\":\"false\"}";
					
				}
				budgWorkDeptYearMapper.updateBatch(list);
				
			}
			
			if("02".equals(resolve_way)){
				
				// 取值方法为历史数据*增减比例时，分解比例=本科室上年业务量*（1+增减比例）/本科室上年业务量*（1+增减比例）之和
				
				String err = "";//记录增长比例未维护  错误信息
				
				String yearValue = "";//记录医院年度预算不存在  错误信息
				
				
				for(Map<String,Object> item : list){
					
					if(item.get("grow_rate") == null){
						err += item.get("year")  + "年度"+ item.get("index_code") + item.get("index_name")+"指标" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}
					
					if(item.get("last_year_workload") == null){
						str += item.get("year")  + "年度"+ item.get("index_code") + item.get("index_name")+"指标" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}
					
					if(item.get("yearValue") == null ){
						
						yearValue += item.get("year")  + "年度"+ item.get("index_code") + item.get("index_name")+"指标" +item.get("dept_code") + item.get("dept_name")+"科室;" ;
					}
				}
				
				if(err != ""){
					
					return "{\"error\":\"以下数据:【"+err+"】增长比例未维护,不允许计算！请维护完增长比例后再点击计算.\"}";
					
				}else if( str != "" ){
					
					return "{\"error\":\"以下数据:【"+str+"】上年业务量未维护,不允许计算！请维护上年业务量后再点击计算.\"}";
					
				}else if(yearValue != ""){
					
					return "{\"error\":\"以下数据:【"+yearValue+"】医院年度预算不存在.\"}";
					
				}else{
					// 统计 各科室上年业务量*（1+增减比例）之和
					double sum = 0.0; 
					
					for(Map<String,Object> item : list){
						
						sum += Double.parseDouble(String.valueOf(item.get("last_year_workload"))) * ( 1 + Double.parseDouble(String.valueOf(item.get("grow_rate")))/100) ;
					}
					
					//计算分解比例、计算值、预算值
					
					for(Map<String,Object> item : list){
						//本科室上年业务量*（1+增减比例）
						double work = Double.parseDouble(String.valueOf(item.get("last_year_workload"))) * ( 1 + Double.parseDouble(String.valueOf(item.get("grow_rate")))/100);
						
						// 计算 分解比例
						
						double resolve_rate = work / sum * 100 ;
						
						//分解比例  保留  小数点后两位数字 四舍五入	
						BigDecimal  b  =   new   BigDecimal(resolve_rate);
						
						double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
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
						if(item.get("grow_value") == null || "".equals(item.get("grow_value"))){
							item.put("grow_value", "");
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
						
						if(item.get("remark") == null || "".equals(item.get("remark"))){
							item.put("remark", "");
						}
						
						
					}
					
					budgWorkDeptYearMapper.updateBatch(list);
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
					
					if(item.get("grow_value") == null || "".equals(item.get("grow_value"))){
						item.put("grow_value", "");
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
					
					if(item.get("remark") == null || "".equals(item.get("remark"))){
						item.put("remark", "");
					}
				}
				
				budgWorkDeptYearMapper.updateBatch(list);
					
			}
			
			if("04".equals(resolve_way)){
				
				//  取值方法为平均分摊时，分解比例=1/指标对应的科室数量
				
				// 查询 指标对应的科室数量
				int deptCount = budgWorkDeptYearMapper.queryIndexDeptCount(mapVo) ;
				
				if(deptCount == 0){
					
					return "{\"error\":\"该指标对应科室关系数据未维护.\"}";
					
				}
				
				// 计算 分解比例
				double resolve_rate = 1/Double.parseDouble(String.valueOf(deptCount)) * 100 ;
				
				//分解比例  保留  小数点后两位数字 四舍五入	
				BigDecimal  b  =   new   BigDecimal(resolve_rate);
				
				double rate =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
				
				int i = 1 ;
				
				double value =  0.00 ;
				
				//计算分解比例、计算值、预算值
				for(Map<String,Object> item : list){
					
					if(item.get("yearValue") == null){
						return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+"指标,医院年度业务量未维护,请维护后操作\"}";
					}
					
					double countVlaue = Double.parseDouble(String.valueOf(item.get("yearValue"))) * rate /100;
					
					BigDecimal  count  =   new   BigDecimal(countVlaue);
					
					if(i == deptCount){//最后一个科室，补偿因保留小数位数计算造成的 计算误差
						
						//更新 分解比例
						item.put("resolve_rate", 100 - (deptCount-1)*rate);
						
						value =   Double.parseDouble(String.valueOf(item.get("yearValue")))-(deptCount-1)*count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
					}else{
						
						//更新 分解比例
						item.put("resolve_rate", rate);
						
						//计算  计算值
						value =   count.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
					}
					
					
					//更新 计算值 、预算值
					item.put("count_value", value);
					item.put("budg_value", value);
					
					// 空值  处理
					
					if(item.get("grow_rate") == null || "".equals(item.get("grow_rate"))){
						item.put("grow_rate", "");
					}
					
					if(item.get("grow_value") == null || "".equals(item.get("grow_value"))){
						item.put("grow_value", "");
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
					
					if(item.get("remark") == null || "".equals(item.get("remark"))){
						item.put("remark", "");
					}
					
					i = ++i ;
					
				}
				
				budgWorkDeptYearMapper.updateBatch(list);
					
			}
			
			
			if("05".equals(resolve_way)){
				
				//  取值方法为手工录入时，分解比例手动维护 
				
				// 根据 自定义分解系数 计算 分解比例   计算值、预算值
				for(Map<String,Object> item : list){
					
					// 根据 科室、自定义分解系数 计算分解比例(科室年)
					Map<String,Object> rateMap = budgWorkDeptYearMapper.queryResolveDataRate(item);
					
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
						return "{\"error\":\""+item.get("year")+"年度 "+item.get("index_code")+"指标,医院年度业务量未维护,请维护后操作\"}";
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
					
					if(item.get("grow_value") == null || "".equals(item.get("grow_value"))){
						item.put("grow_value", "");
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
					
					if(item.get("remark") == null || "".equals(item.get("remark"))){
						item.put("remark", "");
					}
					
				}
				
				budgWorkDeptYearMapper.updateBatch(list);
			}

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败\"}";
		}
		
	}
	@Override
	public Map<String, Object> queryIndexDeptSet(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String, Object>  map = budgWorkDeptYearMapper.queryIndexDeptSet(mapVo) ;
		
		return map;
	}
	
	
	/**
	 * 生成 科室年度业务概率预算 运营尺度数据
	 */
	@Override
	public String queryProbBudgRate(Map<String, Object> entityMap) throws DataAccessException {
			
		List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkDeptYearMapper.queryProbBudgRate(entityMap);
		
		return ChdJson.toJson(list);
			
	}
	@Override
	public String queryBudgWorkDeptYearDown(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = budgWorkDeptYearMapper.queryBudgWorkDeptYearDown(entityMap);
			
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = budgWorkDeptYearMapper.queryBudgWorkDeptYearDown(entityMap, rowBounds);
			
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	/***
	 * 增量生成时 查询要生成的数据
	 */
	@Override
	public List<Map<String, Object>> queryData(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgWorkDeptYearMapper.queryData(entityMap);
	}
	
	/**
	 * 根据主键 查询数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> entityMap)	throws DataAccessException {
		
		return budgWorkDeptYearMapper.queryDataExist(entityMap);
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 预算指标下拉框
	 */
	@Override
	public String queryBudgIndex(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgWorkDeptYearMapper.queryBudgIndex(entityMap, rowBounds));
	}
	
	/**
	 * 预算指标下拉框
	 */
	@Override
	public String queryBudgIndexDeptSet(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgWorkDeptYearMapper.queryBudgIndexDeptSet(entityMap, rowBounds));
	}
	
	/**
	 * 科室年度业务预算  确定预算 计算（自下而上）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String collectCertenDYBudgData(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			List<Map<String,Object>> list = (List<Map<String, Object>>) budgWorkDeptYearMapper.query(mapVo);
			
			if(list.size()>0){
				for(Map<String,Object> item : list){
					
					//空值处理
					if(item.get("remark") == null ){
						item.put("remark", "");
					}
					
					if(item.get("budg_value") == null ){
						item.put("budg_value", "");
					}
					
					if(item.get("count_value") == null ){
						item.put("count_value", "");
					}
					
					if(item.get("grow_rate") == null ){
						item.put("grow_rate", "");
					}
					
					if(item.get("grow_value") == null ){
						item.put("grow_value", "");
					}
					
					if(item.get("resolve_rate") == null ){
						item.put("resolve_rate", "");
					}
					
					if(item.get("last_year_workload") == null ){
						item.put("last_year_workload", "");
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
					
					item.put("budg_year", item.get("year")) ;//字段转换
					
					item.put("budg_level",mapVo.get("budg_level"));
					
					//根据 所传 指标编码 查询其取值方法
					Map<String, Object> map = budgWorkHosYearMapper.queryIndexGetWay(item);
					
					
					if(map != null){
						
						if(map.get("formula_id") != null){//计算公式取值
							
							map.put("year", item.get("year"));
							
							Map<String,Object> formula =  budgFormulaSetService.queryByCode(map);
							
							formula.put("year", item.get("year"));
							
							formula.put("index_code", item.get("index_code"));
							
							formula.put("element_type_code", "03");//元素类型 01 基本指标     02 费用指标 03预算指标 04 收入科目 05支出科目
							
							formula.put("element_level", "03");//element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
							
							formula.put("value_type_code", "01");//value_type_code:元素值类型  01 本年预算 02 上年执行
							
							formula.put("dept_id", item.get("dept_id"));//dept_id: 科室id 取预算值时使用
							
							List<String> indexList = new ArrayList<String>();
							
							indexList.add(String.valueOf(formula.get("element_type_code"))+item.get("index_code")+formula.get("element_level")+"01");//递归校验用
							
							//查询 计算公式元素栈 数据   格式{"formula_stack" : "element_level@element_code@value_type_code@element_type_code;...."}
							// element_level:预算层次  01 医院年度 02 医院月份 03 科室年度 04 科室月份
							//element_code：元素编码
							//value_type_code: 元素值类型  01 本年预算 02 上年执行
							//element_type_code： 元素类型 01 基本指标 02 费用指标 03预算指标 04 收入科目 05支出科目
							Map<String,Object> stack = budgFormulaSetService.queryFormulaStack(map);
							
							String[] element = stack.get("formula_stack").toString().split(";");
							
							 
							Map<String,Object>  countItem = JSONObject.parseObject(budgCountProcessService.queryCountProcess(formula,indexList));
							
							if(countItem.get("error") ==null){//计算公式 计算项取值没有错误时
								// 计算公式 每个计算元素值List
								List<Map<String,Object>> data = JsonListMapUtil.getListMap(countItem.get("Rows").toString());
								
								// 存放 key：格式element_level@element_code@value_type_code@element_type_code(解析公式 替换  每个计算元素值用)；  value：值
								Map<String,Object>  replaceMap = new HashMap<String,Object>();
								
								for(int i= 0 ; i<element.length;i++){
									for(Map<String,Object> val : data){
										if(String.valueOf(i+1).equals(String.valueOf(val.get("stack_seq_no")))){
											replaceMap.put(element[i], val.get("count_value"));
										}
									}
									
								}
								
								String formula_en = formula.get("formula_en").toString();//要解析的计算公式字符串
								
								formula_en = formula_en.replace("%", "/100");//百分号 解析替换
								
								for(String str: replaceMap.keySet()){
									
									formula_en = formula_en.replace(str, replaceMap.get(str).toString());//将每个计算元素替换为对应的值
									
									
								}
								
								Double budg_value = 0.00 ;
								try {
									
									budg_value = Double.parseDouble(String.valueOf( new ScriptEngineManager().getEngineByName("JavaScript").eval(formula_en)));
									
									//分解比例  保留  小数点后4位数字 四舍五入		
									BigDecimal  b  =   new   BigDecimal(budg_value);
									
									budg_value =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();  
									
									item.put("budg_value", budg_value);
									
									item.put("count_value", budg_value);
								} catch (ScriptException e) {
									
									return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+"对应计算公式解析失败,无法计算.请核对该计算公式\",\"state\":\"false\"}";
								}
							}else{
								
								return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+countItem.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
						
						if(map.get("fun_id") != null){ //取值函数 取值
							
							map.put("year", item.get("year"));
							
							map.put("fun_code", map.get("fun_id"));
							
							map.put("index_type_code", "03");//指标类型  01基本指标 02费用标准 03预算指标 04收入科目
							
							map.put("budg_year", item.get("year")) ;//字段转换
							
							map.put("budg_level",mapVo.get("budg_level"));
							
							//查询 函数相关信息
							Map<String,Object> funMap = JSONObject.parseObject(budgFunProcessService.queryFunProcess(map));
							
							if(funMap.get("error") ==null){
								
								List<Map<String,Object>> data = JsonListMapUtil.getListMap(funMap.get("Rows").toString());
								
								item.put("budg_value", data.get(0).get("count_value"));
								
								item.put("count_value", data.get(0).get("count_value"));
								
							}else{
								
								return "{\"error\":\"指标【:"+item.get("index_code")+" "+item.get("index_name")+funMap.get("error")+"\",\"state\":\"false\"}";
								
							}
							
						}
					}
				}
				
				budgWorkDeptYearMapper.updateBatch(list);
				
				return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"error\":\"没有计算数据.\",\"state\":\"false\"}";
				
			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
	}
	
	@Override
	public String getGrowRate(Map<String, Object> mapVo) throws DataAccessException {
		
		List<Map<String, Object>>  list = budgWorkDeptYearMapper.getGrowRate(mapVo) ;
		
		return ChdJson.toJson(list);
		
	}
	
	/**
	 * @Description 
	 * 科室年度业务预算增量预算  更新 增长比例 及相关数据数据
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateGrowRate(List<Map<String,Object>> listVo)throws DataAccessException{
		
		try {
			
		  int state = budgWorkDeptYearMapper.updateGrowRate(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作成功\",\"state\":\"false\"}";

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
				String state = budgWorkDeptYearMapper.queryState(item);
				//下发操作 状态判断 下发后 状态为01
				if("1".equals(item.get("flag"))){
					if(state != null && !"03".equals(state)){
						issuedStr += "指标 : "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				//撤回操作  状态判断  撤回后 状态为空
				if("2".equals(item.get("flag"))){
					if(!"01".equals(state)){
						retractStr += "指标: "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				
				//取消确认操作  状态判断  取消确认后 状态为01 下发
				if("3".equals(item.get("flag"))){
					if(!"02".equals(state) && !"03".equals(state) ){
						cancelConfirmStr += "指标: "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				
			}
			
			if(issuedStr != ""){
				return "{\"msg\":\""+issuedStr+"不可重复下发!\",\"state\":\"true\"}";
			}
			
			if(retractStr != ""){
				return "{\"msg\":\""+retractStr+"不是下发状态,不可撤回!\",\"state\":\"true\"}";
			}
			
			if(cancelConfirmStr != ""){
				return "{\"msg\":\""+cancelConfirmStr+"不是确认（通过或不通过）状态,不可取消确认!\",\"state\":\"true\"}";
			}
			
			budgWorkDeptYearMapper.issuedOrRetractUpdate(entityList);
			
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
				
				String state = budgWorkDeptYearMapper.queryState(item);
				//确认操作  状态判断  确认后 状态为 通过 02  不通过 03
				if(!"01".equals(state)){
					confirmStr += "指标: "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
				}
			}
			
			if(confirmStr != ""){
				return "{\"msg\":\""+confirmStr+"不是下发状态,不可确认!\",\"state\":\"true\"}";
			}
			
			budgWorkDeptYearMapper.passOrDisPassUpdate(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}
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
				String state = budgWorkDeptYearMapper.queryState(item);
				//下发操作 状态判断 下发后 状态为01
				if("1".equals(item.get("flag"))){
					if(state != null && !"03".equals(state)){
						issuedStr += "指标 : "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				//撤回操作  状态判断  撤回后 状态为空
				if("2".equals(item.get("flag"))){
					if(!"01".equals(state)){
						retractStr += "指标: "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
					}
				}
				//取消确认操作  状态判断  取消确认后 状态为01 下发
				if("3".equals(item.get("flag"))){
					if(!"02".equals(state) && !"03".equals(state) ){
						cancelConfirmStr += "指标: "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
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
			
			budgWorkDeptYearMapper.issuedOrRetractUpdate(entityList);
			
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
				
				String state = budgWorkDeptYearMapper.queryState(item);
				//确认操作  状态判断  确认后 状态为 通过 02  不通过 03
				if(!"01".equals(state)){
					confirmStr += "指标: "+item.get("index_name")+",科室: "+item.get("dept_name")+"  "; 
				}
			}
			
			if(confirmStr != ""){
				return "{\"msg\":\""+confirmStr+"不是提交状态,不可审核!\",\"state\":\"true\"}";
			}
			
			budgWorkDeptYearMapper.passOrDisPassUpdate(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	@Override
	public List<Map<String, Object>> getPrintData(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String,Object>> list = (List<Map<String,Object>>) budgWorkDeptYearMapper.query(mapVo);
	
		return list;
	}
}

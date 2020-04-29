/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcash;

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
import com.chd.hrp.budg.dao.budgcash.BudgCashExeMapper;
import com.chd.hrp.budg.dao.budgcash.BudgCashFlowExeMapper;
import com.chd.hrp.budg.dao.budgcash.BudgCashMapper;
import com.chd.hrp.budg.entity.BudgCashFlow;
import com.chd.hrp.budg.service.budgcash.BudgCashFlowExeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 现金流量预算
 * @Table:
 * BUDG_CASH_FLOW
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgCashFlowExeService")
public class BudgCashFlowExeServiceImpl implements BudgCashFlowExeService {

	private static Logger logger = Logger.getLogger(BudgCashFlowExeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCashFlowExeMapper")
	private final BudgCashFlowExeMapper budgCashFlowExeMapper = null;
	//引入DAO操作
	@Resource(name = "budgCashExeMapper")
	private final BudgCashExeMapper budgCashExeMapper = null;
    
	//引入DAO操作
	@Resource(name = "budgCashMapper")
	private final BudgCashMapper budgCashMapper = null;
	
	/**
	 * @Description 
	 * 添加现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象是否存在
		int count = budgCashFlowExeMapper.queryDataExists(entityMap);

		if (count > 0) {

			return "{\"message\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		try {
			
			int state = budgCashFlowExeMapper.add(entityMap);
			
			//添加成功后  根据现金流量项目ID查询流向
			String cash_dire = budgCashFlowExeMapper.queryCashDire(entityMap);
			
			return "{\"message\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
					+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
					+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()
					+"\",\"cash_dire\":\""+cash_dire+"\",\"cash_item_id\":\""+entityMap.get("cash_item_id").toString()+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			//throw new SysException(e.getMessage(),e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	/**
	 * @Description 
	 * 批量添加现金流量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCashFlowExeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgCashFlowExeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新现金流量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgCashFlowExeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 删除现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgCashFlowExeMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除现金流量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCashFlowExeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			//throw new SysException(e.getMessage(),e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	
	/**
	 * @Description 
	 * 添加现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			String errStr = "";
			String monthErr = "";
			
			//创建map 封装主键数据 用于查询月份数据是否已结转
			Map<String, Object> flagMap = new HashMap<String, Object>();
			flagMap.put("group_id", entityMap.get("group_id"))   ;
			flagMap.put("hos_id", entityMap.get("hos_id"))   ;
			flagMap.put("copy_code", entityMap.get("copy_code"))   ;
			flagMap.put("year", entityMap.get("year"))   ;
			flagMap.put("month", entityMap.get("month"))   ;
			
			//查询当前月份数据是否已结转
			String cashFlag = budgCashFlowExeMapper.queryMonthFlag(flagMap);
			
			if("1".equals(cashFlag)){
				monthErr += "现金流量项目 :"+entityMap.get("cash_item_name")+"  "+ entityMap.get("month")+"月份 ;";
			}
			
			//查询系统启用日期  (月份)  进行比较  小于系统启用日期则返回错误信息
			String startMonth = budgCashFlowExeMapper.querySysMonth(flagMap);
			
			if(startMonth != null ){
				int sysMonth = Integer.parseInt(startMonth);
				
				int month = Integer.parseInt(String.valueOf(entityMap.get("month")));
				
				if(month < sysMonth && !"".equals(entityMap.get("amount")) && Double.parseDouble(String.valueOf(entityMap.get("amount"))) > 0){
					errStr += "现金流量项目 :"+entityMap.get("cash_item_name")+"  "+ entityMap.get("month")+"月份 ;";
				}
				
			}
			
			if(!"".equals(monthErr)){
				return "{\"error\":\"更新失败！"+monthErr.substring(0, monthErr.length()-1)+""+"该月份数据已结转,不可编辑该数据.\",\"state\":\"false\"}";
			}
			
			if(!"".equals(errStr)){
				return "{\"error\":\"更新失败！"+errStr.substring(0, errStr.length()-1)+""+"月份在系统启用日期之前,不可编辑该数据.\",\"state\":\"false\"}";
			}
			
			int count = budgCashFlowExeMapper.queryDataExists(entityMap);
			
			if (count>0) {

				budgCashFlowExeMapper.update(entityMap);
				return "{\"state\":\"true\"}";
			}else{
			
				budgCashFlowExeMapper.add(entityMap);
				
				return "{\"message\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
						+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
						+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()
						+"\",\"cash_item_id\":\""+entityMap.get("cash_item_id").toString()+"\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	/**
	 * @Description 
	 * 查询结果集现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCashFlow> list = (List<BudgCashFlow>)budgCashFlowExeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCashFlow> list = (List<BudgCashFlow>)budgCashFlowExeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象现金流量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashFlowExeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金流量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgCashFlow
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashFlowExeMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金流量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgCashFlow>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashFlowExeMapper.queryExists(entityMap);
	}
	
	/**
	 * 1 后台查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总 之后  插入或更新入现金流量执行数据   budg_cash_flow
	 * 2 对现金流量执行数据未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量预算表  budg_cash
	 */
	@Override
	public String collectBudgCashFlowExe(Map<String,Object> entityMap)throws DataAccessException{
		
		//定义月份集合  用于封装月份参数  初始化
		List<Map<String, Object>> initList = new ArrayList<Map<String,Object>>();
		
		//定义变量   接收数据
		Double cash_begin = 0.0;//期初现金存量
		Double cash_in = 0.0;//现金流入
		Double cash_out = 0.0;//现金流出
		Double cash_add = 0.0;//现金净增加额
		Double cash_end = 0.0;//期末现金存量
		
		try {
			//查询结账表中已结账数据中  最大月份  
			List<Map<String, Object>> carryList = budgCashFlowExeMapper.queryMaxMonthFromBudgCarry(entityMap);
			
			int minMonth;
			//如果存在已结账的数据   则判断结账月份是否是12月
			if(carryList.size() > 0){
				minMonth = Integer.parseInt(carryList.get(0).get("month").toString());//已结账最大月份
				if(12 == minMonth){
					return "{\"error\":\"当前年度数据已全部结账,不可计算！\"}";
				}
				
				String mon = "";//结账月份的下一月  即 未结账月份的第一个月
				if(minMonth < 10){
					mon = "0" + (minMonth+1) ;
				}else{
					mon = "" + (minMonth+1);
				}
				//不是12月份  则封装参数  用于删除未结账月份数据
				entityMap.put("month",mon);
				
				//查询流量预算表中 数据最大月份和最小月份
				Map<String, Object> monthMap = budgCashFlowExeMapper.queryMaxMonthFromCashFlow(entityMap);
				int maxMonth = Integer.parseInt(String.valueOf(monthMap.get("maxMonth"))) ;
				
				//清除最大月 最小月键值对 
				monthMap.remove("minMonth");
				monthMap.remove("maxMonth");
				
				String month = "";
				for (int i = minMonth+1 ; i <= maxMonth; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.putAll(monthMap);
					if(i<10){
						month = "0" + i;
					}else{
						month = "" + i ;
					}
					map.put("month",month);
					initList.add(map);
				}
				budgCashFlowExeMapper.delectCash(entityMap);
				budgCashFlowExeMapper.addInitBudgCashBatch(initList);
			
				//查询期初货币资金是否记账
				String state = budgCashMapper.queryCashBeginState(entityMap);
				if(!"1".equals(state)){
					return "{\"error\":\"期初数据未记账,存量预算不可计算!\"}";
				}
				
				//根据需求条件   查询现金流量预算表中数据
				//对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出
				List<Map<String, Object>> cashFlowList = budgCashFlowExeMapper.queryCashFlow(entityMap);
				
				//存在  则将查出的结转值作为存量表中第一个月份期初值
				cash_end = Double.parseDouble(carryList.get(0).get("cash_end").toString());
				
				//遍历 查询到的现金流量预算表中数据
				for (Map<String, Object> item : cashFlowList) {
					
					//定义一个map  用于接收需要保存的数据
					Map<String , Object> map = new HashMap<String, Object>();
					
					//集合已做升序排列  遍历第一条为已结账月份 下一月  则已结账月份的期末  = 该数据月份的期初  依次下推
					cash_begin = cash_end;
					//取出累计流入
					cash_in = Double.valueOf(String.valueOf(item.get("cash_in")));
					//取出累计流入
					cash_out = Double.valueOf(String.valueOf(item.get("cash_out")));
					//取出累计流入
					cash_add = cash_in - cash_out;
					//取出累计流入
					cash_end = cash_begin + cash_add;
					
					item.put("cash_begin", cash_begin);
					item.put("cash_add", cash_add);
					item.put("cash_end", cash_end);
					
				}
				//先删除   后添加
				budgCashExeMapper.delete(entityMap);
			    budgCashFlowExeMapper.addBudgCashBatch(cashFlowList);
			}else {
				
				//查询系统启用年月
				Map<String , Object> sysMap = budgCashFlowExeMapper.querySysYearMonth(entityMap);
				
				int sysYear = Integer.parseInt(sysMap.get("start_year").toString());
				
				int budg_year = Integer.parseInt(entityMap.get("year").toString());
				
				if(budg_year != sysYear){
					return "{\"error\":\"系统启用后从未结账且当前年度不是系统启用年度,请检查本年度之前各年度结账情况后操作！\"}";
				}
				
				entityMap.put("month", sysMap.get("start_month"));
				
				//当前最小月份为系统启用月份
				minMonth = Integer.parseInt(sysMap.get("start_month").toString());
				//查询流量预算表中 数据最大月份和最小月份
				Map<String, Object> monthMap = budgCashFlowExeMapper.queryMaxMonthFromCashFlow(entityMap);
				int maxMonth = Integer.parseInt(String.valueOf(monthMap.get("maxMonth"))) ;
				
				//清除最大月 最小月键值对 
				monthMap.remove("minMonth");
				monthMap.remove("maxMonth");
				
				String month = "";
				for (int i = minMonth ; i <= maxMonth; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.putAll(monthMap);
					if(i<10){
						month = "0" + i;
					}else{
						month = "" + i ;
					}
					map.put("month",month);
					initList.add(map);
				}
				budgCashFlowExeMapper.delectCash(entityMap);
				budgCashFlowExeMapper.addInitBudgCashBatch(initList);
				
				//查询期初货币资金是否记账
				String state = budgCashMapper.queryCashBeginState(entityMap);
				if(!"1".equals(state)){
					return "{\"error\":\"期初数据未记账,存量预算不可计算!\"}";
				}
				
				//不存在  以期初货币资金表中期初现金存量作为系统启用月份的期初现金存量
				//查询期初货币资金表中期初现金存量  并用cash_end接收
				cash_end = budgCashFlowExeMapper.queryCashBegin(entityMap);
				
				//根据需求条件   查询现金流量预算表中数据
				//对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出
				List<Map<String, Object>> cashFlowList = budgCashFlowExeMapper.queryCashFlow(entityMap);
				
				//遍历 查询到的现金流量预算表中数据
				for (Map<String, Object> item : cashFlowList) {
					
					//定义一个map  用于接收需要保存的数据
					Map<String , Object> map = new HashMap<String, Object>();
					
					//集合已做升序排列  遍历第一条为已结账月份 下一月  则已结账月份的期末  = 该数据月份的期初  依次下推
					cash_begin = cash_end;
					//取出累计流入
					cash_in = Double.valueOf(String.valueOf(item.get("cash_in")));
					//取出累计流入
					cash_out = Double.valueOf(String.valueOf(item.get("cash_out")));
					//取出累计流入
					cash_add = cash_in - cash_out;
					//取出累计流入
					cash_end = cash_begin + cash_add;
					
					item.put("cash_begin", cash_begin);
					item.put("cash_add", cash_add);
					item.put("cash_end", cash_end);
					
				}
				//先删除   后添加
				budgCashExeMapper.delete(entityMap);
				budgCashFlowExeMapper.addBudgCashBatch(cashFlowList);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败！\"}");

		}
		
	}
	
	/**
	 * 预算年度  下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryBudgYear(Map<String, Object> mapVo)	throws DataAccessException {
		
		return JSON.toJSONString(budgCashFlowExeMapper.queryBudgYear(mapVo));
	}
	/**
	 * 预算月份  下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryBudgMonth(Map<String, Object> mapVo)	throws DataAccessException {
		
		return JSON.toJSONString(budgCashFlowExeMapper.queryBudgMonth(mapVo));
	}
	
}

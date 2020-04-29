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
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgcash.BudgCashFlowMapper;
import com.chd.hrp.budg.dao.budgcash.BudgCashMapper;
import com.chd.hrp.budg.entity.BudgCash;
import com.chd.hrp.budg.service.budgcash.BudgCashService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 现金存量预算
 * @Table:
 * BUDG_CASH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgCashService")
public class BudgCashServiceImpl implements BudgCashService {

	private static Logger logger = Logger.getLogger(BudgCashServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCashMapper")
	private final BudgCashMapper budgCashMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgCashFlowMapper")
	private final BudgCashFlowMapper budgCashFlowMapper = null;
    
	/**
	 * @Description 
	 * 添加现金存量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象现金存量预算
		BudgCash budgCash = queryByCode(entityMap);

		if (budgCash != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgCashMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加现金存量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCashMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新现金存量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgCashMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新现金存量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgCashMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除现金存量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgCashMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除现金存量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		//定义错误信息字符串
		StringBuilder str = new StringBuilder("");
		
		try {
			for (Map<String, Object> item : entityList) {
				// 查询数据是否结账
				String cashFlag = budgCashMapper.queryBudgCarry(item);
				//判断cashFlag的值  如果等于1 则添加错误信息
				if("1".equals(cashFlag)){
					str.append(item.get("budg_year")+"年度  "+item.get("month")+"月份   ");
				}
			}
			
			if(!"".equals(str.toString())){
				return "{\"error\":\""+str.toString()+"数据已经结账,无法删除!\",\"state\":\"false\"}";
			}
			
			budgCashMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败!\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加现金存量预算<BR> 
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
		//判断是否存在对象现金存量预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgCash> list = (List<BudgCash>)budgCashMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgCashMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgCashMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集现金存量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCash> list = (List<BudgCash>)budgCashMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCash> list = (List<BudgCash>)budgCashMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象现金存量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金存量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgCash
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金存量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgCash>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashMapper.queryExists(entityMap);
	}
	
	/**
	 * 1 后台查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总 之后  插入或更新入现金流量预算表   budg_cash_flow
	 * 2 对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量预算表  budg_cash
	 */
	@Override
	public String collectBudgCash(Map<String,Object> entityMap)throws DataAccessException{
		
		//定义变量   接收数据
		Double cash_begain = 0.0;//期初现金存量
		Double cash_in = 0.0;//现金流入
		Double cash_out = 0.0;//现金流出
		Double cash_add = 0.0;//现金净增加额
		Double cash_end = 0.0;//期末现金存量
		
		String state = budgCashMapper.queryCashBeginState(entityMap);
		if(!"1".equals(state)){
			return "{\"error\":\"期初数据未记账,存量预算不可计算!\"}";
		}
		
		try {
			//查询结账表中已结账数据中  最大月份  
			List<Map<String, Object>> carryList = budgCashFlowMapper.queryMaxMonthFromBudgCarry(entityMap);
			
			if(carryList.size() > 0){
				//根据需求条件   查询现金流量预算表中数据
				//对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出
				List<Map<String, Object>> cashFlowList = budgCashFlowMapper.queryCashFlow(entityMap);
				
				//存在  则将查出的结转值作为存量表中第一个月份期初值
				cash_end = Double.parseDouble(carryList.get(0).get("cash_carry").toString());
				
				//遍历 查询到的现金流量预算表中数据
				for (Map<String, Object> item : cashFlowList) {
					
					//定义一个map  用于接收需要保存的数据
					Map<String , Object> map = new HashMap<String, Object>();
					
					//集合已做升序排列  遍历第一条为已结账月份 下一月  则已结账月份的期末  = 该数据月份的期初  依次下推
					cash_begain = cash_end;
					//取出累计流入
					cash_in = Double.valueOf(String.valueOf(item.get("cash_in")));
					//取出累计流入
					cash_out = Double.valueOf(String.valueOf(item.get("cash_out")));
					//取出累计流入
					cash_add = cash_in - cash_out;
					//取出累计流入
					cash_end = cash_begain + cash_add;
					
					item.put("cash_begain", cash_begain);
					item.put("cash_add", cash_add);
					item.put("cash_end", cash_end);
					
				}
				//先删除   后添加
				budgCashMapper.deleteBatch(cashFlowList);
			    budgCashMapper.addBatch(cashFlowList);
			}else {
				
				//查询期初货币资金表中期初现金存量  并用cash_end接收
				cash_end = budgCashFlowMapper.queryCashBegin(entityMap);
				
				//根据需求条件   查询现金流量预算表中数据
				//对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出
				List<Map<String, Object>> cashFlowList = budgCashFlowMapper.queryCashFlow(entityMap);
				
				//遍历 查询到的现金流量预算表中数据
				for (Map<String, Object> item : cashFlowList) {
					
					//定义一个map  用于接收需要保存的数据
					Map<String , Object> map = new HashMap<String, Object>();
					
					//集合已做升序排列  遍历第一条为已结账月份 下一月  则已结账月份的期末  = 该数据月份的期初  依次下推
					cash_begain = cash_end;
					//取出累计流入
					cash_in = Double.valueOf(String.valueOf(item.get("cash_in")));
					//取出累计流入
					cash_out = Double.valueOf(String.valueOf(item.get("cash_out")));
					//取出累计流入
					cash_add = cash_in - cash_out;
					//取出累计流入
					cash_end = cash_begain + cash_add;
					
					item.put("cash_begain", cash_begain);
					item.put("cash_add", cash_add);
					item.put("cash_end", cash_end);
					
				}
				//先删除   后添加
				budgCashMapper.deleteBatch(cashFlowList);
				budgCashMapper.addBatch(cashFlowList);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			throw new SysException("{\"error\":\"操作失败！\"}");
	
		}
	}
}

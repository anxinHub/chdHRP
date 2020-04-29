/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.begin;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.project.begin.BudgPaymentItmMapper;
import com.chd.hrp.budg.entity.BudgPaymentItem;
import com.chd.hrp.budg.service.project.begin.BudgPaymentItmService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是
 * @Table:
 * BUDG_PAYMENT_ITEM
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgPaymentItmService")
public class BudgPaymentItmServiceImpl implements BudgPaymentItmService {

	private static Logger logger = Logger.getLogger(BudgPaymentItmServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgPaymentItmMapper")
	private final BudgPaymentItmMapper budgPaymentItmMapper = null;
    
	/**
	 * @Description 
	 * 添加控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象控制方式（CONTROL_WAY）取自系统字典表

		BudgPaymentItem budgPaymentItem = queryByCode(entityMap);

		if (budgPaymentItem != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgPaymentItmMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgPaymentItmMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgPaymentItmMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgPaymentItmMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgPaymentItmMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgPaymentItmMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
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
		//判断是否存在对象控制方式（CONTROL_WAY）取自系统字典表

		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgPaymentItem> list = (List<BudgPaymentItem>)budgPaymentItmMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgPaymentItmMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgPaymentItmMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgPaymentItem> list = (List<BudgPaymentItem>)budgPaymentItmMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgPaymentItem> list = (List<BudgPaymentItem>)budgPaymentItmMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentItmMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgPaymentItem
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentItmMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgPaymentItem>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentItmMapper.queryExists(entityMap);
	}
	
}

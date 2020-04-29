/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgdrug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgdrug.BudgDrugTypeIncomeShipMapper;
import com.chd.hrp.budg.entity.BudgDrugTypeIncomeShip;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeIncomeShipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
 * @Table:
 * BUDG_DRUG_TYPE_INCOME_SHIP2
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgDrugTypeIncomeShipService")
public class BudgDrugTypeIncomeShipServiceImpl implements BudgDrugTypeIncomeShipService {

	private static Logger logger = Logger.getLogger(BudgDrugTypeIncomeShipServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDrugTypeIncomeShipMapper")
	private final BudgDrugTypeIncomeShipMapper budgDrugTypeIncomeShipMapper = null;
    
	/**
	 * @Description 
	 * 添加末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
		BudgDrugTypeIncomeShip budgDrugTypeIncomeShip = queryByCode(entityMap);

		if (budgDrugTypeIncomeShip != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgDrugTypeIncomeShipMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDrugTypeIncomeShipMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgDrugTypeIncomeShipMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgDrugTypeIncomeShipMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgDrugTypeIncomeShipMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDrugTypeIncomeShipMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
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
		//判断是否存在对象末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgDrugTypeIncomeShip> list = (List<BudgDrugTypeIncomeShip>)budgDrugTypeIncomeShipMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgDrugTypeIncomeShipMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgDrugTypeIncomeShipMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDrugTypeIncomeShipMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDrugTypeIncomeShipMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgDrugTypeIncomeShipMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgDrugTypeIncomeShip2
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgDrugTypeIncomeShipMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取末级药品分类对应预算收入科目。对应关系中要求药品分类对收入科目，为一对一或一对多。<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgDrugTypeIncomeShip2>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgDrugTypeIncomeShipMapper.queryExists(entityMap);
	}
	
}

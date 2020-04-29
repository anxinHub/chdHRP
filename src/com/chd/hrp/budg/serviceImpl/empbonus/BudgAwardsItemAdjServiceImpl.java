/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.empbonus;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.empbonus.BudgAwardsItemAdjMapper;
import com.chd.hrp.budg.entity.BudgWageChange;
import com.chd.hrp.budg.service.empbonus.BudgAwardsItemAdjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 奖金调整
 * @Table:
 * BUDG_AWARDS_ITEM_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgAwardsItemAdjService")
public class BudgAwardsItemAdjServiceImpl implements BudgAwardsItemAdjService {

	private static Logger logger = Logger.getLogger(BudgAwardsItemAdjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgAwardsItemAdjMapper")
	private final BudgAwardsItemAdjMapper budgAwardsItemAdjMapper = null;
    
	/**
	 * @Description 
	 * 添加奖金变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象奖金变动
		BudgWageChange budgWageChange = queryByCode(entityMap);

		if (budgWageChange != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgAwardsItemAdjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败! \"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加奖金变动<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "";
		try {
			
			for (Map<String, Object> map : entityList) {
				int count = budgAwardsItemAdjMapper.queryDataExists(map);
				
				if(count > 0 ){
					str += "职工类别为"+map.get("emp_type_code")+",奖金项目为"+map.get("awards_item_code")+" ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\""+str+"的数据已存在,不可重复添加.\",\"state\":\"false\"}";
			}
			
			budgAwardsItemAdjMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败! 方法 addBatch\"}") ;

		}
		
	}
	
		/**
	 * @Description 
	 * 更新奖金变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgAwardsItemAdjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败  方法 update\"}") ;

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新奖金变动<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgAwardsItemAdjMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败!\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除奖金变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgAwardsItemAdjMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败!\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除奖金变动<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgAwardsItemAdjMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败! 方法 deleteBatch\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加奖金变动<BR> 
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
		//判断是否存在对象奖金变动
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWageChange> list = (List<BudgWageChange>)budgAwardsItemAdjMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgAwardsItemAdjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgAwardsItemAdjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败!\"}");

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集奖金变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgWageChange> list = (List<BudgWageChange>)budgAwardsItemAdjMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgWageChange> list = (List<BudgWageChange>)budgAwardsItemAdjMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象奖金变动<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgAwardsItemAdjMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取奖金变动<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWageChange
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgAwardsItemAdjMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取奖金变动<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWageChange>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgAwardsItemAdjMapper.queryExists(entityMap);
	}
	
}

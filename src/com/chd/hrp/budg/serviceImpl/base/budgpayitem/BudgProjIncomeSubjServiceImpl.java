/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgpayitem;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgpayitem.BudgProjIncomeSubjMapper;
import com.chd.hrp.budg.entity.BudgPaymentItemCostShip;
import com.chd.hrp.budg.service.base.budgpayitem.BudgProjIncomeSubjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 支出项目与收入预算科目对应关系
 * @Table:
 * BUDG_PROJ_INCOME_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjIncomeSubjService")
public class BudgProjIncomeSubjServiceImpl implements BudgProjIncomeSubjService {

	private static Logger logger = Logger.getLogger(BudgProjIncomeSubjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjIncomeSubjMapper")
	private final BudgProjIncomeSubjMapper budgProjIncomeSubjMapper = null;
    
	/**
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象
		BudgPaymentItemCostShip budgPaymentItemCostShip = queryByCode(entityMap);

		if (budgPaymentItemCostShip != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgProjIncomeSubjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			Map<String,Object> deleteMap = new HashMap<String,Object>();
			
			for(Map<String,Object> map : entityList ){
				
				deleteMap.putAll(map);
				
				deleteMap.remove("proj_id");
				
				break ;
			}
			
			budgProjIncomeSubjMapper.delete(deleteMap);
			
			budgProjIncomeSubjMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgProjIncomeSubjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjIncomeSubjMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgProjIncomeSubjMapper.delete(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjIncomeSubjMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加<BR> 
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
		//判断是否存在对象
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgPaymentItemCostShip> list = (List<BudgPaymentItemCostShip>)budgProjIncomeSubjMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgProjIncomeSubjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgProjIncomeSubjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjIncomeSubjMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjIncomeSubjMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjIncomeSubjMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgPaymentItemCostShip
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjIncomeSubjMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgPaymentItemCostShip>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjIncomeSubjMapper.queryExists(entityMap);
	}
	@Override
	public Map<String, Object> queryItemCodeExist(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgProjIncomeSubjMapper.queryItemCodeExist(entityMap);
	}
	
	/**
	 * 项目信息查询 （添加、修改页面用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgProjDict(Map<String, Object> entityMap)	throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjIncomeSubjMapper.queryBudgProjDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjIncomeSubjMapper.queryBudgProjDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public int queryIncomeSubjByCode(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjIncomeSubjMapper.queryIncomeSubjByCode(mapVo);
	}
	
	/**
	 * 继承
	 */
	@Override
	public String copyData(Map<String, Object> mapVo) throws DataAccessException {
		
		//年度 计算
		int preYear = Integer.parseInt(String.valueOf(mapVo.get("budg_year"))) -1 ;
		
		mapVo.put("preYear", String.valueOf(preYear));
		
		//查询 继承数据
		List<Map<String,Object>> list = budgProjIncomeSubjMapper.queryCopyData(mapVo);
		
		
		// 查询 当前年度 收入科目编码（帅选数据用）
		List<String> subjCodeList = budgProjIncomeSubjMapper.queryIncomeSubjCode(mapVo);
		
		// 存放继承数据
		List<Map<String,Object>> copyList = new ArrayList<Map<String,Object>>() ;
		
		String str = "";//记录当前年度  不存在的 收入预算科目
		
		if(list.size() == 0 ){
			
			return "{\"error\":\"继承失败,无上年项目与收入预算科目对应关系数据.\",\"state\":\"false\"}";
			
		}else{
			
			for(Map<String,Object> map : list){
				
				// 当前年度收入预算科目存在 则继承
				if(subjCodeList.contains(map.get("subj_code"))){
					
					//年度修改为当前年度
					map.put("budg_year", mapVo.get("budg_year"));
					
					copyList.add(map) ;
					
				}else{
					
					str += map.get("subj_code")+" " + map.get("subj_name") +";" ;
				}
				
			}
			
			int count  = 0 ;
			
			if(copyList.size()>0){
				
				count = budgProjIncomeSubjMapper.addBatch(copyList);
				
			}
			
			if(str != ""){
				
				return "{\"msg\":\"共"+list.size()+"条数据,成功继承"+count
						+"条数据!其中当前年度【"+str.substring(0,str.length()-1)+"】收入预算科目不存在,相关数据不继承.\",\"state\":\"true\"}";
			}else{
				
				return "{\"msg\":\"共"+list.size()+"条数据,成功继承"+count+"条数据!\",\"state\":\"true\"}";
			}
		}
		
		
	}
	
}

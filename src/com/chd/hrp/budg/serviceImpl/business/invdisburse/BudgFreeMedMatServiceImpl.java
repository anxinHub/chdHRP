/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.invdisburse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.invdisburse.BudgFreeMedMatMapper;
import com.chd.hrp.budg.entity.BudgFreeMedMat;
import com.chd.hrp.budg.service.business.invdisburse.BudgFreeMedMatService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_FREE_MED_MAT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgFreeMedMatService")
public class BudgFreeMedMatServiceImpl implements BudgFreeMedMatService {

	private static Logger logger = Logger.getLogger(BudgFreeMedMatServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgFreeMedMatMapper")
	private final BudgFreeMedMatMapper budgFreeMedMatMapper = null;
    
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象tabledesc
		BudgFreeMedMat budgFreeMedMat = queryByCode(entityMap);

		if (budgFreeMedMat != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgFreeMedMatMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFreeMedMatMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgFreeMedMatMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgFreeMedMatMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgFreeMedMatMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgFreeMedMatMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
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
		//判断是否存在对象tabledesc
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgFreeMedMat> list = (List<BudgFreeMedMat>)budgFreeMedMatMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgFreeMedMatMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgFreeMedMatMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgFreeMedMat> list = (List<BudgFreeMedMat>)budgFreeMedMatMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgFreeMedMat> list = (List<BudgFreeMedMat>)budgFreeMedMatMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgFreeMedMatMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgFreeMedMat
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgFreeMedMatMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgFreeMedMat>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgFreeMedMatMapper.queryExists(entityMap);
	}
	
	/**
	 * @Description 
	 * 生成  从科室非收费医用材料支出表中取上年（预算年度-1）数据生成本年预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String generatebudgFreeMedMat(Map<String, Object> mapVo) throws Exception {
		//创建两个集合  一个做添加使用  一个做更新数据使用
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>(); 
		List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>(); 
		try {
			mapVo.put("last_year", Integer.parseInt(mapVo.get("year").toString())-1);
			//从BUDG_FREE_MED_MAT_COST科室非收费医用材料支出表中取上年数据生成本年预算。若无法取到数据，提示”请先采集上年各科室非收费医用材料支出数据“
			//要求：科室为未停用的预算科室，
			List<Map<String,Object>> budgFreeMedMatList =budgFreeMedMatMapper.queryBudgFreeMedMatCostByYear(mapVo);
			if(budgFreeMedMatList.size()<=0){
				return"{\"warn\":\"请先采集上年各科室非收费医用材料支出数据\"}";
			}	
			
			//查询该年度物资分类与预算科目对应关系是否维护
			int count = budgFreeMedMatMapper.queryBudgMatTypeSubjByYear(mapVo);
			if(count == 0){
				return "{\"warn\":\"请先维护物资分类与预算科目对应关系\"}";
			}
			//通过sql获取所有需要生成数据 
			List<Map<String,Object>> dataList = budgFreeMedMatMapper.querydataList(mapVo);
			//如果dataList为空  返回信息提示
			if(dataList == null){
				return "{\"warn\":\"没有可生成数据.\",\"state\":\"false\"}";
			}
			//有数据 则遍历数据  并查询是否已存在
			for (Map<String, Object> map : dataList) {
				int num = budgFreeMedMatMapper.queryDataExists(map);
				if(num > 0) {
					updateList.add(map);
				}else{
					addList.add(map);
				}
			}
			
			if(updateList.size() > 0){
				budgFreeMedMatMapper.updateBatch(updateList);
			}
			
			if(addList.size() > 0){
				budgFreeMedMatMapper.addBatch(addList);
			}
			/*budgFreeMedMatMapper.generatebudgFreeMedMat(mapVo);
			int pre_year=Integer.parseInt(mapVo.get("year").toString())-1;
			mapVo.put("pre_year", pre_year);
			budgFreeMedMatMapper.updatebudgFreeMedMat(mapVo);*/
			return "{\"msg\":\"生成成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw e ;
		}
	}
	@Override
	public String budgFreeMedMatUpdateAdjRate(List<Map<String, Object>> listVo) throws Exception {
		try {
			
			budgFreeMedMatMapper.budgChargeMatUpdateAdjRate(listVo);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException(e) ; 

		}	
	}
	/**
	 * 查询数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> map) throws DataAccessException {
		
		return budgFreeMedMatMapper.queryDataExists(map);
		
	}

}

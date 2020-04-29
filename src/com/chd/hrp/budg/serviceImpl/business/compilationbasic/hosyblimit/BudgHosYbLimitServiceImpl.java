/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.compilationbasic.hosyblimit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.business.compilationbasic.hosyblimit.BudgHosYbLimitMapper;
import com.chd.hrp.budg.entity.BudgHosYbLimit;
import com.chd.hrp.budg.service.business.compilationbasic.hosyblimit.BudgHosYbLimitService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医院医保额度控制
 * @Table:
 * BUDG_HOS_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgHosYbLimitService")
public class BudgHosYbLimitServiceImpl implements BudgHosYbLimitService {

	private static Logger logger = Logger.getLogger(BudgHosYbLimitServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgHosYbLimitMapper")
	private final BudgHosYbLimitMapper budgHosYbLimitMapper = null;
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
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
				String  str = budgHosYbLimitMapper.queryDataExistList(addList) ;
				
				if(str == null){
					
					int count = budgHosYbLimitMapper.addBatch(addList);
					
				}else{
					
					return "{\"error\":\"第"+str+"行数据已存在\",\"state\":\"false\"}";
				}
			}
			
			if(updateList.size()>0){
				
				int state = budgHosYbLimitMapper.updateBatch(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 添加医院医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象医院医保额度控制
		int count = budgHosYbLimitMapper.queryDataExist(entityMap);

		if (count > 0) {

			return "{\"message\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		try {
			
			int state = budgHosYbLimitMapper.add(entityMap);
			
			return "{\"message\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""+entityMap.get("group_id").toString()
					+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
					+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 add\",\"state\":\"false\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加医院医保额度控制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		String str = "" ;
		try {
			
			for(Map<String,Object> item : entityList){
				
				int count  = budgHosYbLimitMapper.queryDataExist(item);
				
				if(count > 0){
					str += item.get("year") +"年 " + item.get("insurance_code") + "医保类型;" ;
				}
				
			}
			
			if( str != "" ){
				
				return "{\"error\":\"添加失败!以下数据:【"+str+"】已存在.\"}";
				
			}else{
				
				budgHosYbLimitMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新医院医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgHosYbLimitMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新医院医保额度控制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgHosYbLimitMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 删除医院医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgHosYbLimitMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除医院医保额度控制<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgHosYbLimitMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加医院医保额度控制<BR> 
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
		//判断是否存在对象医院医保额度控制
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgHosYbLimit> list = (List<BudgHosYbLimit>)budgHosYbLimitMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgHosYbLimitMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgHosYbLimitMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集医院医保额度控制<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgHosYbLimitMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgHosYbLimitMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象医院医保额度控制<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgHosYbLimitMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院医保额度控制<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgHosYbLimit
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgHosYbLimitMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医院医保额度控制<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgHosYbLimit>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgHosYbLimitMapper.queryExists(entityMap);
	}
	/**
	 * 生成时 查询生成数据
	 */
	@Override
	public List<Map<String,Object>> queryBudgInsuranceCodeData(Map<String, Object> entityMap) throws DataAccessException {
		
		return  budgHosYbLimitMapper.queryBudgInsuranceCodeData(entityMap); 
	}
	
	/**
	 * 查询  医保类型编码 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgHosYbLimitMapper.queryInsuranceCodeExist(mapVo);
	}
	
	/**
	 * 根据主键 查询 医院医保额度控制数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgHosYbLimitMapper.queryDataExist(mapVo);
	}
	
	
	/**
	 * 医保类型下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgYBType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(budgHosYbLimitMapper.queryBudgYBType(entityMap, rowBounds));
	}
	
}
	

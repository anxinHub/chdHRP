/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.begin;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.project.begin.BudgProjBeginDetailMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjBeginMapper;
import com.chd.hrp.budg.entity.BudgProjBegainDetail;
import com.chd.hrp.budg.service.project.begin.BudgProjBegainDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 期初项目预算明细
 * @Table:
 * BUDG_PROJ_BEGAIN_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjBegainDetailService")
public class BudgProjBegainDetailServiceImpl implements BudgProjBegainDetailService {

	private static Logger logger = Logger.getLogger(BudgProjBegainDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjBeginDetailMapper")
	private final BudgProjBeginDetailMapper budgProjBeginDetailMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjBeginMapper")
	private final BudgProjBeginMapper budgProjBeginMapper = null;
	/**
	 * @Description 
	 * 添加期初项目预算明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象期初项目预算明细
		BudgProjBegainDetail budgProjBegainDetail = queryByCode(entityMap);

		if (budgProjBegainDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgProjBeginDetailMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加期初项目预算明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//获取对象期初项目预算明细
			BudgProjBegainDetail budgProjBegainDetail =budgProjBeginDetailMapper.queryBudgProjBegainDetai(entityList);
			if(budgProjBegainDetail==null){
				budgProjBeginMapper.addBatch(entityList);
			budgProjBeginDetailMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
			return "{\"msg\":\"添加失败,数据已经存在了.\",\"state\":\"false\"}";
			       }
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新期初项目预算明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgProjBeginDetailMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新期初项目预算明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		List<Map<String,Object>> addList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> updateList=new ArrayList<Map<String,Object>>();
		try {
			List tmpl=null;
			for(Map<String,Object> map:entityList){
				tmpl=budgProjBeginDetailMapper.queryExists(map);
				if(tmpl!=null&&tmpl.size()>0){
					updateList.add(map);
				}else{
					addList.add(map);
				}
			}
			if(addList.size()>0)
				budgProjBeginDetailMapper.addBatch(entityList);
			if(updateList.size()>0)
				budgProjBeginDetailMapper.updateBatch(entityList);			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除期初项目预算明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgProjBeginDetailMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除期初项目预算明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjBeginDetailMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加期初项目预算明细<BR> 
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
		//判断是否存在对象期初项目预算明细
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgProjBegainDetail> list = (List<BudgProjBegainDetail>)budgProjBeginDetailMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgProjBeginDetailMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgProjBeginDetailMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集期初项目预算明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgProjBegainDetail> list = (List<BudgProjBegainDetail>)budgProjBeginDetailMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgProjBegainDetail> list = (List<BudgProjBegainDetail>)budgProjBeginDetailMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象期初项目预算明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjBeginDetailMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取期初项目预算明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgProjBegainDetail
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjBeginDetailMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取期初项目预算明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgProjBegainDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjBeginDetailMapper.queryExists(entityMap);
	}
	@Override
	public Map<String, Object> queryDetail(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgProjBeginDetailMapper.queryDetail(mapVo);
	}
	
}

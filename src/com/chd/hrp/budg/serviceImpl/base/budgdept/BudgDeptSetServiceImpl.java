
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.serviceImpl.base.budgdept;

import java.util.ArrayList;
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
import com.chd.hrp.budg.dao.base.budgdept.BudgDeptSetMapper;
import com.chd.hrp.budg.service.base.budgdept.BudgDeptSetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 预算科室定义
 * @Table:
 * HEALTH_INSURANCE_PAY_MODE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgDeptSetService")
public class BudgDeptSetServiceImpl implements BudgDeptSetService {

	private static Logger logger = Logger.getLogger(BudgDeptSetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDeptSetMapper")
	private final BudgDeptSetMapper budgDeptSetMapper = null;
	
	/**
	 * @Description 
	 * 添加预算科室定义<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		// TODO Auto-generated method stub
		return null;
		
	}
	/**
	 * @Description 
	 * 批量添加预算科室定义<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		// TODO Auto-generated method stub
		return null;
		
	}
	
	/**
	 * @Description 
	 * 更新预算科室定义<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			budgDeptSetMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新预算科室定义<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item : entityList ){
				
				int count = budgDeptSetMapper.queryDataExist(item) ;
				
				if(count == 0){
					addList.add(item);
				}else{
					updateList.add(item);
				}
			}
			
			if(updateList.size() > 0){
				
				budgDeptSetMapper.updateBatch(updateList);
			}
			
			if(addList.size() > 0){
				budgDeptSetMapper.addBatch(addList);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败!\"}") ;

		}	
	}
	
	/**
	 * @Description 
	 * 删除预算科室定义<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {	
		// TODO Auto-generated method stub
		return null;
	
	}
    
	/**
	 * @Description 
	 * 批量删除预算科室定义<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 查询结果集 预算科室定义<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");	
		if (sysPage.getTotal()==-1){		
			List<?> list = budgDeptSetMapper.query(entityMap);
			return ChdJson.toJson(list);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = budgDeptSetMapper.query(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgDeptSetMapper.queryByCode(entityMap);
	}
	
	
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int queryDeptIdExist(Map<String, Object> entityMap)	throws DataAccessException {
		return budgDeptSetMapper.queryDeptIdExist(entityMap);
	}
	@Override
	public int queryTypeCodeExist(Map<String, Object> entityMap) throws DataAccessException {
		return budgDeptSetMapper.queryTypeCodeExist(entityMap);
	}
	
}

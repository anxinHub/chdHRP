
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiFormulaStackMapper;
import com.chd.hrp.hpm.entity.AphiFormulaStack;
import com.chd.hrp.hpm.service.AphiFormulaStackService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9907 绩效计算公式指标栈
 * @Table:
 * PRM_FORMULA_STACK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("aphiFormulaStackService")
public class AphiFormulaStackServiceImpl implements AphiFormulaStackService {

	private static Logger logger = Logger.getLogger(AphiFormulaStackServiceImpl.class);
	//引入DAO操作
	@Resource(name = "aphiFormulaStackMapper")
	private final AphiFormulaStackMapper aphiFormulaStackMapper = null;
    
	/**
	 * @Description 
	 * 添加9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9907 绩效计算公式指标栈
		AphiFormulaStack prmFormulaStack = queryPrmFormulaStackByCode(entityMap);

		if (prmFormulaStack != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = aphiFormulaStackMapper.addPrmFormulaStack(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFormulaStack\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9907 绩效计算公式指标栈<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFormulaStack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiFormulaStackMapper.addBatchPrmFormulaStack(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFormulaStack\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = aphiFormulaStackMapper.updatePrmFormulaStack(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmFormulaStack\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9907 绩效计算公式指标栈<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFormulaStack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  aphiFormulaStackMapper.updateBatchPrmFormulaStack(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFormulaStack\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFormulaStack(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = aphiFormulaStackMapper.deletePrmFormulaStack(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFormulaStack\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9907 绩效计算公式指标栈<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFormulaStack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiFormulaStackMapper.deleteBatchPrmFormulaStack(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFormulaStack\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFormulaStack(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmFormulaStack(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmFormulaStack(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AphiFormulaStack queryPrmFormulaStackByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiFormulaStackMapper.queryPrmFormulaStackByCode(entityMap);
	}
	@Override
	public String queryPrmFormulaStackByScore(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmFormulaStackByScore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmFormulaStackByScore(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryPrmHosFormulaStackByScore(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmHosFormulaStackByScore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmHosFormulaStackByScore(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String queryPrmEmpFormulaStackByScore(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmEmpFormulaStackByScore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiFormulaStack> list = aphiFormulaStackMapper.queryPrmEmpFormulaStackByScore(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
}

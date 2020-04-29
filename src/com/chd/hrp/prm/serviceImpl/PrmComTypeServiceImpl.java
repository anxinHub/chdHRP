
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmComTypeMapper;
import com.chd.hrp.prm.entity.PrmComType;
import com.chd.hrp.prm.service.PrmComTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9913 绩效部件类型表
 * @Table:
 * PRM_COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmComTypeService")
public class PrmComTypeServiceImpl implements PrmComTypeService {

	private static Logger logger = Logger.getLogger(PrmComTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmComTypeMapper")
	private final PrmComTypeMapper prmComTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmComType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9913 绩效部件类型表
		PrmComType prmComType = queryPrmComTypeByCode(entityMap);

		if (prmComType != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmComTypeMapper.addPrmComType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmComType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9913 绩效部件类型表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmComType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmComTypeMapper.addBatchPrmComType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmComType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmComType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmComTypeMapper.updatePrmComType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmComType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9913 绩效部件类型表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmComType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmComTypeMapper.updateBatchPrmComType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmComType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmComType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmComTypeMapper.deletePrmComType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmComType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9913 绩效部件类型表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmComType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmComTypeMapper.deleteBatchPrmComType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmComType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmComType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmComType> list = prmComTypeMapper.queryPrmComType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmComType> list = prmComTypeMapper.queryPrmComType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmComType queryPrmComTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmComTypeMapper.queryPrmComTypeByCode(entityMap);
	}
	
}

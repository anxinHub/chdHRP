
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.mat.dao.info.basic.MatOutUseMapper;
import com.chd.hrp.mat.entity.HosPackage;
import com.chd.hrp.mat.entity.MatOutUse;
import com.chd.hrp.mat.service.info.basic.MatOutUseService;
import com.chd.hrp.mat.service.info.basic.MatPayTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * MAT_OUT_USE
 * @Table:
 * MAT_OUT_USE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matOutUseService")
public class MatOutUseServiceImpl implements MatOutUseService {

	private static Logger logger = Logger.getLogger(MatOutUseServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matOutUseMapper")
	private final MatOutUseMapper matOutUseMapper = null;
    
	/**
	 * 添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
	
		

		
		if (matOutUseMapper.queryExists(entityMap).size() >0) {
			
			return "{\"error\":\"数据重复,请重新添加.\"}";
			
		}		
		try {			
			
			int state = matOutUseMapper.add(entityMap);			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatOutUse\"}";
		}		
	}
	
	/**
	 * 批量添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			matOutUseMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatOutUse\"}";

		}	
	}
	
	/**
	 * 更新 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String update(Map<String,Object> entityMap)throws DataAccessException{
       
//		if (matOutUseMapper.queryExists(entityMap).size() >0) {
//			
//			return "{\"error\":\"数据重复,请重新添加.\"}";
//		}
		
		try {
			
			int state = matOutUseMapper.update(entityMap);			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatOutUse\"}";

		}		
	}
	
	/** 
	 * 批量更新MAT_OUT_USE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {
			
			matOutUseMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatOutUse\"}";

		}		
	}
	
	/**
	 * 删除MAT_OUT_USE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
	    try {	
			int state = matOutUseMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatOutUse\"}";
	
		}	
	}
    
	/**
	 * @Description 
	 * 批量删除MAT_OUT_USE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matOutUseMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatOutUse\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集MAT_OUT_USE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<?> list = matOutUseMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<?> list = matOutUseMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象MAT_OUT_USE<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return matOutUseMapper.queryByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 获取MAT_OUT_USE<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPayType
	 * @throws DataAccessException
	*/
	
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matOutUseMapper.queryByUniqueness(entityMap);
	}
	
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatStoreDetailMapper;
import com.chd.hrp.mat.entity.MatStoreDetail;
import com.chd.hrp.mat.service.info.basic.MatStoreDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04109 仓库对应关系明细表
 * @Table:
 * MAT_STORE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matStoreDetailService")
public class MatStoreDetailServiceImpl implements MatStoreDetailService {

	private static Logger logger = Logger.getLogger(MatStoreDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matStoreDetailMapper")
	private final MatStoreDetailMapper matStoreDetailMapper = null;
    
	/**
	 * @Description 
	 * 添加04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatStoreDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04109 仓库对应关系明细表
		MatStoreDetail matStoreDetail = queryMatStoreDetailByCode(entityMap);

		if (matStoreDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matStoreDetailMapper.addMatStoreDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStoreDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04109 仓库对应关系明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatStoreDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		for(Map<String,Object> item : entityList){
			MatStoreDetail matStoreDetail = queryMatStoreDetailByCode(item);

			if (matStoreDetail != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";
			}
		}
		
		try {
			
			matStoreDetailMapper.addBatchMatStoreDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatStoreDetail\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatStoreDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matStoreDetailMapper.updateMatStoreDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatStoreDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04109 仓库对应关系明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatStoreDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matStoreDetailMapper.updateBatchMatStoreDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatStoreDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatStoreDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matStoreDetailMapper.deleteMatStoreDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatStoreDetail\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04109 仓库对应关系明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatStoreDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matStoreDetailMapper.deleteBatchMatStoreDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatStoreDetail\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatStoreDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04109 仓库对应关系明细表
		MatStoreDetail matStoreDetail = queryMatStoreDetailByCode(entityMap);

		if (matStoreDetail != null) {

			int state = matStoreDetailMapper.updateMatStoreDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matStoreDetailMapper.addMatStoreDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatStoreDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatStoreDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatStoreDetail> list = matStoreDetailMapper.queryMatStoreDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatStoreDetail> list = matStoreDetailMapper.queryMatStoreDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04109 仓库对应关系明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatStoreDetail queryMatStoreDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matStoreDetailMapper.queryMatStoreDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04109 仓库对应关系明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStoreDetail
	 * @throws DataAccessException
	*/
	@Override
	public MatStoreDetail queryMatStoreDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matStoreDetailMapper.queryMatStoreDetailByUniqueness(entityMap);
	}
	
}

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
import com.chd.hrp.mat.dao.info.basic.MatVenCertTypeMapper;
import com.chd.hrp.mat.entity.MatVenCertType;
import com.chd.hrp.mat.service.info.basic.MatVenCertTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04604 供应商证件类型
 * @Table:
 * MAT_VEN_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matVenCertTypeService")
public class MatVenCertTypeServiceImpl implements MatVenCertTypeService {

	private static Logger logger = Logger.getLogger(MatVenCertTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matVenCertTypeMapper")
	private final MatVenCertTypeMapper matVenCertTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatVenCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04604 供应商证件类型
		List<MatVenCertType> list = matVenCertTypeMapper.queryMatVenCertTypeByID(entityMap);

		if (list.size()>0) {
			for(MatVenCertType item: list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"已存在\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"已存在\"}";
				}
			}
		}
		try {
			
			int state = matVenCertTypeMapper.addMatVenCertType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatVenCertType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04604 供应商证件类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatVenCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matVenCertTypeMapper.addBatchMatVenCertType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatVenCertType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatVenCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		List<MatVenCertType> list = matVenCertTypeMapper.queryMatVenCertTypeByID(entityMap);

		if (list.size()>0) {
			for(MatVenCertType item: list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"已存在\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"已存在\"}";
				}
			}
		}
		try {
			
		  int state = matVenCertTypeMapper.updateMatVenCertType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatVenCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04604 供应商证件类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatVenCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matVenCertTypeMapper.updateBatchMatVenCertType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatVenCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatVenCertType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matVenCertTypeMapper.deleteMatVenCertType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatVenCertType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04604 供应商证件类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatVenCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matVenCertTypeMapper.deleteBatchMatVenCertType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatVenCertType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatVenCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04604 供应商证件类型
		MatVenCertType matVenCertType = queryMatVenCertTypeByCode(entityMap);

		if (matVenCertType != null) {

			int state = matVenCertTypeMapper.updateMatVenCertType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matVenCertTypeMapper.addMatVenCertType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatVenCertType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04604 供应商证件类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatVenCertType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatVenCertType> list = matVenCertTypeMapper.queryMatVenCertType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatVenCertType> list = matVenCertTypeMapper.queryMatVenCertType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04604 供应商证件类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatVenCertType queryMatVenCertTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matVenCertTypeMapper.queryMatVenCertTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04604 供应商证件类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatVenCertType
	 * @throws DataAccessException
	*/
	@Override
	public MatVenCertType queryMatVenCertTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matVenCertTypeMapper.queryMatVenCertTypeByUniqueness(entityMap);
	}
	/**
	 * 查询 与 输入证件类型编码、证件类型名称 相同的记录(判断证件类型编码、证件类型名称是否已存在)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatVenCertType> queryMatVenCertTypeByID(Map<String, Object> mapVo) throws DataAccessException{
		return matVenCertTypeMapper.queryMatVenCertTypeByID(mapVo);
	}
	
}

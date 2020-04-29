
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
import com.chd.hrp.mat.dao.info.basic.MatInvCertTypeMapper;
import com.chd.hrp.mat.entity.MatInvCertType;
import com.chd.hrp.mat.service.info.basic.MatInvCertTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04601 材料证件类型字典
 * @Table:
 * MAT_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matInvCertTypeService")
public class MatInvCertTypeServiceImpl implements MatInvCertTypeService {

	private static Logger logger = Logger.getLogger(MatInvCertTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInvCertTypeMapper")
	private final MatInvCertTypeMapper matInvCertTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatInvCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04601 材料证件类型字典
		List<MatInvCertType> list = matInvCertTypeMapper.queryMatInvCertTypeById(entityMap);

		if (list.size() > 0) {
			for(MatInvCertType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"重复,请重新添加.\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"重复,请重新添加.\"}";
				}
			}
		}
		
		try {
			
			int state = matInvCertTypeMapper.addMatInvCertType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInvCertType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04601 材料证件类型字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatInvCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matInvCertTypeMapper.addBatchMatInvCertType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInvCertType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatInvCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		List<MatInvCertType> list = matInvCertTypeMapper.queryMatInvCertTypeById(entityMap);

		if (list.size() > 0) {
			for(MatInvCertType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"重复,请重新输入.\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"重复,请重新输入.\"}";
				}
			}
		}
		
		try {
			
		  int state = matInvCertTypeMapper.updateMatInvCertType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatInvCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04601 材料证件类型字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatInvCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matInvCertTypeMapper.updateBatchMatInvCertType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatInvCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatInvCertType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matInvCertTypeMapper.deleteMatInvCertType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInvCertType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04601 材料证件类型字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatInvCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matInvCertTypeMapper.deleteBatchMatInvCertType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatInvCertType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInvCertType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatInvCertType> list = matInvCertTypeMapper.queryMatInvCertType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatInvCertType> list = matInvCertTypeMapper.queryMatInvCertType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatInvCertType queryMatInvCertTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matInvCertTypeMapper.queryMatInvCertTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCertType
	 * @throws DataAccessException
	*/
	@Override
	public MatInvCertType queryMatInvCertTypeByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return matInvCertTypeMapper.queryMatInvCertTypeByUniqueness(entityMap);
	}
	public List<MatInvCertType> queryMatInvCertTypeById(Map<String, Object> mapVo) throws DataAccessException{
		return matInvCertTypeMapper.queryMatInvCertTypeById(mapVo);
	}
	/**
	 * 查询 删除的数据是否也被引用
	 * @param entityMap
	 * @return
	 */
	public int queryDelDate(Map<String, Object> entityMap) throws DataAccessException{
		return matInvCertTypeMapper.queryDelDate(entityMap);
	}
	
}

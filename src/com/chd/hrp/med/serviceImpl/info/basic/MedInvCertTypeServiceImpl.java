
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedInvCertTypeMapper;
import com.chd.hrp.med.entity.MedInvCertType;
import com.chd.hrp.med.service.info.basic.MedInvCertTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08601 材料证件类型字典
 * @Table:
 * MED_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medInvCertTypeService")
public class MedInvCertTypeServiceImpl implements MedInvCertTypeService {

	private static Logger logger = Logger.getLogger(MedInvCertTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medInvCertTypeMapper")
	private final MedInvCertTypeMapper medInvCertTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedInvCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08601 材料证件类型字典
		List<MedInvCertType> list = medInvCertTypeMapper.queryMedInvCertTypeById(entityMap);

		if (list.size() > 0) {
			for(MedInvCertType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"重复,请重新添加.\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"重复,请重新添加.\"}";
				}
			}
		}
		
		try {
			
			int state = medInvCertTypeMapper.addMedInvCertType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedInvCertType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08601 材料证件类型字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedInvCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medInvCertTypeMapper.addBatchMedInvCertType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedInvCertType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedInvCertType(Map<String,Object> entityMap)throws DataAccessException{
		
		List<MedInvCertType> list = medInvCertTypeMapper.queryMedInvCertTypeById(entityMap);

		if (list.size() > 0) {
			for(MedInvCertType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					return "{\"error\":\"证件类型编码:"+entityMap.get("type_code")+"重复,请重新输入.\"}";
				}
				if(item.getType_name().equals(entityMap.get("type_name"))){
					return "{\"error\":\"证件类型名称:"+entityMap.get("type_name")+"重复,请重新输入.\"}";
				}
			}
		}
		
		try {
			
		  int state = medInvCertTypeMapper.updateMedInvCertType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedInvCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08601 材料证件类型字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedInvCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medInvCertTypeMapper.updateBatchMedInvCertType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedInvCertType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedInvCertType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medInvCertTypeMapper.deleteMedInvCertType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedInvCertType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08601 材料证件类型字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedInvCertType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medInvCertTypeMapper.deleteBatchMedInvCertType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedInvCertType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集08601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedInvCertType(Map<String,Object> entityMap) throws DataAccessException{
		
		List<MedInvCertType> list = medInvCertTypeMapper.queryMedInvCertType(entityMap);
		
		return ChdJson.toJson(list);
			
	}
	
	/**
	 * @Description 
	 * 获取对象08601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedInvCertType queryMedInvCertTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medInvCertTypeMapper.queryMedInvCertTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvCertType
	 * @throws DataAccessException
	*/
	@Override
	public MedInvCertType queryMedInvCertTypeByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return medInvCertTypeMapper.queryMedInvCertTypeByUniqueness(entityMap);
	}
	public List<MedInvCertType> queryMedInvCertTypeById(Map<String, Object> mapVo) throws DataAccessException{
		return medInvCertTypeMapper.queryMedInvCertTypeById(mapVo);
	}
	/**
	 * 查询 删除的数据是否也被引用
	 * @param entityMap
	 * @return
	 */
	public int queryDelDate(Map<String, Object> entityMap) throws DataAccessException{
		return medInvCertTypeMapper.queryDelDate(entityMap);
	}
	
}

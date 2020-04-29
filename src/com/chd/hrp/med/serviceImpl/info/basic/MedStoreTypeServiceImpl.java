/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.info.basic.MedStoreTypeMapper;
import com.chd.hrp.med.entity.MedStoreType;
import com.chd.hrp.med.service.info.basic.MedStoreTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08111 仓库类别信息
 * @Table:
 * MED_STORE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medStoreTypeService")
public class MedStoreTypeServiceImpl implements MedStoreTypeService {

	private static Logger logger = Logger.getLogger(MedStoreTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medStoreTypeMapper")
	private final MedStoreTypeMapper medStoreTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedStoreType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08111 仓库类别信息
		MedStoreType medStoreType = queryMedStoreTypeByCode(entityMap);

		if (medStoreType != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medStoreTypeMapper.addMedStoreType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStoreType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08111 仓库类别信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedStoreType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medStoreTypeMapper.addBatchMedStoreType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStoreType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedStoreType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medStoreTypeMapper.updateMedStoreType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedStoreType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08111 仓库类别信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedStoreType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medStoreTypeMapper.updateBatchMedStoreType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedStoreType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedStoreType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medStoreTypeMapper.deleteMedStoreType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStoreType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08111 仓库类别信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedStoreType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medStoreTypeMapper.deleteBatchMedStoreType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStoreType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedStoreType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08111 仓库类别信息
		MedStoreType medStoreType = queryMedStoreTypeByCode(entityMap);

		if (medStoreType != null) {

			int state = medStoreTypeMapper.updateMedStoreType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medStoreTypeMapper.addMedStoreType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedStoreType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStoreType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedStoreType> list = medStoreTypeMapper.queryMedStoreType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedStoreType> list = medStoreTypeMapper.queryMedStoreType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象08111 仓库类别信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreType queryMedStoreTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreTypeMapper.queryMedStoreTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08111 仓库类别信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreType
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreType queryMedStoreTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreTypeMapper.queryMedStoreTypeByUniqueness(entityMap);
	}
	/**
	 * 查询出药品类别字典表 MED_TYPE中IS_STOP=0的所有药品类别记录
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreType(Map<String, Object> entityMap) throws DataAccessException{
		
		
		List<Map<String,Object>> list = medStoreTypeMapper.queryStoreType(entityMap);
		
		return ChdJson.toJson(list);
		
		/*SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medStoreTypeMapper.queryStoreType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medStoreTypeMapper.queryStoreType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}*/
	}
	/**
	 * 根据仓库ID 查询是否存在 对应对应关系数据 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryExist(Map<String, Object> mapVo) throws DataAccessException{
		return medStoreTypeMapper.queryExist(mapVo);
	}
	
	
	/**
	 * 查询出药品类别字典表 MED_TYPE中IS_STOP=0的所有药品类别记录
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedTypeByStore(Map<String, Object> entityMap) throws DataAccessException{

			
			List<Map<String,Object>> list = medStoreTypeMapper.queryMedTypeByStore(entityMap);
			
			return ChdJson.toJson(list);
			
		
	}
	
	
	/**
	 * @Description 
	 * 批量添加08110 仓库材料信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedTypeByStore(List<Map<String,Object>> entityList)throws DataAccessException{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		try 
		{
			medStoreTypeMapper.deleteBatchMedTypeByStore(entityList);
			//public int deleteBatchMedStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
			
			
			medStoreTypeMapper.addBatchMedTypeByStore(entityList);
			//public int addBatchMedStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
			
		
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStoreInv\"}";
		}
		
	}
	
	
}

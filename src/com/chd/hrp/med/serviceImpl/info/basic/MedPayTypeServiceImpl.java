
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.med.dao.info.basic.MedPayTypeMapper;
import com.chd.hrp.med.service.info.basic.MedPayTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * MED_PAY_TYPE
 * @Table:
 * MED_PAY_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("medPayTypeService")
public class MedPayTypeServiceImpl implements MedPayTypeService {

	private static Logger logger = Logger.getLogger(MedPayTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medPayTypeMapper")
	private final MedPayTypeMapper medPayTypeMapper = null;
    
	/**
	 * 添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象MED_PAY_TYPE
		AccPayType accPayType = medPayTypeMapper.queryByCode(entityMap);

		if (accPayType != null) {
			
			return "{\"error\":\"数据重复,请重新添加.\"}";
			
		}		
		try {			
			
			int state = medPayTypeMapper.add(entityMap);			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedPayType\"}";
		}		
	}
	
	/**
	 * 批量添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			medPayTypeMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedPayType\"}";

		}	
	}
	
	/**
	 * 更新 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			int state = medPayTypeMapper.update(entityMap);			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedPayType\"}";

		}		
	}
	
	/** 
	 * 批量更新MED_PAY_TYPE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {
			
			medPayTypeMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedPayType\"}";

		}		
	}
	
	/**
	 * 删除MED_PAY_TYPE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
	    try {	
			int state = medPayTypeMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedPayType\"}";
	
		}	
	}
    
	/**
	 * @Description 
	 * 批量删除MED_PAY_TYPE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPayTypeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedPayType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集MED_PAY_TYPE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		List<?> list = medPayTypeMapper.query(entityMap);
		
		return ChdJson.toJson(list);
		
		/*SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<?> list = medPayTypeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<?> list = medPayTypeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}*/
		
	}
	
	/**
	 * @Description 
	 * 获取对象MED_PAY_TYPE<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return medPayTypeMapper.queryByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 获取MED_PAY_TYPE<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPayType
	 * @throws DataAccessException
	*/
	
	@Override
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medPayTypeMapper.queryByUniqueness(entityMap);
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String extendMedPayType(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			
			List<?> aptList = medPayTypeMapper.query(entityMap);
			
			if( aptList.size()>0){
				
				return "{\"msg\":\"数据已存在，无法继承.\",\"state\":\"false\"}";
				
			}
			
			List<AccPayType> accPayList = (List<AccPayType>) medPayTypeMapper.queryMedPayTypeByExtend(entityMap);
			
			if(accPayList.size()>0){
				
				for (AccPayType accPayType : accPayList) {
					
					Map< String, Object> map = new HashMap<String, Object>();
					
					map.put("pay_code", accPayType.getPay_code());
					
					map.put("group_id", SessionManager.getGroupId());
					
					map.put("hos_id", SessionManager.getHosId());
					
					map.put("copy_code", SessionManager.getCopyCode());
					
					
					map.put("pay_name", accPayType.getPay_name());
					
					map.put("pay_type", accPayType.getPay_type());
					
					map.put("spell_code", accPayType.getSpell_code());
					
					map.put("wbx_code", accPayType.getWbx_code());
					
					map.put("is_stop", accPayType.getIs_stop());
					
					list.add(map);
					
				}
				medPayTypeMapper.addBatch(list);
				
				return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";
				
			}
			
			return "{\"msg\":\"没有可继承数据.\",\"state\":\"false\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"继承数据失败 数据库异常 请联系管理员! 错误编码  ExtendBatchAccPayType{className}\"}";
		}
	}
	
}

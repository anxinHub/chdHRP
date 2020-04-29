
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
import com.chd.hrp.med.dao.info.basic.MedOutUseMapper;
import com.chd.hrp.med.entity.HosPackage;
import com.chd.hrp.med.entity.MedOutUse;
import com.chd.hrp.med.service.info.basic.MedOutUseService;
import com.chd.hrp.med.service.info.basic.MedPayTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * MED_OUT_USE
 * @Table:
 * MED_OUT_USE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("medOutUseService")
public class MedOutUseServiceImpl implements MedOutUseService {

	private static Logger logger = Logger.getLogger(MedOutUseServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medOutUseMapper")
	private final MedOutUseMapper medOutUseMapper = null;
    
	/**
	 * 添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
	
		

		
		if (medOutUseMapper.queryExists(entityMap).size() >0) {
			
			return "{\"error\":\"数据重复,请重新添加.\"}";
			
		}		
		try {			
			
			int state = medOutUseMapper.add(entityMap);			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedOutUse\"}";
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
			medOutUseMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedOutUse\"}";

		}	
	}
	
	/**
	 * 更新 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String update(Map<String,Object> entityMap)throws DataAccessException{
       
//		if (medOutUseMapper.queryExists(entityMap).size() >0) {
//			
//			return "{\"error\":\"数据重复,请重新添加.\"}";
//		}
		
		try {
			
			int state = medOutUseMapper.update(entityMap);			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedOutUse\"}";

		}		
	}
	
	/** 
	 * 批量更新MED_OUT_USE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {
			
			medOutUseMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedOutUse\"}";

		}		
	}
	
	/**
	 * 删除MED_OUT_USE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
	    try {	
			int state = medOutUseMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedOutUse\"}";
	
		}	
	}
    
	/**
	 * @Description 
	 * 批量删除MED_OUT_USE<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medOutUseMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedOutUse\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集MED_OUT_USE<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		List<?> list = medOutUseMapper.query(entityMap);
		
		return ChdJson.toJson(list);
		
		/*SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<?> list = medOutUseMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<?> list = medOutUseMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}*/
		
	}
	
	/**
	 * @Description 
	 * 获取对象MED_OUT_USE<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return medOutUseMapper.queryByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 获取MED_OUT_USE<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPayType
	 * @throws DataAccessException
	*/
	
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medOutUseMapper.queryByUniqueness(entityMap);
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

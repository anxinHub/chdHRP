/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjTypeMapper;
import com.chd.hrp.acc.entity.AccBank;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.service.commonbuilder.AccSubjTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科目类别<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accSubjTypeService")
public class AccSubjTypeServiceImpl implements AccSubjTypeService {

	private static Logger logger = Logger.getLogger(AccSubjTypeServiceImpl.class);
	
	@Resource(name = "accSubjTypeMapper")
	private final AccSubjTypeMapper accSubjTypeMapper = null;
    
	/**
	 * @Description 
	 * 科目类别<BR> 添加AccSubjType
	 * @param AccSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccSubjType(Map<String,Object> entityMap)throws DataAccessException{
		
		AccSubjType accSubjType = queryAccSubjTypeByCode(entityMap);
		int count = accSubjTypeMapper.queryAccSubjTypeNameExist(entityMap);
		
		if (accSubjType != null) {
			if(count > 0){

				return "{\"error\":\"【编码：" + entityMap.get("subj_type_code").toString()+"科目类别名称：" + entityMap.get("subj_type_name").toString() + "】已存在.\"}";

			}else{
				return "{\"error\":\"【编码：" + entityMap.get("subj_type_code").toString() + "】已存在.\"}";
			}

		}else{
			if(count > 0){

				return "{\"error\":\"【科目类别名称：" + entityMap.get("subj_type_name").toString() + "】已存在.\"}";

			}
		}
		
		try {
			
			accSubjTypeMapper.addAccSubjType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccSubjType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量添加AccSubjType
	 * @param  AccSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccSubjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accSubjTypeMapper.addBatchAccSubjType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccSubjType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccSubjType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccSubjType> list = accSubjTypeMapper.queryAccSubjType(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccSubjType> list = accSubjTypeMapper.queryAccSubjType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccSubjType queryAccSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accSubjTypeMapper.queryAccSubjTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量删除AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccSubjType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accSubjTypeMapper.deleteBatchAccSubjType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccSubjType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科目类别<BR> 删除AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccSubjType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accSubjTypeMapper.deleteAccSubjType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccSubjType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科目类别<BR> 更新AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccSubjType(Map<String,Object> entityMap)throws DataAccessException{
		
		int count = accSubjTypeMapper.queryAccSubjTypeNameExist(entityMap);
	
		if(count > 0){

			return "{\"error\":\"【科目类别名称：" + entityMap.get("subj_type_name").toString() + "】已存在.\"}";

		}
		try {

			int re=accSubjTypeMapper.updateAccSubjType(entityMap);
			if(re>0){
				entityMap.put("acc_year", SessionManager.getAcctYear());
				accSubjTypeMapper.updateAccSubjByType(entityMap);
			}

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\""+e.getMessage()+"\"}",e);

		}
	}
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量更新AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccSubjType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accSubjTypeMapper.updateBatchAccSubjType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubjType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科目类别<BR> 导入AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccSubjType(Map<String,Object> entityMap) throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
	/**
	 * 查询  科目类别是否被引用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccSubjTypeReferenced(Map<String, Object> mapVo) throws DataAccessException{
		return accSubjTypeMapper.queryAccSubjTypeReferenced(mapVo);
	}
}

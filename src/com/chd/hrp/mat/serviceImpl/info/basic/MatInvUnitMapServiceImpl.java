/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.*;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatInvUnitMapMapper;
import com.chd.hrp.mat.entity.MatInvUnitMap;
import com.chd.hrp.mat.service.info.basic.MatInvUnitMapService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04116 材料包装单位关系表
 * @Table:
 * MAT_INV_UNIT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matInvUnitMapService")
public class MatInvUnitMapServiceImpl implements MatInvUnitMapService {

	private static Logger logger = Logger.getLogger(MatInvUnitMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInvUnitMapMapper")
	private final MatInvUnitMapMapper matInvUnitMapMapper = null;
    
	/**
	 * @Description 
	 * 添加04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatInvUnitMap(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04116 材料包装单位关系表
		MatInvUnitMap matInvUnitMap = queryMatInvUnitMapByCode(entityMap);

		if (matInvUnitMap != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matInvUnitMapMapper.addMatInvUnitMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加过程出错，添加失败  请联系管理员! 方法 addMatInvUnitMap\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04116 材料包装单位关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatInvUnitMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		for(Map<String,Object> item : entityList){
			Map<String,Object> existMap = matInvUnitMapMapper.queryMatInvUnitMapByID(item);

			if (existMap != null) {

				return "{\"error\":\"材料编码:"+existMap.get("inv_code").toString()+"包装单位:"+existMap.get("pack_name").toString()+"已存在,请重新添加.\"}";

			}
		}
		
		try {
			
			matInvUnitMapMapper.addBatchMatInvUnitMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加过程出错,添加失败 请联系管理员! 方法 addBatchMatInvUnitMap\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatInvUnitMap(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = matInvUnitMapMapper.updateMatInvUnitMap(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败 请联系管理员! 方法 updateMatInvUnitMap\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04116 材料包装单位关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatInvUnitMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> updateNewList = new ArrayList<Map<String,Object>>();
		
		String regexStr = "[\u4E00-\u9FA5]";
		
		Pattern p = Pattern.compile(regexStr);
		
		for(Map<String,Object> item : entityList ){
			if(p.matcher(item.get("pack_codeNew").toString()).find()){
				updateList.add(item);
			}else{
				updateNewList.add(item);
			}
		}
		
		try {
			if(updateList.size()>0){
				matInvUnitMapMapper.updateBatchMatInvUnitMap(updateList);
			}
			if(updateNewList.size()>0){
				matInvUnitMapMapper.updateBatchMatInvUnitMapNew(updateNewList);
			}
		  
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败    请联系管理员! 方法 updateBatchMatInvUnitMap\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatInvUnitMap(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matInvUnitMapMapper.deleteMatInvUnitMap(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInvUnitMap\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04116 材料包装单位关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatInvUnitMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matInvUnitMapMapper.deleteBatchMatInvUnitMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatInvUnitMap\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatInvUnitMap(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04116 材料包装单位关系表
		MatInvUnitMap matInvUnitMap = queryMatInvUnitMapByCode(entityMap);

		if (matInvUnitMap != null) {

			int state = matInvUnitMapMapper.updateMatInvUnitMap(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matInvUnitMapMapper.addMatInvUnitMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatInvUnitMap\"}";

		}
		
	}
	
	public String addOrUpdateMatInvUnitMap(List<Map<String,Object>> listVo)throws DataAccessException{
		
		List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object>  item : listVo){
			MatInvUnitMap matInvUnitMap = queryMatInvUnitMapByCode(item);
			if (matInvUnitMap != null) {
				updateList.add(item);
			}else{
				addList.add(item);
			}
		}
		
		try {
			
			matInvUnitMapMapper.addBatchMatInvUnitMap(addList);
			matInvUnitMapMapper.updateBatchMatInvUnitMap(updateList);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatInvUnitMap\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInvUnitMap(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matInvUnitMapMapper.queryMatInvUnitMapNew(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matInvUnitMapMapper.queryMatInvUnitMapNew(entityMap,rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04116 材料包装单位关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatInvUnitMap queryMatInvUnitMapByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matInvUnitMapMapper.queryMatInvUnitMapByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04116 材料包装单位关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvUnitMap
	 * @throws DataAccessException
	*/
	@Override
	public MatInvUnitMap queryMatInvUnitMapByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matInvUnitMapMapper.queryMatInvUnitMapByUniqueness(entityMap);
	}
	/**
	 * 弹出选择材料页面，根据查询条件查询出物资材料结果集（MAT_INV中IS_STOP=0）
	 * @param entityMap
	 * @return
	 */
	public String queryMatInv(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matInvUnitMapMapper.queryMatInv(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matInvUnitMapMapper.queryMatInv(entityMap,rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
}

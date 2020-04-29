/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedInvUnitMapMapper;
import com.chd.hrp.med.entity.MedInvUnitMap;
import com.chd.hrp.med.service.info.basic.MedInvUnitMapService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08116 材料包装单位关系表
 * @Table:
 * MED_INV_UNIT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medInvUnitMapService")
public class MedInvUnitMapServiceImpl implements MedInvUnitMapService {

	private static Logger logger = Logger.getLogger(MedInvUnitMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medInvUnitMapMapper")
	private final MedInvUnitMapMapper medInvUnitMapMapper = null;
    
	/**
	 * @Description 
	 * 添加08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedInvUnitMap(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08116 材料包装单位关系表
		MedInvUnitMap medInvUnitMap = queryMedInvUnitMapByCode(entityMap);

		if (medInvUnitMap != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medInvUnitMapMapper.addMedInvUnitMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加过程出错，添加失败  请联系管理员! 方法 addMedInvUnitMap\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08116 材料包装单位关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedInvUnitMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		for(Map<String,Object> item : entityList){
			Map<String,Object> existMap = medInvUnitMapMapper.queryMedInvUnitMapByID(item);

			if (existMap != null) {

				return "{\"error\":\"材料编码:"+existMap.get("inv_code").toString()+"包装单位:"+existMap.get("pack_name").toString()+"已存在,请重新添加.\"}";

			}
		}
		
		try {
			
			medInvUnitMapMapper.addBatchMedInvUnitMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加过程出错,添加失败 请联系管理员! 方法 addBatchMedInvUnitMap\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedInvUnitMap(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = medInvUnitMapMapper.updateMedInvUnitMap(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败 请联系管理员! 方法 updateMedInvUnitMap\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08116 材料包装单位关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedInvUnitMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
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
				medInvUnitMapMapper.updateBatchMedInvUnitMap(updateList);
			}
			if(updateNewList.size()>0){
				medInvUnitMapMapper.updateBatchMedInvUnitMapNew(updateNewList);
			}
		  
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败    请联系管理员! 方法 updateBatchMedInvUnitMap\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedInvUnitMap(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medInvUnitMapMapper.deleteMedInvUnitMap(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedInvUnitMap\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08116 材料包装单位关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedInvUnitMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medInvUnitMapMapper.deleteBatchMedInvUnitMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedInvUnitMap\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedInvUnitMap(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08116 材料包装单位关系表
		MedInvUnitMap medInvUnitMap = queryMedInvUnitMapByCode(entityMap);

		if (medInvUnitMap != null) {

			int state = medInvUnitMapMapper.updateMedInvUnitMap(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medInvUnitMapMapper.addMedInvUnitMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedInvUnitMap\"}";

		}
		
	}
	
	public String addOrUpdateMedInvUnitMap(List<Map<String,Object>> listVo)throws DataAccessException{
		
		List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object>  item : listVo){
			MedInvUnitMap medInvUnitMap = queryMedInvUnitMapByCode(item);
			if (medInvUnitMap != null) {
				updateList.add(item);
			}else{
				addList.add(item);
			}
		}
		
		try {
			
			medInvUnitMapMapper.addBatchMedInvUnitMap(addList);
			medInvUnitMapMapper.updateBatchMedInvUnitMap(updateList);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedInvUnitMap\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedInvUnitMap(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medInvUnitMapMapper.queryMedInvUnitMapNew(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medInvUnitMapMapper.queryMedInvUnitMapNew(entityMap,rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象08116 材料包装单位关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedInvUnitMap queryMedInvUnitMapByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medInvUnitMapMapper.queryMedInvUnitMapByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08116 材料包装单位关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvUnitMap
	 * @throws DataAccessException
	*/
	@Override
	public MedInvUnitMap queryMedInvUnitMapByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medInvUnitMapMapper.queryMedInvUnitMapByUniqueness(entityMap);
	}
	/**
	 * 弹出选择材料页面，根据查询条件查询出药品材料结果集（MED_INV中IS_STOP=0）
	 * @param entityMap
	 * @return
	 */
	public String queryMedInv(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medInvUnitMapMapper.queryMedInv(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medInvUnitMapMapper.queryMedInv(entityMap,rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
}

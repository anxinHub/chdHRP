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

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedStoreDetailMapper;
import com.chd.hrp.med.dao.info.basic.MedStoreSetMapper;
import com.chd.hrp.med.entity.MedStoreSet;
import com.chd.hrp.med.service.info.basic.MedStoreSetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08108 虚仓仓库设置
 * @Table: 
 * MED_STORE_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medStoreSetService")
public class MedStoreSetServiceImpl implements MedStoreSetService {

	private static Logger logger = Logger.getLogger(MedStoreSetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medStoreSetMapper")
	private final MedStoreSetMapper medStoreSetMapper = null;
	
	//引入DAO操作
	@Resource(name = "medStoreDetailMapper")
	private final MedStoreDetailMapper medStoreDetailMapper = null;
		
    
	/**
	 * @Description 
	 * 添加08108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedStoreSet(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08108 虚仓仓库设置
		List<MedStoreSet> list = medStoreSetMapper.queryMedStoreSetByName(entityMap);

		if (list.size()>0) {
			return "{\"error\":\"编码或名称重复,请重新输入.\"}";
		}
		
		try {
			
			int state = medStoreSetMapper.addMedStoreSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStoreSet\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08108 虚仓仓库设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedStoreSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medStoreSetMapper.addBatchMedStoreSet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStoreSet\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedStoreSet(Map<String,Object> entityMap)throws DataAccessException{
		
		List<MedStoreSet> list = medStoreSetMapper.queryMedStoreSetByName(entityMap);

		if (list.size()>0) {
			return "{\"error\":\"名称 重复,请重新输入.\"}";
		}
		
		try {
			
		  int state = medStoreSetMapper.updateMedStoreSet(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedStoreSet\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08108 虚仓仓库设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedStoreSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medStoreSetMapper.updateBatchMedStoreSet(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedStoreSet\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedStoreSet(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medStoreSetMapper.deleteMedStoreSet(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStoreSet\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08108 虚仓仓库设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedStoreSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			StringBuffer setIds = new StringBuffer();
			Map<String,Object> map = new HashMap<String,Object>();
			for(int a = 0 ;a < entityList.size();a++){
				if(a==0){
					map.put("group_id", entityList.get(a).get("group_id"));
					map.put("hos_id", entityList.get(a).get("hos_id"));
					map.put("copy_code", entityList.get(a).get("copy_code"));
				}
				setIds.append(entityList.get(a).get("set_id")).append(",");
			}
			
			if(setIds.length() > 0){
				map.put("setIds", setIds.substring(0,setIds.length()-1));
			}
			int count = medStoreDetailMapper.queryCountStoreDetail(map);
			if(count > 0){
				return "{\"msg\":\"删除失败,请先删除对应仓库设置表信息！\",\"state\":\"true\"}";
			}else{
				medStoreSetMapper.deleteBatchMedStoreSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStoreSet\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加08108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedStoreSet(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08108 虚仓仓库设置
		MedStoreSet medStoreSet = queryMedStoreSetByCode(entityMap);

		if (medStoreSet != null) {

			int state = medStoreSetMapper.updateMedStoreSet(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medStoreSetMapper.addMedStoreSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedStoreSet\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStoreSet(Map<String,Object> entityMap) throws DataAccessException{
		
		List<MedStoreSet> list = medStoreSetMapper.queryMedStoreSet(entityMap);
		return ChdJson.toJson(list);
		
		/*SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedStoreSet> list = medStoreSetMapper.queryMedStoreSet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedStoreSet> list = medStoreSetMapper.queryMedStoreSet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}*/
		
	}
	
	/**
	 * @Description 
	 * 获取对象08108 虚仓仓库设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreSet queryMedStoreSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreSetMapper.queryMedStoreSetByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08108 虚仓仓库设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreSet
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreSet queryMedStoreSetByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreSetMapper.queryMedStoreSetByUniqueness(entityMap);
	}
	/**
	 * 根据用户的数据权限，查询出有数据权限的库房列表
	 * @param page
	 * @return
	 */
	public String queryMedStoreData(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedStoreSet> list = medStoreSetMapper.queryMedStoreData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedStoreSet> list = medStoreSetMapper.queryMedStoreData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	public List<MedStoreSet> queryMedStoreSetList(Map<String, Object> mapVo) throws DataAccessException{
		return medStoreSetMapper.queryMedStoreSet(mapVo);
	}
	
	/**
	 * @Description 
	 * 查询虚仓库房明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStoreDetail(Map<String, Object> entityMap) throws DataAccessException{
			
		return ChdJson.toJson(medStoreSetMapper.queryMedStoreDetail(entityMap));
	}

	/**
	 * @Description 
	 * 保存虚仓对应仓库是否结账<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String saveMedStoreSetAccount(Map<String,Object> entityMap)throws DataAccessException{
		try {
			String group_id = entityMap.get("group_id").toString(); 
			String hos_id = entityMap.get("hos_id").toString();
			String set_id = entityMap.get("set_id").toString();
			StringBuffer errMsg = new StringBuffer();
			String msg = "";
			List<Map<String,Object>> storeList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("storeData") != null && !"".equals(entityMap.get("storeData"))){
				
				String[] storeData = entityMap.get("storeData").toString().split("@");
				
				for(String st : storeData){
					Map<String,Object> storeMap = new HashMap<String,Object>();
					
					String[] stores = st.split(",");
					
					storeMap.put("group_id", group_id);
					storeMap.put("hos_id", hos_id);
					storeMap.put("set_id", set_id);
					storeMap.put("store_id", stores[0]);
					
					//查询改仓库是否已在别的虚库设置结账
					msg = medStoreSetMapper.existsIsAccount(storeMap);
					
					if(msg != null && !"".equals(msg)){
						errMsg.append("库房【").append(stores[1]).append("】已在虚仓【").append(msg).append("】中设置结账<br>");
					}
					
					storeList.add(storeMap);
				}
			}
			
			if(errMsg.length() > 0){
				return "{\"error\":\""+errMsg.toString()+"不能重复设置\"}";
			}
			
			medStoreSetMapper.updateBatchForIsAccount(group_id, hos_id, set_id, storeList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
}

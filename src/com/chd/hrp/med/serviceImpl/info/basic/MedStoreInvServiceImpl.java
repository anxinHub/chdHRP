/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.info.basic.MedStoreInvMapper;
import com.chd.hrp.med.entity.MedStoreInv;
import com.chd.hrp.med.service.info.basic.MedStoreInvService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08110 仓库材料信息
 * @Table:
 * MED_STORE_INV 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medStoreInvService")
public class MedStoreInvServiceImpl implements MedStoreInvService {

	private static Logger logger = Logger.getLogger(MedStoreInvServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medStoreInvMapper")
	private final MedStoreInvMapper medStoreInvMapper = null;
    
	/**
	 * @Description 
	 * 添加08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedStoreInv(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08110 仓库材料信息
		MedStoreInv medStoreInv = queryMedStoreInvByCode(entityMap);
		
		if (medStoreInv != null) {
			
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}		
		try {		
			int state = medStoreInvMapper.addMedStoreInv(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStoreInv\"}";
		}
		
	}
	/**
	 * @Description 
	 * 批量添加08110 仓库材料信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedStoreInv(List<Map<String,Object>> entityList)throws DataAccessException{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		try 
		{
			medStoreInvMapper.deleteBatchMedStoreInv(entityList);
			
			medStoreInvMapper.addBatchMedStoreInv(entityList);
			
				System.out.println("============addB============"+entityList.toString());

				Map<String, Object> map=new HashMap<String, Object>();
				for (int i = 0; i < entityList.size(); i++) {
					map.put("group_id", entityList.get(i).get("group_id"));
					map.put("hos_id", entityList.get(i).get("hos_id"));
					map.put("copy_code", entityList.get(i).get("copy_code"));
					map.put("inv_id", entityList.get(i).get("inv_id"));
					map.put("store_id", entityList.get(i).get("store_id"));
					if(StringTool.isNotBlank(entityList.get(i).get("location_id"))){
						map.put("location_id", entityList.get(i).get("location_id"));
						listVo.add(map);
					}
				}
			
			String code=MyConfig.getSysPara("08035").toString();
			
			if(Integer.parseInt(code) == 1 ){
				if(listVo.size()>0){
					
				  System.out.println("aaaaaaaaaaaaaaaaaaaaa"+listVo);
					medStoreInvMapper.addBatchMedLocationInv(listVo);
				}
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStoreInv\"}";
		}
		
	}
	
	@Override
	public String addMedStoreInvAll(Map<String,Object> entityMap)throws DataAccessException
	
	{
		try 
		{
			medStoreInvMapper.deleteStoreMedStoreInv(entityMap);
			
			medStoreInvMapper.addStoreMedStoreInv(entityMap);	
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			
			//return "{\"error\":\"操作失败\"}";
		}
		
	};
	
	
	/**
	 * @Description 
	 * 更新08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedStoreInv(Map<String,Object> entityMap)throws DataAccessException{		
		try {			
			
		  	medStoreInvMapper.updateMedStoreInv(entityMap);	
		  	
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			
			//return "{\"error\":\"操作失败\"}";
		}		
	}
	
	/**
	 * @Description 
	 * 批量更新08110 仓库材料信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addSafeMedStoreInv(List<Map<String,Object>> entityList)throws DataAccessException{		
		try {	
			
			medStoreInvMapper.updateBatchMedStoreInv(entityList);	
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\" 保存失败\"}");
			
			//return "{\"error\":\" 保存失败\"}";
		}			
	}
	/**
	 * @Description 
	 * 删除08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedStoreInv(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medStoreInvMapper.deleteMedStoreInv(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStoreInv\"}";

		}	
		
  }
	/**
	 * @Description 
	 * 仓库安全设置 --查询
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String querySafeMedStoreInv(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");		
		if (sysPage.getTotal()==-1){			
			List<MedStoreInv> list = medStoreInvMapper.querySafeMedStoreInv(entityMap);			
			return ChdJson.toJson(list);			
		}else{			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());			
			List<MedStoreInv> list = medStoreInvMapper.querySafeMedStoreInv(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);		
			return ChdJson.toJson(list, page.getTotal());			
		}
	}
	/**
	 * @Description 
	 * 仓库安全设置 --删除
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteSafeMedStoreInv(List<Map<String, Object>> entityList) throws DataAccessException{
		try {			
			
			medStoreInvMapper.updateBatchMedStoreInv(entityList);	
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException( "{\"error\":\"操作失败\"}");
			
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStoreInv\"}";
		}
	}
	
	
	/**
	 * @Description 
	 * 批量删除08110 仓库材料信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedStoreInv(List<Map<String,Object>> entityList)throws DataAccessException{
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		try {
			System.out.println("============1============"+entityList.toString());

			Map<String, Object> map=new HashMap<String, Object>();
			for (int i = 0; i < entityList.size(); i++) {
				map.put("group_id", entityList.get(i).get("group_id"));
				map.put("hos_id", entityList.get(i).get("hos_id"));
				map.put("copy_code", entityList.get(i).get("copy_code"));
				map.put("inv_id", entityList.get(i).get("inv_id"));
				
				if("-1".equals(entityList.get(i).get("location_id"))){
					
				}else{
					map.put("location_id", entityList.get(i).get("location_id"));
					listVo.add(map);
				}
			}
			
			System.out.println(listVo.size()+"============2============"+map.toString());
			medStoreInvMapper.deleteBatchMedStoreInv(entityList);
			String code=MyConfig.getSysPara("08035").toString();
			if(Integer.parseInt(code) == 1 ){
				if(listVo.size()>0){
					medStoreInvMapper.deleteBatchMedLocationInv(listVo);
				}
			}
			
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStoreInv\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 添加08110 仓库材料信息<BR>  对货位进行操作
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedStoreInv(Map<String,Object> entityMap)throws DataAccessException{
		 
		//获取对象08110 仓库材料信息
		MedStoreInv medStoreInv = queryMedStoreInvByCode(entityMap);
		
		if (medStoreInv != null) {
			 
			int state = medStoreInvMapper.updateMedLocationInv(entityMap);	 
			
			 return "{\"msg\":\"更新成功.\",\"state\":\"true\"}"; 
		}	 
		
		try {		
			int state = medStoreInvMapper.addMedLocationInv(entityMap);	
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedStoreInv\"}";
		}
		
	}
	  
	/**
	 * @Description 
	 * 获取对象08110 仓库材料信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreInv queryMedStoreInvByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreInvMapper.queryMedStoreInvByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08110 仓库材料信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreInv
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreInv queryMedStoreInvByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreInvMapper.queryMedStoreInvByUniqueness(entityMap);
	}
	/**
	 * 根据条件查询 （联合） 药品材料记录
	 * @param entityMap
	 * @return
	 */
	public String queryMedStoreInvNew(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medStoreInvMapper.queryMedStoreInvNew(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medStoreInvMapper.queryMedStoreInvNew(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInv(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medStoreInvMapper.queryMedInv(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medStoreInvMapper.queryMedInv(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 根据仓库Id 查询 与其存在对应关系的所有药品材料Id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInvStoreId(Map<String, Object> mapVo) throws DataAccessException{
		return medStoreInvMapper.queryMedInvStoreId(mapVo);
	}
	
	@Override
	public String updateBatchMedStoreInv(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 仓库材料定义  保存材料
	 */
	@Override
	public String addMedStoreInvCert(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> invList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> locationList = new ArrayList<Map<String,Object>>();
			String user_id = SessionManager.getUserId();
			Date date = new Date();
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				
				String[] params = entityMap.get("detailData").toString().split("@");
				
				for(String param: params){
					
					Map<String,Object> invMap = new HashMap<String,Object>();
					invMap.put("group_id", entityMap.get("group_id"));
					invMap.put("hos_id", entityMap.get("hos_id"));
					invMap.put("copy_code", entityMap.get("copy_code"));
					invMap.put("inv_id",  param.split(",")[0]);//材料ID
					invMap.put("is_apply",  param.split(",")[1]);//是否申领
					invMap.put("store_id", entityMap.get("store_id"));
					invMap.put("oper", user_id);
					invMap.put("oper_date", date);
					invList.add(invMap);
				}
				
				for(String pm: params){
					
					Map<String,Object> locationMap = new HashMap<String,Object>();
					locationMap.put("group_id", entityMap.get("group_id"));
					locationMap.put("hos_id", entityMap.get("hos_id"));
					locationMap.put("copy_code", entityMap.get("copy_code"));
					locationMap.put("inv_id",  pm.split(",")[0]);//材料ID
					locationMap.put("store_id", entityMap.get("store_id"));
					if(pm.split(",").length > 2){
						locationMap.put("location_id",  pm.split(",")[2]);//货位ID
					}else{
						break;
					}
					
					locationList.add(locationMap);
				}
				
			}
			
			if(invList.size()>0){
				medStoreInvMapper.addBatchMedStoreInv(invList);
			}
			String code=MyConfig.getSysPara("08035").toString();
			System.out.println(">>>>>>>>>"+code);
			if(Integer.parseInt(code) == 1 ){
				if(locationList.size()>0){
					medStoreInvMapper.addBatchMedLocationInv(locationList);
				}
			}
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedInvCert\"}";
		}
	}
	/**
	 * 仓库材料定义  选择材料  全部
	 */
	@Override
	public String queryMedInvAllList(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = medStoreInvMapper.queryMedInvAllList(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = medStoreInvMapper.queryMedInvAllList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 判断材料是否已经存在于仓库中
	 * @param mapVo
	 * @return
	 */
	@Override
	public String existsMedInvInStore(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			String inv_ids = medStoreInvMapper.existsMedInvInStore(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMedInvInStore\"}";
		}	
	}
	
	/**
	 * 查询材料申领科室
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedInvApplyStore(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			List<Map<String,Object>> list = medStoreInvMapper.queryMedInvApplyStore(entityMap);
			
			int flag = 0;
			
			for(int x = 0; x < list.size() ; x++){
				
				String store_id = list.get(x).get("store_id").toString();
				
				if(!entityMap.get("store_id").toString().equals(store_id)){
					
					flag = 1;
					
					break;
				}
			}
			
			if(flag == 0){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\"}";
			}
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMedInvApplyStore\"}";
		}	
	}
	
	/**
	 * 设置默认申领仓库
	 * @param List
	 * @return
	 */
	@Override
	public String updateMedInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			medStoreInvMapper.updateMedInvApplyStore(entityMap);
			
			return "{\"msg\":\"设置成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
		
	}
	
	/**
	 * 取消设置默认申领仓库
	 * @param List
	 * @return
	 */
	@Override
	public String updateCancelMedInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			medStoreInvMapper.updateMedInvApplyStore(entityMap);
			
			return "{\"msg\":\"取消成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
		
	}
}

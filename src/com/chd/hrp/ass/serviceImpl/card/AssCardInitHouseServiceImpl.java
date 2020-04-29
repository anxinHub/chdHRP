/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.card;

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
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardInitHouseMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitHouseMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitHouseMapper;
import com.chd.hrp.ass.dao.file.AssFileInitHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitHouseMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInitHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitHouseMapper;
import com.chd.hrp.ass.entity.card.AssCardInitHouse;
import com.chd.hrp.ass.entity.file.AssFileInitHouse;
import com.chd.hrp.ass.entity.photo.AssPhotoInitHouse;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardInitHouseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产卡片维护_房屋及建筑物
 * @Table:
 * ASS_CARD_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assCardInitHouseService")
public class AssCardInitHouseServiceImpl implements AssCardInitHouseService {

	private static Logger logger = Logger.getLogger(AssCardInitHouseServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCardInitHouseMapper")
	private final AssCardInitHouseMapper assCardInitHouseMapper = null;
	
	@Resource(name = "assResourceInitHouseMapper")
	private final AssResourceInitHouseMapper assResourceInitHouseMapper = null;
	
	@Resource(name = "assShareDeptInitHouseMapper")
	private final AssShareDeptInitHouseMapper assShareDeptInitHouseMapper = null;
	
	@Resource(name = "assFileInitHouseMapper")
	private final AssFileInitHouseMapper assFileInitHouseMapper = null;
	
	@Resource(name = "assPhotoInitHouseMapper")
	private final AssPhotoInitHouseMapper assPhotoInitHouseMapper = null;
	
	@Resource(name = "assAccessoryInitHouseMapper")
	private final AssAccessoryInitHouseMapper assAccessoryInitHouseMapper = null;
	
	@Resource(name = "assDepreAccInitHouseMapper")
	private final AssDepreAccInitHouseMapper assDepreAccInitHouseMapper = null;
	
	@Resource(name = "assDepreManageInitHouseMapper")
	private final AssDepreManageInitHouseMapper assDepreManageInitHouseMapper = null;
	
	@Resource(name = "assPayStageInitHouseMapper")
	private final AssPayStageInitHouseMapper assPayStageInitHouseMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    
	/**
	 * @Description 
	 * 添加资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		
		try {
			if(entityMap.get("ven_id") == null || entityMap.get("ven_id").equals("") || entityMap.get("ven_id").equals("undefined")){
				entityMap.put("ven_id","");
				entityMap.put("ven_no","");
			}
			
			if(entityMap.get("fac_id") == null || entityMap.get("fac_id").equals("") || entityMap.get("fac_id").equals("undefined")){
				entityMap.put("fac_id","");
				entityMap.put("fac_no","");
			}
			
			Map<String,Object> delMap = new HashMap<String, Object>();
			delMap.put("group_id", entityMap.get("group_id"));
			delMap.put("hos_id", entityMap.get("hos_id"));
			delMap.put("copy_code", entityMap.get("copy_code"));
			delMap.put("ass_card_no", entityMap.get("ass_card_no_old"));
			
			assShareDeptInitHouseMapper.delete(delMap);
			assResourceInitHouseMapper.delete(delMap);
			assFileInitHouseMapper.delete(delMap);
			assPhotoInitHouseMapper.delete(delMap);
			assAccessoryInitHouseMapper.delete(delMap);
			assPayStageInitHouseMapper.delete(delMap);
			assDepreAccInitHouseMapper.delete(delMap);
			assDepreManageInitHouseMapper.delete(delMap);
			
			
			assCardInitHouseMapper.delete(delMap);
			int state = assCardInitHouseMapper.add(entityMap);
			
			entityMap.put("source_id", 1);
			assResourceInitHouseMapper.add(entityMap);
			
			if(entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("") && entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")){
				entityMap.put("area",1);
				assShareDeptInitHouseMapper.add(entityMap);
			}
			
			assBaseService.updateAssBillNoMaxNo("ASS_CARD_HOUSE");
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_card_no\":\""+entityMap.get("ass_card_no")+"\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 批量添加资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCardInitHouseMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assCardInitHouseMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCardInitHouseMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCardInitHouseMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//删除资产文档
			for(Map<String,Object> fileMap : entityList){
				List<AssFileInitHouse> assFileInitHouseList = (List<AssFileInitHouse>)assFileInitHouseMapper.queryExists(fileMap);
				if(assFileInitHouseList.size() > 0 && assFileInitHouseList != null){
					for(AssFileInitHouse assFileInitHouse : assFileInitHouseList){
						if(assFileInitHouse.getFile_url() != null && !assFileInitHouse.getFile_url().equals("")){
							String file_name = assFileInitHouse.getFile_url().substring(assFileInitHouse.getFile_url().lastIndexOf("/") + 1,  assFileInitHouse.getFile_url().length());
							String path =  assFileInitHouse.getFile_url().substring(0, assFileInitHouse.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitHouse.getAss_card_no(), path);
						}
					}
				}
			}
			
			//删除资产照片
			for(Map<String,Object> photoMap : entityList){
				List<AssPhotoInitHouse> assPhotoInitHouseList = (List<AssPhotoInitHouse>)assPhotoInitHouseMapper.queryExists(photoMap);
				if(assPhotoInitHouseList.size() > 0 && assPhotoInitHouseList != null){
					for(AssPhotoInitHouse assPhotoInitHouse : assPhotoInitHouseList){
						if(assPhotoInitHouse.getFile_url() != null && !assPhotoInitHouse.getFile_url().equals("")){
							String file_name = assPhotoInitHouse.getFile_url().substring(assPhotoInitHouse.getFile_url().lastIndexOf("/") + 1,  assPhotoInitHouse.getFile_url().length());
							String path =  assPhotoInitHouse.getFile_url().substring(0, assPhotoInitHouse.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitHouse.getAss_card_no(), path);
						}
					}
				}
			}
			
			assShareDeptInitHouseMapper.deleteBatch(entityList);
			assResourceInitHouseMapper.deleteBatch(entityList);
			assFileInitHouseMapper.deleteBatch(entityList);
			assPhotoInitHouseMapper.deleteBatch(entityList);
			assAccessoryInitHouseMapper.deleteBatch(entityList);
			assPayStageInitHouseMapper.deleteBatch(entityList);
			assDepreAccInitHouseMapper.deleteBatch(entityList);
			assDepreManageInitHouseMapper.deleteBatch(entityList);
			assCardInitHouseMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象资产卡片维护_房屋及建筑物
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssCardInitHouse> list = (List<AssCardInitHouse>)assCardInitHouseMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assCardInitHouseMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assCardInitHouseMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCardInitHouse> list = (List<AssCardInitHouse>)assCardInitHouseMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCardInitHouse> list = (List<AssCardInitHouse>)assCardInitHouseMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitHouseMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCardHouse
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitHouseMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_房屋及建筑物<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCardHouse>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitHouseMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String,Object>> list = (List<Map<String,Object>>) assCardInitHouseMapper.queryPrint(entityMap);
		return list;
	}
	
}

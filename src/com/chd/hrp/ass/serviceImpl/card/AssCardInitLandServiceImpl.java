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
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitLandMapper;
import com.chd.hrp.ass.dao.card.AssCardInitLandMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitLandMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitLandMapper;
import com.chd.hrp.ass.dao.file.AssFileInitLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitLandMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInitLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitLandMapper;
import com.chd.hrp.ass.entity.card.AssCardInitLand;
import com.chd.hrp.ass.entity.card.AssCardInitLand;
import com.chd.hrp.ass.entity.file.AssFileInitLand;
import com.chd.hrp.ass.entity.photo.AssPhotoInitLand;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardInitLandService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产卡片维护_土地
 * @Table:
 * ASS_CARD_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assCardInitLandService")
public class AssCardInitLandServiceImpl implements AssCardInitLandService {

	private static Logger logger = Logger.getLogger(AssCardInitLandServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCardInitLandMapper")
	private final AssCardInitLandMapper assCardInitLandMapper = null;
	
	@Resource(name = "assResourceInitLandMapper")
	private final AssResourceInitLandMapper assResourceInitLandMapper = null;
	
	@Resource(name = "assShareDeptInitLandMapper")
	private final AssShareDeptInitLandMapper assShareDeptInitLandMapper = null;
	
	@Resource(name = "assFileInitLandMapper")
	private final AssFileInitLandMapper assFileInitLandMapper = null;
	
	@Resource(name = "assPhotoInitLandMapper")
	private final AssPhotoInitLandMapper assPhotoInitLandMapper = null;
	
	@Resource(name = "assAccessoryInitLandMapper")
	private final AssAccessoryInitLandMapper assAccessoryInitLandMapper = null;
	
	@Resource(name = "assDepreAccInitLandMapper")
	private final AssDepreAccInitLandMapper assDepreAccInitLandMapper = null;
	
	@Resource(name = "assDepreManageInitLandMapper")
	private final AssDepreManageInitLandMapper assDepreManageInitLandMapper = null;
	
	@Resource(name = "assPayStageInitLandMapper")
	private final AssPayStageInitLandMapper assPayStageInitLandMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    
	/**
	 * @Description 
	 * 添加资产卡片维护_土地<BR> 
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
			
			assShareDeptInitLandMapper.delete(delMap);
			assResourceInitLandMapper.delete(delMap);
			assFileInitLandMapper.delete(delMap);
			assPhotoInitLandMapper.delete(delMap);
			assAccessoryInitLandMapper.delete(delMap);
			assPayStageInitLandMapper.delete(delMap);
			assDepreAccInitLandMapper.delete(delMap);
			assDepreManageInitLandMapper.delete(delMap);
			
			assCardInitLandMapper.delete(delMap);
			int state = assCardInitLandMapper.add(entityMap);
			
			entityMap.put("source_id", 1);
			assResourceInitLandMapper.add(entityMap);
			
			if(entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("") && entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")){
				entityMap.put("area",1);
				assShareDeptInitLandMapper.add(entityMap);
			}
			
			assBaseService.updateAssBillNoMaxNo("ASS_CARD_LAND");
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_card_no\":\""+entityMap.get("ass_card_no")+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加资产卡片维护_土地<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCardInitLandMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
		/**
	 * @Description 
	 * 更新资产卡片维护_土地<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assCardInitLandMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新资产卡片维护_土地<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCardInitLandMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
		
	}
	/**
	 * @Description 
	 * 删除资产卡片维护_土地<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCardInitLandMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产卡片维护_土地<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//删除资产文档
			for(Map<String,Object> fileMap : entityList){
				List<AssFileInitLand> assFileInitLandList = (List<AssFileInitLand>)assFileInitLandMapper.queryExists(fileMap);
				if(assFileInitLandList.size() > 0 && assFileInitLandList != null){
					for(AssFileInitLand assFileInitLand : assFileInitLandList){
						if(assFileInitLand.getFile_url() != null && !assFileInitLand.getFile_url().equals("")){
							String file_name = assFileInitLand.getFile_url().substring(assFileInitLand.getFile_url().lastIndexOf("/") + 1,  assFileInitLand.getFile_url().length());
							String path =  assFileInitLand.getFile_url().substring(0, assFileInitLand.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitLand.getAss_card_no(), path);
						}
					}
				}
			}
			
			//删除资产照片
			for(Map<String,Object> photoMap : entityList){
				List<AssPhotoInitLand> assPhotoInitLandList = (List<AssPhotoInitLand>)assPhotoInitLandMapper.queryExists(photoMap);
				if(assPhotoInitLandList.size() > 0 && assPhotoInitLandList != null){
					for(AssPhotoInitLand assPhotoInitLand : assPhotoInitLandList){
						if(assPhotoInitLand.getFile_url() != null && !assPhotoInitLand.getFile_url().equals("")){
							String file_name = assPhotoInitLand.getFile_url().substring(assPhotoInitLand.getFile_url().lastIndexOf("/") + 1,  assPhotoInitLand.getFile_url().length());
							String path =  assPhotoInitLand.getFile_url().substring(0, assPhotoInitLand.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitLand.getAss_card_no(), path);
						}
					}
				}
			}
			
			
			assShareDeptInitLandMapper.deleteBatch(entityList);
			assResourceInitLandMapper.deleteBatch(entityList);
			assFileInitLandMapper.deleteBatch(entityList);
			assPhotoInitLandMapper.deleteBatch(entityList);
			assAccessoryInitLandMapper.deleteBatch(entityList);
			assPayStageInitLandMapper.deleteBatch(entityList);
			assDepreAccInitLandMapper.deleteBatch(entityList);
			assDepreManageInitLandMapper.deleteBatch(entityList);
			assCardInitLandMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加资产卡片维护_土地<BR> 
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
		//判断是否存在对象资产卡片维护_土地
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssCardInitLand> list = (List<AssCardInitLand>)assCardInitLandMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assCardInitLandMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assCardInitLandMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集资产卡片维护_土地<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCardInitLand> list = (List<AssCardInitLand>)assCardInitLandMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCardInitLand> list = (List<AssCardInitLand>)assCardInitLandMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产卡片维护_土地<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitLandMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_土地<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCardLand
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitLandMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_土地<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCardLand>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitLandMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String,Object>> list = (List<Map<String,Object>>) assCardInitLandMapper.queryPrint(entityMap);
		return list;
	}
	
}

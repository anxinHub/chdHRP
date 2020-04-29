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
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitInassetsMapper;
import com.chd.hrp.ass.dao.card.AssCardInitInassetsMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitInassetsMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitInassetsMapper;
import com.chd.hrp.ass.dao.file.AssFileInitInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitInassetsMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoInitInassetsMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitInassetsMapper;
import com.chd.hrp.ass.entity.card.AssCardInitInassets;
import com.chd.hrp.ass.entity.file.AssFileInitInassets;
import com.chd.hrp.ass.entity.photo.AssPhotoInitInassets;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardInitInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产卡片维护_无形资产
 * @Table:
 * ASS_CARD_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assCardInitInassetsService")
public class AssCardInitInassetsServiceImpl implements AssCardInitInassetsService {

	private static Logger logger = Logger.getLogger(AssCardInitInassetsServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCardInitInassetsMapper")
	private final AssCardInitInassetsMapper assCardInitInassetsMapper = null;
	
	@Resource(name = "assResourceInitInassetsMapper")
	private final AssResourceInitInassetsMapper assResourceInitInassetsMapper = null;
	
	@Resource(name = "assShareDeptInitInassetsMapper")
	private final AssShareDeptInitInassetsMapper assShareDeptInitInassetsMapper = null;
	
	@Resource(name = "assFileInitInassetsMapper")
	private final AssFileInitInassetsMapper assFileInitInassetsMapper = null;
	
	@Resource(name = "assPhotoInitInassetsMapper")
	private final AssPhotoInitInassetsMapper assPhotoInitInassetsMapper = null;
	
	@Resource(name = "assAccessoryInitInassetsMapper")
	private final AssAccessoryInitInassetsMapper assAccessoryInitInassetsMapper = null;
	
	@Resource(name = "assDepreAccInitInassetsMapper")
	private final AssDepreAccInitInassetsMapper assDepreAccInitInassetsMapper = null;
	
	@Resource(name = "assDepreManageInitInassetsMapper")
	private final AssDepreManageInitInassetsMapper assDepreManageInitInassetsMapper = null;
	
	@Resource(name = "assPayStageInitInassetsMapper")
	private final AssPayStageInitInassetsMapper assPayStageInitInassetsMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    
	/**
	 * @Description 
	 * 添加资产卡片维护_无形资产<BR> 
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
			
			assShareDeptInitInassetsMapper.delete(delMap);
			assResourceInitInassetsMapper.delete(delMap);
			assFileInitInassetsMapper.delete(delMap);
			assPhotoInitInassetsMapper.delete(delMap);
			assAccessoryInitInassetsMapper.delete(delMap);
			assPayStageInitInassetsMapper.delete(delMap);
			assDepreAccInitInassetsMapper.delete(delMap);
			assDepreManageInitInassetsMapper.delete(delMap);
			
			assCardInitInassetsMapper.delete(delMap);
			int state = assCardInitInassetsMapper.add(entityMap);
			
			entityMap.put("source_id", 1);
			assResourceInitInassetsMapper.add(entityMap);
			
			if(entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("") && entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")){
				entityMap.put("area",1);
				assShareDeptInitInassetsMapper.add(entityMap);
			}
			
			assBaseService.updateAssBillNoMaxNo("ASS_CARD_INASSETS");
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_card_no\":\""+entityMap.get("ass_card_no")+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 批量添加资产卡片维护_无形资产<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCardInitInassetsMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新资产卡片维护_无形资产<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assCardInitInassetsMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新资产卡片维护_无形资产<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCardInitInassetsMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除资产卡片维护_无形资产<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCardInitInassetsMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产卡片维护_无形资产<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			//删除资产文档
			for(Map<String,Object> fileMap : entityList){
				List<AssFileInitInassets> assFileInitInassetsList = (List<AssFileInitInassets>)assFileInitInassetsMapper.queryExists(fileMap);
				if(assFileInitInassetsList.size() > 0 && assFileInitInassetsList != null){
					for(AssFileInitInassets assFileInitInassets : assFileInitInassetsList){
						if(assFileInitInassets.getFile_url() != null && !assFileInitInassets.getFile_url().equals("")){
							String file_name = assFileInitInassets.getFile_url().substring(assFileInitInassets.getFile_url().lastIndexOf("/") + 1,  assFileInitInassets.getFile_url().length());
							String path =  assFileInitInassets.getFile_url().substring(0, assFileInitInassets.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileInitInassets.getAss_card_no(), path);
						}
					}
				}
			}
			
			//删除资产照片
			for(Map<String,Object> photoMap : entityList){
				List<AssPhotoInitInassets> assPhotoInitInassetsList = (List<AssPhotoInitInassets>)assPhotoInitInassetsMapper.queryExists(photoMap);
				if(assPhotoInitInassetsList.size() > 0 && assPhotoInitInassetsList != null){
					for(AssPhotoInitInassets assPhotoInitInassets : assPhotoInitInassetsList){
						if(assPhotoInitInassets.getFile_url() != null && !assPhotoInitInassets.getFile_url().equals("")){
							String file_name = assPhotoInitInassets.getFile_url().substring(assPhotoInitInassets.getFile_url().lastIndexOf("/") + 1,  assPhotoInitInassets.getFile_url().length());
							String path =  assPhotoInitInassets.getFile_url().substring(0, assPhotoInitInassets.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoInitInassets.getAss_card_no(), path);
						}
					}
				}
			}
			
			assShareDeptInitInassetsMapper.deleteBatch(entityList);
			assResourceInitInassetsMapper.deleteBatch(entityList);
			assFileInitInassetsMapper.deleteBatch(entityList);
			assPhotoInitInassetsMapper.deleteBatch(entityList);
			assAccessoryInitInassetsMapper.deleteBatch(entityList);
			assPayStageInitInassetsMapper.deleteBatch(entityList);
			assDepreAccInitInassetsMapper.deleteBatch(entityList);
			assDepreManageInitInassetsMapper.deleteBatch(entityList);
			assCardInitInassetsMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加资产卡片维护_无形资产<BR> 
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
		//判断是否存在对象资产卡片维护_无形资产
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssCardInitInassets> list = (List<AssCardInitInassets>)assCardInitInassetsMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assCardInitInassetsMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assCardInitInassetsMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集资产卡片维护_无形资产<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCardInitInassets> list = (List<AssCardInitInassets>)assCardInitInassetsMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCardInitInassets> list = (List<AssCardInitInassets>)assCardInitInassetsMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产卡片维护_无形资产<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitInassetsMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_无形资产<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCardInassets
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitInassetsMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_无形资产<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCardInassets>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCardInitInassetsMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String,Object>> list = (List<Map<String,Object>>) assCardInitInassetsMapper.queryPrint(entityMap);
		return list;
	}
	
}

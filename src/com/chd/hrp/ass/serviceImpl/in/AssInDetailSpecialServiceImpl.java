/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.in;

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
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.accessory.AssAccessorySpecialMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccSpecialMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageSpecialMapper;
import com.chd.hrp.ass.dao.file.AssFileSpecialMapper;
import com.chd.hrp.ass.dao.in.AssInDetailSpecialMapper;
import com.chd.hrp.ass.dao.in.AssInMainSpecialMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageSpecialMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoSpecialMapper;
import com.chd.hrp.ass.dao.resource.AssResourceSpecialMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRSpecialMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptSpecialMapper;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.file.AssFileSpecial;
import com.chd.hrp.ass.entity.in.AssInDetailSpecial;
import com.chd.hrp.ass.entity.photo.AssPhotoSpecial;
import com.chd.hrp.ass.service.in.AssInDetailSpecialService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产入库明细(专用设备)
 * @Table:
 * ASS_IN_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assInDetailSpecialService")
public class AssInDetailSpecialServiceImpl implements AssInDetailSpecialService {

	private static Logger logger = Logger.getLogger(AssInDetailSpecialServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assInDetailSpecialMapper")
	private final AssInDetailSpecialMapper assInDetailSpecialMapper = null;
	
	@Resource(name = "assInMainSpecialMapper")
	private final AssInMainSpecialMapper assInMainSpecialMapper = null;
	
	@Resource(name = "assCardSpecialMapper")
	private final AssCardSpecialMapper assCardSpecialMapper = null;
	
	@Resource(name = "assResourceSpecialMapper")
	private final AssResourceSpecialMapper assResourceSpecialMapper = null;
	
	@Resource(name = "assShareDeptSpecialMapper")
	private final AssShareDeptSpecialMapper assShareDeptSpecialMapper = null;
	
	@Resource(name = "assShareDeptRSpecialMapper")
	private final AssShareDeptRSpecialMapper assShareDeptRSpecialMapper = null;
	
	@Resource(name = "assFileSpecialMapper")
	private final AssFileSpecialMapper assFileSpecialMapper = null;

	@Resource(name = "assPhotoSpecialMapper")
	private final AssPhotoSpecialMapper assPhotoSpecialMapper = null;

	@Resource(name = "assAccessorySpecialMapper")
	private final AssAccessorySpecialMapper assAccessorySpecialMapper = null;

	@Resource(name = "assDepreAccSpecialMapper")
	private final AssDepreAccSpecialMapper assDepreAccSpecialMapper = null;

	@Resource(name = "assDepreManageSpecialMapper")
	private final AssDepreManageSpecialMapper assDepreManageSpecialMapper = null;

	@Resource(name = "assPayStageSpecialMapper")
	private final AssPayStageSpecialMapper assPayStageSpecialMapper = null;
    
	/**
	 * @Description 
	 * 添加资产入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象资产入库明细(专用设备)
		AssInDetailSpecial assInDetailSpecial = queryByCode(entityMap);

		if (assInDetailSpecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assInDetailSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加资产入库明细(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assInDetailSpecialMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新资产入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assInDetailSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新资产入库明细(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assInDetailSpecialMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除资产入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assInDetailSpecialMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产入库明细(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			Map<String, Object> inMapVo =new HashMap<String, Object>();
			inMapVo.put("group_id",entityList.get(0).get("group_id"));
	    	inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
	    	inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
	    	inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
	    	delCard(entityList);
	    	
			assInDetailSpecialMapper.deleteBatch(entityList);
			
			List<AssInDetailSpecial> list = (List<AssInDetailSpecial>)assInDetailSpecialMapper.queryExists(inMapVo);
			
			double in_money=0;
			if(list != null){
				for(AssInDetailSpecial temp :  list ){
					in_money += temp.getPrice() * temp.getIn_amount();
				}
			}
			inMapVo.put("in_money", in_money+"");
			assInMainSpecialMapper.updateInMoney(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"in_money\":\""+in_money+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
	}
	
	
	private void delCard(List<Map<String, Object>> entityList) {
		try {
			Map<String, Object> inMapVo = new HashMap<String, Object>();
			inMapVo.put("group_id", entityList.get(0).get("group_id"));
			inMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
			inMapVo.put("ass_id", entityList.get(0).get("ass_id"));
			inMapVo.put("ass_no", entityList.get(0).get("ass_no"));
			List<Map<String, Object>> cardList = assCardSpecialMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {
				// 删除条码文件
				for (Map<String, Object> map : cardList) {
					AssCardSpecial assCardSpecial = assCardSpecialMapper.queryByCode(map);
					if (assCardSpecial.getBar_url() != null && !assCardSpecial.getBar_url().equals("")) {
						String file_name = assCardSpecial.getBar_url().substring(
								assCardSpecial.getBar_url().lastIndexOf("/") + 1, assCardSpecial.getBar_url().length());
						String path = assCardSpecial.getBar_url().substring(0,
								assCardSpecial.getBar_url().lastIndexOf("/"));
						FtpUtil.deleteFile(path, file_name);
					}
				}

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileSpecial> assFileSpecialList = (List<AssFileSpecial>) assFileSpecialMapper
							.queryExists(fileMap);
					if (assFileSpecialList.size() > 0 && assFileSpecialList != null) {
						for (AssFileSpecial assFileSpecial : assFileSpecialList) {
							if (assFileSpecial.getFile_url() != null && !assFileSpecial.getFile_url().equals("")) {
								String file_name = assFileSpecial.getFile_url().substring(
										assFileSpecial.getFile_url().lastIndexOf("/") + 1,
										assFileSpecial.getFile_url().length());
								String path = assFileSpecial.getFile_url().substring(0,
										assFileSpecial.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileSpecial.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoSpecial> assPhotoSpecialList = (List<AssPhotoSpecial>) assPhotoSpecialMapper
							.queryExists(photoMap);
					if (assPhotoSpecialList.size() > 0 && assPhotoSpecialList != null) {
						for (AssPhotoSpecial assPhotoSpecial : assPhotoSpecialList) {
							if (assPhotoSpecial.getFile_url() != null && !assPhotoSpecial.getFile_url().equals("")) {
								String file_name = assPhotoSpecial.getFile_url().substring(
										assPhotoSpecial.getFile_url().lastIndexOf("/") + 1,
										assPhotoSpecial.getFile_url().length());
								String path = assPhotoSpecial.getFile_url().substring(0,
										assPhotoSpecial.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoSpecial.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRSpecialMapper.deleteBatch(cardList);
				assShareDeptSpecialMapper.deleteBatch(cardList);
				assResourceSpecialMapper.deleteBatch(cardList);
				assFileSpecialMapper.deleteBatch(cardList);
				assPhotoSpecialMapper.deleteBatch(cardList);
				assAccessorySpecialMapper.deleteBatch(cardList);
				assPayStageSpecialMapper.deleteBatch(cardList);
				assDepreAccSpecialMapper.deleteBatch(cardList);
				assDepreManageSpecialMapper.deleteBatch(cardList);
				assCardSpecialMapper.deleteBatchByAssInNo(cardList);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * @Description 
	 * 添加资产入库明细(专用设备)<BR> 
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
		//判断是否存在对象资产入库明细(专用设备)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssInDetailSpecial> list = (List<AssInDetailSpecial>)assInDetailSpecialMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assInDetailSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assInDetailSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集资产入库明细(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssInDetailSpecial> list = (List<AssInDetailSpecial>)assInDetailSpecialMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssInDetailSpecial> list = (List<AssInDetailSpecial>)assInDetailSpecialMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产入库明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assInDetailSpecialMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产入库明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssInDetailSpecial
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assInDetailSpecialMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产入库明细(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssInDetailSpecial>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assInDetailSpecialMapper.queryExists(entityMap);
	}
	@Override
	public List<AssInDetailSpecial> queryByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assInDetailSpecialMapper.queryByAssInNo(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> entityMap) throws DataAccessException {
		return assInDetailSpecialMapper.queryByInit(entityMap);
	}
	@Override
	public List<AssInDetailSpecial> queryAssBackSpecialDetail(
			Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assInDetailSpecialMapper.queryAssBackSpecialDetail(mapVo);
	}
	
}

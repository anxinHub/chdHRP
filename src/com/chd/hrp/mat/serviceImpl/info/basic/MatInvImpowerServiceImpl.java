
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.codec.language.bm.Lang;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.info.basic.MatInvCertTypeMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvImpowerMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvImpowerRelaMapper;
import com.chd.hrp.mat.entity.MatInvCertType;
import com.chd.hrp.mat.entity.MatInvImpower;
import com.chd.hrp.mat.service.info.basic.MatInvCertTypeService;
import com.chd.hrp.mat.service.info.basic.MatInvImpowerService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 *注册授权
 * @Table:
 * MAT_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matInvImpowerService")
public class MatInvImpowerServiceImpl implements MatInvImpowerService {

	private static Logger logger = Logger.getLogger(MatInvImpowerServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInvImpowerMapper")
	private final MatInvImpowerMapper matInvImpowerMapper = null;
	@Resource(name = "matInvImpowerRelaMapper")
	private final MatInvImpowerRelaMapper matInvImpowerRelaMapper = null;
    
	/**
	 * @Description 
	 * 添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatInvImpower(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
	
		List<MatInvImpower> list = matInvImpowerMapper.queryMatInvImpowerById(entityMap);

		if (list.size() > 0) {
			for(MatInvImpower item : list){
				if(item.getImpower_code().equals(entityMap.get("impower_code"))){
					return "{\"error\":\"授权编码:"+entityMap.get("impower_code")+"重复,请重新添加.\"}";
				}
			}
		}
		
		try {
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> relaMap = new HashMap<String,Object>();
						relaMap.put("group_id", entityMap.get("group_id"));
						relaMap.put("hos_id", entityMap.get("hos_id"));
						relaMap.put("copy_code", entityMap.get("copy_code"));
						relaMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						relaMap.put("impower_id", entityMap.get("impower_id"));
						relaMap.put("impower_code", entityMap.get("impower_code"));
						orderRelaList.add(relaMap);
					}
				}
			}
			int state = matInvImpowerMapper.addMatInvImpower(entityMap);
			if(orderRelaList.size()>0){
				matInvImpowerRelaMapper.addBatchMatInvImpowerRela(orderRelaList);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInvImpower\"}";

		}
		
	}
	
	
		/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatInvImpower(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String,Object>> insertList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
		List<MatInvImpower> list = matInvImpowerMapper.queryMatInvImpowerById(entityMap);

		if (list.size() > 0) {
			for(MatInvImpower item : list){
				if(item.getImpower_code().equals(entityMap.get("impower_code"))){
					return "{\"error\":\"授权编码:"+entityMap.get("impower_code")+"重复,请重新输入.\"}";
				}
			}
		}
		
		try {
			if(entityMap.get("insertData") != null && !"".equals(entityMap.get("insertData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("insertData"));
				
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> relaMap = new HashMap<String,Object>();
						relaMap.put("group_id", entityMap.get("group_id"));
						relaMap.put("hos_id", entityMap.get("hos_id"));
						relaMap.put("copy_code", entityMap.get("copy_code"));
						relaMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						relaMap.put("impower_id", entityMap.get("impower_id"));
						relaMap.put("impower_code", entityMap.get("impower_code"));
						insertList.add(relaMap);
					}
				}
			}
			
			if(entityMap.get("updateData") != null && !"".equals(entityMap.get("updateData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("updateData"));
				
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> deleteMap = new HashMap<String,Object>();
						Map<String,Object> insertMap = new HashMap<String,Object>();
						
						if(!jsonObj.get("inv_id").equals(jsonObj.get("invid"))){
							deleteMap.put("group_id", entityMap.get("group_id"));
							deleteMap.put("hos_id", entityMap.get("hos_id"));
							deleteMap.put("copy_code", entityMap.get("copy_code"));
							deleteMap.put("impower_id", entityMap.get("impower_id"));
							deleteMap.put("impower_code", entityMap.get("impower_code"));
							deleteMap.put("impower_inv_name", entityMap.get("impowerinv_name"));
							deleteMap.put("inv_id", jsonObj.get("invid"));
							updateList.add(deleteMap);
							
							insertMap.put("group_id", entityMap.get("group_id"));
							insertMap.put("hos_id", entityMap.get("hos_id"));
							insertMap.put("copy_code", entityMap.get("copy_code"));
							insertMap.put("impower_id", entityMap.get("impower_id"));
							insertMap.put("impower_code", entityMap.get("impower_code"));
							insertMap.put("impower_inv_name", entityMap.get("impower_inv_name"));
							insertMap.put("inv_id", jsonObj.get("inv_id"));
							insertList.add(insertMap);
						}
					}
				}
			}
		  
		  int state = matInvImpowerMapper.updateMatInvImpower(entityMap);
		  if(insertList.size()>0){
				matInvImpowerRelaMapper.addBatchMatInvImpowerRela(insertList);
		  }
		  if(updateList.size()>0){
				matInvImpowerRelaMapper.deleteBatchMatInvImpowerRela(updateList);
		  }
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatInvImpower\"}";

		}	
		
	}
	
	/**
	 * @Description 
	 * 删除
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatInvImpower(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matInvImpowerMapper.deleteMatInvImpower(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInvImpower\"}";

		}	
		
  }
    
	@Override
	public String deleteBatchMatInvImpower(List<Map<String, Object>> entityList)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
				matInvImpowerMapper.deleteBatchMatInvImpower(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInvImpower\"}";

			}	
	}
	
	/**
	 * @Description 
	 * 查询结果集04601 材料证件类型字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInvImpower(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatInvImpower> list = matInvImpowerMapper.queryMatInvImpower(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatInvImpower> list = matInvImpowerMapper.queryMatInvImpower(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatInvImpower queryMatInvImpowerByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matInvImpowerMapper.queryMatInvImpowerByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04601 材料证件类型字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCertType
	 * @throws DataAccessException
	*/
	@Override
	public MatInvImpower queryMatInvImpowerByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return matInvImpowerMapper.queryMatInvImpowerByUniqueness(entityMap);
	}
	public List<MatInvImpower> queryMatInvImpowerById(Map<String, Object> mapVo) throws DataAccessException{
		return matInvImpowerMapper.queryMatInvImpowerById(mapVo);
	}
	/**
	 * 查询 删除的数据是否也被引用
	 * @param entityMap
	 * @return
	 */
	public int queryDelDate(Map<String, Object> entityMap) throws DataAccessException{
		return matInvImpowerMapper.queryDelDate(entityMap);
	}




	@Override
	public String addBatchMatInvImpower(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String updateBatchMatInvImpower(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String queryMatImpowerInvList(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matInvImpowerMapper.queryMatImpowerInvList(entityMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matInvImpowerMapper.queryMatImpowerInvList(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
		}
	}
	
	/**
	 * 查询 选择的材料
	 * @param listVo
	 * @return
	 */
	@Override
	public String queryMatImpowerInvChoiceInvList(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
		
		JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
				Map<String, Object> detailMap = new HashMap<String, Object>();
			
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("inv_id", jsonObj.get("inv_id"));
				detailMap.put("inv_no", jsonObj.get("inv_no"));
				detailList.add(detailMap);
			}
		}
		
		List<Map<String, Object>> list= matInvImpowerMapper.queryMatImpowerInvChoiceInvList(detailList);
		return ChdJson.toJsonLower(list);
	}
	

	/**
	 * 根剧impower_id查询对应关系明细数据
	 */
	@Override
	public String queryMatInvImpowerDetail(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matInvImpowerRelaMapper.queryMatInvImpowerDetail(entityMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matInvImpowerRelaMapper.queryMatInvImpowerDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
		}
		
	}


	public Map<String, Object> queryMaxImpowerId(Map<String, Object> mapVo) {
		
		return matInvImpowerRelaMapper.queryMaxImpowerId(mapVo);
	}


	public Long queryMatInvImpowerNextId(Map<String, Object> mapVo) {
		
		return matInvImpowerMapper.queryMatInvImpowerNextId();
	}
	


	
	
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatInvCertRelaMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvImpowerRelaMapper;
import com.chd.hrp.mat.entity.MatInvCertRela;
import com.chd.hrp.mat.service.info.basic.MatInvCertRelaService;
import com.chd.hrp.mat.service.info.basic.MatInvImpowerRelaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 

 * @Table:
 * MAT_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matInvImpowerRelaService")
public class MatInvImpowerRelaServiceImpl implements MatInvImpowerRelaService {

	private static Logger logger = Logger.getLogger(MatInvImpowerRelaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInvCertRelaMapper")
	private final MatInvCertRelaMapper matInvCertRelaMapper = null;
	
	@Resource(name = "matInvImpowerRelaMapper")
	private final MatInvImpowerRelaMapper matInvImpowerRelaMapper = null;
    
    
	/**
	 * @Description 
	 * 添加
<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatInvCertRela(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象

		MatInvCertRela matInvCertRela = queryMatInvCertRelaByCode(entityMap);

		if (matInvCertRela != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matInvCertRelaMapper.addMatInvCertRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatInvCertRela\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加
<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatInvImpowerRela(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matInvImpowerRelaMapper.addBatchMatInvImpowerRela(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInvCertRela\"}";

		}
		
	}


	/**
	 * @Description 
	 * 删除
<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatInvCertRela(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
    		//查询 该证件是否 存在证件材料对应关系
			int count = matInvCertRelaMapper.queryIsExist(entityMap);
			if(count > 0){
				int state = matInvCertRelaMapper.deleteMatInvCertRela(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"该证件不存在材料证件对应关系.无需删除 .\",\"state\":\"true\"}";
			}
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatInvCertRela\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除
<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatInvImpowerRela(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matInvImpowerRelaMapper.deleteBatchMatInvImpowerRela(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatInvCertRela\"}";

		}	
	}

	/**
	 * @Description 
	 * 查询结果集
<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInvCertRela(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatInvCertRela> list = matInvCertRelaMapper.queryMatInvCertRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatInvCertRela> list = matInvCertRelaMapper.queryMatInvCertRela(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象
<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatInvCertRela queryMatInvCertRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matInvCertRelaMapper.queryMatInvCertRelaByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取
<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCertRela
	 * @throws DataAccessException
	*/
	@Override
	public MatInvCertRela queryMatInvCertRelaByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matInvCertRelaMapper.queryMatInvCertRelaByUniqueness(entityMap);
	}
	public List<Map<String, Object>> queryInvId(Map<String, Object> mapVo) throws DataAccessException{
		return matInvCertRelaMapper.queryInvId(mapVo);
	}
	
	/**
	 * 判断材料是否已经存在于对应关系中
	 * @param mapVo
	 * @return
	 */
	@Override
	public String existsMatInvInCert(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			String inv_ids = matInvCertRelaMapper.existsMatInvInCert(entityMap);
			if(inv_ids == null || "".equals(inv_ids)){
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"inv_ids\":\""+inv_ids+"\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 existsMatInvInCert\"}";
		}	
	}
	
	/**
	 * 添加材料  保存
	 * @param mapVo
	 * @return
	 */
	@Override
	public String addCertInv(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String,Object>> invList = new ArrayList<Map<String,Object>>();
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if(jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("cert_id", entityMap.get("cert_id"));
						detailMap.put("cert_code", entityMap.get("cert_code"));
						invList.add(detailMap);
					}
				}
			}
			
			if(invList.size()>0){
				matInvCertRelaMapper.addBatchMatInvCertRela(invList);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	
	/**
	 * 添加材料  删除
	 * @param mapVo
	 * @return
	 */
	@Override
	public String deleteBatchCertInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			matInvCertRelaMapper.deleteBatchMatInvCertRela(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	
}

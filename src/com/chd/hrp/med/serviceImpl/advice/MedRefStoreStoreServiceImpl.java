/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.advice;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.advice.MedRefStoreStoreMapper;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.service.advice.MedRefStoreStoreService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:  081207 医嘱核销二级库与大库对应关系表
 * @Table: MED_REF_STORE_STORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medRefStoreStoreService")
public class MedRefStoreStoreServiceImpl implements MedRefStoreStoreService {

	private static Logger logger = Logger.getLogger(MedRefStoreStoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medRefStoreStoreMapper")
	private final MedRefStoreStoreMapper medRefStoreStoreMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;

	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medRefStoreStoreMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medRefStoreStoreMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 保存<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String save(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//二级库ID
			if(entityMap.get("sec_store_id") == null || "".equals(entityMap.get("sec_store_id"))){
				return "{\"error\":\"二级库不能为空\",\"state\":\"false\"}";
			}
			//库房ID
			if(entityMap.get("store_id") == null || "".equals(entityMap.get("store_id"))){
				return "{\"error\":\"库房不能为空\",\"state\":\"false\"}";
			}
			
			if(entityMap.get("old_id") != null && !"".equals(entityMap.get("old_id").toString())){
				//修改数据
				medRefStoreStoreMapper.update(entityMap);
			}else{
				//新增数据
				medRefStoreStoreMapper.add(entityMap);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("添加失败");
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""
			+entityMap.get("group_id").toString()
			+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
			+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()
			+"\",\"old_id\":\""+entityMap.get("sec_store_id").toString()
			+"\"}";
	}
	
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//批量表数据
			medRefStoreStoreMapper.deleteBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("删除失败");
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}

	
	/**
	 * @Description 
	 * 库房列表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedRefStoreList(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
		}
		
		return JSON.toJSONString(medRefStoreStoreMapper.queryMedRefStoreList(entityMap, rowBounds));
	}
}

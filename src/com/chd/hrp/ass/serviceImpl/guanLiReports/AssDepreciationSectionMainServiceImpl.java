/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.guanLiReports;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.guanLiReports.AssDepreciationSectionMainMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssDepreciationSectionMain;
import com.chd.hrp.ass.entity.guanLiReports.AssResourceSet;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.guanLiReports.AssDepreciationSectionMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051101 资产月报表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assDepreciationSectionMainService")
public class AssDepreciationSectionMainServiceImpl implements AssDepreciationSectionMainService {

	private static Logger logger = Logger.getLogger(AssDepreciationSectionMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDepreciationSectionMainMapper")
	private final AssDepreciationSectionMainMapper assDepreciationSectionMainMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    


	/**
	 * @Description 
	 * 查询结果集051101 盘点单<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			//
			List<AssDepreciationSectionMain> list = null;
			if ("02".equals(entityMap.get("ass_nature"))){ 
				
				list = assDepreciationSectionMainMapper.querySpecial(entityMap);
			
				}
			if ("03".equals(entityMap.get("ass_nature"))){
				 
				list = assDepreciationSectionMainMapper.queryGeneral(entityMap);
			
				}
			if ("01".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSectionMainMapper.queryHouse(entityMap);
			
				}
			if ("04".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSectionMainMapper.queryOther(entityMap);
			
				}  
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			//
			List<AssDepreciationSectionMain> list =null;
			if ("02".equals(entityMap.get("ass_nature"))){ 
				
				list = assDepreciationSectionMainMapper.querySpecial(entityMap, rowBounds);
			
				}
			if ("03".equals(entityMap.get("ass_nature"))){
				 
				list = assDepreciationSectionMainMapper.queryGeneral(entityMap, rowBounds);
			
				}
			if ("01".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSectionMainMapper.queryHouse(entityMap, rowBounds);
			
				}
			if ("04".equals(entityMap.get("ass_nature"))){
				
				list = assDepreciationSectionMainMapper.queryOther(entityMap, rowBounds);
			
				}  
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assDepreciationSectionMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCheckMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assDepreciationSectionMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘点单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCheckMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assDepreciationSectionMainMapper.queryExists(entityMap);
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//查询
	@Override
	public String queryAssDepreciationSectionMain(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssResourceSet> list =  null ;
			
			list = assDepreciationSectionMainMapper.queryAssDepreciationSectionMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		
	}

	@Override
	public List<Map<String, Object>> queryAssDepreciationSectionPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assDepreciationSectionMainMapper.queryAssDepreciationSectionPrint(entityMap);
	
		return list;
	}

	
	
}

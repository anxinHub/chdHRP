/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.guanLiReports;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.guanLiReports.AssResourceSetMapper; 
import com.chd.hrp.ass.entity.guanLiReports.AssResourceSet;
import com.chd.hrp.ass.service.guanLiReports.AssResourceSetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050801 资产资金来源匹配报表查询
 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assResourceSetService")
public class AssResourceSetServiceImpl implements AssResourceSetService {

	private static Logger logger = Logger.getLogger(AssResourceSetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assResourceSetMapper")
	private final AssResourceSetMapper assResourceSetMapper = null;
    
	 
	/**
	 * @Description 
	 * 查询结果集050801 资产资金来源报表查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssResourceSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssResourceSet> list =  null ;
			
			list = assResourceSetMapper.queryAssResourceSet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}


	@Override
	public List<Map<String, Object>> queryAssResourceSetPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assResourceSetMapper.queryAssResourceSetPrint(entityMap);
	
		return list;
	}
		
	}
	
 
	


/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

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
import com.chd.hrp.acc.dao.wagedata.AccDeptWageItemMapper;
import com.chd.hrp.acc.service.wagedata.AccDeptWageItemService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 个人工资查询<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accDeptWageItemService")
public class AccDeptWageItemServiceImpl implements AccDeptWageItemService {

	private static Logger logger = Logger.getLogger(AccDeptWageItemServiceImpl.class);
	
	@Resource(name = "accDeptWageItemMapper")
	private final AccDeptWageItemMapper accDeptWageItemMapper = null;
    
	/**
	 * @Description 
	 * 个人工资查询<BR> 查询AccPeopleWageItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccDeptWageItem(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = accDeptWageItemMapper.queryAccDeptWageItem(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = accDeptWageItemMapper.queryAccDeptWageItem(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	public String queryAccDeptWageSum(Map<String,Object> entityMap) throws DataAccessException{
		
			List<Map<String,Object>> list = accDeptWageItemMapper.queryAccDeptWageSum(entityMap);
			
			return ChdJson.toJson(list);
		
	}
	
	public List<Map<String,Object>>  queryAccDeptWageSumPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
	       
		entityMap.put("hos_id", SessionManager.getHosId());
        
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String,Object>> list = accDeptWageItemMapper.queryAccDeptWageSum(entityMap);
		
		return list;
	
	}

	public List<Map<String,Object>> queryAccDeptWageItemPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
	       
		entityMap.put("hos_id", SessionManager.getHosId());
        
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String,Object>> list = accDeptWageItemMapper.queryAccDeptWageItem(entityMap);
		
		return list;
		
	}
	@Override
	public String queryAccDeptWageItemSum(Map<String, Object> entityMap)
		    throws DataAccessException
		  {
		    SysPage sysPage = new SysPage();

		    sysPage = (SysPage)entityMap.get("sysPage");

		    if (sysPage.getTotal() == -1)
		    {
		      List<Map<String, Object>> list = accDeptWageItemMapper.queryAccDeptWageItemSum(entityMap);

		      return ChdJson.toJsonLower(list);
		    }

		    RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		    List<Map<String, Object>>  list = accDeptWageItemMapper.queryAccDeptWageItemSum(entityMap, rowBounds);

		    PageInfo page = new PageInfo(list);

		    return ChdJson.toJsonLower(list, Long.valueOf(page.getTotal()));
		  }

		  public List<Map<String, Object>> queryAccDeptWageItemSumPrint(Map<String, Object> entityMap)
		    throws DataAccessException
		  {
		    entityMap.put("group_id", SessionManager.getGroupId());

		    entityMap.put("hos_id", SessionManager.getHosId());

		    entityMap.put("copy_code", SessionManager.getCopyCode());

		    entityMap.put("acc_year", SessionManager.getAcctYear());

		    List list = this.accDeptWageItemMapper.queryAccDeptWageItemSum(entityMap);

		    return list;
		  }
}


/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.serviceImpl.base.budgcostfasset;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.budg.dao.base.budgcostfasset.BudgAssUsageDictMapper;
import com.chd.hrp.budg.entity.BudgAssUsageDict;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgAssUsageDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050104 资产用途
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgAssUsageDictService")
public class BudgAssUsageDictServiceImpl implements BudgAssUsageDictService {

	private static Logger logger = Logger.getLogger(BudgAssUsageDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgAssUsageDictMapper")
	private final BudgAssUsageDictMapper assUsageDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssUsageDict(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050104 资产用途
		BudgAssUsageDict assUsageDict = queryAssUsageDictByCode(entityMap);

		if (assUsageDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assUsageDictMapper.addAssUsageDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050104 资产用途<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssUsageDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assUsageDictMapper.addBatchAssUsageDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssUsageDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assUsageDictMapper.updateAssUsageDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050104 资产用途<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssUsageDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assUsageDictMapper.updateBatchAssUsageDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssUsageDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assUsageDictMapper.deleteAssUsageDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050104 资产用途<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssUsageDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assUsageDictMapper.deleteBatchAssUsageDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssUsageDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssUsageDict> list = assUsageDictMapper.queryAssUsageDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssUsageDict> list = assUsageDictMapper.queryAssUsageDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050104 资产用途<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public BudgAssUsageDict queryAssUsageDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assUsageDictMapper.queryAssUsageDictByCode(entityMap);
	}
	
}

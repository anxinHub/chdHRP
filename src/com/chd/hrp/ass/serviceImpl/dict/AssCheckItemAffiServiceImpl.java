
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.serviceImpl.dict;

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
import com.chd.hrp.ass.dao.dict.AssCheckItemAffiMapper;
import com.chd.hrp.ass.entity.dict.AssCheckItemAffi;
import com.chd.hrp.ass.service.dict.AssCheckItemAffiService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050109 检查项目字典
 * @Table:
 * ASS_CHECK_ITEM_Affi
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assCheckItemAffiService")
public class AssCheckItemAffiServiceImpl implements AssCheckItemAffiService {

	private static Logger logger = Logger.getLogger(AssCheckItemAffiServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCheckItemAffiMapper")
	private final AssCheckItemAffiMapper assCheckItemAffiMapper = null;
    
	/**
	 * @Description 
	 * 添加050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssCheckItemAffi(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050109 检查项目字典
		AssCheckItemAffi assCheckItemAffi = queryAssCheckItemAffiByCode(entityMap);

		if (assCheckItemAffi != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assCheckItemAffiMapper.addAssCheckItemAffi(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050109 检查项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssCheckItemAffi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckItemAffiMapper.addBatchAssCheckItemAffi(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssCheckItemAffi(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assCheckItemAffiMapper.updateAssCheckItemAffi(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050109 检查项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssCheckItemAffi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCheckItemAffiMapper.updateBatchAssCheckItemAffi(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssCheckItemAffi(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCheckItemAffiMapper.deleteAssCheckItemAffi(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050109 检查项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssCheckItemAffi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckItemAffiMapper.deleteBatchAssCheckItemAffi(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssCheckItemAffi(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCheckItemAffi> list = assCheckItemAffiMapper.queryAssCheckItemAffi(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCheckItemAffi> list = assCheckItemAffiMapper.queryAssCheckItemAffi(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssCheckItemAffi queryAssCheckItemAffiByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckItemAffiMapper.queryAssCheckItemAffiByCode(entityMap);
	}
	
}

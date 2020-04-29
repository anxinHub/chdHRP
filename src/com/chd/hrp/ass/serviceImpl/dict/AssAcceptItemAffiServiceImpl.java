
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
import com.chd.hrp.ass.dao.dict.AssAcceptItemAffiMapper;
import com.chd.hrp.ass.entity.dict.AssAcceptItemAffi;
import com.chd.hrp.ass.service.dict.AssAcceptItemAffiService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050107 验收项目字典
 * @Table:
 * ASS_ACCEPT_ITEM_Affi
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assAcceptItemAffiService")
public class AssAcceptItemAffiServiceImpl implements AssAcceptItemAffiService {

	private static Logger logger = Logger.getLogger(AssAcceptItemAffiServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assAcceptItemAffiMapper")
	private final AssAcceptItemAffiMapper assAcceptItemAffiMapper = null;
    
	/**
	 * @Description 
	 * 添加050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssAcceptItemAffi(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050107 验收项目字典
		AssAcceptItemAffi assAcceptItemAffi = queryAssAcceptItemAffiByCode(entityMap);

		if (assAcceptItemAffi != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assAcceptItemAffiMapper.addAssAcceptItemAffi(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050107 验收项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssAcceptItemAffi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assAcceptItemAffiMapper.addBatchAssAcceptItemAffi(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssAcceptItemAffi(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assAcceptItemAffiMapper.updateAssAcceptItemAffi(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050107 验收项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssAcceptItemAffi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assAcceptItemAffiMapper.updateBatchAssAcceptItemAffi(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssAcceptItemAffi(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assAcceptItemAffiMapper.deleteAssAcceptItemAffi(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050107 验收项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssAcceptItemAffi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assAcceptItemAffiMapper.deleteBatchAssAcceptItemAffi(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssAcceptItemAffi(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssAcceptItemAffi> list = assAcceptItemAffiMapper.queryAssAcceptItemAffi(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssAcceptItemAffi> list = assAcceptItemAffiMapper.queryAssAcceptItemAffi(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssAcceptItemAffi queryAssAcceptItemAffiByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assAcceptItemAffiMapper.queryAssAcceptItemAffiByCode(entityMap);
	}
	
}

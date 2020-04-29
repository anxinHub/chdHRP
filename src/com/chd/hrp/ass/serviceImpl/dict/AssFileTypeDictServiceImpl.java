
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.serviceImpl.dict;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.AssFileTypeDictMapper;
import com.chd.hrp.ass.entity.dict.AssFileTypeDict;
import com.chd.hrp.ass.service.dict.AssFileTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050105 文档类别
 * @Table:
 * ASS_FILE_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assFileTypeDictService")
public class AssFileTypeDictServiceImpl implements AssFileTypeDictService {

	private static Logger logger = Logger.getLogger(AssFileTypeDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assFileTypeDictMapper")
	private final AssFileTypeDictMapper assFileTypeDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050105 文档类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssFileTypeDict(Map<String,Object> entityMap)throws DataAccessException{
	
				
		
		try {
			
			int state = assFileTypeDictMapper.addAssFileTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050105 文档类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssFileTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assFileTypeDictMapper.addBatchAssFileTypeDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050105 文档类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssFileTypeDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assFileTypeDictMapper.updateAssFileTypeDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050105 文档类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssFileTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assFileTypeDictMapper.updateBatchAssFileTypeDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050105 文档类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssFileTypeDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assFileTypeDictMapper.deleteAssFileTypeDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050105 文档类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssFileTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assFileTypeDictMapper.deleteBatchAssFileTypeDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050105 文档类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssFileTypeDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssFileTypeDict> list = assFileTypeDictMapper.queryAssFileTypeDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssFileTypeDict> list = assFileTypeDictMapper.queryAssFileTypeDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050105 文档类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssFileTypeDict queryAssFileTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assFileTypeDictMapper.queryAssFileTypeDictByCode(entityMap);
	}
	@Override
	public AssFileTypeDict queryAssFileTypeDictByName(Map<String, Object> entityMap) throws DataAccessException {
		return assFileTypeDictMapper.queryAssFileTypeDictByName(entityMap);
	}
	
}

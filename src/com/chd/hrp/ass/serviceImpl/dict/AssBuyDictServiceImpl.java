
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
import com.chd.hrp.ass.dao.dict.AssBuyDictMapper;
import com.chd.hrp.ass.entity.dict.AssBuyDict;
import com.chd.hrp.ass.service.dict.AssBuyDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050105 申购类别
 * @Table:
 * ASS_FILE_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assBuyDictService")
public class AssBuyDictServiceImpl implements AssBuyDictService {

	private static Logger logger = Logger.getLogger(AssBuyDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBuyDictMapper")
	private final AssBuyDictMapper assBuyDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050105 申购类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssBuyDict(Map<String,Object> entityMap)throws DataAccessException{
	
				
		
		try {
			
			int state = assBuyDictMapper.addAssBuyDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050105 申购类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssBuyDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBuyDictMapper.addBatchAssBuyDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050105 申购类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssBuyDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBuyDictMapper.updateAssBuyDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050105 申购类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssBuyDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBuyDictMapper.updateBatchAssBuyDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050105 申购类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssBuyDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBuyDictMapper.deleteAssBuyDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050105 申购类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssBuyDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBuyDictMapper.deleteBatchAssBuyDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050105 申购类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssBuyDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBuyDict> list = assBuyDictMapper.queryAssBuyDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBuyDict> list = assBuyDictMapper.queryAssBuyDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050105 申购类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssBuyDict queryAssBuyDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBuyDictMapper.queryAssBuyDictByCode(entityMap);
	}
	@Override
	public AssBuyDict queryAssBuyDictByName(Map<String, Object> entityMap) throws DataAccessException {
		return assBuyDictMapper.queryAssBuyDictByName(entityMap);
	}
	
}

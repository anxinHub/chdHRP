
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
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.AssDepreMethodDictMapper;
import com.chd.hrp.ass.entity.dict.AssDepreMethodDict;
import com.chd.hrp.ass.service.dict.AssDepreMethodDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050106 资产折旧方法字典
 * @Table:
 * ASS_DEPRE_METHOD_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assDepreMethodDictService")
public class AssDepreMethodDictServiceImpl implements AssDepreMethodDictService {

	private static Logger logger = Logger.getLogger(AssDepreMethodDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDepreMethodDictMapper")
	private final AssDepreMethodDictMapper assDepreMethodDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050106 资产折旧方法字典
		AssDepreMethodDict assDepreMethodDict = queryAssDepreMethodDictByCode(entityMap);

		if (assDepreMethodDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			assDepreMethodDictMapper.addAssDepreMethodDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050106 资产折旧方法字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssDepreMethodDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDepreMethodDictMapper.addBatchAssDepreMethodDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assDepreMethodDictMapper.updateAssDepreMethodDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050106 资产折旧方法字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssDepreMethodDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assDepreMethodDictMapper.updateBatchAssDepreMethodDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssDepreMethodDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assDepreMethodDictMapper.deleteAssDepreMethodDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050106 资产折旧方法字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssDepreMethodDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDepreMethodDictMapper.deleteBatchAssDepreMethodDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssDepreMethodDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDepreMethodDict> list = assDepreMethodDictMapper.queryAssDepreMethodDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDepreMethodDict> list = assDepreMethodDictMapper.queryAssDepreMethodDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssDepreMethodDict queryAssDepreMethodDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assDepreMethodDictMapper.queryAssDepreMethodDictByCode(entityMap);
	}
	
}

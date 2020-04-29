
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
import com.chd.hrp.ass.dao.dict.AssDeviceDictMapper;
import com.chd.hrp.ass.entity.dict.AssDeviceDict;
import com.chd.hrp.ass.service.dict.AssDeviceDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050105 设备来源
 * @Table:
 * ASS_FILE_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assDeviceDictService")
public class AssDeviceDictServiceImpl implements AssDeviceDictService {

	private static Logger logger = Logger.getLogger(AssDeviceDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDeviceDictMapper")
	private final AssDeviceDictMapper assDeviceDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050105 设备来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssDeviceDict(Map<String,Object> entityMap)throws DataAccessException{
	
				
		
		try {
			
			int state = assDeviceDictMapper.addAssDeviceDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050105 设备来源<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssDeviceDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDeviceDictMapper.addBatchAssDeviceDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050105 设备来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssDeviceDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assDeviceDictMapper.updateAssDeviceDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050105 设备来源<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssDeviceDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assDeviceDictMapper.updateBatchAssDeviceDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050105 设备来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssDeviceDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assDeviceDictMapper.deleteAssDeviceDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050105 设备来源<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssDeviceDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDeviceDictMapper.deleteBatchAssDeviceDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050105 设备来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssDeviceDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDeviceDict> list = assDeviceDictMapper.queryAssDeviceDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDeviceDict> list = assDeviceDictMapper.queryAssDeviceDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050105 设备来源<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssDeviceDict queryAssDeviceDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assDeviceDictMapper.queryAssDeviceDictByCode(entityMap);
	}
	@Override
	public AssDeviceDict queryAssDeviceDictByName(Map<String, Object> entityMap) throws DataAccessException {
		return assDeviceDictMapper.queryAssDeviceDictByName(entityMap);
	}
	
}

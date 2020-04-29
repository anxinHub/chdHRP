
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
import com.chd.hrp.ass.dao.dict.AssBusTypeDictMapper;
import com.chd.hrp.ass.entity.dict.AssBusTypeDict;
import com.chd.hrp.ass.service.dict.AssBusTypeDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050111 业务类型
 * @Table:
 * ASS_BUS_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assBusTypeDictService")
public class AssBusTypeDictServiceImpl implements AssBusTypeDictService {

	private static Logger logger = Logger.getLogger(AssBusTypeDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBusTypeDictMapper")
	private final AssBusTypeDictMapper assBusTypeDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050111 业务类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssBusTypeDict(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050111 业务类型
		AssBusTypeDict assBusTypeDict = queryAssBusTypeDictByCode(entityMap);

		if (assBusTypeDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assBusTypeDictMapper.addAssBusTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050111 业务类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssBusTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBusTypeDictMapper.addBatchAssBusTypeDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050111 业务类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssBusTypeDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBusTypeDictMapper.updateAssBusTypeDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050111 业务类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssBusTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBusTypeDictMapper.updateBatchAssBusTypeDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050111 业务类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssBusTypeDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBusTypeDictMapper.deleteAssBusTypeDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050111 业务类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssBusTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBusTypeDictMapper.deleteBatchAssBusTypeDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050111 业务类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssBusTypeDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBusTypeDict> list = assBusTypeDictMapper.queryAssBusTypeDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBusTypeDict> list = assBusTypeDictMapper.queryAssBusTypeDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050111 业务类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssBusTypeDict queryAssBusTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBusTypeDictMapper.queryAssBusTypeDictByCode(entityMap);
	}
	
}

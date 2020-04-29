
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.dict;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.ass.entity.dict.AssBusTypeDict;
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
 


public interface AssBusTypeDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050111 业务类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBusTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050111 业务类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBusTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050111 业务类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBusTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050111 业务类型<BR> 
	 * @param  entityMap
	 * @return AssBusTypeDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssBusTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050111 业务类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBusTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050111 业务类型<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBusTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050111 业务类型<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBusTypeDict> queryAssBusTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050111 业务类型<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBusTypeDict> queryAssBusTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050111 业务类型<BR> 
	 * @param  entityMap
	 * @return AssBusTypeDict
	 * @throws DataAccessException
	*/
	public AssBusTypeDict queryAssBusTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 编码或名称查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AssBusTypeDict queryAssBusTypeDictByCodeOrName(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
	
}

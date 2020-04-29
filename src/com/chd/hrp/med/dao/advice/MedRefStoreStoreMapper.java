/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.HrpMedSelect;

/**
 * 
 * @Description:  081207 医嘱核销二级库与大库对应关系表
 * @Table: MED_REF_STORE_STORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedRefStoreStoreMapper extends SqlMapper{

	/**
	 * @Description 
	 * 获取自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long querySeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int add(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int update(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 库房列表 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<HrpMedSelect> queryMedRefStoreList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}

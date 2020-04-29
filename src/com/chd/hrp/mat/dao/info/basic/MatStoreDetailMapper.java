/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatStoreDetail;
/**
 *  
 * @Description:
 * 04109 仓库对应关系明细表
 * @Table: 
 * MAT_STORE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatStoreDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatStoreDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return MatStoreDetail
	 * @throws DataAccessException
	*/
	public int updateBatchMatStoreDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatStoreDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04109 仓库对应关系明细表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatStoreDetail> queryMatStoreDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04109 仓库对应关系明细表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatStoreDetail> queryMatStoreDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04109 仓库对应关系明细表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatStoreDetail
	 * @throws DataAccessException
	*/
	public MatStoreDetail queryMatStoreDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04109 仓库对应关系明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStoreDetail
	 * @throws DataAccessException
	*/
	public MatStoreDetail queryMatStoreDetailByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询虚拟仓库设置仓库的个数
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryCountStoreDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
}

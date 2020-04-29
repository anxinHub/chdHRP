/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.protocol;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatProtocolDetail;
/**
 * 
 * @Description:
 * 04503 付款协议明细表
 * @Table:
 * MAT_PROTOCOL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatProtocolDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatProtocolDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatProtocolDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatProtocolDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return MatProtocolDetail
	 * @throws DataAccessException
	*/
	public int updateBatchMatProtocolDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatProtocolDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatProtocolDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04503 付款协议明细表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolDetail> queryMatProtocolDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04503 付款协议明细表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatProtocolDetail> queryMatProtocolDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04503 付款协议明细表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatProtocolDetail
	 * @throws DataAccessException
	*/
	public MatProtocolDetail queryMatProtocolDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04503 付款协议明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolDetail
	 * @throws DataAccessException
	*/
	public MatProtocolDetail queryMatProtocolDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}

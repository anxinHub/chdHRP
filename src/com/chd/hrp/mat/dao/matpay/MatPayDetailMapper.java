/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.matpay;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatPayDetail;
/**
 * 
 * @Description:
 * 保存一个付款单对应的发票
 * @Table:
 * MAT_PAY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPayDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatPayDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatPayDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatPayDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return MatPayDetail
	 * @throws DataAccessException
	*/
	public int updateBatchMatPayDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatPayDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatPayDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存一个付款单对应的发票<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatPayDetail> queryMatPayDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存一个付款单对应的发票<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatPayDetail> queryMatPayDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存一个付款单对应的发票<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatPayDetail
	 * @throws DataAccessException
	*/
	public MatPayDetail queryMatPayDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存一个付款单对应的发票<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPayDetail
	 * @throws DataAccessException
	*/
	public MatPayDetail queryMatPayDetailByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 根据 付款单Id 查询对应的付款单明细  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatPayDetailNew(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据 付款单Id 查询对应的付款单明细 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatPayDetailNew(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 根据 付款单ID 查询 其对应的发票ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatBillId(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
}

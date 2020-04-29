/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.medprepay;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedPrePayDetail;
/**
 * 
 * @Description:
 * 保存一个预付款单对应的入库单，及金额
 * @Table:
 * MED_PRE_PAY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedPrePayDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedPrePayDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedPrePayDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedPrePayDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return MedPrePayDetail
	 * @throws DataAccessException
	*/
	public int updateBatchMedPrePayDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedPrePayDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedPrePayDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存一个预付款单对应的入库单，及金额<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedPrePayDetail> queryMedPrePayDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存一个预付款单对应的入库单，及金额<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedPrePayDetail> queryMedPrePayDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedPrePayDetail
	 * @throws DataAccessException
	*/
	public MedPrePayDetail queryMedPrePayDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPrePayDetail
	 * @throws DataAccessException
	*/
	public MedPrePayDetail queryMedPrePayDetailByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询预付款单明细信息（多表联合查询） 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedPrePayIn(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询预付款单明细信息（多表联合查询）不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedPrePayIn(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据 预付款单ID 查询其明细中的 入库单ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedPrePayIn_id(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
}

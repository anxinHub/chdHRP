/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.storage.check;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_CHECK_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedCheckDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMedCheckDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_check_main  表 库存盘点返回 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckDetailByCheckID(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_check_main  表 库存盘点返回 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckDetailByCheckID(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 生成出入库单取盘盈明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckDetailProfitForInOut(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 生成出入库单取盘亏明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedCheckDetailLossForInOut(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 药品批次库存用于生成出库单的先进先出操作<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAffiFifoInv(Map<String,Object> entityMap) throws DataAccessException;
	
}

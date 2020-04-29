/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.affi.check;
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
 

public interface MedAffiCheckDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMedAffiCheckDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 材料批次库存用于生成出库单的先进先出操作<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAffiFifoInv(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取明细盘盈数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiCheckDetailProfitForInOut(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取明细盘亏数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiCheckDetailLossForInOut(Map<String, Object> entityMap)  throws DataAccessException;

	public List<Map<String, Object>> queryMedAffiCheckDetailByCheckID(Map<String, Object> entityMap) throws DataAccessException;

}

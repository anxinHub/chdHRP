/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.affi.tran;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedAffiTranDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_TRAN_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAffiTranDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 从表查询序列<BR>
	 * @return Long
	 * @throws DataAccessException
	 */
	public Long queryMedAffiTranDetailSeq() throws DataAccessException;
	
	/**
	 * 查询要调入确认的出库材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedAffiTranDetail> queryMedTranDetailForOutConfirm(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 查询要调入确认的入库材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedAffiTranDetail> queryMedTranDetailForInConfirm(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 修改页面加载明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiTranDetailByTranID(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedAffiTranDetailByTranID(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 代销调拨--整单调拨 组装明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiTranDetailBySingle(Map<String, Object> entityMap) throws DataAccessException;
	
}

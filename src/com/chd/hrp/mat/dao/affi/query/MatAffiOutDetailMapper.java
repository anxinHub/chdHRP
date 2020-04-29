package com.chd.hrp.mat.dao.affi.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 代销-出库明细查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAffiOutDetailMapper extends SqlMapper {
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细 查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiOutDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细 分页查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiOutDetail(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细-供应商信息 查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiOutSupMessage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 出库明细-供应商信息 分页查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiOutSupMessage(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 植入介入材料查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutImplant(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAffiOutImplant(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 材料库存分布查询(供应商)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiStockRoutingBySup(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAffiStockRoutingBySup(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	/**
	 * 入库台账查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutCertDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAffiOutCertDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	

	public List<Map<String, Object>> queryMatApplyCountPrint(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatApplyCountPrint(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 入库台账查询打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiOutCertDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAffiOutCertDetailPrint(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 代销材料 按照材料 供应商汇总
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatAffiOutInvCollection(Map<String, Object> entityMap);
	public List<Map<String, Object>> queryMatAffiOutInvCollection(Map<String, Object> entityMap, RowBounds rowBounds);
}

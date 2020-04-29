/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.dura.check;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * @Description: 耐用品科室盘点 
 * @Table: MAT_DURA_DEPT_(CHECK/CHECK_D)
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */  
 
public interface MatDuraCheckDeptMapper extends SqlMapper{

	/**
	 * @Description 
	 * 获取主表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryMainSeq() throws DataAccessException;

	/**
	 * @Description 
	 * 获取明细表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 修改主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMain(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 修改明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除主表数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMainBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteDetailBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单主表加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单明细加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;	
	
	 /**
	 * @Description 
	 * 批量审核、消审或批量<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditOrUnAuditBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量确认<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int confirmBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryMatch(Map<String,Object> entityMap) throws DataAccessException;
	
	 /**
	 * @Description 
	 * 判断是否存在不等于该状态的单据，0：不存在；1：存在<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsState(Map<String, Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量判断是否存在不等于该状态的单据，0：不存在；1：存在<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsStateBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取要确认的明细数据 
	 * @param  entityList
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDetailListForConfirm(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 获取主表数据用于打印 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMainForPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取主表数据用于批量打印 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMainForPrintTemlateBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取明细表数据用于打印 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDetailForPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 选择材料查询数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMatDuraCheckByDeptId(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraCheckByDeptId(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 组装盘点的材料
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraCheckByDeptInvDetail(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 获取盘点的主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatDuraCheckDeptMainForInOut(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatDuraCheckDeptDetailProfitForInOut(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatDuraCheckDeptDetailLossForInOut(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新盘点单的状态
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateStateBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 主页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraDeptMain(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraDeptMain(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMatInvByBalance(
			Map<String, Object> entityMap) throws DataAccessException;
}

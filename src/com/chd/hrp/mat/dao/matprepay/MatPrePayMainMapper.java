/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.matprepay;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatPrePayMain;
/**
 * 
 * @Description:
 * 保存预付款单的主要信息
 * @Table:
 * MAT_PRE_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPrePayMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return MatPrePayMain
	 * @throws DataAccessException
	*/
	public int updateBatchMatPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatPrePayMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存预付款单的主要信息<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatPrePayMain> queryMatPrePayMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集保存预付款单的主要信息<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatPrePayMain> queryMatPrePayMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存预付款单的主要信息<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatPrePayMain
	 * @throws DataAccessException
	*/
	public MatPrePayMain queryMatPrePayMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取保存预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPrePayMain
	 * @throws DataAccessException
	*/
	public MatPrePayMain queryMatPrePayMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 入库单查询 （没有被发票引用过的采购入库单）不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String,Object>> queryMatCommonIn(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 入库单查询 （没有被发票引用过的采购入库单）分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryMatCommonIn(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 入库单明细查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 入库单明细查询  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 新增时查询发票明细对应的发票主表的Id
	 * @return
	 */
	public Long queryMatPrePayMainMaxId() throws DataAccessException;
	/**
	 * 根据 入库单Id 查询入库单明细Id
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Long> queryIn_detail_id(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据 发票信息 查询 其发票号是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public long queryMatPrePayMainExists(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 多表联合查询 发票详细信息（修改页面回值用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatPrePayMainReturnUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 审核、消审
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatPrePayMainState(List<Map<String, Object>> entityList) throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryPrePayPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		
	public List<Map<String, Object>> queryPrePayPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	//入库明细表模板打印 
	public List<Map<String, Object>> queryPrePayPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
		
	
}

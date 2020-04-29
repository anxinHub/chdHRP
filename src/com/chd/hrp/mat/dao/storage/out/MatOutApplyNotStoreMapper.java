package com.chd.hrp.mat.dao.storage.out;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室申请单主表
 * @Table:
 * MAT_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatOutApplyNotStoreMapper extends SqlMapper{
	/**
	 * @Description 
	 * 获取主表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryMatApplyMainSeq() throws DataAccessException;

	/**
	 * @Description 
	 * 获取明细表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryMatApplyDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatApplyMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 修改主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatApplyMain(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 修改明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatApplyMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatApplyDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改之前删除页面移除的明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatApplyDetailForUpdate(Map<String, Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除主表数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatApplyMainBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatApplyDetailBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单主表加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryMatApplyMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单明细加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryMatApplyDetailByCode(Map<String,Object> entityMap)throws DataAccessException;	
	
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
	 * 批量发送<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBySendBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
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
	 * 仓库材料对应关系集合<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatStoreInvs(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 新增、修改操作 中途改变响应仓库时 查询询之前表格中已添加的材料 是否存在于当前选择的响应仓库中
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryStoreExistInv(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 查询结果集科室申请  主页面明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryNDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryNDetail(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	 /**
		 * @Description 
		 * 取消发送
		 * @param  List<Map<String, Object>>
		 * @return int
		 * @throws DataAccessException
		*/
		public int updateBackBySendBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.affi.check;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatAffiCheckMain;
import com.chd.hrp.mat.entity.MatCheckMain;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_AFFI_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAffiCheckMainMapper extends SqlMapper{

	/**
	 * 获取序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatAffiCheckMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_check_main  表 库存盘点返回 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiCheckMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatAffiCheckMain(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 审核
	 * @param mainList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAuditBatch(List<Map<String, Object>> mainList) throws DataAccessException;
	
	/**
	 * 根据材料查明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatAffiCheckMainByMatInv(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAffiCheckMainByMatInv(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据主键查主表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public MatAffiCheckMain queryMatAffiCheckMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改盘点单为生成状态
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateStateBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 获取选中的主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatAffiCheckMainForInOut(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAffiCheckPrintTemlateByMainBatch(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAffiCheckPrintTemlateByDetail(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryAffiCheckPrintTemlateByMain(Map<String, Object> entityMap) throws DataAccessException;
	
	//引入仓库材料
	public List<Map<String, Object>> queryMatAffiStoreInvDetail(List<Map<String, Object>> detailList) throws DataAccessException;

	

}

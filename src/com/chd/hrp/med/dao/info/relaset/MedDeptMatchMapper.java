/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.info.relaset;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedDeptMatch;
/**
 * 
 * @Description:
 * 04114 物资科室配套表
 * @Table:
 * MAT_DEPT_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedDeptMatchMapper extends SqlMapper{

	/**
	 * @Description 
	 * 物资科室配套表<BR> 删除配套表
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedDeptMatch(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 物资科室配套表<BR> 删除配套表明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMdmDetail(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 物资科室配套表<BR>分页查询 配套表明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	public List<MedDeptMatch> queryMdmDetailByCode(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 物资科室配套表<BR>查询 配套表明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	public List<MedDeptMatch> queryMdmDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

   
	public List<MedDeptMatch> queryByName(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 获得序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedDeptMatchNextval() throws DataAccessException;
	
	/**
	 * 批量更新明细表
	 * @param updateList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchDeptMatchDetail(List<Map<String, Object>> updateList) throws DataAccessException;
   
	public int queryDeptExistInv(Map<String, Object> item) throws DataAccessException;

	public List<Map<String, Object>> queryDeptInvData(List<Map<String, Object>> detailList) throws DataAccessException;
	

}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.info.relaset;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedStoreMatch;
/**
 * 
 * @Description:
 * 04113 物资仓库配套表
 * @Table:
 * MAT_STORE_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedStoreMatchMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 物资仓库配套表<BR> 查询 材料明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	public List<MedStoreMatch> querySmmDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 物资仓库配套表<BR> 分页查询 材料明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	public List<MedStoreMatch> querySmmDetailByCode(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 物资仓库配套明细表<BR>删除 配套表材料明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMsmDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 物资仓库配套明细表<BR>批量添加 配套表材料明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMsmDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	public List<MedStoreMatch> queryByName(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 获得序列号
	 * @return
	 */
	public Long queryStoreMatchNextSeq();
	
}

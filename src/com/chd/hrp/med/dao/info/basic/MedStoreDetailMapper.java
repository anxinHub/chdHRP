/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedStoreDetail;
/**
 *  
 * @Description:
 * 08109 仓库对应关系明细表
 * @Table: 
 * MED_STORE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedStoreDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedStoreDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return MedStoreDetail
	 * @throws DataAccessException
	*/
	public int updateBatchMedStoreDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedStoreDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08109 仓库对应关系明细表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedStoreDetail> queryMedStoreDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08109 仓库对应关系明细表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedStoreDetail> queryMedStoreDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取08109 仓库对应关系明细表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedStoreDetail
	 * @throws DataAccessException
	*/
	public MedStoreDetail queryMedStoreDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取08109 仓库对应关系明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreDetail
	 * @throws DataAccessException
	*/
	public MedStoreDetail queryMedStoreDetailByUniqueness(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询虚拟仓库设置仓库的个数
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryCountStoreDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
}

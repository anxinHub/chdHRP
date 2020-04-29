/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.account.report;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedShowDetail;
import com.chd.hrp.med.entity.MedShowSet;
/**
 * 
 * @Description:
 * 081101 药品库存汇总表设置
 * @Table:
 * MED_SHOW_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAccountReportInvStockSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 获取对象结果集<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的条件
	 * @return int(返回值大于0则存在重复)
	 * @throws DataAccessException
	*/
	public int queryMedShowSetExists(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除081101 药品库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedShowSetBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集081101 药品库存汇总表设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedShowSet> queryMedShowSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集081101 药品库存汇总表设置<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedShowSet> queryMedShowSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取081101 药品库存汇总表设置<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedShowSet
	 * @throws DataAccessException
	*/
	public MedShowSet queryMedShowSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加081101 药品库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedShowDetail(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除081101 药品库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedShowDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除081101 药品库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedShowDetailBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集081101 药品库存汇总表设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedShowDetail> queryMedShowDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
}

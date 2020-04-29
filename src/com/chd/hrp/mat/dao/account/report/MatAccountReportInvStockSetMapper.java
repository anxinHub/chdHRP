/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.account.report;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatShowDetail;
import com.chd.hrp.mat.entity.MatShowSet;
/**
 * 
 * @Description:
 * 041101 材料库存汇总表设置
 * @Table:
 * MAT_SHOW_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAccountReportInvStockSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 获取对象结果集<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的条件
	 * @return int(返回值大于0则存在重复)
	 * @throws DataAccessException
	*/
	public int queryMatShowSetExists(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加041101 材料库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新041101 材料库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除041101 材料库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatShowSet(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除041101 材料库存汇总表设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatShowSetBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集041101 材料库存汇总表设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatShowSet> queryMatShowSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集041101 材料库存汇总表设置<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatShowSet> queryMatShowSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取041101 材料库存汇总表设置<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatShowSet
	 * @throws DataAccessException
	*/
	public MatShowSet queryMatShowSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加041101 材料库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatShowDetail(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除041101 材料库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatShowDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除041101 材料库存汇总表设置明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatShowDetailBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集041101 材料库存汇总表设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatShowDetail> queryMatShowDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
}

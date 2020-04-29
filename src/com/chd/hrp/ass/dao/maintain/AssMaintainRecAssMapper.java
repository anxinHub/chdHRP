/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.maintain;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecAss;
/**
 * 
 * @Description:
 * 051203 保养记录资产明细
 * @Table:
 * ASS_MAINTAIN_REC_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMaintainRecAssMapper extends SqlMapper{  
	/**
	 * @Description 
	 * 添加051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMaintainRecAss(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMaintainRecAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMaintainRecAss(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return AssMaintainRecAss
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainRecAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMaintainRecAss(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMaintainRecAss(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051203 保养记录资产明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainRecAss> queryAssMaintainRecAss(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051203 保养记录资产明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainRecAss> queryAssMaintainRecAss(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录资产明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMaintainRecAss
	 * @throws DataAccessException
	*/
	public AssMaintainRecAss queryAssMaintainRecAssByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainRecAss
	 * @throws DataAccessException
	*/
	public AssMaintainRecAss queryAssMaintainRecAssByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainRecAss>
	 * @throws DataAccessException
	*/
	public List<AssMaintainRecAss> queryAssMaintainRecAssExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssMaintainRecAss> queryByAssMaintainRecAssId(Map<String,Object> entityMap)throws DataAccessException;
	
	public Long queryCurrentSequence()throws DataAccessException;

	public List<Map<String, Object>> queryAssMaintainRecAssByUpdate(
			Map<String, Object> buildMapVo1);
}

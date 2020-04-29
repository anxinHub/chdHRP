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
import com.chd.hrp.ass.entity.maintain.AssMaintainRec;
/**
 * 
 * @Description:
 * 051203 保养记录
 * @Table:
 * ASS_MAINTAIN_REC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMaintainRecMapper extends SqlMapper{ 
	/**
	 * @Description 
	 * 添加051203 保养记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051203 保养记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMaintainRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051203 保养记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051203 保养记录<BR> (审核)
	 * @param  entityMap
	 * @return AssMaintainRec
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051203 保养记录<BR> (消审)
	 * @param  entityMap
	 * @return AssMaintainRec
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainRecBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051203 保养记录<BR> (终止)
	 * @param  entityMap
	 * @return AssMaintainRec
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainRecStop(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除051203 保养记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051203 保养记录<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMaintainRec(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051203 保养记录<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainRec> queryAssMaintainRec(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051203 保养记录<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainRec> queryAssMaintainRec(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMaintainRec
	 * @throws DataAccessException
	*/
	public AssMaintainRec queryAssMaintainRecByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainRec
	 * @throws DataAccessException
	*/
	public AssMaintainRec queryAssMaintainRecByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainRec>
	 * @throws DataAccessException
	*/
	public List<AssMaintainRec> queryAssMaintainRecExists(Map<String,Object> entityMap)throws DataAccessException;
	public Long queryCurrentSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集卡片保养记录<BR>全部
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainRec> queryAssMaintainRecCard(Map<String, Object> entityMap);
	/**
	 * @Description 
	 * 查询结果集卡片 保养记录<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainRec> queryAssMaintainRecCard(Map<String, Object> entityMap, RowBounds rowBounds);

	public List<Map<String, Object>> queryAssMainRecPlanByMainBatch(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssMainRecPlanByDetail(
			Map<String, Object> entityMap);

	public Map<String, Object> queryAssMainRecPlanByMain(
			Map<String, Object> entityMap);

	public List<String> queryAssMainRecState(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public int queryAssMainRecStateList(List<Map<String, Object>> listVo);
	
}

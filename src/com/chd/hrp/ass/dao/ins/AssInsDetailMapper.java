/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.ins;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.ins.AssInsDetail;
import com.chd.hrp.ass.entity.ins.AssInsMain;
/**
 * 
 * @Description:
 * 050601 资产安装明细
 * @Table:
 * ASS_INS_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssInsDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssInsDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssInsDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssInsDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return AssInsDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssInsDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssInsDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssInsDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050601 资产安装明细<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInsDetail> queryAssInsDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050601 资产安装明细<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInsDetail> queryAssInsDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装明细<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssInsDetail
	 * @throws DataAccessException
	*/
	public AssInsDetail queryAssInsDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssInsDetail
	 * @throws DataAccessException
	*/
	public AssInsDetail queryAssInsDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssInsDetail>
	 * @throws DataAccessException
	*/
	public List<AssInsDetail> queryAssInsDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAssInsDetailByUpdate(Map<String,Object> entityMap) throws DataAccessException;

	//增加关系表
	public int initAssInsDetailtBid(Map<String, Object> entityMap)throws DataAccessException;
	
	//删除关系表
	public int deleteBatchAssInsContractMap(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<AssInsDetail> queryByAssInsId(Map<String, Object> entityMap)throws DataAccessException;
	
	
	public List<AssInsDetail> queryAssInsDetailByAccept(Map<String, Object> entityMap)throws DataAccessException;
	
	

    
}

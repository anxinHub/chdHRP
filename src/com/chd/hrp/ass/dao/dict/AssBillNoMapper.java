
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.dict;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.ass.entity.dict.AssBillNo;
/**
 * 
 * @Description:
 * 050110 资产单据号规则设置
 * @Table:
 * ASS_BILL_NO
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
public interface AssBillNoMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBillNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBillNo(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 更新050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAssBillNoMaxNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	*/
	public int updateBatchAssBillNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBillNo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050110 资产单据号规则设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBillNo> queryAssBillNo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050110 资产单据号规则设置<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBillNo> queryAssBillNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	*/
	public AssBillNo queryAssBillNoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	*/
	public int updateAssBillNoManageMaxNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	*/
	public Map<String,Object>  queryAssBillNoManageByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加050110 资产单据号规则设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertAssBillNoManage(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 判断业务表中是否已经有单据生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAssBillNoIsExists(Map<String,Object> entityMap)throws DataAccessException;
	
	
}

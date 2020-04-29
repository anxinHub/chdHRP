/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.accept;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptMain;
/**
 * 
 * @Description:
 * 050601 资产验收主表
 * @Table:
 * ASS_ACCEPT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssAcceptMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssAcceptMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssAcceptMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssAcceptMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return AssAcceptMain
	 * @throws DataAccessException
	*/
	public int updateBatchAssAcceptMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssAcceptMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssAcceptMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public Long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050601 资产验收主表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptMain> queryAssAcceptMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050601 资产验收主表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptMain> queryAssAcceptMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产验收主表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssAcceptMain
	 * @throws DataAccessException
	*/
	public AssAcceptMain queryAssAcceptMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产验收主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssAcceptMain
	 * @throws DataAccessException
	*/
	public AssAcceptMain queryAssAcceptMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssAcceptMain> queryAssAcceptMainExists(
			Map<String, Object> entityMap)throws DataAccessException;

	public int updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int initContract(Map<String, Object> entityMap)throws DataAccessException;

	public AssAcceptMain queryAssAcceptMainUniqueness(
			Map<String, Object> entityMap)throws DataAccessException;
	//资产验收打印主表
	public List<Map<String, Object>> queryAssAcceptBatch(
			Map<String, Object> map);
	//资产验收从表
	public List<Map<String, Object>> queryAssAccept_detail(
			Map<String, Object> map);

	public Map<String, Object> querAssAcceptByPrint(Map<String, Object> map);

	public List<String> queryAcceptMainState(Map<String, Object> mapVo);
	
	public int updateAcceptMainInState(Map<String, Object> entityMap)throws DataAccessException;
	
}

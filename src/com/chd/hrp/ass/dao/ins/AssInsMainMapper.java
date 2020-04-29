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
import com.chd.hrp.ass.entity.ins.AssInsMain;
/**
 * 
 * @Description:
 * 050601 资产安装主表
 * @Table:
 * ASS_INS_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssInsMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssInsMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssInsMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssInsMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新主表安装费用
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateInsMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return AssInsMain
	 * @throws DataAccessException
	*/
	public int updateBatchAssInsMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssInsMain(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssInsMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050601 资产安装主表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInsMain> queryAssInsMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050601 资产安装主表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInsMain> queryAssInsMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装主表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssInsMain
	 * @throws DataAccessException
	*/
	public AssInsMain queryAssInsMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssInsMain
	 * @throws DataAccessException
	*/
	public AssInsMain queryAssInsMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssInsMain>
	 * @throws DataAccessException
	*/
	public List<AssInsMain> queryAssInsMainExists(Map<String,Object> entityMap)throws DataAccessException;

	public int updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public Long queryAssInsMainSequence()throws DataAccessException;

	/**
	 * 资产打印主表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryassInsMainBatch(
			Map<String, Object> map);
	/**
	 * 资产打印从表表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryassInsMain_detail(
			Map<String, Object> map);

	public Map<String, Object> querassInsMainByPrint(Map<String, Object> map);

	public List<String> queryInsMainState(Map<String, Object> mapVo);
	
	
}

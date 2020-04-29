/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.hr.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrAccPara;


/**
* @Title. @Description.
* 系统参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HrAccParaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 系统参数<BR> 添加AccPara
	 * @param AccPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量添加AccPara
	 * @param  AccPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询AccPara分页
	 * @param  entityMap RowBounds
	 * @return List<AccPara>
	 * @throws DataAccessException
	*/
	public List<HrAccPara> queryAccPara(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 系统参数<BR> 查询AccPara所有数据
	 * @param  entityMap
	 * @return List<AccPara>
	 * @throws DataAccessException
	*/
	public List<HrAccPara> queryAccPara(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询AccParaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrAccPara queryAccParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 删除AccPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量删除AccPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccPara(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 系统参数<BR> 更新AccPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量更新AccPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 是否允许批量核销
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public HrAccPara queryAccPartVer(Map<String, Object> mapVo) throws DataAccessException;
}

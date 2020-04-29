/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccTermendTemplateDept;

/**
* @Title. @Description.
* 期末调汇科室比例<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTermendTemplateDeptMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 期末调汇科室比例<BR> 添加
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int saveAccTermendTemplateDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇科室比例<BR> 查询分页
	 * @param  entityMap RowBounds
	 * @return List<AccTermendTemplateDept>
	 * @throws DataAccessException
	*/
	public List<AccTermendTemplateDept> queryAccTermendTemplateDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 期末调汇科室比例<BR> 查询所有数据
	 * @param  entityMap
	 * @return List<AccTermendTemplateDept>
	 * @throws DataAccessException
	*/
	public List<AccTermendTemplateDept> queryAccTermendTemplateDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末调汇科室比例<BR> 批量删除
	 * @param  List<entityMap> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTermendTemplateDept(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 批量添加
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccTermendTemplateDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 根据模板ID和科室ID批量删除科室数据
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTermendTemplateDeptForAdd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 根据模板ID删除所有数据
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTermendTemplateDeptForImport(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 提取各科室收入
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccTermendTemplateDeptIncom(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 提取总收入
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public Double queryAccTermendTemplateDeptIncomSum(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 期末调汇科室比例<BR> 根据主键查询所有数据
	 * @param  entityMap
	 * @return List<AccTermendTemplateDept>
	 * @throws DataAccessException
	*/
	public List<AccTermendTemplateDept> queryAccTermendTemplateDeptByCode(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 期末调汇科室比例<BR> 根据主键查询科室比例合计
	 * @param  entityMap
	 * @return List<AccTermendTemplateDept>
	 * @throws DataAccessException
	*/
	public Double queryDeptSumByCode(Map<String,Object> entityMap) throws DataAccessException;

	public Double queryAccTermendTemplateDeptIncomSumAcc(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAccTermendTemplateDeptIncomAcc(Map<String, Object> entityMap);
}

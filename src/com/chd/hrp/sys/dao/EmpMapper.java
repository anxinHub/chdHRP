/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.Emp;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface EmpMapper extends SqlMapper{
	
	/**
	 * @Description 查询刚查询序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 添加Emp
	 * @param Emp entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Emp
	 * @param  Emp entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Emp分页
	 * @param  entityMap RowBounds
	 * @return List<Emp>
	 * @throws DataAccessException
	*/
	public List<Emp> queryEmp(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Emp所有数据
	 * @param  entityMap
	 * @return List<Emp>
	 * @throws DataAccessException
	*/
	public List<Emp> queryEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询EmpByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Emp queryEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Emp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Emp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Emp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新EmpName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateEmpName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新EmpCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateEmpCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询序列
	 * @param 
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryEmpSeqNextVal()throws DataAccessException;
	
	/**
	 * @Description 批量更新Emp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 查询当前集团、医院下 职工的最大排序号
	 * @param entityMap
	 * @return
	 */
	public Emp queryMaxEmp_sort(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询当前集团、医院下 与输入职工 排序号 相同的职工记录 （判断排序号是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Emp queryEmp_sort(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 添加、修改时 根据输入的 职工编码查询 当前集团、医院 下  与其相同的 职工记录 （判断职工编码是否存在）
	 * @param entityMap
	 * @return
	 */
	public Emp queryEmpById(Map<String, Object> entityMap);
	
	
	public int addBatchHosEmp(List<Map<String,Object>> entityList)throws DataAccessException;

	public abstract int addEmpChange(Map<String, Object> paramMap)
		    throws DataAccessException;
	
	public abstract List<Emp> queryEmpChangeRemark(Map<String, Object> paramMap)
		    throws DataAccessException;
}

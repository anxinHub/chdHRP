package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmp;
import com.chd.hrp.hpm.entity.AphiEmpDict;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiEmpDictMapper  extends SqlMapper{

	/**
	 * 
	 */
	public int addAphiEmpDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8801 职工字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAphiEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 *  批量添加职工
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchAphiEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 *  查询序列
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAphiEmpDictSeqNextVal()throws DataAccessException;
	
	/**
	 *  修改停用状态
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAphiEmpDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 *  查询所有集合
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<AphiEmpDict> queryAphiEmpDictList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 *  查询当前职工最大变更号
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryAphiEmpDictMaxNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 *  根据编码查询当前在用的职工
	 * @param entityMap
	 * @return AphiEmpDict
	 * @throws DataAccessException
	 */
	public AphiEmpDict queryAphiEmpDictByCode(Map<String,Object> entityMap)throws DataAccessException;
}

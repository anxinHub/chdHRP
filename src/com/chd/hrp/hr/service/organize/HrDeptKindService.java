package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HrDeptKind;

public interface HrDeptKindService {
     /**
      * 增加
      * @param mapVo
      * @return
      */
	String addDeptKind(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 调转修改页面
     * @param mapVo
     * @return
     */
	HrDeptKind queryByCode(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 修改保存
	 * @param mapVo
	 * @return
	 */
	String deptKindUpate(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询所有部门
	 * @param page
	 * @return
	 */
	String query(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 删除部门
	 * @param listVo
	 * @return
	 */

	String deleteDeptKind(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * 查找部门
	 * @param listVo
	 * @return
	 */
	public String queryDeptKindDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public HrDeptKind queryDeptKindByCode(Map<String,Object> entityMap)throws DataAccessException;

}

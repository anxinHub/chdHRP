package com.chd.hrp.hr.service.record;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.record.HosEmpKind;

/**
 * 
 * @Description: 人员分类
 * @Table: HOS_EMP_KIND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface HosEmpKindService {
	/**
	 * 删除
	 * 
	 * @param listVo
	 * @return
	 */
	String deleteBatchHosEmpKind(List<HosEmpKind> listVo) throws DataAccessException;

	/**
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryHosEmpKinkTree(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 增加人员分类
	 * 
	 * @param mapVo
	 * @return
	 */
	String add(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 修改查询
	 * 
	 * @param mapVo
	 * @return
	 */
	HosEmpKind queryByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 修改人员分类
	 * 
	 * @param mapVo
	 * @return
	 */
	String update(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询人员分类
	 * 
	 * @param page
	 * @return
	 */
	String query(Map<String, Object> page) throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询职工分类（没有停用的）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryEmpKind(Map<String, Object> mapVo) throws DataAccessException;
}

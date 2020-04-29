package com.chd.hrp.hr.service.organize;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrDeptViewService {
    /**
     * 查询部门架构图设置
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryDeptViewOrgSet(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询部门架构图
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryDeptViewOrg(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 保存部门架构图设置
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String addDeptOrgSet(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询右键菜单内容
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryDeptViewOrgMenu(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询科室主任照片
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryDeptViewOrgImg(Map<String, Object> mapVo)throws DataAccessException;

}

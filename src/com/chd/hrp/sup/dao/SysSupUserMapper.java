/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sup.entity.SysSupUser;
 
/**
 * @Description: 供应商管理用户
 * @Table: SYS_SUP_USER
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface SysSupUserMapper extends SqlMapper {

	/**
	 * @Description 查询供应商
	 * @param entityMap
	 *            RowBounds
	 * @return List<DeptKind>
	 * @throws DataAccessException
	 */
	public List<?> querySupDict(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateSysSupUserPwdMap(Map<String, Object> entityMap) throws DataAccessException;

	public int updateSysSupUserPwd(List<Map<String, Object>> entityMap) throws DataAccessException;

	public SysSupUser queryByCodeSupId(Map<String, Object> entityMap) throws DataAccessException;
}

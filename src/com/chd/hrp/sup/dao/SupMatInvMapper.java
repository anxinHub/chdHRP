/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description: 100003 物资材料表
 * @Table: SUP_MAT_INV
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface SupMatInvMapper extends SqlMapper {

	// 判断材料名称是否重复
	public int queryExistsByInvName(Map<String, Object> entityMap) throws DataAccessException;
	
	public <T> T queryMatInvByCode(Map<String,Object> entityMap)throws DataAccessException;
}

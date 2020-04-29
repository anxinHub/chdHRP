/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 代码项外部引用列视图
 * @Table:
 * HR_FIIED_VIEW
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrFiiedViewMapper extends SqlMapper{
	List<Map<String,Object>> queryDictCustomSql(String sql) throws DataAccessException;

	List<Map<String, Object>> queryCount(@Param("sql") String cite_sql);
}

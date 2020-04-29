/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.depre.acc;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 资产折旧_所有性质
 * @View:
 * V_ASS_DEPRE_ACC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDepreAccAllMapper extends SqlMapper{
	List<Map<String, Object>> queryAssDepreAccInfo(Map<String,Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDepreAccInfo(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}

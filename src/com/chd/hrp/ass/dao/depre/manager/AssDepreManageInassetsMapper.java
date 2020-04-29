/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.depre.manager;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 资产折旧_其他无形资产
 * @Table:
 * ASS_DEPRE_MANAGE_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDepreManageInassetsMapper extends SqlMapper{
	List<Map<String, Object>> queryByAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.depre.acc;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 资产摊销_土地
 * @Table:
 * ASS_DEPRE_ACC_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssDepreAccInitLandMapper extends SqlMapper{

	int queryCountByCode(Map<String, Object> entityMap)throws DataAccessException;
	
}
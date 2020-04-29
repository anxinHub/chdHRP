
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.med.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:
 * MED_PAY_TYPE
 * @Table:
 * MED_PAY_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedPayTypeMapper extends SqlMapper{
	/**
	 * 继承
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedPayTypeByExtend(Map<String, Object> entityMap) throws DataAccessException;
	
	
}

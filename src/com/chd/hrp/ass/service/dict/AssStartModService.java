package com.chd.hrp.ass.service.dict;

import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2016年7月5日 下午5:31:57
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: BELL
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */
public interface AssStartModService {
	public String queryMod(Map<String,Object> entityMap) throws DataAccessException;
	public String addModStart(Map<String,Object> entityMap) throws DataAccessException;
}

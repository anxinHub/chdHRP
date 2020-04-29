package com.chd.hrp.webservice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2017年2月27日 下午1:44:32
*  @Company: 杭州亦童科技有限公司
 * @网站：www.e-tonggroup.com
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface WebServiceMapper extends SqlMapper{
	//返回错误
	public Map<String,Object> hrpCommIOF(Map<String,Object> entityMap) throws  DataAccessException;
	//返回动态游标
	public List<Map<String,Object>> hrpCommGroupIOF(Map<String,Object> entityMap) throws  DataAccessException;

}

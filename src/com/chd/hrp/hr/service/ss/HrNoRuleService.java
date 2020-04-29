package com.chd.hrp.hr.service.ss;

import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description.
 * @author LFH
 * @version: 1.0
 */

//service 层接口
public interface HrNoRuleService{
	
	//查询  对应的~Impl中实现具体方法
	public String queryHrNoRule(Map<String, Object> page) throws DataAccessException;
	
	//更新
//	public String updataHrNoRule(Map<String, Object> mapVo) throws DataAccessException;
	
	//保存
	public String saveHrNoRule(List<Map<String, Object>> listVo) throws DataAccessException;
	
	//删除
	public String deleteHrNoRule(List<Map<String, Object>> listVo) throws DataAccessException;

	//新增
	public String addHrNoRule(Map<String, Object> mapVo) throws DataAccessException;
	

}

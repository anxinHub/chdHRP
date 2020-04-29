/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.autovouch.accpubCost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccPersonCollectService {
	//添加保存方法
	public String addAccPersonCollect(Map<String,Object> entityMap)throws DataAccessException;
	
	//更新保存方法
	public String updateAccPersonCollect(Map<String,Object> entityMap)throws DataAccessException;
	
	//审核方法
	public String updateAccPersonState(Map<String,Object> entityMap)throws DataAccessException;
	
	//继承上月方法
	public String updateAccPersonDataFromLastMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	//采集系统数据方法
	public String insertAccSysPerson(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询方法
	public String queryAccPersonCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	//打印的查询方法
	public List<Map<String, Object>> queryAccPersonPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询总人数方法
	public String queryTotalNum(Map<String,Object> entityMap) throws DataAccessException;
	
	//根据dept_id查询
	public Map<String, Object> queryAccPersonById(Map<String,Object> entityMap) throws DataAccessException;
	
	//删除方法
	public String deletePersonCollect(List<Map<String, Object>> list) throws DataAccessException;
	
	//导入方法
	public String importAccPersonCollect(Map<String, Object> mapVo);

}

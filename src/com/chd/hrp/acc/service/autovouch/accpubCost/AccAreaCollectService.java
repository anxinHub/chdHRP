/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.autovouch.accpubCost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccAreaCollectService {
	//添加保存方法
	public String addAccAreaCollect(Map<String,Object> entityMap)throws DataAccessException;
	
	//更新保存方法
	public String updateAccAreaCollect(Map<String,Object> entityMap)throws DataAccessException;
	
	//审核方法
	public String updateAccAreaState(Map<String,Object> entityMap)throws DataAccessException;
	
	//继承上月方法
	public String updateAccAreaDataFromLastMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询方法
	public String queryAccAreaCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	//打印的查询方法
	public List<Map<String, Object>> queryAccAreaPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询总人数方法
	public String queryTotalArea(Map<String,Object> entityMap) throws DataAccessException;
	
	//根据dept_id查询
	public Map<String, Object> queryAccAreaById(Map<String,Object> entityMap) throws DataAccessException;
	
	//删除方法
	public String deleteAreaCollect(List<Map<String, Object>> list) throws DataAccessException;
	
	//导入方法
	public String importAccAreaCollect(Map<String, Object> mapVo);

}

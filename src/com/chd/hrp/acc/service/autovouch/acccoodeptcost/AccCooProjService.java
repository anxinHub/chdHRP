/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.autovouch.acccoodeptcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccCooProjService {
	
	//更新方法
	public String updateAccCooProj(Map<String,Object> mapVo)throws DataAccessException;

	//保存方法
	public String saveAccCooProj(Map<String, Object> mapVo) throws DataAccessException;
	
	//添加页面查询方法
	public String queryAccCooProjDetail(Map<String, Object> mapVo) throws DataAccessException;
	
	//添加页面查询方法
	public String queryAccCooProjObj(Map<String, Object> mapVo) throws DataAccessException;
	
	//主页面查询方法
	public String queryAccCooProj(Map<String, Object> mapVo) throws DataAccessException;
	
	//打印的查询方法
	public List<Map<String, Object>> queryAccProjPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//根据bas_code查询
	public Map<String, Object> queryAccProjByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	//删除方法
	public String deleteAccCooProj(List<Map<String, Object>> list) throws DataAccessException;
	
	//导入方法
	public String importAccCooProj(Map<String, Object> mapVo) throws DataAccessException;
	
	public Map<String, Object> queryAccCooProjDetailPrint(Map<String, Object> mapVo);
}

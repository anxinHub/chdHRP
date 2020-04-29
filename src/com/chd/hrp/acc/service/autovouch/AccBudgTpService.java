/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.autovouch;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 平行记账模板<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBudgTpService {
	
	//列表查询
	public String queryAccBudgTp(Map<String,Object> entityMap) throws DataAccessException;
	
	//精准查询
	public Map<String, Object> queryMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	public String queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	//保存
	public Map<String, Object> saveAccBudgTp(Map<String,Object> entityMap)throws DataAccessException;
	
	//删除
	public Map<String, Object> deleteAccBudgTp(Map<String, Object> entityMap)throws DataAccessException;

	//查询科目
	public String querySubjSelect(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询用户
	public String queryUserSelect(Map<String, Object> entityMap) throws DataAccessException;

	String queryDetailByCodeVouch(Map<String, Object> entityMap)
			throws DataAccessException;

	Map<String, Object> saveAccBudgTpByVouch(Map<String, Object> entityMap)
			throws DataAccessException;
	
	//打印
	public List<Map<String, Object>> queryAccBudgTpPrint(Map<String, Object> entityMap) throws DataAccessException;
}

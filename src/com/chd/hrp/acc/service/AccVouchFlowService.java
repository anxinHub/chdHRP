/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 凭证制单流程<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchFlowService {
	
	//列表查询
	public String queryAccVouchFlow(Map<String,Object> entityMap) throws DataAccessException;

	//保存
	public Map<String, Object> saveAccVouchFlow(Map<String,Object> entityMap)throws DataAccessException;
	
}

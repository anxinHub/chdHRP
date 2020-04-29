/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.budg.service.budgsysset;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Rules;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgNoRulesService {

	public String updateBatchRules(List<Map<String, Object>> listVo) throws DataAccessException;

	public String queryRules(Map<String, Object> page) throws DataAccessException;

	public String updateRules(Map<String, Object> mapVo) throws DataAccessException;
}

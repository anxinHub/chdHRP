package com.chd.hrp.htcg.service.calculation;

import java.util.Map;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: ChenJiCheng
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
import org.springframework.dao.DataAccessException;

public interface HtcgDeptDrgsCostQueryService {

	public String queryHtcgDeptDrgsCostQuery(Map<String, Object> entityMap) throws DataAccessException; 

}

/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccVouch;

/**
* @Title. @Description.
* 凭证主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchStatisticsService {

	/**
	 * @Description 
	 * 凭证主表<BR> 查询AccVouch出纳相关数据分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccVouchStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccVouchStatisticsPrint(Map<String,Object> entityMap) throws DataAccessException;
}

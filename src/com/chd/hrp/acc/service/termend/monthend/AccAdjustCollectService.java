/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.termend.monthend;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 期末调汇<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccAdjustCollectService {

	/**
	 * @Description 
	 * 期末调汇<BR> 添加凭证
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccAdjustCollectVouch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 期末调汇<BR> 选择币种带出汇率信息
	 * @param  map 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccAdjustCollectCurRateByCode(Map<String, Object> map)throws DataAccessException;
}

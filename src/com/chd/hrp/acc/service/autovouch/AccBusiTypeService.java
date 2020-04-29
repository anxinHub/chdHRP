/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.autovouch;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccBudgRange;
import com.chd.hrp.acc.entity.autovouch.AccBusiType;

/**
* @Title. @Description.
* 账龄区间表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBusiTypeService {

	/**
	 * 查询业务类型
	 * 不分页
	 * 
	 * */
	public String queryAccBusiType(Map<String, Object> entityMap)throws DataAccessException;

	
	/**
	 * 更改业务类型是否自动凭证
	 * 
	 * */
	public String updateAccBusiTypeForIsVouch(Map<String, Object> entityMap)throws DataAccessException;
	
}

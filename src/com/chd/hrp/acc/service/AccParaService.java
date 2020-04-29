/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccPara;

/**
* @Title. @Description.
* 系统参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccParaService {

	
	/**
	 * @Description 
	 * 系统参数<BR> 查询AccPara分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccPara(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量更新AccPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccPara(Map<String,Object> mapVo)throws DataAccessException;
	
	
	
}

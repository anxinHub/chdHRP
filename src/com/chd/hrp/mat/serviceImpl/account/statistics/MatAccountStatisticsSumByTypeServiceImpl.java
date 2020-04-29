/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.statistics;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.statistics.MatAccountStatisticsSumByTypeMapper;
import com.chd.hrp.mat.service.account.statistics.MatAccountStatisticsSumByTypeService;

/**
 * 
 * @Description:
 * 收发结存汇总表(类别)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Service("matAccountStatisticsSumByTypeService")
public class MatAccountStatisticsSumByTypeServiceImpl implements MatAccountStatisticsSumByTypeService {

	private static Logger logger = Logger.getLogger(MatAccountStatisticsSumByTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountStatisticsSumByTypeMapper")
	private final MatAccountStatisticsSumByTypeMapper matAccountStatisticsSumByTypeMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountStatisticsSumByType(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountStatisticsSumByTypeMapper.queryMatAccountStatisticsSumByType(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}

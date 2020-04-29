/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.statistics;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.statistics.MedAccountStatisticsSumByTypeMapper;
import com.chd.hrp.med.service.account.statistics.MedAccountStatisticsSumByTypeService;

/**
 * 
 * @Description:
 * 收发结存汇总表(类别)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Service("medAccountStatisticsSumByTypeService")
public class MedAccountStatisticsSumByTypeServiceImpl implements MedAccountStatisticsSumByTypeService {

	private static Logger logger = Logger.getLogger(MedAccountStatisticsSumByTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountStatisticsSumByTypeMapper")
	private final MedAccountStatisticsSumByTypeMapper medAccountStatisticsSumByTypeMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountStatisticsSumByType(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = medAccountStatisticsSumByTypeMapper.queryMedAccountStatisticsSumByType(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}

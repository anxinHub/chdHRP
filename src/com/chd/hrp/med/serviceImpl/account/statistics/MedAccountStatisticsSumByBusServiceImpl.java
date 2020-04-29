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
import com.chd.hrp.med.dao.account.statistics.MedAccountStatisticsSumByBusMapper;
import com.chd.hrp.med.service.account.statistics.MedAccountStatisticsSumByBusService;

/**
 * 
 * @Description:
 * 收发结存汇总表(业务类型)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Service("medAccountStatisticsSumByBusService")
public class MedAccountStatisticsSumByBusServiceImpl implements MedAccountStatisticsSumByBusService {

	private static Logger logger = Logger.getLogger(MedAccountStatisticsSumByBusServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountStatisticsSumByBusMapper")
	private final MedAccountStatisticsSumByBusMapper medAccountStatisticsSumByBusMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountStatisticsSumByBus(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = medAccountStatisticsSumByBusMapper.queryMedAccountStatisticsSumByBus(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}

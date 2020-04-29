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
import com.chd.hrp.med.dao.account.statistics.MedAccountStatisticsSumByStoreMapper;
import com.chd.hrp.med.service.account.statistics.MedAccountStatisticsSumByStoreService;

/**
 * 
 * @Description:
 * 收发结存汇总表(仓库)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountStatisticsSumByStoreService")
public class MedAccountStatisticsSumByStoreServiceImpl implements MedAccountStatisticsSumByStoreService {

	private static Logger logger = Logger.getLogger(MedAccountStatisticsSumByStoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountStatisticsSumByStoreMapper")
	private final MedAccountStatisticsSumByStoreMapper medAccountStatisticsSumByStoreMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountStatisticsSumByStore(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = medAccountStatisticsSumByStoreMapper.queryMedAccountStatisticsSumByStore(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}

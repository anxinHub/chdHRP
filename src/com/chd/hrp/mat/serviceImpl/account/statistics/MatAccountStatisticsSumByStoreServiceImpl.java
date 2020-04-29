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

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.statistics.MatAccountStatisticsSumByStoreMapper;
import com.chd.hrp.mat.service.account.statistics.MatAccountStatisticsSumByStoreService;

/**
 * 
 * @Description:
 * 收发结存汇总表(仓库)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountStatisticsSumByStoreService")
public class MatAccountStatisticsSumByStoreServiceImpl implements MatAccountStatisticsSumByStoreService {

	private static Logger logger = Logger.getLogger(MatAccountStatisticsSumByStoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountStatisticsSumByStoreMapper")
	private final MatAccountStatisticsSumByStoreMapper matAccountStatisticsSumByStoreMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountStatisticsSumByStore(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountStatisticsSumByStoreMapper.queryMatAccountStatisticsSumByStore(entityMap);
		
		return ChdJson.toJsonLower(list);
	}

	@Override
	public List<Map<String, Object>> queryMatAccountStatisticsSumByStorePrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		List<Map<String, Object>> list = matAccountStatisticsSumByStoreMapper.queryMatAccountStatisticsSumByStore(entityMap);
		return list;
	}
}

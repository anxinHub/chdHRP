/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.balance;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.balance.MatAccountBalanceInvHoldMapper;
import com.chd.hrp.mat.entity.MatAccountBalanceInvHold;
import com.chd.hrp.mat.service.account.balance.MatAccountBalanceInvHoldService;

/**
 * 
 * @Description:
 * 材料收发结存表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountBalanceInvHoldService")
public class MatAccountBalanceInvHoldServiceImpl implements MatAccountBalanceInvHoldService {

	private static Logger logger = Logger.getLogger(MatAccountBalanceInvHoldServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountBalanceInvHoldMapper")
	private final MatAccountBalanceInvHoldMapper matAccountBalanceInvHoldMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountBalanceInvHold(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<MatAccountBalanceInvHold> list = matAccountBalanceInvHoldMapper.queryMatAccountBalanceInvHold(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public List<Map<String, Object>> queryMatAccountBalanceInvHoldPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
			List<Map<String, Object>>  list = matAccountBalanceInvHoldMapper.queryMatAccountBalanceInvHoldPrint(entityMap);
		// TODO Auto-generated method stub
		return list;
	}
}

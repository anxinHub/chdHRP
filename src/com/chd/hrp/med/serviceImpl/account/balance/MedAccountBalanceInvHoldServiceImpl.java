/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.balance;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.balance.MedAccountBalanceInvHoldMapper;
import com.chd.hrp.med.entity.MedAccountBalanceInvHold;
import com.chd.hrp.med.service.account.balance.MedAccountBalanceInvHoldService;

/**
 * 
 * @Description:
 * 药品收发结存表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountBalanceInvHoldService")
public class MedAccountBalanceInvHoldServiceImpl implements MedAccountBalanceInvHoldService {

	private static Logger logger = Logger.getLogger(MedAccountBalanceInvHoldServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountBalanceInvHoldMapper")
	private final MedAccountBalanceInvHoldMapper medAccountBalanceInvHoldMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountBalanceInvHold(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<MedAccountBalanceInvHold> list = medAccountBalanceInvHoldMapper.queryMedAccountBalanceInvHold(entityMap);
		
		return ChdJson.toJson(list);
	}
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.balance;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.balance.MedAccountBalanceInvStockMapper;
import com.chd.hrp.med.service.account.balance.MedAccountBalanceInvStockService;

/**
 * 
 * @Description:
 * 库存收发查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountBalanceInvStockService")
public class MedAccountBalanceInvStockServiceImpl implements MedAccountBalanceInvStockService {

	private static Logger logger = Logger.getLogger(MedAccountBalanceInvStockServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountBalanceInvStockMapper")
	private final MedAccountBalanceInvStockMapper medAccountBalanceInvStockMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountBalanceInvStock(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = medAccountBalanceInvStockMapper.queryMedAccountBalanceInvStock(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.balance;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.balance.MatAccountBalanceInvStockMapper;
import com.chd.hrp.mat.service.account.balance.MatAccountBalanceInvStockService;

/**
 * 
 * @Description:
 * 库存收发查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountBalanceInvStockService")
public class MatAccountBalanceInvStockServiceImpl implements MatAccountBalanceInvStockService {

	private static Logger logger = Logger.getLogger(MatAccountBalanceInvStockServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountBalanceInvStockMapper")
	private final MatAccountBalanceInvStockMapper matAccountBalanceInvStockMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountBalanceInvStock(Map<String,Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = matAccountBalanceInvStockMapper.queryMatAccountBalanceInvStock(entityMap);
		
		return ChdJson.toJsonLower(list);
	}
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.account.balance;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.balance.MedAccountBalanceVirStockInvBalanceMapper;
import com.chd.hrp.med.service.account.balance.MedAccountBalanceVirStockInvBalanceService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 库存药品收发帐表(虚仓)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountBalanceVirStockInvBalanceService")
public class MedAccountBalanceVirStockInvBalanceServiceImpl implements MedAccountBalanceVirStockInvBalanceService {

	private static Logger logger = Logger.getLogger(MedAccountBalanceVirStockInvBalanceServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountBalanceVirStockInvBalanceMapper")
	private final MedAccountBalanceVirStockInvBalanceMapper medAccountBalanceVirStockInvBalanceMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountBalanceVirStockInvBalance(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAccountBalanceVirStockInvBalanceMapper.queryMedAccountBalanceVirStockInvBalance(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAccountBalanceVirStockInvBalanceMapper.queryMedAccountBalanceVirStockInvBalance(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.account.balance;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.balance.MatAccountBalanceVirStockInvBalanceMapper;
import com.chd.hrp.mat.service.account.balance.MatAccountBalanceVirStockInvBalanceService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 库存材料收发帐表(虚仓)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountBalanceVirStockInvBalanceService")
public class MatAccountBalanceVirStockInvBalanceServiceImpl implements MatAccountBalanceVirStockInvBalanceService {

	private static Logger logger = Logger.getLogger(MatAccountBalanceVirStockInvBalanceServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountBalanceVirStockInvBalanceMapper")
	private final MatAccountBalanceVirStockInvBalanceMapper matAccountBalanceVirStockInvBalanceMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountBalanceVirStockInvBalance(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAccountBalanceVirStockInvBalanceMapper.queryMatAccountBalanceVirStockInvBalance(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAccountBalanceVirStockInvBalanceMapper.queryMatAccountBalanceVirStockInvBalance(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}

	@Override
	public List<Map<String, Object>> queryMatAccountBalanceVirStockInvBalancePrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("user_id", SessionManager.getUserId());
				entityMap.put("show_history", MyConfig.getSysPara("03001"));
		 	List<Map<String, Object>> list = matAccountBalanceVirStockInvBalanceMapper.queryMatAccountBalanceVirStockInvBalance(entityMap);
		return list;
	}
}

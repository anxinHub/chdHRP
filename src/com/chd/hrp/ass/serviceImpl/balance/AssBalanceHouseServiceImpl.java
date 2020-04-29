/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.balance;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.balance.AssBalanceHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.entity.balance.AssBalanceHouse;
import com.chd.hrp.ass.service.balance.AssBalanceHouseService;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @Description:
 * 050801 资产卡片维护_房屋建筑
 * @Table:
 * ASS_CARD_SPECIAL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assBalanceHouseService")
public class AssBalanceHouseServiceImpl implements AssBalanceHouseService {

	private static Logger logger = Logger.getLogger(AssBalanceHouseServiceImpl.class);

    //引入DAO操作
			@Resource(name = "assBalanceHouseMapper")
			private final AssBalanceHouseMapper assBalanceHouseMapper = null;
		
			@Resource(name = "assCardHouseMapper")
			private final AssCardHouseMapper assCardHouseMapper = null;
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryBalanceAccountHouseMain(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AssBalanceHouse> list = (List<AssBalanceHouse>) assBalanceHouseMapper.queryBalanceAccountHouseMain(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AssBalanceHouse> list = (List<AssBalanceHouse>)assBalanceHouseMapper.queryBalanceAccountHouseMain(entityMap, rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public String queryBalanceAccountHouseDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AssBalanceHouse> list = (List<AssBalanceHouse>) assBalanceHouseMapper.queryBalanceAccountHouseDetail(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AssBalanceHouse> list = (List<AssBalanceHouse>)assBalanceHouseMapper.queryBalanceAccountHouseDetail(entityMap, rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	
}

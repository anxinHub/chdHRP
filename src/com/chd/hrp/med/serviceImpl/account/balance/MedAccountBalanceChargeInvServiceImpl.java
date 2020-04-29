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
import com.chd.hrp.med.dao.account.balance.MedAccountBalanceChargeInvMapper;
import com.chd.hrp.med.service.account.balance.MedAccountBalanceChargeInvService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 计费药品使用查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medAccountBalanceChargeInvService")
public class MedAccountBalanceChargeInvServiceImpl implements MedAccountBalanceChargeInvService {

	private static Logger logger = Logger.getLogger(MedAccountBalanceChargeInvServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAccountBalanceChargeInvMapper")
	private final MedAccountBalanceChargeInvMapper medAccountBalanceChargeInvMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountBalanceChargeInv(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		int is_com = 0;
		if(entityMap.get("is_com") != null){
			is_com = Integer.valueOf(entityMap.get("is_com").toString());
		}
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceChargeInv(entityMap);
			}else{
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceAffiChargeInv(entityMap);
			}
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceChargeInv(entityMap, rowBounds);
			}else{
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceAffiChargeInv(entityMap, rowBounds);
			}
				
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 
	 * 汇总查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountBalanceChargeInvCollect(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceChargeInvCollect(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceChargeInvCollect(entityMap, rowBounds);
				
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * @Description 
	 * 按住院号汇总查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAccountBalanceChargeInvCollectByHospital(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		int is_com = 0;  //默认查询非代销
		if(entityMap.get("is_com") != null){
			is_com = Integer.valueOf(entityMap.get("is_com").toString());
		}
		
		//默认查询对应的入库供应商
		if(entityMap.get("inv_sup") == null){
			entityMap.put("inv_sup", 0);
		}
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceChargeInvCollectByHospital(entityMap);
			}else{
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceAffiChargeInvCollectByHospital(entityMap);
			}
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceChargeInvCollectByHospital(entityMap, rowBounds);
			}else{
				list = medAccountBalanceChargeInvMapper.queryMedAccountBalanceAffiChargeInvCollectByHospital(entityMap, rowBounds);
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}

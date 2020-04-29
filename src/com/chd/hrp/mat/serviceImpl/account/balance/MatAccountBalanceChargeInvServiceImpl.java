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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.dao.account.balance.MatAccountBalanceChargeInvMapper;
import com.chd.hrp.mat.service.account.balance.MatAccountBalanceChargeInvService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 计费材料使用查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matAccountBalanceChargeInvService")
public class MatAccountBalanceChargeInvServiceImpl implements MatAccountBalanceChargeInvService {

	private static Logger logger = Logger.getLogger(MatAccountBalanceChargeInvServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAccountBalanceChargeInvMapper")
	private final MatAccountBalanceChargeInvMapper matAccountBalanceChargeInvMapper = null;

	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatAccountBalanceChargeInv(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		int is_com = 0;
		if(entityMap.get("is_com") != null){
			is_com = Integer.valueOf(entityMap.get("is_com").toString());
		}
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInv(entityMap);
			}else{
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceAffiChargeInv(entityMap);
			}
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInv(entityMap, rowBounds);
			}else{
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceAffiChargeInv(entityMap, rowBounds);
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
	public String queryMatAccountBalanceChargeInvCollect(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInvCollect(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInvCollect(entityMap, rowBounds);
				
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
	public String queryMatAccountBalanceChargeInvCollectByHospital(Map<String,Object> entityMap) throws DataAccessException{
		
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
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInvCollectByHospital(entityMap);
			}else{
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceAffiChargeInvCollectByHospital(entityMap);
			}
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInvCollectByHospital(entityMap, rowBounds);
			}else{
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceAffiChargeInvCollectByHospital(entityMap, rowBounds);
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryMatAccountBalanceChargeInvPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			int is_com = 0;
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
			//转换日期格式
			if(entityMap.get("begin_out_date") != null && !"".equals(entityMap.get("begin_out_date"))){
				entityMap.put("begin_out_date", DateUtil.stringToDate(entityMap.get("begin_out_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			}
			if(entityMap.get("end_out_date") != null && !"".equals(entityMap.get("end_out_date"))){
				entityMap.put("end_out_date", DateUtil.stringToDate(entityMap.get("end_out_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			if(entityMap.get("is_com") != null){
				is_com = Integer.valueOf(entityMap.get("is_com").toString());
			}
				List<Map<String, Object>> list;
				if(is_com == 0){
					list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInv(entityMap);
				}else{
					list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceAffiChargeInv(entityMap);
				}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatAccountBalanceChargeInvCollectPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		//转换日期格式
		if(entityMap.get("begin_out_date") != null && !"".equals(entityMap.get("begin_out_date"))){
			entityMap.put("begin_out_date", DateUtil.stringToDate(entityMap.get("begin_out_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(entityMap.get("end_out_date") != null && !"".equals(entityMap.get("end_out_date"))){
			entityMap.put("end_out_date", DateUtil.stringToDate(entityMap.get("end_out_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		List<Map<String, Object>> list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInvCollect(entityMap);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryMatAccountBalanceChargeInvCollectByHospitalPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		//转换日期格式
		if(entityMap.get("begin_out_date") != null && !"".equals(entityMap.get("begin_out_date"))){
			entityMap.put("begin_out_date", DateUtil.stringToDate(entityMap.get("begin_out_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(entityMap.get("end_out_date") != null && !"".equals(entityMap.get("end_out_date"))){
			entityMap.put("end_out_date", DateUtil.stringToDate(entityMap.get("end_out_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		int is_com = 0;  //默认查询非代销
		if(entityMap.get("is_com") != null){
			is_com = Integer.valueOf(entityMap.get("is_com").toString());
		}
		
		//默认查询对应的入库供应商
		if(entityMap.get("inv_sup") == null){
			entityMap.put("inv_sup", 0);
		}
		
			List<Map<String, Object>> list;
			if(is_com == 0){
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceChargeInvCollectByHospital(entityMap);
			}else{
				list = matAccountBalanceChargeInvMapper.queryMatAccountBalanceAffiChargeInvCollectByHospital(entityMap);
			}
		return list;
	}
}

package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiDeptBonusAuditMapper;
import com.chd.hrp.hpm.dao.AphiDeptBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiDeptBonusGrantItemMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;
import com.chd.hrp.hpm.entity.AphiDeptBonusGrantItem;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiDeptBonusGrantItemService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptBonusGrantItemService")
public class AphiDeptBonusGrantItemServiceImpl implements AphiDeptBonusGrantItemService {

	private static Logger logger = Logger.getLogger(AphiDeptBonusGrantItemServiceImpl.class);

	@Resource(name = "aphiDeptBonusGrantItemMapper")
	private final AphiDeptBonusGrantItemMapper aphiDeptBonusGrantItemMapper = null;

	@Resource(name = "aphiDeptBonusDataMapper")
	private final AphiDeptBonusDataMapper aphiDeptBonusDataMapper = null;

	@Resource(name = "aphiDeptBonusAuditMapper")
	private final AphiDeptBonusAuditMapper aphiDeptBonusAuditMapper = null;
	
	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	/**
	 * 
	 */
	@Override
	public String addDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiDeptBonusGrantItemMapper.addDeptBonusGrantItem(entityMap);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}
	}

	/**
	 * 
	 */
	@Override
	public String queryDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));

		entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4, 6));
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptBonusGrantItem> list = aphiDeptBonusGrantItemMapper.queryDeptBonusGrantItem(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptBonusGrantItem> list = aphiDeptBonusGrantItemMapper.queryDeptBonusGrantItem(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * 
	 */
	@Override
	public AphiDeptBonusGrantItem queryDeptBonusGrantItemByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiDeptBonusGrantItemMapper.queryDeptBonusGrantItemByCode(entityMap);
	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptBonusGrantItem(List<Map<String, Object>> entityList) throws DataAccessException {
		String request = "";
		for (Map<String, Object> entityMap : entityList) {
			int state = aphiDeptBonusGrantItemMapper.deleteDeptBonusGrantItem(entityMap);
			if (state > 0) {
				request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
		}

		return request;
	}

	
	public String deleteDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException {
		String request = "";
		int state = aphiDeptBonusGrantItemMapper.deleteDeptBonusGrantItem(entityMap);
		if (state > 0) {
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;
	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptBonusGrantItemById(String[] ids) throws DataAccessException {
		String request = "";
		if (ids != null && ids.length > 0) {
			for (String s : ids) {
				aphiDeptBonusGrantItemMapper.deleteDeptBonusGrantItemById(s);
			}
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"error\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;

	}

	/**
	 * 
	 */
	@Override
	public String updateDeptBonusGrantItem(List<Map<String, Object>> entityList) throws DataAccessException {

		for (Map<String, Object> entityMap : entityList) {

			List<AphiDeptBonusAudit> deptBonusAuditList = aphiDeptBonusAuditMapper.deptBonusIsAudit(entityMap);

			if (deptBonusAuditList.size() > 0) {

				for (int i = 0; i < deptBonusAuditList.size(); i++) {

					AphiDeptBonusAudit deptBonusAudit = deptBonusAuditList.get(i);

					if (deptBonusAudit.getIs_grant() == 1) {

						return "{\"error\":\"修改失败 ,当前状态已发放.\",\"state\":\"false\"}";

					}

				}

			} else {

				return "{\"error\":\"修改失败 ,当前状态未审核.\",\"state\":\"false\"}";
			}

			try {

				aphiDeptBonusGrantItemMapper.updateDeptBonusGrantItem(entityMap);

			} catch (Exception e) {

				return "{\"error\":\"修改失败.\",\"state\":\"false\"}";

			}

		}
		return "{\"state\":\"true\"}";

	}

	@Override
	public String createHpmDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_yearm.substring(0, 4));

		entityMap.put("acct_month", acct_yearm.substring(4, 6));

		AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		
		if(adba !=null){
			
			if (adba.getIs_audit() != 1) {

				return "{\"error\":\"生成失败 ,当前状态未审核.\",\"state\":\"false\"}";

			}

			if (adba.getIs_grant() == 1) {

				return "{\"error\":\"生成失败 ,当前状态已发放.\",\"state\":\"false\"}";

			}
		}else{
			
			return "{\"error\":\"生成失败 ,当前状态未审核.\",\"state\":\"false\"}";
			
		}
		
		
		
		entityMap.put("is_avg", "1");
		
		//List<AphiItem> itemList = aphiItemMapper.qeuryItemMap(entityMap);
		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);
		
		if(itemList.size() == 0){
			
			return "{\"error\":\"生成失败 ,没有查询到参与人均奖核算的奖金项目.\",\"state\":\"false\"}";
			
		}

		List<Map<String,Object>> deptBonusDataList = aphiDeptBonusDataMapper.queryDeptBonusByItemForBonusMoney(entityMap);

		if(deptBonusDataList.size() == 0){
			
			return "{\"error\":\"生成失败 ,没有查询到奖金核算数据.\",\"state\":\"false\"}";
			
		}
		
		try {
			
			List<Map<String, Object>> lowerList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> map : deptBonusDataList) {

				Map lowerMap = new HashMap();

				for (String key : map.keySet()) {

					lowerMap.put(key.toLowerCase(), map.get(key));

				}

				lowerList.add(lowerMap);

			}

			aphiDeptBonusGrantItemMapper.deleteDeptBonusGrantItem(entityMap);

			for (int i = 0; i < lowerList.size(); i++) {

				Map<String,Object> map = (Map<String,Object>) lowerList.get(i);
				
				Map<String,Object> mapGrant = new HashMap<String,Object>();

				mapGrant.put("group_id", entityMap.get("group_id"));
				
				mapGrant.put("hos_id", entityMap.get("hos_id"));
				
				mapGrant.put("copy_code", entityMap.get("copy_code"));
				
				mapGrant.put("acct_year", entityMap.get("acct_year"));
				
				mapGrant.put("acct_month", entityMap.get("acct_month"));
				
				mapGrant.put("dept_id", map.get("dept_id"));
				
				mapGrant.put("dept_no", map.get("dept_no"));
				
				mapGrant.put("item_code", map.get("item_code"));

				mapGrant.put("bonus_money", map.get("sum_money"));
				
				mapGrant.put("is_audit", 0);

				mapGrant.put("activity_money", 0);

				mapGrant.put("activity_percent", 0);

				mapGrant.put("grant_money", map.get("sum_money"));

				aphiDeptBonusGrantItemMapper.addDeptBonusGrantItem(mapGrant);

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}

	}

	@Override
	public String grantHpmDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException {

		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_yearm.substring(0, 4));

		entityMap.put("acct_month", acct_yearm.substring(4, 6));

		AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		
		int grant_state = Integer.parseInt(entityMap.get("grant_state").toString());
		
		String reStr = null;
		
		if(grant_state == 1){reStr = "发放";}else{reStr = "取消发放";}
		
		if(adba != null && adba.getIs_audit() == 0){
			
			return "{\"error\":\"当前状态未审核,不能"+reStr+".\",\"state\":\"false\"}";
			
		}
		
		if(grant_state == 1){//发放走验证
			
			StringBuffer errSb = new StringBuffer();
			
			entityMap.put("is_audit", "0");
			
			List<AphiDeptBonusGrantItem> list = aphiDeptBonusGrantItemMapper.queryDeptBonusGrantItem(entityMap);
			
			if(list.size() > 0){
				
				for(AphiDeptBonusGrantItem adbg : list){
					
					if(adbg.getIs_audit() == 0){
						
						errSb.append(adbg.getDept_code() +" "+ adbg.getDept_name() +" 没有完成审核操作<br>");
						
					}

				}

				return "{\"error\":\""+errSb.toString()+"\",\"state\":\"false\"}";
			}

		}

		try {

			if(adba != null){
				
				entityMap.put("group_id", entityMap.get("group_id"));
				
				entityMap.put("hos_id", entityMap.get("hos_id"));
				
				entityMap.put("copy_code", entityMap.get("copy_code"));
				
				entityMap.put("acct_year", entityMap.get("acct_year"));
				
				entityMap.put("acct_month", entityMap.get("acct_month"));
				
				entityMap.put("is_audit", adba.getIs_audit());
				
				entityMap.put("is_grant", grant_state);
				
				aphiDeptBonusAuditMapper.updateDeptBonusAudit(entityMap);
				
			}
			
			return "{\"msg\":\""+reStr+"成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String auditHpmDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));

		entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4, 6));
		
		try {
			AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);

			int is_audit = Integer.parseInt(entityMap.get("isAudit").toString());

			String reStr = null;

			if (is_audit == 1) {
				reStr = "审核";
			} else {
				reStr = "消审";
			}

			if (adba == null || adba.getIs_audit() == 0) {

				return "{\"error\":\"当前状态未审核,不能" + reStr + ".\",\"state\":\"false\"}";

			}

			if (adba != null && adba.getIs_grant() == 1) {

				return "{\"error\":\"当前状态已发放,不能" + reStr + ".\",\"state\":\"false\"}";

			}

			String dept_id = String.valueOf(entityMap.get("dept_id"));

			if (dept_id != null && !"null".equals(dept_id) && !"".equals(dept_id)) {

				entityMap.put("dept_id", dept_id.split(",")[0]);

				entityMap.put("dept_no", dept_id.split(",")[1]);

			}
			
			entityMap.put("is_audit", is_audit);

			aphiDeptBonusGrantItemMapper.updateDeptBonusGrantItem(entityMap);

			return "{\"msg\":\"" + reStr + "成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败.\",\"state\":\"false\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryDeptBonusGrantItemPrint(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("user_id", SessionManager.getUserId());
		
		entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));
		entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4, 6));

		List<Map<String, Object>> list = aphiDeptBonusGrantItemMapper.queryDeptBonusGrantItemPrint(entityMap);

		return list;

	}
}

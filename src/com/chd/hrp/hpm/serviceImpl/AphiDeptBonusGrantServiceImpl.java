package com.chd.hrp.hpm.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hpm.dao.AphiDeptBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiDeptBonusGrantMapper;
import com.chd.hrp.hpm.dao.AphiEmpBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiDeptBonusGrant;
import com.chd.hrp.hpm.entity.AphiEmpBonusData;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiDeptBonusGrantService;
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

@Service("aphiDeptBonusGrantService")
public class AphiDeptBonusGrantServiceImpl implements AphiDeptBonusGrantService {

	private static Logger logger = Logger.getLogger(AphiDeptBonusGrantServiceImpl.class);

	@Resource(name = "aphiDeptBonusGrantMapper")
	private final AphiDeptBonusGrantMapper aphiDeptBonusGrantMapper = null;

	@Resource(name = "aphiDeptBonusDataMapper")
	private final AphiDeptBonusDataMapper aphiDeptBonusDataMapper = null;
	
	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	@Resource(name = "aphiEmpBonusDataMapper")
	private final AphiEmpBonusDataMapper aphiEmpBonusDataMapper = null;
	/**
	 * 
	 */
	@Override
	public String addDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiDeptBonusGrantMapper.addDeptBonusGrant(entityMap);
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
	public String queryDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));

		entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4, 6));
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiDeptBonusGrant> list = aphiDeptBonusGrantMapper.queryDeptBonusGrant(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiDeptBonusGrant> list = aphiDeptBonusGrantMapper.queryDeptBonusGrant(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}


	/**
	 * 
	 */
	@Override
	public String deleteDeptBonusGrant(List<Map<String, Object>> entityList) throws DataAccessException {
		String request = "";
		for (Map<String, Object> entityMap : entityList) {
			int state = aphiDeptBonusGrantMapper.deleteDeptBonusGrant(entityMap);
			if (state > 0) {
				request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
		}

		return request;
	}

	
	public String deleteDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException {
		String request = "";
		int state = aphiDeptBonusGrantMapper.deleteDeptBonusGrant(entityMap);
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
	public String deleteDeptBonusGrantById(String[] ids) throws DataAccessException {
		String request = "";
		if (ids != null && ids.length > 0) {
			for (String s : ids) {
				aphiDeptBonusGrantMapper.deleteDeptBonusGrantById(s);
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
	public String updateDeptBonusGrant(List<Map<String, Object>> entityList) throws DataAccessException {

		for (Map<String, Object> entityMap : entityList) {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			//List<AphiDeptBonusAudit> deptBonusAuditList = aphiDeptBonusAuditMapper.deptBonusIsAudit(entityMap);

			List<AphiDeptBonusData> deptBonusAuditList = aphiDeptBonusDataMapper.queryDeptBonusDataForGrant(entityMap);
			
			List<AphiDeptBonusGrant> deptBonusGrantList = aphiDeptBonusGrantMapper.queryDeptBonusGrantState(entityMap);
			
			if (deptBonusAuditList.size() > 0) {

				for (int i = 0; i < deptBonusGrantList.size(); i++) {

					AphiDeptBonusGrant deptBonusAudit = deptBonusGrantList.get(i);

					if (deptBonusAudit.getIs_grant() == 1) {

						return "{\"error\":\"修改失败 ,当前状态已发放.\",\"state\":\"false\"}";

					}

				}

			} else {

				return "{\"error\":\"修改失败 ,当前状态未审核.\",\"state\":\"false\"}";
			}

			try {

				aphiDeptBonusGrantMapper.updateDeptBonusGrant(entityMap);

			} catch (Exception e) {

				return "{\"error\":\"修改失败.\",\"state\":\"false\"}";

			}

		}
		return "{\"state\":\"true\"}";

	}

	@Override
	public String createHpmDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_yearm.substring(0, 4));

		entityMap.put("acct_month", acct_yearm.substring(4, 6));

//		AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//		if(adba !=null){
//			if (adba.getIs_audit() != 1) {
//				return "{\"error\":\"生成失败 ,当前状态未审核.\",\"state\":\"false\"}";
//			}
//			if (adba.getIs_grant() == 1) {
//				return "{\"error\":\"生成失败 ,当前状态已发放.\",\"state\":\"false\"}";
//			}
//		}else{
//			return "{\"error\":\"生成失败 ,当前状态未审核.\",\"state\":\"false\"}";
//		}
		
		
		
		entityMap.put("is_avg", "1");
		
		//List<AphiItem> itemList = aphiItemMapper.qeuryItemMap(entityMap);
		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);
		
		if(itemList.size() == 0){
			
			return "{\"error\":\"生成失败 ,没有查询到参与人均奖核算的奖金项目.\",\"state\":\"false\"}";
			
		}

		StringBuffer sql = new StringBuffer();

		StringBuffer sql_sum = new StringBuffer();

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql_sum.append("nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)+");
		}

		entityMap.put("sql_sum", "sum("+sql_sum.substring(0, sql_sum.length() - 1).toString()+") as sum_money,");

		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<Map<String,Object>> deptBonusDataList = aphiDeptBonusDataMapper.queryDeptBonusForBonusMoney(entityMap);

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

			aphiDeptBonusGrantMapper.deleteDeptBonusGrant(entityMap);

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

				mapGrant.put("bonus_money", map.get("sum_money"));
				
				mapGrant.put("is_audit", 0);

				mapGrant.put("activity_money", 0);

				mapGrant.put("activity_percent", 0);

				mapGrant.put("grant_money", map.get("sum_money"));

				aphiDeptBonusGrantMapper.addDeptBonusGrant(mapGrant);

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}

	}

	@Override
	public String grantHpmDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_yearm.substring(0, 4));

		entityMap.put("acct_month", acct_yearm.substring(4, 6));

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		//根据checkIds来判断是不是单个审批
		if(entityMap.get("checkIds") != null){
			
			//把科室id和no循环出来
			for (String id : entityMap.get("checkIds").toString().split(",")) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				
				String[] ids = id.split("@");
				
				mapVo.put("dept_id", ids[0]);
				mapVo.put("dept_no", ids[1]);
				mapVo.put("item_code", ids[2]);
				
				listVo.add(mapVo);
			
			}
			
			
		}
		
		entityMap.put("list", listVo);
		
	    List<AphiDeptBonusGrant> adba = aphiDeptBonusGrantMapper.queryDeptBonusGrantByCode_GrantList(entityMap);
		
		int grant_state = Integer.parseInt(entityMap.get("grant_state").toString());
		
		String reStr = null;
		
		if(grant_state == 1){reStr = "发放";}else{reStr = "取消发放";}
		
		for(AphiDeptBonusGrant ids : adba){
			
			if(adba.size() > 0 && ids.getIs_audit() == 0){
				
				return "{\"error\":\"当前状态未审核,不能"+reStr+".\",\"state\":\"false\"}";
				
			}
		}
		
		if(grant_state == 1  && entityMap.get("checkIds") == null){//发放走验证
			
			StringBuffer errSb = new StringBuffer();
			
			entityMap.put("is_audit", "0");
			
			List<AphiDeptBonusGrant> list = aphiDeptBonusGrantMapper.queryDeptBonusGrant(entityMap);
			
			if(list.size() > 0){
				
				for(AphiDeptBonusGrant adbg : list){
					
					if(adbg.getIs_audit() == 0){
						
						errSb.append(adbg.getDept_code() +" "+ adbg.getDept_name() +" 没有完成审核操作<br>");
						
					}

				}

				return "{\"error\":\""+errSb.toString()+"\",\"state\":\"false\"}";
			}

		}
		
		
		//取消发放验证,如果二次分配上报,那里做了数据已经审核,就不应该在取消发放
		if(grant_state == 0 ){
			
			List<AphiEmpBonusData> empAudit = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode_dept(entityMap);
			
			for(AphiEmpBonusData emps : empAudit){
				if(emps.getIs_audit() != 0 && empAudit.size()>0){
					
					return "{\"error\":\"二次分配上报已经做审核操作,不能取消发放\",\"state\":\"false\"}";
					
				}
				
			}
			
		}
		
		
		
		

		try {

			if(adba != null){
				
				entityMap.put("group_id", entityMap.get("group_id"));  
				
				entityMap.put("hos_id", entityMap.get("hos_id"));
				
				entityMap.put("copy_code", entityMap.get("copy_code"));
				
				entityMap.put("acct_year", entityMap.get("acct_year"));
				
				entityMap.put("acct_month", entityMap.get("acct_month"));
				
				entityMap.put("is_audit", 1);
				
				
				if(grant_state == 1 ){
					
					if(entityMap.get("checkIds") != null){
						
						for (String id : entityMap.get("checkIds").toString().split(",")) { 
							
							String[] ids = id.split("@");
							
							entityMap.put("dept_id", ids[0]);
							entityMap.put("dept_no", ids[1]);
							entityMap.put("item_code", ids[2]);
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							
							entityMap.put("grant_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
							
							entityMap.put("grant_code", SessionManager.getUserId());
							
							entityMap.put("is_grant", grant_state);
							
							aphiDeptBonusGrantMapper.updateDeptBonusAudit(entityMap);
							
						
						}
						
						return "{\"msg\":\""+reStr+"成功.\",\"state\":\"true\"}";
						
					}
					
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						entityMap.put("grant_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
						
						entityMap.put("grant_code", SessionManager.getUserId());
						
						entityMap.put("is_grant", grant_state);
						
						aphiDeptBonusGrantMapper.updateDeptBonusAudit(entityMap);
						
					
					
					
					return "{\"msg\":\""+reStr+"成功.\",\"state\":\"true\"}";
					
				} else {
					if(entityMap.get("checkIds") != null){
						for (String id : entityMap.get("checkIds").toString().split(",")) {
							
							String[] ids = id.split("@");
							entityMap.put("dept_id", ids[0]);
							entityMap.put("dept_no", ids[1]);
							entityMap.put("item_code", ids[2]);
							
							entityMap.put("grant_date", "");
							
							entityMap.put("grant_code", "");
							
							entityMap.put("is_grant", grant_state);
							
							aphiDeptBonusGrantMapper.updateDeptBonusAudit(entityMap);
						}
						
						return "{\"msg\":\""+reStr+"成功.\",\"state\":\"true\"}";
					}
					
					entityMap.put("grant_date", "");
					
					entityMap.put("grant_code", "");
					
				}
				
				
				entityMap.put("is_grant", grant_state);
				
				//aphiDeptBonusAuditMapper.updateDeptBonusAudit(entityMap);
				aphiDeptBonusGrantMapper.updateDeptBonusAudit(entityMap);
				
			}
			
			return "{\"msg\":\""+reStr+"成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String auditHpmDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));

		entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4, 6));
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		//根据checkIds来判断是不是单个审批
		if(entityMap.get("checkIds") != null){
			
			//把科室id和no循环出来
			for (String id : entityMap.get("checkIds").toString().split(",")) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				
				String[] ids = id.split("@");
				
				mapVo.put("dept_id", ids[0]);
				mapVo.put("dept_no", ids[1]);
				mapVo.put("item_codes", ids[2]);
				
				listVo.add(mapVo);
			
			}
			
			
		}
		
		entityMap.put("list", listVo);
		
		
		try {
			//AphiDeptBonusAudit adba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
			
			List<AphiDeptBonusData> adba = aphiDeptBonusDataMapper.queryHpmDeptBonusDataByCode_AuditList(entityMap);
			
			List<AphiDeptBonusGrant> adba1 = aphiDeptBonusGrantMapper.queryDeptBonusGrantByCode_GrantList(entityMap);

			int is_audit = Integer.parseInt(entityMap.get("isAudit").toString());

			String reStr = null;

			if (is_audit == 1) {
				reStr = "审核";
			} else {
				reStr = "消审";
			}

			for( AphiDeptBonusData ids : adba){
				if(adba.size() > 0 && ids.getIs_audit() == 0){
					return "{\"error\":\"当前状态审核页面未审核,不能" + reStr + ".\",\"state\":\"false\"}";
				}
			
			}
			
			for( AphiDeptBonusGrant isd : adba1){
				if(adba.size() > 0 &&  isd.getIs_grant() == 1 ){
					return "{\"error\":\"当前状态已发放,不能" + reStr + ".\",\"state\":\"false\"}";
				}
			}
			
			if(is_audit == 1){
				for( AphiDeptBonusGrant isd : adba1){
					if(isd.getIs_audit() == 1){
						return "{\"error\":\"当前状态已经审核,不能重复" + reStr + ".\",\"state\":\"false\"}";
					}
				}
			} else{
				for( AphiDeptBonusGrant isd : adba1){
					if(isd.getIs_grant() == 1 && isd.getIs_audit() == 0){
						return "{\"error\":\"当前状态已经审核,不能重复" + reStr + ".\",\"state\":\"false\"}";
					}
				}
			}
			
			
			
			/*if (adba == null || adba.getIs_audit() == 0) {

				return "{\"error\":\"当前状态未审核,不能" + reStr + ".\",\"state\":\"false\"}";

			}*/

			/*if (adba != null && adba1.getIs_grant() == 1) {

				return "{\"error\":\"当前状态已发放,不能" + reStr + ".\",\"state\":\"false\"}";

			}*/

			if(entityMap.get("checkIds") != null){
				
				//把科室id和no循环出来
				for (String id : entityMap.get("checkIds").toString().split(",")) {
					
					String[] ids = id.split("@");
					
					entityMap.put("dept_id", ids[0]);
					entityMap.put("dept_no", ids[1]);
					entityMap.put("item_code", ids[2]);
					if(is_audit == 1){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						entityMap.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
						
						entityMap.put("user_code", SessionManager.getUserId());
						
					} else {
						
						entityMap.put("audit_date", "");
						
						entityMap.put("user_code", "");
						
					}
					
					entityMap.put("is_audit", is_audit);
					
					aphiDeptBonusGrantMapper.updateDeptBonusGrant(entityMap);
				
				}
				return "{\"msg\":\"" + reStr + "成功.\",\"state\":\"true\"}";
				
				
			}
			
			
			String dept_id = String.valueOf(entityMap.get("dept_id"));

			if (dept_id != null && !"null".equals(dept_id) && !"".equals(dept_id)) {

				entityMap.put("dept_id", dept_id.split(",")[0]);

				entityMap.put("dept_no", dept_id.split(",")[1]);

			}
			
			if(is_audit == 1){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				entityMap.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
				
				entityMap.put("user_code", SessionManager.getUserId());
				
			} else {
				
				entityMap.put("audit_date", "");
				
				entityMap.put("user_code", "");
				
			}
			
			entityMap.put("is_audit", is_audit);
			

			aphiDeptBonusGrantMapper.updateDeptBonusGrant(entityMap);

			return "{\"msg\":\"" + reStr + "成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败.\",\"state\":\"false\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryDeptBonusGrantPrint(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("user_id", SessionManager.getUserId());
		
		entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));
		entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4, 6));

		List<Map<String, Object>> list = aphiDeptBonusGrantMapper.queryDeptBonusGrantPrint(entityMap);

		return list;

	}
}

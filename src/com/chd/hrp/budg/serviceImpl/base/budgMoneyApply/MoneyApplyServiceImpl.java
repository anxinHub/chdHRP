package com.chd.hrp.budg.serviceImpl.base.budgMoneyApply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.reimbursemt.BudgPaymentApplyDetMapper;
import com.chd.hrp.budg.dao.base.budgMoneyApply.MoneyApplyMapper;
import com.chd.hrp.budg.service.base.budgMoneyApply.MoneyApplyService;
import com.github.pagehelper.PageInfo;

@Service("moneyApplyService")
public class MoneyApplyServiceImpl implements MoneyApplyService {
	
	private static Logger logger = Logger.getLogger(MoneyApplyServiceImpl.class);
	
	@Resource(name = "moneyApplyMapper")
	private final MoneyApplyMapper moneyApplyMapper = null;
	
	/**
	 * 主页面查询
	 */
	@Override
	public String queryMoneyApply(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			 List<Map<String,Object>> list = (List<Map<String, Object>>) moneyApplyMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = (List<Map<String, Object>>) moneyApplyMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryProjDict(Map<String, Object> entityMap) {
		
		
		/*SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			 List<Map<String,Object>> list = (List<Map<String, Object>>) moneyApplyMapper.queryProjDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = (List<Map<String, Object>>) moneyApplyMapper.queryProjDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}*/
		
		
		
		return JSON.toJSONString(moneyApplyMapper.queryProjDict(entityMap));
	}
	
	
	@Override
	public String queryUserApplyCode(Map<String, Object> entityMap) {
		
		
		return JSON.toJSONString(moneyApplyMapper.queryUserApplyCode(entityMap));
	}

	
	/**
	 * 添加明细页面保存
	 */
	@Override
	public String addMoneyApply(List<Map<String, Object>> list,Map<String, Object> mapVo) {
		
		try {
			Map<String,Object> queryMap = moneyApplyMapper.queryChangeNo(mapVo);
			mapVo.put("dept_no", queryMap.get("dept_no"));
			mapVo.put("duty_dept_no", queryMap.get("duty_dept_no"));
			mapVo.put("proj_no", queryMap.get("proj_no"));
			mapVo.put("emp_no", queryMap.get("emp_no"));
			
			//添加主表
			moneyApplyMapper.addMain(mapVo);
			//添加明细表
			moneyApplyMapper.addDetailed(list);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"apply_code\":\""+mapVo.get("apply_code")+"\" }";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
	}
	
	/**
	 * 生成流水号
	 */
	@Override
	public String queryMaxKey(Map<String, Object> mapVo) {
		
		return moneyApplyMapper.queryMaxKey(mapVo);
	}

	
	/**
	 * 跳转update页面所需要展示的数据
	 */
	@Override
	public Map<String, Object> queryUpdatePageData(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> listMain = moneyApplyMapper.queryUpdatePageMainData(mapVo);
		
		
		return listMain.size()>0?listMain.get(0):null;
	}
	
	
	/**
	 * update页面查询详细
	 */
	@Override
	public String queryMoneyApplyDet(Map<String, Object> mapVo) {
		
		
		List<Map<String,Object>> list = moneyApplyMapper.queryUpdatePageDetiData(mapVo);
		return ChdJson.toJson(list);
	}

	
	/**
	 * 根据主键删除主表及明细表的数据
	 */
	@Override
	public void deleteFromId(HashMap<String, Object> map) {
		try {
			moneyApplyMapper.deleteDetailed(map);
			moneyApplyMapper.deleteMain(map);
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	
	/**
	 * 主页面的批量删除
	 */
	@Override
	public String deleteFromBatch(List<Map<String, Object>> list) {
		try {
			
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> ded = (List<Map<String, Object>>) moneyApplyMapper.query(map);
				String state_t = ded.get(0).get("state_t").toString();
				if (!"01".equals(state_t)) {
					return "{\"warn\":\"只能删除状态为新建的数据！\",\"state\":\"false\"}";
				}
				
			}
			
			
			moneyApplyMapper.deleteDetailedBatch(list);
			moneyApplyMapper.deleteMainBatch(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}

	}

	/**
	 * 单独删除明细
	 */
	@Override
	public String deleteFromBatchDetailed(List<Map<String, Object>> list) {
		try {
			moneyApplyMapper.deleteDetailedBatch(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	/**
	 * 删除明细后修改主表的总金额
	 */
	@Override
	public void updatMainApply_amount(List<Map<String, Object>> list) {
		try {
			Float Apply_amount = moneyApplyMapper.queryDetailedApply_amount(list.get(0));
			
			list.get(0).put("new_Apply_amount", Apply_amount);
			
			moneyApplyMapper.updatMainApply_amount(list.get(0));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
	}

	
	/**
	 * 主页面提交、明细页面提交   state_t 01新建、02已提交、03已审核
	 */
	@Override
	public String updateMoneyApplyState(List<Map<String, Object>> list) {
		try {
			
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> ded = (List<Map<String, Object>>) moneyApplyMapper.query(map);
				String state_t = ded.get(0).get("state_t").toString();
				if (!"01".equals(state_t)) {
					return "{\"warn\":\"只能提交状态为新建的数据！\",\"state\":\"false\"}";
				}
				
			}
			
			
			moneyApplyMapper.updateMoneyApplyState(list);
			
			return "{\"msg\":\"用款申请已经提交完毕.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	
	/**
	 * 主页面撤回、明细页面撤回   state_t 01新建、02已提交、03已审核
	 * @return
	 */
	@Override
	public String updateMoneyApplyStateRevoke(List<Map<String, Object>> list) {
		try {
			
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> ded = (List<Map<String, Object>>) moneyApplyMapper.query(map);
				String state_t = ded.get(0).get("state_t").toString();
				if (!"02".equals(state_t)) {
					return "{\"warn\":\"只能撤回状态为已提交的数据！\",\"state\":\"false\"}";
				}
				
			}
			
			moneyApplyMapper.updateMoneyApplyStateRevoke(list);
			
			return "{\"msg\":\"用款申请已经撤回完毕.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public HashMap<String, Object> queryApplyDataForCode(Map<String, Object> mapVo) {
		return moneyApplyMapper.queryApplyDataForCode(mapVo);
	}

	
	
	
	//用款申请登记 模板设置。
	@Override
	public Map<String, Object> queryMoneyApplyPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try{
			
			 Map<String,Object> reMap=new HashMap<String,Object>();
			 
			 WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			 
			 MoneyApplyMapper moneyApplyMapper = (MoneyApplyMapper)context.getBean("moneyApplyMapper");
				
			 Map<String,Object> mapMian = moneyApplyMapper.queryMoneyApplyByPrintTemlateMain(entityMap);
			 
			 List<Map<String, Object>> mapDetail = moneyApplyMapper.queryMoneyApplyDetByPrintTemlate(entityMap);
			 
			if (entityMap.get("group_id") == null) {
				
				entityMap.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (entityMap.get("hos_id") == null) {
				
				entityMap.put("hos_id", SessionManager.getHosId());
				
			}
			if (entityMap.get("copy_code") == null) {

				entityMap.put("copy_code", SessionManager.getCopyCode());

			}
			 
			 
			reMap.put("main", mapMian);
			
			reMap.put("detail", mapDetail); 
			
			return reMap; 
			
		}catch(Exception e){
			
			logger.error(e.getMessage(),e);
			
			throw new SysException(e.getMessage());
			
		}

	}

	@Override
	public HashMap<String, Object> queryBudgetQuota(Map<String, Object> mapVo) {
	
		
		//List<Map<String,Object>> list = moneyApplyMapper.queryUpdatePageDetiData(mapVo);
		
		
		return moneyApplyMapper.queryBudgetQuota(mapVo);
	}
	
	

}

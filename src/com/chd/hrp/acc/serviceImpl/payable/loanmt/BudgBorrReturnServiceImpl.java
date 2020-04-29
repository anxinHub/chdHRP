package com.chd.hrp.acc.serviceImpl.payable.loanmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetProjMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrProjMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrReturnDetMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrReturnMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetProj;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.entity.payable.BudgBorrReturn;
import com.chd.hrp.acc.entity.payable.BudgBorrReturnDet;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrReturnService;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.github.pagehelper.PageInfo;
@Service("budgBorrReturnService")
public class BudgBorrReturnServiceImpl implements BudgBorrReturnService {
	
	private static Logger logger = Logger.getLogger(BudgBorrReturnServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgBorrReturnMapper")
	private final BudgBorrReturnMapper budgBorrReturnMapper = null;
	
	@Resource(name = "budgBorrReturnDetMapper")
	private final BudgBorrReturnDetMapper budgBorrReturnDetMapper = null;
	
	@Resource(name = "budgNoManagerService")
	private final BudgNoManagerService budgNoManagerService = null;
	
	
	@Resource(name = "budgBorrDeptMapper")
	private final BudgBorrDeptMapper budgBorrDeptMapper = null;

	@Resource(name = "budgBorrDetDeptMapper")
	private final BudgBorrDetDeptMapper budgBorrDetDeptMapper = null;

	@Resource(name = "budgBorrDetProjMapper")
	private final BudgBorrDetProjMapper budgBorrDetProjMapper = null;

	@Resource(name = "budgBorrProjMapper")
	private final BudgBorrProjMapper budgBorrProjMapper = null;

	@Override
	public String addBudgBorrReturn(Map<String, Object> entityMap) throws DataAccessException {
		BudgBorrReturn budgBorrReturn = queryBudgBorrReturnByCode(entityMap);
		try {
			if (budgBorrReturn != null) {
				budgBorrReturnMapper.updateBudgBorrReturn(entityMap);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"return_code\":\""+entityMap.get("return_code")+"\"}";
			}else{
				entityMap.put("state", "01");
				entityMap.put("table_name", "借款申请");
				entityMap.put("prefixe", "JKSQ");
				entityMap.put("table_code", "BUDG_BORR_RETURN");
				String return_code = budgNoManagerService.getBillNOSeqNo(entityMap);
				entityMap.put("return_code", return_code);
				int state = budgBorrReturnMapper.addBudgBorrReturn(entityMap);
				if(state > 0){
					budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_BORR_RETURN");
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"return_code\":\""+return_code+"\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addBatchBudgBorrReturn(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			budgBorrReturnMapper.addBatchBudgBorrReturn(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBudgBorrReturn(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = budgBorrReturnMapper.updateBudgBorrReturn(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}

	@Override
	public String updateBatchBudgBorrReturn(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			budgBorrReturnMapper.updateBatchBudgBorrReturn(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBudgBorrReturn(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = budgBorrReturnMapper.deleteBudgBorrReturn(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBatchBudgBorrReturn(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgBorrReturnDetMapper.deleteBatch(entityMap);
			budgBorrReturnMapper.deleteBatchBudgBorrReturn(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgBorrReturn(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgBorrReturn> list = budgBorrReturnMapper.queryBudgBorrReturn(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgBorrReturn> list = budgBorrReturnMapper.queryBudgBorrReturn(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public BudgBorrReturn queryBudgBorrReturnByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrReturnMapper.queryBudgBorrReturnByCode(entityMap);
	}

	@Override
	public BudgBorrReturn queryBudgBorrReturnByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrReturnMapper.queryBudgBorrReturnByUniqueness(entityMap);
	}

	@Override
	public String queryBudgBorrReturnDet(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgBorrReturnDet> list = (List<BudgBorrReturnDet>)budgBorrReturnDetMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgBorrReturnDet> list = (List<BudgBorrReturnDet>)budgBorrReturnDetMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}


	@Override
	public String updateConfirmPay(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			String return_codes = "";
			Map<String, Object> mainMapVo = new HashMap<String, Object>();
			for (Map<String, Object> map : entityMap) {
				mainMapVo.put("group_id", map.get("group_id"));
				mainMapVo.put("hos_id", map.get("hos_id"));
				mainMapVo.put("copy_code", map.get("copy_code"));
				return_codes = return_codes + "'"+map.get("return_code") + "',";
			}
			return_codes = return_codes.substring(0, return_codes.length() - 1);// 去掉最后一个逗号
			mainMapVo.put("return_codes", return_codes);
			List<BudgBorrReturn> mainList = budgBorrReturnMapper.queryBudgBorrReturn(mainMapVo);
			for (BudgBorrReturn budgBorrReturn : mainList) {
				Map<String, Object> mapBorrAllVo = new HashMap<String, Object>();
				mapBorrAllVo.put("group_id", budgBorrReturn.getGroup_id());
				mapBorrAllVo.put("hos_id", budgBorrReturn.getHos_id());
				mapBorrAllVo.put("copy_code", budgBorrReturn.getCopy_code());
				mapBorrAllVo.put("proj_id", budgBorrReturn.getProj_id());
				mapBorrAllVo.put("emp_id", budgBorrReturn.getEmp_id());
				mapBorrAllVo.put("return_amount", budgBorrReturn.getReturn_amount());

				if (budgBorrReturn.getProj_id() == null) {
					BudgBorrDept budgBorrDept = budgBorrDeptMapper.queryByCode(mapBorrAllVo);
					if (budgBorrDept != null) {
						Double remain_amount = budgBorrDept.getBorrow_amount() - budgBorrDept.getOffset_amount() - budgBorrReturn.getReturn_amount() - budgBorrDept.getReturn_amount();
						if(remain_amount == 0 || remain_amount == 0.0 ){
							mapBorrAllVo.put("remain_amount", "0");
					    }else{
					    	mapBorrAllVo.put("remain_amount", remain_amount);
					    }
						mapBorrAllVo.put("return_amount", budgBorrReturn.getReturn_amount() + budgBorrDept.getReturn_amount());
						budgBorrDeptMapper.update(mapBorrAllVo);
					} else {
						return "{\"error\":\"确认失败,单据["+return_codes+"]没有借款信息.\"}";
					}
				} else {
					BudgBorrProj budgBorrProj = budgBorrProjMapper.queryByCode(mapBorrAllVo);
					if (budgBorrProj != null) {
						Double remain_amount = budgBorrProj.getBorrow_amount() - budgBorrProj.getOffset_amount() - budgBorrReturn.getReturn_amount() - budgBorrProj.getReturn_amount();
						if(remain_amount == 0 || remain_amount == 0.0 ){
							mapBorrAllVo.put("remain_amount", "0");
					    }else{
					    	mapBorrAllVo.put("remain_amount", remain_amount);
					    }
						mapBorrAllVo.put("return_amount", budgBorrReturn.getReturn_amount() + budgBorrProj.getReturn_amount());
						budgBorrProjMapper.update(mapBorrAllVo);
					} else {
						return "{\"error\":\"确认失败,单据["+return_codes+"]没有借款信息.\"}";
					}
				}

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				mapDetailVo.put("group_id", budgBorrReturn.getGroup_id());
				mapDetailVo.put("hos_id", budgBorrReturn.getHos_id());
				mapDetailVo.put("copy_code", budgBorrReturn.getCopy_code());
				mapDetailVo.put("return_code", budgBorrReturn.getReturn_code());
				
				List<BudgBorrReturnDet> detaillist = (List<BudgBorrReturnDet>) budgBorrReturnDetMapper.queryExists(mapDetailVo);
				for (BudgBorrReturnDet budgBorrReturnDet : detaillist) {
					Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
					mapBorrDetAllVo.put("group_id", budgBorrReturnDet.getGroup_id());
					mapBorrDetAllVo.put("hos_id", budgBorrReturnDet.getHos_id());
					mapBorrDetAllVo.put("copy_code", budgBorrReturnDet.getCopy_code());
					mapBorrDetAllVo.put("proj_id", budgBorrReturn.getProj_id());
					mapBorrDetAllVo.put("emp_id", budgBorrReturn.getEmp_id());
					mapBorrDetAllVo.put("source_id", budgBorrReturnDet.getSource_id());
					mapBorrDetAllVo.put("payment_item_id", budgBorrReturnDet.getPayment_item_id());
					mapBorrDetAllVo.put("return_amount", budgBorrReturnDet.getReturn_amount());
					
					if (budgBorrReturn.getProj_id() == null) {
						BudgBorrDetDept budgBorrDetDept = budgBorrDetDeptMapper.queryByCode(mapBorrDetAllVo);
						if (budgBorrDetDept != null) {
						    Double remain_amount = budgBorrDetDept.getBorrow_amount() - budgBorrDetDept.getOffset_amount() - budgBorrReturnDet.getReturn_amount() - budgBorrDetDept.getReturn_amount();
						    mapBorrDetAllVo.put("return_amount", budgBorrReturnDet.getReturn_amount() + budgBorrDetDept.getReturn_amount());
						    if(remain_amount == 0 || remain_amount == 0.0 ){
						    	mapBorrDetAllVo.put("remain_amount", "0");
						    }else{
						    	mapBorrDetAllVo.put("remain_amount", remain_amount);
						    }
						    budgBorrDetDeptMapper.update(mapBorrDetAllVo);
						} else {
							return "{\"error\":\"确认失败,单据["+return_codes+"]没有借款信息.\"}";
						}
					} else {
						BudgBorrDetProj budgBorrDetProj = budgBorrDetProjMapper.queryByCode(mapBorrDetAllVo);
						if (budgBorrDetProj != null) {
							Double remain_amount = budgBorrDetProj.getBorrow_amount() - budgBorrDetProj.getOffset_amount() - budgBorrReturnDet.getReturn_amount() - budgBorrDetProj.getReturn_amount();
							mapBorrDetAllVo.put("return_amount", budgBorrReturnDet.getReturn_amount() + budgBorrDetProj.getReturn_amount());
							if(remain_amount == 0 || remain_amount == 0.0 ){
						    	mapBorrDetAllVo.put("remain_amount", "0");
						    }else{
						    	mapBorrDetAllVo.put("remain_amount", remain_amount);
						    }
							budgBorrDetProjMapper.update(mapBorrDetAllVo);
						} else {
							return "{\"error\":\"确认失败,单据["+return_codes+"]没有借款信息.\"}";
						}
					}
				}
			}
			budgBorrReturnMapper.updateConfirmPay(entityMap);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;

	@Override
	public String queryBorrReturnPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		Map<String,Object> map = budgBorrReturnMapper.queryBorrReturnByPrintTemlate(entityMap);
		
		List<Map<String,Object>> list = budgBorrReturnDetMapper.queryBorrReturnDetByPrintTemlate(entityMap);
		
		return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
	}
	
	@Override
	public List<Map<String,Object>> queryBudgBorrReturnPrint(Map<String, Object> entityMap) throws DataAccessException {
			
			List<Map<String,Object>> list = budgBorrReturnMapper.queryBudgBorrReturnPrint(entityMap);
			
			return list;
			
	}


}

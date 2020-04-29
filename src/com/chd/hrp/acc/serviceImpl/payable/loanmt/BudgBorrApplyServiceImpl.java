package com.chd.hrp.acc.serviceImpl.payable.loanmt;

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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.AccAutoVouchMaintainMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetProjMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrProjMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrApplyDetMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrApplyMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrApply;
import com.chd.hrp.acc.entity.payable.BudgBorrApplyDet;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetProj;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrApplyService;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.serviceImpl.advice.MatRefChargeServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("budgBorrApplyService")
public class BudgBorrApplyServiceImpl implements BudgBorrApplyService {
 
	private static Logger logger = Logger.getLogger(MatRefChargeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgBorrApplyMapper")
	private final BudgBorrApplyMapper budgBorrApplyMapper = null;

	@Resource(name = "budgBorrApplyDetMapper")
	private final BudgBorrApplyDetMapper budgBorrApplyDetMapper = null;

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
	
	@Resource(name = "accAutoVouchMaintainMapper")
	private final AccAutoVouchMaintainMapper accAutoVouchMaintainMapper = null;

	@Override
	public String addBudgBorrApply(Map<String, Object> entityMap) throws DataAccessException {
		BudgBorrApply budgBorrApply = queryBudgBorrApplyByCode(entityMap);
		try {
			if (budgBorrApply != null) {
				budgBorrApplyMapper.updateBudgBorrApply(entityMap);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"apply_code\":\"" + entityMap.get("apply_code") + "\"}";
			} else {
				entityMap.put("state", "01");
				entityMap.put("table_name", "借款申请");
				entityMap.put("prefixe", "JKSQ");
				entityMap.put("table_code", "BUDG_BORR_APPLY");
				String apply_code = budgNoManagerService.getBillNOSeqNo(entityMap);
				entityMap.put("apply_code", apply_code);
				int state = budgBorrApplyMapper.addBudgBorrApply(entityMap);
				if (state > 0) {
					budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_BORR_APPLY");
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"apply_code\":\"" + apply_code + "\"}";
			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addBatchBudgBorrApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgBorrApplyMapper.addBatchBudgBorrApply(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBudgBorrApply(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = budgBorrApplyMapper.updateBudgBorrApply(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBatchBudgBorrApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgBorrApplyMapper.updateBatchBudgBorrApply(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBudgBorrApply(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = budgBorrApplyMapper.deleteBudgBorrApply(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBatchBudgBorrApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgBorrApplyDetMapper.deleteBatch(entityMap);
			budgBorrApplyMapper.deleteBatchBudgBorrApply(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgBorrApply(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgBorrApply> list = budgBorrApplyMapper.queryBudgBorrApply(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgBorrApply> list = budgBorrApplyMapper.queryBudgBorrApply(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public BudgBorrApply queryBudgBorrApplyByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrApplyMapper.queryBudgBorrApplyByCode(entityMap);
	}

	@Override
	public BudgBorrApply queryBudgBorrApplyByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrApplyMapper.queryBudgBorrApplyByUniqueness(entityMap);
	}

	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgBorrApplyMapper.updateToExamine(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgBorrApplyMapper.updateNotToExamine(entityMap);

			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgBorrApplyDet(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgBorrApplyDet> list = (List<BudgBorrApplyDet>) budgBorrApplyDetMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgBorrApplyDet> list = (List<BudgBorrApplyDet>) budgBorrApplyDetMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String updateSubmitAndWithdraw(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgBorrApplyMapper.updateSubmitAndWithdraw(entityMap);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateConfirmPay(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			String apply_codes = "";
			Map<String, Object> mainMapVo = new HashMap<String, Object>();
			//1.获取要确认单据的所有单号
			for (Map<String, Object> map : entityMap) {
				mainMapVo.put("group_id", map.get("group_id"));
				mainMapVo.put("hos_id", map.get("hos_id"));
				mainMapVo.put("copy_code", map.get("copy_code"));
				apply_codes = apply_codes + "'"+map.get("apply_code") + "',";
			}
			apply_codes = apply_codes.substring(0, apply_codes.length() - 1);// 去掉最后一个逗号
			mainMapVo.put("apply_codes", apply_codes);
			
			//2.根据要确认的借款单单号查询所对应的借款单集合
			List<BudgBorrApply> mainList = budgBorrApplyMapper.queryBudgBorrApply(mainMapVo);
			
			//3.遍历借款单结果集
			for (BudgBorrApply budgBorrApply : mainList) {
				Map<String, Object> mapBorrAllVo = new HashMap<String, Object>();
				mapBorrAllVo.put("group_id", budgBorrApply.getGroup_id());
				mapBorrAllVo.put("hos_id", budgBorrApply.getHos_id());
				mapBorrAllVo.put("copy_code", budgBorrApply.getCopy_code());
				mapBorrAllVo.put("proj_id", budgBorrApply.getProj_id());
				mapBorrAllVo.put("emp_id", budgBorrApply.getEmp_id());
				mapBorrAllVo.put("borrow_amount", budgBorrApply.getBorrow_amount());
				mapBorrAllVo.put("offset_amount", "0");
				mapBorrAllVo.put("return_amount", "0");
				mapBorrAllVo.put("remain_amount", budgBorrApply.getBorrow_amount());
				
				//4.判断是科室借款还是项目
				if (budgBorrApply.getProj_id() == null) {//项目Id为空,就是科室借款
					BudgBorrDept budgBorrDept = budgBorrDeptMapper.queryByCode(mapBorrAllVo);
					if (budgBorrDept != null) {
						mapBorrAllVo.put("borrow_amount", budgBorrDept.getBorrow_amount() + budgBorrApply.getBorrow_amount());//借款金额
						mapBorrAllVo.put("remain_amount", budgBorrDept.getRemain_amount() + budgBorrApply.getBorrow_amount());//借款余额
						mapBorrAllVo.put("return_amount", budgBorrDept.getReturn_amount());
						
						mapBorrAllVo.put("offset_amount", budgBorrDept.getOffset_amount());
						
						
						budgBorrDeptMapper.update(mapBorrAllVo);
					} else {
						budgBorrDeptMapper.add(mapBorrAllVo);
					}
				} else {//项目借款
					BudgBorrProj budgBorrProj = budgBorrProjMapper.queryByCode(mapBorrAllVo);
					if (budgBorrProj != null) {
						mapBorrAllVo.put("borrow_amount", budgBorrProj.getBorrow_amount() + budgBorrApply.getBorrow_amount());//借款金额
						mapBorrAllVo.put("remain_amount", budgBorrProj.getRemain_amount() + budgBorrApply.getBorrow_amount());//借款余额
						mapBorrAllVo.put("return_amount", budgBorrProj.getReturn_amount());
						
						mapBorrAllVo.put("offset_amount", budgBorrProj.getOffset_amount());
						budgBorrProjMapper.update(mapBorrAllVo);
					} else {
						budgBorrProjMapper.add(mapBorrAllVo);
					}
				}

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				mapDetailVo.put("group_id", budgBorrApply.getGroup_id());
				mapDetailVo.put("hos_id", budgBorrApply.getHos_id());
				mapDetailVo.put("copy_code", budgBorrApply.getCopy_code());
				mapDetailVo.put("apply_code", budgBorrApply.getApply_code());
				
				//5.根据借款单号查询借款单明细
				List<BudgBorrApplyDet> detaillist = (List<BudgBorrApplyDet>) budgBorrApplyDetMapper.query(mapDetailVo);
				
				//6.遍历借款单明细结果集
				for (BudgBorrApplyDet budgBorrApplyDet : detaillist) {
					Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
					mapBorrDetAllVo.put("group_id", budgBorrApplyDet.getGroup_id());
					mapBorrDetAllVo.put("hos_id", budgBorrApplyDet.getHos_id());
					mapBorrDetAllVo.put("copy_code", budgBorrApplyDet.getCopy_code());
					mapBorrDetAllVo.put("proj_id", budgBorrApply.getProj_id());
					mapBorrDetAllVo.put("emp_id", budgBorrApply.getEmp_id());
					mapBorrDetAllVo.put("source_id", budgBorrApplyDet.getSource_id());
					mapBorrDetAllVo.put("payment_item_id", budgBorrApplyDet.getPayment_item_id());
					mapBorrDetAllVo.put("borrow_amount", budgBorrApplyDet.getBorrow_amount());
					mapBorrDetAllVo.put("offset_amount", "0");
					mapBorrDetAllVo.put("return_amount", "0");
					mapBorrDetAllVo.put("remain_amount", budgBorrApplyDet.getBorrow_amount());
					
					if (budgBorrApply.getProj_id() == null) {//项目ID为空,就是科室借款
						BudgBorrDetDept budgBorrDetDept = budgBorrDetDeptMapper.queryByCode(mapBorrDetAllVo);
						if (budgBorrDetDept != null) {//科室借款明细数据存在,更新
							mapBorrDetAllVo.put("borrow_amount", budgBorrDetDept.getBorrow_amount() + budgBorrApplyDet.getBorrow_amount());
							mapBorrDetAllVo.put("return_amount", budgBorrDetDept.getReturn_amount());
							
							
							mapBorrDetAllVo.put("offset_amount", budgBorrDetDept.getOffset_amount());
							
							mapBorrDetAllVo.put("remain_amount", budgBorrDetDept.getRemain_amount() + budgBorrApplyDet.getBorrow_amount());
							budgBorrDetDeptMapper.update(mapBorrDetAllVo);
						} else {//新增
							budgBorrDetDeptMapper.add(mapBorrDetAllVo);
						}
					} else {//项目借款
						BudgBorrDetProj budgBorrDetProj = budgBorrDetProjMapper.queryByCode(mapBorrDetAllVo);
						if (budgBorrDetProj != null) {//项目借款明细数据存在,更新
							mapBorrDetAllVo.put("borrow_amount", budgBorrDetProj.getBorrow_amount() + budgBorrApplyDet.getBorrow_amount());
							mapBorrDetAllVo.put("remain_amount", budgBorrDetProj.getRemain_amount() + budgBorrApplyDet.getBorrow_amount());
							mapBorrDetAllVo.put("return_amount", budgBorrDetProj.getReturn_amount());
							mapBorrDetAllVo.put("offset_amount", budgBorrDetProj.getOffset_amount());
							budgBorrDetProjMapper.update(mapBorrDetAllVo);
						} else {//新增
							budgBorrDetProjMapper.add(mapBorrDetAllVo);
						}
					}
				}

			}
			
			//7.修改申请单状态为确认
			budgBorrApplyMapper.updateConfirmPay(entityMap);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryBorrApplyPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> map = budgBorrApplyMapper.queryBorrApplyByPrintTemlate(entityMap);
		
		List<Map<String,Object>> list = budgBorrApplyDetMapper.queryBorrApplyDetByPrintTemlate(entityMap);
		
		return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
	}
	
	@Override
	public List<Map<String,Object>>  queryBudgBorrApplyPrint(Map<String, Object> entityMap) throws DataAccessException {
	
			List<Map<String,Object>> list = budgBorrApplyMapper.queryBudgBorrApplyPrint(entityMap);

			return list;

	}

	@Override
	public String unConfirmPay(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {
			
			String apply_codes = "";
			
			Map<String, Object> mainMapVo = new HashMap<String, Object>();
			for (Map<String, Object> map : entityList) {
				apply_codes = apply_codes + "'"+map.get("apply_code") + "',";
				//先删除凭证
				if(map.get("vouch_id") == null || "".equals(map.get("vouch_id"))){
					continue;
				}
				
				mainMapVo.put("group_id", map.get("group_id"));
				mainMapVo.put("hos_id", map.get("hos_id"));
				mainMapVo.put("copy_code", map.get("copy_code"));
				mainMapVo.put("vouch_id", map.get("vouch_id"));
				mainMapVo.put("log_table", map.get("log_table"));
				mainMapVo.put("user_id", SessionManager.getUserId());
				
				accAutoVouchMaintainMapper.deleteAccAutoVouch(mainMapVo);
				
				if(mainMapVo.get("reNote")!=null && !mainMapVo.get("reNote").toString().equalsIgnoreCase("ok")){
					throw new SysException(mainMapVo.get("reNote").toString());
				}
			}
			
			apply_codes = apply_codes.substring(0, apply_codes.length() - 1);// 去掉最后一个逗号
			mainMapVo.put("apply_codes", apply_codes);
			
			List<BudgBorrApply> mainList = budgBorrApplyMapper.queryBudgBorrApply(mainMapVo);
			
			//3.遍历借款单结果集
			for (BudgBorrApply budgBorrApply : mainList) {
				Map<String, Object> mapBorrAllVo = new HashMap<String, Object>();
				mapBorrAllVo.put("group_id", budgBorrApply.getGroup_id());
				mapBorrAllVo.put("hos_id", budgBorrApply.getHos_id());
				mapBorrAllVo.put("copy_code", budgBorrApply.getCopy_code());
				mapBorrAllVo.put("proj_id", budgBorrApply.getProj_id());
				mapBorrAllVo.put("emp_id", budgBorrApply.getEmp_id());
				mapBorrAllVo.put("borrow_amount", budgBorrApply.getBorrow_amount());
				/*mapBorrAllVo.put("offset_amount", "0");
				mapBorrAllVo.put("return_amount", "0");*/
				mapBorrAllVo.put("remain_amount", budgBorrApply.getBorrow_amount());
				
				//4.判断是科室借款还是项目
				if (budgBorrApply.getProj_id() == null) {//项目Id为空,就是科室借款
					BudgBorrDept budgBorrDept = budgBorrDeptMapper.queryByCode(mapBorrAllVo);
					if (budgBorrDept != null) {
						mapBorrAllVo.put("borrow_amount", budgBorrDept.getBorrow_amount() - budgBorrApply.getBorrow_amount());//借款金额
						mapBorrAllVo.put("remain_amount", budgBorrDept.getRemain_amount() - budgBorrApply.getBorrow_amount());//借款余额
						mapBorrAllVo.put("return_amount", budgBorrDept.getReturn_amount());
						
						mapBorrAllVo.put("offset_amount", budgBorrDept.getOffset_amount());
						
						
						budgBorrDeptMapper.update(mapBorrAllVo);
					} /*else {
						budgBorrDeptMapper.add(mapBorrAllVo);
					}*/
				} else {//项目借款
					BudgBorrProj budgBorrProj = budgBorrProjMapper.queryByCode(mapBorrAllVo);
					if (budgBorrProj != null) {
						mapBorrAllVo.put("borrow_amount", budgBorrProj.getBorrow_amount() - budgBorrApply.getBorrow_amount());//借款金额
						mapBorrAllVo.put("remain_amount", budgBorrProj.getRemain_amount() - budgBorrApply.getBorrow_amount());//借款余额
						mapBorrAllVo.put("return_amount", budgBorrProj.getReturn_amount());
						
						mapBorrAllVo.put("offset_amount", budgBorrProj.getOffset_amount());
						budgBorrProjMapper.update(mapBorrAllVo);
					} /*else {
						budgBorrProjMapper.add(mapBorrAllVo);
					}*/
				}

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				mapDetailVo.put("group_id", budgBorrApply.getGroup_id());
				mapDetailVo.put("hos_id", budgBorrApply.getHos_id());
				mapDetailVo.put("copy_code", budgBorrApply.getCopy_code());
				mapDetailVo.put("apply_code", budgBorrApply.getApply_code());
				
				//5.根据借款单号查询借款单明细
				List<BudgBorrApplyDet> detaillist = (List<BudgBorrApplyDet>) budgBorrApplyDetMapper.query(mapDetailVo);
				
				//6.遍历借款单明细结果集
				for (BudgBorrApplyDet budgBorrApplyDet : detaillist) {
					Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
					mapBorrDetAllVo.put("group_id", budgBorrApplyDet.getGroup_id());
					mapBorrDetAllVo.put("hos_id", budgBorrApplyDet.getHos_id());
					mapBorrDetAllVo.put("copy_code", budgBorrApplyDet.getCopy_code());
					mapBorrDetAllVo.put("proj_id", budgBorrApply.getProj_id());
					mapBorrDetAllVo.put("emp_id", budgBorrApply.getEmp_id());
					mapBorrDetAllVo.put("source_id", budgBorrApplyDet.getSource_id());
					mapBorrDetAllVo.put("payment_item_id", budgBorrApplyDet.getPayment_item_id());
					mapBorrDetAllVo.put("borrow_amount", budgBorrApplyDet.getBorrow_amount());
					mapBorrDetAllVo.put("offset_amount", "0");
					mapBorrDetAllVo.put("return_amount", "0");
					mapBorrDetAllVo.put("remain_amount", budgBorrApplyDet.getBorrow_amount());
					
					if (budgBorrApply.getProj_id() == null) {//项目ID为空,就是科室借款
						BudgBorrDetDept budgBorrDetDept = budgBorrDetDeptMapper.queryByCode(mapBorrDetAllVo);
						if (budgBorrDetDept != null) {//科室借款明细数据存在,更新
							mapBorrDetAllVo.put("borrow_amount", budgBorrDetDept.getBorrow_amount() - budgBorrApplyDet.getBorrow_amount());
							mapBorrDetAllVo.put("return_amount", budgBorrDetDept.getReturn_amount());
							
							
							mapBorrDetAllVo.put("offset_amount", budgBorrDetDept.getOffset_amount());
							
							mapBorrDetAllVo.put("remain_amount", budgBorrDetDept.getRemain_amount() - budgBorrApplyDet.getBorrow_amount());
							budgBorrDetDeptMapper.update(mapBorrDetAllVo);
						} /*else {//新增
							budgBorrDetDeptMapper.add(mapBorrDetAllVo);
						}*/
					} else {//项目借款
						BudgBorrDetProj budgBorrDetProj = budgBorrDetProjMapper.queryByCode(mapBorrDetAllVo);
						if (budgBorrDetProj != null) {//项目借款明细数据存在,更新
							mapBorrDetAllVo.put("borrow_amount", budgBorrDetProj.getBorrow_amount() - budgBorrApplyDet.getBorrow_amount());
							mapBorrDetAllVo.put("remain_amount", budgBorrDetProj.getRemain_amount() - budgBorrApplyDet.getBorrow_amount());
							mapBorrDetAllVo.put("return_amount", budgBorrDetProj.getReturn_amount());
							mapBorrDetAllVo.put("offset_amount", budgBorrDetProj.getOffset_amount());
							budgBorrDetProjMapper.update(mapBorrDetAllVo);
						}/* else {//新增
							budgBorrDetProjMapper.add(mapBorrDetAllVo);
						}*/
					}
				}
			}
			
			
			
			/*for (BudgBorrApply budgBorrApply : mainList) {
				
				
				Map<String, Object> mapBorrAllVo = new HashMap<String, Object>();
				mapBorrAllVo.put("group_id", budgBorrApply.getGroup_id());
				mapBorrAllVo.put("hos_id", budgBorrApply.getHos_id());
				mapBorrAllVo.put("copy_code", budgBorrApply.getCopy_code());
				mapBorrAllVo.put("proj_id", budgBorrApply.getProj_id());
				mapBorrAllVo.put("emp_id", budgBorrApply.getEmp_id());
				

				if (budgBorrApply.getProj_id() == null) {
					
					budgBorrDetDeptMapper.delete(mapBorrAllVo);
					budgBorrDeptMapper.delete(mapBorrAllVo);
					
				} else {
					
					budgBorrDetProjMapper.delete(mapBorrAllVo);
					budgBorrProjMapper.delete(mapBorrAllVo);
					
				}

			}*/
			
			budgBorrApplyMapper.updateUnConfirmPay(entityList);
			
			// TODO Auto-generated method stub
			return "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"tfalse\"}");
		}
	}

	@Override
	public Map<String, Object> queryBorrApplyPrintTemlateNew(Map<String, Object> entityMap)
			throws DataAccessException {
		try{
			
			 Map<String,Object> reMap=new HashMap<String,Object>();
			 
			 WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			 
			 BudgBorrApplyMapper budgBorrApplyMapper = (BudgBorrApplyMapper)context.getBean("budgBorrApplyMapper");
				
			 Map<String,Object> mapMian = budgBorrApplyMapper.queryBorrApplyByPrintTemlate(entityMap);
			 
			 List<Map<String, Object>> mapDetail = budgBorrApplyMapper.queryBorrApplyDetByPrintTemlateDetail(entityMap);
			 
			reMap.put("main", mapMian);
			
			reMap.put("detail", mapDetail); 
			
			return reMap; 
			
		}catch(Exception e){
			
			logger.error(e.getMessage(),e);
			
			throw new SysException(e.getMessage());
			
		}
	}

}

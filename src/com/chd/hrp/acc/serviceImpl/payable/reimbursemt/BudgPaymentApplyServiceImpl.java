package com.chd.hrp.acc.serviceImpl.payable.reimbursemt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetProjMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrProjMapper;
import com.chd.hrp.acc.dao.payable.base.BudgPaymentItemDictMapper;
import com.chd.hrp.acc.dao.payable.otherpay.BudgUnitMapper;
import com.chd.hrp.acc.dao.payable.reimbursemt.BudgPaymentApplyDetMapper;
import com.chd.hrp.acc.dao.payable.reimbursemt.BudgPaymentApplyMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetProj;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;
import com.chd.hrp.acc.entity.payable.BudgPaymentApplyDet;
import com.chd.hrp.acc.entity.payable.BudgPaymentItemDict;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyDetService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyService;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.mat.serviceImpl.advice.MatRefChargeServiceImpl;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.dao.ProjDictMapper;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.entity.ProjDict;
import com.chd.hrp.sys.entity.Source;
import com.github.pagehelper.PageInfo;
 
@Service("budgPaymentApplyService")
public class BudgPaymentApplyServiceImpl implements BudgPaymentApplyService {
 
	private static Logger logger = Logger.getLogger(MatRefChargeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgPaymentApplyMapper")
	private final BudgPaymentApplyMapper budgPaymentApplyMapper = null;

	@Resource(name = "budgPaymentApplyDetMapper")
	private final BudgPaymentApplyDetMapper budgPaymentApplyDetMapper = null;

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

	@Resource(name = "budgPaymentApplyDetService")
	private final BudgPaymentApplyDetService budgPaymentApplyDetService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@Resource(name = "budgUnitMapper")
	private final BudgUnitMapper budgUnitMapper = null;
	
	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "projDictMapper")
	private final ProjDictMapper projDictMapper = null;
	
	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;
	
	@Resource(name = "budgPaymentItemDictMapper")
	private final BudgPaymentItemDictMapper budgPaymentItemDictMapper = null;
	
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;
	
	@Resource(name = "hrpAccSelectMapper")
	private final HrpAccSelectMapper hrpAccSelectMapper = null;
	
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;

	@Override
	public String addBudgPaymentApply(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String payment_amount;
			BudgPaymentApply budgPaymentApply = queryBudgPaymentApplyByCode(entityMap);
//			if (entityMap.get("payment_amount") != null) {
//				payment_amount = entityMap.get("payment_amount").toString().replaceAll(",", "");
//				entityMap.put("payment_amount", payment_amount);
//			}
			
			if (budgPaymentApply != null) {
				budgPaymentApplyMapper.updateBudgPaymentApply(entityMap);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"apply_code\":\"" + entityMap.get("apply_code") + "\"}";
			} else {
				// 如果 flag = 1 则用户选择保存并提交功能 直接更改状态为提交状态
				if ("1".equals(entityMap.get("flag"))) {
					entityMap.put("state", "02");
				} else {
					// 否则 为新建状态
					entityMap.put("state", "01");
				}

				entityMap.put("table_name", "报销申请");
				entityMap.put("prefixe", "BXSQ");
				entityMap.put("table_code", "BUDG_PAYMENT_APPLY");
				String apply_code = budgNoManagerService.getBillNOSeqNo(entityMap);
				entityMap.put("apply_code", apply_code);
				int state = budgPaymentApplyMapper.addBudgPaymentApply(entityMap);
				if (state > 0) {
					budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_PAYMENT_APPLY");
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"apply_code\":\"" + apply_code + "\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addBatchBudgPaymentApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgPaymentApplyMapper.addBatchBudgPaymentApply(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBudgPaymentApply(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = budgPaymentApplyMapper.updateBudgPaymentApply(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBatchBudgPaymentApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgPaymentApplyMapper.updateBatchBudgPaymentApply(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBudgPaymentApply(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = budgPaymentApplyMapper.deleteBudgPaymentApply(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBatchBudgPaymentApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgPaymentApplyDetMapper.deleteBatch(entityMap);
			budgPaymentApplyMapper.deleteBatchBudgPaymentApply(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgPaymentApply(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgPaymentApply> list = budgPaymentApplyMapper.queryBudgPaymentApply(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgPaymentApply> list = budgPaymentApplyMapper.queryBudgPaymentApply(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public BudgPaymentApply queryBudgPaymentApplyByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgPaymentApplyMapper.queryBudgPaymentApplyByCode(entityMap);
	}

	@Override
	public BudgPaymentApply queryBudgPaymentApplyByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgPaymentApplyMapper.queryBudgPaymentApplyByUniqueness(entityMap);
	}

	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgPaymentApplyMapper.updateToExamine(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgPaymentApplyMapper.updateNotToExamine(entityMap);

			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgPaymentApplyDet(Map<String, Object> entityMap) throws DataAccessException {

		BudgPaymentApply budgPaymentApply = budgPaymentApplyMapper.queryBudgPaymentApplyByCode(entityMap);

		Double remain_amounts = 0.0;// 借款信息总额

		if (budgPaymentApply.getProj_id() == null) {
			entityMap.put("emp_id", budgPaymentApply.getEmp_id());
			BudgBorrDept budgBorrDept = budgBorrDeptMapper.queryByCode(entityMap);
			if (budgBorrDept != null) {
				remain_amounts = budgBorrDept.getRemain_amount();
			}
		} else {
			entityMap.put("emp_id", budgPaymentApply.getEmp_id());
			entityMap.put("proj_id", budgPaymentApply.getProj_id());
			BudgBorrProj budgBorrProj = budgBorrProjMapper.queryByCode(entityMap);
			if (budgBorrProj != null) {
				remain_amounts = budgBorrProj.getRemain_amount();
			}
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (entityMap.get("p02001").equals("1")) {
			if (sysPage.getTotal() == -1) {
				List<BudgPaymentApplyDet> list = (List<BudgPaymentApplyDet>) budgPaymentApplyDetMapper.query(entityMap);
				List<BudgPaymentApplyDet> relist = new ArrayList<BudgPaymentApplyDet>();
				for (BudgPaymentApplyDet budgPaymentApplyDet : list) {
					Double min = 0.0;
					if (budgPaymentApplyDet.getRemain_amount() != null) {
						if (budgPaymentApplyDet.getRemain_amount() > budgPaymentApplyDet.getPayment_amount()) {
							min = budgPaymentApplyDet.getPayment_amount();
						} else {
							min = budgPaymentApplyDet.getRemain_amount();
						}
					}
					budgPaymentApplyDet.setOffset_amount(min);
					relist.add(budgPaymentApplyDet);
				}

				return ChdJson.toJson(relist);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<BudgPaymentApplyDet> list = (List<BudgPaymentApplyDet>) budgPaymentApplyDetMapper.query(entityMap, rowBounds);

				List<BudgPaymentApplyDet> relist = new ArrayList<BudgPaymentApplyDet>();
				for (BudgPaymentApplyDet budgPaymentApplyDet : list) {
					Double min = 0.0;
					if (budgPaymentApplyDet.getRemain_amount() != null) {
						if (budgPaymentApplyDet.getRemain_amount() > budgPaymentApplyDet.getPayment_amount()) {
							min = budgPaymentApplyDet.getPayment_amount();
						} else {
							min = budgPaymentApplyDet.getRemain_amount();
						}
					}
					budgPaymentApplyDet.setOffset_amount(min);
					relist.add(budgPaymentApplyDet);
				}

				PageInfo page = new PageInfo(relist);
				return ChdJson.toJson(relist, page.getTotal());
			}
		} else {
			if (sysPage.getTotal() == -1) {
				List<BudgPaymentApplyDet> list = (List<BudgPaymentApplyDet>) budgPaymentApplyDetMapper.query(entityMap);
				List<BudgPaymentApplyDet> relist = new ArrayList<BudgPaymentApplyDet>();

				if (remain_amounts != 0.0 && remain_amounts != 0) {
					for (BudgPaymentApplyDet budgPaymentApplyDet : list) {
						if (budgPaymentApplyDet.getPayment_amount() <= remain_amounts) {
							budgPaymentApplyDet.setOffset_amount(budgPaymentApplyDet.getPayment_amount());
							remain_amounts = remain_amounts - budgPaymentApplyDet.getPayment_amount();
						} else {
							if (remain_amounts < 0) {
								budgPaymentApplyDet.setOffset_amount(0.0);
							} else {
								budgPaymentApplyDet.setOffset_amount(remain_amounts);
							}
							remain_amounts = remain_amounts - remain_amounts;
						}
						relist.add(budgPaymentApplyDet);
					}
					return ChdJson.toJson(relist);
				} else {
					for (BudgPaymentApplyDet budgPaymentApplyDet : list) {
						budgPaymentApplyDet.setOffset_amount(0.0);
						relist.add(budgPaymentApplyDet);
					}
					return ChdJson.toJson(relist);
				}
			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<BudgPaymentApplyDet> list = (List<BudgPaymentApplyDet>) budgPaymentApplyDetMapper.query(entityMap, rowBounds);

				List<BudgPaymentApplyDet> relist = new ArrayList<BudgPaymentApplyDet>();

				if (remain_amounts != 0.0 && remain_amounts != 0) {
					for (BudgPaymentApplyDet budgPaymentApplyDet : list) {
						if (budgPaymentApplyDet.getPayment_amount() <= remain_amounts) {
							budgPaymentApplyDet.setOffset_amount(budgPaymentApplyDet.getPayment_amount());
							remain_amounts = remain_amounts - budgPaymentApplyDet.getPayment_amount();
						} else {
							if (remain_amounts < 0) {
								budgPaymentApplyDet.setOffset_amount(0.0);
							} else {
								budgPaymentApplyDet.setOffset_amount(remain_amounts);
							}
							remain_amounts = remain_amounts - remain_amounts;
						}
						budgPaymentApplyDet.setApply_code(budgPaymentApplyDet.getApply_code());

						relist.add(budgPaymentApplyDet);
					}
					PageInfo page = new PageInfo(relist);
					return ChdJson.toJson(relist, page.getTotal());
				} else {
					for (BudgPaymentApplyDet budgPaymentApplyDet : list) {
						budgPaymentApplyDet.setOffset_amount(0.0);
						relist.add(budgPaymentApplyDet);
					}
					PageInfo page = new PageInfo(relist);
					return ChdJson.toJson(relist, page.getTotal());
				}
			}
		}
	}

	@Override
	public String updateSubmitAndWithdraw(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgPaymentApplyMapper.updateSubmitAndWithdraw(entityMap);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	public String tjBudgPaymentApplyAndDet(Map<String, Object> paramMapVo) {
		String result = "";
		if (paramMapVo.get("group_id") == null) {
			paramMapVo.put("group_id", SessionManager.getGroupId());
		}

		if (paramMapVo.get("hos_id") == null) {
			paramMapVo.put("hos_id", SessionManager.getHosId());
		}
		if (paramMapVo.get("copy_code") == null) {
			paramMapVo.put("copy_code", SessionManager.getCopyCode());
		}

		try {
			
			result = addBudgPaymentApply(paramMapVo);
			
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
		JSONObject jsonObj = JSONArray.parseObject(result);
		String apply_code = (String) jsonObj.get("apply_code");

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, paramMapVo.get("detailData").toString());
		for (Map<String, Object> detailVo : detail) {
			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}
			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}
			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}
			detailVo.put("apply_code", apply_code);

			String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
			String payment_item_id = detailVo.get("payment_item_id_no") == null ? "" : detailVo.get("payment_item_id_no").toString();
			if ("".equals(source_id)) {
				break;
			}
			if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").toString().equals("0")
					|| detailVo.get("offset_amount").toString().equals("")) {
				detailVo.put("offset_amount", "0");
			} else {
				detailVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
			}

			if (detailVo.get("offset_amount") != null && !detailVo.get("offset_amount").toString().equals("")) {
				Double pay_amount = Double.parseDouble(detailVo.get("payment_amount").toString())
						- Double.parseDouble(detailVo.get("offset_amount").toString());
				detailVo.put("pay_amount", pay_amount);
			} else {
				detailVo.put("pay_amount", "0");
			}
			detailVo.put("source_id", source_id.split("\\.")[1]);
			detailVo.put("payment_item_id", payment_item_id.split("@")[0]);
			detailVo.put("payment_item_no", payment_item_id.split("@")[1]);

			try {
				result = budgPaymentApplyDetService.addOrUpdate(detailVo);
			} catch (Exception e) {
				throw new SysException(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public String updateConfirmPay(List<Map<String, Object>> entityMap, Map<String, Object> paramMapVo) throws DataAccessException {
		try {
			if (paramMapVo.get("p02001").equals("1")) {
				return confirmPayZ(entityMap, paramMapVo);// 专款专项
			} else {
				return confirmPay(entityMap, paramMapVo);// 非专款专项
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 非专项借款支付
	 * 
	 * @param entityMap
	 * @param paramMapVo
	 * @return
	 */
	public String confirmPay(List<Map<String, Object>> entityList, Map<String, Object> paramMapVo) {
		
		Double offset_amount=0.00;
		
		try {
			
			for(Map<String, Object> entryMap:entityList){
				/**
				 * 查询是否有预借
				 * 获取帐表中的借款金额，借款金额大于0表示有预借，反之无预借
				 * 1、budg_borr_proj  2、budg_borr_dept
				 * */
				BudgPaymentApply bpa = budgPaymentApplyMapper.queryBudgPaymentApplyState(entryMap);
				
				if (bpa.getOffset_amount() > 0) {// 大于0,有预借
					
					Double end_amount=bpa.getOffset_amount();//借款金额
					
					//获取明细页面的分录信息,detailData为空则为主页面确认，反之明细页面确认
					if(paramMapVo.containsKey("detailData")){
						
						BudgBorrDept budgBorrDept = budgBorrDeptMapper.queryByCode(paramMapVo);
						
						BudgBorrProj budgBorrProj = budgBorrProjMapper.queryByCode(paramMapVo);
						
						List<Map> detail = ChdJson.fromJsonAsList(Map.class, paramMapVo.get("detailData").toString());
						
						for (Map<String, Object> detailVo : detail) {
							
							String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
							
							if ("".equals(source_id)) {
								
								break;
							
							}
		
							if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").equals("") || !detailVo.containsKey("offset_amount")) {
								
								detailVo.put("offset_amount", "0");
							
							}
							
							if (Double.parseDouble(detailVo.get("offset_amount").toString()) < 0) {
								
								return "{\"error\":\"确认失败 冲抵借款金额必须大于等于0.\"}";
							
							}
							
							if (Double.parseDouble(detailVo.get("offset_amount").toString()) > Double.parseDouble(detailVo.get("payment_amount").toString())) {
								
								return "{\"error\":\"确认失败 冲抵借款金额必须小于等于报销金额.\"}";
							
							}
							
						}
						//【借款（科室）】
						if (paramMapVo.get("proj_id") == null || paramMapVo.get("proj_id").toString().equals("")) {
							
							if (budgBorrDept.getBorrow_amount() != budgBorrDept.getReturn_amount()) {
		
								if (Double.parseDouble(paramMapVo.get("offset_amount_total").toString()) > budgBorrDept.getRemain_amount()) {
									
									return "{\"error\":\"确认失败 冲抵借款金额总和必须小于等于借款余额总和.\"}";
								
								}
		
								for (Map<String, Object> detailVo : detail) {
		
									String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
									
									String payment_item_id = detailVo.get("payment_item_id_no") == null ? "" : detailVo.get("payment_item_id_no").toString();
									
									if ("".equals(source_id)) {
										
										break;
									
									}
		
									if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").toString().equals("0")
											|| detailVo.get("offset_amount").toString().equals("")) {
										/**
										 * X=借款余额（总），
										 * 第一条明细，冲抵借款金额= X-报销金额，X=X-报销金额；
										 * 若X-报销金额>=0&&X>0,冲抵借款金额=报销金额 ，X=X-报销金额；
										 * 若X-报销金额<0&&X==0,冲抵借款金额=0;
									     * 否则 冲抵借款金额=X；
										 * 若还有其他明细则继续
										 * */
										if(end_amount-Double.parseDouble(detailVo.get("payment_amount").toString())>=0&&end_amount>0){
											
											detailVo.put("offset_amount", Double.parseDouble(detailVo.get("payment_amount").toString()));
											
											end_amount=end_amount-Double.parseDouble(detailVo.get("payment_amount").toString());
											
											offset_amount=offset_amount+Double.parseDouble(detailVo.get("payment_amount").toString());
											
										}else if(end_amount-Double.parseDouble(detailVo.get("payment_amount").toString())<0&&end_amount==0){
											
											detailVo.put("offset_amount", 0);
											
										}else{
											
											detailVo.put("offset_amount", end_amount);
											
											offset_amount=offset_amount+end_amount;
										}
									
									} else {
										
										detailVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
									
									}
		
									if (detailVo.get("pay_amount") == null || detailVo.get("pay_amount").toString().equals("0")
											|| detailVo.get("pay_amount").toString().equals("")) {
										
										detailVo.put("pay_amount", Double.parseDouble(detailVo.get("payment_amount").toString())-Double.parseDouble(detailVo.get("offset_amount").toString()));
									
									} else {
										
										detailVo.put("pay_amount", Double.parseDouble(detailVo.get("pay_amount").toString()));
									
									}
									
									detailVo.put("source_id", source_id.split("\\.")[1]);
									
									detailVo.put("payment_item_id", payment_item_id.split("@")[0]);
		
									Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
									
									mapBorrDetAllVo.put("group_id", detailVo.get("group_id"));
									
									mapBorrDetAllVo.put("hos_id", detailVo.get("hos_id"));
									
									mapBorrDetAllVo.put("copy_code", detailVo.get("copy_code"));
									
									mapBorrDetAllVo.put("proj_id", paramMapVo.get("proj_id"));
									
									mapBorrDetAllVo.put("emp_id", paramMapVo.get("emp_id"));
									
									mapBorrDetAllVo.put("source_id", detailVo.get("source_id"));
									
									mapBorrDetAllVo.put("apply_code", detailVo.get("apply_code"));
									
									mapBorrDetAllVo.put("payment_item_id", detailVo.get("payment_item_id"));
									
									mapBorrDetAllVo.put("payment_amount", detailVo.get("payment_amount"));
									
									mapBorrDetAllVo.put("offset_amount", detailVo.get("offset_amount"));
									
									mapBorrDetAllVo.put("pay_amount", detailVo.get("pay_amount"));
									
									budgPaymentApplyDetMapper.updateAmount(mapBorrDetAllVo);
									
									Map<String, Object> updateMainVo = new HashMap<String, Object>();
									
									updateMainVo.put("group_id", paramMapVo.get("group_id"));
									
									updateMainVo.put("hos_id", paramMapVo.get("hos_id"));
									
									updateMainVo.put("copy_code", paramMapVo.get("copy_code"));
									
									updateMainVo.put("emp_id", paramMapVo.get("emp_id"));
									
									updateMainVo.put("remain_amount", end_amount);
									
									updateMainVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
									
									budgBorrDeptMapper.updateF(updateMainVo);

								}
								
							}
							
						} else {
							//【借款（项目）】
							if (budgBorrProj != null) {
								
								if (budgBorrProj.getBorrow_amount() != budgBorrProj.getReturn_amount()) {
									
									if (Double.parseDouble(paramMapVo.get("offset_amount_total").toString()) > budgBorrProj.getRemain_amount()) {
										
										return "{\"error\":\"确认失败 冲抵借款金额总和必须小于等于借款余额总和.\"}";
									
									}
									for (Map<String, Object> detailVo : detail) {
		
										String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
										
										String payment_item_id = detailVo.get("payment_item_id_no") == null ? "" : detailVo.get("payment_item_id_no").toString();
										
										if ("".equals(source_id)) {
											
											break;
										
										}
		
										if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").toString().equals("0")
												|| detailVo.get("offset_amount").toString().equals("")) {
											
											if(end_amount-Double.parseDouble(detailVo.get("payment_amount").toString())>=0&&end_amount>0){
												
												detailVo.put("offset_amount", Double.parseDouble(detailVo.get("payment_amount").toString()));
												
												end_amount=end_amount-Double.parseDouble(detailVo.get("payment_amount").toString());
												
												offset_amount=offset_amount+Double.parseDouble(detailVo.get("payment_amount").toString());
												
											}else if(end_amount-Double.parseDouble(detailVo.get("payment_amount").toString())<0&&end_amount==0){
												
												detailVo.put("offset_amount", 0);
												
											}else{
												
												detailVo.put("offset_amount", end_amount);
												
												offset_amount=offset_amount+end_amount;
											}
										
										} else {
											
											detailVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
										
										}
		
										if (detailVo.get("pay_amount") == null || detailVo.get("pay_amount").toString().equals("0")
												|| detailVo.get("pay_amount").toString().equals("")) {
											
											detailVo.put("pay_amount", Double.parseDouble(detailVo.get("payment_amount").toString())-Double.parseDouble(detailVo.get("offset_amount").toString()));
										
										} else {
											
											detailVo.put("pay_amount", Double.parseDouble(detailVo.get("pay_amount").toString()));
										
										}
										
										detailVo.put("source_id", source_id.split("\\.")[1]);
										
										detailVo.put("payment_item_id", payment_item_id.split("@")[0]);
		
										Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
										
										mapBorrDetAllVo.put("group_id", detailVo.get("group_id"));
										
										mapBorrDetAllVo.put("hos_id", detailVo.get("hos_id"));
										
										mapBorrDetAllVo.put("copy_code", detailVo.get("copy_code"));
										
										mapBorrDetAllVo.put("proj_id", paramMapVo.get("proj_id"));
										
										mapBorrDetAllVo.put("emp_id", paramMapVo.get("emp_id"));
										
										mapBorrDetAllVo.put("source_id", detailVo.get("source_id"));
										
										mapBorrDetAllVo.put("apply_code", detailVo.get("apply_code"));
										
										mapBorrDetAllVo.put("payment_item_id", detailVo.get("payment_item_id"));
										
										mapBorrDetAllVo.put("payment_amount", detailVo.get("payment_amount"));
										
										mapBorrDetAllVo.put("offset_amount", detailVo.get("offset_amount"));
										
										mapBorrDetAllVo.put("pay_amount", detailVo.get("pay_amount"));
										
										budgPaymentApplyDetMapper.updateAmount(mapBorrDetAllVo);
										
										Map<String, Object> updateMainVo = new HashMap<String, Object>();
										
										updateMainVo.put("group_id", paramMapVo.get("group_id"));
										
										updateMainVo.put("hos_id", paramMapVo.get("hos_id"));
										
										updateMainVo.put("copy_code", paramMapVo.get("copy_code"));
										
										updateMainVo.put("emp_id", paramMapVo.get("emp_id"));
										
										updateMainVo.put("proj_id", paramMapVo.get("proj_id"));
										
										updateMainVo.put("remain_amount", end_amount>0?end_amount:0);
										
										updateMainVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
										
										budgBorrProjMapper.updateF(updateMainVo);
										
									}
									
								}
							}
						}
						
						Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
						
						mapBorrDetAllVo.put("group_id", paramMapVo.get("group_id"));
						
						mapBorrDetAllVo.put("hos_id", paramMapVo.get("hos_id"));
						
						mapBorrDetAllVo.put("copy_code", paramMapVo.get("copy_code"));
						
						mapBorrDetAllVo.put("apply_code", paramMapVo.get("apply_code"));
						
						mapBorrDetAllVo.put("offset_amount",offset_amount);
						
						mapBorrDetAllVo.put("pay_amount", end_amount);
						
						budgPaymentApplyMapper.updateAmount(mapBorrDetAllVo);
						
					}else{
						
						for (int i = 0; i < entityList.size(); i++) {
							
							 BudgPaymentApply budgPaymentApply = budgPaymentApplyMapper.queryBudgPaymentApplyByCode(entityList.get(i));
							
							 Map<String,Object> detailVo = new HashMap<String, Object>();
									 
							List<BudgPaymentApplyDet> list = budgPaymentApplyDetMapper.queryForUpdate(entityList.get(i));
							
							for (int j = 0; j < list.size(); j++) {
								
								BudgPaymentApplyDet budgPaymentApplyDet=list.get(j);
								
								if(budgPaymentApply.getProj_id()!=null|| "".equals(budgPaymentApply.getProj_id().toString())){
									
									/**
									 * X=借款余额（总），
									 * 第一条明细，冲抵借款金额= X-报销金额，X=X-报销金额；
									 * 若X-报销金额>=0&&X>0,冲抵借款金额=报销金额 ，X=X-报销金额；
									 * 若X-报销金额<0&&X==0,冲抵借款金额=0;
								     * 否则 冲抵借款金额=X；
									 * 若还有其他明细则继续
									 * */
									if(end_amount-budgPaymentApplyDet.getPayment_amount()>=0&&end_amount>0){
										
										detailVo.put("offset_amount", budgPaymentApplyDet.getPayment_amount());
										
										end_amount=end_amount-budgPaymentApplyDet.getPayment_amount();
										
										offset_amount=offset_amount+budgPaymentApplyDet.getPayment_amount();
										
									}else if(end_amount-budgPaymentApplyDet.getPayment_amount()<0&&end_amount==0){
										
										detailVo.put("offset_amount", 0);
										
									}else{
										
										detailVo.put("offset_amount", end_amount);
										
										offset_amount=offset_amount+end_amount;
									}
								
									detailVo.put("pay_amount", budgPaymentApply.getPayment_amount()-Double.parseDouble(detailVo.get("offset_amount").toString()));
								
									detailVo.put("source_id", budgPaymentApplyDet.getSource_id());
									
									detailVo.put("payment_item_id",budgPaymentApplyDet.getPayment_item_id());
		
									Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
									
									mapBorrDetAllVo.put("group_id", SessionManager.getGroupId());
									
									mapBorrDetAllVo.put("hos_id", SessionManager.getHosId());
									
									mapBorrDetAllVo.put("copy_code", SessionManager.getCopyCode());
									
									mapBorrDetAllVo.put("proj_id", budgPaymentApply.getProj_id());
									
									mapBorrDetAllVo.put("emp_id", budgPaymentApply.getEmp_id());
									
									mapBorrDetAllVo.put("source_id", budgPaymentApplyDet.getSource_id());
									
									mapBorrDetAllVo.put("apply_code", budgPaymentApplyDet.getApply_code());
									
									mapBorrDetAllVo.put("payment_item_id", budgPaymentApplyDet.getPayment_item_id());
									
									mapBorrDetAllVo.put("payment_amount", detailVo.get("payment_amount"));
									
									mapBorrDetAllVo.put("offset_amount", detailVo.get("offset_amount"));
									
									mapBorrDetAllVo.put("pay_amount", detailVo.get("pay_amount"));
									
									budgPaymentApplyDetMapper.updateAmount(mapBorrDetAllVo);
									
									Map<String, Object> updateMainVo = new HashMap<String, Object>();
									
									updateMainVo.put("group_id",SessionManager.getGroupId());
									
									updateMainVo.put("hos_id", SessionManager.getHosId());
									
									updateMainVo.put("copy_code", SessionManager.getCopyCode());
									
									updateMainVo.put("emp_id",budgPaymentApply.getEmp_id());
									
									updateMainVo.put("remain_amount", end_amount>0?end_amount:0);
									
									updateMainVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
									
									budgBorrDeptMapper.updateF(updateMainVo);
									
								}
								
							}
							
						}
						
					}
					
				} else {
					
					String apply_codes = "";
					
					Map<String, Object> mainMapVo = new HashMap<String, Object>();
					
					for (Map<String, Object> map : entityList) {
						
						mainMapVo.put("group_id", map.get("group_id"));
						
						mainMapVo.put("hos_id", map.get("hos_id"));
						
						mainMapVo.put("copy_code", map.get("copy_code"));
						
						apply_codes = apply_codes + "'" + map.get("apply_code") + "',";
						
					}
					
					apply_codes = apply_codes.substring(0, apply_codes.length() - 1);
					
					mainMapVo.put("apply_codes", apply_codes);
					
					List<BudgPaymentApply> mainList = budgPaymentApplyMapper.queryBudgPaymentApply(mainMapVo);
					
					for (BudgPaymentApply budgPaymentApply : mainList) {
						
						Map<String, Object> mainMap = new HashMap<String, Object>();
						
						mainMap.put("group_id", budgPaymentApply.getGroup_id());
						
						mainMap.put("hos_id", budgPaymentApply.getHos_id());
						
						mainMap.put("copy_code", budgPaymentApply.getCopy_code());
						
						mainMap.put("apply_code", budgPaymentApply.getApply_code());
						
						List<BudgPaymentApplyDet> mainDetList = (List<BudgPaymentApplyDet>) budgPaymentApplyDetMapper.queryExists(mainMap);
						
						for (BudgPaymentApplyDet budgPaymentApplyDet : mainDetList) {
							
							Map<String, Object> detailMap = new HashMap<String, Object>();
							
							detailMap.put("group_id", budgPaymentApplyDet.getGroup_id());
							
							detailMap.put("hos_id", budgPaymentApplyDet.getHos_id());
							
							detailMap.put("copy_code", budgPaymentApplyDet.getCopy_code());
							
							detailMap.put("apply_code", budgPaymentApplyDet.getApply_code());
							
							detailMap.put("source_id", budgPaymentApplyDet.getSource_id());
							
							detailMap.put("payment_item_id", budgPaymentApplyDet.getPayment_item_id());
							
							detailMap.put("pay_amount", budgPaymentApplyDet.getPayment_amount());
							
							detailMap.put("payment_amount", budgPaymentApplyDet.getPayment_amount());
							
							detailMap.put("offset_amount", budgPaymentApplyDet.getOffset_amount() == 0 || budgPaymentApplyDet.getOffset_amount() == 0.0 ? "0"
									: budgPaymentApplyDet.getOffset_amount());
							
							budgPaymentApplyDetMapper.updateAmount(detailMap);
							
						}
	
						mainMap.put("pay_amount", budgPaymentApply.getPayment_amount());
						
						mainMap.put("payment_amount", budgPaymentApply.getPayment_amount());
						
						mainMap.put("offset_amount", budgPaymentApply.getOffset_amount() == 0 || budgPaymentApply.getOffset_amount() == 0.0 ? "0"
								: budgPaymentApply.getOffset_amount());
						
						budgPaymentApplyMapper.updateAmount(mainMap);
						
					}
				}
			}
			
			budgPaymentApplyMapper.updateConfirmPay(entityList);
			
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 专项借款支付
	 * 
	 * @param entityMap
	 * @param paramMapVo
	 * @return
	 */
	public String confirmPayZ(List<Map<String, Object>> entityMap, Map<String, Object> paramMapVo) {
		
		String result = "";
		
		try {
			
			if (paramMapVo.containsKey("detailData")) {// 支付详细页面点击确认
				
				Double total_remain_amount = 0.0;
				
				Double total_offset_amount = 0.0;
				
				List<Map> detail = ChdJson.fromJsonAsList(Map.class, paramMapVo.get("detailData").toString());
				
				for (Map<String, Object> detailVo : detail) {
					
					String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
					
					if ("".equals(source_id)) {
						
						break;
					
					}
					
					Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
					
					mapBorrDetAllVo.put("group_id", detailVo.get("group_id"));
					
					mapBorrDetAllVo.put("hos_id", detailVo.get("hos_id"));
					
					mapBorrDetAllVo.put("copy_code", detailVo.get("copy_code"));
					
					mapBorrDetAllVo.put("proj_id", paramMapVo.get("proj_id"));
					
					mapBorrDetAllVo.put("emp_id", paramMapVo.get("emp_id"));
					
					mapBorrDetAllVo.put("source_id", detailVo.get("source_id"));
					
					mapBorrDetAllVo.put("payment_item_id", detailVo.get("payment_item_id"));
					
					if (paramMapVo.get("proj_id") == null || paramMapVo.get("proj_id").toString().equals("")) {
						
						BudgBorrDetDept budgBorrDetDept = budgBorrDetDeptMapper.queryByCode(mapBorrDetAllVo);
						
						if (budgBorrDetDept == null && Double.parseDouble(detailVo.get("offset_amount").toString()) > 0) {
							
							return "{\"error\":\"确认失败 支出项目[" + detailVo.get("payment_item_name").toString() + "]没有借款,冲抵金额必须为0\"}";
						
						}
						
						if (budgBorrDetDept != null) {
							
							if (Double.parseDouble(detailVo.get("offset_amount").toString()) > budgBorrDetDept.getRemain_amount()) {
								
								return "{\"error\":\"确认失败 冲抵借款金额必须小于等于报销金额\"}";
							
							}
						
						}
					
					} else {
						
						BudgBorrDetProj budgBorrDetProj = budgBorrDetProjMapper.queryByCode(mapBorrDetAllVo);
						
						if (budgBorrDetProj == null && Double.parseDouble(detailVo.get("offset_amount").toString()) > 0) {
							
							return "{\"error\":\"确认失败 支出项目[" + detailVo.get("payment_item_name").toString() + "]没有借款,冲抵金额必须为0\"}";
						
						}
						
						if (budgBorrDetProj != null) {
							
							if (Double.parseDouble(detailVo.get("offset_amount").toString()) > budgBorrDetProj.getRemain_amount()) {
								
								return "{\"error\":\"确认失败 冲抵借款金额必须小于等于报销金额\"}";
							
							}
						
						}
					
					}
					
					if (Double.parseDouble(detailVo.get("offset_amount").toString()) < 0) {
						
						return "{\"error\":\"确认失败 冲抵借款金额必须大于等于0.\"}";
					
					}
					
					if (Double.parseDouble(detailVo.get("offset_amount").toString()) > Double.parseDouble(detailVo.get("payment_amount").toString())) {
						
						return "{\"error\":\"确认失败 冲抵借款金额必须小于等于借款余额\"}";
					
					}

				}

				for (Map<String, Object> detailVo : detail) {

					String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
					
					String payment_item_id = detailVo.get("payment_item_id_no") == null ? "" : detailVo.get("payment_item_id_no").toString();
					
					if ("".equals(source_id)) {
						
						break;
					
					}
					
					if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").toString().equals("0")
							|| detailVo.get("offset_amount").toString().equals("")) {
						
						detailVo.put("offset_amount", "0");
					
					} else {
						
						detailVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
					
					}

					if (detailVo.get("pay_amount") == null || detailVo.get("pay_amount").toString().equals("0")
							|| detailVo.get("pay_amount").toString().equals("")) {
						
						detailVo.put("pay_amount", "0");
					
					} else {
						
						detailVo.put("pay_amount", Double.parseDouble(detailVo.get("pay_amount").toString()));
					
					}
					
					detailVo.put("source_id", source_id.split("\\.")[1]);
					
					detailVo.put("payment_item_id", payment_item_id.split("@")[0]);

					Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
					
					mapBorrDetAllVo.put("group_id", detailVo.get("group_id"));
					
					mapBorrDetAllVo.put("hos_id", detailVo.get("hos_id"));
					
					mapBorrDetAllVo.put("copy_code", detailVo.get("copy_code"));
					
					mapBorrDetAllVo.put("proj_id", paramMapVo.get("proj_id"));
					
					mapBorrDetAllVo.put("emp_id", paramMapVo.get("emp_id"));
					
					mapBorrDetAllVo.put("source_id", detailVo.get("source_id"));
					
					mapBorrDetAllVo.put("payment_item_id", detailVo.get("payment_item_id"));
					
					mapBorrDetAllVo.put("payment_amount", detailVo.get("payment_amount"));
					
					mapBorrDetAllVo.put("offset_amount", detailVo.get("offset_amount"));
					
					mapBorrDetAllVo.put("pay_amount", detailVo.get("pay_amount"));

					if (paramMapVo.get("proj_id") == null || paramMapVo.get("proj_id").toString().equals("")) {
						
						BudgBorrDetDept budgBorrDetDept = budgBorrDetDeptMapper.queryByCode(mapBorrDetAllVo);

						if (budgBorrDetDept != null) {// 有借款
							
							if (budgBorrDetDept.getBorrow_amount() == budgBorrDetDept.getReturn_amount()) {
								
								continue;// 已经退还的数据
							} else {
								
								Double remain_amount = budgBorrDetDept.getBorrow_amount() - Double.parseDouble(detailVo.get("offset_amount").toString())
										- budgBorrDetDept.getReturn_amount();
								 
								total_remain_amount += remain_amount;
								
								total_offset_amount += Double.parseDouble(detailVo.get("offset_amount").toString());
								
								if (remain_amount == 0 || remain_amount == 0.0) {
								
									mapBorrDetAllVo.put("remain_amount", "0");
								
								} else {
									
									mapBorrDetAllVo.put("remain_amount", remain_amount);
								
								}
								
								if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").toString().equals("0")
										|| detailVo.get("offset_amount").toString().equals("")) {
								
									mapBorrDetAllVo.put("offset_amount", "0");
								
								} else {
									
									mapBorrDetAllVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
								
								}
								
								budgBorrDetDeptMapper.update(mapBorrDetAllVo);
							
							}
						
						} else {
							
							continue;// 不存在借款信息
						
						}
					} else {
						
						BudgBorrDetProj budgBorrDetProj = budgBorrDetProjMapper.queryByCode(mapBorrDetAllVo);
						
						if (budgBorrDetProj != null) {// 有借款
							
							if (budgBorrDetProj.getBorrow_amount() == budgBorrDetProj.getReturn_amount()) {
								
								continue;// 已经退还的数据
							
							
							} else {

								Double remain_amount = budgBorrDetProj.getBorrow_amount() - Double.parseDouble(detailVo.get("offset_amount").toString())
										- budgBorrDetProj.getReturn_amount();
								total_remain_amount += remain_amount;
								total_offset_amount += Double.parseDouble(detailVo.get("offset_amount").toString());
								if (remain_amount == 0 || remain_amount == 0.0) {
									mapBorrDetAllVo.put("remain_amount", "0");
								} else {
									mapBorrDetAllVo.put("remain_amount", remain_amount);
								}
								if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").toString().equals("0")
										|| detailVo.get("offset_amount").toString().equals("")) {
									mapBorrDetAllVo.put("offset_amount", "0");
								} else {
									mapBorrDetAllVo.put("offset_amount", Double.parseDouble(detailVo.get("offset_amount").toString()));
								}
								budgBorrDetProjMapper.update(mapBorrDetAllVo);
							}
						} else {
							continue;// 不存在借款信息
						}
					}
				}
				tjBudgPaymentApplyAndDet(paramMapVo);
				if (paramMapVo.get("proj_id") == null || paramMapVo.get("proj_id").toString().equals("")) {
					Map<String, Object> updateMainVo = new HashMap<String, Object>();
					updateMainVo.put("group_id", paramMapVo.get("group_id"));
					updateMainVo.put("hos_id", paramMapVo.get("hos_id"));
					updateMainVo.put("copy_code", paramMapVo.get("copy_code"));
					updateMainVo.put("emp_id", paramMapVo.get("emp_id"));

					if (total_remain_amount == 0 || total_remain_amount == 0.0) {
						updateMainVo.put("remain_amount", "0");
					} else {
						updateMainVo.put("remain_amount", total_remain_amount);
					}

					if (total_offset_amount == 0 || total_offset_amount == 0.0) {
						updateMainVo.put("offset_amount", "0");
					} else {
						updateMainVo.put("offset_amount", total_offset_amount);
					}

					if ((total_remain_amount != 0 || total_offset_amount != 0) || (total_remain_amount != 0.0 || total_offset_amount != 0.0)) {
						budgBorrDeptMapper.update(updateMainVo);
					}
				} else {
					Map<String, Object> updateMainVo = new HashMap<String, Object>();
					updateMainVo.put("group_id", paramMapVo.get("group_id"));
					updateMainVo.put("hos_id", paramMapVo.get("hos_id"));
					updateMainVo.put("copy_code", paramMapVo.get("copy_code"));
					updateMainVo.put("emp_id", paramMapVo.get("emp_id"));
					updateMainVo.put("proj_id", paramMapVo.get("proj_id"));
					if (total_remain_amount == 0 || total_remain_amount == 0.0) {
						updateMainVo.put("remain_amount", "0");
					} else {
						updateMainVo.put("remain_amount", total_remain_amount);
					}

					if (total_offset_amount == 0 || total_offset_amount == 0.0) {
						updateMainVo.put("offset_amount", "0");
					} else {
						updateMainVo.put("offset_amount", total_offset_amount);
					}

					if ((total_remain_amount != 0 || total_offset_amount != 0) || (total_remain_amount != 0.0 || total_offset_amount != 0.0)) {
						budgBorrProjMapper.update(updateMainVo);
					}
				}
			} else {// 主页面批量支付点击确认
				
				String apply_codes = "";
				Map<String, Object> mainMapVo = new HashMap<String, Object>();
				for (Map<String, Object> map : entityMap) {
					mainMapVo.put("group_id", map.get("group_id"));
					mainMapVo.put("hos_id", map.get("hos_id"));
					mainMapVo.put("copy_code", map.get("copy_code"));
					apply_codes = apply_codes + "'" + map.get("apply_code") + "',";
				}
				apply_codes = apply_codes.substring(0, apply_codes.length() - 1);
				mainMapVo.put("apply_codes", apply_codes);
				List<BudgPaymentApply> mainList = budgPaymentApplyMapper.queryBudgPaymentApply(mainMapVo);
				for (BudgPaymentApply budgPaymentApply : mainList) {
					Map<String, Object> mainMap = new HashMap<String, Object>();
					mainMap.put("group_id", budgPaymentApply.getGroup_id());
					mainMap.put("hos_id", budgPaymentApply.getHos_id());
					mainMap.put("copy_code", budgPaymentApply.getCopy_code());
					mainMap.put("apply_code", budgPaymentApply.getApply_code());
					List<BudgPaymentApplyDet> mainDetList = (List<BudgPaymentApplyDet>) budgPaymentApplyDetMapper.queryExists(mainMap);
					for (BudgPaymentApplyDet budgPaymentApplyDet : mainDetList) {
						Map<String, Object> detailMap = new HashMap<String, Object>();
						detailMap.put("group_id", budgPaymentApplyDet.getGroup_id());
						detailMap.put("hos_id", budgPaymentApplyDet.getHos_id());
						detailMap.put("copy_code", budgPaymentApplyDet.getCopy_code());
						detailMap.put("apply_code", budgPaymentApplyDet.getApply_code());
						detailMap.put("source_id", budgPaymentApplyDet.getSource_id());
						detailMap.put("payment_item_id", budgPaymentApplyDet.getPayment_item_id());
						detailMap.put("pay_amount", budgPaymentApplyDet.getPayment_amount());
						detailMap.put("payment_amount", budgPaymentApplyDet.getPayment_amount());
						detailMap.put("offset_amount", budgPaymentApplyDet.getOffset_amount() == 0 || budgPaymentApplyDet.getOffset_amount() == 0.0 ? "0"
								: budgPaymentApplyDet.getOffset_amount());
						budgPaymentApplyDetMapper.updateAmount(detailMap);
					}

					mainMap.put("pay_amount", budgPaymentApply.getPayment_amount());
					mainMap.put("payment_amount", budgPaymentApply.getPayment_amount());
					mainMap.put("offset_amount", budgPaymentApply.getOffset_amount() == 0 || budgPaymentApply.getOffset_amount() == 0.0 ? "0"
							: budgPaymentApply.getOffset_amount());
					budgPaymentApplyMapper.updateAmount(mainMap);
				}
			}
			budgPaymentApplyMapper.updateConfirmPay(entityMap);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public BudgBorrDept queryBudgBorrDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrDeptMapper.queryByCode(entityMap);
	}

	@Override
	public BudgBorrProj queryBudgBorrProjByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrProjMapper.queryByCode(entityMap);
	}

	@Override
	public BudgBorrDetDept queryBudgBorrDetDeptByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrDetProjMapper.queryByCode(entityMap);
	}

	@Override
	public BudgBorrDetProj queryBudgBorrDetProjByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrProjMapper.queryByCode(entityMap);
	}

	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;

	@Override
	public String queryBorrPaymentApplyPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> map = budgPaymentApplyMapper.queryPaymentApplyByPrintTemlate(entityMap);

		List<Map<String, Object>> list = budgPaymentApplyDetMapper.queryPaymentApplyDetByPrintTemlate(entityMap);

		return superPrintService.getMapListByPrintTemplateJson(entityMap, map, list);
	}

	@Override
	public List<Map<String, Object>> queryBudgPaymentApplyPrint(Map<String, Object> entityMap) throws DataAccessException {

			List<Map<String, Object>> list = budgPaymentApplyMapper.queryBudgPaymentApplyPrint(entityMap);

			return list;

	}

	@Override
	public String updateUnConfirmPay(List<Map<String, Object>> entityMap,
			Map<String, Object> paramMapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			String apply_codes = "";
			
			Map<String, Object> mainMapVo = new HashMap<String, Object>();
			
			for (Map<String, Object> map : entityMap) {
				
				mainMapVo.put("group_id", map.get("group_id"));
				
				mainMapVo.put("hos_id", map.get("hos_id"));
				
				mainMapVo.put("copy_code", map.get("copy_code"));
				
				apply_codes = apply_codes + "'" + map.get("apply_code") + "',";
				
			}
			
			apply_codes = apply_codes.substring(0, apply_codes.length() - 1);
			
			mainMapVo.put("apply_codes", apply_codes);
			
			List<BudgPaymentApply> mainList = budgPaymentApplyMapper.queryBudgPaymentApply(mainMapVo);
			
			for (BudgPaymentApply budgPaymentApply : mainList) {
				
				Map<String, Object> mainMap = new HashMap<String, Object>();
				
				mainMap.put("group_id", budgPaymentApply.getGroup_id());
				
				mainMap.put("hos_id", budgPaymentApply.getHos_id());
				
				mainMap.put("copy_code", budgPaymentApply.getCopy_code());
				
				mainMap.put("apply_code", budgPaymentApply.getApply_code());
				
				List<BudgPaymentApplyDet> mainDetList = (List<BudgPaymentApplyDet>) budgPaymentApplyDetMapper.queryExists(mainMap);
				
				for (BudgPaymentApplyDet budgPaymentApplyDet : mainDetList) {
					
					Map<String, Object> detailMap = new HashMap<String, Object>();
					
					detailMap.put("group_id", budgPaymentApplyDet.getGroup_id());
					
					detailMap.put("hos_id", budgPaymentApplyDet.getHos_id());
					
					detailMap.put("copy_code", budgPaymentApplyDet.getCopy_code());
					
					detailMap.put("apply_code", budgPaymentApplyDet.getApply_code());
					
					detailMap.put("source_id", budgPaymentApplyDet.getSource_id());
					
					detailMap.put("payment_item_id", budgPaymentApplyDet.getPayment_item_id());
					
					detailMap.put("pay_amount", "0");
					
					detailMap.put("payment_amount", budgPaymentApplyDet.getPayment_amount());
					
					detailMap.put("offset_amount", "0");
					
					budgPaymentApplyDetMapper.updateAmount(detailMap);
					
				}

				mainMap.put("pay_amount", "0");
				
				mainMap.put("payment_amount", budgPaymentApply.getPayment_amount());
				
				mainMap.put("offset_amount","0");
				
				budgPaymentApplyMapper.updateAmount(mainMap);
				
				mainMap.put("emp_id",budgPaymentApply.getEmp_id());
				
				if(budgPaymentApply.getProj_id()!= null){
					
					mainMap.put("proj_id",budgPaymentApply.getProj_id());
				}
				
				mainMap.put("remain_money","1");
				
				budgBorrProjMapper.update(mainMap);
				
				budgBorrDeptMapper.update(mainMap);
				
			}
			
		budgPaymentApplyMapper.updateConfirmPay(entityMap);
		
		String res="";
		
		for (int i = 0; i < entityMap.size(); i++) {
				
			Map<String, Object> map=budgPaymentApplyMapper.queryBudgPaymentApplyByBusiType(entityMap.get(i));
			
			if(map!=null){
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				mapVo.put("vouch_id", map.get("VOUCH_ID"));
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("user_id", SessionManager.getUserId());
				
				res=superVouchService.deleteSuperVouchByVouchId(mapVo);
				
				if(res!=null && !res.equals("ok")){
					
					if(i==0)res="{\"error\":\""+res+"\",\"state\":\"false\"}";
					
					else res="{\"msg\":\""+res+"\",\"state\":\"true\"}";
					
					break;
					
				}else{
					
					budgPaymentApplyMapper.deleteBudgPaymentApplyByBusiType(entityMap.get(i));
				}
				
			}else{
				
				res="ok";
			}
			
		}
		
		if(res!=null && res.equals("ok")){
			
			res="{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
			
		}
			
		return res;
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	//报销申请  模板打印
	@Override
	public Map<String, Object> queryBorrPaymentApplyPrintTemlateNew(Map<String, Object> entityMap) throws DataAccessException { 
		 
			try{
				
				 Map<String,Object> reMap=new HashMap<String,Object>();
				 
				 WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				 
				 BudgPaymentApplyDetMapper budgPaymentApplyDetMapper = (BudgPaymentApplyDetMapper)context.getBean("budgPaymentApplyDetMapper");
					
				 Map<String,Object> mapMian = budgPaymentApplyDetMapper.queryPaymentApplyDetByPrintTemlateMain(entityMap);
				 
				 List<Map<String, Object>> mapDetail = budgPaymentApplyDetMapper.queryPaymentApplyDetByPrintTemlate(entityMap);
				 
				reMap.put("main", mapMian);
				
				reMap.put("detail", mapDetail); 
				
				return reMap; 
				
			}catch(Exception e){
				
				logger.error(e.getMessage(),e);
				
				throw new SysException(e.getMessage());
				
			}
	}
	
	/**
	 * 用户借款下拉框
	 */
	@Override
	public String queryUseApplyCode(Map<String, Object> entityMap) {
		return JSON.toJSONString(budgPaymentApplyMapper.queryUseApplyCode(entityMap));
	}
	
	
	/**
	 * 选择借款号  use_apply_code 自动带出明细信息
	 */
	@Override
	public String queryMoneyApplyDet(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = budgPaymentApplyMapper.queryMoneyApplyDet(mapVo);
		return ChdJson.toJson(list);
	}

	
	/**
	 * 跳转查询所需要的数据
	 */
	@Override
	public Map<String, Object> queryAddPageData(HashMap<String, Object> mapVo) {
		
		Map<String,Object> map = budgPaymentApplyMapper.queryAddPageData(mapVo); 
		 
		return map;
	}

	
	
	/**
	 * 查询职工银行卡号 下拉框
	 */
	@Override
	public String queryGetEmpCardNoSelect(Map<String, Object> entityMap) {

		return JSON.toJSONString(budgPaymentApplyMapper.queryGetEmpCardNoSelect(entityMap));
	}

	@Override
	public Map<String, Object> queryBudgPro(HashMap<String, Object> mapData) {
		Map<String,Object> map = budgPaymentApplyMapper.queryBudgPro(mapData);
		return map;
	}

	/**
	 * excel导入报销申请
	 */
	@Override
	public String importBudgPaymentApply(Map<String, Object> paraMap) throws DataAccessException {
		String userEmpId = SessionManager.getEmpCode();// 其实是empId
		if(StringUtils.isEmpty(userEmpId)){
			return "{\"error\":\"没有用户没有绑定职工，请先绑定职工.\",\"state\":\"false\"}";
		}
		
		String userId = SessionManager.getUserId();
		String groupId = SessionManager.getGroupId();
		String hosId = SessionManager.getHosId();
		String copyCode = SessionManager.getCopyCode();
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("group_id", groupId);
		pMap.put("hos_id", hosId);
		pMap.put("copy_code", copyCode);
		String state = paraMap.get("state").toString();
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paraMap);// excel表格数据
			if(list != null && list.size() > 0){
				WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
				DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
				
				List<String> mustFillIsNull = new ArrayList<String>();// 必填格是空的格子行列号信息
				List<String> deptNotFound = new ArrayList<String>();// 没有找到的科室
				List<String> empNotFound = new ArrayList<String>();// 没有找到的职工
				List<String> projNotFound = new ArrayList<String>();// 没有找到的项目
				List<String> sourceNotFound = new ArrayList<String>();// 没有找到的项目
				List<String> paymentItemNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> useApplyNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> payWayNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> cardNoNotFound = new ArrayList<String>();// 没有找到的职工的银行卡号
				List<String> dateFormatError = new ArrayList<String>();// 日期格式不正确
				List<String> numberFormatError = new ArrayList<String>();// 数字格式不正确
				
				List<Map<String, Object>> saveMainList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> saveDetailList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> sMap = readyResourceMap(pMap);
				Map<String, DeptDict> deptDictMap = (Map<String, DeptDict>) sMap.get("dept");// 科室Map
				Map<String, EmpDict> empDictMap = (Map<String, EmpDict>) sMap.get("emp");// 职工Map
				Map<String, ProjDict> projDictMap = (Map<String, ProjDict>) sMap.get("proj");// 项目Map
				Map<String, Object> sourceMap = (Map<String, Object>) sMap.get("source");// 资金来源Map
				Map<String, BudgPaymentItemDict> paymentItemMap = (Map<String, BudgPaymentItemDict>) sMap.get("payItem");// 支出项目Map
				Map<String, Object> useApplyMap = (Map<String, Object>) sMap.get("useApply");// 用款申请Map
				Map<String, Map<String, Object>> budgUnitMap = (Map<String, Map<String, Object>>) sMap.get("budgUnit");// 收款单位Map
				Map<String, String> payWayMap = (Map<String, String>) sMap.get("payWay");// 支付方式
				
//				Map<String, String> applyMainMap = new HashMap<String, String>();
//				Map<String, String> applyDetailMap = new HashMap<String, String>();
				String applyCode = null;
				
				pMap.put("table_name", "报销申请");
				pMap.put("prefixe", "BXSQ");
				pMap.put("table_code", "BUDG_PAYMENT_APPLY");

				// excel表格数据遍历
				for(Map<String, List<String>> row : list){
//					String rowMain = row.get("dept_code").get(1) 
//								   + row.get("proj_code").get(1)
//								   + row.get("emp_code").get(1)// 报销人
//								   + row.get("phone").get(1) 
//								   + row.get("start_date").get(1) 
//								   + row.get("end_date").get(1)
//								   + row.get("address").get(1) // 出差起讫地址
//								   + row.get("remark").get(1) //报销事由（必填）
//								   + row.get("use_apply_code").get(1);
//					if("04".equals(state)){// 已支付
//						rowMain += row.get("pay_way").get(1);
//					}
//					String rowDetail = rowMain + row.get("source_code").get(1) + row.get("payment_item").get(1);
					
					// 主表
//					if(!applyMainMap.containsKey(rowMain)){
						applyCode = budgNoManagerService.getBillNOSeqNo(pMap);
						budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_PAYMENT_APPLY");
//						applyMainMap.put(rowMain, applyCode);
						
						Map<String, Object> saveMainMap = new HashMap<String, Object>();
						// 科室（必填）
						if(StringUtils.isEmpty(row.get("dept_code").get(1))){
							mustFillIsNull.add(row.get("dept_code").get(0));
						}else if(deptDictMap.get(row.get("dept_code").get(1)) == null){
							if(!deptNotFound.contains(row.get("dept_code").get(1))){
								deptNotFound.add(row.get("dept_code").get(1));
							}
						}else{
							saveMainMap.put("dept_id", deptDictMap.get(row.get("dept_code").get(1)).getDept_id());
							saveMainMap.put("dept_no", deptDictMap.get(row.get("dept_code").get(1)).getDept_no());
						}
						
						// 项目
						if(StringUtils.isNotEmpty(row.get("proj_code").get(1))){
							if(projDictMap.get(row.get("proj_code").get(1)) == null){
								if(!projNotFound.contains(row.get("proj_code").get(1))){
									projNotFound.add(row.get("proj_code").get(1));
								}
							}else{
								saveMainMap.put("proj_id", projDictMap.get(row.get("proj_code").get(1)).getProj_id());
								saveMainMap.put("proj_no", projDictMap.get(row.get("proj_code").get(1)).getProj_no());
							}
						}
						
						// 联系电话
						if(StringUtils.isNotEmpty(row.get("phone").get(1))){
							saveMainMap.put("phone", row.get("phone").get(1));
						}
						
						// 出差起止时间
						if(StringUtils.isNotEmpty(row.get("start_date").get(1)) 
								&& StringUtils.isNotEmpty(row.get("start_date").get(1)) == StringUtils.isNotEmpty(row.get("end_date").get(1))){
							// 出差开始日期
							if(DateUtil.CheckDate(row.get("start_date").get(1))){ 
								saveMainMap.put("start_date", DateUtil.stringToDate1(row.get("start_date").get(1)));
							}else if(!dateFormatError.contains(row.get("start_date").get(1))){
								dateFormatError.add(row.get("start_date").get(1));
							}
							// 出差结束日期
							if(DateUtil.CheckDate(row.get("end_date").get(1))){ 
								saveMainMap.put("end_date", DateUtil.stringToDate1(row.get("end_date").get(1)));
							}else if(!dateFormatError.contains(row.get("end_date").get(1))){
								dateFormatError.add(row.get("end_date").get(1));
							}
						}
						
						// 报销事由（必填）
						if(StringUtils.isEmpty(row.get("remark").get(1))){
							mustFillIsNull.add(row.get("remark").get(0));
						}else{
							saveMainMap.put("remark", row.get("remark").get(1));
						}
						
						// 用款申请
						if(StringUtils.isNotEmpty(row.get("use_apply_code").get(1))){
							if(useApplyMap.get(row.get("use_apply_code").get(1)) == null){
								if(!useApplyNotFound.contains(row.get("use_apply_code").get(1))){
									useApplyNotFound.add(row.get("use_apply_code").get(1));
								}
							}else{
								saveMainMap.put("use_apply_code", row.get("use_apply_code").get(1));// 用款申请单号
							}
						}

						// 出差起讫地址
						if(StringUtils.isNotEmpty(row.get("address").get(1))){
							saveMainMap.put("address", row.get("address").get(1));
						}
						
						// 报销人
						if(StringUtils.isEmpty(row.get("emp_code").get(1))){
							mustFillIsNull.add(row.get("emp_code").get(0));
						}else if(empDictMap.get(row.get("emp_code").get(1)) == null){
							if(!empNotFound.contains(row.get("emp_code").get(1))){
								empNotFound.add(row.get("emp_code").get(1));
							}
						}else{
							saveMainMap.put("emp_id", empDictMap.get(row.get("emp_code").get(1)).getEmp_id());
							saveMainMap.put("emp_no", empDictMap.get(row.get("emp_code").get(1)).getEmp_no());
							saveMainMap.put("oriEmpValue", row.get("emp_code").get(1));// 页面传来的职工信息原始值
						}
						
						// 报销金额（必填）
						if(StringUtils.isEmpty(row.get("payment_amount").get(1))){
							mustFillIsNull.add(row.get("payment_amount").get(0));
						}else if(NumberUtil.isNumeric(row.get("payment_amount").get(1))){
							saveMainMap.put("payment_amount", row.get("payment_amount").get(1));// 报销金额
							saveMainMap.put("pay_amount", row.get("payment_amount").get(1));// 实际支付金额
						}else if(!numberFormatError.contains(row.get("payment_amount").get(1))){
							numberFormatError.add(row.get("payment_amount").get(1));
						}
						
						if("04".equals(state)){// 已支付
							saveMainMap.put("checker", userId);// 审核人
							saveMainMap.put("check_date", new Date());// 审核日期
							saveMainMap.put("payer", userId);// 支付人
							saveMainMap.put("pay_date", new Date());// 支付日期
							
							// 支付方式
							if(StringUtils.isNotEmpty(row.get("pay_way").get(1))){
								if(payWayMap.get(row.get("pay_way").get(1)) == null){
									if(!payWayNotFound.contains(row.get("pay_way").get(1))){
										payWayNotFound.add(row.get("pay_way").get(1));
									}
								}else{
									saveMainMap.put("pay_way", payWayMap.get(row.get("pay_way").get(1)));
								}
							}
						}
						
						saveMainMap.put("group_id", groupId);
						saveMainMap.put("hos_id", hosId);
						saveMainMap.put("copy_code", copyCode);
						saveMainMap.put("apply_code", applyCode);
						saveMainMap.put("apply_date", new Date());
						saveMainMap.put("maker", userId);// 制单人
						saveMainMap.put("make_date", new Date());// 制单日期
						saveMainMap.put("state", state);// 状态
						
						saveMainList.add(saveMainMap);
//					}
					// 主表 end
					
					// 明细表
//					if(!applyDetailMap.containsKey(rowDetail)){
//						applyDetailMap.put(rowDetail, rowMain);
						
						Map<String, Object> saveDetailMap = new HashMap<String, Object>();
						// 申请编号
//						saveDetailMap.put("apply_code", applyMainMap.get(applyDetailMap.get(rowDetail)));
						saveDetailMap.put("apply_code", applyCode);
						
						// 资金来源（必填）
						if(StringUtils.isEmpty(row.get("source_code").get(1))){
							mustFillIsNull.add(row.get("source_code").get(0));
						}else if(sourceMap.get(row.get("source_code").get(1)) == null){
							if(!sourceNotFound.contains(row.get("source_code").get(1))){
								sourceNotFound.add(row.get("source_code").get(1));
							}
						}else{
							saveDetailMap.put("source_id", sourceMap.get(row.get("source_code").get(1)));
						}
						
						// 支出项目（必填）
						if(StringUtils.isEmpty(row.get("payment_item").get(1))){
							mustFillIsNull.add(row.get("payment_item").get(0));
						}else if(paymentItemMap.get(row.get("payment_item").get(1)) == null){
							if(!paymentItemNotFound.contains(row.get("payment_item").get(1))){
								paymentItemNotFound.add(row.get("payment_item").get(1));
							}
						}else{
							saveDetailMap.put("payment_item_id", paymentItemMap.get(row.get("payment_item").get(1)).getPayment_item_id());
							saveDetailMap.put("payment_item_no", paymentItemMap.get(row.get("payment_item").get(1)).getPayment_item_no());
						}
						
						// 报销金额（必填）
						if(StringUtils.isEmpty(row.get("payment_amount").get(1))){
							mustFillIsNull.add(row.get("payment_amount").get(0));
						}else if(NumberUtil.isNumeric(row.get("payment_amount").get(1))){
							saveDetailMap.put("payment_amount", row.get("payment_amount").get(1));// 报销金额
							saveDetailMap.put("pay_amount", row.get("payment_amount").get(1));// 实际支付金额
						}else if(!numberFormatError.contains(row.get("payment_amount").get(1))){
							numberFormatError.add(row.get("payment_amount").get(1));
						}
						
						// 单据张数
						if(StringUtils.isNotEmpty(row.get("amount").get(1))){
							if(NumberUtil.isNumeric(row.get("amount").get(1))){
								saveDetailMap.put("amount", row.get("amount").get(1));
							}else if(!numberFormatError.contains(row.get("amount").get(1))){
								numberFormatError.add(row.get("amount").get(1));
							}
						}
						
						// 说明
						if(StringUtils.isNotEmpty(row.get("bx_remark").get(1))){
							saveDetailMap.put("remark", row.get("bx_remark").get(1));
						}
						saveDetailMap.put("group_id", groupId);
						saveDetailMap.put("hos_id", hosId);
						saveDetailMap.put("copy_code", copyCode);
						
						saveDetailList.add(saveDetailMap);
//					}
					// 明细表 end
 				}// excel表格数据遍历 end
				
				// 不能导入消息描述*******************
				String mustFillMsg = ExcelReader.mustFillDescribe(mustFillIsNull);
				if(mustFillMsg != null){
					// 回滚事务
					transactionManager.rollback(status);
					return "{\"warn\":\"" + mustFillMsg + "\",\"state\":\"false\"}";
				}
				
				String msg = "";
				StringBuilder msgPrefix = null;
				msgPrefix = new StringBuilder("以下【科室】不存在：");
				msg += msgDescribe(msgPrefix, deptNotFound);
			
				msgPrefix = new StringBuilder("以下【项目】不存在：");
				msg += msgDescribe(msgPrefix, projNotFound);
			
				msgPrefix = new StringBuilder("以下【资金来源】不存在：");
				msg += msgDescribe(msgPrefix, sourceNotFound);
			
				msgPrefix = new StringBuilder("以下【支出项目】不存在或没有权限：");
				msg += msgDescribe(msgPrefix, paymentItemNotFound);
			
				msgPrefix = new StringBuilder("以下【用款申请】不存在：");
				msg += msgDescribe(msgPrefix, useApplyNotFound);
			
				msgPrefix = new StringBuilder("以下【用款申请】不存在：");
				msg += msgDescribe(msgPrefix, useApplyNotFound);
			
				msgPrefix = new StringBuilder("以下【支付方式】不存在：");
				msg += msgDescribe(msgPrefix, payWayNotFound);

				msgPrefix = new StringBuilder("以下【出差起止日期】格式不正确：");
				msg += msgDescribe(msgPrefix, dateFormatError);
			
				msgPrefix = new StringBuilder("以下【数字】格式不正确：");
				msg += msgDescribe(msgPrefix, numberFormatError);
				
				if(StringUtils.isNotEmpty(msg)){
					// 回滚事务
					transactionManager.rollback(status);
					msg = msg.substring(0, msg.lastIndexOf("<br/>"));
					return "{\"error\":\"" + msg + "\",\"state\":\"false\"}";
				}

				// 填充银行卡号
				List<Map<String, Object>> cardNoList = budgPaymentApplyMapper.queryAccountListByEmpIdList(saveMainList);
				if(cardNoList.isEmpty()){
					return "{\"warn\":\"请选给职工设置银行卡号.\",\"state\":\"false\"}";
				}else{
					Map<String, String> cardNoMap = new HashMap<String, String>();
					for(Map<String, Object> item : cardNoList){
						cardNoMap.put(item.get("emp_id").toString(), item.get("account_code").toString());
					}
					for(Map<String, Object> row : saveMainList){
						if(cardNoMap.containsKey(row.get("emp_id").toString())){
							row.put("card_no", cardNoMap.get("emp_id"));
						}else if(!cardNoNotFound.contains(row.get("oriEmpValue").toString())){
							cardNoNotFound.add(row.get("oriEmpValue").toString());
						}
					}
				}
				
				msgPrefix = new StringBuilder("以下职工的【银行账号】没有找到：");
				msg = msgDescribe(msgPrefix, numberFormatError);
				if(StringUtils.isNotEmpty(msg)){
					// 回滚事务
					transactionManager.rollback(status);
					msg = msg.substring(0, msg.lastIndexOf("<br/>"));
					return "{\"error\":\"" + msg + "\",\"state\":\"false\"}";
				}
				// ******************************
				
				// 单条申请总报销金额
//				Map<String, Double> paymentAmountMap = new HashMap<String, Double>();
//				for(Map<String, Object> det : saveDetailList){
//					if(paymentAmountMap.containsKey(det.get("apply_code").toString())){
//						double pa = NumberUtil.add(paymentAmountMap.get(det.get("apply_code").toString()), Double.valueOf(det.get("payment_amount").toString()));
//						paymentAmountMap.put(det.get("apply_code").toString(), pa);
//					}else{
//						paymentAmountMap.put(det.get("apply_code").toString(), Double.valueOf(det.get("payment_amount").toString()));
//					}
//				}
//				for(Map<String, Object> mai : saveMainList){
//					mai.put("payment_amount", paymentAmountMap.get(mai.get("apply_code").toString()));
//					mai.put("pay_amount", paymentAmountMap.get(mai.get("apply_code").toString()));// 实际支付金额
//				}
				
				// 保存
				budgPaymentApplyMapper.addBatchBudgPaymentApply(saveMainList);
				budgPaymentApplyDetMapper.addBatch(saveDetailList);
				
				// 提交事务
				transactionManager.commit(status);
				return "{\"msg\":\"导入成功.\",\"state\":\"false\"}";
			}
			
			return "{\"warn\":\"没有可导入数据.\",\"state\":\"false\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	private String msgDescribe(StringBuilder msg, List<String> list){
		if(!list.isEmpty()){
			for(String str : list){
				msg.append("<br/>").append(str);
			}
			return msg.append("<br/>").toString();
		}
		return "";
	}
	
	/**
	 * 组装导入费用申请时需要用到的map
	 */
	private Map<String, Object> readyResourceMap(Map<String, Object> paraMap){
		Map<String, Object> map = new HashMap<String, Object>();
		// 组装科室Map
		List<DeptDict> deptDictList = deptDictMapper.queryDeptDict(paraMap);
		Map<String, DeptDict> deptDictMap = new HashMap<String, DeptDict>();
		for(DeptDict dept : deptDictList){
			deptDictMap.put(dept.getDept_code(), dept);
			deptDictMap.put(dept.getDept_name(), dept);
		}
		map.put("dept", deptDictMap);
		
		// 组装职工Map
		List<EmpDict> empDictList = empDictMapper.queryEmpDict(paraMap);
		Map<String, EmpDict> empDictMap = new HashMap<String, EmpDict>();
		for(EmpDict emp : empDictList){
			empDictMap.put(emp.getEmp_code(), emp);
			empDictMap.put(emp.getEmp_name(), emp);
		}
		map.put("emp", empDictMap);
		
		// 组装项目Map
		List<ProjDict> projDictList = projDictMapper.queryProjDict(paraMap);
		Map<String, ProjDict> projDictMap = new HashMap<String, ProjDict>();
		for(ProjDict proj : projDictList){
			projDictMap.put(proj.getProj_code(), proj);
			projDictMap.put(proj.getProj_name(), proj);
		}
		map.put("proj", projDictMap);
		
		// 组装资金来源Map
		List<Source> sourceList = sourceMapper.querySource(paraMap);
		Map<String, Object> sourceMap = new HashMap<String, Object>();
		for(Source source : sourceList){
			sourceMap.put(source.getSource_code(), source.getSource_id());
			sourceMap.put(source.getSource_name(), source.getSource_id());
		}
		map.put("source", sourceMap);
		
		// 组装支出项目Map（必填）
		paraMap.put("is_write", "1");
		paraMap.put("user_id", SessionManager.getUserId());
		List<BudgPaymentItemDict> paymentItemList = budgPaymentItemDictMapper.queryByIsWriteOrIsRead(paraMap);
		Map<String, BudgPaymentItemDict> paymentItemMap = new HashMap<String, BudgPaymentItemDict>();
		for(BudgPaymentItemDict payItem : paymentItemList){
			paymentItemMap.put(payItem.getPayment_item_code(), payItem);
			paymentItemMap.put(payItem.getPayment_item_name(), payItem);
		}
		map.put("payItem", paymentItemMap);
		
		// 组装用款申请Map
		List<Map<String, Object>> useApplyList = budgPaymentApplyMapper.queryUseApplyCode(paraMap);
		Map<String, Object> useApplyMap = new HashMap<String, Object>();
		for(Map<String, Object> useApply : useApplyList){
			useApplyMap.put(useApply.get("id").toString(), useApply.get("text"));
		}
		map.put("useApply", useApplyMap);
		
		// 组装支付方式Map
		List<HrpAccSelect> payWayList = hrpAccSelectMapper.queryPayType(paraMap);
		Map<String, String> payWayMap = new HashMap<String, String>();
		for(HrpAccSelect has : payWayList){
			payWayMap.put(has.getText(), has.getId());
		}
		map.put("payWay", payWayMap);
		
		return map;
	}
	
//	/**
//	 * 是否专款专用
//	 * @return
//	 */
//	private String isEarmarked(){
//		Map<String, Object> pMap = new HashMap<String, Object>();
//		pMap.put("group_id", SessionManager.getGroupId());
//		pMap.put("hos_id", SessionManager.getHosId());
//		pMap.put("copy_code", SessionManager.getCopyCode());
//		pMap.put("mod_code", "02");
//		pMap.put("para_code", "02001");
//		
//		AccPara para = accParaMapper.queryByUniqueness(pMap);
//		if(para != null){
//			return para.getPara_value();
//		}
//		return "0";
//	}
}

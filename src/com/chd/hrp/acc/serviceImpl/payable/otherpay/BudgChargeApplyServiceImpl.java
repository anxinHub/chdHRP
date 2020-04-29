package com.chd.hrp.acc.serviceImpl.payable.otherpay;
  
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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.dao.autovouch.AccAutoVouchMaintainMapper;
import com.chd.hrp.acc.dao.payable.base.BudgPaymentItemDictMapper;
import com.chd.hrp.acc.dao.payable.otherpay.BudgChargeApplyDetMapper;
import com.chd.hrp.acc.dao.payable.otherpay.BudgChargeApplyMapper;
import com.chd.hrp.acc.dao.payable.otherpay.BudgUnitMapper;
import com.chd.hrp.acc.dao.payable.reimbursemt.BudgPaymentApplyMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.payable.BudgChargeApply;
import com.chd.hrp.acc.entity.payable.BudgPaymentItemDict;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyDetService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyService;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.dao.ProjDictMapper;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.entity.ProjDict;
import com.chd.hrp.sys.entity.Source;
import com.github.pagehelper.PageInfo;

@Service("budgChargeApplyService")
public class BudgChargeApplyServiceImpl implements BudgChargeApplyService {

	private static Logger logger = Logger.getLogger(BudgChargeApplyServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgChargeApplyMapper")
	private final BudgChargeApplyMapper budgChargeApplyMapper = null;

	@Resource(name = "budgChargeApplyDetMapper")
	private final BudgChargeApplyDetMapper budgChargeApplyDetMapper = null;

	@Resource(name = "budgNoManagerService")
	private final BudgNoManagerService budgNoManagerService = null;

	@Resource(name = "budgChargeApplyDetService")
	private final BudgChargeApplyDetService budgChargeApplyDetService = null;
	
	@Resource(name = "accAutoVouchMaintainMapper")
	private final AccAutoVouchMaintainMapper accAutoVouchMaintainMapper = null;
	
	@Resource(name = "budgUnitMapper")
	private BudgUnitMapper budgUnitMapper = null;
	
	@Resource(name = "deptDictMapper")
	private DeptDictMapper deptDictMapper = null;
	
	@Resource(name = "projDictMapper")
	private ProjDictMapper projDictMapper = null;
	
	@Resource(name = "sourceMapper")
	private SourceMapper sourceMapper = null;
	
	@Resource(name = "budgPaymentItemDictMapper")
	private BudgPaymentItemDictMapper budgPaymentItemDictMapper = null;
	
	@Resource(name = "budgPaymentApplyMapper")
	private BudgPaymentApplyMapper budgPaymentApplyMapper = null;
	
	@Resource(name = "empDictMapper")
	private EmpDictMapper empDictMapper = null;
	
	@Resource(name = "hrpAccSelectMapper")
	private HrpAccSelectMapper hrpAccSelectMapper = null;

	@Override
	public String addBudgChargeApply(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			//1.处理主表数据
			//如果 flag = 1 则用户选择保存并提交功能  直接更改状态为提交状态
			if("1".equals(entityMap.get("flag"))){
				entityMap.put("state", "02");
			}else{
				//否则 为新建状态
				entityMap.put("state", "01");
			}
			entityMap.put("table_name", "费用申请");
			entityMap.put("prefixe", "FYSQ");
			entityMap.put("table_code", "BUDG_CHARGE_APPLY");
			
			//1.1生成申请单号
			String apply_code = budgNoManagerService.getBillNOSeqNo(entityMap);
			entityMap.put("apply_code", apply_code);
			entityMap.put("maker", SessionManager.getUserId());
			entityMap.put("make_date", new Date());
			
			int state = budgChargeApplyMapper.addBudgChargeApply(entityMap);
			if (state > 0) {
				budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_CHARGE_APPLY");
			}
			
			//2.处理明细数据
			List<Map<String,Object>> applyDetlist = new ArrayList<Map<String,Object>>();
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {
				
				if(detailVo.containsKey("bank_location") 
						&& null != detailVo.get("unit_name") 
						&& !"".equals(detailVo.get("unit_name"))){
					
//					int count = budgUnitMapper.queryBankNetIcbcCount(detailVo);
//					if (count == 0) {
//						return "{\"warn\":\"开户行所在地不存在,请通过下拉框选择.\",\"state\":\"false\"}";
//					}
					
					detailVo.put("group_id", SessionManager.getGroupId());
					
					detailVo.put("hos_id",  SessionManager.getHosId());
					
					//1.2查询往来单位信息是否存在
					Map<String,Object> budgUnit = budgUnitMapper.queryBudgUnitByCode(detailVo);
					
					if(budgUnit == null){
						//查询序列
						int unit_id = budgUnitMapper.queryBudgUnitSeqNextVal();
						
						Map<String,Object> budgUnitmap = new HashMap<String,Object>();
						budgUnitmap.put("group_id", SessionManager.getGroupId());
						budgUnitmap.put("hos_id",  SessionManager.getHosId());
						budgUnitmap.put("unit_id", unit_id);
						detailVo.put("unit_id", String.valueOf(unit_id));
						budgUnitmap.put("unit_name", detailVo.get("unit_name"));
						budgUnitmap.put("card_no", detailVo.get("card_no"));
						budgUnitmap.put("bank_name", detailVo.get("bank_name"));
						budgUnitmap.put("bank_location", detailVo.get("bank_location"));
						budgUnitmap.put("is_stop", 0);
						
						budgUnitMapper.addBudgUnit(budgUnitmap);
						
					}else{
						detailVo.put("unit_id", budgUnit.get("unit_id"));
						budgUnitMapper.updateBudgUnit(detailVo);
					}
					
					Map<String,Object> applyDetMap = new HashMap<String,Object>();

					applyDetMap.put("group_id", SessionManager.getGroupId());
					
					applyDetMap.put("hos_id", SessionManager.getHosId());
					
					applyDetMap.put("copy_code", SessionManager.getCopyCode());
					
					applyDetMap.put("apply_code", apply_code);
					
					applyDetMap.put("unit_id", detailVo.get("unit_id"));
					
					applyDetMap.put("card_no", detailVo.get("card_no"));
					
					applyDetMap.put("bank_name", detailVo.get("bank_name"));
					
					applyDetMap.put("bank_location", detailVo.get("bank_location"));
					
					applyDetMap.put("payment_amount", detailVo.get("payment_amount"));
					
					applyDetMap.put("remark", detailVo.get("remark"));
					applyDetMap.put("source_id", entityMap.get("source_id"));
					applyDetMap.put("payment_item_id", entityMap.get("payment_item_id"));
					applyDetMap.put("payment_item_no", entityMap.get("payment_item_no"));
					
					applyDetlist.add(applyDetMap);
					
				}
			}
			
			if(applyDetlist.size() > 0){
				
				budgChargeApplyDetMapper.addBatch(applyDetlist);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\""
					+ ",\"group_id\":\"" + entityMap.get("group_id") + "\""
					+ ",\"hos_id\":\"" + entityMap.get("hos_id") + "\""
					+ ",\"copy_code\":\"" + entityMap.get("copy_code") + "\""
					+ ",\"payment_amount\":\"" + entityMap.get("payment_amount") + "\""
					+ ",\"apply_code\":\"" + apply_code + "\""
					+ "}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("\"error\":\"操作失败.\",\"state\":\"false\"");

		}
	}


	@Override
	public String updateBudgChargeApply(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			
			//1.处理明细数据
			List<Map<String,Object>> applyDetlist = new ArrayList<Map<String,Object>>();
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {
				
				if(detailVo.containsKey("bank_location") &&null != detailVo.get("unit_name")&&!"".equals(detailVo.get("unit_name"))){
					Map<String,Object> applyDetMap = new HashMap<String,Object>();
	
					String source_id = entityMap.get("source_id") == null ? "" : entityMap.get("source_id").toString();
					
					String payment_item_id = entityMap.get("payment_item_id") == null ? "" : entityMap.get("payment_item_id").toString();
					if("".equals(source_id)){
						continue;
					}
					
					detailVo.put("group_id", SessionManager.getGroupId());
					
					detailVo.put("hos_id",  SessionManager.getHosId());
					
					//1.2查询往来单位信息是否存在
					Map<String,Object> budgUnit = budgUnitMapper.queryBudgUnitByCode(detailVo);
					
					if(budgUnit == null){
						//查询序列
						int unit_id = budgUnitMapper.queryBudgUnitSeqNextVal();
						
						Map<String,Object> budgUnitmap = new HashMap<String,Object>();
						budgUnitmap.put("group_id", SessionManager.getGroupId());
						budgUnitmap.put("hos_id",  SessionManager.getHosId());
						budgUnitmap.put("unit_id", unit_id);
						detailVo.put("unit_id", String.valueOf(unit_id));
						budgUnitmap.put("unit_name", detailVo.get("unit_name"));
						budgUnitmap.put("card_no", detailVo.get("card_no"));
						budgUnitmap.put("bank_name", detailVo.get("bank_name"));
						budgUnitmap.put("bank_location", detailVo.get("bank_location"));
						budgUnitmap.put("is_stop", 0);
						
						budgUnitMapper.addBudgUnit(budgUnitmap);
						
					}else{
						detailVo.put("unit_id", budgUnit.get("unit_id"));
						budgUnitMapper.updateBudgUnit(detailVo);
					}

					applyDetMap.put("group_id", SessionManager.getGroupId());
					
					applyDetMap.put("hos_id", SessionManager.getHosId());
					
					applyDetMap.put("copy_code", SessionManager.getCopyCode());
					
					applyDetMap.put("apply_code", entityMap.get("apply_code"));
					
					applyDetMap.put("unit_id", detailVo.get("unit_id"));
					
					applyDetMap.put("card_no", detailVo.get("card_no"));
					
					applyDetMap.put("bank_name", detailVo.get("bank_name"));
					
					applyDetMap.put("bank_location", detailVo.get("bank_location"));
					
					applyDetMap.put("payment_amount", detailVo.get("payment_amount"));
					
					applyDetMap.put("remark", detailVo.get("remark"));
					applyDetMap.put("source_id", entityMap.get("source_id"));
					applyDetMap.put("payment_item_id", entityMap.get("payment_item_id"));
					applyDetMap.put("payment_item_no", entityMap.get("payment_item_no"));
					
					applyDetlist.add(applyDetMap);
					
					
				}
			}
			
			
			if(applyDetlist.size() > 0){
				
				budgChargeApplyDetMapper.delete(entityMap);
				
				budgChargeApplyDetMapper.addBatch(applyDetlist);
			}
			
			budgChargeApplyMapper.updateBudgChargeApply(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}


	@Override
	public String deleteBudgChargeApply(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = budgChargeApplyMapper.deleteBudgChargeApply(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBatchBudgChargeApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgChargeApplyDetMapper.deleteBatch(entityMap);
			budgChargeApplyMapper.deleteBatchBudgChargeApply(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgChargeApply(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgChargeApply> list = budgChargeApplyMapper.queryBudgChargeApply(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgChargeApply> list = budgChargeApplyMapper.queryBudgChargeApply(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public BudgChargeApply queryBudgChargeApplyByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgChargeApplyMapper.queryBudgChargeApplyByCode(entityMap);
	}


	@Override
	public String auditBudgChargeApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgChargeApplyMapper.auditBudgChargeApply(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String reAuditBudgChargeApply(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgChargeApplyMapper.reAuditBudgChargeApply(entityMap);

			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	

	@Override
	public String updateBudgChargeApplyState(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			budgChargeApplyMapper.submitBudgChargeApply(entityMap);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
	}


	@Override
	public String confirmBudgChargeApply(List<Map<String, Object>> entityList,Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			if(entityMap.get("detailData") != null){
				
				//1.2查询往来单位信息是否存在
				Map<String,Object> budgUnit = budgUnitMapper.queryBudgUnitByCode(entityMap);
				if(budgUnit == null){
					//查询序列
					int unit_id = budgUnitMapper.queryBudgUnitSeqNextVal();
					Map<String,Object> budgUnitmap = new HashMap<String,Object>();
					budgUnitmap.put("group_id", entityMap.get("group_id"));
					budgUnitmap.put("hos_id", entityMap.get("hos_id"));
					budgUnitmap.put("unit_id", unit_id);
					budgUnitmap.put("unit_name", entityMap.get("unit_name"));
					budgUnitmap.put("card_no", entityMap.get("card_no"));
					budgUnitmap.put("bank_name", entityMap.get("bank_name"));
					budgUnitmap.put("bank_location", entityMap.get("bank_location"));
					budgUnitmap.put("is_stop", 0);
					
					budgUnitMapper.addBudgUnit(budgUnitmap);
				}else{
					budgUnitMapper.updateBudgUnit(entityMap);
				}

				budgChargeApplyMapper.updateBudgChargeApply(entityMap);
			}
			
			budgChargeApplyMapper.confirmBudgChargeApply(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//取消确认
	@Override
	public String confirmBudgChargeCancel(List<Map<String, Object>> entityList,Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			if(entityMap.get("detailData") != null){
				
				//1.2查询往来单位信息是否存在
				Map<String,Object> budgUnit = budgUnitMapper.queryBudgUnitByCode(entityMap);
				if(budgUnit == null){
					//查询序列
					int unit_id = budgUnitMapper.queryBudgUnitSeqNextVal();
					Map<String,Object> budgUnitmap = new HashMap<String,Object>();
					budgUnitmap.put("group_id", entityMap.get("group_id"));
					budgUnitmap.put("hos_id", entityMap.get("hos_id"));
					budgUnitmap.put("unit_id", unit_id);
					budgUnitmap.put("unit_name", entityMap.get("unit_name"));
					budgUnitmap.put("card_no", entityMap.get("card_no"));
					budgUnitmap.put("bank_name", entityMap.get("bank_name"));
					budgUnitmap.put("bank_location", entityMap.get("bank_location"));
					budgUnitmap.put("is_stop", 0);
					
					budgUnitMapper.addBudgUnit(budgUnitmap);
				}else{
					budgUnitMapper.updateBudgUnit(entityMap);
				}

				budgChargeApplyMapper.updateBudgChargeApply(entityMap);
			}
			
			budgChargeApplyMapper.confirmBudgChargeCancel(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	
	
	@Override
	public String unConfirmBudgChargeApply(List<Map<String, Object>> entityList,Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			//先删除凭证
			for (Map<String, Object> map : entityList) {
				
				Map<String, Object> mainMapVo = new HashMap<String, Object>();
				
				mainMapVo.put("group_id", map.get("group_id"));
				mainMapVo.put("hos_id", map.get("hos_id"));
				mainMapVo.put("copy_code", map.get("copy_code"));
				mainMapVo.put("vouch_id", map.get("vouch_id"));
				mainMapVo.put("log_table", map.get("log_table"));
				mainMapVo.put("user_id", SessionManager.getUserId());
				
				
				if(map.get("vouch_id") == null || "".equals(map.get("vouch_id"))){
					continue;
				}
				
				accAutoVouchMaintainMapper.deleteAccAutoVouch(mainMapVo);
				
				if(mainMapVo.get("reNote")!=null && !mainMapVo.get("reNote").toString().equalsIgnoreCase("ok")){
					
					throw new SysException(mainMapVo.get("reNote").toString());
					
				}
			}
			//更改状态
			budgChargeApplyMapper.confirmBudgChargeApply(entityList);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}


	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;

	@Override
	public List<Map<String, Object>> queryBudgChargeApplyPrint(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = budgChargeApplyMapper.queryBudgChargeApplyPrint(entityMap);

		return list;

	}

	
	@Override
	public String queryBudgUnit(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = budgUnitMapper.queryBudgUnit(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list  = budgUnitMapper.queryBudgUnit(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
		
	}

	@Override
	public String deleteBudgUnit(List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			budgUnitMapper.deleteBatchBudgUnit(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
			
		}
	}

//	添加页面跳转  查询当前用户数据
	@Override
	public BudgChargeApply queryUserDataById(Map<String, Object> entityMap) throws DataAccessException {
		return budgChargeApplyMapper.queryUserDataById(entityMap);
	}
	
	@Override
	public String queryBudgUnitSelect(Map<String, Object> entityMap) throws DataAccessException {

			return JSON.toJSONString(budgUnitMapper.queryBudgUnit(entityMap));

	}


	@Override
	public String queryMoneyApplyDet(Map<String, Object> mapVo) {
		List<Map<String,Object>> listMain = budgChargeApplyMapper.queryMoneyApplyDet(mapVo);
		return ChdJson.toJson(listMain);
	}
	
	/**
	 * excel导入费用申请（按行导入）
	 */
	@Override
	public String importBudgChargeApplySingle(Map<String, Object> paraMap) throws DataAccessException {
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
		
		String state = paraMap.get("state").toString();// 支付状态
		try{
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paraMap);// excel表格数据
			if(list != null && list.size() > 0){
				WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
				DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
				
				List<String> mustFillIsNull = new ArrayList<String>();// 必填格是空的格子行列号信息
				List<String> deptNotFound = new ArrayList<String>();// 没有找到的科室
				List<String> projNotFound = new ArrayList<String>();// 没有找到的项目
				List<String> sourceNotFound = new ArrayList<String>();// 没有找到的项目
				List<String> paymentItemNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> useApplyNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> payWayNotFound = new ArrayList<String>();// 没有找到的支出项目
				
				List<Map<String, Object>> saveMainList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> saveDetailList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> budgUnitAddList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> budgUnitUpdateList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> sMap = readyResourceMap(pMap);
				Map<String, DeptDict> deptDictMap = (Map<String, DeptDict>) sMap.get("dept");// 科室Map
				Map<String, ProjDict> projDictMap = (Map<String, ProjDict>) sMap.get("proj");// 项目Map
				Map<String, Object> sourceMap = (Map<String, Object>) sMap.get("source");// 资金来源Map
				Map<String, BudgPaymentItemDict> paymentItemMap = (Map<String, BudgPaymentItemDict>) sMap.get("payItem");// 支出项目Map
				Map<String, Object> useApplyMap = (Map<String, Object>) sMap.get("useApply");// 用款申请Map
				Map<String, Map<String, Object>> budgUnitMap = (Map<String, Map<String, Object>>) sMap.get("budgUnit");// 收款单位Map
				Map<String, String> payWayMap = (Map<String, String>) sMap.get("payWay");// 支付方式
				
				// 职工
				pMap.put("emp_id", userEmpId);
				pMap.put("is_stop", "0");
				pMap.put("is_disable", "0");
				EmpDict empDict = empDictMapper.queryEmpDictByCode(pMap);
				
				pMap.put("prefixe", "FYSQ");
				pMap.put("table_code", "BUDG_CHARGE_APPLY");
				
				String applyCode = null;
				boolean flag = true;
				int unitId = budgUnitMapper.queryBudgUnitSeqNextVal();
				// excel数据遍历
				for(Map<String, List<String>> rowMap : list){
					// 主表
					// 申请编号
					applyCode = budgNoManagerService.getBillNOSeqNo(pMap);
					budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_CHARGE_APPLY");
					
					Map<String, Object> saveMainMap = new HashMap<String, Object>();
					// 科室（必填）
					if(StringUtils.isEmpty(rowMap.get("dept_code").get(1))){
						mustFillIsNull.add(rowMap.get("dept_code").get(0));
						flag = false;
					}else if(deptDictMap.get(rowMap.get("dept_code").get(1)) == null){
						if(!deptNotFound.contains(rowMap.get("dept_code").get(1))){
							deptNotFound.add(rowMap.get("dept_code").get(1));
							flag = false;
						}
					}else{
						saveMainMap.put("dept_id", deptDictMap.get(rowMap.get("dept_code").get(1)).getDept_id());
						saveMainMap.put("dept_no", deptDictMap.get(rowMap.get("dept_code").get(1)).getDept_no());
					}
					
					// 项目
					if(StringUtils.isNotEmpty(rowMap.get("proj_code").get(1))){
						if(projDictMap.get(rowMap.get("proj_code").get(1)) == null){
							if(!projNotFound.contains(rowMap.get("proj_code").get(1))){
								projNotFound.add(rowMap.get("proj_code").get(1));
								flag = false;
							}
						}else{
							saveMainMap.put("proj_id", projDictMap.get(rowMap.get("proj_code").get(1)).getProj_id());
							saveMainMap.put("proj_no", projDictMap.get(rowMap.get("proj_code").get(1)).getProj_no());
						}
					}
					
					// 资金来源（必填）
					if(StringUtils.isEmpty(rowMap.get("source_code").get(1))){
						mustFillIsNull.add(rowMap.get("source_code").get(0));
						flag = false;
					}else if(sourceMap.get(rowMap.get("source_code").get(1)) == null){
						if(!sourceNotFound.contains(rowMap.get("source_code").get(1))){
							sourceNotFound.add(rowMap.get("source_code").get(1));
							flag = false;
						}
					}else{
						saveMainMap.put("source_id", sourceMap.get(rowMap.get("source_code").get(1)));
					}
					
					// 支出项目（必填）
					if(StringUtils.isEmpty(rowMap.get("payment_code").get(1))){
						mustFillIsNull.add(rowMap.get("payment_code").get(0));
						flag = false;
					}else if(paymentItemMap.get(rowMap.get("payment_code").get(1)) == null){
						if(!paymentItemNotFound.contains(rowMap.get("payment_code").get(1))){
							paymentItemNotFound.add(rowMap.get("payment_code").get(1));
							flag = false;
						}
					}else{
						saveMainMap.put("payment_item_id", paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_id());
						saveMainMap.put("payment_item_no", paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_no());
					}
					
					// 申请事由（必填）
					if(StringUtils.isEmpty(rowMap.get("remark").get(1))){
						mustFillIsNull.add(rowMap.get("remark").get(0));
						flag = false;
					}else{
						saveMainMap.put("remark", rowMap.get("remark").get(1));
					}
					
					// 用款申请
					if(StringUtils.isNotEmpty(rowMap.get("use_apply_code").get(1))){
						if(useApplyMap.get(rowMap.get("use_apply_code").get(1)) == null){
							if(!useApplyNotFound.contains(rowMap.get("use_apply_code").get(1))){
								useApplyNotFound.add(rowMap.get("use_apply_code").get(1));
								flag = false;
							}
						}else{
							saveMainMap.put("use_apply_code", rowMap.get("use_apply_code").get(1));
						}
					}
					
					saveMainMap.put("apply_code", applyCode);
					saveMainMap.put("apply_date", new Date());
					saveMainMap.put("group_id", groupId);
					saveMainMap.put("hos_id", hosId);
					saveMainMap.put("copy_code", copyCode);
					saveMainMap.put("emp_id", empDict.getEmp_id());
					saveMainMap.put("emp_no", empDict.getEmp_no());

					saveMainMap.put("maker", SessionManager.getUserId());
					saveMainMap.put("make_date", new Date());
					saveMainMap.put("state", state);
					if("04".equals(state)){// 已支状态
						// 付款方式
						if(StringUtils.isEmpty(rowMap.get("pay_way").get(1))){
							mustFillIsNull.add(rowMap.get("pay_way").get(0));
							flag = false;
						}else if(payWayMap.get(rowMap.get("pay_way").get(1)) == null){
							if(!payWayNotFound.contains(rowMap.get("pay_way").get(1))){
								payWayNotFound.add(rowMap.get("pay_way").get(1));
								flag = false;
							}
						}else{
							saveMainMap.put("pay_way", payWayMap.get(rowMap.get("pay_way").get(1)));
						}
						saveMainMap.put("checker", userId);
						saveMainMap.put("check_date", new Date());
						saveMainMap.put("payer", userId);
						saveMainMap.put("pay_date", new Date());
					}
					
					saveMainList.add(saveMainMap);
					// 主表end
					
					// 明细表
					Map<String, Object> saveDetailMap = new HashMap<String, Object>();
					// 收款单位（必填）
					if(StringUtils.isEmpty(rowMap.get("unit_name").get(1))){
						mustFillIsNull.add(rowMap.get("unit_name").get(0));
						flag = false;
					}
					if(StringUtils.isEmpty(rowMap.get("card_no").get(1))){
						mustFillIsNull.add(rowMap.get("card_no").get(0));
						flag = false;
					}
					if(StringUtils.isEmpty(rowMap.get("bank_name").get(1))){
						mustFillIsNull.add(rowMap.get("bank_name").get(0));
						flag = false;
					}
					if(StringUtils.isEmpty(rowMap.get("bank_location").get(1))){
						mustFillIsNull.add(rowMap.get("bank_location").get(0));
						flag = false;
					}
					
					if(flag){
						if(budgUnitMap.get(rowMap.get("unit_name").get(1)) == null){
							Map<String, Object> unitAddMap = new HashMap<String, Object>();
							unitAddMap.put("group_id", groupId);
							unitAddMap.put("hos_id", hosId);
							unitId++;
							unitAddMap.put("unit_id", unitId);
							unitAddMap.put("unit_name", rowMap.get("unit_name").get(1));
							unitAddMap.put("card_no", rowMap.get("card_no").get(1));
							unitAddMap.put("bank_name", rowMap.get("bank_name").get(1));
							unitAddMap.put("bank_location", rowMap.get("bank_location").get(1));
							unitAddMap.put("is_stop", "0");
							
							budgUnitAddList.add(unitAddMap);
							saveDetailMap.put("unit_id", unitId);
						}else{
							Map<String, Object> unitUpdateMap = new HashMap<String, Object>();
							unitUpdateMap.put("group_id", groupId);
							unitUpdateMap.put("hos_id", hosId);
							unitUpdateMap.put("unit_id", budgUnitMap.get(rowMap.get("unit_name").get(1)).get("unit_id"));
							unitUpdateMap.put("unit_name", rowMap.get("unit_name").get(1));
							unitUpdateMap.put("card_no", rowMap.get("card_no").get(1));
							unitUpdateMap.put("bank_name", rowMap.get("bank_name").get(1));
							unitUpdateMap.put("bank_location", rowMap.get("bank_location").get(1));
							unitUpdateMap.put("is_stop", "0");
							
							budgUnitUpdateList.add(unitUpdateMap);
							saveDetailMap.put("unit_id", budgUnitMap.get(rowMap.get("unit_name").get(1)).get("unit_id"));
						}
						
						saveDetailMap.put("card_no", rowMap.get("card_no").get(1));
						saveDetailMap.put("bank_name", rowMap.get("bank_name").get(1));
						saveDetailMap.put("bank_location", rowMap.get("bank_location").get(1));
						saveDetailMap.put("group_id", groupId);
						saveDetailMap.put("hos_id", hosId);
						saveDetailMap.put("copy_code", copyCode);
						saveDetailMap.put("apply_code", applyCode);
//					saveDetailMap.put("source_id", sourceMap.containsKey(rowMap.get("source_code").get(1)) ? sourceMap.get(rowMap.get("source_code").get(1)) : null);
//					saveDetailMap.put("payment_item_id", paymentItemMap.containsKey(rowMap.get("payment_code").get(1)) ? paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_id() : null);
//					saveDetailMap.put("payment_item_no", paymentItemMap.containsKey(rowMap.get("payment_code").get(1)) ? paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_no() : null);
						saveDetailMap.put("payment_amount", rowMap.get("payment_amount").get(1));
						
						saveDetailList.add(saveDetailMap);
					}
					// 明细表end
					
				}// excel数据遍历end
				
				// 不能导入消息描述*************************************************************
				String mustFillMsg = ExcelReader.mustFillDescribe(mustFillIsNull);
				if(mustFillMsg != null){
					// 回滚事务
					transactionManager.rollback(status);
					return "{\"warn\":\"" + mustFillMsg + ".\",\"state\":\"false\"}";
				}
				
				String msg = "";
				StringBuilder msgPrefix = null;
				msgPrefix = new StringBuilder("以下【科室】没有找到：");
				msg += msgDescribe(msgPrefix, deptNotFound);
			
				msgPrefix = new StringBuilder("以下【项目名称】没有找到：");
				msg += msgDescribe(msgPrefix, projNotFound);
			
				msgPrefix = new StringBuilder("以下【资金来源】没有找到：");
				msg += msgDescribe(msgPrefix, sourceNotFound);
			
				msgPrefix = new StringBuilder("以下【支出项目】没有找到或对其没有权限：");
				msg += msgDescribe(msgPrefix, paymentItemNotFound);
			
				msgPrefix = new StringBuilder("以下【用款申请】没有找到：");
				msg += msgDescribe(msgPrefix, useApplyNotFound);
			
				if("04".equals(state)){// 已支付状态
					msgPrefix = new StringBuilder("以下【支付方式】没有找到：");
					msg += msgDescribe(msgPrefix, payWayNotFound);
				}
				
				if(StringUtils.isNotEmpty(msg)){
					transactionManager.rollback(status);// 回滚事务
					msg = msg.substring(0, msg.lastIndexOf("<br/>"));
					return "{\"error\":\"" + msg + "\",\"state\":\"false\"}";
				}
				// **********************************************************************
				
				// 付款金额
				Map<String, Double> paymentAmountMap = new HashMap<String, Double>();
				for(Map<String, Object> det : saveDetailList){
					if(paymentAmountMap.containsKey(det.get("apply_code").toString())){
						double pa = NumberUtil.add(paymentAmountMap.get(det.get("apply_code").toString()), Double.valueOf(det.get("payment_amount").toString()));
						paymentAmountMap.put(det.get("apply_code").toString(), pa);
					}else{
						paymentAmountMap.put(det.get("apply_code").toString(), Double.valueOf(det.get("payment_amount").toString()));
					}
				}
				
				for(Map<String, Object> mai : saveMainList){
					mai.put("payment_amount", paymentAmountMap.get(mai.get("apply_code").toString()));
				}
				
				if(budgUnitAddList.size() > 0){
					budgUnitMapper.addBudgUnitBatch(budgUnitAddList);
				}
				if(budgUnitUpdateList.size() > 0){
					budgUnitMapper.updateBatch(budgUnitUpdateList);
				}
				
				budgChargeApplyMapper.addBatch(saveMainList);
				budgChargeApplyDetMapper.addBatch(saveDetailList);

				// 提交事务
				transactionManager.commit(status);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			return "{\"warn\":\"没有可导入数据.\",\"state\":\"false\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException();
		}
	}

	/**
	 * excel导入费用申请
	 */
	@Override
	public String importBudgChargeApply(Map<String, Object> paraMap) throws DataAccessException {
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
		try{
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paraMap);// excel表格数据
			if(list != null && list.size() > 0){
				//WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
				//DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
				//DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				//def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				//TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
				
				List<String> mustFillIsNull = new ArrayList<String>();// 必填格是空的格子行列号信息
				List<String> deptNotFound = new ArrayList<String>();// 没有找到的科室
				List<String> projNotFound = new ArrayList<String>();// 没有找到的项目
				List<String> sourceNotFound = new ArrayList<String>();// 没有找到的项目
				List<String> paymentItemNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> useApplyNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> payWayNotFound = new ArrayList<String>();// 没有找到的支出项目
				List<String> exitsUnitName = new ArrayList<String>();// 没有找到的支出项目
				
				List<Map<String, Object>> saveMainList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> saveDetailList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> budgUnitAddList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> budgUnitUpdateList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> sMap = readyResourceMap(pMap);
				Map<String, DeptDict> deptDictMap = (Map<String, DeptDict>) sMap.get("dept");// 科室Map
				Map<String, ProjDict> projDictMap = (Map<String, ProjDict>) sMap.get("proj");// 项目Map
				Map<String, Object> sourceMap = (Map<String, Object>) sMap.get("source");// 资金来源Map
				Map<String, BudgPaymentItemDict> paymentItemMap = (Map<String, BudgPaymentItemDict>) sMap.get("payItem");// 支出项目Map
				Map<String, Object> useApplyMap = (Map<String, Object>) sMap.get("useApply");// 用款申请Map
				Map<String, Map<String, Object>> budgUnitMap = (Map<String, Map<String, Object>>) sMap.get("budgUnit");// 收款单位Map
				Map<String, String> payWayMap = (Map<String, String>) sMap.get("payWay");// 支付方式
				Map<String, String> exitsUnitNameMap = new HashMap<String,String>();// 明细数据是否重复
				
				// 职工
				pMap.put("emp_id", userEmpId);
				pMap.put("is_stop", "0");
				pMap.put("is_disable", "0");
				EmpDict empDict = empDictMapper.queryEmpDictByCode(pMap);
				
				pMap.put("prefixe", "FYSQ");
				pMap.put("table_code", "BUDG_CHARGE_APPLY");
				
				Map<String, String> existMapMain = new HashMap<String, String>();
				Map<String, String> existMapDetail = new HashMap<String, String>();
				String applyCode = null;
				boolean flag = true;
				//int unitId = budgUnitMapper.queryBudgUnitSeqNextVal();
				// excel数据遍历
				for(Map<String, List<String>> rowMap : list){
					String existRowMain = rowMap.get("dept_code").get(1)
										+ rowMap.get("proj_code").get(1)
										+ rowMap.get("source_code").get(1)
										+ rowMap.get("payment_code").get(1)
										+ rowMap.get("remark").get(1)
										+ rowMap.get("use_apply_code").get(1);
					if("04".equals(state)){// 已支付状态
						existRowMain += rowMap.get("pay_way").get(1);
					}
					
					String existRowDetail = existRowMain + rowMap.get("unit_name").get(1);
					
					// 主表
					if(!existMapMain.containsKey(existRowMain)){
						// 申请编号
						applyCode = budgNoManagerService.getBillNOSeqNo(pMap);
						budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_CHARGE_APPLY");
						
						existMapMain.put(existRowMain, applyCode);
						
						Map<String, Object> saveMainMap = new HashMap<String, Object>();
						// 科室（必填）
						if(StringUtils.isEmpty(rowMap.get("dept_code").get(1))){
							mustFillIsNull.add(rowMap.get("dept_code").get(0));
							flag = false;
						}else if(deptDictMap.get(rowMap.get("dept_code").get(1)) == null){
							if(!deptNotFound.contains(rowMap.get("dept_code").get(1))){
								deptNotFound.add(rowMap.get("dept_code").get(1));
								flag = false;
							}
						}else{
							saveMainMap.put("dept_id", deptDictMap.get(rowMap.get("dept_code").get(1)).getDept_id());
							saveMainMap.put("dept_no", deptDictMap.get(rowMap.get("dept_code").get(1)).getDept_no());
						}
						
						// 项目
						if(StringUtils.isNotEmpty(rowMap.get("proj_code").get(1))){
							if(projDictMap.get(rowMap.get("proj_code").get(1)) == null){
								if(!projNotFound.contains(rowMap.get("proj_code").get(1))){
									projNotFound.add(rowMap.get("proj_code").get(1));
									flag = false;
								}
							}else{
								saveMainMap.put("proj_id", projDictMap.get(rowMap.get("proj_code").get(1)).getProj_id());
								saveMainMap.put("proj_no", projDictMap.get(rowMap.get("proj_code").get(1)).getProj_no());
							}
						}
						
						// 资金来源（必填）
						if(StringUtils.isEmpty(rowMap.get("source_code").get(1))){
							mustFillIsNull.add(rowMap.get("source_code").get(0));
						}else if(sourceMap.get(rowMap.get("source_code").get(1)) == null){
							if(!sourceNotFound.contains(rowMap.get("source_code").get(1))){
								sourceNotFound.add(rowMap.get("source_code").get(1));
								flag = false;
							}
						}else{
							saveMainMap.put("source_id", sourceMap.get(rowMap.get("source_code").get(1)));
						}
						
						// 支出项目（必填）
						if(StringUtils.isEmpty(rowMap.get("payment_code").get(1))){
							mustFillIsNull.add(rowMap.get("payment_code").get(0));
						}else if(paymentItemMap.get(rowMap.get("payment_code").get(1)) == null){
							if(!paymentItemNotFound.contains(rowMap.get("payment_code").get(1))){
								paymentItemNotFound.add(rowMap.get("payment_code").get(1));
								flag = false;
							}
						}else{
							saveMainMap.put("payment_item_id", paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_id());
							saveMainMap.put("payment_item_no", paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_no());
						}
						
						// 申请事由（必填）
						if(StringUtils.isEmpty(rowMap.get("remark").get(1))){
							mustFillIsNull.add(rowMap.get("remark").get(0));
							flag = false;
						}else{
							saveMainMap.put("remark", rowMap.get("remark").get(1));
						}
						
						// 用款申请
						if(StringUtils.isNotEmpty(rowMap.get("use_apply_code").get(1))){
							if(useApplyMap.get(rowMap.get("use_apply_code").get(1)) == null){
								if(!useApplyNotFound.contains(rowMap.get("use_apply_code").get(1))){
									useApplyNotFound.add(rowMap.get("use_apply_code").get(1));
									flag = false;
								}
							}else{
								saveMainMap.put("use_apply_code", rowMap.get("use_apply_code").get(1));
							}
						}
						
						if("04".equals(state)){// 已支状态
							// 付款方式
							if(StringUtils.isEmpty(rowMap.get("pay_way").get(1))){
								mustFillIsNull.add(rowMap.get("pay_way").get(0));
								flag = false;
							}else if(payWayMap.get(rowMap.get("pay_way").get(1)) == null){
								if(!payWayNotFound.contains(rowMap.get("pay_way").get(1))){
									payWayNotFound.add(rowMap.get("pay_way").get(1));
									flag = false;
								}
							}else{
								saveMainMap.put("pay_way", payWayMap.get(rowMap.get("pay_way").get(1)));
							}
							saveMainMap.put("checker", userId);
							saveMainMap.put("check_date", new Date());
							saveMainMap.put("payer", userId);
							saveMainMap.put("pay_date", new Date());
						}
						
						if(flag){
							saveMainMap.put("apply_code", applyCode);
							saveMainMap.put("apply_date", new Date());
							saveMainMap.put("group_id", groupId);
							saveMainMap.put("hos_id", hosId);
							saveMainMap.put("copy_code", copyCode);
							saveMainMap.put("emp_id", empDict.getEmp_id());
							saveMainMap.put("emp_no", empDict.getEmp_no());
							saveMainMap.put("state", state);
							saveMainMap.put("maker", SessionManager.getUserId());
							saveMainMap.put("make_date", new Date());
							saveMainList.add(saveMainMap);
						}
					}// 主表end
					
					if(exitsUnitNameMap.get(rowMap.get("card_no").get(1)) !=null && exitsUnitNameMap.get(rowMap.get("unit_name").get(1)) !=null){
						exitsUnitName.add(rowMap.get("unit_name").get(1));
						flag = false;
					}else{
						exitsUnitNameMap.put(rowMap.get("card_no").get(1), rowMap.get("card_no").get(1));
						exitsUnitNameMap.put(rowMap.get("unit_name").get(1), rowMap.get("unit_name").get(1));
					}
					
					// 明细表
					existMapDetail.put(existRowDetail, existRowMain);
					Map<String, Object> saveDetailMap = new HashMap<String, Object>();
					// 收款单位（必填）
					if(StringUtils.isEmpty(rowMap.get("unit_name").get(1))){
						mustFillIsNull.add(rowMap.get("unit_name").get(0));
						flag = false;
					}
					if(StringUtils.isEmpty(rowMap.get("card_no").get(1))){
						mustFillIsNull.add(rowMap.get("card_no").get(0));
						flag = false;
					}
					if(StringUtils.isEmpty(rowMap.get("bank_name").get(1))){
						mustFillIsNull.add(rowMap.get("bank_name").get(0));
						flag = false;
					}
					if(StringUtils.isEmpty(rowMap.get("bank_location").get(1))){
						mustFillIsNull.add(rowMap.get("bank_location").get(0));
						flag = false;
					}
					
					if(flag){
						if(budgUnitMap.get(rowMap.get("card_no").get(1)) != null){
							String unit_name = budgUnitMap.get(rowMap.get("card_no").get(1)).get("unit_name").toString();
							String in_name = rowMap.get("unit_name").get(1).toString();
							if (in_name != null && unit_name != null && in_name.equals(unit_name)) {
								Map<String, Object> unitUpdateMap = new HashMap<String, Object>();
								unitUpdateMap.put("group_id", groupId);
								unitUpdateMap.put("hos_id", hosId);
								unitUpdateMap.put("unit_id", budgUnitMap.get(rowMap.get("card_no").get(1)).get("unit_id"));
								unitUpdateMap.put("unit_name", rowMap.get("unit_name").get(1));
								unitUpdateMap.put("card_no", rowMap.get("card_no").get(1));
								unitUpdateMap.put("bank_name", rowMap.get("bank_name").get(1));
								unitUpdateMap.put("bank_location", rowMap.get("bank_location").get(1));
								unitUpdateMap.put("is_stop", "0");
								
								budgUnitUpdateList.add(unitUpdateMap);
								saveDetailMap.put("unit_id", budgUnitMap.get(rowMap.get("card_no").get(1)).get("unit_id"));
							}else {
								return "{\"error\":\"银行卡号：" + rowMap.get("card_no").get(1) + "对应的人员不一致\",\"state\":\"false\"}";
							}
						}else{
							Map<String, Object> unitAddMap = new HashMap<String, Object>();
							unitAddMap.put("group_id", groupId);
							unitAddMap.put("hos_id", hosId);
							int unit_id = budgUnitMapper.queryBudgUnitSeqNextVal();
							unitAddMap.put("unit_id", unit_id);
							unitAddMap.put("unit_name", rowMap.get("unit_name").get(1));
							unitAddMap.put("card_no", rowMap.get("card_no").get(1));
							unitAddMap.put("bank_name", rowMap.get("bank_name").get(1));
							unitAddMap.put("bank_location", rowMap.get("bank_location").get(1));
							unitAddMap.put("is_stop", "0");
							
							budgUnitAddList.add(unitAddMap);
							saveDetailMap.put("unit_id", unit_id);
						}
						
						saveDetailMap.put("card_no", rowMap.get("card_no").get(1));
						saveDetailMap.put("bank_name", rowMap.get("bank_name").get(1));
						saveDetailMap.put("bank_location", rowMap.get("bank_location").get(1));
						saveDetailMap.put("group_id", groupId);
						saveDetailMap.put("hos_id", hosId);
						saveDetailMap.put("copy_code", copyCode);
						saveDetailMap.put("apply_code", existMapMain.get(existMapDetail.get(existRowDetail)));
//						saveDetailMap.put("source_id", sourceMap.containsKey(rowMap.get("source_code").get(1)) ? sourceMap.get(rowMap.get("source_code").get(1)) : null);
//						saveDetailMap.put("payment_item_id", paymentItemMap.containsKey(rowMap.get("payment_code").get(1)) ? paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_id() : null);
//						saveDetailMap.put("payment_item_no", paymentItemMap.containsKey(rowMap.get("payment_code").get(1)) ? paymentItemMap.get(rowMap.get("payment_code").get(1)).getPayment_item_no() : null);
						saveDetailMap.put("payment_amount", rowMap.get("payment_amount").get(1));
						
						saveDetailList.add(saveDetailMap);
					}
					// 明细表end
					
				}// excel数据遍历end
				
				// 不能导入消息描述*************************************************************
				String mustFillMsg = ExcelReader.mustFillDescribe(mustFillIsNull);
				if(mustFillMsg != null){
					// 回滚事务
					//transactionManager.rollback(status);
					return "{\"warn\":\"" + mustFillMsg + ".\",\"state\":\"false\"}";
				}
				
				String msg = "";
				StringBuilder msgPrefix = null;
				msgPrefix = new StringBuilder("以下【科室】没有找到：");
				msg += msgDescribe(msgPrefix, deptNotFound);
			
				msgPrefix = new StringBuilder("以下【项目名称】没有找到：");
				msg += msgDescribe(msgPrefix, projNotFound);
			
				msgPrefix = new StringBuilder("以下【资金来源】没有找到：");
				msg += msgDescribe(msgPrefix, sourceNotFound);
			
				msgPrefix = new StringBuilder("以下【支出项目】没有找到或对其没有权限：");
				msg += msgDescribe(msgPrefix, paymentItemNotFound);
			
				msgPrefix = new StringBuilder("以下【用款申请】没有找到：");
				msg += msgDescribe(msgPrefix, useApplyNotFound);
			
				msgPrefix = new StringBuilder("以下【收款人姓名、卡号】重复：");
				msg += msgDescribe(msgPrefix, exitsUnitName);
				
				if("04".equals(state)){// 已支付状态
					msgPrefix = new StringBuilder("以下【支付方式】没有找到：");
					msg += msgDescribe(msgPrefix, payWayNotFound);
				}
				
				if(StringUtils.isNotEmpty(msg)){
					//transactionManager.rollback(status);// 回滚事务
					msg = msg.substring(0, msg.lastIndexOf("<br/>"));
					return "{\"error\":\"" + msg + "\",\"state\":\"false\"}";
				}
				// **********************************************************************
				
				// 付款金额
				Map<String, Double> paymentAmountMap = new HashMap<String, Double>();
				for(Map<String, Object> det : saveDetailList){
					if(paymentAmountMap.containsKey(det.get("apply_code").toString())){
						double pa = NumberUtil.add(paymentAmountMap.get(det.get("apply_code").toString()), Double.valueOf(det.get("payment_amount").toString()));
						paymentAmountMap.put(det.get("apply_code").toString(), pa);
					}else{
						paymentAmountMap.put(det.get("apply_code").toString(), Double.valueOf(det.get("payment_amount").toString()));
					}
				}
				
				for(Map<String, Object> mai : saveMainList){
					mai.put("payment_amount", paymentAmountMap.get(mai.get("apply_code").toString()));
				}
				
				if(budgUnitAddList.size() > 0){
					budgUnitMapper.addBudgUnitBatch(budgUnitAddList);
				}
				if(budgUnitUpdateList.size() > 0){
					budgUnitMapper.updateBatch(budgUnitUpdateList);
				}
				
				budgChargeApplyMapper.addBatch(saveMainList);
				budgChargeApplyDetMapper.addBatch(saveDetailList);

				// 提交事务
				//transactionManager.commit(status);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			return "{\"warn\":\"没有可导入数据.\",\"state\":\"false\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException();
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
		
		// 组装收款单位Map
		List<Map<String, Object>> budgUnitList = budgUnitMapper.queryBudgUnit(paraMap);
		Map<String, Map<String, Object>> budgUnitMap = new HashMap<String, Map<String, Object>>();
		for(Map<String,Object> budgUnit : budgUnitList){
			budgUnitMap.put(budgUnit.get("unit_name").toString(), budgUnit);
			budgUnitMap.put(budgUnit.get("card_no").toString(), budgUnit);
		}
		map.put("budgUnit", budgUnitMap);
		
		// 组装支付方式Map
		List<HrpAccSelect> payWayList = hrpAccSelectMapper.queryPayType(paraMap);
		Map<String, String> payWayMap = new HashMap<String, String>();
		for(HrpAccSelect has : payWayList){
			payWayMap.put(has.getText(), has.getId());
		}
		map.put("payWay", payWayMap);
		
		return map;
	}
	
}

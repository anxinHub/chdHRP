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
import com.chd.hrp.acc.dao.payable.base.BudgBorrBegainMarkMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetDeptMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrDetProjMapper;
import com.chd.hrp.acc.dao.payable.base.BudgBorrProjMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrBegainDetMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrBegainMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrBegain;
import com.chd.hrp.acc.entity.payable.BudgBorrBegainDet;
import com.chd.hrp.acc.entity.payable.BudgBorrBegainMark;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetProj;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.service.payable.base.BudgNoManagerService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrBegainService;
import com.chd.hrp.mat.serviceImpl.advice.MatRefChargeServiceImpl;
import com.github.pagehelper.PageInfo;
@Service("budgBorrBegainService")
public class BudgBorrBegainServiceImpl implements BudgBorrBegainService {
	
	private static Logger logger = Logger.getLogger(MatRefChargeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgBorrBegainMapper")
	private final BudgBorrBegainMapper budgBorrBegainMapper = null;
	
	@Resource(name = "budgBorrBegainDetMapper")
	private final BudgBorrBegainDetMapper budgBorrBegainDetMapper = null;
	
	@Resource(name = "budgNoManagerService")
	private final BudgNoManagerService budgNoManagerService = null;
	
	@Resource(name = "budgBorrBegainMarkMapper")
	private final BudgBorrBegainMarkMapper budgBorrBegainMarkMapper = null;
	
	
	
	@Resource(name = "budgBorrDeptMapper")
	private final BudgBorrDeptMapper budgBorrDeptMapper = null;
	
	@Resource(name = "budgBorrDetDeptMapper")
	private final BudgBorrDetDeptMapper budgBorrDetDeptMapper = null;
	
	@Resource(name = "budgBorrDetProjMapper")
	private final BudgBorrDetProjMapper budgBorrDetProjMapper = null;
	
	@Resource(name = "budgBorrProjMapper")
	private final BudgBorrProjMapper budgBorrProjMapper = null;
	

	@Override
	public String addBudgBorrBegain(Map<String, Object> entityMap) throws DataAccessException {
		BudgBorrBegain budgBorrBegain = queryBudgBorrBegainByCode(entityMap);
		try {
			if (budgBorrBegain != null) {
				budgBorrBegainMapper.updateBudgBorrBegain(entityMap);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"begin_code\":\""+entityMap.get("begin_code")+"\"}";
			}else{
				entityMap.put("state", "0");
				
				entityMap.put("table_name", "期初借款");
				entityMap.put("prefixe", "QCJK");
				entityMap.put("table_code", "BUDG_BORR_BEGAIN");
				String begin_code = budgNoManagerService.getBillNOSeqNo(entityMap);
				entityMap.put("begin_code", begin_code);
				int state = budgBorrBegainMapper.addBudgBorrBegain(entityMap);
				if(state > 0){
					budgNoManagerService.updateBudgNoManagerMaxNo("BUDG_BORR_BEGAIN");
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"begin_code\":\""+begin_code+"\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addBatchBudgBorrBegain(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			budgBorrBegainMapper.addBatchBudgBorrBegain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBudgBorrBegain(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = budgBorrBegainMapper.updateBudgBorrBegain(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}

	@Override
	public String updateBatchBudgBorrBegain(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			budgBorrBegainMapper.updateBatchBudgBorrBegain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBudgBorrBegain(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = budgBorrBegainMapper.deleteBudgBorrBegain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String deleteBatchBudgBorrBegain(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgBorrBegainDetMapper.deleteBatch(entityMap);
			budgBorrBegainMapper.deleteBatchBudgBorrBegain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgBorrBegain(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgBorrBegain> list = budgBorrBegainMapper.queryBudgBorrBegain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgBorrBegain> list = budgBorrBegainMapper.queryBudgBorrBegain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public BudgBorrBegain queryBudgBorrBegainByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrBegainMapper.queryBudgBorrBegainByCode(entityMap);
	}

	@Override
	public BudgBorrBegain queryBudgBorrBegainByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgBorrBegainMapper.queryBudgBorrBegainByUniqueness(entityMap);
	}

	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			budgBorrBegainMapper.updateToExamine(entityMap);
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			budgBorrBegainMapper.updateNotToExamine(entityMap);
			
			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryBudgBorrBegainDet(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgBorrBegainDet> list = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgBorrBegainDet> list = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	@Override
	public String accountBudgBorrBegain(Map<String, Object> entityMap) throws DataAccessException {
		try {
			Map<String, Object> mapMainVo = new HashMap<String, Object>();
			mapMainVo.put("group_id", entityMap.get("group_id"));
			mapMainVo.put("hos_id", entityMap.get("hos_id"));
			mapMainVo.put("copy_code", entityMap.get("copy_code"));
			mapMainVo.put("state", "1");
			List<BudgBorrBegain> mainlist = budgBorrBegainMapper.queryBudgBorrBegain(mapMainVo);
			if(mainlist.size() == 0){
				return "{\"error\":\"记账失败 不存在期初借款数据! \"}";
			}
			BudgBorrBegainMark budgBorrBegainMark = budgBorrBegainMarkMapper.queryByCode(entityMap);
			if(budgBorrBegainMark == null){
				budgBorrBegainMarkMapper.add(entityMap);
			}
			
			for(BudgBorrBegain budgBorrBegain : mainlist){
				Map<String, Object> mapBorrAllVo = new HashMap<String, Object>();
				mapBorrAllVo.put("group_id", budgBorrBegain.getGroup_id());
				mapBorrAllVo.put("hos_id", budgBorrBegain.getHos_id());
				mapBorrAllVo.put("copy_code", budgBorrBegain.getCopy_code());
				mapBorrAllVo.put("proj_id", budgBorrBegain.getProj_id());
				mapBorrAllVo.put("emp_id", budgBorrBegain.getEmp_id());
				mapBorrAllVo.put("borrow_amount", budgBorrBegain.getRemain_amount());
				mapBorrAllVo.put("offset_amount", "0");
				mapBorrAllVo.put("return_amount", "0");
				mapBorrAllVo.put("remain_amount", budgBorrBegain.getRemain_amount());
				if(budgBorrBegain.getProj_id() == null){
					BudgBorrDept budgBorrDept = budgBorrDeptMapper.queryByCode(mapBorrAllVo);
					if(budgBorrDept != null){
						mapBorrAllVo.put("borrow_amount", budgBorrDept.getBorrow_amount() + budgBorrBegain.getRemain_amount());
						mapBorrAllVo.put("remain_amount", budgBorrDept.getRemain_amount() + budgBorrBegain.getRemain_amount());
						mapBorrAllVo.put("return_amount", budgBorrDept.getReturn_amount());
						mapBorrAllVo.put("offset_amount", budgBorrDept.getOffset_amount());
						budgBorrDeptMapper.update(mapBorrAllVo);
					}else{
						budgBorrDeptMapper.add(mapBorrAllVo);
					}
				}else{
					BudgBorrProj budgBorrProj = budgBorrProjMapper.queryByCode(mapBorrAllVo);
					if(budgBorrProj != null){
						mapBorrAllVo.put("borrow_amount", budgBorrProj.getBorrow_amount() + budgBorrBegain.getRemain_amount());
						mapBorrAllVo.put("remain_amount", budgBorrProj.getRemain_amount() + budgBorrBegain.getRemain_amount());
						mapBorrAllVo.put("return_amount", budgBorrProj.getReturn_amount());
						mapBorrAllVo.put("offset_amount", budgBorrProj.getOffset_amount());
						budgBorrProjMapper.update(mapBorrAllVo);
					}else{
						budgBorrProjMapper.add(mapBorrAllVo);
					}
					
				}
				
				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				mapDetailVo.put("group_id", budgBorrBegain.getGroup_id());
				mapDetailVo.put("hos_id", budgBorrBegain.getHos_id());
				mapDetailVo.put("copy_code", budgBorrBegain.getCopy_code());
				mapDetailVo.put("begin_code", budgBorrBegain.getBegin_code());
				List<BudgBorrBegainDet> detaillist = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.query(mapDetailVo);
				for(BudgBorrBegainDet budgBorrBegainDet : detaillist){
					Map<String, Object> mapBorrDetAllVo = new HashMap<String, Object>();
					mapBorrDetAllVo.put("group_id", budgBorrBegainDet.getGroup_id());
					mapBorrDetAllVo.put("hos_id", budgBorrBegainDet.getHos_id());
					mapBorrDetAllVo.put("copy_code", budgBorrBegainDet.getCopy_code());
					mapBorrDetAllVo.put("proj_id", budgBorrBegain.getProj_id());
					mapBorrDetAllVo.put("emp_id", budgBorrBegain.getEmp_id());
					mapBorrDetAllVo.put("source_id", budgBorrBegainDet.getSource_id());
					mapBorrDetAllVo.put("payment_item_id", budgBorrBegainDet.getPayment_item_id());
					mapBorrDetAllVo.put("borrow_amount", budgBorrBegainDet.getRemain_amount());
					mapBorrDetAllVo.put("offset_amount", "0");
					mapBorrDetAllVo.put("return_amount", "0");
					mapBorrDetAllVo.put("remain_amount", budgBorrBegainDet.getRemain_amount());
					if(budgBorrBegain.getProj_id() == null){
						BudgBorrDetDept budgBorrDetDept = budgBorrDetDeptMapper.queryByCode(mapBorrDetAllVo);
						if(budgBorrDetDept != null){
							mapBorrDetAllVo.put("borrow_amount", budgBorrDetDept.getBorrow_amount() + budgBorrBegainDet.getRemain_amount());
							mapBorrDetAllVo.put("remain_amount", budgBorrDetDept.getRemain_amount() + budgBorrBegainDet.getRemain_amount());
							mapBorrDetAllVo.put("return_amount", budgBorrDetDept.getReturn_amount());
							mapBorrDetAllVo.put("offset_amount", budgBorrDetDept.getOffset_amount());
							budgBorrDetDeptMapper.update(mapBorrDetAllVo);
						}else{
							budgBorrDetDeptMapper.add(mapBorrDetAllVo);
						}
					}else{
						BudgBorrDetProj budgBorrDetProj = budgBorrDetProjMapper.queryByCode(mapBorrDetAllVo);
						if(budgBorrDetProj != null){
							mapBorrDetAllVo.put("borrow_amount", budgBorrDetProj.getBorrow_amount() + budgBorrBegainDet.getRemain_amount());
							mapBorrDetAllVo.put("remain_amount", budgBorrDetProj.getRemain_amount() + budgBorrBegainDet.getRemain_amount());
							mapBorrDetAllVo.put("return_amount", budgBorrDetProj.getReturn_amount());
							mapBorrDetAllVo.put("offset_amount", budgBorrDetProj.getOffset_amount());
							budgBorrDetProjMapper.update(mapBorrDetAllVo);
						}else{
							budgBorrDetProjMapper.add(mapBorrDetAllVo);
						}
					}
					
				}
			}
			
			return "{\"msg\":\"记账成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public boolean isAccount(Map<String, Object> entityMap) throws DataAccessException {
		BudgBorrBegainMark budgBorrBegainMark = budgBorrBegainMarkMapper.queryByCode(entityMap);
		if(budgBorrBegainMark == null){
		    return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<Map<String,Object>> queryBudgBorrBegainPrint(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> resList= budgBorrBegainMapper.queryBudgBorrBegainPrint(entityMap);
		
		return resList;
		
	}
}

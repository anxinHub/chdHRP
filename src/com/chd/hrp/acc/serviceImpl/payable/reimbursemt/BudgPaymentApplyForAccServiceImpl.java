package com.chd.hrp.acc.serviceImpl.payable.reimbursemt;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.reimbursemt.BudgPaymentApplyForAccMapper;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyForAccService;
import com.github.pagehelper.PageInfo;

@Service("budgPaymentApplyForAccService")
public class BudgPaymentApplyForAccServiceImpl implements BudgPaymentApplyForAccService {

	private static Logger logger = Logger.getLogger(BudgPaymentApplyForAccServiceImpl.class);

	@Resource(name = "budgPaymentApplyForAccMapper")
	private final BudgPaymentApplyForAccMapper budgPaymentApplyForAccMapper = null;

	@Override
	public String queryBudgPaymentApplyForAcc(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = budgPaymentApplyForAccMapper.queryBudgPaymentApplyForAcc(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = budgPaymentApplyForAccMapper.queryBudgPaymentApplyForAcc(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public List<Map<String,Object>> queryBudgPaymentApplyForAccPrint(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> list = budgPaymentApplyForAccMapper.queryBudgPaymentApplyForAccPrint(entityMap);

		return list;

	}

}

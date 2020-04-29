package com.chd.hrp.acc.serviceImpl.payable.otherpay;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.otherpay.BudgChargeApplyForAccMapper;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyForAccService;
import com.github.pagehelper.PageInfo;

@Service("budgChargeApplyForAccService")
public class BudgChargeApplyForAccServiceImpl implements BudgChargeApplyForAccService {

	private static Logger logger = Logger.getLogger(BudgChargeApplyForAccServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgChargeApplyForAccMapper")
	private final BudgChargeApplyForAccMapper budgChargeApplyForAccMapper = null;

	@Override
	public String queryBudgChargeApplyPayForAcc(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = budgChargeApplyForAccMapper.queryBudgChargeApplyPayForAcc(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());

			List<Map<String, Object>> list = budgChargeApplyForAccMapper.queryBudgChargeApplyPayForAcc(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

}

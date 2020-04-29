package com.chd.hrp.acc.serviceImpl.payable.loanmt;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrPayForAccMapper;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrPayForAccService;
import com.github.pagehelper.PageInfo;


@Service("budgBorrPayForAccService")
public class BudgBorrPayForAccServiceImpl implements BudgBorrPayForAccService {

	private static Logger logger = Logger.getLogger(BudgBorrPayForAccServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgBorrPayForAccMapper")
	private final BudgBorrPayForAccMapper budgBorrPayForAccMapper = null;

	@Override
	public String queryBudgBorrPayForAcc(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = budgBorrPayForAccMapper.queryBudgBorrPayForAcc(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = budgBorrPayForAccMapper.queryBudgBorrPayForAcc(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

}

package com.chd.hrp.acc.serviceImpl.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.AccBookPrintMapper;
import com.chd.hrp.acc.service.books.AccBookPrintService;
import com.chd.hrp.acc.serviceImpl.AccBadDebtsServiceImpl;

@Service("accBookPrintService")
public class AccBookPrintServiceImpl implements AccBookPrintService {

	private static Logger logger = Logger.getLogger(AccBadDebtsServiceImpl.class);
	
	@Resource(name = "accBookPrintMapper")
	private final AccBookPrintMapper accBookPrintMapper = null;
	
	@Override
	public String queryAccBooksPrintSubj(Map<String, Object> mapVo) throws DataAccessException {
		
		return ChdJson.toJson(accBookPrintMapper.queryAccBooksPrintSubj(mapVo));
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccBooksPrint(Map<String, Object> mapVo) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		
		mapVo.put("rst", new ArrayList<Map<String, Object>>());
		accBookPrintMapper.collectAccBooksPrint(mapVo);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		Map<String, Object> main = new HashMap<String, Object>();
		main.put("p_acc_year", mapVo.get("acc_year"));
		main.put("p_year_month_b", mapVo.get("begin_month"));
		main.put("p_year_month_e", mapVo.get("end_month"));
		main.put("p_acc_year", mapVo.get("acc_year"));
		main.put("p_subj_code", mapVo.get("print_code") + " " + mapVo.get("print_name"));

		reMap.put("main", main);
		reMap.put("detail", (List<Map<String, Object>>) mapVo.get("rst"));
		return reMap;
	}
}

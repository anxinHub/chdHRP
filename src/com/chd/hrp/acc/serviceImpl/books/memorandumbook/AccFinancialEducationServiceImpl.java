package com.chd.hrp.acc.serviceImpl.books.memorandumbook;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.memorandumbook.AccFinancialEducationMapper;
import com.chd.hrp.acc.entity.AccFinancialEducation;
import com.chd.hrp.acc.service.books.memorandumbook.AccFinancialEducationService;
import com.github.pagehelper.PageInfo;

@Service("accFinancialEducationService")
public class AccFinancialEducationServiceImpl implements AccFinancialEducationService{
	
private static Logger logger = Logger.getLogger(AccFinancialEducationServiceImpl.class);
	
	@Resource(name = "accFinancialEducationMapper")
	private final AccFinancialEducationMapper accFinancialEducationMapper = null;

	@Override
	public String queryAccFinancialEducation(Map<String, Object> entityMap) throws DataAccessException {
		
		if("false".equals(entityMap.get("usePager"))){
			
			List<AccFinancialEducation> list = accFinancialEducationMapper.queryAccFinancialEducation(entityMap);
			
			return ChdJson.toJson(list);
		  }
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccFinancialEducation> list = accFinancialEducationMapper.queryAccFinancialEducation(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryAccFinancialEducationDataMining(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = null;
	
		String identification = entityMap.get("identification").toString();
		
        if("financialeducation_total_sr".equals(identification)){
        	/*合计资金 本期收入*/
        	
        	
        }else if("financialeducation_total_ot".equals(identification)){
        	/*合计资金 本期支出*/
        	
        }else if("financialeducation_bal_sr".equals(identification)){
        	/*外拨资金 本期收入*/
        	list = accFinancialEducationMapper.queryAccFinancialEducationDataMiningbalsr(entityMap);
        	
        }else if("financialeducation_bal_ot".equals(identification)){
        	/*外拨资金 本期支出*/
        	list = accFinancialEducationMapper.queryAccFinancialEducationDataMiningbalot(entityMap);
        	
        }else if("financialeducation_match_sr".equals(identification)){
        	/*配套资金本期收入*/
        	
        	list = accFinancialEducationMapper.queryAccFinancialEducationDataMiningmatchsr(entityMap);
        	
        }else if("financialeducation_match_ot".equals(identification)){
        	/*配套 资金本期收入*/
        	list = accFinancialEducationMapper.queryAccFinancialEducationDataMiningmatchot(entityMap);
        }
        
		return ChdJson.toJson(list);
	}
   
}

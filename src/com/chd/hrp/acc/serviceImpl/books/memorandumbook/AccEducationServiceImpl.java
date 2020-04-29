package com.chd.hrp.acc.serviceImpl.books.memorandumbook;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.memorandumbook.AccEducationMapper;
import com.chd.hrp.acc.dao.books.memorandumbook.AccFinancialEducationMapper;
import com.chd.hrp.acc.dao.books.memorandumbook.AccScienceEducationMapper;
import com.chd.hrp.acc.entity.AccEducation;
import com.chd.hrp.acc.service.books.memorandumbook.AccEducationService;
import com.github.pagehelper.PageInfo;

@Service("accEducationService")
public class AccEducationServiceImpl implements AccEducationService{
	
private static Logger logger = Logger.getLogger(AccEducationServiceImpl.class);
	
	@Resource(name = "accEducationMapper")
	private final AccEducationMapper accEducationMapper = null;
	
	@Resource(name = "accFinancialEducationMapper")
	private final AccFinancialEducationMapper accFinancialEducationMapper = null;
	
	@Resource(name = "accScienceEducationMapper")
	private final AccScienceEducationMapper accScienceEducationMapper = null;

	@Override
	public String queryAccEducation(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccEducation> list = accEducationMapper.queryAccEducation(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	//项目备查簿  东阳专用
	@Override
	public String queryAccEducationBySplit(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccEducation> list = accEducationMapper.queryAccEducationBySplit(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryAccEducationDataMining(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
         List<Map<String, Object>> list = null;
	
		String identification = entityMap.get("identification").toString();
				
		    if("Education_total_sr".equals(identification)){
	        	/*合计资金 本期收入*/
	        	
	        	
	        }else if("Education_total_ot".equals(identification)){
	        	/*合计资金 本期支出*/
	       
	        	
	        }else if("Education_bal_sr".equals(identification)){
	        	/*外拨资金 本期收入*/
	        	list = accEducationMapper.queryAccEducationDataMiningbalsr(entityMap);
	        	
	        }else if("Education_bal_ot".equals(identification)){
	        	/*外拨资金 本期支出*/
	        	list = accEducationMapper.queryAccEducationDataMiningbalot(entityMap);
	        	
	        }else if("Education_match_sr".equals(identification)){
	        	/*配套资金本期收入*/
	        	list =  accEducationMapper.queryAccEducationDataMiningmatchsr(entityMap);
	        	
	        }else if("Education_match_ot".equals(identification)){
	        	/*配套 资金本期收入*/
	        	list =  accEducationMapper.queryAccEducationDataMiningmatchot(entityMap);
	        }
	        
			return ChdJson.toJson(list);
	
	}
	
	//科目项目备查簿   打印
		@Override
		public List<Map<String, Object>> queryAccEducationPrint(
				Map<String, Object> entityMap) throws DataAccessException {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			List<Map<String, Object>> list = accEducationMapper.queryAccEducationPrint(entityMap);
			
			return list ;
		}

		@Override
		public String queryAccEducationDataMiningByDy(Map<String, Object> entityMap) throws DataAccessException {
			 List<Map<String, Object>> list = null;
				
				String identification = entityMap.get("identification").toString();
						
				    if("Education_total_sr_dy".equals(identification)){
			        	/*合计资金 本期收入*/
			        	
			        }else if("Education_total_ot_dy".equals(identification)){
			        	/*合计资金 本期支出*/
			       
			        	
			        }else if("Education_bal_sr_cz_dy".equals(identification)){
			        	/*财政外拨资金 本期收入*/
			        	list = accFinancialEducationMapper.queryAccFinancialEducationDataMiningbalsr(entityMap);
			        	
			        }else if("Education_bal_ot_cz_dy".equals(identification)){
			        	/*财政外拨资金 本期支出*/
			        	list = accFinancialEducationMapper.queryAccFinancialEducationDataMiningbalot(entityMap);
			        	
			        }else if("Education_bal_sr_kj_dy".equals(identification)){
			        	/*科教外拨资金 本期收入*/
			        	list = accScienceEducationMapper.queryAccScienceEducationDataMiningbalsr(entityMap);
			        	
			        }else if("Education_bal_ot_kj_dy".equals(identification)){
			        	/*科教外拨资金 本期支出*/
			        	list = accScienceEducationMapper.queryAccScienceEducationDataMiningbalot(entityMap);
			        	
			        }else if("Education_match_sr".equals(identification)){
			        	/*配套资金本期收入*/
			        	list =  accEducationMapper.queryAccEducationDataMiningmatchsr(entityMap);
			        	
			        }else if("Education_match_ot".equals(identification)){
			        	/*配套 资金本期收入*/
			        	list =  accEducationMapper.queryAccEducationDataMiningmatchot(entityMap);
			        }
			        
					return ChdJson.toJson(list);
		}

		@Override
		public List<Map<String, Object>> queryAccEducationBySplitPrint(
				Map<String, Object> entityMap) throws DataAccessException {
			
			List<Map<String, Object>> list = accEducationMapper.queryAccEducationBySplitPrint(entityMap);
			
			return list ;
		}
		

}

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
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccEducationMapper;
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccFinancialEducationMapper;
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccScienceEducationMapper;
import com.chd.hrp.acc.entity.AccEducation;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccEducationService;
import com.github.pagehelper.PageInfo;

@Service("groupAccEducationService")
public class GroupAccEducationServiceImpl implements GroupAccEducationService{
	
private static Logger logger = Logger.getLogger(GroupAccEducationServiceImpl.class);
	
	@Resource(name = "groupAccEducationMapper")
	private final GroupAccEducationMapper groupAccEducationMapper = null;
	
	@Resource(name = "groupAccFinancialEducationMapper")
	private final GroupAccFinancialEducationMapper groupAccFinancialEducationMapper = null;
	
	@Resource(name = "groupAccScienceEducationMapper")
	private final GroupAccScienceEducationMapper groupAccScienceEducationMapper = null;

	@Override
	public String queryGroupAccEducation(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccEducation> list = groupAccEducationMapper.queryGroupAccEducation(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	//项目备查簿  东阳专用
	@Override
	public String queryGroupAccEducationBySplit(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccEducation> list = groupAccEducationMapper.queryGroupAccEducationBySplit(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryGroupAccEducationDataMining(
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
	        	list = groupAccEducationMapper.queryGroupAccEducationDataMiningbalsr(entityMap);
	        	
	        }else if("Education_bal_ot".equals(identification)){
	        	/*外拨资金 本期支出*/
	        	list = groupAccEducationMapper.queryGroupAccEducationDataMiningbalot(entityMap);
	        	
	        }else if("Education_match_sr".equals(identification)){
	        	/*配套资金本期收入*/
	        	list =  groupAccEducationMapper.queryGroupAccEducationDataMiningmatchsr(entityMap);
	        	
	        }else if("Education_match_ot".equals(identification)){
	        	/*配套 资金本期收入*/
	        	list =  groupAccEducationMapper.queryGroupAccEducationDataMiningmatchot(entityMap);
	        }
	        
			return ChdJson.toJson(list);
	
	}
	
	//科目项目备查簿   打印
		@Override
		public List<Map<String, Object>> queryGroupAccEducationPrint(
				Map<String, Object> entityMap) throws DataAccessException {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			List<Map<String, Object>> list = groupAccEducationMapper.queryGroupAccEducationPrint(entityMap);
			
			return list ;
		}

		@Override
		public String queryGroupAccEducationDataMiningByDy(Map<String, Object> entityMap) throws DataAccessException {
			 List<Map<String, Object>> list = null;
				
				String identification = entityMap.get("identification").toString();
						
				    if("Education_total_sr_dy".equals(identification)){
			        	/*合计资金 本期收入*/
			        	
			        }else if("Education_total_ot_dy".equals(identification)){
			        	/*合计资金 本期支出*/
			       
			        	
			        }else if("Education_bal_sr_cz_dy".equals(identification)){
			        	/*财政外拨资金 本期收入*/
			        	list = groupAccFinancialEducationMapper.queryGroupAccFinancialEducationDataMiningbalsr(entityMap);
			        	
			        }else if("Education_bal_ot_cz_dy".equals(identification)){
			        	/*财政外拨资金 本期支出*/
			        	list = groupAccFinancialEducationMapper.queryGroupAccFinancialEducationDataMiningbalot(entityMap);
			        	
			        }else if("Education_bal_sr_kj_dy".equals(identification)){
			        	/*科教外拨资金 本期收入*/
			        	list = groupAccScienceEducationMapper.queryGroupAccScienceEducationDataMiningbalsr(entityMap);
			        	
			        }else if("Education_bal_ot_kj_dy".equals(identification)){
			        	/*科教外拨资金 本期支出*/
			        	list = groupAccScienceEducationMapper.queryGroupAccScienceEducationDataMiningbalot(entityMap);
			        	
			        }else if("Education_match_sr".equals(identification)){
			        	/*配套资金本期收入*/
			        	list =  groupAccEducationMapper.queryGroupAccEducationDataMiningmatchsr(entityMap);
			        	
			        }else if("Education_match_ot".equals(identification)){
			        	/*配套 资金本期收入*/
			        	list =  groupAccEducationMapper.queryGroupAccEducationDataMiningmatchot(entityMap);
			        }
			        
					return ChdJson.toJson(list);
		}

		@Override
		public List<Map<String, Object>> queryGroupAccEducationBySplitPrint(
				Map<String, Object> entityMap) throws DataAccessException {
			
			List<Map<String, Object>> list = groupAccEducationMapper.queryGroupAccEducationBySplitPrint(entityMap);
			
			return list ;
		}
		

}

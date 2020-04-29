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
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccScienceEducationMapper;
import com.chd.hrp.acc.entity.AccScienceEducation;
import com.chd.hrp.acc.service.books.memorandumbook.AccScienceEducationService;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccScienceEducationService;
import com.github.pagehelper.PageInfo;

@Service("groupAccScienceEducationService")
public class GroupAccScienceEducationServiceImpl implements GroupAccScienceEducationService{
	
private static Logger logger = Logger.getLogger(GroupAccScienceEducationServiceImpl.class);
	
	@Resource(name = "groupAccScienceEducationMapper")
	private final GroupAccScienceEducationMapper groupAccScienceEducationMapper = null;

	@Override
	public String queryGroupAccScienceEducation(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccScienceEducation> list = groupAccScienceEducationMapper.queryGroupAccScienceEducation(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryGroupAccScienceEducationDataMining(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
         List<Map<String, Object>> list = null;
	
		String identification = entityMap.get("identification").toString();
				
		    if("ScienceEducation_total_sr".equals(identification)){
	        	/*合计资金 本期收入*/
	        	
	        	
	        }else if("ScienceEducation_total_ot".equals(identification)){
	        	/*合计资金 本期支出*/
	       
	        	
	        }else if("ScienceEducation_bal_sr".equals(identification)){
	        	/*外拨资金 本期收入*/
	        	list = groupAccScienceEducationMapper.queryGroupAccScienceEducationDataMiningbalsr(entityMap);
	        	
	        }else if("ScienceEducation_bal_ot".equals(identification)){
	        	/*外拨资金 本期支出*/
	        	list = groupAccScienceEducationMapper.queryGroupAccScienceEducationDataMiningbalot(entityMap);
	        	
	        }else if("ScienceEducation_match_sr".equals(identification)){
	        	/*配套资金本期收入*/
	        	list =  groupAccScienceEducationMapper.queryGroupAccScienceEducationDataMiningmatchsr(entityMap);
	        	
	        }else if("ScienceEducation_match_ot".equals(identification)){
	        	/*配套 资金本期收入*/
	        	list =  groupAccScienceEducationMapper.queryGroupAccScienceEducationDataMiningmatchot(entityMap);
	        }
	        
			return ChdJson.toJson(list);
	
	}
	
	//科目项目备查簿   打印
		@Override
		public List<Map<String, Object>> queryGroupAccScienceEducationPrint(
				Map<String, Object> entityMap) throws DataAccessException {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			List<Map<String, Object>> list = groupAccScienceEducationMapper.queryGroupAccScienceEducationPrint(entityMap);
			
			return list ;
		}
		

}

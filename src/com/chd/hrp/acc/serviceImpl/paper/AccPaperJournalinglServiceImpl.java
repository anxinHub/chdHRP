package com.chd.hrp.acc.serviceImpl.paper;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.paper.AccPaperJournalinglMapper;
import com.chd.hrp.acc.service.paper.AccPaperJournalinglService;
import com.github.pagehelper.PageInfo;



@Service("accPaperJournalinglService")
public class AccPaperJournalinglServiceImpl implements AccPaperJournalinglService{

	private static Logger logger = Logger.getLogger(AccPaperJournalinglServiceImpl.class);
	
	@Resource(name = "accPaperJournalinglMapper")
	private final AccPaperJournalinglMapper accPaperJournalinglMapper = null;

	

	@Override
	public String queryAccPaperStockCollectDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub

		 SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) entityMap.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockCollectDetail(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockCollectDetail(entityMap,rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	
	@Override
	public String queryAccPaperStockCollect(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		 SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) entityMap.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockCollect(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockCollect(entityMap,rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	

	@Override
	public String queryAccPaperStockDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			 
			 sysPage = (SysPage) entityMap.get("sysPage");
			 
			 if (sysPage.getTotal()==-1){
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockDetail(entityMap);
					
					return ChdJson.toJson(list);
					
				
				}else{
					
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockDetail(entityMap,rowBounds);
					
			        PageInfo page = new PageInfo(list); 
					
					return ChdJson.toJson(list, page.getTotal());
					
				}
	}

	
	
	
	@Override
	public String queryAccPaperSolaCount(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
          SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) entityMap.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperSolaCount(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperSolaCount(entityMap,rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}



	@Override
	public String queryAccPaperCollectCount(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			 
			 sysPage = (SysPage) entityMap.get("sysPage");
			 
			 if (sysPage.getTotal()==-1){
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperCollectCount(entityMap);
					
					return ChdJson.toJson(list);
					
				
				}else{
					
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperCollectCount(entityMap,rowBounds);
					
			        PageInfo page = new PageInfo(list); 
					
					return ChdJson.toJson(list, page.getTotal());
					
				}
	}


	@Override
	public String queryAccPaperInOutCollect(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		if("false".equals(entityMap.get("usePager"))){
			
			List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperInOutCollect(entityMap);
			
			return ChdJson.toJson(list);
		  }
		
		  SysPage sysPage = new SysPage();
			 
			 sysPage = (SysPage) entityMap.get("sysPage");
			 
			 if (sysPage.getTotal()==-1){
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperInOutCollect(entityMap);
					
					return ChdJson.toJson(list);
					
				
				}else{
					
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperInOutCollect(entityMap,rowBounds);
					
			        PageInfo page = new PageInfo(list); 
					
					return ChdJson.toJson(list, page.getTotal());
					
				}
	}


	@Override
	public String queryAccPaperIntercourseFundsDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			 
			 sysPage = (SysPage) entityMap.get("sysPage");
			 
			 if (sysPage.getTotal()==-1){
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperIntercourseFundsDetail(entityMap);
					
					return ChdJson.toJson(list);
					
				
				}else{
					
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
					List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperIntercourseFundsDetail(entityMap,rowBounds);
					
			        PageInfo page = new PageInfo(list); 
					
					return ChdJson.toJson(list, page.getTotal());
					
				}
	}


	@Override
	public List<Map<String, Object>> queryAccPaperStockCollectPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockCollect(entityMap);
		
		return list;
	}


	@Override
	public List<Map<String, Object>> queryAccPaperStockDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperStockDetail(entityMap);
		
		return list;
	}


	@Override
	public List<Map<String, Object>> queryAccPaperSolaCountPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperSolaCount(entityMap);
		
		return list;
	}


	@Override
	public List<Map<String, Object>> queryAccPaperCollectCountPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperCollectCount(entityMap);
		
		return list;
	}


	@Override
	public List<Map<String, Object>> queryAccPaperInOutCollectPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperInOutCollect(entityMap);
		
		return list;
	}


	@Override
	public List<Map<String, Object>> queryAccPaperIntercourseFundsDetailPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperJournalinglMapper.queryAccPaperIntercourseFundsDetail(entityMap);
		
		return list;
	}



	

}

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
import com.chd.hrp.acc.dao.paper.AccPaperDetailMapper;
import com.chd.hrp.acc.service.paper.AccPaperDetailService;
import com.github.pagehelper.PageInfo;



@Service("accPaperDetailService")
public class AccPaperDetailServiceImpl implements AccPaperDetailService{

	private static Logger logger = Logger.getLogger(AccPaperDetailServiceImpl.class);
	
	@Resource(name = "accPaperDetailMapper")
	private final AccPaperDetailMapper accPaperDetailMapper = null;

	@Override
	public String queryAccPaperDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			 
			 sysPage = (SysPage) entityMap.get("sysPage");
			 
			 if (sysPage.getTotal()==-1){
					
					List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetail(entityMap);
					
					return ChdJson.toJson(list);
					
				
				}else{
					
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					
					List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetail(entityMap,rowBounds);
					
			        PageInfo page = new PageInfo(list); 
					
					return ChdJson.toJson(list, page.getTotal());
					
				}

	}

	@Override
	public String updateBatchAccPaperDetail(List<Map<String, Object>> list,String getMessage)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			accPaperDetailMapper.updateBatchAccPaperDetail(list);
			
			
			return "{\"msg\":\""+getMessage+"成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\""+getMessage+"失败 数据库异常 请联系管理员! 错误编码 updateBatchAccPaperDetail\"}";
		}
		
	}

	@Override
	public String updateBatchAccPaperDetail(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
			accPaperDetailMapper.updateBatchAccPaperDetail(list);
			
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateBatchAccPaperDetail\"}";
		}
	}

	@Override
	public String deleteBatchAccPaperDetail(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
          try {
			
			accPaperDetailMapper.deleteBatchAccPaperDetail(list);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchAccPaperDetail\"}";
		}
	}

	
	@Override
	public String queryAccPaperDetailManage(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
          SysPage sysPage = new SysPage();
		 
		  sysPage = (SysPage) entityMap.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetailManage(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetailManage(entityMap,rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/*
	 *单张票据核销查询 
	 * */
	@Override
	public String queryAccPaperDetailSola(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) entityMap.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetailSola(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetailSola(entityMap,rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public List<Map<String, Object>> queryexistAccPaperDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accPaperDetailMapper.queryAccPaperDetail(entityMap);
	}
	
	@Override
	public List<Map<String, Object>> queryAccPaperDetailUseOnePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetail(entityMap);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAccPaperDetailUseTwoPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetail(entityMap);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAccPaperDetailManagePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetailManage(entityMap);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAccPaperDetailSolaPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperDetailMapper.queryAccPaperDetailSola(entityMap);
		
		return list;
	}



}

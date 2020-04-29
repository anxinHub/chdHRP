
package com.chd.hrp.htc.serviceImpl.task.business;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.task.business.HtcSrouceTestMapper;
import com.chd.hrp.htc.entity.task.business.HtcSrouceTest;
import com.chd.hrp.htc.service.task.business.HtcSrouceTestService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcSrouceTestService")
public class HtcSrouceTestServiceImpl implements HtcSrouceTestService {

	private static Logger logger = Logger.getLogger(HtcSrouceTestServiceImpl.class);
	
	@Resource(name = "htcSrouceTestMapper")
	private final HtcSrouceTestMapper htcSrouceTestMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String queryHtcSrouceTest(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			
			List<HtcSrouceTest> list = htcSrouceTestMapper.queryHtcSrouceTest(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcSrouceTest> list = htcSrouceTestMapper.queryHtcSrouceTest(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * 资源动因校验
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String checkHtcSrouceTest(Map<String, Object> entityMap) throws DataAccessException{
		
	      try {
	    	  
	    	  String err_txt = "";
		     
	    	  htcSrouceTestMapper.checkHtcSrouceTest(entityMap);
	    	  
	    	  err_txt = entityMap.get("err_txt").toString();
	    	  
	    	  return "{\"msg\":\""+err_txt+".\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"校验失败\"}");
			}
	}
	
	@Override
    public String queryHtcSrouceTestCostItem(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcSrouceTest> list = htcSrouceTestMapper.queryHtcSrouceTestCostItem(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcSrouceTest> list = htcSrouceTestMapper.queryHtcSrouceTestCostItem(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
    }
	

	@Override
    public String queryHtcSrouceTestCostItemSource(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcSrouceTest> list = htcSrouceTestMapper.queryHtcSrouceTestCostItemSource(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcSrouceTest> list = htcSrouceTestMapper.queryHtcSrouceTestCostItemSource(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
    }
	
	
}

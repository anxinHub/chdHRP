
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
import com.chd.hrp.htc.dao.task.business.HtcWorkTestMapper;
import com.chd.hrp.htc.entity.task.business.HtcWorkTest;
import com.chd.hrp.htc.service.task.business.HtcWorkTestService;
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


@Service("htcWorkTestService")
public class HtcWorkTestServiceImpl implements HtcWorkTestService {

	private static Logger logger = Logger.getLogger(HtcWorkTestServiceImpl.class);
	
	@Resource(name = "htcWorkTestMapper")
	private final HtcWorkTestMapper htcWorkTestMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String queryHtcWorkTest(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkTest> list = htcWorkTestMapper.queryHtcWorkTest(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkTest> list = htcWorkTestMapper.queryHtcWorkTest(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	@Override
	public  String checkHtcWorkTest(Map<String, Object> entityMap) throws DataAccessException{
		try {
			
			 String err_txt = "";
			 
			htcWorkTestMapper.checkHtcWorkTest(entityMap);
			
			err_txt = entityMap.get("err_txt").toString();
			
			return "{\"msg\":\""+err_txt+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"失败\"}");
		}
	}
	
	@Override
    public String queryHtcWorkDetailTest(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkTest> list = htcWorkTestMapper.queryHtcWorkDetailTest(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkTest> list = htcWorkTestMapper.queryHtcWorkDetailTest(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
    }

	@Override
    public String queryHtcWorkItemDetailTest(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcWorkTest> list = htcWorkTestMapper.queryHtcWorkItemDetailTest(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcWorkTest> list = htcWorkTestMapper.queryHtcWorkItemDetailTest(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
    }
}


package com.chd.hrp.htc.serviceImpl.task.projectcost;

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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptChargeWorkDetailMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeWorkDetail;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeWorkDetailService;
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
@Service("htcDeptChargeWorkDetailService")
public class HtcDeptChargeWorkDetailServiceImpl implements HtcDeptChargeWorkDetailService {

	private static Logger logger = Logger.getLogger(HtcDeptChargeWorkDetailServiceImpl.class);
	
	@Resource(name = "htcDeptChargeWorkDetailMapper")
	private final HtcDeptChargeWorkDetailMapper htcDeptChargeWorkDetailMapper = null;

	@Override
	public String saveHtcDeptChargeWorkDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			
			 
			 HtcDeptChargeWorkDetail htcDeptChargeWorkDetail = htcDeptChargeWorkDetailMapper.queryHtcDeptChargeWorkDetailByCode(entityMap);
			 
			 if(null == htcDeptChargeWorkDetail){
				 htcDeptChargeWorkDetailMapper.addHtcDeptChargeWorkDetail(entityMap);				 
			 }else {
				 htcDeptChargeWorkDetailMapper.updateHtcDeptChargeWorkDetail(entityMap);
			 }
			 
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"添加失败\"}");
			}
	}

	@Override
	public String queryHtcDeptChargeWorkDetailWork(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeWorkDetail> list = htcDeptChargeWorkDetailMapper.queryHtcDeptChargeWorkDetailWork(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeWorkDetail> list = htcDeptChargeWorkDetailMapper.queryHtcDeptChargeWorkDetailWork(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcDeptChargeWorkDetailTitle(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeWorkDetail> list = htcDeptChargeWorkDetailMapper.queryHtcDeptChargeWorkDetailTitle(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeWorkDetail> list = htcDeptChargeWorkDetailMapper.queryHtcDeptChargeWorkDetailTitle(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryHtcDeptChargeWorkDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeWorkDetail> list = htcDeptChargeWorkDetailMapper.queryHtcDeptChargeWorkDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeWorkDetail> list = htcDeptChargeWorkDetailMapper.queryHtcDeptChargeWorkDetail(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public String deleteBatchHtcDeptChargeWorkDetail(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			 
    	     htcDeptChargeWorkDetailMapper.deleteBatchHtcDeptChargeWorkDetail(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}
}


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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptWorkPeopleRatioMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptWorkPeopleRatio;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptWorkPeopleRatioService;
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


@Service("htcDeptWorkPeopleRatioService")
public class HtcDeptWorkPeopleRatioServiceImpl implements HtcDeptWorkPeopleRatioService {

	private static Logger logger = Logger.getLogger(HtcDeptWorkPeopleRatioServiceImpl.class);
	
	@Resource(name = "htcDeptWorkPeopleRatioMapper")
	private final HtcDeptWorkPeopleRatioMapper htcDeptWorkPeopleRatioMapper = null;

	@Override
	public String addHtcDeptWorkPeopleRatio(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptWorkPeopleRatio htcDeptWorkPeopleRatio = htcDeptWorkPeopleRatioMapper.queryHtcDeptWorkPeopleRatioByCode(entityMap);
			 if(null != htcDeptWorkPeopleRatio){
					return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			 }
			htcDeptWorkPeopleRatioMapper.addHtcDeptWorkPeopleRatio(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcDeptWorkPeopleRatio(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptWorkPeopleRatio> list = htcDeptWorkPeopleRatioMapper.queryHtcDeptWorkPeopleRatio(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptWorkPeopleRatio> list = htcDeptWorkPeopleRatioMapper.queryHtcDeptWorkPeopleRatio(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcDeptWorkPeopleRatio queryHtcDeptWorkPeopleRatioByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptWorkPeopleRatioMapper.queryHtcDeptWorkPeopleRatioByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptWorkPeopleRatio(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
			  htcDeptWorkPeopleRatioMapper.deleteBatchHtcDeptWorkPeopleRatio(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");	
			}
	}

	@Override
	public String updateHtcDeptWorkPeopleRatio(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			  htcDeptWorkPeopleRatioMapper.updateHtcDeptWorkPeopleRatio(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");	
			}
	}
    
}

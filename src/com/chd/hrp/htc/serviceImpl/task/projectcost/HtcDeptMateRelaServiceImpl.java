
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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptMateRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptMateRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptMateRelaService;
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


@Service("htcDeptMateRelaService")
public class HtcDeptMateRelaServiceImpl implements HtcDeptMateRelaService {

	private static Logger logger = Logger.getLogger(HtcDeptMateRelaServiceImpl.class);
	
	@Resource(name = "htcDeptMateRelaMapper")
	private final HtcDeptMateRelaMapper htcDeptMateRelaMapper = null;

	@Override
	public String addHtcDeptMateRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
		   
		   HtcDeptMateRela htcDeptMateRela = htcDeptMateRelaMapper.queryHtcDeptMateRelaByCode(entityMap);
	       if(null != htcDeptMateRela){
	    	   return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
	       }
		   htcDeptMateRelaMapper.addHtcDeptMateRela(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptMateRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptMateRela> list = htcDeptMateRelaMapper.queryHtcDeptMateRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptMateRela> list = htcDeptMateRelaMapper.queryHtcDeptMateRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcDeptMateRela queryHtcDeptMateRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptMateRelaMapper.queryHtcDeptMateRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptMateRela(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		   try {
				
			   htcDeptMateRelaMapper.deleteBatchHtcDeptMateRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");	
			}
	}

	@Override
	public String updateHtcDeptMateRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		    try {
			
			   htcDeptMateRelaMapper.updateHtcDeptMateRela(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");	
			}
	}
	
}

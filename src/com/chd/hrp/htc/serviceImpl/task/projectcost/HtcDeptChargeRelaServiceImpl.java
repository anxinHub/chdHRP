
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
import com.chd.hrp.htc.dao.task.projectcost.HtcDeptChargeRelaMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeRelaService;
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


@Service("htcDeptChargeRelaService")
public class HtcDeptChargeRelaServiceImpl implements HtcDeptChargeRelaService {

	private static Logger logger = Logger.getLogger(HtcDeptChargeRelaServiceImpl.class);
	
	@Resource(name = "htcDeptChargeRelaMapper")
	private final HtcDeptChargeRelaMapper htcDeptChargeRelaMapper = null;

	@Override
	public String addHtcDeptChargeRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcDeptChargeRela htcDeptChargeRela = htcDeptChargeRelaMapper.queryHtcDeptChargeRelaByCode(entityMap);
			
			if(null != htcDeptChargeRela){
				return "{\"error\":\"对应关系已经存在.\",\"state\":\"false\"}";
			}
			htcDeptChargeRelaMapper.addHtcDeptChargeRela(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcDeptChargeRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   SysPage sysPage = new SysPage();
			
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcDeptChargeRela> list = htcDeptChargeRelaMapper.queryHtcDeptChargeRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcDeptChargeRela> list = htcDeptChargeRelaMapper.queryHtcDeptChargeRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcDeptChargeRela queryHtcDeptChargeRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcDeptChargeRelaMapper.queryHtcDeptChargeRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcDeptChargeRela(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
         try {
			
			htcDeptChargeRelaMapper.deleteBatchHtcDeptChargeRela(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String updateHtcDeptChargeRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
			
				htcDeptChargeRelaMapper.updateHtcDeptChargeRela(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");	
			}
				
	}
}

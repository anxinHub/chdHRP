
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
import com.chd.hrp.htc.dao.task.projectcost.HtcWorkRearMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptMateRela;
import com.chd.hrp.htc.entity.task.projectcost.HtcWorkRear;
import com.chd.hrp.htc.service.task.projectcost.HtcWorkRearService;
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


@Service("htcWorkRearService")
public class HtcWorkRearServiceImpl implements HtcWorkRearService {

	private static Logger logger = Logger.getLogger(HtcWorkRearServiceImpl.class);
	
	@Resource(name = "htcWorkRearMapper")
	private final HtcWorkRearMapper htcWorkRearMapper = null;

	@Override
	public String addHtcWorkRear(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
			   
			  HtcWorkRear htcWorkRear = htcWorkRearMapper.queryHtcWorkRearByCode(entityMap);
		       if(null != htcWorkRear){
		    	   return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
		       }
		       htcWorkRearMapper.addHtcWorkRear(entityMap);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"添加失败\"}");	
			}
	}

	@Override
	public String queryHtcWorkRear(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				
				List<HtcWorkRear> list = htcWorkRearMapper.queryHtcWorkRear(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<HtcWorkRear> list = htcWorkRearMapper.queryHtcWorkRear(entityMap, rowBounds);
			
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}

	@Override
	public HtcWorkRear queryHtcWorkRearByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcWorkRearMapper.queryHtcWorkRearByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcWorkRear(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			     htcWorkRearMapper.deleteBatchHtcWorkRear(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");	
			}
	}

	@Override
	public String updateHtcWorkRear(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
		     htcWorkRearMapper.updateHtcWorkRear(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
}

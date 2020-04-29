package com.chd.hrp.htcg.serviceImpl.calculation;

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
import com.chd.hrp.htcg.dao.calculation.HtcgDeptDdrgsCostMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDeptDdrgsCost;
import com.chd.hrp.htcg.service.calculation.HtcgDeptDdrgsCostService;
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


@Service("htcgDeptDdrgsCostService")
public class HtcgDeptDdrgsCostServiceImpl implements HtcgDeptDdrgsCostService {

	private static Logger logger = Logger.getLogger(HtcgDeptDdrgsCostServiceImpl.class);
	
	@Resource(name = "htcgDeptDdrgsCostMapper")
	private final HtcgDeptDdrgsCostMapper htcgDeptDdrgsCostMapper = null;

	@Override
	public String initHtcgDeptDdrgsCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			 htcgDeptDdrgsCostMapper.initHtcgDeptDdrgsCost(entityMap);
			    
			    return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgDeptDdrgsCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcgDeptDdrgsCost> list = htcgDeptDdrgsCostMapper.queryHtcgDeptDdrgsCost(entityMap) ;
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgDeptDdrgsCost> list = htcgDeptDdrgsCostMapper.queryHtcgDeptDdrgsCost(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgDeptDdrgsCost(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   try {
				
			   htcgDeptDdrgsCostMapper.deleteBatchHtcgDeptDdrgsCost(list);
				 return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}
}

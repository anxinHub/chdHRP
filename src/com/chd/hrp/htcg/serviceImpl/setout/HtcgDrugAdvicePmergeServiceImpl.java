package com.chd.hrp.htcg.serviceImpl.setout;

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
import com.chd.hrp.htcg.dao.setout.HtcgDrugAdvicePmergeMapper;
import com.chd.hrp.htcg.entity.setout.HtcgDrugAdvicePmerge;
import com.chd.hrp.htcg.service.setout.HtcgDrugAdvicePmergeService;
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
@Service("htcgDrugAdvicePmergeService")
public class HtcgDrugAdvicePmergeServiceImpl implements HtcgDrugAdvicePmergeService {

	private static Logger logger = Logger.getLogger(HtcgDrugAdvicePmergeServiceImpl.class);
	
	@Resource(name = "htcgDrugAdvicePmergeMapper")
	private final HtcgDrugAdvicePmergeMapper htcgDrugAdvicePmergeMapper = null;

	@Override
	public String initHtcgDrugAdvicePmerge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			 htcgDrugAdvicePmergeMapper.initHtcgDrugAdvicePmerge(entityMap);
			  
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String queryHtcgDrugAdvicePmerge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgDrugAdvicePmerge> list = htcgDrugAdvicePmergeMapper.queryHtcgDrugAdvicePmerge(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgDrugAdvicePmerge> list = htcgDrugAdvicePmergeMapper.queryHtcgDrugAdvicePmerge(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBathcHtcgDrugAdvicePmerge(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			 htcgDrugAdvicePmergeMapper.deleteBathcHtcgDrugAdvicePmerge(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		  } catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
    
}

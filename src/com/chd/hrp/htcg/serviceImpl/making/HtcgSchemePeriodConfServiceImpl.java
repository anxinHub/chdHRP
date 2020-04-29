package com.chd.hrp.htcg.serviceImpl.making;
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
import com.chd.hrp.htcg.dao.making.HtcgSchemePeriodConfMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemePeriodConf;
import com.chd.hrp.htcg.service.making.HtcgSchemePeriodConfService;
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


@Service("htcgSchemePeriodConfService")
public class HtcgSchemePeriodConfServiceImpl implements HtcgSchemePeriodConfService {

	private static Logger logger = Logger.getLogger(HtcgSchemePeriodConfServiceImpl.class);
	
	@Resource(name = "htcgSchemePeriodConfMapper")
	private final HtcgSchemePeriodConfMapper htcgSchemePeriodConfMapper = null;

	@Override
	public String initHtcgSchemePeriodConf(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	try {
			
		htcgSchemePeriodConfMapper.initHtcgSchemePeriodConf(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgSchemePeriodConf(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemePeriodConf> list = htcgSchemePeriodConfMapper.queryHtcgSchemePeriodConf(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemePeriodConf> list = htcgSchemePeriodConfMapper.queryHtcgSchemePeriodConf(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchHtcgSchemePeriodConf(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgSchemePeriodConfMapper.deleteBatchHtcgSchemePeriodConf(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String saveHtcgSchemePeriodConf(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
           try {
			System.out.println("list = " + list);
			htcgSchemePeriodConfMapper.updateBatchHtcgSchemePeriodConf(list);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"保存失败\"}");
			}
	}

	
}

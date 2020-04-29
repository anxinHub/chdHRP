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
import com.chd.hrp.htcg.dao.making.HtcgSchemeMapper;
import com.chd.hrp.htcg.entity.making.HtcgScheme;
import com.chd.hrp.htcg.service.making.HtcgSchemeService;
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


@Service("htcgSchemeService")
public class HtcgSchemeServiceImpl implements HtcgSchemeService {

	private static Logger logger = Logger.getLogger(HtcgSchemeServiceImpl.class);
	
	@Resource(name = "htcgSchemeMapper")
	private final HtcgSchemeMapper htcgSchemeMapper = null;

	@Override
	public String addHtcgScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
		  	 HtcgScheme htcgScheme = htcgSchemeMapper.queryHtcgSchemeByCode(entityMap);
		  	 
		  	 if(null != htcgScheme){
		  		 
		  		return "{\"error\":\"方案编码重复!.\",\"state\":\"false\"}";
		  	 }
			htcgSchemeMapper.addHtcgScheme(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgScheme(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
         try {
			
			htcgSchemeMapper.addBatchHtcgScheme(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgScheme> list = htcgSchemeMapper
					.queryHtcgScheme(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcgScheme> list = htcgSchemeMapper.queryHtcgScheme(
					entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgScheme queryHtcgSchemeByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgSchemeMapper.queryHtcgSchemeByCode(entityMap);
	}

	@Override
	public String deleteHtcgScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
			htcgSchemeMapper.deleteHtcgScheme(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgScheme(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
				htcgSchemeMapper.deleteBatchHtcgScheme(list);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String updateHtcgScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			htcgSchemeMapper.updateHtcgScheme(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}


	
	 
}

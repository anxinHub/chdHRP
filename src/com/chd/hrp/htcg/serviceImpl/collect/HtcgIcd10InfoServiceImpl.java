package com.chd.hrp.htcg.serviceImpl.collect;
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
import com.chd.hrp.htcg.dao.collect.HtcgIcd10InfoMapper;
import com.chd.hrp.htcg.entity.collect.HtcgIcd10Info;
import com.chd.hrp.htcg.service.collect.HtcgIcd10InfoService;
import com.github.pagehelper.PageInfo;

@Service("htcgIcd10InfoService")
public class HtcgIcd10InfoServiceImpl implements HtcgIcd10InfoService {

	private static Logger logger = Logger.getLogger(HtcgIcd10InfoServiceImpl.class);
	
	@Resource(name = "htcgIcd10InfoMapper")
	private final HtcgIcd10InfoMapper htcgIcd10InfoMapper = null;
	

	@Override
	public String addHtcgIcd10Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			HtcgIcd10Info htcgIcd10Info = htcgIcd10InfoMapper.queryHtcgIcd10InfoByCode(entityMap);
			if(null != htcgIcd10Info){
				return "{\"error\":\"病案号住院号重复！.\",\"state\":\"false\"}";
			}
			htcgIcd10InfoMapper.addHtcgIcd10Info(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBathcHtcgIcd10Info(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgIcd10InfoMapper.addBathcHtcgIcd10Info(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgIcd10Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgIcd10Info> list = htcgIcd10InfoMapper.queryHtcgIcd10Info(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcgIcd10Info> list = htcgIcd10InfoMapper.queryHtcgIcd10Info(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}


	@Override
	public HtcgIcd10Info queryHtcgIcd10InfoByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgIcd10InfoMapper.queryHtcgIcd10InfoByCode(entityMap);
	}

	@Override
	public String deleteHtcgIcd10Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgIcd10InfoMapper.deleteHtcgIcd10Info(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgIcd10Info(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			
			htcgIcd10InfoMapper.deleteBatchHtcgIcd10Info(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgIcd10Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
				htcgIcd10InfoMapper.updateHtcgIcd10Info(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}
	
}

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
import com.chd.hrp.htcg.dao.collect.HtcgMaterialAdviceMapper;
import com.chd.hrp.htcg.entity.collect.HtcgMaterialAdvice;
import com.chd.hrp.htcg.service.collect.HtcgMaterialAdviceService;
import com.github.pagehelper.PageInfo;



@Service("htcgMaterialAdviceService")
public class HtcgMaterialAdviceServiceImpl implements HtcgMaterialAdviceService {

	private static Logger logger = Logger.getLogger(HtcgMaterialAdviceServiceImpl.class);
	
	@Resource(name = "htcgMaterialAdviceMapper")
	private final HtcgMaterialAdviceMapper htcgMaterialAdviceMapper = null;
	

	@Override
	public String addHtcgMaterialAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			HtcgMaterialAdvice htcgMaterialAdvice = htcgMaterialAdviceMapper.queryHtcgMaterialAdviceByCode(entityMap);
			if(null != htcgMaterialAdvice){
				return "{\"error\":\"数据重复请重新添加.\",\"state\":\"false\"}";
			}
			htcgMaterialAdviceMapper.addHtcgMaterialAdvice(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgMaterialAdvice(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMaterialAdviceMapper.addBatchHtcgMaterialAdvice(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgMaterialAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgMaterialAdvice> list = htcgMaterialAdviceMapper.queryHtcgMaterialAdvice(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcgMaterialAdvice> list = htcgMaterialAdviceMapper.queryHtcgMaterialAdvice(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgMaterialAdvice queryHtcgMaterialAdviceByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgMaterialAdviceMapper.queryHtcgMaterialAdviceByCode(entityMap);
	}

	@Override
	public String deleteHtcgMaterialAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMaterialAdviceMapper.deleteHtcgMaterialAdvice(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgMaterialAdvice(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMaterialAdviceMapper.deleteBatchHtcgMaterialAdvice(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgMaterialAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMaterialAdviceMapper.updateHtcgMaterialAdvice(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

}

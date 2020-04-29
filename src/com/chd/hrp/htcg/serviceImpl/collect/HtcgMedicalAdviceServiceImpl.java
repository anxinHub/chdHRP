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
import com.chd.base.util.DateUtil;
import com.chd.hrp.htcg.dao.collect.HtcgMedicalAdviceMapper;
import com.chd.hrp.htcg.entity.collect.HtcgMedicalAdvice;
import com.chd.hrp.htcg.service.collect.HtcgMedicalAdviceService;
import com.github.pagehelper.PageInfo;

@Service("htcgMedicalAdviceService")
public class HtcgMedicalAdviceServiceImpl implements HtcgMedicalAdviceService {
	
	private static Logger logger = Logger.getLogger(HtcgMedicalAdviceServiceImpl.class);
	
	@Resource(name = "htcgMedicalAdviceMapper")
	private final HtcgMedicalAdviceMapper htcgMedicalAdviceMapper = null;

	@Override
	public String addHtcgMedicalAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			entityMap.put("advice_date", DateUtil.stringToDate(entityMap.get("advice_date").toString(),"yyyy-MM-dd"));
			HtcgMedicalAdvice htcgMedicalAdvice = htcgMedicalAdviceMapper.queryHtcgMedicalAdviceByCode(entityMap);
			if(null != htcgMedicalAdvice){
				return "{\"msg\":\"此数据以存在,请重新添加.\",\"state\":\"false\"}";	
			}
			htcgMedicalAdviceMapper.addHtcgMedicalAdvice(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgMedicalAdvice(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryHtcgMedicalAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgMedicalAdvice> list = htcgMedicalAdviceMapper.queryHtcgMedicalAdvice(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcgMedicalAdvice> list = htcgMedicalAdviceMapper.queryHtcgMedicalAdvice(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgMedicalAdvice queryHtcgMedicalAdviceByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("advice_date", DateUtil.stringToDate(entityMap.get("advice_date").toString(),"yyyy-MM-dd"));
		return htcgMedicalAdviceMapper.queryHtcgMedicalAdviceByCode(entityMap);
	}

	@Override
	public String deleteHtcgMedicalAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMedicalAdviceMapper.deleteHtcgMedicalAdvice(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgMedicalAdvice(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgMedicalAdviceMapper.deleteBatchHtcgMedicalAdvice(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String updateHtcgMedicalAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			entityMap.put("advice_date", DateUtil.stringToDate(entityMap.get("advice_date").toString(),"yyyy-MM-dd"));
			htcgMedicalAdviceMapper.updateHtcgMedicalAdvice(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
}

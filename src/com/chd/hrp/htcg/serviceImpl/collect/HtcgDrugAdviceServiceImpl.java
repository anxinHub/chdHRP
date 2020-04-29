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
import com.chd.hrp.htcg.dao.collect.HtcgDrugAdviceMapper;
import com.chd.hrp.htcg.entity.collect.HtcgDrugAdvice;
import com.chd.hrp.htcg.service.collect.HtcgDrugAdviceService;
import com.github.pagehelper.PageInfo;



@Service("htcgDrugAdviceService")
public class HtcgDrugAdviceServiceImpl implements HtcgDrugAdviceService {

	private static Logger logger = Logger.getLogger(HtcgDrugAdviceServiceImpl.class);
	
	@Resource(name = "htcgDrugAdviceMapper")
	private final HtcgDrugAdviceMapper htcgDrugAdviceMapper = null;
	
	@Override
	public String addHtcgDrugAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			entityMap.put("advice_date", DateUtil.stringToDate(entityMap.get("advice_date").toString(),"yyyy-MM-dd"));
			HtcgDrugAdvice htcgDrugAdvice = htcgDrugAdviceMapper.queryHtcgDrugAdviceByCode(entityMap);
			if(null != htcgDrugAdvice){
				return "{\"msg\":\"此数据以存在,请重新添加.\",\"state\":\"false\"}";	
			}
			htcgDrugAdviceMapper.addHtcgDrugAdvice(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgDrugAdvice(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
	   try {
			
			htcgDrugAdviceMapper.addBatchHtcgDrugAdvice(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgDrugAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgDrugAdvice> list = htcgDrugAdviceMapper.queryHtcgDrugAdvice(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcgDrugAdvice> list = htcgDrugAdviceMapper.queryHtcgDrugAdvice(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgDrugAdvice queryHtcgDrugAdviceByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgDrugAdviceMapper.queryHtcgDrugAdviceByCode(entityMap);
	}

	@Override
	public String deleteHtcgDrugAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
				htcgDrugAdviceMapper.deleteHtcgDrugAdvice(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String deleteBathcHtcgDrugAdvice(List<Map<String,Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				htcgDrugAdviceMapper.deleteBathcHtcgDrugAdvice(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String updateHtcgDrugAdvice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			 entityMap.put("advice_date", DateUtil.stringToDate(entityMap.get("advice_date").toString(),"yyyy-MM-dd"));
				htcgDrugAdviceMapper.updateHtcgDrugAdvice(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

}

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
import com.chd.hrp.htcg.dao.making.HtcgSchemeSimilarTreatMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeSimilarTreat;
import com.chd.hrp.htcg.service.making.HtcgSchemeSimilarTreatService;
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


@Service("htcgSchemeSimilarTreatService")
public class HtcgSchemeSimilarTreatServiceImpl implements HtcgSchemeSimilarTreatService {

	private static Logger logger = Logger.getLogger(HtcgSchemeSimilarTreatServiceImpl.class);
	
	@Resource(name = "htcgSchemeSimilarTreatMapper")
	private final HtcgSchemeSimilarTreatMapper htcgSchemeSimilarTreatMapper = null;

	@Override
	public String addHtcgSchemeSimilarTreat(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			HtcgSchemeSimilarTreat htcgSchemeSimilarTreat = htcgSchemeSimilarTreatMapper.queryHtcgSchemeSimilarTreatByCode(entityMap);
			 if(null != htcgSchemeSimilarTreat){
				return "{\"error\":\"当前标准已存在相似项目.\",\"state\":\"false\"}";
			 }
			htcgSchemeSimilarTreatMapper.addHtcgSchemeSimilarTreat(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgSchemeSimilarTreat(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgSchemeSimilarTreatMapper.addBatchHtcgSchemeSimilarTreat(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgSchemeSimilarTreat(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeSimilarTreat> list = htcgSchemeSimilarTreatMapper.queryHtcgSchemeSimilarTreat(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeSimilarTreat> list = htcgSchemeSimilarTreatMapper.queryHtcgSchemeSimilarTreat(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcgSchemeSimilarTreat queryHtcgSchemeSimilarTreatByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgSchemeSimilarTreatMapper.queryHtcgSchemeSimilarTreatByCode(entityMap);
	}

	@Override
	public String deleteHtcgSchemeSimilarTreat(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgSchemeSimilarTreatMapper.deleteHtcgSchemeSimilarTreat(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgSchemeSimilarTreat(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgSchemeSimilarTreatMapper.deleteBatchHtcgSchemeSimilarTreat(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgSchemeSimilarTreat(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgSchemeSimilarTreatMapper.updateHtcgSchemeSimilarTreat(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String updateBatchHtcgSchemeSimilarTreat(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgSchemeSimilarTreatMapper.updateBatchHtcgSchemeSimilarTreat(list);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
    
	
}

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
import com.chd.hrp.htcg.dao.collect.HtcgIcd9InfoMapper;
import com.chd.hrp.htcg.entity.collect.HtcgIcd9Info;
import com.chd.hrp.htcg.service.collect.HtcgIcd9InfoService;
import com.github.pagehelper.PageInfo;

@Service("htcgIcd9InfoService")
public class HtcgIcd9InfoServiceImpl implements HtcgIcd9InfoService {

	private static Logger logger = Logger.getLogger(HtcgIcd9InfoServiceImpl.class);
	
	
	@Resource(name = "htcgIcd9InfoMapper")
	private final HtcgIcd9InfoMapper htcgIcd9InfoMapper = null;
	
	@Override
	public String addIcdHtcg9Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			entityMap.put("icd9_date", DateUtil.stringToDate(entityMap.get("icd9_date").toString(),"yyyy-MM-dd"));
			HtcgIcd9Info htcgIcd9Info = htcgIcd9InfoMapper.queryHtcgIcd9InfoByCode(entityMap);
			if(null != htcgIcd9Info){
				return "{\"msg\":\"此数据已存在,请重新输入.\",\"state\":\"false\"}";
			}
			htcgIcd9InfoMapper.addIcdHtcg9Info(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgIcd9Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgIcd9Info> list = htcgIcd9InfoMapper.queryHtcgIcd9Info(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgIcd9Info> list = htcgIcd9InfoMapper.queryHtcgIcd9Info(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcgIcd9Info queryHtcgIcd9InfoByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgIcd9InfoMapper.queryHtcgIcd9InfoByCode(entityMap);
	}

	@Override
	public String deleteHtcgIcd9Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgIcd9InfoMapper.deleteHtcgIcd9Info(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgIcd9Info(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgIcd9InfoMapper.deleteBatchHtcgIcd9Info(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgIcd9Info(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			entityMap.put("icd9_date", DateUtil.stringToDate(entityMap.get("icd9_date").toString(),"yyyy-MM-dd"));
			htcgIcd9InfoMapper.updateHtcgIcd9Info(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}
    
}

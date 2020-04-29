package com.chd.hrp.htc.serviceImpl.relative.plan.deptrela;

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
import com.chd.hrp.htc.dao.relative.plan.deptrela.HtcRelativeDeptRelaMapper;
import com.chd.hrp.htc.entity.relative.plan.deptrela.HtcRelativeDeptRela;
import com.chd.hrp.htc.service.relative.plan.deptrela.HtcRelativeDeptRelaService;
import com.github.pagehelper.PageInfo;



@Service("htcRelativeDeptRelaService")
public class HtcRelativeDeptRelaServiceImpl implements HtcRelativeDeptRelaService{
	
private static Logger logger = Logger.getLogger(HtcRelativeDeptRelaServiceImpl.class);
	
	@Resource(name = "htcRelativeDeptRelaMapper")
	private final HtcRelativeDeptRelaMapper htcRelativeDeptRelaMapper = null;

	@Override
	public String addHtcRelativeDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcRelativeDeptRela htcRelativeDeptRela = htcRelativeDeptRelaMapper.queryHtcRelativeDeptRelaByCode(entityMap);
			
			if(null != htcRelativeDeptRela){
				
				return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			}
			
			htcRelativeDeptRelaMapper.addHtcRelativeDeptRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcRelativeDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativeDeptRela> list = htcRelativeDeptRelaMapper.queryHtcRelativeDeptRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativeDeptRela> list = htcRelativeDeptRelaMapper.queryHtcRelativeDeptRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcRelativeDeptRela queryHtcRelativeDeptRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcRelativeDeptRelaMapper.queryHtcRelativeDeptRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcRelativeDeptRela(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcRelativeDeptRelaMapper.deleteBatchHtcRelativeDeptRela(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String updateHtcRelativeDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcRelativeDeptRelaMapper.updateHtcRelativeDeptRela(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
}

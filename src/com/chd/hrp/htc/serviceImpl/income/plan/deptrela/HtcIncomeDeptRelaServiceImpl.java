package com.chd.hrp.htc.serviceImpl.income.plan.deptrela;

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
import com.chd.hrp.htc.dao.income.plan.deptrela.HtcIncomeDeptRelaMapper;
import com.chd.hrp.htc.entity.income.plan.deptrela.HtcIncomeDeptRela;
import com.chd.hrp.htc.service.income.plan.deptrela.HtcIncomeDeptRelaService;
import com.github.pagehelper.PageInfo;



@Service("htcIncomeDeptRelaService")
public class HtcIncomeDeptRelaServiceImpl implements HtcIncomeDeptRelaService{
	
private static Logger logger = Logger.getLogger(HtcIncomeDeptRelaServiceImpl.class);
	
	@Resource(name = "htcIncomeDeptRelaMapper")
	private final HtcIncomeDeptRelaMapper htcIncomeDeptRelaMapper = null;

	@Override
	public String addHtcIncomeDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcIncomeDeptRela htcIncomeDeptRela = htcIncomeDeptRelaMapper.queryHtcIncomeDeptRelaByCode(entityMap);
			
			if(null != htcIncomeDeptRela){
				
				return "{\"error\":\"对应关系已存在.\",\"state\":\"false\"}";
			}
			
			htcIncomeDeptRelaMapper.addHtcIncomeDeptRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcIncomeDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcIncomeDeptRela> list = htcIncomeDeptRelaMapper.queryHtcIncomeDeptRela(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomeDeptRela> list = htcIncomeDeptRelaMapper.queryHtcIncomeDeptRela(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcIncomeDeptRela queryHtcIncomeDeptRelaByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcIncomeDeptRelaMapper.queryHtcIncomeDeptRelaByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcIncomeDeptRela(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcIncomeDeptRelaMapper.deleteBatchHtcIncomeDeptRela(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String updateHtcIncomeDeptRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcIncomeDeptRelaMapper.updateHtcIncomeDeptRela(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
}

package com.chd.hrp.ass.serviceImpl.assremould;

import java.util.HashMap;
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
import com.chd.hrp.ass.dao.assremould.AssRemouldAdetailMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldAsourceSpecialMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldAspecialMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailSpecial;
import com.chd.hrp.ass.entity.assremould.AssRemouldAdetail;
import com.chd.hrp.ass.entity.assremould.AssRemouldAspecial;
import com.chd.hrp.ass.service.assremould.AssRemouldADetailSpecialService;
import com.chd.hrp.ass.serviceImpl.allot.out.AssAllotOutDetailSpecialServiceImpl;
import com.github.pagehelper.PageInfo;



@Service("assRemouldADetailSpecialService")
public class AssRemouldADetailSpecialServiceImpl implements AssRemouldADetailSpecialService {
	
	private static Logger logger = Logger.getLogger(AssRemouldADetailSpecialServiceImpl.class);
	//引入DAO操作
		//明细
		@Resource(name = "assRemouldAdetailMapper")
		private final AssRemouldAdetailMapper assRemouldAdetailMapper = null;
	   //资金来源
		@Resource(name = "assRemouldAsourceSpecialMapper")
		private final AssRemouldAsourceSpecialMapper assRemouldAsourceSpecialMapper = null;
		//主表
		@Resource(name = "assRemouldAspecialMapper")
		private final AssRemouldAspecialMapper assRemouldAspecialMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			
			
			assRemouldAsourceSpecialMapper.deleteBatch(entityList);
			assRemouldAdetailMapper.deleteBatch(entityList);
			
		
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			
		}	
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)assRemouldAdetailMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = (List<Map<String, Object>>)assRemouldAdetailMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


	public List<AssRemouldAdetail> queryByDisANo(Map<String, Object> mapVo) {
		return assRemouldAdetailMapper.queryByDisANo(mapVo);
	}

}

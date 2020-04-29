package com.chd.hrp.ass.serviceImpl.assremould;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.hrp.ass.dao.assremould.AssRemouldFdetailSpecialMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldRdetailSpecialMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldRsourceSpecialMapper;
import com.chd.hrp.ass.entity.assremould.AssRemouldRdetailSpecial;
import com.chd.hrp.ass.service.assremould.AssRemouldRDetailSpecialService;
import com.chd.hrp.ass.serviceImpl.change.AssChangeDetailSpecialServiceImpl;


@Service("assRemouldRDetailSpecialService")
public class AssRemouldRDetailSpecialServiceImpl  implements AssRemouldRDetailSpecialService{
	private static Logger logger = Logger.getLogger(AssRemouldRDetailSpecialServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assRemouldRsourceSpecialMapper")
	private final AssRemouldRsourceSpecialMapper assRemouldRsourceSpecialMapper = null;
	//引入DAO操作
		@Resource(name = "assRemouldRdetailSpecialMapper")
		private final AssRemouldRdetailSpecialMapper assRemouldRdetailSpecialMapper = null;
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
			assRemouldRsourceSpecialMapper.deleteBatch(entityList);
			assRemouldRdetailSpecialMapper.deleteBatch(entityList);
			
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
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public List<AssRemouldRdetailSpecial> queryByDisANo(Map<String, Object> mapVo) {
		return assRemouldRdetailSpecialMapper.queryByDisANo(mapVo);
	}

}

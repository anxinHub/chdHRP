package com.chd.hrp.ass.serviceImpl.assremould;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.ass.dao.assremould.AssRemouldAdetailMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldFdetailSpecialMapper;
import com.chd.hrp.ass.entity.assremould.AssRemouldFdetailSpecial;
import com.chd.hrp.ass.service.assremould.AssRemouldFDetailSpecialService;


@Service("assRemouldFDetailSpecialService")
public class AssRemouldFDetailSpecialServiceImpl implements AssRemouldFDetailSpecialService{
	//引入DAO操作
	@Resource(name = "assRemouldFdetailSpecialMapper")
	private final AssRemouldFdetailSpecialMapper assRemouldFdetailSpecialMapper = null;
    
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
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
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
	public List<AssRemouldFdetailSpecial> queryByDisANo(Map<String, Object> mapVo) {
		return assRemouldFdetailSpecialMapper.queryByDisANo(mapVo);
	}

}

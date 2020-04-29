package com.chd.hrp.sup.serviceImpl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.sup.dao.HrpSupSelectMapper;
import com.chd.hrp.sup.service.HrpSupSelectService;
import com.chd.hrp.sys.serviceImpl.EmpServiceImpl;

@Service("hrpSupSelectService")
public class HrpSupSelectServiceImpl implements HrpSupSelectService {
	private static Logger logger = Logger.getLogger(EmpServiceImpl.class);

	@Resource(name = "hrpSupSelectMapper")
	private final HrpSupSelectMapper hrpSupSelectMapper = null;

	// 后期通过配置文件读取
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String queryDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.queryDeptDict(entityMap, rowBounds));
	}
	@Override
	public String queryDeptDictLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.queryDeptDictLast(entityMap, rowBounds));
	}
	@Override
	public String querySupDict(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.querySupDict(entityMap, rowBounds));
	}
	@Override
	public String querySupStockType(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.querySupStockType(entityMap, rowBounds));
	}
	@Override
	public String querySupPayTerm(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.querySupPayTerm(entityMap, rowBounds));
	}
	@Override
	public String querySupPurDept(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.querySupPurDept(entityMap, rowBounds));
	}
	@Override
	public String querySupStockEmp(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.querySupStockEmp(entityMap, rowBounds));
	}
	@Override
	public String querySupDeptDict(Map<String, Object> entityMap)throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.querySupDeptDict(entityMap, rowBounds));
	}
	@Override
    public String queryHosInfo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSupSelectMapper.queryHosInfo(entityMap, rowBounds));
    }

}

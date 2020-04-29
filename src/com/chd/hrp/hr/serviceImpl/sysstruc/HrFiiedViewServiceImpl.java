package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedViewMapper;
import com.chd.hrp.hr.service.sysstruc.HrFiiedViewService;

@Service("hrFiiedViewService")
public class HrFiiedViewServiceImpl implements HrFiiedViewService {

	private static Logger logger = Logger.getLogger(HrFiiedViewServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "hrFiiedViewMapper")
	private final HrFiiedViewMapper hrFiiedViewMapper = null;

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
		return hrFiiedViewMapper.queryByCode(entityMap);
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
	public String saveHrFiiedView(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrFiiedViewMapper.delete(entityMap);
			String cite_sql=replaceConstant(entityMap.get("cite_sql").toString(),entityMap);
			try {
				List<Map<String, Object>> columnsList = hrFiiedViewMapper.queryCount(cite_sql);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new SysException("外部引用sql语句错误");
			}
			String query_sql=replaceConstant(entityMap.get("query_sql").toString(),entityMap);
			try {
				List<Map<String, Object>> columnsList = hrFiiedViewMapper.queryCount(cite_sql);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new SysException("查询sql语句错误");
			}
			//String content = entityMap.get("cite_sql").toString();
			//entityMap.put("cite_sql", content.getBytes());
			hrFiiedViewMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	private String replaceConstant(String replaceStr,Map<String, Object> entityMap){
		try {
		if(replaceStr.indexOf("@group_id")!=-1) {
			replaceStr = replaceStr.replaceAll("@group_id", entityMap.get("group_id").toString());
		}
		
		if(replaceStr.indexOf("@hos_id")!=-1) {
		   replaceStr = replaceStr.replaceAll("@hos_id", entityMap.get("hos_id").toString());
		}
		
		if(replaceStr.indexOf("@copy_code")!=-1) {
		replaceStr = replaceStr.replaceAll("@copy_code", entityMap.get("copy_code").toString());
		}
		
	/*	replaceStr = replaceStr.replaceAll("@store_type_code", entityMap.get("store_type_code").toString());*/
		if(replaceStr.indexOf("@user_id")!=-1) {
		replaceStr = replaceStr.replaceAll("@user_id", entityMap.get("user_id").toString());
		}
		
		return replaceStr;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
}

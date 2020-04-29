package com.chd.hrp.eqc.serviceImpl.xymanage;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccElementAnalyzeMapper;
import com.chd.hrp.acc.dao.accDictType.AccDictTypeMapper;
import com.chd.hrp.acc.service.AccElementAnalyzeService;
import com.chd.hrp.acc.service.accDictType.AccDictTypeService;
import com.chd.hrp.eqc.dao.xymanage.EqcEQdeviceMapper;
import com.chd.hrp.eqc.service.xymanage.EqcEQdeviceService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("EqcEQdeviceService")
public class EqcEQdeviceServiceImpl implements EqcEQdeviceService {

	private static Logger logger = Logger.getLogger(EqcEQdeviceServiceImpl.class);
	@Resource(name = "eqcEQdeviceMapper")
	private final EqcEQdeviceMapper eqcEQdeviceMapper = null;
	

	@Override
	public String queryEQdevice(Map<String, Object> mapVo) {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
	 
			List<Map<String, Object>> list = eqcEQdeviceMapper.queryEQdevice(mapVo);
			
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		
	}

	@Override
	public String addEQdevice(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());  
		try {
			int addCount = eqcEQdeviceMapper.updateEQdevice(mapVo);
			if (addCount == 0) {
				throw new SysException("保存失败,请刷新尝试!");
			}
			return "{\"msg\":\"修改成功\",\"status\":true}";
//			return "{\"error\":\"该字典编码已存在\",\"status\":false}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}

	@Override
	public String deleteEQdevice(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());  
		try {
			int addCount = eqcEQdeviceMapper.deleteEQdevice(mapVo);
			if (addCount == 0) {
				throw new SysException("保存失败,请刷新尝试!");
			}
			return "{\"msg\":\"修改成功\",\"status\":true}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}

	@Override
	public String updateEQdevice(Map<String, Object> mapVo) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());  
		try {
			int addCount = eqcEQdeviceMapper.updateEQdevice(mapVo);
			if (addCount == 0) {
				throw new SysException("保存失败,请刷新尝试!");
			}
			return "{\"msg\":\"修改成功\",\"status\":true}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}

	
	
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





}

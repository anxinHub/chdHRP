package com.chd.hrp.hr.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.hr.dao.HosCommonMapper;
import com.chd.hrp.hr.service.HosCommonService;

/**
 * 
 * @ClassName: HrCommonService
 * @Description: hr动态表格、表单增删改查
 * @author zn
 * @date 2017年11月5日 上午10:53:57
 * 
 *
 */

@Service("hosCommonService")
public class HosCommonServiceImpl implements HosCommonService {
	
	@Resource(name = "hosCommonMapper")
	private final HosCommonMapper hosCommonMapper = null;
	@Override
	public List<Map<String, Object>> queryHosUserPermByUserId(
			Map<String, Object> entityMap) throws DataAccessException {
		return hosCommonMapper.queryHosUserPermByUserId(entityMap);
		}
	
	
	
}

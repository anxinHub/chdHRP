package com.chd.hrp.hr.serviceImpl.salarymanagement.accumulationfund;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.hrp.hr.dao.salarymanagement.accumulationfund.HrProvidentfundratioMapper;
import com.chd.hrp.hr.service.salarymanagement.accumulationfund.HrProvidentfundratioService;

@Service("hrProvidentfundratioService")
public class HrProvidentfundratioServiceImpl implements HrProvidentfundratioService{
	
	private static Logger logger = Logger.getLogger(HrProvidentfundratioServiceImpl.class);
	
	// 引入Service
	@Resource(name = "hrProvidentfundratioMapper")
	private final HrProvidentfundratioMapper hrProvidentfundratioMapper = null;

	@Override
	public String saveProvidentfundratio(Map<String, Object> mapVo) {
		
		try {
			
			//删除数据
			hrProvidentfundratioMapper.deleteProvidentfundratio(mapVo);
			
			//添加数据
			int addCount = hrProvidentfundratioMapper.saveProvidentfundratio(mapVo);
			if(addCount == 0){
				new SysException();
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public Map<String, Object> queryProvidentfundratio(Map<String, Object> mapVo) {
		return hrProvidentfundratioMapper.queryProvidentfundratio(mapVo);
	}

}

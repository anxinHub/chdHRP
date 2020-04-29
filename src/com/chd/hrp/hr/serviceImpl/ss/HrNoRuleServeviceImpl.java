package com.chd.hrp.hr.serviceImpl.ss;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.ss.HrNoRuleMapper;
import com.chd.hrp.hr.service.ss.HrNoRuleService;

/**
 * @Title.@Description.
 * @author LFH
 *
 */

//@Service("Controller 层创建，调用的对象")

@Service("hrNoRuleService")  
public class HrNoRuleServeviceImpl implements HrNoRuleService {
	
	private static Logger logger = Logger.getLogger(HrNoRuleServeviceImpl.class);   //返回错误信息

	@Resource(name="hrNoRuleMapper")   // 注解初始化一个 dao 层 对象  进而调用dao
	private final HrNoRuleMapper hrNoRuleMapper = null;  
	
	//实现 service 中定义的接口
	@Override
	public String queryHrNoRule(Map<String, Object> entityMap) throws DataAccessException {
		
		List <Map <String, Object>> list = hrNoRuleMapper.queryRule(entityMap); // 调用 dao 层方法
		
		return ChdJson.toJson(list);
	}

/*	//更新
	@Override
	public String updataHrNoRule(Map<String, Object> mapVo) throws DataAccessException {
		
		return null;
	}*/
	
	//保存
	@Override
	public String saveHrNoRule(List<Map<String, Object>> listVo) throws DataAccessException{
		try{
			hrNoRuleMapper.saveHrNoRule(listVo);
			
			return "{\"msg\":\"修改成功。\",\"state\":\"true\"}"; //  {"msg":"修改成功","state":"true"}   json 格式
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
			return "{\"error\":\"修改失败，数据库异常，请联系管理员! 错误编码  saveHrNoRule \"}";
		}
	}

	// 删除
	@Override
	public String deleteHrNoRule(List<Map<String, Object>> listVo) throws DataAccessException {
		try{
			hrNoRuleMapper.deleHrNoRule(listVo);
			return "{\"msg\":\"修改成功。\",\"state\":\"true\"}";
		}catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			return "{\"error\":\"删除失败，数据库异常，请联系管理员! 错误编码  deleteHrNoRule \"}";
		}
	}

	// 新增
	@Override
	public String addHrNoRule(Map<String, Object> mapVo)throws DataAccessException {
		try{
			hrNoRuleMapper.addHrNoRule(mapVo);
			return "{\"msg\":\"新增成功。\",\"state\":\"true\"}";
		}catch (Exception ex){
			logger.error(ex.getMessage(),ex);
			return "{\"error\":\"新增失败，数据库异常，请联系管理员! 错误编码  addHrNoRule \"}";
		}
	}
	
	
}

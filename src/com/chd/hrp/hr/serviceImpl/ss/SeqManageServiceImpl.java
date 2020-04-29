package com.chd.hrp.hr.serviceImpl.ss;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.ss.SeqManageMapper;
import com.chd.hrp.hr.entity.base.HrTitleLevel;
import com.chd.hrp.hr.service.ss.SeqManageService;


@Service("seqManageService")  
public class SeqManageServiceImpl implements SeqManageService{
	
	private static Logger logger = Logger.getLogger(SeqManageServiceImpl.class);
	@Resource(name="seqManageMapper")   // 注解初始化一个 dao 层 对象  进而调用dao
	private final SeqManageMapper seqManageMapper = null;  
	
	//实现 service 中定义的接口
	@Override
	public String querySeqManage(Map<String, Object> mapVo) throws DataAccessException {
		
		// 调用 dao 层方法
		
		List<Map<String, Object>> list = seqManageMapper.querySeqManage(mapVo);

		return ChdJson.toJson(list);
	}

    public String addSeqManage(Map<String, Object> mapVo) throws DataAccessException {
		
    	try{
    		// 调用 dao 层方法
    		seqManageMapper.addSeqManage(mapVo);
			return "{\"msg\":\"新增成功。\",\"state\":\"true\"}";
		}catch (Exception ex){
			logger.error(ex.getMessage(),ex);
			return "{\"error\":\"新增失败，数据库异常，请联系管理员! 错误编码  addHrNoRule \"}";
		}

	}

    public String deleteSeqManage(List<Map<String, Object>> listVo) throws DataAccessException {
	
    	try{
    		seqManageMapper.deleteSeqManage(listVo);
			return "{\"msg\":\"删除成功。\",\"state\":\"true\"}";
		}catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			return "{\"error\":\"删除失败，数据库异常，请联系管理员! 错误编码  deleteSeqManage \"}";
		}
		
	}
    public String dropSeqManage(List<Map<String, Object>> listVo) throws DataAccessException {		
    	try{
    		seqManageMapper.dropSeqManage(listVo);
			return "{\"msg\":\"删除成功。\",\"state\":\"true\"}";
		}catch(Exception ex){
			logger.error(ex.getMessage(),ex);
			return "{\"error\":\"删除失败，数据库异常，请联系管理员! 错误编码  deleteSeqManage \"}";
		}
		
	}
    /**
	 * @Description 生成序列<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String createSeqManage(Map<String, Object> entityMap) throws DataAccessException {

		try{
    		// 调用 dao 层方法
    		seqManageMapper.createSeqManage(entityMap);
			return "{\"msg\":\"新增成功。\",\"state\":\"true\"}";
		}catch (Exception ex){
			logger.error(ex.getMessage(),ex);
			return "{\"error\":\"生成失败，数据库异常，请联系管理员! 错误编码  createSeqManage \"}";
		}

	}

}

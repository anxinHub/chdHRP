/**
 * 
 */
package com.chd.hrp.acc.serviceImpl.paper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.paper.AccPaperExchangeMapper;
import com.chd.hrp.acc.service.paper.AccPaperExchangeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

/**
 * @author
 *
 */
@Service("accPaperExchangeService")
public class AccPaperExchangeServiceImpl implements AccPaperExchangeService {

	private static Logger logger = Logger.getLogger(AccPaperExchangeServiceImpl.class);

 	@Resource(name = "accPaperExchangeMapper")
	private final AccPaperExchangeMapper accPaperExchangeMapper = null;

 	@Resource(name = "sysFunUtilMapper")
 	private final SysFunUtilMapper sysFunUtilMapper = null;
 	
	@Override
	public String queryAccExchange(Map<String, Object> entityMap) 
			throws DataAccessException {
		
		return ChdJson.toJson(accPaperExchangeMapper.queryAccExchange(entityMap));
	}

	@Override
	public String addPaperExchange(Map<String, Object> mapVo) {
		
		try {
			//查询汇率编码是否唯一
			String byCode = accPaperExchangeMapper.queryAccExchangeByCode(mapVo);
			
			if (byCode != null) {
				return "{\"msg\":\"汇率编码重复！.\",\"state\":\"false\"}";
			}
			
			//新增汇率
			int addPaperExchangeCount = accPaperExchangeMapper.addPaperExchange(mapVo);
			
			if (addPaperExchangeCount == 0) {
				new Exception();
			}
			
			return "{\"msg\":\"添加成功！.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
	}

	@Override
	public String updatePaperExchange(Map<String, Object> mapVo) {
		
		try {
			
			int updatePaperExchangeCount = accPaperExchangeMapper.updatePaperExchange(mapVo);
			
			if (updatePaperExchangeCount == 0) {
				new Exception();
			}
			
			return "{\"msg\":\"修改成功！.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
	}

	@Override
	public String deletePaperExchange(Map<String, Object> mapVo) {
		
		try {
		      //判断是否被使用
		      String reStr="";
		      Map<String, Object> map = new HashMap<String, Object>();
		      //需要验证的表
		      map.put("dict_code", "acc_rate".toUpperCase());
		      map.put("group_id", mapVo.get("group_id"));
		      map.put("hos_id", mapVo.get("hos_id"));
		      map.put("copy_code", mapVo.get("copy_code"));
		      //需要验证的数据主键id，多id用逗号分隔
		      map.put("dict_id_str", mapVo.get("arrid"));
		      map.put("acc_year", "");
		      map.put("p_flag", "1");
		      sysFunUtilMapper.querySysDictDelCheck(map);
		      
		      if(map.get("reNote")!=null) {
		       reStr+=map.get("reNote");
		      }
		      
		      if(reStr!=null && !reStr.equals("")){
		       return "{\"error\":\"删除失败，选择的汇率被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
		      }
		      
		      String [] strid = mapVo.get("arrid").toString().replaceAll("'", "").split(",");
		     
		      int deleteCount = accPaperExchangeMapper.deletePaperExchange(strid,mapVo);
		      
		      if(deleteCount == 0){
		    	  throw new SysException();
		      }
	    	  return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}
	
	
	
}

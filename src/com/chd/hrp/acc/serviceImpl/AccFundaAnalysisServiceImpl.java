package com.chd.hrp.acc.serviceImpl;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccFundaAnalysisMapper;
import com.chd.hrp.acc.service.AccFundaAnalysisService;
import com.chd.hrp.acc.service.verification.AccBudgLederService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("accFundaAnalysisService")
public class AccFundaAnalysisServiceImpl implements AccFundaAnalysisService{

	private static Logger logger = Logger.getLogger(AccFundaAnalysisServiceImpl.class);
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "accFundaAnalysisMapper")
	private final AccFundaAnalysisMapper accFundaAnalysisMapper = null;

	@Override
	public String addAccFundaAnalysis(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
				Map<String,Object> accFundaMap = accFundaAnalysisMapper.queryAccFundaByCode(entityMap);
				
				if(accFundaMap != null){
					
					return "{\"error\":\"编码：" + accFundaMap.get("bas_code").toString() + "已存在.\"}";
				}
				
				
				/*
				 *自动生成排序号 
				 * */
				Map<String, Object> utilMap = new HashMap<String, Object>();
				
				utilMap.put("group_id", entityMap.get("group_id"));
				
				utilMap.put("hos_id", entityMap.get("hos_id"));
				
				utilMap.put("copy_code",entityMap.get("copy_code"));
				
				utilMap.put("field_table","acc_aly_bas");
				
				utilMap.put("field_sort", "sort_code");
				
				accFundaAnalysisMapper.addAccFunda(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccFundaAnalysis\"}";
			}
	}
	
	@Override
	public String queryAccFundaAnalysis(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accFundaAnalysisMapper.queryAccFundaAnalysis(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accFundaAnalysisMapper.queryAccFundaAnalysis(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBatchAccFunda(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			accFundaAnalysisMapper.deleteBatchAccFunda(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccFunda\"}";
		}

	}
	
	@Override
	public List<Map<String, Object>> queryAccFundaPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
			
			List<Map<String, Object>> list = accFundaAnalysisMapper.queryAccFundaAnalysis(entityMap);
			
			//将取出的“状态”值进行修改
			for (int i=0; i<list.size(); i++){
				Map<String, Object> mapList = list.get(i);
				if (mapList.containsKey("is_stop")){
					String value = mapList.get("is_stop").toString();
					if (value.equals("0") == true){
						mapList.put("is_stop", "启用");
					}else{
						mapList.put("is_stop", "停用");
					}
					
				}
			}
			
			return list;
		
	}
	
	@Override
	public String updateAccFundaAnalysis(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			accFundaAnalysisMapper.updateAccFundaAnalysis(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTarget\"}";
		}

	}
	
	@Override
	public Map<String, Object> queryAccFundaByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return accFundaAnalysisMapper.queryAccFundaByCode(entityMap);
	}
	
}

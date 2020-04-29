/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.io.IOException;
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
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.cost.dao.CostIncomeHisViewLogMapper;
import com.chd.hrp.cost.service.CostIncomeHisViewLogService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 门诊工作量表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costIncomeHisViewLogService")
public class CostIncomeHisViewLogServiceImpl implements CostIncomeHisViewLogService {

	private static Logger logger = Logger.getLogger(CostIncomeHisViewLogServiceImpl.class);

	@Resource(name = "costIncomeHisViewLogMapper")
	private final CostIncomeHisViewLogMapper costIncomeHisViewLogMapper = null;

	@Override
	public String queryCostHisViewLog(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) mapVo.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = costIncomeHisViewLogMapper.queryCostHisViewLog(mapVo);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = costIncomeHisViewLogMapper.queryCostHisViewLog(mapVo, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public String queryCostIncomeHisViewSetting(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) mapVo.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = costIncomeHisViewLogMapper.queryCostIncomeHisViewSetting(mapVo);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = costIncomeHisViewLogMapper.queryCostIncomeHisViewSetting(mapVo, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public String updateOrAddCostIncomeHisViewSetting(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			if(mapVo.get("detailData") != null && !"".equals(mapVo.get("detailData"))){
				
				JSONArray json = JSONArray.parseArray((String)mapVo.get("detailData"));
				
				Iterator it = json.iterator();
				
				while (it.hasNext()) {
					
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					Map<String,Object> map = new HashMap<String,Object>();
					
					map.put("group_id", mapVo.get("group_id"));
					map.put("hos_id", mapVo.get("hos_id"));
					map.put("copy_code", mapVo.get("copy_code"));
					map.put("his_log_code", jsonObj.get("his_log_code"));
					map.put("his_log_name", jsonObj.get("his_log_name"));
					map.put("sort_code", jsonObj.get("sort_code"));

					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("is_stop")))){
						map.put("is_stop", jsonObj.get("is_stop"));
					}else{
						map.put("is_stop", "0");
					}
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("g_day")))){
						map.put("g_day", jsonObj.get("g_day"));
					}else{
						map.put("g_day", "1");
					}
					
					int flag = costIncomeHisViewLogMapper.queryCostHisViewLogById(map);
					
					if(flag > 0){
						
						costIncomeHisViewLogMapper.updateCostHisViewLog(map);
						
					}else {
						
						costIncomeHisViewLogMapper.addCostHisViewLog(map);
					}
					
					  costIncomeHisViewLogMapper.deleteBatchDetailCostHisViewLogMain(map);
				}
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}

	@Override
	public Map<String, Object> queryCostHisViewInitByCode(
			Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return costIncomeHisViewLogMapper.queryCostHisViewInitByCode(mapVo);
	}

	@Override
	public String getDateCostIncomeHisView(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			List<Map<String,Object>> List = new ArrayList<Map<String,Object>>();
			
			String begin_date = mapVo.get("begin_date").toString();
			
			String end_date = mapVo.get("end_date").toString();
			
			List<String> dataList= DateUtil.getMonthList(begin_date,end_date);
			
			for (String date:dataList) {
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				String acc_year = date.substring(0, 4).toString();
				
				String acc_month = date.substring(4, 6).toString();
				
				map.put("group_id", mapVo.get("group_id"));
				
            	map.put("hos_id", mapVo.get("hos_id"));
            	
            	map.put("copy_code", mapVo.get("copy_code"));
            	
            	map.put("his_log_code", mapVo.get("his_log_code"));
            	
            	map.put("acc_year", acc_year);
            	
            	map.put("acc_month", acc_month);
            	
            	int detailFlag = costIncomeHisViewLogMapper.queryCostHisViewdetailByCode(map);
            	
            	if(detailFlag == 0){
            		
            	     List.add(map);
            	
            	}
			}

			if(List.size() > 0){
				
				costIncomeHisViewLogMapper.addBatchDetailCostHisViewLog(List);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
	         throw new SysException("{\"error\":\"操作失败\"}");
		}
	}

	@Override
	public String deleteBatchDetailCostHisViewLog(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			costIncomeHisViewLogMapper.deleteBatchDetailCostHisViewLog(list);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
	         throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public Map<String, Object> costRunJob(String etlPath, String jobPath,
			String viewCode) throws Exception {
		// TODO Auto-generated method stub
		
        Map<String,Object> resMap = new HashMap<String,Object>();

		try {
			
			
            ConfigInit.setConfigProperties("etlPath",etlPath);
            ConfigInit.setConfigProperties("jobPath",jobPath);
            
            //F:\CHD\DRP\HIP\V6.0\ETL
            //F:\CHD\DRP\HIP\V6.0\wenzhou\his3.0\jobs.kjb
        } catch (IOException ioe) {  
            throw new SysException(ioe.getMessage(),ioe);
        }
        
        resMap.put("state", "true");
        resMap.put("msg", "执行成功"); 
        return resMap;
	}

}

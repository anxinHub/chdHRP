package com.chd.hrp.acc.serviceImpl.autovouch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.AccAutoHisLogMainMapper;
import com.chd.hrp.acc.service.autovouch.AccAutoHisLogMainService;
import com.github.pagehelper.PageInfo;
@Service("accAutoHisLogMainService")
public class AccAutoHisLogMainServiceImpl implements AccAutoHisLogMainService {
	private static Logger logger = Logger.getLogger(AccAutoHisLogMainServiceImpl.class);

	@Resource(name = "accAutoHisLogMainMapper")
	private final AccAutoHisLogMainMapper accAutoHisLogMainMapper = null;
	
	@Override
	public String queryAccHisViewLog(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accAutoHisLogMainMapper.queryAccHisViewLog(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accAutoHisLogMainMapper.queryAccHisViewLog(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			accAutoHisLogMainMapper.deleteBatch(entityMap);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		
	}

	@Override
	public String queryAutoHisViewSetting(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accAutoHisLogMainMapper.queryAutoHisViewSetting(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accAutoHisLogMainMapper.queryAutoHisViewSetting(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String update(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
		
		if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
			
			JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
			List<Map<String,Object>> iList = new ArrayList<Map<String,Object>>();
			
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
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
				
				map.put("his_log_date", entityMap.get("his_log_date"));
				iList.add(map);
				
				int flag=accAutoHisLogMainMapper.queryById(map);
				if(flag>0){
					accAutoHisLogMainMapper.update(map);
				}else{
					accAutoHisLogMainMapper.add(map);
				}
				
			}
			
			accAutoHisLogMainMapper.delete(entityMap);
			
			
			/*for(Map<String, Object> dMap : iList){
				dMap.put("group_id", dMap.get("group_id"));
				dMap.put("hos_id", dMap.get("hos_id"));
				dMap.put("copy_code", dMap.get("copy_code"));
				dMap.put("his_log_code", dMap.get("his_log_code"));
				dMap.put("his_log_date", dMap.get("his_log_date"));
				
				int detailFlag=accAutoHisLogMainMapper.queryDetailById(dMap);
				if(detailFlag==0){
					accAutoHisLogMainMapper.add(dMap);
				}
			}*/
			
			
		}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
        
	}

	@Override
	public String updateDate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			List<Map<String,Object>> dList = new ArrayList<Map<String,Object>>();
			
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(entityMap.get("end_date").toString());
            Date date2 = sdf.parse(entityMap.get("begin_date").toString());
            //将转换的两个时间对象转换成Calendard对象
            Calendar can1 = Calendar.getInstance();
            can1.setTime(date1);
            can1.add(Calendar.DAY_OF_MONTH, 1);
            Calendar can2 = Calendar.getInstance();
            can2.setTime(date2);
    
            
            while(can2.getTime().before(can1.getTime())){//判断是否到结束日期
            	Map<String,Object> map = new HashMap<String,Object>();
            	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            	String str = sdf.format(can2.getTime());

            	map.put("group_id", entityMap.get("group_id"));
            	map.put("hos_id", entityMap.get("hos_id"));
            	map.put("copy_code", entityMap.get("copy_code"));
            	map.put("his_log_code", entityMap.get("his_log_code"));
            	map.put("his_log_date", str);
            	
            	can2.add(Calendar.DAY_OF_MONTH, 1);
            	
            	int detailFlag=accAutoHisLogMainMapper.queryDetailById(map);
				if(detailFlag==0){
					dList.add(map);
				}
				
            }
           
            if(dList.size()>0){
            	accAutoHisLogMainMapper.addBatchDetail(dList);
            }
            
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SysException("{\"error\":\"操作失败\"}");
        }
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return accAutoHisLogMainMapper.queryByCode(entityMap);
	}
	
	
	//执行ETL的bat文件
	@Override
	public synchronized Map<String,Object> runJob(String etlPath,String jobPath,String viewCode) throws Exception{  
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		File file =new File(etlPath+"\\data-integration\\Kitchen.bat");
		//ETL文件不存在
		if(!file.exists()){
			resMap.put("error", "ETL目录不存在："+etlPath+"\\Kitchen.bat");
			return resMap;
		}
		
		String jobDir=jobPath.substring(0,jobPath.lastIndexOf("\\"));
		file =new File(jobPath);
		//ETL文件不存在
		if(!file.exists()){
			resMap.put("error", "JOB文件不存在："+jobPath);
			return resMap;
		}
		String pf=etlPath.substring(0,1);
		StringBuffer cmdStr=new StringBuffer();
		cmdStr.append(pf+": \n");
		cmdStr.append("cd "+etlPath+"\\data-integration \n");
		cmdStr.append("Kitchen.bat /norep /file="+jobPath+" -param:view_code="+viewCode+" /level:Basic>>"+jobDir+"\\log\\ETL_%date:~0,4%_%date:~5,2%_%date:~8,2%_%time:~0,2%%time:~3,2%%time:~6,2%.log");
		//level: The logging level (Basic, Detailed, Debug, Rowlevel, Error, Nothing)
        //param
		
		try {
			
			file = File.createTempFile("tmp", ".bat",new File( LoadSystemInfo.getProjectUrl() ));
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            fw.write(cmdStr.toString());
            fw.close();
            
            Process p = Runtime.getRuntime().exec(file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                //System.out.println(line);
            }
            input.close();
            file.delete();
			
            ConfigInit.setConfigProperties("etlPath",etlPath);
            ConfigInit.setConfigProperties("jobPath",jobPath);
        } catch (IOException ioe) {  
            throw new SysException(ioe.getMessage(),ioe);
        }
        
        resMap.put("state", "true");
        resMap.put("msg", "执行成功");
        return resMap;
    }

	@Override
	public List<Map<String, Object>> queryAccHisViewLogPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accAutoHisLogMainMapper.queryAccHisViewLog(entityMap);
		
		return list;
		
	}

	
}

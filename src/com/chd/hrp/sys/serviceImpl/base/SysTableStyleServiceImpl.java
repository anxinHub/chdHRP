/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl.base;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.base.SysTableStyleMapper;
import com.chd.hrp.sys.entity.SysTableStyle;
import com.chd.hrp.sys.service.base.SysTableStyleService;




@Service("sysTableStyleService")
public class SysTableStyleServiceImpl implements SysTableStyleService {

	private static Logger logger = Logger.getLogger(SysTableStyleServiceImpl.class);
	
	@Resource(name = "sysTableStyleMapper")
	private final SysTableStyleMapper sysTableStyleMapper = null;

	@Override
	public String querySysTableStyle(Map<String, Object> entityMap
			) throws DataAccessException {
	

		List<SysTableStyle> list = sysTableStyleMapper.querySysTableStyle(entityMap);


		return ChdJson.toJson(list);
		
	}

	@Override
	public SysTableStyle querySysTableStyleByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	//表头添加
	@Override
	public String addSysTableStyle(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
            
			sysTableStyleMapper.deleteSysTableStyle(entityMap);
			
			sysTableStyleMapper.addSysTableStyle(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	//表头删除
	@Override
	public String deleteSysTableStyle(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			int state = sysTableStyleMapper.deleteSysTableStyle(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	//表头批量添加
	@Override
	public String addBatchSysTableStyle(List<Map<String, Object>> entityList)
			throws DataAccessException {
		
		try {
			sysTableStyleMapper.deleteBatchSysTableStyle(entityList);
			
			sysTableStyleMapper.addBatchSysTableStyle(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {


			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
    //表头批量删除
	@Override
	public String deleteBatchSysTableStyle(List<Map<String, Object>> entityList)
			throws DataAccessException {
		try {

			int state = sysTableStyleMapper.deleteBatchSysTableStyle(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	//保存列表打印格式
	@Override
	public String saveStyleByUrl(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			map.put("table", "SYS_"+map.get("type")+"_"+map.get("mod_code"));
			//删除列表打印格式
			sysTableStyleMapper.deleteStyleByUrl(map);
			//添加列表打印格式
			sysTableStyleMapper.insertStyleByUrl(map);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//查询列表格式
	public String queryGridByUserID(Map<String, Object> map)throws DataAccessException {
		try {
			
			StringBuilder sb=new StringBuilder();
			sb.append("{");
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			
			//查询grid格式
			map.put("table", "SYS_GRID_"+map.get("mod_code"));			
			list = sysTableStyleMapper.queryGridByUserID(map);
			if(list!=null && list.size()>0){
				for(Map<String, Object> m:list){
					sb.append("'"+m.get("page_url")+"':'"+clobToString((Clob)m.get("page_json"))+"',");
				}
				sb.setCharAt(sb.length()-1, ' ');
			}
			
			sb.append("}");
			return sb.toString();

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	//查询用户打印格式
	public String queryPrintByPageUrl(Map<String, Object> map)throws DataAccessException {
		try {
			
			//查询print格式
			map.put("table", "SYS_PRINT_"+map.get("mod_code"));			
			String pageJson= sysTableStyleMapper.queryPrintByPageUrl(map);
			return pageJson==null?"":pageJson;
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	//clob转string
	private String clobToString(Clob clob) throws SQLException, IOException {
	    String reString = "";
	    Reader is = clob.getCharacterStream();// 得到流
	    BufferedReader br = new BufferedReader(is);
	    String s = br.readLine();
	    StringBuffer sb = new StringBuffer();
	    while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
	        sb.append(s);
	        s = br.readLine();
	    }
	    reString = sb.toString();
	    return reString;
	}
	
	//删除列表格式
	@Override
	public String deleteStyleByUrl(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			//删除列表查询格式
			map.put("table", "SYS_GRID_"+map.get("mod_code"));
			sysTableStyleMapper.deleteStyleByUrl(map);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
}

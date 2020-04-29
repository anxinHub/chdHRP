/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.HosDictMapper;

import com.chd.hrp.sys.entity.HosDict;

import com.chd.hrp.sys.entity.SupType;
import com.chd.hrp.sys.service.HosDictService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("hosDictService")
public class HosDictServiceImpl implements HosDictService {

	private static Logger logger = Logger.getLogger(HosDictServiceImpl.class);
	
	@Resource(name = "hosDictMapper")
	private final HosDictMapper hosDictMapper = null;
	
	
	
	
	

	@Override
	public String queryHosDictBySelector(Map<String, Object> entityMap)
			throws DataAccessException {
StringBuilder jsonResult = new StringBuilder();

         //System.out.println("1111111");
		
		jsonResult.append("{Rows:[");
		
		List<HosDict> hosList = hosDictMapper.queryHosDict(entityMap);
		
		
		if (hosList.size()>0) {
			
			
			
			int row = 0;
			
			for (HosDict hosDict : hosList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + hosDict.getHos_id() + "',");
				
				jsonResult.append("group_id:'" + hosDict.getGroup_id() + "',");
				
				
				jsonResult.append("name:'"+hosDict.getHos_code()+ " "+ hosDict.getHos_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}
	/**
	 * 用于集团化管理   集团单位选择器
	 */
	@Override
	public String queryGroupHosDictBySelector(Map<String, Object> entityMap)throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<HosDict> hosList = hosDictMapper.queryGroupHosDict(entityMap);
		
		
		if (hosList.size()>0) {
			
			
			
			int row = 0;
			
			for (HosDict hosDict : hosList) {
				
				if (row == 0) {
					
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + hosDict.getHos_id() + "',");
				
				jsonResult.append("group_id:'" + hosDict.getGroup_id() + "',");
				
				
				jsonResult.append("name:'"+hosDict.getHos_code()+ " "+ hosDict.getHos_name() +"',");
				//jsonResult.append("check_type_code:'"+groupDict.getCheck_type_code()+ "'");
				jsonResult.append("}");
				
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
	}
}

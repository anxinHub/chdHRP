/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.matpayquery;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.matpayquery.MatMainMapper;
import com.chd.hrp.mat.service.matpayquery.MatMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 常备材料入库
 * @Table:
 * MAT_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matMainService")
public class MatMainServiceImpl implements MatMainService {

	private static Logger logger = Logger.getLogger(MatMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matMainMapper")
	private final MatMainMapper matMainMapper = null;
	
	/**
	 * @Description 
	 * 查询结果集常备材料入库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		//转换日期格式
		if(entityMap.get("begin_in_date") != null && !"".equals(entityMap.get("begin_in_date"))){
			entityMap.put("begin_in_date", DateUtil.stringToDate(entityMap.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_in_date") != null && !"".equals(entityMap.get("end_in_date"))){
			entityMap.put("end_in_date", DateUtil.stringToDate(entityMap.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("begin_confirm_date") != null && !"".equals(entityMap.get("begin_confirm_date"))){
			entityMap.put("begin_confirm_date", entityMap.get("begin_confirm_date").toString());
		}
		if(entityMap.get("end_confirm_date") != null && !"".equals(entityMap.get("end_confirm_date"))){
			entityMap.put("end_confirm_date", entityMap.get("end_confirm_date"));
		}
		
		String query_type = "0";
		if(entityMap.get("query_type") != null && !"".equals(entityMap.get("query_type"))){
			query_type = entityMap.get("query_type").toString();
		}
		
		if (sysPage.getTotal()==-1){
				
			List<Map<String, Object>> list = null;
			
			if("0".equals(query_type)){
				list = matMainMapper.query(entityMap);
			}else if("1".equals(query_type)){
				list = matMainMapper.queryDetails(entityMap);
			}else if("2".equals(query_type)){
				list = matMainMapper.querySum(entityMap);
			}
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = null;

			if("0".equals(query_type)){
				list = matMainMapper.query(entityMap, rowBounds);
			}else if("1".equals(query_type)){
				list = matMainMapper.queryDetails(entityMap, rowBounds);
			}else if("2".equals(query_type)){
				list = matMainMapper.querySum(entityMap, rowBounds);
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> printData(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		//转换日期格式
		if(entityMap.get("begin_in_date") != null && !"".equals(entityMap.get("begin_in_date"))){
			entityMap.put("begin_in_date", DateUtil.stringToDate(entityMap.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_in_date") != null && !"".equals(entityMap.get("end_in_date"))){
			entityMap.put("end_in_date", DateUtil.stringToDate(entityMap.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("begin_confirm_date") != null && !"".equals(entityMap.get("begin_confirm_date"))){
			entityMap.put("begin_confirm_date", entityMap.get("begin_confirm_date").toString());
		}
		if(entityMap.get("end_confirm_date") != null && !"".equals(entityMap.get("end_confirm_date"))){
			entityMap.put("end_confirm_date", entityMap.get("end_confirm_date"));
		}
		
		String query_type = "0";
		if(entityMap.get("query_type") != null && !"".equals(entityMap.get("query_type"))){
			query_type = entityMap.get("query_type").toString();
		}
		
		List<Map<String, Object>> list = null;
			
		if("0".equals(query_type)){
			list = matMainMapper.query(entityMap);
		}else if("1".equals(query_type)){
			list = matMainMapper.queryDetails(entityMap);
		}else if("2".equals(query_type)){
			list = matMainMapper.querySum(entityMap);
		}
			
		return JsonListMapUtil.toListMapLower(list);
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

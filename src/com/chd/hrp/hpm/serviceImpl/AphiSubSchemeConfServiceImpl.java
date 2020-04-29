
package com.chd.hrp.hpm.serviceImpl;

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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiSubSchemeConfMapper;
import com.chd.hrp.hpm.entity.AphiSubSchemeConf;
import com.chd.hrp.hpm.service.AphiSubSchemeConfService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("aphiSubSchemeConfService")
public class AphiSubSchemeConfServiceImpl implements AphiSubSchemeConfService {

	private static Logger logger = Logger.getLogger(AphiSubSchemeConfServiceImpl.class);
	
	@Resource(name = "aphiSubSchemeConfMapper")
	private final AphiSubSchemeConfMapper aphiSubSchemeConfMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addSubSchemeConf(Map<String,Object> entityMap)throws DataAccessException{
	    int state = aphiSubSchemeConfMapper.addSubSchemeConf(entityMap);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}	
	}
	
	/**
	 * 
	 */
	@Override
	public String querySubSchemeConf(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiSubSchemeConfMapper.querySubSchemeConf(entityMap, rowBounds), sysPage.getTotal());
	}
	
	/**
	 * 
	 */
	@Override
	public AphiSubSchemeConf querySubSchemeConfByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiSubSchemeConfMapper.querySubSchemeConfByCode(entityMap);
	}
	
	
	/**
	 * 
	 */
	
	public String deleteSubSchemeConf(List<Map<String,Object>> entityList)throws DataAccessException{
		String request="";
		for (Map<String,Object> entityMap : entityList) {
	        int state = aphiSubSchemeConfMapper.deleteSubSchemeConf(entityMap);
        	if (state > 0) {
			request="{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request="{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
        }
		
		return request;
	}
	@Override
    public String deleteSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException {
		String request="";
		int state = aphiSubSchemeConfMapper.deleteSubSchemeConf(entityMap);
		if (state > 0) {
			request= "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request= "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;
    }
	/**
	 * 
	 */
	@Override
	public String deleteSubSchemeConfById(String[] ids)throws DataAccessException{
		String request="";
		if (ids != null && ids.length>0) {
					for (String s : ids) {
						aphiSubSchemeConfMapper.deleteSubSchemeConfById(s);
					}
					request= "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				} else {
					request= "{\"error\":\"没有要删除的数据.\",\"state\":\"false\"}";
				}
		return request;
		
	}
	/**
	 * 
	 */
	@Override
	public String updateSubSchemeConf(Map<String,Object> entityMap)throws DataAccessException{
		int state = aphiSubSchemeConfMapper.updateSubSchemeConf(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
    public String createSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException {
		String rbtnl = (String)entityMap.get("rbtnl");
		try{
			String year_month = (String)entityMap.get("year_month");
			
			String sub_scheme_seq_no = (String)entityMap.get("sub_scheme_seq_no");

			//生成下一月
			String next_year_month=DateUtil.getNextYear_Month(year_month);
			
			entityMap.put("sub_scheme_seq_no", sub_scheme_seq_no);
			
			entityMap.put("acct_year", next_year_month.substring(0, 4));
			
			entityMap.put("acct_month", next_year_month.substring(4, 6));
			
			aphiSubSchemeConfMapper.addSubSchemeConf(entityMap);
		
			if("yes".equals(rbtnl)){

				//生成当前月
				
				entityMap.put("scheme_seq_no", sub_scheme_seq_no);
				
				entityMap.put("acct_year", year_month.substring(0, 4));
				
				entityMap.put("acct_month", year_month.substring(4, 6));
				
				aphiSubSchemeConfMapper.addSubSchemeConf(entityMap);
				
			}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  createSubSchemeConf\"}";
		}
    }

	@Override
	public String queryHpmSubSchemeConfForEmp(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		AphiSubSchemeConf assc = aphiSubSchemeConfMapper.querySubSchemeConfSeq(entityMap);
		
		if(assc == null){
			
			return "{\"error\":\"当前年月没有配置序号 \"}";
		}
		
		entityMap.put("sub_scheme_seq_no", assc.getSub_scheme_seq_no());
		//entityMap.put("sub_scheme_seq_no", 1);
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = aphiSubSchemeConfMapper.queryHpmSubSchemeConfForEmp(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = aphiSubSchemeConfMapper.queryHpmSubSchemeConfForEmp(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryHpmSubSchemeConfForEmpPrint(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		if(MyConfig.getSysPara("09001") == null){
			entityMap.put("para_value", 0);
		}else{
			entityMap.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		if (entityMap.get("acct_year_month") != null && !"".equals(entityMap.get("acct_year_month"))) {
			entityMap.put("acct_year", entityMap.get("acct_year_month").toString().substring(0, 4));
			entityMap.put("acct_month", entityMap.get("acct_year_month").toString().substring(4, entityMap.get("acct_year_month").toString().length()));
		}
		
		AphiSubSchemeConf assc = aphiSubSchemeConfMapper.querySubSchemeConfSeq(entityMap);
		
		entityMap.put("sub_scheme_seq_no", assc.getSub_scheme_seq_no());
		//entityMap.put("sub_scheme_seq_no", 1);
		
		List<Map<String, Object>> list = aphiSubSchemeConfMapper.queryHpmSubSchemeConfForEmp(entityMap);
			
		return list;
	}
}

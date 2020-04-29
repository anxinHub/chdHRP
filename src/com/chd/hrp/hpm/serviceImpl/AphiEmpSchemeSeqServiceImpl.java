
package com.chd.hrp.hpm.serviceImpl;

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
import com.chd.base.exception.SysException;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiEmpSchemeMapper;
import com.chd.hrp.hpm.dao.AphiEmpSchemeSeqMapper;
import com.chd.hrp.hpm.dao.AphiSubSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiSubSchemeSeqMapper;
import com.chd.hrp.hpm.entity.AphiEmpScheme;
import com.chd.hrp.hpm.entity.AphiEmpSchemeSeq;
import com.chd.hrp.hpm.service.AphiEmpSchemeSeqService;

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


@Service("aphiEmpSchemeSeqService")
public class AphiEmpSchemeSeqServiceImpl implements AphiEmpSchemeSeqService {

	private static Logger logger = Logger.getLogger(AphiEmpSchemeSeqServiceImpl.class);
	
	@Resource(name = "aphiEmpSchemeSeqMapper")
	private final AphiEmpSchemeSeqMapper aphiEmpSchemeSeqMapper = null;
	
	@Resource(name = "aphiEmpSchemeMapper")
	private final AphiEmpSchemeMapper aphiEmpSchemeMapper = null;
    
	@Resource(name = "aphiSubSchemeSeqMapper")
	private final AphiSubSchemeSeqMapper aphiSubSchemeSeqMapper = null;
    
	@Resource(name = "aphiSubSchemeConfMapper")
	private final AphiSubSchemeConfMapper aphiSubSchemeConfMapper = null;
	/**
	 * 
	 */
	@Override
	public String addEmpSchemeSeq(Map<String,Object> entityMap)throws DataAccessException{

		try{
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			//查询方案配置数据
			List<AphiEmpScheme> list = aphiEmpSchemeMapper.queryEmpScheme(entityMap);
			if (list.size() == 0) {
				return "{\"error\":\"没有要审核的数据.\",\"state\":\"false\"}";
			}
			
			//查询最大序号
			int maxId = aphiSubSchemeSeqMapper.queryMaxSubSchemeSeq(entityMap)+1;
			entityMap.put("sub_scheme_seq_no", maxId);
			
			//添加序号表数据
			aphiSubSchemeSeqMapper.addSubSchemeSeq(entityMap);
			
			for (int x = 0 ; x < list.size() ; x++) {	
				AphiEmpScheme empScheme = list.get(x);
				//复制aphi_emp_scheme到aphi_emp_scheme_seq
				Map<String,Object> aphiEmpSchemeSeqMap = new HashMap<String,Object>();
				
				aphiEmpSchemeSeqMap.put("group_id", entityMap.get("group_id"));
				aphiEmpSchemeSeqMap.put("hos_id", entityMap.get("hos_id"));
				aphiEmpSchemeSeqMap.put("copy_code", entityMap.get("copy_code"));
				aphiEmpSchemeSeqMap.put("dept_id", empScheme.getDept_id());
				aphiEmpSchemeSeqMap.put("dept_no", empScheme.getDept_no());
				aphiEmpSchemeSeqMap.put("duty_code",empScheme.getDuty_code());
				aphiEmpSchemeSeqMap.put("sub_scheme_seq_no", maxId);
				aphiEmpSchemeSeqMap.put("method_code", empScheme.getMethod_code());
				aphiEmpSchemeSeqMap.put("formula_code", empScheme.getFormula_code());
				aphiEmpSchemeSeqMap.put("fun_code", empScheme.getFun_code() == null ? "" : empScheme.getFun_code());
				aphiEmpSchemeSeqMap.put("item_code", empScheme.getItem_code());
				
				aphiEmpSchemeSeqMapper.addEmpSchemeSeq(aphiEmpSchemeSeqMap);
				
			}
			
			//根据是否选择年月,判断是否操作aphi_sub_scheme_conf表         先删除,后添加
			if(entityMap.get("year_month") != null && !"".equals(entityMap.get("year_month"))){
				entityMap.put("acct_year", entityMap.get("year_month").toString().substring(0, 4));
				entityMap.put("acct_month", entityMap.get("year_month").toString().substring(4, 6));
				aphiSubSchemeConfMapper.deleteSubSchemeConf(entityMap);
				aphiSubSchemeConfMapper.addSubSchemeConf(entityMap);
			}
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

	    }catch(Exception e){
	    	
	    	logger.error(e.getMessage(), e);
	    	
	    	throw new SysException("{\"error\":\"审核失败 \"}");
	    }
	}
	
	/**
	 * 
	 */
	@Override
	public String queryEmpSchemeSeq(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiEmpSchemeSeqMapper.queryEmpSchemeSeq(entityMap, rowBounds), sysPage.getTotal());
	}
	
	/**
	 * 
	 */
	@Override
	public AphiEmpSchemeSeq queryEmpSchemeSeqByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiEmpSchemeSeqMapper.queryEmpSchemeSeqByCode(entityMap);
	}
	
	
	/**
	 * 
	 */
	
	public String deleteEmpSchemeSeq(List<Map<String,Object>> entityList)throws DataAccessException{
		String request="";
		for (Map<String,Object> entityMap : entityList) {
	        int state = aphiEmpSchemeSeqMapper.deleteEmpSchemeSeq(entityMap);
        	if (state > 0) {
			request="{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request="{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
        }
		
		return request;
	}
	@Override
    public String deleteEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException {
		String request="";
		int state = aphiEmpSchemeSeqMapper.deleteEmpSchemeSeq(entityMap);
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
	public String deleteEmpSchemeSeqById(String[] ids)throws DataAccessException{
		String request="";
		if (ids != null && ids.length>0) {
					for (String s : ids) {
						aphiEmpSchemeSeqMapper.deleteEmpSchemeSeqById(s);
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
	public String updateEmpSchemeSeq(Map<String,Object> entityMap)throws DataAccessException{
		int state = aphiEmpSchemeSeqMapper.updateEmpSchemeSeq(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}
}

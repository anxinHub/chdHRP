
package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiSubSchemeSeqMapper;
import com.chd.hrp.hpm.entity.AphiSubSchemeSeq;
import com.chd.hrp.hpm.service.AphiSubSchemeSeqService;

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


@Service("aphiSubSchemeSeqService")
public class AphiSubSchemeSeqServiceImpl implements AphiSubSchemeSeqService {

	private static Logger logger = Logger.getLogger(AphiSubSchemeSeqServiceImpl.class);
	
	@Resource(name = "aphiSubSchemeSeqMapper")
	private final AphiSubSchemeSeqMapper aphiSubSchemeSeqMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addSubSchemeSeq(Map<String,Object> entityMap)throws DataAccessException{
	    int state = aphiSubSchemeSeqMapper.addSubSchemeSeq(entityMap);
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
	public String querySubSchemeSeq(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(aphiSubSchemeSeqMapper.querySubSchemeSeq(entityMap, rowBounds), sysPage.getTotal());
	}
	
	/**
	 * 
	 */
	@Override
	public AphiSubSchemeSeq querySubSchemeSeqByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiSubSchemeSeqMapper.querySubSchemeSeqByCode(entityMap);
	}
	
	
	/**
	 * 
	 */
	
	public String deleteSubSchemeSeq(List<Map<String,Object>> entityList)throws DataAccessException{
		String request="";
		for (Map<String,Object> entityMap : entityList) {
	        int state = aphiSubSchemeSeqMapper.deleteSubSchemeSeq(entityMap);
        	if (state > 0) {
			request="{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request="{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
        }
		
		return request;
	}
	@Override
    public String deleteSubSchemeSeq(Map<String, Object> entityMap) throws DataAccessException {
		String request="";
		int state = aphiSubSchemeSeqMapper.deleteSubSchemeSeq(entityMap);
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
	public String deleteSubSchemeSeqById(String[] ids)throws DataAccessException{
		String request="";
		if (ids != null && ids.length>0) {
					for (String s : ids) {
						aphiSubSchemeSeqMapper.deleteSubSchemeSeqById(s);
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
	public String updateSubSchemeSeq(Map<String,Object> entityMap)throws DataAccessException{
		int state = aphiSubSchemeSeqMapper.updateSubSchemeSeq(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}
}

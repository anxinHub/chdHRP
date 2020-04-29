/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.apply;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.apply.AssApplyAcceptMapMapper;
import com.chd.hrp.ass.entity.apply.AssApplyAcceptMap;
import com.chd.hrp.ass.service.apply.AssApplyAcceptMapService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050201 购置申请与验收关系表
 * @Table:
 * ASS_APPLY_ACCEPT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assApplyAcceptMapService")
public class AssApplyAcceptMapServiceImpl implements AssApplyAcceptMapService {

	private static Logger logger = Logger.getLogger(AssApplyAcceptMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assApplyAcceptMapMapper")
	private final AssApplyAcceptMapMapper assApplyAcceptMapMapper = null;
    
	/**
	 * @Description 
	 * 添加050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssApplyAcceptMap(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assApplyAcceptMapMapper.addAssApplyAcceptMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050201 购置申请与验收关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssApplyAcceptMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assApplyAcceptMapMapper.addBatchAssApplyAcceptMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 删除050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssApplyAcceptMap(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assApplyAcceptMapMapper.deleteAssApplyAcceptMap(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050201 购置申请与验收关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssApplyAcceptMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assApplyAcceptMapMapper.deleteBatchAssApplyAcceptMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssApplyAcceptMap(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssApplyAcceptMap> list = assApplyAcceptMapMapper.queryAssApplyAcceptMap(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssApplyAcceptMap> list = assApplyAcceptMapMapper.queryAssApplyAcceptMap(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
}

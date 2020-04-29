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
import com.chd.hrp.ass.dao.apply.AssApplyInMapMapper;
import com.chd.hrp.ass.entity.apply.AssApplyInMap;
import com.chd.hrp.ass.service.apply.AssApplyInMapService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050201 购置申请与入库关系表
 * @Table:
 * ASS_APPLY_IN_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assApplyInMapService")
public class AssApplyInMapServiceImpl implements AssApplyInMapService {

	private static Logger logger = Logger.getLogger(AssApplyInMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assApplyInMapMapper")
	private final AssApplyInMapMapper assApplyInMapMapper = null;
    
	/**
	 * @Description 
	 * 添加050201 购置申请与入库关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssApplyInMap(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assApplyInMapMapper.addAssApplyInMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050201 购置申请与入库关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssApplyInMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assApplyInMapMapper.addBatchAssApplyInMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
	/**
	 * @Description 
	 * 删除050201 购置申请与入库关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssApplyInMap(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assApplyInMapMapper.deleteAssApplyInMap(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050201 购置申请与入库关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssApplyInMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assApplyInMapMapper.deleteBatchAssApplyInMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050201 购置申请与入库关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssApplyInMap(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssApplyInMap> list = assApplyInMapMapper.queryAssApplyInMap(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssApplyInMap> list = assApplyInMapMapper.queryAssApplyInMap(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
}

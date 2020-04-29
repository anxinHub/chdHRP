/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.bid;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.bid.AssBidProjectMapper;
import com.chd.hrp.ass.entity.bid.AssBidProject;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.bid.AssBidProjectService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050401 招标管理
 * @Table:
 * ASS_BID_Project
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assBidProjectService")
public class AssBidProjectServiceImpl implements AssBidProjectService {

	private static Logger logger = Logger.getLogger(AssBidProjectServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBidProjectMapper")
	private final AssBidProjectMapper assBidProjectMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	/**
	 * @Description 
	 * 添加050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssBidProject(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050401 招标管理
		AssBidProject assBidProject = queryAssBidProjectByCode(entityMap);

		if (assBidProject != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			entityMap.put("bill_table", "ASS_BID_PROJECT");
			String project_no=assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("project_no", project_no);
			int state = assBidProjectMapper.addAssBidProject(entityMap);
			Long project_id=queryAssBidProjectSequence();
			if(state>0){
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"project_id\":\""+project_id+"\",\"project_no\":\""+project_no+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050401 招标管理<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssBidProject(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidProjectMapper.addBatchAssBidProject(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssBidProject(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBidProjectMapper.updateAssBidProject(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050401 招标管理<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssBidProject(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBidProjectMapper.updateBatchAssBidProject(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssBidProject(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBidProjectMapper.deleteAssBidProject(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050401 招标管理<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssBidProject(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidProjectMapper.deleteBatchAssBidProject(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssBidProject(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050401 招标管理
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
    	mapVo.put("project_id", entityMap.get("project_id"));
		
		List<AssBidProject> list = assBidProjectMapper.queryAssBidProjectExists(mapVo);
		
		if (list.size() > 0) {

			int state = assBidProjectMapper.updateAssBidProject(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"project_id\":\""+entityMap.get("project_id")+"\",\"project_no\":\""+entityMap.get("project_no")+"\"}";

		}
		entityMap.put("bill_table", "ASS_BID_PROJECT");
		try {
			String project_no=assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("project_no", project_no);
			int state = assBidProjectMapper.addAssBidProject(entityMap);
			Long project_id=queryAssBidProjectSequence();
			if(state>0){
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"project_id\":\""+project_id+"\",\"project_no\":\""+project_no+"\"}";


		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050401 招标管理<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssBidProject(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBidProject> list = assBidProjectMapper.queryAssBidProject(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBidProject> list = assBidProjectMapper.queryAssBidProject(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssBidProject queryAssBidProjectByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBidProjectMapper.queryAssBidProjectByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidProject
	 * @throws DataAccessException
	*/
	@Override
	public AssBidProject queryAssBidProjectByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBidProjectMapper.queryAssBidProjectByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidProject>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssBidProject> queryAssBidProjectExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBidProjectMapper.queryAssBidProjectExists(entityMap);
	}
	@Override
	public Long queryAssBidProjectSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assBidProjectMapper.queryAssBidProjectSequence();
	}
	@Override
	public List<AssBidProject> queryAssBidProjectList(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AssBidProject> list = assBidProjectMapper.queryAssBidProject(entityMap);
		
		return list;
	}
	
	
	
}

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
import com.chd.hrp.ass.dao.bid.AssBidMainMapper;
import com.chd.hrp.ass.entity.bid.AssBidMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.bid.AssBidMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050401 招标管理
 * @Table:
 * ASS_BID_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assBidMainService")
public class AssBidMainServiceImpl implements AssBidMainService {

	private static Logger logger = Logger.getLogger(AssBidMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBidMainMapper")
	private final AssBidMainMapper assBidMainMapper = null;
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
	public String addAssBidMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050401 招标管理
		AssBidMain assBidMain = queryAssBidMainByCode(entityMap);

		if (assBidMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			entityMap.put("bill_table", "ASS_BID_MAIN");
			String bid_no=assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("bid_no", bid_no);
			int state = assBidMainMapper.addAssBidMain(entityMap);
			Long bid_id=queryAssBidMainSequence();
			if(state>0){
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"bid_id\":\""+bid_id+"\",\"bid_no\":\""+bid_no+"\"}";

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
	public String addBatchAssBidMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidMainMapper.addBatchAssBidMain(entityList);
			
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
	public String updateAssBidMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBidMainMapper.updateAssBidMain(entityMap);
			
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
	public String updateBatchAssBidMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBidMainMapper.updateBatchAssBidMain(entityList);
			
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
    public String deleteAssBidMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBidMainMapper.deleteAssBidMain(entityMap);
			
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
	public String deleteBatchAssBidMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidMainMapper.deleteBatchAssBidMain(entityList);
			
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
	public String addOrUpdateAssBidMain(Map<String,Object> entityMap)throws DataAccessException{
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
    	mapVo.put("bid_id", entityMap.get("bid_id"));
		
		List<AssBidMain> list = assBidMainMapper.queryAssBidMainExists(mapVo);
		
		if (list.size() > 0) {

			int state = assBidMainMapper.updateAssBidMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"bid_id\":\""+entityMap.get("bid_id")+"\",\"bid_no\":\""+entityMap.get("bid_no")+"\"}";

		}
		entityMap.put("bill_table", "ASS_BID_MAIN");
		try {
			String bid_no=assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("bid_no", bid_no);
			int state = assBidMainMapper.addAssBidMain(entityMap);
			Long bid_id=queryAssBidMainSequence();
			if(state>0){
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"bid_id\":\""+bid_id+"\",\"bid_no\":\""+bid_no+"\"}";


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
	public String queryAssBidMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBidMain> list = assBidMainMapper.queryAssBidMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBidMain> list = assBidMainMapper.queryAssBidMain(entityMap, rowBounds);
			
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
	public AssBidMain queryAssBidMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBidMainMapper.queryAssBidMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidMain
	 * @throws DataAccessException
	*/
	@Override
	public AssBidMain queryAssBidMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBidMainMapper.queryAssBidMainByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标管理<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssBidMain> queryAssBidMainExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBidMainMapper.queryAssBidMainExists(entityMap);
	}
	@Override
	public Long queryAssBidMainSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assBidMainMapper.queryAssBidMainSequence();
	}
	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  int state = assBidMainMapper.updateToExamine(entityMap);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  int state = assBidMainMapper.updateNotToExamine(entityMap);
				
				return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public List<AssBidMain> queryAssBidMainList(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AssBidMain> list = assBidMainMapper.queryAssBidMain(entityMap);
		
		return list;
	}
	
	/**
	 * @Description 
	 * 引入购置计划时  批量往中间表 添加数据  《ASS_BID_PLAN_MAP》
	 * @param  entityMap	
	 * @return String
	 * @throws DataAccessException
	*/
	
	@Override
	public String addBatchAssBidMainImportPlan( List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	
	/**
	 * @Description 
	 * 引入购置计划时  往中间表 添加数据  《ASS_BID_PLAN_MAP》
	 * @param  entityMap	
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String addAssBidMainImportPlan(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
					
					assBidMainMapper.addAssBidMainImportPlan(entityMap);
					
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		
				}
				catch (Exception e) {
		
					logger.error(e.getMessage(), e);
		
					throw new SysException(e.getMessage());
		
				}
	}
	@Override
	public String queryAssBidFileMain(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBidMain> list = assBidMainMapper.queryAssBidFileMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBidMain> list = assBidMainMapper.queryAssBidFileMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
}

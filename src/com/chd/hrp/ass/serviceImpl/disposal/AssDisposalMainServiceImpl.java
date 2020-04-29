/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.disposal;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.disposal.AssDisposalDetailMapper;
import com.chd.hrp.ass.dao.disposal.AssDisposalMainMapper;
import com.chd.hrp.ass.entity.disposal.AssDisposalMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.disposal.AssDisposalMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051001资产处置主表
 * @Table:
 * ASS_DISPOSAL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assDisposalMainService")
public class AssDisposalMainServiceImpl implements AssDisposalMainService {

	private static Logger logger = Logger.getLogger(AssDisposalMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDisposalMainMapper")
	private final AssDisposalMainMapper assDisposalMainMapper = null;
	@Resource(name = "assDisposalDetailMapper")
	private final AssDisposalDetailMapper assDisposalDetailMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	 * @Description 
	 * 添加051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assDisposalMainMapper.addAssDisposalMain(entityMap);
			
			Long dis_id = assDisposalMainMapper.queryCurrentSequence();
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"dis_id\":\""+dis_id+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051001资产处置主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssDisposalMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDisposalMainMapper.addBatchAssDisposalMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assDisposalMainMapper.updateAssDisposalMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051001资产处置主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssDisposalMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assDisposalMainMapper.updateBatchAssDisposalMain(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssDisposalMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assDisposalMainMapper.deleteAssDisposalMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051001资产处置主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssDisposalMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDisposalMainMapper.deleteBatchAssDisposalMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssDisposalMain(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051001资产处置主表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("dis_id", entityMap.get("dis_id"));
		
		List<AssDisposalMain> list = assDisposalMainMapper.queryAssDisposalMainExists(mapVo);
		
		if (list.size() > 0) {

			int state = assDisposalMainMapper.updateAssDisposalMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"dis_id\":\""+entityMap.get("dis_id")+"\",\"ass_dis_no\":\""+entityMap.get("ass_dis_no")+"\"}";

		}
		
		try {
			if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
				entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
				entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
			}
			entityMap.put("bill_table", "ASS_DISPOSAL_MAIN");
			String ass_dis_no=assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("ass_dis_no", ass_dis_no);
			int state = assDisposalMainMapper.addAssDisposalMain(entityMap);
			Long dis_id=assDisposalMainMapper.queryCurrentSequence();
			if(state>0){
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"dis_id\":\""+dis_id+"\",\"ass_dis_no\":\""+ass_dis_no+"\"}";


		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051001资产处置主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssDisposalMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDisposalMain> list = assDisposalMainMapper.queryAssDisposalMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDisposalMain> list = assDisposalMainMapper.queryAssDisposalMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051001资产处置主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssDisposalMain queryAssDisposalMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assDisposalMainMapper.queryAssDisposalMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051001资产处置主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssDisposalMain
	 * @throws DataAccessException
	*/
	@Override
	public AssDisposalMain queryAssDisposalMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assDisposalMainMapper.queryAssDisposalMainByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051001资产处置主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssDisposalMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssDisposalMain> queryAssDisposalMainExists(Map<String,Object> entityMap)throws DataAccessException{
		return assDisposalMainMapper.queryAssDisposalMainExists(entityMap);
	}
	
	
	/**
	 * @Description 
	 * 查询数据(按照新建状态) 051001资产处置主表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@Override
	public String queryAssDisposalState(Map<String, Object> entityMap)
			throws DataAccessException {
SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDisposalMain> list = assDisposalMainMapper.queryAssDisposalState(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDisposalMain> list = assDisposalMainMapper.queryAssDisposalState(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
//	@Override
//	public String updateAssDisposalApproveAudit(
//			List<Map<String, Object>> entityList) throws DataAccessException {
//		try {
//			
//			assDisposalDetailMapper.updateAssDisposalApproveAudit(entityList);
//				
//				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
//
//			}
//			catch (Exception e) {
//
//				logger.error(e.getMessage(), e);
//
//				throw new SysException(e.getMessage());
//
//			}	
//	}
	@Override
	public String updateAssDisposalApproveAudit(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			assDisposalDetailMapper.updateAssDisposalApproveAudit(entityMap);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
	}
	@Override
	public String updateBatchAssDisposalMain1(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			  assDisposalMainMapper.updateBatchAssDisposalMain1(entityMap);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
	}
	@Override
	public String updateAccountAssDisposalMain(
			Map<String, Object>entityMap) throws DataAccessException {
		try {
			
			  assDisposalMainMapper.updateAccountAssDisposalMain(entityMap);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
	}
	@Override
	public String updateRemoveAssDisposalMain(
			Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  assDisposalMainMapper.updateRemoveAssDisposalMain(entityMap);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	
}

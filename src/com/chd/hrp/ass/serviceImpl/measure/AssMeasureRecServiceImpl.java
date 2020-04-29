/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.measure;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.measure.AssMeasureRecMapper;
import com.chd.hrp.ass.entity.measure.AssMeasureRec;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.measure.AssMeasureRecDetailService;
import com.chd.hrp.ass.service.measure.AssMeasureRecService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051204 检测计量记录
 * @Table:
 * ASS_MEASURE_REC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assMeasureRecService")
public class AssMeasureRecServiceImpl implements AssMeasureRecService { 

	private static Logger logger = Logger.getLogger(AssMeasureRecServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMeasureRecMapper")
	private final AssMeasureRecMapper assMeasureRecMapper = null;
	
	@Resource(name = "assMeasureRecDetailService")
	private final AssMeasureRecDetailService assMeasureRecDetailService = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    
	/**
	 * @Description 
	 * 添加051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051204 检测计量记录
		AssMeasureRec assMeasureRec = queryAssMeasureRecByCode(entityMap);

		if (assMeasureRec != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMeasureRecMapper.addAssMeasureRec(entityMap);
			
			Long sequence = assMeasureRecMapper.queryCurrentSequence();
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"sequence\":\""+sequence+"\"}"; 
			
			
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051204 检测计量记录<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMeasureRec(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMeasureRecMapper.addBatchAssMeasureRec(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMeasureRecMapper.updateAssMeasureRec(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051204 检测计量记录<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMeasureRec(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMeasureRecMapper.updateBatchAssMeasureRec(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMeasureRec(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMeasureRecMapper.deleteAssMeasureRec(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051204 检测计量记录<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMeasureRec(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMeasureRecMapper.deleteBatchAssMeasureRec(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssMeasureRec(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051204 检测计量记录
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("acct_year", entityMap.get("acct_year"));
    	
    	mapVo.put("rec_id", entityMap.get("rec_id"));
		
		List<AssMeasureRec> list = assMeasureRecMapper.queryAssMeasureRecExists(mapVo);
		
		if (list.size()>0) {

			int state = assMeasureRecMapper.updateAssMeasureRec(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"rec_id\":\""+entityMap.get("rec_id")+"\",\"seq_no\":\""+entityMap.get("seq_no")+"\"}";


		}
		
		try {
			if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
				entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
				entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
			}
			entityMap.put("bill_table", "ASS_MEASURE_REC");
			String seq_no = assBaseService.getBillNOSeqNo(entityMap);
			 
			entityMap.put("seq_no", seq_no); 
			
			int state = assMeasureRecMapper.addAssMeasureRec(entityMap);
			
			Long rec_id = assMeasureRecMapper.queryCurrentSequence();
			
			if(state > 0){
				
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"rec_id\":\""+rec_id+"\",\"seq_no\":\""+seq_no+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051204 检测计量记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMeasureRec(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMeasureRec> list = assMeasureRecMapper.queryAssMeasureRec(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMeasureRec> list = assMeasureRecMapper.queryAssMeasureRec(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051204 检测计量记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMeasureRec queryAssMeasureRecByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMeasureRecMapper.queryAssMeasureRecByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051204 检测计量记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMeasureRec
	 * @throws DataAccessException
	*/
	@Override
	public AssMeasureRec queryAssMeasureRecByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assMeasureRecMapper.queryAssMeasureRecByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051204 检测计量记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMeasureRec>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssMeasureRec> queryAssMeasureRecExists(Map<String,Object> entityMap)throws DataAccessException{
		return assMeasureRecMapper.queryAssMeasureRecExists(entityMap);
	}
	
	/**
	 * 批量更新计量记录  (消审)
	 */
	@Override
	public String updateBatchAssMeasureRecBack(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMeasureRecMapper.updateBatchAssMeasureRecBack(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String queryAssMeasureRecByCard(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMeasureRec> list = assMeasureRecMapper.queryAssMeasureRecByCard(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMeasureRec> list = assMeasureRecMapper.queryAssMeasureRecByCard(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	@Override
	public String addOrUpdateMeasureRec(Map<String, Object> entityMap) {
		
		String rec_id = entityMap.get("rec_id").toString();
		String seq_no = entityMap.get("seq_no").toString();
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	
    	
    	mapVo.put("rec_id", entityMap.get("rec_id"));
		
		List<AssMeasureRec> list = assMeasureRecMapper.queryAssMeasureRecExists(mapVo);
	entityMap.put("bill_table", "ASS_MEASURE_REC");
	try{
		if(list.size() > 0){
			assMeasureRecMapper.updateAssMeasureRec(entityMap);
		} else {
			seq_no = assBaseService.getBillNOSeqNo(entityMap);
			 
			entityMap.put("seq_no", seq_no); 
			
			int state = assMeasureRecMapper.addAssMeasureRec(entityMap);
			
			rec_id =String.valueOf(queryCurrentSequence());
			if(state > 0){
							
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
			}
		}
		validateMapVo.put("group_id", SessionManager.getGroupId());

		validateMapVo.put("hos_id", SessionManager.getHosId());

		validateMapVo.put("copy_code", SessionManager.getCopyCode());
		
		validateMapVo.put("rec_id",rec_id);
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			
			
			//detailVo.put("measure_result", entityMap.get("measure_result"));
			
			if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
				continue;
			}

			detailVo.put("rec_id", rec_id);

			if (detailVo.get("detail_id") == null) {
				detailVo.put("detail_id", "0");
			} else {
				detailVo.put("detail_id", detailVo.get("detail_id"));
			}
			
			if (detailVo.get("measure_memo") == null) {
				detailVo.put("measure_memo", "");
			} else {
				detailVo.put("measure_memo", detailVo.get("measure_memo"));
			}
			
			if (detailVo.get("measure_idea") == null) {
				detailVo.put("measure_idea", "");
			} else {
				detailVo.put("measure_idea", detailVo.get("measure_idea"));
			}
			
			if (detailVo.get("cert_no") == null) {
				detailVo.put("cert_no", "");
			} else {
				detailVo.put("cert_no", detailVo.get("cert_no"));
			}
			
			if (detailVo.get("measure_result") == null) {
				detailVo.put("measure_result", "");
			} else {
				detailVo.put("measure_result", detailVo.get("measure_result"));
			}
			
			if (StringUtils.isNotEmpty((String) detailVo.get("pre_next_date"))) {
				detailVo.put("pre_next_date",
						DateUtil.stringToDate(detailVo.get("pre_next_date").toString(), "yyyy-MM-dd"));
			}
			
			assMeasureRecDetailService.addOrUpdateAssMeasureRecDetail(detailVo);
			
		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"rec_id\":\"" + rec_id + "\",\"seq_no\":\""
		+seq_no + "\"}";
		
	}catch (Exception e) {
		logger.error(e.getMessage());
		throw new SysException(e.getMessage());
	}
	
	
	
	}
	
	@Override
	public Long queryCurrentSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assMeasureRecMapper.queryCurrentSequence();
	}
	
	//新版打印  调用的方法
		@Override
		public Map<String, Object> queryAssMeasureRecPrint(Map<String, Object> entityMap)throws DataAccessException {
			
			try{
				
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				AssMeasureRecMapper assMeasureRecMapper = (AssMeasureRecMapper)context.getBean("assMeasureRecMapper");
				 
				//主页面 批量打印查询
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					
					//查询 专用设备 入库主表
					List<Map<String,Object>> map= assMeasureRecMapper.queryAssMeasureRecByMainBatch(entityMap);
					//查询 专用设备  入库明细表
					List<Map<String,Object>> list= assMeasureRecMapper.queryAssMeasureRecByDetail(entityMap);
					
					reMap.put("main", map);
					
					reMap.put("detail", list); 
					
					return reMap;
					
				}else{ //修改页面 打印查询
					//
					Map<String,Object> map= assMeasureRecMapper.queryAssMeasureRecByMain(entityMap);
					//查询 专用设备  入库明细表
					List<Map<String,Object>> list= assMeasureRecMapper.queryAssMeasureRecByDetail(entityMap);
					
				
					reMap.put("main", map);
					
					reMap.put("detail", list);
					
					return reMap;
					
				}
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
			
			
		}
		@Override
		public List<String> queryAssMeasureRecState(Map<String, Object> mapVo) {
			// TODO Auto-generated method stub
			return assMeasureRecMapper.queryAssMeasureRecState(mapVo);
		}
}

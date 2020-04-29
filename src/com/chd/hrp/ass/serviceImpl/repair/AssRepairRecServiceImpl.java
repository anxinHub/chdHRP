/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.repair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.card.AssCardOtherMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.repair.AssRepairApplyMapper;
import com.chd.hrp.ass.dao.repair.AssRepairRecDetailMapper;
import com.chd.hrp.ass.dao.repair.AssRepairRecMapper;
import com.chd.hrp.ass.entity.repair.AssRepairApply;
import com.chd.hrp.ass.entity.repair.AssRepairRec;
import com.chd.hrp.ass.entity.repair.AssRepairRecDetail;
import com.chd.hrp.ass.service.repair.AssRepairRecService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_REC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assRepairRecService")
public class AssRepairRecServiceImpl implements AssRepairRecService {

	private static Logger logger = Logger.getLogger(AssRepairRecServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assRepairRecMapper")
	private final AssRepairRecMapper assRepairRecMapper = null;
	
	//引入DAO操作
	@Resource(name = "assRepairRecDetailMapper")
	private final AssRepairRecDetailMapper assRepairRecDetailMapper = null;
	//引入DAO操作
	@Resource(name = "assRepairApplyMapper")
	private final AssRepairApplyMapper assRepairApplyMapper = null;
    
	

	@Resource(name = "assCardSpecialMapper")
	private final AssCardSpecialMapper assCardSpecialMapper = null;
	
	
	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;
	
	@Resource(name = "assCardOtherMapper")
	private final AssCardOtherMapper assCardOtherMapper = null;
	
	@Resource(name = "assCardHouseMapper")
	private final AssCardHouseMapper assCardHouseMapper = null;
	
	@Resource(name = "assCardInassetsMapper")
	private final AssCardInassetsMapper assCardInassetsMapper = null;
	
	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象tabledesc
		AssRepairRec assRepairRec = queryByCode(entityMap);

		if (assRepairRec != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assRepairRecMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assRepairRecMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
			List<Map<String,Object>> listVo = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> exitMap = new HashMap<String, Object>();
			Map<String,Object> invMap = new HashMap<String, Object>();
			
		try {
			//从主表取出卡片信息，根据资产性质，更新卡片状态，
			Map<String,Object> cardMap = new HashMap<String, Object>();
			AssRepairApply assRepairApply =assRepairApplyMapper.queryByCode(entityMap);
			cardMap.put("group_id", assRepairApply.getGroup_id());
			cardMap.put("hos_id", assRepairApply.getHos_id());
			cardMap.put("copy_code", assRepairApply.getCopy_code());
			cardMap.put("ass_card_no", assRepairApply.getAss_card_no());
			if("7".equals(entityMap.get("state").toString())){
				cardMap.put("use_state", 4);
			}else{
				cardMap.put("use_state", 3);
			}
			
			if(assRepairApply.getAss_nature()!=null){
				if("02".equals(assRepairApply.getAss_nature())){
					assCardSpecialMapper.updateAssCardStateByRecCardNo(cardMap);
				}else if("03".equals(assRepairApply.getAss_nature())){
					assCardGeneralMapper.updateAssCardStateByRecCardNo(cardMap);
				}else if("04".equals(assRepairApply.getAss_nature())){
					assCardOtherMapper.updateAssCardStateByRecCardNo(cardMap);
				} else if("01".equals(assRepairApply.getAss_nature())){
					assCardHouseMapper.updateAssCardStateByRecCardNo(cardMap);
				}else if("05".equals(assRepairApply.getAss_nature())){
					assCardInassetsMapper.updateAssCardStateByRecCardNo(cardMap);
				}else if("06".equals(assRepairApply.getAss_nature())){
					assCardLandMapper.updateAssCardStateByRecCardNo(cardMap);
				}
			}
			//更新记录表信息，
		  int state = assRepairRecMapper.update(entityMap);
		  
		    //材料取出明细数据
		  List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		  for (Map<String,Object> map : detail) {
			if(map.get("inv_code")!=null){
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("repair_rec_no", entityMap.get("repair_rec_no"));
				map.put("ass_year", assRepairApply.getAss_year());
				map.put("ass_month", assRepairApply.getAss_month());
				exitMap.put("group_id", SessionManager.getGroupId());
				exitMap.put("hos_id", SessionManager.getHosId());
				exitMap.put("copy_code", SessionManager.getCopyCode());
				//判断所传
				AssRepairRecDetail assRepairRecDetail=	assRepairRecDetailMapper.queryAssRepairRecDetailByCode(map);
				if(assRepairRecDetail!=null){
					exitMap.put("repair_rec_no", assRepairRecDetail.getRepair_rec_no());
					exitMap.put("inv_code", assRepairRecDetail.getInv_code());
					listVo.add(exitMap);
				}
				list.add(map);
			}
		}
		  if(listVo.size()>0){
			  	assRepairRecDetailMapper.deleteBatchAssRepairRecDetail(listVo);
				assRepairRecDetailMapper.addBatchAssRepairRecDetail(list);
		  }else if(list.size()>0){
			  assRepairRecDetailMapper.addBatchAssRepairRecDetail(list);
		  }
		  	
		  
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException(e);

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assRepairRecMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assRepairRecMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assRepairRecMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象tabledesc
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssRepairRec> list = (List<AssRepairRec>)assRepairRecMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assRepairRecMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assRepairRecMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssRepairRec> list = (List<AssRepairRec>)assRepairRecMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRepairRec> list = (List<AssRepairRec>)assRepairRecMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairRecMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRepairRec
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairRecMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRepairRec>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairRecMapper.queryExists(entityMap);
	}
	@Override
	public String queryAssRepairRecDetail(Map<String, Object> mapVo) {
		List<AssRepairRecDetail> list =(List<AssRepairRecDetail>) assRepairRecDetailMapper.queryAssRepairRecDetail(mapVo);
		 return ChdJson.toJson(list);
	}
	@Override
	public String deleteAssRepairRecDetail(List<Map<String, Object>> listVo) {
		try {
			assRepairRecDetailMapper.deleteBatchAssRepairRecDetail(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			throw new SysException(e);
		}
	}
	
	
	@Override
	public String auditAssRepairRec(List<Map<String, Object>> mapVo) {
		try {
			for (Map<String, Object> map : mapVo) {
				AssRepairRec assRepairRec=assRepairRecMapper.queryByCode(map);
				if(assRepairRec.getState()==3){
					continue;
				}else if (assRepairRec.getState()==4){
					return "{\"warn\":\"已维修确认维修完成的单据能审核，请重新选择.\",\"state\":\"false\"}";
				}else {
					map.put("audit_emp", SessionManager.getUserId());
					assRepairRecMapper.auditAssRepairRec(map);
				}
				
			}
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			
			throw new SysException(e);
		}
		
	}
	
	@Override
	public String backAssRepairRec(List<Map<String, Object>> mapVo) {
		try {
			for (Map<String, Object> map : mapVo) {
				AssRepairRec assRepairRec=assRepairRecMapper.queryByCode(map);
				if(assRepairRec.getState()==2){
					continue;
				}else if (assRepairRec.getState()!=3){
					return "{\"warn\":\"所选择单据存在未审核数据，请核对.\",\"state\":\"false\"}";
				}else if (assRepairRec.getState()==4){
					return "{\"warn\":\"已维修确认维修完成的单据能撤销审核，请重新选择.\",\"state\":\"false\"}";
				}else{
					map.put("audit_emp", SessionManager.getUserId());
					assRepairRecMapper.backAssRepairRec(map);
				}
				
			}
			return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			
			throw new SysException(e);
		}
		
	}
	@Override
	public String countAssRepairRec(List<Map<String, Object>> mapVo) {
		try {
			for (Map<String, Object> map : mapVo) {
				Map<String,Object> cardMap = new HashMap<String, Object>();
				AssRepairRec assRepairRec=assRepairRecMapper.queryByCode(map);
				
				if(assRepairRec.getState()!=3){
					return "{\"warn\":\"维修确认完成的单据必须为审核状态.\",\"state\":\"false\"}";
				}else{
					map.put("audit_emp", SessionManager.getUserId());
					assRepairRecMapper.countAssRepairRec(map);
				}
				cardMap.put("group_id", assRepairRec.getGroup_id());
				cardMap.put("hos_id", assRepairRec.getHos_id());
				cardMap.put("copy_code", assRepairRec.getCopy_code());
				cardMap.put("ass_card_no", assRepairRec.getAss_card_no());
				cardMap.put("use_state",4);
				if(assRepairRec.getAss_nature()!=null){
					if("02".equals(assRepairRec.getAss_nature())){
						assCardSpecialMapper.updateAssCardStateByRecCardNo(cardMap);
					}else if("03".equals(assRepairRec.getAss_nature())){
						assCardGeneralMapper.updateAssCardStateByRecCardNo(cardMap);
					}else if("04".equals(assRepairRec.getAss_nature())){
						assCardOtherMapper.updateAssCardStateByRecCardNo(cardMap);
					} else if("01".equals(assRepairRec.getAss_nature())){
						assCardHouseMapper.updateAssCardStateByRecCardNo(cardMap);
					}else if("05".equals(assRepairRec.getAss_nature())){
						assCardInassetsMapper.updateAssCardStateByRecCardNo(cardMap);
					}else if("06".equals(assRepairRec.getAss_nature())){
						assCardLandMapper.updateAssCardStateByRecCardNo(cardMap);
					}
				}
				
			}
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			
			throw new SysException(e);
		}
		
	}
	/**
	 * 资产维修打印
	 */
	@Override
	public Map<String,Object> queryAssRepairRecDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssRepairRecMapper assRepairRecMapper = (AssRepairRecMapper)context.getBean("assRepairRecMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 资产维修打印模板主表
				List<Map<String,Object>> mainList=assRepairRecMapper.queryaAssRepairRecBatch(map);
						
				//资产维修打印模板从表
				List<Map<String,Object>> detailList=assRepairRecMapper.queryAssRepairRec_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assRepairRecMapper.querAssRepairRecByPrint(map);
				
				//资产维修打印模板从表
				List<Map<String,Object>> detailList=assRepairRecMapper.queryAssRepairRec_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
}

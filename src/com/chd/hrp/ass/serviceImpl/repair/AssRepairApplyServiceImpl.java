/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.repair;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.card.AssCardOtherMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.repair.AssRepairApplyMapper;
import com.chd.hrp.ass.dao.repair.AssRepairRecMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlan;
import com.chd.hrp.ass.entity.repair.AssRepairApply;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.repair.AssRepairApplyService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assRepairApplyService")
public class AssRepairApplyServiceImpl implements AssRepairApplyService {

	private static Logger logger = Logger.getLogger(AssRepairApplyServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assRepairApplyMapper")
	private final AssRepairApplyMapper assRepairApplyMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	//维修记录表
	@Resource(name = "assRepairRecMapper")
	private final AssRepairRecMapper assRepairRecMapper = null;
	
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
		
		entityMap.put("bill_table", "ASS_REPAIR_APPLY");
		
		try {
			String apply_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("apply_no", apply_no);
			entityMap.put("create_emp", SessionManager.getUserId());
			entityMap.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			entityMap.put("audit_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			int state = assRepairApplyMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
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
			
			assRepairApplyMapper.addBatch(entityList);
			
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
		
		try {
			
		  int state = assRepairApplyMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

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
			
		  assRepairApplyMapper.updateBatch(entityList);
			
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
			
			int state = assRepairApplyMapper.delete(entityMap);
			
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
			
			assRepairApplyMapper.deleteBatch(entityList);
			
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
		
		List<AssRepairApply> list = (List<AssRepairApply>)assRepairApplyMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assRepairApplyMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assRepairApplyMapper.add(entityMap);
			
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
			
			List<AssRepairApply> list = (List<AssRepairApply>)assRepairApplyMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRepairApply> list = (List<AssRepairApply>)assRepairApplyMapper.query(entityMap, rowBounds);
			
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
		return assRepairApplyMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRepairApply
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairApplyMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRepairApply>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairApplyMapper.queryExists(entityMap);
	}
	@Override
	public String auditAssRepairApply(List<Map<String, Object>> listVo) {
		for (Map<String, Object> map : listVo) {
			//根据单号查询维修申请的数据将数据灌输到维修记录的表中，同时将有卡片的维修单中的卡片变成待修状态
			Map<String,Object>mapVo=assRepairApplyMapper.queryByApplyNo(map);
			map.put("bill_table", "ASS_REPAIR_REC");
			if("0".equals(mapVo.get("state").toString())){
				String repair_rec_no = assBaseService.getBillNOSeqNo(map);
				mapVo.put("repair_rec_no", repair_rec_no);
				mapVo.put("create_emp", SessionManager.getUserId());
				mapVo.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				mapVo.put("audit_emp", SessionManager.getUserId());
				mapVo.put("audit_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				assBaseService.updateAssBillNoMaxNo(map);
				//判断维修单中是否存在卡片
				if(mapVo.get("ass_nature")!=null){
					if("02".equals(mapVo.get("ass_nature"))){
						assCardSpecialMapper.updateAssCardStateByCardNo(mapVo);
					}else if("03".equals(mapVo.get("ass_nature"))){
						assCardGeneralMapper.updateAssCardStateByCardNo(mapVo);
					}else if("04".equals(mapVo.get("ass_nature"))){
						assCardOtherMapper.updateAssCardStateByCardNo(mapVo);
					} else if("01".equals(mapVo.get("ass_nature"))){
						assCardHouseMapper.updateAssCardStateByCardNo(mapVo);
					}else if("05".equals(mapVo.get("ass_nature"))){
						assCardInassetsMapper.updateAssCardStateByCardNo(mapVo);
					}else if("06".equals(mapVo.get("ass_nature"))){
						assCardLandMapper.updateAssCardStateByCardNo(mapVo);
					}
				}
				mapVo.put("fixed_dept_id", mapVo.get("repair_dept_id"));
				mapVo.put("fixed_dept_no", mapVo.get("repair_dept_no"));
				assRepairApplyMapper.auditAssRepairApply(mapVo);
				assRepairRecMapper.add(mapVo);
			}else{
				continue ;
			}
					
		}
		
		return "{\"msg\":\"成功.\",\"state\":\"true\"}";
	}
	//查询维修提醒
	@Override
	public String queryAssRepairApply(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssRepairApply> list = (List<AssRepairApply>)assRepairApplyMapper.queryy(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRepairApply> list = (List<AssRepairApply>)assRepairApplyMapper.queryy(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public AssRepairApply queryAssMaintainPlanByCode(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assRepairApplyMapper.queryAssMaintainPlanByCode(mapVo);
	}
	
	
}

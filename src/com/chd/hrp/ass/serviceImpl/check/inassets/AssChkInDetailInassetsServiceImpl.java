/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.check.inassets;

import java.util.ArrayList;
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.check.inassets.AssChkDsInInassetsMapMapper;
import com.chd.hrp.ass.dao.check.inassets.AssChkInDetailInassetsMapper;
import com.chd.hrp.ass.dao.check.inassets.AssChkInMainInassetsMapper;
import com.chd.hrp.ass.dao.check.inassets.AssChkRdetailInassetsMapper;
import com.chd.hrp.ass.dao.check.inassets.AssChkRinassetsMapper;
import com.chd.hrp.ass.entity.check.inassets.AssChkDsInInassetsMap;
import com.chd.hrp.ass.entity.check.inassets.AssChkInDetailInassets;
import com.chd.hrp.ass.entity.check.inassets.AssChkInMainInassets;
import com.chd.hrp.ass.entity.check.inassets.AssChkRdetailInassets;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.inassets.AssChkInDetailInassetsService;
import com.chd.hrp.ass.service.check.inassets.AssChkInMainInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050701 资产盘盈入库明细(无形资产)
 * @Table:
 * ASS_CHK_IN_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assChkInDetailInassetsService")
public class AssChkInDetailInassetsServiceImpl implements AssChkInDetailInassetsService { 

	private static Logger logger = Logger.getLogger(AssChkInDetailInassetsServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assChkInDetailInassetsMapper")
	private final AssChkInDetailInassetsMapper assChkInDetailInassetsMapper = null;
    
	@Resource(name = "assChkDsInInassetsMapMapper")
	private final AssChkDsInInassetsMapMapper assChkDsInInassetsMapMapper = null;
	
	@Resource(name = "assChkInMainInassetsMapper")
	private final AssChkInMainInassetsMapper assChkInMainInassetsMapper = null;
	
	@Resource(name = "assChkRdetailInassetsMapper")
	private final AssChkRdetailInassetsMapper assChkRdetailInassetsMapper = null;
	
	@Resource(name = "assChkRinassetsMapper")
	private final AssChkRinassetsMapper assChkRinassetsMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assChkInMainInassetsService")
	private final AssChkInMainInassetsService assChkInMainInassetsService = null;
	/**
	 * @Description 
	 * 添加050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050701 资产盘盈入库明细(无形资产)
		AssChkInDetailInassets assChkInDetailInassets = queryByCode(entityMap);

		if (assChkInDetailInassets != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assChkInDetailInassetsMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assChkInDetailInassetsMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assChkInDetailInassetsMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assChkInDetailInassetsMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assChkInDetailInassetsMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assChkInDetailInassetsMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加050701 资产盘盈入库明细(无形资产)<BR> 
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
		//判断是否存在对象050701 资产盘盈入库明细(无形资产)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssChkInDetailInassets> list = (List<AssChkInDetailInassets>)assChkInDetailInassetsMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assChkInDetailInassetsMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assChkInDetailInassetsMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssChkInDetailInassets> list = (List<AssChkInDetailInassets>)assChkInDetailInassetsMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssChkInDetailInassets> list = (List<AssChkInDetailInassets>)assChkInDetailInassetsMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInDetailInassetsMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssChkInDetailInassets
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInDetailInassetsMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050701 资产盘盈入库明细(无形资产)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssChkInDetailInassets>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInDetailInassetsMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> mapVo) throws DataAccessException {
		return assChkInDetailInassetsMapper.queryByInit(mapVo);
	}
	@Override
	public String saveInassetsInventory(List<Map<String, Object>> listVo)
			throws DataAccessException {
		List<Map<String, Object>> listVo1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo2 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo3 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo4 = new ArrayList<Map<String, Object>>();
		
		int i = 0;//盘盈数量
		int f = 0;//盘亏数量
		
		try {
			
			//判断是否重复生成盈亏单
			for( Map<String, Object> listV : listVo){
				Map<String, Object> mapVo4 = new HashMap<String, Object>();
				mapVo4.put("hos_id",listV.get("hos_id"));
				mapVo4.put("group_id",listV.get("group_id"));
				mapVo4.put("copy_code",listV.get("copy_code"));
				 String check_no = listV.get("check_no").toString();
				mapVo4.put("check_no", check_no);
				mapVo4.put("check_plan_no", listV.get("check_plan_no"));
				List<AssChkDsInInassetsMap> list = (List<AssChkDsInInassetsMap>) assChkDsInInassetsMapMapper.queryy(mapVo4);
				for( AssChkDsInInassetsMap ids: list){
					String ass_in_nos = ids.getAss_in_no();
					mapVo4.put("ass_in_no", ass_in_nos);
					 List<AssChkInMainInassets>  lists = (List<AssChkInMainInassets>) assChkInMainInassetsMapper.queryExists(mapVo4);
					 if(lists.size() != 0){
						 return "{\"msg\":\"盘点单"+check_no+"已经生成过盈亏单,不能重复生成\",\"state\":\"true\"}";
					 }
				}
				mapVo4.put("ass_card_no", listV.get("ass_card_no"));
				List<AssChkRdetailInassets> lists = (List<AssChkRdetailInassets>) assChkRdetailInassetsMapper.queryExists(mapVo4);
				if(lists.size() != 0){
					 return "{\"msg\":\"盘点单"+check_no+"已经生成过盈亏单,不能重复生成\",\"state\":\"true\"}";
				}
				
			}
			
				List<Map<String,Object>> cardMap1 = assChkInDetailInassetsMapper.queryStore(listVo);//盘盈
				for(  Map<String,Object> card : cardMap1){
					i++;
					Map<String, Object> mapVo1 = new HashMap<String, Object>();
					Map<String, Object> mapVo2 = new HashMap<String, Object>();
					//从表数据
						mapVo2.put("hos_id",card.get("hos_id"));
						mapVo2.put("group_id",card.get("group_id"));
						mapVo2.put("copy_code",card.get("copy_code"));
						mapVo2.put("ass_id",card.get("ass_id"));
						mapVo2.put("ass_no",card.get("ass_no"));
						mapVo2.put("store_id",card.get("store_id"));
						mapVo2.put("store_no",card.get("store_no"));
						card.put("bill_table", "ASS_CHK_IN_MAIN_INASSETS");
						String ass_in_no = assBaseService.getBillNOSeqNo(card);
						assBaseService.updateAssBillNoMaxNo(card);
						
						mapVo2.put("ass_in_no",ass_in_no);
						mapVo2.put("check_no",card.get("check_no"));
						mapVo2.put("check_plan_no",card.get("check_plan_no"));
						
						
						if(card.get("ass_spec") == null || "".equals(card.get("ass_spec"))){
							mapVo2.put("ass_spec","*");
						} else{
							mapVo2.put("ass_spec",card.get("ass_spec"));
						}
						if(card.get("ass_mondl") == null || "".equals(card.get("ass_mondl"))){
							mapVo2.put("ass_model","*");
						} else{
							mapVo2.put("ass_model",card.get("ass_mondl"));
						}
						mapVo2.put("ass_brand","");
						mapVo2.put("unit_code","");
						mapVo2.put("fac_id","");
						mapVo2.put("fac_no","");
					
						mapVo2.put("in_amount",card.get("in_amount"));//入库数量
						if(card.get("price") == null){
							mapVo2.put("price","0");
						}else{
							mapVo2.put("price",card.get("price"));
						}
						if(card.get("depre_money") == null){
							mapVo2.put("add_depre","0");
						}else{
							mapVo2.put("add_depre",card.get("depre_money"));
						}
						if(card.get("add_depre_month") == null){
							mapVo2.put("add_depre_month","");
						}else{
							mapVo2.put("add_depre_month",card.get("add_depre_month"));
						}
	
						if(card.get("cur_money") == null){
							mapVo2.put("cur_money","0");
						}else{
							mapVo2.put("cur_money",card.get("cur_money"));
						}
						if(card.get("fore_money") == null){
							mapVo2.put("fore_money","0");
						}else{
							mapVo2.put("fore_money",card.get("fore_money"));
						}
						mapVo2.put("note","");
						
						listVo2.add(mapVo2);
						
				
						//主表
						mapVo1.put("hos_id",card.get("hos_id"));
						mapVo1.put("group_id",card.get("group_id"));
						mapVo1.put("copy_code",card.get("copy_code"));
						mapVo1.put("ass_id",card.get("ass_id"));
						mapVo1.put("ass_no",card.get("ass_no"));
						mapVo1.put("store_id",card.get("store_id"));
						mapVo1.put("store_no",card.get("store_no"));
						mapVo1.put("ass_in_no",ass_in_no);
						if(card.get("proj_id") == null){
							mapVo1.put("proj_id","");
						}else{
							mapVo1.put("proj_id",card.get("proj_id"));
						}
						if(card.get("proj_no") == null){
							mapVo1.put("proj_no","");
						}else{
							mapVo1.put("proj_no",card.get("proj_no"));
						}
						if(card.get("ass_purpose") == null){
							mapVo1.put("ass_purpose","");
						}else{
							mapVo1.put("ass_purpose",card.get("ass_purpose"));
						}
						mapVo1.put("dept_id","");
						
						mapVo1.put("dept_no","");
					    
					 Double price = Double.valueOf(card.get("price").toString());
					 
					 Double in_amount = Double.valueOf(card.get("in_amount").toString());
					
					mapVo1.put("in_money", price * in_amount);//入库金额
					
					mapVo1.put("create_emp",SessionManager.getUserId());
					mapVo1.put("create_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					mapVo1.put("in_date","");
					mapVo1.put("confirm_emp","");
					mapVo1.put("state","0");
					mapVo1.put("note","");
					listVo1.add(mapVo1);	
				}
				
				if(cardMap1.size() != 0){
					//增加关系表 判断时用
					assChkDsInInassetsMapMapper.addBatch(listVo2);
					
					assChkInDetailInassetsMapper.addBatch(listVo2);
					
					
					assChkInMainInassetsMapper.addBatch(listVo1);	
					

					//生成卡片
					for(  Map<String,Object> card : cardMap1){
						Map<String, Object> mapVo3 = new HashMap<String, Object>();
							mapVo3.put("is_source","0");
							mapVo3.put("hos_id",card.get("hos_id"));
							mapVo3.put("group_id",card.get("group_id"));
							mapVo3.put("copy_code",card.get("copy_code"));
							mapVo3.put("ass_id",card.get("ass_id"));
							mapVo3.put("ass_no",card.get("ass_no"));
							
							
						assChkInMainInassetsService.initAssChkInBatchCardInassets(mapVo3);
					}
				}
				
				
				
				
				List<Map<String,Object>> cardMap2 = assChkRdetailInassetsMapper.queryStoreStore(listVo);//盘亏
				
				for(  Map<String,Object> card1 : cardMap2){
					f++;
					Map<String, Object> mapVo = new HashMap<String, Object>();
					Map<String, Object> mapVo3 = new HashMap<String, Object>();
					//从表
					mapVo.put("hos_id", card1.get("hos_id"));	
					mapVo.put("group_id", card1.get("group_id"));	
					mapVo.put("copy_code", card1.get("copy_code"));	
					mapVo.put("copy_code", card1.get("copy_code"));	
					card1.put("bill_table", "ASS_CHK_R_INASSETS");
					String ass_chk_no = assBaseService.getBillNOSeqNo(card1);
					assBaseService.updateAssBillNoMaxNo(card1);
					mapVo.put("ass_chk_no", ass_chk_no);
					mapVo.put("ass_card_no", card1.get("ass_card_no"));
					if(card1.get("price") == null){
						mapVo.put("price","0");
					}else{
						mapVo.put("price",card1.get("price"));
					}
					
					if(card1.get("fore_money") == null){
						mapVo.put("fore_money","0");
					}else{
						mapVo.put("fore_money",card1.get("fore_money"));
					}
					if(card1.get("cur_money") == null){
						mapVo.put("cur_money","0");
					}else{
						mapVo.put("cur_money",card1.get("cur_money"));
					}
					mapVo.put("now_depre", "0");
					mapVo.put("now_manage_depre", "0");
					mapVo.put("note", "");
					if(card1.get("manage_depre_money") == null){
						mapVo.put("add_manage_depre","0");
					}else{
						mapVo.put("add_manage_depre",card1.get("manage_depre_money"));
					}
					if(card1.get("depre_money") == null){
						mapVo.put("add_depre","0");
					}else{
						mapVo.put("add_depre",card1.get("depre_money"));
					}
					
					listVo3.add(mapVo);
					
					//主表
					mapVo3.put("hos_id", card1.get("hos_id"));	
					mapVo3.put("group_id", card1.get("group_id"));	
					mapVo3.put("copy_code", card1.get("copy_code"));	
					mapVo3.put("copy_code", card1.get("copy_code"));
					mapVo3.put("ass_chk_no",ass_chk_no);
					mapVo3.put("note","");
					mapVo3.put("create_emp",SessionManager.getUserId());
					mapVo3.put("create_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					mapVo3.put("apply_date","");
					mapVo3.put("audit_emp","");
					mapVo3.put("state","0");
					listVo4.add(mapVo3);
					
					
				}
				if(cardMap2.size() != 0){
					assChkRdetailInassetsMapper.addBatch(listVo3);
					assChkRinassetsMapper.addBatch(listVo4);
				}
			
			
					
			
				return "{\"msg\":\"生成成功.盘盈入库   "+i+"条"+"盘亏记录     "+f+"条\",\"state\":\"true\"}";
		
				
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String saveDeptInassetsInventory(List<Map<String, Object>> listVo)
			throws DataAccessException {
		List<Map<String, Object>> listVo1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo2 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo3 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo4 = new ArrayList<Map<String, Object>>();
		
		int i = 0;//盘盈数量
		int f = 0;//盘亏数量
		
		try {
			
			//判断是否重复生成盈亏单
			for( Map<String, Object> listV : listVo){
				Map<String, Object> mapVo4 = new HashMap<String, Object>();
				mapVo4.put("hos_id",listV.get("hos_id"));
				mapVo4.put("group_id",listV.get("group_id"));
				mapVo4.put("copy_code",listV.get("copy_code"));
				 String check_no = listV.get("check_no").toString();
				mapVo4.put("check_no", check_no);
				mapVo4.put("check_plan_no", listV.get("check_plan_no"));
				List<AssChkDsInInassetsMap> list = (List<AssChkDsInInassetsMap>) assChkDsInInassetsMapMapper.queryy(mapVo4);
				for( AssChkDsInInassetsMap ids: list){
					String ass_in_nos = ids.getAss_in_no();
					mapVo4.put("ass_in_no", ass_in_nos);
					 List<AssChkDsInInassetsMap>  lists = (List<AssChkDsInInassetsMap>) assChkInMainInassetsMapper.queryExists(mapVo4);
					 if(lists.size() != 0){
						 return "{\"msg\":\"盘点单"+check_no+"已经生成过盈亏单,不能重复生成\",\"state\":\"true\"}";
					 }
				}
				mapVo4.put("ass_card_no", listV.get("ass_card_no"));
				List<AssChkInDetailInassets> lists = (List<AssChkInDetailInassets>) assChkInDetailInassetsMapper.queryExists(mapVo4);
				if(lists.size() != 0){
					 return "{\"msg\":\"盘点单"+check_no+"已经生成过盈亏单,不能重复生成\",\"state\":\"true\"}";
				}
				
			}
			
			
				List<Map<String,Object>> cardMap1 = assChkInDetailInassetsMapper.queryDept(listVo);//盘盈
				for(  Map<String,Object> card : cardMap1){
					i++;
					Map<String, Object> mapVo1 = new HashMap<String, Object>();
					Map<String, Object> mapVo2 = new HashMap<String, Object>();
					//从表数据
						mapVo2.put("hos_id",card.get("hos_id"));
						mapVo2.put("group_id",card.get("group_id"));
						mapVo2.put("copy_code",card.get("copy_code"));
						mapVo2.put("ass_id",card.get("ass_id"));
						mapVo2.put("ass_no",card.get("ass_no"));
						mapVo2.put("dept_id",card.get("dept_id"));
						mapVo2.put("dept_no",card.get("dept_no"));
						card.put("bill_table", "ASS_CHK_IN_MAIN_INASSETS");
						String ass_in_no = assBaseService.getBillNOSeqNo(card);
						assBaseService.updateAssBillNoMaxNo(card);
						
						mapVo2.put("ass_in_no",ass_in_no);
						
						if(card.get("ass_spec") == null || "".equals(card.get("ass_spec"))){
							mapVo2.put("ass_spec","*");
						} else{
							mapVo2.put("ass_spec",card.get("ass_spec"));
						}
						if(card.get("ass_mondl") == null || "".equals(card.get("ass_mondl"))){
							mapVo2.put("ass_model","*");
						} else{
							mapVo2.put("ass_model",card.get("ass_mondl"));
						}
						mapVo2.put("ass_brand","");
						mapVo2.put("unit_code","");
						mapVo2.put("fac_id","");
						mapVo2.put("fac_no","");
					
						mapVo2.put("in_amount",card.get("in_amount"));//入库数量
						if(card.get("price") == null){
							mapVo2.put("price","0");
						}else{
							mapVo2.put("price",card.get("price"));
						}
						if(card.get("depre_money") == null){
							mapVo2.put("add_depre","0");
						}else{
							mapVo2.put("add_depre",card.get("depre_money"));
						}
						if(card.get("add_depre_month") == null){
							mapVo2.put("add_depre_month","");
						}else{
							mapVo2.put("add_depre_month",card.get("add_depre_month"));
						}
	
						if(card.get("cur_money") == null){
							mapVo2.put("cur_money","0");
						}else{
							mapVo2.put("cur_money",card.get("cur_money"));
						}
						if(card.get("fore_money") == null){
							mapVo2.put("fore_money","0");
						}else{
							mapVo2.put("fore_money",card.get("fore_money"));
						}
						mapVo2.put("note","");
						
						listVo2.add(mapVo2);
						
						
						//主表
						mapVo1.put("hos_id",card.get("hos_id"));
						mapVo1.put("group_id",card.get("group_id"));
						mapVo1.put("copy_code",card.get("copy_code"));
						mapVo1.put("ass_id",card.get("ass_id"));
						mapVo1.put("ass_no",card.get("ass_no"));
						mapVo1.put("dept_id",card.get("dept_id"));
						mapVo1.put("dept_no",card.get("dept_no"));
						mapVo1.put("ass_in_no",ass_in_no);
						if(card.get("proj_id") == null){
							mapVo1.put("proj_id","");
						}else{
							mapVo1.put("proj_id",card.get("proj_id"));
						}
						if(card.get("proj_no") == null){
							mapVo1.put("proj_no","");
						}else{
							mapVo1.put("proj_no",card.get("proj_no"));
						}
						if(card.get("ass_purpose") == null){
							mapVo1.put("ass_purpose","");
						}else{
							mapVo1.put("ass_purpose",card.get("ass_purpose"));
						}
						mapVo1.put("store_id","");
						
						mapVo1.put("store_no","");
					    
					 Double price = Double.valueOf(card.get("price").toString());
					 
					 Double in_amount = Double.valueOf(card.get("in_amount").toString());
					
					mapVo1.put("in_money", price * in_amount);//入库金额
					
					mapVo1.put("create_emp",SessionManager.getUserId());
					mapVo1.put("create_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					mapVo1.put("in_date","");
					mapVo1.put("confirm_emp","");
					mapVo1.put("state","0");
					mapVo1.put("note","");
					listVo1.add(mapVo1);	
				}
				
				if(cardMap1.size() != 0){
					//增加关系表 判断时用
					assChkDsInInassetsMapMapper.addBatch(listVo2);
					
					assChkInDetailInassetsMapper.addBatch(listVo2);
					assChkInMainInassetsMapper.addBatch(listVo1);
					
					//生成卡片
					for(  Map<String,Object> card : cardMap1){
						Map<String, Object> mapVo3 = new HashMap<String, Object>();
							mapVo3.put("is_source","0");
							mapVo3.put("hos_id",card.get("hos_id"));
							mapVo3.put("group_id",card.get("group_id"));
							mapVo3.put("copy_code",card.get("copy_code"));
							mapVo3.put("ass_id",card.get("ass_id"));
							mapVo3.put("ass_no",card.get("ass_no"));
							
							
						assChkInMainInassetsService.initAssChkInBatchCardInassets(mapVo3);
					}
				}
				
				List<Map<String,Object>> cardMap2 = assChkRdetailInassetsMapper.queryDeptDept(listVo);//盘亏
				
				for(  Map<String,Object> card1 : cardMap2){
					f++;
					Map<String, Object> mapVo = new HashMap<String, Object>();
					Map<String, Object> mapVo3 = new HashMap<String, Object>();
					//从表
					mapVo.put("hos_id", card1.get("hos_id"));	
					mapVo.put("group_id", card1.get("group_id"));	
					mapVo.put("copy_code", card1.get("copy_code"));	
					mapVo.put("copy_code", card1.get("copy_code"));	
					card1.put("bill_table", "ASS_CHK_R_INASSETS");
					String ass_chk_no = assBaseService.getBillNOSeqNo(card1);
					assBaseService.updateAssBillNoMaxNo(card1);
					mapVo.put("ass_chk_no", ass_chk_no);
					mapVo.put("ass_card_no", card1.get("ass_card_no"));
					if(card1.get("price") == null){
						mapVo.put("price","0");
					}else{
						mapVo.put("price",card1.get("price"));
					}
					
					if(card1.get("fore_money") == null){
						mapVo.put("fore_money","0");
					}else{
						mapVo.put("fore_money",card1.get("fore_money"));
					}
					if(card1.get("cur_money") == null){
						mapVo.put("cur_money","0");
					}else{
						mapVo.put("cur_money",card1.get("cur_money"));
					}
					mapVo.put("now_depre", "0");
					mapVo.put("now_manage_depre", "0");
					mapVo.put("note", "");
					if(card1.get("manage_depre_money") == null){
						mapVo.put("add_manage_depre","0");
					}else{
						mapVo.put("add_manage_depre",card1.get("manage_depre_money"));
					}
					if(card1.get("depre_money") == null){
						mapVo.put("add_depre","0");
					}else{
						mapVo.put("add_depre",card1.get("depre_money"));
					}
					
					listVo3.add(mapVo);
					
					//主表
					mapVo3.put("hos_id", card1.get("hos_id"));	
					mapVo3.put("group_id", card1.get("group_id"));	
					mapVo3.put("copy_code", card1.get("copy_code"));	
					mapVo3.put("copy_code", card1.get("copy_code"));
					mapVo3.put("ass_chk_no",ass_chk_no);
					mapVo3.put("note","");
					mapVo3.put("create_emp",SessionManager.getUserId());
					mapVo3.put("create_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					mapVo3.put("apply_date","");
					mapVo3.put("audit_emp","");
					mapVo3.put("state","0");
					listVo4.add(mapVo3);
					
					
				}
				if(cardMap2.size() != 0){
					assChkRdetailInassetsMapper.addBatch(listVo3);
					assChkRinassetsMapper.addBatch(listVo4);
				}
			
			
					
			return "{\"msg\":\"生成成功.盘盈入库   "+i+"条"+"盘亏记录     "+f+"条\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
}

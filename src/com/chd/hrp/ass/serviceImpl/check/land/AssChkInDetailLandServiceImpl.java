/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.check.land;

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
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.check.land.AssChkInDetailLandMapper;
import com.chd.hrp.ass.dao.check.land.AssChkInMainLandMapper;
import com.chd.hrp.ass.dao.check.land.AssChkRdetailLandMapper;
import com.chd.hrp.ass.dao.check.land.AssChkRlandMapper;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.check.land.AssChkInDetailLand;
import com.chd.hrp.ass.entity.check.land.AssChkInMainLand;
import com.chd.hrp.ass.entity.check.land.AssChkRdetailLand;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.check.land.AssChkInDetailLandService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050701 资产盘盈入库明细(土地)
 * @Table:
 * ASS_CHK_IN_DETAIL_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assChkInDetailLandService")
public class AssChkInDetailLandServiceImpl implements AssChkInDetailLandService {

	private static Logger logger = Logger.getLogger(AssChkInDetailLandServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assChkInDetailLandMapper")
	private final AssChkInDetailLandMapper assChkInDetailLandMapper = null;
	
	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assChkRdetailLandMapper")
	private final AssChkRdetailLandMapper assChkRdetailLandMapper = null;
	
	@Resource(name = "assChkInMainLandMapper")
	private final AssChkInMainLandMapper assChkInMainLandMapper = null;
	
	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;
	
	@Resource(name = "assChkRlandMapper")
	private final AssChkRlandMapper assChkRlandMapper = null;
	/**
	 * @Description 
	 * 添加050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050701 资产盘盈入库明细(土地)
		AssChkInDetailLand assChkInDetailLand = queryByCode(entityMap);

		if (assChkInDetailLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assChkInDetailLandMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assChkInDetailLandMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assChkInDetailLandMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assChkInDetailLandMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assChkInDetailLandMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assChkInDetailLandMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加050701 资产盘盈入库明细(土地)<BR> 
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
		//判断是否存在对象050701 资产盘盈入库明细(土地)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssChkInDetailLand> list = (List<AssChkInDetailLand>)assChkInDetailLandMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assChkInDetailLandMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assChkInDetailLandMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssChkInDetailLand> list = (List<AssChkInDetailLand>)assChkInDetailLandMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssChkInDetailLand> list = (List<AssChkInDetailLand>)assChkInDetailLandMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInDetailLandMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssChkInDetailLand
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInDetailLandMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050701 资产盘盈入库明细(土地)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssChkInDetailLand>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInDetailLandMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByInit(Map<String, Object> mapVo) throws DataAccessException {
		return assChkInDetailLandMapper.queryByInit(mapVo);
	}
	@Override
	public String saveLandInventory(List<Map<String, Object>> listVo)
			throws DataAccessException {
		List<Map<String, Object>> listVo3 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo4 = new ArrayList<Map<String, Object>>();
		
		int i = 0;//盘盈数量
		int f = 0;//盘亏数量
		
		try {
			
			for( Map<String, Object> detailVo : listVo){
				Map<String, Object> mapVo4 = new HashMap<String, Object>();
				mapVo4.put("hos_id",detailVo.get("hos_id"));
				mapVo4.put("group_id",detailVo.get("group_id"));
				mapVo4.put("copy_code",detailVo.get("copy_code"));
				 String check_no = detailVo.get("check_no").toString();
				mapVo4.put("check_no", check_no);
				mapVo4.put("check_plan_no", detailVo.get("check_plan_no"));
				mapVo4.put("ass_ori_card_no", detailVo.get("ass_card_no"));
				
				List<AssCardLand> listss = (List<AssCardLand>) assCardLandMapper.queryExists(mapVo4);
				for( AssCardLand ids: listss){
					String ass_in_nos = ids.getAss_in_no();
					mapVo4.put("ass_in_no", ass_in_nos);
				 List<AssChkInMainLand>  lists = (List<AssChkInMainLand>) assChkInMainLandMapper.queryExists(mapVo4);
					 if(lists.size() != 0){
						 return "{\"msg\":\"盘点单"+check_no+"已经生成过盈亏单,不能重复生成\",\"state\":\"true\"}";
					 }
				}
				 mapVo4.put("ass_card_no", detailVo.get("ass_card_no"));
				List<AssChkRdetailLand> listsss = (List<AssChkRdetailLand>) assChkRdetailLandMapper.queryExists(mapVo4);
				if(listsss.size() != 0 ){
					 return "{\"msg\":\"盘点单"+check_no+"已经生成过盈亏单,不能重复生成\",\"state\":\"true\"}";
				}
			}
			for( Map<String, Object> detailVo : listVo){
				Map<String, Object> mapVo5= new HashMap<String, Object>();
				mapVo5.put("hos_id",detailVo.get("hos_id"));
				mapVo5.put("group_id",detailVo.get("group_id"));
				mapVo5.put("copy_code",detailVo.get("copy_code"));
				 String check_no = detailVo.get("check_no").toString();
				mapVo5.put("check_no", check_no);
				mapVo5.put("check_plan_no", detailVo.get("check_plan_no"));
				
				
			}
						List<Map<String,Object>> list1 = assCardLandMapper.queryStore(listVo);//盘盈
						
						//盘盈
						for( Map<String, Object> listMap : list1){
							i++;
							Map<String, Object> mapVo2 = new HashMap<String, Object>();
							
							mapVo2.put("hos_id", listMap.get("hos_id"));
							mapVo2.put("group_id", listMap.get("group_id"));
							mapVo2.put("copy_code", listMap.get("copy_code"));
							mapVo2.put("ass_id",listMap.get("ass_id"));
							mapVo2.put("ass_no",listMap.get("ass_no"));
							mapVo2.put("store_id",listMap.get("store_id"));
							mapVo2.put("store_no",listMap.get("store_no"));
							if(listMap.get("ass_card_no") == null){
								mapVo2.put("ass_ori_card_no","");
							}else{
								mapVo2.put("ass_ori_card_no",listMap.get("ass_card_no"));
							}
							listMap.put("bill_table", "ASS_CARD_LAND");
							String ass_card_no = assBaseService.getBillNOSeqNo(listMap);
							assBaseService.updateAssBillNoMaxNo(listMap);
							mapVo2.put("ass_card_no",ass_card_no);
							mapVo2.put("ass_card_no_old","0");
							if(listMap.get("gb_code") == null){
								mapVo2.put("gb_code","");
							}else{
								mapVo2.put("gb_code",listMap.get("gb_code"));
							}
							if(listMap.get("ass_amount") == null){
								mapVo2.put("ass_amount","1");
							}else{
								mapVo2.put("ass_amount",listMap.get("ass_amount"));
							}
							if(listMap.get("price") == null){
								mapVo2.put("price",0);
							}else{
								mapVo2.put("price",listMap.get("price"));
							}
							if(listMap.get("depre_money") == null){
								mapVo2.put("depre_money","0");
							}else{
								mapVo2.put("depre_money",listMap.get("depre_money"));
							}
							if(listMap.get("manage_depre_money") == null){
								mapVo2.put("manage_depre_money","0");
							}else{
								mapVo2.put("manage_depre_money",listMap.get("manage_depre_money"));
							}
							
							if(listMap.get("cur_money") == null){
								mapVo2.put("cur_money","0");
							}else{
								mapVo2.put("cur_money",listMap.get("cur_money"));
							}
							if(listMap.get("fore_money") == null){
								mapVo2.put("fore_money","0");
							}else{
								mapVo2.put("fore_money",listMap.get("fore_money"));
							}
							if(listMap.get("buy_type") == null){
								mapVo2.put("buy_type","0");
							}else{
								mapVo2.put("buy_type",listMap.get("buy_type"));
							}
							if(listMap.get("unit_code") == null){
								mapVo2.put("unit_code","0");
							}else{
								mapVo2.put("unit_code",listMap.get("unit_code"));
							}
								mapVo2.put("dispose_type","");
								mapVo2.put("use_state","0"); 
								mapVo2.put("dispose_cost","");
								mapVo2.put("dispose_income ","");
								mapVo2.put("dispose_tax","");
								
							if(listMap.get("is_depr") == null){
								mapVo2.put("is_depr","");
							}else{
								mapVo2.put("is_depr",listMap.get("is_depr"));
							}
							if(listMap.get("depr_method") == null){
								mapVo2.put("depr_method","");
							}else{
								mapVo2.put("depr_method",listMap.get("depr_method"));
							}
							if(listMap.get("acc_depre_amount") == null){
								mapVo2.put("acc_depre_amount","");
							}else{
								mapVo2.put("acc_depre_amount",listMap.get("acc_depre_amount"));
							}
							if(listMap.get("is_manage_depre") == null){
								mapVo2.put("is_manage_depre","");
							}else{
								mapVo2.put("is_manage_depre",listMap.get("is_manage_depre"));
							}
							if(listMap.get("manage_depr_method") == null){
								mapVo2.put("manage_depr_method","");
							}else{
								mapVo2.put("manage_depr_method",listMap.get("manage_depr_method"));
							}
							if(listMap.get("manage_depre_amount") == null){
								mapVo2.put("manage_depre_amount","");
							}else{
								mapVo2.put("manage_depre_amount",listMap.get("manage_depre_amount"));
							}
							if(listMap.get("ven_id") == null){
								mapVo2.put("ven_id","");
							}else{
								mapVo2.put("ven_id",listMap.get("ven_id"));
							}
							if(listMap.get("ven_no") == null){
								mapVo2.put("ven_no","");
							}else{
								mapVo2.put("ven_no",listMap.get("ven_no"));
							}
							if(listMap.get("pact_code") == null){
								mapVo2.put("pact_code","");
							}else{
								mapVo2.put("pact_code",listMap.get("pact_code"));
							}
							if(listMap.get("struct_code") == null){
								mapVo2.put("struct_code","");
							}else{
								mapVo2.put("struct_code",listMap.get("struct_code"));
							}
							
							if(listMap.get("house_area") == null){
								mapVo2.put("house_area","");
							}else{
								mapVo2.put("house_area",listMap.get("house_area"));
							}
							if(listMap.get("use_area") == null){
								mapVo2.put("use_area","");
							}else{
								mapVo2.put("use_area",listMap.get("use_area"));
							}
							if(listMap.get("base_area") == null){
								mapVo2.put("base_area","");
							}else{
								mapVo2.put("base_area",listMap.get("base_area"));
							}
							if(listMap.get("accept_date ") == null){
								mapVo2.put("accept_date","");
							}else{
								mapVo2.put("accept_date",listMap.get("accept_date"));
							}
							if(listMap.get("cert_name") == null){
								mapVo2.put("cert_name","");
							}else{
								mapVo2.put("cert_name",listMap.get("cert_name"));
							}
							if(listMap.get("cert_code") == null){
								mapVo2.put("cert_code","");
							}else{
								mapVo2.put("cert_code",listMap.get("cert_code"));
							}
							
								mapVo2.put("cert_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							
							if(listMap.get("prop_code") == null){
								mapVo2.put("prop_code","");
							}else{
								mapVo2.put("prop_code",listMap.get("prop_code"));
							}
								mapVo2.put("run_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
						
							if(listMap.get("location") == null){
								mapVo2.put("location","");
							}else{
								mapVo2.put("location",listMap.get("location"));
							}	
							if(listMap.get("dept_id") == null){
								mapVo2.put("dept_id","");
							}else{
								mapVo2.put("dept_id",listMap.get("dept_id"));
							}
							if(listMap.get("dept_no") == null){
								mapVo2.put("dept_no","");
							}else{
								mapVo2.put("dept_no",listMap.get("dept_no"));
							}
							if(listMap.get("note") == null){
								mapVo2.put("note","");
							}else{
								mapVo2.put("note",listMap.get("note"));
							}
							listMap.put("bill_table", "ASS_CHK_IN_MAIN_LAND");
							String ass_in_no = assBaseService.getBillNOSeqNo(listMap);
							assBaseService.updateAssBillNoMaxNo(listMap);
								mapVo2.put("ass_in_no",ass_in_no);
								mapVo2.put("in_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							if(listMap.get("is_init") == null){
								mapVo2.put("is_init","");
							}else{
								mapVo2.put("is_init",listMap.get("is_init"));
							}	
							mapVo2.put("dispose_date","");
							mapVo2.put("add_depre_month","");
							mapVo2.put("add_manage_month","");
							mapVo2.put("in_type","03");
							mapVo2.put("is_addin",true);
							assCardLandService.add(mapVo2);
							
						}
					
							
							//盘亏
							List<Map<String,Object>> list2 = assChkRdetailLandMapper.queryStoreStore(listVo);//盘亏
							for(  Map<String,Object> card1 : list2){
								f++;
								Map<String, Object> mapVo = new HashMap<String, Object>();
								Map<String, Object> mapVo3 = new HashMap<String, Object>();
								//从表
								mapVo.put("hos_id", card1.get("hos_id"));	
								mapVo.put("group_id", card1.get("group_id"));	
								mapVo.put("copy_code", card1.get("copy_code"));	
								mapVo.put("copy_code", card1.get("copy_code"));	
								card1.put("bill_table", "ASS_CHK_R_LAND");
								String ass_chk_no = assBaseService.getBillNOSeqNo(card1);
								assBaseService.updateAssBillNoMaxNo(card1);
								mapVo.put("ass_chk_no", ass_chk_no);
								mapVo.put("ass_card_no", card1.get("ass_card_no"));
								if(card1.get("price") == null){
									mapVo.put("price",0);
								}else{
									mapVo.put("price",card1.get("price"));
								}
								
								if(card1.get("fore_money") == null){
									mapVo.put("fore_money",0);
								}else{
									mapVo.put("fore_money",card1.get("fore_money"));
								}
								if(card1.get("cur_money") == null){
									mapVo.put("cur_money",0);
								}else{
									mapVo.put("cur_money",card1.get("cur_money"));
								}
								mapVo.put("now_depre", 0);
								mapVo.put("now_manage_depre", 0);
								mapVo.put("note", "");
								if(card1.get("manage_depre_money") == null){
									mapVo.put("add_manage_depre",0);
								}else{
									mapVo.put("add_manage_depre",card1.get("manage_depre_money"));
								}
								if(card1.get("depre_money") == null){
									mapVo.put("add_depre",0);
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
							if(list2.size() != 0){
								assChkRdetailLandMapper.addBatch(listVo3);
								assChkRlandMapper.addBatch(listVo4);
							}
					
					
				
					return "{\"msg\":\"生成成功.盘盈入库   "+i+"条"+"盘亏记录     "+f+"条\",\"state\":\"true\"}";
				} catch (Exception e) {
		
					logger.error(e.getMessage(), e);
					throw new SysException(e.getMessage());
		
				}
	}
	
}

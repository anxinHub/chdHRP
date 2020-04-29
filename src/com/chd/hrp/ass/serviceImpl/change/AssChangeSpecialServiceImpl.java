/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.change;

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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.change.AssChangeDetailSpecialMapper;
import com.chd.hrp.ass.dao.change.AssChangeSpecialMapper;
import com.chd.hrp.ass.dao.change.source.AssChangeSourceSpecialMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageSpecialMapper;
import com.chd.hrp.ass.dao.resource.AssResourceSpecialMapper;
import com.chd.hrp.ass.entity.change.AssChangeSpecial;
import com.chd.hrp.ass.entity.change.source.AssChangeSourceSpecial;
import com.chd.hrp.ass.entity.resource.AssResourceSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.change.AssChangeSpecialService;
import com.chd.hrp.mat.dao.info.basic.MatPayTermMapper;
import com.chd.hrp.mat.entity.MatPayTerm;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050805 资产原值变动(专用设备)
 * @Table:
 * ASS_Change_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
 
 
@Service("assChangeSpecialService")
public class AssChangeSpecialServiceImpl implements AssChangeSpecialService {

	private static Logger logger = Logger.getLogger(AssChangeSpecialServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assChangeSpecialMapper")
	private final AssChangeSpecialMapper assChangeSpecialMapper = null;
	
	@Resource(name = "assChangeDetailSpecialMapper")
	private final AssChangeDetailSpecialMapper assChangeDetailSpecialMapper = null;
	
	@Resource(name = "assChangeSourceSpecialMapper")
	private final AssChangeSourceSpecialMapper assChangeSourceSpecialMapper = null;
	
	@Resource(name = "assResourceSpecialMapper")
	private final AssResourceSpecialMapper assResourceSpecialMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;
	
	@Resource(name = "assCardSpecialMapper")
	private final AssCardSpecialMapper assCardSpecialMapper = null;
	
	@Resource(name = "assPayStageSpecialMapper")
	private final AssPayStageSpecialMapper assPayStageSpecialMapper = null;
	
	@Resource(name = "matPayTermMapper")
	private final MatPayTermMapper matPayTermMapper = null;
    
	/**
	 * @Description 
	 * 添加050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050805 资产原值变动(专用设备)
		AssChangeSpecial assChangeSpecial = queryByCode(entityMap);

		if (assChangeSpecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assChangeSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050805 资产原值变动(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assChangeSpecialMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assChangeSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050805 资产原值变动(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assChangeSpecialMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assChangeSpecialMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050805 资产原值变动(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			assChangeSourceSpecialMapper.deleteBatch(entityList);
			assChangeDetailSpecialMapper.deleteBatch(entityList);
			assChangeSpecialMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> detSourceList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> sourceList = new ArrayList<Map<String,Object>>();
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("change_no", entityMap.get("change_no"));
    	
    	Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
    	vCreateDateMap.put("group_id",entityMap.get("group_id"));
    	vCreateDateMap.put("hos_id",entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "02");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		vCreateDateMap.put("price_change_where", "true");
		
		
		entityMap.put("state", "0");
		List<AssChangeSpecial> list = (List<AssChangeSpecial>)assChangeSpecialMapper.queryExists(mapVo);
		boolean flag = true;
		try {
			if (list.size()>0) {
				assChangeSpecialMapper.update(entityMap);
			}else{
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_CHANGE_SPECIAL");
				String change_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("change_no", change_no);
				assChangeSpecialMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			
			List<Map<String, Object>> detailList = assChangeDetailSpecialMapper.queryByChangeNoMap(mapVo);
			
			
			for (Map<String, Object> det : detailList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("ass_card_no") == null || "".equals(temp.get("ass_card_no"))) {
						continue;
					}
					if (det.get("ass_card_no").toString().equals(temp.get("ass_card_no").toString())) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					detSourceList.add(det);
				}
			}
			if(detSourceList.size() > 0){
				assChangeSourceSpecialMapper.deleteBatch(detSourceList);
			}
			
			
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				
				detailVo.put("group_id",entityMap.get("group_id"));
				detailVo.put("hos_id",entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("change_no", entityMap.get("change_no"));
				
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
				
				Integer createDateExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vCreateDateMap);
				if(createDateExists == 0){
					flag = false;
					break;
				}
				
				if(detailVo.get("old_price") != null && !detailVo.get("old_price").equals("")){
					detailVo.put("old_price",detailVo.get("old_price")+"");
				}else{
					detailVo.put("old_price","0");
				}
				
				if(detailVo.get("note") != null && !detailVo.get("note").equals("")){
					detailVo.put("note",detailVo.get("note"));
				}else{
					detailVo.put("note","");
				}
				Double price = 0.0;
				Double change_price = 0.0;
				List<Map<String, Object>> existsSource = assChangeSourceSpecialMapper.queryChangeSourceByAssCardNo(detailVo);
				if(existsSource.size() == 0 || existsSource == null){
					List<Map<String, Object>> reSourceList = assResourceSpecialMapper.queryByAssCardNoMap(detailVo);
					for(Map<String, Object> source : reSourceList){
						source.put("change_no", entityMap.get("change_no"));
						source.put("ass_card_no", detailVo.get("ass_card_no"));
						source.put("source_id", source.get("source_id"));
						source.put("old_price", source.get("price"));
						source.put("change_price", "0");
						source.put("price", source.get("price"));
						source.put("note", "");
						price = price + Double.parseDouble(source.get("price").toString());
						sourceList.add(source);
					}
				}else{
					for(Map<String, Object> source : existsSource){
						price = price + Double.parseDouble(source.get("price").toString());
						change_price = change_price + Double.parseDouble(source.get("change_price").toString());
					}
				}
				detailVo.put("price", price + "");
				detailVo.put("change_price", change_price + "");
				listVo.add(detailVo);
			}
			
			if(!flag){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在尚未入账的卡片不能进行变动.\"}";
			}
			
			assChangeDetailSpecialMapper.delete(entityMap);
			assChangeDetailSpecialMapper.addBatch(listVo);
			
			if(sourceList.size() > 0 ){
				assChangeSourceSpecialMapper.deleteBatch(sourceList);
				assChangeSourceSpecialMapper.addBatch(sourceList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_no\":\""+entityMap.get("change_no")+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssChangeSpecial> list = (List<AssChangeSpecial>)assChangeSpecialMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssChangeSpecial> list = (List<AssChangeSpecial>)assChangeSpecialMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assChangeSpecialMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssChangeSpecial
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assChangeSpecialMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050805 资产原值变动(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssChangeSpecial>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assChangeSpecialMapper.queryExists(entityMap);
	}
	@Override
	public String deleteAssChangeSourceSpecial(List<Map<String, Object>> entityMap) {
		try {
			assChangeSourceSpecialMapper.deleteBatch(entityMap);
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",entityMap.get(0).get("group_id"));
			mapVo.put("hos_id",entityMap.get(0).get("hos_id"));
	    	mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
	    	mapVo.put("change_no", entityMap.get(0).get("change_no"));
	    	mapVo.put("ass_card_no", entityMap.get(0).get("ass_card_no"));
	    	Double change_price = 0.0;
			Double price = 0.0;
			List<Map<String, Object>> listMap = assChangeSourceSpecialMapper.queryChangeSourceByAssCardNo(mapVo);
			for(Map<String, Object> map : listMap){
				change_price = change_price + Double.parseDouble(map.get("change_price").toString());
				price = price + Double.parseDouble(map.get("price").toString());
			}
			mapVo.put("change_price", change_price + "");
			mapVo.put("price", price + "");
			
			assChangeDetailSpecialMapper.updatePriceBySource(mapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"change_price\":\""+change_price+"\",\"price\":\""+price+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
	}
	@Override
	public String queryAssChangeSourceSpecial(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssChangeSourceSpecial> list = (List<AssChangeSourceSpecial>)assChangeSourceSpecialMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssChangeSourceSpecial> list = (List<AssChangeSourceSpecial>)assChangeSourceSpecialMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String saveAssChangeSourceSpecial(Map<String, Object> entityMap) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		Double change_price = 0.0;
		Double price = 0.0;
		try {
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("source_id") == null || "".equals(detailVo.get("source_id"))) {
					continue;
				}
				detailVo.put("group_id",entityMap.get("group_id"));
				detailVo.put("hos_id",entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("change_no", entityMap.get("change_no"));
				detailVo.put("ass_card_no", entityMap.get("ass_card_no"));
				detailVo.put("source_id", String.valueOf(detailVo.get("source_id")));
				
				if(detailVo.get("old_price") != null && !detailVo.get("old_price").equals("")){
					detailVo.put("old_price",detailVo.get("old_price")+"");
				}else{
					detailVo.put("old_price","0");
				}
				
				if(detailVo.get("change_price") != null && !detailVo.get("change_price").equals("")){
					change_price = change_price + Double.parseDouble(detailVo.get("change_price").toString());
					detailVo.put("change_price",detailVo.get("change_price")+"");
				}else{
					detailVo.put("change_price","0");
				}
				
				if(detailVo.get("price") != null && !detailVo.get("price").equals("")){
					price = price + Double.parseDouble(detailVo.get("price").toString());
					detailVo.put("price",detailVo.get("price")+"");
				}else{
					detailVo.put("price","0");
				}
				
				if(detailVo.get("note") != null && !detailVo.get("note").equals("")){
					detailVo.put("note",detailVo.get("note"));
				}else{
					detailVo.put("note","");
				}
				
				listVo.add(detailVo);
			}
			
			assChangeSourceSpecialMapper.delete(entityMap);
			assChangeSourceSpecialMapper.addBatch(listVo);
			
			entityMap.put("change_price", change_price + "");
			entityMap.put("price", price + "");
			
			assChangeDetailSpecialMapper.updatePriceBySource(entityMap);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_price\":\""+change_price+"\",\"price\":\""+price+"\"}";
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		List<Map<String, Object>> cardList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> reSourceAddList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> reSourceUpdateList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> payStageListVo = new ArrayList<Map<String, Object>>();
		MatPayTerm matPayTerm = null;
		Map<String,Object> payTermMap = new HashMap<String, Object>();
		payTermMap.put("group_id", SessionManager.getGroupId());
		payTermMap.put("hos_id", SessionManager.getHosId());
		payTermMap.put("copy_code", SessionManager.getCopyCode());
		if(matPayTermMapper.queryPayTerm(payTermMap) != null && matPayTermMapper.queryPayTerm(payTermMap).size() > 0){
			matPayTerm = (MatPayTerm) matPayTermMapper.queryPayTerm(payTermMap).get(0);
		}else{
			matPayTerm = new MatPayTerm();
			matPayTerm.setPay_term_id((long)1);
		}
		
		try {
			for(Map<String, Object> map : entityMap){
				List<Map<String,Object>> detailMap = assChangeDetailSpecialMapper.queryByChangeNoMap(map);
				
				AssChangeSpecial assChangeSpecial = assChangeSpecialMapper.queryByCode(map);
				
				if(detailMap == null ||detailMap.size() == 0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"warn\":\"存在没有明细数据的单据,不能确认.\"}";
				}
				for(Map<String,Object> detail : detailMap){
					
					
					List<Map<String,Object>> sourceMap = assChangeSourceSpecialMapper.queryChangeSourceByAssCardNo(detail);
					if(sourceMap == null ||sourceMap.size() == 0){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return "{\"warn\":\"存在没有资金来源的单据,不能确认.\"}";
					}
					cardList.add(detail);
					
					for(Map<String,Object> source : sourceMap){
						AssResourceSpecial assResourceSpecial = assResourceSpecialMapper.queryByCode(source);
						if(assResourceSpecial != null){
							reSourceUpdateList.add(source);
						}else{
							reSourceAddList.add(source);
						}
					}
					Map<String,Object> payMap = new HashMap<String, Object>();
					payMap.put("group_id", SessionManager.getGroupId());
					payMap.put("hos_id", SessionManager.getHosId());
					payMap.put("copy_code", SessionManager.getCopyCode());
					payMap.put("ass_card_no", detail.get("ass_card_no"));
					payMap.put("stage_no", assPayStageSpecialMapper.queryStageSpecialMaxNo(detail) + 1);
					payMap.put("stage_name", "原值变动");
					payMap.put("pay_term_id", matPayTerm.getPay_term_id());
					payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					payMap.put("pay_plan_percent", "0");
					payMap.put("pay_plan_money", detail.get("change_price").toString());
					payMap.put("pay_money", "0");
					payMap.put("unpay_money", detail.get("change_price").toString());
					payMap.put("ven_id", assChangeSpecial.getVen_id() == null ? "" :assChangeSpecial.getVen_id());
					payMap.put("ven_no", assChangeSpecial.getVen_no() == null ? "" :assChangeSpecial.getVen_no());
					payMap.put("note", "原值变动");
					payMap.put("is_pre", "0");
					payMap.put("bill_money", "0");
					payMap.put("unbill_money", detail.get("change_price").toString());
					payStageListVo.add(payMap);
				}
			}
			
			assChangeSpecialMapper.updateConfirm(entityMap);//更新主表状态
			assCardSpecialMapper.updatePriceConfirm(cardList);//更新卡片原值
			if(reSourceUpdateList.size() > 0){
				assResourceSpecialMapper.updateBatchByChange(reSourceUpdateList);
			}
			if(reSourceAddList.size() > 0){
				assResourceSpecialMapper.addBatchByChange(reSourceAddList);
			}
			if(payStageListVo.size() > 0){
				assPayStageSpecialMapper.addBatch(payStageListVo);
			}
			
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
//	查询未确认数据单号  打印专用
	@Override
	public List<String> queryState(Map<String, Object> entityMap) throws DataAccessException {
		 return assChangeSpecialMapper.queryState(entityMap);
		  
	}
	
	//出库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public Map<String,Object> assChangeSpecialByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AssChangeSpecialMapper assChangeSpecialMapper = (AssChangeSpecialMapper)context.getBean("assChangeSpecialMapper");
		
		
		if("1".equals(String.valueOf(entityMap.get("p_num")))){
			//查询移库主表批量
			List<Map<String,Object>> map = assChangeSpecialMapper.queryAssChangeSpecialPrintTemlateByMainBatch(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assChangeSpecialMapper.queryAssChangeSpecialPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}else{
			//查询移库主表
			Map<String,Object> map = assChangeSpecialMapper.queryAssChangeSpecialPrintTemlateByMain(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assChangeSpecialMapper.queryAssChangeSpecialPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}
		
	}
	@Override
	public String updateAssChangeMainBillNo(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			
			assChangeSpecialMapper.updateAssChangeMainBillNo(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
}

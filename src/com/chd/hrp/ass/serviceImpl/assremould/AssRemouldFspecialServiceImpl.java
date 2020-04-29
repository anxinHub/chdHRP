/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.assremould;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldFdetailSpecialMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldFsourceSpecialMapper;
import com.chd.hrp.ass.dao.assremould.AssRemouldFspecialMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.resource.AssResourceSpecialMapper;
import com.chd.hrp.ass.entity.assremould.AssRemouldFdetailSpecial;
import com.chd.hrp.ass.entity.assremould.AssRemouldFsourceSpecial;
import com.chd.hrp.ass.entity.assremould.AssRemouldFspecial;
import com.chd.hrp.ass.entity.back.AssBackSpecial;
import com.chd.hrp.ass.entity.change.AssChangeSpecial;
import com.chd.hrp.ass.entity.resource.AssResourceSpecial;
import com.chd.hrp.ass.service.assremould.AssRemouldFspecialService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050805 资产改造竣工(专用设备)
 * @Table:
 * ASS_REMOULD_F_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assRemouldFspecialService")
public class AssRemouldFspecialServiceImpl implements AssRemouldFspecialService {

	private static Logger logger = Logger.getLogger(AssRemouldFspecialServiceImpl.class);
	//引入DAO操作   主表
	@Resource(name = "assRemouldFspecialMapper")
	private final AssRemouldFspecialMapper assRemouldFspecialMapper = null;
	//明细表
	@Resource(name = "assRemouldFdetailSpecialMapper")
	private final AssRemouldFdetailSpecialMapper assRemouldFdetailSpecialMapper = null;
	//资金来源表
	@Resource(name = "assRemouldFsourceSpecialMapper")
	private final AssRemouldFsourceSpecialMapper assRemouldFsourceSpecialMapper = null;
	@Resource(name = "assResourceSpecialMapper")
	private final AssResourceSpecialMapper assResourceSpecialMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;
	
	@Resource(name = "assCardSpecialMapper")
	private final AssCardSpecialMapper assCardSpecialMapper = null;
	/**
	 * @Description 
	 * 添加050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050805 资产改造竣工(专用设备)
		AssRemouldFspecial assRemouldFspecial = queryByCode(entityMap);

		if (assRemouldFspecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assRemouldFspecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assRemouldFspecialMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assRemouldFspecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assRemouldFspecialMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			int state = assRemouldFspecialMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			assRemouldFsourceSpecialMapper.deleteBatch(entityList);
			assRemouldFdetailSpecialMapper.deleteBatch(entityList);
			assRemouldFspecialMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); //获得事务状态

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSourceVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("fcord_no", entityMap.get("fcord_no"));
		
		
		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "02");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		
		Map<String, Object> vStoreMap = new HashMap<String, Object>();
		vStoreMap.put("group_id", entityMap.get("group_id"));
		vStoreMap.put("hos_id", entityMap.get("hos_id"));
		vStoreMap.put("copy_code", entityMap.get("copy_code"));
		vStoreMap.put("ass_nature", "02");
		vStoreMap.put("use_state", "1,2,3,4,5");
		vStoreMap.put("store_id", entityMap.get("store_id"));
		vStoreMap.put("store_no", entityMap.get("store_no"));
		
	/*	Map<String, Object> vVenMap = new HashMap<String, Object>();
		vVenMap.put("group_id", entityMap.get("group_id"));
		vVenMap.put("hos_id", entityMap.get("hos_id"));
		vVenMap.put("copy_code", entityMap.get("copy_code"));
		vVenMap.put("ass_nature", "02");
		vVenMap.put("use_state", "1,2,3,4,5");
		vVenMap.put("ven_id", entityMap.get("ven_id"));
		vVenMap.put("ven_no", entityMap.get("ven_no"));*/
		
		entityMap.put("state", "0");
		entityMap.put("create_emp", SessionManager.getUserId());
		entityMap.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		List<AssBackSpecial> list = (List<AssBackSpecial>) assRemouldFspecialMapper.queryExists(mapVo);
		try {
			boolean flag = true;
			boolean flag1 = true;
		/*	boolean flag2 = true;*/
			if (list.size() > 0) {
				assRemouldFspecialMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_REMOULD_F_SPECIAL");
				String fcord_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("fcord_no", fcord_no);
				assRemouldFspecialMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			Double back_money = 0.0;
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("ass_card_no", detailVo.get("ass_card_no").toString());
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
				vStoreMap.put("ass_card_no", detailVo.get("ass_card_no"));
				//vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
				entityMap.put("ass_card_no", detailVo.get("ass_card_no"));
				
				
			/*	Integer venExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vVenMap);
				if(venExists == 0){
					flag2 = false;
					break;
				}*/
				Integer createDateExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vCreateDateMap);
				if(createDateExists == 0){
					flag = false;
					break;
				}
				
				
				String create_date = entityMap.get("create_date").toString();

				detailVo.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

				detailVo.put("ass_month",create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

				detailVo.put("ass_naturs", "02");

				String results = collectAssBackSpecial(detailVo);

				JSONObject jsonObj = JSONObject.parseObject(results);

				if (jsonObj.containsKey("msg")) {
					transactionManager.rollback(status);
					jsonObj.put("warn", jsonObj.get("msg"));
					jsonObj.remove("msg");
					return jsonObj.toJSONString();
				}

				JSONArray cardArray = JSONArray.parseArray(jsonObj.get("Card").toString());

				JSONObject cardObj = JSONObject.parseObject(cardArray.get(0).toString());

				detailVo.put("fcord_no", entityMap.get("fcord_no"));

				detailVo.put("price", detailVo.get("price"));

				back_money = back_money + Double.parseDouble(detailVo.get("price").toString());

				detailVo.put("now_depre", cardObj.get("NowDepre"));

				detailVo.put("now_manage_depre", cardObj.get("NowManageDepre"));

				detailVo.put("add_depre",cardObj.get("AddDepre"));

				detailVo.put("add_manage_depre", cardObj.get("AddManageDepre"));

				detailVo.put("cur_money", cardObj.get("Cur"));

				detailVo.put("fore_money", cardObj.get("Fore"));
				
				detailVo.put("change_price",detailVo.get("change_price"));
				
				detailVo.put("new_price", Double.parseDouble(detailVo.get("cur_money").toString())+Double.parseDouble(detailVo.get("fore_money").toString()));
				
				
				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}
				//存储过程取出该卡片的资金来源,改资金来源是页面上修改后的卡片
				JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());
				//查询该卡片是否存在明细数据
				List<Map<String,Object>> detailLiSt= assRemouldFdetailSpecialMapper.queryByFcordNo(detailVo);
					for (Map<String,Object> det : detailLiSt) {
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
							assRemouldFsourceSpecialMapper.delete(det);
						}
					}
					List<Map<String,Object>> sourceLiSt=assRemouldFsourceSpecialMapper.queryRemouldSourceByAssCardNo(detailVo);
					if(sourceLiSt.size()==0 ){
						for (int i = 0; i < sourceArray.size(); i++) {
							JSONObject job = sourceArray.getJSONObject(i);Map<String, Object> mapSource = new HashMap<String, Object>();
								mapSource.put("group_id", entityMap.get("group_id"));
								mapSource.put("hos_id", entityMap.get("hos_id"));
								mapSource.put("copy_code", entityMap.get("copy_code"));
								mapSource.put("fcord_no", entityMap.get("fcord_no"));
								mapSource.put("ass_card_no", detailVo.get("ass_card_no"));
								mapSource.put("source_id", job.get("ID"));
								mapSource.put("price", detailVo.get("price"));
								mapSource.put("now_depre", job.get("NowDepre"));
								mapSource.put("add_depre", job.get("AddDepre"));
								mapSource.put("cur_money", job.get("Cur"));
								mapSource.put("fore_money", job.get("Fore"));
								mapSource.put("change_price",0);
								mapSource.put("new_price", Double.parseDouble(mapSource.get("cur_money").toString())+Double.parseDouble(mapSource.get("fore_money").toString()));
								mapSource.put("now_manage_depre", job.get("NowManageDepre"));
								mapSource.put("add_manage_depre", job.get("AddManageDepre"));
							if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
								mapSource.put("note", detailVo.get("note"));
							} else {
								mapSource.put("note", "");
							}
							listSourceVo.add(mapSource);
						}
					}
						
						
					
				listVo.add(detailVo);
			}
			
			if(!flag){
				transactionManager.rollback(status);
				return "{\"warn\":\"存在尚未入账的卡片不能进行改造.\"}";
			}
			
		/*	if(!flag2){
				transactionManager.rollback(status);
				return "{\"warn\":\"存在非本供应商的卡片,不能退货.\"}";
			}
			*/
			
			assRemouldFdetailSpecialMapper.delete(entityMap);//明细表
			assRemouldFdetailSpecialMapper.addBatch(listVo);//明细表
			if (listSourceVo.size() > 0) {
				assRemouldFsourceSpecialMapper.deleteBatch(listSourceVo);
				assRemouldFsourceSpecialMapper.addBatch(listSourceVo);//资金来源表
				
			}
			transactionManager.commit(status);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"fcord_no\":\"" + entityMap.get("fcord_no").toString()
					+ "\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}
	
	
	public String collectAssBackSpecial(Map<String, Object> entityMap) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		assRemouldFspecialMapper.collectAssBackSpecial(entityMap);

		String ass_AppCode = entityMap.get("ass_AppCode").toString();
		String ass_ErrTxt = "";
		if (entityMap.get("ass_ErrTxt") != null && !entityMap.get("ass_ErrTxt").equals("")) {
			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();
		}

		if ("0".equals(ass_AppCode)) {
			// 计算成功！提交事务
			transactionManager.commit(status);
			return entityMap.get("ass_json_str").toString();

		} else if ("-1".equals(ass_AppCode) || "100".equals(ass_AppCode)) {
			// 计算失败！回滚事务
			transactionManager.rollback(status);

			return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";
		}

		if (!"".equals(entityMap.get("ass_ErrTxt").toString()) && null != entityMap.get("ass_ErrTxt").toString()) {

			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();

		}
		return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";

	}
	
	/**
	 * @Description 
	 * 查询结果集050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssRemouldFspecial> list = (List<AssRemouldFspecial>)assRemouldFspecialMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRemouldFspecial> list = (List<AssRemouldFspecial>)assRemouldFspecialMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assRemouldFspecialMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRemouldFspecial
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assRemouldFspecialMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050805 资产改造竣工(专用设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRemouldFspecial>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assRemouldFspecialMapper.queryExists(entityMap);
	}
	@Override
	public String queryAssRemouldFSourceSpecial(Map<String, Object> entityMap) {
		List<Map<String,Object>> ListVo = assRemouldFsourceSpecialMapper.queryAssRemouldFSourceSpecial(entityMap);

		return ChdJson.toJson(ListVo);
	}
	@Override
	public String queryAssRemouldFDetailSpecial(Map<String, Object> mapVo) {
				

		List<Map<String,Object>> ListVo = assRemouldFdetailSpecialMapper.queryAssRemouldFDetailSpecial(mapVo);

		return ChdJson.toJson(ListVo);
	}
	@Override
	public String saveAssRemouldFSourceSpecial(Map<String, Object> mapVo) {
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		Double change_price = 0.0;
		try {
			for (Map<String, Object> map : detail) {
				if(map.get("source_id")==null || ("").equals(map.get("source_id")) ){
					continue;
				}
				
				if(map.get("change_price") != null && !"".equals(map.get("change_price").toString())){
					change_price=change_price+Double.parseDouble(map.get("change_price").toString());
				}
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("ass_card_no", mapVo.get("ass_card_no"));
				map.put("fcord_no", mapVo.get("fcord_no"));
				Map<String, Object> sourceMap=assRemouldFsourceSpecialMapper.queryRemouldSourceByAssSourceId(map);
				if(sourceMap==null){
					assRemouldFsourceSpecialMapper.add(map);
				}else{
					assRemouldFsourceSpecialMapper.update(map);
				}
			}
			mapVo.put("change_price", change_price.toString());
			assRemouldFdetailSpecialMapper.update(mapVo);
			  
			return"{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_price\":\"" +change_price+ "\"}";
		} catch (NumberFormatException e) {
			throw new SysException(e);
		} 
	}
	@Override
	public String deleteAssRemouldFDetailspecial(List<Map<String, Object>> listVo) {
		assRemouldFsourceSpecialMapper.deleteBatch(listVo);
		assRemouldFdetailSpecialMapper.deleteBatchByCardNo(listVo);
		return "{\"msg\":\"成功.\",\"state\":\"true\"}";
	}
	@Override
	public String deleteAssRemouldFSourceSpecial(List<Map<String, Object>> listVo) {
		Double change_price = 0.0;
		Map<String,Object> mapVo = new HashMap<String,Object>();
		for (Map<String, Object> map : listVo) {
			Map<String,Object> sourceMap =assRemouldFsourceSpecialMapper.queryRemouldSourceByAssSourceId(map);
			change_price=change_price+Double.parseDouble(sourceMap.get("change_price").toString());
			mapVo.put("group_id", sourceMap.get("group_id"));
			mapVo.put("hos_id", sourceMap.get("hos_id"));
			mapVo.put("copy_code", sourceMap.get("copy_code"));
			mapVo.put("fcord_no", sourceMap.get("fcord_no"));
			mapVo.put("ass_card_no", sourceMap.get("ass_card_no"));
		}
		Map<String, Object> detailMap =assRemouldFdetailSpecialMapper.queryByAssCardNoMap(mapVo);
			mapVo.put("change_price", Double.parseDouble(detailMap.get("change_price").toString())-change_price);
		assRemouldFsourceSpecialMapper.deleteBatchBySourceId(listVo);
		assRemouldFdetailSpecialMapper.updatechangePriceByCardNo(mapVo);
		Double newchange_price=Double.parseDouble(detailMap.get("change_price").toString())-change_price;
		return "{\"msg\":\"成功.\",\"state\":\"true\",\"change_price\":\"" +newchange_price+ "\"}";
	}
	//改造竣工确认
	@Override
	public String updateConfirmAssRemouldFspecial(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo) {
	
	try {
		assRemouldFspecialMapper.updateConfirmAssRemouldFspecial(listVo);
		assCardSpecialMapper.updateRemouldFSpecialConfirm(listCardVo);
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		throw new SysException(e);
	}}
}

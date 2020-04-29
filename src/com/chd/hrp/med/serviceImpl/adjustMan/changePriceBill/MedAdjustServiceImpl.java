package com.chd.hrp.med.serviceImpl.adjustMan.changePriceBill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.adjustMan.changePriceBill.MedAdjustMapper;
import com.chd.hrp.med.dao.info.basic.MedInvDictMapper;
import com.chd.hrp.med.service.adjustMan.changePriceBill.MedAdjustService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 04114 调价单
 * @Table:
 * MED_ADJUST_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medAdjustService")
public class MedAdjustServiceImpl implements MedAdjustService {
	
	private static Logger logger = Logger.getLogger(MedAdjustServiceImpl.class);
	
	@Resource(name = "medAdjustMapper")
	private MedAdjustMapper medAdjustMapper = null ;
	
	@Resource(name = "medInvDictMapper")
	private MedInvDictMapper medInvDictMapper = null ;
	
	
	/**
	 * @Description 
	 * 调价单<BR>添加
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
		
			if(entityMap.get("group_id") == null || "".equals(entityMap.get("group_id").toString())){
				
				return "{\"error\":\"保存失败 group_id不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("hos_id") == null || "".equals(entityMap.get("hos_id").toString())){
				
				return "{\"error\":\"保存失败 hos_id不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("copy_code") == null || "".equals(entityMap.get("copy_code").toString())){
				
				return "{\"error\":\"保存失败 copy_code不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("create_date") == null || "".equals(entityMap.get("create_date").toString())){
				
				return "{\"error\":\"保存失败 create_date不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("allData") == null || "".equals(entityMap.get("allData").toString())){
				
				return "{\"error\":\"保存失败 allData不能为空! 错误编码 add\"}";
			}
			
			String adjust_code;
			
			String create_date = entityMap.get("create_date").toString();
			
			entityMap.put("create_year",create_date.substring(0, 4));
			
			entityMap.put("create_month", create_date.substring(5, 7));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			int flag =  medAdjustMapper.queryIsExists(entityMap);
			
			boolean codeFlag;
			
				if(flag > 0){
					
					String maxCode = medAdjustMapper.queryMaxCode(entityMap);
					
					entityMap.put("max_code", (Integer.parseInt(maxCode))+1);
					
					//更新单据号表
					int state1 = medAdjustMapper.updateMedNoMatch(entityMap);
					
					String code = "00000" + entityMap.get("max_code").toString();
					
					code = code.substring(code.length()-5,code.length());
					
					adjust_code = "TJ"+entityMap.get("create_year")+entityMap.get("create_month")+code;
					
					entityMap.put("adjust_code", adjust_code);//计划单号
					
					codeFlag = true;
					
				}else{
				
					adjust_code  = "TJ"+entityMap.get("create_year")+entityMap.get("create_month")+"00001";
					
					entityMap.put("adjust_code", adjust_code);
					
					codeFlag = false;
					
				}
				
				entityMap.put("maker", SessionManager.getUserId());//制单人取当前用户id
				
				entityMap.put("make_date", sdf.format(new Date()));
				
				entityMap.put("state", 1);
				
				entityMap.put("checker","");
				
				entityMap.put("adjust_date", "");
				
				medAdjustMapper.add(entityMap);
				
				List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合
				
				JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据
				
				if(allDataJson == null){
					
					new SysException("{\"error\":\"操作失败\"}");
					//return "{\"error\":\"保存失败 allDataJson不能为空! 错误编码 add\"}";
				}
				
				Iterator iterator = allDataJson.iterator();
				
				while(iterator.hasNext()){
					
					Map<String,Object> map = defaultValue();
					
					JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
					
					if(jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						
						map.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));
						
						map.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));
						
						map.put("copy_code", entityMap.get("copy_code"));
						
						if(entityMap.get("adjust_id") == null || "".equals(entityMap.get("adjust_id"))){
							
							new SysException("{\"error\":\"操作失败\"}");
							//return "{\"error\":\"保存失败 adjust_id不能为空! 错误编码 add\"}";
						}
						
						map.put("adjust_id", entityMap.get("adjust_id"));
						
						if(jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
							
							map.put("inv_id",jsonObj.get("inv_id"));
						}
						
						if(jsonObj.get("inv_no") != null && !"".equals(jsonObj.get("inv_no"))){
							
							map.put("inv_no", jsonObj.get("inv_no"));
						}
						
						if(jsonObj.get("old_price") != null && !"".equals(jsonObj.get("old_price"))){
							
							map.put("old_price", jsonObj.get("old_price"));
						}
						
						if(jsonObj.get("new_price") != null && !"".equals(jsonObj.get("new_price"))){
							
							map.put("new_price",jsonObj.get("new_price"));
						}
						
						if(jsonObj.get("old_sell_price") != null && !"".equals(jsonObj.get("old_sell_price"))){
							
							map.put("old_sell_price", jsonObj.get("old_sell_price"));
						}
						
						if(jsonObj.get("new_sell_price") != null && !"".equals(jsonObj.get("new_sell_price"))){
							
							map.put("new_sell_price",jsonObj.get("new_sell_price"));
						}
						
						if(jsonObj.get("adjust_reason") != null && !"".equals(jsonObj.get("adjust_reason"))){
							
							map.put("adjust_reason", jsonObj.get("adjust_reason"));
						}
						
						allDataList.add(map);
					}
					
				}
				
				if(allDataList.size() > 0 ){
						
					medAdjustMapper.addBatchMedAdjustDetail(allDataList);//保存调价单明细数据
						
				}
					
				if(codeFlag == false){
						
					//更新单据序号表
					entityMap.put("prefixe", "TJ");
					entityMap.put("table_code", "MED_ADJUST_MAIN");
					entityMap.put("max_no", 1);
					int state = medAdjustMapper.addMedNoMedch(entityMap);
						
				}

				return "{\"msg1\":\"保存成功.\",\"state\":\"true\",\"update_para\":\""+entityMap.get("adjust_id").toString()+",1\"}";
				
			} catch (Exception e) {
				
				logger.error(e.getMessage());
				
				throw new SysException("{\"error\":\"操作失败\"}");
			}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 调价单 更新调价单主表及明细表数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		
		if(entityMap.get("group_id") == null || "".equals(entityMap.get("group_id").toString())){
			
			return "{\"error\":\"保存失败 group_id不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("hos_id") == null || "".equals(entityMap.get("hos_id").toString())){
			
			return "{\"error\":\"保存失败 hos_id不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("copy_code") == null || "".equals(entityMap.get("copy_code").toString())){
			
			return "{\"error\":\"保存失败 copy_code不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("create_date") == null || "".equals(entityMap.get("create_date").toString())){
			
			return "{\"error\":\"保存失败 create_date不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("allData") == null || "".equals(entityMap.get("allData").toString())){
			
			return "{\"error\":\"保存失败 allData不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("adjust_id") == null || "".equals(entityMap.get("adjust_id").toString())){
			
			return "{\"error\":\"保存失败 adjust_id不能为空! 错误编码 update\"}";
		}
			
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
			
		List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合
			
		JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据
			
		if(allDataJson == null ){
				
			return "{\"error\":\"保存失败 allDataJson不能为空! 错误编码 update\"}";
		}
			
		Iterator iterator = allDataJson.iterator();
			
		while(iterator.hasNext()){
				
			Map<String,Object> map = defaultDetailValue();
				
			JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				
			if(jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
				
				map.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));
				
				map.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));
					
				map.put("copy_code", entityMap.get("copy_code"));
					
				map.put("adjust_id", entityMap.get("adjust_id"));
					
				if(jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
					map.put("inv_id",jsonObj.get("inv_id"));
				}
					
				if(jsonObj.get("inv_no") != null && !"".equals(jsonObj.get("inv_no"))){
					map.put("inv_no", jsonObj.get("inv_no"));
				}
				
				if(jsonObj.get("old_price") != null && !"".equals(jsonObj.get("old_price"))){
					map.put("old_price", jsonObj.get("old_price"));
				}else{
					map.put("old_price", 0);
				}
					
				if(jsonObj.get("new_price") != null && !"".equals(jsonObj.get("new_price"))){
					map.put("new_price",jsonObj.get("new_price"));
				}else{
					map.put("new_price",0);
				}
					
				if(jsonObj.get("old_sell_price") != null && !"".equals(jsonObj.get("old_sell_price"))){
					map.put("old_sell_price", jsonObj.get("old_sell_price"));
				}else{
					map.put("old_sell_price", 0);
				}
					
				if(jsonObj.get("new_sell_price") != null && !"".equals(jsonObj.get("new_sell_price"))){
					map.put("new_sell_price",jsonObj.get("new_sell_price"));
				}else{
					map.put("new_sell_price",0);
				}
					
				if(jsonObj.get("adjust_reason") != null){
					map.put("adjust_reason",jsonObj.get("adjust_reason"));
				}else{
					map.put("adjust_reason","");
				}
					
				allDataList.add(map);
			}
		}
			
		
		try {
			
			medAdjustMapper.update(entityMap);//修改主表数据
			
			list.add(entityMap);
			
			if(list.size() > 0 ){
				
				medAdjustMapper.deleteBatchMedAdjustDetail(list);//删除调价单明细数据
			}
				
			if(allDataList.size() > 0 ){//有数据、就添加
					
				medAdjustMapper.addBatchMedAdjustDetail(allDataList);//添加明细数据
					
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}
		
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 调价单<BR>批量删除 调价单
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		if(entityMap == null || entityMap.size() <=0){
			
			return "{\"error\":\"删除失败 entityMap不能为空! 错误编码 deleteBatch\"}";
		}
		
		for(Map<String,Object> map:entityMap){
				
			if(map.get("group_id") == null || "".equals(map.get("group_id").toString())){
					
				return "{\"error\":\"删除失败 group_id不能为空! 错误编码 deleteBatch\"}";
			}
				
			if(map.get("hos_id") == null || "".equals(map.get("hos_id").toString())){
					
				return "{\"error\":\"删除失败 hos_id不能为空! 错误编码 deleteBatch\"}";
			}
				
			if(map.get("copy_code") == null || "".equals(map.get("copy_code").toString())){
					
				return "{\"error\":\"删除失败 copy_code不能为空! 错误编码 deleteBatch\"}";
			}
				
			if(map.get("adjust_id") == null || "".equals(map.get("adjust_id").toString())){
					
				return "{\"error\":\"删除失败 adjust_id不能为空! 错误编码 deleteBatch\"}";
			}
		}
			
		try {
			
			medAdjustMapper.deleteBatchMedAdjustDetail(entityMap);//批量删除调价单明细
			
			medAdjustMapper.deleteBatch(entityMap);//批量删除调价单主表数据
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}
			
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 调价单<BR>查询
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = medAdjustMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = medAdjustMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 调价单<BR>按调价单ID查询 
	 * @param entityMap
	 * @return <T>
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)throws DataAccessException {

		return medAdjustMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();

		mapDetailVo.put("note","");
				
		mapDetailVo.put("checker", "");
		
		mapDetailVo.put("adjust_reason","");
				
		return mapDetailVo;
	}
	
	/**
	 * @Description 
	 * 调价单<BR>审核 修改状态
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedAdjustState(List<Map<String, Object>> entityMap) throws DataAccessException {
		StringBuffer inv_nos = new StringBuffer();
		Map<String,Object> vMap = new HashMap<String,Object>();
		try {
			List<Map<String,Object>> list = (List<Map<String, Object>>) medAdjustMapper.queryBatchMedAdjustDetailByCode(entityMap);
			
			medAdjustMapper.updateMedAdjustState(entityMap);//修改主表审核状态
			
			//medAdjustMapper.updateBatchMedInvSellPrice(list);//修改材料表零售价
			
			//medAdjustMapper.updateBatchMedInvDictSellPrice(list);//修改材料变更表零售价
			
			medAdjustMapper.updateBatchMedInvPlanPrice(list);//修改材料表计划价
			
			List<Map<String,Object>> invListNew = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map : list){
				Map<String,Object> invMap = medInvDictMapper.queryByCode(map); 
				inv_nos.append(invMap.get("inv_no")).append(",");
				invMap.put("is_stop", "0");
				invMap.put("use_state", "1");
				invMap.put("plan_price", map.get("new_price"));
				invMap.put("sell_price", map.get("new_sell_price"));
				if(map.get("adjust_reason") != null && !"".equals(map.get("adjust_reason"))){
					invMap.put("change_note", map.get("adjust_reason"));
				}else{
					invMap.put("change_note", "调价变更");
				}
				invMap.put("change_date", new Date());
				invMap.put("change_user", SessionManager.getUserId());
				invListNew.add(invMap);
			}
			
			// 先停用材料 新添加一条记录
			if(inv_nos.toString().length() > 0){
				vMap.put("inv_nos", inv_nos.substring(0, inv_nos.length()-1));
				vMap.put("group_id", SessionManager.getGroupId());
				vMap.put("hos_id", SessionManager.getHosId());
				vMap.put("copy_code", SessionManager.getCopyCode());
				//vMap.put("change_date", new Date());
				//vMap.put("change_user", SessionManager.getUserId());
				//vMap.put("change_note", "材料单价变更停用");
				vMap.put("is_stop", "1");
				medAdjustMapper.updateBatchInvIsStop(vMap);
			}
			
			if(invListNew.size() > 0){
				medInvDictMapper.addBatch(invListNew);
			}
			
			medAdjustMapper.updateBatchMedInvDictPlanPrice(list);//修改材料变更表计划价
			
			StringBuffer whereSql = new StringBuffer();
			
			//修改科室需求计划、仓库需求计划、采购计划、订单编制中 材料的单价(20170331台州增加)
			for(Map<String,Object> map : list){
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("inv_id", map.get("inv_id"));
				mapVo.put("inv_no", map.get("inv_no"));
				mapVo.put("old_price", map.get("old_price"));
				mapVo.put("new_price", map.get("new_price"));
				
				//返回订单明细ID
				String orderIds = medAdjustMapper.queryIsExistsInOrder(mapVo);
				if( orderIds != null && !"".equals(orderIds)){
					mapVo.put("orderIds", orderIds);
					mapVo.put("cloumn_name", "price");
					
					//更新订单明细表
					whereSql.setLength(0);//order_detail_id 订单明细ID，订单明细表通过订单ID，order_id关联订单主表，where条件为材料ID，材料变更ID，原单价
					mapVo.put("table_name", "med_order_detail");
					whereSql.append(" and order_detail_id in ( select distinct a.order_detail_id from med_order_detail a left join med_order_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.order_id=b.order_id ");
					whereSql.append(" where b.state in (1,2,4) and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql./*append(" and a.inv_no = ").append(mapVo.get("inv_no")).*/append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					mapVo.put("type", "0");//订单ID不包括 and order_id not in (${orderIds})
					medAdjustMapper.updateMedDetailTable(mapVo);
					
					//更新采购计划明细表
					whereSql.setLength(0);//pur_detail_id 采购单明细ID，采购计划明细表通过采购单ID，pur_id关联采购主表，where条件为材料ID，材料变更ID，原单价
					mapVo.put("table_name", "med_pur_detail");
					whereSql.append(" and pur_detail_id in ( select distinct a.pur_detail_id from med_pur_detail a left join med_pur_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.pur_id=b.pur_id ");
					whereSql.append(" where b.state in (1,2) and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql/*.append(" and a.inv_no = ").append(mapVo.get("inv_no"))*/.append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					mapVo.put("type", "1");//采购单ID不包括 and pur_id not in 采购单与订单关系表的采购单ID，条件为订单ID order_id in()
					medAdjustMapper.updateMedDetailTable(mapVo);
					
					//更新仓库需求计划明细表
					whereSql.setLength(0);//req_detail_id 仓库需求计划单明细ID，仓库需求计划明细表通过仓库需求计划ID，pur_id关联仓库需求主表，where条件为材料ID，材料变更ID，原单价
					mapVo.put("table_name", "med_req_store_detail");
					whereSql.append(" and req_detail_id in ( select distinct a.req_detail_id from med_req_store_detail a left join med_req_store_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.req_id=b.req_id ");
					whereSql.append(" where b.state !=0 and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql./*append(" and a.inv_no = ").append(mapVo.get("inv_no")).*/append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					mapVo.put("type", "2");//仓库需求计划单ID不包括 and req_id not in 仓库与采购计划关系表 关联 采购计划与订单表的仓库需求计划ID，pur_id。条件为采购计划与订单表的订单ID order_id in()
					medAdjustMapper.updateMedDetailTable(mapVo);
					
					//更新科室需求计划明细表
					whereSql.setLength(0);//req_detail_id 科室需求计划单明细ID，科室需求计划明细表通过科室需求计划ID，pur_id关联科室需求主表，where条件为材料ID，材料变更ID，原单价
					mapVo.put("table_name", "med_require_detail");
					whereSql.append(" and req_detail_id in ( select distinct a.req_detail_id from med_require_detail a left join med_require_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.req_id=b.req_id ");
					whereSql.append(" where b.state !=0 and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql./*append(" and a.inv_no = ").append(mapVo.get("inv_no")).*/append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					mapVo.put("type", "3");//科室需求计划单ID不包括  and req_id not in 科室与采购计划关系表 关联 采购计划与订单关系表的科室需求计划ID，pur_id。条件为采购计划与订单表的订单ID order_id in()
										//得到req_id 与以下返回的的req_id 相连起来   科室与仓库关系表 关联 仓库与需求计划关系表 通过需求计划ID，req_id关联，仓库与需求计划关系表 关联 采购计划与订单关系表 通过采购单ID,pur_id
					medAdjustMapper.updateMedDetailTable(mapVo);
					
				}else{
					//更新科室需求计划明细表
					mapVo.put("cloumn_name", "price");
					mapVo.put("table_name", "med_require_detail");
					whereSql.setLength(0);
					whereSql.append(" and req_detail_id in ( select distinct a.req_detail_id from med_require_detail a left join med_require_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.req_id=b.req_id ");
					whereSql.append(" where b.state !=0 and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql/*.append(" and a.inv_no = ").append(mapVo.get("inv_no"))*/.append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					medAdjustMapper.updateMedDetailTable(mapVo);
					
					//更新仓库需求计划明细表
					whereSql.setLength(0);
					mapVo.put("table_name", "med_req_store_detail");
					whereSql.append(" and req_detail_id in ( select distinct a.req_detail_id from med_req_store_detail a left join med_req_store_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.req_id=b.req_id ");
					whereSql.append(" where b.state !=0 and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql/*.append(" and a.inv_no = ").append(mapVo.get("inv_no"))*/.append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					medAdjustMapper.updateMedDetailTable(mapVo);
					
					//更新采购计划明细表
					whereSql.setLength(0); 
					mapVo.put("table_name", "med_pur_detail");
					whereSql.append(" and pur_detail_id in ( select distinct a.pur_detail_id from med_pur_detail a left join med_pur_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.pur_id=b.pur_id ");
					whereSql.append(" where b.state in (1,2) and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql/*.append(" and a.inv_no = ").append(mapVo.get("inv_no"))*/.append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					medAdjustMapper.updateMedDetailTable(mapVo);
					
					//更新订单明细表
					whereSql.setLength(0);
					mapVo.put("table_name", "med_order_detail");
					whereSql.append(" and order_detail_id in ( select distinct a.order_detail_id from med_order_detail a left join med_order_main b on a.group_id=b.group_id ");
					whereSql.append(" and a.hos_id=b.hos_id and a.copy_code=b.copy_code and a.order_id=b.order_id ");
					whereSql.append(" where b.state in (1,2,4) and a.group_id=").append(mapVo.get("group_id")).append(" and a.hos_id = ").append(mapVo.get("hos_id"));
					whereSql.append(" and a.copy_code=").append(mapVo.get("copy_code").toString()).append(" and a.inv_id=").append(mapVo.get("inv_id"));
					whereSql/*.append(" and a.inv_no = ").append(mapVo.get("inv_no"))*/.append(" and a.price=").append(mapVo.get("old_price"));
					whereSql.append(" )");
					mapVo.put("whereSql", whereSql.toString());
					medAdjustMapper.updateMedDetailTable(mapVo);
				}
			}
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}
	
	/**
	 * @Description 
	 * 调价单<BR>按主表ID 查询明细表数据
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedAdjustDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = medAdjustMapper.queryMedAdjustDetailByCode(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = medAdjustMapper.queryMedAdjustDetailByCode(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				
		mapDetailVo.put("inv_id", "");
				
		mapDetailVo.put("inv_no", "");
				
		mapDetailVo.put("old_sell_price", "");
				
		mapDetailVo.put("new_sell_price", "");
				
		mapDetailVo.put("adjust_reason", "");
				
		return mapDetailVo;
	}
	
	@Override
	public String queryMedInvList(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = medAdjustMapper.queryMedInvList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medAdjustMapper.queryMedInvList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> queryMedAdjustInvByCode( Map<String, Object> entityMap) throws DataAccessException {
		
		return medAdjustMapper.queryMedAdjustInvByCode(entityMap);
	}
}

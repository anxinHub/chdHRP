/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.dura.check;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraStoreBalanceMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraStoreBarMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraStoreSurplusMapper;
import com.chd.hrp.mat.dao.dura.check.MatDuraCheckStoreMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.check.MatDuraCheckStoreService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 耐用品库房盘点 
 * @Table: MAT_DURA_STORE_(CHECK/CHECK_D) 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matDuraCheckStoreService")
public class MatDuraCheckStoreServiceImpl implements MatDuraCheckStoreService {

	private static Logger logger = Logger.getLogger(MatDuraCheckStoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDuraCheckStoreMapper")
	private final MatDuraCheckStoreMapper matDuraCheckStoreMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matDuraStoreBalanceMapper")
	private final MatDuraStoreBalanceMapper matDuraStoreBalanceMapper = null;
	@Resource(name = "matDuraStoreSurplusMapper")
	private final MatDuraStoreSurplusMapper matDuraStoreSurplusMapper = null;
	@Resource(name = "matDuraStoreBarMapper")
	private final MatDuraStoreBarMapper matDuraStoreBarMapper = null;
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	
	
	/**
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//编制日期
			if(entityMap.get("make_date") == null || "".equals(entityMap.get("make_date"))){
				return "{\"error\":\"编制日期不能为空\",\"state\":\"false\"}";
			}
			
			//截取期间
			String[] date = entityMap.get("make_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			
			//转换日期格式
			if(entityMap.get("make_date") != null && !"".equals(entityMap.get("make_date"))){
				entityMap.put("make_date", DateUtil.stringToDate(entityMap.get("make_date").toString(), "yyyy-MM-dd"));
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成单据号
			if("自动生成".equals(entityMap.get("check_no"))){
				entityMap.put("table_code", "MAT_DURA_STORE_CHECK");
				entityMap.put("length_no", "5");  //流水码5位
				entityMap.put("containsDay", "0");  //单据号不按day生成
				entityMap.put("check_no", matCommonService.getMatNextNo(entityMap));
			}
			
			entityMap.put("maker", SessionManager.getUserId());  //制单人
			entityMap.put("state", 1);  //状态
			
			//取出主表ID（自增序列）
			entityMap.put("check_id", matDuraCheckStoreMapper.queryMainSeq());
			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						//存放明细数据
						Map<String,Object> detailMap = new HashMap<String, Object>();
						
						detailMap.put("group_id", entityMap.get("group_id").toString());
						detailMap.put("hos_id", entityMap.get("hos_id").toString());
						detailMap.put("copy_code", entityMap.get("copy_code").toString());
						detailMap.put("check_id", entityMap.get("check_id").toString());  //主表ID
						detailMap.put("detail_id", matDuraCheckStoreMapper.queryDetailSeq());  //明细表ID
						
						detailMap.put("inv_id",  jsonObj.get("inv_id").toString());  //材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no").toString());  //材料变更号
						detailMap.put("price",  jsonObj.get("price").toString());  //单价
						detailMap.put("cur_amount",  jsonObj.get("cur_amount").toString());  //账面数量
						detailMap.put("chk_amount",  jsonObj.get("chk_amount").toString());  //盘点数量
						detailMap.put("bar_code",  jsonObj.get("bar_code") == null ? "" : jsonObj.get("bar_code").toString());  //条码
						detailMap.put("note", jsonObj.get("note") == null ? "" : jsonObj.get("note").toString());  //备注
						
						detailList.add(detailMap);
					}
				}
				
				if(detailList.size() == 0){
					return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
				}
				
				//新增入库主表数据
				matDuraCheckStoreMapper.addMain(entityMap);
				//新增入库明细数据
				matDuraCheckStoreMapper.addDetail(detailList);
			}else{
				return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("添加失败");
		}
		
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("check_id").toString()+","+
			entityMap.get("check_no").toString()+","
			+"\"}";
	}
	
		/**
	 * @Description 
	 * 更新<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//校验单据状态
			entityMap.put("check_state", 1);
			int flag = matDuraCheckStoreMapper.existsState(entityMap);
			if(flag > 0){
				return "{\"error\":\"单据不为未审核状态不能修改！\",\"state\":\"false\"}";
			}
			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						//存放明细数据
						Map<String,Object> detailMap = new HashMap<String, Object>();
						
						detailMap.put("group_id", entityMap.get("group_id").toString());
						detailMap.put("hos_id", entityMap.get("hos_id").toString());
						detailMap.put("copy_code", entityMap.get("copy_code").toString());
						detailMap.put("check_id", entityMap.get("check_id").toString());  //主表ID
						
						detailMap.put("inv_id",  jsonObj.get("inv_id").toString());  //材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no").toString());  //材料变更号
						detailMap.put("price",  jsonObj.get("price").toString());  //单价
						detailMap.put("cur_amount",  jsonObj.get("cur_amount").toString());  //账面数量
						detailMap.put("chk_amount",  jsonObj.get("chk_amount").toString());  //盘点数量
						detailMap.put("bar_code",  jsonObj.get("bar_code") == null ? "" : jsonObj.get("bar_code").toString());  //条码
						detailMap.put("note", jsonObj.get("note") == null ? "" : jsonObj.get("note").toString());  //备注
						
						//明细表ID
						if(jsonObj.get("detail_id") == null || "".equals(jsonObj.get("detail_id"))){
							//获取明细ID
							detailMap.put("detail_id", String.valueOf(matDuraCheckStoreMapper.queryDetailSeq()));  //明细表ID
							detailList.add(detailMap);
						}else{
							detailMap.put("detail_id", jsonObj.get("detail_id").toString());  //明细ID
							detailList.add(detailMap);
						}
					}
				}
				
				if(detailList.size() == 0){
					return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
				}
				
				//修改入库主表数据
				matDuraCheckStoreMapper.updateMain(entityMap);
				
				//删除明细记录重新添加
				matDuraCheckStoreMapper.deleteDetail(entityMap);
				matDuraCheckStoreMapper.addDetail(detailList);
				
			}else{
				return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("更新失败");
		}		
		
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
    
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不为新建不能删除
			int flag = matDuraCheckStoreMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有状态不是未审核的单据，请重新选择！\",\"state\":\"true\"}";
			}
				
			//批量删除明细表
			matDuraCheckStoreMapper.deleteDetailBatch(entityList);
			//批量删除主表
			matDuraCheckStoreMapper.deleteMainBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("删除失败");
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 批量审核或消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditOrUnAuditBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//校验状态
			int flag = matDuraCheckStoreMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有其他状态的单据，请重新选择！\",\"state\":\"true\"}";
			}
				
			//批量审核
			matDuraCheckStoreMapper.auditOrUnAuditBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String confirmBatch(List<Map<String, Object>> entityList)throws DataAccessException{
		
		try {	
			//校验状态
			int flag = matDuraCheckStoreMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有其他状态的单据，请重新选择！\",\"state\":\"true\"}";
			}
			
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			
			//常量定义
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			//获取所选单据明细
			List<Map<String, Object>> detailList = matDuraCheckStoreMapper.queryDetailListForConfirm(entityList);
			
			//存放Balance表数据
			List<Map<String, Object>> updateBalanceList = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> balanceMap = new HashMap<String, Map<String, Object>>();
			String balanceKey = ""; 
			
			String barKey = "";
			
			Double amount = null;  //盘点数量
			Double amount_money = null;  //盘点金额
			
			//用于记录数据
			Map<String, Object> map = null;
			
			//循环明细数据
			for(Map<String, Object> detailMap : detailList){
				//初始化循环变量
				amount = Double.valueOf(detailMap.get("amount").toString());
				//由于金额=数量*单价故需要格式化小数位数
				amount_money = NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount_money").toString()), money_para);
				
				//查询库房信息
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("group_id", detailMap.get("group_id"));
				searchMap.put("hos_id", detailMap.get("hos_id"));
				searchMap.put("copy_code", detailMap.get("copy_code"));
				searchMap.put("year", year);
				searchMap.put("month", month);
				searchMap.put("store_id", detailMap.get("store_id")); 
				searchMap.put("inv_id", detailMap.get("inv_id")); 
				searchMap.put("price", detailMap.get("price")); 
				searchMap.put("bar_code", detailMap.get("bar_code")); 
				
				barKey = detailMap.get("store_id").toString() + detailMap.get("inv_id");
				balanceKey = barKey + detailMap.get("price").toString() + detailMap.get("bar_code").toString();
				
				/**操作mat_dura_store_balance表***********begin***********/
				map = new HashMap<String, Object>();  //初始化map
				if(balanceMap.containsKey(balanceKey)){
					map = balanceMap.get(balanceKey);
				}else{
					//查询库房帐表数据
					map = matDuraStoreBalanceMapper.queryByCode(searchMap);
				}
				
				//如果不存在记录则添加
				if(map == null || map.size() == 0){
					map = new HashMap<String, Object>();
					map.putAll(searchMap);
					map.put("in_amount", amount);
					map.put("in_money", amount_money);
					map.put("out_amount", 0);
					map.put("out_money", 0);
					map.put("left_amount", amount);
					map.put("left_money", amount_money);
					map.put("operation", "insert");
				}else{
					
					if(Double.valueOf(detailMap.get("amount").toString()) > 0){
						//累加入库数据
						map.put("in_amount", NumberUtil.add(Double.valueOf(map.get("in_amount").toString()), amount));
						map.put("in_money", NumberUtil.add(Double.valueOf(map.get("in_money").toString()), amount_money));
						//累加库存量
						map.put("left_amount", NumberUtil.add(Double.valueOf(map.get("left_amount").toString()), amount));
						map.put("left_money", NumberUtil.add(Double.valueOf(map.get("left_money").toString()), amount_money));
					}else if(Double.valueOf(detailMap.get("amount").toString()) < 0){
						//判断库存是否充足
						if(-1 * Double.valueOf(map.get("left_amount").toString()) > amount){
							return "{\"error\":\"单据" + detailMap.get("check_no") + "中材料《" + 
									detailMap.get("inv_code") + " " + detailMap.get("inv_name") + "》库存不足\"}";
						}
						//累加出库数据
						map.put("out_amount", NumberUtil.sub(Double.valueOf(map.get("out_amount").toString()), amount));
						map.put("out_money", NumberUtil.sub(Double.valueOf(map.get("out_money").toString()), amount_money));
						//减少库存量
						map.put("left_amount", NumberUtil.add(Double.valueOf(map.get("left_amount").toString()), amount));
						map.put("left_money", NumberUtil.add(Double.valueOf(map.get("left_money").toString()), amount_money));
					}
					map.put("operation", "update");
				}
				//放入Map中
				balanceMap.put(balanceKey, map);
				/**操作mat_dura_store_balance表***********end*************/
			}
			
			//解析balanceMap获得需要修改的数据
			for(String key : balanceMap.keySet()){
				updateBalanceList.add(balanceMap.get(key));
			}
			
			//修改mat_dura_store_balance表
			if(updateBalanceList.size() > 0){
				matDuraStoreBalanceMapper.updateOrAddBatch(updateBalanceList);
			}
			
			//修改单据状态
			matDuraCheckStoreMapper.confirmBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<?> list = matDuraCheckStoreMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matDuraCheckStoreMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return matDuraCheckStoreMapper.queryMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<?> list = matDuraCheckStoreMapper.queryDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 
	 * 模板打印（包含主从表）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryDataByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException{
		try{
			//查询主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=matDuraCheckStoreMapper.queryMainForPrintTemlateBatch(entityMap);
				//查询明细表
				List<Map<String,Object>> list=matDuraCheckStoreMapper.queryDetailForPrintTemlate(entityMap);
				
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				Map<String,Object> map=matDuraCheckStoreMapper.queryMainForPrintTemlate(entityMap);
				//查询明细表
				List<Map<String,Object>> list=matDuraCheckStoreMapper.queryDetailForPrintTemlate(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryMatInvByBalance(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return ChdJson.toJsonLower(matDuraCheckStoreMapper.queryMatInvByBalance(mapVo));
	}

	@Override
	public String queryMatDuraInvChoiceByStore(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return ChdJson.toJsonLower(matDuraCheckStoreMapper.queryMatDuraInvChoiceByStore(mapVo));
	}
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.dura.scrap;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

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
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptBalanceMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptBarMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptSurplusMapper;
import com.chd.hrp.mat.dao.dura.scrap.MatDuraScrapDeptMapper;
import com.chd.hrp.mat.dao.dura.tran.MatDuraTranStoreStoreMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.scrap.MatDuraScrapDeptService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 耐用品科室报废 
 * @Table: MAT_DURA_DEPT_(SCRAP/SCRAP_D) 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0 
 */ 
@Service("matDuraScrapDeptService")
public class MatDuraScrapDeptServiceImpl implements MatDuraScrapDeptService {

	private static Logger logger = Logger.getLogger(MatDuraScrapDeptServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDuraScrapDeptMapper")
	private final MatDuraScrapDeptMapper matDuraScrapDeptMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matDuraDeptBalanceMapper")
	private final MatDuraDeptBalanceMapper matDuraDeptBalanceMapper = null;
	@Resource(name = "matDuraDeptSurplusMapper")
	private final MatDuraDeptSurplusMapper matDuraDeptSurplusMapper = null;
	@Resource(name = "matDuraDeptBarMapper")
	private final MatDuraDeptBarMapper matDuraDeptBarMapper = null;
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
			if("自动生成".equals(entityMap.get("scrap_no"))){
				entityMap.put("table_code", "MAT_DURA_STORE_SCRAP");
				entityMap.put("length_no", "5");  //流水码5位
				entityMap.put("containsDay", "0");  //单据号不按day生成
				entityMap.put("scrap_no", matCommonService.getMatNextNo(entityMap));
			}
			
			entityMap.put("maker", SessionManager.getUserId());  //制单人
			entityMap.put("bus_type_code", "35");  //耐用品库到库转移业务类型
			entityMap.put("state", 1);  //状态
			
			//取出主表ID（自增序列）
			entityMap.put("scrap_id", matDuraScrapDeptMapper.queryMainSeq());
			
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
						detailMap.put("scrap_id", entityMap.get("scrap_id").toString());  //主表ID
						detailMap.put("detail_id", matDuraScrapDeptMapper.queryDetailSeq());  //明细表ID
						
						detailMap.put("inv_id",  jsonObj.get("inv_id").toString());  //材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no").toString());  //材料变更号
						detailMap.put("price",  jsonObj.get("price").toString());  //单价
						detailMap.put("amount",  jsonObj.get("amount").toString());  //数量
						detailMap.put("pay_money",  jsonObj.get("pay_money").toString());  //处置费用
						detailMap.put("income_money",  jsonObj.get("income_money").toString());  //处置收入
						detailMap.put("bar_code",  jsonObj.get("bar_code") == null ? "" : jsonObj.get("bar_code").toString());  //条码
						detailMap.put("note", jsonObj.get("note") == null ? "" : jsonObj.get("note").toString());  //备注
						
						detailList.add(detailMap);
					}
				}
				
				if(detailList.size() == 0){
					return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
				}
				
				//新增入库主表数据
				matDuraScrapDeptMapper.addMain(entityMap);
				//新增入库明细数据
				matDuraScrapDeptMapper.addDetail(detailList);
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
			entityMap.get("scrap_id").toString()+","
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
			int flag = matDuraScrapDeptMapper.existsState(entityMap);
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
						detailMap.put("scrap_id", entityMap.get("scrap_id").toString());  //主表ID
						
						detailMap.put("inv_id",  jsonObj.get("inv_id").toString());  //材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no").toString());  //材料变更号
						detailMap.put("price",  jsonObj.get("price").toString());  //单价
						detailMap.put("amount",  jsonObj.get("amount").toString());  //数量
						detailMap.put("pay_money",  jsonObj.get("pay_money").toString());  //处置费用
						detailMap.put("income_money",  jsonObj.get("income_money").toString());  //处置收入
						detailMap.put("bar_code",  jsonObj.get("bar_code") == null ? "" : jsonObj.get("bar_code").toString());  //条码
						detailMap.put("note", jsonObj.get("note") == null ? "" : jsonObj.get("note").toString());  //备注
						
						//明细表ID
						if(jsonObj.get("detail_id") == null || "".equals(jsonObj.get("detail_id"))){
							//获取明细ID
							detailMap.put("detail_id", String.valueOf(matDuraScrapDeptMapper.queryDetailSeq()));  //明细表ID
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
				matDuraScrapDeptMapper.updateMain(entityMap);
				
				//删除明细记录重新添加
				matDuraScrapDeptMapper.deleteDetail(entityMap);
				matDuraScrapDeptMapper.addDetail(detailList);
				
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
			int flag = matDuraScrapDeptMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有状态不是未审核的单据，请重新选择！\",\"state\":\"true\"}";
			}
				
			//批量删除明细表
			matDuraScrapDeptMapper.deleteDetailBatch(entityList);
			//批量删除主表
			matDuraScrapDeptMapper.deleteMainBatch(entityList);
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
			int flag = matDuraScrapDeptMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有其他状态的单据，请重新选择！\",\"state\":\"true\"}";
			}
				
			//批量审核
			matDuraScrapDeptMapper.auditOrUnAuditBatch(entityList);
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
	public synchronized String confirmBatch(List<Map<String, Object>> entityList)throws DataAccessException{
		
		try {	
			//校验状态
			int flag = matDuraScrapDeptMapper.existsStateBatch(entityList);
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
			List<Map<String, Object>> detailList = matDuraScrapDeptMapper.queryDetailListForConfirm(entityList);
			
			//存放Balance表数据
			List<Map<String, Object>> updateBalanceList = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> balanceMap = new HashMap<String, Map<String, Object>>();
			String balanceKey = ""; 
			
			String barKey = "";
			
			Double amount = null;  //报废数量
			Double amount_money = null;  //报废金额
			
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
				searchMap.put("dept_id", detailMap.get("dept_id")); 
				searchMap.put("inv_id", detailMap.get("inv_id")); 
				searchMap.put("price", detailMap.get("price")); 
				searchMap.put("bar_code", detailMap.get("bar_code")); 
				
				barKey = detailMap.get("dept_id").toString() + detailMap.get("inv_id");
				balanceKey = barKey + detailMap.get("price").toString() + detailMap.get("bar_code").toString();
				
				/**操作mat_dura_dept_balance表***********begin***********/
				map = new HashMap<String, Object>();  //初始化map
				if(balanceMap.containsKey(balanceKey)){
					map = balanceMap.get(balanceKey);
				}else{
					//查询库房帐表数据
					map = matDuraDeptBalanceMapper.queryByCode(searchMap);
				}
				
				//如果不存在记录不能确认
				if(map == null || map.size() == 0){
					return "{\"error\":\"单据" + detailMap.get("scrap_no") + "中材料《" + 
							detailMap.get("inv_code") + " " + detailMap.get("inv_name") + "》库存不足\"}";
				}else{
					//判断库存是否充足
					if(Double.valueOf(map.get("left_amount").toString()) < amount){
						return "{\"error\":\"单据" + detailMap.get("scrap_no") + "中材料《" + 
								detailMap.get("inv_code") + " " + detailMap.get("inv_name") + "》库存不足\"}";
					}
					//累加出库数据
					map.put("out_amount", NumberUtil.add(Double.valueOf(map.get("out_amount").toString()), amount));
					map.put("out_money", NumberUtil.add(Double.valueOf(map.get("out_money").toString()), amount_money));
					//减少库存量
					map.put("left_amount", NumberUtil.sub(Double.valueOf(map.get("left_amount").toString()), amount));
					map.put("left_money", NumberUtil.sub(Double.valueOf(map.get("left_money").toString()), amount_money));
				}
				//放入Map中
				balanceMap.put(balanceKey, map);
				/**操作mat_dura_dept_balance表***********end*************/
			}
			
			//解析balanceMap获得需要修改的数据
			for(String key : balanceMap.keySet()){
				updateBalanceList.add(balanceMap.get(key));
			}
			
			//修改mat_dura_dept_balance表
			if(updateBalanceList.size() > 0){
				matDuraDeptBalanceMapper.updateBatch(updateBalanceList);
			}
			
			//修改单据状态
			matDuraScrapDeptMapper.confirmBatch(entityList);
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
			
			List<?> list = matDuraScrapDeptMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matDuraScrapDeptMapper.query(entityMap, rowBounds);
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
		
		return matDuraScrapDeptMapper.queryMainByCode(entityMap);
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

		List<?> list = matDuraScrapDeptMapper.queryDetailByCode(entityMap);
		
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
	public Map<String,Object> queryDataByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException{
		try {
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatDuraScrapDeptMapper matDuraScrapDeptMapper = (MatDuraScrapDeptMapper)context.getBean("matDuraScrapDeptMapper");
			if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
				List<Map<String,Object>> map=matDuraScrapDeptMapper.queryMainForPrintTemlateBatch(entityMap);
				//查询明细表
				List<Map<String,Object>> list=matDuraScrapDeptMapper.queryDetailForPrintTemlate(entityMap);
				reMap.put("main", map);
				reMap.put("detail", list);
			}else{
				Map<String,Object> map=matDuraScrapDeptMapper.queryMainForPrintTemlate(entityMap);
				//查询明细表
				List<Map<String,Object>> list=matDuraScrapDeptMapper.queryDetailForPrintTemlate(entityMap);
				
				reMap.put("main", map);
				reMap.put("detail", list);
			}
			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * 审批、销审批
	 */
	@Override
	public String auditOrUnApproveBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			/*//校验状态
			int flag = matDuraScrapDeptMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有其他状态的单据，请重新选择！\",\"state\":\"true\"}";
			}*/
				
			//批量操作
			matDuraScrapDeptMapper.auditOrUnApproveBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
}

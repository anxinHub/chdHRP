/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.dura.init;

import java.util.*;

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
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.dura.init.MatDuraInitChargeMapper;
import com.chd.hrp.mat.dao.dura.init.MatDuraInitStoreMapper;
import com.chd.hrp.mat.service.dura.init.MatDuraInitStoreService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 耐用品库房期初登记表 
 * @Table: MAT_DURA_STORE_REG
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matDuraInitStoreService")
public class MatDuraInitStoreServiceImpl implements MatDuraInitStoreService {

	private static Logger logger = Logger.getLogger(MatDuraInitStoreServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDuraInitStoreMapper")
	private final MatDuraInitStoreMapper matDuraInitStoreMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matDuraInitChargeMapper")
	private final MatDuraInitChargeMapper matDuraInitChargeMapper = null;
	
	/**
	 * @Description 
	 * 查询结果集耐用品库房期初登记表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matDuraInitStoreMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matDuraInitStoreMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 添加耐用品库房期初登记表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String save(Map<String,Object> entityMap)throws DataAccessException{
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			//制单人
			entityMap.put("maker", SessionManager.getUserId());
			//状态
			entityMap.put("state", 1);
			//编制日期
			if(entityMap.get("make_date") == null || "".equals(entityMap.get("make_date"))){
				return "{\"error\":\"统计日期不能为空\",\"state\":\"false\"}";
			}else{
				//转换日期格式(前台传来的是js格式的date)
				String make_date = DateUtil.jsDateToString(entityMap.get("make_date").toString(), "yyyy-MM-dd");
				String[] dates = make_date.split("-");
				
				entityMap.put("make_date", DateUtil.stringToDate(make_date, "yyyy-MM-dd"));
//				entityMap.put("acc_year", dates[0]); 
//				entityMap.put("acc_month", dates[1]); 
				entityMap.put("year_month", dates[0] + dates[1]); 
			}
			//仓库
			if(entityMap.get("store_id") == null || "".equals(entityMap.get("store_id"))){
				return "{\"error\":\"仓库不能为空\",\"state\":\"false\"}";
			}
			//材料ID
			if(entityMap.get("inv_id") == null || "".equals(entityMap.get("inv_id"))){
				return "{\"error\":\"材料不能为空\",\"state\":\"false\"}";
			}
			//单价
			if(entityMap.get("price") == null || "".equals(entityMap.get("price"))){
				return "{\"error\":\"单价不能为空\",\"state\":\"false\"}";
			}
			//条码
			if(entityMap.get("bar_code") == null || "".equals(entityMap.get("bar_code"))){
				entityMap.put("bar_code", "-");  //默认条码
			}
			//数量
			if(entityMap.get("amount") == null || "".equals(entityMap.get("amount"))){
				return "{\"error\":\"数量不能为空\",\"state\":\"false\"}";
			}
			//备注
			if(entityMap.get("note") == null){
				entityMap.put("note", "");
			}
			
			//统计日期需在系统启用日期之前year_month
			entityMap.put("year", entityMap.get("year_month").toString().substring(0, 4));
			entityMap.put("month", entityMap.get("year_month").toString().substring(4));
			int flag = matCommonMapper.existsMatStartDateCheck(entityMap);
			if(flag == 1){
				return "{\"error\":\"保存失败，统计日期需在系统启用日期之前！\",\"state\":\"false\"}";
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			/*flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}*/
			
			//判断耐用品是否期初记账
			flag =  matDuraInitChargeMapper.existsStoreAccount(entityMap);
			if(flag == 0){
				return "{\"error\":\"仓库已记账.\",\"state\":\"false\"}";
			}
				
			if(entityMap.get("reg_id") != null && !"".equals(entityMap.get("reg_id").toString())){
				//修改数据
				matDuraInitStoreMapper.update(entityMap);
			}else{
				//取自增序列
				entityMap.put("reg_id", matDuraInitStoreMapper.querySeq());
				//新增数据
				matDuraInitStoreMapper.add(entityMap);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("添加失败");
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"group_id\":\""
			+entityMap.get("group_id").toString()
			+"\",\"hos_id\":\""+entityMap.get("hos_id").toString()
			+"\",\"copy_code\":\""+entityMap.get("copy_code").toString()
			+"\",\"reg_id\":\""+entityMap.get("reg_id").toString()
			+"\"}";
	}
	
	/**
	 * @Description 
	 * 批量添加耐用品库房期初登记表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败");
		}
		//暂无该业务
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量删除耐用品库房期初登记表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//判断耐用品是否期初记账
			Map<String, Object> entityMap = entityList.get(0);
			int state =  matDuraInitChargeMapper.existsStoreAccount(entityMap);
			if(state == 0){
				return "{\"error\":\"仓库已记账.\",\"state\":\"false\"}";
			}
			
			//判断状态是否已审核（审核后不能删除修改）
			state = matDuraInitStoreMapper.existsState(entityList);
			if(state > 0){
				return "{\"error\":\"期初已审核不能删除\"}";
			}
			//批量表数据
			matDuraInitStoreMapper.deleteBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("删除失败");
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 审核或消审<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String auditOrUnAudit(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//判断耐用品是否期初记账
			Map<String, Object> entityMap = entityList.get(0);
			int state =  matDuraInitChargeMapper.existsStoreAccount(entityMap);
			if(state == 0){
				return "{\"error\":\"仓库已记账.\",\"state\":\"false\"}";
			}
			
			//判断状态是否都是已审核或未审核
			state = matDuraInitStoreMapper.existsState(entityList);
			if(state > 0){
				return "{\"error\":\"请选择状态一致的期初数据\"}";
			}
			
			//批量审核
			matDuraInitStoreMapper.auditOrUnAudit(entityList);
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String importData(Map<String, Object> entityMap)throws DataAccessException{
		
		try {	
			List<Map<String, Object>> duraList = new ArrayList<Map<String,Object>>();
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String user_id = SessionManager.getUserId();
			
			Map<String, Object> seachMap = new HashMap<String, Object>();
			seachMap.put("group_id", group_id);
			seachMap.put("hos_id", hos_id);
			seachMap.put("copy_code", copy_code);
			seachMap.put("user_id", user_id);
			
			//耐用品材料列表
			List<Map<String, Object>> invList = matDuraInitStoreMapper.queryInvListForImport(seachMap);
			Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> inv : invList){
				if(inv.get("INV_CODE") != null){
					invMap.put(inv.get("INV_CODE").toString(), inv);
				}
			}
			
			//库房列表含记账标识state
			List<Map<String, Object>> storeList = matDuraInitStoreMapper.queryStoreListForImport(seachMap);
			Map<String, Map<String, Object>> storeMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> store : storeList){
				if(store.get("STORE_CODE") != null){
					storeMap.put(store.get("STORE_CODE").toString(), store);
				}
			}
			
			//存放错误信息
			StringBuffer errorJson = new StringBuffer();
			
			//解析前台数据
			String data = entityMap.get("data").toString();
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(data, 1);
			if(dataList==null || dataList.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			String store_code;
			String inv_code;
			List<String> rowList=null;
			for(Map<String, List<String>> dataMap : dataList){
				Map<String, Object> map = new HashMap<String, Object>();
				
				/**校验库房**************begin*****************/
				rowList = dataMap.get("库房编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，库房编码为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				store_code = rowList.get(1);
				Map<String, Object> v_store = storeMap.get(store_code);
				if(v_store == null){
					return "{\"warn\":\"" + rowList.get(0) + "，库房编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//判断库房是否记账
				if(v_store.get("state") != null && !"0".equals(v_store.get("state"))){
					return "{\"warn\":\"" + v_store.get("store_name") + "已记账不能导入！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//验证通过存放库房信息
				map.put("store_id", v_store.get("STORE_ID"));
				map.put("store_no", v_store.get("STORE_NO"));
				/**校验库房**************end*******************/
				
				/**校验日期**************begin*****************/
				rowList = dataMap.get("统计日期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，统计日期为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//验证通过存放日期
				map.put("make_date", DateUtil.stringToDate(rowList.get(1), "yyyy-MM-dd"));
				/**校验日期**************end*******************/
				
				/**校验材料**************begin*****************/
				rowList = dataMap.get("材料编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，材料编码为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				inv_code = rowList.get(1);
				Map<String, Object> v_inv = invMap.get(inv_code);
				if(v_inv == null){
					return "{\"warn\":\"" + rowList.get(0) + "，材料编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//验证通过存放材料信息
				map.put("inv_id", v_inv.get("INV_ID"));
				map.put("inv_no", v_inv.get("INV_NO"));
				/**校验材料**************end*******************/
				
				/**校验单价**************begin*****************/
				rowList = dataMap.get("单价");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，单价为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!NumberUtil.isNumeric(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，单价不为数字（可能存在空格或回车）！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//验证通过存放单价
				map.put("price", rowList.get(1));
				/**校验单价**************end*******************/
				
				/**校验数量**************begin*****************/
				rowList = dataMap.get("数量");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，数量为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!NumberUtil.isNumeric(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，数量不为数字（可能存在空格或回车）！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//验证通过存放数量
				map.put("amount", rowList.get(1));
				/**校验数量**************end*******************/
				
				/**校验金额**************begin*****************/
				rowList = dataMap.get("金额");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，金额为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!NumberUtil.isNumeric(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，金额不为数字（可能存在空格或回车）！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//验证通过存放金额
				map.put("amount_money", rowList.get(1));
				/**校验金额**************end*******************/
				
				/**校验金额**************begin*****************/
				rowList = dataMap.get("金额");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，金额为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				if(!NumberUtil.isNumeric(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，金额不为数字（可能存在空格或回车）！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				//验证通过存放金额
				map.put("amount_money", rowList.get(1));
				/**校验金额**************end*******************/
				
				/**条码**************begin*****************/
				rowList = dataMap.get("条码");
				//存放条码
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					map.put("bar_code", rowList.get(1));
				}else{
					map.put("bar_code", "-");
				}
				/**条码**************end*******************/
				
				/**备注**************begin*****************/
				rowList = dataMap.get("备注");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					map.put("note", rowList.get(1));
				}else{
					map.put("note", "");
				}
				/**校验备注**************end*******************/
				
				/**状态**************begin*****************/
				rowList = dataMap.get("状态");
				//存放状态
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1)) && "已审核".equals(rowList.get(1))){
					map.put("state", "2");
					map.put("checker", user_id);
					map.put("check_date", new Date());
				}else{
					map.put("state", "1");
					map.put("checker", "");
					map.put("check_date", "");
				}
				/**状态**************end*******************/
				
				//必填项
				map.put("group_id", group_id);
				map.put("hos_id", hos_id);
				map.put("copy_code", copy_code);
				map.put("maker", user_id);
				map.put("reg_id", matDuraInitStoreMapper.querySeq());
				
				duraList.add(map);
			}
			
			//批量添加
			matDuraInitStoreMapper.addBatch(duraList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
}

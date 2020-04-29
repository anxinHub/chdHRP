/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.budgcontrol;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.budg.dao.project.budgcontrol.BudgProjApplyMapper;
import com.chd.hrp.budg.dao.project.budgcontrol.BudgProjApplyResolveMapper;
import com.chd.hrp.budg.dao.project.budgcontrol.BudgProjApplySourceMapper;
import com.chd.hrp.budg.entity.BudgProjApply;
import com.chd.hrp.budg.service.project.budgcontrol.BudgProjApplyService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 项目预算申报
 * @Table:
 * BUDG_PROJ_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjApplyService")
public class BudgProjApplyServiceImpl implements BudgProjApplyService {

	private static Logger logger = Logger.getLogger(BudgProjApplyServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjApplyMapper")
	private final BudgProjApplyMapper budgProjApplyMapper = null;
	
	//项目预算申报明细（按资金来源）
	@Resource(name = "budgProjApplySourceMapper")
	private final BudgProjApplySourceMapper budgProjApplySourceMapper = null;
	
	// 项目预算分解
	@Resource(name = "budgProjApplyResolveMapper")
	private final BudgProjApplyResolveMapper budgProjApplyResolveMapper = null;
	
	
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;
	
	
	
    
	/**
	 * @Description 
	 * 项目预算申报<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			/*申报类型APPLY_TYPE为01年度预算申报时同一项目同一年度只允许申报一次；

			申报类型APPLY_TYPE为02追加预算申报时可申报多次。*/

			if("01".equals(entityMap.get("apply_type"))){
				
				//查询数据是否已存在
				
				int count = budgProjApplyMapper.queryDataExist(entityMap);
		
				if (count > 0) {
		
					return "{\"error\":\"数据已存在,请重新添加.\",\"state\":\"false\"}";
		
				}
			}
			
			//查询  预算启用年度；
			String startYear = budgProjApplyMapper.queryBudgModStartYear(entityMap) ;
			
			
			if(startYear != null && !"".equals(startYear)){
				//保存时 校验预算年度必须大于等于预算启用年度；
				if(Integer.parseInt(startYear) > Integer.parseInt(String.valueOf(entityMap.get("budg_year")))){
					
					return "{\"error\":\"添加失败！申报预算年度必须大于等于预算启用年度.\",\"state\":\"false\"}";
					
				}
				
			}else{
				
				return "{\"error\":\"添加失败！预算系统未启用.\",\"state\":\"false\"}";
				
			}
			
			
			// 根据 申报预算年度  查询 结转状态
			String is_carred = budgProjApplyMapper.queryBudgYearIsCarried(entityMap) ;
			
			//已结转年度不能进行预算申报。
			if("1".equals(is_carred)){
				
				return "{\"error\":\"添加失败！填写申报预算年度已结转.\"}";
				
			}
			
			// 生成 预算申报单号
			if("系统生成".equals(entityMap.get("apply_code"))){
				
				//组装 生成 预算申报单号 参数Map
				Map<String,Object> map  = new HashMap<String,Object>();
				
				map.put("group_id", entityMap.get("group_id")) ;
				
				map.put("hos_id", entityMap.get("hos_id")) ;
				
				map.put("copy_code", entityMap.get("copy_code")) ;
				
				map.put("year", entityMap.get("budg_year")) ;
				
				entityMap.put("apply_code", setBudgApplyCode(map));
				
			}
			
			DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
			
			entityMap.put("maker", SessionManager.getUserId());
			
			entityMap.put("make_date", df.format(new Date()));
			
			entityMap.put("checker", "");
			
			entityMap.put("check_date","");
			
			
			//BUDG_PROJ_APPLY_SOURCE 项目预算申报明细（按资金来源） 添加用List
			List<Map<String,Object>> sourceList = new ArrayList<Map<String,Object>>();
			
			//BUDG_PROJ_APPLY_RESOLVE 项目预算分解 添加用List
			List<Map<String,Object>> resolveList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("sourceData")));
				
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				Map<String,Object> sourceMap = new HashMap<String,Object>();
				
				sourceMap.put("group_id", entityMap.get("group_id"));
				sourceMap.put("hos_id", entityMap.get("hos_id"));
				sourceMap.put("copy_code", entityMap.get("copy_code"));
				sourceMap.put("budg_year",entityMap.get("budg_year"));//年度
				sourceMap.put("apply_code", entityMap.get("apply_code"));//预算申报单号
				sourceMap.put("proj_id",entityMap.get("proj_id"));//项目ID
				sourceMap.put("source_id",String.valueOf(jsonObj.get("source_id")));//资金来源ID
				sourceMap.put("apply_amount",  jsonObj.get("apply_amount"));//申报金额
				sourceList.add(sourceMap);
				
				if(jsonObj.get("detail") != null && !"".equals(String.valueOf(jsonObj.get("detail")))){
					
					JSONArray jsonResolve = JSONArray.parseArray(String.valueOf(JSONObject.parseObject(String.valueOf(jsonObj.get("detail"))).get("Rows")));
					if(jsonResolve.size() > 0){
						
						Iterator itResolve = jsonResolve.iterator();
						while (itResolve.hasNext()) {
							
							JSONObject jsonObjResolve = JSONObject.parseObject(itResolve.next().toString());
							
							Map<String,Object> rateMap = new HashMap<String,Object>();
							
							rateMap.put("group_id", entityMap.get("group_id"));
							rateMap.put("hos_id", entityMap.get("hos_id"));
							rateMap.put("copy_code", entityMap.get("copy_code"));
							rateMap.put("budg_year",entityMap.get("budg_year"));//年度
							rateMap.put("apply_code", entityMap.get("apply_code"));//预算申报单号
							rateMap.put("proj_id",entityMap.get("proj_id"));//项目ID
							rateMap.put("payment_item_id",jsonObjResolve.get("payment_item_id"));//支出项目ID
							rateMap.put("payment_item_no",jsonObjResolve.get("payment_item_no"));//支出项目NO
							rateMap.put("source_id",String.valueOf(jsonObj.get("source_id")));//资金来源ID
							
							rateMap.put("rate",jsonObjResolve.get("rate"));//预算比例
							
							rateMap.put("budg_amount",jsonObjResolve.get("budg_amount"));//预算金额
							
							resolveList.add(rateMap);
							
						}
						
					}
					
				}
				
			}
			
			budgProjApplyMapper.add(entityMap);
			
			budgProjApplySourceMapper.addBatch(sourceList);
			
			if(resolveList.size()> 0){
				
				budgProjApplyResolveMapper.addBatch(resolveList);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\",\"state\":\"false\"}");
		}
		
	}
	/**
	 * @Description 
	 * 批量添加 项目预算申报<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjApplyMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\",\"state\":\"false\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新 项目预算申报
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//BUDG_PROJ_APPLY_SOURCE 项目预算申报明细（按资金来源） 添加用List
			List<Map<String,Object>> sourceList = new ArrayList<Map<String,Object>>();
			
			//BUDG_PROJ_APPLY_RESOLVE 项目预算分解 添加用List
			List<Map<String,Object>> resolveList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("sourceData")));
				
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				Map<String,Object> sourceMap = new HashMap<String,Object>();
				
				sourceMap.put("group_id", entityMap.get("group_id"));
				sourceMap.put("hos_id", entityMap.get("hos_id"));
				sourceMap.put("copy_code", entityMap.get("copy_code"));
				sourceMap.put("budg_year",entityMap.get("budg_year"));//年度
				sourceMap.put("apply_code", entityMap.get("apply_code"));//预算申报单号
				sourceMap.put("proj_id",entityMap.get("proj_id"));//项目ID
				sourceMap.put("source_id",String.valueOf(jsonObj.get("source_id")));//资金来源ID
				sourceMap.put("apply_amount",  jsonObj.get("apply_amount"));//申报金额
				sourceList.add(sourceMap);
				
				if(jsonObj.get("detail") != null && !"".equals(String.valueOf(jsonObj.get("detail")))){
					
					JSONArray jsonResolve = JSONArray.parseArray(String.valueOf(JSONObject.parseObject(String.valueOf(jsonObj.get("detail"))).get("Rows")));
					
					if(jsonResolve.size()>0){
						
						Iterator itResolve = jsonResolve.iterator();
						while (itResolve.hasNext()) {
							
							JSONObject jsonObjResolve = JSONObject.parseObject(itResolve.next().toString());
							
							Map<String,Object> rateMap = new HashMap<String,Object>();
							
							rateMap.put("group_id", entityMap.get("group_id"));
							rateMap.put("hos_id", entityMap.get("hos_id"));
							rateMap.put("copy_code", entityMap.get("copy_code"));
							rateMap.put("budg_year",entityMap.get("budg_year"));//年度
							rateMap.put("apply_code", entityMap.get("apply_code"));//预算申报单号
							rateMap.put("proj_id",entityMap.get("proj_id"));//项目ID
							rateMap.put("payment_item_id",jsonObjResolve.get("payment_item_id"));//支出项目ID
							rateMap.put("payment_item_no",jsonObjResolve.get("payment_item_no"));//支出项目NO
							rateMap.put("source_id",String.valueOf(jsonObj.get("source_id")));//资金来源ID
							
							rateMap.put("rate",jsonObjResolve.get("rate"));//预算比例
							
							rateMap.put("budg_amount",jsonObjResolve.get("budg_amount"));//预算金额
							
							resolveList.add(rateMap);
							
						}
					}
					
				}
				
			}
			

			budgProjApplyMapper.update(entityMap);
			
			budgProjApplyResolveMapper.delete(entityMap);
			
			budgProjApplySourceMapper.delete(entityMap);
			
			budgProjApplySourceMapper.addBatch(sourceList);
			
			if(resolveList.size() > 0 ){
				
				budgProjApplyResolveMapper.addBatch(resolveList);
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新 项目预算申报
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgProjApplyMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除  项目预算申报
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteData(Map<String, Object> entityMap,Map<String, Object> fileMap) throws DataAccessException {
    	
		try {
		
			budgProjApplyResolveMapper.delete(entityMap);
			
			budgProjApplySourceMapper.delete(entityMap);
    	
			budgProjApplyMapper.delete(entityMap);
			
			//如果存在文件名  说明存在文件  删除文件
			if(!"null".equals(entityMap.get("file_url"))){
				String file_name = fileMap.get("file_url").toString();
				String path = fileMap.get("filePath").toString();
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"error\":\"删除失败\",\"state\":\"false\"}";
				}
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\",\"state\":\"false\"}");

		}	
		
  }
	/**
	 * @Description 
	 * 删除  项目预算申报
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			budgProjApplyResolveMapper.delete(entityMap);
			
			budgProjApplySourceMapper.delete(entityMap);
			
			budgProjApplyMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\",\"state\":\"false\"}");
			
		}	
		
	}
    
	/**
	 * @Description 
	 * 批量删除 项目预算申报
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjApplyResolveMapper.deleteBatch(entityList);
			
			budgProjApplySourceMapper.deleteBatch(entityList);
			
			budgProjApplyMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\",\"state\":\"false\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加 项目预算申报
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
		//判断是否存在对象引用项目字典生成项目预算申报

		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgProjApply> list = (List<BudgProjApply>)budgProjApplyMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgProjApplyMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgProjApplyMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			
			throw new SysException("{\"error\":\"操作失败\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集 项目预算申报
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjApplyMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjApplyMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象  项目预算申报
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjApplyMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取  项目预算申报
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgProjApply
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjApplyMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgProjApply>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjApplyMapper.queryExists(entityMap);
	}
	
	/**
	 * @Description 
	 * 查询 支出项目数据 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryBudgPaymentItem(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjApplyMapper.queryBudgPaymentItem(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjApplyMapper.queryBudgPaymentItem(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 生成 预算申报单号
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String setBudgApplyCode(Map<String,Object> mapVo) throws DataAccessException{
		
		mapVo.put("month", "");
		mapVo.put("table_code", "BUDG_PROJ_APPLY") ;
		mapVo.put("table_name", "项目预算申报") ;
		mapVo.put("prefixe", "XMYSSB") ;
		mapVo.put("seq_no", 4);
		
		BudgNoManager budgNoManager = budgNoManagerMapper.queryBudgNoManagerByCode(mapVo);
		
		String pref = "XMYSSB";

		String max_no = "1";
		
		String no = "1" ;
		if(budgNoManager == null){
			
			mapVo.put("max_no", 1);
			
			budgNoManagerMapper.addBudgNoManager(mapVo);
			
		}else{
			
			max_no = String.valueOf(budgNoManager.getMax_no() + 1);
			
			no = max_no ;
			
			mapVo.put("max_no", max_no);
			
			budgNoManagerMapper.updateBudgNoManagerMaxNo(mapVo);
		}
		
		for(int i= 0 ; i< 4- no.length() ; i++){
			
			max_no = "0"+ max_no ;
			
		}
		
		return pref + mapVo.get("year") + max_no;
	}
	
	/**
	 * 查询 BUDG_PROJ_APPLY_SOURCE  项目预算申报明细（按资金来源）
	 */
	@Override
	public String queryBudgProjApplySource(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjApplySourceMapper.query(entityMap);
			
			for(Map<String,Object>  item : list){
				
				List<Map<String,Object>> detailList = budgProjApplyMapper.queryBudgProjApplyResolve(item);
				
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgProjApplySourceMapper.query(entityMap, rowBounds);
			
			for(Map<String,Object>  item : list){
				
				List<Map<String,Object>> detailList = budgProjApplyMapper.queryBudgProjApplyResolve(item);
				
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 查询 BUDG_PROJ_APPLY_RESOLVE  项目预算分解 
	 */
	@Override
	public String queryBudgProjApplyResolve(Map<String, Object> entityMap) throws DataAccessException {
		
		String sourceID = String.valueOf(entityMap.get("sourceID"));
		
		//查询 BUDG_PROJ_APPLY_RESOLVE  项目预算分解  汇总 数据（按支出项目汇总）
		List<Map<String,Object>> dataList = budgProjApplyMapper.querySumApplyResolve(entityMap) ;
		
		//查询 BUDG_PROJ_APPLY_RESOLVE  项目预算分解  
		List<Map<String,Object>> list = budgProjApplyMapper.queryBudgProjApplyResolve(entityMap);
		
		//行转列 组装数据
		
		for(Map<String,Object> item: dataList){
			
			for(Map<String,Object> resolve : list){
				
				if(item.get("payment_item_id").equals(resolve.get("payment_item_id"))){
					
					for(String id : sourceID.split("@")){
						
						if(id.equals(String.valueOf(resolve.get("source_id")))){
							
							item.put(String.valueOf(resolve.get("source_code")), resolve.get("budg_amount"));
							
							item.put("Rate"+id, resolve.get("rate"));
							
							item.put("budgAmount"+id, resolve.get("budg_amount"));
						}
						
					}
				}
				
			}
			
			
		}
		
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			
			return ChdJson.toJson(dataList);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(dataList, page.getTotal());
			
		}
	}
	/**
	 * 查询 期初项目预算记账BUDG_PROJ_BEGAIN_MARK 状态    为1记账时才可进行预算申报。
	 */
	@Override
	public String queryBegainMark(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgProjApplyMapper.queryBegainMark(entityMap);

	}
	/**
	 * 提交 撤回 修改 数据状态 
	 */
	@Override
	public String updateBudgProjApplyState(List<Map<String, Object>> listVo)	throws DataAccessException {
		try {
			
			  budgProjApplyMapper.updateBudgProjApplyState(listVo);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"操作失败\"}");

			}	
	}
	
	
	/**
	 * 审核
	 */
	@Override
	public String auditBudgProjApply(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			
				// 查询  项目预算数据
				List<Map<String,Object>> list = budgProjApplyMapper.queryBudgProjData(listVo);
				
				// 查询 项目预算明细数据
				List<Map<String,Object>> detailList = budgProjApplyMapper.queryBudgProjDetailData(listVo);
				
				//添加 年度项目预算用List
				List<Map<String,Object>> addYearList = new ArrayList<Map<String,Object>>();
				//修改 年度项目预算用List
				List<Map<String,Object>> upadateYearList = new ArrayList<Map<String,Object>>();
				//添加 年度项目预算明细用List
				List<Map<String,Object>> addYearDetailList = new ArrayList<Map<String,Object>>();
				//修改 年度项目预算明细用List
				List<Map<String,Object>> upadateYearDetailList = new ArrayList<Map<String,Object>>();
				
				for(Map<String,Object> item : list){
					
					
					
					//查询 年度项目预算
					Map<String,Object> yearData = budgProjApplyMapper.queryBudgProjYearByCode(item) ;
					
					
					// 如果存在 则更新数据 , 否则添加数据
					if(yearData != null ){
						
						// 计算  本期预算金额
						double budg_amount = Double.parseDouble(String.valueOf(yearData.get("budg_amount"))) 
								+ Double.parseDouble(String.valueOf(item.get("budg_amount"))) ;
						
						//记录 本期到账金额
						double in_amount = 0.0 ;
						
						// source_attr 1 自筹资金 2 财政资金 3科研资金  4教学资金
						if("1".equals(String.valueOf(item.get("source_attr")))){ //自筹资金 类资金  到账金额(in_amount) 等于原到账金额 + 申报金额
							
							//到账金额
							in_amount = Double.parseDouble(String.valueOf(yearData.get("in_amount")))  +
										Double.parseDouble(String.valueOf(item.get("budg_amount"))) ; ;
							
						}else{ //非自筹资金 类资金  到账金额(in_amount) 等于原到账金额 
							//到账金额
							in_amount = Double.parseDouble(String.valueOf(yearData.get("in_amount"))) ;
							
						}
						//计算 预算累计金额   原预算累计金额 + 申报金额
						double t_budg_amount = Double.parseDouble(String.valueOf(item.get("budg_amount"))) 
									+ Double.parseDouble(String.valueOf(yearData.get("t_budg_amount")));
						
						double t_in_amount = 0.00 ;
						//计算 到账累计金额 变动  
						// source_attr 1 自筹资金 2 财政资金 3科研资金  4教学资金
						if("1".equals(String.valueOf(item.get("source_attr")))){ //自筹资金 类资金   到账累计金额(t_in_amount) 等于期初到账金额 + 本期到账金额
							
							t_in_amount = Double.parseDouble(String.valueOf(yearData.get("b_in_amount"))) + in_amount;
							
						}else{
							
							t_in_amount =  Double.parseDouble(String.valueOf(yearData.get("t_in_amount"))) ;
						}
						
						
						//计算 可用金额 
						double usable_amount = t_in_amount - Double.parseDouble(String.valueOf(yearData.get("t_cost_amount")));
						
						//计算 预算余额  
						double remain_amount = t_budg_amount - Double.parseDouble(String.valueOf(yearData.get("t_cost_amount")));
						
						//执行进度 
						double rate = 0.00 ;
						if(budg_amount+ Double.parseDouble(String.valueOf(yearData.get("b_budg_amount"))) > 0){
							
							rate = Double.parseDouble(String.valueOf(yearData.get("cost_amount"))) / (budg_amount+ Double.parseDouble(String.valueOf(yearData.get("b_budg_amount")))) * 100 ; 
						}
								
						double t_rate = 0.00 ;
						if(t_budg_amount > 0){
							
							t_rate = Double.parseDouble(String.valueOf(yearData.get("t_cost_amount"))) / t_budg_amount * 100 ; 
						}
						
						yearData.put("budg_amount", budg_amount) ;
						
						yearData.put("in_amount", in_amount) ;
						
						yearData.put("t_budg_amount", t_budg_amount) ;
						
						yearData.put("t_in_amount", t_in_amount) ;
						
						yearData.put("usable_amount", usable_amount) ;
						
						yearData.put("remain_amount", remain_amount) ;
						
						yearData.put("rate", rate);
						
						yearData.put("t_rate", t_rate);
						
						upadateYearList.add(yearData) ;
						
					}else{//数据不存在
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(item);
						//不存在 期初数据全部为0
						map.put("b_budg_amount",0.00);
						map.put("b_in_amount",0.00);
						map.put("b_cost_amount",0.00);
						map.put("b_remain_amoun",0.00);
						map.put("b_usable_amount",0.00);
						map.put("remain_adj",0.00);
						//记录 本期到账金额
						double in_amount = 0.0 ;
						
						// source_attr 1 自筹资金 2 财政资金 3科研资金  4教学资金
						if("1".equals(String.valueOf(item.get("source_attr")))){ //自筹资金 类资金  到账金额(in_amount) 等于  申报金额
							
							//到账金额
							in_amount = Double.parseDouble(String.valueOf(item.get("budg_amount"))) ; ;
							
						}
						//计算 预算累计金额   等于  申报金额
						double t_budg_amount = Double.parseDouble(String.valueOf(item.get("budg_amount"))) ; ;
						
						//计算 到账累计金额 变动 
						double t_in_amount = in_amount ;
						
						//计算 可用金额 
						double usable_amount = t_in_amount ;
						
						//计算 预算余额 
						double remain_amount = t_budg_amount ;
						
						
						map.put("in_amount", in_amount) ;
						
						map.put("cost_amount", 0.00) ;//支出金额 为0.00
						
						map.put("t_budg_amount", t_budg_amount) ;
						
						map.put("t_in_amount", t_in_amount) ;
						
						map.put("t_cost_amount", 0.00) ;
						
						map.put("usable_amount", usable_amount) ;
						
						map.put("remain_amount", remain_amount) ;
						
						map.put("rate", 0.00);
						
						map.put("t_rate", 0.00);
						
						addYearList.add(map) ;
						
					}
				}
				
				for(Map<String,Object> item : detailList ){
					
					
					Map<String,Object> yearDetail = budgProjApplyMapper.queryBudgProjYearDetailByCode(item) ;
					
					// 如果存在 则更新数据 , 否则添加数据
					if(yearDetail != null ){
						
						// 计算  本期预算金额 原预算金额 + 申报金额
						double budg_amount = Double.parseDouble(String.valueOf(yearDetail.get("budg_amount"))) +
									Double.parseDouble(String.valueOf(item.get("budg_amount"))) ;
						
						//计算 预算累计金额  原预算累计金额 + 申报金额
						double y_budg_amount = Double.parseDouble(String.valueOf(item.get("budg_amount"))) 
									+ Double.parseDouble(String.valueOf(yearDetail.get("y_budg_amount")));
						
						//计算 预算余额  
						double remain_amoun = y_budg_amount - Double.parseDouble(String.valueOf(yearDetail.get("y_cost_amount")));
						
						//执行进度 
						double rate = 0.00 ;
						if(budg_amount + Double.parseDouble(String.valueOf(yearDetail.get("b_budg_amount"))) > 0 ){
							
							rate = Double.parseDouble(String.valueOf(yearDetail.get("cost_amount"))) / (budg_amount + Double.parseDouble(String.valueOf(yearDetail.get("b_budg_amount")))) * 100 ; 
							
						}
						
						// 总执行进度 
						double t_rate = 0.00 ;
						if(y_budg_amount > 0 ){
							
							t_rate = Double.parseDouble(String.valueOf(yearDetail.get("y_cost_amount"))) / y_budg_amount * 100 ; 
							
						}
								
						yearDetail.put("budg_amount", budg_amount) ;
						
						yearDetail.put("y_budg_amount", y_budg_amount) ;
						
						yearDetail.put("remain_amoun", remain_amoun) ;
						
						yearDetail.put("rate", rate);
						
						yearDetail.put("t_rate", t_rate);
						
						upadateYearDetailList.add(yearDetail) ;
						
					}else{//数据不存在
						
						Map<String,Object> map =  new HashMap<String,Object>();
						
						map.putAll(item);
						
						//不存在 期初数据全部为0
						map.put("b_budg_amount",0.00);
						map.put("b_in_amount",0.00);
						map.put("b_cost_amount",0.00);
						map.put("b_remain_amoun",0.00);
						
						
						//计算 预算累计金额  等于申报金额
						double y_budg_amount = Double.parseDouble(String.valueOf(item.get("budg_amount"))) ; 
						
						
						//计算 预算余额  
						double remain_amoun = y_budg_amount ;
						
						
						map.put("cost_amount", 0.00) ;//支出金额 为0.00
						
						map.put("y_budg_amount", y_budg_amount) ;
						
						map.put("y_cost_amount", 0.0) ;
						
						map.put("remain_amoun", remain_amoun) ;
						
						map.put("rate", 0.00);
						
						map.put("t_rate", 0.00);
						
						addYearDetailList.add(map) ;
						
					}
					
					
				}
				
				//修改项目预算申报状态
			 	budgProjApplyMapper.auditBudgProjApply(listVo);
			 	
			 	//添加 年度项目预算 数据
			 	if(addYearList.size() > 0){
			 		
			 		budgProjApplyMapper.addBudgProjYearData(addYearList) ;
			 	}
			 	
			 	//修改 年度项目预算 数据
			 	if(upadateYearList.size() > 0){
			 		
			 		budgProjApplyMapper.updateBudgProjYearData(upadateYearList) ;
			 	}
			 	
			 	//添加  年度项目预算明细 数据
			 	if(addYearDetailList.size() > 0){
			 		
			 		budgProjApplyMapper.addBudgProjYearDetailData(addYearDetailList) ;
				}
			 	
			 	//修改  年度项目预算明细 数据
			 	if(upadateYearDetailList.size() > 0){
					
			 		budgProjApplyMapper.updateBudgProjYearDetailData(upadateYearDetailList) ;
				}
				
			 	return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"操作失败\"}");

			}	

	}
	
	/**
	 * 消审
	 */
	@Override
	public String reAuditBudgProjApply(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			// 查询  项目预算数据
			List<Map<String,Object>> list = budgProjApplyMapper.queryBudgProjData(listVo);
			
			// 查询 项目预算明细数据
			List<Map<String,Object>> detailList = budgProjApplyMapper.queryBudgProjDetailData(listVo);
			
			//修改 年度项目预算用List
			List<Map<String,Object>> upadateYearList = new ArrayList<Map<String,Object>>();
			//修改 年度项目预算明细用List
			List<Map<String,Object>> upadateYearDetailList = new ArrayList<Map<String,Object>>();
			
			for(Map<String,Object> item : list){
				
				
				//查询 年度项目预算
				Map<String,Object> yearData = budgProjApplyMapper.queryBudgProjYearByCode(item) ;
				
					
				// 计算  本期预算金额 原本期预算金额 - 申报金额
				double budg_amountYear = Double.parseDouble(String.valueOf(yearData.get("budg_amount"))) 
						- Double.parseDouble(String.valueOf(item.get("budg_amount"))) ;
				
				//记录 本期到账金额
				double in_amount = 0.0 ;
				
				// source_id 1 自筹资金 2 财政资金 3科研资金  4教学资金
				if("1".equals(String.valueOf(item.get("source_attr")))){ //自筹资金 类资金  到账金额(in_amount) 等于原到账金额 - 申报金额
					
					in_amount = Double.parseDouble(String.valueOf(yearData.get("in_amount")))  -
								Double.parseDouble(String.valueOf(item.get("budg_amount"))) ; ;
					
				}else{ //非自筹资金 类资金  到账金额(in_amount) 等于原到账金额 
					//到账金额
					in_amount = Double.parseDouble(String.valueOf(yearData.get("in_amount"))) ;
					
				}
				//计算 预算累计金额   原预算累计金额 - 申报金额
				double t_budg_amount =  Double.parseDouble(String.valueOf(yearData.get("t_budg_amount"))) 
							- Double.parseDouble(String.valueOf(item.get("budg_amount"))) ;
				
				double t_in_amount = 0.00 ;
				//计算 到账累计金额 变动  
				// source_id 1 自筹资金 2 财政资金 3科研资金  4教学资金
				if("1".equals(String.valueOf(item.get("source_attr")))){ //自筹资金 类资金  到账金额(in_amount) 等于原到账金额 - 申报金额
					
					t_in_amount =  Double.parseDouble(String.valueOf(yearData.get("t_in_amount"))) 
							- Double.parseDouble(String.valueOf(item.get("budg_amount")));
					
				}else{//非自筹资金 类资金  到账金额(in_amount) 等于原到账金额 
					
					t_in_amount =  Double.parseDouble(String.valueOf(yearData.get("t_in_amount"))) ;
				}
				
				
				//计算 可用金额 
				double usable_amount = t_in_amount - Double.parseDouble(String.valueOf(yearData.get("t_cost_amount")));
				
				//计算 预算余额  
				double remain_amountYear = t_budg_amount - Double.parseDouble(String.valueOf(yearData.get("t_cost_amount")));
				
				//执行进度 
				double rateYear = 0.00;
				
				if(budg_amountYear + Double.parseDouble(String.valueOf(yearData.get("b_cost_amount")))> 0){
					
					rateYear = Double.parseDouble(String.valueOf(yearData.get("cost_amount"))) / (budg_amountYear + Double.parseDouble(String.valueOf(yearData.get("b_cost_amount")))) * 100 ;
				}
				
				//执行进度 
				double t_rate = 0.00;
				
				if(t_budg_amount > 0){
					
					t_rate = Double.parseDouble(String.valueOf(yearData.get("t_cost_amount"))) / t_budg_amount * 100 ;
				}
				
				yearData.put("budg_amount", budg_amountYear) ;
				
				yearData.put("in_amount", in_amount) ;
				
				yearData.put("t_budg_amount", t_budg_amount) ;
				
				yearData.put("t_in_amount", t_in_amount) ;
				
				yearData.put("usable_amount", usable_amount) ;
				
				yearData.put("remain_amount", remain_amountYear) ;
				
				yearData.put("rate", rateYear);
				
				yearData.put("t_rate", t_rate);
				
				upadateYearList.add(yearData) ;
					
			}
			
			for(Map<String,Object> item : detailList ){
				
				
				Map<String,Object> yearDetail = budgProjApplyMapper.queryBudgProjYearDetailByCode(item) ;
				
					
					// 计算  本期预算金额 原预算金额 - 申报金额
					double budg_amountYear = Double.parseDouble(String.valueOf(yearDetail.get("budg_amount"))) -
								Double.parseDouble(String.valueOf(item.get("budg_amount"))) ;
					
					//计算 预算累计金额  原预算累计金额 - 申报金额
					double y_budg_amount =  Double.parseDouble(String.valueOf(yearDetail.get("y_budg_amount"))) -
								Double.parseDouble(String.valueOf(item.get("budg_amount"))) ;
					
					//计算 预算余额  
					double remain_amounYear = y_budg_amount - Double.parseDouble(String.valueOf(yearDetail.get("y_cost_amount")));
					
					//执行进度 
					double rateYear = 0.00 ;
					
					if(budg_amountYear + Double.parseDouble(String.valueOf(yearDetail.get("b_cost_amount"))) > 0 ){
						
						rateYear = Double.parseDouble(String.valueOf(yearDetail.get("cost_amount"))) / (budg_amountYear + Double.parseDouble(String.valueOf(yearDetail.get("b_cost_amount"))) ) * 100 ; 
					}
					
					//执行进度 
					double t_rate = 0.00 ;
					
					if(y_budg_amount  > 0 ){
						
						t_rate = Double.parseDouble(String.valueOf(yearDetail.get("y_cost_amount"))) / y_budg_amount * 100 ; 
					}
					yearDetail.put("budg_amount", budg_amountYear) ;
					
					yearDetail.put("y_budg_amount", y_budg_amount) ;
					
					yearDetail.put("remain_amoun", remain_amounYear) ;
					
					yearDetail.put("rate", rateYear);
					
					yearDetail.put("t_rate", t_rate);
					
					upadateYearDetailList.add(yearDetail) ;
				
			}
			
			//修改项目预算申报状态
		 	budgProjApplyMapper.auditBudgProjApply(listVo);
		 	
		 	
		 	//修改 年度项目预算 数据
		 	if(upadateYearList.size() > 0){
		 		
		 		budgProjApplyMapper.updateBudgProjYearData(upadateYearList) ;
		 	}
		 	
		 	
		 	//修改  年度项目预算明细 数据
		 	if(upadateYearDetailList.size() > 0){
				
		 		budgProjApplyMapper.updateBudgProjYearDetailData(upadateYearDetailList) ;
			}
			
		 	return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";


			}catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"操作失败\"}");

			}	

	}
	@Override
	public String importPaymentItem(Map<String, Object> map) throws DataAccessException {
		try{
			
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			// 查询 支出项目字典的 所有支出项目的 ID、NO 、name 等基本信息   匹配数据用
			List<Map<String,Object>> listDict = budgProjApplyMapper.queryPaymentItemDict(entityMap);
			
			// 判断支出项目名称是否存在用 map key为支出项目名称 ， value : 支出项目名称、id、no、code等信息
			Map<String, Map<String, Object>> dictMap = new HashMap<String, Map<String, Object>>();
			
			
			for(Map<String, Object> dict : listDict){
				if(dict.get("payment_item_name") != null){
					dictMap.put(dict.get("payment_item_name").toString(), dict);
				}
			}
			
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			
			for(Map<String,List<String>> item : liData ){
				
				
				List<String> name = item.get("支出项目名称") ;
				
				List<String> rateList = item.get("预算比例(%)") ;
				
				List<String> amount = item.get("预算金额(元)") ;
				
				if(name == null || name.get(1) == null || "".equals(name.get(1))){
					
					return "{\"warn\":\"" + name.get(0) + ",支出项目名称为空！\",\"state\":\"false\",\"row_cell\":\"" + name.get(0) + "\"}";
				
				}else{
					
					if(dictMap.get(name.get(1)) == null){
						
						return "{\"warn\":\"" + name.get(0) + ",支出项目名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + name.get(0) + "\"}";
					}
				}
				
				if(rateList == null || rateList.get(1) == null || "".equals(rateList.get(1))){
					
					return "{\"warn\":\"" + rateList.get(0) + ",预算比例(%)为空！\",\"state\":\"false\",\"row_cell\":\"" + rateList.get(0) + "\"}";
					
				}else{
					
					 try{
						   int  rate = Integer.parseInt(rateList.get(1));
						   
						   if(rate < 0 || rate > 100){
							   
							   return "{\"warn\":\"" + rateList.get(0) + ",预算比例(%)输入不合法,只能输入大于等于0、小于等于100的数字！\",\"state\":\"false\",\"row_cell\":\"" + rateList.get(0) + "\"}";
						   }
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + rateList.get(0) + ",预算比例(%)输入不合法,只能输入大于等于0、小于等于100的数字！\",\"state\":\"false\",\"row_cell\":\"" + rateList.get(0) + "\"}";
					  }
					
				}
				
				if(amount == null || amount.get(1) == null || "".equals(amount.get(1))){
					
					return "{\"warn\":\"" + amount.get(0) + ",预算金额(元)为空！\",\"state\":\"false\",\"row_cell\":\"" + amount.get(0) + "\"}";
					
				}else{
					
					 try{
						    Integer.parseInt(amount.get(1));
						   
					 }catch(NumberFormatException e){
						 
						 return "{\"warn\":\"" + amount.get(0) + ",预算金额(元)输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + amount.get(0) + "\"}";
					  }
					
				}
				
				//存放 正确信息用Map
				Map<String,Object> returnMap = new HashMap<String,Object>();
					
				returnMap.putAll(dictMap.get(name.get(1))); // 存放 支出项目名称、id、no、code等基本信息
				
				returnMap.put("rate", rateList.get(1));
				
				returnMap.put("budg_amount", amount.get(1));
				
				returnList.add(returnMap);
			}
			
			return "{\"Rows\":" + JSON.toJSONString(returnList) + ",\"state\":\"true\",\"rowIndex\":"+returnList.size()+"}";
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 上传文件
	 */
	@Override
	public String importFile(Map<String, Object> entityMap, MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath) throws Exception {
		try {
			if(entityMap.get("old_file_url") != null && !entityMap.get("old_file_url").equals("")){
				String file_name = entityMap.get("old_file_url").toString();
				String path = filePath;
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			
			if (uploadFile != null ) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath,request,response)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"上传成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除文件
	 */
	@Override
	public String deleteFile(List<Map<String,Object>> entityMap) {
		
		try {
			for(Map<String,Object> map : entityMap){
				String file_name = map.get("file_url").toString();
				String path = map.get("file_url").toString();
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 下载文件
	 */
	@Override
	public String downloadFile(HttpServletResponse response, Map<String, Object> entityMap) {
		try {
			String file_name = entityMap.get("file_url").toString();
			String path = entityMap.get("file_url").toString();
			if(!FtpUtil.downloadFile(response, file_name, path)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "{\"msg\":\"下载成功.\",\"state\":\"true\"}";
		} catch (NoTransactionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

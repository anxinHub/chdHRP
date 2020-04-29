/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.matpay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.matpay.MatPayMainMapper;
import com.chd.hrp.mat.entity.MatPayMain;
import com.chd.hrp.mat.service.matpay.MatPayMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
 * @Table:
 * MAT_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matPayMainService")
public class MatPayMainServiceImpl implements MatPayMainService {

	private static Logger logger = Logger.getLogger(MatPayMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matPayMainMapper")
	private final MatPayMainMapper matPayMainMapper = null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
    
	@Override
	public String queryMatMainlYesNo(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String billNos=	matPayMainMapper.queryMatMainlYesNo(entityList);
			if(billNos !=null  && !"".equals(billNos)){
				return "{\"error\":\"以下单据未审核，请审核后继续操作 ：<br>"+billNos.toString()+"\"}";
			}else{
				return "{\"state\":\"true\"}";
			}
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	
	/**
	 * @Description 
	 * 添加state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatPayMain(Map<String,Object> entityMap)throws DataAccessException{
		
	
		//获取对象state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
		MatPayMain matPayMain = queryMatPayMainByCode(entityMap);

		if (matPayMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			Long pay_id  = matPayMainMapper.queryPaySeqNextval();
			
			entityMap.put("pay_id", pay_id);
			
			int state = matPayMainMapper.addMatPayMain(entityMap);
			
			//返回的pay_id和pay_bill_no前台页面需要用到，返回位置(第4个和第5个)不能变
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+"|"+
				entityMap.get("hos_id").toString()+"|"+
				entityMap.get("copy_code").toString()+"|"+
				entityMap.get("pay_id").toString()+"|"+
				entityMap.get("pay_bill_no").toString()
				+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
		
	}
	/**
	 * @Description 
	 * 批量添加state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR>	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatPayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPayMainMapper.addBatchMatPayMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
		
	}
	
		/**
	 * @Description 
	 * 更新state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatPayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matPayMainMapper.updateMatPayMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 批量更新state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR>
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatPayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matPayMainMapper.updateBatchMatPayMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatPayMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matPayMainMapper.deleteMatPayMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR>
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatPayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPayMainMapper.deleteBatchMatPayMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatPayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
		MatPayMain matPayMain = queryMatPayMainByCode(entityMap);

		if (matPayMain != null) {

			int state = matPayMainMapper.updateMatPayMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matPayMainMapper.addMatPayMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatPayMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatPayMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatPayMain> list = matPayMainMapper.queryMatPayMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatPayMain> list = matPayMainMapper.queryMatPayMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatPayMain queryMatPayMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matPayMainMapper.queryMatPayMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPayMain
	 * @throws DataAccessException
	*/
	@Override
	public MatPayMain queryMatPayMainByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return matPayMainMapper.queryMatPayMainByUniqueness(entityMap);
	}
	/**
	 *根据查询条件查询出没有被付款单引用过的发票
	 * @param page
	 * @return
	 */
	public String queryMatBillMain_Pay(Map<String, Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if(entityMap.containsKey("set_id")){
			entityMap.put("set_id", entityMap.get("set_id"));
		}
		if(entityMap.containsKey("store_id")){
			entityMap.put("store_id", entityMap.get("store_id"));
		}	
		if(!entityMap.containsKey("store_id") && !entityMap.containsKey("set_id")){
			entityMap.put("store_id","");
			entityMap.put("set_id", "");
		}
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matPayMainMapper.queryMatBillMain_PayAdd(entityMap);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= matPayMainMapper.queryMatBillDetail_PayAdd(item);
				// 存放  明细数据 
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matPayMainMapper.queryMatBillMain_PayAdd(entityMap, rowBounds);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= matPayMainMapper.queryMatBillDetail_PayAdd(item);
				// 存放  明细数据 
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 *修改页面的添加按钮的查询
	 * @param page
	 * @return
	 */
	public String queryMatBillMain_PayAdd(Map<String, Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		if(entityMap.containsKey("set_id")){
			entityMap.put("set_id", entityMap.get("set_id"));
		}
		if(entityMap.containsKey("store_id")){
			entityMap.put("store_id", entityMap.get("store_id"));
		}	
		if(!entityMap.containsKey("store_id") && !entityMap.containsKey("set_id")){
			entityMap.put("store_id","");
			entityMap.put("set_id", "");
		}
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matPayMainMapper.queryMatBillMain_PayAdd(entityMap);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= matPayMainMapper.queryMatBillDetail_PayAdd(item);
				// 存放  明细数据 
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matPayMainMapper.queryMatBillMain_PayAdd(entityMap, rowBounds);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= matPayMainMapper.queryMatBillDetail_PayAdd(item);
				// 存放  明细数据 
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * 根据发票ID 查询发票明细信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatBillDetail_Pay(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matPayMainMapper.queryMatBillDetail_Pay(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matPayMainMapper.queryMatBillDetail_Pay(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 查询 付款单主表当前最大的 ID
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryPayID() throws DataAccessException{
		return matPayMainMapper.queryPayID();
	}
	/**
	 * 查询 付款单详细信息 （修改页面回值用）
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryMatPayMainUnit(Map<String, Object> entityMap) {
		return matPayMainMapper.queryMatPayMainUnit(entityMap);
	}
	/**
	 * 审核、消审
	 * @param entityList
	 * @return
	 */
	public String updatePayState(List<Map<String, Object>> entityList) {
		try {
			
				matPayMainMapper.updatePayState(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"操作失败\"}");

			}	
	}
	/**
	 * 生成付款单号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setPayBillNo(Map<String, Object> mapVo) throws DataAccessException {
		
		mapVo.put("table_code", "MAT_PAY_MAIN");
		if(mapVo.get("pay_bill_type").toString().equals("1")){
			mapVo.put("prefixe", "FK");
		}else{
			mapVo.put("prefixe", "FK");
		}
		
		// 判断该 类型 协议编码是否存在（存在取 max_no,不存在 则max_no=1 添加新记录 ）
		int count = matNoManageMapper.queryIsExists(mapVo);
		String max_no = "";
		if(count == 0){
			max_no = "1";
			mapVo.put("max_no", max_no);
			matNoManageMapper.add(mapVo);
		}else{
			//更新该业务流水码+1
			matNoManageMapper.updateMaxNo(mapVo);
			//取出该业务更新后的流水码
			max_no = matNoManageMapper.queryMaxCode(mapVo);
		}
		//补充流水号为4位（如：0001）
		for (int i = max_no.length() ; i < 4; i++) {
			max_no = "0" + max_no;
		}
		mapVo.put("max_no", max_no);
		return ChdJson.toJson(mapVo);
	}
	
	//入库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatPayMainByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map =matPayMainMapper.queryMatPayMainPrintTemlateByMainBatch(entityMap);
				List<Map<String,Object>> list=matPayMainMapper.queryMatPayMainPrintTemlateByDetail(entityMap);
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				
				Map<String,Object> map=matPayMainMapper.queryMatPayMainPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matPayMainMapper.queryMatPayMainPrintTemlateByDetail(entityMap);
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,null);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	
	
	
}
	
	
	public Map<String, Object> queryMatPayMainByPrintTemlateNew(Map<String, Object> entityMap){
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatPayMainMapper matPayMainMapper = (MatPayMainMapper)context.getBean("matPayMainMapper");
			
			
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map =matPayMainMapper.queryMatPayMainPrintTemlateByMainBatch(entityMap);
				List<Map<String,Object>> list=matPayMainMapper.queryMatPayMainPrintTemlateByDetail(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}else{
				
				Map<String,Object> map=matPayMainMapper.queryMatPayMainPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matPayMainMapper.queryMatPayMainPrintTemlateByDetail(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 查询付款单明细打印数据
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryMatPayDetailByPrintTemlateNew(Map<String, Object> entityMap){
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatPayMainMapper matPayMainMapper = (MatPayMainMapper)context.getBean("matPayMainMapper");

			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				//按批号打印
				return result;
			}else{
				//查询入库明细表
                Map<String,Object> map=matPayMainMapper.queryMatPayDetailByPrintByMain(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=matPayMainMapper.queryMatPayDetailByPrintByDetail(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryMatPayMainByPrintBill(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String, Object>> list = matPayMainMapper.queryMatPayMainByBill(entityMap);
		return list;
	}

	@Override
	public List<String> queryMatPayState(Map<String, Object> entityMap) throws DataAccessException {
		return matPayMainMapper.queryMatPayState(entityMap);
	}
}

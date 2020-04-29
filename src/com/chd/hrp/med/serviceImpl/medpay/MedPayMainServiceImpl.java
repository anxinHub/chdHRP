/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.medpay;

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
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.medpay.MedPayMainMapper;
import com.chd.hrp.med.entity.MedPayMain;
import com.chd.hrp.med.service.medpay.MedPayMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
 * @Table:
 * MED_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medPayMainService")
public class MedPayMainServiceImpl implements MedPayMainService {

	private static Logger logger = Logger.getLogger(MedPayMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medPayMainMapper")
	private final MedPayMainMapper medPayMainMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
    
	/**
	 * @Description 
	 * 添加state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedPayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
		MedPayMain medPayMain = queryMedPayMainByCode(entityMap);

		if (medPayMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			Long pay_id  = medPayMainMapper.queryPaySeqNextval();
			
			entityMap.put("pay_id", pay_id);
			
			int state = medPayMainMapper.addMedPayMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+"|"+
				entityMap.get("hos_id").toString()+"|"+
				entityMap.get("copy_code").toString()+"|"+
				entityMap.get("pay_id").toString()
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
	public String addBatchMedPayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPayMainMapper.addBatchMedPayMain(entityList);
			
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
	public String updateMedPayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medPayMainMapper.updateMedPayMain(entityMap);
			
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
	public String updateBatchMedPayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medPayMainMapper.updateBatchMedPayMain(entityList);
			
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
    public String deleteMedPayMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medPayMainMapper.deleteMedPayMain(entityMap);
			
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
	public String deleteBatchMedPayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPayMainMapper.deleteBatchMedPayMain(entityList);
			
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
	public String addOrUpdateMedPayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
		MedPayMain medPayMain = queryMedPayMainByCode(entityMap);

		if (medPayMain != null) {

			int state = medPayMainMapper.updateMedPayMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medPayMainMapper.addMedPayMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedPayMain\"}";

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
	public String queryMedPayMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedPayMain> list = medPayMainMapper.queryMedPayMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedPayMain> list = medPayMainMapper.queryMedPayMain(entityMap, rowBounds);
			
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
	public MedPayMain queryMedPayMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medPayMainMapper.queryMedPayMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPayMain
	 * @throws DataAccessException
	*/
	@Override
	public MedPayMain queryMedPayMainByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return medPayMainMapper.queryMedPayMainByUniqueness(entityMap);
	}
	/**
	 *根据查询条件查询出没有被付款单引用过的发票
	 * @param page
	 * @return
	 */
	public String queryMedBillMain_Pay(Map<String, Object> entityMap) throws DataAccessException{

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
			
			List<Map<String,Object>> list = medPayMainMapper.queryMedBillMain_Pay(entityMap);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= medPayMainMapper.queryMedBillDetail_Pay(item);
				// 存放  明细数据 
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medPayMainMapper.queryMedBillMain_Pay(entityMap, rowBounds);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= medPayMainMapper.queryMedBillDetail_Pay(item);
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
	public String queryMedBillDetail_Pay(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medPayMainMapper.queryMedBillDetail_Pay(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medPayMainMapper.queryMedBillDetail_Pay(entityMap, rowBounds);
			
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
		return medPayMainMapper.queryPayID();
	}
	/**
	 * 查询 付款单详细信息 （修改页面回值用）
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryMedPayMainUnit(Map<String, Object> entityMap) {
		return medPayMainMapper.queryMedPayMainUnit(entityMap);
	}
	/**
	 * 审核、消审
	 * @param entityList
	 * @return
	 */
	public String updatePayState(List<Map<String, Object>> entityList) {
		try {
			
				medPayMainMapper.updatePayState(entityList);
				
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
		
		mapVo.put("table_code", "MED_PAY_MAIN");
		if(mapVo.get("pay_bill_type").toString().equals("1")){
			mapVo.put("prefixe", "FK");
		}else{
			mapVo.put("prefixe", "FK");
		}
		
		// 判断该 类型 协议编码是否存在（存在取 max_no,不存在 则max_no=1 添加新记录 ）
		int count = medNoManageMapper.queryIsExists(mapVo);
		String max_no = "";
		if(count == 0){
			max_no = "1";
			mapVo.put("max_no", max_no);
			medNoManageMapper.add(mapVo);
		}else{
			//更新该业务流水码+1
			medNoManageMapper.updateMaxNo(mapVo);
			//取出该业务更新后的流水码
			max_no = medNoManageMapper.queryMaxCode(mapVo);
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
	public String queryMedPayMainByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map =medPayMainMapper.queryMedPayMainPrintTemlateByMainBatch(entityMap);
				List<Map<String,Object>> list=medPayMainMapper.queryMedPayMainPrintTemlateByDetail(entityMap);
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				
				Map<String,Object> map=medPayMainMapper.queryMedPayMainPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=medPayMainMapper.queryMedPayMainPrintTemlateByDetail(entityMap);
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,null);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	
	
	
}
	
	
	public Map<String, Object> queryMedPayMainByPrintTemlateNew(Map<String, Object> entityMap){
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MedPayMainMapper medPayMainMapper = (MedPayMainMapper)context.getBean("medPayMainMapper");
			
			
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map =medPayMainMapper.queryMedPayMainPrintTemlateByMainBatch(entityMap);
				List<Map<String,Object>> list=medPayMainMapper.queryMedPayMainPrintTemlateByDetail(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}else{
				
				Map<String,Object> map=medPayMainMapper.queryMedPayMainPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=medPayMainMapper.queryMedPayMainPrintTemlateByDetail(entityMap);
				result.put("main", map);
				result.put("detail", list);
				return result;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.medprepay;

import java.util.*;

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
import com.chd.hrp.med.dao.medprepay.MedPrePayDetailMapper;
import com.chd.hrp.med.dao.medprepay.MedPrePayMainMapper;
import com.chd.hrp.med.entity.MedPrePayMain;
import com.chd.hrp.med.service.medprepay.MedPrePayMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 保存预付款单的主要信息
 * @Table:
 * MED_Pre_Pay_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medPrePayMainService")
public class MedPrePayMainServiceImpl implements MedPrePayMainService {

	private static Logger logger = Logger.getLogger(MedPrePayMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medPrePayMainMapper")
	private final MedPrePayMainMapper medPrePayMainMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	
	/**
	 * @Description 
	 * 添加保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedPrePayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存预付款单的主要信息
		long count = medPrePayMainMapper.queryMedPrePayMainExists(entityMap);

		if (count>0) {

			return "{\"error\":\"预付款单号重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medPrePayMainMapper.addMedPrePayMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加保存预付款单的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedPrePayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPrePayMainMapper.addBatchMedPrePayMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"添加失败\"}");
		}
		
	}
	
		/**
	 * @Description 
	 * 更新保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedPrePayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medPrePayMainMapper.updateMedPrePayMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"更新失败\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 批量更新保存预付款单的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedPrePayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medPrePayMainMapper.updateBatchMedPrePayMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedPrePayMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medPrePayMainMapper.deleteMedPrePayMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除保存预付款单的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedPrePayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPrePayMainMapper.deleteBatchMedPrePayMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedPrePayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存预付款单的主要信息
		MedPrePayMain medPrePayMain = queryMedPrePayMainByCode(entityMap);

		if (medPrePayMain != null) {

			int state = medPrePayMainMapper.updateMedPrePayMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medPrePayMainMapper.addMedPrePayMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedPrePayMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedPrePayMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedPrePayMain> list = medPrePayMainMapper.queryMedPrePayMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedPrePayMain> list = medPrePayMainMapper.queryMedPrePayMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象保存预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedPrePayMain queryMedPrePayMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medPrePayMainMapper.queryMedPrePayMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取保存预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPrePayMain
	 * @throws DataAccessException
	*/
	@Override
	public MedPrePayMain queryMedPrePayMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medPrePayMainMapper.queryMedPrePayMainByUniqueness(entityMap);
	}
	/**
	 * 入库单查询 （没有被预付款单引用过的采购入库单）
	 * @param page
	 * @return
	 */
	public String queryMedCommonIn(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medPrePayMainMapper.queryMedCommonIn(entityMap);
			for(Map<String,Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MED_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList= medPrePayMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}else{
					item.put("table", "MED_IN_DETAIL");
					// 明细数据 查询
					List<Map<String,Object>> detailList = medPrePayMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medPrePayMainMapper.queryMedCommonIn(entityMap, rowBounds);
			//附加  明细数据
			for(Map<String,Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MED_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList= medPrePayMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}else{
					item.put("table", "MED_IN_DETAIL");
					// 明细数据 查询
					List<Map<String,Object>> detailList = medPrePayMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 入库单明细查询
	 * @param entityMap
	 * @return
	 */
	public String queryMedInDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medPrePayMainMapper.queryMedInDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medPrePayMainMapper.queryMedInDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 新增时查询预付款单明细对应的预付款单主表的Id
	 * @return
	 */
	public Long queryMedPrePayMainMaxId() throws DataAccessException {
		return medPrePayMainMapper.queryMedPrePayMainMaxId();
	}
	/**
	 * 根据 入库单Id 查询入库单明细Id
	 * @param mapVo
	 * @return
	 */
	public List<Long> queryIn_detail_id(Map<String, Object> mapVo) throws DataAccessException {
		return medPrePayMainMapper.queryIn_detail_id(mapVo);
	}
	
	/**
	 * 多表联合查询 预付款单详细信息（修改页面回值用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedPrePayMainReturnUpdate(Map<String, Object> mapVo) throws DataAccessException {
		return medPrePayMainMapper.queryMedPrePayMainReturnUpdate(mapVo);
	}
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 */
	public String updateMedPrePayMainState(List<Map<String, Object>> entityList) {
		try {
			
			  medPrePayMainMapper.updateMedPrePayMainState(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"操作过程出错，操作失败 请联系管理员! 方法 updateMedPrePayMainState\"}";

			}	
	}
	/**
	 * 生成预付款单号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setMedPrePayMainNo(Map<String, Object> mapVo) throws DataAccessException {
		
		mapVo.put("table_code", "MED_PRE_PAY_MAIN");
		mapVo.put("prefixe", "YF");
		
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
	public String queryPrePayByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=medPrePayMainMapper.queryPrePayPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=medPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				
				//查询入库主表
				Map<String,Object> map=medPrePayMainMapper.queryPrePayPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=medPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	public Map<String, Object> queryPrePayByPrintTemlateNew(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MedPrePayMainMapper medPrePayMainMapper = (MedPrePayMainMapper)context.getBean("medPrePayMainMapper");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=medPrePayMainMapper.queryPrePayPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=medPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				mapVo.put("main", map);
				mapVo.put("detail", list);
				return mapVo;
			}else{
				
				//查询入库主表
				Map<String,Object> map=medPrePayMainMapper.queryPrePayPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=medPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				mapVo.put("main", map);
				mapVo.put("detail", list);
				return mapVo;
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
}

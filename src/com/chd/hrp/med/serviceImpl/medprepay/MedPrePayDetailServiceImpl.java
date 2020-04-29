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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.medprepay.MedPrePayDetailMapper;
import com.chd.hrp.med.dao.medprepay.MedPrePayMainMapper;
import com.chd.hrp.med.entity.MedPrePayDetail;
import com.chd.hrp.med.service.medprepay.MedPrePayDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 保存一个预付款单对应的入库单，及金额
 * @Table:
 * MED_BILL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medPrePayDetailService")
public class MedPrePayDetailServiceImpl implements MedPrePayDetailService {

	private static Logger logger = Logger.getLogger(MedPrePayDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medPrePayDetailMapper")
	private final MedPrePayDetailMapper medPrePayDetailMapper = null;
	
	@Resource(name = "medPrePayMainMapper")
	private final MedPrePayMainMapper medPrePayMainMapper = null;
    
	/**
	 * @Description 
	 * 添加保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedPrePayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个预付款单对应的入库单，及金额
		MedPrePayDetail MedPrePayDetail = queryMedPrePayDetailByCode(entityMap);

		if (MedPrePayDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medPrePayDetailMapper.addMedPrePayDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedPrePayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPrePayDetailMapper.addBatchMedPrePayDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
		
	}
	
		/**
	 * @Description 
	 * 更新保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedPrePayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medPrePayDetailMapper.updateMedPrePayDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"eeror\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedPrePayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medPrePayDetailMapper.updateBatchMedPrePayDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"eeror\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedPrePayDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medPrePayDetailMapper.deleteMedPrePayDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedPrePayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPrePayDetailMapper.deleteBatchMedPrePayDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"更新失败\"}");
		}	
	}
	
	/**
	 * @Description 
	 * 添加保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedPrePayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个预付款单对应的入库单，及金额
		MedPrePayDetail MedPrePayDetail = queryMedPrePayDetailByCode(entityMap);

		if (MedPrePayDetail != null) {

			int state = medPrePayDetailMapper.updateMedPrePayDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medPrePayDetailMapper.addMedPrePayDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedPrePayDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedPrePayDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedPrePayDetail> list = medPrePayDetailMapper.queryMedPrePayDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedPrePayDetail> list = medPrePayDetailMapper.queryMedPrePayDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedPrePayDetail queryMedPrePayDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medPrePayDetailMapper.queryMedPrePayDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPrePayDetail
	 * @throws DataAccessException
	*/
	@Override
	public MedPrePayDetail queryMedPrePayDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medPrePayDetailMapper.queryMedPrePayDetailByUniqueness(entityMap);
	}
	/**
	 * 查询预付款单明细信息（多表联合查询）
	 * @param page
	 * @return
	 */
	public String queryMedPrePayIn(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medPrePayDetailMapper.queryMedPrePayIn(entityMap);
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
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medPrePayDetailMapper.queryMedPrePayIn(entityMap, rowBounds);
			
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
	 * 根据 预付款单ID 查询其明细中的 入库单ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedPrePayIn_id(Map<String, Object> mapVo) throws DataAccessException {
		return medPrePayDetailMapper.queryMedPrePayIn_id(mapVo);
	}
	
}

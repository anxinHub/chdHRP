/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.medpay;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.medpay.MedPayDetailMapper;
import com.chd.hrp.med.dao.medpay.MedPayMainMapper;
import com.chd.hrp.med.entity.MedPayDetail;
import com.chd.hrp.med.service.medpay.MedPayDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 保存一个付款单对应的发票
 * @Table:
 * MED_PAY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medPayDetailService")
public class MedPayDetailServiceImpl implements MedPayDetailService {

	private static Logger logger = Logger.getLogger(MedPayDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medPayDetailMapper")
	private final MedPayDetailMapper medPayDetailMapper = null;
	
	@Resource(name = "medPayMainMapper")
	private final MedPayMainMapper medPayMainMapper = null;
    
	/**
	 * @Description 
	 * 添加保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedPayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个付款单对应的发票
		MedPayDetail medPayDetail = queryMedPayDetailByCode(entityMap);

		if (medPayDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medPayDetailMapper.addMedPayDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
		
	}
	/**
	 * @Description 
	 * 批量添加保存一个付款单对应的发票<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedPayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPayDetailMapper.addBatchMedPayDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedPayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medPayDetailMapper.updateMedPayDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新保存一个付款单对应的发票<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedPayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medPayDetailMapper.updateBatchMedPayDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 删除保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedPayDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medPayDetailMapper.deleteMedPayDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除保存一个付款单对应的发票<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedPayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPayDetailMapper.deleteBatchMedPayDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedPayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个付款单对应的发票
		MedPayDetail medPayDetail = queryMedPayDetailByCode(entityMap);

		if (medPayDetail != null) {

			int state = medPayDetailMapper.updateMedPayDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medPayDetailMapper.addMedPayDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedPayDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedPayDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedPayDetail> list = medPayDetailMapper.queryMedPayDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedPayDetail> list = medPayDetailMapper.queryMedPayDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象保存一个付款单对应的发票<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedPayDetail queryMedPayDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medPayDetailMapper.queryMedPayDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取保存一个付款单对应的发票<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPayDetail
	 * @throws DataAccessException
	*/
	@Override
	public MedPayDetail queryMedPayDetailByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return medPayDetailMapper.queryMedPayDetailByUniqueness(entityMap);
	}
	/**
	 * 根据 付款单Id 查询对应的付款单明细
	 * @param entityMap
	 * @return
	 */
	public String queryMedPayDetailNew(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medPayDetailMapper.queryMedPayDetailNew(entityMap);
			
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
			
			List<Map<String,Object>> list = medPayDetailMapper.queryMedPayDetailNew(entityMap, rowBounds);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= medPayMainMapper.queryMedBillDetail_PayN(item);
				// 存放  明细数据 
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 根据 付款单ID 查询 其对应的发票ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedBillId(Map<String, Object> mapVo) throws DataAccessException{
		return medPayDetailMapper.queryMedBillId(mapVo);
	}
	
}

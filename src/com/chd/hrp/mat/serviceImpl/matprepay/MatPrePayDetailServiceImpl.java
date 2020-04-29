/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.matprepay;

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
import com.chd.hrp.mat.dao.matprepay.MatPrePayDetailMapper;
import com.chd.hrp.mat.dao.matprepay.MatPrePayMainMapper;
import com.chd.hrp.mat.entity.MatPrePayDetail;
import com.chd.hrp.mat.service.matprepay.MatPrePayDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 保存一个预付款单对应的入库单，及金额
 * @Table:
 * MAT_BILL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matPrePayDetailService")
public class MatPrePayDetailServiceImpl implements MatPrePayDetailService {

	private static Logger logger = Logger.getLogger(MatPrePayDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matPrePayDetailMapper")
	private final MatPrePayDetailMapper matPrePayDetailMapper = null;
	
	@Resource(name = "matPrePayMainMapper")
	private final MatPrePayMainMapper matPrePayMainMapper = null;
    
	/**
	 * @Description 
	 * 添加保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatPrePayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个预付款单对应的入库单，及金额
		MatPrePayDetail MatPrePayDetail = queryMatPrePayDetailByCode(entityMap);

		if (MatPrePayDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matPrePayDetailMapper.addMatPrePayDetail(entityMap);
			
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
	public String addBatchMatPrePayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPrePayDetailMapper.addBatchMatPrePayDetail(entityList);
			
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
	public String updateMatPrePayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matPrePayDetailMapper.updateMatPrePayDetail(entityMap);
			
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
	public String updateBatchMatPrePayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matPrePayDetailMapper.updateBatchMatPrePayDetail(entityList);
			
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
    public String deleteMatPrePayDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matPrePayDetailMapper.deleteMatPrePayDetail(entityMap);
			
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
	public String deleteBatchMatPrePayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPrePayDetailMapper.deleteBatchMatPrePayDetail(entityList);
			
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
	public String addOrUpdateMatPrePayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个预付款单对应的入库单，及金额
		MatPrePayDetail MatPrePayDetail = queryMatPrePayDetailByCode(entityMap);

		if (MatPrePayDetail != null) {

			int state = matPrePayDetailMapper.updateMatPrePayDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matPrePayDetailMapper.addMatPrePayDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatPrePayDetail\"}";

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
	public String queryMatPrePayDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatPrePayDetail> list = matPrePayDetailMapper.queryMatPrePayDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatPrePayDetail> list = matPrePayDetailMapper.queryMatPrePayDetail(entityMap, rowBounds);
			
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
	public MatPrePayDetail queryMatPrePayDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matPrePayDetailMapper.queryMatPrePayDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取保存一个预付款单对应的入库单，及金额<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPrePayDetail
	 * @throws DataAccessException
	*/
	@Override
	public MatPrePayDetail queryMatPrePayDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matPrePayDetailMapper.queryMatPrePayDetailByUniqueness(entityMap);
	}
	/**
	 * 查询预付款单明细信息（多表联合查询）
	 * @param page
	 * @return
	 */
	public String queryMatPrePayIn(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matPrePayDetailMapper.queryMatPrePayIn(entityMap);
			//附加  明细数据
			for(Map<String,Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MAT_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList= matPrePayMainMapper.queryMatInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}else{
					item.put("table", "MAT_IN_DETAIL");
					// 明细数据 查询
					List<Map<String,Object>> detailList = matPrePayMainMapper.queryMatInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matPrePayDetailMapper.queryMatPrePayIn(entityMap, rowBounds);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MAT_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList= matPrePayMainMapper.queryMatInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}else{
					item.put("table", "MAT_IN_DETAIL");
					// 明细数据 查询
					List<Map<String,Object>> detailList = matPrePayMainMapper.queryMatInDetail(item);
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
	public List<Map<String, Object>> queryMatPrePayIn_id(Map<String, Object> mapVo) throws DataAccessException {
		return matPrePayDetailMapper.queryMatPrePayIn_id(mapVo);
	}
	
}

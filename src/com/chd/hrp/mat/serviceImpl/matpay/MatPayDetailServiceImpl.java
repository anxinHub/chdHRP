/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.matpay;

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
import com.chd.hrp.mat.dao.matpay.MatPayDetailMapper;
import com.chd.hrp.mat.dao.matpay.MatPayMainMapper;
import com.chd.hrp.mat.entity.MatPayDetail;
import com.chd.hrp.mat.service.matpay.MatPayDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 保存一个付款单对应的发票
 * @Table:
 * MAT_PAY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matPayDetailService")
public class MatPayDetailServiceImpl implements MatPayDetailService {

	private static Logger logger = Logger.getLogger(MatPayDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matPayDetailMapper")
	private final MatPayDetailMapper matPayDetailMapper = null;
	
	@Resource(name = "matPayMainMapper")
	private final MatPayMainMapper matPayMainMapper = null;
    
	/**
	 * @Description 
	 * 添加保存一个付款单对应的发票<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatPayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个付款单对应的发票
		MatPayDetail matPayDetail = queryMatPayDetailByCode(entityMap);

		if (matPayDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matPayDetailMapper.addMatPayDetail(entityMap);
			
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
	public String addBatchMatPayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPayDetailMapper.addBatchMatPayDetail(entityList);
			
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
	public String updateMatPayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matPayDetailMapper.updateMatPayDetail(entityMap);
			
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
	public String updateBatchMatPayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matPayDetailMapper.updateBatchMatPayDetail(entityList);
			
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
    public String deleteMatPayDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matPayDetailMapper.deleteMatPayDetail(entityMap);
			
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
	public String deleteBatchMatPayDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPayDetailMapper.deleteBatchMatPayDetail(entityList);
			
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
	public String addOrUpdateMatPayDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存一个付款单对应的发票
		MatPayDetail matPayDetail = queryMatPayDetailByCode(entityMap);

		if (matPayDetail != null) {

			int state = matPayDetailMapper.updateMatPayDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matPayDetailMapper.addMatPayDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatPayDetail\"}";

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
	public String queryMatPayDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatPayDetail> list = matPayDetailMapper.queryMatPayDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatPayDetail> list = matPayDetailMapper.queryMatPayDetail(entityMap, rowBounds);
			
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
	public MatPayDetail queryMatPayDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matPayDetailMapper.queryMatPayDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取保存一个付款单对应的发票<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPayDetail
	 * @throws DataAccessException
	*/
	@Override
	public MatPayDetail queryMatPayDetailByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return matPayDetailMapper.queryMatPayDetailByUniqueness(entityMap);
	}
	/**
	 * 根据 付款单Id 查询对应的付款单明细
	 * @param entityMap
	 * @return
	 */
	public String queryMatPayDetailNew(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matPayDetailMapper.queryMatPayDetailNew(entityMap);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= matPayMainMapper.queryMatBillDetail_Pay(item);
				// 存放  明细数据 
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matPayDetailMapper.queryMatPayDetailNew(entityMap, rowBounds);
			
			//附加  明细数据
			for(Map<String,Object> item : list){
				// 明细数据 查询
				List<Map<String,Object>> detailList= matPayMainMapper.queryMatBillDetail_PayN(item);
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
	public List<Map<String, Object>> queryMatBillId(Map<String, Object> mapVo) throws DataAccessException{
		return matPayDetailMapper.queryMatBillId(mapVo);
	}
	
}

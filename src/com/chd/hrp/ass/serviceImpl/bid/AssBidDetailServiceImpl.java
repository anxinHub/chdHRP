/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.bid;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.bid.AssBidDetailMapper;
import com.chd.hrp.ass.dao.bid.AssBidMainMapper;
import com.chd.hrp.ass.entity.bid.AssBidDetail;
import com.chd.hrp.ass.entity.bid.AssBidMain;
import com.chd.hrp.ass.service.bid.AssBidDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050401 招标资产明细
 * @Table:
 * ASS_BID_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assBidDetailService")
public class AssBidDetailServiceImpl implements AssBidDetailService {

	private static Logger logger = Logger.getLogger(AssBidDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBidDetailMapper")
	private final AssBidDetailMapper assBidDetailMapper = null;
    
	@Resource(name = "assBidMainMapper")
	private final AssBidMainMapper assBidMainMapper = null;
	
	/**
	 * @Description 
	 * 添加050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssBidDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050401 招标资产明细
		AssBidDetail assBidDetail = queryAssBidDetailByCode(entityMap);

		if (assBidDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assBidDetailMapper.addAssBidDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050401 招标资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssBidDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidDetailMapper.addBatchAssBidDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchAssBidDetail\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssBidDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBidDetailMapper.updateAssBidDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050401 招标资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssBidDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBidDetailMapper.updateBatchAssBidDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssBidDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBidDetailMapper.deleteAssBidDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050401 招标资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssBidDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidDetailMapper.deleteBatchAssBidDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssBidDetail(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
//		//判断是否存在对象050401 招标资产明细
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("ass_code", entityMap.get("ass_code")); 
    	mapVo.put("ass_model", entityMap.get("ass_model"));
    	mapVo.put("ass_spec", entityMap.get("ass_spec"));
    	mapVo.put("ass_brand",entityMap.get("ass_brand"));
    	mapVo.put("fac_id", entityMap.get("fac_id"));
    	mapVo.put("bid_id", entityMap.get("bid_id"));
    	 
		
		AssBidDetail assBidMain=queryAssBidDetailByCode(entityMap);
		 
		if (assBidMain !=null ) {

			int state = assBidDetailMapper.updateAssBidDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			List<AssBidMain> list = (List<AssBidMain>) assBidDetailMapper.queryAssBidMainByID(mapVo);
			if(list.size()>0){
				
				return "{\"error\":\"资产信息重复.\"}";
			}
			int state = assBidDetailMapper.addAssBidDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssBidDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBidDetail> list = assBidDetailMapper.queryAssBidDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBidDetail> list = assBidDetailMapper.queryAssBidDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssBidDetail queryAssBidDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBidDetailMapper.queryAssBidDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidDetail
	 * @throws DataAccessException
	*/
	@Override
	public AssBidDetail queryAssBidDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBidDetailMapper.queryAssBidDetailByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssBidDetail> queryAssBidDetailExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBidDetailMapper.queryAssBidDetailExists(entityMap);
	}
	@Override
	public List<AssBidDetail> queryAssBidDetailList(
			Map<String, Object> entityMap) throws DataAccessException {
		List<AssBidDetail> list = assBidDetailMapper.queryAssBidDetailByList(entityMap);
		return list;
	}
	
}

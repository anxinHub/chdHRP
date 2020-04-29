/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.mat.serviceImpl.protocol;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.protocol.MatProtocolDetailMapper;
import com.chd.hrp.mat.entity.MatProtocolDetail;
import com.chd.hrp.mat.service.protocol.MatProtocolDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04503 付款协议明细表
 * @Table:
 * MAT_PROTOCOL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matProtocolDetailService")
public class MatProtocolDetailServiceImpl implements MatProtocolDetailService {

	private static Logger logger = Logger.getLogger(MatProtocolDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matProtocolDetailMapper")
	private final MatProtocolDetailMapper matProtocolDetailMapper = null;
    
	/**
	 * @Description 
	 * 添加04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatProtocolDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04503 付款协议明细表
		MatProtocolDetail matProtocolDetail = queryMatProtocolDetailByCode(entityMap);

		if (matProtocolDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matProtocolDetailMapper.addMatProtocolDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatProtocolDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04503 付款协议明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatProtocolDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolDetailMapper.addBatchMatProtocolDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatProtocolDetail\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatProtocolDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matProtocolDetailMapper.updateMatProtocolDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatProtocolDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04503 付款协议明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatProtocolDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			Map<String,Object> entityMap = new HashMap<String,Object>();
			StringBuffer detailIds = new StringBuffer();
			for(int a = 0 ; a < entityList.size() ; a++){
				if(a == 0){
					entityMap.put("group_id", entityList.get(a).get("group_id"));
					entityMap.put("hos_id", entityList.get(a).get("hos_id"));
					entityMap.put("copy_code", entityList.get(a).get("copy_code").toString());
					entityMap.put("protocol_id", entityList.get(a).get("protocol_id"));
				}
				detailIds.append(entityList.get(a).get("detail_id").toString()).append(",");
			}
			
			if(detailIds.length() > 0){
				entityMap.put("detailIds", detailIds.substring(0,detailIds.length()-1));//明细IDS
				entityMap.put("detailIds", detailIds);
			}
			//先删除
			matProtocolDetailMapper.updateBatchMatProtocolDetail(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatProtocolDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatProtocolDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matProtocolDetailMapper.deleteMatProtocolDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatProtocolDetail\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04503 付款协议明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatProtocolDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolDetailMapper.deleteBatchMatProtocolDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatProtocolDetail\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatProtocolDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04503 付款协议明细表
		MatProtocolDetail matProtocolDetail = queryMatProtocolDetailByCode(entityMap);

		if (matProtocolDetail != null) {

			int state = matProtocolDetailMapper.updateMatProtocolDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matProtocolDetailMapper.addMatProtocolDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatProtocolDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatProtocolDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatProtocolDetail> list = matProtocolDetailMapper.queryMatProtocolDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatProtocolDetail> list = matProtocolDetailMapper.queryMatProtocolDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04503 付款协议明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatProtocolDetail queryMatProtocolDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matProtocolDetailMapper.queryMatProtocolDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04503 付款协议明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolDetail
	 * @throws DataAccessException
	*/
	@Override
	public MatProtocolDetail queryMatProtocolDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matProtocolDetailMapper.queryMatProtocolDetailByUniqueness(entityMap);
	}
	
}

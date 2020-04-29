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
import com.chd.hrp.mat.dao.protocol.MatProtocolTypeMapper;
import com.chd.hrp.mat.entity.MatProtocolType;
import com.chd.hrp.mat.service.protocol.MatProtocolTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04501 付款协议类别
 * @Table:
 * MAT_PROTOCOL_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matProtocolTypeService")
public class MatProtocolTypeServiceImpl implements MatProtocolTypeService {

	private static Logger logger = Logger.getLogger(MatProtocolTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matProtocolTypeMapper")
	private final MatProtocolTypeMapper matProtocolTypeMapper = null;
	
	/**
	 * @Description 
	 * 添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatProtocolType(Map<String,Object> entityMap)throws DataAccessException{
		//获取当前物流管理系统的启用年月
		Date current =new Date();
		Map<String, Object> paraMap = matProtocolTypeMapper.queryYearMonth(entityMap);
		if(Integer.parseInt(String.valueOf(current.getYear())+String.valueOf(current.getMonth())) > 
			Integer.parseInt(String.valueOf(paraMap.get("start_year"))+String.valueOf(paraMap.get("start_month")))){
			return ("协议类别维护日期必须在当前物流管理系统的启用年月:"+ paraMap.get("start_year") + paraMap.get("start_month") + "之前！");
		}
		
		//获取对象04501 付款协议类别
		List<MatProtocolType> list = matProtocolTypeMapper.queryMatProtocolTypeByID(entityMap);

		if (list.size()>0) {
			for(MatProtocolType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else if(item.getPre().equals(entityMap.get("pre"))){
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
					}else{
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+" 重复,请重新添加.\"}";
					}
				}else{
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else{
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}
					}
					
				}
			}
		}
		try {
			
			int state = matProtocolTypeMapper.addMatProtocolType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatProtocolType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04501 付款协议类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatProtocolType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolTypeMapper.addBatchMatProtocolType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatProtocolType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatProtocolType(Map<String,Object> entityMap)throws DataAccessException{
		//获取当前物流管理系统的启用年月
		Date current =new Date();
		Map<String, Object> paraMap = matProtocolTypeMapper.queryYearMonth(entityMap);
		if(Integer.parseInt(String.valueOf(current.getYear())+String.valueOf(current.getMonth())) > 
			Integer.parseInt(String.valueOf(paraMap.get("start_year"))+String.valueOf(paraMap.get("start_month")))){
			return ("协议类别维护日期必须在当前物流管理系统的启用年月:"+ paraMap.get("start_year") + paraMap.get("start_month") + "之前！");
		}
		
		List<MatProtocolType> list = matProtocolTypeMapper.queryMatProtocolTypeByID(entityMap);

		if (list.size()>0) {
			for(MatProtocolType item : list){
				if(item.getType_code().equals(entityMap.get("type_code"))){
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else if(item.getPre().equals(entityMap.get("pre"))){
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
					}else{
						return "{\"error\":\"类别代码:"+entityMap.get("type_code")+" 重复,请重新添加.\"}";
					}
				}else{
					if(item.getType_name().equals(entityMap.get("type_name"))){
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+",协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}else{
							return "{\"error\":\"类别名称:"+entityMap.get("type_name")+" 重复,请重新添加.\"}";
						}
					}else{
						if(item.getPre().equals(entityMap.get("pre"))){
							return "{\"error\":\"协议前缀:"+entityMap.get("pre")+" 重复,请重新添加.\"}";
						}
					}
					
				}
			}
		}
		try {
			
		  int state = matProtocolTypeMapper.updateMatProtocolType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败  请联系管理员! 方法 updateMatProtocolType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04501 付款协议类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatProtocolType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matProtocolTypeMapper.updateBatchMatProtocolType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改过程出错，修改失败 请联系管理员! 方法 updateBatchMatProtocolType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatProtocolType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matProtocolTypeMapper.deleteMatProtocolType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatProtocolType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04501 付款协议类别<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatProtocolType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolTypeMapper.deleteBatchMatProtocolType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatProtocolType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatProtocolType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04501 付款协议类别
		MatProtocolType matProtocolType = queryMatProtocolTypeByCode(entityMap);

		if (matProtocolType != null) {

			int state = matProtocolTypeMapper.updateMatProtocolType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matProtocolTypeMapper.addMatProtocolType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatProtocolType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04501 付款协议类别<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatProtocolType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatProtocolType> list = matProtocolTypeMapper.queryMatProtocolType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatProtocolType> list = matProtocolTypeMapper.queryMatProtocolType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04501 付款协议类别<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatProtocolType queryMatProtocolTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matProtocolTypeMapper.queryMatProtocolTypeByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04501 付款协议类别<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolType
	 * @throws DataAccessException
	*/
	@Override
	public MatProtocolType queryMatProtocolTypeByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return matProtocolTypeMapper.queryMatProtocolTypeByUniqueness(entityMap);
	}
	public List<MatProtocolType> queryMatProtocolTypeByID(Map<String, Object> entityMap) throws DataAccessException{
		return matProtocolTypeMapper.queryMatProtocolTypeByID(entityMap);
	}
	/**
	 * 获取当前物流管理系统的启用年月
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryYearMonth(Map<String, Object> mapVo) throws DataAccessException{
		return matProtocolTypeMapper.queryYearMonth(mapVo);
	}
	
}

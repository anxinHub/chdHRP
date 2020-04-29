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
import com.chd.hrp.mat.dao.protocol.MatProtocolFileMapper;
import com.chd.hrp.mat.entity.MatProtocolFile;
import com.chd.hrp.mat.service.protocol.MatProtocolFileService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04503 协议文档信息
 * @Table:
 * MAT_PROTOCOL_FILE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matProtocolFileService")
public class MatProtocolFileServiceImpl implements MatProtocolFileService {

	private static Logger logger = Logger.getLogger(MatProtocolFileServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matProtocolFileMapper")
	private final MatProtocolFileMapper matProtocolFileMapper = null;
    
	/**
	 * @Description 
	 * 添加04503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04503 付款协议明细表
		MatProtocolFile MatProtocolFile = queryMatProtocolFileByCode(entityMap);

		if (MatProtocolFile != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matProtocolFileMapper.addMatProtocolFile(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatProtocolFile\"}";

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
	public String addBatchMatProtocolFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolFileMapper.addBatchMatProtocolFile(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatProtocolFile\"}";

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
	public String updateMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matProtocolFileMapper.updateMatProtocolFile(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatProtocolFile\"}";

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
	public String updateBatchMatProtocolFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolFileMapper.updateBatchMatProtocolFile(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatProtocolFile\"}";

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
    public String deleteMatProtocolFile(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matProtocolFileMapper.deleteMatProtocolFile(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatProtocolFile\"}";

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
	public String deleteBatchMatProtocolFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolFileMapper.deleteBatchMatProtocolFile(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatProtocolFile\"}";

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
	public String addOrUpdateMatProtocolFile(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04503 付款协议明细表
		MatProtocolFile MatProtocolFile = queryMatProtocolFileByCode(entityMap);

		if (MatProtocolFile != null) {

			int state = matProtocolFileMapper.updateMatProtocolFile(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matProtocolFileMapper.addMatProtocolFile(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatProtocolFile\"}";

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
	public String queryMatProtocolFile(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatProtocolFile> list = matProtocolFileMapper.queryMatProtocolFile(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatProtocolFile> list = matProtocolFileMapper.queryMatProtocolFile(entityMap, rowBounds);
			
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
	public MatProtocolFile queryMatProtocolFileByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matProtocolFileMapper.queryMatProtocolFileByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04503 付款协议明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolFile
	 * @throws DataAccessException
	*/
	@Override
	public MatProtocolFile queryMatProtocolFileByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matProtocolFileMapper.queryMatProtocolFileByUniqueness(entityMap);
	}
	/**
	 * 协议文档详细 信息（多表查询）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatProtocolFileByID(Map<String, Object> mapVo) throws DataAccessException{
		return matProtocolFileMapper.queryMatProtocolFileByID(mapVo);
	}
	
}

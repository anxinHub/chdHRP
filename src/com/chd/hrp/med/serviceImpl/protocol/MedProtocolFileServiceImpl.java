/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.med.serviceImpl.protocol;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.protocol.MedProtocolFileMapper;
import com.chd.hrp.med.entity.MedProtocolFile;
import com.chd.hrp.med.service.protocol.MedProtocolFileService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08503 协议文档信息
 * @Table:
 * MED_PROTOCOL_FILE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medProtocolFileService")
public class MedProtocolFileServiceImpl implements MedProtocolFileService {

	private static Logger logger = Logger.getLogger(MedProtocolFileServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medProtocolFileMapper")
	private final MedProtocolFileMapper medProtocolFileMapper = null;
    
	/**
	 * @Description 
	 * 添加08503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08503 付款协议明细表
		MedProtocolFile MedProtocolFile = queryMedProtocolFileByCode(entityMap);

		if (MedProtocolFile != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medProtocolFileMapper.addMedProtocolFile(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedProtocolFile\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08503 付款协议明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedProtocolFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medProtocolFileMapper.addBatchMedProtocolFile(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedProtocolFile\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medProtocolFileMapper.updateMedProtocolFile(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedProtocolFile\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08503 付款协议明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedProtocolFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medProtocolFileMapper.updateBatchMedProtocolFile(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedProtocolFile\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedProtocolFile(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medProtocolFileMapper.deleteMedProtocolFile(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedProtocolFile\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08503 付款协议明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedProtocolFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medProtocolFileMapper.deleteBatchMedProtocolFile(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedProtocolFile\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加08503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedProtocolFile(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08503 付款协议明细表
		MedProtocolFile MedProtocolFile = queryMedProtocolFileByCode(entityMap);

		if (MedProtocolFile != null) {

			int state = medProtocolFileMapper.updateMedProtocolFile(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medProtocolFileMapper.addMedProtocolFile(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedProtocolFile\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08503 付款协议明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedProtocolFile(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedProtocolFile> list = medProtocolFileMapper.queryMedProtocolFile(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedProtocolFile> list = medProtocolFileMapper.queryMedProtocolFile(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象08503 付款协议明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedProtocolFile queryMedProtocolFileByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medProtocolFileMapper.queryMedProtocolFileByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08503 付款协议明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedProtocolFile
	 * @throws DataAccessException
	*/
	@Override
	public MedProtocolFile queryMedProtocolFileByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medProtocolFileMapper.queryMedProtocolFileByUniqueness(entityMap);
	}
	/**
	 * 协议文档详细 信息（多表查询）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedProtocolFileByID(Map<String, Object> mapVo) throws DataAccessException{
		return medProtocolFileMapper.queryMedProtocolFileByID(mapVo);
	}
	
}

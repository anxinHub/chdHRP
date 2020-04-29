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
import com.chd.hrp.ass.dao.bid.AssBidFileMapper;
import com.chd.hrp.ass.entity.bid.AssBidFile;
import com.chd.hrp.ass.service.bid.AssBidFileService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050401 招标资产明细
 * @Table:
 * ASS_BID_File
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assBidFileService")
public class AssBidFileServiceImpl implements AssBidFileService {

	private static Logger logger = Logger.getLogger(AssBidFileServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBidFileMapper")
	private final AssBidFileMapper assBidFileMapper = null;
    
	/**
	 * @Description 
	 * 添加050401 招标资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssBidFile(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assBidFileMapper.addAssBidFile(entityMap);
			
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
	public String addBatchAssBidFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidFileMapper.addBatchAssBidFile(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

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
	public String updateAssBidFile(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBidFileMapper.updateAssBidFile(entityMap);
			
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
	public String updateBatchAssBidFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBidFileMapper.updateBatchAssBidFile(entityList);
			
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
    public String deleteAssBidFile(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBidFileMapper.deleteAssBidFile(entityMap);
			
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
	public String deleteBatchAssBidFile(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidFileMapper.deleteBatchAssBidFile(entityList);
			
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
	public String addOrUpdateAssBidFile(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
//		//判断是否存在对象050401 招标资产明细
//		Map<String, Object> mapVo=new HashMap<String, Object>();
//		mapVo.put("group_id",entityMap.get("group_id"));
//		mapVo.put("hos_id",entityMap.get("hos_id"));
//    	mapVo.put("copy_code", entityMap.get("copy_code"));
//    	mapVo.put("acct_year", entityMap.get("acct_year"));
//    	mapVo.put("bid_id", entityMap.get("bid_id"));
//		List<AssBidFile> list = assBidFileMapper.queryAssBidFileExists(mapVo);
		
		AssBidFile assBidMain=queryAssBidFileByCode(entityMap);
		 
		if (assBidMain !=null ) {

			int state = assBidFileMapper.updateAssBidFile(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assBidFileMapper.addAssBidFile(entityMap);
			
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
	public String queryAssBidFile(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBidFile> list = assBidFileMapper.queryAssBidFile(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBidFile> list = assBidFileMapper.queryAssBidFile(entityMap, rowBounds);
			
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
	public AssBidFile queryAssBidFileByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBidFileMapper.queryAssBidFileByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidFile
	 * @throws DataAccessException
	*/
	@Override
	public AssBidFile queryAssBidFileByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBidFileMapper.queryAssBidFileByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidFile>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssBidFile> queryAssBidFileExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBidFileMapper.queryAssBidFileExists(entityMap);
	}
	
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedStoreDetailMapper;
import com.chd.hrp.med.entity.MedStoreDetail;
import com.chd.hrp.med.service.info.basic.MedStoreDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08109 仓库对应关系明细表
 * @Table:
 * MED_STORE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medStoreDetailService")
public class MedStoreDetailServiceImpl implements MedStoreDetailService {

	private static Logger logger = Logger.getLogger(MedStoreDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medStoreDetailMapper")
	private final MedStoreDetailMapper medStoreDetailMapper = null;
    
	/**
	 * @Description 
	 * 添加08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedStoreDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08109 仓库对应关系明细表
		MedStoreDetail medStoreDetail = queryMedStoreDetailByCode(entityMap);

		if (medStoreDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medStoreDetailMapper.addMedStoreDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStoreDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08109 仓库对应关系明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedStoreDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		for(Map<String,Object> item : entityList){
			MedStoreDetail medStoreDetail = queryMedStoreDetailByCode(item);

			if (medStoreDetail != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";
			}
		}
		
		try {
			
			medStoreDetailMapper.addBatchMedStoreDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStoreDetail\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedStoreDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medStoreDetailMapper.updateMedStoreDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedStoreDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08109 仓库对应关系明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedStoreDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medStoreDetailMapper.updateBatchMedStoreDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedStoreDetail\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedStoreDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medStoreDetailMapper.deleteMedStoreDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStoreDetail\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08109 仓库对应关系明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedStoreDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medStoreDetailMapper.deleteBatchMedStoreDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStoreDetail\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedStoreDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08109 仓库对应关系明细表
		MedStoreDetail medStoreDetail = queryMedStoreDetailByCode(entityMap);

		if (medStoreDetail != null) {

			int state = medStoreDetailMapper.updateMedStoreDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medStoreDetailMapper.addMedStoreDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedStoreDetail\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集08109 仓库对应关系明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedStoreDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedStoreDetail> list = medStoreDetailMapper.queryMedStoreDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedStoreDetail> list = medStoreDetailMapper.queryMedStoreDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象08109 仓库对应关系明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreDetail queryMedStoreDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreDetailMapper.queryMedStoreDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08109 仓库对应关系明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreDetail
	 * @throws DataAccessException
	*/
	@Override
	public MedStoreDetail queryMedStoreDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStoreDetailMapper.queryMedStoreDetailByUniqueness(entityMap);
	}
	
}

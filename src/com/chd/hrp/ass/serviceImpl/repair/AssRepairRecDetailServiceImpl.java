/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.repair;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.AssRepairRecDetailMapper;
import com.chd.hrp.ass.entity.repair.AssRepairApplyDetail;
import com.chd.hrp.ass.entity.repair.AssRepairRecDetail;
import com.chd.hrp.ass.service.repair.AssRepairRecDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051201 资产维修记录明细
 * @Table:
 * ASS_REPAIR_REC_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assRepairRecDetailService")
public class AssRepairRecDetailServiceImpl implements AssRepairRecDetailService {

	private static Logger logger = Logger.getLogger(AssRepairRecDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assRepairRecDetailMapper")
	private final AssRepairRecDetailMapper assRepairRecDetailMapper = null;
    
	/**
	 * @Description 
	 * 添加051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssRepairRecDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051201 资产维修记录明细
		AssRepairRecDetail assRepairRecDetail = queryAssRepairRecDetailByCode(entityMap);

		if (assRepairRecDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assRepairRecDetailMapper.addAssRepairRecDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051201 资产维修记录明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssRepairRecDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assRepairRecDetailMapper.addBatchAssRepairRecDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssRepairRecDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assRepairRecDetailMapper.updateAssRepairRecDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051201 资产维修记录明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssRepairRecDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assRepairRecDetailMapper.updateBatchAssRepairRecDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssRepairRecDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assRepairRecDetailMapper.deleteAssRepairRecDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051201 资产维修记录明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssRepairRecDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assRepairRecDetailMapper.deleteBatchAssRepairRecDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssRepairRecDetail(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051201 资产维修记录明细
		Map<String, Object> mapVo=new HashMap<String, Object>();

		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("rec_detail_id", entityMap.get("rec_detail_id"));
    	
       	validateMapVo.put("group_id", entityMap.get("group_id"));
       	
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	
    	validateMapVo.put("copy_code", entityMap.get("copy_code")); 
    	
    	validateMapVo.put("repair_rec_id", entityMap.get("repair_rec_id"));
		
    	validateMapVo.put("inv_code", entityMap.get("inv_code"));
    	
		List<AssRepairRecDetail> list = assRepairRecDetailMapper.queryAssRepairRecDetailExists(mapVo);
		
		if (list.size()>0) {

			int state = assRepairRecDetailMapper.updateAssRepairRecDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			List<AssRepairRecDetail> validateList = (List<AssRepairRecDetail>) assRepairRecDetailMapper.queryAssRepairRecById(validateMapVo);
			
			if(validateList.size() > 0){
			
				return "{\"error\":\"材料信息重复.\"}";
			
			}
			
			int state = assRepairRecDetailMapper.addAssRepairRecDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051201 资产维修记录明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssRepairRecDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssRepairRecDetail> list = assRepairRecDetailMapper.queryAssRepairRecDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRepairRecDetail> list = assRepairRecDetailMapper.queryAssRepairRecDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051201 资产维修记录明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssRepairRecDetail queryAssRepairRecDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairRecDetailMapper.queryAssRepairRecDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051201 资产维修记录明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRepairRecDetail
	 * @throws DataAccessException
	*/
	@Override
	public AssRepairRecDetail queryAssRepairRecDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairRecDetailMapper.queryAssRepairRecDetailByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051201 资产维修记录明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRepairRecDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssRepairRecDetail> queryAssRepairRecDetailExists(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairRecDetailMapper.queryAssRepairRecDetailExists(entityMap);
	}
	
}

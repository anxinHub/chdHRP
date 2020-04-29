/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.disposal;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.disposal.AssDisposalDetailMapper;
import com.chd.hrp.ass.entity.disposal.AssDisposalDetail;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecItem;
import com.chd.hrp.ass.service.disposal.AssDisposalDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051001 资产处置明细
 * @Table:
 * ASS_DISPOSAL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assDisposalDetailService")
public class AssDisposalDetailServiceImpl implements AssDisposalDetailService {

	private static Logger logger = Logger.getLogger(AssDisposalDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDisposalDetailMapper")
	private final AssDisposalDetailMapper assDisposalDetailMapper = null;
    
	/**
	 * @Description 
	 * 添加051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssDisposalDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051001 资产处置明细
		AssDisposalDetail assDisposalDetail = queryAssDisposalDetailByCode(entityMap);

		if (assDisposalDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assDisposalDetailMapper.addAssDisposalDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051001 资产处置明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssDisposalDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDisposalDetailMapper.addBatchAssDisposalDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssDisposalDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assDisposalDetailMapper.updateAssDisposalDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051001 资产处置明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssDisposalDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assDisposalDetailMapper.updateBatchAssDisposalDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssDisposalDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assDisposalDetailMapper.deleteAssDisposalDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051001 资产处置明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssDisposalDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assDisposalDetailMapper.deleteBatchAssDisposalDetail(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssDisposalDetail(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/ 
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("acct_year", entityMap.get("acct_year"));
    	
    	mapVo.put("ass_dis_id", entityMap.get("ass_dis_id"));
    	
    	mapVo.put("ass_detail_id", entityMap.get("ass_detail_id"));
    	
    	Map<String, Object> validateMapVo =new HashMap<String, Object>();
    	
    	validateMapVo.put("group_id",entityMap.get("group_id"));
    	
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	
    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	validateMapVo.put("ass_dis_id",entityMap.get("ass_dis_id"));
    	
    	validateMapVo.put("ass_card_no",entityMap.get("ass_card_no"));
    	
    	List<AssDisposalDetail> list = (List<AssDisposalDetail>) assDisposalDetailMapper.queryAssDisposalDetailExists(mapVo);
    	 
		if (list.size() >0) {

			int state = assDisposalDetailMapper.updateAssDisposalDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			List<AssDisposalDetail> list1 = (List<AssDisposalDetail>) assDisposalDetailMapper.queryByAssDisposalDetailId(validateMapVo);
			
			if (list1.size()>0){
				 
				 return "{\"error\":\"资产卡片号重复.\",\"state\":\"true\"}";
			 }
			
			
			int state = assDisposalDetailMapper.addAssDisposalDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051001 资产处置明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssDisposalDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDisposalDetail> list = null;
			
			if (entityMap.get("ass_nature").equals("02")){ 
				
				list = assDisposalDetailMapper.queryDisposalDetailSpecial(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("03")){
				 
				list = assDisposalDetailMapper.queryDisposalDetailGeneral(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("01")){
				
				list = assDisposalDetailMapper.queryDisposalDetailHouse(entityMap);
			
				}
			if (entityMap.get("ass_nature").equals("04")){
				
				list = assDisposalDetailMapper.queryDisposalDetailOther(entityMap);
			
				} 
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDisposalDetail> list = null;
			
			if (entityMap.get("ass_nature").equals("02")){ 
				
				list = assDisposalDetailMapper.queryDisposalDetailSpecial(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("03")){
				 
				list = assDisposalDetailMapper.queryDisposalDetailGeneral(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("01")){
				
				list = assDisposalDetailMapper.queryDisposalDetailHouse(entityMap, rowBounds);
			
				}
			if (entityMap.get("ass_nature").equals("04")){
				
				list = assDisposalDetailMapper.queryDisposalDetailOther(entityMap, rowBounds);
			
				} 
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051001 资产处置明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssDisposalDetail queryAssDisposalDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assDisposalDetailMapper.queryAssDisposalDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051001 资产处置明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssDisposalDetail
	 * @throws DataAccessException
	*/
	@Override
	public AssDisposalDetail queryAssDisposalDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assDisposalDetailMapper.queryAssDisposalDetailByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051001 资产处置明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssDisposalDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssDisposalDetail> queryAssDisposalDetailExists(Map<String,Object> entityMap)throws DataAccessException{
		return assDisposalDetailMapper.queryAssDisposalDetailExists(entityMap);
	}
	@Override
	public String initAssDisposalDetail(Map<String, Object> entityMap)
			throws DataAccessException {
        try {
			
			int state = assDisposalDetailMapper.initAssDisposalDetail(entityMap);
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.maintain;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanAssMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanItemMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanAssService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051202 保养计划资产明细
 * @Table:
 * ASS_MAINTAIN_PLAN_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assMaintainPlanAssService")
public class AssMaintainPlanAssServiceImpl implements AssMaintainPlanAssService { 

	private static Logger logger = Logger.getLogger(AssMaintainPlanAssServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMaintainPlanAssMapper")
	private final AssMaintainPlanAssMapper assMaintainPlanAssMapper = null;
	
	@Resource(name = "assMaintainPlanItemMapper")
	private final AssMaintainPlanItemMapper assMaintainPlanItemMapper = null;
    
	/**
	 * @Description 
	 * 添加051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMaintainPlanAss(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051202 保养计划资产明细
		AssMaintainPlanAss assMaintainPlanAss = queryAssMaintainPlanAssByCode(entityMap);

		if (assMaintainPlanAss != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMaintainPlanAssMapper.addAssMaintainPlanAss(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051202 保养计划资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMaintainPlanAss(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainPlanAssMapper.addBatchAssMaintainPlanAss(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMaintainPlanAss(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMaintainPlanAssMapper.updateAssMaintainPlanAss(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051202 保养计划资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMaintainPlanAss(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMaintainPlanAssMapper.updateBatchAssMaintainPlanAss(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMaintainPlanAss(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMaintainPlanAssMapper.deleteAssMaintainPlanAss(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051202 保养计划资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMaintainPlanAss(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainPlanAssMapper.deleteBatchAssMaintainPlanAss(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssMaintainPlanAss(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051202 保养计划资产明细
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssMaintainPlanAss> list = assMaintainPlanAssMapper.queryAssMaintainPlanAssExists(mapVo);
		
		if (list != null) {

			int state = assMaintainPlanAssMapper.updateAssMaintainPlanAss(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assMaintainPlanAssMapper.addAssMaintainPlanAss(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051202 保养计划资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMaintainPlanAss(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainPlanAss> list = assMaintainPlanAssMapper.queryAssMaintainPlanAss(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainPlanAss> list = assMaintainPlanAssMapper.queryAssMaintainPlanAss(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051202 保养计划资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainPlanAss queryAssMaintainPlanAssByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanAssMapper.queryAssMaintainPlanAssByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051202 保养计划资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainPlanAss
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainPlanAss queryAssMaintainPlanAssByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanAssMapper.queryAssMaintainPlanAssByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051202 保养计划资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainPlanAss>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssMaintainPlanAss> queryAssMaintainPlanAssExists(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanAssMapper.queryAssMaintainPlanAssExists(entityMap);
	}
	@Override
	public String addOrUpdateAssMaintainDetail(Map<String, Object> entityMap) {

		//获取保养计划明细
		Map<String, Object> mapVo = new HashMap<String, Object>();
		Map<String, Object> inMapVo = new HashMap<String, Object>();
		Map<String, Object> validateMapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("detail_id", entityMap.get("detail_id"));
		mapVo.put("plan_id", entityMap.get("plan_id"));
		inMapVo.put("group_id", entityMap.get("group_id"));
		inMapVo.put("hos_id", entityMap.get("hos_id"));
		inMapVo.put("copy_code", entityMap.get("copy_code"));
		inMapVo.put("plan_id", entityMap.get("plan_id"));
		validateMapVo.put("group_id", entityMap.get("group_id"));
		validateMapVo.put("hos_id", entityMap.get("hos_id"));
		validateMapVo.put("copy_code", entityMap.get("copy_code"));
		validateMapVo.put("ass_card_no", entityMap.get("ass_card_no"));
		validateMapVo.put("ass_no", entityMap.get("ass_no"));
		validateMapVo.put("ass_model", entityMap.get("ass_model"));
		validateMapVo.put("ass_spec", entityMap.get("ass_spec"));
		validateMapVo.put("ass_brand", entityMap.get("ass_brand"));
		validateMapVo.put("fac_id", entityMap.get("fac_id"));
		validateMapVo.put("plan_id", entityMap.get("plan_id"));
		// AssAcceptDetail assAcceptDetail =
		// queryAssAcceptDetailByCode(entityMap);
		List<AssMaintainPlanAss> list = assMaintainPlanAssMapper.queryAssMaintainPlanAssExists(mapVo);
//	    List<AssAcceptItem> listAss=assAcceptMainService.queryByAcceptId(entityMap);
	    
		try {
			if (list.size() > 0) {

				for (AssMaintainPlanAss assAcceptDetail : list) {
				 	String ass_card_no = assAcceptDetail.getAss_card_no().toString().substring(4,12);
				 	String asscard_no = entityMap.get("ass_card_no").toString().substring(4, 12);
					int ass_card_no1 = Integer.valueOf(ass_card_no);
					int asscard_no1 = Integer.valueOf(asscard_no);
					if (ass_card_no1 != asscard_no1) {
						assMaintainPlanItemMapper.deleteAssMaintaintItemByAssMaintainDetail(assAcceptDetail);

						int state = assMaintainPlanAssMapper.updateAssMaintainPlanAss(entityMap);
					}
				}
				// assAcceptDetailMapper.updateAssAcceptDetail(entityMap);

				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			} else {
				List<AssMaintainPlanAss> validateList = (List<AssMaintainPlanAss>) assMaintainPlanAssMapper
						.queryByAssMaintainPlanId(validateMapVo);
				if (validateList.size() > 0) {
					return "{\"error\":\"资产信息重复.\"}";
				}
				int state = assMaintainPlanAssMapper.addAssMaintainPlanAss(entityMap);

				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
}

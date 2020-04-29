/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.plan;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.plan.AssPlanDeptDetailMapper;
import com.chd.hrp.ass.dao.plan.AssPlanDeptMapper;
import com.chd.hrp.ass.dao.resource.AssPlanResourceMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
import com.chd.hrp.ass.service.plan.AssPlanDeptDetailService;
import com.chd.hrp.ass.service.resource.AssPlanResourceService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050301 资产购置计划明细表
 * @Table:
 * ASS_PLAN_DEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assPlanDeptDetailService")
public class AssPlanDeptDetailServiceImpl implements AssPlanDeptDetailService {

	private static Logger logger = Logger.getLogger(AssPlanDeptDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPlanDeptDetailMapper")
	private final AssPlanDeptDetailMapper assPlanDeptDetailMapper = null;
	@Resource(name = "assPlanDeptMapper")
	private final AssPlanDeptMapper assPlanDeptMapper = null;
	@Resource(name = "assPlanResourceMapper")
	private final AssPlanResourceMapper assPlanResourceMapper = null;
	@Resource(name = "assPlanResourceService")
	private final AssPlanResourceService assPlanResourceService = null;
	/**
	 * @Description 
	 * 添加050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assPlanDeptDetailMapper.addAssPlanDeptDetail(entityMap);
			Map<String, Object> inMapVo =new HashMap<String, Object>();
	    	inMapVo.put("group_id",entityMap.get("group_id"));
	    	inMapVo.put("hos_id",entityMap.get("hos_id"));
	    	inMapVo.put("copy_code", entityMap.get("copy_code"));
	    	inMapVo.put("plan_id", entityMap.get("plan_id"));
	    	inMapVo.put("plan_no", entityMap.get("plan_no"));
			List<AssPlanDeptDetail> details=assPlanDeptDetailMapper.queryByAssPlanDeptDetail(inMapVo);
			double plan_money=0;
			String plan_detail_id = null;
			for(AssPlanDeptDetail temp :  details ){
				plan_money += Double.parseDouble(temp.getPlan_amount().toString()) * temp.getAdvice_price();
				plan_detail_id=temp.getPlan_detail_id().toString();
			}
			inMapVo.put("plan_money", plan_money+"");
			assPlanDeptMapper.updateAssPlanDeptMoney(inMapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"plan_detail_id\":\""+plan_detail_id+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050301 资产购置计划明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssPlanDeptDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPlanDeptDetailMapper.addBatchAssPlanDeptDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assPlanDeptDetailMapper.updateAssPlanDeptDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050301 资产购置计划明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssPlanDeptDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assPlanDeptDetailMapper.updateBatchAssPlanDeptDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssPlanDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assPlanDeptDetailMapper.deleteAssPlanDeptDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050301 资产购置计划明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssPlanDeptDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		inMapVo.put("group_id",entityList.get(0).get("group_id"));
    	inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
    	inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
    	inMapVo.put("plan_id", entityList.get(0).get("plan_id"));
    	inMapVo.put("plan_no", entityList.get(0).get("plan_no"));
		try {
			
			assPlanDeptDetailMapper.deleteBatchAssPlanDeptDetail(entityList);
			
			List<AssPlanDeptDetail> details=assPlanDeptDetailMapper.queryByAssPlanDeptDetail(inMapVo);
			double plan_money=0;
			for(AssPlanDeptDetail temp :  details ){
				plan_money += Double.parseDouble(temp.getPlan_amount().toString()) * temp.getAdvice_price();
			}
			inMapVo.put("plan_money", plan_money+"");
			assPlanDeptMapper.updateAssPlanDeptMoney(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"plan_money\":\""+plan_money+"\"}";


		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException{
		
//		//获取对象050301 资产购置计划明细表
//		AssPlanDeptDetail assPlanDeptDetail = queryAssPlanDeptDetailByCode(entityMap);
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		entityMap.put("price", entityMap.get("sum_price"));
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("plan_detail_id", entityMap.get("plan_detail_id"));
    	inMapVo.put("group_id",entityMap.get("group_id"));
    	inMapVo.put("hos_id",entityMap.get("hos_id"));
    	inMapVo.put("copy_code", entityMap.get("copy_code"));
    	inMapVo.put("plan_id", entityMap.get("plan_id"));
    	inMapVo.put("plan_no", entityMap.get("plan_no"));
    	//验证是否为重复数据的Map
    	validateMapVo.put("group_id", entityMap.get("group_id"));
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
    	validateMapVo.put("ass_code", entityMap.get("ass_no"));
    	validateMapVo.put("ass_model", entityMap.get("ass_model"));
    	validateMapVo.put("ass_spec", entityMap.get("ass_spec"));
    	validateMapVo.put("ass_brand",entityMap.get("ass_brand"));
    	validateMapVo.put("fac_id", entityMap.get("fac_id"));
    	validateMapVo.put("plan_id", entityMap.get("plan_id"));
    	validateMapVo.put("ass_id", entityMap.get("ass_id"));
    	validateMapVo.put("ass_no", entityMap.get("ass_no"));
    	
    	
    	List<AssPlanDeptDetail> list=assPlanDeptDetailMapper.queryAssPlanDeptDetailExists(mapVo);

		if (list.size()>0) {
			for (AssPlanDeptDetail assPlanDeptDetail : list) {
				int ass_id_no=Integer.parseInt(assPlanDeptDetail.getAss_id().toString());
	         	   int assid_no=Integer.parseInt(entityMap.get("ass_id").toString());
				//Long ass_id=Long.parseLong(entityMap.get("ass_id").toString());
				
					assPlanResourceMapper.deleteAssAcceptItemAssApplyDeptDetail(assPlanDeptDetail);
					assPlanResourceMapper.addAssPlanSource(entityMap);
				
			}
			
			int state = assPlanDeptDetailMapper.updateAssPlanDeptDetail(entityMap);
			
			List<AssPlanDeptDetail> details=assPlanDeptDetailMapper.queryByAssPlanDeptDetail(inMapVo);
			double plan_money=0;
			int plan_detail_id = 0;
			for(AssPlanDeptDetail temp :  details ){
				plan_money += Double.parseDouble(temp.getPlan_amount().toString()) * temp.getAdvice_price();
				plan_detail_id=Integer.parseInt(String.valueOf(temp.getPlan_detail_id()));
			}
			inMapVo.put("plan_money", plan_money+"");
			inMapVo.put("plan_detail_id", plan_detail_id);
			assPlanDeptMapper.updateAssPlanDeptMoney(inMapVo);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"plan_money\":\""+plan_money+"\",\"plan_detail_id\":\"" + plan_detail_id
					+ "\"}";

		}
		
		try {
		
			List<AssPlanDeptDetail> validateList = (List<AssPlanDeptDetail>) assPlanDeptDetailMapper.queryByAssPlanDeptId(validateMapVo);
			if(validateList.size() > 0){
				return "{\"error\":\"资产信息重复.\"}";
			}
			
			int state = assPlanDeptDetailMapper.addAssPlanDeptDetail(entityMap);
			
			List<AssPlanDeptDetail> details = assPlanDeptDetailMapper.queryByAssPlanDeptDetail(inMapVo);
			double plan_money = 0;
			int plan_detail_id = 0;
			for(AssPlanDeptDetail temp : details){
				plan_money += Double.parseDouble(temp.getPlan_amount().toString()) * temp.getAdvice_price();
				plan_detail_id=Integer.parseInt(String.valueOf(temp.getPlan_detail_id()));
			}
			inMapVo.put("plan_money", plan_money+"");
			entityMap.put("plan_detail_id", plan_detail_id);
			assPlanResourceService.addAssPlanSource(entityMap);
			assPlanDeptMapper.updateAssPlanDeptMoney(inMapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"plan_money\":\""+plan_money+"\",\"plan_detail_id\":\"" + plan_detail_id
					+ "\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssPlanDeptDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPlanDeptDetail> list = assPlanDeptDetailMapper.queryAssPlanDeptDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPlanDeptDetail> list = assPlanDeptDetailMapper.queryAssPlanDeptDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050301 资产购置计划明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssPlanDeptDetail queryAssPlanDeptDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assPlanDeptDetailMapper.queryAssPlanDeptDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050301 资产购置计划明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssPlanDeptDetail
	 * @throws DataAccessException
	*/
	@Override
	public AssPlanDeptDetail queryAssPlanDeptDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assPlanDeptDetailMapper.queryAssPlanDeptDetailByUniqueness(entityMap);
	}
	@Override
	public List<AssPlanDeptDetail> queryAssPlanDeptDetailExists(
			Map<String, Object> entityMap) throws DataAccessException {
		return assPlanDeptDetailMapper.queryAssPlanDeptDetailExists(entityMap);
	}
	@Override
	public List<AssPlanDeptDetail> queryByAssPlanDeptDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		return assPlanDeptDetailMapper.queryByAssPlanDeptDetail(entityMap);
	}
	@Override
	public List<AssPlanDeptDetail> queryAssPlanDeptDetailList(
			Map<String, Object> entityMap) throws DataAccessException {
		return assPlanDeptDetailMapper.queryAssPlanDeptDetailList(entityMap);
	}
	@Override
	public List<AssPlanDeptDetail> queryAssPlanDeptDetailByBidList(Map<String, Object> entityMap)
			throws DataAccessException {
		return assPlanDeptDetailMapper.queryAssPlanDeptDetailByBidList(entityMap);
	}
	
}

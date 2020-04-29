/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.contract;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.contract.AssContractDetailMapper;
import com.chd.hrp.ass.dao.contract.AssContractMainMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.contract.AssContractDetail;
import com.chd.hrp.ass.service.contract.AssContractDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050501 资产合同明细
 * @Table:
 * ASS_CONTRACT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assContractDetailService")
public class AssContractDetailServiceImpl implements AssContractDetailService {

	private static Logger logger = Logger.getLogger(AssContractDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assContractDetailMapper")
	private final AssContractDetailMapper assContractDetailMapper = null;
	@Resource(name = "assContractMainMapper")
	private final AssContractMainMapper assContractMainMapper = null;
    
	/**
	 * @Description 
	 * 添加050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssContractDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050501 资产合同明细
		AssContractDetail assContractDetail = queryAssContractDetailByCode(entityMap);

		if (assContractDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assContractDetailMapper.addAssContractDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050501 资产合同明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssContractDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractDetailMapper.addBatchAssContractDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssContractDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assContractDetailMapper.updateAssContractDetail(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050501 资产合同明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssContractDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assContractDetailMapper.updateBatchAssContractDetail(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssContractDetail(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assContractDetailMapper.deleteAssContractDetail(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050501 资产合同明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssContractDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		inMapVo.put("group_id",entityList.get(0).get("group_id"));
    	inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
    	inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
    	inMapVo.put("contract_id", entityList.get(0).get("contract_id"));
    	inMapVo.put("contract_no", entityList.get(0).get("contract_no"));
    	
		try {
			
			assContractDetailMapper.deleteBatchAssContractDetail(entityList);
			
			List<AssContractDetail> details=assContractDetailMapper.queryByAssContractDetail(inMapVo);
			double contract_money=0;
			for(AssContractDetail temp :  details ){
				contract_money += Double.parseDouble(temp.getContract_amount().toString()) * temp.getContract_price();
			}
			inMapVo.put("contract_money", contract_money+"");
			assContractMainMapper.updateAssContractMain(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"contract_money\":\""+contract_money+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssContractDetail(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		Map<String, Object> mapVo=new HashMap<String, Object>();
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		Map<String, Object> mapVod =new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("contract_detail_id", entityMap.get("contract_detail_id"));
    	inMapVo.put("group_id",entityMap.get("group_id"));
    	inMapVo.put("hos_id",entityMap.get("hos_id"));
    	inMapVo.put("copy_code", entityMap.get("copy_code"));
    	inMapVo.put("contract_id", entityMap.get("contract_id"));
    	inMapVo.put("contract_no", entityMap.get("contract_no"));
    	inMapVo.put("ass_in_id", entityMap.get("ass_in_id"));
    	mapVod.put("group_id",entityMap.get("group_id"));
    	mapVod.put("hos_id",entityMap.get("hos_id"));
    	mapVod.put("copy_code", entityMap.get("copy_code"));
    	mapVod.put("ass_code",entityMap.get("ass_code"));
    	mapVod.put("ass_model",entityMap.get("ass_model"));
    	mapVod.put("ass_spec", entityMap.get("ass_spec"));
    	mapVod.put("ass_brand",entityMap.get("ass_brand"));
    	mapVod.put("fac_id", entityMap.get("fac_id"));
    	mapVod.put("contract_id", entityMap.get("contract_id"));
		List<AssContractDetail> list = assContractDetailMapper.queryAssContractDetailExists(mapVo);
		try {		
		if (list.size()>0) {

			int state = assContractDetailMapper.updateAssContractDetail(entityMap);
			
			List<AssContractDetail> details=assContractDetailMapper.queryByAssContractDetail(inMapVo);
			double contract_money=0;
			for(AssContractDetail temp :  details ){
				contract_money += Double.parseDouble(temp.getContract_amount().toString()) * temp.getContract_price();
			}
			inMapVo.put("contract_money", contract_money+"");
			assContractMainMapper.updateAssContractMain(inMapVo);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"contract_money\":\""+contract_money+"\"}";

		}else{
			List<AssContractDetail> listd=assContractDetailMapper.queryAssContractDetailExists(mapVod);
			if(listd.size()>0){
				return "{\"error\":\"添加数据重复.\"}";
			}
			
			int state = assContractDetailMapper.addAssContractDetail(entityMap);
			
			List<AssContractDetail> details=assContractDetailMapper.queryByAssContractDetail(inMapVo);
			double contract_money=0;
			for(AssContractDetail temp :  details ){
				contract_money += Double.parseDouble(temp.getContract_amount().toString()) * temp.getContract_price();
			}
			inMapVo.put("contract_money", contract_money+"");
			assContractMainMapper.updateAssContractMain(inMapVo);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"contract_money\":\""+contract_money+"\"}";
		}
	
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050501 资产合同明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssContractDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssContractDetail> list = assContractDetailMapper.queryAssContractDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssContractDetail> list = assContractDetailMapper.queryAssContractDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050501 资产合同明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssContractDetail queryAssContractDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assContractDetailMapper.queryAssContractDetailByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050501 资产合同明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractDetail
	 * @throws DataAccessException
	*/
	@Override
	public AssContractDetail queryAssContractDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assContractDetailMapper.queryAssContractDetailByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050501 资产合同明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssContractDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssContractDetail> queryAssContractDetailExists(Map<String,Object> entityMap)throws DataAccessException{
		return assContractDetailMapper.queryAssContractDetailExists(entityMap);
	}
	@Override
	public String queryAssContractDetailByUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,Object>> list = assContractDetailMapper.queryAssContractDetailByUpdate(entityMap);
		
		return ChdJson.toJson(list);
	}
	@Override
	public List<AssContractDetail> queryByAssContractDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return assContractDetailMapper.queryByAssContractDetail(entityMap);
	}
	@Override
	public List<AssContractDetail> queryAssContractDetailList(
			Map<String, Object> entityMap) throws DataAccessException {
		List<AssContractDetail> list = assContractDetailMapper.queryAssContractDetailList(entityMap);
		
		return list;
	}
	@Override
	public List<AssContractDetail> queryAssContractDetailHosList(Map<String, Object> entityMap)
			throws DataAccessException {
		return assContractDetailMapper.queryAssContractDetailHosList(entityMap);
	}
	
}

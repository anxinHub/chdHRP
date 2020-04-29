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
import com.chd.hrp.ass.dao.contract.AssContractMainMapper;
import com.chd.hrp.ass.entity.contract.AssContractMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.contract.AssContractMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050501 资产合同主表
 * @Table:
 * ASS_CONTRACT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assContractMainService")
public class AssContractMainServiceImpl implements AssContractMainService {

	private static Logger logger = Logger.getLogger(AssContractMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assContractMainMapper")
	private final AssContractMainMapper assContractMainMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	/**
	 * @Description 
	 * 添加050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssContractMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050501 资产合同主表
		AssContractMain assContractMain = queryAssContractMainByCode(entityMap);

		if (assContractMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assContractMainMapper.addAssContractMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050501 资产合同主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssContractMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractMainMapper.addBatchAssContractMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssContractMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assContractMainMapper.updateAssContractMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050501 资产合同主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssContractMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assContractMainMapper.updateBatchAssContractMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssContractMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assContractMainMapper.deleteAssContractMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050501 资产合同主表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssContractMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractMainMapper.deleteBatchAssContractMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssContractMain(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050501 资产合同主表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	//mapVo.put("acct_year", entityMap.get("acct_year"));
    	mapVo.put("contract_id", entityMap.get("contract_id"));
		List<AssContractMain> list = assContractMainMapper.queryAssContractMainExists(mapVo);
		
		
		if (list.size()>0) {

			int state = assContractMainMapper.updateAssContractMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"contract_id\":\""+entityMap.get("contract_id")+"\",\"contract_no\":\""+entityMap.get("contract_no")+"\"}";

		}
		
		try {
			entityMap.put("bill_table", "ASS_CONTRACT_MAIN");
			String contract_no=assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("contract_no", contract_no);
			int state = assContractMainMapper.addAssContractMain(entityMap);
			
			Long contract_id=queryAssContractMainSequence();
			
			if(state>0){
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"contract_id\":\""+contract_id+"\",\"contract_no\":\""+contract_no+"\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050501 资产合同主表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssContractMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssContractMain> list = assContractMainMapper.queryAssContractMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssContractMain> list = assContractMainMapper.queryAssContractMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050501 资产合同主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssContractMain queryAssContractMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assContractMainMapper.queryAssContractMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050501 资产合同主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractMain
	 * @throws DataAccessException
	*/
	@Override
	public AssContractMain queryAssContractMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assContractMainMapper.queryAssContractMainByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050501 资产合同主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssContractMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssContractMain> queryAssContractMainExists(Map<String,Object> entityMap)throws DataAccessException{
		return assContractMainMapper.queryAssContractMainExists(entityMap);
	}
	@Override
	public Long queryAssContractMainSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assContractMainMapper.queryAssContractMainSequence();
	}
	@Override
	public String updateToExamine(List<Map<String,Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  int state = assContractMainMapper.updateToExamine(entityMap);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateNotToExamine(List<Map<String,Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  int state = assContractMainMapper.updateNotToExamine(entityMap);
				
				return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateToPerform(List<Map<String,Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assContractMainMapper.updateToPerform(entityMap);
				
				return "{\"msg\":\"履行成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateNotToPerform(List<Map<String,Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assContractMainMapper.updateNotToPerform(entityMap);
				
				return "{\"msg\":\"取消履行成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateToFile(List<Map<String,Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assContractMainMapper.updateToFile(entityMap);
				
				return "{\"msg\":\"归档成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateNotToFile(List<Map<String,Object>> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assContractMainMapper.updateNotToFile(entityMap);
				
				return "{\"msg\":\"取消归档成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	
}

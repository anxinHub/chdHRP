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
import com.chd.hrp.ass.dao.contract.AssContractPaySetMapper;
import com.chd.hrp.ass.entity.contract.AssContractPaySet;
import com.chd.hrp.ass.service.contract.AssContractPaySetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050502 资产合同分期付款设置
 * @Table:
 * ASS_CONTRACT_PAY_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assContractPaySetService")
public class AssContractPaySetServiceImpl implements AssContractPaySetService {

	private static Logger logger = Logger.getLogger(AssContractPaySetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assContractPaySetMapper")
	private final AssContractPaySetMapper assContractPaySetMapper = null;
    
	/**
	 * @Description 
	 * 添加050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssContractPaySet(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050502 资产合同分期付款设置
		AssContractPaySet assContractPaySet = queryAssContractPaySetByCode(entityMap);

		if (assContractPaySet != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assContractPaySetMapper.addAssContractPaySet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050502 资产合同分期付款设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssContractPaySet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractPaySetMapper.addBatchAssContractPaySet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssContractPaySet(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assContractPaySetMapper.updateAssContractPaySet(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050502 资产合同分期付款设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssContractPaySet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assContractPaySetMapper.updateBatchAssContractPaySet(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssContractPaySet(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assContractPaySetMapper.deleteAssContractPaySet(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050502 资产合同分期付款设置<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssContractPaySet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractPaySetMapper.deleteBatchAssContractPaySet(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssContractPaySet(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050502 资产合同分期付款设置
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssContractPaySet> list = assContractPaySetMapper.queryAssContractPaySetExists(mapVo);
		
		if (list != null) {

			int state = assContractPaySetMapper.updateAssContractPaySet(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assContractPaySetMapper.addAssContractPaySet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050502 资产合同分期付款设置<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssContractPaySet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssContractPaySet> list = assContractPaySetMapper.queryAssContractPaySet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssContractPaySet> list = assContractPaySetMapper.queryAssContractPaySet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050502 资产合同分期付款设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssContractPaySet queryAssContractPaySetByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assContractPaySetMapper.queryAssContractPaySetByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050502 资产合同分期付款设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractPaySet
	 * @throws DataAccessException
	*/
	@Override
	public AssContractPaySet queryAssContractPaySetByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assContractPaySetMapper.queryAssContractPaySetByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050502 资产合同分期付款设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssContractPaySet>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssContractPaySet> queryAssContractPaySetExists(Map<String,Object> entityMap)throws DataAccessException{
		return assContractPaySetMapper.queryAssContractPaySetExists(entityMap);
	}
	
}

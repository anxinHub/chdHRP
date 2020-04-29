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
import com.chd.hrp.ass.dao.contract.AssContractBondMapper;
import com.chd.hrp.ass.entity.contract.AssContractBond;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.contract.AssContractBondService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050502 资产合同保证金
 * @Table:
 * ASS_CONTRACT_BOND
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

 
@Service("assContractBondService")
public class AssContractBondServiceImpl implements AssContractBondService {

	private static Logger logger = Logger.getLogger(AssContractBondServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assContractBondMapper")
	private final AssContractBondMapper assContractBondMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    
	/**
	 * @Description 
	 * 添加050502 资产合同保证金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050502 资产合同保证金
		AssContractBond assContractBond = queryByCode(entityMap);

		if (assContractBond != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assContractBondMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050502 资产合同保证金<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractBondMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050502 资产合同保证金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assContractBondMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050502 资产合同保证金<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assContractBondMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050502 资产合同保证金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assContractBondMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050502 资产合同保证金<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assContractBondMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050502 资产合同保证金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050502 资产合同保证金
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("bond_no", entityMap.get("bond_no").toString().equals("")?"0":entityMap.get("bond_no").toString());
		
		List<AssContractBond> list = (List<AssContractBond>)assContractBondMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assContractBondMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			entityMap.put("bill_table", "ASS_CONTRACT_BOND");
			String bond_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("bond_no", bond_no);
			int state = assContractBondMapper.add(entityMap);
			if(state > 0){
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"bond_no\":\""+bond_no+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050502 资产合同保证金<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssContractBond> list = (List<AssContractBond>)assContractBondMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssContractBond> list = (List<AssContractBond>)assContractBondMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050502 资产合同保证金<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assContractBondMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050502 资产合同保证金<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssContractBond
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assContractBondMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050502 资产合同保证金<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssContractBond>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assContractBondMapper.queryExists(entityMap);
	}
	
}

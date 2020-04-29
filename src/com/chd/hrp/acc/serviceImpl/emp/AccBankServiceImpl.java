/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.emp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.emp.AccBankMapper;
import com.chd.hrp.acc.entity.AccBank;
import com.chd.hrp.acc.service.emp.AccBankService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 银行信息<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accBankService")
public class AccBankServiceImpl implements AccBankService {

	private static Logger logger = Logger.getLogger(AccBankServiceImpl.class);
	
	@Resource(name = "accBankMapper")
	private final AccBankMapper accBankMapper = null;
    
	/**
	 * @Description 
	 * 银行信息<BR> 添加AccBank
	 * @param AccBusiType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccBank(Map<String,Object> entityMap)throws DataAccessException{
		
		AccBank accBank = queryAccBankByCode(entityMap);

		if (accBank != null) {

			return "{\"error\":\"编码：" + entityMap.get("bank_number").toString() + "重复.\"}";

		}
		
		try {
			
			AccBank accBanks = accBankMapper.queryAccBankBySortCode(entityMap);
			
			if(accBanks != null){
				
				entityMap.put("sort_code", accBanks.getSort_code()+10);
			}else{
				entityMap.put("sort_code",10);
			}
			
			accBankMapper.addAccBank(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccBank\"}";

		}

	}
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量添加AccBank
	 * @param  AccBusiType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccBank(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accBankMapper.addBatchAccBank(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccBank\"}";

		}
	}
	
	/**
	 * @Description 
	 * 银行信息<BR> 查询AccBank分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBank(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccBank> list = accBankMapper.queryAccBank(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccBank> list = accBankMapper.queryAccBank(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 银行信息<BR> 查询AccBankByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccBank queryAccBankByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accBankMapper.queryAccBankByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量删除AccBank
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccBank(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accBankMapper.deleteBatchAccBank(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccBank\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 银行信息<BR> 删除AccBank
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccBank(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accBankMapper.deleteAccBank(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccBank\"}";

		}
    }
	
	/**
	 * @Description 银行信息<BR>
	 *              更新AccBank
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAccBank(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (!entityMap.get("sort_code").toString().matches("\\d*")) {
				return "{\"warn\":\"排序号只能填数字.\",\"state\":\"false\"}";
			}

			accBankMapper.updateAccBank(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBank\"}";
		}
	}
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量更新AccBank
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccBank(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accBankMapper.updateBatchAccBank(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBank\"}";

		}
		
	}
	
	@Override
	public List<Map<String,Object>> queryAccBankPrint(Map<String,Object> entityMap) throws DataAccessException{
		
			entityMap.put("group_id", SessionManager.getGroupId());
			       
			entityMap.put("hos_id", SessionManager.getHosId());
		        
			entityMap.put("copy_code", SessionManager.getCopyCode());
				
			if(entityMap.get("bank_name") != null){
				//用来转换大写
				entityMap.put("bank_name",entityMap.get("bank_name").toString().toUpperCase());
			}		
			
			List<Map<String,Object>> list = accBankMapper.queryAccBankPrint(entityMap);
			
			return list;
		
	}
	
}

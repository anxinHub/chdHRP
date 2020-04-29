/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccBankIssuingMapper;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.service.wagedata.AccBankIssuingService;
import com.github.pagehelper.PageInfo;
 
/**
* @Title. @Description.
* 个人工资查询<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accBankIssuingService")
public class AccBankIssuingServiceImpl implements AccBankIssuingService {

	private static Logger logger = Logger.getLogger(AccBankIssuingServiceImpl.class);
	
	@Resource(name = "accBankIssuingMapper")
	private final AccBankIssuingMapper accBankIssuingMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	/**
	 * @Description 
	 * 个人工资查询<BR> 查询AccBankIssuing分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccBankIssuing(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = accBankIssuingMapper.queryAccBankIssuing(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = accBankIssuingMapper.queryAccBankIssuing(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 查询AccBankIssuingByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWagePay queryAccBankIssuingByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accBankIssuingMapper.queryAccBankIssuingByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 更新AccBankIssuing
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccBankIssuing(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accBankIssuingMapper.updateAccBankIssuing(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankIssuing\"}";

		}
	}
	
	/**
	 * @Description 
	 * 个人工资查询<BR> 批量更新AccBankIssuing
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccBankIssuing(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accBankIssuingMapper.updateBatchAccBankIssuing(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccBankIssuing\"}";

		}
		
	}
	
	public List<Map<String,Object>>  queryAccBankIssuingPrint(Map<String,Object> entityMap) throws DataAccessException{
		
			List<Map<String,Object>> list = accBankIssuingMapper.queryAccBankIssuing(entityMap);
			
			return list;
		
	}
		
}

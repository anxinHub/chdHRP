/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.serviceImpl.payable.loanmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrBegainDetMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrBegainMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrBegainDet;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrBegainDetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 期初借款明细
 * @Table:
 * BUDG_BORR_BEGAIN_DET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgBorrBegainDetService")
public class BudgBorrBegainDetServiceImpl implements BudgBorrBegainDetService {

	private static Logger logger = Logger.getLogger(BudgBorrBegainDetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgBorrBegainDetMapper")
	private final BudgBorrBegainDetMapper budgBorrBegainDetMapper = null;
	
	@Resource(name = "budgBorrBegainMapper")
	private final BudgBorrBegainMapper budgBorrBegainMapper = null;
    
	/**
	 * @Description 
	 * 添加期初借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象期初借款明细
		BudgBorrBegainDet budgBorrBegainDet = queryByCode(entityMap);

		if (budgBorrBegainDet != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgBorrBegainDetMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加期初借款明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgBorrBegainDetMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新期初借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgBorrBegainDetMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新期初借款明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgBorrBegainDetMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除期初借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgBorrBegainDetMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除期初借款明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		Map<String, Object> inMapVo =new HashMap<String, Object>();
		inMapVo.put("group_id",entityList.get(0).get("group_id"));
    	inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
    	inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
    	inMapVo.put("begin_code", entityList.get(0).get("begin_code"));
		try {
			
			budgBorrBegainDetMapper.deleteBatch(entityList);
			double remain_amount = 0;
			List<BudgBorrBegainDet> remainList = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.queryExists(inMapVo);
			for(BudgBorrBegainDet temp : remainList){
				remain_amount += temp.getRemain_amount();
			}
			inMapVo.put("remain_amount", remain_amount+"");
			budgBorrBegainMapper.updateAmount(inMapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"remain_amount\":\""+remain_amount+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加期初借款明细<BR> 
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
		//判断是否存在对象期初借款明细
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("begin_code", entityMap.get("begin_code"));
    	mapVo.put("source_id", entityMap.get("source_id"));
    	mapVo.put("payment_item_id", entityMap.get("payment_item_id"));
    	
    	Map<String, Object> inMapVo=new HashMap<String, Object>();
    	inMapVo.put("group_id",entityMap.get("group_id"));
    	inMapVo.put("hos_id",entityMap.get("hos_id"));
    	inMapVo.put("copy_code", entityMap.get("copy_code"));
    	inMapVo.put("begin_code", entityMap.get("begin_code"));
		
		List<BudgBorrBegainDet> list = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.queryExists(mapVo);
		
		if (list.size() > 0) {
			
			int state = budgBorrBegainDetMapper.update(entityMap);
			double remain_amount = 0;
			List<BudgBorrBegainDet> remainList = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.queryExists(inMapVo);
			for(BudgBorrBegainDet temp : remainList){
				remain_amount += temp.getRemain_amount();
			}
			mapVo.put("remain_amount", remain_amount+"");
			budgBorrBegainMapper.updateAmount(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"remain_amount\":\""+remain_amount+"\"}";

		}
		
		try {
			
			int state = budgBorrBegainDetMapper.add(entityMap);
			double remain_amount = 0;
			List<BudgBorrBegainDet> remainList = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.queryExists(inMapVo);
			for(BudgBorrBegainDet temp : remainList){
				remain_amount += temp.getRemain_amount();
			}
			mapVo.put("remain_amount", remain_amount+"");
			budgBorrBegainMapper.updateAmount(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"remain_amount\":\""+remain_amount+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集期初借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgBorrBegainDet> list = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgBorrBegainDet> list = (List<BudgBorrBegainDet>)budgBorrBegainDetMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象期初借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgBorrBegainDetMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取期初借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgBorrBegainDet
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgBorrBegainDetMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取期初借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgBorrBegainDet>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgBorrBegainDetMapper.queryExists(entityMap);
	}
	
}

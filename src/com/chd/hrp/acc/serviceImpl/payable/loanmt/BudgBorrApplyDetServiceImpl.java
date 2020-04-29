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
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrApplyDetMapper;
import com.chd.hrp.acc.dao.payable.loanmt.BudgBorrApplyMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrApplyDet;
import com.chd.hrp.acc.entity.payable.BudgBorrBegainDet;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrApplyDetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 借款明细
 * @Table:
 * BUDG_BORR_Apply_DET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("budgBorrApplyDetService")
public class BudgBorrApplyDetServiceImpl implements BudgBorrApplyDetService {

	private static Logger logger = Logger.getLogger(BudgBorrApplyDetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgBorrApplyDetMapper")
	private final BudgBorrApplyDetMapper budgBorrApplyDetMapper = null;
	
	@Resource(name = "budgBorrApplyMapper")
	private final BudgBorrApplyMapper budgBorrApplyMapper = null;
    
	/**
	 * @Description 
	 * 添加借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象借款明细
		BudgBorrApplyDet budgBorrApplyDet = queryByCode(entityMap);

		if (budgBorrApplyDet != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgBorrApplyDetMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加借款明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgBorrApplyDetMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgBorrApplyDetMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新借款明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgBorrApplyDetMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgBorrApplyDetMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除借款明细<BR> 
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
    	inMapVo.put("apply_code", entityList.get(0).get("apply_code"));
		try {
			
			budgBorrApplyDetMapper.deleteBatch(entityList);
			double borrow_amount = 0;
			List<BudgBorrApplyDet> remainList = (List<BudgBorrApplyDet>)budgBorrApplyDetMapper.queryExists(inMapVo);
			for(BudgBorrApplyDet temp : remainList){
				borrow_amount += temp.getBorrow_amount();
			}
			inMapVo.put("borrow_amount", borrow_amount+"");
			budgBorrApplyMapper.updateAmount(inMapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"borrow_amount\":\""+borrow_amount+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加借款明细<BR> 
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
		//判断是否存在对象借款明细
		Map<String, Object> entityMapVo=new HashMap<String, Object>();
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("apply_code", entityMap.get("apply_code"));
    	mapVo.put("source_id", entityMap.get("source_id"));
    	mapVo.put("payment_item_id", entityMap.get("payment_item_id"));
    	
    	Map<String, Object> inMapVo=new HashMap<String, Object>();
    	inMapVo.put("group_id",entityMap.get("group_id"));
    	inMapVo.put("hos_id",entityMap.get("hos_id"));
    	inMapVo.put("copy_code", entityMap.get("copy_code"));
    	inMapVo.put("apply_code", entityMap.get("apply_code"));
		
		List<BudgBorrApplyDet> list = (List<BudgBorrApplyDet>)budgBorrApplyDetMapper.queryExists(mapVo);
		
		try {
			if (list.size() > 0) {
				
				int state = budgBorrApplyDetMapper.update(entityMap);
				double borrow_amount = 0;
				List<BudgBorrApplyDet> remainList = (List<BudgBorrApplyDet>)budgBorrApplyDetMapper.queryExists(inMapVo);
				for(BudgBorrApplyDet temp : remainList){
					borrow_amount += temp.getBorrow_amount();
				}
				mapVo.put("borrow_amount", borrow_amount+"");
				budgBorrApplyMapper.updateAmount(mapVo);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"borrow_amount\":\""+borrow_amount+"\"}";
	
			}
			int state = budgBorrApplyDetMapper.add(entityMap);
			double borrow_amount = 0;
			List<BudgBorrApplyDet> remainList = (List<BudgBorrApplyDet>)budgBorrApplyDetMapper.queryExists(inMapVo);
			for(BudgBorrApplyDet temp : remainList){
				borrow_amount += temp.getBorrow_amount();
			}
			mapVo.put("borrow_amount", borrow_amount+"");
			budgBorrApplyMapper.updateAmount(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"borrow_amount\":\""+borrow_amount+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集借款明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgBorrApplyDet> list = (List<BudgBorrApplyDet>)budgBorrApplyDetMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgBorrApplyDet> list = (List<BudgBorrApplyDet>)budgBorrApplyDetMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgBorrApplyDetMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgBorrApplyDet
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgBorrApplyDetMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgBorrApplyDet>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgBorrApplyDetMapper.queryExists(entityMap);
	}
	
}

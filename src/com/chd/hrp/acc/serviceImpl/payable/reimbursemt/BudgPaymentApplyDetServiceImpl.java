/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.serviceImpl.payable.reimbursemt;

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
import com.chd.hrp.acc.dao.payable.reimbursemt.BudgPaymentApplyDetMapper;
import com.chd.hrp.acc.dao.payable.reimbursemt.BudgPaymentApplyMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrApplyDet;
import com.chd.hrp.acc.entity.payable.BudgPaymentApplyDet;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyDetService;
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
 


@Service("budgPaymentApplyDetService")
public class BudgPaymentApplyDetServiceImpl implements BudgPaymentApplyDetService {

	private static Logger logger = Logger.getLogger(BudgPaymentApplyDetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgPaymentApplyDetMapper")
	private final BudgPaymentApplyDetMapper budgPaymentApplyDetMapper = null;
	
	@Resource(name = "budgPaymentApplyMapper")
	private final BudgPaymentApplyMapper budgPaymentApplyMapper = null;
    
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
		BudgPaymentApplyDet budgPaymentApplyDet = queryByCode(entityMap);

		if (budgPaymentApplyDet != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgPaymentApplyDetMapper.add(entityMap);
			
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
			
			budgPaymentApplyDetMapper.addBatch(entityList);
			
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
			
		  int state = budgPaymentApplyDetMapper.update(entityMap);
			
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
			
		  budgPaymentApplyDetMapper.updateBatch(entityList);
			
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
			
			int state = budgPaymentApplyDetMapper.delete(entityMap);
			
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
			
			budgPaymentApplyDetMapper.deleteBatch(entityList);
			double payment_amount = 0;
			double offset_amount = 0;
			double pay_amount = 0;
			List<BudgPaymentApplyDet> remainList = (List<BudgPaymentApplyDet>)budgPaymentApplyDetMapper.queryExists(inMapVo);
			for(BudgPaymentApplyDet temp : remainList){
				payment_amount += temp.getPayment_amount();
				offset_amount += temp.getOffset_amount();
				pay_amount += temp.getPay_amount();
			}
			inMapVo.put("payment_amount", payment_amount+"");
			inMapVo.put("offset_amount", offset_amount+"");
			inMapVo.put("pay_amount", pay_amount+"");
			budgPaymentApplyMapper.updateAmount(inMapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"payment_amount\":\""+payment_amount+"\",\"offset_amount\":\""+offset_amount+"\",\"pay_amount\":\""+pay_amount+"\"}";

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
		
		List<BudgPaymentApplyDet> list = (List<BudgPaymentApplyDet>)budgPaymentApplyDetMapper.queryExists(mapVo);
		
		if (list.size() > 0) {

			int state = budgPaymentApplyDetMapper.update(entityMap);
			double payment_amount = 0;
			double offset_amount = 0;
			double pay_amount = 0;
			List<BudgPaymentApplyDet> remainList = (List<BudgPaymentApplyDet>)budgPaymentApplyDetMapper.queryExists(inMapVo);
			for(BudgPaymentApplyDet temp : remainList){
				payment_amount += temp.getPayment_amount();
				offset_amount += temp.getOffset_amount();
				pay_amount += temp.getPay_amount();
			}
			mapVo.put("payment_amount", payment_amount+"");
			mapVo.put("offset_amount", offset_amount+"");
			mapVo.put("pay_amount", pay_amount+"");
			budgPaymentApplyMapper.updateAmount(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"payment_amount\":\""+payment_amount+"\",\"offset_amount\":\""+offset_amount+"\",\"pay_amount\":\""+pay_amount+"\"}";

		}
		
		try {
			
			int state = budgPaymentApplyDetMapper.add(entityMap);
			double payment_amount = 0;
			double offset_amount = 0;
			double pay_amount = 0;
			List<BudgPaymentApplyDet> remainList = (List<BudgPaymentApplyDet>)budgPaymentApplyDetMapper.queryExists(inMapVo);
			for(BudgPaymentApplyDet temp : remainList){
				payment_amount += temp.getPayment_amount();
				offset_amount += temp.getOffset_amount();
				if(temp.getPay_amount() !=null && !"".equals(temp.getPay_amount())){
					pay_amount += temp.getPay_amount();		
				}
			}
			mapVo.put("payment_amount", payment_amount+"");
			mapVo.put("offset_amount", offset_amount+"");
			mapVo.put("pay_amount", pay_amount+"");
			budgPaymentApplyMapper.updateAmount(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"payment_amount\":\""+payment_amount+"\",\"offset_amount\":\""+offset_amount+"\",\"pay_amount\":\""+pay_amount+"\"}";

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
			
			List<BudgPaymentApplyDet> list = (List<BudgPaymentApplyDet>)budgPaymentApplyDetMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgPaymentApplyDet> list = (List<BudgPaymentApplyDet>)budgPaymentApplyDetMapper.query(entityMap, rowBounds);
			
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
		return budgPaymentApplyDetMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgPaymentApplyDet
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentApplyDetMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取借款明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgPaymentApplyDet>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgPaymentApplyDetMapper.queryExists(entityMap);
	}
	@Override
	public String queryForUpdate(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HashMap<String,Object>> list = budgPaymentApplyDetMapper.queryForUpdateToMap(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HashMap<String,Object>> list = budgPaymentApplyDetMapper.queryForUpdateToMap(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
}

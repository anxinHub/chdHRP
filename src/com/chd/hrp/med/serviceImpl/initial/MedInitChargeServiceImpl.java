/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.initial;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.initial.MedInitChargeMapper;
import com.chd.hrp.med.service.initial.MedInitChargeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 期初记账
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medInitChargeService")
public class MedInitChargeServiceImpl implements MedInitChargeService {

	private static Logger logger = Logger.getLogger(MedInitChargeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medInitChargeMapper")
	private final MedInitChargeMapper medInitChargeMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
	/**
	 * @Description 
	 * 添加期初记账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			int flag = 0;
			//仓库是否已记账
			flag =  medInitChargeMapper.existsStoreIsAccount(entityMap);
			if(flag > 0){
				return "{\"error\":\"仓库已记账.\",\"state\":\"false\"}";
			}
			//记账日期是否大于当前账套起始日期
			flag =  medCommonMapper.existsDateCheckBefore(entityMap);
			if(flag > 0){
				return "{\"error\":\"记账日期不能大于当前账套起始日期.\",\"state\":\"false\"}";
			}
			//检测记账的物资期间是否存在
			flag = medCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"请配置物资期间表.\",\"state\":\"false\"}";
			}
			//查询是否存在未确认的期初入库单据
			flag = medInitChargeMapper.existsInitNotConfirmBill(entityMap);
			if(flag > 0){
				return "{\"error\":\"含有未入库确认的期初入库单据.\",\"state\":\"false\"}";
			}
			//检查是否存在期间小于记账期间的非期初入库业务的数据，如果存在，则不能记账
			flag = medInitChargeMapper.existsNotInitBill(entityMap);
			if(flag > 0){
				return "{\"error\":\"存在小于记账期间的非期初入库单据.\",\"state\":\"false\"}";
			}
			//新增入库主表数据
			medInitChargeMapper.add(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"记账失败\"}");
			//return "{\"error\":\"记账失败 数据库异常 请联系管理员! 方法 addMedInitCharge\"}";
		}
		
		return "{\"msg\":\"记账成功\",\"state\":\"true\"}";
	}
	/**
	 * @Description 
	 * 批量添加期初记账<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedInitCharge\"}";
		}
	}
	
		/**
	 * @Description 
	 * 更新期初记账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedInitCharge\"}";
		}		
	}
	/**
	 * @Description 
	 * 批量更新期初记账，无此业务<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedInitCharge\"}";
		}	
	}
	/**
	 * @Description 
	 * 删除期初记账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//暂无该业务	
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedInitCharge\"}";
		}	
  }
    
	/**
	 * @Description 
	 * 批量删除期初记账<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//暂无该业务
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedInitCharge\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 添加期初记账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedInitCharge\"}";
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集期初记账<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medInitChargeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medInitChargeMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象期初记账<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medInitChargeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取期初记账<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInitCharge
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medInitChargeMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}

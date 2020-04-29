/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgpur.matpur;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgpur.matpur.BudgMatPurCheckMapper;
import com.chd.hrp.budg.dao.common.BudgEditStateMapper;
import com.chd.hrp.budg.entity.BudgWorkCheck;
import com.chd.hrp.budg.service.budgpur.matpur.BudgMatPurCheckService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * @Table:
 * BUDG_MAT_PUR_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */


@Service("budgMatPurCheckService")
public class BudgMatPurCheckServiceImpl implements BudgMatPurCheckService {

	private static Logger logger = Logger.getLogger(BudgMatPurCheckServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMatPurCheckMapper")
	private final BudgMatPurCheckMapper budgMatPurCheckMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgEditStateMapper")
	private final BudgEditStateMapper budgEditStateMapper = null;
	
	/**
	 * @Description 
	 * 添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		
		try {
			
			int state = budgMatPurCheckMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加审批类型
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgMatPurCheckMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新审批类型
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgMatPurCheckMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新审批类型
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgMatPurCheckMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除审批类型
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
	    try {
	    	//校验 审批申请单 是否已调整
			int count = budgMatPurCheckMapper.queryIsAdjust(entityMap);
			
			//查询最大审批单号 （只允许删除最大审批单号的审批申请单）
			String check_code = budgMatPurCheckMapper.queryMaxCheckCode(entityMap);
			
			String err = "";//记录返回的错误信息
			
			if(count > 0){
				
				err += "该单据已做过调整,";
					
			}
			
			if(!check_code.equals(entityMap.get("check_code"))){
				
				err += "该单据审批单号不是最大审批单号";
			}
			
			if("".equals(err)){
				
				int state = budgMatPurCheckMapper.delete(entityMap);
				
				entityMap.put("table_code", "BUDG_MAT_PUR_CHECK");
				
				//修改 预算单据号管理表中 该年度 材料采购预算的最大单号
				budgMatPurCheckMapper.updateMaxNo(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"error\":\"删除失败."+err+"\",\"state\":\"false\"}";
			}
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败! 方法 delete\"}");
			
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgMatPurCheckMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加审批类型
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
		//判断是否存在对象审批类型（CHECK_TYPE）取自系统字典表：01初始审批、02调整审批预算审批状态（BC_STATE）取自系统字典表：“01新建、02已发送、03已审核、04已下达、05批中”，其中05审批中暂不使用



		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkCheck> list = (List<BudgWorkCheck>)budgMatPurCheckMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMatPurCheckMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMatPurCheckMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集审批类型
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象审批类型
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMatPurCheckMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取审批类型
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return 
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMatPurCheckMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取审批类型
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkCheck>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMatPurCheckMapper.queryExists(entityMap);
	}
	
	/**
	 * 发送 、 召回  修改状态 同时 材料采购可编辑标记
	 */
	@Override
	public String updateBc_state(List<Map<String, Object>> list) throws DataAccessException {
		
		try {
			Map<String,Object> entityMap = new HashMap<String,Object>();
			
			int  flag = 1 ; // 发送、召回标识  1：发送  2：召回
			
			for(Map<String,Object> item : list){
				
				if("01".equals(item.get("bc_state"))){//根据 状态 判断是 发送还是召回 操作 
					
					flag = 2 ;//召回
					
				}
				entityMap.put("group_id",item.get("group_id"));
				entityMap.put("hos_id",item.get("hos_id"));
				entityMap.put("copy_code",item.get("copy_code"));
				entityMap.put("year",item.get("budg_year"));
				break ;
			}
			
			if(flag == 1){//发送  材料采购预算可编辑标记 置为不可编辑状态 "0"
				
				entityMap.put("mat_pur_flag", "0");
				
				int count = budgEditStateMapper.queryDataExist(entityMap);
				
				if(count>0){
					
					budgEditStateMapper.updateMatPurFlag(entityMap);
					
				}else{
					
					entityMap.put("work_flag", "1");
					
					entityMap.put("med_income_flag", "1");
					
					entityMap.put("else_income_flag", "1");
					
					entityMap.put("med_cost_flag", "1");
					
					entityMap.put("else_cost_flag", "1");
					
					entityMap.put("med_pur_flag", "1");
					
					budgEditStateMapper.add(entityMap);
					
				}
				
			}else{//召回  材料采购预算可编辑标记 置为可编辑状态 "1"
				
				entityMap.put("mat_pur_flag", "1");
				
				budgEditStateMapper.updateMatPurFlag(entityMap);
				
			}
			budgMatPurCheckMapper.updateBc_state(list) ;
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
		
	}
	//审核 、 消审
	@Override
	public String auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			
			budgMatPurCheckMapper.auditOrUnAudit(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
	}
	
	/**
	 * 预算下达 、取消预算下达
	 */
	@Override
	public String cancelIssueOrIssue(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			
			int flag = 1 ;//预算下达 、取消预算下达 标识
			
			for(Map<String,Object> item : listVo){
				
				if("03".equals(item.get("bc_state"))){
					
					flag = 2 ;
				}
				
				break ;
			}
			
			if(flag == 1){//预算下达 备份数据
				
				//备份 材料采购预算数据
				budgMatPurCheckMapper.copyData(listVo);
				
				
			}else{//取消预算下达  删除 材料采购预算备份数据
				
				//查询 审核申请单 是否已做调整（取消下达时使用）
				List<String> str = budgMatPurCheckMapper.queryCheckIsAdjust(listVo);
				
				if(str.size() > 0){
					
					String returnErr = "";
					
					for(String item : str){
						returnErr += item +",";
					}
					
					return "{\"error\":\"操作失败！【"+returnErr.substring(0, returnErr.length()-1)+"】单据已做调整.不能取消下达！\",\"state\":\"true\"}";
				}
				
				//删除 材料采购预算备份数据
				budgMatPurCheckMapper.deleteCopyData(listVo);
				
			}
			
			budgMatPurCheckMapper.cancelIssueOrIssue(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
	}
	
	    
	/**
	 *  初始审批 材料采购预算 查询(未下达)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgMatPur(Map<String, Object>   entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPur(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPur(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	
	/**
	 *  初始审批 材料采购预算 查询(已下达)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgMatPurCopy(Map<String, Object>   entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPurCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPurCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	
	/**
	 * 调整审批 材料采购预算 查询(未下达)
	 */
	@Override
	public String queryBudgMatPurAdjust(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPurAdjust(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPurAdjust(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/**
	 * 调整审批 材料采购预算 查询(已下达)
	 */
	@Override
	public String queryBudgMatPurAdjustCopy(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPurAdjustCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgMatPurCheckMapper.queryBudgMatPurAdjustCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	/**
	 * 根据 所传参数 查询数据状态
	 */
	@Override
	public List<String> queryBudgMatPurCheckState(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgMatPurCheckMapper.queryBudgMatPurCheckState(entityMap);
	}
	/**
	 * 初始审批 校验数据是否存在
	 */
	@Override
	public int queryDateExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgMatPurCheckMapper.queryDateExist(mapVo) ;
	}
	/**
	 * 根据参数 查询 初始审批单是否已下达 
	 */
	@Override
	public int queryInitDateExist(Map<String, Object> mapVo) throws DataAccessException {

		return budgMatPurCheckMapper.queryInitDateExist(mapVo) ;
	}
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 */
	@Override
	public int queryIssueDateExist(Map<String, Object> mapVo) throws DataAccessException {

		return budgMatPurCheckMapper.queryIssueDateExist(mapVo) ;
	}
	
}

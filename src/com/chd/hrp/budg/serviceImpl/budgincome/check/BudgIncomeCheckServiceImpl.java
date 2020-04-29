/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.check;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgincome.check.BudgIncomeCheckMapper;
import com.chd.hrp.budg.dao.common.BudgEditStateMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeCheck;
import com.chd.hrp.budg.service.budgincome.check.BudgIncomeCheckService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * @Table:
 * BUDG_MED_INCOME_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */


@Service("budgIncomeCheckService")
public class BudgIncomeCheckServiceImpl implements BudgIncomeCheckService {

	private static Logger logger = Logger.getLogger(BudgIncomeCheckServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgIncomeCheckMapper")
	private final BudgIncomeCheckMapper budgIncomeCheckMapper = null;
	
	@Resource(name = "budgEditStateMapper")
	private final BudgEditStateMapper budgEditStateMapper = null;
	
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
    
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
			
			budgIncomeCheckMapper.add(entityMap);
			
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
			
			budgIncomeCheckMapper.addBatch(entityList);
			
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
			
		  int state = budgIncomeCheckMapper.update(entityMap);
			
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
			
			
		  budgIncomeCheckMapper.updateBatch(entityList);
			
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
			int count = budgIncomeCheckMapper.queryIsAdjust(entityMap);
			
			//查询最大审批单号 （只允许删除最大审批单号的审批申请单）
			String check_code = budgIncomeCheckMapper.queryMaxCheckCode(entityMap);
			
			String err = "";//记录返回的错误信息
			
			if(count > 0){
				
				err += "该单据已做过调整,";
					
			}
			
			if(!check_code.equals(entityMap.get("check_code"))){
				
				err += "该单据审批单号不是最大审批单号";
			}
			
			if("".equals(err)){
				
				int state = budgIncomeCheckMapper.delete(entityMap);
				
				entityMap.put("table_code", "BUDG_MED_INCOME_CHECK");
				
				//修改 预算单据号管理表中 该年度 医疗收入预算的最大单号
				budgIncomeCheckMapper.updateMaxNo(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"error\":\"删除失败."+err+"\",\"state\":\"false\"}";
			}

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败.\",\"state\":\"false\"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除审批类型
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgIncomeCheckMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

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
		
		List<BudgMedIncomeCheck> list = (List<BudgMedIncomeCheck>)budgIncomeCheckMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgIncomeCheckMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgIncomeCheckMapper.add(entityMap);
			
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
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.query(entityMap, rowBounds);
			
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
		return budgIncomeCheckMapper.queryByCode(entityMap);
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
		return budgIncomeCheckMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取审批类型
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMedIncomeCheck>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgIncomeCheckMapper.queryExists(entityMap);
	}

	/**
	 * 查询用户的ID对应的科室
	 */
	@Override
	
	public String queryDeptNameByUserId(Map<String, Object> map) throws DataAccessException {
	
		return budgIncomeCheckMapper.queryDeptNameByUserId(map);
	}
	
	/**
	 * 查询科室信息
	 */
	@Override
	public String queryDeptInformation(Map<String, Object> page) throws DataAccessException {

		return budgIncomeCheckMapper.queryDeptInformation(page);
	}
	
	/**
	 * 发送 、 召回  修改状态 同时修改  医疗收入可编辑标记
	 * 发送需 进行数据验证
	 * 依次查验医院月合计与医院年，科室年合计与医院年，科室月合计与科室年是否一致。
	 * 检验中一方为0或为空不判错。数据验证不通过，提示‘某某科目医院月预算合计值与医院年预算不一致’、
	 * ‘某某科目科室年预算合计值与医院年预算 不一致’、‘’某某科目某科室月预算合计值与年预算不一致“
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
				// 预算校验误差 参数
				entityMap.put("paraValue",MyConfig.getSysPara("02006"));
				
				break ;
			}
			
			String errMonth = "" ;//记录医院月合计与医院年 数据 不一致 信息。
			
			String errDeptYear = "" ;//记录 科室年合计与医院年 数据 不一致 信息。
			
			String errDeptMonth = "" ;//记录 科室月合计与科室年 数据 不一致 信息。
			
			if(flag == 1){ //发送 校验数据
				
				
				// 查验医院月合计与医院年 数据 是否一致。
				List<String> hosMonth = budgIncomeCheckMapper.queryHosMonthDataCheck(entityMap) ;
				
				// 查验 科室年合计与医院年 数据 是否一致。
				List<String> deptYear = budgIncomeCheckMapper.queryDeptYearDataCheck(entityMap) ;
				
				// 查验 科室月合计与科室年 数据 是否一致。
				List<String> deptMonth = budgIncomeCheckMapper.queryDeptMonthDataCheck(entityMap) ;
				
				if(hosMonth.size() > 0){
					
					for(String item : hosMonth){
						
						errMonth += item +";" ;
						
					}
					
					if(!"".equals(errMonth)){
						errMonth  = "科目【"+ errMonth.substring(0, errMonth.length()-1) + "】医院月预算合计值与医院年预算不一致!";
					}
				}
				
				if(deptYear.size() > 0){
					
					for(String item : hosMonth){
						
						errDeptYear += item +";" ;
						
					}
					
					if(!"".equals(errDeptYear)){
						
						errDeptYear  = "科目【"+ errDeptYear.substring(0, errDeptYear.length()-1) + "】科室年预算合计值与医院年预算不一致!";
					}
					
				}
				
				if(deptMonth.size() > 0){
					
					for(String item : hosMonth){
						
						errDeptMonth += item +";" ;
						
					}
					
					if(!"".equals(errDeptMonth)){
						
						errDeptMonth  = "【"+ errDeptMonth.substring(0, errDeptMonth.length()-1) + "】科目科室月份预算合计值与科室年度预算不一致!";
					}
					
				}
				
				
				
			}
			
			
			if(!"".equals(errMonth)||!"".equals(errDeptYear)||!"".equals(errDeptMonth)){
				
				return "{\"error\":\""+errMonth+errDeptYear+errDeptMonth+"\",\"state\":\"false\"}";
				
			}else{
				
				if(flag == 1){//发送  医疗收入预算可编辑标记 置为不可编辑状态 "0"
					
					entityMap.put("med_income_flag", "0");
					
					int count = budgEditStateMapper.queryDataExist(entityMap);
					
					if(count>0){
						
						budgEditStateMapper.updateMedIncomeFlag(entityMap);
						
					}else{
						
						entityMap.put("work_flag", "1");
						
						entityMap.put("else_income_flag", "1");
						
						entityMap.put("med_cost_flag", "1");
						
						entityMap.put("else_cost_flag", "1");
						
						entityMap.put("mat_pur_flag", "1");
						
						entityMap.put("med_pur_flag", "1");
						
						budgEditStateMapper.add(entityMap);
						
					}
					
				}else{//召回  医疗收入预算可编辑标记 置为可编辑状态 "1"
					
					entityMap.put("med_income_flag", "1");
					
					budgEditStateMapper.updateMedIncomeFlag(entityMap);
					
				}
				
				budgIncomeCheckMapper.updateBc_state(list) ;
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	//审核 、 消审
	@Override
	public String auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException {
		
			budgIncomeCheckMapper.auditOrUnAudit(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	//预算下达、取消下达
	@Override
	public String cancelIssueOrIssue(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {

			int flag = 1 ;
			
			for(Map<String,Object> item : listVo){
				
				if("03".equals(item.get("bc_state"))){
					
					flag = 2 ;
				}
				
				break ;
			}
			
			if(flag == 1){//预算下达 备份数据
				
				//备份 医院月份医疗收入预算
				budgIncomeCheckMapper.copyHMData(listVo);
				
				//备份 医院年度医疗收入预算
				budgIncomeCheckMapper.copyHYData(listVo);
				
				//备份 科室年度医疗收入预算
				budgIncomeCheckMapper.copyDYData(listVo);
				
				//备份 科室月份医疗收入预算
				budgIncomeCheckMapper.copyDMData(listVo);
				
			}else{//取消预算下达  删除备份数据
				
				//查询 审核申请单 是否已做调整（取消下达时使用）
				List<String> str = budgIncomeCheckMapper.queryCheckIsAdjust(listVo);
				
				if(str.size() > 0){
					
					String returnErr = "";
					
					for(String item : str){
						returnErr += item +",";
					}
					
					return "{\"error\":\"操作失败！【"+returnErr.substring(0, returnErr.length()-1)+"】单据已做调整.不能取消下达！\",\"state\":\"true\"}";
				}
				
				//删除医疗收入预算医院的月备份数据
				budgIncomeCheckMapper.deleteHYCopy(listVo);
				//医疗收入预算医院的年备份数据
				budgIncomeCheckMapper.deleteHMCopy(listVo);
				//医疗收入预算科室的月备份数据
				budgIncomeCheckMapper.deleteDMCopy(listVo);
				//医疗收入预算科室的年备份数据
				budgIncomeCheckMapper.deleteDYCopy(listVo);
				
			}
			
			budgIncomeCheckMapper.cancelIssueOrIssue(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
			
	}
	
	    
	/**
	 *  初始审批 医院收入预算 查询(修改页面 未下达)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBudgMedIncomeHosMonth(Map<String, Object>   entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonth(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonth(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	
	/**
	 * 调整审批 医院医疗收入预算  查询(修改页面 未下达)
	 */
	@Override
	public String queryBudgMedIncomeHosMonthAdjust(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonthAdjust(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonthAdjust(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/**
	 *  初始审批 科室医疗收入预算  查询(修改页面 未下达)
	 */
	@Override
	public String queryBudgMedIncomeDeptMonth(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeDeptMonth(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeDeptMonth(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
		}
	}
	
	
	/**
	 * 调整审批 科室医疗收入预算  查询(修改页面 未下达)
	 */
	@Override
	public String queryBudgMedIncomeCheckDeptMonthAdjust(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeCheckDeptMonthAdjust(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeCheckDeptMonthAdjust(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	/**		
	 * 查询医院明细 初始审核 (修改页面 已下达)
	 */
	@Override
	public String queryBudgMedIncomeHosMonthCopy(Map<String, Object>   entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonthCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonthCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	
	/**
	 * 查询医院明细 调整审核 (修改页面 已下达)
	 */
	@Override
	public String queryBudgMedIncomeHosMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonthAdjustCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeHosMonthAdjustCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/**
	 *  查询科室明细 初始审核 (修改页面 已下达)
	 */
	@Override
	public String queryBudgMedIncomeDeptMonthCopy(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeDeptMonthCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeDeptMonthCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
		}
	}
	
	/**
	 * 查询科室明细 调整审核 (修改页面 已下达)
	 */
	@Override
	public String queryBudgMedIncomeCheckDeptMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeCheckDeptMonthAdjustCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgMedIncomeCheckDeptMonthAdjustCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	@Override
	public String queryIssueData(Map<String, Object> entityMap) throws DataAccessException {
	SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryIssueData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryIssueData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
	}
	}
	/**
	 * 查询调整单号
	 * 
	 * **/
	public String queryBudgAdjFile(Map<String, Object> entityMap) throws DataAccessException {
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgAdjFile(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgIncomeCheckMapper.queryBudgAdjFile(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
    }
	/**
	 * 初始审批  校验数据是否存在
	 */
	@Override
	public int queryDateExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgIncomeCheckMapper.queryDateExist(mapVo);
	}
	
	/**
	 * 根据参数 查询 初始审批单是否已下达 
	 */
	@Override
	public int queryInitDateExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgIncomeCheckMapper.queryInitDateExist(mapVo);
	}
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 */
	@Override
	public int queryIssueDateExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgIncomeCheckMapper.queryIssueDateExist(mapVo);
	}
	
	/**
	 * 根据所传预算年度 查询 医疗收入预算编制模式
	 */
	@Override
	public String queryIncomeBudgMode(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgIncomeCheckMapper.queryIncomeBudgMode(mapVo);
	}
	
	/**
	 * 根据 所传参数 查询数据状态
	 */
	@Override
	public List<String> queryBudgMedIncomeCheckState(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgIncomeCheckMapper.queryBudgMedIncomeCheckState(entityMap);
	}
	
	/**
	 * @Description 
	 * 更新医院意见
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateHosSuggest(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgIncomeCheckMapper.updateHosSuggest(entityMap);
			
			return "{\"message\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"message\":\"更新失败! 方法 updateHosSuggest\"}";

		}	
		
	}
	
	/**
	 * @Description 
	 * 更新科室意见
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateDeptSuggest(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgIncomeCheckMapper.updateDeptSuggest(entityMap);
			
			return "{\"message\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"message\":\"更新失败! 方法 updateDeptSuggest\"}";

		}	
		
	}
	
}

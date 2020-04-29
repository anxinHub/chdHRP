/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.budgeworkcheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.budg.dao.business.budgeworkcheck.BudgWorkCheckMapper;
import com.chd.hrp.budg.dao.common.BudgEditStateMapper;
import com.chd.hrp.budg.entity.BudgWorkCheck;
import com.chd.hrp.budg.service.business.budgeworkcheck.BudgWorkCheckService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 业务预算审批申请
 * @Table:
 * BUDG_WORK_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWorkCheckService")
public class BudgWorkCheckServiceImpl implements BudgWorkCheckService {

	private static Logger logger = Logger.getLogger(BudgWorkCheckServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWorkCheckMapper")
	private final BudgWorkCheckMapper budgWorkCheckMapper = null;
	
	
	//引入DAO操作
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
			
			budgWorkCheckMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWorkCheckMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgWorkCheckMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgWorkCheckMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
	    	//校验 审批申请单 是否已调整
			int count = budgWorkCheckMapper.queryIsAdjust(entityMap);
			
			//查询最大审批单号 （只允许删除最大审批单号的审批申请单）
			String check_code = budgWorkCheckMapper.queryMaxCheckCode(entityMap);
			
			String err = "";//记录返回的错误信息
			
			if(count > 0){
				
				err += "该单据已做过调整,";
					
			}
			
			if(!check_code.equals(entityMap.get("check_code"))){
				
				err += "该单据审批单号不是最大审批单号";
			}
			
			if("".equals(err)){
				
				int state = budgWorkCheckMapper.delete(entityMap);
				
				entityMap.put("table_code", "BUDG_WORK_CHECK");
				
				//修改 预算单据号管理表中 该年度业务预算审批的最大单号
				budgWorkCheckMapper.updateMaxNo(entityMap);
				
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
	 * 批量删除
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWorkCheckMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加
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
		//判断是否存在对象（CHECK_TYPE）取自系统字典表：01初始审批、02调整审批预算审批状态（BC_STATE）取自系统字典表：“01新建、02已发送、03已审核、04已下达、05批中”，其中05审批中暂不使用



		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkCheck> list = (List<BudgWorkCheck>)budgWorkCheckMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWorkCheckMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWorkCheckMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkCheckMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWorkCheck
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkCheckMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkCheck>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkCheckMapper.queryExists(entityMap);
	}
	
	/**
	 * 查询用户的ID对应的科室
	 */
	@Override
	
	public String queryDeptNameByUserId(Map<String, Object> map) throws DataAccessException {
	
		return budgWorkCheckMapper.queryDeptNameByUserId(map);
	}
	
	/**
	 * 查询科室信息
	 */
	@Override
	public String queryDeptInformation(Map<String, Object> page) throws DataAccessException {

		return budgWorkCheckMapper.queryDeptInformation(page);
	}
	
	
	/**
	 * 发送 、 召回  修改状态
	 * 发送需 进行数据验证，只检验数据性质为01可累加的预算指标，
	 * 依次查验医院月合计与医院年，科室年合计与医院年，科室月合计与科室年是否一致。
	 * 检验中一方为0或为空不判错。数据验证不通过，提示‘某某指标医院月预算合计值与医院年预算不一致’、
	 * ‘某某指标科室年预算合计值与医院年预算 不一致’、‘’某某指标某科室月预算合计值与年预算不一致“
	 */
	@Override
	public String sendOrRecall(List<Map<String, Object>> list) throws DataAccessException {
		
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
				List<String> hosMonth = budgWorkCheckMapper.queryHosMonthDataCheck(entityMap) ;
				
				// 查验 科室年合计与医院年 数据 是否一致。
				List<String> deptYear = budgWorkCheckMapper.queryDeptYearDataCheck(entityMap) ;
				
				// 查验 科室月合计与科室年 数据 是否一致。
				List<String> deptMonth = budgWorkCheckMapper.queryDeptMonthDataCheck(entityMap) ;
				
				if(hosMonth.size() > 0){
					
					for(String item : hosMonth){
						
						errMonth += item +";" ;
						
					}
					
					if(!"".equals(errMonth)){
						errMonth  = "指标【"+ errMonth.substring(0, errMonth.length()-1) + "】医院月预算合计值与医院年预算不一致!";
					}
				}
				
				if(deptYear.size() > 0){
					
					for(String item : hosMonth){
						
						errDeptYear += item +";" ;
						
					}
					
					if(!"".equals(errDeptYear)){
						
						errDeptYear  = "指标【"+ errDeptYear.substring(0, errDeptYear.length()-1) + "】科室年预算合计值与医院年预算不一致!";
					}
					
				}
				
				if(deptMonth.size() > 0){
					
					for(String item : hosMonth){
						
						errDeptMonth += item +";" ;
						
					}
					
					if(!"".equals(errDeptMonth)){
						
						errDeptMonth  = "【"+ errDeptMonth.substring(0, errDeptMonth.length()-1) + "】指标科室月份预算合计值与科室年度预算不一致!";
					}
					
				}
				
				
				
			}
			
			
			if(!"".equals(errMonth)||!"".equals(errDeptYear)||!"".equals(errDeptMonth)){
				
				return "{\"error\":\""+errMonth+errDeptYear+errDeptMonth+"\",\"state\":\"false\"}";
				
			}else{
				
				if(flag == 1){//发送  业务预算可编辑标记 置为不可编辑状态 "0"
					
					entityMap.put("work_flag", "0");
					
					int count = budgEditStateMapper.queryDataExist(entityMap);
					
					if(count>0){
						
						budgEditStateMapper.updateWorkFlag(entityMap);
						
					}else{
						
						entityMap.put("med_income_flag", "1");
						
						entityMap.put("else_income_flag", "1");
						
						entityMap.put("med_cost_flag", "1");
						
						entityMap.put("else_cost_flag", "1");
						
						entityMap.put("mat_pur_flag", "1");
						
						entityMap.put("med_pur_flag", "1");
						
						budgEditStateMapper.add(entityMap);
						
					}
					
				}else{//召回  业务预算可编辑标记 置为可编辑状态 "1"
					
					entityMap.put("work_flag", "1");
					
					budgEditStateMapper.updateWorkFlag(entityMap);
					
				}
				
				budgWorkCheckMapper.updateBc_state(list) ;
				
				
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
		
			budgWorkCheckMapper.auditOrUnAudit(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	//预算下达、取消下达
	@Override
	public String cancelIssueOrIssue(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try{
			
			
			int flag = 1 ;
			
			for(Map<String,Object> item : listVo){
				
				if("03".equals(item.get("bc_state"))){
					
					flag = 2 ;
				}
				
				break ;
			}
			
			if(flag == 1){//预算下达 备份数据
				
				//备份 医院月份业务预算
				budgWorkCheckMapper.copyHMData(listVo);
				
				//备份 医院年度业务预算
				budgWorkCheckMapper.copyHYData(listVo);
				
				//备份 科室年度业务预算
				budgWorkCheckMapper.copyDYData(listVo);
				
				//备份 科室月份业务预算
				budgWorkCheckMapper.copyDMData(listVo);
				
			}else{//取消预算下达  删除备份数据
				
				//查询 审核申请单 是否已做调整（取消下达时使用）
				List<String> str = budgWorkCheckMapper.queryCheckIsAdjust(listVo);
				
				if(str.size() > 0){
					
					String returnErr = "";
					
					for(String item : str){
						returnErr += item +",";
					}
					
					return "{\"error\":\"操作失败！【"+returnErr.substring(0, returnErr.length()-1)+"】单据已做调整.不能取消下达！\",\"state\":\"true\"}";
				}
				
				//删除业务预算医院的月备份数据
				budgWorkCheckMapper.deleteHYCopy(listVo);
				//业务预算医院的年备份数据
				budgWorkCheckMapper.deleteHMCopy(listVo);
				//业务预算科室的月备份数据
				budgWorkCheckMapper.deleteDMCopy(listVo);
				//业务预算科室的年备份数据
				budgWorkCheckMapper.deleteDYCopy(listVo);
				
			}
			
			budgWorkCheckMapper.cancelIssueOrIssue(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
		}
	}
	
	    
	/**		
	 * 查询医院明细 初始审核 (修改页面 未下达)
	 */
	public String queryBudgWorkHosMonth(Map<String, Object>   entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonth(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonth(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	
	/**
	 * 查询医院明细 调整审核 (修改页面 未下达)
	 */
	@Override
	public String queryBudgWorkHosMonthAdjust(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonthAdjust(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonthAdjust(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/**
	 *  查询科室明细 初始审核 (修改页面 未下达)
	 */
	@Override
	public String queryBudgWorkDeptMonth(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkDeptMonth(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkDeptMonth(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
		}
	}
	
	/**
	 * 查询科室明细 调整审核 (修改页面 未下达)
	 */
	@Override
	public String queryBudgWorkCheckDeptMonthAdjust(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptMonthAdjust(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptMonthAdjust(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/**		
	 * 查询医院明细 初始审核 (修改页面 已下达)
	 */
	public String queryBudgWorkHosMonthCopy(Map<String, Object>   entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonthCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonthCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}
	
	/**
	 * 查询医院明细 调整审核 (修改页面 已下达)
	 */
	@Override
	public String queryBudgWorkHosMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonthAdjustCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkHosMonthAdjustCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/**
	 *  查询科室明细 初始审核 (修改页面 已下达)
	 */
	@Override
	public String queryBudgWorkDeptMonthCopy(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkDeptMonthCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkDeptMonthCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
		}
	}
	
	/**
	 * 查询科室明细 调整审核 (修改页面 已下达)
	 */
	@Override
	public String queryBudgWorkCheckDeptMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptMonthAdjustCopy(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptMonthAdjustCopy(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	/**
	 * 
	 */
	@Override
	public String queryBudgWorkCheckDeptYear(Map<String, Object> entityMap) throws DataAccessException {
      SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptYear(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptYear(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String  queryBudgWorkCheckYear(Map<String, Object> entityMap) throws DataAccessException {
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckYear(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckYear(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public String queryBudgWorkCheckYearAdjust(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckYearAdjust(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckYearAdjust(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
	
	
	/**
	 * 
	 */
	@Override
	public String queryBudgWorkCheckDeptYearAdjust(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptYearAdjust(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgWorkCheckDeptYearAdjust(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String queryIssueData(Map<String, Object> entityMap) throws DataAccessException {
	SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryIssueData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryIssueData(entityMap, rowBounds);
			
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
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgAdjFile(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkCheckMapper.queryBudgAdjFile(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
    }
	/**
	 * 初始审批 校验数据是否存在
	 */
	@Override
	public int queryDateExist(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkCheckMapper.queryDateExist(mapVo);
	}
	/**
	 * 根据参数 查询 初始审批单是否已下达 
	 */
	@Override
	public int queryInitDateExist(Map<String, Object> initMap) throws DataAccessException {
		
		return budgWorkCheckMapper.queryInitDateExist(initMap);
	}
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 */
	@Override
	public int queryIssueDateExist(Map<String, Object> initMap) throws DataAccessException {
		
		return budgWorkCheckMapper.queryIssueDateExist(initMap);
	}
	
	/**
	 * 根据 所传参数 查询数据状态
	 */
	@Override
	public List<String> queryBudgWorkCheckState(Map<String, Object> entityMap) throws DataAccessException {
		
		return budgWorkCheckMapper.queryBudgWorkCheckState(entityMap);
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
			
		  int state = budgWorkCheckMapper.updateHosSuggest(entityMap);
			
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
			
		  int state = budgWorkCheckMapper.updateDeptSuggest(entityMap);
			
			return "{\"message\":\"更新成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"message\":\"更新失败! 方法 updateDeptSuggest\"}";

		}	
		
	}
	/**
	 * 根据所传预算年度 查询 业务预算编制模式
	 */
	@Override
	public String queryWorkBudgMode(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkCheckMapper.queryWorkBudgMode(mapVo);
	}
}
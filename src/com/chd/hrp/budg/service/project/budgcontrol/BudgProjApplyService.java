/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.budgcontrol;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 项目预算申报
 * @Table:
 * BUDG_PROJ_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjApplyService extends SqlService {
	
	/**
	 * 查询 支出项目数据 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgPaymentItem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 生成 预算申报单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String setBudgApplyCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 BUDG_PROJ_APPLY_SOURCE  项目预算申报明细（按资金来源）
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjApplySource(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 BUDG_PROJ_APPLY_RESOLVE  项目预算分解 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjApplyResolve(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 期初项目预算记账BUDG_PROJ_BEGAIN_MARK 状态    为1记账时才可进行预算申报。
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBegainMark(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 提交 撤回 修改 数据状态
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBudgProjApplyState(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 审核 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String auditBudgProjApply(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 消审 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String reAuditBudgProjApply(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 导入项目预算分解数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String importPaymentItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 上传文件
	 */
	public String importFile(Map<String,Object> entityMap,MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath)throws Exception;
	//删除文件
	public String deleteFile(List<Map<String,Object>> entityMap) throws DataAccessException;
	//下载文件
	public String downloadFile(HttpServletResponse response,Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 删除数据
	 * @param listVo
	 * @param fileList
	 * @return
	 */
	public String deleteData(Map<String, Object> mapVo,Map<String, Object> fileMap);
	
}

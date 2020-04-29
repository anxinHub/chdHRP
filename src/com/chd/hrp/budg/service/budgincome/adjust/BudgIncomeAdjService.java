/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.adjust;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgWorkAdj;
/**
 * 
 * @Description:
 * 系统字典表
状态（STATE），“01新建、02已审核”
 * @Table:
 * BUDG_WORK_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgIncomeAdjService extends SqlService {
    
	/**
	 * 审核
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String auditBudgIncomeAdj(List<Map<String, Object>> listVo) throws DataAccessException;
    
	/**
	 * 销审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String unAuditBudgIncomeAdj(List<Map<String, Object>> listVo) throws DataAccessException;
	
	public String queryBudgIncomeAdjHosMonth(Map<String, Object> page) throws  DataAccessException;
	public String queryBudgIncomeAdjDeptMonth(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 查询 调整表中数据是否均已下达
	 * @param mapVo
	 * @return
	 */
	public int queryBcState(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 调整表中最大单号  年度  下达日期   添加页面使用
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMaxCheckData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 上传文件
	 */
	public String importFile(Map<String,Object> entityMap,MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath)throws Exception;
	//删除文件
	public String deleteFile(List<Map<String,Object>> entityMap) throws DataAccessException;
	//下载文件
	public String downloadFile(HttpServletResponse response,Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询 调整表中最大单号  年度  下达日期   修改页面使用
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMaxCheckDataForUpdate(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 删除数据
	 * @param listVo
	 * @param fileList
	 * @return
	 */
	public String deleteData(Map<String, Object> mapVo,Map<String, Object> fileMap);
	
	/**
	 * 查询 审核表中是否存在当前预算年度数据   添加页面使用
	 * @param mapVo
	 * @return
	 */
	public int queryCheckDataExists(Map<String, Object> mapVo) throws DataAccessException;
	
}

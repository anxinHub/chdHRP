package com.chd.hrp.acc.service.vouch;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.chd.hrp.acc.entity.superVouch.SuperVouchMain;
 
/**
 * @Title. @Description. 超级凭证Service 
 * @Author: Perry
 * @Version: 1.0
 */
public interface SuperVouchService {
	
	//凭证制单取所有相关参数
	public String queryVouchParaList(Map<String,Object> entityMap) throws DataAccessException;
	
	//根据参数编码取系统参数的通用方法
	public List<Map<String,Object>> queryVouchParaListByParaCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
	//凭证制单-分录-科目下拉框字典加载
	public String queryAccVouchSubjDict(Map<String,Object> entityMap) throws DataAccessException;

	//凭证制单-选择科目树
	public String queryAccVcouchSubjTree(Map<String,Object> entityMap) throws DataAccessException;
	
	//凭证制单-分录、辅助核算-摘要下拉框字典
	public String queryVouchSummaryDict(Map<String,Object> entityMap) throws DataAccessException;
	
	//凭证制单-取当前年度最大凭证日期 
	public String queryMaxVouchDate(Map<String,Object> entityMap) throws DataAccessException;
	
	
	//凭证制单-根据凭证类型、凭证日期取最大凭证号
	public String queryMaxVouchNo(Map<String,Object> entityMap) throws DataAccessException;

	//凭证制单-取凭证审核流程 
	public String queryVouchFlow(Map<String, Object> map) throws DataAccessException;
	
	//保存凭证
	public String saveSuperVouch(Map<String,Object> mapVo) throws DataAccessException;
	
	//删除相关联的凭证
	public String deleteSuperVouchByVouchId(Map<String,Object> mapVo) throws DataAccessException;
	
	//根据vouch_id查询凭证主表
	public SuperVouchMain querySuperVouchByMain(Map<String,Object> mapVo) throws DataAccessException;
	
	//根据vouch_id查询凭证明细表
	public String querySuperVouchByDetail(Map<String,Object> mapVo) throws DataAccessException;
	
	//凭证一次性加载：根据vouch_id查询凭证明细表、辅助核算表、现金流量表
	public String querySuperVouchByDetailCheckCash(Map<String,Object> mapVo) throws DataAccessException;
	
	
	//根据系统参数010取凭证类型
	public String querySuperVouchTypeByPerm(Map<String,Object> mapVo) throws DataAccessException;
	
	
	//凭证跳转：根据凭证号、凭证日期，查询凭证明细表
	public String querySuperVouchByJump(Map<String,Object> mapVo) throws DataAccessException;	
		
	
	//凭证制单-辅助核算-核算类型加载
	public String queryVouchCheckType(Map<String,Object> entityMap) throws DataAccessException;
		
	//凭证制单-辅助核算-下拉框字典加载（辅助核算所有下拉字典）
	public List<Map<String,Object>> queryVouchCheckItemDict(Map<String, Object> checkMap) throws DataAccessException;
	
	//凭证制单-辅助核算导入
	public String importCheck(Map<String, Object> checkMap) throws DataAccessException;
	
	//根据凭证流程操作签字、审核、记账
	public String updateVouchStateByFlow(Map<String, Object> map) throws DataAccessException;
	
	//凭证审批流判断操作权限
	public int queryVouchFlowByPerm(Map<String, Object> map) throws DataAccessException;	
	
	//凭证现金流量加载：根据vouch_detail_id查询现金流量标注表
	public String querySuperVouchByCash(Map<String, Object> map) throws DataAccessException;
	
	//复制凭证
	public String copySuperVouch(Map<String, Object> map) throws DataAccessException;
	
	//复制凭证模板
	public String insertAccVouchTemplate(Map<String, Object> map)throws DataAccessException;
	
	//打印凭证-单张打印
	public String querySuperVouchPrint(Map<String, Object> map)throws DataAccessException;
	
	//批量打印凭证-返回spreadjson
	public String querySuperVouchPrintBatch(Map<String, Object> map)throws DataAccessException;
	
	public Map<String,Object> querySuperVouchPrintPage(Map<String, Object> map)throws DataAccessException;
	
	
	public Map<String,Object> querySuperVouchPrintBatchPage(Map<String, Object> map)throws DataAccessException;
	
	//点击凭证分录，查询科目余额 
	public String querySuperVouchSubjBalance(Map<String,Object> map) throws DataAccessException;
	
	/*
	 * 自动凭证，检查科目是否有辅助核算，用于查询辅助核算数据的检索条件
	 * 返回辅助核算{"HOS_DEPT_DICT","65,66,67"},{"HOS_EMP_DICT","65,66,67"}
	 */
	public String getCheckSubjId(List<Map<String, Object>> metaList,String checkEl);
	
	/**
	 * 组装凭证json格式
	 * mainMap：主表数据
	 * detailList分录数据
	 */
	public String getVouchJon(Map<String,Object> mainMap,List<List<Map<String, Object>>> detailList,Map<Integer,List<Map<String, Object>>> checkMap,Map<Integer,List<Map<String, Object>>> cashMap) throws Exception;
	
	
	public List<String> getCheckSqlBySubjId(Map<String, Object> detailMap,Map<String,String> checkEl) throws Exception;
	
	//查询凭证模板-取模板
	public String queryAccTemplateMain(Map<String,Object> map)throws DataAccessException;
	
	//取模板页面-删除模板
	public String deleteAccVouchTemplate(Map<String,Object> map)throws DataAccessException;
	
	//取模板页面-修改模板主表
	public String updateAccTemplateMain(Map<String,Object> map) throws DataAccessException;
	
	//取模板页面-置顶
	public String updateAccTemplateMainBySort(Map<String,Object> map) throws DataAccessException;
	
	//取模板
	public String queryAccTemplateVouch(Map<String,Object> map) throws DataAccessException;
	
	//摘要维护页面-查询凭证摘要模板
	public String queryAccVouchSummary(Map<String,Object> map)throws DataAccessException;
	
	//删除凭证摘要模板
	public String deleteAccVouchSummary(Map<String,Object> map) throws DataAccessException;
	
	//摘要维护页面-修改摘要
	public String updateAccVouchSummaryBySid(Map<String,Object> map) throws DataAccessException;
	
	//摘要维护页面-置顶
	public String updateAccVouchSummaryBySort(Map<String,Object> map) throws DataAccessException;
	
	//保存凭证摘要模板
	public String saveAccVouchSummary(Map<String,Object> map) throws DataAccessException;

	//辅助核算，查询个人供应商余额
	public String querySuperVouchCheckBalance(Map<String, Object> map);
	
	//删除
	public String deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	//	添加
	public String addAccFile(Map<String,Object> entityMap,HttpServletRequest request, HttpServletResponse response)throws DataAccessException;
	//public String importFile(Map<String,Object> entityMap,MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath )throws Exception;
	public String importFile(Map<String,Object> entityMap,MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath,String fileName )throws Exception;
	//查询
	public String queryFile(Map<String,Object> entityMap) throws DataAccessException;
	//下载附件
	public String downloadFile(HttpServletResponse response,Map<String,Object> entityMap);
	
	public void saveAccAutoCheck(Map<String,Object> detailMap,Map<String,String> checkEl,String vouch_sql,String where_sql,String group_sql)throws Exception;
	
	//查询自动凭证数据
	public String queryAutoVouch(Map<String,Object> map) throws DataAccessException;

	String queryAccBudgTpTree(Map<String, Object> map)
			throws DataAccessException;

	String queryBudgSubjByAcc(Map<String, Object> map)
			throws DataAccessException;

	String updateBudgSubj(Map<String, Object> map) throws DataAccessException;

	String querySuperVouchByDiff(Map<String, Object> map)
			throws DataAccessException;

	String updateAccVouchDiffAuto(Map<String, Object> map)
			throws DataAccessException;

	String updateAccVouchDiffSg(Map<String, Object> mapVo)
			throws DataAccessException;

	List<Map<String,Object>> queryCheckPrintByDid(Map<String, Object> map)
			throws DataAccessException;

}

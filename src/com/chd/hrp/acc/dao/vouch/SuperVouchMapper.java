package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.superVouch.SuperVouchMain;



/**
 * @Title. @Description. 超级凭证Dao 
 * @Author: Perry
 * @Version: 1.0
*/
public interface SuperVouchMapper extends SqlMapper{

	//添加凭证主表
	public int saveSuperVouchByMain(Map<String, Object> map) throws DataAccessException;
	
	//修改凭证主表
	public int updateSuperVouchByMain(Map<String, Object> map) throws DataAccessException;
	
	//保存凭证明细表
	public int saveSuperVouchByDetail(List<Map<String, Object>> map)throws DataAccessException;
	
	//根据vouch_id删除凭证明细表
	public int deleteSuperVouchByDetail(Map<String, Object> map)throws DataAccessException;
	
	
	//保存凭证辅助核算表
	public int saveSuperVouchByCheck(@Param("column") String column,@Param("list") List<Map<String, Object>> list)throws DataAccessException;
	
	//批量删除需要修改的辅助核算
	public int deleteSuperVouchByCheck(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id,@Param("list") List<String> list)throws DataAccessException;
	
	//没有保存分录，根据vouch_id删除凭证辅助核算表
	public int deleteSuperVouchByCheckAndVouchId(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id)throws DataAccessException;
	
	//删除当前凭证不存在分录的辅助核算
	public int deleteSuperVouchCheckByNotDetail(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id)throws DataAccessException;
	
	//删除当前凭证不存在分录的现金流量
	public int deleteSuperVouchCashByNotDetail(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id)throws DataAccessException;
	
	//保存现金流量标注表
	public int saveSuperVouchByCash(List<Map<String, Object>> map)throws DataAccessException;
	
	//批量删除需要修改的现金流量
	public int deleteSuperVouchByCash(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id,@Param("list") List<String> list)throws DataAccessException;
	
	//没有保存分录，根据vouch_id删除现金流量标注表
	public int deleteSuperVouchByCashAndVouchId(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id)throws DataAccessException;
	
	//根据vouch_id删除差异标注
	public int deleteSuperVouchByDiff(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id)throws DataAccessException;
	
	//保存差异标注
	public int saveSuperVouchByDiff(@Param(value="map") Map<String,Object> map,@Param(value="list") List<Map<String,Object>> list)throws DataAccessException;
	
	//更新票据核销状态
	public int updateSuperVouchByCheckNo(Map<String,Object> map)throws DataAccessException;
	
	public int updateSuperVouchBySignFlag(Map<String,Object> map)throws DataAccessException;
	
	//删除凭证操作：删除凭证相关联的表
	public String deleteSuperVouchByVouchId(Map<String,Object> map)throws DataAccessException;
	
	//凭证加载、上张下张跳转：根据vouch_id or vouch_no or acc_year or acc_month查询凭证主表
	public SuperVouchMain querySuperVouchByMain(Map<String,Object> map)throws DataAccessException;
	
	//根据vouch_id查询凭证明细表
	public List<Map<String,Object>> querySuperVouchByDetail(Map<String,Object> map)throws DataAccessException;
	
	//根据vouch_id or vouch_detail_id查询凭证辅助核算表、现金流量表
	public List<Map<String,Object>> querySuperVouchByCheckCash(Map<String,Object> map)throws DataAccessException;
	
	//根据vouch_id or vouch_detail_id查询凭证辅助核算表
	public List<Map<String,Object>> querySuperVouchByCheck(Map<String,Object> map)throws DataAccessException;
	
	//根据vouch_detail_id查询现金流量表
	public List<Map<String,Object>> querySuperVouchByCash(Map<String,Object> map)throws DataAccessException;
	
	//根据vouch_id查询差异标注表
	public List<Map<String,Object>> querySuperVouchByDiff(Map<String,Object> map)throws DataAccessException;
	
	
	//凭证制单取所有相关参数
	public List<Map<String,Object>> queryVouchParaList(Map<String, Object> map) throws DataAccessException;
	
	//根据系统参数010取凭证类型
	public List<HrpAccSelect> querySuperVouchTypeByPerm(Map<String, Object> map) throws DataAccessException;
	
	
	//凭证制单-分录-科目下拉框字典加载
	public List<Map<String,Object>> queryAccVouchSubjDict(Map<String, Object> map) throws DataAccessException;
	
	//凭证制单-选择科目树
	public List<Map<String,Object>> queryAccVcouchSubjTree(Map<String, Object> map) throws DataAccessException;
	
	
	//凭证制单-取当前年度最大凭证日期 
	public String queryMaxVouchDate(Map<String, Object> map) throws DataAccessException;
	
	//凭证制单-取凭证审核流程
	public List<Map<String,Object>> queryVouchFlow(Map<String, Object> map) throws DataAccessException;
	
	//凭证制单-根据凭证类型、凭证日期取最大凭证号
	public String queryMaxVouchNo(Map<String, Object> map) throws DataAccessException;

	
	//凭证制单-根据凭证类型、会计期间取最大凭证号、最大凭证日期，003序时控制判断使用 -->
	public String[] queryMaxMinNoByVouchDate(Map<String, Object> map) throws DataAccessException;
	
	
	//凭证制单-分录、辅助核算-摘要下拉框字典
	public List<String> queryVouchSummaryDict(Map<String, Object> map) throws DataAccessException;
	
	
	//凭证制单-辅助核算-核算类型加载
	public List<Map<String,Object>> queryVouchCheckType(Map<String, Object> map) throws DataAccessException;
		
	//凭证制单-辅助核算-下拉框字典加载（辅助核算所有下拉字典）
	public List<Map<String,Object>> queryVouchCheckItemDict(Map<String, Object> map) throws DataAccessException;
	
	
	public List<Map<String,Object>> queryVouchDiffItemDict(Map<String, Object> map) throws DataAccessException;
	
	
	//凭证制单-辅助核算-票据号下拉框字典
	public List<Map<String,Object>> queryVouchCheckNoDict(Map<String, Object> map) throws DataAccessException;
	
	
	//根据凭证流程操作签字、审核、记账
	public int updateVouchStateByFlow(Map<String, Object> map)throws DataAccessException;
	
	//凭证审批流判断操作权限
	public int queryVouchFlowByPerm(Map<String, Object> map)throws DataAccessException;
	
	//凭证制单保存，验证分录金额是否与辅助核算金额一致
	public List<String> queryVouchDedailAndCheckByMoney(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id) throws DataAccessException;
	
	//凭证制单保存，验证分录金额是否与现金流量标注金额一致
	public List<String> queryVouchDedailAndCashByMoney(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id) throws DataAccessException;
	
	//凭证制单保存，验证分录金额是否与现金流量标注方向一致
	public List<String> queryVouchDedailAndCashByDire(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("vouch_id") String vouch_id) throws DataAccessException;
	
	//根据凭证ID、科目ID判断辅助核算是否为空
	public String queryVouchCheckExists(Map<String, Object> map)throws DataAccessException;
	
	//凭证打印，查询主表
	public Map<String,Object> queryVouchPrintMain(Map<String,Object> map) throws DataAccessException;
	
	//凭证打印，查询明细表
	public List<Map<String,Object>> queryVouchPrintDetail(Map<String,Object> map) throws DataAccessException;
	
	
	//凭证批量打印，查询主表
	public List<Map<String,Object>> querySuperVouchPrintBatch(Map<String,Object> map) throws DataAccessException;
	
	
	//凭证批量打印，查询明细表
	public List<Map<String,Object>> querySuperVouchPrintBatch_detail(Map<String,Object> map) throws DataAccessException;
	
	//凭证打印，查询辅助核算
	public List<Map<String,Object>> querySuperVouchCheckPrint(Map<String,Object> map) throws DataAccessException;
	
	
	//点击凭证分录，查询科目余额 
	public Map<String,Object> querySuperVouchSubjBalance(Map<String,Object> map) throws DataAccessException;
	

	//添加凭证日志表，自动凭证
	public int saveSuperVouchByAutoLog(Map<String,Object> map) throws DataAccessException;
	
	
	//添加凭证日志表，期末处理凭证
	public int saveSuperVouchByQimoLog(Map<String,Object> map) throws DataAccessException;
	
	//出纳凭证回写出纳登记表
	public int updateSuperVouchByAccTell(Map<String,Object> map) throws DataAccessException;
	
	//复制凭证主表
	public int copySuperVouchMain(Map<String, Object> entityMap) throws DataAccessException;
	
	//复制凭证明细表
	public int copySuperVouchDetail(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	//凭证复制、 查询要复制的凭证信息
	public Map<String,Object> queryCopyAccVouchByVouchId(Map<String,Object> map) throws DataAccessException;
	
	//凭证复制、 查询要复制的凭证明细
	public List<String> queryCopyAccVouchDetailByVouchId(Map<String,Object> map) throws DataAccessException;
	
	
	//凭证复制、查询要复制的辅助核算
	public List<Map<String,Object>> queryCopyAccVouchCheckByVouchId(Map<String,Object> map) throws DataAccessException;

	
	//凭证复制、查询要复制的现金流量 
	public List<Map<String,Object>> queryCopyAccVouchCashByVouchId(Map<String,Object> map) throws DataAccessException;
	
	
	public int copySuperVouchDiff(Map<String,Object> map) throws DataAccessException;
	
	
	//复制凭证辅助核算表
	public int copySuperVouchCheck(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	//复制现金流量标注表
	public int copySuperVouchCash(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	//根据核算类型column_check查找字典对应表，自动生成凭证自定义核算关联系统字典表
	public Map<String,Object>  queryAccCheckTypeZCode(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("column_check") String column_check,@Param("busi_type_code") String busi_type_code) throws DataAccessException;
	
	//根据业务表的编码复制辅助核算项表
	public int copyAccCheckItemByBusi(Map<String,Object> map) throws DataAccessException;
	
	//添加凭证模板主表
	public int insertAccVouchTemplate(Map<String,Object> map) throws DataAccessException;
	
	//添加用户数据表-凭证模板
	public int insertUserPermbyTemplate(Map<String,Object> map) throws DataAccessException;
	
	
	//添加凭证模板明细表
	public int insertAccVouchTemplateDetail(Map<String,Object> map) throws DataAccessException;
	
	//添加凭证模板明细表-批量 
	public int insertAccVouchTemplateDetailBatch(List<Map<String, Object>> map)throws DataAccessException;
	
	//添加凭证模板辅助核算表
	public int insertAccVouchTemplateCheck(Map<String,Object> map) throws DataAccessException;

	//添加凭证模板辅助核算表-批量
	public int insertAccVouchTemplateCheckBatch(@Param("column") String column,@Param("list") List<Map<String, Object>> list)throws DataAccessException;
	
	//添加凭证模板现金流量表
	public int insertAccVouchTemplateCash(Map<String,Object> map) throws DataAccessException;
	
	//添加凭证模板现金流量表-批量
	public int insertAccVouchTemplateCashBatch(List<Map<String, Object>> map)throws DataAccessException;
	
	//删除凭证模板主表
	public int deleteAccVouchTemplate(Map<String,Object> map) throws DataAccessException;
	
	
	//删除用户数据表-凭证模板
	public int deleteUserPermbyTemplate(Map<String,Object> map) throws DataAccessException;
	
	//删除凭证模板明细表
	public int deleteAccVouchTemplateDetail(Map<String,Object> map) throws DataAccessException;
	
	//删除凭证模板辅助核算表
	public int deleteAccVouchTemplateCheck(Map<String,Object> map) throws DataAccessException;
	
	//删除凭证模板现金流量表
	public int deleteAccVouchTemplateCash(Map<String,Object> map) throws DataAccessException;
	
	//查询凭证模板-取模板
	public List<Map<String,Object>> queryAccTemplateMain(Map<String,Object> map) throws DataAccessException;
	
	//取模板页面-修改模板主表
	public int updateAccTemplateMain(Map<String,Object> map) throws DataAccessException;
	
	//查询用户凭证模板的数据权限
	public List<Map<String,Object>> queryAccTemplateByPerm(Map<String,Object> map) throws DataAccessException;
	
	//取模板页面-置顶
	public int updateAccTemplateMainBySort(Map<String,Object> map) throws DataAccessException;
	
	//取模板-查询凭证模板主表
	public Map<String,Object> queryAccTemplateMainByCode(Map<String,Object> map) throws DataAccessException;
	
	//取模板-查询凭证模板明细表
	public List<Map<String,Object>> queryAccTemplateDetail(Map<String,Object> map) throws DataAccessException;
	
	//取模板-查询凭证模板辅助核算表
	public List<Map<String,Object>> queryAccTemplateCheck(Map<String,Object> map) throws DataAccessException;
	
	//取模板-查询凭证模板现金流量表
	public List<Map<String,Object>> queryAccTemplateCash(Map<String,Object> map) throws DataAccessException;
	
	//取模板-查询凭证模板辅助核算、现金流量表
	public List<Map<String,Object>> queryAccTemplateCheckCash(Map<String,Object> map) throws DataAccessException;
	
	//取当前账套下所有辅助核算字段
	public List<Map<String,Object>> queryAccCheckTypeColumnCheck(Map<String,Object> map) throws DataAccessException;
	
	//摘要维护页面-查询摘要
	public List<Map<String,Object>> queryAccVouchSummary(Map<String,Object> map) throws DataAccessException;
	
	//删除凭证摘要模板
	public int deleteAccVouchSummary(Map<String,Object> map) throws DataAccessException;
	
	//保存凭证摘要模板
	public int saveAccVouchSummary(Map<String,Object> map) throws DataAccessException;
	
	//摘要维护页面-修改摘要
	public int updateAccVouchSummaryBySid(Map<String,Object> map) throws DataAccessException;
	
	//摘要维护页面-置顶
	public int updateAccVouchSummaryBySort(Map<String,Object> map) throws DataAccessException;

	//点击辅助核算，显示供应商以及个人余额
	public Map<String, Object> querySuperVouchCheckBalance(Map<String, Object> map)throws DataAccessException;

	//科目字典下拉框挂现金流量科目项
	public List<Map<String, Object>> queryVouchCashItemDict(Map<String, Object> map)throws DataAccessException;
	
	//科目字典下拉框挂结算方式科目项
	public List<Map<String, Object>> queryVouchCountItemDict(Map<String, Object> map)throws DataAccessException;
	
	//科目字典下拉框挂票据类型科目项
	public List<Map<String, Object>> queryVouchNoteItemDict(Map<String, Object> map)throws DataAccessException;
	
	//科目字典下拉框挂系统核算类型科目项 以前台字段 column_check包含check分类
	public List<Map<String, Object>> queryVouchCheckSysItemDict(Map<String, Object> map)throws DataAccessException;

	
	//判断审核人、出纳人、记账人是否是当前用户
	public int queryAccVouchExistsUser(Map<String, Object> map)throws DataAccessException;
	
	public List<Map<String,Object>> queryCheckIdByDetailId(Map<String, Object> map)throws DataAccessException;
	
	
	//添加附件
	public int addFile(Map<String,Object> entityMap)throws DataAccessException;
	//删除文件
	public int deleteBatchFile(List<Map<String, Object>> entityMap)throws DataAccessException;
	//查询
	public List<?> queryFile(Map<String,Object> entityMap)throws DataAccessException;
	public <T> T queryByCodeFile(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryFileDel(Map<String,Object> map)throws DataAccessException;
	
	
	//判断是否自动生成凭证
	public int queryAutoVouchIsCreate(Map<String,Object> map)throws DataAccessException;
	
	//自动凭证主表
	public int saveAutoVouch(Map<String, Object> map) throws DataAccessException;
	
	//自动凭证明细表
	public int saveAutoVouchDetail(List<Map<String, Object>> map) throws DataAccessException;
	
	//查询自动凭证辅助核算表汇总金额，用来判断与分录金额是否一致，主要由于3位小数导致的
	public Double queryAutoVouchCheckSum(Map<String,Object> map) throws DataAccessException;
	
	//自动凭证辅助核算表金额不等于分录金额，将差额更新到辅助核算第一条数据上
	public int updateAutoVouchCheckByOne(Map<String,Object> map) throws DataAccessException;
	
	//自动凭证-保存自动辅助核算表acc_auto_check
	public int saveAccAutoCheck(Map<String,Object> map)throws DataAccessException;

	//期末处理-保存辅助核算表acc_auto_check
	public int saveAccAutoCheckByTermend(@Param(value="column") String column, @Param(value="column_value") String column_value, @Param(value="list")List<Map<String,Object>> list)throws DataAccessException;
	
	//自动凭证-保存自动现金流量表   acc_auto_cash  只用于付款单自动生成凭证
	public int saveAccAutoCash(List<Map<String,Object>> map)throws DataAccessException;
	
	//查询自动凭证主表
	public Map<String,Object> queryAutoVouch(Map<String,Object> map)throws DataAccessException;
	
	//查询自动凭证明细表
	public List<Map<String,Object>> queryAutoVouchDetail(Map<String,Object> map)throws DataAccessException;
	
	//查询自动凭证辅助核算现金流量表
	public List<Map<String,Object>> queryAutoCheckCash(Map<String,Object> map)throws DataAccessException;
	
	//查询自动凭证辅助核算
	public List<Map<String,Object>> queryAutoCheck(Map<String,Object> map)throws DataAccessException;
	
	//查询自动凭证现金流量表
	public List<Map<String,Object>> queryAutoVouchCash(Map<String,Object> map)throws DataAccessException;
	
	//自动凭证复制凭证日志表,删除不等于今天的临时日志或者当前vouch_id
	public int saveAutoVouchLog(Map<String,Object> map)throws DataAccessException;
	
	//查询自动凭证辅助核算表第一条数据的供应商名称
	public String queryAutoVouchCheckByCheck6(Map<String,Object> map)throws DataAccessException;
	
	//查询平行记账模板
	public List<Map<String,Object>> queryAccBudgTpTree(@Param(value="map") Map<String,Object> map,@Param(value="list") List<Map> list)throws DataAccessException;
	
	//根据财务会计科目对照查询预算会计
	public List<Map<String,Object>> queryBudgSubjByAcc(Map<String,Object> map)throws DataAccessException;
	
	public int updateBudgSubj(Map<String,Object> map)throws DataAccessException;

	public List<Map<String,Object>> querySuperVouchPrintBatch_kind(Map<String,Object> map)throws DataAccessException;

	//转换合并 需要添加的分录信息
	public List<Map<String, Object>> querySaveAutoVouchDetail(List<Map<String, Object>> detailList);
	
	//判断分录是否出纳对账
	public List<String> queryVouchByTellVeri(Map<String,Object> map)throws DataAccessException;
	
	//判断辅助核算是否单位银行对账
	public List<String> queryVouchByBankVeri(Map<String,Object> map)throws DataAccessException;
	
	//判断辅助核算是否往来核销
	public List<String> queryVouchByVeri(Map<String,Object> map)throws DataAccessException;
	

	public void updateAutoVouchDetailSummary(Map<String,Object> map)throws DataAccessException;
	
	public void updateAccVouchDiffAuto(Map<String,Object> map)throws DataAccessException;
	
	public List<String> queryAccVouchDiffAutoTemp(Map<String,Object> map)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccVouchDiffAutoTempAll(Map<String,Object> map)throws DataAccessException;
	
	public void updateAccVouchDiffNote(Map<String,Object> map)throws DataAccessException;
}

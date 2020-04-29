package com.chd.hrp.acc.dao.paper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AccPaperIncomeMapper extends SqlMapper {

	/**
	 * 票据类型下拉框加载 dao
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncomeType_code(Map<String, Object> mapVo);

	/**
	 * 币种下拉加载 dao
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncomeMoney(Map<String, Object> mapVo);

	/**
	 * 汇率下拉加载 dao
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncomeRatename(Map<String, Object> mapVo);

	/**
	 * 核算项下拉加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncomeCheckItemNo(Map<String, Object> mapVo);

	/**
	 * 核算类下拉加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncomeCheckTypeId(Map<String, Object> mapVo);

	/**
	 * 应收票据添加
	 * @param mapVo
	 * @return
	 */
	int addPaperIncome(Map<String, Object> mapVo);

	/**
	 * 应收票据查询
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncome(Map<String, Object> mapVo);

	/**
	 * 查询票据编号是否存在
	 * @param object
	 * @return
	 */
	int queryPaperIncomeCounts(Map<String,Object> vo);

	/**
	 * 应收票据查询-分页
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncome(Map<String, Object> mapVo, RowBounds rowBounds);

	/**
	 * 背书数据新增
	 * @param mapVo 
	 * @param mapVo
	 * @return
	 */
	int addBeishu(@Param("alllistVo")List<Map> alllistVo, @Param("mapVo")Map<String, Object> mapVo);

	/**
	 * 查询是否可以修改
	 * @param mapVo 
	 * @param mapVo
	 * @return
	 */
	int accPaperIncomeUpdateQueryCount(Map<String, Object> mapVo);

	/**
	 * 修改回显数据
	 * @param mapVo
	 * @return
	 */
	Map<String, Object> accPaperIncomeUpdateQuery(Map<String, Object> mapVo);

	/**
	 * 应收票据修改
	 * @param mapVo
	 * @return
	 */
	int updatePaperIncome(Map<String, Object> mapVo);

	/**
	 * 背书人查询
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncomeBook(Map<String, Object> mapVo);

	/**
	 * 修改之删除背书人
	 * @param mapVo
	 * @return 
	 */
	int accPaperIncomeUpdateBeiShuDelete(Map<String, Object> mapVo);

	/**
	 * 票据删除之先删除背书信息
	 * @param mapVo
	 */
	void deleteAccPaperIncomeBeiShu(@Param("list")List<Map> list,@Param("vo")Map<String, Object> vo);

	/**
	 * 删除票据
	 * @param mapVo
	 */
	void deleteAccPaperIncome(@Param("list")List<Map> list,@Param("vo")Map<String, Object> mapVo);

	/**
	 * 批量验证是否有不可删除的数据,有侧剔除
	 * @param mapVo
	 */
	List<Map> queryPaperIncomeCountState(@Param("list")List<Map> list,@Param("vo")Map<String, Object> mapVo);

	/**
	 * 审核票据
	 * @param mapVo
	 */
	int updateAuditAccPaperIncome(@Param("list")List<Map> list,@Param("vo")Map<String, Object> mapVo);

	/**
	 * 取消票据审核
	 * @param list
	 * @param mapVo
	 */
	int updateCancelAccPaperIncome(@Param("list")List<Map> list,@Param("vo")Map<String, Object> mapVo);

	/**
	 * 票据作废
	 * @param list
	 * @param mapVo
	 * @return
	 */
	int updateZuofeiAccPaperIncome(@Param("list")List<Map> list,@Param("vo")Map<String, Object> mapVo);

	/**
	 * 退票
	 * @param mapVo
	 */
	int updateRefundAccPaperIncome(Map<String, Object> mapVo);

	/**
	 * 收款
	 * @param mapVo
	 */
	int updatePutAccPaperIncome(Map<String, Object> mapVo);

	/**
	 * 制单人下拉加载
	 * @param mapVo
	 */
	List<Map<String, Object>> queryAccPaperIncomeOpCreateuser(Map<String, Object> mapVo);

	/**
	 * 审核人下拉加载
	 * @param mapVo
	 */
	List<Map<String, Object>> queryAccPaperIncomeOpAudituser(Map<String, Object> mapVo);

	/**
	 * 删除背书信息
	 * @param arrid
	 */
	int deletePaperIncomeBeishu(@Param("list")List<Map> list,@Param("vo")Map<String, Object> mapVo);

	/**
	 * 条件查询-核算项下拉加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryAccPaperIncomeCheckNo(Map<String, Object> mapVo);

}

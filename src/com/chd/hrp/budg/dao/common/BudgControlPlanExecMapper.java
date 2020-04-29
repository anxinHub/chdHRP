/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 

public interface BudgControlPlanExecMapper extends SqlMapper{
	
	/**
	 * 查询控制环节，及状态是否在控制范围 环节编码 LINK_CODE
	 * @param mapVo 
	 * @return
	 * @throws DataAccessException
	 */
	public int queryLinkCodeIsExists(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询操作日期对应的预算年、月 及对应启始、结束日期
	 * @param mapVo 
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryOperDateBeginEndDate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据控制环节及控制节点 返回对应的启用 is_start=1 预算控制方案表中信息 
	 * @param mapVo 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryLinkCodeMap(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室领用出库 材料支出预算 明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryWorkBudgList(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室领用出库 材料支出预算 非明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryWorkBudgListOne(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室领用出库 材料支出预算 明细总额控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryWorkBudgListThree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室领用出库 医疗支出预算  明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatOutMedCostTwo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室领用出库 医疗支出预算  非明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatOutMedCostOne(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室领用出库 医疗支出预算  明细总额控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatOutMedCostThree(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryWorkBudgAssInList(Map<String, Object> matOutMapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --采购入库 材料采购预算  明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatInMatPurTwo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 -- 科室申领 材料支出预算  明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatApplyMatInvTwo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室申领 材料支出预算  非明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatApplyMatInvOne(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --科室申领 材料支出预算  明细总额控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatApplyMatInvThree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 -- 科室申领 医疗支出预算  明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatApplyMedCostTwo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 -- 科室申领 医疗支出预算  非明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatApplyMedCostOne(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 -- 科室申领 医疗支出预算  明细总额控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatApplyMedCostThree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --采购计划 材料采购预算  明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatPurMatPurTwo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --采购订单 材料采购预算  明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMatOrderMatPurTwo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --会计凭证 --明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgAccVouchMedCostTwo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --会计凭证 --非明细控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgAccVouchMedCostOne(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算控制执行过程查看 各计算项数据查询 --会计凭证 --明细总额控制
	 * @param mapVo 集团、医院、账套、预算年度、预算月份、方案编码、模块编码、控制环节、预算表、控制状态、占用状态、关联环节、预算年启始日期、预算年结束日期、预算月份启始日期、预算月份结束日期
	 * @return map 预算科室、预算分类、预算 单据值、预算值、已执行值
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgAccVouchMedCostThree(Map<String, Object> mapVo) throws DataAccessException;
	
	
}

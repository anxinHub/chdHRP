package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrWritten;
/**
 * 笔试成绩
 * @author Administrator
 *
 */
public interface HrWrittenMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrWritten> queryWrittenById(Map<String, Object> entityMap);
    /**
     * 删除笔试成绩
     * @param entityList
     */
	void deleteWritten(List<HrWritten> entityList);
	/**
	 * 删除全部笔试成绩
	 * @param alllistVo
	 */
	void deleteHrWritten(List<HrWritten> alllistVo);
	/**
	 * 提交笔试成绩到晋级汇总表
	 * @param hrWritten
	 */
	void addPromotionEvaluate(HrWritten hrWritten);
	/**
	 * 更新晋级汇总表的笔试成绩（批量）
	 * @param List<HrWritten>
	 */
	void updateWriteScoreBatch(@Param(value = "list") List<HrWritten> list);
	/**
	 * 修改状态为提交
	 * @param hrWritten
	 */
	void confirmWritten(HrWritten hrWritten);
	/**
	 * 修改笔试能力提交状态（批量）
	 * @param List<HrWritten>
	 */
	void updateStateBatch(@Param(value = "list") List<HrWritten> list);
	/**
	 * 撤回笔试成绩
	 * @param hrWritten
	 */
	void reConfirmPromotionEvaluate(HrWritten hrWritten);
	/**
	 * 修改状态为新建
	 * @param hrWritten
	 */
	void reConfirmWritten(HrWritten hrWritten);
	/**
	 * 查询是否重复
	 * @param alllistVo
	 * @return
	 */
	List<Map<String, Object>> queryHrWrittenByEmpId(List<HrWritten> alllistVo);
	/**
	 * 通过提交状态查数据记录数
	 * @param map
	 * @param list
	 * @return
	 */
	int queryByState(@Param(value = "map") Map<String, Object> map, @Param(value = "list") List<HrWritten> list);
}

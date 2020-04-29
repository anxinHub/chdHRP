package com.chd.hrp.hr.dao.salarymanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HrStandardMapper extends SqlMapper{

	//查询数据表下拉框
	List<Map<String, Object>> queryStandardTabCodeOption(Map<String, Object> mapVo);
	
	//查询关联字段下拉
	List<Map<String, Object>> queryStandardColCodeOption(Map<String, Object> mapVo);

	//添加薪资标准信息
	int addStandard(Map<String, Object> mapVo);

	//人员限定条件信息
	int addStandardCdt(@Param("vo")Map<String, Object> mapVo,@Param("list")List<Map> list);

	//查询自增ID
	int queryStanId();

	//薪资标准查询
	List<Map<String, Object>> queryStandard(Map<String, Object> mapVo);
	List<Map<String, Object>> queryStandard(Map<String, Object> mapVo, RowBounds rowBounds);

	//删除薪资标准相关条件
	int deleteStandardCond(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Integer> list);

	//删除薪资标准数据
	int deleteStandard(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Integer> list);

	//薪资标准条件启用--接口停用
	int qiyongStandard(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Integer> list);

	//薪资标准修改回显
	Map<String, Object> queryStandardUpdate(Map<String, Object> mapVo);

	//删除薪资标准信息
	void deleteStandardCdt(Map<String, Object> mapVo);

	//修改薪资标准
	int updateStandard(Map<String, Object> mapVo);

	//薪资标准修改明细回显
	List<Map<String,Object>> queryStandardCtd(Map<String, Object> mapVo);

	//添加时验证是否有重复的启用数据
	int queryStandardStateCount(Map<String, Object> mapVo);

	//查询是否有重复的启用数据
	int queryCopyStateStandardCount(Map<String, Object> mapVo);

	//复制明细数据
	int addCopyStandardCdt(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Map<String,Object>> list);
	
	//复制薪资标准数据
	int addCopyStandard(Map<String, Object> mapVo);

	//岗位级别下拉加载
	List<Map<String, Object>> queryStandardRankodeOption(Map<String, Object> mapVo);

	//薪资标准维护查询表头
	List<Map<String,Object>> queryStandardMaintain(Map<String, Object> mapVo);

	//薪资维护数据添加
	int addMaintain(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Map> list);

	//薪资维护查询
	List<Map<String, Object>> queryMaintain(Map<String, Object> mapVo);
	List<Map<String, Object>> queryMaintain(Map<String, Object> mapVo,
			RowBounds rowBounds);
	
	/**
	 * 取薪资标准表（下拉选用）（状态：已启用的）
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectHrWageStan(Map<String, Object> paramMap);

	//维护数据-先删除所有维护数据
	int deleteMaintain(Map<String, Object> mapVo);

	//查询是否可以修改-存在明细数据则不可以修改
	int querStandardUpdateCode(Map<String, Object> mapVo);

	//删除薪资标准维护数据
	int deleteweihu(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Integer> list);

	//查询要复制的明细数据
	List<Map> queryStandardMaintainList(
			Map<String, Object> mapVo);
	
	/**
	 * 取薪资标准表条件（通过外键）
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 * @author yang
	 */
	public List<Map<String, Object>> findHrWageStanCondByFK(Map<String, Object> paramMap) throws DataAccessException;

	//表数据加载-只查询人员基本情况
	List<Map<String, Object>> queryStandardTabCodeOptionEditable(Map<String, Object> mapVo);

	//关联数据加载--只查询人员基本情况相关数据
	List<Map<String, Object>> queryStandardColCodeOptionEditable(Map<String, Object> mapVo);

}

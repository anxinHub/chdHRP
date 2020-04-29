package com.chd.hrp.hr.dao.salarymanagement.socialsecuritymanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.salarymanagement.socialsecuritymanagement.HrAreatopay;

public interface AreatopayMapper extends SqlMapper{

	//社保险种下拉加载
	List<Map<String, Object>> queryAreatopayInsurtypeSelect(Map<String, Object> mapVo);

	//删除所有数据
	void deleteFromAreapay(Map<String, Object> mapVo);

	//批量添加缴费金额
	int addAreapay(@Param("vo")Map<String, Object> mapVo, @Param("list")List<HrAreatopay> list);

	//缴费金额查询
	List<Map<String, Object>> queryAreatopay(Map<String, Object> mapVo);
	List<Map<String, Object>> queryAreatopay(Map<String, Object> mapVo,RowBounds rowBounds);

	//条件查询下拉加载
	List<Map<String, Object>> queryAreatopayInsurtypeSelects(
			Map<String, Object> mapVo);

	//查询是否有存在的重复数据
	HrAreatopay queryImportAreatopay(Map<String, Object> saveMap);

}

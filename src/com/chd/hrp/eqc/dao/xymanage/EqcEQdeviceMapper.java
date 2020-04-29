package com.chd.hrp.eqc.dao.xymanage;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface EqcEQdeviceMapper extends SqlMapper{
	//模糊查询
	List<Map<String, Object>> queryEQdevice(Map<String, Object> mapVo);

	//添加
	int addEQdevice(Map<String, Object> mapVo);
	//删除
	int deleteEQdevice(Map<String, Object> mapVo);
	//修改
	int updateEQdevice(Map<String, Object> mapVo);

}

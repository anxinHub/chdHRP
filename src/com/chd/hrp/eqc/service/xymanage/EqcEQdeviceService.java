package com.chd.hrp.eqc.service.xymanage;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface EqcEQdeviceService extends SqlService  {
	//查询
	String queryEQdevice(Map<String, Object> mapVo);

	//添加
	String addEQdevice(Map<String, Object> mapVo);

	//删除
	String deleteEQdevice(Map<String, Object> mapVo);
	
	//添加
	String updateEQdevice(Map<String, Object> mapVo);

}

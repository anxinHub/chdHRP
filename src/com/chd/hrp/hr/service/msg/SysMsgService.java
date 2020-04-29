package com.chd.hrp.hr.service.msg;

import java.util.List;
import java.util.Map;

import com.chd.hrp.hr.entity.msg.SysMsg;

public interface SysMsgService {

	public List<SysMsg> getUserPushMsg(Map<String,Object> mapVo);
	
	public List<Map<String,Object>> getUserBirthMsg(Map<String,Object> mapVo);
	
	public void addMsg(SysMsg msg);
}

package com.chd.hrp.hr.serviceImpl.msg;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chd.hrp.hr.dao.msg.SysMsgMapper;
import com.chd.hrp.hr.entity.msg.SysMsg;
import com.chd.hrp.hr.service.msg.SysMsgService;

@Service("sysMsgService")
public class SysMsgServiceImpl implements SysMsgService{

	@Resource(name = "sysMsgMapper")
	private final SysMsgMapper sysMsgMapper=null;
	public List<SysMsg> getUserPushMsg(Map<String,Object> mapVo){		
		List<SysMsg> list=sysMsgMapper.selectUserPushMsg(null);
		return list;
	}
	@Override
	public List<Map<String, Object>> getUserBirthMsg(Map<String, Object> mapVo) {
		List<Map<String, Object>> list=sysMsgMapper.getUserBirthMsg(mapVo);
		return list;
	}
	@Override
	public void addMsg(SysMsg msg) {
		sysMsgMapper.insert(msg);
		
	}
}

package com.chd.hrp.htc.serviceImpl.relative.plan.chargeitemvalue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.relative.plan.chargeitemvalue.HtcRelativeChargeItemValueMapper;
import com.chd.hrp.htc.entity.relative.plan.chargeitemvalue.HtcRelativeChargeItemValue;
import com.chd.hrp.htc.service.relative.plan.chargeitemvalue.HtcRelativeChargeItemValueService;
import com.github.pagehelper.PageInfo;



@Service("htcRelativeChargeItemValueService")
public class HtcRelativeChargeItemValueServiceImpl implements HtcRelativeChargeItemValueService{
	
private static Logger logger = Logger.getLogger(HtcRelativeChargeItemValueServiceImpl.class);
	
	@Resource(name = "htcRelativeChargeItemValueMapper")
	private final HtcRelativeChargeItemValueMapper htcRelativeChargeItemValueMapper = null;

	@Override
	public String addHtcRelativeChargeItemValue(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcRelativeChargeItemValue htcRelativeChargeItemValue = htcRelativeChargeItemValueMapper.queryHtcRelativeChargeItemValueByCode(entityMap);
			
			if(null != htcRelativeChargeItemValue){
				
				return "{\"error\":\"数据已存在.\",\"state\":\"false\"}";
			}
			
			htcRelativeChargeItemValueMapper.addHtcRelativeChargeItemValue(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");	
		}
	}

	@Override
	public String queryHtcRelativeChargeItemValue(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<HtcRelativeChargeItemValue> list = htcRelativeChargeItemValueMapper.queryHtcRelativeChargeItemValue(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcRelativeChargeItemValue> list = htcRelativeChargeItemValueMapper.queryHtcRelativeChargeItemValue(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcRelativeChargeItemValue queryHtcRelativeChargeItemValueByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcRelativeChargeItemValueMapper.queryHtcRelativeChargeItemValueByCode(entityMap);
	}

	@Override
	public String deleteBatchHtcRelativeChargeItemValue(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcRelativeChargeItemValueMapper.deleteBatchHtcRelativeChargeItemValue(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");	
		}
	}

	@Override
	public String updateHtcRelativeChargeItemValue(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcRelativeChargeItemValueMapper.updateHtcRelativeChargeItemValue(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");	
		}
	}
}

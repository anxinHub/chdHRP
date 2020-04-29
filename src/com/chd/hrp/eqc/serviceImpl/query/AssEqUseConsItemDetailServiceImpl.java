package com.chd.hrp.eqc.serviceImpl.query;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.eqc.dao.query.AssEqUseConsItemDetailMapper;
import com.chd.hrp.eqc.dao.query.AssEqUseConsItemMapper;
import com.chd.hrp.eqc.service.query.AssEqUseConsItemDetailService;
import com.github.pagehelper.PageInfo;

/**
 * Title: AssEqUseConsItemDetailServiceImpl
 * @author ChengXiaoDong
 * @version 2020年4月17日 下午12:54:30
*/

/**
* 14设备使用消耗资源记录 ASS_EQUseConsumableItem Service实现类
*/
@Service("assEqUseConsItemDetailService")
public class AssEqUseConsItemDetailServiceImpl implements AssEqUseConsItemDetailService {

	private static Logger logger = Logger.getLogger(AssEqUseConsItemDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assEqUseConsItemDetailMapper")
	private final AssEqUseConsItemDetailMapper assEqUseConsItemDetailMapper = null;
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqUseConsItemDetailMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqUseConsItemDetailMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
    
}

package com.chd.hrp.acc.serviceImpl.autovouch.his;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.his.AccBalDetailIMapper;
import com.chd.hrp.acc.entity.AccBalDetailI;
import com.chd.hrp.acc.service.autovouch.his.AccBalDetailIService;
import com.github.pagehelper.PageInfo;
@Service("accBalDetailIService")
public class AccBalDetailIServiceImpl implements AccBalDetailIService {
	private static Logger logger = Logger.getLogger(AccBalDetailIServiceImpl.class);

	@Resource(name = "accBalDetailIMapper")
	private final AccBalDetailIMapper accBalDetailIMapper = null;
	@Override
	public String queryAccBalDetailI(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accBalDetailIMapper.queryAccBalDetailI(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accBalDetailIMapper.queryAccBalDetailI(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryAccBalDetailIPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accBalDetailIMapper.queryAccBalDetailI(entityMap);
		
		return list;
	}

}

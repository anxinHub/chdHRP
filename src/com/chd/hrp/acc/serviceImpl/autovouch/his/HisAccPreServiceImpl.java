/**
 @Copyright: Copyright (c) 2015-2-14 
 @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch.his;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.his.HisAccPreMapper;
import com.chd.hrp.acc.service.autovouch.his.HisAccPreService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 客户字典属性表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccPreService")
public class HisAccPreServiceImpl implements HisAccPreService {

	private static Logger logger = Logger.getLogger(HisAccPreServiceImpl.class);

	@Resource(name = "hisAccPreMapper")
	private final HisAccPreMapper hisAccPreMapper = null;

	//住院
	@Override
	public String queryHisAccPreI(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		if (sysPage.getTotal() == -1) {

			if(entityMap.get("sumTypeId")!=null && entityMap.get("sumTypeId").equals("1")){
				//按收款员
				list = hisAccPreMapper.queryHisAccPreI(entityMap);
			}else{
				//按结算方式
				list = hisAccPreMapper.queryHisAccPreIByType(entityMap);
			}
			
			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			if(entityMap.get("sumTypeId")!=null && entityMap.get("sumTypeId").equals("1")){
				//按收款员
				list = hisAccPreMapper.queryHisAccPreI(entityMap, rowBounds);
			}else{
				//按结算方式
				list = hisAccPreMapper.queryHisAccPreIByType(entityMap, rowBounds);
			}

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
		
		
	}

	@Override
	public String queryHisAccPreDetailI(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = hisAccPreMapper.queryHisAccPreDetailI(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = hisAccPreMapper.queryHisAccPreDetailI(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	//门诊
	@Override
	public String queryHisAccPreO(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		if (sysPage.getTotal() == -1) {
			
			
			if(entityMap.get("sumTypeId")!=null && entityMap.get("sumTypeId").equals("1")){
				//按收款员
				list = hisAccPreMapper.queryHisAccPreO(entityMap);
			}else{
				//按结算方式
				list = hisAccPreMapper.queryHisAccPreOByType(entityMap);
			}

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			if(entityMap.get("sumTypeId")!=null && entityMap.get("sumTypeId").equals("1")){
				//按收款员
				list = hisAccPreMapper.queryHisAccPreO(entityMap, rowBounds);
			}else{
				//按结算方式
				list = hisAccPreMapper.queryHisAccPreOByType(entityMap, rowBounds);
			}

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryHisAccPreDetailO(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = hisAccPreMapper.queryHisAccPreDetailO(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = hisAccPreMapper.queryHisAccPreDetailO(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public int queryHisAccType(Map<String, Object> entityMap) throws DataAccessException {
		return hisAccPreMapper.queryHisAccType(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryHisAccPreIPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();

			if(entityMap.get("sumTypeId")!=null && entityMap.get("sumTypeId").equals("1")){
				//按收款员
				list = hisAccPreMapper.queryHisAccPreI(entityMap);
			}else{
				//按结算方式
				list = hisAccPreMapper.queryHisAccPreIByType(entityMap);
			}
			
			return list;
	}

	@Override
	public List<Map<String, Object>> queryHisAccPreOPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
			List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
			if(entityMap.get("sumTypeId")!=null && entityMap.get("sumTypeId").equals("1")){
				//按收款员
				list = hisAccPreMapper.queryHisAccPreO(entityMap);
			}else{
				//按结算方式
				list = hisAccPreMapper.queryHisAccPreOByType(entityMap);
			}

			return list;
	}

	@Override
	public List<Map<String, Object>> queryHisAccPreDetailIPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = hisAccPreMapper.queryHisAccPreDetailI(entityMap);

		return list;
	}

	@Override
	public List<Map<String, Object>> queryHisAccPreDetailOPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = hisAccPreMapper.queryHisAccPreDetailO(entityMap);

		return list;
	}

}

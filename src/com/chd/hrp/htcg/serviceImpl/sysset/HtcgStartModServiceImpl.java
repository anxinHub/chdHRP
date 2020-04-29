package com.chd.hrp.htcg.serviceImpl.sysset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.htcg.service.syset.HtcgStartModService;
import com.chd.hrp.sys.dao.ModMapper;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.entity.ModStart;
import com.github.pagehelper.PageInfo;

@Service("htcgStartModService")
public class HtcgStartModServiceImpl implements HtcgStartModService {
	@Resource(name = "modMapper")
	private final ModMapper modMapper = null;
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	/**
	 * @Description 查询Mod分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryhtcgMod(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Mod> list = modMapper.queryMod(entityMap, rowBounds);
		List<Mod> resultList = new ArrayList<Mod>();
		for(Mod temp : list){
			entityMap.put("mod_code", temp.getMod_code());
			ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
			if(modStart != null){
				temp.setModStart(modStart);
			}
			resultList.add(temp);
		}
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(resultList, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 添加ModStart
	 * @param ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String saveHtcgMod(Map<String,Object> entityMap)throws DataAccessException{
		ModStart modStart = modStartMapper.queryModStartByCode(entityMap);
		if(modStart != null){
			if( StringTool.isNotBlank(modStart.getStart_month())){
				return "{\"msg\":\"已经启动.\",\"state\":\"true\"}";
			}
			modStartMapper.updateModStart(entityMap);
			return "{\"msg\":\"启用成功.\",\"state\":\"true\"}";
		}else{
			modStartMapper.addModStart(entityMap);
			return "{\"msg\":\"启用成功.\",\"state\":\"true\"}";
		}
	}

}

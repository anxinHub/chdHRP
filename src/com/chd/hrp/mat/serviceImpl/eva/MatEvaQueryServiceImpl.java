package com.chd.hrp.mat.serviceImpl.eva;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.eva.MatEvaQueryMapper;
import com.chd.hrp.mat.dao.eva.MatEvaSchemeMapper;
import com.chd.hrp.mat.service.eva.MatEvaQueryService;
import com.github.pagehelper.PageInfo;

@Service("matEvaQueryService")
public class MatEvaQueryServiceImpl implements MatEvaQueryService{

	private static Logger logger = Logger.getLogger(MatEvaQueryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matEvaQueryMapper")
	private final MatEvaQueryMapper matEvaQueryMapper = null;
	
	@Override
	public String queryMatEvaReportMain(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> targetList = matEvaQueryMapper.queryMatEvaSchemeTarget(mapVo);
		StringBuffer sb = new StringBuffer();
		StringBuffer select = new StringBuffer();
		for(int i = 0; i < targetList.size(); i++){
			sb.append("'" + targetList.get(i).get("target_code") + "' t_" + targetList.get(i).get("target_code") + ",");
			select.append("t_" + targetList.get(i).get("target_code") + ",");
		}
		mapVo.put("pivotsql", sb.substring(0, sb.length()-1));
		mapVo.put("selectsql", select.substring(0, select.length()-1));
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage"); 
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matEvaQueryMapper.queryMatEvaReportMain(mapVo);
			return ChdJson.toJsonLower(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matEvaQueryMapper.queryMatEvaReportMain(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	@Override
	public String queryMatEvaReportDetail(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> targetList = matEvaQueryMapper.queryMatEvaSchemeTarget(mapVo);
		StringBuffer sb = new StringBuffer();
		StringBuffer avg = new StringBuffer();
		StringBuffer select = new StringBuffer();
		for(int i = 0; i < targetList.size(); i++){
			sb.append("'" + targetList.get(i).get("target_code") + "' t_" + targetList.get(i).get("target_code") + ",");
			avg.append("round(avg(t_"+ targetList.get(i).get("target_code") + "), 2) t_" + targetList.get(i).get("target_code") + ",");
			select.append("t_" + targetList.get(i).get("target_code") + ",");
		}
		mapVo.put("pivotsql", sb.substring(0, sb.length()-1));
		mapVo.put("avgsql", avg.substring(0, avg.length()-1));
		mapVo.put("selectsql", select.substring(0, select.length()-1));
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> resultList = matEvaQueryMapper.queryMatEvaReportDetail(mapVo);
			return ChdJson.toJsonLower(resultList);	
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matEvaQueryMapper.queryMatEvaReportDetail(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public String queryTargetCodeThead(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> targetList = matEvaQueryMapper.queryMatEvaSchemeTarget(mapVo);

		return ChdJson.toJsonLower(targetList);
	}

	@Override
	public String queryMatEvaTarget(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(matEvaQueryMapper.queryMatEvaTarget(mapVo));
	}
}

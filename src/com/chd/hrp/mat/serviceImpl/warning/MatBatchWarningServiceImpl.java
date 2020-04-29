package com.chd.hrp.mat.serviceImpl.warning;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.warning.MatBatchWarningMapper;
import com.chd.hrp.mat.service.warning.MatBatchWarningService;
import com.github.pagehelper.PageInfo;

@Service("matBatchWarningService")
public class MatBatchWarningServiceImpl implements MatBatchWarningService {

	private static Logger logger = Logger.getLogger(MatBatchWarningServiceImpl.class);

	@Resource(name = "matBatchWarningMapper")
	private final MatBatchWarningMapper matBatchWarningMapper = null;

	// 查询材料效期预警
	@Override
	public String queryMatBatchWarning(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = matBatchWarningMapper.queryMatBatchWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list =
		 matBatchWarningMapper.queryMatBatchWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}

}

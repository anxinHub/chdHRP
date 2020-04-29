package com.chd.hrp.med.serviceImpl.warning;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.warning.MedBatchWarningMapper;
import com.chd.hrp.med.service.warning.MedBatchWarningService;
import com.github.pagehelper.PageInfo;

@Service("medBatchWarningService")
public class MedBatchWarningServiceImpl implements MedBatchWarningService {

	private static Logger logger = Logger.getLogger(MedBatchWarningServiceImpl.class);

	@Resource(name = "medBatchWarningMapper")
	private final MedBatchWarningMapper medBatchWarningMapper = null;

	// 查询材料效期预警
	@Override
	public String queryMedBatchWarning(Map<String, Object> entityMap) throws DataAccessException {
		 SysPage sysPage = new SysPage();
		
		 sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
		
		List<Map<String,Object>> list = medBatchWarningMapper.queryMedBatchWarning(entityMap);
		
		 return ChdJson.toJson(list);
		
		 }else{
		
		 RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		 sysPage.getPagesize());
		
		 List<Map<String,Object>> list =
		 medBatchWarningMapper.queryMedBatchWarning(entityMap, rowBounds);
		
		 PageInfo page = new PageInfo(list);
		
		 return ChdJson.toJson(list, page.getTotal());
		
		 }
	}

}

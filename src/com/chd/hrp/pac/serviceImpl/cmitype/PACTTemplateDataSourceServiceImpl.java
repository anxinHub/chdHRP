package com.chd.hrp.pac.serviceImpl.cmitype;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.cmitype.PACTTemplateDataSourceMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.cmitype.PACTTemplateDataSource;
import com.chd.hrp.pac.service.cmitype.PACTTemplateDataSourceService;
import com.github.pagehelper.PageInfo;

@Service("PACTTemplateDataSourceService")
public class PACTTemplateDataSourceServiceImpl implements PACTTemplateDataSourceService{
	private static Logger logger = Logger.getLogger(PACTTemplateDataSourceServiceImpl.class);
	@Resource(name = "PACTTemplateDataSourceMapper")
	private final PACTTemplateDataSourceMapper PACTTSMapper = null;
	@Override
	public String addPACTTemplateDataSource(List<PACTTemplateDataSource> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			if (!mapVo.isEmpty()) {
				PACTTemplateDataSource pactSoyrce = mapVo.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("IFB_GROUPID", pactSoyrce.getIFB_GROUPID());
				map.put("IFB_PrjName", pactSoyrce.getIFB_PrjName());
				map.put("COPY_CODE", pactSoyrce.getCOPY_CODE());
				map.put("CS_Code", pactSoyrce.getCS_Code());
				Integer RowId = PACTTSMapper.queryMaxrowid(map);
				if (RowId == null) {
					RowId = 1;
					for (PACTTemplateDataSource pactSoyrce2 : mapVo) {
						RowId=RowId+1;
						pactSoyrce2.setCS_Rowid(RowId);
					}
				} else {
					for (PACTTemplateDataSource pactSoyrce2 : mapVo) {							
							RowId=RowId+1;
							pactSoyrce2.setCS_Rowid(RowId);					
					}
				}
				//PACTTSMapper.deleteByCSCode(map);
				PACTTSMapper.addBatchPACTTemplateDataSource(mapVo);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
		/*
		try {
			if (mapVo.size()>0) {
				PACTTemplateDataSource pactSoyrce =mapVo.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("IFB_GROUPID", pactSoyrce.getIFB_GROUPID());
				map.put("IFB_PrjName", pactSoyrce.getIFB_PrjName());
				map.put("COPY_CODE", pactSoyrce.getCOPY_CODE());				
				PACTTSMapper.addBatchPACTTemplateDataSource(mapVo);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		*/
	}

	@Override
	public String updatePACTTemplateDataSource(List<PACTTemplateDataSource> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		try {	
		if(mapVo.size()>0) {
			PACTTemplateDataSource pactSoyrce =mapVo.get(0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("IFB_GROUPID", pactSoyrce.getIFB_GROUPID());
			map.put("IFB_PrjName", pactSoyrce.getIFB_PrjName());
			map.put("COPY_CODE", pactSoyrce.getCOPY_CODE());
			PACTTSMapper.updatePACTTemplateDataSource(mapVo);
		}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}	
	}

	@Override
	public String deletePACTTemplateDataSource(List<PACTTemplateDataSource> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		try {	
			if(mapVo.size()>0) {
				PACTTemplateDataSource pactSoyrce =mapVo.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("IFB_GROUPID", pactSoyrce.getIFB_GROUPID());
				map.put("IFB_PrjName", pactSoyrce.getIFB_PrjName());
				map.put("COPY_CODE", pactSoyrce.getCOPY_CODE());
				PACTTSMapper.deletePACTTemplateDataSource(mapVo);
			}
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}catch (Exception e) {
				logger.warn(e.getMessage(), e);
				throw new SysException(e.getMessage(), e);
			}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPACTTemplateDataSource(Map<String, Object> mapvo) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapvo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query =(List<Map<String,Object>>) PACTTSMapper.queryPACTTSourceByCode(mapvo);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>)PACTTSMapper.query(mapvo, rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page=new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());			
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}


	
}
package com.chd.hrp.pac.serviceImpl.cmitype;

import java.util.HashMap;
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
import com.chd.hrp.pac.dao.cmitype.COSTBUSISOURECDICTMapper;
import com.chd.hrp.pac.dao.cmitype.PACTInterfaceTypeMapper;
import com.chd.hrp.pac.entity.cmitype.PACTInterfaceType;
import com.chd.hrp.pac.service.cmitype.PACTInterfaceTypeService;
import com.github.pagehelper.PageInfo;
@Service("PACTInterfaceTypeService")
public class PACTInterfaceTypeServiceImpl implements PACTInterfaceTypeService {
	
	private static Logger logger = Logger.getLogger(PACTInterfaceTypeServiceImpl.class);

	@Resource(name = "PACTInterfaceTypeMapper")
	private PACTInterfaceTypeMapper PACTITypeMapper = null;
	
	@Resource(name = "COSTBUSISOURECDICTMapper")
	private COSTBUSISOURECDICTMapper CSystemmapper = null;
	


	@Override
	public String addBatchPACTInterfaceType(List<PACTInterfaceType> mapVo) {
		// TODO Auto-generated method stub
		try {
			if (mapVo.size()>0) {
				PACTInterfaceType pactInterfaceType = mapVo.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("IFB_GROUPID", pactInterfaceType.getIFB_GROUPID());
				map.put("IFB_PrjName", pactInterfaceType.getIFB_PrjName());
				map.put("COPY_CODE", pactInterfaceType.getCOPY_CODE());				
				PACTITypeMapper.addBatchPACTInterfaceType(mapVo);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPACTInterfaceType(Map<String, Object> mapVo) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query =(List<Map<String,Object>>) PACTITypeMapper.queryPACTInterfaceType(mapVo);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) PACTITypeMapper.queryPACTInterfaceType(mapVo,rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page=new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
				
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	
	@Override
	public String deletePACTInterfaceTypByStatus(List<PACTInterfaceType> list) {
		// TODO Auto-generated method stub		
		try {
			if (list.size()>0) {
				PACTInterfaceType pactInterfaceType = list.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("IFB_GROUPID", pactInterfaceType.getIFB_GROUPID());
				map.put("IFB_PrjName", pactInterfaceType.getIFB_PrjName());
				map.put("COPY_CODE", pactInterfaceType.getCOPY_CODE());			
				PACTITypeMapper.deletePACTInterfaceTypByStatus(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}  catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}		
	}
	
	@Override
	public String updatePACTInterfaceType(List<PACTInterfaceType> list) {
		// TODO Auto-generated method stub
		try {
			if (list.size()>0) {
				PACTInterfaceType pactInterfaceType = list.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("IFB_GROUPID", pactInterfaceType.getIFB_GROUPID());
				map.put("IFB_PrjName", pactInterfaceType.getIFB_PrjName());
				map.put("COPY_CODE", pactInterfaceType.getCOPY_CODE());				
				PACTITypeMapper.updatePACTInterfaceTyp(list);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}  catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	

	
	


}

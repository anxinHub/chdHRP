package com.chd.hrp.pac.serviceImpl.fkxy.pactinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkxy.pactinfo.PactDetFKXYMapper;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactDetFKXYService;
import com.github.pagehelper.PageInfo;

@Service("pactDetFKXYService")
public class PactDetFKXYServiceImpl implements PactDetFKXYService {

	private static Logger logger = Logger.getLogger(PactDetFKXYServiceImpl.class);

	@Resource(name = "pactDetFKXYMapper")
	private PactDetFKXYMapper pactDetFKXYMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryPactDetFKXY(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("change_code") == null) {
				mapVo.put("table_code", "PACT_DET_FKXY");
			} else {
				mapVo.put("table_code", "PACT_DET_FKXY_C");
			}

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactDetFKXYEntity> list = (List<PactDetFKXYEntity>) pactDetFKXYMapper.query(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactDetFKXYEntity> list = (List<PactDetFKXYEntity>) pactDetFKXYMapper.query(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
  
	@Override
	public String updatePactDetFKXY(List<PactDetFKXYEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactDetFKXYMapper.updateBatch(list);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetFKXY(List<PactDetFKXYEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactDetFKXYMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetFKXYByPactCode(List<Map<String, Object>> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactDetFKXYMapper.deletePactDetFKXYByPactCode(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	  @Override
	    public String queryPactFKXYMaterial(Map<String ,Object>mapVo){
	    	
	    	try{
	    		SysPage sysPage = new SysPage();
				sysPage = (SysPage) mapVo.get("sysPage");
				if (sysPage.getTotal() == -1) {
					List<Map<String,Object>> list = (List<Map<String,Object>>) pactDetFKXYMapper.queryPactFKXYMaterial(mapVo);
					return ChdJson.toJson(list);
				} else {
					RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
					List<Map<String,Object>> list = (List<Map<String,Object>>) pactDetFKXYMapper.queryPactFKXYMaterial(mapVo);
					PageInfo page = new PageInfo(list);
					return ChdJson.toJson(list, page.getTotal());
				}
	    		
	    	}catch(Exception e){
	    		
	    		logger.warn(e.getMessage(),e);
	    		throw new SysException(e.getMessage(),e);
	    	}
	    	
	    }
}

package com.chd.hrp.sys.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.SysHisDeptRefMapper;
import com.chd.hrp.sys.service.SysHisDeptRefService;
import com.github.pagehelper.PageInfo;


@Service("sysHisDeptRefService")
public class SysHisDeptRefServiceImpl implements SysHisDeptRefService{
	
	private static Logger logger = Logger.getLogger(SysHisDeptRefServiceImpl.class);

	@Resource(name = "sysHisDeptRefMapper")
	private final SysHisDeptRefMapper sysHisDeptRefMapper = null;

	@Override
	public String addSysHisDeptRef(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			//业务类型dept_type 1.开单 2.执行 3共用
			Map<String, Object> existsMmap = sysHisDeptRefMapper.querySysHisDeptRefByCode(mapVo);
			if(null!=existsMmap){
				return "{\"error\":\"对应关系已存在!.\",\"state\":\"false\"}";
			}
			//一个HIS科室加业务类型只能维护一次,禁止HIS科室加业务类型对应多个HRP科室
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", mapVo.get("group_id"));
			map.put("hos_id", mapVo.get("hos_id"));
			map.put("his_dept_code", mapVo.get("his_dept_code"));
			map.put("dept_type", mapVo.get("dept_type"));
			Map<String, Object> hisDeptMap = sysHisDeptRefMapper.querySysHisDeptRefByCode(map);
			if(null != hisDeptMap){
				return "{\"error\":\"HIS科室业务类型已存在!.\",\"state\":\"false\"}";
			}
			sysHisDeptRefMapper.addSysHisDeptRef(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败\"}";
		}
	}

	@Override
	public String querySysHisDeptRef(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		    SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = sysHisDeptRefMapper.querySysHisDeptRef(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = sysHisDeptRefMapper.querySysHisDeptRef(entityMap, rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public String deleteBatchSysHisDeptRef(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
       try {
			
    		sysHisDeptRefMapper.deleteBatchSysHisDeptRef(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败\"}";
		}
	}

}

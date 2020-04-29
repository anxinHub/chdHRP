package com.chd.hrp.hip.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.quartz.QuartzManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.hip.dao.HipDataJobMapper;
import com.chd.hrp.hip.entity.HipDataJob;
import com.chd.hrp.hip.quartz.DataJobImpl;
import com.chd.hrp.hip.service.HipDataJobService;
import com.github.pagehelper.PageInfo;

@Service("dataJobService")
public class HipDataJobServiceImpl implements HipDataJobService {
	
	private static Logger logger = Logger.getLogger(HipDataJobServiceImpl.class);
	
	@Autowired
	private HipDataJobMapper dataJobMapper;
	
	@Override
	public String queryDataJob(Map<String, Object> entityMap)throws DataAccessException {
		SysPage sysPage =(SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<HipDataJob> list = (List<HipDataJob>) dataJobMapper.queryDataJobByPage(entityMap, rowBounds);
		PageInfo<HipDataJob> page = new PageInfo<HipDataJob>(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String addDataJob(Map<String, Object> map)throws DataAccessException {
		try {
			HipDataJob job=dataJobMapper.queryByUniqueness(map);
			if(job!=null){
				return "{\"error\":\"任务编码重复，请修改后重试\"}";
			}
			map.put("id", UUIDLong.absStringUUID());
			dataJobMapper.addDataJob(map);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 addDataJob\"}";

		}
	}

	@Override
	public String updateDataJob(Map<String, Object> map)
			throws DataAccessException {
		try {
			HipDataJob job=dataJobMapper.queryByUniqueness(map);
			if(job!=null){
				return "{\"error\":\"任务编码重复，请修改后重试\"}";
			}
			//查询任务状态，如果运行中，则不让删除
			job=dataJobMapper.queryByCode(map);			
			if(job!=null&&job.getState()==1)
				return "{\"error\":\"所修改任务在运行中，请先停止任务执行在修改\"}";
			dataJobMapper.updateJob(map);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 方法 updateDataJob\"}";

		}
	}
	
	@Override
	public HipDataJob queryDataJobById(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		return dataJobMapper.queryByCode(map);
	}

	@Override
	public String deleteDataJob(List<Map<String, Object>> listMap)throws DataAccessException {
			try {
				dataJobMapper.deleteDataJob(listMap);

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteDataJob\"}";

			}
	}
	
	@Override
	public void synDataJob(String uri) throws DataAccessException{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("state", 1);
		List<HipDataJob> list=dataJobMapper.queryDataJob(map);
		Map<String,Object> paraMap=null;
		for(HipDataJob job:list){
			try{
				paraMap=new HashMap<String,Object>();
				paraMap.put("uri", uri);
				paraMap.put("isCheck", "false");
				paraMap.put("job",job);
				QuartzManager.addJob(job.getId().toString(), job.getGroup_id().toString(), job.getId().toString(), job.getGroup_id().toString(), DataJobImpl.class,job.getCron(),paraMap);
			}catch(Exception e){
				logger.error("任务同步失败，任务号:"+job.getId()+" "+e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
		}
	}
	
	@Override
	public String startDataJob(List<String> listVo,String uri)throws DataAccessException {
		try {
			HipDataJob job=null;
			Map<String,Object> map=new HashMap<String,Object>();
			Map<String,Object> paraMap=null;
			for (String id : listVo) {
				map.clear();
				map.put("id", id);
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("mod_code", SessionManager.getModCode());
				job = (HipDataJob) dataJobMapper.queryByCode(map);
				if (job != null) {
					if (job.getState() != 1 && job.getIs_stop()==0) {
						paraMap=new HashMap<String,Object>();
						paraMap.put("uri", uri);
						paraMap.put("isCheck", "false");
						paraMap.put("type_id", job.getType_id());
						paraMap.put("job", job);
						QuartzManager.addJob(job.getId().toString(), map.get("group_id").toString(), job.getId().toString(), map.get("group_id").toString(), DataJobImpl.class,job.getCron(),paraMap);
						map.put("state", 1);
						dataJobMapper.updateJob(map);
					} else {
						continue;
					}
				}
			}
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String stopDataJob(List<String> listVo) throws DataAccessException {
		try {
			HipDataJob job=null;
			Map<String,Object> map=new HashMap<String,Object>();
			for (String id : listVo) {
				map.clear();
				map.put("id", id);
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("mod_code", SessionManager.getModCode());
				job = (HipDataJob) dataJobMapper.queryByCode(map);
				if (job != null) {
					if (job.getState() != 0) {
						QuartzManager.removeJob(job.getId().toString(), map.get("group_id").toString(), job.getId().toString(), map.get("group_id").toString());
						map.put("state", 0);
						dataJobMapper.updateJob(map);
					} else {
						continue;
					}
				}
			}
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryDataType(Map<String, Object> map)throws DataAccessException {
		return JSON.toJSONString(dataJobMapper.queryDataType(map));
	}
}

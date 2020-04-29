package com.chd.hrp.ass.serviceImpl.tendnotify;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.FileUtil;
import com.chd.hrp.ass.dao.tend.AssTendMapper;
import com.chd.hrp.ass.dao.tendnotify.AssTendNotifyMapper;
import com.chd.hrp.ass.entity.tend.AssTendMain;
import com.chd.hrp.ass.service.tend.AssTendService;
import com.chd.hrp.ass.service.tendnotify.AssTendNotifyService;
import com.github.pagehelper.PageInfo;

/**
 * 中标通知书
 * 
 * @author Administrator
 *
 */
@Service("assTendNotifyService")
public class AssTendNotifyServiceImpl implements AssTendNotifyService {
	private static Logger logger = Logger.getLogger(AssTendNotifyServiceImpl.class);

	@Resource(name = "assTendNotifyMapper")
	private final AssTendNotifyMapper assTendNotifyMapper = null;

	@Override
	/**
	 * 查询方法
	 */
	public String queryAssTendNotifyMain(Map<String, Object> mapvo) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapvo.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<AssTendMain> list = assTendNotifyMapper.queryAssTendNotifyMain(mapvo, rowBounds);
		PageInfo<AssTendMain> page = new PageInfo<AssTendMain>(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	/**
	 * 添加招标文件管理表
	 */
	public int addAssTendFile(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return assTendNotifyMapper.addAssTendFile(mapVo);
	}

	@Override
	/**
	 * 修改招标主表
	 */
	public int updateAssTendMain(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return assTendNotifyMapper.updateAssTendMain(mapVo);
	}

	@Override
	public String queryAssTendFile(Map<String, Object> mapvo) throws DataAccessException {
		// TODO Auto-generated method stub
		return ChdJson.toJson(assTendNotifyMapper.queryAssTendFile(mapvo));
	}

	@Override
	public int deleteTendFile(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return assTendNotifyMapper.deleteTendFile(mapVo);
	}

}

package com.chd.hrp.acc.serviceImpl.autovouch.accamorttize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeInfoMapper;
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeTypeMapper;
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeTypeService;
import com.github.pagehelper.PageInfo;

@Service("accAmortizeTypeService")
public class AccAmortizeTypeServiceImpl implements AccAmortizeTypeService {

	private static Logger logger = Logger.getLogger(AccAmortizeTypeServiceImpl.class);

	@Resource(name = "accAmortizeTypeMapper")
	private AccAmortizeTypeMapper accAmortizeTypeMapper;

	@Resource(name = "accAmortizeInfoMapper")
	private AccAmortizeInfoMapper accAmortizeInfoMapper;

	@Override
	public String saveAmortizeType(Map<String, Object> mapVo) {
		try {
			Map<String, Object> exist = accAmortizeTypeMapper.queryTypeCodeExist(mapVo);
			if (exist != null && !exist.isEmpty()) {
				return "{\"error\":\"添加失败，编码已存在!\"}";
			}
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

			accAmortizeTypeMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryAmortizeType(Map<String, Object> mapVo) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) accAmortizeTypeMapper.query(mapVo);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) accAmortizeTypeMapper.query(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public Map<String, Object> queryAmortizeTypeByCode(Map<String, Object> mapVo) {
		Map<String, Object> accAmortizeType = accAmortizeTypeMapper.queryTypeCodeExist(mapVo);
		return accAmortizeType;
	}

	@Override
	public String deleteAmortizeType(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			String type_code = mapVo.get("type_code").toString();
			String[] typeArr = type_code.split(",");
			for (String string : typeArr) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type_code", string);
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				List<?> used = accAmortizeInfoMapper.query(map);
				if (used.isEmpty()) {
					list.add(map);
				} else {
					return "{\"error\":\"删除失败，编码" + string + "已使用!\"}";
				}
			}

			accAmortizeTypeMapper.deleteBatch(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateAmortizeType(Map<String, Object> mapVo) {
		try {
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

			accAmortizeTypeMapper.update(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> amortizeTypeSelect(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accAmortizeTypeMapper.queryAmortizeTypeSelect(mapVo);
		return list;
	}

}

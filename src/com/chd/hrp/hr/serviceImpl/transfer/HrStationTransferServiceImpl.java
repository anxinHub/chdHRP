package com.chd.hrp.hr.serviceImpl.transfer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.transfer.HrStationTransferMapper;
import com.chd.hrp.hr.entity.transfer.HrStationTransfer;
import com.chd.hrp.hr.service.transfer.HrStationTransferService;
import com.github.pagehelper.PageInfo;

@Service("hrStationTransferService")
public class HrStationTransferServiceImpl implements HrStationTransferService {
	private static Logger logger = Logger.getLogger(HrStationTransferServiceImpl.class);

	@Resource(name = "hrStationTransferMapper")
	private final HrStationTransferMapper hrStationTransferMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 调岗增加
	 */
	@Override
	@SuppressWarnings("unused")
	public String addHrStationTransfer(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrStationTransfer> list = hrStationTransferMapper.queryStationTransferById(entityMap);

		if (list.size() > 0) {

			for (HrStationTransfer hrStationTransfer : list) {
				/*
				 * if (hrStationTransfer.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrStationTransfer.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrStationTransfer.getKind_name().equals(entityMap.get("kind_name"))) {
				 * return "{\"error\":\"名称：" + hrStationTransfer.getKind_name().toString() +
				 * "已存在.\"}"; }
				 */
			}
		}
		try {

			
			int state = hrStationTransferMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 调岗修改
	 */
	@Override
	public String updateHrStationTransfer(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrStationTransferMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 调岗删除
	 */
	@Override
	public String deleteHrStationTransfer(List<HrStationTransfer> entityList) throws DataAccessException {

		try {

			hrStationTransferMapper.deleteStationTransfer(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 调岗查询
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String queryHrStationTransfer(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			
			List<HrStationTransfer> list = (List<HrStationTransfer>) hrStationTransferMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrStationTransfer> list = (List<HrStationTransfer>) hrStationTransferMapper.query(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrStationTransfer queryByCodeStationTransfer(Map<String, Object> entityMap) throws DataAccessException {

		return hrStationTransferMapper.queryByCode(entityMap);
	}

	/***
	 * 审核调岗增加
	 */
	@Override
	public String auditStationTransfer(List<Map<String, Object>> entityList) throws DataAccessException {
		return hrStationTransferMapper.auditStationTransfer(entityList);
	}

	/**
	 * 销审调岗增加
	 */
	@Override
	public String reAuditStationTransfer(List<Map<String, Object>> entityList) throws DataAccessException {
		return hrStationTransferMapper.reAuditStationTransfer(entityList);
	}
}

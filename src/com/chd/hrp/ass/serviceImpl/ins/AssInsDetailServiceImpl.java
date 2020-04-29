/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.ins;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.ins.AssInsDetailMapper;
import com.chd.hrp.ass.dao.ins.AssInsMainMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.ins.AssInsDetail;
import com.chd.hrp.ass.entity.ins.AssInsMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.ins.AssInsDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050601 资产安装明细
 * @Table: ASS_INS_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assInsDetailService")
public class AssInsDetailServiceImpl implements AssInsDetailService {

	private static Logger logger = Logger.getLogger(AssInsDetailServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assInsDetailMapper")
	private final AssInsDetailMapper assInsDetailMapper = null;
	@Resource(name = "assInsMainMapper")
	private final AssInsMainMapper assInsMainMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加050601 资产安装明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssInsDetail(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050601 资产安装明细
		AssInsDetail assInsDetail = queryAssInsDetailByCode(entityMap);

		if (assInsDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assInsDetailMapper.addAssInsDetail(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050601 资产安装明细<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssInsDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInsDetailMapper.addBatchAssInsDetail(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050601 资产安装明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssInsDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assInsDetailMapper.updateAssInsDetail(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050601 资产安装明细<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssInsDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInsDetailMapper.updateBatchAssInsDetail(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050601 资产安装明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssInsDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assInsDetailMapper.deleteAssInsDetail(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050601 资产安装明细<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssInsDetail(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			Map<String, Object> inMapVo =new HashMap<String, Object>();
			inMapVo.put("group_id",entityList.get(0).get("group_id"));
	    	inMapVo.put("hos_id",entityList.get(0).get("hos_id"));
	    	inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
	    	inMapVo.put("ins_id", entityList.get(0).get("ins_id"));
			assInsDetailMapper.deleteBatchAssInsDetail(entityList);
			
			List<AssInsDetail> list = assInsDetailMapper.queryAssInsDetail(inMapVo);
			double ins_money = 0;
			for(AssInsDetail temp :  list ){
				ins_money += Double.parseDouble(temp.getIns_money().toString());
			}
			inMapVo.put("ins_money", ins_money+"");
			assInsMainMapper.updateInsMoney(inMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"ins_money\":\""+ins_money+"\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050601 资产安装明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdateAssInsDetail(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象050601 资产安装明细
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("ins_detail_id", entityMap.get("ins_detail_id"));
		Map<String, Object> validateMapVo = new HashMap<String, Object>();
		Map<String, Object> inMapVo = new HashMap<String, Object>();
		inMapVo.put("group_id", entityMap.get("group_id"));
		inMapVo.put("hos_id", entityMap.get("hos_id"));
		inMapVo.put("copy_code", entityMap.get("copy_code"));
		inMapVo.put("ins_id", entityMap.get("ins_id"));
		validateMapVo.put("group_id", entityMap.get("group_id"));
		validateMapVo.put("hos_id", entityMap.get("hos_id"));
		validateMapVo.put("copy_code", entityMap.get("copy_code"));
		validateMapVo.put("ass_code", entityMap.get("ass_code"));
		validateMapVo.put("ass_model", entityMap.get("ass_model"));
		validateMapVo.put("ass_spec", entityMap.get("ass_spec"));
		validateMapVo.put("ass_brand", entityMap.get("ass_brand"));
		validateMapVo.put("fac_id", entityMap.get("fac_id"));
		validateMapVo.put("ins_id", entityMap.get("ins_id"));
		// AssInsDetail assInsDetail= queryAssInsDetailByCode(entityMap);

		try {
			List<AssInsDetail> list = assInsDetailMapper.queryAssInsDetailExists(mapVo);
			if (list.size() > 0) {

				int state = assInsDetailMapper.updateAssInsDetail(entityMap);
				
				List<AssInsDetail> listDet = assInsDetailMapper.queryAssInsDetail(inMapVo);
				double ins_money = 0;
				for(AssInsDetail temp :  listDet ){
					ins_money += Double.parseDouble(temp.getIns_money().toString());
				}
				inMapVo.put("ins_money", ins_money+"");
				assInsMainMapper.updateInsMoney(inMapVo);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"ins_money\":\""+ins_money+"\"}";
			}

			else {
				List<AssInsDetail> validateList = (List<AssInsDetail>) assInsDetailMapper
						.queryByAssInsId(validateMapVo);
				if (validateList.size() > 0) {
					return "{\"error\":\"资产信息重复.\"}";
				}
				int state = assInsDetailMapper.addAssInsDetail(entityMap);
				
				List<AssInsDetail> listDet = assInsDetailMapper.queryAssInsDetail(inMapVo);
				double ins_money = 0;
				for(AssInsDetail temp :  listDet ){
					ins_money += Double.parseDouble(temp.getIns_money().toString());
				}
				inMapVo.put("ins_money", ins_money+"");
				assInsMainMapper.updateInsMoney(inMapVo);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ins_money\":\""+ins_money+"\"}";

			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050601 资产安装明细<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssInsDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssInsDetail> list = assInsDetailMapper.queryAssInsDetail(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssInsDetail> list = assInsDetailMapper.queryAssInsDetail(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	public String queryAssInsDetailByUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = assInsDetailMapper.queryAssInsDetailByUpdate(entityMap);
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 获取对象050601 资产安装明细<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssInsDetail queryAssInsDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assInsDetailMapper.queryAssInsDetailByCode(entityMap);
	}

	/**
	 * @Description 获取050601 资产安装明细<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssInsDetail
	 * @throws DataAccessException
	 */
	@Override
	public AssInsDetail queryAssInsDetailByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assInsDetailMapper.queryAssInsDetailByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050601 资产安装明细<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssInsDetail>
	 * @throws DataAccessException
	 */
	@Override
	public List<AssInsDetail> queryAssInsDetailExists(Map<String, Object> entityMap) throws DataAccessException {
		return assInsDetailMapper.queryAssInsDetailExists(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assInsDetailMapper.queryExists(entityMap);
	}

	@Override
	public String initAssInsDetailtBid(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assInsDetailMapper.initAssInsDetailtBid(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public List<AssInsDetail> queryAssInsDetailByAccept(Map<String, Object> entityMap) throws DataAccessException {
		return assInsDetailMapper.queryAssInsDetailByAccept(entityMap);
	}

	@Override
	public String deleteBatchAssInsContractMap(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			int state = assInsDetailMapper.deleteBatchAssInsContractMap(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

}

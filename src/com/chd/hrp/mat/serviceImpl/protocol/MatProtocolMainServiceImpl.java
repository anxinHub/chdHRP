/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.protocol.MatProtocolDetailMapper;
import com.chd.hrp.mat.dao.protocol.MatProtocolFileMapper;
import com.chd.hrp.mat.dao.protocol.MatProtocolMainMapper;
import com.chd.hrp.mat.entity.MatProtocolMain;
import com.chd.hrp.mat.service.protocol.MatProtocolMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 *state：0：新建 1：审核 2：确认<BR> 
 * @Table:
 * MAT_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matProtocolMainService")
public class MatProtocolMainServiceImpl implements MatProtocolMainService {

	private static Logger logger = Logger.getLogger(MatProtocolMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matProtocolMainMapper")
	private final MatProtocolMainMapper matProtocolMainMapper = null;
    
	@Resource(name = "matProtocolDetailMapper")
	private final MatProtocolDetailMapper matProtocolDetailMapper = null;
	
	@Resource(name = "matProtocolFileMapper")
	private final MatProtocolFileMapper matProtocolFileMapper = null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	/**
	 * @Description 
	 * 添加state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatProtocolMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象state：0：新建 1：审核 2：确认

		List<MatProtocolMain> list = matProtocolMainMapper.queryMatProtocolMainByID(entityMap);

		if (list.size()>0) {

			return "{\"error\":\"协议名称"+entityMap.get("protocol_name").toString()+"重复,请重新添加.\",\"state\":\"false\"}";

		}
		entityMap.put("creat_emp", null);
		entityMap.put("check_emp", null);
		entityMap.put("confirm_emp", null);
		entityMap.put("state", 1);
		entityMap.put("spell", StringTool.toPinyinShouZiMu(entityMap.get("protocol_name").toString()));
		entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("sign_date").toString(), "yyyy-MM-dd"));
		
		if(entityMap.get("mana_emp") != null && "undefined".equals(entityMap.get("mana_emp").toString())){
			entityMap.put("mana_emp", null);
		}
		if(entityMap.get("file_path") != null && "undefined".equals(entityMap.get("file_path").toString())){
			entityMap.put("file_path", null);
		}
		
		//判断签订日期是否在系统启用之前，用来判断是否是期初协议还是其他协议
		int count = 0;
		count = matCommonMapper.existsDateCheckBefore(entityMap);
		if(count > 0){
			entityMap.put("is_init", "1");//期初
		}else{
			entityMap.put("is_init", "0");//采购
		}
		
		try {
			
			int state = matProtocolMainMapper.addMatProtocolMain(entityMap);
			
			Long protocol_id = matProtocolMainMapper.qureyCurrval();
		
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+"|"+
				entityMap.get("hos_id").toString()+"|"+
				entityMap.get("copy_code").toString()+"|"+
				protocol_id+"|"+
				entityMap.get("state").toString()+"|"
				+"\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatProtocolMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatProtocolMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matProtocolMainMapper.addBatchMatProtocolMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatProtocolMain\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatProtocolMain(Map<String,Object> entityMap)throws DataAccessException{

		try {
			List<MatProtocolMain> list = matProtocolMainMapper.queryMatProtocolMainByID(entityMap);
	
			if (list.size()>0) {
				return "{\"error\":\"协议名称"+entityMap.get("protocol_name").toString()+"重复,请重新添加.\"}";
			}
			
			if(entityMap.get("mana_emp") != null && "undefined".equals(entityMap.get("mana_emp").toString())){
				entityMap.put("mana_emp", null);
			}
			if(entityMap.get("file_path") != null && "undefined".equals(entityMap.get("file_path").toString())){
				entityMap.put("file_path", null);
			}
			
		  int state = matProtocolMainMapper.updateMatProtocolMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatProtocolMain\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatProtocolMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matProtocolMainMapper.updateBatchMatProtocolMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatProtocolMain\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatProtocolMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			int state = matProtocolMainMapper.deleteMatProtocolMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatProtocolMain\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatProtocolMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			Long stateExists = matProtocolMainMapper.existsMatProtocolMainStateBatch(entityList);
			
			if(stateExists > 0){
				return "{\"error\":\"删除失败 !只有新建状态的数据允许删除 deleteBatchMatProtocolMain\"}";
			}
			
			matProtocolDetailMapper.deleteBatchMatProtocolDetail(entityList);
			matProtocolFileMapper.deleteBatchMatProtocolFile(entityList);
			matProtocolMainMapper.deleteBatchMatProtocolMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatProtocolMain\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatProtocolMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象state：0：新建 1：审核 2：确认
		
		MatProtocolMain matProtocolMain = queryMatProtocolMainByCode(entityMap);

		if (matProtocolMain != null) {

			int state = matProtocolMainMapper.updateMatProtocolMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matProtocolMainMapper.addMatProtocolMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatProtocolMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatProtocolMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatProtocolMain> list = matProtocolMainMapper.queryMatProtocolMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatProtocolMain> list = matProtocolMainMapper.queryMatProtocolMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 多表联合查询 采购协议详细信息 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatProtocolMainInfo(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatProtocolMainInfo(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatProtocolMainInfo(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatProtocolMain queryMatProtocolMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matProtocolMainMapper.queryMatProtocolMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatProtocolMain
	 * @throws DataAccessException
	*/
	@Override
	public MatProtocolMain queryMatProtocolMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matProtocolMainMapper.queryMatProtocolMainByUniqueness(entityMap);
	}
	/**
	 * 据协议类型Id 查询其开始年月 、协议前缀 ,同时生成 协议编号
	 * @param mapVo
	 * @return
	 */
	public String queryMatProtocolTypePre(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String,Object> preMap = matProtocolMainMapper.queryMatProtocolTypePre(mapVo);
		
		Map<String,Object> codeMap = new HashMap<String,Object>();
		codeMap.put("group_id", preMap.get("group_id"));
		codeMap.put("hos_id", preMap.get("hos_id"));
		codeMap.put("copy_code", preMap.get("copy_code"));
		codeMap.put("year", mapVo.get("year"));
		codeMap.put("table_code", "MAT_PROTOCOL_MAIN");
		codeMap.put("prefixe", preMap.get("pre"));
		// 判断该 类型 协议编码是否存在（存在取 max_no,不存在 则max_no=1 添加新记录 ）
		int count = matNoManageMapper.queryIsExists(codeMap);
		String max_no = "";
		if(count == 0){
			max_no = "1";
			codeMap.put("max_no", max_no);
			matNoManageMapper.add(codeMap);
		}else{
			//更新该业务流水码+1
			matNoManageMapper.updateMaxNo(codeMap);
			//取出该业务更新后的流水码
			max_no = matNoManageMapper.queryMaxCode(codeMap);
		}
		//补充流水号为4位（如：0001）
		for (int i = max_no.length() ; i < 4; i++) {
			max_no = "0" + max_no;
		}
		codeMap.put("max_no", max_no);
		return ChdJson.toJson(codeMap);
	}
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	/**
	 * grid明细编辑  文档类别下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatFileType(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(matProtocolMainMapper.queryMatFileType(mapVo, rowBounds));
	}
	/**
	 * grid明细编辑  管理科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryManaDept(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(matProtocolMainMapper.queryManaDept(mapVo, rowBounds));
	}
	/**
	 * grid明细编辑  管理员下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryManaEmp(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(matProtocolMainMapper.queryManaEmp(mapVo, rowBounds));
	}
	/**
	 * 物资材料明细列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatInvDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatInvDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 根据ID 多表联合查询采购协议（修改页面回值用）
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMatProtocolMainUnit(Map<String, Object> mapVo) throws DataAccessException{
		return matProtocolMainMapper.queryMatProtocolMainUnit(mapVo);
	}
	/**
	 * 协议明细
	 * @param entityMap
	 * @return
	 */
	public String queryMatProtocolDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatProtocolDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatProtocolDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 协议文档信息
	 * @param entityMap
	 * @return
	 */
	public String queryMatProtocolFile(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatProtocolFile(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matProtocolMainMapper.queryMatProtocolFile(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 查询协议主表的当前protocol_id
	 * @return
	 */
	public Long qureyCurrval() throws DataAccessException {
		 
		return matProtocolMainMapper.qureyCurrval();
	}
	
	/**
	 * 审核、消审、确认、取消确认、终止、取消终止
	 * @param listVo
	 * @return
	 */
	public String updateState(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
				
				int state = matProtocolMainMapper.updateState(listVo);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addOrUpdateMatProtocolMain\"}";
	
			}
	}
	/**
	 * 查询  指定采购协议的协议类型的开始年月 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryTypePre(Map<String, Object> mapVo) throws DataAccessException {
		return matProtocolMainMapper.queryMatProtocolTypePre(mapVo);
	}
	@Override
	public String queryMatProtocolMainPur(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list  = matProtocolMainMapper.queryMatProtocolMainPur(entityMap);
		
		if(list.size() > 0){
		
		for (Map<String, Object> map : list) {
			
			
			return "{\"msg\":\"\",\"state\":\"true\",\"price\":\""+map.get("price")+"\"}";
			
		   }
			
		}
		
		return "{\"error\":\"\",\"state\":\"false\"}";
	}
}

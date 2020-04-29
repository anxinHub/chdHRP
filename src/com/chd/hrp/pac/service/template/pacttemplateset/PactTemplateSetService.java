package com.chd.hrp.pac.service.template.pacttemplateset;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;


public interface PactTemplateSetService extends SqlService {
	/**
	 * 合同模板配置方案 保存（新增或修改）
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String savePactTemplateSet(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 选择合同模板后  根据 模板id 查询 合同模板表单模块/表格模块数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryTemplateModular(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 合同配置项查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactTemplateItem(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 根据 合同模板配置方案模块id  查询 合同模板表单配置项/表格配置项数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactTemplateItemSet(Map<String, Object> page) throws DataAccessException;
	/**
	 * 新增或修改 合同模板配置方案模块
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String savePactTemplateModular(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 合同模板配置方案 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactTemplateSet(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 根据 合同模板配置方案模块 id 删除 模块数据及后续设置数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deletePactTemplateModular(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 表单配置  保存
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String savePactTemplateSheetItemSet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 表单模块元素配置  信息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryPactTemplateSheet(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据 方案id,模块id 查询 按钮信息   渲染按钮用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateButtonInfo(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 表格模块标签页配置  信息查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactTemplateGrid(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 表格模块标签页配置 保存
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String savePactTemplateGridItemSet(Map<String, Object> mapVo) throws DataAccessException;
	 
	/**
	 * 双击 删除表格配置页面 页签
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deletePactTemplateGridtab(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据id查询表格页签
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryPactTemplateGridTab(Map<String, Object> mapVo) throws DataAccessException;

   /**
    * 取数函数下拉
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	public String queryCsCode(Map<String, Object> mapVo) throws DataAccessException;
}

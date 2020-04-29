package com.chd.webservice.server.his;

import java.util.Map;

import javax.jws.WebService;

/**
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2017年2月26日 下午2:08:21
*  @Company: 杭州亦童科技有限公司
 * @网站：www.e-tonggroup.com
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
@WebService
public interface HisService {
	
	/**
	 * 声明hrp对外通过实时接口。
	 * <p>
	 * 详细接口方案说明请参看 <b>HRP接口文档—Webservice方式</b> 的文档说明
	 * <p>
	 * <h4>各参数详细说明</h4><br>
	 * <ul type="disc">
	 * <li>传入参数一：消息码（transType）
	 * <pre>
	 *  消息码                 描述
	 *  1000                  系统平台信息
	 *  1001                  财务接口信息
	 *  1002                  预算接口信息
	 *  1003                  成本接口信息
	 *  1004                  物流接口信息
	 *  1005                  资产接口信息
	 * </pre>
	 * <ul type="circle">
	 * <li>新增业务消息码递增</li>
	 * <li>必填项，为空则返回相应错误信息</li>
	 * </ul>
	 * </li>
	 * <li>传入参数二：数据来源（ dscode）
	 * <ul type="circle">
	 * <li>当前医院所属集团ID，在确定接口方案前有HRP提供给各接口厂商，其值为固定值</li>
	 * </ul>
	 * </li>
	 * <li>传入参数三：交易码（ processingCode）
	 * <pre>
	 *  交易代码	     描述
     *  100010	     科室字典下载（查询）
     *  100011	     科室字典上传（新增）
     *  100012	     科室字典上传（修改）
     *  100013	     科室字典上传（删除）
     *  100020	     收费类别下载（查询）            
     *  100021	     收费类别上传（新增）
     *  100022	     收费类别上传（修改）
     *  100023	     收费类别上传（删除）
     *  100030	     收费项目下载（查询）            
     *  100031	     收费项目上传（新增）
     *  100032	     收费项目上传（修改）
     *  100033	     收费项目上传（删除）
     *  100410	     材料字典下载（查询）            
     *  100411	     材料字典上传（新增）
     *  100412	     材料字典上传（修改）
     *  100413	     材料字典上传（删除）
     *  100421	     查询材料库存查询（区分批号）
     *  100422	     查询材料库存查询（不区分批号）
     *  100423	     根据条码查询材料库存
     *  100431	     医嘱材料出库请求（高值耗材）
     *  100432	     医嘱材料退库请求（高值耗材）
     *  100441	     医嘱材料出库请求（先进先出）
     *  100442	     医嘱材料退库请求（先进先出）
	 * </pre>
	 * <ul type="circle">
	 * <li>按照不同业务进行传递各自的交易码</li>
	 * <li>必填项，为空则返回相应错误信息</li>
	 * </ul>
	 * </li>
	 * <li>传入参数四：集团ID（ group_id）
	 * <ul type="circle">
	 * <li>当前医院所属集团ID，在确定接口方案前有HRP提供给各接口厂商，其值为固定值</li>
	 * <li>必填项，为空则返回相应错误信息</li>
	 * </ul>
	 * </li>
	 * <li>传入参数五：医院ID（ hos_id）
	 * <ul type="circle">
	 * <li>当前医院所属ID，在确定接口方案前有HRP提供给各接口厂商，其值为固定值</li>
	 * <li>必填项，为空则返回相应错误信息</li>
	 * </ul>
	 * </li>
	 * <li>传入参数六：账套（copy_code）
	 * <ul type="circle">
	 * <li>当前医院所属ID，在确定接口方案前有HRP提供给各接口厂商，其值为固定值</li>
	 * <li>必填项，为空则返回相应错误信息</li>
	 * </ul>
	 * </li>
	 * <li>传入参数七：输入参数（inputStr）
	 * <pre>
	 * 输入参数（分业务定义的不同的输入串）， 
	 * 记录之间使用分割符‘^’分割，最后必须要以分割符号‘^’结尾。
	 * 字段之间之间使用分割符‘|’分割。
     * 如：收费流水号|收费日期|医嘱编号|收费项目编码|收费项目名称|物资材料编码|物资材料名称|
     *    材料批号|条形码|收费数量|消耗数量|收费单价|科室编码|科室名称|护士工号|护士名称|
     *    医生工号|医生姓名|患者姓名|住院号|病床号|医嘱类别|^
     *    收费流水号|收费日期|医嘱编号|收费项目编码|收费项目名称|物资材料编码|物资材料名称|
     *    材料批号|条形码|收费数量|消耗数量|收费单价|科室编码|科室名称|护士工号|护士名称|
     *    医生工号|医生姓名|患者姓名|住院号|病床号|医嘱类别|^
	 * </pre>
	 * <ul type="circle">
	 * <li>必填项，传入空则返回对应响应码</li>
	 * </ul>
	 * </li>
	 * <li>传入参数八：结束标识（item_indicator）
	 * <ul type="circle">
	 * <li>次参数主要用于一次传输多个医嘱传递规则</li>
	 * <pre>
	 *  传输条数大于1时：第一条标识为1
     *  中间记录统一标识为2,最后一条标识为9 表示此次；
     *  传输条数等于1：标识为0
	 * </pre>
	 * <li>传输为空则为0</li>
	 * </ul>
	 * </li>
	 * <li>输出参数：HRP接收信息后返回给各接口厂商的信息（OutputStr|ResponseCode）
	 * <ul type="circle">
	 * <li>正常业务消息</li>
	 * <pre>
	 * 记录之间使用分割符‘^’分割，最后必须要以分割符号‘^’结尾。
	 * 字段之间之间使用分割符‘|’分割。
     * 如： 仓库编码|仓库名称|材料编码|材料名称|材料批号|条形码|平均单价|当前库存数量|即时库存数量|货位|^
     *     仓库编码|仓库名称|材料编码|材料名称|材料批号|条形码|平均单价|当前库存数量|即时库存数量|货位|^
     *     仓库编码|仓库名称|材料编码|材料名称|材料批号|条形码|平均单价|当前库存数量|即时库存数量|货位|^
	 * </pre>
	 * <li>提醒消息</li>
	 * <pre>
	 *  例如
     *   拆分字符串失败
     *   查询材料实时库存失败(区分批号)
     *   查询材料实时库存失败(不区分批号)
     *   查询材料实时库存失败(条码)
     *   插入医嘱信息时，在HRP中未找到对应字典
     *   输入串不允许空串
     *   检索约束条件不允许空
     *   医嘱更新失败
     *   获取变更ID失败
     *   库存不足
	 * </pre>
	 * <li>业务信息根据业务进行递增</li>
	 * </ul>
	 * </li>
	 * </ol>
	 * @author bell
	 * @return 输出参数 JSON 格式返回
	 * <pre>
	 *  格式如下：
     *   key：prm_transtype                   消息类型
     *   key：prm_processingcode              交易代码 
     *   key：prm_group_id                    集团ID
     *   key：prm_hos_id                      医院ID
     *   key：prm_copy_code                   账套编码
     *   key：prm_inputstr                    输入参数
     *   key：prm_item_indicator              结束标识
     *   key：prm_outputdata                  输出参数（如需要返回集合则需要此KEY值）
     *           如科室字典 材料字典 需要返回结果集 格式如下 
     *                  key:DEPT_CODE         科室编码
     *                  key:DEPT_NAME         科室名称
     *                  key:SUPER_CODE        上级编码
     *                  key:IS_LAST           是否末级
     *   key：prm_outputstr                   输出参数 (用于返回响应消息 )
     *                                        成功返回 00  失败和提示 返回文字信息
     *   key: prm_responsecode                输出参数(HRP内置响应码，可忽略不计)
     *   
	 * </pre>
	 */
	public String hrpCommIof(String transType, String processingCode, int group_id, int hos_id, String copy_code,String dsCode, String inputStr, int item_indicator);
	/**
	 * 声明hrp对外通过实时接口。
	 * <p>
	 * 详细接口方案说明请参看 <b>HRP接口文档—Webservice方式</b> 的文档说明
	 * <p>
	 * @param transType                      消息类型
	 * @param processingCode                 交易代码
	 * @param group_id                       集团ID
	 * @param hos_id                         医院ID
	 * @param copy_code                      账套编码
	 * @param inputStr                       输入参数
	 * @param item_indicator                 结束标识
	 * @return 输出参数  MAP集合 KEY值说明
	 * <pre>
	 *  格式如下：
     *   key：prm_transtype                   消息类型
     *   key：prm_processingcode              交易代码 
     *   key：prm_group_id                    集团ID
     *   key：prm_hos_id                      医院ID
     *   key：prm_copy_code                   账套编码
     *   key：prm_inputstr                    输入参数
     *   key：prm_item_indicator              结束标识
     *   key：prm_outputdata                  输出参数（如需要返回集合则需要此KEY值）
     *           如科室字典 材料字典 需要返回结果集 格式如下 
     *                  key:DEPT_CODE         科室编码
     *                  key:DEPT_NAME         科室名称
     *                  key:SUPER_CODE        上级编码
     *                  key:IS_LAST           是否末级
     *   key：prm_outputstr                   输出参数 (用于返回响应消息 )
     *                                        成功返回 00  失败和提示 返回文字信息
     *   key: prm_responsecode                输出参数(HRP内置响应码，可忽略不计)
     *   
	 * </pre>
	 */
	//public Map<String,Object> hrpCommIofToMap(String transType, String processingCode, int group_id, int hos_id, String copy_code,String dsCode, String inputStr, int item_indicator);
}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.begin;
import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 控制方式（CONTROL_WAY）取自系统字典表
01不控制
02提示控制
03严格控制

支出项目性质（PAYMENT_ITEM_NATURE)取自系统字典表
01一般项目
02固定资产项目
03无形资产项目
04材料项目
05药品项目


是否管理费（IS_MANAGE）：0否1是
 * @Table:
 * BUDG_PAYMENT_ITEM
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgPaymentItmService extends SqlService {

}

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.accept;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.accept.AssAcceptPhoto;
import com.chd.hrp.ass.entity.contract.AssContractDetail;
/**
 * 
 * @Description:
 * 验收照片
 * @Table:
 * ASS_PHOTO_ACCEPT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssAcceptPhotoService extends SqlService {
	public List<AssAcceptPhoto> queryAssAcceptPhoto(Map<String,Object> entityMap)throws DataAccessException;
}

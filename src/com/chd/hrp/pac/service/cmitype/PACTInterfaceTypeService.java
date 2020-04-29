package com.chd.hrp.pac.service.cmitype;

import java.util.List;
import java.util.Map;
import com.chd.hrp.pac.entity.cmitype.PACTInterfaceType;



public interface PACTInterfaceTypeService {

	String queryPACTInterfaceType(Map<String, Object> page);

	String deletePACTInterfaceTypByStatus(List<PACTInterfaceType> mapVo);

	String addBatchPACTInterfaceType(List<PACTInterfaceType> mapVo);

	String updatePACTInterfaceType(List<PACTInterfaceType> list);


}

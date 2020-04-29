package com.chd.hrp.pac.controller.cmitype;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.chd.hrp.pac.service.cmitype.COSTBUSISOURECDICTService;


@Controller
@RequestMapping(value = "/hrp/pac/cmitype/system")
public class COSTBUSISOURECDICTController {
	@Resource(name = "COSTBUSISOURECDICTService")
	private COSTBUSISOURECDICTService CostService;
	
	
	@ResponseBody
	@RequestMapping(value = "/COSTBUSISOURECDICT", method = RequestMethod.POST)
	public Map<String, Object> queryCOSTBUSISOURECDICT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			String query = CostService.queryCOSTBUSISOURECDICT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
}

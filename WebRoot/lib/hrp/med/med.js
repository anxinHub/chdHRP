
//是否
var yes_or_no = {
		Rows : [ {
			"id" : "0",
			"text" : "否"
		}, {
			"id" : "1",
			"text" : "是"
		} ],
		Total : 2
	};
//计价方法
var price_type = {
		Rows : [ {
			"id" : "0",
			"text" : "先进先出"
		}, {
			"id" : "1",
			"text" : "移动加权平均"
		}, {
			"id" : "2",
			"text" : "一次性加权平均"
		} ],
		Total : 3
	};
//摊销方式
var amortize_type = {
		Rows : [ {
			"id" : "1",
			"text" : "一次性摊销"
		}, {
			"id" : "2",
			"text" : "五五摊销"
		} ],
		Total : 2
	};
//入库状态
var medInMain_state = {
		Rows : [ {
			"id" : 1,
			"text" : "未审核"
		}, {
			"id" : "2",
			"text" : "已审核"
		}, {
			"id" : "3",
			"text" : "已确认"
		} ],
		Total : 3
	};
//出库状态
var medOutMain_state = {
		Rows : [ {
			"id" : "1",
			"text" : "未审核"
		}, {
			"id" : "2",
			"text" : "已审核"
		}, {
			"id" : "3",
			"text" : "已确认"
		} ],
		Total : 3
	};

//采购计划主表单据类型
var medPurMain_purType = {
		Rows : [ {
			"id" : "1",
			"text" : "自购计划"
		}, {
			"id" : "2",
			"text" : "统购计划"
		} ],
		Total : 2
	};
//出库状态
var medPurMain_purType = {
		Rows : [ {
			"id" : "1",
			"text" : "未审核"
		}, {
			"id" : "2",
			"text" : "已审核"
		}, {
			"id" : "3",
			"text" : "已出库"
		} ],
		Total : 3
	};
//科室需求计划主表是否提交
var medRequireMain_isSubmit = {
		Rows : [ {
			"id" : "0",
			"text" : "未提交"
		}, {
			"id" : "1",
			"text" : "提交"
		} ],
		Total : 2
	};
//科室需求计划主表是否提交
var medRequireMain_isReturn = {
		Rows : [ {
			"id" : "0",
			"text" : "未回退"
		}, {
			"id" : "1",
			"text" : "回退"
		} ],
		Total : 2
	};
//科室需求计划  状态
var medRequireMain_state={
	Rows : [ {
		"id" : "0",
		"text" : "中止计划"
	}, {
		"id" : "1",
		"text" : "未审核"
	},{
		"id" : "2",
		"text" : "审核"
	}, {
		"id" : "3",
		"text" : "汇总执行"
	}],
	Total : 4
}
//订单 状态
var medOrderMain_state={
		Rows : [ {
			"id" : "0",
			"text" : "已中止"
		}, {
			"id" : "1",
			"text" : "未审核"
		},{
			"id" : "2",
			"text" : "已审核"
		}, {
			"id" : "3",
			"text" : "执行完毕"
		}, {
			"id" : "4",
			"text" : "已合并"
		}],
		Total : 4
	}
var medOrderMain_state2={
		Rows : [ {
			"id" : "0",
			"text" : "已中止"
		}, {
			"id" : "1",
			"text" : "新建"
		}, {
			"id" : "3",
			"text" : "部分执行"
		}, {
			"id" : "4",
			"text" : "已合并"
		}, {
			"id" : "5",
			"text" : "执行完毕"
		}],
		Total : 5
	}
//订单--采购方式
var medOrderMain_purType={
		Rows : [ {
			"id" : "1",
			"text" : "自购订单"
		}, {
			"id" : "2",
			"text" : "统购订单"
		}],
		Total : 2
	}
//订单--订单类型
var medOrderMain_orderType={
	Rows : [ {
		"id" : "1",
		"text" : "普通订单"
	}, {
		"id" : "2",
		"text" : "代销备货单"
	}],
	Total : 2
}

//调价单--状态
var medAdjust_state = {
		Rows : [ {
			"id" : "1",
			"text" : "未审核"
		}, {
			"id" : "2",
			"text" : "已审核"
		}],
		Total : 2	
}


//科室申请单--审核状态
var medApplyMain_checkState = {
		Rows : [ {
			"id" : "1",
			"text" : "新建"
		}, {
			"id" : "2",
			"text" : "审核"
		}, {
			"id" : "3",
			"text" : "发送"
		}, {
			"id" : "4",
			"text" : "退回"
		} ],
		Total : 3	
	}

//科室申请单--发送状态
var medApplyMain_appState = {
		Rows : [ {
			"id" : "0",
			"text" : "未发送"
		}, {
			"id" : "1",
			"text" : "已发送"
		} ],
		Total : 2	
	}

//科室申请单--完成状态
var medApplyMain_storeCheckState = {
		Rows : [ {
			"id" : "0",
			"text" : "待处理"
		}, {
			"id" : "1",
			"text" : "全部完成"
		}, {
			"id" : "2",
			"text" : "部分完成--其余待处理"
		}, {
			"id" : "3",
			"text" : "部分完成--其余未完成"
		}, {
			"id" : "4",
			"text" : "未完成"
		} ],
		Total : 5	
	}

//科室申请单--完成状态
var medCheckMain_state = {
		Rows : [ {
			"id" : "1",
			"text" : "未审核"
		}, {
			"id" : "2",
			"text" : "已审核"
		}, {
			"id" : "3",
			"text" : "已完成"
		}],
		Total : 3	
	}

//调拨单--业务类型
var medTranMain_busType = {
		Rows : [ {
			"id" : "1",
			"text" : "无偿调拨"
		}, {
			"id" : "2",
			"text" : "有偿调拨"
		}],
		Total : 2	
	}

//调拨单--调拨类型
var medTranMain_tranType = {
		Rows : [ {
			"id" : "1",
			"text" : "院内调拨"
		}, {
			"id" : "2",
			"text" : "院外调拨"
		}],
		Total : 2	
	}
//调拨单--调拨类型
var medTranMain_tranMethod = {
		Rows : [ {
			"id" : "1",
			"text" : "同价调拨"
		}, {
			"id" : "2",
			"text" : "异价调拨"
		}],
		Total : 2	
	}

//科室申请单--明细处理状态
var medApplyDetail_doState = {
		Rows : [ {
			"id" : "1",
			"text" : "未完成"
		}, {
			"id" : "2",
			"text" : "已完成"
		} ],
		Total : 3	
	}

//调拨单--调拨类型
var medTranMain_state = {
		Rows : [ {
			"id" : "1",
			"text" : "未确认"
		}, {
			"id" : "2",
			"text" : "调出确认"
		}, {
			"id" : "3",
			"text" : "调入确认"
		}],
		Total : 3	
	}

//材料库存汇总查询表设置--方向
var medShowSet_directionFlag = {
		Rows : [ {
			"id" : "1",
			"text" : "增加"
		}, {
			"id" : "2",
			"text" : "减少"
		}],
		Total : 2	
	}

//ABC分类字典
var medInv_ABCType = {
		Rows : [ {
			"id" : "A",
			"text" : "A"
		}, {
			"id" : "B",
			"text" : "B"
		}, {
			"id" : "C",
			"text" : "C"
		}
		],
		Total : 3	
	}
//材料字典--计划来源
var medInv_sourcePlan = {
		Rows : [ {
			"id" : "1",
			"text" : "科室申领"
		}, {
			"id" : "2",
			"text" : "仓库报备"
		}
		],
		Total : 2	
	}

//安全库存设置 周期单位
var medStoreInv_state = {
		Rows : [ {
			"id" : "0",
			"text" : "无"
		}, {
			"id" : "1",
			"text" : "年"
		},{
			"id" : "2",
			"text" : "季"
		},{
			"id" : "3",
			"text" : "月"
		},{
			"id" : "4",
			"text" : "天"
		}],
		Total : 5	
	}
//专购品来源
var medSpecailMain_comeFrom = {
	Rows : [ {
		"id" : "1",
		"text" : "手工录入"
	}, {
		"id" : "2",
		"text" : "代销生成"
	}],
	Total : 2	
}
//材料证件类型--效期类型
var medInvCertType_validityType = {
	Rows : [ {
		"id" : "1",
		"text" : "永久有效"
	}, {
		"id" : "2",
		"text" : "限定有效"
	}],
	Total : 2	
}
//耐用品期初(库房/科室)--状态
var medDuraReg_state = {
	Rows : [ {
		"id" : "1",
		"text" : "未审核"
	}, {
		"id" : "2",
		"text" : "已审核"
	}],
	Total : 2	
}
//耐用品流转--状态
var medDuraTran_state = {
	Rows : [ {
		"id" : "1",
		"text" : "新建"
	}, {
		"id" : "2",
		"text" : "移出确认"
	}, {
		"id" : "3", 
		"text" : "移入确认"
	}],
	Total : 2	
}
//耐用品(库房/科室)报废--状态
var medDuraScrap_state = {
	Rows: [
		{
			"id": "1",
			"text": "1 未确认"
		},
		{
			"id": "3",
			"text": "3 已确认"
		}
	],
	Total: 2
}
//耐用品(库房/科室)盘点--状态
var medDuraCheck_state = {
	Rows: [
		{
			"id": "1",
			"text": "1 未确认"
		},
		{
			"id": "3",
			"text": "3 已确认"
		}
	],
	Total: 2
}
//耐用品(库房/科室)盘点--状态
var bus_type_code_state = {
	Rows: [
		{
			"id": "47",
			"text": "专购品入库"
		},
		{
			"id": "48",
			"text": "专购品退货"
		},
		{
			"id": "49",
			"text": "专购品出库"
		},
		{
			"id": "50",
			"text": "专购品退库"
		}
	],
	Total: 4
}
//药品字典审核状态
var medInv_state = {
	Rows: [
		{
			"id": "0",
			"text": "未审核"
		},
		{
			"id": "1",
			"text": "已审核"
		}
	],
	Total: 2
}
//科室需求计划  状态   注:根据系统参数04031,若值为1,使用审核操作
var medRequireMain_state={
	Rows : [ {
		"id" : "0",
		"text" : "已中止"
	}, {
		"id" : "1",
		"text" : "未提交"
	},{
		"id" : "2",
		"text" : "已提交"
	}, {
		"id" : "3",
		"text" : "已审核"
	}],
	Total : 4
}

//科室需求计划  状态   注:根据系统参数04031,若值为0,不使用审核操作
var medRequireMain_state2={
	Rows : [ {
		"id" : "0",
		"text" : "已中止"
	}, {
		"id" : "1",
		"text" : "未提交"
	},{
		"id" : "2",
		"text" : "已提交"
	}],
	Total : 3
}


//材料字典审核状态
var medPurMain_state0 = {
	Rows: [
		{
			"id": "0",
			"text": "已中止"
		},
		{
			"id": "2",
			"text": "未执行"
		},
		{
			"id": "3",
			"text": "执行完毕"
		},
		{
			"id": "4",
			"text": "部分执行"
		}
	],
	Total: 4
}

var medPurMain_state1 = {
		Rows: [
			{
				"id": "0",
				"text": "已中止"
			},
			{
				"id": "1",
				"text": "未审核"
			},
			{
				"id": "2",
				"text": "已审核"
			},
			{
				"id": "3",
				"text": "执行完毕"
			},
			{
				"id": "4",
				"text": "部分执行"
			}
		],
		Total: 5
	}



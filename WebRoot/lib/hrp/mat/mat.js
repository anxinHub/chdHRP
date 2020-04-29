
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
var matInMain_state = {
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
var matOutMain_state = {
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
var matPurMain_purType = {
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
var matPurMain_purType = {
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
var matRequireMain_isSubmit = {
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
var matRequireMain_isReturn = {
		Rows : [ {
			"id" : "0",
			"text" : "未回退"
		}, {
			"id" : "1",
			"text" : "回退"
		} ],
		Total : 2
	};
//科室需求计划  状态   注:根据系统参数04031,若值为1,使用审核操作
var matRequireMain_state={
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
var matRequireMain_state2={
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

//2017-03-17 matOrderMain_state与matOrderMain_state2中id和text都应对应上
//订单 状态
var matOrderMain_state={
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
			"text" : "部分执行"
		}, {
			"id" : "4",
			"text" : "执行完毕"
		}, {
			"id" : "8",
			"text" : "已合并"
		}, {
			"id" : "9",
			"text" : "已撤销"
		} ],
		Total : 6
	}

//订单 状态 2
var matOrderMain_state2={
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
			"text" : "执行完毕"
		}, {
			"id" : "8",
			"text" : "已合并"
		}, {
			"id" : "9",
			"text" : "已撤销"
		} ],
		Total : 5
	}

//订单--采购方式
var matOrderMain_purType={
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
var matOrderMain_orderType={
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
var matAdjust_state = {
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
var matApplyMain_checkState = {
		Rows : [ {
			"id" : "1",
			"text" : "未审核"
		}, {
			"id" : "2",
			"text" : "已审核"
		}, {
			"id" : "3",
			"text" : "已发送"
		}, {
			"id" : "0",
			"text" : "已作废"
		} ],
		Total : 5	
	}

//科室申请单--发送状态
var matApplyMain_appState = {
		Rows : [ {
			"id" : "0",
			"text" : "未发送"
		}, {
			"id" : "1",
			"text" : "已发送"
		} ],
		Total : 2	
	}

//科室申请单--处理状态
var matApplyMain_storeCheckState = {
		Rows : [ {
			"id" : "0",
			"text" : "待处理+部分完成"
		}, {
			"id" : "1",
			"text" : "待处理"
		}, {
			"id" : "2",
			"text" : "部分完成"
		}, {
			"id" : "3",
			"text" : "全部完成"
		} , {
			"id" : "4",
			"text" : "作废"
		} ],
		Total : 5	
	}

//科室申请单--明细处理状态
var matApplyDetail_doState = {
		Rows : [ {
			"id" : "1",
			"text" : "未完成"
		}, {
			"id" : "2",
			"text" : "已完成"
		} ],
		Total : 3	
	}

//科室申请单--完成状态
var matCheckMain_state = {
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
var matTranMain_busType = {
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
var matTranMain_tranType = {
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
var matTranMain_tranMethod = {
		Rows : [ {
			"id" : "1",
			"text" : "同价调拨"
		}, {
			"id" : "2",
			"text" : "异价调拨"
		}],
		Total : 2	
	}

//调拨单--调拨类型
var matTranMain_state = {
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
var matShowSet_directionFlag = {
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
var matInv_ABCType = {
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
var matInv_sourcePlan = {
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
var matStoreInv_state = {
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
	var matSpecailMain_comeFrom = {
		Rows : [ {
			"id" : "1",
			"text" : "手工录入"
		}, {
			"id" : "2",
			"text" : "代销生成"
		}],
		Total : 2	
	}
//入库出库
var matIN_Out = {
		Rows : [ {
			"id" : "1",
			"text" : "入库"
		}, {
			"id" : "2",
			"text" : "出库"
		}],
		Total : 2	
	}
//材料证件类型--效期类型
var matInvCertType_validityType = {
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
var matDuraReg_state = {
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
var matDuraTran_state = {
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

// 耐用品(库房/科室)报废--状态
var matDuraScrap_state = {
	Rows: [{
				"id": "1",
				"text": "新建"
			},{
				"id": "2",
				"text": "审核"
			},{
				"id": "3",
				"text": "审批"
			},{
				"id": "4",
				"text": "已确认"
			}],
	Total: 4
}

// 耐用品(库房/科室)盘点--状态
var matDuraCheck_state = {
	Rows: [
		{
			"id": "1",
			"text": "新建"
		},{
			"id": "2",
			"text": "审核"
		},{
			"id": "3",
			"text": "已确认"
		}
	],
	Total: 3
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

//材料字典审核状态
var matInv_state = {
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

//材料字典审核状态
var matPurMain_state0 = {
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

var matPurMain_state1 = {
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

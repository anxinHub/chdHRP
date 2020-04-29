<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
<jsp:param value="select,datepicker,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid;
	var year_input,check_type_select,state_select;
	//打印 单元格格式化 用
	var renderFunc = {
		check_type: function (value) { //审批类型
			if (value == '01') {
				return "初始审批";
			}
			if (value == '02') {
				return "调整审批";
			}
		},
		bc_state: function (value) { //状态
			if (value == '01') {
				return "新建";
			}
			if (value == '02') {
				return "已发送";
			}
			if (value == '03') {
				return "已审核";
			}
			if (value == '04') {
				return "已下达";
			}
		}
	};
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	function query(){
		var parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'check_type', value: check_type_select.getValue() },
			{ name: 'bc_state', value: state_select.getValue() }
		]
		//加载查询条件
		grid.loadData(parms,"queryBudgIncomeCheck.do?isCheck=false");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
	    	columns: [ 
            	{ display: '审批单号', name: 'check_code', align: 'left',width:120,
		        	render:function(ui) {
						return '<a href=javascript:openUpdate("'+ ui.rowData.budg_year + "|" + ui.rowData.check_code + '")>'+ui.rowData.check_code+'</a>';
					}
				},
				{ display: '预算年度', name: 'budg_year', align: 'left',width:80,
				    },
                { display: '审批类型', name: 'check_type', align: 'left',width:100,
                	render:function(ui){
						if(ui.rowData.check_type == '01'){
							return "初始审批";
						}
						if(ui.rowData.check_type == '02'){
							return "调整审批";
						}
					}		
         		},
                { display: '备注', name: 'remark', align: 'left',width:200},
				{ display: '状态', name: 'bc_state', align: 'left',width:80,
					render:function(ui){
						if(ui.rowData.bc_state =='01'){
							return "新建";
						}
						if(ui.rowData.bc_state == '02'){
							return "已发送";
						}
						if(ui.rowData.bc_state == '03'){
							return "已审核";
						}
						if(ui.rowData.bc_state == '04'){
							return "已下达";
						}
					}		
			   },
               { display: '制单人', name: 'maker_name', align: 'left',width:80 },
               { display: '制单日期', name: 'make_data', align: 'left',width:100 },
               { display: '审核人', name: 'check_name', align: 'left',width:80 },
               { display: '审核日期', name: 'check_data', align: 'left',width:100 },
               { display: '预算下达日期', name: 'issue_data', align: 'left',width:100 }                 
           ],
           dataModel:{
          	 method:'POST',
          	 location:'remote',
          	 url:'',
          	 recIndx: 'check_code' //必填 且该列不能为空  
           },
           usePager:true,width: '100%', height: '100%',checkbox: true,
		   toolbar: {
                items: [
                { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
                { type: "button", label: '添加',icon:'plus',listeners: [{ click: add_open}] },
				{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
				{ type: "button", label: '发送',icon:'circle-arrow-e',listeners: [{ click: send}] },
				{ type: "button", label: '召回',icon:'circle-arrow-w',listeners: [{ click: recall}] },
				{ type: "button", label: '审核',icon:'check',listeners: [{ click: audit}] },
				{ type: "button", label: '销审',icon:'closethick',listeners: [{ click: unAudit}] },
				{ type: "button", label: '预算下达',icon:'arrowreturnthick-1-e',listeners: [{ click: etissued}] },
				{ type: "button", label: '取消下达',icon:'arrowreturnthick-1-w',listeners: [{ click: cancelEtissued}] },
				//{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
            ]}
        });
	}

	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "医疗收入预算审批";
	}

	function add_open() {
		var budg_year = year_input.getValue() ;
		
    	if(!budg_year){
    		$.etDialog.error('预算年度不能为空');
    		return false;
    	}
    	ajaxPostData({
            url: "../../common/budgeditstate/queryMedIncomeFlag.do?isCheck=false&budg_year="+budg_year,
            success: function(responseData) {
                if (responseData.flag == "1") {
                    parent.$.etDialog.open({
                        url: 'hrp/budg/budgincome/check/budgIncomeCheckAddPage.do?isCheck=false',
                        title: '医疗收入预算审批申请添加',
                        isMax: true,
                        frameName: window.name //用于parent弹出层调用本页面的方法或变量
                    });
                } else {
                    $.etDialog.error('请不要重复审批!');
                }
            }
        })
	}
	//删除;
	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else if (data.length == 1){
            var check_code = "";
			var budg_year = year_input.getValue() ;
            $(data).each(function (){	
            	check_code += "'" + this.rowData.check_code + "'";	
            	formPara={
                        
       	        	budg_year:this.rowData.budg_year ,
       	            
       	        	check_code:this.rowData.check_code ,
       	            
       	         	bc_state: this.rowData.bc_state
       	            
       	         };
											
            });
            
            //校验 选中数据状态
            ajaxPostData({
                url: "queryBudgMedIncomeCheckState.do?isCheck=false",
                data: {
                    budg_year: budg_year,
                    check_code: check_code,
                    bc_state: '01'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("删除失败！" + responseData.check_code + "单据不是新建状态不允许删除！");
                    } else {
                        $.etDialog.confirm('确定删除?', function() {
                            ajaxPostData({
                                url: "deleteBudgIncomeCheck.do?isCheck=false",
                                data: formPara,
                                success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
		}else{
        	$.etDialog.error('每次只能删除一条且是当前最大审批单号的单据!'); 
        }
	}
	//导入;
	function imp() {
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgMedIncomeCheckImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	//下载临时模板
	function downTemplate() {
		location.href = "downTemplate.do?isCheck=false";
	}

	//修改页面跳转
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "&budg_year=" + vo[0] + "&check_code=" + vo[1];
		
		parent.$.etDialog.open({
            url: 'hrp/budg/budgincome/check/budgIncomeCheckUpdatePage.do?isCheck=false&' + parm,
            title: '医疗收入预算审核申请修改',
            isMax: true,
            frameName: window.name
        });
	}

	//发送的功能
	function send() {
		var data = grid.selectGetChecked();
    	if(data.length==0){		
    		$.etDialog.error('请选择行');
			return;
    	}else{
    		var ParamVo =[];
			var check_code = "";
			var budg_year = year_input.getValue() ;
			$(data).each(function (){
				check_code += "'" + this.rowData.check_code + "',";	
	    		ParamVo.push(
					this.rowData.budg_year   +"@"+ 
					this.rowData.check_code   +"@"+ 
					'02'
				)  
    		});
			//校验 选中数据状态
            ajaxPostData({
                url: "queryBudgMedIncomeCheckState.do?isCheck=false",
                data: {
                    budg_year: budg_year,
                    check_code: check_code.substring(0, check_code.length - 1),
                    bc_state: '01'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("发送失败！" + responseData.check_code + "单据不是新建状态不允许发送！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定要发送吗?', function() {
                            //isCheck=true;可以根据不同用户来让他进行不同的操作,一般不可建议使用
                            ajaxPostData({
                                url: "sendOrRecall.do?isCheck=false",
                                data: { ParamVo: ParamVo.toString() },
                                success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
		}
	}
	//召回的功能
	function recall() {
		var data = grid.selectGetChecked();
    	if(data.length==0){		
    		$.etDialog.error('请选择行');
			return;
    	}else{
    		var ParamVo =[];
			var check_code = "";
			var budg_year = year_input.getValue() ;
			$(data).each(function (){
				check_code += "'" + this.rowData.check_code + "',";	
	    		ParamVo.push(
					this.rowData.budg_year   +"@"+ 
					this.rowData.check_code   +"@"+ 
					'01'
				)  
    		});
			 //校验 选中数据状态
            ajaxPostData({
                url: "queryBudgMedIncomeCheckState.do?isCheck=false",
                data: {
                    budg_year: budg_year,
                    check_code: check_code.substring(0, check_code.length - 1),
                    bc_state: '02'
                }, 
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("召回失败！" + responseData.check_code + "单据不是已发送状态不允许召回！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定召回吗?', function() {
                            ajaxPostData({
                                url: "sendOrRecall.do?isCheck=false",
                                data: { ParamVo: ParamVo.toString() },
                                success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
		}
	}
	//审核的功能
	function audit() {
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}else{
		
			var ParamVo =[];
			var check_code = "";
			var budg_year = year_input.getValue() ;
			$(data).each(function (){
				check_code += "'" + this.rowData.check_code + "',";	
				ParamVo.push(
					this.rowData.budg_year   +"@"+ 
					this.rowData.check_code   +"@"+ 
				    '03' 
				) 
			});
			//校验 选中数据状态
            ajaxPostData({
                url: "queryBudgMedIncomeCheckState.do?isCheck=false",
                data: {
                    budg_year: budg_year,
                    check_code: check_code.substring(0, check_code.length - 1),
                    bc_state: '02'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("审核失败！" + responseData.check_code + "单据不是已发送状态不允许审核！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定审核?', function(yes) {
                            ajaxPostData({
                                url: "auditOrUnAudit.do?isCheck=false",
                                data: { ParamVo: ParamVo.toString() },
                                success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
		}
	}
	//消审
	function unAudit() {
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}else{
			var ParamVo =[];
			var check_code = "";
			var budg_year = year_input.getValue() ;
			$(data).each(function (){
				check_code += "'" + this.rowData.check_code + "',";	
				 ParamVo.push(
					this.rowData.budg_year   +"@"+ 
					this.rowData.check_code   +"@"+ 
				    '02' 
				) 
				
			});
			 //校验 选中数据状态
            ajaxPostData({
                url: "queryBudgMedIncomeCheckState.do?isCheck=false",
                data: {
                    budg_year: budg_year,
                    check_code: check_code.substring(0, check_code.length - 1),
                    bc_state: '03'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("销审失败！" + responseData.check_code + "单据不是已审核状态不允许销审！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定要销审?', function(yes) {
                            ajaxPostData({
                                url: "auditOrUnAudit.do?isCheck=false",
                                data: { ParamVo: ParamVo.toString() },
                                success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
		}
	}
	//预算下达的功能
	function etissued() {
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}else{
			var ParamVo =[];
			var check_code = "";
			var budg_year = year_input.getValue() ;
			$(data).each(function (){
				check_code += "'" + this.rowData.check_code + "',";	
				 ParamVo.push(
					this.rowData.budg_year   +"@"+ 
					this.rowData.check_code   +"@"+ 
				    '04' 
				) 
			});
			 //校验 选中数据状态
            ajaxPostData({
                url: "queryBudgMedIncomeCheckState.do?isCheck=false",
                data: {
                    budg_year: budg_year,
                    check_code: check_code.substring(0, check_code.length - 1),
                    bc_state: '03'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("预算下达失败！" + responseData.check_code + "单据不是已审核状态不允许预算下达！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定要预算下达吗?', function() {
                            ajaxPostData({
                                url: "cancelIssueOrIssue.do?isCheck=false",
                                data: { ParamVo: ParamVo.toString() },
                                success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
		}
	}

	//取消预算下达的功能
	function cancelEtissued() {
		var data = grid.selectGetChecked();
		if (data.length == 0){
			$.etDialog.error('请选择行');
			return;
		}else{
			var ParamVo =[];
			var check_code = "";
			var budg_year = year_input.getValue() ;
			$(data).each(function (){
				check_code += "'" + this.rowData.check_code + "',";	
				ParamVo.push(
					this.rowData.budg_year   +"@"+ 
					this.rowData.check_code   +"@"+ 
				    '03' 
				) 
			});
			 //校验 选中数据状态
            ajaxPostData({
                url: "queryBudgMedIncomeCheckState.do?isCheck=false",
                data: {
                    budg_year: budg_year,
                    check_code: check_code.substring(0, check_code.length - 1),
                    bc_state: '04'
                },
                success: function(responseData) {
                    if (responseData.state == "false") {
                        $.etDialog.error("取消预算下达失败！" + responseData.check_code + "单据不是已下达状态不允许取消预算下达！");
                        return false;
                    } else {
                        $.etDialog.confirm('确定取消预算下达吗?', function() {
                            ajaxPostData({
                                url: "cancelIssueOrIssue.do?isCheck=false",
                                data: { ParamVo: ParamVo.toString() },
                                success: function(responseData) {
                                    query();
                                }
                            });
                        });
                    }
                }
            })
		}
	}
	
	function init(){
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: true
        });

		check_type_select = $("#check_type_select").etSelect({
			url:'../../queryBudgCheckType.do?isCheck=false',
			defaultValue: "none",
			onChange: query
		});

		state_select = $("#state_select").etSelect({
			url:'../../queryBudgBcState.do?isCheck=false',
			defaultValue: "none",
			onChange: query
		});
	}

	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	};
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
	}
</script>
	
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>

	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">审核类型：</td>
				<td class="ipt">
					<select name="" id="check_type_select" style="width:180px;"></select>
				</td>
				<td class="label">状态：</td>
				<td class="ipt">
					<select name=" " id="state_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>

</html>
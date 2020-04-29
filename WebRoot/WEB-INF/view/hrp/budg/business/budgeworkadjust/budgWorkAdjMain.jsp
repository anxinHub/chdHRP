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
	<jsp:param value="select,datepicker,dialog,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var budg_year;
  //打印 单元格格式化 用
    var renderFunc = {
		state : function(value){ //状态
			if(value =='01'){
				return "新建";
			}
			if(value == '02'){
				return "已审核";
			}
		}
	};
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();		
    });
    var year_input,state_select;

	function init(){
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	initColumns();
                }, 10);
            },
            defaultDate: true
        });

		state_select = $("#state_select").etSelect({
			url:'../../queryBudgState.do?isCheck=false',
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
	
	function query(){
		var search = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'state', value: state_select.getValue() }
		]
		//加载查询条件
		grid.loadData(search,'queryBudgWorkAdj.do?isCheck=false');
	}
	
	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{ display: '调整单号', name: 'adj_code', width: 140,
	                render: function(ui) {
	                    var rowData = ui.rowData;
	                    var open_param = [
	                        rowData.budg_year,
	                        rowData.adj_code
	                    ];
	                    open_param = open_param.join('|');
	                    return "<a href=javascript:openUpdate('" + open_param+ "')>" + rowData.adj_code + "</a>";
	                }
	            },
	            { display: '预算年度', name: 'budg_year', width: 100 },
	            { display: '调整文号', name: 'adj_file', width: 140,
	                render: function(ui) {
	                    var adj_file = ui.rowData.adj_file;
	                    if (adj_file) {
	                        return "<a href=javascript:down('" + adj_file + "')>" + adj_file + "</a>";
	                    } else {
	                        return "";
	                    }
	                }
	            },
	            { display: '调整说明', name: 'adj_remark', width: 160 },
	            { display: '状态', name: 'state', width: 140,
	                render: function(ui) {
	                    var state = ui.rowData.state;
	                    if (state == '01') {
	                        return "新建";
	                    } else if (state == '02') {
	                        return "已审核";
	                    }
	                }
	            },
	            { display: '调整前预算查看', name: 'last_check_code', width: 140,
	                render: function(ui) {
	                    var rowData = ui.rowData;
	                    var open_param = [
	                        rowData.budg_year,
	                        rowData.last_check_code,
	                        rowData.bc_state
	                    ];
	                    open_param = open_param.join('|');

	                    return "<a href=javascript:lookBeforAdjustInfo('" + open_param + "')>" + rowData.last_check_code + "</a>";
	                }
	            },
	            { display: '制单人', name: 'make_name', width: 140 },
	            { display: '制单日期', name: 'make_data', width: 140 },
	            { display: '审核人', name: 'check_name', width: 140 },
	            { display: '审核日期', name: 'check_date', width: 140 },
			],
			width: '100%',
			height: '100%',
            checkbox: true,
            toolbar: {
                items: [
                    { type: 'button', label: '查询（<u>E</u>）', listeners: [{ click: query }], icon: 'search' },
                    { type: 'button', label: '添加（<u>A</u>）', listeners: [{ click: add_open }], icon: 'add' },
                    { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
                    { type: 'button', label: '审核（<u>A</u>）', listeners: [{ click: audit }], icon: 'audit' },
                    { type: 'button', label: '销审（<u>U</u>）', listeners: [{ click: unAudit }], icon: 'unaudit' },
                ]
            },
			rowDblClick: function (event, ui) {
                var rowData = ui.rowData;
                var paramArray = [
                    rowData.budg_year,
                    rowData.adj_code
                ];
                paramArray = paramArray.join('|');
                openUpdate(paramArray);
            },
		});
	}
    
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "医疗收入预算调整";
	}
	
	function add_open() {
		budg_year = year_input.getValue();
		if(!budg_year){
			$.etDialog.error('预算年度不能为空!');
			return;
		}
		
		ajaxPostData({
            url: "queryCheckDataExists.do?isCheck=false&budg_year=" + budg_year,
            success: function(responseData) {
            	ajaxPostData({
                    url: "queryBcState.do?isCheck=false&budg_year=" + budg_year,
                    success: function(responseData) {
                        $.etDialog.open({
                            url: 'budgWorkAdjAddPage.do?isCheck=false&',
                            title: '业务预算调整申请',
                            isMax: true
                        });
                    }
                });
            }
        });
    }
	
	function remove() {
        var data = grid.selectGet();

        if (data.length == 0) {
            $.etDialog.error('请选择行');
            return;
        }
        if (data.length > 1) {
            $.etDialog.error('每次只能删除一条且单号必需为最大单号的数据!');
        } else {
            var ParamVo = [];
            $(data).each(function() {
                ParamVo.push(
                    this.rowData.group_id + "@" +
                    this.rowData.hos_id + "@" +
                    this.rowData.copy_code + "@" +
                    this.rowData.budg_year + "@" +
                    this.rowData.adj_code + "@" +
                    (this.rowData.adj_file ? this.rowData.adj_file : "-1")
                );
            });
            $.etDialog.confirm('确定删除?', function(yes) {
                ajaxPostData({
                    url: "deleteBudgWorkAdj.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        query();
                    }
                });
            });
        }
    }
	
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"budg_year="+vo[0]   +"&"+ 
			"adj_code="+vo[1] 
		$.etDialog.open({
            url: 'budgWorkAdjUpdatePage.do?isCheck=false&' + parm,
            title: '业务预算调整申请修改',
            isMax: true
        });
    }
    
  //审核的功能
	function audit() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
			return false;
		} else {
			var ParamVo = [];
			var order_nos = "";
			var notice_nos = "";
			$(data).each(function () {
				if (this.rowData.state != '01') {
					order_nos = order_nos + this.rowData.adj_code + ",";
				}
				ParamVo.push(
					this.rowData.budg_year + "@" +
					this.rowData.adj_code + "@" +
					'02'
				)
			});
			if (notice_nos != "") {
				$.etDialog.error("审核失败！" + order_nos + "单据不是新建状态不允许审核！");
				return false;
			}
			$.etDialog.confirm('确定要审核?', function() {
                ajaxPostData({
                    url: "auditBudgWorkAdj.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function() {
                        query();
                    }
                });
            });
		}
	}
	//消除审核的功能
	function unAudit() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
			return false;
		} else {
			var ParamVo = [];
			var order_nos = "";
			$(data).each(function () {
				if (this.rowData.state != '02') {
					order_nos = order_nos + this.rowData.adj_code + ",";
				}
				ParamVo.push(
					this.rowData.budg_year + "@" +
					this.rowData.adj_code + "@" +
					this.rowData.last_check_code   +"@"+ 
					'01'
				);
			});
			if (order_nos != "") {
				$.etDialog.error("销审失败！" + notice_nos + "单据不是审核状态不允许销审！");
				return false;
			}
			$.etDialog.confirm('确定要销审?', function () {
				ajaxPostData({
					url: "unAuditBudgWorkAdj.do?isCheck=false",
					data: { ParamVo: ParamVo.toString()},
					success: function() {
						query();
					}
				});
			});
		}
	}
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
	}
	 
	//调整前预算查看
	function lookBeforAdjustInfo(obj){	    	
    	var vo = obj.split("|");
		var parm = "year="+ vo[0] +"&check_code="+vo[1]+"&bc_state="+vo[2];
		parent.$.etDialog.open({
			url: 'hrp/budg/business/budgeworkadjust/openAdjustStateLinkJumpPage.do?isCheck=false&' + parm, 
			title: '调整前预算查看', 
			isMax: true,
			frameName: window.name
		});
    }
	  
	function down(adj_file) {
		location.href = "downWorkAdjFile.do?isCheck=false&adj_file="+adj_file;
 	}
  </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label"><span style="color: red">*</span>预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">状态： </td>
				<td class="ipt">
					<select name="" id="state_select" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="ipt">
					
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>

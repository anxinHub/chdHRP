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
    var gridManager = null;
    var userUpdateStr;
    $(function (){
    	//加载数据
    	loadHead(null);
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
                	query();
                }, 10);
            },
            defaultDate: true
        });


		state_select = $("#state_select").etSelect({
			url:"../../qureyCashPlanStateSelect.do?isCheck=false",
			defaultValue:"none",
			onChange:query
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
    }

	//查询
    function  query(){
    	var parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'event', value: $("#event_input").val() },
			{ name: 'state', value: state_select.getValue() }
		];
    	
    	//加载查询条件
		grid.loadData(parms, '');
     }
    
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
                { display: '计划单号', name: 'plan_code', align: 'left',width: 120,
               	 	 render: function (ui) {
                     	 return "<a href=javascript:openUpdate('"
                   			 +ui.rowData.budg_year+"|"
                   			 +ui.rowData.plan_code+"')>"
                   			 +ui.rowData.plan_code+"</a>";
                 	 }
 				},
                { display: '预算年度', name: 'budg_year', align: 'left',width: 80
 				},
                { display: '资金流动事项', name: 'event', align: 'left',width: 200
 				},
                { display: '说明', name: 'remark', align: 'left',width: 200
 				},
                { display: '制单人', name: 'mak_emp_name', align: 'left',width: 80
 				},
                { display: '制单日期', name: 'make_date', align: 'left',width: 100
 				},
                { display: '审核人', name: 'che_emp_name', align: 'left',width: 80
 				},
                { display: '审核日期', name: 'check_date', align: 'left',width: 100
 				},
                { display: '状态', name: 'state_name', align: 'left',width: 80
 				}
            ],
            dataModel:{
	           	method:'POST',
	           	location:'remote',
	           	url:'queryBudgCashPlan.do?isCheck=false',
	           	recIndx: 'budg_year' //必填 且该列不能为空  
            },
            usePager:false,width: '100%', height: '100%',checkbox: true,
            toolbar: {
                items: [
	                { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
	                { type: "button", label: '添加',icon:'plus',listeners: [{ click: add_open}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
					{ type: "button", label: '审核',icon:'check',listeners: [{ click: review}] },
					{ type: "button", label: '销审',icon:'closethick',listeners: [{ click: cancel}] },
	            ]
            },
        });
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	parent.$.etDialog.open({
		    url: 'hrp/budg/budgcash/budgcashplan/budgCashPlanAddPage.do?isCheck=false',
		    isMax: true,
		    title: '资金计划明细添加',
		    frameName: window.name
		}) 
    }
    	
    function remove(){
    	var str = "";
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.warn('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){
            	if(this.state == '02'){
            		str += this.plan_code+" "
            	}
				ParamVo.push(
					this.rowData.group_id   +"@"+ 
					this.rowData.hos_id   +"@"+ 
					this.rowData.copy_code   +"@"+ 
					this.rowData.budg_year   +"@"+ 
					this.rowData.plan_code 
				) 
			});
            if(str.length){
            	$.etDialog.warn('单号为'+ str + "数据已审核,不可删除!");
            	return false;
            }
            $.etDialog.confirm('确定删除?', function() {
                ajaxPostData({
                    url: "deleteBudgCashPlan.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function() {
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
			"plan_code="+vo[1] 
		parent.$.etDialog.open({
		    url: 'hrp/budg/budgcash/budgcashplan/budgCashPlanUpdatePage.do?isCheck=false&'+parm,
		    isMax: true,
		    title: '资金计划明细调整查看',
		    frameName: window.name
		}) 
    }
    
    //审核
    function review(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.warn('请选择行');
        	return false;
        }
        var ParamVo =[];
        $(data).each(function (){					
			ParamVo.push( 
				this.rowData.budg_year   +"@"+ 
				this.rowData.plan_code 
			) 
		});
        $.etDialog.confirm('确定审核?', function() {
            ajaxPostData({
                url: "reviewbudgCashPlan.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function() {
                    query();
                }
            });
        });
    }
    
    //销审
    function cancel(){
    	var data = grid.selectGet();
        if (data.length === 0){
        	$.etDialog.warn('请选择行');
        	return false;
        }
        var ParamVo =[];
        $(data).each(function (){					
			ParamVo.push(
				this.rowData.budg_year   +"@"+ 
				this.rowData.plan_code 
			) 
		});
        $.etDialog.confirm('确定销审?', function(yes) {
            ajaxPostData({
                url: "cancelbudgCashPlan.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                    query();
                }
            });
        });
    }
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">流动事项：</td>
				<td class="ipt">
					<input type="text" id="event_input" />
				</td>
				<td class="label">状态：</td>
				<td class="ipt">
					<select id="state_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>

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
    var budg_year;
    $(function (){
    	//加载数据
    	loadHead(null);
    	init();
    });
    
    var year_input,month_input;
    
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


		month_input = $("#month_input").etDatepicker({
			view:'months',
			minView:'months',
			dateFormat:"mm",
			todayButton:false,
			showNav:false,
			onSelect:query
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
    
    //查询
    function  query(){
    	var parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'month', value: month_input.getValue() }
		];
    	//加载查询条件
		grid.loadData(parms, '');
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
             { display: '预算年度', name: 'budg_year', align: 'left',width:'7%'
				},
             { display: '月份', name: 'month', align: 'left',width:'5%'
				},
             { display: '期初现金存量', name: 'cash_begain', align: 'right',width:'18%',
					render:function(ui){
						var value = ui.cellData;
						if(value || value == 0){
							return formatNumber(value,2,1);
						}
					}
				},
             { display: '现金流入', name: 'cash_in', align: 'right',width:'17%',
					render:function(ui){
						var value = ui.cellData;
						if(value || value == 0){
							return formatNumber(value,2,1);
						}
					}
				},
             { display: '现金流出', name: 'cash_out', align: 'right',width:'17%',
					render:function(ui){
						var value = ui.cellData;
						if(value || value == 0){
							return formatNumber(value,2,1);
						}
					}
				},
             { display: '现金净增加额', name: 'cash_add', align: 'right',width:'17%',
					render:function(ui){
						var value = ui.cellData;
						if(value || value == 0){
							return formatNumber(value,2,1);
						}
					}
				},
             { display: '期末现金存量', name: 'cash_end', align: 'right',minWidth:180,
					render:function(ui){
						var value = ui.cellData;
						if(value || value == 0){
							return formatNumber(value,2,1);
						}
					}
				}
             ],
             dataModel:{
              	 method:'POST',
              	 location:'remote',
              	 url:'queryBudgCash.do?isCheck=false',
              	 recIndx: 'budg_year' //必填 且该列不能为空  
             },
             usePager:false,width: '100%', height: '100%',checkbox: true,
             toolbar: {
               	items: [
	                { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
					{ type: "button", label: '计算',icon:'contact',listeners: [{ click: count}] },
					{ type: "button", label: '删除',icon:'minusthick',listeners: [{ click: remove}] },
             	]
             },
        });
    }
   
    //对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量预算表  budg_cash
    function count(){
    	var param = {
    		budg_year : year_input.getValue()
    	}
    	ajaxPostData({
            url: "collectBudgCash.do?isCheck=false",
            data: param,
            success: function(responseData) {
                query();
            }
        });
    }
    
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.warn('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
					this.rowData.budg_year   +"@"+ 
					this.rowData.month
				) 
			});
            $.etDialog.confirm('确定删除?', function(yes) {
                ajaxPostData({
                    url: "deleteBudgCash.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                        query();
                    }
                });
            });
        }
    }
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
     <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">月份： </td>
				<td class="ipt">
					<input type="text" id="month_input" />
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>

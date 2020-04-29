<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'change_reason', value: $("#change_reason").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({  url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: "none"});
          }
        
        var initGrid = function () {
            var columns = [
                 { display: '变更编号', name: 'change_code', width: '14%',
                	render : function(data){
                		return '<a class="toDetail" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code+'</a>'
                	}	 
                 },
                 { display: '变更日期', name: 'change_date',align: 'center',  width: '8%'},
            	 { display: '合同编号', name: 'pact_code', width: '13%',
                	render : function (data){
                		return '<a class="toPact" rowIndex = "'+data.rowIndx+'">'+data.rowData.pact_code+'</a>'
                	}	 
            	 },
                 { display: '合同名称', name: 'pact_name', width: '15%'},
                 { display: '客户', name: 'cus_name',  width: '15%'},
                 { display: '变更原因', name: 'change_reason', width: '18%'},
                 { display: '变更金额', name: 'money_c', width: '10%'},
                 { display: '操作员', name: 'user_name',  width: '10%'},
                 { display: '变更前合同查看', name: '', align: 'center', width: '10%',
                	render :function(data){
                		return '<a class="toPre" rowIndex = "'+data.rowIndx+'">查看</a>'
                	}	 
                 },
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactMainChangeMoneySKHT.do?isCheck=false'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '打印',  listeners: [{ click: print }],  icon: 'print' },
                        { type: 'button', label: '生成原值变动单', /* listeners: [{ click: query }], */ icon: 'save' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
            $("#mainGrid").on('click','.toPre',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPre(currentRowData);
       		})
            $("#mainGrid").on('click','.toPact',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPact(currentRowData);
       		})
            $("#mainGrid").on('click','.toDetail',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toDetail(currentRowData);
       		})
        };
        
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("请先查询数据！");
       			return;
       		}
           	var printPara={
                   	title: "收款合同金额变更记录",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.basicset.common.PactChangeService",
   					method_name:"queryPactMainChangeMoneySKHTPrint",
   					bean_name:"pactChangeService",
   					pact_type_code : $("#pact_type_code").val(),
                 	sup_no : $("#sup_no").val(),
                 	pact_code : $("#pact_code").val(),
                 	pact_name : $("#pact_name").val(),
                 	start_date : startpicker.getValue(),
                 	end_date : endpicker.getValue(),
                 	change_reason : $("#change_reason").val()
               };
               officeGridPrint(printPara);
           };
           
        //跳转资金来源页面
        function toPre(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/skht/change/pactMainChangeSKHTPrePage.do?isCheck=false&change_code='+rowData.change_code +'&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toPact(rowData){
        	parent.$.etDialog.open({
                url: 'hrp/pac/skht/pactinfo/pactexec/pactExecSKHTEdit.do?isCheck=false&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toDetail(rowData){
        	parent.$.etDialog.open({
          	 	 url: 'hrp/pac/skht/change/pactMainChangeSKHTPDetailPage.do?isCheck=false&change_code='+rowData.change_code +'&pact_code='+rowData.pact_code,
                   width: $(window).width(),
                   height: $(window).height(),
                   title: '金额变动明细',
                   modal: true,
               });
        }
        
        //删除
        
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: 'yyyy-fm-fd',
    		  	onChange: function (date) {
    		  		var end = endpicker.getValue();
    		  		if(end < date){
    		  			endpicker.setValue(end);
    		  		}
    		  	}
    		});
    		endpicker = $("#end_date").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = startpicker.getValue();
    		  		if(start > date){
    		  			endpicker.setValue(start);
    		  		}
    		  	}
    		});
	}
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">变更日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">变更原因：</td>
            <td class="ipt"><input id="change_reason" type="text" /> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">卡片编号：</td>
            <td class="ipt"><input id="card" type="text" /> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>


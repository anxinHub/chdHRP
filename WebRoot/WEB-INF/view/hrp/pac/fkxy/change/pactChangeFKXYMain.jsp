<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate,checkbox" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var is_total_cont;
        var is_price_cont;
        var query = function () {
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'change_reason', value: $("#change_reason").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'is_total_cont', value:is_total_cont.checked ? 1 : 0},
                { name: 'is_price_cont', value:is_price_cont.checked ? 1 : 0},
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false',defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({  url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: "none"});
          }
        
        var initGrid = function () {
            var columns = [
                 { display: '变更编号', name: 'change_code', width: '12%',
                 	render :function(data){
                 		return '<a class="toAft" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code+'</a>'
                 	}
                 },
                 { display: '变更日期', name: 'change_date',align: 'center',  width: '8%'},
            	 { display: '协议编号', name: 'pact_code', width: '12%',
                	render : function (data){
                		return '<a class="toPact" rowIndex = "'+data.rowIndx+'">'+data.rowData.pact_code+'</a>'
                	}	 
            	 },
                 { display: '协议名称', name: 'pact_name', width: '10%'},
                 { display: '供应商', name: 'sup_name',  width: '10%'},
                 { display: '变更原因', name: 'change_reason', width: '10%'},
                 { display: '操作员', name: 'user_name',  width: '10%'},
                 { display: '变更前协议查看', name: '', align: 'center', width: '15%',
                	render :function(data){
                		var code = data.rowData.change_code.toString();
                		var change_code = code.match(/(\S*)-/)[1]+'-'+(parseInt(code.match(/-(\S*)/)[1])-1);
                		return '<a class="toBef" rowIndex = "'+data.rowIndx+'">'+change_code+'</a>'
                	}	 
                 },
                 { display: '变更后协议查看', name: '', align: 'center', width: '15%',
                 	render :function(data){
                 		return '<a class="toAft" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code+'</a>'
                 	}	 
                 }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactMainChangeFKXY.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
            $("#mainGrid").on('click','.toAft',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toAft(currentRowData);
       		})
       		$("#mainGrid").on('click','.toBef',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  var code = currentRowData.change_code;
                  var change_code = code.match(/(\S*)-/)[1]+'-'+(parseInt(code.match(/-(\S*)/)[1])-1);
                  var pact_code = currentRowData.pact_code;
                  toBef(change_code,pact_code);
       		})
            $("#mainGrid").on('click','.toPact',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPact(currentRowData);
       		})
        };
        
        function toAft(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkxy/change/pactMainChangeFKXYPrePage.do?isCheck=false&change_code='+rowData.change_code +'&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toBef(change_code,pact_code){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/fkxy/change/pactMainChangeFKXYPrePage.do?isCheck=false&change_code='+change_code +'&pact_code='+pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        }
        
        function toPact(rowData){
        	parent.$.etDialog.open({
                url: 'hrp/pac/fkxy/pactinfo/pactexec/pactExecFKXYEdit.do?isCheck=false&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
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
    		
    		is_total_cont = $('#is_total_cont').etCheck();
    		is_price_cont = $('#is_price_cont').etCheck();
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
            <td class="label" style="width: 100px;">协议类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">协议编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">协议名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">变更原因：</td>
            <td class="ipt"><input id="change_reason" type="text" /> </td>
            <td><input id="is_total_cont" type="checkbox" />总额控制</td>
			<td><input id="is_price_cont" type="checkbox" />单价控制</td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>


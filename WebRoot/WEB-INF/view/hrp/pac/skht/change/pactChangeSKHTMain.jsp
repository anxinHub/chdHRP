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
                { name: 'cus_no', value: $("#cus_no").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'change_reason', value: $("#change_reason").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                {name:'is_exe',value:'0'}
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeSKHTSelect.do?isCheck=false',defaultValue: "none"});
          	cus_no = cus_no = $("#cus_no").etSelect({ url: '../../basicset/select/queryHosCusDictSelect.do?isCheck=false',defaultValue: "none"});
          }
        
        var initGrid = function () {
            var columns = [
                 { display: '变更编号', name: 'change_code', width: '14%'},
                 { display: '变更日期', name: 'change_date',align: 'center',  width: '8%'},
            	 { display: '合同编号', name: 'pact_code', width: '13%',
                	render : function (data){
                		return '<a class="toPact" rowIndex = "'+data.rowIndx+'">'+data.rowData.pact_code+'</a>'
                	}	 
            	 },
                 { display: '合同名称', name: 'pact_name', width: '15%'},
                 { display: '客户', name: 'cus_name',  width: '15%'},
                 { display: '变更原因', name: 'change_reason', width: '18%'},
                 { display: '操作员', name: 'user_name',  width: '10%'},
                 { display: '变更前合同查看', name: '', align: 'center', width: '15%',
                	render :function(data){
                		return '<a class="toPreBefor" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code.split("-")[0]+"-"+(parseInt(data.rowData.change_code.split("-")[1])-1)+'</a>'
                	}	 
                 }, { display: '变更后合同查看', name: '', align: 'center', width: '15%',
                	render :function(data){
                		return '<a class="toPre" rowIndex = "'+data.rowIndx+'">'+data.rowData.change_code+'</a>'
                	}	 
                 }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactMainChangeSKHT.do?isCheck=false&is_exe=0'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
            $("#mainGrid").on('click','.toPre',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPre(currentRowData);
       		})
       		
       		   $("#mainGrid").on('click','.toPreBefor',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPreBefor(currentRowData);
       		})
       		
            $("#mainGrid").on('click','.toPact',function(){
             	 var rowIndex = $(this).attr('rowIndex');
                  var currentRowData = grid.getAllData()[rowIndex];
                  toPact(currentRowData);
       		})
        };
        
        //跳转备份合同
        function toPre(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/skht/change/pactMainChangeSKHTPrePage.do?isCheck=false&change_code='+rowData.change_code +'&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '查看',
                modal: true,
            });
        };
        
        //跳转备份合同
        function toPreBefor(rowData){
       	 	parent.$.etDialog.open({
       	 	 url: 'hrp/pac/skht/change/pactMainChangeSKHTPrePage.do?isCheck=false&change_code='+rowData.change_code.split("-")[0]+"-"+(parseInt(rowData.change_code.split("-")[1])-1) +'&pact_code='+rowData.pact_code,
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
            <td class="label" style="width: 100px;">客户：</td>
            <td class="ipt"> <select id="cus_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">变更原因：</td>
            <td class="ipt"><input id="change_reason" type="text" /> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>


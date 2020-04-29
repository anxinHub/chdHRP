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
        	formValidate = $.etValidate({
    			items : [ 
    				{el : $("#min_money"),type:'number'}, 
    				{el : $("#max_money"),type:'number'},
    				]
    		});
    		if(!formValidate.test()){
    			return;
    		};
            params = [
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: $("#sup_no").val() },
                { name: 'min_money', value: $("#min_money").val() },
                { name: 'max_money', value: $("#max_money").val() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'trade_type', value: $("#trade_type").val() },
                { name: 'state_code', value: $("#state_code").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
            trade_type = $("#trade_type").etSelect({ url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE',defaultValue: "none" });
            
            ajaxPostData({
          		 url: '../../../basicset/select/queryPactStateSelect.do?isCheck=false',
       			 success: function (result) {
       				  var stateSoues = [];
       				  for(var i = 0;i<result.length;i++){
       					  var obj = result[i];
       					  if(obj.id >= 12){
       						  stateSoues.push(obj);
       					  }
       				  }
       				state_code = $("#state_code").etSelect({options:stateSoues,defaultValue: "none"});
       			  }
       		});
            
            sup_no = $("#sup_no").etSelect({url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false', defaultValue: "none"});
          }
        
        var initGrid = function () {
            var columns = [
            	 { display: '合同编号', name: 'pact_code', width: '150px'},
                 { display: '合同名称', name: 'pact_name', width: '190px'},
                 { display: '供应商', name: 'sup_name',  width: '190px'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '90px'},
                 { display: '签订科室', name: 'dept_name',  width: '120px'},
                 { display: '贸易类别', name: 'trade_name', align: 'center', width: '80px'},
                 { display: '合同金额', name: 'pact_money', align: 'right', width: '100px'},
                 { display: '合同状态', name: 'state_code_name',  align: 'center', width: '80px'},
                 { display: '合同档案', name: 'state', align: 'center', width: '80px' ,
                	 render:function(data){
               		  return '<a class="getSouse" rowIndex = "'+data.rowIndx+'">合同档案</a>'
               	  	}	 
                 },
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactExecFKHT.do'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '归档',   listeners: [{ click: file }],  icon: 'audit' },
                        { type: 'button', label: '取消归档',   listeners: [{ click: unfile }],  icon: 'back' },
                        { type: 'button', label: '作废',   listeners: [{ click: unuse }],  icon: 'cancel' },
                        { type: 'button', label: '恢复',  listeners: [{ click: recover }],  icon: 'back' },
                        { type: 'button', label: '中止',   listeners: [{ click: stop }],  icon: 'cancel' },
                        { type: 'button', label: '取消中止',   listeners: [{ click: unstop}],  icon: 'back' },
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
            
        	$("#mainGrid").on('click','.getSouse',function(){
              	 var rowIndex = $(this).attr('rowIndex');
                   var currentRowData = grid.getAllData()[rowIndex];
                   toResource(currentRowData);
        	})
        };
        
        function toResource(rowData){
       	 	parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactinfo/pactexec/toPactExecFKHTFilePage.do?isCheck=false&pact_code='+rowData.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '合同归档',
                modal: true,
            });
        }
        
        var unuse = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
	                 var rowdata = this.rowData;
                	 if(rowdata.state_code == '12'){
	                     param.push(rowdata.pact_code);
                	 }
                 });
                 if(param.length == 0){
               		 $.etDialog.error('当前状态不可作废');
               	 	return;
                }
                ajaxPostData({
             	    url: '../pactinit/checkPactMainFKHT.do',
                    data: {
	                   	 mapVo: JSON.stringify(param),
	                   	 check: 'unuse',
	                   	 is_init : 0
                    },
                    success:function(){
                    	query();
                    }
                })
             }
        }
        var recover = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
	                 var rowdata = this.rowData;
                	 if(rowdata.state_code == '13'){
	                     param.push(rowdata.pact_code);
                	 }
                 });
                 if(param.length == 0){
               		 $.etDialog.error('当前状态不可恢复');
               	 	return;
                }
                ajaxPostData({
             	    url: '../pactinit/checkPactMainFKHT.do',
                    data: {
	                   	 mapVo: JSON.stringify(param),
	                   	 check: 'recover',
	                   	 is_init : 0
                    },
                    success:function(){
                    	query();
                    }
                })
             }
        }
        var file = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
	                 var rowdata = this.rowData;
                	 if(rowdata.state_code == '12'){
	                     param.push(rowdata.pact_code);
                	 }
                 });
                 if(param.length == 0){
               		 $.etDialog.error('当前状态不可归档');
               	 	return;
                }
                   ajaxPostData({
                	   url: '../pactinit/checkPactMainFKHT.do',
                       data: {
                      	 mapVo: JSON.stringify(param),
                      	 check: 'file',
                      	 is_init : 0
                       },
                       success:function(){
                    	   query();
                       }
                   })
             }
        }
        var unfile = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     if(rowdata.state_code == '15'){
                         param.push(rowdata.pact_code);
                     }
                 });
                 if(param.length == 0){
                	 $.etDialog.error('当前状态不可取消归档');
                	 return;
                 }
                   ajaxPostData({
                	   url: '../pactinit/checkPactMainFKHT.do',
                       data: {
                      	 mapVo: JSON.stringify(param),
                      	 check: 'unfile',
                      	 is_init : 0
                       },
                       success:function(){
                    	   query();
                       }
                   })
             }
        }
        var stop = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.state_code == '12'){
                        param.push(rowdata.pact_code);
                    }
                });
                if(param.length == 0){
               		 $.etDialog.error('当前状态不可中止');
               	 	return;
                }
                  ajaxPostData({
                	  url: '../pactinit/checkPactMainFKHT.do',
                      data: {
                     	 mapVo: JSON.stringify(param),
                     	 check: 'stop',
                     	 is_init : 0
                      },
                      success:function(){query();}
                  })
            }
       }
        var unstop = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.state_code=='14'){
                        param.push(rowdata.pact_code);
                    }
                });
                if(param.length == 0){
               		$.etDialog.error('当前状态不可取消中止');
               	 	return;
                }
                  ajaxPostData({
                	  url: '../pactinit/checkPactMainFKHT.do',
                      data: {
                     	 mapVo: JSON.stringify(param),
                     	 check: 'unstop',
                     	 is_init : 0
                      },
                      success:function(){query();}
                  })
            }
       }
        //跳转保存页面
        var edit = function (data) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit.do?isCheck=false&pact_code='+data.pact_code,
                 width: $(window).width(),
                 height: $(window).height(),
                 title: '查看',
                 modal: true,
             });
        };

        
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
            <td class="label" style="width: 100px;">签订日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同类型：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">合同金额：</td>
            <td class="ipt"> <input id="min_money" type="text" style="width: 100px">至 <input id="max_money" type="text" style="width: 100px"></td>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">贸易类别：</td>
            <td class="ipt"> <select id="trade_type" style="width: 180px"></select> </td>
        	<td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt"> <select id="state_code" style="width: 180px"></select> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>


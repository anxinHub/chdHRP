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
        var stateArray;
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
                { name: 'state', value: $("#state").val() },
                { name: 'state_code', value: $("#state_code").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
          	pact_type_code = $("#pact_type_code").etSelect({url: '../../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
            trade_type = $("#trade_type").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE',defaultValue: "none"});
            state_code = $("#state_code").etSelect({url: '../../../basicset/select/queryPactStateSelect.do?isCheck=false',defaultValue: "none"});
            sup_no = $("#sup_no").etSelect({url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	
            ajaxPostData({
          		 url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=STATE',
       			  success: function (result) {
       				state_code = $("#state").etSelect({
       	                 options:result,
       	                 defaultValue: "none"
       	              });
       				stateArray = result;
       			  },
       		});
          }
        
        var initGrid = function () {
            var columns = [
            	{ display: '合同编号', name: 'pact_code', width: '150px'},
                { display: '合同名称', name: 'pact_name', width: '190px'},
                { display: '供应商', name: 'sup_name',  width: '190px'},
                { display: '签订日期', name: 'sign_date', align: 'center', width: '90px'},
                { display: '签订科室', name: 'dept_name',  width: '120px'},
                { display: '贸易类别', name: 'trade_name', align: 'center', width: '80px'},
                { display: '合同金额', name: 'pact_money', align: 'right', width: '100px',
               	 render:function(ui){
  	          		  var data = ui.rowData
  	          		  return formatNumber(parseFloat(data.pact_money),2,1)
  	          	 	}
                },
                { display: '合同状态', name: 'state_code_name',  align: 'center', width: '80px'},
                { display: '审核状态', name: 'state_name', align: 'center', width: '80px'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: '../pactinit/queryPactMainFKHT.do?isCheck=false&is_init=0'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: save }], icon: 'save' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'del' },
                        { type: 'button', label: '审核', listeners: [{ click: check }], icon: 'audit' },
                        { type: 'button', label: '消审', listeners: [{ click: uncheck }], icon: 'back' },
                        { type: 'button', label: '确认', listeners: [{ click: confir }], icon: 'audit' },
                        { type: 'button', label: '取消确认', listeners: [{ click: unconfir }], icon: 'back' },
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        var check = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
	                 var rowdata = this.rowData;
                	 if(rowdata.state == 1 && rowdata.state_code == '11'){
                		  param.push(rowdata.pact_code);
                	}
                 });
                 if(param.length == 0){
               		 $.etDialog.error('当前状态不可审核');
               	 	return;
                }
                 ajaxPostData({
                     url: '../pactinit/checkPactMainFKHT.do?isCheck=false',
                     data: {
                    	 mapVo: JSON.stringify(param),
                    	 check: 'check',
                    	 is_init:0
                     },
                     success:function(){
                    	 query();
                     }
                 })
             }
        }
        var uncheck = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                     if(rowdata.state == 2 && rowdata.state_code == '11'){
                    	 param.push(rowdata.pact_code);
                     }
                 });
                 if(param.length == 0){
                	 $.etDialog.error('当前状态不可消审');
                	 return;
                 }
                   ajaxPostData({
                       url: '../pactinit/checkPactMainFKHT.do?isCheck=false',
                       data: {
                      	 mapVo: JSON.stringify(param),
                      	 check: 'uncheck',
                      	is_init:0
                       },
                       success:function(){
                    	   query();
                       }
                   })
             }
        }
        var confir = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.state == 2 && rowdata.state_code == '11'){
                    	param.push(rowdata.pact_code);
                    }
                });
                if(param.length == 0){
               		 $.etDialog.error('当前状态不可确认');
               	 	return;
                }
                  ajaxPostData({
                      url: '../pactinit/checkPactMainFKHT.do?isCheck=false',
                      data: {
                     	 mapVo: JSON.stringify(param),
                     	 check: 'confir',
                     	is_init:0
                      },
                      success:function(){
                    	  query();
                      }
                  })
            }
       }
        var unconfir = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
                    var rowdata = this.rowData;
                    if(rowdata.state == 3 && rowdata.state_code=='12'){
                    	param.push(rowdata.pact_code);
                    }
                });
                if(param.length == 0){
               		 $.etDialog.error('当前状态不可取消确认');
               	 	return;
                }
                  ajaxPostData({
                      url: '../pactinit/checkPactMainFKHT.do',
                      data: {
                     	 mapVo: JSON.stringify(param),
                     	 check: 'unconfir',
                     	is_init:0
                      },
                      success:function(){
                    	  query();
                      }
                  })
            }
       }
        //跳转保存页面
        var save = function (data) {
        	 parent.$.etDialog.open({
                 url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTAdd.do?isCheck=false&is_init=0',
                 //width: $(window).width(),
                 //height: $(window).height(),
                 isMax: true,
                 title: '添加',
                 modal: true,
                 zIndex: 10,
                 frameName: window.name
             });
        };
        //跳转保存页面
        var edit = function (data) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit.do?isCheck=false&pact_code='+data.pact_code,
                 //width: $(window).width(),
                 //height: $(window).height(),
                 isMax: true, isResize: true,
                 title: '修改',
                 modal: true,
                 frameName: window.name
             });
        };
        
        //删除
        var remove = function () {
        	 var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
                     var rowdata = this.rowData;
                	 if(rowdata.state == 1){
	                     rowdata.group_id = ${group_id};
	                     rowdata.hos_id = ${hos_id};
	                     rowdata.copy_code = '${copy_code}';
	                     param.push(rowdata);
                	 }
                 });
                 if(param.length == 0){$.etDialog.error('暂无数据可删除');return;}
                 $.etDialog.confirm('确定删除?', function () {
                     ajaxPostData({
                         url: '../pactinit/deletePactMainFKHT.do',
                         data: {
                        	 mapVo: JSON.stringify(param)
                         },
                         success: function () {
                             grid.deleteRows(data);
                         }
                     })
                 });
             }
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
        	<td class="label" style="width: 100px;">审核状态：</td>
            <td class="ipt"> <select id="state" style="width: 180px"></select> </td>
        	<td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt"> <select id="state_code" style="width: 180px"></select> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>


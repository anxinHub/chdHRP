<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate,jquery_print,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker;
        var endpicker;
        var query = function () {
            params = [
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() },
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: sup_no.getValue() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'bill_code', value: $("#bill_code").val() },
                { name: 'pay_code', value: $("#pay_code").val() },
                { name: 'state', value: state.getValue() },
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
          	sup_no = $("#sup_no").etSelect({url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	state = $("#state").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=STATE',defaultValue: "none"});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '付款单号', name: 'pay_code',width: '140px'},
            	 { display: '付款日期', name: 'pay_date', align: 'center', width: '100px'},
                 { display: '供应商', name: 'sup_name', width: '150px'},
            	 { display: '发票号码', name: 'bill_code',width: '150px'},
            	 { display: '付款金额', name: 'pay_money', align:'right', width: '150px'},
            	 { display: '合同编号', name: 'pact_code',width: '150px'},
                 { display: '合同名称', name: 'pact_name',width: '150px'},
                 { display: '制单人', name: 'maker_name', align: 'center',width: '80px'},
                 { display: '审核人', name: 'checker_name',align: 'center', width: '80px'},
                 { display: '确认人', name: 'confirmer_name',align: 'center', width: '80px'},
                 { display: '状态', name: 'state_name', align: 'center', width: '80px'}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactPayMainFKHT.do?isCheck=false&is_init=0'
                },
                rowDblClick: function (event, ui) {
                    var rowData = ui.rowData;
                    edit(rowData);
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: del }], icon: 'del' },
                        { type: 'button', label: '审核',   listeners: [{ click: check }],  icon: 'audit' },
                        { type: 'button', label: '消审',   listeners: [{ click: uncheck }],  icon: 'back' },
                        { type: 'button', label: '确认',   listeners: [{ click: myConfirm }],  icon: 'audit' },
                        { type: 'button', label: '取消确认',  listeners: [{ click: unconfirm }],  icon: 'back' },
                        { type: 'button', label: '打印',  listeners: [{ click: print }],  icon: 'print' },
                        { type: 'button', label: '模板打印',  listeners: [{ click: printModel }],  icon: 'print' },
                        
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        var printModel = function(){
 			useId='${user_id }';
 			officeFormTemplate({template_code:"11006",use_id:useId});
        };
        
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("请先查询数据！");
       			return;
       		}
           	var printPara={
                   	title: "付款单",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.fkht.payment.PactPayMainFKHTService",
   					method_name:"queryPactPayMainFKHTPrint",
   					bean_name:"pactPayMainFKHTService",
	   				start_date:startpicker.getValue(),
	                end_date:endpicker.getValue(),
	                pact_type_code:$("#pact_type_code").val(),
	                sup_no:sup_no.getValue(),
	                pact_code:$("#pact_code").val(),
	                pact_name:$("#pact_name").val(),
	                bill_code:$("#bill_code").val(),
	                pay_code:$("#pay_code").val(),
	                state:state.getValue(),
	                is_init : 0
               };
               officeGridPrint(printPara);
           };
        
        var check = function(data){
             var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
                 var param = [];
                 $(data).each(function () {
	                 var rowdata = this.rowData;
                	 if(rowdata.state == '01'){
                		 var obj = new Object();
                		 obj.pay_code = rowdata.pay_code;
                		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
                	 }
                 });
                if(param.length == 0){$.etDialog.error('当前状态不可审核'); return;}
                ajaxPostData({
             	   	url: 'checkPactPayMainFKHTState.do?isCheck=false',
                    data: {mapVo: JSON.stringify(param),state: 'check'},
                    success:function(){query();}
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
               	 	if(rowdata.state == '02'){
	               	 	 var obj = new Object();
	            		 obj.pay_code = rowdata.pay_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消审核'); return;}
               ajaxPostData({
            	   	url: 'checkPactPayMainFKHTState.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'uncheck'},
                   success:function(){query();}
               })
            }
       }
        var myConfirm = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.state == '02'){
	               	 	 var obj = new Object();
	            		 obj.pay_code = rowdata.pay_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可确认'); return;}
               ajaxPostData({
            	   	url: 'checkPactPayMainFKHTState.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'confirm'},
                   success:function(){query();}
               })
            }
       }
        
        var unconfirm = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.state == '03'){
	               	 	 var obj = new Object();
	            		 obj.pay_code = rowdata.pay_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消确认'); return;}
               ajaxPostData({
            	   	url: 'checkPactPayMainFKHTState.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'unconfirm'},
                   success:function(){query();}
               })
            }
       }
        
        var del = function(data){
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
                 	   url: 'deletePactPayMainFKHT.do?isCheck=false',
                        data: { mapVo: JSON.stringify(param) },
                        success: function () {grid.deleteRows(data);}
                    })
                });
            }
       }
        //跳转修改页面
        var edit = function (data) {
        	parent.$.etDialog.open({
        		 url: 'hrp/pac/fkht/payment/payment/pactPayMainFKHTEditPage.do?isCheck=false&is_init=0&pay_code='+data.pay_code + '&pact_code='+data.pact_code + '&state=' + data.state,
        		 width: $(window).width(),
                 height: $(window).height(),
                 title: '修改',
                 modal: true,
                 frameName: window.name
             });
        };
        var add = function (data) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/fkht/payment/payment/pactPayMainFKHTAddPage.do?isCheck=false&is_init=0',
                 width: $(window).width(),
                 height: $(window).height(),
                 title: '添加',
                 modal: true,
                 frameName: window.name
             });
        };

        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
        
        //日期
        var initfrom = function(){
        	startpicker = $("#start_date").etDatepicker({
    			defaultDate: "yyyy-fm-fd",
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
            <td class="label" style="width: 100px;">付款日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同类别：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">发票号码：</td>
            <td class="ipt"><input id="bill_code" type="text"/> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">付款单号：</td>
            <td class="ipt"><input id="pay_code" type="text"/> </td>
         	<td class="label" style="width: 100px;">状态：</td>
            <td class="ipt"><select id="state" style="width: 180px"/></select></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
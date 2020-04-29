<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,validate,pageOffice" name="plugins" />
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
                { name: 'bank_code', value: $("#bank_code").val() },
                { name: 'letter_code', value: $("#letter_code").val() },
                { name: 'sp_cond', value: $("#sp_cond").val() },
                { name: 'letter_state', value: $("#letter_state").val() },
                { name: 'max_money', value: $("#max_money").val() },
                { name: 'min_money', value: $("#min_money").val() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() }
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../../basicset/select/queryPactTypeSKHTSelect.do?isCheck=false',defaultValue: "none"});
          	cus_no = $("#cus_no").etSelect({url: '../../../basicset/select/queryHosCusDictSelect.do?isCheck=false',defaultValue: "none"});
          	letter_state = $("#letter_state").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=STATE' ,defaultValue: "none"});
            sp_cond = $("#sp_cond").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=SP_COND',defaultValue: "none"});
            bank_code = $("#bank_code").etSelect({url: '../../../basicset/select/queryPactBankDictSelect.do?isCheck=false',defaultValue: "none"});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '保函编号', name: 'letter_code', align: 'center',width: '15%'},
            	 { display: '开具日期', name: 'sign_date', align: 'center',width: '10%'},
            	 { display: '银行', name: 'bank_name', align: 'center',width: '12%'},
                 { display: '客户', name: 'cus_name', align: 'center', width: '12%'},
            	 { display: '合同编号', name: 'pact_code', align: 'center',width: '15%'},
                 { display: '合同名称', name: 'pact_name', align: 'center' ,width: '19%'},
                 { display: '担保金额', name: 'money', align: 'right', width: '10%'},
                 { display: '开始日期', name: 'start_date', align: 'center', width: '10%'},
                 { display: '结束日期', name: 'end_date', align: 'center', width: '10%'},
                 { display: '索赔条件', name: 'sp_cond', align: 'center', width: '10%'},
                 { display: '备注', name: 'note', align: 'center', width: '15%'},
                 { display: '制单人', name: 'maker_name', align: 'center', width: '12%'},
                 { display: '审核人', name: 'checker_name', align: 'center', width: '12%'},
                 { display: '确认人', name: 'confirmer_name', align: 'center', width: '12%'},
                 { display: '失效人', name: 'disabler_name', align: 'center', width: '12%'},
                 { display: '状态', name: 'letter_state', align: 'center', width: '5%'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactLetterSKHT.do?isCheck=false'
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
                        { type: 'button', label: '确认',   listeners: [{ click: myconfirm }],  icon: 'audit' },
                        { type: 'button', label: '取消确认',  listeners: [{ click: unconfirm }],  icon: 'back' },
                        { type: 'button', label: '失效',  listeners: [{ click: disable }],  icon: 'cancel' },
                        { type: 'button', label: '恢复',  listeners: [{ click: undisable }],  icon: 'back' },
                        { type: 'button', label: '打印',  listeners: [{ click: print }],  icon: 'print' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("请先查询数据！");
       			return;
       		}
           	var printPara={
                   	title: "履约银行保函",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.skht.guarantee.PactLetterSKHTService",
   					method_name:"queryPactLetterSKHTPrint",
   					bean_name:"pactLetterSKHTService",
   				 	pact_type_code : $("#pact_type_code").val(),
                 	cus_no : $("#cus_no").val(),
                 	pact_code : $("#pact_code").val(),
                 	pact_name : $("#pact_name").val(),
                 	rec_code : $("#rec_code").val(),
                 	pay_way : $("#pay_way").val(),
                 	cheq_no : $("#cheq_no").val(),
                 	note : $("#note").val(),
                 	state : $("#state").val(),
                 	start_date : startpicker.getValue(),
                 	end_date : endpicker.getValue(),
                 	is_init: 0
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
                	 if(rowdata.letter_state == '新建'){
                		 var obj = new Object();
                		 obj.letter_code = rowdata.letter_code;
                		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
                	 }
                 });
                if(param.length == 0){$.etDialog.error('当前状态不可审核'); return;}
                ajaxPostData({
             	   	url: 'checkPactLetterSKHT.do?isCheck=false',
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
               	 	if(rowdata.letter_state == '审核'){
	               	 	 var obj = new Object();
	            		 obj.letter_code = rowdata.letter_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消审核'); return;}
               ajaxPostData({
            	   	url: 'checkPactLetterSKHT.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'uncheck'},
                   success:function(){query();}
               })
            }
       }
        var myconfirm = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.letter_state == '审核'){
	               	 	 var obj = new Object();
	            		 obj.letter_code = rowdata.letter_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可确认'); return;}
               ajaxPostData({
            	   	url: 'checkPactLetterSKHT.do?isCheck=false',
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
               	 	if(rowdata.letter_state == '确认'){
	               	 	 var obj = new Object();
	            		 obj.letter_code = rowdata.letter_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消确认'); return;}
               ajaxPostData({
            	   	url: 'checkPactLetterSKHT.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'unconfirm'},
                   success:function(){query();}
               })
            }
       }
        
        var disable = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.letter_state == '确认'){
	               	 	 var obj = new Object();
	            		 obj.letter_code = rowdata.letter_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可失效'); return;}
               ajaxPostData({
            	   	url: 'checkPactLetterSKHT.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'disable'},
                   success:function(){query();}
               })
            }
       }
        var undisable = function(data){
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                var param = [];
                $(data).each(function () {
	                var rowdata = this.rowData;
               	 	if(rowdata.letter_state == '失效'){
	               	 	 var obj = new Object();
	            		 obj.letter_code = rowdata.letter_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可恢复'); return;}
               ajaxPostData({
            	   	url: 'checkPactLetterSKHT.do?isCheck=false',
                   data: {mapVo: JSON.stringify(param),state: 'undisable'},
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
	                if(rowdata.letter_state == '新建'){
		                rowdata.group_id = ${group_id};
	                    rowdata.hos_id = ${hos_id};
	                    rowdata.copy_code = '${copy_code}';
		               	param.push(rowdata);
	                }
                });
                if(param.length == 0){$.etDialog.error('暂无数据可删除'); return;}
                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                 	   url: 'deletePactLetterSKHT.do?isCheck=false',
                        data: { mapVo: JSON.stringify(param) },
                        success: function () {grid.deleteRows(data);}
                    })
                });
            }
       }
        //跳转修改页面
        var edit = function (data) {
	        	parent.$.etDialog.open({
	        		 url: 'hrp/pac/skht/guarantee/letter/toPactLetterSKHTEditPage.do?isCheck=false&letter_code='+data.letter_code + '&pact_code='+data.pact_code,
	                 width: '680px',
	                 height: $(window).height(),
	                 title: '修改',
	                 modal: true,
	                 frameName: window.name
	             });
        };
        var add = function (data) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/skht/guarantee/letter/toPactLetterSKHTAddPage.do?isCheck=false',
                 width: '680px',
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
            <td class="label" style="width: 100px;">开具日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="start_date" type="text" style="width: 100px"/>至 <input id="end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同类别：</td>
            <td class="ipt"><select id="pact_type_code" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">客户：</td>
            <td class="ipt"> <select id="cus_no" style="width: 180px"></select> </td>
        </tr>
        <tr>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
            <td class="label" style="width: 100px;">银行信息：</td>
            <td class="ipt"><input id="bank_code" type="text" style="width: 180px"/> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">担保金额：</td>
            <td class="ipt"><input id="min_money" type="text" style="width: 100px"/>至<input id="max_money" type="text" style="width: 100px"/></td>
        	<td class="label" style="width: 100px;">保函编号：</td>
            <td class="ipt"><input id="letter_code" type="text" /></td>
        	<td class="label" style="width: 100px;">索赔条件：</td>
            <td class="ipt"><input id="sp_cond" type="text" style="width: 180px"/> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">状态：</td>
            <td class="ipt"><select id="letter_state" style="width: 180px"></select></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
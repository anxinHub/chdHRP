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
                { name: 'cus_no', value: cus_no.getValue() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'state', value: state.getValue() },
                { name: 'first_man', value: first_man.getValue() },
                { name: 'reason', value: $("#reason").val() },
                { name: 'party', value: party.getValue() },
                { name: 'start_date', value: startpicker.getValue() },
                { name: 'end_date', value: endpicker.getValue() }
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../../basicset/select/queryPactTypeSKHTSelect.do?isCheck=false',defaultValue: "none"});
          	cus_no = $("#cus_no").etSelect({url: '../../../basicset/select/queryHosCusDictSelect.do?isCheck=false',defaultValue: "none"});
          	first_man = $("#first_man").etSelect({url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',defaultValue: "none"});
          	state = $("#state").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=STATE',defaultValue: "none"});
            party = $("#party").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=PARTY',defaultValue: "none"});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '登记编号', name: 'sp_code',width: '140px'},
            	 { display: '登记日期', name: 'sign_date', align: 'center',width: '100px'},
                 { display: '客户', name: 'cus_name', width: '150px'},
            	 { display: '合同编号', name: 'pact_code',width: '150px'},
                 { display: '合同名称', name: 'pact_name',width: '150px'},
                 { display: '摘要', name: 'note',width: '150px'},
                 { display: '索赔金额', name: 'sp_money', align: 'right', width: '90px'},
                 { display: '索赔方', name: 'party_name', align: 'center', width: '60px'},
                 { display: '制单人', name: 'maker_name', align: 'center',width: '80px'},
                 { display: '审核人', name: 'checker_name',align: 'center', width: '80px'},
                 { display: '确认人', name: 'confirmer_name',align: 'center', width: '80px'},
                 { display: '状态', name: 'state_name', align: 'center', width: '80px'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	checkbox: true,
                dataModel: {
                    url: 'queryPactSPSKHT.do?isCheck=false'
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
                   	title: "合同索赔",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.service.skht.breach.PactSPSKHTService",
   					method_name:"queryPactSPSKHTPrint",
   					bean_name:"pactSPSKHTService",
   				 	pact_type_code : $("#pact_type_code").val(),
                 	cus_no : $("#cus_no").val(),
                 	pact_code : $("#pact_code").val(),
                 	pact_name : $("#pact_name").val(),
                 	state : $("#state").val(),
                 	start_date : startpicker.getValue(),
                 	end_date : endpicker.getValue(),
                    first_man : first_man.getValue(),
                    reason : $("#reason").val(),
                    party : party.getValue(),
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
                		 obj.sp_code = rowdata.sp_code;
                		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
                	 }
                 });
                if(param.length == 0){$.etDialog.error('当前状态不可审核'); return;}
                ajaxPostData({
             	   	url: 'checkPactSPSKHT.do?isCheck=false',
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
	            		 obj.sp_code = rowdata.sp_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消审核'); return;}
               ajaxPostData({
            	   	url: 'checkPactSPSKHT.do?isCheck=false',
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
               	 	if(rowdata.state == '02'){
	               	 	 var obj = new Object();
	            		 obj.sp_code = rowdata.sp_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可确认'); return;}
               ajaxPostData({
            	   	url: 'checkPactSPSKHT.do?isCheck=false',
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
	            		 obj.sp_code = rowdata.sp_code;
	            		 obj.pact_code = rowdata.pact_code;
	                     param.push(obj);
               	 	}
                });
               if(param.length == 0){$.etDialog.error('当前状态不可取消确认'); return;}
               ajaxPostData({
            	   	url: 'checkPactSPSKHT.do?isCheck=false',
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
	                if(rowdata.state == '01'){
		                rowdata.group_id = ${group_id};
	                    rowdata.hos_id = ${hos_id};
	                    rowdata.copy_code = '${copy_code}';
		               	param.push(rowdata);
	                }
                });
                if(param.length == 0){$.etDialog.error('暂无数据可删除'); return;}
                $.etDialog.confirm('确定删除?', function () {
                    ajaxPostData({
                 	   url: 'deletePactSPSKHT.do?isCheck=false',
                        data: { mapVo: JSON.stringify(param) },
                        success: function () {grid.deleteRows(data);}
                    })
                });
            }
       }
        //跳转修改页面
        var edit = function (data) {
        	parent.$.etDialog.open({
        		 url: 'hrp/pac/skht/break/sp/toPactSPSKHTEditPage.do?isCheck=false&sp_code='+data.sp_code + '&pact_code='+data.pact_code,
        		 width: '1000px',
                 height: $(window).height(),
                 title: '修改',
                 modal: true,
                 frameName: window.name
             });
        };
        var add = function (data) {
        	parent.$.etDialog.open({
                 url: 'hrp/pac/skht/break/sp/toPactSPSKHTAddPage.do?isCheck=false',
                 width: '1000px',
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
            <td class="label" style="width: 100px;">登记日期：</td>
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
            <td class="label" style="width: 100px;">摘要：</td>
            <td class="ipt"><input id="note" type="text"/> </td>
        </tr>
        <tr>
        	<td class="label" style="width: 100px;">乙方负责人：</td>
            <td class="ipt"><select id="first_man" style="width: 180px"/></select></td>
        	<td class="label" style="width: 100px;">赔偿方：</td>
            <td class="ipt"><select id="party" style="width: 180px"/></select></td>
         	<td class="label" style="width: 100px;">状态：</td>
            <td class="ipt"><select id="state" style="width: 180px"/></select></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
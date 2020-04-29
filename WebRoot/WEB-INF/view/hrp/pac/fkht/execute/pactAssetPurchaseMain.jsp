<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker,checkbox,validate,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker, endpicker,pact_type_code,sup_no,sign_dept,ass_natur,state_code,is_ins,is_accept,is_in;
        var query = function () {
            params = [
				{ name: 'start_date', value: startpicker.getValue() },
				{ name: 'end_date', value: endpicker.getValue() },
                { name: 'pact_type_code', value: pact_type_code.getValue()},
                { name: 'sup_no', value: sup_no.getValue() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'sign_dept', value: $("#sign_dept").val() },
                { name: 'ass_natur', value: ass_natur.getValue() },
                { name: 'ass_name', value: $("#ass_name").val() },
                { name: 'state_code', value: state_code.getValue() },
                {name: 'is_ins', value: is_ins.status == "checked" ? 1 : 0, },
                {name: 'is_accept', value: is_accept.status == "checked" ? 1 : 0, },
                {name: 'is_in', value: is_in.status == "checked" ? 1 : 0, }
                
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	sign_dept = $("#sign_dept").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue: "none"});
          	ass_natur = $("#ass_natur").etSelect({url: '../../basicset/select/queryAssTypeSelect.do?isCheck=false',defaultValue: "none"});
          	state_code = $("#state_code").etSelect({url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',defaultValue: "none"});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '合同编号', name: 'pact_code', align: 'center',width: '10%'},
                 { display: '合同名称', name: 'pact_name', align: 'left' ,width: '12%'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '8%'},
                 { display: '供应商', name: 'sup_name', align: 'left', width: '12%'},
                 { display: '签订科室', name: 'dept_name', align: 'left', width: '10%'},
                 { display: '合同金额', name: 'pact_money', align: 'right', width: '10%',
                	 render:function(ui){
             			var value = ui.rowData.pact_money
             			if(value){
             				return formatNumber(value,2,1);
             			}else{
             				return 0.00;
             			}
             		}	 
                 },
                 { display: '资产编码', name: 'ass_code', align: 'left', width: '8%'},
                 { display: '资产名称', name: 'ass_name', align: 'left', width: '10%'},
                 { display: '规格', name: 'ass_spec', align: 'left', width: '6%'},
                 { display: '型号', name: 'ass_model', align: 'left', width: '6%'},
                 { display: '合同数量', name: 'amount', align: 'center', width: '6%'},
                 { display: '安装数量', name: 'ins_amount', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.ins_amount==0){
	               			 return 0;
	               		 }else{
	               			 return "<a href=javascript:openInsInfo('"+data.pact_code+"|"+data.sup_id+"')>"+data.ins_amount+"</a>";
	               		 }
                 	}
                 },
                 { display: '验收数量', name: 'accept_amount', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.accept_amount==0){
	               			 return 0;
	               		 }else{
	               			 return "<a href=javascript:openAcceptInfo('"+data.pact_code+"|"+data.sup_id+"')>"+data.accept_amount+"</a>";
	               		 }
                 	}
                 },
                 { display: '入库数量', name: 'in_amount', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.in_amount==0){
	               			 return 0;
	               		 }else{
	               			 return "<a href=javascript:openAssInInfo('"+data.pact_code+"|"+data.sup_id+"')>"+data.in_amount+"</a>";
	               		 }
                 	}
                 },
                 { display: '退货数量', name: 'back_amount', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.back_amount==0){
	               			 return 0;
	               		 }else{
	               			 return "<a href=javascript:openAssBackInfo('"+data.pact_code+"|"+data.sup_id+"')>"+data.back_amount+"</a>";
	               		 }
                 	}
                 },
                 { display: '未入库数量', name: 'left_amount', align: 'center', width: '6%'}
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssetPurchaseFKHT.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
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
                   	title: "资产执行汇总表",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.serviceImpl.fkht.execute.PactAssetPurchaseServiceImpl",
   					method_name:"queryPactAssetPurchaseFKHTPrint",
   					bean_name:"pactAssetPurchaseService",
   					start_date : startpicker.getValue(),
                 	end_date : endpicker.getValue(),
   	                pact_type_code: pact_type_code.getValue(),
   	                sup_no: sup_no.getValue() ,
   	                pact_code: $("#pact_code").val() ,
   	                pact_name: $("#pact_name").val() ,
   	                sign_dept: $("#sign_dept").val() ,
   	                ass_natur: ass_natur.getValue() ,
   	                ass_name: $("#ass_name").val() ,
   	                state_code: state_code.getValue() ,
   	                is_ins: is_ins.status == "checked" ? 1 : 0, 
   	                is_accept: is_accept.status == "checked" ? 1 : 0, 
   	                is_in: is_in.status == "checked" ? 1 : 0, 
                 	
               };
               officeGridPrint(printPara);
           };
        
        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
         //安装信息 页面 跳转
        function openInsInfo(data){
        	parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseMainInsPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
                width: $(window).width(),
                height: $(window).height(),
                title: '安装信息',
                modal: true,
                frameNameObj :window.name
            });
        }
        //验收信息 页面 跳转
		function openAcceptInfo(data){
			parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseMainAcceptPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
                width: $(window).width(),
                height: $(window).height(),
                title: '验收信息',
                modal: true,
                frameNameObj :window.name
            });   
		}
		//入库信息 页面 跳转
		function openAssInInfo(data){
			parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseMainInPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
                width: $(window).width(),
                height: $(window).height(),
                title: '入库信息',
                modal: true,
                frameNameObj :window.name
            }); 
		}
		//退货信息 页面 跳转
		function openAssBackInfo(data){
			parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseMainBackPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
                width: $(window).width(),
                height: $(window).height(),
                title: '退货信息',
                modal: true,
                frameNameObj :window.name
            }); 
		}
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
    		is_ins = $('#is_ins').etCheck();
    		is_accept = $('#is_accept').etCheck();
    		is_in = $('#is_in').etCheck();
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
            <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><input id="sign_dept" type="text" style="width: 180px"/> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">资产性质：</td>
            <td class="ipt"><input id="ass_natur" type="text" style="width: 180px"/></td>
        	<td class="label" style="width: 100px;">资产名称：</td>
            <td class="ipt"><input id="ass_name" type="text" /></td>
        	<td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt"><input id="state_code" type="text" style="width: 180px"/> </td>
        </tr>
        <tr>
        	<td></td>
        	<td>
        		<input id="is_ins" type="checkbox" />含安装中
        		<input id="is_accept" type="checkbox" />含验收中
        		<input id="is_in" type="checkbox" />含入库中
        	</td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var grid;
        var startpicker,endpicker,pact_type_code,sup_no,trade_type,sign_dept,state_code;
        var query = function () {
            params = [
                      { name: 'start_date', value: startpicker.getValue() },
	                  { name: 'end_date', value: endpicker.getValue() },
		              { name: 'pact_type_code', value: pact_type_code.getValue() },
		              { name: 'sup_id', value: sup_no.getValue() },
		              { name: 'min_money', value: $("#min_money").val() },
		              { name: 'max_money', value: $("#max_money").val() },
		              { name: 'pact_code', value: $("#pact_code").val() },
		              { name: 'pact_name', value: $("#pact_name").val() },
		              { name: 'trade_type', value: trade_type.getValue() },
		              { name: 'sign_dept', value: sign_dept.getValue() },
		              { name: 'state_code', value: state_code.getValue() }
               
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	trade_type = $("#trade_type").etSelect({url: '../../basicset/select/queryDictSelect.do?isCheck=false&f_code=TRADE_TYPE',defaultValue: "none"});
          	sign_dept = $("#sign_dept").etSelect({url: '../../basicset/select/queryDeptSelect.do?isCheck=false',defaultValue: "none"});
          	state_code = $("#state_code").etSelect({url: '../../basicset/select/queryPactStateSelect.do?isCheck=false',defaultValue: "none"});
        }
        
        var initGrid = function () {
            var columns = [
            	 { display: '合同编号', name: 'pact_code', align: 'center',width: '10%'},
                 { display: '合同名称', name: 'pact_name', align: 'left' ,width: '15%'},
                 { display: '贸易类型', name: 'trade_type_name', align: 'center', width: '6%'},
                 { display: '签订日期', name: 'sign_date', align: 'center', width: '6%'},
                 { display: '供应商', name: 'sup_name', align: 'left', width: '15%'},
                 { display: '签订科室', name: 'dept_name', align: 'left', width: '12%'},
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
                 { display: '安装', name: 'ins_no', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.ins_no==0){
	               			 //return '查看';
	               			 return '';
	               		 }else{
	               			 return "<a href=javascript:openInsInfo('"+data.pact_code+"')>查看</a>";
	               		 }
                 	}
                 },
                 { display: '验收', name: 'accept_no', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.accept_no==0){
	               			 //return '查看';
	               			 return '';
	               		 }else{
	               			 return "<a href=javascript:openAcceptInfo('"+data.pact_code+"')>查看</a>";
	               		 }
                 	}
                 },
                 { display: '入库', name: 'ass_in_no', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.ass_in_no==0){
	               			 //return '查看';
	               			 return '';
	               		 }else{
	               			 return "<a href=javascript:openAssInInfo('"+data.pact_code+"|"+data.sup_id+"')>查看</a>";
	               		 }
                 	}
                 },
                 { display: '退货', name: 'ass_back_no', align: 'center', width: '6%',
                	 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.ass_back_no==0){
	               			 //return '查看';
	               			 return '';
	               		 }else{
	               			 return "<a href=javascript:openAssBackInfo('"+data.pact_code+"|"+data.sup_id+"')>查看</a>";
	               		 }
                 	}
                 }
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
                dataModel: {
                    url: 'queryPactAssPurchaseFKHTDet.do?isCheck=false'
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };
        
        
        $(function () {
            initfrom();
            initGrid();
            initSelect();
        })
        //安装信息 页面 跳转
        function openInsInfo(data){
        	parent.$.ligerDialog.open({
				url : 'hrp/ass/assinsmain/assInsMainMainPage.do?isCheck=false&pact_code='+data.pact_code,
				height : $(window).height(),
				width : $(window).width(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '安装信息'
			});
        	/* parent.$.etDialog.open({
                url: 'hrp/ass/assinsmain/assInsMainMainPage.do?isCheck=false&pact_code='+data.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '安装信息',
                modal: true,
                frameNameObj :window.name
            }); */
        }
        //验收信息 页面 跳转
		function openAcceptInfo(data){
			parent.$.ligerDialog.open({
				url : 'hrp/ass/assacceptmain/assAcceptMainMainPage.do?isCheck=false&pact_code='+data.pact_code,
				height : $(window).height(),
				width : $(window).width(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '验收信息'
			});
		/* 	parent.$.etDialog.open({
                url: 'hrp/ass/assacceptmain/assAcceptMainMainPage.do?isCheck=false&pact_code='+data.pact_code,
                width: $(window).width(),
                height: $(window).height(),
                title: '验收信息',
                modal: true,
                frameNameObj :window.name
            });     */	
		}
		//入库信息 页面 跳转
		function openAssInInfo(data){
			parent.$.ligerDialog.open({
				url : 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseDetInPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
				height : $(window).height(),
				width : $(window).width(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '入库信息'
			});
			/* parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseDetInPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
                width: $(window).width(),
                height: $(window).height(),
                title: '入库信息',
                modal: true,
                frameNameObj :window.name
            }); */
		}
		//退货信息 页面 跳转
		function openAssBackInfo(data){
			parent.$.ligerDialog.open({
				url : 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseDetBackPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
				height : $(window).height(),
				width : $(window).width(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '退货信息'
			});
			/* parent.$.etDialog.open({
                url: 'hrp/pac/fkht/pactExecAnaly/pactAssetPurchaseDetBackPage.do?isCheck=false&pact_code='+data.split("|")[0]+'&sup_id='+data.split("|")[1],
                width: $(window).width(),
                height: $(window).height(),
                title: '退货信息',
                modal: true,
                frameNameObj :window.name
            }); */
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
        	<td class="label" style="width: 100px;">合同金额：</td>
            <td class="ipt" style="width: 220px;">
                <input id="min_money" type="text" style="width: 100px"/>至 <input id="max_money" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">贸易类别：</td>
            <td class="ipt"><input id="trade_type" type="text" style="width: 180px"/></td>
             <td class="label" style="width: 100px;">签订科室：</td>
            <td class="ipt"><input id="sign_dept" type="text" style="width: 180px"/> </td>
        	<td class="label" style="width: 100px;">合同状态：</td>
            <td class="ipt"><input id="state_code" type="text" style="width: 180px"/></td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
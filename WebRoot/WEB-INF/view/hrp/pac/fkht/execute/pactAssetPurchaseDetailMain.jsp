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
        var startpicker,endpicker,pact_type_code ,sup_no ,ass_type ,use_state ;
        var pact_code = '${pact_code}' ;
        var query = function () {
            params = [
				{ name: 'start_date', value: startpicker.getValue() },
				{ name: 'end_date', value: endpicker.getValue() },
                { name: 'pact_type_code', value: $("#pact_type_code").val() },
                { name: 'sup_no', value: sup_no.getValue() },
                { name: 'in_start_date', value: in_start_date.getValue() },
                { name: 'in_end_date', value: in_end_date.getValue() },
                { name: 'pact_code', value: $("#pact_code").val() },
                { name: 'pact_name', value: $("#pact_name").val() },
                { name: 'ass_type_id', value: $("#ass_type").val() },
                { name: 'ass_name', value: ass_type.getValue() },
                { name: 'use_state', value: use_state.getValue() }
                
                
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
        	pact_type_code = $("#pact_type_code").etSelect({url: '../../basicset/select/queryPactTypeFKHTSelect.do?isCheck=false',defaultValue: "none"});
          	sup_no = $("#sup_no").etSelect({url: '../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "none"});
          	ass_type = $("#ass_type").etSelect({url: '../../basicset/select/queryAssTypeSelect.do?isCheck=false',defaultValue: "none"});
          	use_state = $("#use_state").etSelect({
          		url:'../../../ass/queryAssCardUseStateDict.do?isCheck=false',
          		defaultValue:"none"
          	})
        }
        
        var initGrid = function () {
            var columns = [
                 { display: '卡片编码', name: 'ass_card_no', align: 'left', width: '10%',
                	 render:function(ui) {
 	               		var data = ui.rowData;
 	               		return "<a href=javascript:openAssCard('"+data.group_id+"|"+data.hos_id
 	               				+"|"+data.copy_code+"|"+data.ass_card_no+"')>"+data.ass_card_no+"</a>";
                  	}
                 },
                 { display: '使用状态', name: 'use_state_name', align: 'center', width: '8%'},
                 { display: '资产名称', name: 'ass_name', align: 'left', width: '10%'},
                 { display: '资产类别', name: 'ass_type_name', align: 'left', width: '10%'},
                 { display: '品牌', name: 'ass_brand', align: 'left', width: '6%'},
                 { display: '规格', name: 'ass_spec', align: 'left', width: '6%'},
                 { display: '型号', name: 'ass_model', align: 'left', width: '6%'},
                 { display: '生产厂家', name: 'fac_name', align: 'left', width: '10%'},
                 { display: '原值', name: 'price', align: 'right', width: '8%',
                	 render:function(ui){
                		 var value = ui.rowData.price
                		 if(value){
                			 return formatNumber(value,2,1)
                		 }else{
                			 return 0.00;
                		 }
                	 }
                	 
                 },
            	 { display: '入库日期', name: 'in_date', align: 'center',width: '8%'},
            	 { display: '入库单号', name: 'ass_in_no', align: 'left',width: '10%',
            		 render:function(ui) {
 	               		var data = ui.rowData;
 	               		if(data.ass_in_no){
	 	               		return "<a href=javascript:openAssin('"+data.group_id+"|"+data.hos_id
	            				+"|"+data.copy_code+"|"+data.ass_in_no+"|"+data.ass_naturs+"')>"+data.ass_in_no+"</a>";
 	               		}else{
	               			 return "";
	               		 }
 	               		
 	               		
                  	}	 
            	 },
            	 { display: '退货日期', name: 'back_date', align: 'center',width: '8%'},
            	 { display: '退货单号', name: 'ass_back_no', align: 'left',width: '10%',
            		 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.ass_back_no){
	               			 return "<a href=javascript:openAssBack('"+data.group_id+"|"+data.hos_id
	               				+"|"+data.copy_code+"|"+data.ass_back_no+"|"+data.ass_naturs+"')>"+data.ass_back_no+"</a>";
	               		 }else{
	               			 return "";
	               		 }
                 	}
            		 
            	 },
            	 { display: '合同编号', name: 'pact_code', align: 'left',width: '10%',
            		 render:function(ui) {
	               		 var data = ui.rowData;
	               		 if(data.ass_back_no){
	               			 return "<a href=javascript:openPactInfo('"+data.pact_code+"')>"+data.pact_code+"</a>";
	               		 }else{
	               			 return "";
	               		 }
                 	}
            	 },
                 { display: '合同名称', name: 'pact_name', align: 'left' ,width: '12%'},
                 { display: '合同类别', name: 'type_name', align: 'left' ,width: '12%'},
                 { display: '签订科室', name: 'dept_name', align: 'left', width: '10%'},
                 { display: '供应商', name: 'sup_name', align: 'left', width: '12%'},
            ];
            var paramObj = {
            	editable: false,
            	height: '97%',
            	width:'100%',
            	dataModel: {
                     url: 'queryPactAssetPurchaseDetailFKHT.do?isCheck=false'
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
        
        //卡片页面链
        function openAssCard(obj) {
    		var vo = obj.split("|");
    		if("null"==vo[3] || "undefined"==vo[3]){
    			return false;
    		}
	        var parm = "group_id=" + vo[0] + "&hos_id=" + vo[1] 
					+ " &copy_code=" + vo[2] + "&ass_card_no=" + vo[3] ;
			parent.$.ligerDialog.open({
				 url: 'hrp/ass/asscard/assCardUpdatePage.do?isCheck=false&'+parm,
				 width: $(window).width(),
	             height: $(window).height(),
	             title: '卡片信息',
	             modal: true,
	             frameNameObj :window.name
			
				
			});
        }
      //入库单信息查看
    	function openAssin(obj) {
    		var vo = obj.split("|");
    		if("null"==vo[3] || "undefined"==vo[3]){
    			return false;
    			
    		}
    		var url = '';
    		var ass_natur = vo[4]
    		if(ass_natur=='01'){
    			
    		}else if(ass_natur=='02'){
    			url = 'hrp/ass/assspecial/assin/assInMainSpecialUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='03'){
    			url= 'hrp/ass/assgeneral/assin/assInMainGeneralUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='04'){
    			url= 'hrp/ass/assother/assin/assInMainOtherUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='05'){
    			url='hrp/ass/assinassets/assin/assInMainInassetsUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='06'){
    			
    		}
    		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
    				+ " copy_code=" + vo[2] + "&" + "ass_in_no=" + vo[3];

    		parent.$.etDialog.open({
                url: url+parm ,
                width: $(window).width(),
                height: $(window).height(),
                title: '资产入库',
                modal: true,
                frameNameObj :window.name
            }); 
    	}
    	//退货单信息查看
    	function openAssBack(obj) {
    		var vo = obj.split("|");
    		if("null"==vo[3] || "undefined"==vo[3]){
    			return false;
    			
    		}
    		var url = '';
    		var ass_natur = vo[4]
    		if(ass_natur=='01'){
    			url='hrp/ass/asshouse/assback/assBackHouseUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='02'){
    			url = 'hrp/ass/assspecial/assback/assBackSpecialUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='03'){
    			url= 'hrp/ass/assgeneral/assback/assBackGeneralUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='04'){
    			url= 'hrp/ass/assother/assback/assBackOtherUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='05'){
    			url='hrp/ass/assinassets/assback/assBackInassetsUpdatePage.do?isCheck=false&'
    		}else if(ass_natur=='06'){
    			url='hrp/ass/assland/assback/assBackLandUpdatePage.do?isCheck=false&'
    		}
    		var parm = "group_id=" + vo[0] + "&hos_id=" + vo[1] 
    				+ "&copy_code=" + vo[2] + "&" + "ass_back_no=" + vo[3];

    		parent.$.etDialog.open({
                url: url+parm ,
                width: $(window).width(),
                height: $(window).height(),
                title: '资产退货',
                modal: true,
                frameNameObj :window.name
            }); 
    	}
    	//合同信息
    	function openPactInfo(pact_code) {
         	parent.$.etDialog.open({
                  url: 'hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit.do?isCheck=false&pact_code='+pact_code,
                  width: $(window).width(),
                  height: $(window).height(),
                  title: '合同信息',
                  modal: true,
                  frameNameObj :window.name
              });
         };
        var print = function(){
           	if(grid.getAllData()==null){
           		$.etDialog.error("资产采购明细表");
       			return;
       		}
           	var printPara={
                   	title: "履约银行保函",//标题
                  	columns: JSON.stringify(grid.getPrintColumns()),//表头
               		class_name:"com.chd.hrp.pac.serviceImpl.fkht.execute.PactAssetPurchaseServiceImpl",
   					method_name:"queryPactAssetPurchaseDetailFKHTPrint",
   					bean_name:"pactAssetPurchaseService",
   					start_date: startpicker.getValue() ,
   					end_date: endpicker.getValue() ,
   	                pact_type_code: $("#pact_type_code").val() ,
   	                sup_no: sup_no.getValue() ,
   	                in_start_date: in_start_date.getValue() ,
   	                in_end_date: in_end_date.getValue() ,
   	                pact_code: $("#pact_code").val() ,
   	                pact_name: $("#pact_name").val() ,
   	                ass_type_id: $("#ass_type").val() ,
   	                ass_name: ass_type.getValue() ,
   	             	use_state: use_state.getValue() 
               };
               officeGridPrint(printPara);
           };
        //跳转修改页面
        
        $(function () {
            initfrom();
            initGrid();
            initSelect();
            if(pact_code){
            	$("#pact_code").val(pact_code);
            	$("#pact_code").attr("disabled",true);
            }else{
            	$("#pact_code").attr("disabled",false);
            }
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
    		in_start_date = $("#in_start_date").etDatepicker({
    			defaultDate: "yyyy-fm-fd",
    		  	onChange: function (date) {
    		  		var end = in_end_date.getValue();
    		  		if(end < date){
    		  			in_end_date.setValue(end);
    		  		}
    		  	}
    		});
    		in_end_date = $("#in_end_date").etDatepicker({
    			defaultDate: true,
    		  	onChange: function (date) {
    		  		var start = in_start_date.getValue();
    		  		if(start > date){
    		  			in_end_date.setValue(start);
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
          <td class="label" style="width: 100px;">入库日期：</td>
            <td class="ipt" style="width: 220px;">
                <input id="in_start_date" type="text" style="width: 100px"/>至 <input id="in_end_date" type="text" style="width: 100px"/>
            </td>
            <td class="label" style="width: 100px;">合同编号：</td>
            <td class="ipt"><input id="pact_code" type="text" /> </td>
            <td class="label" style="width: 100px;">合同名称：</td>
            <td class="ipt"><input id="pact_name" type="text" /> </td>
        </tr>
        <tr>
         	<td class="label" style="width: 100px;">资产类别：</td>
            <td class="ipt"><input id="ass_type" type="text" style="width: 180px"/></td>
        	<td class="label" style="width: 100px;">资产名称：</td>
            <td class="ipt"><input id="ass_name" type="text" /></td>
        	<td class="label" style="width: 100px;">使用状态：</td>
            <td class="ipt"><input id="use_state" type="text" style="width: 180px"/> </td>
        </tr>
    </table>
    <div id="mainGrid"></div>
</body>

</html>
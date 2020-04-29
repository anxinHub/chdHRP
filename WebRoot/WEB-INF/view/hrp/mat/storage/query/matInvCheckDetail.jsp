<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day+"日";
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		var begin_date = $("#begin_date").val();
        var end_date  = $("#end_date").val();
        
        if(begin_date == ''){
        	$.ligerDialog.warn('开始期间不能为空 ');
        	return ;
        }
        
        if(end_date == ''){
        	$.ligerDialog.warn('结束期间不能为空 ');
        	return ;
        }
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : begin_date});
		grid.options.parms.push({name : 'end_date',value : end_date});
		grid.options.parms.push({
			name : 'inv_id',
			value : $("#inv_id").val()});
		grid.options.parms.push({
			name : 'mat_type_id',
			value : liger.get("mat_type_code").getValue().split(",")[0]});
		grid.options.parms.push({
			name : 'store_id',
			value :  liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({
			name : 'in_no',
			value : $("#in_no").val()});
		grid.options.parms.push({
			name : 'inv_model',
			value : $("#inv_model").val()});
		grid.options.parms.push({
			name : 'sup_name',
			value : $("#sup_name").val()});
		
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
			    {
					display: '日期', name: 'confirm_date', align: 'left', width: '75', formatter: "yyyy-MM-dd"
				}, {
					display: '单号', name: 'in_no', align: 'left', width: '150',
					render : function(rowdata,rowindex, value) {
						return '<a href=javascript:update_open("'
						+ rowdata.group_id + ','
						+ rowdata.hos_id + ','
						+ rowdata.copy_code + ','
						+ rowdata.in_id + '")>'
						+ rowdata.in_no + '</a>';
					}
			    }, {
					display: '材料名称', name: 'inv_name', align: 'left', width: '150'
			    }, { 
		 			display: '规格', name: 'inv_model', align: 'left', width: '50'
		 		}, { 
		 			display: '单位', name: 'unit_name', align: 'left', width: '50'
		 		}, { 
		 			display: '数量', name: 'amount', align: 'right', width: '50',formatter:'0.00'
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'right',  width: '150',
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'right', width: '150',
		 			
		 		},{ 
		 			display: '注册证号', name: 'cert_code', align: 'left', width: '100'
		 		},{ 
		 			display: '批号', name: 'batch_no', align: 'left', width: '100'
		 		}, { 
		 			display: '有效期', name: 'inva_date', align: 'left', width: '75', formatter: "yyyy-MM-dd"
		 		}, { 
		 			display: '出厂检验合格单', name: 'has_fac_insp_cert', align: 'left', width: '100'
		 		}, { 
		 			display: '验收温度', name: 'temperature', align: 'left', width: '75'
		 		}, { 
		 			display: '包装情况', name: 'is_cov_good', align: 'left', width: '75'
		 		}, { 
		 			display: '外观质量', name: 'is_appe_comp', align: 'left', width: '75'
		 		}, { 
		 			display: '验收结果', name: 'check_result', align: 'left', width: '75',
		 			render : function(rowdata,rowindex, value) {
			 			  if(value!=null){
			 			  	return " "+value+" ";
			 			  }else{
			 			  	return " ";
			 			  }
					}
		 		}, { 
		 			display: '验收人', name: 'user_name', align: 'left', width: '100'
		 		}, { 
		 			display: '备注', name: 'note', align: 'left', width: '100'
		 		}, { 
		 			display: '物资类别', name: 'mat_type_name', align: 'left', width: '100'
		 		} ],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInvCheckDetail.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text:'打印', id:'print', click: print,icon:'print'},
				{ line:true}

			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>入库日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="材料验收查询表";
    }
    
    function update_open(obj) {
		var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "in_id=" + vo[3] + "&"
				+ "in_no=" + vo[4];
		parent.$.ligerDialog.open({
			title : '入库单查看',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/query/matInvCheckDetailByIdPage.do?isCheck=false&'+ paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', print);
	}
 
	//打印
	function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
	if(liger.get("store_code").getValue()== ""){ 
    		
    		var heads={
            		"isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"统计年月："},
        	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
        	          ]
    	}
    		}else {
    			
    			var heads={
    	        		"isAuto":true,//系统默认，页眉显示页码
    	        		"rows": [
    	    	          {"cell":0,"value":"统计年月："},
    	    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
    	    	          {"cell":3,"value":"仓库："},
    	    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""}
    	        	]}; 
    		}
    	
    	
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} , 
			]
		}; 
    	var printPara={
          		title: "材料验收查询表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatInvCheckService",
       			method_name: "queryMatInvCheckDetailPrint",
       			bean_name: "matInvCheckService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);


   		
    }
   
    function loadDict(){
    	$("#begin_date").ligerTextBox({width:90});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:90});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
		//字典下拉框
/* 		autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,"",false);
		autocomplete("#mat_type_code", "../../queryMatTypeByRead.do?isCheck=false", "id", "text", true, true, '',false); */
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false);
		$("#store_code").ligerTextBox({ width: 200 });
		$("#mat_type_code").ligerTextBox({ width: 200 });
		$("#sup_name").ligerTextBox({ width: 200 });
		$("#in_no").ligerTextBox({ width: 200 });
		$("#inv_id").ligerTextBox({ width: 200 });
		$("#inv_model").ligerTextBox({ width: 200 });
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
            
            <td align="right" class="l-table-edit-td" width="10%">仓&nbsp;&nbsp;库：</td>
            <td align="left" class="l-table-edit-td" width="20%">
           		<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">物资类别：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr> 
        
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">供应商：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_name" type="text" id="sup_name" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        	<td align="right" class="l-table-edit-td"  width="10%">入库单号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">材料名称：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_id" type="text" id="inv_id" ltype="text" validate="{required:false}" />
            </td>
        </tr>
        <tr>
        	
	        <td align="right" class="l-table-edit-td" width="10%">规&nbsp;&nbsp;格：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false}" />
            </td>
        </tr>
        
	</table>
	<div id="maingrid"></div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			 state:function(value){//数量
				
				if(value==1){
					return "未审核";
				}else if (value == 2){
					return "已审核";
				}else if (value == 3){
					return "已完成";
				} 
			} 
	}; 
    
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
        
		loadHotkeys();
		query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name:'create_date_start',value:$("#create_date_start").val()}); 
		grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()}); 
		grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]}); 
		//grid.options.parms.push({name:'store_no',value:liger.get("store_id").getValue().split(",")[1]}); 
		grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue()}); 
		grid.options.parms.push({
			name : 'check_code',
			value : $("#check_code").val()
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    
    //检查盘盈入库,盘亏入库是否删除
    //删除部分,删除对应的关系表
    //入库出库都不存在时修改盘点的完成状态
    function check_in_out(){
var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择查验的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state != 3){nos = nos + this.check_code + "<br>";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id + "@" +
								this.in_id + "@" +
								this.out_id+ "@" +
								this.check_code + "@" +
								this.in_no + "@" +
								this.out_no
								)
			});
			if(nos != ""){
				$.ligerDialog.error("查验失败！"+nos+"单据不是完成状态");
				return;
			}
			ajaxJsonObjectByUrl("checkInOut.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	                     { display: '盘点单号', name: 'check_code', align: 'left',width : 130,
	                    	 render : function(rowdata, rowindex, value) {
	     						return '<a href=javascript:openUpdate("' 
	     							+ rowdata.group_id
	     							+ ',' + rowdata.hos_id 
	     							+ ',' + rowdata.copy_code
	     							+ ',' + rowdata.check_id
	     							+ '")>'+rowdata.check_code+'</a>';
	     					 }	 
	                     },
	                     { display: '摘要', name: 'brif', align: 'left',width : 150},
	                     { display: '仓库', name: 'store_name', align: 'left',width : 120},
	                     { display: '经手人', name: 'emp_name', align: 'left',width : 100},
	                     { display: '盘点科室', name: 'dept_name', align: 'left',width : 100},
	                     { display: '制单日期', name: 'create_date', align: 'left',width : 100},
	                     { display: '制单人', name: 'maker_user_name', align: 'left',width : 100},
	                     { display: '审核日期', name: 'check_date', align: 'left',width : 100},
	                     { display: '审核人', name: 'checker_user_name', align: 'left',width : 100},
	                     { display: '状态', name: 'state', align: 'left',render : fieldTypeRender,width : 80},
	                     { display: '盘盈单号', name: 'in_no', align: 'left',width : 130,
	                    	 render : function(rowdata, rowindex, value) {
	                    		 var result = value == '' || value == null ? ''  :value ;
		     						return "<a href=javascript:openInMain('"
		     							+ rowdata.group_id
		     							+ "," + rowdata.hos_id 
		     							+ "," + rowdata.copy_code
		     							+ "," + rowdata.in_id
		     							+ "')>"+ result +"</a>";
		     				}	
	                     },
	                     { display: '盘亏单号', name: 'out_no', align: 'left',width : 150,
	                    	 render : function(rowdata, rowindex, value) {
	                    		 var result = value == '' || value == null ? ''  :value ;
		     						return "<a href=javascript:openOutMain('"
		     							+ rowdata.group_id
		     							+ "," + rowdata.hos_id 
		     							+ "," + rowdata.copy_code
		     							+ "," + rowdata.out_id
		     							+ "," + rowdata.store_id
		     							+ "')>"+ result +"</a>";
		     				}			 
	                     }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatCheckMain.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     					{ text: '查询', id:'search', click: query,icon:'search' },
                     					{ line:true },
				    					{ text: '添加', id:'add', click: add_open, icon:'add' },
										{ line:true },
										{ text: '删除', id:'delete', click: remove,icon:'delete' },
				    	                { line:true },
										{ text: '审核', id:'audit', click: audit,icon:'audit' },
										{ line:true },
										{ text: '消审', id:'unaudit', click: unAudit,icon:'unaudit' },
										{ line:true },
										{ text: '生成出入库单', id:'createInOut', click: createInOut,icon:'create' },
										{ line:true },
										{ text: '批量打印（<u>P</u>）', id:'print', click: print1, icon:'print' },
										{ line:true } ,
										{ text: '模板设置', id:'printSet', click: printSet1, icon:'settings' },
										{ line:true },
										{ text: '校验选中盘点出入库单可用状态', id:'check_in_out', click: check_in_out },
										]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "," + 
								rowdata.hos_id   + "," + 
								rowdata.copy_code   + "," + 
								rowdata.check_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //字段类型渲染器
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = matCheckMain_state.Rows.length; i < l; i++){
            var o = matCheckMain_state.Rows[i];
            if (o.id == r.state){
            	return o.text;
            }
        }
        return "未确认";
    }
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>制单日期："+$("#create_date_start").val() +" 至  "+ $("#create_date_end").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="库存盘点";
    }
    
    
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '盘点单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/check/matCheckMainAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
    	
    function remove(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要删除的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state > 1){nos = nos + this.check_code + ",";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id
								)
			});
			if(nos != ""){
				$.ligerDialog.error("删除失败！"+nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatCheckMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function audit(){
		
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要审核的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state != 1){nos = nos + this.check_code + "<br>";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id
								)
			});
			if(nos != ""){
				$.ligerDialog.error("审核失败！"+nos+"单据不是未审核状态");
				return;
			}
			$.ligerDialog.confirm('确定审核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditMatCheckMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function unAudit(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要消审的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state != 2){nos = nos + this.check_code + "<br>";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id
								)
			});
			if(nos != ""){
				$.ligerDialog.error("消审失败！"+nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定消审?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unAuditMatCheckMain.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    function createInOut(){

		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要生成的数据');
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state != 2){nos = nos + this.check_code + "<br>";}
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.check_id
								)
			});
			if(nos != ""){
				$.ligerDialog.error("生成出入库单失败！"+nos+"单据不是审核状态");
				return;
			}
			$.ligerDialog.confirm('确定生成出入库单?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("createInOut.do", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function openUpdate(obj) {
		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ " copy_code=" + voStr[2].toString() + "&" 
			+ "check_id=" + voStr[3].toString();
		parent.$.ligerDialog.open({
			title: '盘点单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/check/matCheckMainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
	//打开盘盈单
	function openInMain(obj){
		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ " copy_code=" + voStr[2].toString() + "&" 
			+ "in_id=" + voStr[3].toString();
		
		parent.$.ligerDialog.open({
			title: '盘盈单',
			height: $(window).height(),
			width: $(window).width(),
			//这个url可以对单据进行审核确认操作
			url: 'hrp/mat/storage/in/updatePage.do?isCheck=false&' + paras.toString(),
			//这个url只有查看权限
			//url: 'hrp/mat/storage/check/matStorageInPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
	//打开盘亏单
	function openOutMain(obj){
		var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ " copy_code=" + voStr[2].toString() + "&" 
			+ "out_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString();
		
		parent.$.ligerDialog.open({
			title: '盘亏单',
			height: $(window).height(),
			width: $(window).width(),
			//这个url可以对单据进行审核确认操作
			url: 'hrp/mat/storage/out/outlibrary/matOutMainUpdatePage.do?isCheck=false&' + paras.toString(),
			//这个url只有查看权限
			//url: 'hrp/mat/storage/check/matStorageOutPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
	}
	
	function loadDict() {
		//字典下拉框
		//autocompleteAsync("#store_id", "../../queryMatStore.do?isCheck=false", "id","text", true, true, "", true, false, '180');
		autocompleteAsync("#store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id","text", true, true, {read_or_write:1}, true, false, '180');
		$("#create_date_start").ligerTextBox({width : 100});
        autodate("#create_date_start", "yyyy-mm-dd", "month_first");
		$("#create_date_end").ligerTextBox({width : 100});
        autodate("#create_date_end", "yyyy-mm-dd", "month_last");
		autocomplete("#emp_id", "../../queryMatEmp.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		autoCompleteByData("#state", matCheckMain_state.Rows, "id", "text",true, true);
		$("#check_code").ligerTextBox({width : 239});
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', print);
		hotkeys('I', imp);

	}
	
	function imp() {

		var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'matCheckMainImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}
	
	
	
	  //打印模板设置
	  
	  function printSet1(){
		  
		  var useId=0;//统一打印
			if('${p04046 }'==1){
				//按用户打印
				useId='${sessionScope.user_id }';
			}else if('${p04046 }'==2){
				//按仓库打印
				if(liger.get("store_code").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("store_code").getValue().split(",")[0];
			}
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${sessionScope.user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"04027",use_id:useId});
	}
	  
	  
   
    //打印
    function print1(){
	
	 var useId=0;//统一打印
		if('${p04046 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p04046 }'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
 	//if($("#create_date_b").val())
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var check_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				check_id  += this.check_id+","
					
			});
	
	var para={
			template_code:'04027',
			class_name:"com.chd.hrp.mat.serviceImpl.storage.check.MatCheckMainServiceImpl",
			method_name:"queryMatCheckByPrintDY",
			paraId :check_id.substring(0,check_id.length-1),
			isPreview:true,//预览窗口，传绝对路径
			use_id:useId,
			p_num:1
	};
	
	officeFormPrint(para);
	
}
    
    
    function print(){
    	
    	
    	 
    	 var useId=0;//统一打印
 		if('${p04027 }'==1){
 			//按用户打印
 			useId='${sessionScope.user_id }';
 		}
    	//if($("#create_date_b").val())
 		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			
			var check_id ="" ;
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 2){
					in_nos = in_nos + this.in_no + "<br>";
				}
				
				check_id  += this.check_id+","
					
			});
		}
// 			if(in_nos != ""){
// 				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+in_nos);
// 				return;
// 			} 
			
			
			 var para={
	    			paraId :check_id.substring(0,check_id.length-1) ,
	    			
	    			template_code:'04027',
	    			isPrintCount:false,//更新打印次数
	    			isPreview:true,//预览窗口，传绝对路径
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
		 	
			//alert(JSON.stringify(para));
			
	    	printTemplate("hrp/mat/storage/check/queryMatCheckByPrintTemlate.do?isCheck=false",para);
	    	
		}
    	
    }

	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "MatCheckMain.xls", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			check_id : $("#check_id").val(),
			check_code : $("#check_code").val(),
			store_id : $("#store_id").val(),
			store_no : $("#store_no").val(),
			dept_id : $("#dept_id").val(),
			dept_no : $("#dept_no").val(),
			check_date : $("#check_date").val(),
			emp_id : $("#emp_id").val(),
			maker : $("#maker").val(),
			checker : $("#checker").val(),
			state : $("#state").val(),
			brif : $("#brif").val()
		};
		ajaxJsonObjectByUrl("queryMatCheckMain.do", exportPara,
				function(responseData) {
					$.each(responseData.Rows, function(idx, item) {
						var trHtml = "<tr>";
						trHtml += "<td>" + item.check_id + "</td>";
						trHtml += "<td>" + item.check_code + "</td>";
						trHtml += "<td>" + item.store_id + "</td>";
						trHtml += "<td>" + item.store_no + "</td>";
						trHtml += "<td>" + item.dept_id + "</td>";
						trHtml += "<td>" + item.dept_no + "</td>";
						trHtml += "<td>" + item.check_date + "</td>";
						trHtml += "<td>" + item.emp_id + "</td>";
						trHtml += "<td>" + item.maker + "</td>";
						trHtml += "<td>" + item.checker + "</td>";
						trHtml += "<td>" + item.state + "</td>";
						trHtml += "<td>" + item.brif + "</td>";
						trHtml += "</tr>";
						$("#resultPrint > table > tbody").empty();
						$("#resultPrint > table > tbody").append(trHtml);
					});
					manager.close();
					lodopExportExcel("resultPrint", "导出Excel",
							"MatCheckMain.xls", true);
				}, true, manager);
		return;
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td" >
				<table>
					<tr>
						<td align="left" >
							<input class="Wdate" name="create_date_start" id="create_date_start" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="create_date_end" id="create_date_end" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
	            	</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓  库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" /></td>
			           
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">状   态：</td>
			<td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" /></td>
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td" >
				单据号：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="check_code" type="text" id="check_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">经 手 人：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" /></td>
			
			
			<td></td>
			<td></td>
		</tr> 
	</table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">checkId</th>	
                <th width="200">checkCode</th>	
                <th width="200">storeId</th>	
                <th width="200">storeNo</th>	
                <th width="200">deptId</th>	
                <th width="200">deptNo</th>	
                <th width="200">checkDate</th>	
                <th width="200">empId</th>	
                <th width="200">maker</th>	
                <th width="200">checker</th>	
                <th width="200">state</th>	
                <th width="200">brif</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>

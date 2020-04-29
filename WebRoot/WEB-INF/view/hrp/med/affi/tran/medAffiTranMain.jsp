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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	$("html body").height("100%");
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
		grid.options.parms.push({name:'tran_date_start',value:$("#tran_date_start").val()}); 
		grid.options.parms.push({name:'tran_date_end',value:$("#tran_date_end").val()}); 
		
		grid.options.parms.push({name:'out_store_id',value:liger.get("out_store_id").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name:'in_store_id',value:liger.get("in_store_id").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name:'in_hos_id',value:liger.get("in_hos_id").getValue().split(",")[0]}); 

		grid.options.parms.push({name:'out_hos_id',value:liger.get("out_hos_id").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name:'check_date_start',value:$("#out_date_start").val()}); 
		grid.options.parms.push({name:'check_date_end',value:$("#out_date_end").val()}); 
		
		grid.options.parms.push({name:'confirm_date_start',value:$("#in_date_start").val()}); 
		grid.options.parms.push({name:'confirm_date_end',value:$("#in_date_end").val()});
		
		grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({name:'tran_type',value:liger.get("tran_type").getValue()}); 
		grid.options.parms.push({name:'bus_type',value:liger.get("bus_type").getValue()}); 
		grid.options.parms.push({
			name : 'tran_no',
			value : $("#tran_no").val()
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '单据号', name: 'tran_no', width : 130,  align: 'left',
						 render : function(rowdata, rowindex, value) {
								return '<a href=javascript:openUpdate("' 
									+ rowdata.group_id
									+ ',' + rowdata.hos_id 
									+ ',' + rowdata.copy_code
									+ ',' + rowdata.tran_id
									+ ',' + rowdata.out_store_id
									+ '")>'+rowdata.tran_no+'</a>';
							}		 
					},
					{ display: '编制日期', name: 'tran_date', width : 100, align: 'left'},
					{ display: '调出单位', name: 'out_hos_name', width : 100, align: 'left'},
					{ display: '调出仓库', name: 'out_store_name', width : 100, align: 'left'},
					{ display: '调拨出库单', name: 'out_no', width : 150, align: 'left',
						 render : function(rowdata, rowindex, value) {
								return '<a href=javascript:openOutUpdate("' 
									+ rowdata.group_id
									+ ',' + rowdata.hos_id 
									+ ',' + rowdata.copy_code
									+ ',' + rowdata.out_id
									+ ',' + rowdata.out_store_id
									+ '")>'+rowdata.out_no+'</a>';
							}		
					},
					{ display: '调入单位', name: 'in_hos_name', width : 100, align: 'left'},
					{ display: '调入仓库', name: 'in_store_name', width : 100, align: 'left'},
					{ display: '调拨入库单', name: 'in_no', width : 150, align: 'left',
						 render : function(rowdata, rowindex, value) {
								return '<a href=javascript:openInUpdate("' 
									+ rowdata.group_id
									+ ',' + rowdata.hos_id 
									+ ',' + rowdata.copy_code
									+ ',' + rowdata.in_id
									+ '")>'+rowdata.in_no+'</a>';
							}		
					},
					{ display: '金额', name: 'amount_money', width : 100, align: 'right',
						render : function(rowdata, rowindex, value) {
	     					return value == null ? "" : formatNumber(value, '${p08005 }', 1);
	     				}
					},
					{ display: '制单人', name: 'maker_name', width : 100, align: 'left'},
					{ display: '调出确认人', name: 'checker_name', width : 100, align: 'left'},
					{ display: '调出日期', name: 'check_date', width : 100, align: 'left'},
					{ display: '调入确认人', name: 'confirmer_name', width : 100, align: 'left'},
					{ display: '调入日期', name: 'confirm_date', width : 100, align: 'left'},
					{ display: '状态', name: 'state', width : 100, align: 'left',render: fieldTypeRender}
					],
					dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAffiTranMain.do?isCheck=true',
					width: '100%', height: '100%', checkbox: true,rownumbers:true,isScroll:true,
					delayLoad: true,//初始化不加载，默认false
					selectRowButtonOnly:true,//heightDiff: -10,
                     
                     toolbar: { items: [
										{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     					{ line:true },
				    					{ text: '添加（<u>N</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '调出确认（<u>O</u>）', id:'outConfirm', click: outConfirm,icon:'audit' },
										{ line:true },
										{ text: '取消调出确认（<u>U</u>）', id:'unOutConfirm', click: unOutConfirm,icon:'unaudit' },
										{ line:true },
										{ text: '调入确认（<u>I</u>）', id:'inConfirm', click: inConfirm,icon:'account' },
										{ line:true },
										{ text: '冲账（<u>C</u>）', id:'offset', click: balanceConfirm,icon:'offset' },
										{ line:true }, 
										{ text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
										{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
										{ line:true } ,
										{ text: '批量打印（<u>P</u>）', id:'print', click: print, icon:'print' }
										
				    				]}
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  	//字段类型渲染器
    function fieldTypeRender(r, i, value){
        for (var i = 0, l = medTranMain_state.Rows.length; i < l; i++){
            var o = medTranMain_state.Rows[i];
            if (o.id == value) return o.text;
        }
        return "未确认";
    }
  	
  	//添加
    function add_open(){ 
    	
    	parent.$.ligerDialog.open({
			title: '代销药品调拨单添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/tran/medAffiTranMainAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true, top : 1,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }
  	
    //删除	
    function remove(){
		var data = gridManager.getCheckedRows();		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要删除的数据');
			return;
		} else {
			var ParamVo = [];
			var nos = "";
			$(data).each(
					function() {
						if(this.state > 1){nos = nos + this.tran_no + ",";}
						ParamVo.push(
								this.group_id + "@"+ 
								this.hos_id + "@" +
								this.copy_code + "@" +
								this.tran_id
								)
			});
			 if(nos != ""){
 				$.ligerDialog.error("删除失败！"+nos+"单据不是未审核状态");
 				return;
 			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMedAffiTranMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    //调出确认
    function outConfirm(){
		var data = gridManager.getCheckedRows();		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要调出确认的数据');
			return;
		} else {
			var ParamVo = [];
			$(data).each(function() {
					ParamVo.push(
						this.hos_id + "@" + 
						this.group_id + "@"+ 
						this.copy_code + "@" +
						this.tran_id 
					)
			});
			$.ligerDialog.confirm('确定调出确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("outConfirmMedAffiTranMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
    //取消确认
    function unOutConfirm(){
		var data = gridManager.getCheckedRows();		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要取消调出确认的数据');
			return;
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.tran_id 
								)
			});
			$.ligerDialog.confirm('确定取消调出确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unOutConfirmMedAffiTranMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    //调入确认
    function inConfirm(){
		var data = gridManager.getCheckedRows();		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要调入确认的数据');
			return;
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.tran_id 
								)
			});
			$.ligerDialog.confirm('确定调入确认?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("inConfirmMedAffiTranMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    //取消调入确认
    function balanceConfirm(){
		var data = gridManager.getCheckedRows();
		
		if (data.length == 0) {
			$.ligerDialog.error('请选择要冲账的数据');
			return;
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(
								this.hos_id + "@" + 
								this.group_id + "@"+ 
								this.copy_code + "@" +
								this.tran_id
								)
			});
			$.ligerDialog.confirm('确定冲账?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("balanceConfirmMedAffiTranMain.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
    
	//更新
	function openUpdate(obj) {
		var voStr = obj.split(",");
		var parm = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ " copy_code=" + voStr[2].toString() + "&" 
			+ "tran_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString();
		parent.$.ligerDialog.open({
			title: '代销药品调拨单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/tran/medAffiTranMainUpdatePage.do?isCheck=false&' + parm.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 1,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
    //出库单修改
    function openOutUpdate(obj){		
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"out_id="+vo[3] + "&" +  
			"store_id=" + vo[4];
		parent.$.ligerDialog.open({
			title: '代销药品出库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/out/medAffiOutCommonUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, initShowMax:true,isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    }
    //入库单修改
    function openInUpdate(obj){		
		var vo = obj.split(",");
		var paras ="group_id="+vo[0] +"&hos_id="+vo[1] +"&copy_code="+vo[2] +"&in_id="+vo[3] ;
		parent.$.ligerDialog.open({
			title: '代销药品入库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/affi/in/medAffiInCommonUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, initShowMax:true,isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    }
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('N', add_open);
		hotkeys('D', remove);
		hotkeys('O', outConfirm);
		hotkeys('U', unOutConfirm);
		hotkeys('I', inConfirm);
		hotkeys('F', balanceConfirm);
		//hotkeys('B', downTemplate);
		//hotkeys('E', exportExcel);
		//hotkeys('P', printDate);
		hotkeys('M', imp);
	}
	
	function imp() {

		var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'medAffiTranMainImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}
	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "MedCheckMain.xls", true);
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
		ajaxJsonObjectByUrl("queryMedCheckMain.do", exportPara,
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
							"MedCheckMain.xls", true);
				}, true, manager);
		return;
	}
    
	  //打印模板设置
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${p08025 }'==1){
			//按用户打印
			useId='${sessionScope.user_id }';
		}else if('${p08025 }'==1){
			//按仓库打印
			if(liger.get("out_store_id").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("out_store_id").getValue().split(",")[0];
		}
    	/* 
    	parent.$.ligerDialog.open({url : 'hrp/med/storage/tran/medTranPrintSetPage.do?template_code=08015&use_id='+useId,
    		data:{}, height: $(parent).height(),width: $(parent).width(), title:'打印模板设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
    		}); */
    	officeFormTemplate({template_code:"08021",useId:useId});
    }

  //打印
    function print(flag){
    	 var useId=0;//统一打印
	 		if('${p08025 }'==1){
	 			//按用户打印
	 			useId='${sessionScope.user_id }';
	 		}else if('${p08025 }'==1){
				//按仓库打印
				if(liger.get("out_store_id").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("out_store_id").getValue().split(",")[0];
			}
	    	//if($("#create_date_b").val())
	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
				
				var tran_id ="" ;
				var in_nos = "";
				$(data).each(function (){		
					if(this.state != 2){
						in_nos = in_nos + this.in_no + "<br>";
					}
					
					tran_id  += this.tran_id+","
						
				});
			}
			 var para={
					 template_code:'08021',
						class_name:"com.chd.hrp.med.serviceImpl.affi.tran.MedAffiTranMainServiceImpl",
						method_name:"queryMedAffiTranPrintTemlateMain",
						isSetPrint:flag,//是否套打，默认非套打 
						/* isPreview:isPreview//是否预览，默认直接打印 */
						paraId :tran_id.substring(0,tran_id.length-1) ,
			    		use_id:useId,
			    		p_num:1
						
	    	}; 
		 	
			
			
			 officeFormPrint(para);
	    	
		
    	
    }
  	
	   
	
	function loadDict() {
		//字典下拉框
		autoCompleteByData("#bus_type", medTranMain_busType.Rows, "id", "text",true, true,'',false,false,'180');
		autoCompleteByData("#tran_type", medTranMain_tranType.Rows, "id", "text",true, true,'',true,false,'180');
		
		autocomplete("#in_store_id", "../../queryMedStore.do?isCheck=false", "id","text", true, true, {is_com : '1'}, false, false, '180');
		autocomplete("#out_store_id", "../../queryMedStore.do?isCheck=false", "id","text", true, true, {is_com : '1'}, false, false, '180');
		
		autocomplete("#in_hos_id", "../../queryMedHosInfoDict.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		autocomplete("#out_hos_id", "../../queryMedHosInfoDict.do?isCheck=false", "id","text", true, true, "", false, false, '180');
		
		autoCompleteByData("#state", medTranMain_state.Rows, "id", "text",true, true,'',false,false,'180');

		$("#tran_date_start").ligerTextBox({width : 100});
		$("#tran_date_end").ligerTextBox({width : 100});
		autodate("#tran_date_start", "yyyy-mm-dd", "month_first");
        autodate("#tran_date_end", "yyyy-mm-dd", "month_last");
		
		$("#out_date_start").ligerTextBox({width : 100});
		$("#out_date_end").ligerTextBox({width : 100});
		
		$("#in_date_start").ligerTextBox({width : 100});
		$("#in_date_end").ligerTextBox({width : 100});
		
		$("#tran_no").ligerTextBox({width : 223});
	}
	
	function changeTranType(){
		if(liger.get("tran_type").getValue() == 1){
			
			$("#out_hos_id").attr("readonly", true).ligerComboBox({width:180,disabled:true, cancelable: false});
			liger.get("out_hos_id").setValue("");
			liger.get("out_hos_id").setText("");
			$("#in_hos_id").attr("readonly", true).ligerComboBox({width:180,disabled:true, cancelable: false});
	        liger.get("in_hos_id").setValue("");
			liger.get("in_hos_id").setText("");
		}else{
			$("#out_hos_id").attr("readonly", false).ligerComboBox({width:180,disabled:false, cancelable: true});
			$("#in_hos_id").attr("readonly", false).ligerComboBox({width:180,disabled:false, cancelable: true});
		}
    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">编制日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="tran_date_start" type="text" id="tran_date_start" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至</td>
			            <td align="left"><input name="tran_date_end" type="text" id="tran_date_end" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类型：</td>
			<td align="left" class="l-table-edit-td" ><input name="bus_type" type="text" id="bus_type" ltype="text" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>调拨类型：</td>
			<td align="left" class="l-table-edit-td" ><input name="tran_type" type="text" id="tran_type" ltype="text" onChange="changeTranType();"/></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调出日期：</td>
			<td align="left" class="l-table-edit-td">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="out_date_start" type="text" id="out_date_start" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至</td>
			            <td align="left"><input name="out_date_end" type="text" id="out_date_end" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">调出单位：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="out_hos_id" type="text" id="out_hos_id" ltype="text" /></td>
			<td align="left"></td>

			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="10%">调出仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input name="out_store_id" type="text" id="out_store_id" ltype="text" /></td>
			<td align="left"></td>
		</tr>  
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调入日期：</td>
			<td align="left" class="l-table-edit-td">
				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="left"><input name="in_date_start" type="text" id="in_date_start" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left" class="l-table-edit-td">至</td>
			            <td align="left"><input name="in_date_end" type="text" id="in_date_end" ltype="text"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调入单位：</td>
			<td align="left" class="l-table-edit-td"><input name="in_hos_id" type="text" id="in_hos_id" ltype="text" /></td>
			<td align="left"></td>
			            
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">调入仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="in_store_id" type="text" id="in_store_id" ltype="text" /></td>
			<td align="left"></td>
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据号：</td>
			<td align="left" class="l-table-edit-td" ><input name="tran_no" type="text" id="tran_no" ltype="text" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">状   态：</td>
			<td align="left" class="l-table-edit-td" ><input name="state" type="text" id="state" ltype="text" /></td>
			<td align="left"></td>
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

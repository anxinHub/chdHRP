<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
String path = request.getContextPath(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script type="text/javascript">	
	var grid;
	var gridManager = null;
	var print_codes = [];
	var print_names = [];
	var files_name = {};  //已预览过的文件路径，格式为：{打印编码: 文件路径}
	var row_ids = {};
	var print;  //轮循标记
	
	var is_first = true;  //是否第一次打印
	var is_print = false;  //是否处于打印状态
	var row_id;  //记录行号用于修改状态
	var fileName = "";	  //文件名称(含路径)，用于打开或删除文件
	var retMap = JSON.parse("${map}".replace("{", "{\"").replace("}", "\"}").replace(/\=/g, "\":\"").replace(/\, /g,"\", \""));
	var gridData = JSON.parse(window.external.UserParams);
	
	$(function (){
		
		$("#layout1").ligerLayout({ leftWidth: 390, allowLeftResize: true });
		loadTool();//加载工具条
		loadHead();	//加载数据
	});
	
	//加载工具条
	var $tool;
	function loadTool(){
		$tool = $("#leftToolBar").ligerToolBar({
			items: [
   				{ text: '开始打印', id: 'start', click: startPrint, icon: 'print'},
   				{ line: true },
   				{ text: '结束打印', id: 'end', click: endPrint, icon: 'unaudit', disabled: true},
   				{ line: true },
   				{ text: '关闭', id: 'close', click: thisClose, icon: 'close'},
   				{ line: true }
			]
		});
	}

	//加载grid
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '编码', name: 'print_code', align: 'left', width: '90'
			}, { 
				display: '名称', name: 'print_name', align: 'left', width: '160'
			}, { 
				display: '操作', name: '', align: 'left', width: '45', 
				render: function(rowdata, rowindex, value){
					return "<a href='#' onclick='showFile(\""+rowdata.print_code+"\", \""+rowdata.print_name+"\")'>预览</a>"; 
				}
			}, { 
				display: '打印状态', name: 'print_state', align: 'left', width: '60'
			} ],
			data: gridData, width: '100%', height: '100%', usePager: false, rownumbers: true, 
			selectRowButtonOnly: true, heightDiff: 25,
		});
		
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//预览
	function showFile(p_code, p_name){
		var param = retMap;
		param.print_code = p_code; 
		param.print_name = p_name;
		
		var paraStr = "";
		
		if(files_name[p_code]){
			
			paraStr = "group_id=" + retMap.group_id + "&hos_id=" + retMap.hos_id 
				+ "&hos_copy=" + retMap.hos_copy + "&temp_file_name=" + files_name[p_code];
			
			$("#myOffice").attr("src", "printTableBatchPre.do?isCheck=false&" + paraStr);
			
		}else{
			//请求数据
			var resMsg = ajaxJsonObjectByUrl("printTableDataBatch.do?isCheck=false", param, function (responseData) {
				//记录文件地址
				files_name[p_code] = responseData.toFilePath;
				
				for (var key in responseData) {
					paraStr = paraStr + "&" + key + "=" + responseData[key];
				}
				$("#myOffice").attr("src", "printTableBatchPre.do?isCheck=false&" + paraStr);
				
			}, false);
		}
	}
	
	//开始打印
	function startPrint(){
		//改变按钮样式
		$tool.setDisabled("start");
		$tool.setEnabled("end");
		
		//第一次打印需要组装print_code
		if(is_first){
			var data = grid.getData();
		   	$.each(data, function(i, obj){
		   		print_codes[i] = obj.print_code;
		   		print_names[i] = obj.print_name;
		   		row_ids[obj.print_code] = i;
		   	});
		}
		//开启轮循任务30秒执行一次 
		print = self.setInterval("printData()",5000); 
		//第一次执行打印
		printData();
	}
	
	//打印
	function printData(){
		//判断是否处于打印中
		if(!is_print){
			if(print_codes.length > 0){
				is_print = true;
				row_id = row_ids[print_codes[0]];
				grid.updateCell("print_state", "打印中", row_id);
				
				if(files_name[print_codes[0]]){
					//打印数据
					fileName = files_name[print_codes[0]];
					openFile();
				}else{
					//请求数据
					var param = retMap;
					param.print_code = print_codes[0]; 
					param.print_name = print_names[0];
					
					var resMsg = ajaxJsonObjectByUrl("printTableDataBatch.do?isCheck=false", param, function (res) {
						//记录文件地址
						files_name[print_codes[0]] = res.toFilePath;
					
						//打印数据
						fileName = res.toFilePath;
						openFile();
					}, false);

					//status=200 表示ajax请求执行成功，返回信息中不包含error表示java程序执行成功
					if(resMsg.status != 200 || resMsg.responseText.indexOf("{\"error\":") > 0){
						grid.updateCell("print_state", "打印失败", row_id);
						endPrint(false);
					}
				}
				
			}else{
				myClearInterval();
			}
		}
	}
	
	//结束打印
	function endPrint(flag){
		//改变按钮样式
		$tool.setDisabled("end");
		$tool.setEnabled("start");
		/* $("#start").val("继续（C）");
		$("#start").removeAttr("disabled");
		$("#start").ligerButton({disabled: false}); */
		if(flag && row_id){
			grid.updateCell("print_state", "暂停打印", row_id);
		}
		myClearInterval();
	}
	
	//打开文件  临时文件路径  fileName
	var is_open = false;
	function openFile(){
		//更新下一行打印信息
		print_codes.shift();
		print_names.shift();
		if(print_codes.length > 0){
			grid.updateCell("print_state", "准备打印", row_id + 1);
		}
		//如果如果已打开文件需先关闭当前文件然后才能打开新文件
		if(is_open){
			document.getElementById("PageOfficeCtrl1").Close();
			//更新文件是否打开标记		
			is_open = false;
		}
		//打开新文件（只读模式）
		document.getElementById("PageOfficeCtrl1").ServerPage = "${serverPage}";//"../pageoffice/server.aspx"; //设置服务器页面
		document.getElementById("PageOfficeCtrl1").JsFunction_AfterDocumentOpened = "afterDocumentOpened()";
		document.getElementById("PageOfficeCtrl1").WebOpen( fileName, "xlsReadOnly", "");
	}
	
	//打开文档后的操作
	function afterDocumentOpened(){
		//更新文件是否打开标记		
		is_open = true;
		//直接打印
		document.getElementById("PageOfficeCtrl1").PrintOut();
		//更新打印状态
		is_print = false;
		//更新打印信息
		grid.updateCell("print_state", "已打印", row_id);
		//检查是否结束打印
		existsEnd();
	}
	
	//校验是否结束轮循任务
	function existsEnd(){
		if(print_codes.length == 0){
			//改变按钮样式
			$tool.setDisabled("end");
			myClearInterval();
			alert("打印完成");
		}
	}
	
	//结束打印轮循任务
	function myClearInterval(){
		print = window.clearInterval(print);
	}
	
	//关闭页面
	function thisClose(){
		myClearInterval();
		//判断是否生成临时文件
		if(JSON.stringify(files_name) != "{}"){
			//组装临时文件
			var fileList = [];
			for (var key in files_name) {
				fileList.push({temp_file_name: files_name[key]});
			}
			//删除临时文件
			ajaxJsonObjectByUrl("deleteFileBatch.do?isCheck=false",{fileList: JSON.stringify(fileList)}, function(){});
		}
		window.external.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1">
		<div position="left" title="打印列表">
			<div id="leftToolBar"></div>
			<div style="overflow-x:hidden;" id="treeDiv">
				<div id="maingrid"></div>
			</div>
		</div>

		<div position="center" id="centerReport">
			<div id="pageOfficeDiv" style="width:auto;display:none;margin-left:20px;">
				<!-- *********************pageoffice组件的使用 **************************-->
				<po:PageOfficeCtrl id="PageOfficeCtrl1" />
				<!-- *********************pageoffice组件的使用 **************************-->
			</div>
			<iframe id="myOffice" width="100%" height="100%" scrolling="auto" src=""></iframe>
		</div>
	</div>
	
	<script>
		//如果没有.show()，则WebOpen不可用
		$("#pageOfficeDiv").show();
	</script>
</body>
</html>

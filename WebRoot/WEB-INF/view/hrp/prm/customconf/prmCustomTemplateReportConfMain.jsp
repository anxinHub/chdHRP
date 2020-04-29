<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:auto;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>


<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">

    var tree;
    var treeManager =null;
    var saveFlag = true;
    
    
    
    
   
    //页面初始化
    $(function (){
    	
    	autocomplete("#template_kind_code","queryPrmTemplateKind.do?isCheck=false","id","text",true,true,'',true);
    	
    	//$("#template_kind_code").ligerTextBox({width : 160});
    	$("#template_code").ligerTextBox({width : 160});
    	$("#template_name").ligerTextBox({width : 160});
    	
    	loadTree();//加载树
    	toobar();//加载模板工具栏
    	
    	$("#tree").css("height", $(window).height()-60);
    	$("#layout1").ligerLayout({leftWidth:200});
    	
    	$("#clear").ligerButton({ width:90});
    	$("#delete").ligerButton({width:90});

    });
    
	
  	//模板工具栏
	function toobar(){
    	var obj = [];
    	obj.push({ text: '添加新模板（<u>A</u>）', id:'add', click: addNewTemplate,icon:'add' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '新增语句块（<u>N</u>）', id:'addSqlBlock', click: addSqlBlock,icon:'add' });
    	obj.push({ line:true }); 
    	
    	obj.push({ text: '保存模板（<u>S</u>）', id:'save', click: saveTemplateReportConf,icon:'save' });
    	obj.push({ line:true });
    	
    	obj.push({ text: '删除模板（<u>D</u>）', id:'delete', click: deleteHpmTemplateReportConf,icon:'delete' });
    	obj.push({ line:true });
    	
    	
    	$("#toptool").ligerToolBar({ items: obj});
    }
  	
	//键盘事件
	function loadHotkeys(){
		hotkeys('A',addNewTemplate);
		hotkeys('N',addSqlBlock);
		hotkeys('S',saveTemplateReportConf);
		hotkeys('D',deleteHpmTemplateReportConf);
	}
  	
	
	
	/** 
  		功能类型编码对照
		01:查询,02:添加,03:删除,
		04:生成,05:审核,06:消审,
		07:修改,08:导入,09:备注
	 */
  	
  	//保存模板
  	function saveTemplateReportConf(){
  		
		var template_code = $('#template_code').val();
		if(template_code == ''){
			$.ligerDialog.warn('模板编码不能为空');
  			return ; 
		}
			
  		//获取模板名称
  		var template_name = $('#template_name').val();
  		if(template_name == ''){
  			$.ligerDialog.warn('模板名称不能为空');
  			return ; 
  		}
  		
  		var template_kind_code = liger.get("template_kind_code").getValue();
  		if(template_kind_code == ''){
  			$.ligerDialog.warn('模板分类不能为空');
  			return ; 
  		}
  		
  		//获取id为editorBlock所有编辑区div
  		var divs = $("div[id^='editorBlock']");
  		
  		var typeMap = new HashMap();//用于存储功能类型,判断是否重复
  		var typeflag  = 0 ;//功能类型是否重复判断标记
  		var sqlflag = 0 ; //sql是否为空标记
  		
  		var paramVo = [];
  		$.each(divs,function(rowindex,rowdata){
  			
  			//获取当前div的id,用于获取div所在区域编码;
  			var divId = rowdata.id.toString();
  			
  			//获取当前div所在区域编码,用于获取当前区域选择的功能类型,获取当前区域填写的SQL
  			var divCode = divId.split('_')[1];
  			
  			//单选框名称,用于获取所选值
  			var templateTypeName = "template_type" + divCode;
  			var templateType = $('input:radio[name="'+templateTypeName+'"]:checked').val();
  			
  			
  			//判断功能类型是否选择重复
  			if(typeMap.get(templateType) != null){
  				typeflag++;
  				return ; 
  			}
  			typeMap.put(templateType,templateType);
  			
  			
  			//SQL区域id,用于获取录入的SQL
  			var templateSqlId = "template_sql" + divCode;
  			var templateSql = $('#'+templateSqlId).val();
  			
  			if(templateSql == ''){
  				sqlflag++;
  				return ; 
  			}
  			
  			
  			var data = {
  				template_type:templateType,
  				template_sql:templateSql
  			}
  			
  			paramVo.push(data);
  		});
  		
  		
  		if(typeflag > 0){//类型重复
  			$.ligerDialog.warn('功能类型重复.');
  			return ; 
  		}
  		
  		if(sqlflag > 0){//SQL为空判断
  			$.ligerDialog.warn('SQL不能为空.');
  			return ; 
  		}
  		
  		var para = {
  			saveFlag:saveFlag,
  			template_code : template_code,
  			template_name : template_name,
  			template_kind_code : template_kind_code,
  			allData : JSON.stringify(paramVo)
  		}
  		
  		ajaxJsonObjectByUrl("addPrmCustomTemplateReportConf.do",para,function (responseData){
  			if(responseData.state == "true"){
  				loadTree();
  			}
  		});
  	} 
  	
    
	//删除模板
	function deleteHpmTemplateReportConf(){
		
		if(template_code == "" || template_code == undefined){
			$.ligerDialog.warn('请选择模板');
			return ; 
		}
		
		var para = {
			template_code:$('#template_code').val()
		}
		
		$.ligerDialog.confirm('确定删除模板?',function(yes){
			if(yes){
				ajaxJsonObjectByUrl("deletePrmCustomTemplateReportConf.do",para,function (responseData){
            		if(responseData.state=="true"){
            			$("#template_code").ligerTextBox({disabled:false});//设置模板编码为启用
            			$('#template_code').val('');//清空模板编码
            			$('#template_name').val('');//清空模板名称
            			$("div[id^='editorBlock']").remove();//删除所有编辑区
            			addSqlBlock();//添加语句块
            			loadTree();//加载树
            		}
            	});
			}
		});
	}
	
    
	//节点选中事件 查询
	function onSelect(note){
		
		if(note.data.pid == -1){
			return ; 
		}
		
		saveFlag = false;//用于标记新增或修改
		var template_code = note.data.id;//记录节点的模板编码,作为删除后查询使用参数
		
		var formPara = {
			template_code : note.data.id
		}; 
		
		$('#template_code').val(note.data.id);
		$("#template_code").ligerTextBox({disabled:true});
		
		$('#template_name').val(note.data.text);
		
		ajaxJsonObjectByUrl("queryPrmCustomTemplateReportConf.do", formPara, function(responseData) {//查询模板
			
			$("div[id^='editorBlock']").remove();//删除所有编辑区
			var datas = responseData.Rows;//获取数据
			
			var setValueCount = 0 ;
			$.each(datas,function(rowindex,rowdata){
				addSqlBlock();//添加语句块
				//设置选中
				$(":radio[name='template_type" + divCount + "'][value='" + rowdata.template_type + "']").attr("checked", "checked");
				
				
				if(setValueCount == 0){//只赋值一次
					//给模板分类赋值
					liger.get("template_kind_code").setValue(rowdata.template_kind_code);
					liger.get("template_kind_code").setText(rowdata.template_kind_name);
					setValueCount++;
				}
				
				//给SQL赋值
				$('#template_sql' + divCount).val(rowdata.template_sql);
			});
		});
	}
     
   	//设置树形菜单 
	function loadTree(){
		ajaxJsonObjectByUrl("queryPrmCustomTemplateReportConfTree.do?isCheck=false",{},function(responseData) {
    		if (responseData != null) {
    			tree = $("#tree").ligerTree({
    				data : responseData.Rows,
    				checkbox : false,
    				idFieldName : 'id',
    				parentIDFieldName : 'pid',
    				onSelect: onSelect,
    				isExpand: 3,
    				nodeWidth:400
    			});
    						
    			treeManager = $("#tree").ligerGetTreeManager();
    			treeManager.collapseAll();
    		}
    	});
	}
   	
   	
	//删除语句块
  	function deleteSqlBlock(divElementTag){
  		
  		var divs = $("div[id^='editorBlock']");
  		if(divs.length == 1){
  			$.ligerDialog.warn('功能类型必须至少填写一个');
  			return ; 
  		}
  		
  		$('#editorBlock_'+divElementTag).remove();
  	}
	
  	
  	
  	//清空语句块
  	function clearSqlBlock(divElementTag){
  		
  		$('#template_sql'+divElementTag).val('');
  	}
   	
    
	//添加新模板
	function addNewTemplate(){
		saveFlag = true ;
		
		$("#template_code").ligerTextBox({disabled:false});//设置模板编码为启用
		
		$('#template_code').val('');//清空模板编码
		$('#template_name').val('');//清空模板名称
		$("div[id^='editorBlock']").remove();//删除所有编辑区
		addSqlBlock();//添加语句块
	}
	
	
	//新增语句块
  	var divCount = 1 ;//div所在区域编码,用于和其它div区分
  	function addSqlBlock(){
  		
  		divCount += 1;
  		
  		var divStr = new StringBuffer();
  		
  		divStr.append('<div id="editorBlock_' + divCount +'" style="overflow: auto;">');
  		
  		
  		divStr.append('<div id="detailtool' + divCount + '" style="background-color: #d6e7fc;">');
  		
  		
  		divStr.append('<table cellpadding="0" cellspacing="0" class="l-table-edit" >');
  		divStr.append('<tr>');
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;">功能类型：</td>');
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" checked="checked" name="template_type' + divCount + '" value="01" />查询</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="02" />添加</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="03" />删除</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="04" />生成</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="05" />审核</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="06" />消审</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="07" />修改</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="08" />导入</td>');
  		divStr.append('<td align="left"></td>');
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type' + divCount + '" value="09" />备注</td>');
  		divStr.append('<td align="left"></td>');
  		
  		
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;">');
  		divStr.append('<button id ="clear' + divCount + '" onclick="clearSqlBlock(' + divCount + ')"><b>清空</b></button>');
  		divStr.append('</td>');
  		divStr.append('<td align="left"></td>');
  		
  		
  		divStr.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;">');
  		divStr.append('<button id ="delete' + divCount + '" onclick="deleteSqlBlock(' + divCount + ')"><b>删除</b></button>');
  		divStr.append('</td>');
  		divStr.append('<td align="left"></td>');
  		divStr.append('</tr>');
  		divStr.append('</table>');
  		divStr.append('</div>');
  		
  		
  		divStr.append('<div>');
  		divStr.append('<textarea id="template_sql' + divCount +'" style="width:100%;height:200px;font-size: 17" ></textarea>');
  		divStr.append('</div>');
  		divStr.append('</div>');
  		
  		$('#allEditorBlock').append(divStr.toString());
  		
  		
  		$("#clear"+divCount).ligerButton({ width:90});
    	$("#delete"+divCount).ligerButton({width:90});
  	}
  	
</script>

</head>
</head>

<body style="padding: 0px; overflow: hidden;" >

	<div id="pageloading" class="l-loading" style="display: none"></div>
    <div id="layout1">
            <div position="left" title="自定义模板"><ul id="tree"></ul></div>
            
            
            <div position="center" style="overflow: auto;">
            <div id="toptool"></div>
            <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			   		 <tr>
			   		 	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板编码：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_code" type="text" id="template_code" ltype="text" /></td>
			            <td align="left"></td>
			   		 
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板名称：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_name" type="text" id="template_name" ltype="text" /></td>
			            <td align="left"></td>
			            
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板分类：</td>
			            <td align="left" class="l-table-edit-td"><input name="template_kind_code" type="text" id="template_kind_code" ltype="text" /></td>
			            <td align="left"></td>
			        </tr> 
			    </table>
			    
			    <div id="allEditorBlock" style="overflow: auto;height: 100%;">
				    <div id="editorBlock_1" style="overflow: auto;">
					    <div id="detailtool" style="background-color: #d6e7fc">
					    	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					    		<tr>
					    			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">功能类型：</td>
					    			
					    			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" checked="checked" value="01"/>查询</td>
						            <td align="left"></td>
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="02"/>添加</td>
						            <td align="left"></td>
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="03"/>删除</td>
						            <td align="left"></td>
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="04"/>生成</td>
						            <td align="left"></td>
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="05"/>审核</td>
						            <td align="left"></td>
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="06"/>消审</td>
						            <td align="left"></td>
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="07"/>修改</td>
						            <td align="left"></td>
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="08"/>导入</td>
						            <td align="left"></td>
						            
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input type="radio" name="template_type1" value="09"/>备注</td>
						            <td align="left"></td>
						            
						            
						            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						            	<button id ="clear" onclick="clearSqlBlock(1)"><b>清空</b></button>
						            </td>
						            <td align="left"></td>
						            
						             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						            	<button id ="delete" onclick="deleteSqlBlock('1')"><b>删除</b></button>
						            </td>
						            <td align="left"></td>
					    		</tr>
					    	</table>
					    </div>
					    <div>
		    				<textarea id="template_sql1" style="width:100%;height:200px;font-size: 17" ></textarea>
						</div>
						
				    </div>
			    </div>
            </div>  
        </div>
</body>
</html>

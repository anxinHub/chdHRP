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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;

    $(function (){

    	$("#layout1").ligerLayout({ leftWidth : 255, centerBottomHeight : $(window).height() - 215});
		$("#treeDiv").css("height", $(window).height() - 28);
    	
    	$("#toptoolbar").ligerToolBar({
    		
    		items: [{
    			text: '添加（<u>A</u>）', id:'add', icon:'add', click: add_mt
    		},{ line:true },{
    			text: '保存（<u>S</u>）',id:'save', icon:'save', click: save
    		},/*{ line:true },{
    			text: '变更（<u>C</u>）',id:'openChange', icon:'edit', click: openChange
    		},*/{ line:true },{
    			text: '删除（<u>D</u>）',id:'delete', icon:'delete', click: remove
    		},{ line:true },{ 
				text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }]
    	});
    	ajaxJsonObjectByUrl("getRules.do?isCheck=false", {}, function(responseData) {
			$("#font2").text(responseData.ruless);
        }, false);
    	loadTree();
        loadDict();//加载下拉框
		loadHotkeys();
		loadForm();
    });

    function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element){
	             if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] });
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function (){
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
		$("form").ligerForm();
    }

    /* 设置树形菜单 */
    function onSelect(note){
    	if(note){
        	query(note.data.id);
    	}
    }

    function loadTree(){
    	tree = $("#tree").css({"height": $(window).height()-60}).ligerTree({
    		url: "queryMatInstruTypeByTree.do?isCheck=false", 
    		parentIcon: null,
    		childIcon: null ,
    		checkbox : false,
    		idFieldName : 'code',
    		parentIDFieldName : 'pcode',
    		textFieldName : 'text',
    		onSelect: onSelect,
    		isExpand: true,
    		nodeWidth:400,
    	});
    	//$("#tree").css({"width":"100%"});
    	treeManager = $("#tree").ligerGetTreeManager();
    	//treeManager.collapseAll(); //全部收起
    }

    //查询
    function query(tid){

    	$("#instru_type_code").attr("disabled", true);
    	$("#instru_type_name").attr("disabled", true);
    	//主表查询
    	paras = {
    		"instru_type_id" : tid
    	};
    	ajaxJsonObjectByUrl("queryMatInstruTypeById.do?isCheck=false", paras, function (responseData){
    		$("#instru_type_id").val(responseData.instru_type_id);
    		$("#instru_type_code").val(responseData.instru_type_code);
    		$("#instru_type_name").val(responseData.instru_type_name);
    		liger.get("super_code").setValue(responseData.super_code + ',' + responseData.super_code);
    		if(responseData.super_code != '0'){
        		liger.get("super_code").setText(responseData.super_code + ' ' + responseData.super_name);
    		}
    		if(responseData.is_last == '1'){
        		liger.get("is_last").setValue(responseData.is_last);
        		liger.get("is_last").setText("是");
    		}else{
        		liger.get("is_last").setValue(responseData.is_last);
        		liger.get("is_last").setText("否");
    		}
    		if(responseData.is_stop == '1'){
        		liger.get("is_stop").setValue(responseData.is_stop);
        		liger.get("is_stop").setText("是");
    		}else{
        		liger.get("is_stop").setValue(responseData.is_stop);
        		liger.get("is_stop").setText("否");
    		}
    		$("#spell_code").val(responseData.spell_code);
    		$("#wbx_code").val(responseData.wbx_code);
    		$("#group_id").val(responseData.group_id);
    		$("#hos_id").val(responseData.hos_id);
    		$("#copy_code").val(responseData.copy_code);
		});
    	//变更记录查询
		grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name : 'instru_type_id', value : tid});
    	//加载查询条件
    	grid.loadData(grid.where);
    }

    function add_mt(){
    	$("#instru_type_id").val("");
    	$("#mat_type_no").val("");
    	$("#instru_type_code").val("");
    	$("#instru_type_name").val("");
		$("#instru_type_code").removeAttr("disabled");
		$("#instru_type_name").removeAttr("disabled");
    	liger.get("super_code").setValue("");
    	liger.get("super_code").setText("");
    	liger.get("is_last").setValue("1");
    	liger.get("is_last").setText("是");
    	$("#spell_code").val("");
    	$("#wbx_code").val("");
    	//grid.loadData();
    }

    function remove(){
    	var instru_type_id = $("#instru_type_id").val();
		if (instru_type_id == ""){
 			$.ligerDialog.warn('请选择医疗器械分类');
 		}else{
        	$.ligerDialog.confirm('确定删除?', function (yes){
        		if(yes){
        			ajaxJsonObjectByUrl("queryMatInstruTypeById.do?isCheck=false", paras, function (responseData){
	        			
        				if(responseData.is_last == '1'){
	        				paras = {
		        				instru_type_id : instru_type_id,
		        				group_id : $("#group_id").val(),
		        				hos_id : $("#hos_id").val(),
		        				copy_code : $("#copy_code").val(),
		        				instru_type_code : $("#instru_type_code").val()
		        			}
		        			ajaxJsonObjectByUrl("deleteMatInstruType.do", paras, function (responseData){
		        				if(responseData.state=="true"){
		        					loadTree();
		        					add_mt();
		        				}
		        			})
        				}else{
        					$.ligerDialog.warn('当前医疗器械分类并非末级分类!');
        				}
        			});	
        		}
        	});
        }
    }

    function save(){
		if($("form").valid()){
    		var formPara ={
				instru_type_id : $("#instru_type_id").val(),
				instru_type_code : $("#instru_type_code").val(),
				instru_type_name : $("#instru_type_name").val(),
				supper_code : liger.get("super_code").getValue() ==null?"":liger.get("super_code").getValue().split(",")[0],
				is_last : liger.get("is_last").getValue(),
				is_stop : liger.get("is_stop").getValue(),
				matTypeMainPage : $("#matTypeMainPage").val(),
			};
			ajaxJsonObjectByUrl("saveMatInstruType.do",formPara,function (responseData){
        		if(responseData.state=="true"){
        			loadTree();
        			//query(responseData.instru_type_id);
        		}
        	});
		}
		add_mt();
    }

    function openChange(obj){
    	if($("#instru_type_id").val() == ""){
    		$.ligerDialog.error('请选择医疗器械分类');
    		return;
    	}
		var paras =
			"group_id=" + $("#group_id").val() + "&"+
			"hos_id=" + $("#hos_id").val() +"&"+
			"copy_code=" + $("#copy_code").val() + "&"+
			"instru_type_id=" + $("#instru_type_id").val() ;
		$.ligerDialog.open({
			title:'医疗器械分类变更',
			height: 300,
			width: 800,
			url: 'matInstruTypeDictPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			buttons: [
				{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function(item, dialog){dialog.close();}}
			]
		});
    }
    function loadDict(){
		//字典下拉框
		$("#super_code").ligerComboBox({disabled:true,cancelable: false});
        $("#is_last").ligerComboBox({disabled:true,cancelable: false});
        liger.get("is_last").setValue("1");
		liger.get("is_last").setText("是");
    	
		autocomplete("#is_stop", "../../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true, "", true);

    	$("#instru_type_code").ligerTextBox({width:160});
    	$("#instru_type_name").ligerTextBox({width:160});
    	$("#spell_code").ligerTextBox({width:160,disabled:true});
    	$("#wbx_code").ligerTextBox({width:160,disabled:true});
	}
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('S', save);
		hotkeys('C', openChange);
	   /*  hotkeys('E', exportExcel);
		hotkeys('P', printDate); */
		hotkeys('I', imp); 
	 }

    //导入
    function imp() {
		var para = {
			url : 'hrp/mat/info/basic/instrutype/matInstruTypeImportPage.do?isCheck=false',
			title : '医疗器械分类导入',
			width : 0,
			height : 0,
			isShowMin : false,
			isModal : true,
			data : {
				tree : tree
			}
		};
		parent.openDialog(para);
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" >
		<div position="left" title="医疗器械分类"  >
			<h2><font id="font1">编码规则：<font id="font2" color="red"></font></font></h2>
			<div id="treeDiv" class="l-scroll" style="width: 100%">
				<ul id="tree" style="overflow: auto;margin-top:3px;"/>
			</div>
		</div>
		<div position="center" >
			<div id="toptoolbar" ></div>
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" style="width:100%" class="l-table-edit">
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							<span style="color:red">*</span>医疗器械分类编码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="instru_type_id" type="hidden" id="instru_type_id" ltype="text" />
							<input name="instru_type_code" disabled="disabled" type="text" id="instru_type_code" ltype="text" required="true" validate="{required:true,maxlength:40}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							<span style="color:red">*</span>医疗器械分类名称：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="instru_type_name" disabled="disabled" type="text" id="instru_type_name" ltype="text" required="true" validate="{required:true,maxlength:80}" />
						</td>
					</tr>
					<tr style="display:none">
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							上级编码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="super_code" disabled="disabled" type="text" id="super_code" ltype="text" validate="{required:false}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 0px;">
							<span style="color:red">*</span>是否末级：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="is_last" disabled="disabled" type="text" id="is_last" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							<span style="color:red">*</span>是否停用：
						</td>
						<td align="left"  class="l-table-edit-td" >
							<input name="is_stop" type="text" id="is_stop" ltype="text" required="true" validate="{required:true}" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							拼音码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="spell_code" disabled="disabled" type="text" id="spell_code" ltype="text" validate="{required:false,maxlength:40}" />
						</td>
						<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
							五笔码：
						</td>
						<td align="left" class="l-table-edit-td" >
							<input name="wbx_code" disabled="disabled" type="text" id="wbx_code" ltype="text" validate="{required:false,maxlength:40}" />
						</td>
					</tr>
					<tr>
						<td>
							<input name="group_id" type="hidden" id="group_id" ltype="text" />
							<input name="hos_id" type="hidden" id="hos_id" ltype="text" />
							<input name="copy_code" type="hidden" id="copy_code" ltype="text" />
						</td>
					</tr>
				</table>
			</form>
		</div>
    </div>
</body>
</html>

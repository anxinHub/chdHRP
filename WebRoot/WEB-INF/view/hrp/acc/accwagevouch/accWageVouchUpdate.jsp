<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;"> 
<head>   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<style>
	.checkbox-disabled {
		background-position: -26px 0 !important;
	}
</style>
<script type="text/javascript">
     
     var data;
     
     var grid;
     
     var rowindex_id="";
     
     var empKindStatus = [];
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        loadHead();
        
        $("#scheme_code").ligerTextBox({ disabled: true});
        
        $("#subj_code").ligerTextBox({width:180});
        
        $("#subj_dire").ligerTextBox({width:100});
        
        $("#summary").ligerTextBox({width:400});
        
		$("#close").ligerButton({disabled: false,width:92});
        
        $("#save").ligerButton({disabled: false,width:92});
        
     });  
     
     function loadHead(){
    	 
    	 grid = $("#maingrid").ligerGrid({
            
    		 columns: [ 
                    
    		           { display: '科目编码', name: 'subj_code',width:'15%', align: 'left',
    		        	   render : function(rowdata, rowindex,
   								value) {
   								return "<a href=javascript:openUpdate('"+rowindex+"')>"+rowdata.subj_code+"</a>";
   						}
  					 },
                       { display: '科目名称', name: 'subj_name',width:'15%', align: 'left'
  					 },{ display: '方向', name: 'subj_dire',width:'6%', align: 'left',
 						render : function(rowdata, rowindex,
 								value) {
 								return rowdata.subj_dire==0?'借':'贷';
 						}
 					 },{ display: '摘要', name: 'summary',width:'20%', align: 'left'
  					 },{ display: '职工分类', name: 'kind_name',width:'20%', align: 'left',render:function(rowdata, rowindex,
 								value){
  							
  							if(rowdata.kind_code=="0"){
  								
  								return "全部";
  							}
  							
  							return rowdata.kind_name;
  						}
 					 },{ display: '计算公式', name: 'cal_name',width:'23%', align: 'left'
	 				 }
                       ],
                       
                       dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccWageVouchSubj.do?isCheck=false&scheme_code='+$("#scheme_code").val()+'&wage_code='+'${wage_code}'+'&acc_year='+'${acc_year}',
                      
                       height: '98%',width: '63%', checkbox: true,rowindex_idbers:true,rowDraggable:true,
                      
                       selectRowButtonOnly:true,rownumbers:true,
                       
                       toolbar: { items: [
                            
                                 { text: '删除', id:'delete', click: itemclick,icon:'delete' },
                              	
                                 { line:true }
                                 
                      		]}
                     });

          gridManager = $("#maingrid").ligerGetGridManager();
     }
     
     function itemclick(item){
    	 
         if(item.id)
         {
             switch (item.id)
             {
            		
              case "delete":
             	 
             	  var data = gridManager.getCheckedRows();
             	  
                  if (data.length == 0){
                 	 
                  	$.ligerDialog.error('请选择行');
                  	
                  }
                  
                  gridManager.deleteSelectedRow2();
                  
                  return;
                 case "Excel":
                 case "Word":
                 case "PDF":
                 case "TXT":
             }   
         }
         
     }

     // 保存
     function save(){
    	var data = gridManager.getData();
        var formPara={
           scheme_code: $("#scheme_code").val(),
           scheme_name: $("#scheme_name").val(),
           vouch_type_code: liger.get("vouch_type_code").getValue(),
           wage_code: liger.get("wage_code").getValue(),
           group_id: '${group_id}',
           hos_id: '${hos_id}',
           copy_code: '${copy_code}',
           acc_year: '${acc_year}',
           data: JSON.stringify(data)
         };
        ajaxJsonObjectByUrl("updateAccWageVouch.do?isCheck=fasle",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
 }       
   
    function saveAccWageVouch(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	
            //字典下拉框
   		autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true,'',false,'',180);
            
   		autocomplete("#vouch_type_code","../queryVouchType.do?isCheck=false","id","text",true,true,'',false,'',180);
		
		$("#subj_code").ligerComboBox({
			      	
		    		url: '../querySubj.do?isCheck=false&is_last=1',
			      	
					valueField: 'id',
				 	
					textField: 'text', 
				 	
					selectBoxWidth: 400,
					
					autocomplete: true,
					
					width: 180
					
				 });
		
		$("#emp_kind").ligerComboBox({
    		
        	url: '../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0',
        	
            emptyText: '全部',
        	
            valueField: 'id',
        	
         	textField: 'text', 
         	
         	selectBoxWidth: 180,
         	
			isMultiSelect: true,   //是否多选
         	
            isShowCheckBox: true, 
         	
        	autocomplete: true,
        	
        	width: 180,
        	
        	onButtonClick: function () {
        		
        		checkCustomize();
        		
        	}
        	
		  });
		
		liger.get("emp_kind").setValue("0");
		 
	   	liger.get("emp_kind").setText("全部");
			
   		liger.get("vouch_type_code").setValue('${vouch_type_code}');
   		
        liger.get("vouch_type_code").setText('${vouch_type_name}');
        
        liger.get("wage_code").setValue('${wage_code}');
        
        liger.get("wage_code").setText('${wage_name}');
        
     } 
    
    /**
   	 * emp_kind状态管理器。勿复用。wsj.2017.7.5
   	 * @return {[type]} [description]
   	 */
	function checkCustomize () {
		var checkBoxWrap = liger.get('emp_kind').selectBoxInner;
		var checkTr = checkBoxWrap.find("table tr");
		var checkBoxWrap = checkBoxWrap.find("table tr .l-checkbox-wrapper");

		var inputValue = liger.get("emp_kind").getValue();
		inputValue = inputValue.split(";");
		if (inputValue.indexOf("") >= 0) {
			inputValue = null;
		}
		<%--console.log(inputValue)--%>

		// 重置
		empKindStatus = [];

		// 初始设置状态 更新状态
		for (var i = 0; i < checkTr.length; i++) {
			var statusObj = {};

			statusObj.value = $(checkTr[i]).attr("value");
			statusObj.$tr = $(checkTr[i]);
			statusObj.$checkbox = $(checkTr[i]).find(".l-checkbox");
			statusObj.$input = $(checkTr[i]).find("input");
			statusObj.text = $(checkTr[i]).text();

			// 判断有没有值是已经填入的，checked
			if (inputValue && inputValue.indexOf(statusObj.value) >= 0) {
				statusObj.$checkbox.addClass("l-checkbox-checked");
			}

			if (statusObj.$checkbox.attr("class") === "l-checkbox l-checkbox-checked") {
				statusObj.isChecked = true;
			} else {
				statusObj.isChecked = false;
			}

			if (statusObj.$input.attr("disabled")) {
				statusObj.isDisabled = true;
			} else {
				statusObj.isDisabled = false;
			}
			empKindStatus.push(statusObj)
		}
		/**
	     * emp_kind下拉框的点击事件，勿复用。wsj.2017.7.5
	     */
		empKindStatus.forEach(function (item, index) {
			// 初始化下拉框
			if (item.isChecked) {
				if (item.value === "0") {
					empKindStatus.forEach(function (theItem, theIndex) {
						if (theItem.value !== "0") {
							theItem.isDisabled = true;
							theItem.$input.attr("disabled", true);
							theItem.$checkbox.addClass("checkbox-disabled");
						}
					});
				} else {
					empKindStatus.forEach(function (theItem, theIndex) {
						if (theItem.value === "0") {
							theItem.isDisabled = true;
							theItem.$input.attr("disabled", true);
							theItem.$checkbox.addClass("checkbox-disabled");
						}
					});
				}
			}

			// 选项点击事件
			item.$checkbox.get(0).onclick = function () {
				item.isChecked = !item.isChecked;

				if (item.text === "全部" && !item.isDisabled) {
					if (item.isChecked) {
						empKindStatus.forEach(function (theItem, theIndex) {
							if (theIndex !== index) {
								theItem.isDisabled = true;
								theItem.$input.attr("disabled", true);
								theItem.$checkbox.addClass("checkbox-disabled");
							}
						});
					} else {
						empKindStatus.forEach(function (theItem, theIndex) {
							if (theIndex !== index) {
								theItem.isDisabled = false;
								theItem.$input.attr("disabled", false);
								theItem.$checkbox.removeClass("checkbox-disabled");
							}
						});
					}
				} else if (item.text !== "全部" && !item.isDisabled) {
					if (item.isChecked) {
						empKindStatus.forEach(function (theItem, theIndex) {
							if (theItem.text === "全部" && theItem.isDisabled === false) {
								theItem.isDisabled = true;
								theItem.$input.attr("disabled", true);
								theItem.$checkbox.addClass("checkbox-disabled");
							}
						});
					} else {
						var hasChecked = false;
						empKindStatus.forEach(function (thisItem, thisIndex) {
							// 寻找除了全部和自己的判断有没有被选择的
							if (thisIndex !== index && thisItem.text !== "全部" && thisItem.isChecked) {
								hasChecked = true;
							}
						})
						
						if (!hasChecked) {
							empKindStatus.forEach(function (theItem, theIndex) {
								if (theItem.text === "全部" && theItem.isDisabled === true) {
									theItem.isDisabled = false;
									theItem.$input.attr("disabled", false);
									theItem.$checkbox.removeClass("checkbox-disabled");
								}
							});
						}
					}
				}
			}
		})
	}
    
    function openUpdate(obj){
    	
    	rowindex_id = obj;

    	var row = gridManager.getRow(obj);
    	
		liger.get("subj_code").setValue(row.subj_code);
   		
        liger.get("subj_code").setText(row.subj_code+" "+row.subj_name); 
        
        if(row.kind_code==null||row.kind_code==0){
        	
        	liger.get("emp_kind").setValue('0');
            
            liger.get("emp_kind").setText('全部'); 
        	
        }else{
        	
        	liger.get("emp_kind").setValue(row.kind_code);
            
            liger.get("emp_kind").setText(row.kind_name); 
        	
        }
        
        $("#subj_dire").val(row.subj_dire);
        
        $("#cal_name").val(row.cal_name);
        
        $("#summary").val(row.summary);
        
        if(row.is_balance=="0"){
        	
        	$("#con_table").find("input[name='is_balance']")[0].checked=false;
        	
        }else{
        	
        	$("#con_table").find("input[name='is_balance']")[0].checked=true;
        }
        
		if(row.is_neg=="0"){
        	
        	$("#con_table").find("input[name='is_minus']")[0].checked=false;
        	
        }else{
        	
        	$("#con_table").find("input[name='is_minus']")[0].checked=true;
        }
		
		if(row.is_assign=="0"){
        	
        	$("#con_table").find("input[name='is_assign']")[0].checked=false;
        	
        }else{
        	
        	$("#con_table").find("input[name='is_assign']")[0].checked=true;
        }
        
    }
    
    function saveData(){
    	var subj_code = liger.get("subj_code").getValue();
    	var summary =$("#summary").val();
        if (subj_code == ""){
        	$.ligerDialog.error('请选择科目');
        	return;
        }
        
       /*  if(rowindex_id != "undefined" && rowindex_id != ""){
        	
        	 gridManager.deleteRow(rowindex_id);
        	 
        	 rowindex_id="";
        	 
        } */
        
        if(summary==""){
			$.ligerDialog.error('摘要不能为空！');
        	return;
        }
    	var rowd = { 
           	subj_code: liger.get("subj_code").getValue(),
           	subj_name: $("#subj_code").val().split(" ")[1],
           	subj_dire: $("#subj_dire").val(),
           	kind_code: liger.get("emp_kind").getValue(),
           	kind_name: $("#emp_kind").val(),
           	cal_name: $("#cal_name").val(),
           	cal_eng: $("#cal_eng").val(),
           	summary: $("#summary").val(),
           	is_balance: $('#is_balance').is(':checked') == true ? "1" : "0",
           	is_neg: $('#is_minus').is(':checked') == true ? "1" : "0",
           	is_assign: $('#is_assign').is(':checked') == true ? "1" : "0"
        };
    	if(rowindex_id==null||rowindex_id==""){
    		gridManager.addRow(rowd);
    	}else{
			gridManager.updateRow(rowindex_id, rowd);
    	} 
    	
    	liger.get("subj_code").setValue('');
        liger.get("subj_code").setText('');
        liger.get("emp_kind").setValue('');
        liger.get("emp_kind").setText('');
        $("#subj_dire").val("");
        $("#cal_name").val("");
        $("#summary").val("");
    }
    
	function showWindow() {

		var wage_code = liger.get("wage_code").getValue();

		if (wage_code == null || wage_code == "") {

			$.ligerDialog.error('请选择工资套');

			return;

		}
		
		var cal_name =$("#cal_name").val();

		$.ligerDialog.open({
					url : 'accWageVouchCalPage.do?isCheck=false&wage_code='+ wage_code+ '&cal_name=' + cal_name.replace(/\+/g, "%2B"),
					data : {},
					height : 433,
					width : 1095,
					title : '设置公式',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : false,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							
							var cal_name = dialog.frame.saveAccWageTaxCal();
							
							$("#cal_name").val(cal_name.split("|")[0]);

							$("#cal_eng").val(cal_name.split("|")[1]);

							if(cal_name.trim() != null && cal_name.trim() != "" && cal_name.trim() != "|"){
							
								$("input[name='is_balance']").removeAttr("checked");
								
								$("input[name='is_balance']").attr("disabled","disabled");
								
							}else{
								
								$("input[name='is_balance']").removeAttr("disabled");
							}
							
							dialog.close();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});

	}

	function clearContent() {

		liger.get("subj_code").setValue("");
		
		liger.get("subj_code").setText("");

		liger.get("emp_kind").setText("");
		
		liger.get("emp_kind").setValue("");

		$("#subj_dire").val("");

		$("#cal_name").val("");

		$("input[name='is_balance']").removeAttr("checked");

		$("input[name='is_balance']").removeAttr("disabled");
		
		rowindex_id="";

	}
	
	//关闭窗口
	function closeDialog() {

		frameElement.dialog.close();
	}
</script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			id="table_id" style="width: 100%">

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">方案编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="scheme_code" type="text" id="scheme_code"
					value="${scheme_code }" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">方案名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="scheme_name" type="text" id="scheme_name" ltype="text"
					value="${scheme_name }" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">凭证类型：</td>
				<td align="left" class="l-table-edit-td">
					<input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工资套：</td>
				<td align="left" class="l-table-edit-td">
					<input name="wage_code" type="text" id="wage_code" ltype="text"	validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				<td align="left"><input id="save" type="button" value="保存（S）" onclick="saveAccWageVouch();" /></td>
				<td align="left"><input id="close" type="button" value="关闭（ESC）" onclick="closeDialog();" /></td>
			</tr>
		</table>
	</form>
	<hr>
	<div style="width: 1365px;">
		<div id="maingrid" style="width: 65%; float: left; display: inline"></div>
		<div style="width:35%; float: left; display: inline; height: 200px">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" id="con_table">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px">科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
					<td align="left" class="l-table-edit-td"><input
						name="subj_code" type="text" id="subj_code" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td">方向：</td>
					<td align="left" class="l-table-edit-td"><select
						id="subj_dire" ltype="select">
							<option value="0">借</option>
							<option value="1">贷</option>
					</select></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">职工分类：</td>
					<td align="left" class="l-table-edit-td" colspan="4"><input
						name="emp_kind" type="text" id="emp_kind" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
				</tr>
				<tr>
				<td align="right" class="l-table-edit-td"style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
				<td align="left" class="l-table-edit-td" colspan="4"><input
					name="summary" type="text" id="summary" ltype="text"
					 validate="{required:false,maxlength:100}" /></td>
				<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px; padding-bottom: 180px">计算公式：</td>
					<td align="left" class="l-table-edit-td" colspan="4"><textarea
							class="liger-textarea" style="height: 200px; width: 400px"
							validate="{required:true}" name="cal_name" id="cal_name"
							disabled="disabled"></textarea> <input name="cal_eng"
						type="hidden" id="cal_eng" ltype="text"
						validate="{required:true,maxlength:2000}" /></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="left" class="l-table-edit-td" colspan="2"
						style="padding-left: 100px;"><input name="is_balance"
						type="checkbox" id="is_balance" ltype="text" />取借贷平衡 <input
						name="is_minus" type="checkbox" id="is_minus" ltype="text" />金额取反
						<input name="is_assign" type="checkbox" id="is_assign" ltype="text" />是否指定科目
					</td>
				</tr>
				<tr>
					<td align="left" colspan="2" class="l-table-edit-td" style="padding-left: 100px;">
						<input class="liger-button" type="button" value="重置" onClick="clearContent();">
						<input class="liger-button" type="button" value="设置" onClick="showWindow();">
						<input class="liger-button" type="button" value="确定" onClick="saveData();">
					</td>
				</tr>
			</table>
		</div>
	</div>
    </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//out.print(path);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
var mainform 
var dialog = frameElement.dialog;

$(function(){
	loadHead()
	loadCss()
	//loadForm()
})
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
 
function loadCss(){
		$("#target_ruler_input").ligerTextBox({
			width : 400
		});
		$("#target_name_input").ligerTextBox({
			width : 400
		});
		$("#target_unit_input").ligerTextBox({
			width : 400
		});
		$("#target_status_select").ligerTextBox({
			width : 400
		}); 
}

//初始化
function loadHead(){
	//添加取数公式设置按钮 
	$("#target_formulaSet_input").ligerButton({
		text: '设置',
		click: formulaSetButton ,
	});
	// 编码规则 
	$("#target_ruler_input").html('2-2-2-2-2')
}

//公式按钮事件
function formulaSetButton(){

	parent.$.ligerDialog.open({
		url :'hrp/acc/accfunda/cellSetPage.do?isCheck=false&mod_code=1',
		data:{"fma_en":$("#fma_en").val(),"fma_cn":$("#fma_cn").val()},
		height: 600,width: 1200,
		title : '公式',
		parentframename : window.name,
		modal:true,showToggle:false,initShowMax:false,showMax:false,showMin: false,isResize:true,
		buttons : [{
			text : '保存',
			onclick : function(item, dialog) {
				//  取数公式 
				//$("#target_formula_input").val(234)
				var obj = {};//js对象实现引用传递
				dialog.frame.mySave(obj);
				$("#fma_en").val(obj.fma_en);
				$("#fma_cn").val(obj.fma_cn);
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

//保存按钮事件
function addPaperIncome(forms){
	var dataObject ={}
	//编码规则 
	//dataObject.SUPER_CODE = dialog.frame.$("#target_ruler_input").val()
}
</script>
</head>

<body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="acc_element_sf" method="post"  id="acc_element_sf" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编码规则:</td>
                <td align="left" class="l-table-edit-td">
                	<label name="target_ruler_input" type="text" disabled="disabled" id="target_ruler_input" ltype="text" maxlength="20" validate="{required:true}">
                	</label>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="target_code_input" style="width:400px;" type="text" id="target_code_input" disabled="disabled" ltype="text" maxlength="20" validate="{required:true}" value="${FAC_CODE}"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="bas_name" type="text" id="target_name_input" ltype="text" maxlength="50" validate="{required:true}" value="${FAC_NAME}"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标单位:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="bas_unit" type="text" id="target_unit_input" ltype="text" maxlength="20" validate="{required:true}" value="${ZB_UNIT}"/>
                </td>
                <td align="left"></td>
            </tr> 

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取数公式:</td>
                <td align="left" class="l-table-edit-td">
                	<textarea id="fma_en" name="fma_en" cols="100" rows="3" hidden=true></textarea>
                	<textarea name="fma_cn" id="fma_cn" validate="{required:true}" readonly disabled
                			style=" border: 1px solid #aecaf0;height: 100px;line-height: 15px;width: 400px;" >${FMA_CN}</textarea>
                </td>
                <td align="left">
                	<input  type="button" class="l-dialog-btn-highlight" style="width:50px;height:25px" value=" 设置" id="target_formulaSet_input"/>
                </td>
            </tr>
            
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注:</td>
                <td align="left" class="l-table-edit-td">
                	<textarea name="note" id="target_note_input" ltype="textarea" maxlength="100"
                			style=" border: 1px solid #aecaf0;height: 50px;line-height: 20px;width: 400px;background: #fff;" <%--  value="${NOTE} --%>" >${NOTE}</textarea>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态:</td>
                <td align="left" class="l-table-edit-td">
                	<select name="is_stop" id="target_status_select" style="width:180px;"> <%-- value="${IS_STOP}" --%>
						<option value="1">启用</option>
						<option value="0">停用</option>
					</select>
                </td>
                <td align="left"></td>
            </tr>
        </table>
	</form>

</body>

</html>
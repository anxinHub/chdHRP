<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>

<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var frameGrid;

	$(function() {
		
		frameGrid=parent.frameObj.grid;
		$("form").ligerForm();
		$("#template_code").val(dialog.get("data").pxTpCode);
		if($("#template_code").val()!=""){
			liger.get("is_replace").setValue(true);
			$("#template_name").val(dialog.get("data").pxTpName);
			$("#note").val(dialog.get("data").pxTpNote);
		}
		$("#template_code").focus();
		$("[name='textAreaWidth']").css("width",180);
		$(':button').ligerButton({width:80});
		
	});
	
	
	function mySave(){
		
		if($("#template_code").val()==""){
			return false;
		}
		
		if($("#template_name").val()==""){
			return false;
		}
		
		if($("#template_code").val().indexOf("'")!=-1){
			$.ligerDialog.error("模板编码不能包含单引号.");
			return false;
		}
		
		//沒有凭ID，保存模板
		var saveJson=[];
		var saveIndex = layer.load(1);
		/* if(parent.liger.get("vouch_type_code").getValue()==""){
			layer.close(saveIndex);
			$.ligerDialog.error("凭证类型不能为空.");
			return false;
		} */
			
		var isSubmit=true;
		getSaveJson(false);//取财务会计科目
		if(isSubmit && $("#is_budg_val",parent.document).val()==1){
			//取预算会计科目
			getSaveJson(true);
		}
		
		if(!isSubmit){
			layer.close(saveIndex);
			return false;
		}
		
		var isReplace=0;
    	if(liger.get("is_replace").getValue()){
    		isReplace=1;
      	}

		var fromData={
				detail:JSON.stringify(saveJson),
				vouch_type_code:dialog.get("data").vouch_type_code,
				tp_code:$("#template_code").val(),
				tp_name:$("#template_name").val(),
				note:$("#note").val(),
				is_replace:isReplace
		};
		ajaxJsonObjectBylayer("saveAccBudgTpByVouch.do?isCheck=false",fromData, function(responseData) {
			if(responseData.state=="true"){
				parent.$.ligerDialog.success("保存成功.");
				frameElement.dialog.close();
			}
			
			
		},layer,saveIndex);
		
		
		//拼保存json
		function getSaveJson(isBudg){
			var vouchRow=0;
			$.each(frameGrid.getGridData(),function(i,obj){
				vouchRow=vouchRow+1;
				//借贷金额为空不处理
				if(obj.debit=="")obj.debit=0;
				if(obj.credit=="")obj.credit=0;
				if(obj.budg_debit=="")obj.budg_debit=0;
				if(obj.budg_credit=="")obj.budg_credit=0;
				
				if(isBudg){
					//只取预算会计科目
					if(obj.budg_subj_code=="" && parseFloat(obj.budg_debit)==0 && parseFloat(obj.budg_credit)==0 ){
						return true;
					}
				}else{
					//只取财务会计科目
					if(obj.subj_code=="" && parseFloat(obj.debit)==0 && parseFloat(obj.credit)==0){
						return true;
					}
				}
			
				
				if(obj.summary=="" && (obj.subj_code!="" || obj.budg_subj_code!="")){
					isSubmit=false;
					$.ligerDialog.error((obj.subj_code?obj.subj_code:obj.budg_subj_code)+"，摘要不能为空.");
					return false;
				}else if(obj.summary!="" && obj.subj_code=="" && obj.budg_subj_code==""){
					isSubmit=false;
					$.ligerDialog.error(obj.summary+"，科目不能为空.");
					return false;
				}else if(obj.summary=="" && obj.subj_code=="" && obj.budg_subj_code==""){
					isSubmit=false;
					$.ligerDialog.error("摘要、科目不能为空.");
					return false;
				}
				
				var subjAttr;
				var jsonKey=obj.did;
				if(isBudg){
					//取预算会计
					jsonKey=obj.budg_did;
					subjAttr=parent.frameObj.getSubjAttr(obj.budg_subj_code);//根据科目编码获取科目属性
				}else{
					subjAttr=parent.frameObj.getSubjAttr(obj.subj_code);//根据科目编码获取科目属性
				}
				
				if(!jsonKey){
					//新的分录没有ID,用行号+科目类别属性
					if($("#is_budg_val",parent.document).val()==1){
						//分栏式
						jsonKey=obj.id+"-"+subjAttr.kind_code;
					}else{
						jsonKey=obj.id;
					}
				}
				
				
				//saveJson.push({id:obj.id,summary:obj.summary,sid:obj.subj_code,debit:obj.debit,credit:obj.credit,check:checkData==null?"":checkData[0].Rows});
				if(isBudg){
					//分栏式，取预算会计科目
					saveJson.push({vouch_row:vouchRow,id:obj.id,did:obj.budg_did,summary:obj.summary,sid:obj.budg_subj_code,debit:obj.budg_debit,credit:obj.budg_credit,nature_code:subjAttr.subj_nature_code,is_check: subjAttr.is_check,is_cash: subjAttr.is_cash,is_bill: subjAttr.is_bill,kind_code: subjAttr.kind_code});
				}else{
					//取财务会计科目
					saveJson.push({vouch_row:vouchRow,id:obj.id,did:obj.did,summary:obj.summary,sid:obj.subj_code,debit:obj.debit,credit:obj.credit,nature_code:subjAttr.subj_nature_code,is_check: subjAttr.is_check,is_cash: subjAttr.is_cash,is_bill: subjAttr.is_bill,kind_code: subjAttr.kind_code});
				}
				
			});
		}
		
	}
	
	function myClose(){
		
		setTimeout(function(){			
			frameGrid.editCell(); 			
			frameElement.dialog.close();
		},50);
	}
	

</script>
</head>

<body style="padding: 0px;"  onload="">

 	<form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        	<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板编码<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td">
                <input name="template_code" type="text" id="template_code" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">模板名称<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td"><input name="template_name" type="text" id="template_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td">
              		  <textarea id="note" class="l-textarea" ltype="text" name="textAreaWidth" rows="2"></textarea>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td">
              		  <input type="checkbox" id="is_replace"/>是否覆盖&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td align="left"></td>
            </tr>
            <input type="button" style="bottom:40px;right:200px;position:absolute" value="保存（S） " accessKey="S" onclick="mySave();"/>
			<input type="button" style="bottom:40px;right:100px;position:absolute" value="关闭（C） " accessKey="C" onclick="myClose();"/>
				
        </table>
      
    </form>
	
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    $(function (){     
        loadDict();
        loadForm();
        if ('${state}' != 0) {//置黑不能点击
        	$("#create_date").ligerTextBox({disabled:true});
        	$("#apply_date").ligerTextBox({disabled:true});
        	$("#sharp_degree").ligerTextBox({disabled:true});
        	$("#rep_phone").ligerTextBox({disabled:true});
        	$("#rep_team_code").ligerTextBox({disabled:true});
        	$("#ass_card_no").ligerTextBox({disabled:true});
        	$("#repair_dept_id").ligerTextBox({disabled:true});
        	$("#apply_emp").ligerTextBox({disabled:true});
        	//$("#ass_name").ligerTextBox({disabled:true});
        	
		}  
    });  
     
    function getGridOptions(checkbox)
    {
        var options = {
            columns: [
			 { display: '卡片号', name: 'ass_card_no', minWidth: 140, width: 100 },
            { display: '资产名称', name: 'ass_name', minWidth: 120, width: 100 },
            { display: '资产分类', name: 'ass_type_name', minWidth: 140, width: 100 },
            { display: '规格', name: 'ass_spec', width: 100 },
            { display: '型号', name: 'ass_model', width: 100 },
            { display: '品牌', name: 'ass_brand', width: 100 },
            { display: '单位', name: 'unit_name', width: 100 }
            ], switchPageSizeApplyComboBox: false,
            usePager : true,
            url:'../queryAssCardNoDictTable.do?isCheck=false&use_state=1,2,3,4,5',
            pageSize: 30, 
            checkbox: false
        };
        return options;
    }
    function save(){
    	if('${state}' != 0){
    		$.ligerDialog.warn('维修申请已经确认无法操作!');
			return false;
    		
    	}else{
        var formPara={
       		  apply_no:'${apply_no}',
       		            
	          repair_dept_id:liger.get("repair_dept_id").getValue().split("@")[0],
	           
	          repair_dept_no:liger.get("repair_dept_id").getValue().split("@")[1],
	           
	          ass_name:$("#ass_name").val(),
	           
	          apply_emp:liger.get("apply_emp").getValue(),
	           
	          create_date:$("#create_date").val(),
	          apply_date:$("#apply_date").val(),
	           
	          state:'0',
	           
	          sharp_degree:liger.get("sharp_degree").getValue(),
	           
	          rep_phone:$("#rep_phone").val(),
	           
	          rep_team_code:liger.get("rep_team_code").getValue(),
	           
	          note:$("#note").val(),
	           
	          ass_card_no:liger.get("ass_card_no").getValue().split(" ")[0],
	       
	          ass_nature:liger.get("ass_card_no").getValue().split(" ")[1],
	       
	      	   STATE : '0'
        };
        ajaxJsonObjectByUrl("updateAssRepairApply.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }}
     
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
   
    function saveAssRepairApply(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
	autocomplete("#rep_team_code","../queryRepTeam.do?isCheck=false", "id","text", true, true, null, null,'${rep_team_code}');
	autocomplete("#repair_dept_id","../queryDeptDict.do?isCheck=false", "id","text", true, true, null, null,'${repair_dept_id}@${repair_dept_no}');
	/* autocomplete("#ass_card_no","../queryAssCardNoDict.do?isCheck=false&use_state=1,2,3,4,5", "id","text", true, true, null, null,'${ass_card_no} ${ass_nature}'); */
	/* liger.get("ass_card_no").setValue("${ass_card_no} ${ass_nature}");
    liger.get("ass_card_no").setText("${ven_name}"); */
	autocomplete("#apply_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true, null, null,'${apply_emp}');
	/* $("#sharp_degree").ligerComboBox({cancelable : false}); */
	$('#sharp_degree').ligerComboBox({
			data:[{id:3,text:'非常紧急'},{id:2,text:'一般紧急'},{id:1,text:'一般'},{id:0,text:'不紧急'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:180
		});
	
	$("#ass_name").ligerTextBox({disabled:true});
	
	$("#create_date").ligerTextBox({width:178});
		$("#apply_date").ligerTextBox({width:178});
  	liger.get("sharp_degree").setValue("${sharp_degree}");
	   /* $("#ass_card_no").ligerComboBox({ 
              onSelected: function (value,text)
              {
                  $("#ass_name").val(text.split(" ")[1]);
              }
		          }); */
	//资产卡片下拉框表单	
	 $("#ass_card_no").ligerComboBox({
        width: 180,
        slide: false,
        selectBoxWidth: 500,
        selectBoxHeight: 240,
        valueField: 'id',
        textField: 'text', 
        autocomplete: true,
        grid: getGridOptions(false),
        onSelected:function(value,text){
       	 $("#ass_name").val(text.split(" ")[1]);
        }
    });	
	liger.get("ass_card_no").setValue("${ass_card_no} ${ass_nature}");
    liger.get("ass_card_no").setText("${ass_card_no} ${ass_name}");
    $("#ass_name").val('${ass_name}');	
} 
</script>

</head>

<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
		<form name="form1" method="post"  id="form1" >
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			
		    <tr>
		    </tr> 
		    <tr>
		        <td align="right" class="l-table-edit-td" style="padding-left: 20px;">
		            <b>
			            <font color="red">
			            	*
			            </font>
		            </b>
		           		 制单日期：
		        </td>
				<td align="left" class="l-table-edit-td">
					<input name="create_date" type="text" id="create_date" class="Wdate" value="${create_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
				</td>
				 <td align="left"></td>
		        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> <b>
			            <font color="red">
			            	*
			            </font>
		            </b>维修班组：</td>
		        <td align="left" class="l-table-edit-td">
		        <input name="rep_team_code" type="text" id="rep_team_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
		        <td align="left"></td>
		         <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> <b>
			            <font color="red">
			            	*
			            </font>
		            </b>维修部门ID：</td>
		        <td align="left" class="l-table-edit-td"><input name="repair_dept_id" type="text" id="repair_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
		        <td align="left"></td>
		        </tr> 
		    <tr>
		    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">
		            <b>
			            <font color="red">
			            	*
			            </font>
		            </b>
		           		 申请日期：
		        </td>
				<td align="left" class="l-table-edit-td">
					<input name="apply_date" type="text" id="apply_date" class="Wdate" value="${apply_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
				</td>
				 <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> <b>
			            <font color="red">
			            	*
			            </font>
		            </b>申请人：</td>
		        <td align="left" class="l-table-edit-td"><input name="apply_emp" type="text" id="apply_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
		        <td align="left"></td>
		        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> <b>
			            <font color="red">
			            	*
			            </font>
		            </b>报修人电话：</td>
		        <td align="left" class="l-table-edit-td"><input name="rep_phone" type="text" id="rep_phone" value="${rep_phone}" ltype="text" validate="{required:true,maxlength:20}" /></td>
		        <td align="left"></td>
		       
		    
		       
		        </tr> 
		    <tr>
		     <td align="right" class="l-table-edit-td"  style="padding-left:20px;">紧急程度：</td>
		        <td align="left" class="l-table-edit-td">
		        	<!-- <select name="sharp_degree" id="sharp_degree">
		        		<option  value="0">不紧急</option>
		        		<option  value="1">一般</option>
		        		<option  value="2">一般紧急</option>
		        		<option  value="3">非常紧急</option>
		        	</select> -->
		        	<input name="sharp_degree" type="text" id="sharp_degree"/>
		        </td>
		        <td align="left"></td>
		   		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"> <b>
			            <font color="red">
			            	*
			            </font>
		            </b>资产卡片：</td>
		        <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no" ltype="text" validate="{required:true}" /></td>
		        <td align="left"></td>
		        <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> <b>
			            <font color="red">
			            	*
			            </font>
		            </b>资产名称：</td>
		        <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name" ltype="text" value="${ass_name}" validate="{required:true}" /></td>
		        <td align="left"></td>
		       
		    </tr> 
		    <tr>
		     	
		        
		    </tr> 
		    <tr>
		        
		        
		        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">故障内容：</td>
		        <td align="left" class="l-table-edit-td" colspan="4">
		        	<textarea rows="5" cols="60"name="note"  id="note" style="border-color: #aecaf0;">${note}</textarea>
		        </td>
		        <td align="left"></td>
		    </tr> 
		    <tr>
		        
		    </tr> 
		</table>
		</form>
    </body>
</html>

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
    var subj_type = "${subj_type}";
    var flag = 0 ; //
    $(function (){
        loadDict();
        loadForm();
        
        $("#subj_type").change(function(){
        	flag = flag + 1
        	subj_type = liger.get("subj_type").getValue();
        	//根据收入科目类别设置查询预算科目 
        	if(flag > 0){
        		liger.get("subj_code").setValue("");
            	liger.get("subj_code").setText("")
        	}
        	autocomplete("#subj_code","../../../queryBudgIncomeTypeSet.do?isCheck=false&subj_type="+subj_type+"&budg_year="+"${budg_year}","id","text",true,true,'',false,'',260);
        })
        
    });  
     
    function save(){
        var formPara={
        budg_year:$("#budg_year").val(),
        subj_code:liger.get("subj_code").getValue(),
        acc_subj_code2Old:'${acc_subj_code2}',
        acc_subj_code2:liger.get("acc_subj_code2").getValue().split(".")[0],
        subj_type:liger.get("subj_type").getValue()
        };
        ajaxJsonObjectByUrl("updateBudgAccSubjIncomeShip.do?isCheck=false",formPara,function(responseData){
            
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
     //$("form").ligerForm();
    }       
   
    function saveBudgAccRelationship(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
        //收入预算科目类型下拉框
   	 	autocomplete("#subj_type","../../../queryBudgIncomeSubjType.do?isCheck=false","id","text",true,true,"",false);
   	 	liger.get("subj_type").setValue("${subj_type}");
     	liger.get("subj_type").setText("${subj_type} ${subj_type_name}")
        
     	
     	
       //根据收入科目类别设置查询预算科目 
        autocomplete("#subj_code","../../../queryBudgIncomeTypeSet.do?isCheck=false&subj_type="+subj_type+"&budg_year="+"${budg_year}","id","text",true,true,'',false,'',260);
		liger.get("subj_code").setValue("${subj_code}");
        liger.get("subj_code").setText("${subj_code} ${subj_name}")
    	//会计科目下拉框
        autocomplete("#acc_subj_code2","../../../queryAccSubj.do?isCheck=false&subj_type="+"04"+"&acc_year="+"${budg_year}","id","text",true,true,'',false,'',260);
        liger.get("acc_subj_code2").setValue("${acc_subj_code2}");
        liger.get("acc_subj_code2").setText("${acc_subj_code2} ${subj_name}")
        
        $("#budg_year").ligerTextBox({width:260,disabled:true});
   	 	$("#subj_type").ligerTextBox({width:260});
   	 	$("#subj_code").ligerTextBox({width:260});
	 	$("#acc_subj_code2").ligerTextBox({width:260});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" disabled="disabled" ltype="text"  value="${budg_year}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目类别<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_type" type="text"  id="subj_type" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>会计科目编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="acc_subj_code2" type="text" id="acc_subj_code2" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
          
        </table>
    </form>
    </body>
</html>

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
     var dataFormat;
     var flag = '${flag}';
     $(function (){
    	 
    	 //通过标识符进行判断 添加的字段是那些   1位诊断   2位手术
        if(flag=='1'){
        	var zdTable= document.getElementById("zdTable"); 
        	zdTable.style.display="block";
        	var ssTable= document.getElementById('ssTable');
        	ssTable.style.display ="none";
        }
        if(flag=='2'){
        	var ssTable= document.getElementById("ssTable"); 
        	ssTable.style.display="block";
        	var zdTable= document.getElementById('zdTable');
        	zdTable.style.display ="none";
        }
        loadDict()//加载下拉框
        loadForm();
     });  
     
     //保存  诊断规则
     function  saveIcd10Rule(){
        var formPara={
           scheme_code:$("#scheme_code").val(),
           drgs_code:$("#drgs_code").val(),
           icd10_code : liger.get("icd10_code").getValue(),
           icd_rule_code : liger.get("icd_rule_code_zd").getValue()
         };
        ajaxJsonObjectByUrl("addHtcgSchemeIcd10Rule.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='icd10_code']").val('');
				 $("input[name='icd_rule_code_zd']").val('');
                parent.loadHead2_1();
            }
        });
    }
   //保存  手术规则
     function  saveIcd9Rule(){
        var formPara={
           scheme_code:$("#scheme_code").val(),
           drgs_code:$("#drgs_code").val(),
           icd9_code : liger.get("icd9_code").getValue(),
           icd_rule_code : liger.get("icd_rule_code_ss").getValue()
         };
        ajaxJsonObjectByUrl("addHtcgSchemeIcd9Rule.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='icd9_code']").val('');
				 $("input[name='icd_rule_code_ss']").val('');
                parent.loadHead2_2();
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
   
    function saveRule(){
        if($("form").valid()){
        	if(flag=='1'){
            	saveIcd10Rule();
        	}
        	if(flag=='2'){
        		saveIcd9Rule();
        	}
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#icd10_code","../../base/queryHtcgIcd10Dict.do?isCheck=false", "id", "text", true,true);
    	var parms = {icd_rule_type:"01"}
    	autocomplete("#icd_rule_code_zd","../../base/queryHtcgIcdRuleDict.do?isCheck=false", "id", "text", true,true,parms);
    	
    	autocomplete("#icd9_code","../../base/queryHtcgIcd9Dict.do?isCheck=false", "id", "text", true,true);
    	var parms = {icd_rule_type:"02"}
    	autocomplete("#icd_rule_code_ss","../../base/queryHtcgIcdRuleDict.do?isCheck=false", "id", "text", true,true,parms);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
		<input name="drgs_code" type="hidden" id="drgs_code"  value="${drgs_code}"  />
		<input name="scheme_code" type="hidden" id="scheme_code" value="${scheme_code}"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" id="zdTable">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">诊断编码：</td>
                <td align="left" class="l-table-edit-td"><input name="icd10_code" type="text" id="icd10_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">ICD入组规则 ：</td>
                <td align="left" class="l-table-edit-td"><input name="icd_rule_code_zd" type="text" id="icd_rule_code_zd" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" id="ssTable">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手术编码：</td>
                <td align="left" class="l-table-edit-td"><input name="icd9_code" type="text" id="icd9_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">ICD入组规则 ：</td>
                <td align="left" class="l-table-edit-td"><input name="icd_rule_code_ss" type="text" id="icd_rule_code_ss" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>

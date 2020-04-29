<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.chd.hrp.cost.entity.*" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<%
  List<CostBonusSchemeSet> list = (List<CostBonusSchemeSet>) request.getAttribute("bonusSchemeSetList");
%>
<script type="text/javascript">
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
        		 scheme_id: '${scheme_id}',
 				acc_year:$("#year_month").val().substring(0,4),
                 
                 acc_month:$("#year_month").val().substring(4,6),
                  
                 dept_id:liger.get("dept").getValue().split(".")[0],
                  
                 dept_no:liger.get("dept").getValue().split(".")[1],
                  
                 emp_id:liger.get("emp").getValue().split(".")[0],
                  
                 emp_no:liger.get("emp").getValue().split(".")[1],
                  
                 emp_kind_code:liger.get("emp_kind_code").getValue(),
                 
                 <%for(int l = 0 ; l < list.size(); l ++ ){ %>
                 	<%=list.get(l).getBonus_column() %>:$("input[name='<%=list.get(l).getBonus_column() %>']").val()
      		   <%}%>
            
         };
        
        ajaxJsonObjectByUrl("addCostEmpBonusDetail.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("input[name='year_month']").val('');
				 $("input[name='dept']").val('');
				 $("input[name='emp']").val('');
				 $("input[name='emp_kind_code']").val('');
				 <%for(int j = 0 ; j < list.size(); j ++ ){ %>
				 $("input[name=<%=list.get(j).getBonus_column() %>]").val('');
				 <%}%>
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
   
    function saveCostEmpBonusDetail(){
        if($("form").valid()){
            save();
        }
   }
    function chargematetype(){
    	  var param = {
    			dept_id:liger.get("dept").getValue()
             };
          autocomplete("#emp","../queryEmpArrt.do?isCheck=false","id","text",true,true,param);
      }
    
    function loadDict(){
    	
		autocomplete("#emp","../queryEmpArrt.do?isCheck=false","id","text",true,true);
		
		autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
	     //字典下拉框
		
            //字典下拉框
        $("#dept").ligerComboBox({
    	      	url: '../queryDeptDictNoLast.do?isCheck=false&is_last=1',
    	      	valueField: 'id',
    	       	textField: 'text', 
    	       	selectBoxWidth: 160,
    	      	autocomplete: true,
    	      	width: 160,
    	      	onSelected : function (selectValue){
    	      		if(selectValue != null){
        				var dept={
        						dept_id:selectValue.split(".")[0],
        						dept_no:selectValue.split(".")[1],
        	        	};	
        			      $("#emp").ligerComboBox({
        			    	    parms : dept,
        		    	      	url: '../queryEmpArrt.do?isCheck=false',
        		    	      	valueField: 'id',
        		    	       	textField: 'text', 
        		    	       	selectBoxWidth: 240,
        		    	      	autocomplete: true,
        		    	      	defaultSelect:true,
        		    	      	width: 180,
        		    	      	onSelected : function (selectValue){
        		    	      		if(selectValue != null){
        		    	      			var emp={
        		        						emp_id:selectValue.split(".")[0],
        		        						emp_no:selectValue.split(".")[1],
        		        	        	};	
        		    	      			
        		    	      		  autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true,emp,false,false,'180');
        		    	      		}
        		    	      	}
        		    		 });
        	      		}
    	      	}
    		 });
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept" type="text" id="dept" ltype="text"  onchange="chargematetype()" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
                <td align="left" class="l-table-edit-td"><input name="emp" type="text" id="emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_kind_code" type="text" id="emp_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <%for(int i = 0 ; i < list.size(); i ++ ){ %>
	            <tr>
	                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><%=list.get(i).getBonus_item_name() %>：</td>
	                <td align="left" class="l-table-edit-td"><input name="<%=list.get(i).getBonus_column() %>" type="text" id="<%=list.get(i).getBonus_column() %>" ltype="text" validate="{required:true,maxlength:20}" /></td>
	                <td align="left"></td>
	            </tr> 
            <%} %>

        </table>
    </form>
   
    </body>
</html>
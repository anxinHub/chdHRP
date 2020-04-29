<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var para = {
			
			scheme_id:'${scheme_id}',
			
			emp_kind_code:'${emp_kind_code}'
	};
	
	var listbox1, listbox2;
	
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        listbox1 =  $("#listbox_ok").ligerListBox({
			parms : para,
			url : 'queryCostBonusMap.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 160,
			width : 200
		});
        listbox2 = $("#listbox_no").ligerListBox({
 			parms : para,
			url : 'queryCostBonusItemList.do?isCheck=false', 
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 160,
			width : 200
		}); 
        
    });  
     
    function save(){
    	
    var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
   	 
   	 box2.selectAll();
   	 
   	 var bonusData = liger.get("listbox_no").getValue();

   	 if(bonusData==""){
   		 
   		 $.ligerDialog.error('请选择工资项');
   		 
   		 return false;
   		 
   	 }

       var formPara={
    		   
    	  scheme_id:'${scheme_id}',
           
          scheme_name:$("#scheme_name").val(),

          emp_kind_code:liger.get("emp_kind_code").getValue(),
           
          bonus_code :bonusData,
           
          order_no:$("#order_no").val()

        };
       
        ajaxJsonObjectByUrl("updateCostBonusSchemeSet.do",formPara,function(responseData){
            
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
   
    function saveCostBonusSchemeSet(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
		liger.get("emp_kind_code").setValue("${emp_kind_code}");	
		liger.get("emp_kind_code").setText("${emp_kind_name}");
     }   
    
    function moveToLeft() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box2.getSelectedItems();
		if (!selecteds || !selecteds.length)
			return;
		box2.removeItems(selecteds);
		box1.addItems(selecteds);

	}
	function moveToRight() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box1.getSelectedItems();
		if (!selecteds || !selecteds.length)
			return;
		box1.removeItems(selecteds);
		box2.addItems(selecteds);
	}
	function moveAllToLeft() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box2.data;
		if (!selecteds || !selecteds.length)
			return;
		box1.addItems(selecteds);
		box2.removeItems(selecteds);
	}
	function moveAllToRight() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box1.data;
		if (!selecteds || !selecteds.length)
			return;
		box2.addItems(selecteds);
		box1.removeItems(selecteds);

	}
	
	function changeEmp(){
    	listbox1.clear();
    	
        listbox1.clearContent();
        
		listbox2.clear();
    	
        listbox2.clearContent();
        
    	para="";//清空参数
    	
		para = {
				scheme_id:'${scheme_id}',
				emp_kind_code : liger.get("emp_kind_code").getValue()
			};
    	
		 $("#listbox_ok").ligerListBox({
			parms : para,
			url : 'queryCostBonusMap.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 160,
			width : 200
		}); 

    } 
    </script>
  
  <style type="text/css">
       .middle input {
           display: block;width:30px; margin:2px;
       }
   </style>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
                <td align="left" class="l-table-edit-td"><input name="scheme_name" type="text" id="scheme_name" ltype="text"  value="${scheme_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_kind_code" type="text" id="emp_kind_code" ltype="text"  value="${emp_kind_code}" onblur="changeEmp();" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">显示顺序：</td>
                <td align="left" class="l-table-edit-td"><input name="order_no" type="text" id="order_no" ltype="text"  value="${order_no}" validate="{maxlength:4}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin-left: 200px">
		<tr>
			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">未选择的工资项</td>
			<td align="left"></td>
			<td align="left">已选择的工资项</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<div style="margin: 4px; float: left;">
					<div id="listbox_ok"></div>
				</div>
			</td>
			<td align="left">
				<div>
					<input type="button" onclick="moveToLeft()"  value="左      移" /> <br /><br />
					<input type="button" onclick="moveToRight()" value="右      移" /><br /><br />
					<input type="button" onclick="moveAllToLeft()" value="全部左移" /><br /><br />
					<input type="button" onclick="moveAllToRight()" value="全部右移" />
				</div>
			</td>
			<td align="left">
				<div style="margin: 4px; float: left;">
					<div id="listbox_no"></div>
				</div>
			</td>
		</tr>
	</table> 
    </body>
</html>

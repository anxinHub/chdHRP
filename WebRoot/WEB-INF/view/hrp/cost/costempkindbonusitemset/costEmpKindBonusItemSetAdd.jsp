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

	var para = {};
	
	var listbox1, listbox2;
	
	var width = 200;

     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        $("#listbox_ok").ligerListBox({
			url : 'queryCostEmpBonusItem.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 160,
			width : 200
		}); 
        
        listbox2 = $("#listbox_no").ligerListBox({
            isShowCheckBox: true,
            width: width,
            height : 160,
            data: [
            ]
        });
        
        
     });  
     
     function  save(){
    	 
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
    	 
    	 box2.selectAll();
    	 
    	 var bonusData = liger.get("listbox_no").getValue();
    	 
 			if(bonusData==""){
    		 
    		 $.ligerDialog.error('请选择工资项');
    		 
    	 }

        var formPara={

           emp_kind_code:liger.get("emp_kind_code").getValue(),
            
           bonus_code :bonusData
            
         };
        
        ajaxJsonObjectByUrl("addCostEmpKindBonusItemSet.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='emp_kind_code']").val('');
				 $("input[name='bonus_item_code']").val('');
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
   
    function saveCostEmpKindBonusItemSet(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	//autocomplete("#bonus_item_code","../queryBonusItemArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
     } 

    function changeEmp(){
    	listbox1.clear();
    	
        listbox1.clearContent();
        
    	para="";//清空参数
		para = {
				emp_kind_code : liger.get("emp_kind_code").getValue()
			};
    	
		 $("#listbox_ok").ligerListBox({
			parms : para,
			url : 'queryCostEmpBonusList.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 160,
			width : 200
		}); 

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
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin-left: 100px">

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_kind_code" type="text" id="emp_kind_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin-left: 100px">
		<tr>
			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">未选择的奖金项</td>
			<td align="left"></td>
			<td align="left">已选择的奖金项</td>
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

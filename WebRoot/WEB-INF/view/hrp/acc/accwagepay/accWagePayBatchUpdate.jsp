<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<style type="text/css">
.inp{
	border-left-width:0px;
	border-top-width:0px;
	border-right-width:0px;
	border-bottom-color:black;
	margin-top: 10px;
	padding-top: 5px
}

</style>
<script type="text/javascript">
	
     var dataFormat;
     
     var data;
     
     var item_column;
     
     var sum_column;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        addColumn();
        
        $("#layout1").ligerLayout({ topHeight: 130,centerWidth:750});
        
        $("#table_id").keydown(function(e) {
            var event = e || window.event;
            var _index = $(".inp").index($(event.target));    //获取当前编辑的单元格index值
            var trLength = $(this).find('tr:first').find('td[align="right"]').length;      //获取表格中一行的单元格数以便键盘上下时控制
            if (event.keyCode == 13 || event.keyCode == 39) {   //当回车到最后一格时再回车即转到第一格

                if (_index == $(".inp").index($(".inp:enabled").eq($(".inp:enabled").length -1))) {   //当回车到最后一个编辑格时再回车回到最开始的编辑格
                    _index = -1;
                };

                for (var i = _index + 1; i < $(".inp").length; i++) {
                    if (!$(".inp").eq(i).attr('disabled')) {
                        $(".inp").eq(i).focus();
                        break;
                    }
                };
            } else if (event.keyCode == 37) { //左
                if (_index == $(".inp").index($(".inp:enabled").eq(0)))    //当回车到最开始的编辑格时再回车回到最后的编辑格
                    _index = $(".inp").length;

                for (var a = _index - 1; a >= 0; a--) {
                    if (!$(".inp").eq(a).attr('disabled')) {
                        $(".inp").eq(a).focus();
                        break;
                    }
                }
            } else if (event.keyCode == 38) { //上      根据index值控制，键盘向上时即index - “一行单元格数”

                for (var n = _index - trLength; n >= 0; n -= trLength) {
                    if (!$(".inp").eq(n).attr('disabled')) {
                        $(".inp").eq(n).focus();
                        break;
                    }
                }
            } else if (event.keyCode == 40) { //下     同理

                for (var n = _index + trLength; n < $(".inp").length; n += trLength) {
                    if (!$(".inp").eq(n).attr('disabled')) {
                        $(".inp").eq(n).focus();
                        break;
                    }
                }
            }
        });
        
     });  
     
     function addColumn(){
    	 
    	 item_column="";
    	 
    	 sum_column="";
    	var param = {
			wage_code:'${wage_code}',
			acc_year:'${acc_year}',
			scheme_code:'${scheme_code}',
			is_stop:0
    	};
 		ajaxJsonObjectByUrl("queryTrendColumn.do?isCheck=false",param,function(responseData){
 			
 			$("#table_id").html("");
 			
 			var tHtml="";
              
 			$.each(responseData.Rows,function(index,value){
 				
 				sum_column+=","+value.column_item;

 				if(value.item_cal == 1){
 					
 					item_column+=","+value.column_item
 					
 				}
 				
 				if(index%7==0){
 					
 					tHtml=tHtml+"<tr>";
 					
 				}else if(index%7==0&&index != 0){
 					
 					tHtml=tHtml+"</tr>";
 				}
 				
 				if(value.item_cal == 1){
 					
 					tHtml=tHtml+"<td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>"+value.item_name+"：</td>"
 					+"<td align='left' class='l-table-edit-td'><input class='inp' name='"+value.column_item+"' type='text' id='"+value.column_item+"' ltype='text' validate='{required:true,maxlength:20}' style='width: 80px'/></td>"
 					+"<td align='left'></td>";
 					
 				}else{
 					
 					tHtml=tHtml+"<td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>"+value.item_name+"：</td>"
 					+"<td align='left' class='l-table-edit-td'><input class='inp' name='"+value.column_item+"' type='text' id='"+value.column_item+"' ltype='text' validate='{required:true,maxlength:20}' disabled='disabled' style='width: 80px' /></td>"
 					+"<td align='left'></td>";
 					 
 				}
 				
 	     	});
 			
 			$("#table_id").append(tHtml);
 			
         });
    	 
     }
     
	function save(){
		var exp = /^([-1-9+1+9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		var item="";
		var wage_item = sum_column.substring(1,sum_column.length).split(",");
		var flag = false;
		$(wage_item).each(function(index,value){
			var v = $("#"+wage_item[index]).val();
			if(v != ""){
				if(!exp.test(v)){
					var text = $("#" + value).parent().prev().text().replace("：", "");
					$.ligerDialog.warn("工资项【" + text + "】数字不合法.");
					flag = true;
					return;
				}
				item+=v+";";
			}else{
				item+=";";
			}
		});
		if(flag){
			return;
		}

		var acc_money = $("#money1").val();
		var acc_money1 = $("#money2").val();
		var acc_money2 = $("#money3").val();
		var acc_money3 = $("#money4").val();
		var acc_money4 = $("#money5").val();
		var item_code = liger.get("item_code").getValue();
		var item_code1 = liger.get("item_code1").getValue();
		var item_code2 = liger.get("item_code2").getValue();
		var item_code3 = liger.get("item_code3").getValue();
		var item_code4 = liger.get("item_code4").getValue();
		var wage_where="";var wage_where1="";var wage_where2="";var wage_where3="";var wage_where4="";
		
		if(item_code!=""&&$("#wage_item").val()!=""&&acc_money!=""){
			wage_where="awp." + item_code+" "+$("#wage_item").val()+" "+acc_money
		}
		if(item_code1!=""&&$("#wage_item1").val()!=""&&acc_money1!=""){
			wage_where1= "awp." + item_code1+" "+$("#wage_item1").val()+" "+acc_money1
		}
		if(item_code2!=""&&$("#wage_item2").val()!=""&&acc_money2!=""){
			wage_where2= "awp." + item_code2+" "+$("#wage_item2").val()+" "+acc_money2
		}
		if(item_code3!=""&&$("#wage_item3").val()!=""&&acc_money3!=""){
			wage_where3= "awp." + item_code3+" "+$("#wage_item3").val()+" "+acc_money3
		}
		if(item_code4!=""&&$("#wage_item4").val()!=""&&acc_money4!=""){
			wage_where4= "awp." + item_code4+" "+$("#wage_item4").val()+" "+acc_money4
		}
		
		var kind_code = "";
		if(liger.get("emp_kind").getValue().split(";").length > 0 && liger.get("emp_kind").getValue() != ''){
			for ( var i = 0; i < liger.get("emp_kind").getValue().split(";").length; i++){
				kind_code += "'" + liger.get("emp_kind").getValue().split(";")[i] + "',";
			}
		}
		var formPara={
			emp_id:liger.get("emp_code").getValue(),
			acc_year:'${acc_year}',
			acc_month:'${acc_month}',
			wage_code:'${wage_code}',
			dept_id:liger.get("dept_code").getValue().split(".")[0],
			kind_code:kind_code.slice(0, kind_code.length-1),
			pay_code:liger.get("emp_pay").getValue(),
			wage_where:wage_where,
			wage_where1:wage_where1,
			wage_where2:wage_where2,
			wage_where3:wage_where3,
			wage_where4:wage_where4,
			notes:$("#note").val(),
			item:item,
			item_column:sum_column
		};
 		ajaxJsonObjectByUrl("updateAccWagePayOfBatch.do?isCheck=false",formPara,function(responseData){
 			if(responseData.state=="true"){
 				 //btn_query();
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
   
    function loadDict(){
            //字典下拉框
    	autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true, '', false, '', 200);
    	
        autocompleteAsyncMulti("#emp_kind", "../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0", "id", "text", true, true);
    
        autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false&is_stop=0","id","text",true,true);
        
       // autocomplete("#dept_kind","../../sys/queryDeptKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
        
        autocomplete("#emp_pay", "../queryEmpPay.do?isCheck=false&is_stop=0", "id", "text", true, true);
        
    	$("#wage_code").ligerTextBox({disabled:true,width:200});
    	
    	$("#scheme_name").ligerTextBox({disabled:true,width:200});
    	
    	$("#acc_time").ligerTextBox({ disabled: true,width:160});
    	
    	/* $("#emp_code").ligerComboBox({
    		url: '../../sys/queryEmpDict.do?isCheck=false&is_stop=0', 
            isMultiSelect: false,
            onSelected: function (newvalue)
            {
            	addColumn();
            	
            }
        }); */
        
        var fromData = {

                'wage_code': '${wage_code}',

                'acc_year': '${acc_year}',

                'scheme_id': '${scheme_id}'

            }
        
        autocomplete("#item_code", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
        
        autocomplete("#item_code1", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
        
        autocomplete("#item_code2", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
        
		autocomplete("#item_code3", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
        
        autocomplete("#item_code4", "../queryAccWageItemColumn.do?isCheck=false&is_stop=0", "id", "text", true, true, fromData);
        
    	$("#item_code").ligerComboBox({
            selectBoxWidth: 80,
            autocomplete: true,
            width: 80
        });
        
        $("#item_code1").ligerComboBox({
            selectBoxWidth: 80,
            autocomplete: true,
            width: 80
        });
        
        $("#item_code2").ligerComboBox({
            selectBoxWidth: 80,
            autocomplete: true,
            width: 80
        });
        
        $("#item_code3").ligerComboBox({
            selectBoxWidth: 80,
            autocomplete: true,
            width: 80
        });
        
        $("#item_code4").ligerComboBox({
            selectBoxWidth: 80,
            autocomplete: true,
            width: 80
        });
        
        $("#wage_item").ligerTextBox({
            width: 40
        });
        
        $("#wage_item1").ligerTextBox({
            width: 40
        });
        
        $("#wage_item2").ligerTextBox({
            width: 40
        });
        
        $("#wage_item3").ligerTextBox({
            width: 40
        });
        
        $("#wage_item4").ligerTextBox({
            width: 40
        });
        
        $("#money1").ligerTextBox({
            width: 60
        });
        
        $("#money2").ligerTextBox({
            width: 60
        });
        
        $("#money3").ligerTextBox({
            width: 60
        });
    	
        $("#money4").ligerTextBox({
            width: 60
        });
        
        $("#money5").ligerTextBox({
            width: 60
        });
        $("#note").ligerTextBox({
            width: 160
        });
     } 

    //清空工资项文本框中的内容
    function clear_input(){
    	
    	$('input').filter('.inp').val("");
    }
    
   /*  function btn_query(){
    	
    	var emp_id=liger.get("emp_code").getValue();
    	
    	if(emp_id ==""){
    		
    		$.ligerDialog.error('职工为必填项');
        	
        	return;
    	}
    	
    	var formPara={
        		
    			wage_code:'${wage_code}',
        		
        		acc_year:$("#acc_time").val(),
        		
        		emp_id:emp_id,
        		
        		//emp_no:$("#emp_no").val(),
        		
        		column_item:sum_column
    	};
    	
		ajaxJsonObjectByUrl("queryAccEmpDetail.do?isCheck=false",formPara,function(responseData){
            
        	data = responseData.Rows[0];
        	
        	var item=sum_column.substring(1,sum_column.length).split(",");
   
        	if(data != null ){
        		
            	$.each(item,function(i,v){
            		
            		$("#"+v).val(data[v]);
            		
            	});
        		
        	}else{
        		
				$.each(item,function(i,v){
            		
            		$("#"+v).val("0");
            		
            	});
        		
        	}
        	
        });
    	
    } */
    
    function btn_close(){
    	
    	frameElement.dialog.close();
    	
    }
   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
	   
	  	<div id="layout1">
	  	
	  		<div position="top" title="  ">
	  		
			  	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		            <tr>
		                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
		                <td align="left" class="l-table-edit-td" colspan="3"><input name="wage_code" type="text" id="wage_code" ltype="text" value="${wage_name }" disabled="disabled"/></td>
		                <td align="left"></td>
		                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
		                <td align="left" class="l-table-edit-td" colspan="3"><input name="scheme_name" type="text" id="scheme_name" ltype="text" value="${scheme_name }" disabled="disabled"/></td>
		                <td align="left"></td>
		                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
		                <td align="left" class="l-table-edit-td" colspan="4"><input name="acc_time" type="text" id="acc_time" ltype="text" value="${acc_year}.${acc_month}" disabled="disabled"/></td>
		                <td align="left"></td>
		                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td>
		                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
		                <td align="left"></td>
		             </tr>
		           	 <tr>
		                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工名称：</td>
		                <td align="left" class="l-table-edit-td" colspan="3"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
		                <td align="left"></td>
		                <td align="right" class="l-table-edit-td"  style="padding-left:35px;">职工分类：</td>
			            <td align="left" class="l-table-edit-td" colspan="3"><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:18}" /></td>
		            	<td align="left"></td>
		            	<td align="right" class="l-table-edit-td" style="width:80px;">发放方式：</td>
			            <td align="left" class="l-table-edit-td" colspan="4"><input name="emp_pay" type="text" id="emp_pay" ltype="text" validate="{required:true,maxlength:18}" /></td>
		            	<td align="left"></td>
		            	<td align="right" class="l-table-edit-td"  style="width:80px;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
			         	<td align="left" class="l-table-edit-td" ><input name="note" type="text" id="note" /></td>
		            </tr>
		            <tr>
		                <td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code" type="text" id="item_code" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
						<td align="left"><input name="money1" type="text" id="money1"  ltype="text" validate="{required:true,maxlength:18}" />
		            	 <td align="left"></td>
		            	<td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code1" type="text" id="item_code1" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item1">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
			        	<td align="left"><input name="money2" type="text" id="money2"  ltype="text" validate="{required:true,maxlength:18}" />
		            	<td align="left"></td>
		            	<td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code2" type="text" id="item_code2" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item2">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
			        	<td align="left"><input name="money3" type="text" id="money3"  ltype="text" validate="{required:true,maxlength:18}" />
		            	<td align="left"></td>
		            </tr>
		            <tr>	
		            	<td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code3" type="text" id="item_code3" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item3">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
			        	<td align="left"><input name="money4" type="text" id="money4"  ltype="text" validate="{required:true,maxlength:18}" />
		            	<td align="left"></td>
		            	<td align="right" class="l-table-edit-td" >工资项：</td>
			            <td align="right" class="l-table-edit-td" ><input name="item_code4" type="text" id="item_code4" /></td>
			        	<td align="left" class="l-table-edit-td" ><select id="wage_item4">
			        	<option value="<">&lt;</option>
			        	<option value="<=">&lt;=</option>
			        	<option value=">">&gt;</option>
			        	<option value=">=">&gt;=</option>
			        	<option value="=">=</option>
			        	<option value="!=">!=</option>
			        	</select></td>
			        	<td align="left"><input name="money5" type="text" id="money5"  ltype="text" validate="{required:true,maxlength:18}" />
		            	<td align="left"></td>
		            	<!-- <td><input class="l-button" type="button" style="width: 70px;margin-left: 20px" id="query" value="查询" onclick="btn_query();" /></td> -->
					    <td align="right">
					    	<input class="l-button" type="button" style="width: 70px" id="query" value="保存(S)" onclick="save();" />
					    	<input class="l-button" type="button" style="width: 70px;margin-left: 5px" id="query" value="清空" onclick="clear_input();" />
					    	<input class="l-button" type="button" style="width: 70px;margin-left: 5px" id="query" value="关闭" onclick="btn_close();" />
					    </td>
		            </tr>
		        </table>
	  		</div>
	  		<div position="center" title="  ">
	  			
		  		<table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table_id">
		  			
		  		</table>
	  		</div>
	  	</div>
    </body>
</html>

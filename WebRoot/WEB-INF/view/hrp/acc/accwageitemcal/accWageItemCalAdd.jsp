<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>

    <script type="text/javascript">
     var dataFormat;
     
     $(function (){
    	 
        loadDict();
        
        //loadTree();
        
        (function($) {
       	 $.fn.extend({
       	   insertContent : function(myValue, t) {
       	   var $t = $(this)[0];
       	   if (document.selection) { // ie
       	    this.focus();
       	    var sel = document.selection.createRange();
       	    sel.text = myValue;
       	    this.focus();
       	    sel.moveStart('character', -l);
       	    var wee = sel.text.length;
       	    if (arguments.length == 2) {
       	    var l = $t.value.length;
       	    sel.moveEnd("character", wee + t);
       	    t <= 0 ? sel.moveStart("character", wee - 2 * t - myValue.length) : sel.moveStart( "character", wee - t - myValue.length);
       	    sel.select();
       	    }
       	   } else if ($t.selectionStart
       	    || $t.selectionStart == '0') {
       	    var startPos = $t.selectionStart;
       	    var endPos = $t.selectionEnd;
       	    var scrollTop = $t.scrollTop;
       	    $t.value = $t.value.substring(0, startPos)
       	     + myValue
       	     + $t.value.substring(endPos,$t.value.length);
       	    this.focus();
       	    $t.selectionStart = startPos + myValue.length;
       	    $t.selectionEnd = startPos + myValue.length;
       	    $t.scrollTop = scrollTop;
       	    if (arguments.length == 2) {
       	    $t.setSelectionRange(startPos - t,
       	     $t.selectionEnd + t);
       	    this.focus();
       	    }
       	   } else {
       	    this.value += myValue;
       	    this.focus();
       	   }
       	   }
       	  })
       	 })(jQuery);

        $("#wage_code").bind("change",function(){
        	
        	changeWageItem();
        	
        });
        
		$("#wage_code1").bind("change",function(){
			var fromData = {
				wage_code: liger.get("wage_code1").getValue()
			};
    		autocomplete("#item_name", "../queryAccWageItem.do?isCheck=false", "id", "text", true, true, fromData, true);
		});
	});  
     
     function  save(){
    	 
    	 var kind_code = liger.get("kind_code").getValue();
         
         var wage_code = liger.get("wage_code1").getValue();
         
         var acc_year = liger.get("acc_year").getValue();
         
         var item_name = liger.get("item_name").getValue();
         
         var cal_name = $("#formula_method_chs").val();
         
         var kind_name = $("#kind_code").val();
         
         if(kind_name =="全部"){
        	 
       	  kind_code ="0";
       	  
         }
        
         if(acc_year==""||kind_code ==""||wage_code==""||item_name==""||$.trim(cal_name)==""){
       	  
       	  $.ligerDialog.error('请完善信息！');
       	  
       	  return;
       	  
         }
    	 
    	 
        var formPara={
        		
           wage_code :wage_code,
           
           kind_code :kind_code,
           
           acc_year  :acc_year.split(".")[0],

           item_code:item_name,
           
           cal_name:$("#formula_method_chs").val()
            
         };
       	 
         ajaxJsonObjectByUrl("../accwagecal/addAccWageCal.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='formula_method_chs']").val('');
				 $("input[name='formula_method_eng']").val('');
                parent.query();
            }
        });
    }       
   
    function saveWageItemCal(){

            save();

    }
    
    var count = 0;
    
    function loadDict(){
    	
    	$("#layout1").ligerLayout({ leftWidth: 200,topHeight:30,centerBottomHeight:40 }); 
    	
    	autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true,'',true);
    	autocomplete("#wage_code1","../queryAccWage.do?isCheck=false","id","text",true,true,'',true);
		<%--
    	var fromData={
        	wage_code: liger.get("wage_code1").getValue()
    	};
    	autocomplete("#item_name","../queryAccWageItem.do?isCheck=false","id","text",true,true,fromData,true);
		--%>
        
    	$("#kind_code").ligerComboBox({
    		
        	url: '../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0',
        	
            emptyText: '全部',
        	
            valueField: 'id',
        	
         	textField: 'text', 
         	
         	selectBoxWidth: 160,
         	
        	autocomplete: true,
        	
        	width: 160
        	
		  });
    	
    	liger.get("kind_code").setValue("0");
		 
	   	liger.get("kind_code").setText("全部");
    	
    	$("#acc_year").ligerComboBox({
          	url: '../queryYearMonth.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160
 	 	});
    	
    	var year_Month = '${wage_year_month}';

	   	liger.get("acc_year").setValue(year_Month.substring(0,4));
			 
	   	liger.get("acc_year").setText(year_Month.substring(0,4));
    	
     } 
    
	function onSelect(note){

		if(note.data.pid!=0){

		    var wage_name= $("#wage_code").val();
		    
			$("#formula_method_chs").insertContent("{"+wage_name+"."+note.data.item_name+"}");
		    
	 	   // $("#formula_method_eng").insertContent("{"+wage_code+"."+note.data.item_name+"}");

		}

    }

	function buttonJia(){
		
		$("#formula_method_chs").insertContent('+');
 	    
	 	$("#formula_method_eng").insertContent('+');
		
	}
	
	function buttonZKH(){
		
		$("#formula_method_chs").insertContent('(');
 	    
	 	$("#formula_method_eng").insertContent('(');
		
	}
	
	function buttonCH(){
		
		$("#formula_method_chs").insertContent('/');
 	    
	 	$("#formula_method_eng").insertContent('/');
        
	}
	
	function buttonYKH(){
		
		$("#formula_method_chs").insertContent(')');
 	    
	 	$("#formula_method_eng").insertContent(')');
        
	}
	
	function buttonCheng(){
		
		$("#formula_method_chs").insertContent('*');
 	    
	 	$("#formula_method_eng").insertContent('*');
        
	}
	
	function buttonJH(){
		
		$("#formula_method_chs").insertContent('-');
 	    
	 	$("#formula_method_eng").insertContent('-');
		
	}
    
    function changeWageItem(){
    	
    	var wage_code = liger.get("wage_code").getValue();
    	
    	var formPara={
        		
 	           wage_code:wage_code,
 	           
 	          item_cal:'2'
 	            
 	         };
 	        
    	ajaxJsonObjectByUrl("queryAccWageItem.do", formPara,
    			
    			function(responseData) {
    			
    					if (responseData != null) {

    						tree = $("#tree1").ligerTree({
    							
    							data : responseData.Rows,
    							
    							checkbox : false,

    							idFieldName : 'item_code',
    							
    							parentIDFieldName : '0',
    							
    							textFieldName:'item_name',
    							
    							onSelect: onSelect,
    							
    							isExpand: 3,
    							
    							nodeWidth:190

    						});
    						
    						treeManager = $("#tree1").ligerGetTreeManager();
    						
    						treeManager.collapseAll();
    					}
    					
    				});
    	
    }
    
    </script>

  </head>
  
   <body style="padding:10px;margin:0;">
   <div id="pageloading" class="l-loading" style="display:none"></div>
   <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="3px" color="red">*</font>期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
		   	<td align="right" class="l-table-edit-td" style="padding-left:20px;"><font size="3px" color="red">*</font>工&nbsp; 资&nbsp; 套：</td>
		   	<td align="left" class="l-table-edit-td"><input name="wage_code1" type="text" id="wage_code1" ltype="text" validate="{required:true,maxlength:20}" /></td>
		    <td align="left"></td>
		 </tr>
		 <tr>   
		    <td align="right" class="l-table-edit-td" style="padding-left: 20px"><font size="3px" color="red">*</font>工资项目：</td>
		    <td align="left" class="l-table-edit-td"><input name="item_name" type="text" id="item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td" style="padding-left: 20px"><font size="3px" color="red">*</font>职工分类：</td>
		    <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
		    <td align="left"></td>
		</tr> 
	</table>
   <form name="form1" method="post"  id="form1" >
   <div id="layout1" style="width:100%; margin:40px;  height:70%;margin:0; padding:0;">
		   	
            <div position="left" title="工资项目"  style="width:98%; height:95%; overflow:auto;">
            	
            	<div style="height: 7%;width: 100%;padding-left: 17px;padding-top: 8px;">
            	 <input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}" />
            	</div>
            	<hr/>
            	<ul id="tree1"></ul>
            </div>
            <div position="center" title="计算公式（中文）">
            	<table cellpadding="0" cellspacing="0" class="l-table-edit" ><tr>
					<td align="left" class="l-table-edit-td">
	                	<textarea class="liger-textarea" id="formula_method_chs"  name="formula_method_chs" style="height:290px;width:555px;font-size:16px;resize: none;" validate="{required:true}" onblur="" ></textarea>
	              	    <textarea name="formula_method_eng" type="hidden" id="formula_method_eng" style="display: none"/></textarea>
	                </td>
                <tr/></table>
            </div>  
          	<div position="centerbottom">
          	
				<table align="center" >
					<tr>
		           		<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td"></td>
		           		<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
		           		<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
		           		<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
		           		<td align="left" style="padding:0px 0px 10px 40px;" class="l-table-edit-td"></td>
		           	</tr>
					<tr>
		           		<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td">
		           			<input class="liger-button" type="button" value="+" onClick="buttonJia();">
		           		</td>
		           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		           			<input class="liger-button" type="button" value="-" onClick="buttonJH();">
		           		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" type="button" value="*" onClick="buttonCheng();">
		          		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" type="button" value="/" onClick="buttonCH();">
		          		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" type="button" value="("  onClick="buttonZKH();">
		          		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" type="button" value=")"  onClick="buttonYKH();">
		          		</td>
		          	</tr>
		           
		     	</table>
          	</div>  
        </div>

            </form>
	            
   
    </body>
</html>

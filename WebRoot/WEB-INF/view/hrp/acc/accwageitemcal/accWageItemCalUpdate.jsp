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
        $("#formula_method_chs").val('${cal_name}');
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
        	
        })
     });  
     
     function  save(){
    	 
        var formPara={
        		
           wage_code :'${wage_code}',
           
           kind_code :'${kind_code}',
           
           item_code :'${item_code}',
           
           acc_year  :'${acc_year}',

           cal_name:$("#formula_method_chs").val()//,
            
           //cal_name_eng:"001.{岗位工资}+001.{薪级工资}"//$("#formula_method_eng").val()
           
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
    function loadDict(){
    	
    	$("#layout1").ligerLayout({ leftWidth: 200,topHeight:30,centerBottomHeight:40 }); 
    	
    	autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
  
     } 
    
	function onSelect(note){

		if(note.data.pid!=0){

		    var wage_name= $("#wage_code").val();
		    
		    var wage_code= liger.get("wage_code").getValue();
			
			$("#formula_method_chs").insertContent("{"+wage_name+"."+note.data.item_name+"}");
		    
	 	    $("#formula_method_eng").insertContent("{"+wage_code+"."+note.data.item_name+"}");

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
        		
 	           wage_code:wage_code
 	            
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
    							
    							nodeWidth:200

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
		   <td align="right" class="l-table-edit-td" >工资套：</td>
		   <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
		    <td align="left"></td>
		</tr> 
	</table>
   <form name="form1" method="post"  id="form1" >
   <div id="layout1" style="width:100%; margin:40px;  height:400px;margin:0; padding:0;">
		   	
            <div position="left" title="工资项目"  style="width:250px; height:367px; overflow:auto;">
            	<ul id="tree1"></ul>
            </div>
            <div position="center" title="计算公式（中文）">
            	<table cellpadding="0" cellspacing="0" class="l-table-edit" ><tr>
					<td align="left" class="l-table-edit-td">
	                	<textarea class="liger-textarea" id="formula_method_chs"  name="formula_method_chs" style="height:321px;width:555px;font-size:16px;resize: none;" validate="{required:true}" ></textarea>
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

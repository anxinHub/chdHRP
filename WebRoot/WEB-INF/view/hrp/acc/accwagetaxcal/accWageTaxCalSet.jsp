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
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>

    <script type="text/javascript">
     var dataFormat;
     var summary;
     $(function (){
    	
        loadDict();
        $("#formula_method_chs").val("${cal_name}");
        $("#jia").ligerButton({width:140});
        $("#jian").ligerButton({width:140});
        $("#cheng").ligerButton({width:140});
        $("#chu").ligerButton({width:140});
        $("#zkh").ligerButton({width:140});
        
        $("#ykh").ligerButton({width:140});
        $("#dh").ligerButton({width:140});
        $("#dyh").ligerButton({width:140});
        $("#xyh").ligerButton({width:140});
        $("#hz").ligerButton({width:140});
        
        $("#bq").ligerButton({width:140});
        $("#rg").ligerButton({width:140});
        $("#wage_data").ligerButton({width:140});
        $("#old_day").ligerButton({width:140});
        $("#qz").ligerButton({width:140});
        //在光标位置输入字符串
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
        
     });  
     
     function  save(){
    	
        var formPara={

           formula_code:$("#formula_code").val(),
            
           formula_name:$("#formula_name").val(),
            
           formula_method_chs:$("#formula_method_chs").val(),
            
           formula_method_eng:$("#formula_method_eng").val(),
           
           is_stop:$("#is_stop").val()
            
         };
        
        ajaxJsonObjectByUrl("addHpmFormula.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='formula_code']").val('');
				 $("input[name='formula_name']").val('');
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
    	
    	$("#layout1").ligerLayout({ leftWidth: 200,topHeight:30,centerBottomHeight:120 }); 
    	
    	var formPara={
        		
  	           wage_code:'${wage_code}',
  	           
  	           acc_year:'${acc_year}'/* ,
  	           
  	         item_cal:'1' */
  	            
  	         };
  	      
     	ajaxJsonObjectByUrl("../accwageitemcal/queryAccWageItem.do?isCheck=false", formPara,
     			
     			function(responseData) {
     			
     					if (responseData != null) {

     						tree = $("#tree1").ligerTree({
     							
     							data : responseData.Rows,
     							
     							checkbox : false,

     							idFieldName : 'id',
     							
     							parentIDFieldName : 'pId',
     							
     							textFieldName:'name',
     							
     							onSelect: onSelect,
     							
     							isExpand: 3,
     							
     							nodeWidth:296

     						});
     						
     						treeManager = $("#tree1").ligerGetTreeManager();
     						
     						treeManager.collapseAll();
     					}
     					
     				});
     } 
    
	function onSelect(note){
		
		if(note.data.pId==1){
			
			buttonWageData(note.data);
			
		}
	
		if(note.data.pId==0){
			
			$("#formula_method_chs").insertContent("{"+note.data.name+"}");
	 	    
		 	//$("#formula_method_eng").insertContent("{"+note.data.column_item+"}");
			
		}
    }

	function buttonJia(){
		
		$("#formula_method_chs").insertContent('+');
 	    
	 	//$("#formula_method_eng").insertContent('+');
		
	}
	function buttonZKH(){
		
		$("#formula_method_chs").insertContent('(');
 	    
	 	//$("#formula_method_eng").insertContent('(');
		
	}
	
	
	
	function buttonCH(){
		
		$("#formula_method_chs").insertContent('/');
 	    
	 	//$("#formula_method_eng").insertContent('/');
		
	}
	function buttonYKH(){
		
		$("#formula_method_chs").insertContent(')');
 	    
	 	//$("#formula_method_eng").insertContent(')');
		
	}
	
	
	function buttonCheng(){
		
		$("#formula_method_chs").insertContent('*');
 	    
		//$("#formula_method_eng").insertContent('*');
        
	}
	
	function buttonJH(){
		
		$("#formula_method_chs").insertContent('-');
 	    
		//$("#formula_method_eng").insertContent('-');
		
	}
	
	function buttonDH(){
		
		$("#formula_method_chs").insertContent('=');
 	    
		//$("#formula_method_eng").insertContent('=');
		
	}
	
	function buttonDYH(){
		
		$("#formula_method_chs").insertContent('>');
 	    
		//$("#formula_method_eng").insertContent('>');
		
	}
	
	function buttonXYH(){
		
		$("#formula_method_chs").insertContent('<');
 	    
		//$("#formula_method_eng").insertContent('<');
		
	}
	
	function buttonHZ(){
		
		$("#formula_method_chs").insertContent('或者');
 	    
		//$("#formula_method_eng").insertContent('or');
		
	}
	
	function buttonBQ(){
		
		$("#formula_method_chs").insertContent('并且');
 	    
		//$("#formula_method_eng").insertContent('and');
		
	}
	
	function buttonRG(){
		
		$("#formula_method_chs").insertContent('如果...则...否则...如果完');
 	    
		//$("#formula_method_eng").insertContent('case when ...then ... else ... end');
		
	}
	
	function buttonOldDay(){
		
		$("#formula_method_chs").insertContent('取上月自然天数');
 	    
		//$("#formula_method_eng").insertContent('wageLastMonthDays');
		
	}
	
	function buttonQZ(){
		
		$("#formula_method_chs").insertContent('取整');
 	    
		//$("#formula_method_eng").insertContent('wageLastMonthDays');
		
	}
	
    function saveAccWageTaxCal(){
    
    	var cal_name = $("#formula_method_chs").val(); 
    	
    	var cal_eng = $("#formula_method_eng").val();
    	
    	return cal_name+"#"+cal_eng;
    	
    }
    
    function buttonWageData(obj){
    	
    	$.ligerDialog.open({ url : 'accWageTaxSetCal.do?isCheck=false&ele_code='+obj.id,
    			data:{}, 
    			height: 340,
    			width: 755, 
    			title:'',
    			modal:true,
    			showToggle:false,
    			showMax:false,
    			showMin: false,
    			isResize:true,
    			buttons: [ { 
    				text: '确定', 
    				onclick: function (item, dialog) { 
    					
    					var cal_name =dialog.frame.saveAccItemCal();
    					
    					if(cal_name !=""){
    						
    						 $("#formula_method_chs").insertContent("{"+obj.name+""+cal_name.split("|")[0]+"}");
    						 $("#formula_method_eng").insertContent(obj.name+""+cal_name.split("|")[1]+"|");
    						 setTimeout(function (){
    	    					 dialog.close();  
    	    					},300)
    						     						 
    					}
    					
    					},cls:'l-dialog-btn-highlight' }, 
    				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    
    function changeWageItem(){
    	
    	var wage_code = liger.get("wage_code").getValue();
    	
    	var formPara={
        		
 	           wage_code:wage_code,
 	           
 	          item_cal:'1'
 	            
 	         };
 	        
    	ajaxJsonObjectByUrl("../accwageitemcal/queryAccWageItem.do", formPara,
    			
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
 
   <form name="form1" method="post"  id="form1" >
   <div id="layout1" style="width:100%; margin:40px;  height:400px;margin:0; padding:0;">
		   	
            <div position="left" title="工资项目"  style="height:404px; overflow:auto;">
            	<ul id="tree1"></ul>
            </div>
            <div position="center" title="计算公式（中文）">
            	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            	<tr>
						<td align="left" class="l-table-edit-td">
		                	<textarea class="liger-textarea" id="formula_method_chs"  name="formula_method_chs" style="height:270px;width:900px;font-size:16px;resize: none;" validate="{required:true}" ></textarea>
		              	    <input name="formula_method_eng" style="display: none;" type="text"  id="formula_method_eng" ltype="text" validate="{required:true,maxlength:2000}" />
		                </td>
	                <tr/>
	            </table>
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
		           			<input class="liger-button" id="jia" type="button" value="+" onClick="buttonJia();">
		           		</td>
		           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		           			<input class="liger-button" id="jian" type="button" value="-" onClick="buttonJH();">
		           		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="cheng" type="button" value="*" onClick="buttonCheng();">
		          		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="chu" type="button" value="/" onClick="buttonCH();">
		          		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="zkh" type="button" value="("  onClick="buttonZKH();">
		          		</td>
		          	</tr>
		           <tr>
		           		<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td">
		           			<input class="liger-button" id="ykh" type="button" value=")" onClick="buttonYKH();">
		           		</td>
		           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		           			<input class="liger-button" id="dh" type="button" value="=" onClick="buttonDH();">
		           		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="dyh" type="button" value=">" onClick="buttonDYH();">
		          		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="xyh" type="button" value="< " onClick="buttonXYH();">
		          		</td>
		          		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="hz" type="button" value="或者"  onClick="buttonHZ();">
		          		</td>
		          	</tr>
		          	<tr>
		           		<td align="left" style="padding:0px 0px 10px 10px;" class="l-table-edit-td">
		           			<input class="liger-button" id="bq" type="button" value="并且" onClick="buttonBQ();">
		           		</td>
		           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		           			<input class="liger-button" id="rg" type="button" value="如果...否则" onClick="buttonRG();">
		           		</td>
		           		<td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="qz" type="button" value="取整"  onClick="buttonQZ();">
		          		</td>
		          		 <td align="left" style="padding:0px 0px 10px 30px;" class="l-table-edit-td">
		          			<input class="liger-button" id="old_day" type="button" value="取上月自然天数" onClick="buttonOldDay();">
		          		</td>
		          	</tr>
		     	</table>
          	</div>  
        </div>

            </form>
	            
   
    </body>
</html>

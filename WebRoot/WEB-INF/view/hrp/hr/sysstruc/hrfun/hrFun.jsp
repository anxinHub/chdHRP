<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
  
<script type="text/javascript">
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var fun_para_code;//存储部件类型
    
    var dialog = frameElement.dialog;
    
    $(function ()
    {
        loadDict();

    	loadHead(null);	
        
        loadTree();

    });
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	//grid.options.parms.push({name:'fun_code',value:$("#fun_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '函数编码', name: 'fun_code', align: 'left',width:80},
                     
                     { display: '函数名称', name: 'fun_name', align: 'left',width:120},
                     
                     { display: '取值函数(中文)', name: 'fun_method_chs', align: 'left'}
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmFun.do?target_code=${target_code}',
                     width: '100%', height: '78%',pageSize:10,checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true, showBottom:false,
                     onSelectRow : function (rowdata, rowindex, value){

    					loadComType(rowdata.fun_code);
    					$("#fun_code").val(rowdata.fun_code);
    					
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();

    }


    function loadDict(){
    	$("#layout1").ligerLayout({ leftWidth: 200,topHeight:30,centerBottomHeight:120 }); 
    } 
    

    
    function onSelect(note){
        
        grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        if(note.data.pid != "-1"){
        	grid.options.parms.push({name:'fun_code',value:note.data.id}); 
        }

    	//加载查询条件
    	grid.loadData(grid.where);
    }
    
    function loadComType(obj){
    	ajaxJsonObjectByUrl("../hpmfunpara/queryComTypePara.do?isCheck=false&fun_code="+obj+"&target_code="+'${target_code}', {},
    			
    			function(responseData) {
    		
    					if (responseData != null) {

    						var obj = eval(responseData.Rows);
    						
    						fun_para_code = obj;
    						
    						var str = "";

    						var iw = $("#iw");
    						
    						$("#iw").empty();
    						
    						
    						
    						$.each(obj,function(i,v){
    							if((i+1)  % 4==0){
    								iw.append('<tr class="l-table-edit-tr">');
    							}
    							
    							if(this.com_type_nature == "text"){

    								iw.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;">'+this.para_name+'：</td>'+
    		    		            		'<td align="left" class="l-table-edit-td">'+
    		    		            		'<input name="'+this.para_code+'" ltype="text" validate="{required:true}" type="text" id="'+this.para_code+'"  />'+
    		    		            		'</td><td align="left"></td>');
    								loadTextStyle(this.para_code,this.fun_para_value);
    		    		        }else if(this.com_type_nature == "input"){
    		    		        	
    		    		        	iw.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;">'+this.para_name+'：</td>'+
    		    		            		'<td align="left" class="l-table-edit-td">'+
    		    		            		'<input name="'+this.para_code+'" ltype="text" validate="{required:true}" type="text" id="'+this.para_code+'"   />'+
    		    		            		'</td><td align="left"></td>');   
    		    		        	loadComBoBox(this.para_code,this.fun_para_value);
    		    		        }else if(this.com_type_nature == "date"){
    		    		        	iw.append('<td align="right" class="l-table-edit-td"  style="padding-left:20px;">'+this.para_name+'：</td>'+
    		    		            		'<td align="left" class="l-table-edit-td">'+
    		    		            		'<input name="'+this.para_code+'" ltype="text" validate="{required:true}" type="text" id="'+this.para_code+'" />'+
    		    		            		'</td><td align="left"></td>'); 
    		    		        	loadDate(this.para_code,this.fun_para_value);
    		    		        }
    							if((i+1)  % 4==0){
    								iw.append('</tr>');
    							}
    						});
    						
    						
    					}
    					
    				});
    	
    }

	function loadTextStyle(fc,fv){
		
		$("#"+fc+"").ligerTextBox({width:160 });
		
		$("#"+fc+"").ligerTextBox({width:160 });
		
		$("#"+fc+"").val(fv);
		
	}
	function loadDate(fc,fv) {
			autoCompleteByData("#"+fc+"",para_date.Rows,"para_data_code", "para_data_name", true, true, "");
			$("#"+fc+"").val(fv);
	}
	function loadComBoBox(fc,fv) {
		//alert(fc);
		//--指标编码
    	$("#"+fc).ligerComboBox({
    		parms : {"para_code":fc},
         	url: '../hpmfunparamethod/queryHpmFunParaByDict.do?isCheck=false',
         	valueField: 'ID',
          	textField: 'TEXT', 
          	selectBoxWidth: 160,
         	autocomplete: true,
         	width: 160,
    		onSuccess: function (data) {
    			this.setValue(fv);
    		}
		});
		
		
	
	}
	function loadTree(){  

		ajaxJsonObjectByUrl("../hpmfuntype/queryHpmFunTypeTree.do?isCheck=false", {},
		
			function(responseData) {
			
					if (responseData != null) {
						tree = $("#tree1").ligerTree({
							
							data : responseData.Rows,
							
							checkbox : false,
							
							idFieldName : 'id',
							
							parentIDFieldName : 'pid',
							
							textFieldName:'text',
							
							onSelect: onSelect,
							
							nodeWidth:400

						});
						
						treeManager = $("#tree1").ligerGetTreeManager();
						
						treeManager.collapseAll();
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
		             setTimeout(function(){
		            	 lable.ligerHideTip();
		                 lable.remove();
		               },1600) 
		             
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
	
	function saveFunStack(obj){

		var row = grid.getSelectedRow();
		
        if (!row) {
        	alert('请选择函数'); return;
        }
        loadForm();
        if($("form").valid() == false){
        	return;
        }
        target_code = obj.target_code
        var fun_para_value = [];
    	$.each(fun_para_code,function(i,v){
    			if(this.com_type_nature == "text"){
    				var val = $("#"+this.para_code).val();
    				var key = this.para_code;
    				fun_para_value.push({'target_code':obj.target_code,'fun_para_code':key,'fun_para_value':val});
    		    }else if(this.com_type_nature == "input"){
    		    	var val = liger.get(this.para_code).getValue();
    				var key = this.para_code;
    				fun_para_value.push({'target_code':obj.target_code,'fun_para_code':key,'fun_para_value':val});   	
    		    }else if(this.com_type_nature == "date"){
    		    	var val = $("#"+this.para_code).val();
    				var key = this.para_code;
    				fun_para_value.push({'target_code':obj.target_code,'fun_para_code':key,'fun_para_value':val});
    		    }
    	});
    	
		obj.fun_code = row.fun_code;
        
        obj.fun_note = row.fun_method_chs;
        
        obj.fun_name = row.fun_name;
        
        obj.fun_para_value = fun_para_value;
        
        
        dialog.close();
    }

</script>

</head>

<body style="padding: 0px; overflow:hidden;">
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<input type="hidden" id="fun_code"/>
		<div id="layout1" style="width:100%; margin:40px;  height:400px;margin:0; padding:0;">
		   	
            <div position="left" title="指标">
            	<ul id="tree1"></ul>
            </div>
            <div position="center" >
            	<div id="toptoolbar" ></div>
				<div id="maingrid"></div>
            </div>  
          	<div position="centerbottom">
          		<form name="form1" method="post" id="form1">
					<table cellpadding="0" cellspacing="0" class="l-table-edit" id="iw">
						
					</table>
				</form>
				
          	</div>  
        </div>
	
</body>
</html>

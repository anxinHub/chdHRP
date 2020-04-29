<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
   <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
  <link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    
    var gridManager = null;
    
    var fun_para_code;//存储部件类型
    
    var dialog = frameElement.dialog;
    
    var budg_year = "${budg_year}" ;
    
    
    $(function (){
    	
    	$("#layout1").ligerLayout({topHeight:30,leftWidth: 260,centerBottomHeight:120}); 
    	
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
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgFun.do',
                     width: '100%', height: '80%',pageSize:10,checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
    					           {text : '添加（<u>A</u>）',id : 'add',	click : addFun,	icon : 'add'},
    		                   	]},
                    onSelectRow : function (rowdata, rowindex, value){
    					loadComType(rowdata.fun_code);
    					$("#fun_code").val(rowdata.fun_code);
    					
    				} 
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    // 函数添加
	function addFun(){
    	
    	parent.$.ligerDialog.open({url: 'hrp/budg/common/budgfun/budgFunAddPage.do?isCheck=false', 
    		height: $(window).height(),width: $(window).width(),title:'函数添加',modal:true,showToggle:false,
			showMax:true,showMin: false,isResize:true,
			parentframename: window.name,
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
	 
	 function loadComType(obj){
		ajaxJsonObjectByUrl("queryComTypePara.do?isCheck=false&fun_code="+obj+"&budg_year="+budg_year+"&index_code=${index_code}"+"&index_type_code=${index_type_code}", {},
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
		//--指标编码
    	$("#"+fc).ligerComboBox({
    		parms : {"para_code":fc},
         	url: 'queryBudgFunParaByDict.do?isCheck=false',
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
    function saveSelectFun(){
    	
		var row = grid.getSelectedRow();
		
        if (!row) {
        	$.ligerDialog.error('请选择函数'); 
        	return;
        }
        loadForm();
        
        if($("form").valid() == false){
        	return;
        }
        index_code = "${index_code}";
        index_type_code = "${index_type_code}" //指标类型   从各个配置页面 传  01 基本指标 02 费用标准 03 预算指标 04 收入科目
        var ParamVo = [];
    	$.each(fun_para_code,function(i,v){
    			if(this.com_type_nature == "text"){
    				var val = $("#"+this.para_code).val();
    				var key = this.para_code;
    				ParamVo.push({'budg_year':budg_year,'index_code':index_code,'fun_para_code':key,'fun_para_value':val,"index_type_code":index_type_code});
    		    }else if(this.com_type_nature == "input"){
    		    	var val = liger.get(this.para_code).getValue();
    				var key = this.para_code;
    				ParamVo.push({'budg_year':budg_year,'index_code':index_code,'fun_para_code':key,'fun_para_value':val,"index_type_code":index_type_code});   	
    		    }else if(this.com_type_nature == "date"){
    		    	var val = $("#"+this.para_code).val();
    				var key = this.para_code;
    				ParamVo.push({'budg_year':budg_year,'index_code':index_code,'fun_para_code':key,'fun_para_value':val,"index_type_code":index_type_code});
    		    }
    	});
    	
    	parentFrameUse().liger.get("fun_id").setValue(row.fun_code);

		parentFrameUse().dataStack = JSON.stringify(ParamVo) ;
		/* // 存储 预算指标函数参数栈数据 
		ajaxJsonObjectByUrl("saveBudgIndexStack.do?isCheck=false",{ParamVo : JSON.stringify(ParamVo)},function(responseData){
            
            if(responseData.state=="true"){
				
            }
        }); */
		
        setTimeout(function(){
			dialog.close();
		},100)
    }
    
    function loadDict(){
    	//函数类型 
    	autocomplete("#type_code","../../qureyBudgFunType.do?isCheck=false","id","text",true,true);
    } 
    
    function onSelect(event, treeid, treeNode) {
		grid.options.parms = [];
		//根据表字段进行添加查询条件
		grid.options.parms.push({ name: 'type_code', value: treeNode.id });

		//加载查询条件
		grid.loadData(grid.where);
	}
	// 函数类型树
	function loadTree() {
		ajaxJsonObjectByUrl("queryFunTypeTree.do?isCheck=false", {},
			function (responseData) {
				if (responseData.Rows.length != 0) {
					tree = $.fn.zTree.init($('#typeTree'), {
						checkbox: false,
						data: {
							key: {
								name: 'text'
							}
						},
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId"
						},
						callback: {
							onClick: onSelect
						}
					}, responseData.Rows);
				}
			}
		);
	}

</script>
</head>

<body style="padding: 0px; overflow:hidden;">
		<div id="pageloading" class="l-loading" style="display: none"></div>
		<input type="hidden" id="fun_code"/>
		<div id="layout1" style="width:100%; margin:40px;  height:400px;margin:0; padding:0;">
		   	<div position="left" title="">
	           	<div>
					<table>
		            	<tr>
		            		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>函数类型:</b></td>
		            		<td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" /></td>
		            	</tr>
		            </table> 
				</div>
	           	<div id="typeTree" class="ztree" ></div>
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

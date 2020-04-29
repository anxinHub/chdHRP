<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=path%>/lib/build/lang/zh-cn/zh-cn.js"></script>
    
    <script type="text/javascript">
    var grid;
    var gridManager = null;
     var dataFormat;
     var ue ;
     $(function (){
    	 ue = UE.getEditor('fun_sql',{        // 放在这里是以免光标获取不到
 			autoHeightEnabled: false,
 			autoFloatEnabled: false,
 			maximumWords:500000
 		});
         loadDict()//加载下拉框
         loadHead(null);	
         loadForm();
         $("#layout1").ligerLayout({ leftWidth: 400,allowLeftResize:true });
     });  
     
     function loadHead(){

     	grid = $("#maingrid").ligerGrid({
            columns: [ 
            		{ display: '函数参数', name: 'para_code', textField: 'para_name' , align: 'left',width:180,
					editor: {
						type : 'select',
						valueField: 'id', 
						textField: 'text',
						url : '../../queryBudgFunParaMethod.do?isCheck=false',
						keySupport:true,
				      	autocomplete: true
						}
		      		},
 					{ display: '参数类型', name: 'com_type_code',textField: 'com_type_name', align: 'left',width:110,
		      			valueField: 'type_code', textField: 'type_name',
							editor: {
								type : 'select',
								valueField: 'type_code', 
								textField: 'type_name',
								url : 'queryBudgComType.do?isCheck=false',
								keySupport:true,
						      	autocomplete: true
								}
				      },
                   	{ display: '序号', name: 'stack_seq_no', align: 'center',width:50}
                      ],
                      usePager:false,url:'',
                      width: '100%', height: '50%', checkbox: true,rownumbers:false,enabledEdit : true,isAddRow:false ,
                      selectRowButtonOnly:true,title:"参数",
                      toolbar: { items: [
                    	  {
								text : '增加一行',
								id : 'addRow',
								click : is_addRow,
								icon : 'add'
							},
                    	     {
								line : true
							},
							{
								text : '删除',
								id : 'delete',
								click : remove,
								icon : 'delete'
							}
						 ]}
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
     
     function remove(){
    	 
 		grid.deleteSelectedRow();
 		
 		setTimeout(function(){
 			var data = grid.getData();
 			$.each(data, function(i, v) {
 	 			grid.updateCell("stack_seq_no",i+1,i);
 	 		})
 		},500)
 		
 		
     }
     
     function  save(){
    	 
    	 gridManager.endEdit();
    	 
  		var para_fun_para_data = gridManager.getData();
  		
  		if(!validateGrid(para_fun_para_data)){
  			
  			return false ;
  		}
        var formPara={
            
           fun_code:$("#fun_code").val(),
            
           fun_name:$("#fun_name").val(),
            
           type_code:liger.get("type_code").getValue(),
            
           fun_note:$("#fun_note").val(),
            
           fun_sql:UE.getEditor('fun_sql').getPlainTxt(),
           
           prc_name:$("#prc_name").val(),
           
           pkg_name:liger.get("pkg_name").getValue(),
            
           is_pre:'0',
            
           is_rec:liger.get("is_rec").getValue(),
            
           is_stop:liger.get("is_stop").getValue(),
           
           para_fun_para_data:JSON.stringify(para_fun_para_data)
            
         };
        
        ajaxJsonObjectByUrl("addBudgFun.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='fun_code']").val('');
				 $("input[name='fun_name']").val('');
				 $("input[name='type_code']").val('');
				 $("input[name='fun_note']").val('');
				 $("input[name='is_rec']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='pkg_name']").val('');
				 ue.setContent("");
				 grid.reload();
				 $("input[name='prc_name']").val('');
				 parentFrameUse().query();
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
   
    function saveBudgFun(){
        if($("form").valid()){
            save();
        }
   }
    
    function validateGrid(data) {  
    	
    	if (data.length == 0) {
    		
			$.ligerDialog.warn("无参数数据保存");
			
			return false;
		}
    	
     	var msg="";
  		var rowm = "";
  		//判断grid 中的数据是否重复或者为空
  		var targetMap = new HashMap();
  		$.each(data,function(i, v){
  			rowm = "";
 			if (!v.para_code) {
 				rowm+="[函数参数]、";
 			}
 			if (!v.com_type_code) {
 				rowm+="[参数类型]、";
 			}
 			if(rowm != ""){
 				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
 			}
 			msg += rowm;
 			var key=v.para_code 
 			var value="第"+(i+1)+"行";
 			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
 				targetMap.put(key ,value);
 			}else{
 				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
 			}
  		});
  		if(msg != ""){
  			$.ligerDialog.warn(msg);  
 			return false;  
  		}else{
  			return true;  
  		} 	
	}
    
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_code","../../qureyBudgFunType.do?isCheck=false","id","text",true,true); 
            
    	autocomplete("#pkg_name","../../queryBudgOraclePkg.do?isCheck=false","id","text",true,true);     
    	
            
    	$("#save").ligerButton({click: saveBudgFun, width:90});
    	$("#close").ligerButton({click: this_close, width:90});
    	$("#intiProcBody").ligerButton({click: intiProcBody, width:120});
    	
     } 
    
    function intiProcBody(){
    	var pkg_name = liger.get("pkg_name").getValue();
    	var prc_name = $("#prc_name").val();
    	var para_data = gridManager.rows;
    	var str = "PROCEDURE "+prc_name+"<br/>(<br/>";
    	$.each(para_data,function(i,v){
    		 if(isnull(v.para_code)){
    			 return;
    		 }
    	     str = str + "&nbsp;&nbsp;"+v.para_code+" IN VARCHAR2,<br/>";
		});	
    	str = str + "&nbsp;&nbsp;BUDG_INDEXCODE OUT NUMBER,<br/>"
		+   "&nbsp;&nbsp;BUDGERRTXT OUT VARCHAR2<br/>"
		+")<br/>&nbsp;&nbsp;IS&nbsp;&nbsp;<br/><br/>"
		+"BEGIN&nbsp;&nbsp;<br/><br/>"
		+"EXCEPTION&nbsp;&nbsp;<br/>"
	    +"WHEN OTHERS THEN&nbsp;&nbsp;<br/><br/>"
		+"END&nbsp;&nbsp; "+prc_name+";"; 
    	ue.setContent(str);
    	return false;
    }
    
    function this_close(){
		frameElement.dialog.close();
	}
    function is_addRow() {
    	var data = gridManager.getData();
    	
		grid.addRow({
			para_code:'',
			com_type_code :'',
			stack_seq_no : data.length+1
		});

	}
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   
   	<div id="layout1" style="height: 100%;">
            <div  position="left" title="函数" style="left: 0px; top: 0px;  height: 100%;">
            	<div class="l-layout-content" position="left" style="height:100%;overflow: auto;">
            	<form name="form1" method="post"  id="form1" >
					<table cellpadding="0" cellspacing="0" class="l-table-edit" style="float: left;" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">函数编码：</td>
                <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">函数名称：</td>
                <td align="left" class="l-table-edit-td"><input name="fun_name" type="text" id="fun_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">存储过程名称：</td>
                <td align="left" class="l-table-edit-td"><input name="prc_name" type="text" id="prc_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">存储过程包名：</td>
                <td align="left" class="l-table-edit-td"><input name="pkg_name" type="text" id="pkg_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">函数分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                		<select name="is_stop" id="is_stop"  validate="{required:true}">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
				</td>
                <td align="left"></td>
            </tr> 
          
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否递归：</td>
                <td align="left" class="l-table-edit-td">
                		<select name="is_rec" id="is_rec"  validate="{required:true}">
							<option value="0">否</option>
							<option value="1">是</option>	
						</select>
				</td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">函数说明：</td>
                <td align="left" class="l-table-edit-td"  colspan="4">
                	<textarea rows="2" cols="40" class="liger-textarea" id="fun_note" name="fun_note" ></textarea>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td" colspan="3"><div id="maingrid"></div></td>
            </tr>
            
        </table>
        </form>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 10px;">
			<tr style="padding-top: 100px;">	
				<td align="center" class="l-table-edit-td" >
					<button id ="intiProcBody"><b>生成代码体</b></button>
					&nbsp;&nbsp;
					<button id ="save"><b>保存</b></button>
					&nbsp;&nbsp;
					<button id ="close"><b>关闭</b></button>
				</td>
			</tr>
		</table>
				</div>
            </div>
            <div position="center" title="函数SQL"  style="left:width: 975px; height: 100%;">
            	<div class="l-layout-content" style="height: 100%;"
				position="center">
				<table style="float: left;">
        	 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                	<div>
    				<textarea id="fun_sql" style="width:100%;height:75%;"></textarea>
					</div>
                </td>
                <td align="left"></td>
            </tr> 
        	<tr>
				<td align="left" class="l-table-edit-td" colspan="4" >
					<font color="red">注：</font>
					返回代码：BUDG_INDEXCODE[DEF_OK,DEF_ERR,DEF_WARNING]
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					返回信息：BUDG_ERRTXT[字符串||SQLERRM]
				</td>
			</tr>
       </table>
			</div>
            </div>
        </div> 
   
    </body>
</html>

<%-- 
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
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
           fun_code:$("#fun_code").val(),
            
           fun_name:$("#fun_name").val(),
            
           type_code:liger.get("type_code").getValue(),
            
           fun_method_chs:$("#fun_method_chs").val(),
            
           fun_method_eng:$("#fun_method_eng").val(),
            
           fun_note:$("#fun_note").val(),
            
           is_pre:$("#is_pre").val(),
            
           is_rec:$("#is_rec").val(),
            
           is_stop:$("#is_stop").val(),
           
           para_value:$("#para_value").val()
            
         };
        
        ajaxJsonObjectByUrl("addBudgFun.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='fun_code']").val('');
				 $("input[name='fun_name']").val('');
				 $("input[name='type_code']").val('');
				 $("input[name='fun_method_chs']").val('');
				 $("input[name='fun_note']").val('');
				 $("input[name='is_pre']").val('');
				 $("input[name='is_rec']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='para_name']").val('');
				 $("textarea[name='fun_method_eng']").val('');
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
	     //$("form").ligerForm();
	 }       
   	
	function openComTypeAddPage(){
		 $.ligerDialog.open({
				url: '../budgcomtype/budgComTypeAddPage.do?isCheck=false', 
				height: 350,
				width: 700, 
				title:'部件类型',
				data: {
		            name: $("#para_value").val()
		        },
		        buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savePrmComType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
				
			});
	}
    function saveBudgFun(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    		autocomplete("#type_code","../../qureyBudgFunType.do?isCheck=false","id","text",true,true,'',false,'',200);
            
    		$("#fun_code").ligerTextBox({width:200});
    		$("#fun_name").ligerTextBox({width:200});
    		$("#type_code").ligerTextBox({width:200});
    		$("#fun_method_chs").ligerTextBox({width:200});
    		$("#is_pre").ligerTextBox({width:200});
    		$("#is_rec").ligerTextBox({width:200});
    		$("#is_stop").ligerTextBox({width:200});
    		$("#para_name").ligerTextBox({width:200});
    		
            $("#fun_note").ligerTextBox({width:500});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>函数编码<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>函数名称:</b></td>
            <td align="left" class="l-table-edit-td"><input name="fun_name" type="text" id="fun_name" ltype="text" validate="{required:true,maxlength:40}" /></td>
            <td align="left"></td>
         </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>函数分类编码:</b></td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>取值函数(中文):</b></td>
            <td align="left" class="l-table-edit-td"><input name="fun_method_chs" type="text" id="fun_method_chs" ltype="text" validate="{required:true,maxlength:1000}" /></td>
            <td align="left"></td>
         </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否执行:</b></td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_pre" name="is_pre" style="width: 135px;" >
	                	<option value="0">否</option>
	                	<option value="1">是</option>	
              	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否递归:</b></td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_rec" name="is_rec" style="width: 135px;" >
	                	<option value="0">否</option>
	                	<option value="1">是</option>	
              	</select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用:</b></td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_stop" name="is_stop" style="width: 135px;" >
	                	<option value="0">否</option>
	                	<option value="1">是</option>	
              	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b>查询条件:</b></td>
			<td align="left" class="l-table-edit-td">
				<input name="para_name" type="text" id="para_name" ltype="text" validate="{required:true}"/>
				<input type="hidden" name="para_value" id="para_value"/>
			</td>
			<td align="left"><input class="liger-button" onClick="openComTypeAddPage()" value="选择条件"/></td>
        </tr>
        <tr> 
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>函数说明:</b></td>
            <td align="left" class="l-table-edit-td" colspan="4" ><input name="fun_note" type="text" id="fun_note" ltype="text" validate="{maxlength:200}" /></td>
            <td align="left"></td>
        	
		</tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>取值函数(英文):</b></td>
            <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="16" class="liger-textarea" id="fun_method_eng" name="fun_method_eng" style="width:500px" validate="{maxlength:1000}" ></textarea>
            </td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
   var ue ;
   var grid;
   var gridManager = null;
   var dataFormat;
   $(function (){
	   
	   ue = UE.getEditor('fun_sql',{        // 放在这里是以免光标获取不到
			autoHeightEnabled: false,
			autoFloatEnabled: false,
			maximumWords:500000
		});
	   ue.ready(function() {
		  	 ue.setContent("${fun_sql}");
		   });
      loadDict();
      loadHead(null);	
      loadForm();
      $("#layout1").ligerLayout({ leftWidth: 400,allowLeftResize:true }); 
   });  
   
   function  query(){
   	
   	grid.options.parms=[];
   	grid.options.newPage=1;
       //根据表字段进行添加查询条件 
   	//加载查询条件
   	grid.options.parms.push({name:'fun_code',value:'${fun_code}'});
   	grid.loadData(grid.where);
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
         
         is_pre: '0',
         
         is_rec: liger.get("is_rec").getValue(),
         
         is_stop: liger.get("is_stop").getValue(),
         
         para_fun_para_data:JSON.stringify(para_fun_para_data)

       };
      ajaxJsonObjectByUrl("updateBudgFun.do",formPara,function(responseData){
          
          if(responseData.state=="true"){
          	parentFrameUse().query();
          }
      });
	}
   
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
			      	autocomplete: true,
					}
	      		},
					{ display: '参数类型', name: 'com_type_code',textField: 'com_type_name', align: 'left',width:110,
	      			editor: {
					type : 'select',
					valueField: 'type_code', 
					textField: 'type_name',
					url : 'queryBudgComType.do?isCheck=false',
					keySupport:true,
			      	autocomplete: true
					}
			      },
                  	{ display: '序号', name: 'stack_seq_no', align: 'right',width:50}
                     ],
                     usePager:false,url:'queryBudgFunParaByFunCode.do?isCheck=false&fun_code=${fun_code}',
                     width: '100%', height: '40%', checkbox: true,rownumbers:false,enabledEdit : true,isAddRow:false ,
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
    
	function is_addRow() {
		var data = gridManager.getData();
		
		grid.addRow({
			para_code:'',
			com_type_code :'',
			stack_seq_no : data.length+1
		});
	}
    
    function remove(){
  	  var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){	
            	if(!this.group_id){
            		
					gridManager.deleteSelectedRow();
					
				}else{
					ParamVo.push(
						this.group_id   +"@"+ 
						this.hos_id   +"@"+ 
						this.copy_code   +"@"+ 
						this.fun_code   +"@"+ 
						this.para_code
					);
				}
            });
            if(ParamVo.length == 0 ){
            	setTimeout(function(){
    	 			var data = grid.getData();
    	 			console.log(data)
    	 			$.each(data, function(i, v) {
    	 	 			grid.updateCell("stack_seq_no",i+1,i);
    	 	 		})
    	 		},500)
				return;
			}
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteBudgFunParaByFunCode.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                			setTimeout(function(){
                	 			var data = grid.getData();
                	 			$.each(data, function(i, v) {
                	 	 			grid.updateCell("stack_seq_no",i+1,i);
                	 	 		})
                	 		},1000)
                	 		
                		}
                	});
            	}
            }); 
        }
			
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
	function validateGrid(data) {  
    	
    	if (data.length == 0) {
    		
			$.ligerDialog.warn("无参数数据,不能保存");
			
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
   function saveBudgFun(){
       if($("form").valid()){
           save();
       }
 	}
   function loadDict(){
   	
   	//$("#fun_note").ligerTextBox({width:440 });
   	
   	$("#fun_code").ligerTextBox({width:160,disabled:true});
   	
   	$("#prc_name").ligerTextBox({width:160,disabled:true});
   	
   	$("#fun_method_chs").ligerTextBox({width:160 });
   	
   	$("#para_name").ligerTextBox({width:160 });
   	
   	$("#fun_name").ligerTextBox({width:160 });
   	
   	$("#is_rec").ligerComboBox({width:160 });
   	
   	$("#is_stop").ligerComboBox({width:160 });

   	autocomplete("#type_code","../../qureyBudgFunType.do?isCheck=false","id","text",true,true);
   	
   	liger.get("type_code").setValue("${type_code}");
   	
	liger.get("type_code").setText("${type_code} ${type_name}");
	
	autocomplete("#pkg_name","../../queryBudgOraclePkg.do?isCheck=false","id","text",true,true);  
	
	liger.get("pkg_name").setValue("${pkg_name}");
   	
	liger.get("pkg_name").setText("${pkg_name}");
	
	if('0' == '${is_rec}'){
		
		liger.get("is_rec").setValue("0");
		
		liger.get("is_rec").setText("否");
		
	}else{
		
		liger.get("is_rec").setValue("1");
		
		liger.get("is_rec").setText("是");
		
	}
	
	if('0' == '${is_stop}'){
		
		liger.get("is_stop").setValue("0");
		
		liger.get("is_stop").setText("否");
		
	}else{
		
		liger.get("is_stop").setValue("1");
		
		liger.get("is_stop").setText("是");
		
	}

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
   	     str = str + "&nbsp;&nbsp;"+v.para_code+" IN NUMBER, <br/>";
	});	
   	str = str + "&nbsp;&nbsp;BUDG_APPCODE OUT NUMBER,&nbsp;&nbsp;<br/>"
	+   "&nbsp;&nbsp;BUDG_ERRTXT OUT VARCHAR2&nbsp;&nbsp;<br/>"
	+") IS&nbsp;&nbsp;<br/>"
	+"BEGIN&nbsp;&nbsp;<br/><br/>"
	+"EXCEPTION&nbsp;&nbsp;<br/>"
    +"WHEN OTHERS THEN&nbsp;&nbsp;<br/><br/>"
	+"END "+prc_name+";"; 
   	ue.setContent(str);
   	return false;
   }
	function this_close(){
		frameElement.dialog.close();
	}
   </script>
 
 </head>
 
<body>
	<div id="layout1" style="height: 100%;">
		<div  position="left" title="函数" style="left: 0px; top: 0px;  height: 100%;">
			<div class="l-layout-content" position="left" style="height:100%;overflow: auto;">
			    <form name="form1" method="post"  id="form1" >
					<table cellpadding="0" cellspacing="0" class="l-table-edit" >
					
					    <tr>
						    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">函数编码：</td>
						    <td align="left" class="l-table-edit-td"><input name="fun_code"  type="text" id="fun_code" ltype="text" value="${fun_code}" validate="{required:true}" /></td>
						    <td align="left"></td>
							</tr> 
						<tr>
						    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">函数名称：</td>
						    <td align="left" class="l-table-edit-td"><input name="fun_name" type="text" id="fun_name" ltype="text" value="${fun_name}" validate="{required:true}" /></td>
						    <td align="left"></td>
						</tr> 
						<tr>
						    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">存储过程名称：</td>
						    <td align="left" class="l-table-edit-td"><input name="prc_name"  type="text" id="prc_name" ltype="text" value="${prc_name}" validate="{required:true}" /></td>
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
					    		<textarea rows="3" cols="40" class="liger-textarea" id="fun_note" name="fun_note">${fun_note }</textarea>
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
			<div class="l-layout-content" style="height: 100%;"position="center">
				<table style="float: left;">
					<tr>
				    	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
				        <td align="left" class="l-table-edit-td" colspan="4">
				        	<div>
								<textarea id="fun_sql" style="width:100%;height:75%;" style="overflow:scroll;"></textarea>
							</div>
				         </td>
				         <td align="left"></td>
				    </tr> 
				    <tr>
						<td align="left" class="l-table-edit-td" colspan="4" >
							<font color="red">注：</font>
							返回代码：BUDG_APPCODE[DEF_OK,DEF_ERR,DEF_WARNING]
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

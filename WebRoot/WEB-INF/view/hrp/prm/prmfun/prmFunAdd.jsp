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
     var dataFormat,ue;
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
            		{ display: '函数参数', name: 'para_code', textField: 'para_name' , align: 'left',width:140,
					editor: {
						type : 'select',
						valueField: 'id', 
						textField: 'text',
						url : '../queryPrmFunParaMethod.do?isCheck=false',
						keySupport:true,
				      	autocomplete: true,
				      	selectBoxWidth: 240
						}
		      		},
 					{ display: '参数类型', name: 'com_type_code',textField: 'com_type_name', align: 'left',width:110,
							editor: {
								type : 'select',
								valueField: 'id', 
								textField: 'text',
								url : '../quertPrmComType.do?isCheck=false',
								keySupport:true,
						      	autocomplete: true
								}
				      },
                   	{ display: '序号', name: 'stack_seq_no', align: 'right',width:50,
	 				    	 editor: {type:'int'}}
                      ],
                      usePager:false,url:'',
                      width: '100%', height: '50%', checkbox: true,rownumbers:false,enabledEdit : true,
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
 		gridManager.deleteSelectedRow();
 			
     }
     
     function  save(){
    	 gridManager.endEdit();
  		var para_fun_para_data = gridManager.rows;
    	  
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
        
        ajaxJsonObjectByUrl("addPrmFun.do",formPara,function(responseData){
            
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
   
    function savePrmFun(){
        if($("form").valid()){
            save();
        }
   }
    
    
    
    function validateGrid() {
 		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			var key = v.para_code + "|" + v.com_type_code;
			var value = "第" + (i + 1) + "行";
			if (isnull(v.para_code)) {
				gridManager.deleteRow(i);
				return;
			}
			if (isnull(v.para_code)) {
				msg += "[函数参数]、";
			}
			if (isnull(v.com_type_code)) {
				msg += "[参数类型]、";
			} 
			if (msg != "") {
				msgMap.put(value+msg+"不能为空或不能为零！\n\r", "");
			}
			if (isnull(targetMap.get(key))) {
				targetMap.put(key, value);
			} else {
				msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r", value);
			}
		});
		
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	} 
    
    
    
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_code","../queryPrmFunType.do?isCheck=false","id","text",true,true); 
            
    	autocomplete("#pkg_name","../queryPrmOraclePkg.do?isCheck=false","id","text",true,true);     
    	
            
    	$("#save").ligerButton({click: savePrmFun, width:90});
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
    	str = str + "&nbsp;&nbsp;PRM_APPCODE OUT NUMBER,<br/>"
		+   "&nbsp;&nbsp;PRM_ERRTXT OUT VARCHAR2<br/>"
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
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);

	}
    </script>
  
  </head>
  
   <body onload="is_addRow()">
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
					返回代码：PRM_APPCODE[DEF_OK,DEF_ERR,DEF_WARNING]
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					返回信息：PRM_ERRTXT[字符串||SQLERRM]
				</td>
			</tr>
       </table>
			</div>
            </div>
        </div> 
   
    </body>
</html>

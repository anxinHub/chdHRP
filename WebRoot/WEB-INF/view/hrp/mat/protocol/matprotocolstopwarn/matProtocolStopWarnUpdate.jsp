<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     var detailGrid;
	 var feilGrid;
	 var invGrid;
     var detailManager = null;
     var fileManager = null;
     var obj;
     $(function (){
         loadDict()//加载下拉框
         loadForm();
         loadHead("detail");
        
         
		 
		 $("#save").click(function(){
 	    	if($("form").valid()){
 	            save();
 	        }
 	     });
		 $("#detail").click(function(){
			 $("#filegrid").hide();
			 $("#invgrid").hide();
			 $("#detailgrid").show();
			 obj = "detail";
			 loadHead(obj);
	 	 });
		 $("#file").click(function(){
			 $("#detailgrid").hide();
			 $("#invgrid").hide();
			 $("#filegrid").show();
			 obj = "file";
			 loadHead(obj);
	 	 });
		 
		 var beginDate;
		 var sign_date = $("#sign_date").val().split("-")[0]+$("#sign_date").val().split("-")[1]+$("#sign_date").val().split("-")[2];
		 var start_date = $("#start_date").val().split("-")[0]+$("#start_date").val().split("-")[1]+$("#start_date").val().split("-")[2];
		 var curDate=new Date();
		 $("#curentDate").val(curDate.getFullYear());
		 var date =$("#curentDate").val();
		 var parm={type_id:liger.get("type_id").getValue().split(",")[0]};
		 ajaxJsonObjectByUrl("../matprotocolmain/queryMatProtocolTypePre.do?isCheck=false",parm,function (responseData){
			  beginDate = responseData.start_year+responseData.start_month;
         });
		//根据协议类型Id 查询其开始年月 、协议前缀
		 $("#type_id").blur(function(){
				 parm={
						 type_id:liger.get("type_id").getValue().split(",")[0]
				         };
				ajaxJsonObjectByUrl("../matprotocolmain/queryMatProtocolTypePre.do?isCheck=false",parm,function (responseData){
					$("#protocol_code").val(responseData.pre+date);
					// 协议类别开始年月
					beginDate = responseData.start_year+responseData.start_month;
				})
	 	    });
		 $("#sign_date").blur(function(){
			 if(beginDate == null | beginDate == '' ){
				 $.ligerDialog.error("请先选择协议类别");
			 }else{
				 sign_date= $("#sign_date").val().split("-")[0]+$("#sign_date").val().split("-")[1];
				 if(sign_date < beginDate){
					 $.ligerDialog.error('签订日期不能小于协议类别的开始年月'+beginDate);
				 }else{
					 sign_date += $("#sign_date").val().split("-")[2];
				 }
			 }
 	    });
		 $("#start_date").blur(function(){
			 if(sign_date == null | sign_date == ''){
				 $.ligerDialog.error("请先选择签订日期");
			 }else{
				 start_date= $("#start_date").val().split("-")[0]+$("#start_date").val().split("-")[1]+$("#start_date").val().split("-")[2];
				 if(start_date < sign_date){
					 $.ligerDialog.error('开始日期不能小于签订日期'+sign_date);
				 }
			 }
 	    });
		 $("#end_date").blur(function(){
			 if(start_date == null | start_date == ''){
				 $.ligerDialog.error("请先选择开始日期");
			 }else{
				 var end_date= $("#end_date").val().split("-")[0]+$("#end_date").val().split("-")[1]+$("#end_date").val().split("-")[2];
				 if(end_date < start_date){
					 $.ligerDialog.error('结束日期不能小于开始日期'+start_date);
				 }
			 }
 	    });
		
     });  
    
     function loadHead(obj){
     	if("detail"==obj){
     		detailGrid = $("#detailgrid").ligerGrid({
     	           columns: [ 
		             { display: '材料名称', name: 'inv_name', align: 'left'
					 	},
	                 { display: '规格型号', name: 'inv_model', align: 'left'
					 	},
					 { display: '计量单位', name: 'unit_name', align: 'left',width:80
					 		
				 		},
				 	 { display: '单价', name: 'price', align: 'left',editor: { type: 'float'},width:80
					 	},
					 { display: '证件信息', name: 'cert_code', align: 'left',width:220
				 		},
					 { display: '备注', name: 'note', align: 'left',editor: { type: 'text'}
				 		}
                     ],
                     enabledEdit:true,checkbox: true,usePager:true,dataAction: 'server',dataType: 'server',
                     width: '100%', height: '200',url:'../matprotocolmain/queryMatProtocolDetail.do?isCheck=false&protocol_id=${protocol_id}',
                    /*  dataAction: 'server',dataType: 'server',usePager:true,url:'../queryMatProtocolDetail.do?dept_id='+dept_id,
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true */
                   });
     		detailManager = $("#detailgrid").ligerGetGridManager();
     	}
		if("file"==obj){
			fileGrid = $("#filegrid").ligerGrid({
  	           columns: [ 
                    { display: '文档类别', name:'type_name', align: 'left',
                    	valueField : 'type_code',textField: 'type_name',
			 			editor: { type: 'select', url:'../matprotocolmain/queryMatFileType.do?isCheck=false',valueField : 'type_code',textField: 'type_name',autocomplete : autocomplete }
				 		},
				 	{ display: '文档编码', name: 'file_code', align: 'left',editor: { type: 'text'}
						},
			 		{ display: '文档名称', name: 'file_name', align: 'left',editor: { type: 'text'}
			 			},
			 		{ display: '文档原件存放位置', name: 'at_local', align: 'left',editor: { type: 'text'}
				 		},
				 	{ display: '管理科室', name: 'dept_name', align: 'left',
				 			valueField : 'dept_id',textField: 'dept_name',
				 			editor: { type: 'select', url:'../matprotocolmain/queryManaDept.do?isCheck=false',valueField : 'dept_id',textField: 'dept_name',autocomplete : autocomplete }
			 			},
				 	{ display: '管理员', name: 'emp_name', align: 'left',
			 				valueField : 'mana_emp',textField: 'emp_name',
				 			editor: { type: 'select', url:'../matprotocolmain/queryManaEmp.do?isCheck=false',valueField : 'mana_emp',textField: 'emp_name',autocomplete : autocomplete }
			 			},
				 	{ display: '上传', name: '上传', align: 'left',
						 	render:function(rowdata,index,value){
							 return "<a herf=javascript:downTemplate.do?isCheck=false>上传</a>"
						 	}
				 		},
			 		{ display: '查看', name: '查看', align: 'left',
					 		render:function(rowdata,index,value){
						 		return "<a href=javascript:openUpdateFlie('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.file_id+"|"+rowdata.protocol_id+"')>查看</a>";
					 		}
				 		}
				 ],
				 enabledEdit:true,checkbox: true,rownumbers:true,usePager:true,dataAction: 'server',dataType: 'server',
				 width: '100%', height: '200',url:'../matprotocolmain/queryMatProtocolFile.do?isCheck=false&protocol_id=${protocol_id}',
                 checkbox: true,selectRowButtonOnly:true ,async:false
     		});
			fileManager = $("#filegrid").ligerGetGridManager();
     	}
		
     }
     function openUpdateFlie(data){
    	 var vo = data.split("|");
 			var parm = 
 				"group_id="+vo[0]   +"&"+ 
 				"hos_id="+vo[1]   +"&"+ 
 				"copy_code="+vo[2]   +"&"+ 
 				"file_id="+vo[3]   +"&"+
 				"protocol_id="+vo[4] 
 			$.ligerDialog.open({ url : '../matprotocolmain/matProtocolFilePage.do?isCheck=false&'+parm,data:{}, 
 				height: 205,width: 620, title:'修改',modal:true,showToggle:false,showMax:false,
 				showMin: false,isResize:true,
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
	   
    function loadDict(){
            //字典下拉框
	    	//协议类别下拉框
			autocomplete("#type_id", "../../queryMatProtocolType.do?isCheck=false", "id", "text", true, true,'',false,'','160');
            liger.get("type_id").setValue('${type_id}');
            liger.get("type_id").setText('${type_code} ${type_name}');
			//供应商下拉框
			autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
			liger.get("sup_id").setValue('${sup_id},${sup_no}');
            liger.get("sup_id").setText('${sup_code} ${sup_name}');
			//签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
			autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
			liger.get("dept_id").setValue('${dept_id},${dept_no}');
            liger.get("dept_id").setText('${dept_code} ${dept_name}');
			//甲方负责人（职工下拉框）
			autocomplete("#first_man", "../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true);
			liger.get("first_man").setValue('${first_man},${emp_no}');
            liger.get("first_man").setText('${emp_code} ${emp_name}');
            $("#is_bid").val('${is_bid}');
            
            $("#protocol_name").ligerTextBox({width:160});
            $("#protocol_code").ligerTextBox({width:160,disabled:true});
   		 	$("#original_no").ligerTextBox({width:160});
	   		 $("#sign_date").ligerTextBox({width:160});
	   		 $("#sup_id").ligerTextBox({width:160});
	   		 $("#is_bid").ligerTextBox({width:160});
	   		 $("#first_man").ligerTextBox({width:160});
	   	     $("#second_man").ligerTextBox({width:160});
	   	     $("#second_phone").ligerTextBox({width:160});
	   		 $("#dept_id").ligerTextBox({width:160});
	   		 $("#start_date").ligerTextBox({width:160});
	   	     $("#end_date").ligerTextBox({width:160});
	   		 $("#addr").ligerTextBox({width:360});
     } 
    </script>
  
  </head>
  
   <body>
   		<div class="l-layout" id="layout1" style="height: 100%;" ligeruiid="layout1">
        	<div class="l-layout-content" position="center">
        	<input type="hidden" id="curentDate" name="curentDate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
	      	 <form name="form1" method="post"  id="form1" >
			     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
					<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>协议编号<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="protocol_code" type="text" id="protocol_code" disabled="disabled" value="${protocol_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>协议名称<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="protocol_name" type="text" id="protocol_name" ltype="text" value="${protocol_name}" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>协议类别<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>签订日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="sign_date" type="text" id="sign_date" value="${sign_date}" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否招标<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<select name="is_bid" id="is_bid"style="width: 135px;" >
			                		<option value="0">否</option>
			                		<option value="1">是</option>
			            	</select>
			            </td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>甲方负责人<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="first_man" type="text" id="first_man" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>乙方负责人<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="second_man" type="text" id="second_man" value="${second_man}" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>乙方电话<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="second_phone" type="text" id="second_phone" value="${second_phone}" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>签订科室ID<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开始日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="start_date" type="text" id="start_date" ltype="text" value="${start_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>结束日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="end_date" type="text" id="end_date" value="${end_date}" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>档案位置:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<input name="addr" type="text" id="addr" ltype="text" value="${addr}" validate="{maxlength:100}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>原始编码:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="original_no" type="text" id="original_no" value="${original_no}" ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>协议简介:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="liger-textarea" name="contract_detail" id="contract_detail" rows="4" style="width: 300px;" >${contract_detail}</textarea>
			            </td>
			            <td align="left"></td>
			             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>售后服务:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="liger-textarea" name="service_detail" id="service_detail" rows="4" style="width: 300px;" >${service_detail}</textarea>
			            </td>
			        </tr>
			     </table>
		    </form>
        	</div>
	        <div title="" class="l-layout-content" style="" position="centerbottom">
	        	<div class="l-layout-header" id="toptoolbar" ></div>
	        	<input class="liger-button" name="detail"  id="detail"  type="button" width="200" value="协议明细" />
	        	<input class="liger-button" name="file"  id="file"  type="button" width="200" value="文档管理" />
	            <div id="detailgrid" ></div>
	            <div id="filegrid" style="display: none"></div>
	        </div>
      </div>  
    </body>
</html>

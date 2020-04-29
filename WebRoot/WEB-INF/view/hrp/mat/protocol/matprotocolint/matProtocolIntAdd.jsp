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
    <script type="text/javascript">
     var dataFormat;
     var detailGrid;
	 var feilGrid;
     var detailManager = null;
     var fileManager = null;
     var obj;
     var flag = false;
     var beginDate;
	 var sign_date;
	 var start_date;
	 var year;
	 var type_id;
     $(function (){
         loadDict()//加载下拉框
         loadForm();
         loadHead("detail");
        
		 $("#save").click(function(){
 	    	if($("form").valid()){
 	            save();
 	        }
 	     });
		 $("#inv").click(function(){
			 if(!$("#detailgrid").is(":hidden")){
				 $.ligerDialog.open({url: 'matInvDetailPage.do?isCheck=false',height: 500,width: 900, 
		 				title:'选择 材料',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true
		 				});
			 }else{
				 $.ligerDialog.error("请先点击协议明细"); 
				 return;
			 }
	 	 });
		 
		 $("#detail").click(function(){
			 $("#filegrid").hide();
			 $("#detailgrid").show();
			 obj = "detail";
			 loadHead(obj);
	 	     });
		 
		 $("#file").click(function(){
			 $("#detailgrid").hide();
			 $("#filegrid").show();
			 obj = "file";
			 loadHead(obj);
			 fileGrid.addRow({
				 type_name : '',
				 file_code: '',
				 file_name: '',
				 at_local:'',
				 dept_id : '',
				 mana_emp : '',
				上传: '',
				 查看: '查看'
			 });
	 	     });
		 
		 $("#toptoolbar").ligerToolBar({ items: [
             { text: '删除',icon:'delete', click:deleteData},
             {line:true},
             { text: '保存',icon:'add', click:add}]
         }); 
		
     });  
     var curDate=new Date();
	 year =curDate.getFullYear();
     //根据协议类型Id 查询其开始年月 、协议前缀  同时生成协议编号
	 function typeId(){
			 var parm={
					 type_id:liger.get("type_id").getValue().split(",")[0],
			 		 year:year
			         };
			 if(parm.type_id != null && parm.type_id !=''){
				 ajaxJsonObjectByUrl("../matprotocolmain/queryMatProtocolTypePre.do?isCheck=false",parm,function (responseData){
						$("#protocol_code").val(responseData.prefixe+responseData.year+responseData.max_no);
						// 协议类别开始年月
						beginDate = responseData.start_year+responseData.start_month;
					})
			 }	
 	 };
     function signDate(){
		 if(beginDate == null | beginDate == ''){
			 $.ligerDialog.error("请先选择协议类别");
			 return;
		 }else{
			 sign_date= $("#sign_date").val().split("-")[0]+$("#sign_date").val().split("-")[1];
			 if(sign_date < beginDate){
				 sign_date += $("#sign_date").val().split("-")[2];
			 }else{
				 $.ligerDialog.error('签订日期不能大于协议类别的开始年月'+beginDate);
				 return;
			 }
		 }
	 }; 
	 
	function startDate(){
		 if(sign_date == null | sign_date == ''){
			 $.ligerDialog.error("请先选择签订日期");
			 return;
		 }else{
			 start_date= $("#start_date").val().split("-")[0]+$("#start_date").val().split("-")[1]+$("#start_date").val().split("-")[2];
			 if(start_date < sign_date){
				 $.ligerDialog.error('开始日期不能小于签订日期'+sign_date);
				 return;
			 }
		 }
	 };
	 function endDate(){
		 if(start_date == null | start_date == ''){
			 $.ligerDialog.error("请先选择开始日期");
			 return;
		 }else{
			 var end_date= $("#end_date").val().split("-")[0]+$("#end_date").val().split("-")[1]+$("#end_date").val().split("-")[2];
			 if(end_date < start_date){
				 $.ligerDialog.error('结束日期不能小于开始日期'+start_date);
				 return;
			 }
		 }
	 };
     
	 function deleteData(){
    	 var data;
    	 if(!$("#filegrid").is(":hidden")){
    		 data = fileGrid.getCheckedRows();
    		 if(data.length == 0){
                 alert('请点击协议明细或文档管理，选择要删除的行!');
                 return;
             }else{
             	 for (var i = 0; i < data.length; i++){
             		fileGrid.remove(data[i]);
                  } 
             }
    	 } 
    	 if(!$("#detailgrid").is(":hidden")){
    		 data = detailGrid.getCheckedRows();
    		 if(data.length == 0){
                 alert('请点击协议明细或文档管理，选择要删除的行!');
                 return;
             }else{
             	 for (var i = 0; i < data.length; i++){
             		detailGrid.remove(data[i]);
                  } 
             }
    	 }
     }

     function add(){
    	 if(!$("#detailgrid").is(":hidden")){
    		 if(!flag){
    			 $.ligerDialog.error('请先添加采购协议，再点击');
    			 return;
    		 }else{
    			 var data = detailManager.getAdded();
                 if (data.length == 0){
                 	$.ligerDialog.error('请选择材料后再保存');
                 	return;
                 }else{
                     var ParamVo =[];
                     $(data).each(function (){					
    						ParamVo.push(
    							this.group_id   +"@"+ 
    							this.hos_id   +"@"+ 
    							this.copy_code   +"@"+ 
    							this.inv_id   +"@"+ 
    							this.inv_no   +"@"+ 
    							this.price 	 +"@"+ 
    							this.note   +"@"+ 
    							0  +"@"+ 
    							0
    						) 
    					});
                       	ajaxJsonObjectByUrl("../matprotocolmain/addMatProtocolDetail.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                       		if(responseData.state=="true"){
                       		}
                       	});
                     } 
    		 }
    		 
         }
		if(!$("#filegrid").is(":hidden")){
			if(!flag){
   			 	$.ligerDialog.error('请先添加采购协议，再点击');
   			 	return;
   		 	}else{
				 var data = fileManager.getAdded();
	             if (data.length == 0){
	             	$.ligerDialog.error('请正确填写信息后再保存');
	             	return;
	             }else{
	                 var ParamVo =[];
	                 $(data).each(function (){					
							ParamVo.push(
								this.type_name   +"@"+ 
								this.file_code   +"@"+ 
								this.file_name 	 +"@"+ 
								this.at_local  +"@"+ 
								this.dept_name  +"@"+ 
								this.emp_name  +"@"+  
								0 +"@"+  
								0
							) 
						});
	                   	ajaxJsonObjectByUrl("../matprotocolmain/addMatProtocolFile.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
	                   		if(responseData.state=="true"){
	                   			
	                   		}
	                   	});
	                 }
	    	 }
		}
     }
     
     function  save(){
        var formPara={
        	protocol_id:'',
           	protocol_code:$("#protocol_code").val(),
           	protocol_name:$("#protocol_name").val(),
        	type_id:liger.get("type_id").getValue(),
        	sign_date:$("#sign_date").val(),
        	sup_id:liger.get("sup_id").getValue().split(",")[0],
        	sup_no:liger.get("sup_id").getValue().split(",")[1],
        	is_bid:$("#is_bid").val(),
        	first_man:liger.get("first_man").getValue().split(",")[0],
           	second_man:$("#second_man").val(),
           	second_phone:$("#second_phone").val(),
           	dept_id:liger.get("dept_id").getValue().split(",")[0],
        	dept_no:liger.get("dept_id").getValue().split(",")[1],
           	start_date:$("#start_date").val(),
           	end_date:$("#end_date").val(),
           	original_no:$("#original_no").val(),
           	addr:$("#addr").val(),
           	contract_detail:$("#contract_detail").val(),
           	service_detail:$("#service_detail").val(),
            
         };
        
        ajaxJsonObjectByUrl("addMatProtocolInt.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	parent.loadHead(null);
           	 $.ligerDialog.confirm('是否继续添加?', function (yes){
                	if(yes){
        				 $("input[name='protocol_code']").val('');
        				 $("input[name='protocol_name']").val('');
        				 $("input[name='original_no']").val('');
        				 $("input[name='sign_date']").val('');
        				 $("input[name='sup_id']").val('');
        				 $("input[name='is_bid']").val('');
        				 $("input[name='first_man']").val('');
        				 $("input[name='second_man']").val('');
        				 $("input[name='second_phone']").val('');
        				 $("input[name='dept_id']").val('');
        				 $("input[name='start_date']").val('');
        				 $("input[name='end_date']").val('');
        				 $("input[name='addr']").val('');
        				 $("input[name='type_id']").val('');
        				 $("textarea[name='contract_detail']").val('');
        				 $("input[name='service_detail']").val('');
                		
                	}else{
                		parent.openUpdate(responseData.update_para);
                		frameElement.dialog.close();
                	}
           		})
            }
        });
    }
	 
     function loadHead(obj){
     	if("detail"==obj){
     		detailGrid = $("#detailgrid").ligerGrid({
     	           columns: [ 
		             	{ display: '材料名称', name: 'inv_name', align: 'left'},
	                 { display: '规格型号', name: 'inv_model', align: 'left'},
					 { display: '计量单位', name: 'unit_name', align: 'left',width:80},
				 	 { display: '单价', name: 'price', align: 'left',editor: { type: 'float'},width:80,
						 render:function(rowdata){
			            		return formatNumber(rowdata.price ==null ? 0 : rowdata.price,2,1);
			             }
					 	},
					 	{ display: '生产厂商', name: 'fac_name', align: 'left',width:220},
					 { display: '备注', name: 'note', align: 'left',editor: { type: 'text'}
				 		}
                     ],
                     enabledEdit:true,checkbox: true,usePager:true,dataAction: 'server',dataType: 'server',
                     width: '100%', height: '240'
                   });
     		detailManager = $("#detailgrid").ligerGetGridManager();
     	}
		if("file"==obj){
			fileGrid = $("#filegrid").ligerGrid({
  	           columns: [ 
                    { display: '文档类别', name: 'type_name', align: 'left',
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
				 	{ display: '管理员', name:'emp_name', align: 'left',
			 				valueField : 'mana_emp',textField: 'emp_name',
				 			editor: { type: 'select', url:'../matprotocolmain/queryManaEmp.do?isCheck=false',valueField : 'mana_emp',textField: 'emp_name',autocomplete : autocomplete }
			 			},
				 	{ display: '上传', name: '上传', align: 'left',
						 	render:function(rowdata,index,value){
							 return "<a herf=javascript:downTemplate.do?isCheck=false>上传</a>"
						 	}
				 		}
				 ],
				 enabledEdit:true,checkbox: true,rownumbers:true,usePager:true,dataAction: 'server',dataType: 'server',
				 width: '100%', height: '240',
     		});
			fileManager = $("#filegrid").ligerGetGridManager();
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
	     //$("form").ligerForm();
	 }       
	   
    function saveMatProtocolMain(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	//字典下拉框
    	//协议类别下拉框
		autocomplete("#type_id", "../../queryMatProtocolType.do?isCheck=false", "id", "text", true, true,'',false,'','160');
	 	//供应商下拉框
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		//签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
		autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
		//甲方负责人（职工下拉框）
		autocomplete("#first_man", "../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true);
		
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
			            <td align="left" class="l-table-edit-td"><input name="protocol_code" type="text" id="protocol_code" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>协议名称<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="protocol_name" type="text" id="protocol_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>协议类别<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" onChange="typeId()" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
					<tr>
			        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>签订日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="sign_date" type="text" id="sign_date" ltype="text" onChange="signDate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true}" /></td>
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
			            <td align="left" class="l-table-edit-td"><input name="second_man" type="text" id="second_man" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>乙方电话<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="second_phone" type="text" id="second_phone" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>签订部门<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>开始日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="start_date" type="text" id="start_date" ltype="text" onChange="startDate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
						<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>结束日期<font color="red">*</font>:</b></td>
			            <td align="left" class="l-table-edit-td">
			            	<input class="Wdate" name="end_date" type="text" id="end_date" ltype="text" onChange="endDate()" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true,maxlength:20}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>档案位置:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="4">
			            	<input name="addr" type="text" id="addr" ltype="text" validate="{maxlength:100}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>原始编码:</b></td>
			            <td align="left" class="l-table-edit-td"><input name="original_no" type="text" id="original_no" ltype="text" validate="{maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>协议简介:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="liger-textarea" name="contract_detail" id="contract_detail" rows="4" style="width: 300px;" ></textarea>
			            </td>
			            <td align="left"></td>
			             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>售后服务:</b></td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="liger-textarea" name="service_detail" id="service_detail" rows="4" style="width: 300px;" ></textarea>
			            </td>
			        </tr>
			        <tr>
	                	<td align="center" colspan="9"><input class="liger-button" name="save"  id="save"  type="button" width="100" value="保存" /></td>
			        </tr>
			     </table>
		    </form>
        	</div>
	        <div title="" class="l-layout-content" style="" position="centerbottom">
	        	<div class="l-layout-header" id="toptoolbar" ></div>
	        	<input class="liger-button" name="inv"  id="inv"  type="button" width="100" value="选择材料" />
	        	<input class="liger-button" name="detail"  id="detail"  type="button" width="100" value="协议明细" />
	        	<input class="liger-button" name="file"  id="file"  type="button" width="100" value="文档管理" />
	            <div id="detailgrid" ></div>
	             <div id="filegrid" style="display: none"></div>
	        </div>
      </div>  
    </body>
</html>

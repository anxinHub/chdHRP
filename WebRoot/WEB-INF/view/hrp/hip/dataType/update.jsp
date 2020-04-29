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
    <style type="text/css"> 
	.comments { 
	width:100%;
	overflow:auto; 
	word-break:break-all; 
	} 
	</style> 
    <script type="text/javascript">
    var grid;
    var gridManager = null;
    var dataFormat,ue;
    var is_stop = {
 			Rows : [ {
 				"id" : "0",
 				"text" : "否" 
 			}, {
 				"id" : "1",
 				"text" : "是" 
 			}],
 			Total : 2
 	};
    var no_info = {
    		Rows : [ {
 				"id" : "0",
 				"text" : "无" 
 			}],
 			Total : 1
    };
    var data_type_info = {
    		Rows : [ {
 				"id" : "0",
 				"text" : "无" 
 			},{
 				"id" : "1",
 				"text" : "按日期取数" 
 			}],
 			Total : 2
    };
    var sync_type_info = {
    		Rows : [ {
 				"id" : "0",
 				"text" : "删除同步" 
 			}, {
 				"id" : "1",
 				"text" : "增量同步" 
 			}],
 			Total : 2
    };
    
     $(function (){
    	 
        loadDict()//加载下拉框
        loadHead();	
        $("#layout1").ligerLayout({ leftWidth: 500,allowLeftResize:true });
        query();
     });  
     function  query(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
 		
 		
 		if($("#table_name").val() == null || $("#table_name").val() == ""){
 				$.ligerDialog.error('请选择目标表!');
 				return;
 		}
        
 		grid.options.parms.push({name:'table_name',value:$("#table_name").val()}); 
 		
     	//加载查询条件
     	grid.loadData(grid.where);
      }
     
     
     
     function loadHead(){

     	grid = $("#maingrid").ligerGrid({
            columns: [ 
            		{ display: '字段编码', name: 'column_name', align: 'left',width:120,isSort:false
		      		},
 					{ display: '字段名称', name: 'comments', align: 'left',width:140,isSort:false
				    },
 					{ display: '字段类型', name: 'data_type', align: 'left',width:100,isSort:false
				    },
 					{ display: '是否为空', name: 'nullable', align: 'left',width:90,isSort:false
				    }
                      ],
                    	  dataAction: 'server',dataType: 'server',usePager:false,url:'queryHrpTableColumn.do?isCheck=false',
              			width: 'atuo', height: '100%', checkbox: false,rownumbers:false,
              			delayLoad: true,//初始化加载，默认false
              			selectRowButtonOnly:false
                      
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
     }
     
     
     function  save(){
    	if(liger.get("dgroup_id").getValue() == null || liger.get("dgroup_id").getValue() == ""){
   			$.ligerDialog.error('数据集团不能为空!');
   			return;
   		}
     	 
     	if(liger.get("dhos_id").getValue() == null || liger.get("dhos_id").getValue() == ""){
   			$.ligerDialog.error('数据医院不能为空!');
   			return;
   		}
     	
     	if(liger.get("dcopy_code").getValue() == null || liger.get("dcopy_code").getValue() == ""){
   			$.ligerDialog.error('数据账套不能为空!');
   			return;
   		}
     	
     	if(liger.get("data_type").getValue() == null || liger.get("data_type").getValue() == ""){
  			$.ligerDialog.error('取值方式不能为空!');
  			return;
  		}
    	
    	if(liger.get("sync_type").getValue() == null || liger.get("sync_type").getValue() == ""){
  			$.ligerDialog.error('同步方式不能为空!');
  			return;
  		}
    	 
    	if(isnull($("#type_code").val())){
 			$.ligerDialog.error('业务编码不能为空!');
 			return;
 		} 
    	 
    	if(isnull($("#type_name").val())){
 			$.ligerDialog.error('业务名称不能为空!');
 			return;
 		} 
    	 
    	if(isnull(liger.get("source_code").getValue())){
 			$.ligerDialog.error('数据源不能为空!');
 			return;
 		} 
    	
    	if(liger.get("is_stop").getValue() == null || liger.get("is_stop").getValue() == ""){
 			$.ligerDialog.error('是否停用不能为空!');
 			return;
 		} 
    	
    	
    	if(isnull($("#q_sql").val())){
 			$.ligerDialog.error('查询SQL不能为空!');
 			return;
 		} 
    	 
    	if(isnull($("#table_name").val())){
			$.ligerDialog.error('请选择目标表!');
			return;
		} 
    	
    	if(liger.get("data_type").getValue() == "1"){
    		if(isnull($("#pk_col").val())){
    			$.ligerDialog.error('按日期取数条件表达式不能为空!');
    			return;
    		} 
    	}
    	
		var pk_col = $("#pk_col").val().toUpperCase();
    	
    	if(pk_col.indexOf("GROUP_ID") != -1){
    		$.ligerDialog.error('条件表达式不能包含GROUP_ID');
			return;
    	}
    	
		if(pk_col.indexOf("HOS_ID") != -1){
			$.ligerDialog.error('条件表达式不能包含HOS_ID');
			return;
    	}
		
		if(pk_col.indexOf("COPY_CODE") != -1){
			$.ligerDialog.error('条件表达式不能包含COPY_CODE');
			return;
    	}
		
		
		if($("#is_group").val() == 0){
			if(liger.get("dgroup_id").getValue() != 0){
				$.ligerDialog.error('目标表是否集团为否，数据集团必须为无!');
				return;
			}
		}else if($("#is_group").val() == 1){
			if(liger.get("dgroup_id").getValue() == 0){
				$.ligerDialog.error('目标表是否集团为是，数据集团不能为无!');
				return;
			}
		}
		
		if($("#is_hos").val() == 0){
			if(liger.get("dhos_id").getValue() != 0){
				$.ligerDialog.error('目标表是否医院为否，数据医院必须为无!');
				return;
			}
		}else if($("#is_hos").val() == 1){
			if(liger.get("dhos_id").getValue() == 0){
				$.ligerDialog.error('目标表是否医院为是，数据医院不能为无!');
				return;
			}
		}
		
		if($("#is_copy_code").val() == 0){
			if(liger.get("dcopy_code").getValue() != 0){
				$.ligerDialog.error('目标表是否账套为否，数据账套必须为无!');
				return;
			}
		}else if($("#is_copy_code").val() == 1){
			if(liger.get("dcopy_code").getValue() == 0){
				$.ligerDialog.error('目标表是否账套为是，数据账套不能为无!');
				return;
			}
		}
		
    	 
    	 
  		var para = {
  				dgroup_id : liger.get("dgroup_id").getValue(),
  				dhos_id : liger.get("dhos_id").getValue(),
  				dcopy_code : liger.get("dcopy_code").getValue(),
  				data_type : liger.get("data_type").getValue(),
  				sync_type : liger.get("sync_type").getValue(),
  				type_id : $("#type_id").val(),
  				type_code : $("#type_code").val(),
  				type_name : $("#type_name").val(),
  				source_code : liger.get("source_code").getValue(),
  				is_stop : liger.get("is_stop").getValue(),
  				to_table : $("#table_name").val(),
  				mod_code : liger.get("mod_code").getValue(),
  				note : $("#note").val(),
  				pk_col : $("#pk_col").val(),
  				q_sql : $("#q_sql").val()
  		};
  		
  		ajaxJsonObjectByUrl("saveHipDataType.do?isCheck=false", para, function(
				responseData) {
			if (responseData.state == "true") {
				
			}
		});
  		
    }
     
    
    
    function loadDict(){
    	//当前用户登录类型
        var user_type = parent.sessionJson.type_code;
        //当前用户登录的模块
        var l_mod_code = '${sessionScope.mod_code}';
            
        autocomplete("#source_code", "../queryHipDataSource.do?isCheck=false","id", "text",true,true,null,true,null,"280");
            
    	autoCompleteByData("#is_stop", is_stop.Rows, "id", "text", false, true, true,true,null,280);
    	
		autoCompleteByData("#data_type", data_type_info.Rows, "id", "text", false, true,null,true,null,280);
        
    	autoCompleteByData("#sync_type", sync_type_info.Rows, "id", "text", false, true, true,true,null,280);
    	
    	$("#source_code").ligerComboBox({ cancelable: false });
    	
    	$("#is_stop").ligerComboBox({ cancelable: false });
    	
		$("#data_type").ligerComboBox({ cancelable: false });
    	
    	$("#sync_type").ligerComboBox({ cancelable: false });
    	
		liger.get("source_code").setValue("${source_code}");
		
		liger.get("source_code").setText("${source_name}");
    	
    	liger.get("is_stop").setValue("${is_stop}");
    	
		liger.get("data_type").setValue("${data_type}");
    	
    	liger.get("sync_type").setValue("${sync_type}");
    	
    	
    	if(user_type == '0'){
    		autocompleteAsync("#mod_code","../../sys/queryModDict.do?isCheck=false&mod_code="+l_mod_code+"&type_code="+user_type,"id","text",false,true,'',true,'',280);
    	}else{
    		autocompleteAsync("#mod_code","../../sys/queryModDict.do?isCheck=false&type_code="+user_type,"id","text",false,true,'',true,'',280);
    	}
		
	
		liger.get("mod_code").setValue("${mod_code}");
    	
    	$("#table_name").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 280
		});
    	
    	$("#type_code").ligerTextBox({
			width : 280
		});
    	
    	$("#type_name").ligerTextBox({
			width : 280
		});
    	
    	$("#note").ligerTextBox({
			width : 280
		});
    	
    	
    	$("#btn_query").ligerButton({click: open_query, width:60});
    	$("#save").ligerButton({click: save, width:60});
    	$("#close").ligerButton({click: this_close, width:60});

    	$("#decodeSet").ligerButton({click: open_decodeSet, width: 60});
    	
    	
    	if(user_type == '0' || user_type == '4'){
    		autocompleteAsync("#dgroup_id", "../../acc/queryGroupDictDataType.do?isCheck=false&group_id=${sessionScope.group_id}","id", "text",true,true,null,true,null,"280");
        	
        	autoCompleteByData("#dhos_id", no_info.Rows, "id", "text", false, true, true,true,null,280);
        	
        	autoCompleteByData("#dcopy_code", no_info.Rows, "id", "text", false, true, true,true,null,280);
        	
    		$("#dgroup_id").change(function(){
    			autocompleteAsync("#dhos_id", "../../acc/queryHosInfoDataType.do?isCheck=false","id", "text",true,true,{group_id:liger.get("dgroup_id").getValue(),hos_id:'${sessionScope.hos_id}'},true,null,"280");
    		});
    		
    		$("#dhos_id").change(function(){
    			autocompleteAsync("#dcopy_code", "../../acc/queryHosCopyDataType.do?isCheck=false","id", "text",true,true,{group_id:liger.get("dgroup_id").getValue(),hos_id:liger.get("dhos_id").getValue()},true,null,"280");
    			
    		});
    		
    	}else if(user_type == '1' || user_type == '3'){
    		autocompleteAsync("#dgroup_id", "../../acc/queryGroupDictDataType.do?isCheck=false&group_id=${sessionScope.group_id}","id", "text",true,true,null,true,null,"280");
        	
        	autoCompleteByData("#dhos_id", no_info.Rows, "id", "text", false, true, true,true,null,280);
        	
        	autoCompleteByData("#dcopy_code", no_info.Rows, "id", "text", false, true, true,true,null,280);
        	
    		$("#dgroup_id").change(function(){
    			autocompleteAsync("#dhos_id", "../../acc/queryHosInfoDataType.do?isCheck=false","id", "text",true,true,{group_id:liger.get("dgroup_id").getValue()},true,null,"280");
    		});
    		
    		$("#dhos_id").change(function(){
    			autocompleteAsync("#dcopy_code", "../../acc/queryHosCopyDataType.do?isCheck=false","id", "text",true,true,{group_id:liger.get("dgroup_id").getValue(),hos_id:liger.get("dhos_id").getValue()},true,null,"280");
    		});
    	}
    	
    	
    	if(liger.get("dgroup_id").getValue() == 0){
    		liger.get("dhos_id").setValue(0);
    		liger.get("dcopy_code").setValue(0);
    	}
    	
    	if(liger.get("dhos_id").getValue() == 0){
    		liger.get("dcopy_code").setValue(0);
    	}
		
	    $("#dgroup_id").ligerComboBox({ cancelable: false });
    	
    	$("#dhos_id").ligerComboBox({ cancelable: false,width : 280 });
    	
    	$("#dcopy_code").ligerComboBox({ cancelable: false,width : 280 });
    	
    	liger.get("dgroup_id").setValue("${dgroup_id}");
		
		liger.get("dgroup_id").setText("${dgroup_name}" == "" ? "无":"${dgroup_name}");
    	
    	liger.get("dhos_id").setValue("${dhos_id}");
    	
    	liger.get("dhos_id").setText("${dhos_name}" == "" ? "无":"${dhos_name}");
    	
    	liger.get("dcopy_code").setValue("${dcopy_code}");
    	
    	liger.get("dcopy_code").setText("${dcopy_name}" == "" ? "无":"${dcopy_name}");
     } 
    
    function open_query(){
    	
    	$.ligerDialog.open({ 
			url : 'queryPage.do?isCheck=false',
			data:{}, 
			height:$(window).height() / 2,
			width: $(window).width()  / 2, 
			title:'查询表',
			modal:true,
			showToggle:false,
			showMax:false,
			showMin: false,
			isResize:true,
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { 
					
					var rs = dialog.frame.selectedTable();
					if(!rs){
						dialog.frame.$.ligerDialog.error('请选择表名!');
						return;
					}
					$("#table_name").val(dialog.frame.table_code);
					$("#is_group").val(dialog.frame.is_group);
					$("#is_hos").val(dialog.frame.is_hos);
					$("#is_copy_code").val(dialog.frame.is_copy_code);
					dialog.close();
					query();
					
				},cls:'l-dialog-btn-highlight' },
				{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } ] 
		});
    }
 
    function this_close(){
		frameElement.dialog.close();
	}
    
    var hipDataType; //子页面需要勿动
	function open_decodeSet(){
		hipDataType = {type_id: $("#type_id").val(), to_table: $("#table_name").val()};
		parent.$.etDialog.open({
			url: 'hrp/hip/dataType/decodeSetPage.do?isCheck=false',
			frameName : window.name,
			width: $(parent.window).width() - 200,
			height: $(parent.window).height() - 100,
			title: '字典转换设置',
			showMax: true,
			shade: 0,
			maxmin: true,
			restore: function(layero){
			  	//得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			  	setTimeout(function() {
		  			iframeWin.$grid.refreshView();
			  	}, 100);
		  	}
		});
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <input id="type_id" type="hidden"  value="${type_id }" />
   <input id="is_group" type="hidden"  value = "${is_group }" />
   <input id="is_hos" type="hidden"  value = "${is_hos }"  />
   <input id="is_copy_code" type="hidden" value = "${is_copy }"  />
   	<div id="layout1" style="height: 100%;">
            <div  position="left" title="基础信息" style="left: 0px; top: 0px;  height: 100%;">
            	<div class="l-layout-content" position="left" style="height:100%;">
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" style="float: left;" width="100%"  height="70%" >
		    <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务编码：</td>
                <td align="left" class="l-table-edit-td" ><input name="type_code" type="text" id="type_code" value="${type_code }"  /></td>
                <td align="left" class="l-table-edit-td" ></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务名称：</td>
                <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" value="${type_name }"  /></td>
                <td align="left" class="l-table-edit-td" ></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据源：</td>
                <td align="left" class="l-table-edit-td"><input name="source_code" type="text" id="source_code"  /></td>
                <td align="left" class="l-table-edit-td" ></td>
            </tr>
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据集团：</td>
                <td align="left" class="l-table-edit-td"><input name="dgroup_id" type="text" id="dgroup_id" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据医院：</td>
                <td align="left" class="l-table-edit-td"><input name="dhos_id" type="text" id="dhos_id" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据账套：</td>
                <td align="left" class="l-table-edit-td"><input name="dcopy_code" type="text" id="dcopy_code" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">系统模块：</td>
                <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code"  /></td>
                <td align="left" class="l-table-edit-td" ></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值方式：</td>
                <td align="left" class="l-table-edit-td"><input name="data_type" type="text" id="data_type" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">同步方式：</td>
                <td align="left" class="l-table-edit-td"><input name="sync_type" type="text" id="sync_type" />
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop"  /></td>
                <td align="left" class="l-table-edit-td" ></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"  >
                	<input name="note" type="text" id="note" value="${note }" />
                </td>
                <td align="left" class="l-table-edit-td" ></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  colspan="3" style="padding-left:20px;"><hr/></td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标表：</td>
                <td align="left"  class="l-table-edit-td">
		            <input  name="table_name" type="text" id="table_name"  value="${to_table }" />
                </td>
                <td align="left"><button id ="btn_query" ><b>查询</b></button></td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  colspan="3">
                	<div id="maingrid" ></div>
                </td>
            </tr>
        </table>
				</div>
            </div>
            <div position="center" title="查询SQL"  style="left:width:100%; height: 100%;">
            	<div class="l-layout-content" style="height: 100%;"
				position="center">
				<table style="float: left;" width="100%" >
					<tr >	
						<td align="left" class="l-table-edit-td" >
							<button id ="save"><b>保存</b></button>
							&nbsp;&nbsp;
							<button id ="decodeSet"><b>字段转换</b></button>
							&nbsp;&nbsp;
							<button id ="close"><b>关闭</b></button>
						</td>
					</tr>
		        	 <tr>
		                <td align="left" class="l-table-edit-td">
		    				<textarea class="comments" id="q_sql" rows="24" cols="160" placeholder="select的别名必须与目标表的字段保持一致；如遇到目标表有id的字段需要做字段转换，可以不需要查询；group_id,hos_id,copy_code字段可以省略，系统自动处理" onpropertychange= "this.style.posHeight=this.scrollHeight">${q_sql }</textarea>
		                </td>
		            </tr> 
		            <tr>
		                <td align="left" class="l-table-edit-td">
		                	同步条件表达式（HRP）：
		    				<textarea class="comments" id="pk_col" rows="5" cols="160" placeholder="如根据type_code和inv_code增量同步：type_code,inv_code...；如根据时间删除同步：date >= @begin_date and date <=@end_date" onpropertychange= "this.style.posHeight=this.scrollHeight">${pk_col }</textarea>
		                </td>
		            </tr>
		        	<tr>
						<td align="left" class="l-table-edit-td" >
							1.如果取值方式为<font color="red">按日期取数 </font>,
							查询SQL中必须带有日期条件,例：date >= @begin_date and date <= @end_date<br/>
							2.如果同步方式为<font color="red">删除同步 </font>，
							有外键约束或者带有序列的表不能删除同步<br/>
						</td>
					</tr>
		       </table>
			</div>
            </div>
        </div> 
   
    </body>
</html>

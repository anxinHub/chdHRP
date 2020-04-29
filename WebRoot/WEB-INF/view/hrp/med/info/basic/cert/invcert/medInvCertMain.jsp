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
    var grid;
    var gridManager = null;
    var userUpdateStr;
  	//注册证号状态
    var medInvCert_state = {
    		Rows : [ {
    			"id" : "0",
    			"text" : "未审核"
    		}, {
    			"id" : "1",
    			"text" : "已审核"
    		}
    		],
    		Total : 2	
    	}

    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'cert_code',value:$("#cert_code").val()}); 
    	  grid.options.parms.push({name:'cert_inv_name',value:$("#cert_inv_name").val()}); 
    	  grid.options.parms.push({name:'type_id',value:liger.get("type_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_code").getValue()}); 
    	  grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  { 
						display: '供应商', name: 'sup_name', align: 'left',width:200,
					}, 
                     { display: '证件号', name: 'cert_code', align: 'left', width:240,
                    	 render:function(rowdata,index,value){
                    		 return '<a href=javascript:openUpdate("'+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.cert_id+"|"+index+'")>'+rowdata.cert_code+'</a>';
                    	 }
					 },{ 
						display: '注册证产品名称', name: 'cert_inv_name', align: 'left',width:200,
					}, { 
						display: '起始日期', name: 'start_date', align: 'left',width:90,
					}, { 
						display: '截止日期', name: 'end_date', align: 'left',width:90,
					}, { 
						display: '生产厂商', name: 'fac_name', align: 'left',width:200,
					},  { 
						 display: '包装规格', name: 'issuing_authority', align: 'left',width:150,
					},  { 
						 display: '证件类型', name: 'type_name', align: 'left',width:150,
					},{ 
						display: '使用状态', name: 'cert_state', align: 'left',width:100,
						render: function(rowdata,index,value){
							if(rowdata.cert_state == 1){
								return "在用";
							}else if(rowdata.cert_state == -1){
								return "未维护";
							}else{
								return "停用";
							}
						}
					},{ 
						display: '状态', name: 'state', align: 'left',width:100,
						render: function(rowdata,index,value){
							if(rowdata.state == 1){
								return "已审核";
							}else if(rowdata.state == 0){
								return "未审核";
							}
						}
					}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInvCert.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 0,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    					{ line:true },
    					{ text: '复制', id:'copy', click: addInvCertCopy, icon:'copy' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
						{ text: '审核(<u>S</u>)', id:'audit', click: audit, icon:'audit' },
	    				{ line:true }, 
	    				{ text: '消审(<u>U</u>)', id:'unAudit', click: unAudit, icon:'unaudit' },
	    				{ line:true }, 
    	                /* { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down' },
		                { line:true },
		                */
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' },
		                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.cert_id + "|" +
								rowindex
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
	//添加
    function add_open(){
    	parent.$.ligerDialog.open({
			title: '添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/info/basic/cert/invcert/medInvCertAddPage.do?isCheck=false',
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  
    	//$.ligerDialog.open({url: 'medInvCertAddPage.do?isCheck=false', height: 380,width: 620, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedInvCert(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    }
    function remove(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
					ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.cert_id 	+"@"+ 
					this.cert_code 
					) });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteMedInvCert.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    
    //复制证件信息
    function addInvCertCopy(){

    	var data = gridManager.getCheckedRows();
    	if (data.length == 0){
			$.ligerDialog.warn('请选择一条要复制的证件信息数据');
    	}else if(data.length > 1){
    		$.ligerDialog.warn('每次只能复制一条证件信息数据');
    	}else{
    		var paras='';
    		$(data).each(function (){
	    		paras = "&group_id="+this.group_id +"&hos_id="+this.hos_id
						+"&copy_code="+this.copy_code +"&cert_id="+this.cert_id;
	    		return ;
    		})
			parent.$.ligerDialog.open({
				title: '证件信息复制',height: 550,width: 1000,
				url: 'hrp/med/info/basic/cert/invcert/medInvCertCopyPage.do?isCheck=false'+paras,
				modal: true, showToggle: false, showMax: true, showMin: false, isResize: true, top : 0,
				parentframename: window.name,
				buttons: [
					{ text: '保存', onclick: function(item, dialog){dialog.frame.save();}, cls:'l-dialog-btn-highlight' },
					{ text: '取消', onclick: function(item, dialog){dialog.close();}}
				]
			});
    	}
    }
    
    function imp(){
    	
    	parent.$.ligerDialog.open({
			url : 'hrp/med/info/basic/cert/invcert/medInvCertImportPage.do?isCheck=false',
			data : {
				columns : grid.columns,
				grid : grid
			},
			height : 300,
			width : 450,
			title : '证件信息导入',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1] +"&copy_code="+vo[2] +"&cert_id="+ vo[3];
		parent.$.ligerDialog.open({
			title: '修改',
			height: $(window).height(),
			width: $(window).width(),
			selectedRow: vo[4],
			url: 'hrp/med/info/basic/cert/invcert/medInvCertUpdatePage.do?isCheck=false&' + parm.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  
		
    }
    
  //批量审核
	function audit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 0){
					in_nos = in_nos + this.cert_code + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.cert_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("审核失败！<br>以下单据不是未审核状态：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定审核?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("auditMedInvCertBatch.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
	
	//批量消审
	function unAudit(){
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 1){
					in_nos = in_nos + this.cert_code + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.cert_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("消审失败！<br>以下单据不是已审核状态：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定消审?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("unAuditMedInvCertBatch.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
	}
    
    function loadDict(){
    	//字典下拉框
    	autoCompleteByData("#state", medInvCert_state.Rows, "id", "text", true, true);
    	autocomplete("#type_id","../../../../qryMedInvCertType.do?isCheck=false","id","text",true,true);
    	autocomplete("#sup_code","../../../../queryHosSup.do?isCheck=false","id","text",true,true);
    	autocomplete("#fac_id","../../../../queryHosFac.do?isCheck=false","id","text",true,true);
        $("#cert_code").ligerTextBox({width:160});
        $("#cert_inv_name").ligerTextBox({width:160});
    }  
    //键盘事件
	  function loadHotkeys() {
    	
		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('T', downTemplate);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
  }
  //打印数据
	 function printDate(){
		//有数据直接打印
		 if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","08601 药品证件信息",true);
			return;
		}  
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           cert_code:$("#cert_code").val(),
           type_id:liger.get("type_id").getValue()
         };
		ajaxJsonObjectByUrl("queryMedInvCert.do",printPara,function (responseData){
			var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				 trHtml+="<tr>";
				 trHtml+="<td>"+item.cert_code+"</td>"; 
				 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="<td>"+item.fac_name+"</td>";
				 trHtml+="<td>"+item.start_date+"</td>";
				 trHtml+="<td>"+item.end_date+"</td>"; 
				 trHtml+="<td>"+item.cert_date+"</td>"; 
				 trHtml+="<td>"+item.issuing_authority+"</td>"; 
				 /* trHtml+="<td>"+item.link_person+"</td>";
				 trHtml+="<td>"+item.link_tel+"</td>"; 
				 trHtml+="<td>"+item.link_mobile+"</td>"; 
				 trHtml+="<td>"+item.cert_memo+"</td>"; 
				 trHtml+="<td>"+item.file_path+"</td>"; 
				 trHtml+="<td>"+item.file_address+"</td>"; */
				 if(item.cert_state == 1 ){
					 trHtml+="<td>在用</td>";
				 }else{
					 trHtml+="<td>停用</td>";
				 }
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08601 药品证件信息",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		 if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08601 药品证件信息.xls",true);
			return;
		} 
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           cert_code:$("#cert_code").val(),
           type_id:liger.get("type_id").getValue()
         };
		ajaxJsonObjectByUrl("queryMedInvCert.do",exportPara,function (responseData){
			var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				 trHtml+="<tr>";
				 trHtml+="<td>"+item.cert_code+"</td>"; 
				 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="<td>"+item.fac_name+"</td>";
				 trHtml+="<td>"+item.start_date+"</td>";
				 trHtml+="<td>"+item.end_date+"</td>"; 
				 trHtml+="<td>"+item.cert_date+"</td>"; 
				 trHtml+="<td>"+item.issuing_authority+"</td>"; 
				 /* trHtml+="<td>"+item.link_person+"</td>";
				 trHtml+="<td>"+item.link_tel+"</td>"; 
				 trHtml+="<td>"+item.link_mobile+"</td>"; 
				 trHtml+="<td>"+item.cert_memo+"</td>"; 
				 trHtml+="<td>"+item.file_path+"</td>"; 
				 trHtml+="<td>"+item.file_address+"</td>"; */
				 if(item.cert_state == 1 ){
					 trHtml+="<td>在用</td>";
				 }else{
					 trHtml+="<td>停用</td>";
				 }
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","药品证件信息.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件号：</td>
            <td align="left" class="l-table-edit-td"><input name="cert_code" type="text" id="cert_code" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件名称：</td>
            <td align="left" class="l-table-edit-td"><input name="cert_inv_name" type="text" id="cert_inv_name" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
            
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  width="10%">状态：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false}" />
            </td>
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">证件号</th>	
                <th width="200">证件类型ID</th>	
                <th width="200">生产厂商</th>
                <th width="200">起始日期</th>	
                <th width="200">截止日期</th>		
                <th width="200">发证日期</th>	
                <th width="200">发证机关</th>	
               <!--  <th width="200">联系人</th>	
                <th width="200">联系电话</th>	
                <th width="200">手机</th>	
                <th width="200">证件描述</th>	
                <th width="200">文档路径</th>	
                <th width="200">存档位置</th>	-->
                <th width="200">是否停用</th>	 
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>

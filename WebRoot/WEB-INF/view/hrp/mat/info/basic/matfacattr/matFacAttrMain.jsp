<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var type_code ='' ;//生成厂商类型编码
    $(function (){
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		$("#tree").css("height", $(window).height()-50);
		
   	 	$("#layout1").ligerLayout({ leftWidth: 210,minLeftWidth:230,allowLeftResize: false}); 
		
   	 	loadTree();
    	query();
    });
    var tree;
	var setting = {      
		data: {
			simpleData: {
				enable: true
			}
		},
		treeNode:{
			open:true
		},
		callback:{
			beforeExpand: beforeExpand ,
			onClick:paraData
		}
	}; 

	function beforeExpand(treeId, treeNode) {
	  	singlePath(treeNode);
	}

	function singlePath(currNode) {
    	var cLevel = currNode.level;
    	//这里假设id是唯一的
    	var cId = currNode.id;
    	
    	//此对象可以保存起来，没有必要每次查找
    	var treeObj = $.fn.zTree.getZTreeObj("tree");
    	//展开的所有节点，这是从父节点开始查找（也可以全文查找）
    	var expandedNodes = treeObj.getNodesByParam("open", true, currNode.getParentNode());

    	for(var i = expandedNodes.length - 1; i >= 0; i--){
    		var node = expandedNodes[i];
    		var level = node.level;
    		var id = node.id;
    		if (cId != id && level == cLevel) {
    			treeObj.expandNode(node, false);
    		}
    	}
	}
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue()});
        
    	grid.options.parms.push({name:'fac_message',value:$('#fac_message').val()});
    	
    	grid.options.parms.push({name:'type_code',value:type_code});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '生产厂商编码', name: 'fac_code', align: 'left',width:100,
                    	 render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id   + "|"  + rowdata.fac_id +"|"+rowdata.fac_name+ "|"  + rowdata.fac_code+"')>"+rowdata.fac_code+"</a>";
							   }
					 		},
					 { display: '生产厂商名称', name: 'fac_name', align: 'left',width:200
						 	},
					 { display: '厂商类别', name: 'type_name', align: 'left',width:200
						 	},
					 { display: '系统模块', name: 'mod_name', align: 'left',width:80
						 	},
                     { display: '法人', name: 'legal', align: 'left',width:80
					 		},
                     { display: '纳税人登记号', name: 'regis_no', align: 'left',width:150
					 		},
                     { display: '电话', name: 'phone', align: 'left',width:100,
					 		},
                     { display: '手机', name: 'mobile', align: 'left',width:100,
					 		},
                     { display: '联系人', name: 'contact', align: 'left',width:80
					 		},
                     { display: '传真', name: 'fax', align: 'left',width:100
					 		},
                     { display: 'EMAIL', name: 'email', align: 'left',width:100,
					 		},
                     { display: '地区', name: 'region', align: 'left',width:120,
					 		},
                     { display: '邮政编码', name: 'zip_code', align: 'left',width:100,
					 		},
                     { display: '地址', name: 'address', align: 'left',width:120,
					 		},
                     { display: '备注', name: 'note', align: 'left',width:150,
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatFacAttr.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
                     	{ line:true },
    	                { text: '清除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true },
						{ text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
						/*{ text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印', id:'print', click: printDate,icon:'print' },
		                { line:true } */
	    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.fac_id +"|"+
								rowdata.fac_name +"|"+
								rowdata.fac_code
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
            	case "add":
            		parent.$.ligerDialog.open({url: 'hrp/mat/info/basic/matfacattr/matFacAttrAddPage.do?isCheck=false&type_code='+type_code, 
	          				height: 600,width: 980, title:'添加',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
	          				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
	          				buttons: [ 
	          				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssFacAttr(); },cls:'l-dialog-btn-highlight' }, 
	          				           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
	          				         ] 
	          		});
          			return;
            	case "init":
                	$.ligerDialog.confirm('确定生成?', function (yes){
                    	if(yes){
                    	
                    		ajaxJsonObjectByUrl("init.do?isCheck=false", {},function(responseData){
                    	
                    			if(responseData.state=="true"){
                   				  query();
                        		}
                        	});
                    	}
                    	
                    });
                	return;
                	
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.fac_id    +"@"+ 
							this.fac_code    +"@"+ 
							this.mod_code  
							) 
						});
                        
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMatFacAttr.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
                	$.ligerDialog.open({url: 'matFacAttrImportPage.do?isCheck=false', height: 500,width: 1100, title:'导入',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,top:10 });
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
                	return;
               }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm ="&group_id="+ 
		   vo[0]   +"&hos_id="+ 
		   vo[1]   +"&fac_id="+ 
		   vo[2]   +"&fac_name="+
		   vo[3]   +"&fac_code="+
		   vo[4];
    	parent.$.ligerDialog.open({ url : 'hrp/mat/info/basic/matfacattr/matFacAttrUpdatePage.do?isCheck=false&' + parm,data:{}, 
    						height: 600,width: 1000 ,  title:'修改',modal:true,showToggle:false,showMax:true , 
    						showMin: false,isResize:true, top:0,
    						parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    						buttons: [ { text: '确定', onclick: function (item, dialog) 
    							{ dialog.frame.saveAssFacAttr(); },cls:'l-dialog-btn-highlight' }, 
    							{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
        
    }
    function loadTree(){
    	$.post("queryHosFacTypeByMenu.do?isCheck=false",null,function (responseData){
    	       tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
    	},"json");
    }
    
    function paraData(){
    	var node = tree.getSelectedNodes();
  	   
		$.each(node,function(index,value){
			
			type_code = value.id;
		})
		query();
    }
    function loadDict(){
		//字典下拉框
    	autocomplete("#fac_id","../../../queryHosFac.do?isCheck=false","id","text",true,true,'',false,'',360);
    	$("#fac_id").ligerTextBox({width:360});
    	$("#fac_message").ligerTextBox({width:200});
	}  
    
  //打印数据
	 function printDate(){
	  
		 $("#resultPrint > table > tbody").empty();
		 
		//有数据直接打印
		/*if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","050115 生产厂商附属表",true);
			return;
		}*/
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		
		
		var printPara={
				
			usePager:false,
            
           fac_id:liger.get("fac_id").getValue()
           
         };
		ajaxJsonObjectByUrl("queryMatFacAttr.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.fac_id+"</td>"; 
					 trHtml+="<td>"+item.fac_code+"</td>"; 
					 trHtml+="<td>"+item.fac_name+"</td>";
					 trHtml+="<td>"+item.bank_name+"</td>"; 
					 trHtml+="<td>"+item.bank_number+"</td>"; 
					 trHtml+="<td>"+item.legal+"</td>"; 
					 trHtml+="<td>"+item.regis_no+"</td>"; 
					 trHtml+="<td>"+item.phone+"</td>"; 
					 trHtml+="<td>"+item.mobile+"</td>"; 
					 trHtml+="<td>"+item.contact+"</td>"; 
					 trHtml+="<td>"+item.fax+"</td>"; 
					 trHtml+="<td>"+item.email+"</td>"; 
					 trHtml+="<td>"+item.region+"</td>"; 
					 trHtml+="<td>"+item.zip_code+"</td>"; 
					 trHtml+="<td>"+item.address+"</td>"; 
					 trHtml+="<td>"+item.note+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
				
			});
			manager.close();
			
			
			lodopPrinterTable("resultPrint","开始打印","050115 生产厂商附属表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","050115 生产厂商附属表集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		
		
		var exportPara={
				
			usePager:false,
            
			fac_id:liger.get("fac_id").getValue()
           
         };
		ajaxJsonObjectByUrl("queryMatFacAttr.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.fac_id+"</td>";
					 trHtml+="<td>"+item.fac_code+"</td>";
					 trHtml+="<td>"+item.fac_name+"</td>";
					 trHtml+="<td>"+item.bank_name+"</td>"; 
					 trHtml+="<td>"+item.bank_number+"</td>"; 
					 trHtml+="<td>"+item.legal+"</td>"; 
					 trHtml+="<td>"+item.regis_no+"</td>"; 
					 trHtml+="<td>"+item.phone+"</td>"; 
					 trHtml+="<td>"+item.mobile+"</td>"; 
					 trHtml+="<td>"+item.contact+"</td>"; 
					 trHtml+="<td>"+item.fax+"</td>"; 
					 trHtml+="<td>"+item.email+"</td>"; 
					 trHtml+="<td>"+item.region+"</td>"; 
					 trHtml+="<td>"+item.zip_code+"</td>"; 
					 trHtml+="<td>"+item.address+"</td>"; 
					 trHtml+="<td>"+item.note+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","050115 生产厂商附属表.xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout1" style="height: 100%;" >
		 <div position="left" title="生产厂商分类">
	     		<div  style="overflow:auto;" >
	     			<ul class="ztree"  id="tree"></ul>
	     		</div>
	     </div>
	     <div position="center" title="生产厂商信息">
			<div id="toptoolbar" ></div>
		    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		        <tr>
		        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">厂商信息：</td>
		            <td align="left" class="l-table-edit-td"><input name="fac_message" type="text" id="fac_message" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left" class="l-table-edit-td"  style="padding-left:20px;"></td>
		        	
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
		            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left" class="l-table-edit-td"  style="padding-left:20px;"></td>
		       		
		        </tr>  
		    </table>
		
			<div id="maingrid"></div>
		</div>
	</div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
			 
				<tr>
				<th width="200">生产厂商ID</th>
                <th width="200">生产厂商编码</th>
                <th width="200">生产厂商名称</th>	
                <th width="200">开户银行</th>	
                <th width="200">银行账号</th>	
                <th width="200">法人</th>	
                <th width="200">纳税人登记号</th>	
                <th width="200">电话</th>	
                <th width="200">手机</th>	
                <th width="200">联系人</th>	
                <th width="200">传真</th>	
                <th width="200">EMAIL</th>	
                <th width="200">地区</th>	
                <th width="200">邮政编码</th>	
                <th width="200">地址</th>	
                <th width="200">备注</th>	
          
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>

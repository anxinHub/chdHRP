<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var show_sum = 0;
    
    
    //页面初始化
    $(function (){
		loadDict();
		loadButton();
    	loadHead(null);	//加载数据
    	loadHotkeys();
    	
    	$("#topmenu").ligerMenuBar({
			items : [ 
				{text : '添加（<u>A</u>）',id : 'add',click : addLeder},
				{text : '删除（<u>D</u>）',id : 'delete',click : deleteLeder},/* {
				text : '打印',
				menu : menu_print
			}, */ 
				{text : '导出',id : 'export',click : exportExcel},
				{text : '导入',id : 'import',click : importExcel},
				{text : '下载导入模板',id : 'downTemplate',click : downExcel} 
			]
		});
    	
    	 ajaxJsonObjectByUrl("queryModByModCode.do?isCheck=false",{},function(responseData){
             if(responseData!=null){
            	 if(responseData[0].start_month=="01"||responseData[0].start_month=="1"){
     				 
                 	show_sum=1;
                  	grid.toggleCol('sum_od', false);
                  	grid.toggleCol('sum_oc', false);
                  }
             }
         }); 
    });
    
    
    
    //查询
    function  query(){
    	
    	var is_bal_show=0;
    	if($("#is_bal_show").is(':checked')){
    		is_bal_show=1;
    	}
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		//grid.options.parms.push({name:'subj_id',value:liger.get("subj_code").getValue().split(".")[0]}); 
   		
   		grid.options.parms.push({name:'subj_code',value:$("#subj_code").val().split(" ")[0]});
       	if(liger.get("is_check").getValue() !=""){
       		grid.options.parms.push({name:'is_check',value:liger.get("is_check").getValue()}); 
       	}
       	grid.options.parms.push({name:'is_bal_show',value:is_bal_show}); 
   		//加载查询条件
   		grid.loadData(grid.where);
	}
	
    
    //加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '科目编码', name: 'subj_code', align: 'left',render:
					function(rowdata,index,value){
                   		if(rowdata.is_last == "0"){
							return rowdata.subj_code;
						}
						return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.subj_code+"|"+rowdata.acc_year+"|"+rowdata.is_last+"|"+rowdata.subj_name+"|"+rowdata.subj_dire+"')>"+rowdata.subj_code+"</a>";
					}
				},
				
				{ display: '科目全称', name: 'subj_name_all', align: 'left'},
				
				{ display: '方向', name: 'subj_dire', align: 'left',render:
					function(rowdata,index,value){
                   		if(rowdata.subj_dire==0){
                   			return "借";
						}
                   		return "贷";
					}
				},
				
				{ display: '币种', name: 'cur_code', align: 'left'},
				
				{ display: '年初余额', name: 'bal_os', align: 'right',formatter:'###,##0.00', render:
					function(rowdata){
	   					return formatNumber(rowdata.bal_os, 2, 1);
					}
			 	},
			 	
			 	{ display: '累计借方', name: 'sum_od', align: 'right',formatter:'###,##0.00',render:
			 		function(rowdata){
   						 return formatNumber(rowdata.sum_od, 2, 1);
					}
				},
				
				{ display: '累计贷方', name: 'sum_oc', align: 'right',formatter:'###,##0.00',render:
					function(rowdata){
 	   					return formatNumber(rowdata.sum_oc, 2, 1);
 					}
			 	},
				
			 	{ display: '期初余额', name: 'end_os', align: 'right',formatter:'###,##0.00',render:
			 		function(rowdata){
   	   					return formatNumber(rowdata.end_os, 2, 1);
   					}
				}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccLeder.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			selectRowButtonOnly:true ,//heightDiff: -10,
			toolbar: { 
				items: [
/*             		{ text: '删除（<u>D</u>）', id:'delete', click: deleteLeder,icon:'delete' },
                	{ line:true }, */
                	                
                	{ text: '导入', id:'import', click: importExcel,icon:'up' },
                	{ line:true },
                	
                	{ text: '打印', id:'print', click: print,icon:'print' },
                	{ line:true }
				]
			},
			onDblClickRow : function (rowdata, rowindex, value){
				openUpdate(
					rowdata.group_id+"|"
					+ rowdata.hos_id+"|"
					+rowdata.copy_code+"|"
					+rowdata.subj_code+"|"
					+rowdata.acc_year+"|"
					+rowdata.is_last+"|"
					+rowdata.subj_name+"|"
					+rowdata.subj_dire
				);
			} ,
			lodop:{
      	    	title:"科目初始账",fn:{
      	    		debit:function(value){//借方
    	          		if(value == 0){
    	          			return "";
    	          		}else{
    	                	return formatNumber(value, 2, 1);
    	                }
    	          	},
					credit:function(value){//贷方
     	          		if(value == 0){
     	          			return "";
     	          		}else{
     	          			return formatNumber(value, 2, 1);
     	          		}
					},
      	         	end_os:function(value){//余额
      	      	   		if(value==0){
      	      	   			return "Q";
      	      	   		}else{
      	      	   			return formatNumber(value, 2, 1);
      	      	   		}
      	        	}
				}
			}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
	
    
    //打印
    function print(){
    	
    	if(grid.getData().length==0){
    		$.ligerDialog.error("请先查询数据！");
    		return;
    	}
    	
    	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
			"rows": []
		};
	   		
		var printPara={
			rowCount:1,
			title:'科目初始帐',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.AccLederService",
			method_name: "queryAccLederPrint",
			bean_name: "accLederService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
		};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara); 
	
      	/* var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});

    		//console.log(grid)
    		var printPara={
    			headCount:2,
    			title:'科目初始账',
    			type:3,
    			columns:grid.getColumns(1),
    			autoFile:true
    		};
    		
    		ajaxJsonObjectByUrl("queryAccLeder.do?isCheck=false", selPara, function(responseData) {
    			printGridView(responseData,printPara);
    	}); */
    }
    
    
    //添加页跳转
    function addLeder(){
    	$.ligerDialog.open({
    		url: 'accLederAddPage.do?isCheck=false', 
    		height: 538,width: 1154, title:'添加',modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveAccLeder(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
    
    
    //删除
	function deleteLeder(){
    	
		var subj_code="";
		
        var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return ; 
        }
        
		var ParamVo =[];
		$(data).each(function (){					
			ParamVo.push(
				//表的主键
				this.subj_code+"@"+this.group_id+"@"+this.hos_id+"@"+this.copy_code+"@"+this.acc_year
			);
			
			subj_code+=this.subj_code+",";
		});
           
		ajaxJsonObjectByUrl("queryAccLederBySubjId.do?isCheck=false",{subj_code : subj_code.substring(0, subj_code.length-1)},function (responseData){
			if(responseData=="false"){
         		$.ligerDialog.error('您所选择的科目参与业务操作，不允许删除！');
         			return;
        	}
			
       		$.ligerDialog.confirm('确定删除?', function (yes){
               	if(yes){
                   	ajaxJsonObjectByUrl("deleteAccLeder.do",{ParamVo : ParamVo.toString()},function (responseData){
                   		if(responseData.state=="true"){
                   			query();
                   		}
                   	});
               	}
			}); 
		});
    }
    
    
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "modify":
                    return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    var menu_print = {
    		width : 120,
    		items : [ {
    			text : '打印',
    			id : 'print',
    			click : itemclick
    		}, {
    			text : '预览',
    			id : 'view',
    			click : itemclick
    		}, {
    			text : '设置',
    			id : 'set',
    			click : itemclick
    		} ]
    	};
    
  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
/* 
		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('K', printBarcode); */

	}
  
  
  	//修改页跳转
    function openUpdate(obj){
  		
		var vo = obj.split("|");
		var mapVo =
			"group_id="+ vo[0] +
		 	"&hos_id=" + vo[1]+
		 	"&copy_code=" + vo[2] +
		 	"&subj_code="+ vo[3] +
		 	"&acc_year="+ vo[4] +
		 	"&show_sum="+ show_sum+
		 	"&subj_name="+ vo[6]+
		 	"&subj_dire="+ vo[7];
			
		if(vo[5]=="1"){			
			parent.openDialog({ 
				url : 'hrp/acc/accleder/accLederUpdatePage.do?'+ mapVo ,
				data:{}, height: 80,width: 100, title:'',
				modal:true,showToggle:false,showMin: false,isResize:true,
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				buttons: [
					{ text: '保存', onclick: function (item, dialog) { dialog.frame.saveAccLeder(); },cls:'l-dialog-btn-highlight' }, 
					{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
				] 
			});
		}
    }
  	
  	
  	//字典下拉框
    function loadDict(){
        $("#is_check").ligerComboBox({data:[{id:"0",text:"否"},{id:"1",text:"是"}],width:80,cancelable: true});
    	//autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true);
    	//查询科目下拉框，根据条件匹配通用，不分页，id=subj_id，text=code name
    	autocomplete("#subj_code","../querySubjId.do?isCheck=false","id","text",true,true);
	}  
    
  	
	//导出数据
	function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","科目初始账.xls",true);
			return;
		}
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		
		var exportPara={
			usePager:false ,
	         subj_id:liger.get("subj_code").getValue().split(".")[0]
	       };
		
		ajaxJsonObjectByUrl("queryAccLeder.do",exportPara,function (responseData){
			
			var trHtml='';
			
			$.each(responseData.Rows,function(idx,item){ 
				 trHtml+="<tr>";
				 trHtml+="<td>"+item.subj_code+"</td>"; 
				 trHtml+="<td>"+item.subj_name+"</td>"; 
				 trHtml+="<td>"+item.subj_dire+"</td>";
				 trHtml+="<td>"+item.cur_code+"</td>";
				 trHtml+="<td>"+item.bal_os+"</td>";
				 trHtml+="<td>"+item.sum_od+"</td>";
				 trHtml+="<td>"+item.sum_oc+"</td>";
				 trHtml+="<td>"+item.end_os+"</td>";
			 	 trHtml+="</tr>";
			 	 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			
			manager.close();
			
			lodopExportExcel("resultPrint","导出Excel","科目初始账.xls",true);
			
	    },true,manager);
		return;
	}	
	
	
	
	function importExcel(){
		  /* 
		  $.ligerDialog.open({url: 'accLederMainImportPage.do', height: 484,width: 1151, title:'导入',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });
	   */
		var para = {
			"column" : [ 
				{"name" : "subj_code","display" : "科目编码","width" : "200","require" : true},
				{"name" : "subj_name","display" : "科目名称","width" : "200","require" : true},
				{"name" : "subj_dire","display" : "方向","width" : "200","require" : true},
				{"name" : "cur_code","display" : "币种","width" : "200"},
				{"name" : "bal_os","display" : "年初余额","width" : "200"},
				{"name" : "sum_od","display" : "累计借方","width" : "200"},
				{"name" : "sum_oc","display" : "累计贷方","width" : "200"} 
			]
		};
	    
		importSpreadView("/hrp/acc/accleder/readAccLederFiles.do?isCheck=false", para); 
	}
	  
	
	function downExcel(){
		location.href = "downLederTemplate.do";
      	return;
	}
	
	
    function loadButton(){
		$("#query").ligerButton({click: query, width:90});
	}

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<!-- <div id="topmenu"></div> -->
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否辅助核算：</td>
            <td align="left" class="l-table-edit-td"><input type="text" id="is_check"/></td>
            <td align="left"><input type="checkbox" id="is_bal_show" ltype="text"  />无余额不显示</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="query" value="查询(Q)"/>
			</td>
			<td align="left"></td>
            
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
		<table width="100%">
			   	<thead>
				<tr>
                <th width="200">科目编码</th>	
                <th width="200">科目名称</th>	
                <th width="200">方向</th>
                <th width="200">币种</th>
                <th width="200">年初余额</th>
                <th width="200">累计借方</th>
                <th width="200">累计贷方</th>
                <th width="200">期初余额</th>	
				 </tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
	</div>
</body>
</html>

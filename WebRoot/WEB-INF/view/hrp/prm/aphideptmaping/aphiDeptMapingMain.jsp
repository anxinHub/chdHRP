<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		
        $("#dept_id").ligerTextBox({width:160});
        $("#sys_dept_id").ligerTextBox({width:160});
        $("#ref_code").ligerTextBox({width:160});
        $("#spilt_perc").ligerTextBox({width:160});
        
        loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	 
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_code1").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'sys_dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
    	  grid.options.parms.push({name:'ref_code',value:liger.get("ref_code").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
        	   { display: '绩效科室编码', name: 'dept_code', align: 'left',
					render : function(rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('"
								+ rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.sys_dept_id  + "')>"
								+ rowdata.dept_code + "</a>";
					}

   	 		},
               { display: '绩效科室编码', name: 'dept_name', align: 'left'
   			 		},
               { display: '平台科室编码', name: 'sys_dept_code', align: 'left'
   			 		},
               { display: '平台科室名称', name: 'sys_dept_name', align: 'left'
   			 		},
               { display: '关系', name: 'ref_name', align: 'left'
   			 		},
               { display: '拆分比例', name: 'spilt_perc', align: 'left'
   			 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAphiDeptMaping.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: addDeptMaping, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: deleteDeptMaping,icon:'delete' },
											{ line:true }, 
				    	                { text: '导出Excel（<u>E</u>）', id:'export', click: exportDeptMaping,icon:'pager' },
						                { line:true },
						                { text: '打印（<u>P</u>）', id:'print', click: printDeptMaping,icon:'print' },
						                { line:true },
						                { text: '下载导入模板（<u>T</u>）', id:'downTemplate', click: templateDeptMaping,icon:'down' },
						                { line:true },
						                { text: '导入（<u>I</u>）', id:'import', click: importDeptMaping,icon:'up' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.sys_dept_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addDeptMaping);
		
		hotkeys('D',deleteDeptMaping);
		
		hotkeys('E',exportDeptMaping);
		
		hotkeys('T',templateDeptMaping);
		
		hotkeys('I',importDeptMaping);
		
		hotkeys('P',printDeptMaping);
		 
	}
    function addDeptMaping(){
    	
    	parent.$.ligerDialog.open({url: 'hrp/prm/aphideptmaping/aphiDeptMapingAddPage.do?isCheck=false', height: $(window).height(),
			width: $(window).width(), title:'添加',modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name });
  		
    }
    
    function deleteDeptMaping (){
    	
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
													this.dept_id   +"@"+ 
													this.sys_dept_id 
													) });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteAphiDeptMapping.do",{ParamVo : ParamVo},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    
    function exportDeptMaping(){
    	
    	//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","8806 奖金科室映射表集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,
            
           dept_id:$("#dept_id").val(),
            
           sys_dept_id:$("#sys_dept_id").val(),
            
           ref_code:$("#ref_code").val(),
            
           spilt_perc:$("#spilt_perc").val()
            
            
         };
		ajaxJsonObjectByUrl("queryAphiDeptMaping.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.dept_id+"</td>"; 
					 trHtml+="<td>"+item.sys_dept_id+"</td>"; 
					 trHtml+="<td>"+item.ref_code+"</td>"; 
					 trHtml+="<td>"+item.spilt_perc+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","8806 奖金科室映射表.xls",true);
	    },true,manager);
    }
    
    function templateDeptMaping(){
    	location.href = "downTemplate.do?isCheck=false";
    	
    }
    
    function importDeptMaping (){
    	
    	$.ligerDialog.open({url: 'aphiDeptMapingImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
        
    }
    
    function printDeptMaping (){
    	
    	//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","8806 奖金科室映射表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,
            
           dept_id:$("#dept_id").val(),
            
           sys_dept_id:$("#sys_dept_id").val(),
            
           ref_code:$("#ref_code").val(),
            
           spilt_perc:$("#spilt_perc").val()
            
            
         };
		ajaxJsonObjectByUrl("queryAphiDeptMaping.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.dept_id+"</td>"; 
					 trHtml+="<td>"+item.sys_dept_id+"</td>"; 
					 trHtml+="<td>"+item.ref_code+"</td>"; 
					 trHtml+="<td>"+item.spilt_perc+"</td>"; 
				 trHtml+="</tr>";
				
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","8806 奖金科室映射表",true);
	    },true,manager);
    }
   
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&dept_id="+ 
			vo[3]   +"&sys_dept_id="+ 
			vo[4];
		parent.$.ligerDialog.open({url: 'hrp/prm/aphideptmaping/aphiDeptMapingUpdatePage.do?isCheck=false&'+parm, height: $(window).height(),
			width: $(window).width(), title:'修改',modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name });

    }
    function loadDict(){
            //字典下拉框
            
    	autocomplete("#dept_code1","../queryPrmDeptDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_code","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#ref_code","../queryPrmDeptRefDict.do?isCheck=false","id","text",true,true);
            
         }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">绩效科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_code1" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">平台科室：</td>
            <td align="left" class="l-table-edit-td"><input name="sys_dept_id" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">关系</td>
            <td align="left" class="l-table-edit-td"><input name="ref_code" type="text" id="ref_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
			 
				<tr>
                <th width="200">变更ID</th>	
                <th width="200">部门ID</th>	
                <th width="200">平台科室变更ID</th>	
                <th width="200">平台科室ID</th>	
                <th width="200">01:平行 02:拆分 C03:合并</th>	
                <th width="200">拆分比例</th>	
          
				   	</tr>
			   	</thead>
			   	<tbody>
			   		<tr>
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
			   		</tr>
			   	</tbody>
	   	</table>
   	</div>
</body>
</html>

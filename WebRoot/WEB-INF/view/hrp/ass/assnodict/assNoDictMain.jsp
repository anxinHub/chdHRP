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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		
        $("#ass_id").ligerTextBox({width:160});
        $("#ass_no").ligerTextBox({width:160});
        $("#ass_code").ligerTextBox({width:160});
        $("#ass_name").ligerTextBox({width:160});
        $("#ass_type_code").ligerTextBox({width:160});
        $("#acc_type_code").ligerTextBox({width:160});
        $("#ass_unit").ligerTextBox({width:160});
        $("#is_measure").ligerTextBox({width:160});
        $("#is_depre").ligerTextBox({width:160});
        $("#ass_depre_code").ligerTextBox({width:160});
        $("#depre_years").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
        $("#ass_spec").ligerTextBox({width:160});
        $("#ass_model").ligerTextBox({width:160});
        $("#fac_id").ligerTextBox({width:160});
        $("#fac_no").ligerTextBox({width:160});
        $("#ven_id").ligerTextBox({width:160});
        $("#ven_no").ligerTextBox({width:160});
        $("#usage_code").ligerTextBox({width:160});
        $("#gb_code").ligerTextBox({width:160});
        $("#wbx_code").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'ass_id',value:$("#ass_id").val()}); 
    	  grid.options.parms.push({name:'ass_no',value:$("#ass_no").val()}); 
    	  grid.options.parms.push({name:'ass_code',value:$("#ass_code").val()}); 
    	  grid.options.parms.push({name:'ass_name',value:$("#ass_name").val()}); 
    	  grid.options.parms.push({name:'ass_type_code',value:$("#ass_type_code").val()}); 
    	  grid.options.parms.push({name:'acc_type_code',value:$("#acc_type_code").val()}); 
    	  grid.options.parms.push({name:'ass_unit',value:$("#ass_unit").val()}); 
    	  grid.options.parms.push({name:'is_measure',value:$("#is_measure").val()}); 
    	  grid.options.parms.push({name:'is_depre',value:$("#is_depre").val()}); 
    	  grid.options.parms.push({name:'ass_depre_code',value:$("#ass_depre_code").val()}); 
    	  grid.options.parms.push({name:'depre_years',value:$("#depre_years").val()}); 
    	  grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 
    	  grid.options.parms.push({name:'ass_spec',value:$("#ass_spec").val()}); 
    	  grid.options.parms.push({name:'ass_model',value:$("#ass_model").val()}); 
    	  grid.options.parms.push({name:'fac_id',value:$("#fac_id").val()}); 
    	  grid.options.parms.push({name:'fac_no',value:$("#fac_no").val()}); 
    	  grid.options.parms.push({name:'ven_id',value:$("#ven_id").val()}); 
    	  grid.options.parms.push({name:'ven_no',value:$("#ven_no").val()}); 
    	  grid.options.parms.push({name:'usage_code',value:$("#usage_code").val()}); 
    	  grid.options.parms.push({name:'gb_code',value:$("#gb_code").val()}); 
    	  grid.options.parms.push({name:'wbx_code',value:$("#wbx_code").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资产ID', name: 'ass_id', align: 'left'
					 		},
                     { display: '资产NO', name: 'ass_no', align: 'left'
					 		},
                     { display: '资产编码', name: 'ass_code', align: 'left'
					 		},
                     { display: '资产名称', name: 'ass_name', align: 'left'
					 		},
                     { display: '类别编码', name: 'ass_type_code', align: 'left'
					 		},
                     { display: '财务分类编码', name: 'acc_type_code', align: 'left'
					 		},
                     { display: '单位', name: 'ass_unit', align: 'left'
					 		},
                     { display: '是否计量', name: 'is_measure', align: 'left'
					 		},
                     { display: '是否折旧', name: 'is_depre', align: 'left'
					 		},
                     { display: '折旧方法编码', name: 'ass_depre_code', align: 'left'
					 		},
                     { display: '折旧年限', name: 'depre_years', align: 'left'
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left'
					 		},
                     { display: '规格', name: 'ass_spec', align: 'left'
					 		},
                     { display: '型号', name: 'ass_model', align: 'left'
					 		},
                     { display: '生产厂商ID', name: 'fac_id', align: 'left'
					 		},
                     { display: '生产厂商NO', name: 'fac_no', align: 'left'
					 		},
                     { display: '主要供应商ID', name: 'ven_id', align: 'left'
					 		},
                     { display: '主要供应商NO', name: 'ven_no', align: 'left'
					 		},
                     { display: '资产用途', name: 'usage_code', align: 'left'
					 		},
                     { display: '国标码', name: 'gb_code', align: 'left'
					 		},
                     { display: '五笔码', name: 'wbx_code', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssNoDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
										{ line:true }, 
						                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
						                { line:true },
						                { text: '导入', id:'import', click: itemclick,icon:'up' },
						                { line:true },
						                { text: '打印', id:'print', click: printDate,icon:'print' },
						                { line:true },
						                { text: '导出Excel', id:'export', click: exportExcel,icon:'pager' },
						                { line:true },
						                
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.ass_id   + "|" + 
								rowdata.ass_no 
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
              		$.ligerDialog.open({url: 'assNoDictAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssNoDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
																this.copy_code   +"@"+ 
																this.ass_id   +"@"+ 
																this.ass_no 
																) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssNoDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
                	$.ligerDialog.open({url: 'assNoDictImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
		var parm = 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index]   +"&"+ 
			vo[column_index] 
		
    	$.ligerDialog.open({ url : 'assNoDictUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssNoDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         }  
    
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","050102 资产变更字典",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,
            
            
            
            
           ass_id:$("#ass_id").val(),
            
           ass_no:$("#ass_no").val(),
            
           ass_code:$("#ass_code").val(),
            
           ass_name:$("#ass_name").val(),
            
           ass_type_code:$("#ass_type_code").val(),
            
           acc_type_code:$("#acc_type_code").val(),
            
           ass_unit:$("#ass_unit").val(),
            
           is_measure:$("#is_measure").val(),
            
           is_depre:$("#is_depre").val(),
            
           ass_depre_code:$("#ass_depre_code").val(),
            
           depre_years:$("#depre_years").val(),
            
           is_stop:$("#is_stop").val(),
            
           ass_spec:$("#ass_spec").val(),
            
           ass_model:$("#ass_model").val(),
            
           fac_id:$("#fac_id").val(),
            
           fac_no:$("#fac_no").val(),
            
           ven_id:$("#ven_id").val(),
            
           ven_no:$("#ven_no").val(),
            
           usage_code:$("#usage_code").val(),
            
           gb_code:$("#gb_code").val(),
            
            
           wbx_code:$("#wbx_code").val()
            
            
         };
		ajaxJsonObjectByUrl("queryAssNoDict.do",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.ass_id+"</td>"; 
					 trHtml+="<td>"+item.ass_no+"</td>"; 
					 trHtml+="<td>"+item.ass_code+"</td>"; 
					 trHtml+="<td>"+item.ass_name+"</td>"; 
					 trHtml+="<td>"+item.ass_type_code+"</td>"; 
					 trHtml+="<td>"+item.acc_type_code+"</td>"; 
					 trHtml+="<td>"+item.ass_unit+"</td>"; 
					 trHtml+="<td>"+item.is_measure+"</td>"; 
					 trHtml+="<td>"+item.is_depre+"</td>"; 
					 trHtml+="<td>"+item.ass_depre_code+"</td>"; 
					 trHtml+="<td>"+item.depre_years+"</td>"; 
					 trHtml+="<td>"+item.is_stop+"</td>"; 
					 trHtml+="<td>"+item.ass_spec+"</td>"; 
					 trHtml+="<td>"+item.ass_model+"</td>"; 
					 trHtml+="<td>"+item.fac_id+"</td>"; 
					 trHtml+="<td>"+item.fac_no+"</td>"; 
					 trHtml+="<td>"+item.ven_id+"</td>"; 
					 trHtml+="<td>"+item.ven_no+"</td>"; 
					 trHtml+="<td>"+item.usage_code+"</td>"; 
					 trHtml+="<td>"+item.gb_code+"</td>"; 
					 trHtml+="<td>"+item.wbx_code+"</td>"; 
				 trHtml+="</tr>";
				
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","050102 资产变更字典",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","050102 资产变更字典集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var cost_item = liger.get("cost_item_id").getValue();
		
		var dept_dict = liger.get("dept_id").getValue();
		
		var printPara={
				
			usePager:false,
            
            
            
            
           ass_id:$("#ass_id").val(),
            
           ass_no:$("#ass_no").val(),
            
           ass_code:$("#ass_code").val(),
            
           ass_name:$("#ass_name").val(),
            
           ass_type_code:$("#ass_type_code").val(),
            
           acc_type_code:$("#acc_type_code").val(),
            
           ass_unit:$("#ass_unit").val(),
            
           is_measure:$("#is_measure").val(),
            
           is_depre:$("#is_depre").val(),
            
           ass_depre_code:$("#ass_depre_code").val(),
            
           depre_years:$("#depre_years").val(),
            
           is_stop:$("#is_stop").val(),
            
           ass_spec:$("#ass_spec").val(),
            
           ass_model:$("#ass_model").val(),
            
           fac_id:$("#fac_id").val(),
            
           fac_no:$("#fac_no").val(),
            
           ven_id:$("#ven_id").val(),
            
           ven_no:$("#ven_no").val(),
            
           usage_code:$("#usage_code").val(),
            
           gb_code:$("#gb_code").val(),
            
            
           wbx_code:$("#wbx_code").val()
            
            
         };
		ajaxJsonObjectByUrl("queryAssNoDict.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.ass_id+"</td>"; 
					 trHtml+="<td>"+item.ass_no+"</td>"; 
					 trHtml+="<td>"+item.ass_code+"</td>"; 
					 trHtml+="<td>"+item.ass_name+"</td>"; 
					 trHtml+="<td>"+item.ass_type_code+"</td>"; 
					 trHtml+="<td>"+item.acc_type_code+"</td>"; 
					 trHtml+="<td>"+item.ass_unit+"</td>"; 
					 trHtml+="<td>"+item.is_measure+"</td>"; 
					 trHtml+="<td>"+item.is_depre+"</td>"; 
					 trHtml+="<td>"+item.ass_depre_code+"</td>"; 
					 trHtml+="<td>"+item.depre_years+"</td>"; 
					 trHtml+="<td>"+item.is_stop+"</td>"; 
					 trHtml+="<td>"+item.ass_spec+"</td>"; 
					 trHtml+="<td>"+item.ass_model+"</td>"; 
					 trHtml+="<td>"+item.fac_id+"</td>"; 
					 trHtml+="<td>"+item.fac_no+"</td>"; 
					 trHtml+="<td>"+item.ven_id+"</td>"; 
					 trHtml+="<td>"+item.ven_no+"</td>"; 
					 trHtml+="<td>"+item.usage_code+"</td>"; 
					 trHtml+="<td>"+item.gb_code+"</td>"; 
					 trHtml+="<td>"+item.wbx_code+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","050102 资产变更字典.xls",true);
	    },true,manager);
		return;
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产ID：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_id" type="text" id="ass_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产NO：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_no" type="text" id="ass_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产编码：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_code" type="text" id="ass_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">类别编码：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_type_code" type="text" id="ass_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务分类编码：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_type_code" type="text" id="acc_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_unit" type="text" id="ass_unit" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否计量：</td>
            <td align="left" class="l-table-edit-td"><input name="is_measure" type="text" id="is_measure" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否折旧：</td>
            <td align="left" class="l-table-edit-td"><input name="is_depre" type="text" id="is_depre" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法编码：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_depre_code" type="text" id="ass_depre_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年限：</td>
            <td align="left" class="l-table-edit-td"><input name="depre_years" type="text" id="depre_years" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_spec" type="text" id="ass_spec" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">型号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_model" type="text" id="ass_model" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商ID：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商NO：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_no" type="text" id="fac_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主要供应商ID：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主要供应商NO：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_no" type="text" id="ven_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产用途：</td>
            <td align="left" class="l-table-edit-td"><input name="usage_code" type="text" id="usage_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">国标码：</td>
            <td align="left" class="l-table-edit-td"><input name="gb_code" type="text" id="gb_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">五笔码：</td>
            <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text" id="wbx_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
			 
				<tr>
                <th width="200">资产ID</th>	
                <th width="200">资产NO</th>	
                <th width="200">资产编码</th>	
                <th width="200">资产名称</th>	
                <th width="200">类别编码</th>	
                <th width="200">财务分类编码</th>	
                <th width="200">单位</th>	
                <th width="200">是否计量</th>	
                <th width="200">是否折旧</th>	
                <th width="200">折旧方法编码</th>	
                <th width="200">折旧年限</th>	
                <th width="200">是否停用</th>	
                <th width="200">规格</th>	
                <th width="200">型号</th>	
                <th width="200">生产厂商ID</th>	
                <th width="200">生产厂商NO</th>	
                <th width="200">主要供应商ID</th>	
                <th width="200">主要供应商NO</th>	
                <th width="200">资产用途</th>	
                <th width="200">国标码</th>	
                <th width="200">五笔码</th>	
          
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
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
                <td>1</td>	
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

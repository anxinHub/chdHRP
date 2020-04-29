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
    var sexData = [{ id:0, text: '否' }, { id:1, text: '是'}];
   
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		
        $("#bill_table").ligerTextBox({width:160});
        $("#is_year_pref").ligerTextBox({width:160});
        $("#is_month_pref").ligerTextBox({width:160});
        $("#is_day_pref").ligerTextBox({width:160});
        
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'bill_table',value:$("#bill_table").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     { display: '单据名称', name: 'bill_name', align: 'left',width:180
					 		},

                     { display: '标准前缀', name: 'pref', align: 'left',width:180
					 		},
					 		{ display: '连接符',width:60, name: 'pref_point', align: 'center',
					 			editor: { type: 'string'}
						 },
							{
								display : '前缀仓库',
								name : 'is_store',
								align : 'center',
								width:60,
								render : function(rowdata, rowindex,
										value) {
									var str = "<select id='is_store"+rowindex+"'  name = 'is_store"+rowindex+"' style='margin-top:5px;width:60;'>";
									if (rowdata.is_store == 1) {
										str = str
												+ "<option value='"+rowdata.is_store+"' selected='selected'>是</option>";
										str = str
												+ "<option value='0' >否</option>";
									} else if (rowdata.is_store == 0) {
										str = str
												+ "<option value='"+rowdata.is_store+"' selected='selected'>否</option>";
										str = str
												+ "<option value='1' >是</option>";
									}
									str = str + "</select>";
									return str;
								}
							},{ display: '连接符',width:60, name: 'is_store_point', align: 'center',
					 			editor: { type: 'string'}
							 },
					 		{
								display : '前缀年',
								name : 'is_year',
								align : 'center',
								width:60,
								render : function(rowdata, rowindex,
										value) {
									var str = "<select id='is_year"+rowindex+"'  name = 'is_year"+rowindex+"' style='margin-top:5px;width:60;'>";
									if (rowdata.is_year == 1) {
										str = str
												+ "<option value='"+rowdata.is_year+"' selected='selected'>是</option>";
										str = str
												+ "<option value='0' >否</option>";
									} else if (rowdata.is_year == 0) {
										str = str
												+ "<option value='"+rowdata.is_year+"' selected='selected'>否</option>";
										str = str
												+ "<option value='1' >是</option>";
									}
									str = str + "</select>";
									return str;
								}
							},{ display: '连接符',width:60, name: 'is_year_point', align: 'center',
					 			editor: { type: 'string'}
							 },
							{
								display : '前缀月',
								name : 'is_month',
								align : 'center',
								width:50,
								render : function(rowdata, rowindex,
										value) {
									var str = "<select id='is_month"+rowindex+"'  name = 'is_month"+rowindex+"' style='margin-top:5px;width:60;'>";
									if (rowdata.is_month == 1) {
										str = str
												+ "<option value='"+rowdata.is_month+"' selected='selected'>是</option>";
										str = str
												+ "<option value='0' >否</option>";
									} else if (rowdata.is_month == 0) {
										str = str
												+ "<option value='"+rowdata.is_month+"' selected='selected'>否</option>";
										str = str
												+ "<option value='1' >是</option>";
									}
									str = str + "</select>";
									return str;
								}
							},{ display: '连接符',width:60, name: 'is_month_point', align: 'center',
					 			editor: { type: 'string'}
							 },
							{
								display : '前缀日',
								name : 'is_day',
								align : 'center',
								width:60,
								render : function(rowdata, rowindex,
										value) {
									var str = "<select id='is_day"+rowindex+"'  name = 'is_day"+rowindex+"' style='margin-top:5px;width:60;'>";
									if (rowdata.is_day == 1) {
										str = str
												+ "<option value='"+rowdata.is_day+"' selected='selected'>是</option>";
										str = str
												+ "<option value='0' >否</option>";
									} else if (rowdata.is_day == 0) {
										str = str
												+ "<option value='"+rowdata.is_day+"' selected='selected'>否</option>";
										str = str
												+ "<option value='1' >是</option>";
									}
									str = str + "</select>";
									return str;
								}
							},{ display: '连接符',width:60, name: 'is_day_point', align: 'center',
					 			editor: { type: 'string'}
							 },
							
					 { display: '流水号位数',width:60, name: 'seq_no', align: 'center',
					 			editor: { type: 'int'}
						 },

					 { display:'操作',name:'edit',align:'center',
 						 		render:function(rowdata,rowindex,value){	
						 			//alert(rowdata.is_year_pref)
 						 			return "<div class='l-button' style='width: 60px; margin-top:1px; margin-left: 60px;' ligeruiid='Button1004'" 
									+"onclick=save('"+rowdata.bill_table+"','"+rowdata.bill_name+"','"+rowdata.seq_no+"','"+rowindex+"')>"
				       					+"<span>保存</span></div>";
						 		}
					 },
					
	                     ],
	                 enabledEdit: true,
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAssBillNo.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
										{ text: '打印', id:'print', click: printDate,icon:'print' }
				    				]}
				    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    function save(v1,v2,v3,row){
    	grid.endEdit(row);
    	var data=gridManager.getData();
    
    	var formPara={
    			
    	bill_table:v1,
        bill_name:v2,
        seq_no:data[row].seq_no,
        is_year:$("#is_year" + row + "").val(),
        is_month:$("#is_month" + row + "").val(),
        is_day:$("#is_day" + row + "").val(),
        is_store:$("#is_store" + row + "").val()
        };
        ajaxJsonObjectByUrl("updateAssBillNo.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }


    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'assBillNoAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssBillNo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
																this.bill_table 
																) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssBillNo.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
                	$.ligerDialog.open({url: 'assBillNoImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
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
		if("null"==vo[3]){
			return false;
			
		}
		var parm ="&group_id="+ 
		   vo[0]   +"&hos_id="+ 
		   vo[1]   +"&copy_code="+ 
		   vo[2]   +"&bill_table="+ 
		   vo[3]; 	
    	$.ligerDialog.open({ url : 'assBillNoUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssBillNo(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         }  
    
  
	  function printDate(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'资产单据号规则设置',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssBillNo.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","资产单据号规则设置集.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');
		var exportPara={
			usePager:false,
           bill_no:$("#bill_table").val()
         };
		ajaxJsonObjectByUrl("queryAssBillNo.do",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				var trHtml="<tr>";
				 trHtml+="<td>"+item.bill_name+"</td>"; 
				 trHtml+="<td>"+item.pref+"</td>"; 
				 trHtml+="<td>"+item.seq_no+"</td>"; 
			 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","资产单据号规则设置.xls",true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务单据：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_table" type="text" id="bill_table" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			   	<thead>
				<tr>
                <th width="200">单据名称</th>	
                <th width="200">标准前缀</th>	
                <th width="200">流水号位数</th>	
				   	</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>

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
      $("#ass_code").ligerTextBox({width:160});

    });
	
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'ass_code',value:$("#ass_code").val()}); 
    
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资产编码', name: 'ass_code', align: 'left',width: '80',
                    	 render : function(rowdata, rowindex,
  								value) {
 								
 								return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code   + "|"  + rowdata.ass_id +"')>"+rowdata.ass_code+"</a>";

                     	 }
					 		},
                     { display: '资产名称', name: 'ass_name', align: 'left',width: '160'
					 		},
                     { display: '资产分类', name: 'ass_type_name', align: 'left',width: '80'
					 		},
                     { display: '财务分类', name: 'acc_type_name', align: 'left',width: '80'
					 		},
                     { display: '单位', name: 'ass_unit_name', align: 'left',width: '40'
					 		},
                     { display: '是否计量', name: 'is_measure', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_measure == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '60'
					 			
					 		},
                     { display: '是否折旧', name: 'is_depre', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_depre == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '60'
					 		},
                     { display: '折旧方法编码', name: 'ass_depre_name', align: 'left',width: '80'
					 		},
                     { display: '折旧年限', name: 'depre_years', align: 'left',width: '80'
					 		},
                     { display: '是否停用', name: 'is_stop', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_stop == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '60'
					 		},
                     { display: '规格', name: 'ass_spec', align: 'left',width: '80'
					 		},
                     { display: '型号', name: 'ass_model', align: 'left',width: '80'
					 		},
                     { display: '生产厂商', name: 'fac_code', align: 'left',width: '180'
					 		},
                     { display: '主要供应商', name: 'ven_code', align: 'left',width: '180'
					 		},
                     { display: '资产用途', name: 'usage_name', align: 'left',width: '80'
					 		},
                     { display: '国标码', name: 'gb_code', align: 'left',width: '80'
					 		},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgAssDict.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
						{ text: '打印', id:'print', click: printDate,icon:'print' },
			            { line:true },
		                { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.ass_id 
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
                	
                	var index = layer.open({
    					type : 2,
    					title : '添加',
    					shadeClose : false,
    					shade : false,
    					maxmin : true, //开启最大化最小化按钮
    					area : [ '893px', '500px' ],
    					content : 'budgAssDictAddPage.do?isCheck=false'
    				});
    				layer.full(index);
                	
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
							this.ass_id 
							) 
						});
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgAssDict.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
                	$.ligerDialog.open({url: 'budgAssDictImportPage.do?isCheck=false', height: 400,width: 900, title:'导入',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true });
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
		var parm ="&group_id="+ vo[0] +"&hos_id="+ vo[1] +"& copy_code="+ vo[2] +"&ass_id="+ vo[3]; 
    	
		$.ligerDialog.open({url: 'budgAssDictUpdatePage.do?isCheck=false&'+parm, height: 500,width: 800, 
				title:'修改',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true });
    }
    function loadDict(){
    	 //字典下拉框
    	autocomplete("#ass_type_id","../../../../ass/queryAssTypeDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#fac_id","../../../../ass/queryHosFacDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#ven_id","../../../../ass/queryHosSupDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#usage_code","../../../../ass/queryAssUsageDict.do?isCheck=false","id","text",true,true);
            
         }  
    
	function printDate(){
	    	
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
		
    	grid.options.parms.push({name:'ass_code',value:$("#ass_code").val()}); 

        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"资产字典信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgAssDict.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});	
	   		
	    }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
      
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产编码：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_code" type="text" id="ass_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
           <%--  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id" ltype="text" value="${ass_type_id }" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> --%>
        </tr> 
    </table>
    

	<div id="maingrid"></div>
</body>
</html>

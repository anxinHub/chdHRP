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
    var budg_year;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
       $("#budg_year").change(function(){
       	 query() ;
       })
       
       $("#subj_code").change(function(){
       	 query() ;
       })
       
       $("#subj_level").change(function(){
       	 query() ;
       })
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'budg_year',value:budg_year.getValue()}); 
    	  grid.options.parms.push({name:'subj_code',value:$("#subj_code").val()});
    	  grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue() });
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    /*  { display: '预算年度', name: 'budg_year', align: 'left'
                    	 
					 		}, */
                     { display: '科目编码', name: 'subj_code', align: 'left',
					 			render : function(rowdata, rowindex,value) {
		                        	 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+
		                        			"|"+rowdata.copy_code+"|"+rowdata.budg_year+"|"+rowdata.subj_code+"')>"+rowdata.subj_code+"</a>";
		                        }
					 		},
                     { display: '科目名称', name: 'subj_name', align: 'left'
					 		},
                     { display: '科目全称', name: 'subj_name_all', align: 'left',width:300},
                     { display: '科目类别', name: 'type_name', align: 'left',width:70},
                     /* { display: '科目性质', name: 'subj_nature_name', align: 'left'}, */
                     { display: '上级编码', name: 'super_code', align: 'left'},
                     { display: '科目级次', name: 'subj_level', align: 'center',width:70},
                     { display: '是否末级', name: 'last', align: 'center',width:70},
					 { display: '是否结转', name: 'caarried', align: 'center',width:70}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBudgCostSubj.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
						{ text: '继承', id:'extend', click: itemclick,icon:'down' },
						{ line:true }, 
						{ text: '类别批量维护', id:'bathUpdate', click: itemclick,icon:'add' },
						{ line:true }, 
						/* { text: '由会计科目生成', id:'relationShip', click: itemclick,icon:'add' },
						{ line:true }, */
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
								rowdata.budg_year   + "|" + 
								rowdata.subj_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
    	var budg_year= $("#budg_year").val()
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'budgCostSubjAddPage.do?isCheck=false&budg_year='+budg_year, height: 400,width: 350, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostBudgSubj(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.budg_year   +"@"+ 
							this.subj_code +"@"+
							this.super_code	+"@"+
							this.last
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgCostSubj.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "extend":
                	var map ={
            			budg_year:$("#budg_year").val()
             		};
                	 $.ligerDialog.confirm('确定继承上一年度的支出预算科目及与会计科目的对应关系?', function (yes){
                        if(yes){
			               	ajaxJsonObjectByUrl("extendBudgCostSubj.do?isCheck=false",map,function (responseData){
			               		if(responseData.state=="true"){
			               			query()
			               		}
	               			});
                        }
                	 })
            	return;
                case "import":
                	$.ligerDialog.open({url: 'budgCostSubjImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:true,showMin:false,isResize:true });
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
                	return;
                case "relationShip":
                	$.ligerDialog.open({url: 'budgAccRelationshipMainPage.do?isCheck=false', height: 500 ,width:1000 , title:'与会计科目关系',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                
                case "bathUpdate":
             	   var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
								this.budg_year  +"@"+
								this.subj_code 
							) 
						});
                    }
             	   $.ligerDialog.open({url: 'budgBathUpdateMainPage.do?isCheck=false&ParamVo='+ ParamVo.toString(), height: 260,width: 280, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostBudgSubj(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
               }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&copy_code="+ vo[2]+"&budg_year="+ vo[3]+"&subj_code="+ vo[4];
		
    	$.ligerDialog.open({ url : 'budgCostSubjUpdatePage.do?isCheck=false&' + parm,data:{}, height: 400,width: 350, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostBudgSubj(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
        //字典下拉框
        //预算年度下拉框
        //autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,"",true);
        
        budg_year = $("#budg_year").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: true
        });
        
    	//科目级次下拉框
        autocomplete("#subj_level","../../../queryBudgSubjLevel.do?isCheck=false","id","text",true,true);
    	
        $("#subj_code").ligerTextBox({width:200});
     }  
    
  //打印数据
	function printDate(){
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
    	grid.options.newPage=1;
		
    	grid.options.parms.push({name:'budg_year',value:budg_year.getValue() });
        grid.options.parms.push({name:'subj_code',value:$("#subj_code").val() });
        grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue() });
        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"支出预算科目信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgCostSubj.do?isCheck=false", selPara, function(responseData) {
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
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目级次：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
</body>
</html>

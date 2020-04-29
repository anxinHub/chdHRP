<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		/* var type_code = liger.get("type_code").getValue();
   		if(!type_code){
   			$.ligerDialog.error('项目分类为必填项');
   			return;
   		} */
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	grid.options.parms.push({name:'level_code',value:liger.get("level_code").getValue()}); 
    	grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue()}); 
    	grid.options.parms.push({name:'con_emp_id',value:liger.get("con_emp_id").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	
    	var gridurl = "";
    	if('${attr_code}' == '01'){
    		gridurl = 'queryAccMatchInitQuery.do?attr_code='+'${attr_code}';
    	}else if('${attr_code}' == '02'){
    		gridurl = 'queryAccMatchInit.do?attr_code='+'${attr_code}';
    	}
    	
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '项目编码', name: 'proj_code', align: 'left',
							render : function(rowdata, rowindex, value) {
								return "<a href=javascript:openUpdate('"
										+ rowdata.proj_id + "|"
										+ rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.acc_year + "')>"
										+ rowdata.proj_code + "</a>";
							}
					 },
                     { display: '项目名称', name: 'proj_name', align: 'left'
					 },
                     { display: '负责人', name: 'emp_name', align: 'left'
					 },
                     { display: '年初余额', name: 'bal_os', align: 'right',
						 render:function(rowdata){
   	   						 return formatNumber(rowdata.bal_os, 2, 1);
   						 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:gridurl,
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true },
    	                { text: '打印', id:'print', click: print,icon:'print' },
    	                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.proj_id   + "|" + 
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function print(){
    	
    	if(grid.getData().length==0){
    		$.ligerDialog.error("请先查询数据！");
    		return;
    	}

    	var heads={
      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
      		  "rows": [
  	          {"cell":0,"value":"项目分类："+$("#type_code").val() ,"colSpan":"5"} 
      		  ]
      	};
      	
     		var printPara={
     			title: "配套资金初始账",//标题
     			columns: JSON.stringify(grid.getPrintColumns()),//表头
     			class_name: "com.chd.hrp.acc.service.books.memorandumbook.AccMatchInitService",
	  			method_name: "queryAccMatchInitPrint",
	  			bean_name: "accMatchInitService",
	  			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
  			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
     		};
      	
     		//执行方法的查询条件
     		$.each(grid.options.parms,function(i,obj){
     			printPara[obj.name]=obj.value;
      	});
     		
      	officeGridPrint(printPara);
    }
    
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accMatchInitAddPage.do?isCheck=false&attr_code='+'${attr_code}', height: 380,width: 420, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccPayType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							//表的主键
							this.proj_id   +"@"+ 
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.acc_year 
							);
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deletBatchAccMatchInit.do?isCheck=false&attr_code="+'${attr_code}',{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
            }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|");
			var parm = "&proj_id=" + vo[0] + 
			   "&group_id=" + vo[1] + 
			   "&hos_id=" + vo[2] + 
			   "&copy_code=" + vo[3]+
			   "&acc_year=" + vo[4]

    	$.ligerDialog.open({ url : 'accMatchInitUpdatePage.do?isCheck=false&attr_code='+'${attr_code}' + parm,data:{}, height: 300,width: 400, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccPayType(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_code","../../sys/queryProjTypeDict.do?isCheck=false&attr_code="+'${attr_code}',"id","text",true,true);
    	autocomplete("#level_code","../../sys/queryProjLevelDict.do?isCheck=false","id","text",true,true);  
    	
    	autocomplete("#proj_id","../../sys/queryProjDictDict.do?isCheck=false&attr_code="+'${attr_code}',"id","text",true,true,'','','',subjWidth);  
    	var param = {"is_last":1};
    	autocomplete("#dept_id","../../sys/queryDeptAll.do?isCheck=false","id","text",true,true,param);  
    	autocomplete("#con_emp_id","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);  
    	
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><!-- <b><font color="red">*</font></b> -->项目分类：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目级别：</td>
            <td align="left" class="l-table-edit-td"><input name="level_code" type="text" id="level_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目：</td>
            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>

</body>
</html>

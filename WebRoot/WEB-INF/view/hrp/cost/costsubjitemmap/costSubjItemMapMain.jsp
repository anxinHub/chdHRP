<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'item_code',value:$("#item_code").val()}); 
    	grid.options.parms.push({name:'subj_code',value:$("#subj_code").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目年度', name: 'acc_year', align: 'left'},
                     { display: '科目编码', name: 'subj_code', align: 'left',
 						render : function(rowdata, rowindex,value) {
							return "<a href=javascript:openUpdate('"+ rowdata.group_id + "|" 
							                                        + rowdata.hos_id   + "|"
							                                        + rowdata.copy_code + "|"
							                                        + rowdata.acc_year + "|"
							                                        + rowdata.subj_code + "|"
							                                        + rowdata.item_code + "|"							                                        + rowdata.subj_id+"')>"
							                                        + rowdata.subj_code+"</a>";
					   }
 					 },
                     { display: '科目名称', name: 'subj_name', align: 'left'},
                     { display: '成本项目编码', name: 'item_code', align: 'left'},
                     { display: '成本项目名称', name: 'item_name', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostSubjItemMap.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
						{ line:true }, 
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id  + "|" + 
								rowdata.hos_id  + "|" + 
								rowdata.acc_year + "|" + 
								rowdata.copy_code + "|" + 
								rowdata.subj_code + "|" + 
								rowdata.item_code
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

     function add_open(){
    	 $.ligerDialog.open({
    		    url: 'costSubjItemMapAddPage.do?isCheck=false',
    		    height: 300,
    		    width: 350,
    		    title: '添加',
    		    modal: true,
    		    showToggle: false,
    		    showMax: false,
    		    showMin: true,
    		    isResize: true,
    		    buttons: [{
    		        text: '确定',
    		        onclick: function(item, dialog) {
    		            dialog.frame.saveCosItemSubjRef();
    		        },
    		        cls: 'l-dialog-btn-highlight'
    		    },
    		    {
    		        text: '取消',
    		        onclick: function(item, dialog) {
    		            dialog.close();
    		        }
    		    }]
    		});
         }

    function remove(){
    	 var data = gridManager.getCheckedRows();
         if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.acc_year +"@"+ 
					this.subj_code +"@"+ 
					this.item_code
					)
             });
             $.ligerDialog.confirm('确定删除?', function (yes){
             	if(yes){
                 	$.post("deleteCostSubjItemMap.do",{ParamVo : ParamVo.toString()},function (responseData){
                 		if(responseData.state=="true"){
                 			query();
                 		}else{
                 			$.ligerDialog.warn(responseData.error);
                 		}
                 	},"json");
             	}
             }); 
         }
        }
    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm =  
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"acc_year="+vo[3] +"&"+ 
			"subj_code="+vo[4] +"&"+ 
			"item_code="+vo[5] 
		
		$.ligerDialog.open({
		    url: 'costSubjItemMapUpdatePage.do?isCheck=false&' + parm,
		    data: {},
		    height: 300,
		    width: 350,
		    title: '修改',
		    modal: true,
		    showToggle: false,
		    showMax: false,
		    showMin: false,
		    isResize: true,
		    buttons: [{
		        text: '确定',
		        onclick: function (item, dialog) {
		            dialog.frame.saveCosItemSubjRef();
		        }, cls: 'l-dialog-btn-highlight'
		    }, {
		        text: '取消',
		        onclick: function (item, dialog) {
		            dialog.close();
		        }
		    }]
		});

    }
    function loadDict(){
            //字典下拉框
	   	 $("#item_code").ligerTextBox({ width:160 });
	     $("#subj_code").ligerTextBox({ width:160});
	     
       }  

    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" /></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>

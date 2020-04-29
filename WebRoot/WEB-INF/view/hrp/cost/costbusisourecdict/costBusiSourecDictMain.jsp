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
		grid.options.parms.push({name:'busi_data_source_type',value:liger.get("busi_data_source_type").getValue()}); 
    	grid.options.parms.push({name:'busi_data_source_code',value:$("#busi_data_source_code").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '数据来源类型', name: 'busi_data_source_type', align: 'left',
                    	 render : function(rowdata, rowindex,value) {
                        	 if(rowdata.is_sys==0){
                    		      return "<a href='#' onclick=\"openUpdate('"+rowdata.busi_data_source_type+'|'+rowdata.busi_data_source_code+"');\" >"+(rowdata.busi_data_source_type==1?'收入数据来源':'成本数据来源')+"</a>";
                            	 }else if(rowdata.is_sys==1){
                            	   return (rowdata.busi_data_source_type==1?'收入数据来源':'成本数据来源')
                            	 }
   						   }
                         },
                     { display: '数据来源编码', name: 'busi_data_source_code', align: 'left'},
                     { display: '数据来源名称', name: 'busi_data_source_name', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostBusiSourecDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     checkBoxDisplay : f_checkBoxDisplay,
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
        				if(rowdata.is_sys==0){
						openUpdate(
								
								rowdata.busi_data_source_type  +"|"+ 
								rowdata.busi_data_source_code 
							);
        				}
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

     function f_checkBoxDisplay(rowdata){
    	 if (rowdata.is_sys == 1)
 		      return false;
	       }


     function add_open(){

    	 $.ligerDialog.open({
    		    url: 'costBusiSourecDictAddPage.do?isCheck=false',
    		    height: 300,
    		    width: 500,
    		    title: '添加',
    		    modal: true,
    		    showToggle: false,
    		    showMax: false,
    		    showMin: true,
    		    isResize: true,
    		    buttons: [{
    		        text: '确定',
    		        onclick: function(item, dialog) {
    		            dialog.frame.saveCostBusiSourecDict();
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
						this.busi_data_source_type   +"@"+ 
						this.busi_data_source_code  
						)
	             });
	             $.ligerDialog.confirm('确定删除?', function (yes){
	             	if(yes){
	                 	ajaxJsonObjectByUrl("deleteCostBusiSourecDict.do",{ParamVo : ParamVo.toString()},function (responseData){
	                 		if(responseData.state=="true"){
	                 			query();
	                 		}
	                 	});
	             	}
	             }); 
	         }

         }
   
    function openUpdate(obj){


		var vo = obj.split("|");
		var parm = 
			"busi_data_source_type="+vo[0]   +"&"+ 
			"busi_data_source_code="+vo[1];
    	$.ligerDialog.open({ url : 'CostBusiSourecDictUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostBusiSourecDict(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
         
	     $("#busi_data_source_code").ligerTextBox({ width:160 });
		  	$("#busi_data_source_type").ligerComboBox({  
		  		   width : 160,	
		           data: [
		               { text: '收入数据来源', id: '1' },
		               { text: '成本数据来源', id: '2' },
		           ]
		       });  
       }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据来源类型：</td>
            <td align="left" class="l-table-edit-td"><input name="busi_data_source_type" type="text" id="busi_data_source_type" /></td>
            <td align="right" class="l-table-edit-td"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数据来源：</td>
            <td align="left" class="l-table-edit-td"><input name="busi_data_source_code" type="text" id="busi_data_source_code" /></td>
            <td align="right" class="l-table-edit-td"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>

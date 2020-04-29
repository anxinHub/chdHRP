<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'cus_id',value:liger.get("cus_code").getValue().split(".")[0]});
    	grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue().split(".")[0]}); 
    	grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '客户编码', name: 'cus_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.cus_id    +"')>"+rowdata.cus_code+"</a>";
							}
					 },
                     { display: '客户名称', name: 'cus_name', align: 'left'
					 },
					 { display: '客户分类', name: 'type_name', align: 'left'
					 },
					 { display: '联系人', name: 'contact', align: 'left'
					 },
                     { display: '是否停用', name: 'is_stop', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_stop == 0){
									return "启用";
								}else{
									return "停用"
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../sys/cusdict/queryCusDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
                     	{ text: '添加', id:'add', click: add_btn,icon:'add' },
                     	{ line:true },
                     	{ text: '删除', id:'delete', click: del_btn,icon:'delete' },
                     	{ line:true }

    				]}, 
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.cus_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+ vo[1]+"&cus_id="+ vo[2];
		
		parent.$.ligerDialog.open({ url : 'accCheckCusUpdatePage.do?' + parm,data:{}, height: 450,width: 930, 
				title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
				parentframename:window.name,
				buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCusAttr();},
				cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
 	function add_btn(){
    	
 		parent.$.ligerDialog.open({url: '../../sys/cus/cusAddPage.do?isCheck=false', height: 300,width: 700, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCus(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

 	}
 	function del_btn(){
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
				this.cus_id   +"@"+ 
				this.cus_code 
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("../../sys/cus/deleteCus.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
 	}

    
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
    
    function loadDict(){
            //字典下拉框
    	
    	autocomplete("#cus_code","../../sys/queryCusDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#type_code","../../sys/queryCusTypeDict.do?isCheck=false","id","text",true,true);
    	$("#is_stop").ligerTextBox({width:160});
    }
    
    function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'客户',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.sys.service.CusDictService",
			method_name: "queryCusDictPrint",
			bean_name: "cusDictService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
		
	officeGridPrint(printPara); 
	
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">客户：</td>
            <td align="left" class="l-table-edit-td"><input name="cus_code" type="text" id="cus_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">客户分类：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
              		   <select id="is_stop" name="is_stop" style="width: 135px;">
              		   				<option value="">&nbsp;</option>
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            <td >
            	<input class="l-button "   type="button" value="查询(Q)" onclick="query();"/>
            	<input class="l-button "   type="button" value="打 印" onclick="printDate();"/>
            </td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script type="text/javascript">	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	

		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#create_date_b").val(acc_month.split(";")[0]);
		$("#create_date_e").val(acc_month.split(";")[1]);
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    		grid.options.parms.push({name:'create_date_b',value:$('#create_date_b').val()}); 
    		grid.options.parms.push({name:'create_date_e',value:$('#create_date_e').val()});
    		grid.options.parms.push({name:'summary',value:$('#summary').val()});
    		grid.options.parms.push({name:'fun_code',value:liger.get("fun_code").getValue()});
    		grid.options.parms.push({name:'eco_code',value:liger.get("eco_code").getValue().split(".")[0]});
    		grid.options.parms.push({name:'subj_type_code',value:"04"}); 
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '发生日期', name: 'occur_date', align: 'left',
					 },
					 { display: '凭证编号', name: 'vouch_no', align: 'left'
					 },
					 { display: '摘要', name: 'summary', align: 'left',
					 },
					 { display: '科目', name: 'subj_name', align: 'left'
					 },
                     /* { display: '功能分类', name: 'fun_name', align: 'left'
					 },
                     { display: '经济分类', name: 'eco_name', align: 'left'
					 }, */
                     { display: '收入金额', name: 'credit', align: 'right',formatter:'###,##0.00',
   						 render:function(rowdata){
   	   						 return formatNumber(rowdata.credit, 2, 1);
   						 }
					 },
                     { display: '制单人', name: 'create_username', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccItialIncome.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
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
    				]}
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
		          {"cell":0,"value":"发生日期："+$("#create_date_b").val()+"至"+$("#create_date_e").val(),"colSpan":"5"}
	    		  ]
	    	};
	     
	   		var printPara={
	   			title: "初始收入",//标题
	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
	   			class_name: "com.chd.hrp.acc.service.books.memorandumbook.AccItialIncomeService",
				method_name: "queryAccItialIncomePrint",
				bean_name: "accItialIncomeService",
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
              		$.ligerDialog.open({url: 'accItialIncomeAddPage.do', height: 376,width: 420, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccCashItem(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.sub_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deletBatchAccItialIncome.do",{ParamVo : ParamVo.toString()},function (responseData){
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
   
    function loadDict(){
    	var data={
    			subj_type_code:'04'
    		};
            //字典下拉框
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,data);
    
    	autocomplete("#eco_code","../queryEcoType.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#fun_code","../queryFunType.do?isCheck=false","id","text",true,true);
    	
    	$("#summary").ligerTextBox({width:360});
    	
    	$("#create_date_b").ligerTextBox({width:160});
    	
    	$("#create_date_e").ligerTextBox({width:160});
    }   
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<!-- <div id="topmenu"></div> -->
    <table cellpadding="0" cellspacing="0" class="l-table-edit">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>发生日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate"
				name="create_date_b" type="text" id="create_date_b" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				style="width: 160px;" /></td>
            <td align="center" class="l-table-edit-td" >至 :</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date_e"
				type="text" id="create_date_e" ltype="text"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				style="width: 160px;" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
        </tr>
           <tr>
            <td align="center" class="l-table-edit-td"  style="padding-left:20px;">摘 要：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">功能分类：</td>
            <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">经济分类：</td>
            <td align="left" class="l-table-edit-td"><input name="eco_code" type="text" id="eco_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>

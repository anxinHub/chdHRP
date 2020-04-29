<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
		var acc_month=getMonthDate('${acc_year}','${acc_month}');
		$("#create_date_b").val(acc_month.split(";")[0]);
		$("#create_date_e").val(acc_month.split(";")[1]);
		
		$("#create_date_b").ligerTextBox({ width:120 });
    	$("#create_date_e").ligerTextBox({ width:120 });
    });
    //查询
    function  query(){
    	
    	
    	
    	var create_date_b = $("#create_date_b").val();
    	
		var create_date_e = $("#create_date_e").val();
		
		var content_code = liger.get("content_code").getValue();
		
		if(create_date_b==""||create_date_e==""){
			
			$.ligerDialog.error('发生起止日期为必填项！');
			
			return;
		}
		
		if(content_code==""){
			
			$.ligerDialog.error('补助内容为必填项！');
			
			return;
		}
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
   		grid.options.parms.push({name:'acc_time',value:create_date_b});
   		grid.options.parms.push({name:'acc_date',value:create_date_e});
   		
   		
   		grid.options.parms.push({name:'content_code',value:content_code.split(".")[0]});
   		//加载查询条件
   		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '期间',align: 'left',columns:[
  					  { display: '年', isSort:false, name: 'acc_year', align: 'left',
  					 },
                       { display: '月', isSort:false, name: 'acc_month', align: 'left'
  					 }]
					 },
					 { display: '凭证号', isSort:false, name: 'vouch_no', align: 'left'
					 },
					 { display: '摘要', isSort:false, name: 'summary', align: 'left'
					 },
					 { display: '补助内容',name: 'content_name',align: 'left',columns:[
					  { display: '支出功能分类科目', isSort:false, name: 'fun_name', align: 'left'
					 },
                     { display: '支出经济分类科目', isSort:false, name: 'eco_name', align: 'left'
					 },
					 { display: '借方', isSort:false, name: 'debit',align: 'right',formatter:'###,##0.00',
   						 render:function(rowdata){
   	   						 return formatNumber(rowdata.debit, 2, 1);
   						 }
					 },
                     { display: '贷方', isSort:false, name: 'credit', align: 'right',formatter:'###,##0.00',
   						 render:function(rowdata){
   	   						 return formatNumber(rowdata.credit, 2, 1);
   						 }
					 },
                     { display: '方向', isSort:false, name: 'subj_dire', align: 'left'
					 },
                     { display: '余额', isSort:false, name: 'end_os', align: 'right',formatter:'###,##0.00',
   						 render:function(rowdata){
   	   						 return formatNumber(rowdata.end_os, 2, 1);
   						 }
					 }
					 ]
					 }
                     
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'collectExpendFinchdancial.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '打印', id:'print', click: printDate,icon:'print' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accItialExpendAddPage.do?isCheck=false', height: 376,width: 420, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
                            	ajaxJsonObjectByUrl("deletBatchAccItialExpend.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
              		$.ligerDialog.open({url: 'accVouchImportMainPage.do?isCheck=false', height: 464,width: 590, title:'导入凭证',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
    	
            //字典下拉框
		var count = 0;
    	$("#content_code").ligerComboBox({
          	url: '../queryAccFinaContent.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 180,
          	autocomplete: true,
          	width: 180,
          	onSuccess: function (data) {
          		
          		if (count == 0) {
          			
					this.setValue(data[0].id);
					
					//grid.changeHeaderText('content_name', data[0].text);
					
				}
          		
    			count++;

    		},
    		onSelected: function (selectValue,selectText){
				grid.changeHeaderText('c108', selectText);
				grid._columns['c108'].display = selectText;
           }
    	 });
	}   
    
    
    //打印数据
    function printDate(){
    	if(grid.getData().length==0){
    		$.ligerDialog.error("请先查询数据！");
    		return;
    	}
    	/*formatter:
			金额格式化：###,##0.00
			日期格式化：yyyy-MM-dd HH:mm:ss
		reg:
    		正则判断：0=否,1=是
    		正则判断：0.00=Q
    	*/
    	var heads={
    		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
    		  "rows": [
	          {"cell":0,"value":"发生日期："+$("#create_date_b").val()+"至"+$("#create_date_e").val(),"colSpan":"5"},
	          {"cell":3,"value":"补助内容："+liger.get("content_code").getText(),"from":"right","align":"right","colSpan":"4"}
    		  ]
    	};
    	
   		var printPara={
   			title: "财政基本补助支出备查簿",//标题
   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   			class_name: "com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService",
			method_name: "collectAccExpendFinancialPrint",
			bean_name: "accSubjLedgerService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
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
            <td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>发生日期：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date_b" type="text" id="create_date_b" ltype="text" validate="{required:true,maxlength:20}" 
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate" name="create_date_e" type="text" id="create_date_e" ltype="text" validate="{required:true,maxlength:20}" 
            onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>补助内容：</td>
            <td align="left" class="l-table-edit-td"><input name="content_code" type="text" id="content_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>

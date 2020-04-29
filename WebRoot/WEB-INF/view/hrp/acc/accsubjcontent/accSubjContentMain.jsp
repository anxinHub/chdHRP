<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
		loadButton();
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		grid.options.parms.push({name:'content_code',value:liger.get("content_code").getValue()}); 

    		grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    		//加载查询条件
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目编码', name: 'subj_code', align: 'left'
					 },
					 { display: '科目全称', name: 'subj_name', align: 'left'
					 },
					 { display: '财政补助内容', name: 'content_name', align: 'left'
					 },
					 { display: '操作', align: 'left',render:function(rowdata,index,value){
						 return "<a href='#' onClick='javascript:addAccSubjContent("+index+")'>【设置】</a>&nbsp;&nbsp;&nbsp;&nbsp;"
						 +"<a href='#' onClick='javascript:delAccSubjContent("+index+")'>【删除】</a>";
					 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccSubjContent.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	
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
							this.content_code +"@"+ 
							this.subj_code +"@"+ 
							this.acc_year +"@"+ 
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccSubjContent.do",{ParamVo : ParamVo.toString()},function (responseData){
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

    function addAccSubjContent(obj){
    	
    	var data = gridManager.getRow(obj);
    	
    	var parm="";
    	
    		parm="group_id="+data.group_id+
    		"&hos_id="+data.hos_id+
    		"&subj_code="+data.subj_code+
    		"&acc_year="+data.acc_year+
    		"&copy_code="+data.copy_code;
    	
    	$.ligerDialog.open({url: 'accSubjContentAddPage.do?'+parm, height: 200,width: 452, title:'选择财政补助内容',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
				buttons: [ 
				           { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccSubj(data,dialog); },cls:'l-dialog-btn-highlight' }, 
				           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
				         ]
		});
    }
    
    function delAccSubjContent(obj){
    	
    	var data = gridManager.getRow(obj);
    	
    	var ParamVo =[];

			ParamVo.push(
			//表的主键
			data.group_id   +"@"+ 
			data.hos_id   +"@"+ 
			data.content_code +"@"+ 
			data.subj_code +"@"+ 
			data.acc_year +"@"+ 
			data.copy_code 
			);
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	if(data.content_code == null){
                		$.ligerDialog.error('财政补助内容为空!');
                		return false;
                	}
                	ajaxJsonObjectByUrl("deleteAccSubjContent.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			gridManager.updateRow(data,{
                				content_name: null
                			})
                		}
                	});
            	}
            }); 
        }
   
	
	function loadButton(){
		$("#query").ligerButton({click: query, width:90});
		$("#printDate").ligerButton({click: printDate, width:90});
	}
    
    function loadDict(){
    	
    	autocomplete("#content_code","../queryAccFinaContent.do?isCheck=false","id","text",true,true,'',false,'',200);
         
    	autocomplete("#subj_code","../querySubj.do?isCheck=false","id","text",true,true,'',false,'',200);
    	
    	$("#content_code").ligerTextBox({ width:200});
    	$("#subj_code").ligerTextBox({ width:200});
        
    }  
    
    function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							//{"cell":0,"value":"购置日期："+$("#begin_date").val()+"至"+$("#end_date").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'对应财政补助内容',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.AccSubjContentService",
			method_name: "queryAccSubjContentPrint",
			bean_name: "accSubjContentService",
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
	<!-- <div id="topmenu"></div> -->
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财政补助内容编码：</td>
            <td align="left" class="l-table-edit-td"><input name="content_code" type="text" id="content_code" ltype="text" /></td>
            <td align="left"></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="query" accessKey="T"  value="查询"/>
				<input  type="button" id="printDate" value=" 打 印" /> 
			</td>
			<td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>

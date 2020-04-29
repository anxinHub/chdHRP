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
    	grid.options.parms.push({name:'b_year_month',value:$("#b_year_month").val()}); 
    	grid.options.parms.push({name:'e_year_month',value:$("#e_year_month").val()}); 
    	var dept_dict = liger.get("dept_id").getValue();
		if(dept_dict !=null && dept_dict !=''){
    		grid.options.parms.push({name:'dept_id',value:dept_dict.split(".")[0]}); 
        	grid.options.parms.push({name:'dept_no',value:dept_dict.split(".")[1]}); 
    	}
		grid.options.parms.push({name:'para_code',value:liger.get("para_code").getValue()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '统计年月', name: 'year_month', align: 'left',render : function(rowdata, rowindex,value) {
                 			return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code  + "|" +rowdata.acc_year +"|"+rowdata.acc_month+"|" + rowdata.dept_id  +"|" + rowdata.para_code  +"')>"+rowdata.acc_year+rowdata.acc_month+"</a>";
                     	}
                     	/*
					 	2016/11/3 lxj
					 		增加合计行
					 	*/
						,totalSummary:{
							type:'sum',
							render:function(suminf,column,cell){
								return '<div>合计</div>';
							}
						}
                     },
                     { display: '科室', name: 'dept_name', align: 'left'
					 },
                     { display: '自定义分摊参数', name: 'para_name', align: 'left'
					 },
                     { display: '统计值', name: 'para_value', align: 'left'
                    	 /*
						 	2016/11/3 lxj
						 	增加合计行
						 */
						 ,totalSummary: {
	                      type: 'sum',
	                      render: function (suminf, column, cell) {
	                         return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
	                      }
	                 	}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryCostUserDefinedPara.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
						{ line:true }, 
						{ text: '按月删除', id:'delete', click: deleteMonthly,icon:'delete'},
						{ line:true}, 
						{ text: '继承', id:'extend', click: itemclick,icon:'extend' },
						{ line:true}, 
						{ text: '同步', id:'synchro', click:synchro,icon:'config'},
						{ line:true }, 
		                { text: '打印', id:'print', click: print,icon:'print' },
		                { line:true },
		                { text: '导入', id:'import', click: itemclick,icon:'up' }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.acc_year   +"|"+ 
								rowdata.acc_month   +"|"+ 
								rowdata.dept_id   + "|" + 
								rowdata.para_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function deleteMonthly(){
    	if ($("#b_year_month").val() == '' || $("#e_year_month").val() == '') {
    		$.ligerDialog.question('统计年月不能为空')
    		return false;
    	}
    	$.ligerDialog.confirm('是否确认删除', function(yes) {
    		if (yes) {
    			var formPara = {
					b_year_month: $("#b_year_month").val(),
					e_year_month: $("#e_year_month").val(),
    			};
    			ajaxJsonObjectByUrl("deleteMonthlyCostUserDefinedPara.do", formPara, function(responseData) {
    				if (responseData.state == "true") {
    					query();
    				}
    			});
    		}
    	});
        }
    
    function synchro(){
	    	$.ligerDialog.open({
	    	    url: 'costUserDefinedParaSynchroPage.do?isCheck=false',
	    	    height: 300,
	    	    width: 400,
	    	    title: '同步',
	    	    modal: true,
	    	    showToggle: false,
	    	    showMax: false,
	    	    showMin: true,
	    	    isResize: true,
	    	    buttons: [{
	    	        text: '确定',
	    	        onclick: function(item, dialog) {
	    	            dialog.frame.costUserDefinedParaSynchro();
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

    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'costUserDefinedParaAddPage.do?isCheck=false', height: 300,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostUserDefinedPara(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.acc_year   +"@"+ 
							this.acc_month   +"@"+ 
							this.dept_id   +"@"+ 
							this.para_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteCostUserDefinedPara.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "extend" :
                	$.ligerDialog.open({
                    	url: 'costUserDefinedParaExtendPage.do?isCheck=false',
                    	 height: 270,width: 500, title:'继承数据',
                    	 modal:true,showToggle:false,showMax:false,showMin: true,
                    	 isResize:true,
                    	 buttons: [ { text: '确定', onclick: function (item, dialog)
                        	  { dialog.frame.saveCostUserDefinedParaExtend(); },cls:'l-dialog-btn-highlight' },
                        	   { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] 
             	   });
                    return;
				case "import":
					//公用import.jsp页面使用
					var para={
					    "column": [
					        {
					            "name": "acc_year",
					            "display": "统计年度",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "acc_month",
					            "display": "统计月份",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "dept_id",
					            "display": "科室编码",
					            "width": "200",
					            "require":true
					        },
					        {
					            "name": "dept_name",
					            "display": "科室名称",
					            "width": "200",
					            "require":true
					        },{
					            "name": "para_code",
					            "display": "自定义分摊参数编码",
					            "width": "200",
					            "require":true
					        },{
					            "name": "para_code_name",
					            "display": "自定义分摊参数名称",
					            "width": "200",
					            "require":true
					        },{
					            "name": "para_value",
					            "display": "统计值",
					            "width": "200",
					            "require":true
					        }
					    ]/* ,
					    isUpdate:true */
					};
					importSpreadView("hrp/cost/costuserdefinedpara/costUserDefinedParaImportPage.do?isCheck=false",para); 
                	//$.ligerDialog.open({url: 'costUserDefinedParaImportPage.do?isCheck=false', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
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
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"acc_year="+vo[3]   +"&"+ 
			"acc_month="+vo[4]   +"&"+ 
			"dept_id="+vo[5]   +"&"+ 
			"para_code="+vo[6] 
    	$.ligerDialog.open({ url : 'costUserDefinedParaUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostUserDefinedPara(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
		    	$("#b_year_month").ligerTextBox({ width:160 });
			     $("#e_year_month").ligerTextBox({ width:160});
	 		 
			 autodate("#b_year_month","yyyyMM");
			 autodate("#e_year_month","yyyyMM");

            //字典下拉框
			autocomplete("#dept_id","../queryDeptDictNo.do?isCheck=false","id","text",true,true);
	
			 var param = {
	            		is_sys:0
	            };
	    	autocomplete("#para_code","../queryDeptParaDict.do?isCheck=false","id","text",true,true,param); 
         }  
 
        function print(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var heads={
	 	    		//"isAuto":true,//系统默认，页眉显示页码
	 	    		"rows": [
	 		          {"cell":0,"value":"统计日期："+$("#b_year_month").val()+"至"+$("#e_year_month").val(),"colSpan":"5"}
	 	    	]};
	 	       var printPara={
	 	      		title: "自定义参数数据采集",//标题
	 	      		columns: JSON.stringify(grid.getPrintColumns()),//表头
	 	      		class_name: "com.chd.hrp.cost.service.CostUserDefinedParaService",
	 	   			method_name: "queryCostUserDefinedParaPrint",
	 	   			bean_name: "costUserDefinedParaService",
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
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
           <td align="left" class="l-table-edit-td"><input name="b_year_month" type="text" id="b_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td align="left" class="l-table-edit-td"><input name="e_year_month" type="text" id="e_year_month" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分摊参数：</td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" /></td>
            
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day;
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
        
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	if(!$("#start_yearmonth").val()){
    		$.ligerDialog.error("起始日期不能为空！");
    	}
    	if(!$("#end_yearmonth").val()){
    		$.ligerDialog.error("结束日期不能为空！");
    	}
    	
    	if(!dateValid("start_yearmonth","end_yearmonth")){
    		$.ligerDialog.error("起始日期不能大于结束日期！");
    	}
    	
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'start_yearmonth',value:$("#start_yearmonth").val()});
        grid.options.parms.push({name:'end_yearmonth',value:$("#end_yearmonth").val()});
    	grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]});
    	grid.options.parms.push({name:'type_level',value:liger.get("type_level").getValue()});
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '物资类别编码', name: 'mat_type_code', align: 'left',width: '110'},
                     { display: '物资类别名称', name: 'mat_type_name', align: 'left',width: '110'},
                     { display: '期初金额', name:'begin_money',align:'right',width:'120',
                    	 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${p04005}',1);
							}
                     } ,
					 { display: '增加', align: 'center',  
					 		columns:[
                                   {display:'合计',name:'add_money',align:'right',width:'120',
				                    	render : function(rowdata, rowindex,value) {
											return formatNumber(value,'${p04005}',1);
										}
                                    },
									{display:'采购入库',name:'add_in_money',align:'right',width:'100',
				                        render : function(rowdata, rowindex,value) {
												return formatNumber(value,'${p04005}',1);
										}
                                    },
                                    {display:'移入库',name:'in_store_money',align:'right',width:'100',
    			                        render : function(rowdata, rowindex,value) {
    											return formatNumber(value,'${p04005}',1);
    									}
                                    }
				                    
								]
					 		},
			 		{ display: '减少', align: 'center',  
				 		columns:[
                                {display:'合计',name:'out_money',align:'right',width:'120',
			                    	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                },
                                {display:'采购退货',name:'store_back_money',align:'right',width:'100',
		                         	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                },
			                    {display:'科室领用',name:'dept_out_money',align:'right',width:'100',
		                         	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                },
                                {display:'科室退库',name:'dept_back_money',align:'right',width:'100',
		                         	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                },
                                {display:'移出库',name:'out_store_money',align:'right',width:'100',
		                         	render : function(rowdata, rowindex,value) {
										return formatNumber(value,'${p04005}',1);
									}
                                },
							]
				 		},
			 		{ display: '期末金额', align: 'center',name:'end_money',align:'right',width:'120',
			 			render : function(rowdata, rowindex,value) {
							return formatNumber(value,'${p04005}',1);
						}
				 	} 
				 		
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatDuraQueryBalanceReport.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                                    { text: '查   询 （<u>Q</u>）', id:'search', click: query,icon:'search' },
                                    { line:true }, 
           						    { text: '打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' }
                                ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
    function loadDict(){
       	//字典下拉框
       	autocomplete("#store_id", "../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", false,'',200);
       	
       	autocomplete("#type_level", "../../queryMatTypeLevel.do?isCheck=false", "id", "text", true, true, "", false,'',80);
       	
       	$("#start_yearmonth").ligerTextBox({width:100}); 
       	$("#end_yearmonth").ligerTextBox({width:100}); 
       	$("#type_level").ligerTextBox({width:80});
        autodate("#start_yearmonth","yyyyMM"); 
        autodate("#end_yearmonth","yyyyMM"); 
		 
    } 
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', printDate);
	}

	//打印数据
	/* function printDate(){
		if(grid.getData().length==0){
  			$.ligerDialog.error("请先查询数据！");
  			return;
  		}
		
		var printPara={
				title: "",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.ass.service.",
				method_name: "",
				bean_name: ""// ,
				//heads: JSON.stringify(heads) //表头需要打印的查询条件,可以为空
				// foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	   		
	} */
	
	function printDate(){
		
		if(grid.getData().length==0){
		    		
					$.ligerDialog.error("请先查询数据！");
					
					return;
				}
		    	
		    	
		    	var heads={
		        		"isAuto":true,//系统默认，页眉显示页码
		        		"rows": [
		    	          {"cell":0,"value":"统计年月："},
		    	          {"cell":1,"value":date} , /*          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()}	, */
		    	        /*   {"cell":3,"value":"仓库："},
		    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""} */
		        	]}; 
		    	//表尾
				var foots = {
					rows: [
						{"cell":0,"value":"制单日期:"} ,
						{"cell":1,"value":date} ,
					]
				}; 
		    	var printPara={
		          		title: "耐用品收发存报表",//标题
		          		columns: JSON.stringify(grid.getPrintColumns()),//表头
		          		class_name: "com.chd.hrp.mat.serviceImpl.dura.query.MatDuraQueryServiceImpl",
		    			method_name: "queryMatDuraQueryBalanceReportPrint",
		       			bean_name: "matDuraQueryService",
		       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
		       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
		           	};
		        	$.each(grid.options.parms,function(i,obj){
		       			printPara[obj.name]=obj.value;
		        	});
		       		
		        	officeGridPrint(printPara);
    	/* if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		
    	var printPara={
       			title:'耐用品收发存报表查询',
       			columns:grid.getColumns(1),
       			headCount:2,//列头行数
       			autoFile:true,
       			type:3
       	};
   		ajaxJsonObjectByUrl("queryMatDuraQueryBalanceReport.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		}); */

   		
    }
	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr >
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
        	<td>
        		<table>
        			<tr>
		            <td align="left" class="l-table-edit-td"><input name="start_yearmonth" type="text" id="start_yearmonth" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">至：</td>
		            <td align="left" class="l-table-edit-td"><input name="end_yearmonth" type="text" id="end_yearmonth" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
		            <td align="left"></td>
        			</tr>
        		</table>
        	</td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="store_id" type="text" id="store_id"/>
            </td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">汇总层次：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="type_level" type="text" id="type_level"/>
            </td>
        </tr>
       
    </table>
	<div align="center">
    	<h2>
	    	<span id="year_month_span"></span>耐用品收发存查询
    	</h2>
    </div>
	<div id="maingrid"></div>
	
</body>
</html>

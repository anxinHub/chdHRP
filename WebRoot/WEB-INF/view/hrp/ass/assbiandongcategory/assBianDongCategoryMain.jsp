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
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    $(function () {
        loadDict();//加载下拉框
    	loadHead(null);	//加载数据
		loadHotkeys();
      
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	if(!isnull($("#acc_year_month").val())){
          grid.options.parms.push({name:'year_month',value:$("#acc_year_month").val()}); 
    	}
        grid.options.parms.push({name:'type_level',value:$("#type_level").val()});
    	//加载查询条件
    	grid.loadData(grid.where);
	}

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     
                     { display: '资产分类', name: 'ass_type_name', align: 'left',width:'20%'
					 		},
                     { display: '期初金额', name: 'begin_money', align: 'right',width:'20%'
                    	 ,
				    	 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(
											rowdata.begin_money == null ? 0
													: rowdata.begin_money,
											'${ass_05005}',
											1);
							},formatter:'###,##0.00'
			 			 
					 		},
                     { display: '本期增加金额', name: 'add_money', align: 'right',width:'20%'
                    	 ,
				    	 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(
											rowdata.add_money == null ? 0
													: rowdata.add_money,
											'${ass_05005}',
											1);
							},formatter:'###,##0.00'},
                     { display: '本期减少金额', name: 'dec_money', align: 'right',width:'20%'
                    	 ,
				    	 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(
											rowdata.dec_money == null ? 0
													: rowdata.dec_money,
											'${ass_05005}',
											1);
							},formatter:'###,##0.00'},
					 { display: '期末金额', name: 'end_money', align: 'right',width:'20%'
						 ,
				    	 render : function(rowdata, rowindex,
									value) {
								 return formatNumber(
											rowdata.end_money == null ? 0
													: rowdata.end_money,
											'${ass_05005}',
											1);
							},formatter:'###,##0.00'}
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:true,
                     url:'queryAssBianDongCategory.do',
                     width: '100%', 
                     height: '100%', 
                     checkbox: true,
                     rownumbers:true,
                     delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items : [ { text: '查   询', id:'search', click: query,icon:'search' },
              						     { line:true },
             						     { text: '打   印', id:'print', click: printDate,icon:'print' },
             						     { line:true },
				    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function loadDict(){
    	
        //字典下拉框
		$("#acc_year_month").ligerTextBox({width:100}); 
		if(${ass_year_month }){
			$("#acc_year_month").val('${ass_year_month }')
		}else{
			autodate("#acc_year_month","YYYYMM"); 
		}
		
		$("#type_level").ligerComboBox({
	    	width : 120,
	    	data: [
	           { text: '1', id: '1' },
	           { text: '2', id: '2' },
	           { text: '3', id: '3' }
	        ]
		});
		
     }  
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', printDate);
	}
  
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"报表日期："},
    	          {"cell":1,"value":$("#acc_year_month").val()},
    	          
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":3,"value":"制表人:"},
    				{"cell":4,"value":"${user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "变动表(类别)",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.biandongcategory.AssBianDongCategoryMainService",
 				method_name: "queryAssBianDongCategoryMainPrint",
 				bean_name: "assBianDongCategoryMainService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year_month" type="text" id="acc_year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
			<td align="left" ></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类汇总到级次：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="type_level" type="text" id="type_level"/>
            </td>
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>

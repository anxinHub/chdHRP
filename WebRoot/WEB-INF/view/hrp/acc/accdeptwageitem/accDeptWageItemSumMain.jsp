<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  
String path = request.getContextPath();
%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script language="javascript" src="<%=path%>/lib/Lodop/LodopFuncs.js"></script>
<script type="text/javascript">
	
    var grid;
    
    var wage_columns;
    
    var wage_columns_sum;
    
    $(function ()
    {
    	
    	$("#layout1").ligerLayout({ leftWidth: 350,centerWidth:750,onLeftToggle: function (isColl){grid._onResize();},onRightToggle: function (isColl){}});
    	
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    	$("#acc_time").ligerTextBox({cancelable: false});
    	
    	$("#acc_year").ligerTextBox({cancelable: false});
    	
    	$("#emp_code").ligerTextBox({cancelable: false});
    	
    	$("#wage_code").ligerTextBox({cancelable: false});
    	
    });

   
    //查询
    function  query(){
    	
    		grid.options.parms=[];
    		
    		grid.options.newPage=1;
    		
          	var wage_code =liger.get("wage_code").getValue();
          	
        	grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(".")[0]}); 
        	
        	grid.options.parms.push({name:'wage_code',value:wage_code}); 
        	
        	grid.options.parms.push({name:'column_item',value:liger.get("item_code").getValue().split(".")[1]}); 
        	
        	grid.options.parms.push({name:'wage_columns',value:wage_columns}); 
        	
        	grid.options.parms.push({name:'wage_columns_sum',value:wage_columns_sum}); 
        	
	    	grid.set("url","queryAccDeptWageItemSum.do");
	    	//加载查询条件
	    	grid.loadData(grid.where);
     
    }

    function loadHead(){
    	
    	var columns = [];

        columns.push({
            display: '职工编码',
            name: 'emp_code',
            width: '10%'
        });

        columns.push({
            display: '职工名称',
            name: 'emp_name',
            width: '10%'
        });

        wage_columns="";
        
        var grid_columns="";
        
        var reg = new RegExp("money","g");
        
         for (var i =1; i < 13; i++) {
            var mothColumn = {
                display: i + '月',
                name: 'month' + i,
                width: '6%',
                align: 'right',
                render: function(rowdata, index, value) {
                    return value > 0 ? formatNumber(value, 2, 1) : formatNumber('0', 2, 1)
                }
            	
            }
            
            grid_columns=grid_columns+"to_char(nvl(sum(decode(acc_month,"+i+", money)),0))+";
        	
            columns.push(mothColumn);
        }; 
        
        wage_columns=wage_columns+",("+grid_columns.substring(0,grid_columns.length-1)+")/12 avg_money";
        
        wage_columns=wage_columns+","+grid_columns.substring(0,grid_columns.length-1)+" sum_money";
       
        wage_columns_sum=wage_columns.replace(reg,"note");

        columns.push({
            display: '月均',
            name: 'avg_money',
            align: 'right',
            width: '10%',
            render: function(rowdata, index, value) {
              return formatNumber(rowdata.avg_money, 2, 1);
            }
        });
        
        columns.push({
            display: '年度合计',
            name: 'sum_money',
            align: 'right',
            width: '10%',
            render: function(rowdata, index, value) {
              return formatNumber(rowdata.sum_money, 2, 1);
            }
        }) 
        
    	grid = $("#maingrid").ligerGrid({
    		
           columns: columns,
           
           dataAction: 'server',dataType: 'server',usePager:false,url:'',
                     
           width: '100%', height: '100%',rownumbers:true,
           
           selectRowButtonOnly:true,delayLoad:true,
           
           toolbar: { items: [
						
						{ text: '查询', id:'search', click: query,icon:'search' },
						
						{ line:true },
						
						{ text: '打印', id:'print', click: printDate,icon:'print' },
                     	
						{ line:true }
						
    				]}
    	
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    function printDate(){
 	   
    	var dept_code = liger.get("dept_code").getText();
    	
    	if(grid.getData().length==0){
    		
    		$.ligerDialog.error("请先查询数据！");
    		
    		return;
    	}
    	
    	 var heads={ 
    		"rows": [
				{"cell":0,"value":"账套: ${sessionScope.copy_name}","colspan":2,"br":true} ,
	          	{"cell":0,"value":"部门编码: "+dept_code.split(" ")[0],"colspan":2,"br":true},
				{"cell":0,"value":"部门名称: "+dept_code.split(" ")[1],"colspan":2,"br":true}
    	]}; 
    	
    	var printPara={
      		title: "部门工资综合查询",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.acc.service.wagedata.AccDeptWageItemService",
   			method_name: "queryAccDeptWageItemSumPrint",
   			bean_name: "accDeptWageItemService" ,
   			heads: JSON.stringify(heads)///表头需要打印的查询条件,可以为空
       	};
    	
    	//执行方法的查询条件
   		 $.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	}); 
   		
    	officeGridPrint(printPara);

    }
    
    function loadDict(){
        //字典下拉框

        $("#item_code").ligerComboBox({width:160});

        autocomplete("#dept_code","../../sys/queryDeptDict.do?isCheck=false&user_id=${sessionScope.user_id}","id","text",true,true,'',true);
        
        selectWage();
		
     } 
    
 function selectWage(obj){
    	
    	autocompleteObj({
    		id:"#wage_code",
    		urlStr:"../queryAccWage.do?isCheck=false",
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		highLight:true,
    		parmsStr:null,
    		defaultSelect:true,
    		initvalue:null,
    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,selectEvent:function (value){
			
    			var fromData={
                		
                		wage_code:liger.get("wage_code").getValue()
                
            	}
    			
    			autocomplete("#item_code","../queryAccWageColumn.do?isCheck=false","id","text",true,true,fromData,true);
            
			}
    	});
	     
    }

    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div id="layout1">
           
            <div position="center"  title="  ">
			    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			       <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
			            <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目：</td>
			            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>  
			    </table>
			    <div id="maingrid"></div>
			</div>
	</div>
</body>
</html>

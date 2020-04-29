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

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var acc_times;
    
    var acc_years;
    
    $(function ()
    {
    	
    	$("#layout1").ligerLayout({ leftWidth: 350,centerWidth:750,onLeftToggle: function (isColl){grid._onResize();},onRightToggle: function (isColl){}});
    	
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    	$("#emp_code").ligerTextBox({cancelable: false});
    	
    	$("#wage_code").ligerTextBox({cancelable: false});
    	
    	$("#wage_code").bind("change",function(){
    		
    		var fromData={
                		
                		wage_code:liger.get("wage_code").getValue()
                
               }

            	autocomplete("#scheme_name","../queryAccWageScheme.do?isCheck=false","id","text",true,true,fromData);
        })
        
    });

    // 查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
		var acc_time= acc_times.getValue().split(".")[0];
		var acc_year= acc_years.getValue().split(".")[0];
		var acc_month= acc_times.getValue().split(".")[1];
		var year_month= acc_years.getValue().split(".")[1];
		var wage_code =liger.get("wage_code").getValue();
		if(wage_code == ""||acc_time ==""||acc_year==""){
			$.ligerDialog.error('工资套和期间为必填项！');
			return;
		}
		if(acc_time > acc_year){
			$.ligerDialog.error('开始日期不能晚于结束日期！');
			return;
		}
		if(acc_month > year_month){
			$.ligerDialog.error('开始日期不能晚于结束日期！');
			return;
		}
       	grid.options.parms.push({name:'user_id',value:liger.get("emp_code").getValue()}); 
       	grid.options.parms.push({name:'wage_code',value:wage_code}); 
       	grid.options.parms.push({name:'acc_year',value:acc_time}); 
       	
       	var columns = [
       		{ display: '项目', name: 'item_name', width: '10%', align: 'left' }
        ];
       	var acc_time = acc_times.getValue().split(".")[1];
        var acc_year = acc_years.getValue().split(".")[1];
         for (var i = 0; i < Number(acc_year) - Number(acc_time) + 1; i++) {
            var mothColumn = {
                display: (Number(acc_time) + i) + '月',
                name: 'month' + (Number(acc_time) + i),
                width: '6%',
                align: 'right',
                render: function(rowdata, index, value) {
                    return value > 0 ? formatNumber(value, 2, 1) : formatNumber('0', 2, 1)
                }
            }
            columns.push(mothColumn);
        }
		columns.push(
			{ display: '年度合计', name: 'sum_money', align: 'right',  width: '10%',
				render: function(rowdata, index, value) {
					var sumValue = 0;
					for (var j = Number(acc_time); j <= Number(acc_year); j++) {
						var sum_money= rowdata["month" + j]>0?rowdata["month" + j]:0
						sumValue += Number(sum_money);
					}
					return formatNumber(sumValue, 2, 1);
             	}
			},
			{ display: '月均工资', name: 'avg_money', align: 'right',  width: '10%',
				render: function(rowdata, index, value) {
					var sumValue = 0;
					for (var j = Number(acc_time); j <= Number(acc_year); j++) {
						var sum_money= rowdata["month" + j]>0?rowdata["month" + j]:0
						sumValue += Number(sum_money);
					}
					return formatNumber(sumValue/12, 2, 1);
             	}
			}
		);
        grid.set("columns", columns);
         
    	grid.set("url","queryAccPeoplePay.do");
    	//加载查询条件
//     	grid.loadData(grid.where);
    }

    function loadHead(){
    	
    	var columns = [];

        columns.push({
            display: '项目',
            name: 'item_name',
            width: '10%',
            align: 'left',
        });

        var acc_time = acc_times.getValue().split(".")[1];
        var acc_year = acc_years.getValue().split(".")[1];

         for (var i = 0; i < Number(acc_year) - Number(acc_time) + 1; i++) {
            var mothColumn = {
                display: (Number(acc_time) + i) + '月',
                name: 'month' + (Number(acc_time) + i),
                width: '6%',
                align: 'right',
                render: function(rowdata, index, value) {
                    return value > 0 ? formatNumber(value, 2, 1) : formatNumber('0', 2, 1)
                }
            }
            columns.push(mothColumn);
        } 
         
         columns.push({
            display: '年度合计',
            name: 'sum_money',
            align: 'left',
            render: function(rowdata, index, value) {
              var sumValue = 0;

              for (var j = Number(acc_time); j <= Number(acc_year); j++) {
            	  var sum_money= rowdata["month" + j]>0?rowdata["month" + j]:0
                  sumValue += Number(sum_money);
              }
              return formatNumber(sumValue, 2, 1);
            }
        }); 
        
    	var formData={
    			emp_code:'${sessionScope.user_id}'
    	};
    	
    	grid = $("#maingrid").ligerGrid({
    		
           columns: columns,
           
           dataAction: 'server',dataType: 'server',usePager:false,url:'',
                     
           parms:formData,width: '100%', height: '100%',rownumbers:true,
           
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
 	   
    	var emp_code = liger.get("emp_code").getText();
    	
    	if(grid.getData().length==0){
    		
    		$.ligerDialog.error("请先查询数据！");
    		
    		return;
    	}
    	
    	 var heads={ 
    		"rows": [
				{"cell":0,"value":"年度: "+acc_times.getValue().split(".")[0],"colspan":2,"br":true} ,
				{"cell":0,"value":"账套: ${sessionScope.copy_name}","colspan":2,"br":true} ,
	          	{"cell":0,"value":"职工编码: "+emp_code.split(" ")[0],"colspan":2,"br":true},
				{"cell":0,"value":"职工名称: "+emp_code.split(" ")[1],"colspan":2,"br":true}
    	]}; 
    	
    	var printPara={
      		title: "员工工资查询",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.acc.service.wagedata.AccPeopleWageItemService",
   			method_name: "queryAccPeoplePayPrint",
   			bean_name: "accPeopleWageItemService" ,
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

       var fromData={
        		
        		wage_code:'0000'
        
        }

    	autocomplete("#scheme_name","../queryAccWageScheme.do?isCheck=false","id","text",true,true,fromData);
        
   		autocomplete("#wage_code","../queryAccWage.do?isCheck=false&user_id=${sessionScope.user_id}&is_read=1","id","text",true,true,true,true);
        
        autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&user_id=${sessionScope.user_id}","id","text",true,true,'',true);
        
		//$("#acc_time").ligerComboBox({url: '../queryYearMonth.do?isCheck=false',valueField: 'id',textField: 'text', selectBoxWidth: 160,autocomplete: true,width: 160});
        
	       //期间
     	acc_times = $("#acc_time").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: true,
   		});
	       
	       //期间
     	acc_years = $("#acc_year").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: true,
   		});
     	//默认月
		autodate("#acc_years","YYYYmm");
		autodate("#acc_time","YYYYmm");
		var year_Month = '${wage_year_month}';
		
		if(year_Month.toString()=="000000"){
			
			var date=new Date;
			
			 var year=date.getFullYear();
			 
			 var month=date.getMonth()+1;
			 
			 month =(month<10 ? "0"+month:month); 
			 
			 year_Month = (year.toString()+month.toString());
			
		}
		
		acc_times.setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
		 
	    acc_years.setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
    	 
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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>期间：</td>
			            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="center">至</td>
			            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
			            <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
			            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			        </tr>  
			    </table>
			    <div id="maingrid"></div>
			</div>
	</div>
</body>
</html>

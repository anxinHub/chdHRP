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
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">

    var grid;

    var rightgrid;

    var gridManager = null;

    var rightgridManager = null;

    var userUpdateStr;

    var para = "";

    var sumPara = "";

    var wherePara = "";

    var tree_wage_code = "";

    var tree_wage_name = "";

    var scheme_code = "";

    var scheme_name = "";

    var tree;

    var is_DataCanSave = false;    // 数据是否能保存   

    var column = "";

    var acc_times;
    
    $(function() {
    	
        loadDict(null);

        loadHead(null); //加载数据

        $("#money1").ligerTextBox({
            width: 80
        });

        $("#money2").ligerTextBox({
            width: 80
        });
        
        $("#money3").ligerTextBox({
            width: 80
        });

        $("#money4").ligerTextBox({
            width: 80
        });
        
        $("#money5").ligerTextBox({
            width: 80
        });
    
        $("#emp_name").ligerTextBox({
            width: 183
        });

        $("#dept_code").ligerTextBox({
            width: 110
        });
        
        $("#emp_pay").ligerTextBox({
            width: 110
        });
        
        $("#note").ligerTextBox({
            width: 160
        });

        $("#wage_item").ligerTextBox({
            width: 70
        });
        
        $("#wage_item1").ligerTextBox({
            width: 70
        });
        
        $("#wage_item2").ligerTextBox({
            width: 70
        });
        
        $("#wage_item3").ligerTextBox({
            width: 70
        });
        
        $("#wage_item4").ligerTextBox({
            width: 70
        });
        
        $("#wage_code").bind("change", function() {

            var fromData = {

                wage_code: liger.get("wage_code").getValue()

            }

            autocomplete("#scheme_name", "../queryAccWageScheme.do?isCheck=false", "id", "text", true, true, fromData);
        })

    });

    //查询
    function query() {

        grid.options.parms = [];

        grid.options.newPage = 1;

        //根据表字段进行添加查询条件
        var wage_id;

        var acc_time = acc_times.getValue();
        
        var wage_code = liger.get("wage_code").getValue();

        if (wage_code == "") {

            $.ligerDialog.error('请选择工资套或者方案进行查询！');

            return;
        }

        if (acc_time == "") {

            $.ligerDialog.error('会计期间为必填项！');

            return;

        }

        grid.options.parms.push({
            name: 'wage_code',
            value: wage_code
        });

        grid.options.parms.push({
            name: 'acc_year',
            value: acc_time.split(".")[0]
        });

        grid.options.parms.push({
            name: 'acc_month',
            value: acc_time.split(".")[1]
        });

        grid.options.parms.push({
            name: 'user_id',
            value: liger.get("emp_code").getValue()
        });
        
        grid.options.parms.push({
            name: 'column_item',
            value: sumPara
        });

        //加载查询条件
        grid.set("url", "queryAccPeopleMeals.do");
		<%--
        grid.loadData(grid.where);
		--%>
        grid._onResize();

    }
    
	var result=0;
	
    function loadHead() {

        var columns = "";

        //if(scheme_code==""){

        columns = columns + "{ display: '职工编码', name: 'EMP_CODE', align: 'left',frozen: true,width:'10%'},"

        + "{ display: '职工名称', name: 'EMP_NAME', align: 'left',frozen: true,width:'10%'}";

        para = "";

        sumPara = "";

         $.ajax({

            type: "POST",

            url: "../accwagepay/queryAccWagePayGrid.do?isCheck=false",

            data: {
                "wage_code":liger.get("wage_code").getValue(),
                "acc_year": acc_times.getValue().split(".")[0]
            },

            dataType: "json",

            async: false,

            success: function(data) {

                if (data.Rows.length > 0) {
					
                    $.each(data.Rows, function(i, v) {

                        columns = columns + ",{ display: '" + v.ITEM_NAME + "', name: '" + v.COLUMN_ITEM + "', align: 'right',editor: { type: 'text' },formatter: '###,##0.00'," +

                            "render:function(rowdata){ return formatNumber(rowdata." + v.COLUMN_ITEM + ",2,1)}},";

                        para += ",to_char(awp." + v.COLUMN_ITEM + ") as " + v.COLUMN_ITEM;

                        if (v.IS_SUM == 1) {

                            sumPara += ",to_char(sum(awp." + v.COLUMN_ITEM + ")) as " + v.COLUMN_ITEM;

                        } else {

                            sumPara += ",'0' as " + v.COLUMN_ITEM
                        }
						
                        if(result==0){
                        	
                        	column += "," + v.COLUMN_ITEM;
                        	
							if(data.Rows.length==i+1){
                        		
                        		result=1;
                        		
                        	}
                        	
                        }
						
                    });
                    
                    columns = columns.substr(0, columns.length - 1);

                }

            }
        }); 

        grid = $("#maingrid").ligerGrid({

            columns: eval("[" + columns + "]"),

            dataAction: 'server',
            dataType: 'server',
            usePager: true,
            url: '',
            width: '100%',
            height: '100%',
            checkbox: true,
            rownumbers: true,
            minColumnWidth: 100,
            clickToEdit: true,
            selectRowButtonOnly: true,
            delayLoad: true,
            columnWidth: '15%',
            enabledEdit: true,
            toolbar: {
                items: [

                    {
                        text: '查询',
                        id: 'search',
                        click: query,
                        icon: 'search'
                    },

                    {
                        line: true
                    },
                    { text: '打印', id:'print', click: printDate,icon:'print' },

                    { line:true } 

                ]
            }

        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict() {
		var fromData={
			wage_code: '0000'
        };
        autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&user_id=${sessionScope.user_id}", "id", "text", true, true, '', true);
        
		//$("#acc_time").ligerComboBox({url: '../queryYearMonth.do?isCheck=false',valueField: 'id',textField: 'text', selectBoxWidth: 160,autocomplete: true,width: 160});
        
		var year_Month = '${wage_year_month}';
		
		if(year_Month.toString()=="000000"){
			
			var date=new Date;
			
			 var year=date.getFullYear();
			 
			 var month=date.getMonth()+1;
			 
			 month =(month<10 ? "0"+month:month); 
			 
			 year_Month = (year.toString()+month.toString());
			
		}
		
	       //期间
     	acc_times = $("#acc_time").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: true,
   		});
		
		autocompleteObj({
    		id: "#wage_code",
    		urlStr: "../queryAccWage.do?isCheck=false&is_read=1&wage_type=meals",
    		valueField: "id",
    		textField: "text",
    		autocomplete: true,
    		highLight: true,
    		parmsStr: null,
    		defaultSelect: true,
    		initvalue: null,
    		initWidth: "160", initHeight: null, boxwidth: null, alwayShowInDown: null, selectEvent: function (value){
    			loadHead();
			}
    	});
		
		acc_times.setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
		 
    }

    function printDate(){
    	   
    	if(grid.getData().length==0){
    		
    		$.ligerDialog.error("请先查询数据！");
    		
    		return;
    	}
    	//console.log(grid.getColumns(1))
    	var heads={
    		"isAuto":true,//系统默认，页眉显示页码
    		"rows": [
	          {"cell":0,"value":"会计期间："},
	          {"cell":1,"value":""+acc_times.getText()+""}
    	]};
    	
    	/*var dates = getCurrentDate();
    	var cur_date = dates.split(";")[2];
    	var foots={
       		//"isAuto":true/false 默认true，页脚默认左下角显示打印人、右下角显示打印时间 
       		"rows":[
   			{"cell":0,"value":"制表人： ${sessionScope.user_name}","colSpan":3},//"cell":0,"value" 单元格的下标
   			{"cell":2,"value":"打印日期: " + cur_date,"colSpan":3,"from":"right","align":"right"}//"from":"right"：cell下标从右边开始数
           	]
        };*/
    	
    	var printPara={
      		title: "职工工资明细",//标题
      		columns: JSON.stringify(grid.getPrintColumns()),//表头
      		class_name: "com.chd.hrp.acc.service.wagedata.AccWagePayService",
   			method_name: "queryAccWagePayPrint",
   			bean_name: "accWagePayService",
   			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
   			
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
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" ><b><font color="red">*</font></b>期间：</td>
			            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
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

</body>
</html>

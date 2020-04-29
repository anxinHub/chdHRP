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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day+"日";
    var grid;
    var gridManager = null;
 
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 //query();
		$("#store_type_td").find(":radio").click(function(){
			var store_type = $("#store_type_td").find(":radio:checked").val();
			if(store_type == 1){
				$("#store_label").text("虚仓");
				autocompleteAsync("#store_code", "../../queryMatVirStoreWithEntireStoreWriteOrRead.do?isCheck=false", "id", "text", true, true, "", true);
			}else{
				$("#store_label").text("仓库");
				autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1}, true);
			}
		});
    });
    
    
  jQuery.fn.rowspan = function (colname, tableObj) {
        var colIdx;
        for (var i = 0, n = tableObj.columns.length; i < n; i++) {
        	if (tableObj.columns[i]["columnname"] == colname) {
                //colIdx = i - 1 < 1 ? 0 : i - 1;
                colIdx = i;
                console.log(i);
                break;
            }
        }
        return this.each(function () {
            var that;
            $('tr', this).each(function (row) {
                $('td:eq(' + colIdx + ')', this).filter(':visible').each(function (col) {
                    if (that != null && $(this).html() == $(that).html()) {
                        rowspan = $(that).attr("rowSpan");
                        if (rowspan == undefined) {
                            $(that).attr("rowSpan", 1);
                            rowspan = $(that).attr("rowSpan");
                        }
                        rowspan = Number(rowspan) + 1;
                        $(that).attr("rowSpan", rowspan);
                        $(this).hide();
                    } else {
                        that = this;
                    }
                });
            });
        });
    }    
    //查询
    function  query(){

		
		grid.options.parms=[];
		grid.options.newPage=1;
		
        //根据表字段进行添加查询条件
        if(!liger.get("begin_year").getValue()){
        	$.ligerDialog.warn("请选择开始年份！");
        	return false;
        }
        if(!liger.get("begin_month").getValue()){
        	$.ligerDialog.warn("请选择开始月份！");
        	return false;
        }
        if(!liger.get("end_year").getValue()){
        	$.ligerDialog.warn("请选择结束年份！");
        	return false;
        }
        if(!liger.get("end_month").getValue()){
        	$.ligerDialog.warn("请选择结束月份！");
        	return false;
        }
        if(!liger.get("store_code").getValue()){
        	$.ligerDialog.warn("请选择仓库！");
        	return false;
        }
        
		grid.options.parms.push({
			name : 'begin_year',
			value : liger.get("begin_year").getValue() == null ? "" : liger.get("begin_year").getValue()
		});
		grid.options.parms.push({
			name : 'begin_month',
			value : liger.get("begin_month").getValue() == null ? "" : liger.get("begin_month").getValue()
		}); 
		grid.options.parms.push({
			name : 'end_year',
			value : liger.get("end_year").getValue() == null ? "" : liger.get("end_year").getValue()
		});
		grid.options.parms.push({
			name : 'end_month',
			value : liger.get("end_month").getValue() == null ? "" : liger.get("end_month").getValue()
		}); 
		grid.options.parms.push({
			name : 'store_type',
			value : $("#store_type_td").find(":radio:checked").val()
		}); 
		grid.options.parms.push({
			name : 'set_store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		var store_name='set_id';
		var store_type=$("#store_type_td").find(":radio:checked").val()
		store_name=store_type=='1'?'set_id':'store_id';
		grid.options.parms.push({
			name : store_name,
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		}); 
		
		var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
		ajaxJsonObjectByUrl("../../../hip/queryALLMatFimTypeDict.do?isCheck=false", selPara, function (responseData) {
	  		 columns=[ 
	  	    			{display: '本期业务', name: 'DIRECTION_NAME', align: 'center', minWidth: '30', }, 
	  	    			{display: '业务类型', name: 'BUS_TYPE_NAME', align: 'left', minWidth: '140', }, 
	  	    				
	  	    		];
		    		var name='';
	  			for(var i=0;i<responseData.length;i++){
	  				columns.push({
	  					display:responseData[i].text,
	  					id:'',
	  					formatter:'###,##0.00',
	  					name:responseData[i].id,
	  					align:'right',
	  					minWidth:'140', render:function(rowdata,rowindex,value){
	      					return formatNumber(value,2,1);
	      				}
	  				});
	  			}
	  				columns.push({display:'汇总金额', id:'', name:'SUM_MONEY', align:'right', minWidth:'80', type:'float',
	  					render: function (rowdata, rowindex, value) {
								return formatNumber(value == null ? 0 : value, 2, 1);
							},formatter:'###,##0.00'
	  				 
	  				}); 
	  			grid.set('columns', columns);  
	  				 
			});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	
	
		
    	grid = $("#maingrid").ligerGrid({
    		columns : [], 
			dataAction: 'server', dataType: 'server', usePager: false, url: 'queryMatStoreInvStock.do?isCheck=false', 
			width: '100%', height: '100%', //rownumbers: false, //checkbox: true, 
			delayLoad: true, //初始化不加载，默认false
			onAfterShowData: function (s) {
                setTimeout(function () {
             	   $('#maingrid .l-grid-body-table tbody').rowspan('DIRECTION_NAME', grid);
             	  
                }, 0)
            },
			tree: { columnId: 'mat_type_code' }, 
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印（<u>P</u>）', id:'print', click: print, icon:'print' }
			]},
		});

        gridManager = $("#maingrid").ligerGetGridManager();
		
    }
     //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
    
	//批量打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	var str=$("#store_type_td").find(":radio:checked").val()==1?'虚仓: ':'仓库'+": ";
    	str=str+(liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]);
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："+liger.get("begin_year").getText()+"年"+liger.get("begin_month").getText()+"至"+liger.get("end_year").getText()+"年"+liger.get("end_month").getText(),"colSpan":3},
    	          {"cell":3,"value":str,"colSpan":2},
    	         ]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":'分管院领导：',"br":"true","colSpan":2} ,
				{"cell":2,"value":"部门主管:","colSpan":2},
				{"cell":4,"value":"会计:","colSpan":2}
			]
		}; 
	  
    	var printPara={
          		title: "库房收支月报表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.MatAccountReportInvStockToFimService",
       			method_name: "collectMatStoreInvStockPrint",
       			bean_name: "matAccountReportInvStockToFimService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
       			mergeColumns:"DIRECTION_NAME"//需要合并显示的列名称,多个列时,列明用@符号拼接比如  columnsName1@columnsName2
       				
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
     
   		
    }
   
    function loadDict(){//字典下拉框
		//返回当前年,当前月,
		var date = getCurrentDate();
        var aa = date.split(';');
        year = aa[0];
        month = aa[1];
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		autocompleteAsync("#begin_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#begin_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		autocompleteAsync("#end_year", "../../queryMatYear.do?isCheck=false", "id", "text", true, true, "", false, year, "60");
        autocompleteAsync("#end_month", "../../queryMatMonth.do?isCheck=false", "id", "text", true, true, "", false, month, "50");
		//第一次加载显示虚仓
        autocompleteAsync("#store_code", "../../queryMatVirStoreWithEntireStoreWriteOrRead.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
	}  
	
	</script>
</head>

<body style="padding: 0px; ">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td"  width="100px">
	            统计年月：
			</td>
			<td align="left" class="l-table-edit-td"  width="200px">
				<table >
					<tr>
						<td align="left" class="l-table-edit-td">
							<input name="begin_year" id="begin_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="begin_month" id="begin_month" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="end_year" id="end_year" type="text" required="true" validate="{required:true}" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							年
						</td>
						<td align="left" class="l-table-edit-td">
							<input name="end_month" id="end_month" type="text" required="true" validate="{required:true}" />
						</td>
	            	</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td"  >
				类型：
			</td> 
			<td id="store_type_td" align="left" class="l-table-edit-td"  >
				<input name="store_type" type="radio" checked="checked" value="1" />按虚仓
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="store_type" type="radio" value="0" />按仓库
			</td>
			<td align="right" class="l-table-edit-td"  >
				<span id="store_label">虚仓</span>：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
		</tr>
	</table>
	<div style="width: 100%; height: 100%; ">
		<div id="maingrid"></div>
	</div>
</body>
</html>

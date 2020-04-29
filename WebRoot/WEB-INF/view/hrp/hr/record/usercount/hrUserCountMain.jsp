<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

 <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,datepicker,pageOffice" name="plugins" />
    </jsp:include>
    <script src="<%=path%>/lib/hrp.js"></script>
     <style type="text/css">
	    .hfont{
	    color:#F00;
	    font-weight:bold;
	    font-size:18px
	}
    </style>
    <script type="text/javascript">
    var grid ,begin_hostime;
    var stationColumn=[],degreeColumn=[] ;
    $(function () {
        loadGrid();
        loadDict();
        initColumns();
    })
    
      function loadDict() {
    	begin_hostime = $("#begin_hostime").etDatepicker({
			range: true,
			defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed']
		});
            }
    function initColumns(){
    	ajaxPostData({
            url: "queryDegreeColumns.do?isCheck=false",
            success: function (res) {
            	var coldeg = [];
            	var colsta = [];
            	$.each(res.degreeList,function(i,resou){
            		degreeColumn.push("'"+resou.FIELD_COL_CODE+"' as "+'"'+resou.FIELD_COL_CODE+'"')
            		coldeg.push( 
            				{display: resou.FIELD_COL_NAME,name: resou.FIELD_COL_CODE,align:'right',width: 100 }
		            		);
						});
            	$.each(res.stationList,function(i,resou){
            		stationColumn.push("'"+resou.STATION_CODE+"' as "+'"'+resou.STATION_CODE+'"')
            		colsta.push( 
            				{display: resou.STATION_NAME,name: resou.STATION_CODE,align:'right',width: 100 }
		           			 );
						});
                	var gridColumns =[{ display: '人员类别', name: 'kind_name', width: 100},
			            { display: '人数', name: 'numb', align:'right',width: 100  },    
			            { display: '其中女性', name: 'ingirl', align:'right',width: 100 },
			            {	display: '学历',  columns:  coldeg },
						{display: '院内岗位', columns: colsta }, 
			                {display: '年龄', columns: [
			                    { display: '25岁以下', name: '25岁以下', align:'right',width: 100 },
			                    { display: '26-30岁', name: '26-30岁', align:'right',width: 100 },
			                    { display: '31-40岁', name: '31-40岁', align:'right',width: 100 },
			                    { display: '41-50岁', name: '41-50岁', align:'right',width: 100 },
			                    { display: '50以上', name: '50以上', align:'right',width: 100 },
			                ]
			                }
                	]
                	 grid.option('columns', gridColumns);
                    search();
                   
            }
        })
    }
    function loadGrid() {
        var gridObj = {
            editable: false,
            checkbox: false,
            height: '97%',
            width: '100%',
            inWindowHeight: true,
            addRowByKey: true //  快捷键控制添加行
        };
        gridObj.numberCell = {
            title: '#'
        };
        gridObj.columns = [
            { display: '人员类别', name: 'kind_name', width: 100},
            { display: '人数', name: 'numb', align:'right',width: 100  },    
            { display: '其中女性', name: 'ingirl', align:'right',width: 100 ,},
            {
                display: '年龄', columns: [
                    { display: '25岁以下', name: '25岁以下', align:'right',width: 100 },
                    { display: '26-30岁', name: '26-30岁', align:'right',width: 100 },
                    { display: '31-40岁', name: '31-40岁', align:'right',width: 100 },
                    { display: '41-50岁', name: '41-50岁', align:'right',width: 100 },
                    { display: '50以上', name: '50以上', align:'right',width: 100 },
                ]
            }
        ];
        grid = $("#maingrid").etGrid(gridObj);
    }
	 
	function print(){
		
    	if(grid.getAllData()==null){
    		
    		$.etDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："+$("#begin_hostime").val()},
    	          {"cell":1,"value":"单位："+'${group_name}'},
        	]}; 
    	var printPara={
          		title: "服务年限分布表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.hr.service.report.HrEducationCountService",
       			method_name: "queryHrUserCountMainPrint",
       			bean_name: "hrEducationCountService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: '',//表尾需要打印的查询条件,可以为空 
           	};
         	$.each(grid.getUrlParms(),function(i,obj){
       			printPara[obj.name]=obj.value;
        	}); 
         	console.log(printPara);
        	officeGridPrint(printPara);
   		
	}
    function search() {
        var parms = [];
         parms.push({
            name: 'degreeColumn',
            value: degreeColumn.toString()
        });
         parms.push({
            name: 'stationColumn',
            value: stationColumn.toString()
        });
         parms.push({
            name: 'begin_hostime',
            value: $('#begin_hostime').val().split("至")[0]
        });
        parms.push({
            name: 'end_hostime',
            value: $('#begin_hostime').val().split("至")[1]
        });  
        //加载查询条件
        console.log(parms)
        grid.loadData(parms, 'queryHrUserCount.do');
        $('#date').html($('#begin_hostime').val())
    }
    </script>
</title>
</head>
<body>
	<div class="container">
        <div class="center">
            <table class="table-layout">
                <tr>
                    <td class="label">入院时间：</td>
                    <td class="ipt">
                        <input id="begin_hostime" type="text" style="width:180px;"/>
                    </td>
                    <td>
						<div style="float:left;padding-right:10px;" >
						    <button onclick="search()">查询</button>
						    <button onclick="print()">打印</button>
						</div>
                    </td>
                </tr>
            </table>
            <div class="grid_title">
            	<div style="text-align: center;height:60px;font-size:22px">
            		人员分布表
            	</div>
            	<div style="height:26px;width:650px;"> 
            		<div style="display:inline-block;margin-right:250px">
            		编制单位：
            		<span id="danwei" >${group_name}</span>
            		</div>
            		<div style="display:inline-block;">
            		日期：
            		<span id="date"></span>
            		
            		</div>
            	</div>
            </div>
            <div id="maingrid"></div>
        </div>
   </div>
</body>
</html>
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
<script src="<%=path%>/lib/hrp/mat/mat.js"  type="text/javascript"></script>
<script type="text/javascript">
    var time = new Date(); //获得当前时间
    var year = time.getFullYear();//获得年、月、日
    var month = time.getMonth()+1;
    var day = time.getDate(); 
    var date = year+"年"+month+"月"+day;
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
             
            amount_money:function(value){//汇总金额
                return formatNumber(value, 2, 1);
            },
            /* mat_type_code:function(value){//汇总金额
                return formatNumber(value, 2, 1);
            }, */
         
    };
    
    $(function ()
    {
        loadDict()//加载下拉框
        //加载数据
        loadHead(null); 
        loadHotkeys();
        
    });
    //查询
    function  query(){
    	if($("#begin_date").val() > $("#end_date").val()){
    		$.ligerDialog.warn('结束日期必须大于开始日期！');
    		return;
    	}
    		
        grid.options.parms=[];
        grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
        grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
        grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
        grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
        grid.options.parms.push({name : 'begin_year',value : $("#begin_date").val().substring(0,4)});
        grid.options.parms.push({name : 'begin_month',value : $("#begin_date").val().substring(5,7)});
        grid.options.parms.push({name : 'end_year',value : $("#end_date").val().substring(0,4)});
        grid.options.parms.push({name : 'end_month',value : $("#end_date").val().substring(5,7)});
        grid.options.parms.push({name : 'begin_year_month',value : $("#begin_date").val().substring(0,4) + $("#begin_date").val().substring(5,7)});
        grid.options.parms.push({name : 'end_year_month',value : $("#end_date").val().substring(0,4) + $("#end_date").val().substring(5,7)});
        //加载查询条件
        grid.loadData(grid.where);
         
     }

    function loadHead(){
        grid = $("#maingrid").ligerGrid({
            columns: [{
                display: '科室', name: 'dept_name', align: 'left', 
            },{
                display: '代码', name: 'inv_code', align: 'left', 
            },{
                display: '名称', name: 'inv_name', align: 'left', 
            },{
                display: '品牌', name: 'brand_name', align: 'left', 
            },{
                display: '数量', name: 'amount', align: 'left', 
            },{
                display: '单位', name: 'unit_name', align: 'left', 
            },{
                display: '货位', name: 'location_name', align: 'left', 
            },{
                display: '厂商', name: 'fac_name', align: 'left', 
            },
            ],
            dataAction: 'server',dataType: 'server',usePager:false,url:'queryOutSumSingleSum.do',
            width: '100%', height: '100%',rownumbers:true,
            delayLoad: true,//初始化不加载，默认false
            selectRowButtonOnly:true,//heightDiff: -10,
            toolbar: { items: [
                { text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
                { line:true },
                { text: '打印', id:'print', click: print, icon:'print' },
                { line:true }
            ]},
            //onAfterShowData:function(data){
             //   console.log(data);
            //}
        });
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
     
    //打印回调方法
    function lodopPrint(){
        var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
        head=head+"<tr><td>入库日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
        head=head+"</table>";
        grid.options.lodop.head=head; 
        grid.options.lodop.fn=renderFunc;
        grid.options.lodop.title="供应商入库分类汇总表";
    }
    
    
    //打印
    function print(){
        
        if(grid.getData().length==0){
            
            $.ligerDialog.error("请先查询数据！");
            
            return;
        }
        
        var heads={
                "isAuto":true,//系统默认，页眉显示页码
                "rows": [
                  {"cell":0,"value":"统计年月："},
                  {"cell":1,"value":""+$("#begin_date").val()+"至" +$("#end_date").val(),"colSpan":"2" },
                  {"cell":3,"value":"仓库："},
                  {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""}

            ]}; 
        //表尾
        var foots = {
            rows: [
                {"cell":0,"value":"制表日期:"} ,
                {"cell":1,"value":date} ,
                {"cell":0,"value":'分管院领导：',"br":"true"} ,
                {"cell":3,"value":"部门主管:"},
                {"cell":11,"value":"会计:"}
            ]
        }; 
        var printPara={
                title: "供应商入库分类汇总表",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.mat.service.outsum.MatOutSumService",
                method_name: "queryOutSumSingleSumPrint",
                bean_name: "matOutSumService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 

            };
            $.each(grid.options.parms,function(i,obj){
                printPara[obj.name]=obj.value;
            });
            
            officeGridPrint(printPara);        
    }

    
    //键盘事件
    function loadHotkeys() {

        hotkeys('Q', query);
    }
   
    function loadDict(){
        autocomplete("#store_code", "../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1});
        $("#begin_date").ligerTextBox({width:100});
        $("#end_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
    }  
    </script>
</head>

<body style="padding: 0px; overflow: hidden;"  >
    <div id="pageloading" class="l-loading" style="display: none"></div>

    <div id="toptoolbar" ></div>
    <div class="search-block clearfix">
         <table>
        <tr>
            <td align="left" class="l-table-edit-td" width="3%">统计时间： </td>
            <td align="left" class="l-table-edit-td"  width="15%">
                <table>
                    <tr>
                        <td>
                            <input align="left" class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
                        </td>
                        <td align="left" class="l-table-edit-td"> 至 </td>
                        <td>
                            <input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
                        </td>
                    </tr>
                </table>
            </td>
            <td align="left" class="l-table-edit-td"  width="3%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
                <input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
        </tr>
    </table>
    </div>
    <div id="maingrid"></div>
</body>
</html>

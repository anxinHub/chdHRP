<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    $(function ()
    {
    	loadHead(null);	//加载数据
    	loadDict();
    	
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        var acc_time=$("#acc_time").val();
        
        if(acc_time ==null){
        	
        	$.ligerDialog.error('请选择会计期间！');
        	
        	
        	return false;
        }
        
    	grid.options.parms.push({name:'acc_year',value:acc_time.split(".")[0]}); 
    	
    	grid.options.parms.push({name:'acc_month',value:acc_time.split(".")[1]}); 
    	grid.options.parms.push({name:'ass_naturs',value:liger.get("ass_naturs").getValue()})

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目名称', name: 'SUBJ_NAME_ALL', align: 'left'
					 },
                     { display: '资产类别', name: 'ASS_TYPE_NAME', align: 'left'
					 },
					 { display: '资产性质', name: 'NATURS_NAME', align: 'left'
					 },
					 { display: '期初额', align: 'left',columns:[
					  { display: '会计账', name: 'ACC_BAL', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.ACC_BAL,2,1)
					  }
					 },
					 { display: '实物账', name: 'ASS_BAL', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.ASS_BAL,2,1)
					  }
					 }]
					 },
					 { display: '借方(本期增加)', align: 'left',columns:[
   					  { display: '会计账', name: 'DEBIT', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.DEBIT,2,1)
					  }
   					 },
   					 { display: '实物账', name: 'IN_MONEY', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.IN_MONEY,2,1)
					  }
   					 }]
   					 }, 
   					{ display: '贷方(本期减少)', align: 'left',columns:[
    				{ display: '会计账', name: 'CREDIT', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.CREDIT,2,1)
					  }
    				},
    				{ display: '实物账', name: 'OUT_MONEY', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.OUT_MONEY,2,1)
					  }
    				}]
    				},
    				{ display: '期末余额', align: 'left',columns:[
      				{ display: '会计账', name: 'ACC_END', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.ACC_END,2,1)
					  }
      				},
      				{ display: '实物账', name: 'ASS_END', align: 'left',formatter: '###,##0.00',render:function(rowdata){
						  return formatNumber(rowdata.ASS_END,2,1)
					  }
      				}]
      				}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccReconciliationByAss.do',
                     width: '100%', height: '97%',rownumbers:true,delayLoad: true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '查询', id:'search', click: query,icon:'search' },
						{ line:true },
                     	{ text: '打印', id:'print', click: print,icon:'print' },
                     	{ line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
       
    }
   
    function print(){
    	
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
         
		var printPara={
				title: "资产对账",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.verification.AccNostroService",
				method_name: "queryCurrentAccountCheckPrint",
				bean_name: "accNostroService"
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
        
    }
    
    function loadDict(){
    	
    	autocomplete("#acc_time", "../queryYearMonth.do?isCheck=false", "id", "text", true, true, '', true);
    	
    	autocomplete("#ass_naturs", "../../ass/queryAssNaturs.do?isCheck=false", "id", "text", true, true, '', false);

    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_naturs" type="text" id="ass_naturs" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
	
    </table>

	<div id="maingrid"></div>
</body>
</html>

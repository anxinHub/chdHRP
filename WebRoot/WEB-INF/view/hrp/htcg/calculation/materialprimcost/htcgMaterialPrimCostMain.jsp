<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<style type="text/css"> 
       h1 {font-size: 12px;color: #000000; display : inline}

  		.input-right-warp {
		    text-align: right;
		    line-height: 28px;
		    padding-bottom: 6px;
		}
		.input-right-warp>div {
		    display: inline-block;
		    margin-right: 10px;
		    margin-bottom: -6px;
		}
		
</style> 
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	grid.options.parms.push({name:'period_type_code',value:liger.get("period_type_code").getValue()});
    	grid.options.parms.push({name:'period_code',value:liger.get("period_code").getValue()}); 
    	grid.options.parms.push({name:'mate_type_code',value:$("#mate_type_code").val()}); 
    	grid.options.parms.push({name:'mate_code',value:$("#mate_code").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
				           columns: [{
				        	    display: '期间类型',
				        	    name: 'period_type_name',
				        	    align: 'left',
				        	    frozen: true,
				        	    minWidth: 80
				        	},
				        	{
				        	    display: '核算年度',
				        	    name: 'acc_year',
				        	    align: 'left',
				        	    frozen: true,
				        	    minWidth: 80
				        	},
				        	{
				        	    display: '核算期间',
				        	    name: 'period_name',
				        	    align: 'left',
				        	    frozen: true,
				        	    minWidth: 80
				        	},{
				        	    display: '材料类别编码',
				        	    name: 'mate_type_code',
				        	    align: 'left',
				        	    frozen: true,
				        	    minWidth: 80
				        	},
				        	{
				        	    display: '材料类别',
				        	    name: 'mate_type_name',
				        	    align: 'left',
				        	    frozen: true,
				        	    minWidth: 80
				        	},
				        	{
				        	    display: '材料编码',
				        	    name: 'mate_code',
				        	    align: 'left',
				        	    frozen: true,
				        	    minWidth: 140
				        	},
				        	{
				        	    display: '材料名称',
				        	    name: 'mate_name',
				        	    align: 'left',
				        	    frozen: true,
				        	    minWidth: 140
				        	},
				        	{
				        	    display: '规格',
				        	    name: 'mate_mode',
				        	    align: 'left',
				        	    minWidth: 80
				        	},
				        	{
				        	    display: '计量单位',
				        	    name: 'meas_name',
				        	    align: 'left',
				        	    minWidth: 80
				        	},
				        	{
				        	    display: '生产厂家',
				        	    name: 'fac_name',
				        	    align: 'left',
				        	    minWidth: 80
				        	},
				        	{
				        	    display: '数量',
				        	    name: 'amount',
				        	    align: 'left',
				        	    minWidth: 80,
				        	    render: function(rowdata, rowindex, value) {
				        	        return formatNumber(rowdata.amount, 2, 1);
				        	    }
				        	},
				        	{
				        	    display: '销售单价',
				        	    name: 'price',
				        	    align: 'left',
				        	    minWidth: 80,
				        	    render: function(rowdata, rowindex, value) {
				        	        return formatNumber(rowdata.price, 2, 1);
				        	    }
				        	},
				        	{
				        	    display: '材料收入',
				        	    name: 'income_money',
				        	    align: 'left',
				        	    minWidth: 80,
				        	    render: function(rowdata, rowindex, value) {
				        	        return formatNumber(rowdata.income_money, 2, 1);
				        	    }
				        	},
				        	{
				        	    display: '成本单价',
				        	    name: 'cost_price',
				        	    align: 'left',
				        	    minWidth: 80,
				        	    render: function(rowdata, rowindex, value) {
				        	        return formatNumber(rowdata.cost_price, 2, 1);
				        	    }
				        	},
				        	{
				        	    display: '材料成本',
				        	    name: 'cost_money',
				        	    align: 'left',
				        	    minWidth: 80,
				        	    render: function(rowdata, rowindex, value) {
				        	        return formatNumber(rowdata.cost_money, 2, 1);
				        	    }
				        	},
				        	{
				        	    display: '加成率',
				        	    name: 'markup_percent',
				        	    align: 'left',
				        	    minWidth: 80,
				        	    render: function(rowdata, rowindex, value) {
				        	        return value + '%';
				        	    }
				        	},
				        	{
				        	    display: '直接收益',
				        	    name: 'cost_money',
				        	    align: 'left',
				        	    minWidth: 80,
				        	    render: function(rowdata, rowindex, value) {
				        	        return formatNumber(rowdata.cost_money, 2, 1);
				        	    }
				
				        	}],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHtcgMaterialPrimCost.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad:true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '生成', id:'init', click: init, icon:'add' },
    	                { line:true },
    	                { text: '计算成本', id:'add_cost', click: calculate_cost, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
    	                { line:true },
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function init(){
	    	var acc_year=$("#acc_year").val();
	    	var scheme_code=liger.get("scheme_code").getValue();

	    	if(scheme_code==''){
	    		$.ligerDialog.error('请选择方案!');
	    		return ;
	    	}
	    	
	    	if(acc_year==''){
	    		$.ligerDialog.error('请选择核算年度!');
	    		return ;
	    	}
	
	    	var formPara={
	            acc_year:acc_year,
	            scheme_code:scheme_code
	          };
	    	
			ajaxJsonObjectByUrl("initHtcgMaterialPrimCost.do?isCheck=false",formPara, function(WorkponseData) {
				if (WorkponseData.state == "true") {
					query();
				}
			});
        }
    //计算成本
    function  calculate_cost(){
    	var acc_year=$("#acc_year").val();
    	var scheme_code=liger.get("scheme_code").getValue();
    	if(scheme_code==''){
    		$.ligerDialog.error('请选择方案!');
    		return ;
    	}
    	if(acc_year==''){
    		$.ligerDialog.error('请选择核算年度!');
    		return ;
    	}
    	var formPara={
            acc_year:acc_year,
            scheme_code:scheme_code
          };
    	
		ajaxJsonObjectByUrl("calHtcgMaterialPrimCost.do?isCheck=false",formPara, function(WorkponseData) {
			if (WorkponseData.state == "true") {
				query();
			}
		});
    }
    //批量填充加成率
    function  updateBatchMarkupPercent(){
    	var reg = new RegExp("^([1-9]|[1-9]\\d|100)$");
		var markup_percent  = $("#markup_percent").val();
		if(markup_percent == ""){
			$.ligerDialog.error("加成率不能为空！");
			return false
		}

		if(!reg.test(markup_percent)) {
            $.ligerDialog.error("请输入1-100的整数！");
            return false;
        }
        
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){
            	ParamVo.push(this.group_id + "@"
						  + this.hos_id + "@"
						  + this.copy_code + "@"
						  + this.period_type_code + "@"
						  + this.acc_year + "@"
						  + this.acc_month + "@"
						  + this.mate_code + "@"
						  + markup_percent);//实际代码中temp替换主键
            });
            $.ligerDialog.confirm('确定批量填充加成率?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("updatebatchMarkupPercentMaterialPrimCost.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    function remove(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){
            	ParamVo.push(this.group_id + "@"
						  + this.hos_id + "@"
						  + this.copy_code + "@"
						  + this.period_type_code + "@"
						  + this.acc_year + "@"
						  + this.acc_month + "@"
						  + this.mate_code);//实际代码中temp替换主键
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteHtcgMaterialPrimCost.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
   
    function chargePeriodType(){
    	var formPara = {
				period_type_code : liger.get("period_type_code").getValue()
			};
		autocomplete("#period_code","../../base/queryHtcgPeriodDict.do?isCheck=false","id","text",true,true,formPara);
    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#period_type_code","../../base/queryHtcgPeriodTypeDict.do?isCheck=false","id","text",true,true);
		$(':button').ligerButton({ width: 80 });
		$("#markup_percent").ligerTextBox({
			width : 160
		});	
		$("#acc_year").ligerTextBox({
			width : 160
		});
		$("#mate_type_code").ligerTextBox({
			width : 160
		});
		$("#mate_code").ligerTextBox({
			width : 160
		});
  }   
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案名称：</td>
            <td align="left" class="l-table-edit-td"><input name="scheme_code" style="width:160px;" type="text" id="scheme_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年度：</td>
            <td align="left" class="l-table-edit-td"><input  class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间类型：</td>
            <td align="left" class="l-table-edit-td"><input name="period_type_code" type="text" id="period_type_code" ltype="text" validate="{required:true,maxlength:20}"  onchange="chargePeriodType()"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算期间：</td>
            <td align="left" class="l-table-edit-td"><input name="period_code" type="text" id="period_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料类别：</td>
            <td align="left" class="l-table-edit-td"><input name="mate_type_code" type="text" id="mate_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料编码：</td>
            <td align="left" class="l-table-edit-td"><input name="mate_code" type="text" id="mate_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    <div>
	<hr style="height:1px;border:none;border-top:1px  double #c1dbfa;" /> 
	</div>
	<div class ="input-right-warp"> 
		            <label><h1>加成率:</h1></label>
	                <input name="markup_percent" type="text" id="markup_percent" ltype="text" />
	                <input type="button" value="批量填充" onclick="updateBatchMarkupPercent();" />
	 </div>
	<div id="maingrid"></div>
</body>
</html>

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
<!-- 全院固定资产分布 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var searchType = {id:'sum'};
    
    $(function (){
        loadDict();//加载下拉框
    	loadHead(null);	//加载数据
    	
    	//在库checkbox
        $('input:checkbox').ligerCheckBox();
        
       // $("#show_store").change(function () {
        	query(searchType);
       // });
        
        $("#store_id").change(function () {
        	if(liger.get("store_id").getValue()){
        		//liger.get("show_store").setValue(true)
        	}else{
        		//liger.get("show_store").setValue(false)
        	}
        }); 
    });
    
    //查询
    function query(item){
    	searchType.id = item.id;
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    	grid.options.parms.push({name:'ass_name',value:liger.get("ass_name").getValue()});
    	grid.options.parms.push({name:'use_dept_id',value:liger.get("use_dept_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'use_dept_no',value:liger.get("use_dept_id").getValue().split("@")[1]});
    	grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]});
    	grid.options.parms.push({name:'store_no',value:liger.get("store_id").getValue().split("@")[1]});
    	grid.options.parms.push({name:'use_state',value:liger.get("use_state").getValue()});
    	grid.options.parms.push({name:'is_measure',value:liger.get("is_measure").getValue()});
    	
    	grid.options.parms.push({name:'in_date_beg',value:$('#in_date_beg').val()});
    	grid.options.parms.push({name:'in_date_end',value:$('#in_date_end').val()});
    	grid.options.parms.push({name:'run_date_beg',value:$('#run_date_beg').val()});
    	grid.options.parms.push({name:'run_date_end',value:$('#run_date_end').val()});
    	
    	grid.options.parms.push({name:'ass_card_no',value:$('#ass_card_no').val()});
    	
    	grid.options.parms.push({name:'searchType',value:item.id});
    	//grid.options.parms.push({name:'show_store',value:liger.get("show_store").getValue() == true ? 0 : 1 });
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		columns : [
    		               { display: '资产卡片号', name: 'ass_card_no', align: 'left', width: 120 },
    		               { display: '资产编码', name: 'ass_code', align: 'left', width: 120},
    		               { display: '资产名称', name: 'ass_name', align: 'left', width: 120},
    		               { display: '资产分类', name: 'ass_type_name', align: 'left', width: 120},
    		               { display: '规格', name: 'ass_spec', align: 'left', width: 120},
    		               { display: '型号', name: 'ass_mondl', align: 'left', width: 120},
    		               { display: '品牌', name: 'ass_brand', align: 'left', width: 120},
    		               { display: '生产厂商', name: 'fac_name', align: 'left', width: 120},
    		               { display: '计量单位', name: 'unit_name', align: 'left', width: 120},
    		               { display: '原值', name: 'price', align: 'right',width: '120',
    		   				 render : function(rowdata, rowindex,value) {
    		   					return formatNumber(value,'${ass_05005}',1);
    		   				 }
    		   			},
    		   			{ display: '累计折旧', name: 'depre_money', align: 'right',width: '120',
    		   				 render : function(rowdata, rowindex,value) {
    		   					return formatNumber(value,'${ass_05005}',1);
    		   				 }
    		   			},
    		   			{ display: '净值', name: 'cur_money', align: 'right',width: '120',
    		   				 render : function(rowdata, rowindex,value) {
    		   					return formatNumber(value,'${ass_05005}',1);
    		   				 }
    		   			},
    		   			{ display: '数量', name: 'ass_amount', align: 'left',width: '100'
    		   			},
    		   			{ display: '入库日期', name: 'in_date', align: 'left',width: '100'
    		   			},
    		   			{ display: '启用日期', name: 'run_date', align: 'left',width: '100'
    		   			},
    		   			{ display: '在用状态', name: 'is_dept', align: 'left',width: '100'
    		   			},
    		   			{ display: '所在部门', name: 'dept_name', align: 'left',width: '160'
    		   			},
    		   			{ display: '所在库房', name: 'store_name', align: 'left',width: '160'
    		   			},
    		   			{ display: '业务类型', name: 'bus_type_name', align: 'left',width: '160'
    		   			},
    		   			{ display: '序列号', name: 'ass_seq_no', align: 'left',width: '160'
    		   			},
    		   			{ display: '所在位置', name: 'location', align: 'left',width: '160'
    		   			},
    		   			{ display: '使用状态', name: 'state_name', align: 'left',width: '160'
    		   			},
    		               ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssCheckAllocationOther.do?isCheck=false&ass_naturs=04',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     tree:{columnId:'ass_type_code'},
                     toolbar: { 
                    	 items: [
                            { text: '查询', id:'detail', click: query,icon:'search' }
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //字典下拉框
    function loadDict(){
    	autocomplete("#ass_type_id","../../../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#use_dept_id","../../../queryDeptDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#store_id","../../../queryHosStoreDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#use_state","../../../queryAssCardUseStateDict.do?isCheck=false&state_codes=6,7","id","text",true,true,null,null,null,"180");
		
    	
    	$('#is_measure').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
            width:180,
			cancelable:true
		})
    	
    	$("#ass_name,#ass_card_no").ligerTextBox({width:180});
    	$("#in_date_beg,#in_date_end,#run_date_beg,#run_date_end").ligerTextBox({width : 80});
        
    } 
    
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
		    <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用科室：</td>
		    <td align="left" class="l-table-edit-td"><input name="use_dept_id" type="text" id="use_dept_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;房：</td>
		    <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否计量：</td>
		    <td align="left" class="l-table-edit-td"><input name="is_measure" type="text" id="is_measure"  /></td>
		    <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用状态：</td>
		    <td align="left" class="l-table-edit-td"><input name="use_state" type="text" id="use_state"  /></td>
		    <td align="left"></td>
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">入库日期：</td>
            <td align="left" class="l-table-edit-td" >
	            <div style="float:left;">
	            	<input name="in_date_beg" class="Wdate" type="text" id="in_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
	            </div>
	            <span style="float:left;margin: 0 3px;">至</span>
	            <div style="float:left;">
	            	<input name="in_date_end" class="Wdate" type="text" id="in_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
				</div>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">启用日期：</td>
            <td align="left" class="l-table-edit-td" >
	            <div style="float:left;">
	            	<input name="run_date_beg" class="Wdate" type="text" id="run_date_beg" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
	            </div>
	            <span style="float:left;margin: 0 3px;">至</span>
	            <div style="float:left;">
	            	<input name="run_date_end" class="Wdate" type="text" id="run_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMMdd'})" />
				</div>
			</td>
			<td align="left"></td>
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
				<th width="100" colspan="1" rowspan="2">类别编码</th>	
                <th width="100" colspan="1" rowspan="2">类别名称</th>	
                
                <th width="400" colspan="4" rowspan="1">原值</th> 
                <th width="400" colspan="4" rowspan="1">累计折旧</th>	 
                <th width="400" colspan="2" rowspan="1">净值</th> 
			</tr>
			<tr>
				<th width="100" >期初</th>	
                <th width="100" >借方</th>	
                <th width="100" >贷方</th>	
                <th width="100" >余额</th>
                <th width="200" >期初</th>	
                <th width="200" >借方</th>	
                <th width="200" >贷方</th>	
                <th width="200" >余额</th>
                <th width="200" >期初</th>	
                <th width="200" >期末</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>

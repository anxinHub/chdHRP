<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    $(function ()
    {
		loadDict();
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
    	grid.options.parms.push({name:'charge_kind_code',value:$("#charge_kind_code").val()}); 
    	grid.options.parms.push({name:'income_type_id',value:liger.get("income_type_id").getValue()}); 
    	grid.options.parms.push({name:'income_item_id_in',value:liger.get("income_item_id_in").getValue()}); 
    	grid.options.parms.push({name:'income_item_id_out',value:liger.get("income_item_id_out").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '收费类别编码', name: 'charge_kind_code', align: 'left',
						render : function(rowdata, rowindex,value) {
								return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code   + "|" + rowdata.charge_kind_id +"')>"+rowdata.charge_kind_code+"</a>";
						}
					 },
                     { display: '收费类别名称', name: 'charge_kind_name', align: 'left'},
					 { display: '收入类别名称', name: 'income_type_name', align: 'left'},
                     { display: '收入项目_门诊', name: 'income_item_name_in', align: 'left'},
                     { display: '收入项目_住院', name: 'income_item_name_out', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHtcChargeKindDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.charge_kind_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_open(){
	    	$.ligerDialog.open({
	    	    url: 'htcChargeKindDictAddPage.do?isCheck=false',
	    	    height: 400,
	    	    width: 500,
	    	    title: '添加',
	    	    modal: true,
	    	    showToggle: false,
	    	    showMax: false,
	    	    showMin: true,
	    	    isResize: true,
	    	    buttons: [{
	    	        text: '确定',
	    	        onclick: function(item, dialog) {
	    	            dialog.frame.saveCostChargeKindArrt();
	    	        },
	    	        cls: 'l-dialog-btn-highlight'
	    	    },
	    	    {
	    	        text: '取消',
	    	        onclick: function(item, dialog) {
	    	            dialog.close();
	    	        }
	    	    }]
	    	});
        }
    function remove(){
    	  var data = gridManager.getCheckedRows();
          if (data.length == 0){
          	$.ligerDialog.error('请选择行');
          }else{
              var ParamVo =[];
              $(data).each(function (){					
					ParamVo.push(
					//表的主键
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.charge_kind_id 
					)
              });
              $.ligerDialog.confirm('确定删除?', function (yes){
              	if(yes){
                  	$.post("deleteHtcChargeKindDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                  		if(responseData.state=="true"){
                  			query();
                  		}else{
                  			$.ligerDialog.warn(responseData.error);
                  		}
                  	},"json");
              	}
              }); 
          }
        }

    
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"charge_kind_id="+vo[3] 
		$.ligerDialog.open({
		    url: 'htcChargeKindDictUpdatePage.do?isCheck=false&' + parm,
		    data: {},
		    height: 400,
		    width: 500,
		    title: '修改',
		    modal: true,
		    showToggle: false,
		    showMax: false,
		    showMin: false,
		    isResize: true,
		    buttons: [{
		        text: '确定',
		        onclick: function(item, dialog) {
		            dialog.frame.saveCostChargeKindArrt();
		        },
		        cls: 'l-dialog-btn-highlight'
		    },
		    {
		        text: '取消',
		        onclick: function(item, dialog) {
		            dialog.close();
		        }
		    }]
		});

    }
    function loadDict(){
            //字典下拉框
             $("#charge_kind_code").ligerTextBox({ width:160 });
			 autocomplete("#income_type_id","../../base/queryHtcIncomeType.do?isCheck=false","id","text",true,true);
			 autocomplete("#income_item_id_in","../../base/queryHtcIncomeItemDict.do?isCheck=false","id","text",true,true);
			 autocomplete("#income_item_id_out","../../base/queryHtcIncomeItemDict.do?isCheck=false","id","text",true,true);
         }  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_kind_code" type="text" id="charge_kind_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入类别：</td>
            <td align="left" class="l-table-edit-td"><input name="income_type_id" type="text" id="income_type_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入项目_门诊：</td>
            <td align="left" class="l-table-edit-td"><input name="income_item_id_in" type="text" id="income_item_id_in" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收入项目_住院：</td>
            <td align="left" class="l-table-edit-td"><input name="income_item_id_out" type="text" id="income_item_id_out" /></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>

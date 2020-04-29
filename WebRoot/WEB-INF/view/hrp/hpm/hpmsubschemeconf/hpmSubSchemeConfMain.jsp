<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">


    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    
    //页面初始化
    $(function (){
    	
        loadDict();//加载字典
    	loadHead(null);	//加载数据
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acct_year_month',value:$("#acct_year_month").val()});
    	grid.options.parms.push({name:'sub_scheme_seq_no',value:liger.get("sub_scheme_seq_no").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
	}
    
	//加载grid
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '核算年度', name: 'acct_year', align: 'left',render: 
					function (rowdata, rowindex, value){
						return "<a href='#' onclick=\"openUpdate('"
								+ rowdata.acct_year+"','"
								+ rowdata.acct_month+"');\" >"
								+ rowdata.acct_year+"</a>";
					}
				},
                { display: '核算月份', name: 'acct_month', align: 'left'},
                
                { display: '方案序号', name: 'sub_scheme_seq_no', align: 'left'}
			],
            dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmSubSchemeConf.do',
            width: '100%',height: '100%',   checkbox: true,rownumbers:true,
            selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow : function (rowdata, rowindex, value)
			{
				openUpdate(rowdata.acct_year,rowdata.acct_month);//实际代码中temp替换主键
			} 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'create', click: addSubSchemeConf, icon:'add' });
       	obj.push({ line:true });
       	
       	/* obj.push({ text: '生成（<u>G</u>）', id:'add', click: create, icon:'bookpen' });
       	obj.push({ line:true }); */
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteSubSchemeConf,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addSubSchemeConf);
		
		hotkeys('D',deleteSubSchemeConf);
		
	}
	
	
	//添加
	function addSubSchemeConf(){
  		$.ligerDialog.open({
  			url: 'hpmSubSchemeConfAddPage.do?isCheck=false', 
  			height: 300,width: 500, title:'添加',modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveSubSchemeConf(); 
  					},cls:'l-dialog-btn-highlight' 
  				}, 
  				{ text: '取消', onclick: 
  					function (item, dialog) { 
  						dialog.close(); 
  					} 
  				} 
  			] 
  		});
	}
	
	
	
	//删除
	function deleteSubSchemeConf(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        	return ; 
        }
        
        var ParamVo =[];
        $(data).each(function (){
        	ParamVo.push(this.acct_year+";"+this.acct_month);//实际代码中temp替换主键
        });
        
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("deleteHpmSubSchemeConf.do",{ParamVo : ParamVo.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
	
	//生成
	function create(){
		
		var year_month = $("#acct_year_month").val();
		var sub_scheme_seq_no = liger.get("sub_scheme_seq_no").getValue();
		
		if(year_month == ""){
			$.ligerDialog.warn('请选择核算年月');
			return false;
		}
		
		if(sub_scheme_seq_no == ""){
			$.ligerDialog.warn('请选择方案序号！');
			return false;
		}
		
		
		$.ligerDialog.open({
			url: 'hpmCreateSubSchemeConfPage.do?isCheck=false&year_month='+year_month+'&sub_scheme_seq_no='+sub_scheme_seq_no, 
			title:'选择生成',height: 200,width: 450,modal:true,showToggle:false,
			showMax:false,showMin: true,isResize:true,
			buttons: [ 
				{ text: '确定', onclick: 
					function (item, dialog) { 
						dialog.frame.saveSchemeApply(); 
					},cls:'l-dialog-btn-highlight' 
				}, 
				{ text: '取消', onclick: 
					function (item, dialog) { 
						dialog.close(); 
					} 
				} 
			] 
		});
	}
	
	
	//修改页
    function openUpdate(acct_year,acct_month){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'hpmSubSchemeConfUpdatePage.do?isCheck=false&acct_year='
    			+acct_year+'&acct_month='+acct_month,data:{}, 
    		title:'修改',height: 300,width: 500,modal:true,showToggle:false,
    		showMax:false,showMin: false,isResize:true,
    		buttons: [ 
    			{ text: '确定', onclick: 
    				function (item, dialog) { 
    					dialog.frame.saveSubSchemeConf(); 
    				},cls:'l-dialog-btn-highlight' 
    			}, 
    			{ text: '取消', onclick: 
    				function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
	
	//加载字典
    function loadDict(){
    	$("#acct_year_month").ligerTextBox({width:160 });
    	autocomplete("#sub_scheme_seq_no","../querySubSchemeSeqDict.do?isCheck=false","id","text",true,true);
    }  
	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_year_month" class="Wdate" type="text" id="acct_year_month" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案序号：</td>
			<td align="left" class="l-table-edit-td"><input name="sub_scheme_seq_no" type="text" id="sub_scheme_seq_no" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

</body>
</html>

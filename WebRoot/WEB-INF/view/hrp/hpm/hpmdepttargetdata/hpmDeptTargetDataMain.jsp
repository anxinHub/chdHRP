<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<style type="text/css">
.ccc .l-grid-row-cell{background: #F1D3F7;}
</style>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    var is_add_show = ${is_add_show};
    var is_create_show = ${is_create_show};
    var is_del_show = ${is_del_show};
    var is_audit_show = ${is_audit_show};
    
 	//页面初始化
    $(function ()
    {
    	$("#acct_yearm").ligerTextBox({width:160 });autodate("#acct_yearm","yyyymm");
    	
        loadDict();//加载下拉框
    	loadHead(null);//加载数据
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : ''});
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : ''}); 
    	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 
    	grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()});
    	
    	grid.options.parms.push({name:'is_show_zero',value:${is_show_zero} == true ? 1 : 0 }); 
    	
    	
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	var avg = 0;
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室名称', name: 'dept_name', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                    		 if(rowdata.is_audit != 1 && rowdata.dept_name != '合计'){
                    			 return "<a href='#' onclick=\"openUpdate('"+rowdata.target_code+"','"+rowdata.dept_id+"','" +rowdata.dept_no+"','" +rowdata.acct_year+"','"+rowdata.acct_month+"','"+rowindex+"');\" >"+rowdata.dept_name+"</a>";
                    		 }else{
                    			 
                    			 return rowdata.dept_name;
                    			 
                    		 }
							
			           }
					 },
                     { display: '指标编码', name: 'target_code', align: 'left'},
					 { display: '指标名称', name: 'target_name', align: 'left'},
                     { display: '指标值', name: 'target_value', align: 'right',formatter:"###,##0.0000"
						 ,editor: {
		     					type: 'number',
		    					precision: '4'
		    					}
							 	,render : function(rowdata, rowindex,value,col){
									 var col = arguments[arguments.length - 1];if (rowdata.is_audit == 1) {rowdata.notEidtColNames.push(col.columnname);}
									 return formatNumber(rowdata[col.name] ==null ? 0 : rowdata[col.name],4,1);
								} 
					 
					},
                     { display: '状态', name: 'is_audit', align: 'left',width:80,
                    	 render: function (rowdata, rowindex, value){
                    		 if(rowdata.dept_name == "合计"){
                    			 return "" ; 
                    		 }
                    		 if(rowdata.is_audit == 1){
                      			 return "审核";
                      		 }
                    		 
                    		 if(rowdata.is_audit == 2){
                      			 return "";
                      		 }else{
                      			return "未审核";
                      		 } 
  			           }
					 },
                     { display: '审核人', name: 'check_name', align: 'left'
					 },
                     { display: '审核时间', name: 'audit_time', align: 'left',
						 render: function (rowdata, rowindex, value){
                    		 if(rowdata.is_audit == 2){
                      			 
                      			 return "";
                      			 
                      		 }else{
                      			 
                      			return rowdata.audit_time;
                      		 } 
  			           }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptTargetData.do',
                     width: '100%',height: '100%',   checkbox: true,rownumbers:true,delayLoad:true,checkBoxDisplay: isCheckDisplay,
                     //selectRowButtonOnly:true,//heightDiff: -10,
                     /* 编辑前事件 */onBeforeEdit: f_onBeforeEdit,enabledEdit:true,
                     /* 编辑后事件 */onAfterEdit : f_onAfterEdit,isAddRow:false,
                     rowClsRender:  function (rowdata,rowid)
		                {
		                    if (rowdata.is_audit == 1){
		                    	
		                        return "";
		                        
		                    }else if(rowdata.is_audit == 2){
		                    	
		                    	return "";
		                    }else{
		                    	
		                    	return "ccc";
		                    }
		                }, 
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					if(rowdata.dept_name == '合计'){
    						$.ligerDialog.warn('请选择数据行');
    						return ; 
    					}
    					
    					if(rowdata.is_audit == '1'){
    						$.ligerDialog.warn('已审核的数据不允许修改');
    						return ; 
    					}
    					openUpdate(rowdata.target_code,rowdata.dept_id,rowdata.dept_no,rowdata.acct_year,rowdata.acct_month,rowindex);//实际代码中temp替换主键
    					
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
    
    
  	//工具栏
	function toolbar(){
       	var obj = [];
       	
       	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加（<u>A</u>）', id:'add', click: addDeptTargetData, icon:'add',hide:is_add_show});
       	obj.push({ line:true,hide:is_add_show});
       	
       	obj.push({ text: '删除（<u>D</u>）', id:'delete', click: deleteDeptTargetData,icon:'delete',hide:is_del_show});
       	obj.push({ line:true,hide:is_del_show});
       	
       	obj.push({ text: '生成（<u>G</u>）', id:'init', click: createDeptTargetData,icon:'bookpen',hide:is_create_show});
       	obj.push({ line:true,hide:is_create_show});
       	
       	obj.push({ text: '指标值（<u>V</u>）', id:'targetView', click: targetView,icon:'bookpen' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '审核（<u>C</u>）', id:'shenhe', click: check,icon:'right',hide:is_audit_show});
       	obj.push({ line:true,hide:is_audit_show});
       	
       	obj.push({ text: '反审（<u>U</u>）', id:'noshenhe', click: unCheck,icon:'back',hide:is_audit_show});
       	obj.push({ line:true,hide:is_audit_show});
       	
       	obj.push({ text: '模板（<u>T</u>）', id:'downTemplate', click: downTemplate,icon:'down',hide:is_add_show});
       	obj.push({ line:true,hide:is_add_show});
       	
       	obj.push({ text: '导入（<u>I</u>）', id:'import', click: importData,icon:'up',hide:is_add_show});
       	obj.push({ line:true,hide:is_add_show});
       	
       	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print'});
       	obj.push({ line:true });
       	
       	$("#toptoolbar").ligerToolBar({ items: obj});
	}
    
  	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		hotkeys('G',createDeptTargetData);
		hotkeys('A',addDeptTargetData);
		hotkeys('D',deleteDeptTargetData);
		hotkeys('V',targetView);
		hotkeys('C',check);
		hotkeys('U',unCheck);
		hotkeys('P',print);
		hotkeys('T',downTemplate);
		hotkeys('I',importData);
	}
    
  	//打印
    function print() {

		if (grid.getData().length <= 1) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "科室指标数据采集",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.AphiDeptTargetDataService",
				method_name: "queryDeptTargetDataPrint",
				bean_name: "aphiDeptTargetDataService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
    function isCheckDisplay(rowdata) {
    	
    	if(rowdata.dept_name == '合计' && rowdata.target_code == null){
    		return false;
    	} 
    	
      /*   if (rowdata.is_audit == 1){
        	return false;
        } */
        return true;
    }
    function updateRow(obj){
    	var selectArr = grid.selected;
        var selected = selectArr[selectArr.length - 1];

        grid.updateRow(selected,{target_value:obj});
    }
    
    function targetView(obj) {
		//实际代码中&temp替换主键
			parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataViewPage.do?isCheck=false&year_month=' + $("#acct_yearm").val(),
			data : {},
			height : $(window).height(),
			width : $(window).width(),
			title : '指标值查看',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}
    
    
    //添加
    function addDeptTargetData(){
  		$.ligerDialog.open({
  			url: 'hpmDeptTargetDataAddPage.do?isCheck=false', 
  			title:'添加',height: 450,width: 500,modal:true,showToggle:false,
  			showMax:false,showMin: true,isResize:true,
  			buttons: [ 
  				{ text: '确定', onclick: 
  					function (item, dialog) { 
  						dialog.frame.saveDeptTargetData(); 
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
    function deleteDeptTargetData(){
    	var acct_year = $("#acct_yearm").val();
    	if(acct_year == ""){
    		$.ligerDialog.warn('请选择年月');
    		return false;
    	}
        var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.warn('请选择行');
        }
        
        var checkIds =[];
        $(data).each(function (){
        	checkIds.push(this.target_code+";"+this.dept_id + ";"+this.dept_no +";"+acct_year);//实际代码中temp替换主键
        });
        $.ligerDialog.confirm('确定删除?', function (yes){
        	if(yes){
        		ajaxJsonObjectByUrl("deleteHpmDeptTargetData.do",{checkIds:checkIds.toString()},function (responseData){
            		if(responseData.state=="true"){
            			query();
            		}
            	});
        	}
        }); 
    }
    
    
    //审核
    function check(){
    	
		var data = gridManager.getCheckedRows();
      	 
		var acct_yearm = $("#acct_yearm").val();
      	 
   	 	if (acct_yearm == ""){
   		 
        	$.ligerDialog.warn('请选择核算年月');
        	
        	return false;
        }
      	 
      	var checkIds =[];
      	 
      	if(data.length == 0){
      		 
			checkIds.push(";"+";"+ ";" +acct_yearm+";1");
      		 
		}else{
   		 	$(data).each(function (){
				checkIds.push(this.target_code+";"+this.dept_id+";" +this.dept_no+";" +acct_yearm+";1");
			});
      	 }

         ajaxJsonObjectByUrl("auditHpmDeptTargetData.do",{checkIds:checkIds.toString()},function (responseData){
            if(responseData.state=="true"){
            	 query();
            }
         });
    }
    
    
    //反审
    function unCheck(){
    	var data = gridManager.getCheckedRows();
     	 
     	 var acct_yearm = $("#acct_yearm").val();
     	 
     	 if (acct_yearm == ""){
     		 
          	$.ligerDialog.warn('请选择核算年月');
          	
          	return false;
          }
     	 
     	 var checkIds =[];
     	 
     	 if(data.length == 0){
     		 
     		 checkIds.push(";"+";"+";"+acct_yearm+";0");
     		 
     	 }else{
     		 
     		 $(data).each(function (){

               	checkIds.push(this.target_code+";"+this.dept_id+";" +this.dept_no+";" +acct_yearm+";0");
               	
               });
     	 }
     	 
     	 var params = {
     		acct_year : acct_yearm.substring(0,4),
     		acct_month : acct_yearm.substring(4,6),
     		audit_state : '0' ,
     		checkIds : checkIds.toString()
     	 };

          ajaxJsonObjectByUrl("auditHpmDeptTargetData.do",params,function (responseData){
             if(responseData.state=="true"){
             	 query();
             }
          });
    }
    
    
    //下载导入模板
    function downTemplate(){
    	location.href = "downTemplateDeptTargetData.do?isCheck=false";
    }
    
    
    //导入
    function importData(){
    	parent.$.ligerDialog.open({ 
       		url : 'hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataImportPage.do?isCheck=false'
       				+'&dept_kind_code=' + liger.get("dept_kind_code").getValue()
       				+'&acct_yearm=' + $("#acct_yearm").val()
       				,
			data:{
				columns : grid.columns, 
				grid : grid
			}, height: 300,width: 450,title:'科室指标数据采集导入',modal:true,showToggle:false,showMax:true,
			showMin: false,isResize:true,parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    }
    
    //生成
    function createDeptTargetData(){
    	var acct_yearm=$("#acct_yearm").val();
		
    	var target_code = liger.get("target_code").getValue()? liger.get("target_code").getValue() :'null';
    	
    	var dept_id = liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : 'null';
    	
    	var dept_no = liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : 'null';
    	
    	if(acct_yearm==''){
    		
    		$.ligerDialog.warn('请选择核算年月');
    		
    		return false;
    	}
    	
    	var paras = acct_yearm+"@"+target_code+"@"+dept_id +"@"+dept_no;
    	
    	$.ligerDialog.open({url: 'hpmDeptTargetDataChoosePage.do?isCheck=false&paras='+paras, height: 200,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveHospTargetConf(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
        
    }

    function openUpdate(target_code,dept_id,dept_no,acct_year,acct_month,rowindex){
    	grid.select(rowindex);
    	$.ligerDialog.open({ url: 'hpmDeptTargetDataUpdatePage.do?isCheck=false&target_code='+target_code+'&dept_id='+dept_id +'&dept_no='+dept_no +'&acct_year='+acct_year+'&acct_month='+acct_month,
			data:{}, height: 350,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveDeptTargetData(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });


    }
    function loadDict(){
            //字典下拉框
    	var param={
    			target_nature:"03",
    			method_code:"01"
    	}
           
    	autocomplete("#target_code","../queryTargetMethod.do?isCheck=false","id","text",true,true,param);
            
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
            
    	autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true);   

    	$("#is_audit").ligerComboBox({width:160 });

    }
    
	 //编制之前验证
	 function f_onBeforeEdit(e){
		 
	 }
	 
	 //编辑之后事件
	 function f_onAfterEdit(e){
		var data = gridManager.getData();
		var sum = 0;
		
		if(isNaN(e.value) == true || typeof(e.value) != "number"){
			return ; 
		}
		
		if(e.column.columnname === "target_value"){
			
			if(e.value == e.oldvalue){
				return false;
			}
		}
		
		
		if(e.record.is_audit == 1){
			$.ligerDialog.warn('已审核的指标数据不能修改 ')
			return ; 
		}
		
		if(e.record.dept_name == '合计'){
			$.ligerDialog.warn('合计行不能修改 ')
			return ; 
		}
		
		if(e.value.toString() == ""){
			gridManager.updateRow(e.record,{target_value:e.oldvalue});
			return ;
		}
		gridManager.updateRow(e.record,{target_value:e.value});
			
		var para = {
			target_value : e.record.target_value,
			dept_id : e.record.dept_id,
			dept_no : e.record.dept_no,
			acct_year : e.record.acct_year,
			acct_month : e.record.acct_month,
			target_code : e.record.target_code
		};
						
		ajaxJsonObjectByUrl("updateHpmDeptTargetData.do?isCheck=false", para, function(responseData){
			if (responseData.state == "true") {
				
				if(e.value > e.oldvalue){//新值大于旧值
					//总值 = 总值 + 新值 - 旧值
					sum = data[0].target_value + e.value - e.oldvalue;
				}
				
				if(e.value < e.oldvalue){//新值小于旧值
					//总值 = 总值 - (旧值 - 新值)
					sum = data[0].target_value - (e.oldvalue - e.value);
				}
				
				gridManager.updateRow(0,{target_value:sum});
			}
		}); 
	 }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acct_yearm"  class="Wdate"  type="text" id="acct_yearm" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
        <tr>    
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_audit" id="is_audit">
						<option value="">请选择</option>
						<option value="0">否</option>
						<option value="1">是</option>
				</select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>

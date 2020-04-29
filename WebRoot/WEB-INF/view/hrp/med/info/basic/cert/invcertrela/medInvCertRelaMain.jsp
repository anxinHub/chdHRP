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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var cert_id ;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
        $("#cert_id").change(function(){
        	query();
        })
        $("#fac_id").change(function(){
        	query();
        })
        loadHotkeys();
        
    });
    //查询
    function  query(){
    	  grid.options.parms=[];
    	  grid.options.newPage=1;
    	  
    	  if($("#cert_id").val() ==  ''){
  	    		$.ligerDialog.error('证件编码不能为空！');
  	    		return;
  	      }
    	  
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'cert_id',value:liger.get("cert_id").getValue()}); 
    	  grid.options.parms.push({name:'med_type_id',value:liger.get("med_type_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 
    	  grid.options.parms.push({name:'inv_model',value:$("#inv_model").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '药品编码', name: 'inv_code', align: 'left'
					 		},
                     { display: '药品名称', name: 'inv_name', align: 'left'
					 		},
					 { display: '规格型号', name: 'inv_model', align: 'left'
					 		},
					 { display: '生产厂商', name: 'fac_name', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInvCertRela.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     delayLoad:true,
                     selectRowButtonOnly:false,isChecked:getCheck,heightDiff: -30,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true }, 
    					{ text: '删除（<u>D</u>）', id:'delete', click: remove, icon:'delete' },
    	                { line:true },
    	                { text: '选择药品（<u>N</u>）', id:'openInv', click: openInv, icon:'add' },
    	                { line:true }
    	                /*{ text: '全部保存（<u>S</u>）', id:'add', click: addAll, icon:'add' },
    	                { line:true },
    	                { text: '全部删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    	                 { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
		                { line:true },
		                { text: '打印（<u>P</u>）', id:'print', click: printDate,icon:'print' },
		                { line:true },
		                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
		                { line:true },
		                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' } */
				    ]},
    				/* onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.cert_id   + "|" + 
								rowdata.inv_id 
							);
    				}  */
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //选择材料
    function openInv(){
    	
    	if(liger.get("cert_id").getValue() == ''){
    		$.ligerDialog.warn('请选择证件编码');
    		return ; 
    	}
    	
    	var parm = "&cert_id="+liger.get("cert_id").getValue()+"&cert_code="+ liger.get("cert_id").getText().split(" ")[0] +"&cert_name="+liger.get("cert_id").getText().split(" ")[1];
    	parent.$.ligerDialog.open({
			title: '添加',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/info/basic/cert/invcertrela/medInvCertRelaAddInvPage.do?isCheck=false'+ parm.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});  
    }
    
    
    //存在关系则选中
    function getCheck(rowdata){
    	if(typeof(rowdata.cert_id) == "number"){
            return true;
    	}
        return false;
    }
    
 	/* function addRela(){
 		var data = grid.getCheckedRows();
 		cert_code = $("#cert_id").val();
 		var ParamVo =[];
 		if(data.length == 0){
 			$.ligerDialog.error('若保存对应关系数据，请选择要保存的对应关系数据。若删除全部对应关系数据，请点全部删除');
 			return;
 		}else{
 			$(data).each(function (){					
				ParamVo.push(
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code   +"@"+ 
				this.cert_id   +"@"+ 
				this.inv_id + "@"+ cert_code
				) });
	        ajaxJsonObjectByUrl("addMedInvCertRela.do", {ParamVo : ParamVo.toString()},function(responseData){
	            if(responseData.state=="true"){
	            	query();
	            }
	        });
 		}
    } 
 	
 	function addAll(){
 		var data = grid.getData();
 		var ParamVo =[];
 		cert_id = liger.get("cert_id").getValue();
 		cert_code = liger.get("cert_id").getText().split(" ")[1];
        var para = {cert_id:cert_id,cert_code:cert_code}
        ajaxJsonObjectByUrl("addMedInvCertRelaAll.do?isCheck=false",para,function(responseData){
            if(responseData.state=="true"){
            	query();
            }
        });
	} */
	
    function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择要删除的药品！');
			return;
		}else{
			var ParamVo =[];
			$(data).each(function (){		
				ParamVo.push(
					this.inv_id +"@"+liger.get("cert_id").getValue()
				) 
			});
			
			$.ligerDialog.confirm('确定删除?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("deleteMedInvCertRelaAdd.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		}
		
     }
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medInvCertRelaImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"cert_id="+vo[3]   +"&"+ 
			"inv_id="+vo[4] 
		 
    var index = layer.open({
					type : 2,
					title : '科室申请资产明细',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'medInvCertRelaImportPage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    
    }
    function loadDict(){
            //字典下拉框
            /* 证件编码下拉框 */
            autocomplete("#cert_id", "../../../../queryMedInvCert.do?isCheck=false", "id", "text", true, true,'',true,'','160');
            /*药品类别下拉框 */
            autocomplete("#med_type_id", "../../../../queryMedTypeDict.do?isCheck=false","id", "text", true, true, {is_last : 1},false,'','160');
			/* 生产厂商 下拉框 */
            autocomplete("#fac_id", "../../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true,'',false,'','160');
			
            $("#inv_code").ligerTextBox({width:160}); 
            $("#inv_model").ligerTextBox({width:160}); 
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addRela);
		hotkeys('D', remove);

		hotkeys('S', addAll);
	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           cert_id:$("#cert_id").val(),
           inv_code:$("#inv_code").val()
         };
		ajaxJsonObjectByUrl("queryMedInvCertRela.do",printPara,function (responseData){
			var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
					 trHtml+="<td>"+item.cert_id+"</td>"; 
					 trHtml+="<td>"+item.inv_id+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel",".xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           cert_id:$("#cert_id").val(),
           inv_id:$("#inv_id").val()
         };
		ajaxJsonObjectByUrl("queryMedInvCertRela.do",exportPara,function (responseData){
			var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				 	 trHtml+="<tr>";
					 trHtml+="<td>"+item.cert_id+"</td>"; 
					 trHtml+="<td>"+item.inv_id+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel",".xls",true);
	    },true,manager);
		return;
	 }		  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>证件编码<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="cert_id" type="text" id="cert_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品类别:</b></td>
            <td align="left" class="l-table-edit-td"><input name="med_type_id" type="text" id="med_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>规格型号:</b></td>
            <td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商:</b></td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品信息:</b></td>
            <td align="left" class="l-table-edit-td"><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
         
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">证件</th>	
                <th width="200">药品</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>

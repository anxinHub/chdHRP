<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,grid,dialog" name="plugins" />
</jsp:include>
 <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var is_stop ;
    $(function (){
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		
		$("#measure_code").change(function(){
			query()
		})
				
    });
    //查询
    function  query(){
    	debugger;
   	    var parms=[];
        //根据表字段进行添加查询条件
   	    parms.push({name:'measure_code',value:$("#measure_code").val()}); 
   	    parms.push({name : 'is_stop',value : is_stop.getValue() == null ? "" : is_stop.getValue()});

    	//加载查询条件
    	grid.loadData(parms,'queryBudgOperationMeasure.do?isCheck=false');
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
               { display: '运营尺度编码(E)', name: 'measure_code', align: 'left',datatype : 'text',width:'40%',editable:setEdit ,
			   },
               { display: '运营尺度名称(E)', name: 'measure_name', align: 'left',datatype : 'text',width:'40%',
			   },
               { display: '是否停用(E)', name: 'stop',align: 'left',width:"17%",
					editor: {
						keyField:'is_stop',
	                	type: 'select',  //编辑框为下拉框时
	                	source:[{id : "0",label : "否"}, {id : "1",label : "是"}],   //  静态数据接口  也可以是回调函数返回值
	                }
			   }
          ],
          dataModel:{
         	 method:'POST',
         	 location:'remote',
         	 url:'',
         	 recIndx: 'measure_code'
          },
          usePager:false,width: '100%', height: '100%',checkbox: true,editable: true,
          addRowByKey:true,
		  toolbar: {
	          items: [
		          { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
		 		  { type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
		 	      { type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
		 		  { type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
		 		  { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]},
		 		  { type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
	      	  ]
		  },
	  });
    }
    
 // 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
    function setEdit(ui){
   		 if(ui.rowData && ui.rowData.group_id){
   			 return false ;
   		 }else{
   			 return true ;
   		 }
    }
    
    function add_Row(){
    	grid.addRow() ;
    }
    
    function save(){
    	
    	var data = grid.getChanges();
    	
    	var ParamVo =[];
    	
    	if(data.addList.length > 0 || data.updateList.length > 0){
    		
        	if(data.addList.length > 0){
        		
        		var addData = data.addList ;
        		
        		if(!validateGrid(addData)){
        			return  false ;
        		}
                $(addData).each(function (){	
                	ParamVo.push(
    					this.measure_code   +"@"+ 
    					this.measure_name +"@"+ 
    					this.is_stop +"@"+ 
    					this._rowIndx +"@"+ 
    					'1' //添加数据标识
    				) 
    			});
        	}
    		if(data.updateList.length > 0){
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
               			this.measure_code   +"@"+ 
       					this.measure_name +"@"+ 
       					this.is_stop +"@"+ 
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
    		ajaxPostData({
    			url: 'saveBudgOperationMeasure.do?isCheck=false',
			    data: {ParamVo : ParamVo.toString()},
			    success: function (responseData) {
			    	query();
			    }
			})
    	}else{
    		$.etDialog.warn('没有需要保存的数据!');
    	}
    }
    
    function remove(){
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];//后台删除数据
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
           				this.rowData.group_id   +"@"+ 
           				this.rowData.hos_id   +"@"+ 
           				this.rowData.copy_code   +"@"+ 
           				this.rowData.measure_code   
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
			});
            if(ParamVo.length > 0){
            	$.etDialog.confirm('删除操作会刷新页面,页面未保存数据可能丢失！确定删除?', function (yes){
                	if(yes){
                		ajaxPostData({
                			url: 'deleteBudgOperationMeasure.do?isCheck=false',
            			    data: {ParamVo : ParamVo.toString()},
            			    success: function (responseData) {
            			    	query();
            			    }
            			})
                	}
                }); 
            }else if(deletePageRow.length > 0 ){
            	grid.deleteRows(deletePageRow);
            	$.etDialog.success("删除成功!");
            }
        }
    }
    
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.measure_code) {
				rowm+="[运营尺度编码]、";
			}
			if (!v.measure_name) {
				rowm+="[运营尺度名称]、"; 
			}
			if (!v.is_stop) {
				rowm+="[是否停用]、";
			}
 			
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.measure_code
			var value="第"+(Number(v._rowIndx)+1)+"行";
			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
				targetMap.put(key ,value);
			}else{
				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
			}
 		});
 		if(msg != ""){
 			$.etDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
    
    function imp(){
    	var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'budgOperationMeasureImportPage.do?isCheck=false'
		});
		layer.full(index);
   	}	
    function downTemplate(){
   		location.href = "downTemplate.do?isCheck=false";
   	}	
  
    function loadDict(){
        //字典下拉框
        is_stop = $("#is_stop").etSelect({
        	options: [{id : "0",text : "否"}, {id : "1",text : "是"}],
			defaultValue: "none",
			onChange: query
		});
    }  
    //键盘事件
  	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('I', imp);
 	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">运营尺度：</td>
				<td class="ipt">
					<input type="text" id="measure_code" style="width:200px" />
				</td>
				<td class="label">是否停用：</td>
				<td class="ipt">
					<select name="" id="is_stop" style="width:180px"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>

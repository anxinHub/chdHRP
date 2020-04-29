<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/static_resource.jsp">
		<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
	</jsp:include>
<script type="text/javascript">
    var grid;
    var budg_year;
    
    $(function (){
    	loadHead(null);	//加载数据
		init();
    });
    
    var year_input,subj_name_select,type_code

	function init(){
    	year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function () {
                setTimeout(function () {
                	query();
                }, 10);
            },
            defaultDate: true
        });


		subj_name_select = $("#subj_name_select").etSelect({
			url:"../../../queryBudgSubj.do?isCheck=false&subj_type=05",
			defaultValue: "none",
			onChange: query
		});
		function reloadSubjName(value){
			subj_name_select.reload({
				url:"../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:'05',
					budg_year:value
				}
			})
		}
		
		type_code = $("#type_code").etSelect({
			url: "../../../queryBudgCostSubjType.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		});
	}

	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		})
	};
    
    //查询
    function  query(){
    	var search = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_name_select.getValue() },
			{ name: 'type_code', value: type_code.getValue() },
		]
    	//加载查询条件
    	grid.loadData(search,"queryBudgCostTypeSet.do?isCheck=false");
     }

    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
        	   { display: '科目类别编码', name: 'type_code_old', align: 'left', hidden:true},
        	   { display: '科目编码', name: 'subj_code_old', align: 'left', hidden:true},
        	   {display: '预算年度', name: 'budg_year', align: 'center',width:"10%",editable:setEdit,
					 editor: {
		 				 keyField:'budg_year',
	               	     type: 'select',  //编辑框为下拉框时
	               	     url: '../../../queryBudgYear.do?isCheck=false',      //  动态数据接口
	               	     change:function(rowdata,celldata){
	                   		 grid.updateRow(celldata.rowIndx,{subj_code:"",subj_name:""})
	                   	 }
               	 	 },
				},
				{ display: '科目类别编码', name: 'type_code', align: 'left', width: "20%",editable:false},
				{ display: '科目类别名称', name: 'type_name', align: 'left', width: "24%",
						editor:{
			 				type:'select' ,
			 				keyField:'type_code',
			 				url:'queryBudgCostSubjType.do?isCheck=false',
			 				change:reloadTypeCode,
						}
					},
				{display: '科目编码', name: 'subj_code', align: 'left',width:"20%",editable:false },
				{display: '科目名称', name: 'subj_name', align: 'left',minWidth:320,
					editor: {
		 				 keyField:'subj_code',
	               	     type: 'select',  //编辑框为下拉框时
	               	     //source:[],   //  静态数据接口  也可以是回调函数返回值
	               	     url: 'queryBudgCostSubj.do?isCheck=false&budg_year='+budg_year,
	               	     change:function(rowdata,celldata){
	               	    	 grid.updateRow(celldata.rowIndx,{subj_code:rowdata.subj_code})
	               	     },
	               	     //与年度联动查询
	               	     create:function(rowdata,celldata,setting){
	               	    	 if(rowdata.budg_year){
	               	    		 setting.url = 'queryBudgCostSubj.do?isCheck=false&budg_year='+rowdata.budg_year;
	               	    	 }else{
	               	    		 $.etDialog.error('请先填写年度');
	               	    		 return false ;
	               	    	 }
	               	     }
	               	 }
				 }
		     ],
		     dataModel:{
			    method: 'POST',
			    location:'remote',
			    url:"",
			    recIndx:"budg_year"
		     },
		     usePager:false,width:'100%', height:'100%', checkbox:true,editable: true, addRowByKey:true,
		     toolbar: {
               items: [
		           	{ type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
					{ type: "button", label: '添加行',icon:'plus',listeners: [{ click: add_Row}] },
					{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
					{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
             ]}
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
    
    //添加行
    function add_Row(){
    	grid.addRow() ;
    }
    
    //选择指标后 更新指标编码 
    function reloadTypeCode(rowdata,celldata){
    	setTimeout(function () {
    		grid.updateRow(celldata.rowIndx,{'type_code':rowdata.type_name.split(" ")[0]})
    	}, 10);
    }	
    
    function save() {
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
               			//表的主键
       					this.budg_year	 +"@"+
       					this.type_code   +"@"+ 
       					this.type_name   +"@"+ 
       					this.subj_code   +"@"+ 
       					this.subj_name   +"@"+
       					this.type_code_old   +"@"+ 
       					this.subj_code_old   +"@"+ 
    					this._rowIndx +"@"+ 
    					'1' //添加数据标识
    				) 
    			});
        	}
        	
    		if(data.updateList.length > 0){
        		var updateData = data.updateList ;
                $(updateData).each(function (){	
                	ParamVo.push(
          				//表的主键
     					this.budg_year	+"@"+
     					this.type_code   +"@"+ 
     					this.type_name   +"@"+ 
     					this.subj_code   +"@"+ 
     					this.subj_name   + "@" +
     					this.type_code_old   +"@"+ 
       					this.subj_code_old   +"@"+ 
    					this._rowIndx +"@"+ 
    					'2' //修改数据标识
    				) 
    			});
        	}
			ajaxPostData({
				url: "saveBudgCostTypeSet.do?isCheck=false",
				data: { ParamVo: ParamVo.toString() },
				success: function (res) {
					query();
				}
			});
			
		}else{
    		$.etDialog.warn('没有需要保存的数据!');
    	}
	}
	
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否重复或者为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.budg_year) {
				rowm+="[年度]、";
			}
			if (!v.subj_name) {
				rowm+="[科目名称]、";
			}
			if (!v.type_name) {
				rowm+="[科目类型]、";
			}
			if(rowm != ""){
				rowm = "第"+(Number(v._rowIndx)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key=v.budg_year + v.subj_code + v.type_code
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
    
	function remove() {
		var data = grid.selectGet();
        if (data.length == 0){
        	$.etDialog.error('请选择行');
        }else{
            var ParamVo =[];//后台删除数据
            var deletePageRow = [];// 页面删除数据
            $(data).each(function (){	
            	if(this.rowData.group_id){
            		ParamVo.push(
           				//表的主键
						this.rowData.group_id   +"@"+ 
						this.rowData.hos_id   +"@"+ 
						this.rowData.copy_code +"@"+
						this.rowData.budg_year +"@"+
						this.rowData.type_code +"@"+
						this.rowData.subj_code 
       				) 
            	}else{
            		deletePageRow.push(this);
            	}
			});
            if(ParamVo.length > 0){
            	$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: "deleteBudgCostTypeSet.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function () {
							query();
						}
					})
				});
            }else if(deletePageRow.length > 0 ){
            	grid.deleteRows(deletePageRow);
            	$.etDialog.success("删除成功!");
            }
		}
	}
    
    //继承
    function extend(){
    	var ParamVo ={ budg_year:year_input.getValue()};
    	$.etDialog.confirm('确定继承上一年收入科目类别维护数据吗?', function (){
	    	ajaxPostData({
				url: "extendBudgCostTypeSet.do?isCheck=false",
				data: ParamVo,
				success: function () {
					query();
				}
			})
        }); 
    }
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label" style="width:100px">支出科目类别：</td>
				<td class="ipt">
					<select name="" id="type_code" style="width:180px;"></select>
				</td>
				<td class="label" style="width:100px">支出预算科目：</td>
				<td class="ipt">
					<select name=" " id="subj_name_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>
</html>

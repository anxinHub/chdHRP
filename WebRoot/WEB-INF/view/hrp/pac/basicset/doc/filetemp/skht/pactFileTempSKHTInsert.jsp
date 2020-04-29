<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,tree,grid,hr,upload,pageOffice" name="plugins" />
	</jsp:include>
	<script>
	var parentFrameName = parent.$.etDialog.parentFrameName;
	var parentWindow = parent.window[parentFrameName]
	 var  mainGrid,pact_type_code;
     $(function () {
         
         initGrid();
         var pact_type_code=GetQueryString('pid');
       
        
         setTimeout(function(){
        	 initTree();
    		},1000);
         // 给输入框绑定搜索树事件
        
     })
	 /**
	  * 获取页面路径传递的参数
	  * @param name
	  * @returns
	  */
	 function GetQueryString(name) {
	     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	     var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
	    //console.info(r);
	     var context = "";
	     if (r != null)
	         context = decodeURIComponent(r[2]);
	     reg = null;
	     r = null;
	     return context == null || context == "" || context == "undefined" ? "" : context;
	 }
     var query = function () {
         params = [];
    	 //var pact_type_code=GetQueryString('pid');

    	 //var params=[{name:'pact_type_code',value:pact_type_code}];
     	//grid.loadData(params,'queryPactFileTempFKHTfile.do?isCheck=false');
         grid.loadData(params);
     };
       //跳转保存页面
       var addFileType = function (data) {
       	 $.etDialog.open({
                url: 'PactFileTempSKHTAdd.do?isCheck=false',
                width: 300,
                height: 350,
                title: '添加',
                btn: ['保存', '取消'],
                modal: true,
                btn1: function (index, el) {
                    var iframeWindow = window[el.find('iframe').get(0).name];
                    iframeWindow.save()
                }
            });
       };
       
  
       
       
       var save = function(){
           var param = [];
           var pact_type_code=GetQueryString('pid');
           var data = grid.selectGet();
	        if (data.length == 0) {
	        	var rowdata = new Object();
              
              	rowdata.pact_type_code=pact_type_code;
              	rowdata.group_id = ${group_id};
               rowdata.hos_id = ${hos_id};
               rowdata.copy_code = '${copy_code}';
               param.push(rowdata);
	        }else{
	        	 $(data).each(function () {
		                var rowdata = this.rowData;
		               	
		               	rowdata.pact_type_code=pact_type_code;
		               	rowdata.file_type=rowdata.type_code;
		               	rowdata.group_id = ${group_id};
	                    rowdata.hos_id = ${hos_id};
	                    rowdata.copy_code = '${copy_code}';
		                param.push(rowdata);
		          });
	        }
           ajaxPostData({
               url: 'addPactFileTempSKHT.do?isCheck=false',
               data: {
                   paramVo: JSON.stringify(param)
               },
               success: function(){
            	   var curIndex = parent.$.etDialog.getFrameIndex(window.name);
      		        parent.$.etDialog.close(curIndex);
               	var params=[{name:'pact_type_code',value:pact_type_code}];
      				parent.window.grid.loadData(params,'queryPactFileTempSKHTfile.do?isCheck=false');
               }
           })
   }
   	 var initGrid = function () {
         var columns = [
         	 { display: '归档项目编码', name: 'type_code',width: '15%',editable: false},
              { display: '归档项目名称', name: 'type_name',width: '19%',editable: false},
              { display: '备注', name: 'note', width: '15%',editable: true,hidden:false}
         ];
         
         var paramObj = {
         	editable: true,
         	height: '100%',
         	width:'100%',
         	checkbox: true,
             dataModel: {
             	url: '../../filetype/queryPactFileType.do?ischeck=false&is_stop=0'
             },
             columns: columns,
             toolbar: {
                 items: [
                   { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                    { type: 'button', label: '添加归档项目', listeners: [{ click: addFileType }], icon: 'add' }
                 ]
             }
         };
         grid = $("#mainGrid").etGrid(paramObj);
     };
     var initTree = function () {
      
    	 var type_code=GetQueryString('pid');
	                    	grid.selectRemoveAll();
	                    	 $.ajax({
	                             type: 'POST',
	                             url: 'queryPactFileTempSKHT.do',
	                             data : {
	                            	pact_type_code : type_code
	                  			},
	                             dataType: 'json',
	                             success: function (res) {
	                            	var rows = res.Rows;
	                            	for(var i=0;i<rows.length;i++){
	                            		var data = grid.getDataInPage();
	                            		
	                            		for(var j=0;j<data.length;j++){
	                            			var obj = data[j];
	                            			
	                            			if(rows[i].file_type == obj.type_code){
	                            				grid.selectAdd(obj._rowIndx)
	                            				break;
	                            			}
	                            		}
	                            	}
	                             }
	                         })
                 
                
                 };
             
         
    

	</script>
</head>
<body>
<div class="container">
       
        
            <div id="mainGrid"></div>
        
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
	</jsp:include>
	<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script>

var codeAndname;
var BF_Type1,BF_Type2;
var str1= "";
var str2= "";
var grid;
var attribute;
var query = function () {
    params = [
    	 { name: 'CS_Code', value: $("#codeAndname").val() },
    ];
 
    grid.loadData(params);
	
};
  var initGrid = function () {
      var columns = [
           { display: 'ID', name: 'CS_Rowid', width: '80px',hidden:true},
      	   { display: '数据源编码', name: 'CS_Code', width: '200px'},
           { display: '数据源名称', name: 'CS_Name', width: '200px'}
      ];
      var paramObj = {
      	editable: true,
      	height: '99%',
      	width:'100%',
      	checkbox: true,
      	selectionModel:{
      		type: "cell", 
      		mode:"single"
      	},
          dataModel: {
             url: 'queryPACTTemplateDataSource.do?isCheck=false'
          },
          rowClick:  rowClick_f,
          rowSelect:  rowClick_f, 
          columns: columns,
          toolbar: {
              items: [
                  { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                  { type: 'button', label: '新增', listeners: [{ click: add }], icon: 'add' },
                  { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                  { type: 'button', label: '删除', listeners: [{ click: del }], icon: 'del' },

              ]
          }
      };
      grid = $("#maingrid").etGrid(paramObj);
  };
 function add(){
	 grid.addRow();
	 initfrom(); 
 } 
 ///表格行点击时间，加在右边内容
 var rowClick_f=function(event,ui){
	               grid.setSelection(ui.rowIndx,true,true);				
				   var CS_Rowid=ui.rowData.CS_Rowid;
				   var CS_Input=ui.rowData.CS_Input;
				   var CS_OutPut=ui.rowData.CS_OutPut;
				   var CS_SQL=ui.rowData.CS_SQL;
				   var CS_Attribute=ui.rowData.CS_Attribute;
				   if(CS_Rowid!=0){
				   ajaxPostData({
			         url: 'queryPACTTemplateDataSource.do?isCheck=false',
			         data: {
			        	 CS_Rowid:CS_Rowid			                         
			         },
			         success: function () {
			      	 if(CS_Attribute){		      		
				     	  str1 ="";
			      		  str2 ="";
				     	  $("input[name='con_radio'][value="+CS_Attribute+"]").prop("checked",true); 
			      		  if (CS_Attribute=='1'){
			      			str1 = '<td colspan="2" style="text-align:left align="left" style="width: 100px;">静态数据源</td>';
							str2 = '<td style="width: 735px;" colspan="2"><textarea id="CS_SQL"  style="width: 500px;height: 150px"></textarea></td>';
							$('#headerTitle').html(str1);
							$('#headerBody').html(str2);
			      		  }else{
			      			str1 = '<td colspan="2" style="text-align:left; align:left" style="width: 100px;">SQL</td>';
							str2 = '<td style="width: 735px;" colspan="2"><textarea id="CS_SQL"  style="width: 500px;height: 150px"></textarea></td>';
							$('#headerTitle').html(str1);
							$('#headerBody').html(str2);
				     	   }
			      		$("#CS_SQL").val(CS_SQL);
						$("#CS_Input").val(CS_Input);
						$("#CS_OutPut").val(CS_OutPut);
					
					}

				},
				error : function() {
					$("#CS_Code").val('');
					$("#CS_Name").val('');
					$("#CS_Input").val('');
					$("#CS_OutPut").val('');
					$("#CS_SQL").val('');
				}
			});

		} else {
			$("#CS_Code").val('');
			$("#CS_Name").val('');
			$("#CS_Input").val('');
			$("#CS_OutPut").val('');
			$("#CS_SQL").val('');
		}

	};
	//跳转保存页面
	var save = function(data) {
		 var data = grid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
             return;
         }
		var error="";
		var param = [];
		if(data!=null && data.length != 0){
			 $(data).each(function () {
	            var rowdata = this.rowData;
	            var row = rowdata._rowIndx +1 ;		       
	            if(!rowdata.CS_Code){error = "第"+row + "编码不能为空";return;}
	            if(!rowdata.CS_Name){error = "第"+row + "名称不能为空";return;}     	                      
	            rowdata.IFB_GROUPID = ${IFB_GROUPID};
	            rowdata.IFB_PrjName = ${IFB_PrjName};
	            rowdata.COPY_CODE = '${COPY_CODE}';
	            //alert($("#CS_SQL").val()+"te="+$("input[name='con_radio']:checked").val())	//yh
	            rowdata.CS_Attribute =$("input[name='con_radio']:checked").val();
	            rowdata.CS_Input = $("#CS_Input").val();
	            rowdata.CS_SQL = $("#CS_SQL").val();
	            rowdata.CS_OutPut = $("#CS_OutPut").val();
	            if(!rowdata.CS_Rowid){
	            	param.push(rowdata);
	            	addByid(param);	                 	     	
	            	}else{
	            	  rowdata.PIT_IsActive=1
	            	  param.push(rowdata);
	            	  updateByid(param);	       	            		
	            	}
	            //alert( rowdata.group_id+","+rowdata.hos_id+","+rowdata.copy_code+"+[]+"+CodeResource+rowdata.PIT_TypeCode+rowdata.PIT_TypeName+rowdata.PI_MethodName+rowdata.PI_ClassName+rowdata.PI_ClassDesc+rowdata.PI_BEANNAME+rowdata.PIT_SYSFrom)            
	        });
	        if(error.length!=0){$.etDialog.error(error);return;}
		}

	};
	//更新
	var updateByid = function(param) {
		console.log(param);
		ajaxPostData({
		 	url: 'updatePACTTemplateDataSource.do?isCheck=false',
		 	 data: {
            	 mapVo: JSON.stringify(param)
             },
			  success: function (result) {
				  query();
			  },
		});
	}
	//新增
	var addByid = function(param) {
		console.log(param);		
		ajaxPostData({
		 	url: 'addPACTTemplateDataSource.do?isCheck=false',
		 	 data: {
            	 mapVo: JSON.stringify(param)
             },
			  success: function (result) {
				  query();
			  },
		});
	}
	//删除
	var del = function() {
		 var data = grid.selectGet();
         if (data.length == 0) {
             $.etDialog.error('请选择行');
             return;
         } else {
             var param = [];
             $(data).each(function () {
                 var rowdata = this.rowData;
                 rowdata.IFB_GROUPID = ${IFB_GROUPID};
 	             rowdata.IFB_PrjName = ${IFB_PrjName};
 	             rowdata.COPY_CODE = '${COPY_CODE}'; 	            
                 param.push(rowdata);
             });
             $.etDialog.confirm('确定删除?', function () {
                 ajaxPostData({
                     url: 'deletePACTTemplateDataSource.do',
                     data: {
                    	 mapVo: JSON.stringify(param)
                     },
                     success: function () {
                    	 grid.deleteRows(data);
                    	 query();
                     }
                 })
             });
         }	
	};

	$(function() {
		initfrom();
		initGrid();
		
	})

	var initfrom = function() {
   	        $("input[name='con_radio'][value='1']").prop("checked",true); 
			str1 = '<td colspan="2" style="text-align:left align="left" style="width: 100px;">静态数据源</td>';
			str2 = '<td style="width: 735px;" colspan="2"><textarea id="CS_SQL"  style="width: 500px;height: 150px"></textarea></td>';
			$('#headerTitle').html(str1);
			$('#headerBody').html(str2);
			$("input[name='con_radio']").change(function(){	
				var val = $(this).val();
				attribute=val;	//yh
				  $("input[name='con_radio'][value="+val+"]").prop("checked",true); 
				 if (val=='1'){
		   			    str1 = '<td colspan="2" style="text-align:left align="left" style="width: 100px;">静态数据源</td>';
						str2 = '<td style="width: 735px;" colspan="2"><textarea id="CS_SQL"  style="width: 500px;height: 150px"></textarea></td>';
						$('#headerTitle').html(str1);
						$('#headerBody').html(str2);
		   		  }else if(val=='2'){
		   			    str1 = '<td colspan="2" style="text-align:left; align:left" style="width: 100px;">SQL:</td>';
						str2 = '<td style="width: 735px;" colspan="2"><textarea id="CS_SQL"  style="width: 500px;height: 150px"></textarea></td>';
						$('#headerTitle').html(str1);
						$('#headerBody').html(str2);
			     }
			});
	}
	

</script>
</head>

<body  style="overflow: scroll; ">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table class="table-layout">
       <tr>
      		<td class="label no-empty" style="width: 100px;">数据源:</td>
			<td class="ipt"><input id="codeAndname" style="width: 180px;"></input> </td>
         <!--yh    <td class="label" style="width: 100px;">数据源：</td>
            <td class="ipt">
                <select id="codeAndname" style="width: 180px"></select>
            </td>--> 
            
        </tr>
    </table>   
         <div class="container" style="width: 100%;" > 
	     <div align="left" style="width: 40%" >
            <div id="maingrid"></div>
        </div>     
       <div align="left" style="width: 60%;" id="con" >  
       <table  class="table-layout">
        <tr >
        <td style="width:100px;"><input type="radio" id="CS_Attribute1"  name="con_radio" value="1" />下拉框</td>
        <td style="width:100px;"><input type="radio" id="CS_Attribute2"  name="con_radio" value="2"/>下拉数据集</td>
       </tr>
       <tr >
       <td colspan="2" style="text-align:left" align="left"   style="width: 500px;">数据源入参：</td>
       </tr>
       <tr>
       <td colspan="2" ><input id="CS_Input" type="text" style="width: 500px;"/></td>
       </tr>
       <tr id="headerTitle">
       <td colspan="2" style="text-align:left; align:left" style="width: 100px;">SQL</td>
       </tr>
       <tr id="headerBody">
       <td colspan="2" ><input id="CS_SQL" type="text" style="width: 500px;height: 150px"/></td>
       </tr>
       <tr >
       <td colspan="2" style="text-align:left" align="left" style="width: 100px;">数据源返回值参数：</td>
       </tr>
       <tr>
       <td colspan="2" ><input id="CS_OutPut" type="text" style="width: 500px;;height: 50px"/></td>
       </tr> 
       </table>
       </div>     
     </div>
    
</body>
</html>
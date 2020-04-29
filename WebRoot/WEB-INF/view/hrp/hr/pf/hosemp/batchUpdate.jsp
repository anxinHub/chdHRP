<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>条件查询</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, datepicker, checkbox, grid, hr,form, validate" name="plugins" />
        </jsp:include>
       <style type="text/css">
       .div{
       float:left;
        height:100%;
        width: 100%;
       }
   .div1{
       float:left;
        height: 98%;
        width: 25.2%;
           margin-top: 5px;
         border:1px solid #aecaf0; 
       }
      .div2{
       float:right;
        height: 98%;
        width: 69.2%;
         margin-top: 5px;
         border:1px solid #aecaf0;  
       }
       .form-readonly{
    position: relative;
}
.form-readonly:before{
    content: "";
    z-index: 1;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
}
.label1{
background-color:#d6e4f4;
    font-size: 14px;
    width: 100%;
    text-align: left;
    display: inline-block;

}
#detailTable{
margin-left: -40px;
 margin-top: -35px;
  
}
h1{
    font-size: 2px;
    color: red;
}
       </style>
        <script>
        	var main_form,child_form, serach_form,serach1_form,validate;
        	
            $(function () {
                init();
            });

            function init() {
                initForm();
                leftForm();
            }

            function initForm() {
          	  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
              
			  var parentFrameName = parent.$.etDialog.parentFrameName;
			  var parentWindow = parent.window[parentFrameName];
			  
            	
            	
            	var mainformEle =  ${formEle};
            
            	var option=mainformEle.fieldItems;
            	
            
            	/* for(var k=0;k<option.length;k++){
        			option[k].disabled=true;
        			
        		} */
            	main_form = $('#main').etForm(mainformEle);
                
            	main_form.initWidget();

                validate = main_form.initValidate();
                
              /*   var child_fromEle = ${child_fromEle};
                
         

                	var option=child_fromEle.fieldItems;

                	
                	child_form = $('#child').etForm(child_fromEle);
                	
                	child_form.initWidget();
                	
                    validate = child_form.initValidate(); */
                    
                
                
             
            }
            function leftForm(){
          	  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                
  			  var parentFrameName = parent.$.etDialog.parentFrameName;
  			  var parentWindow = parent.window[parentFrameName];
  			  
  			  var tab_code=parentWindow.main_select.getValue();
  			  
  			  var store_type_code = parentWindow.archives_select.getValue();
  			  
  			  var child_code=parentWindow.child_select.getValue();
  			  
  			  var data = new Object(parentWindow.searchParam);
  			 var arr = new Array();
        	 
        	 var data = new Object(parentWindow.searchParam);
        	 
        	 Object.keys(data).forEach(function(key){
        		 
        		 arr.push({name: key,value: data[key]})

        	}); 
          	/*   $.post('hrEmpUpateLeft.do?isCheck=false&tab_code=' + tab_code + '&store_type_code='+store_type_code+'&child_code='+child_code+'&Param='+JSON.stringify(data), function (data) {
                    $('#serach').html(''); //初始化前清空
                   
                  	var option=data.fieldItems;
                	for (var i=0;i<arr.length;i++){
                		
                		var fieldItems;
                		
                		var name=arr[i].name.slice(7);
                	 	
                		for(var j=0;j<option.length;j++){
                			//option[j].disabled=true;
                			var optionName=option[j].id;
                			
	                			if(name==optionName){		if(option[j].type=="select"){
	                				option[j].OPTIONS.defaultValue=arr[i].value;
	               				 option[j].disabled=true;
	               				}
	           				
	           				if(option[j].type=="text"){
	           					option[j].value=arr[i].value;
	           					 option[j].disabled=true;
	           				}
	           				if(option[j].type=="date"){
	           					option[j].value=arr[i].value;
	           					//option[j].background-color='#ccc';
	           				}
	                			}
                		} 
                		
                	}
                	 serach_form = $('#serach').etForm(data);
                     serach_form.initWidget();
                     validate = serach_form.initValidate();
                }, 'json'); */
            }
            function saveUpdate() {
            	
             	var formData = convert_FormData_to_json(main_form.getFormData());
             	
          	   var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                
                var parentFrameName = parent.$.etDialog.parentFrameName;
                var parentWindow = parent.window[parentFrameName];
                parentWindow.saveUpdate(formData); 
               /*  parent.window.saveUpdate(formData); */
            }
            var extend=function(obj1,obj2){ 
            	 for(var key in obj2){ 
            	    if(obj1.hasOwnProperty(key))continue;//有相同的属性则略过 
            	    obj1[key]=obj2[key]; 
            	 } 
            	 return obj1; 
            	}
            var convert_FormData_to_json = function (formData) {
            	//var  ParamVo = []
            	  var objData = {};
                
                for (var entry of formData.entries()){
                	
                	if(entry[1] != ''){
                		
                		
                		objData[entry[0]] = entry[1];
                		
                		//ParamVo.push(objData);
                	}
                    
                }
                
                return objData;
            };
     /*        function getFormData(form){
            	      var data = form.serialize();
            	      data = decodeURI(data);
            	      var arr = data.split('&');
            	      var item,key,value,newData={};
            	      for(var i=0;i<arr.length;i++){
            	          item = arr[i].split('=');
            	         key = item[0];
            	          value = item[1];
            	          if(key.indexOf('[]')!=-1){
            	          key = key.replace('[]','');
            	          if(!newData[key]){
            	          newData[key] = [];
            	          }
            	          newData[key].push(value);
            	          }else{
            	          newData[key] = value;
            	          }
            	     }
            	      return newData;
            	} */
            	
            	$(function(){

            		 add_detail();

            		})

            		var newAddId = 0;
            		function add_detail(){
            			 var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                         
             			  var parentFrameName = parent.$.etDialog.parentFrameName;
             			  var parentWindow = parent.window[parentFrameName];
             			  
             			  var tab_code=parentWindow.main_select.getValue();
             			  
             			  var store_type_code = parentWindow.archives_select.getValue();
             			  
             			  var child_code=parentWindow.child_select.getValue();
             			  
             			  var data = new Object(parentWindow.searchParam);
            		    var add_id;
            		    add_id = 'add_'+newAddId;
            		    newAddId++;
            		    $.ajax({
            		        type: "post",
            		        dataType: "json",
            		        async: false,  //同步
            		        url: "hrEmpUpateLeft.do?isCheck=false",
            		        data: {
            		         tab_code: tab_code,
            		store_type_code:store_type_code,
            		child_code:child_code,
            		Param:JSON.stringify(data)
            		       },
            		       success: function (data) {
            		            var str = "";
            		            var json = eval(data.fieldItems);
            		            $.each(json, function (index, item) {  
            			
            		
            		                //循环获取数据    
            		                str += '<tr>' +   
            		                    '<td><td class="label">'+item.name+":"+'</td>   <td class="ipt"> <input id="'+item.id+'" type="text" value="'+item.OPTIONS.defaultValue+' " disabled></td>'+
            		                    // '<td><td class="label">'+item.name+":"+'</td>   <td class="ipt"> <input id="'+item.id+'" type="'+item.type+'" value="'+item.OPTIONS.defaultValue+' " disabled></td>'
            		                    '</tr>';   
            		            }); 
            		            $("#detailTable tbody").prepend(str);
            		        },
            		})
            		}
        </script>
        
    </head>

    <body style="overflow: auto">
	    <div class="div">
	    <h1>说明：批量修改勾选无效，会修改所有查询结果集数据.</h1>
	    <div class="div1">
	    <label class="label1">修改前</label>
	<table id="detailTable" class="table-layout" >
 <thead>
  <tr>
   <th></th>
   <th></th>
 
  </tr>
 </thead>
 <tbody>
 </tbody>
</table>

	   <!--   <form id='serach1' >  </form>    -->
	       
	    </div>
       
	    <div class="div2">
	     <label class="label1">修改后</label>
	      <form id='main' class="main">  </form>
	        <form id='child' class="main"> </form> 
	        
	    </div>
	    
	    </div>
    </body>
    </html>
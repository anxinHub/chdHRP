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
        var grid;
        var con_type1,con_type2,con_type3,con_type4,con_type5,con_type6,conp_personelid,conp_personelidn,uploadFile;
        var startpicker;
        var personelids=[];
        var personelidns=[];
        var endpicker;
        var query = function () {
            params = [
                { name: 'con_type', value: $("#con_type").val() },
                { name: 'con_date', value: $("#con_date").val() },
                { name: 'prj_name', value: $("#prj_name").val() }
            ];
            grid.loadData(params);
        };
        
        var initSelect=  function(){
           //$("#con_type").etSelect({url: '../../../basicset/select/queryPactTypeFKXYSelect.do?isCheck=false',defaultValue: "none"});
           
          conp_personelid= $("#conp_personelid").etSelect({
        	   url: '../../pac/basicset/select/queryHosEmpDictSelect.do?isCheck=false',
        	   defaultValue: "none",
        	   onItemAdd: onItemAdd_f
        	   //checkboxMode:true  
        		   });
           
           conp_personelidn= $("#conp_personelidn").etSelect({
        	   url: '../../pac/basicset/select/queryHosEmpDictSelect.do?isCheck=false',
        	   defaultValue: "none",
        	   onItemAdd: onItemAddn_f
        	   //checkboxMode:true 
        		   });
         
        
           var select = $('#con_type').etSelect({
               valueField: 'id',
               labelField: 'text',
               searchField: 'text',
               options: [
                   { id: "招标", text: '招标' },
                   { id: "投标", text: '投标' },
                   { id: "开标", text: '开标' },
                   { id: "评标", text: '评标' },
                   { id: "定标", text: '定标' },
                   { id: "签订合同", text: '签订合同' }

               ],
               create: false
           });
           
        }
        
        var initGrid = function () {
            var columns = [
                 { display: 'CON_ID', name: 'con_id', width: '80px',hidden:true},
            	 { display: '会议类型', name: 'con_type', width: '80px'},
                 { display: '会议主题', name: 'con_theme', width: '190px'},
                 { display: '会议日期', name: 'con_date',  width: '80px'}
            ];
            var paramObj = {
            	editable: false,
            	height: '99%',
            	width:'100%',
            	checkbox: true,
            	selectionModel:{
            		type: "cell", 
            		mode:"single"
            	},
                dataModel: {
                   url: 'queryAssConference.do?isCheck=false'
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
        
        ///参会人员选择后事件
       
        var onItemAdd_f=function(value,item){
        	
        if(personelids.indexOf(value)==-1){
        	var emp_name=item[0].innerHTML;
        	$("#conp_personelids").append("<span id='personelids_"+value+"' ondblclick='removesp(id)';>;"+emp_name+"</span>");
        	//把id保存在对象中，保存时用
        	personelids.push(value);
        	//conp_personelid.removeOption(value) 
        	}
        };
        ///缺席人员
        var  onItemAddn_f=function(value,item){
        	
            if(personelidns.indexOf(value)==-1){
            	var emp_name=item[0].innerHTML;
            	$("#conp_personelidns").append("<span id='personelidns_"+value+"' ondblclick='removespn(id)';>;"+emp_name+"</span>");
            	//把id保存在对象中，保存时用
            	personelidns.push(value);
            	//conp_personelidn.removeOption(value) 
            	}
            };
        Array.prototype.indexOf = function(val) {
        	for (var i = 0; i < this.length; i++) {
        	if (this[i] == val) return i;
        	}
        	return -1;
        	};
        
        Array.prototype.remove = function(val) {
        		var index = this.indexOf(val);
        		if (index > -1) {
        		this.splice(index, 1);
        		}
        		};
        ///双击移除,并且移除相关数据
        var removesp=function(th){
        if($("#con_id").val()>0){
        	$.etDialog.confirm('确定删除?', function () {
        		$("#"+th).remove();
            	personelids.remove(th.split("_")[1]);
            	//conp_personelid.addOption(th.split("_")[1]);
            	//conp_personelid.refreshOptions();
            	
                ajaxPostData({
                    url: 'deleteConferencePerson.do?isCheck=false',
                    data:{
                    	conp_personelid: th.split("_")[1],                   	
                    	con_id: $("#con_id").val(),
                    	conp_type:'出席'
                     },
                    success: function () {
                    }
                })
            });
        	
        	
        	}else{
        		$("#"+th).remove();
            	personelids.remove(th.split("_")[1]);
        		
        	}
        };
        
        ///双击移除,并且移除相关数据
        var removespn=function(th){
        	
        	if($("#con_id").val()>0){
            	$.etDialog.confirm('确定删除?', function () {
            		$("#"+th).remove();
            		personelidns.remove(th.split("_")[1]);
                	//personelidns.remove(th.split("_")[1]);
                	//personelids.remove(th.split("_")[1]);
                	//conp_personelid.addOption(th.split("_")[1]);
                	//conp_personelid.refreshOptions();
                	
                    ajaxPostData({
                        url: 'deleteConferencePerson.do?isCheck=false',
                        data:{
                        	conp_personelid: th.split("_")[1],                   	
                        	con_id: $("#con_id").val(),
                        	conp_type:'缺席'
                         },
                        success: function () {
                        }
                    })
                });
            	
            	
            	}else{
            		$("#"+th).remove();
                	personelidns.remove(th.split("_")[1]);
            		
            	}
        	
        
        	
        };
        ///增加空行
       var add=function(){
    	   grid.addRow();  
    	   
       };
       ///表格行点击时间，加在右边内容
       var rowClick_f=function(event,ui){
    	   personelids=[];
    	   personelidns=[];
    	   $('#conp_personelids').children().remove();
    	   $('#conp_personelidns').children().remove();
    	   conp_personelid.setValue("");
    	   conp_personelidn.setValue("");
    	   var con_id=ui.rowData.con_id;
    	   if(!con_id){con_id=0;}
    	   //alert(con_id);
    	   $("#con_id").val(con_id);
    	 if(con_id!=0){
    	   ajaxPostData({
               url: 'queryAssConferenceByID.do?isCheck=false',
               data: {
            	   con_id:con_id
                               
               },
               success: function (response) {

            	 if(response.con_theme){
            	   $("#con_theme").val(response.con_theme);
            	   $("#prj_named").val(response.prj_name);
            	   $("#con_convener").val(response.con_convener);
            	   $("#con_host").val(response.con_host);
            	   $("#con_recorder").val(response.con_recorder);
            	   $("#con_dated").val(response.con_date);
            	   $("#start_time").val(response.con_stime);
            	   $("#end_time").val(response.con_etime);
            	   $("#con_address").val(response.con_address);
            	   $("#con_content").val(response.con_content);
            	   $("#con_conclusion").val(response.con_conclusion);
            	   $("#con_file").val(response.con_file);
            	   $("#con_id").val(response.con_id);
            	   $("#conp_otherpersonel").val(response.otherpersonel);
            	  // $("#con_id").val(1);
            if(response.con_type=="招标"){
            	 con_type1.setCheck();
               	}else if(response.con_type=="投标"){
               	 con_type2.setCheck();
               	}else if(response.con_type=="开标"){
               	 con_type3.setCheck();
               	}
               	else if(response.con_type=="评标"){
               	 con_type4.setCheck();
               	}
               	else if(response.con_type=="定标"){
               	 con_type5.setCheck();
               	}
               	else if(response.con_type=="签订合同"){
               	 con_type6.setCheck();
               	}
            	
            	///下面处理参会人员和缺席人员
            	//参会
            	var personelNames=response.personelNames;
            	for(var i=0;i<personelNames.split(",").length;i++){
            		var id=personelNames.split(",")[i].split("-")[0];
            		var name=personelNames.split(",")[i].split("-")[1];
            		if(id){
            		$("#conp_personelids").append("<span id='personelids_"+id+"' ondblclick='removesp(id)';>;"+name+"</span>");
            		
            		personelids.push(id);
               	 
            		}
            	};
            	///缺席
            	var personelNamens=response.personelNamens;
            	for(var i=0;i<personelNamens.split(",").length;i++){
            		var idn=personelNamens.split(",")[i].split("-")[0];
            		var namen=personelNamens.split(",")[i].split("-")[1];
            		if(idn){
            		$("#conp_personelidns").append("<span id='personelidns_"+idn+"' ondblclick='removespn(id)';>;"+namen+"</span>");
            		   personelidns.push(idn);
            	   }
            	};
            	
              
            	}else{
            	   $("#con_theme").val('');
            	   $("#prj_named").val('');
            	   $("#con_convener").val('');
            	   $("#con_host").val('');
            	   $("#con_recorder").val('');
            	   $("#con_dated").val('');
            	   $("#start_time").val('');
            	   $("#end_time").val('');
            	   $("#con_address").val('');
            	   $("#con_content").val('');
            	   $("#con_conclusion").val('');
            	   $("#con_file").val('');  
            	   $('#conp_personelids').children().remove();
            	   $('#conp_personelidns').children().remove();
            	   conp_personelid.setValue("");
            	   conp_personelidn.setValue("");
            	   $("#conp_otherpersonel").val('');
               }
               
           },
           error: function () { 
        	   //alert("shibai");
        	   $("#con_theme").val('');
        	   $("#prj_named").val('');
        	   $("#con_convener").val('');
        	   $("#con_host").val('');
        	   $("#con_recorder").val('');
        	   $("#con_dated").val('');
        	   $("#start_time").val('');
        	   $("#end_time").val('');
        	   $("#con_address").val('');
        	   $("#con_content").val('');
        	   $("#con_conclusion").val('');
        	   $("#con_file").val('');  
        	   $('#conp_personelids').children().remove();
        	   $('#conp_personelidns').children().remove();
        	   conp_personelid.setValue("");
        	   conp_personelidn.setValue("");
        	   $("#conp_otherpersonel").val('');
           }
           });
    	   
    	  }else{
    		   $("#con_theme").val('');
        	   $("#prj_named").val('');
        	   $("#con_convener").val('');
        	   $("#con_host").val('');
        	   $("#con_recorder").val('');
        	   $("#con_dated").val('');
        	   $("#start_time").val('');
        	   $("#end_time").val('');
        	   $("#con_address").val('');
        	   $("#con_content").val('');
        	   $("#con_conclusion").val('');
        	   $("#con_file").val('');  
        	   $('#conp_personelids').children().remove();
        	   $('#conp_personelidns').children().remove();
        	   conp_personelid.setValue("");
        	   conp_personelidn.setValue("");
        	   $("#conp_otherpersonel").val('');
    		   
    	   }
       };
        //跳转保存页面
        var save = function (data) {
        	var con_content= $("#con_content").val()
        	
        	if(con_content.length>500){
        		 $.etDialog.error('会议内容控制在500字内');
        		 return;
        	}
        	
        	if(con_conclusion.length>500){
       		 $.etDialog.error('会议结论控制在500字内');
       		 return;
       	}
        	var con_type="";
        	//console.log(con_type1.status);
        	if(con_type1.status=="checked"){
        		con_type="招标";
        	}else if(con_type2.status=="checked"){
        		con_type="投标";
        	}else if(con_type3.status=="checked"){
        		con_type="开标";
        	}
        	else if(con_type4.status=="checked"){
        		con_type="评标";
        	}
        	else if(con_type5.status=="checked"){
        		con_type="定标";
        	}
        	else if(con_type6.status=="checked"){
        		con_type="签订合同";
        	}
        	if(con_type==""){
        		 $.etDialog.error('请选择会议类型');
        		 return;
        	}
			formValidate = $.etValidate({
				items : [ 
					{el : $("#con_theme"), required : true}, 
					{el : $("#con_convener"),required : true},
					{el : $("#con_host"),required : true},
					{el : $("#con_recorder"),required : true},
					{el : $("#con_dated"),required : true},
					{el : $("#start_time"),required : true},
					{el : $("#end_time"),required : true},
					{el : $("#con_content"),required : true}
					
					]
			});
			
			if(!formValidate.test()){return;};
        	var urlstr="";
        	var con_id= $("#con_id").val();

        	if((con_id>0)&&(con_id)){
        		urlstr='updateAssConference.do?isCheck=false';
        	}else{
        		urlstr='saveAssConference.do';
        	}
            ajaxPostData({
                url: urlstr,
                data: {
                 con_type:con_type,
                 con_theme:$("#con_theme").val(),
                 prj_name:$("#prj_named").val(),
                 con_convener:$("#con_convener").val(),
                 con_host:$("#con_host").val(),
                 con_recorder:$("#con_recorder").val(),
                 con_date:$("#con_dated").val(),
                 con_stime:$("#start_time").val(),
                 con_etime:$("#end_time").val(),
                 con_address:$("#con_address").val(),
                 con_content:$("#con_content").val(),
                 con_conclusion:$("#con_conclusion").val(),
                 con_file:$("#con_file").val(),
                 con_id:$("#con_id").val(),
                 personelidsv :personelids.toString(),
                 personelidnsv :personelidns.toString(),
                 conp_otherpersonel:$("#conp_otherpersonel").val()
                 
                },
                success: function () {
                	query();
                	//personelids=[];
                	//personelidns=[];
                	conp_personelid.setValue("");
              	    conp_personelidn.setValue("");
                }
            })
        };
 
        
        //删除
        var del = function () {
        	 var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
             } else {
               
            	
               if(!data[0].rowData.con_id){
               // $.etDialog.error('暂无数据可删除');
                grid.deleteRows(data);
                 return;
                 }
                 $.etDialog.confirm('确定删除?', function () {
                     ajaxPostData({
                         url: 'deleteConference.do',
                         data:{
 	                    	group_id: data[0].rowData.group_id,
 	                    	hos_id: data[0].rowData.hos_id,
 	                    	copy_code: data[0].rowData.copy_code,
 	                    	con_id: data[0].rowData.con_id
 	                     },
                         success: function () {
                             grid.deleteRows(data);
                             
                      	   $("#con_theme").val('');
                    	   $("#prj_named").val('');
                    	   $("#con_convener").val('');
                    	   $("#con_host").val('');
                    	   $("#con_recorder").val('');
                    	   $("#con_dated").val('');
                    	   $("#start_time").val('');
                    	   $("#end_time").val('');
                    	   $("#con_address").val('');
                    	   $("#con_content").val('');
                    	   $("#con_conclusion").val('');
                    	   $("#con_file").val('');  
                    	   $("#con_id").val('0');  
                    	   $('#conp_personelids').children().remove();
                    	   $('#conp_personelidns').children().remove();
                    	   conp_personelid.setValue("");
                    	   conp_personelidn.setValue("");
                    	   $("#conp_otherpersonel").val('');
                         }
                     })
                 });
             }
        };        
     var uplode=function(){
    	 var con_id=$("#con_id").val();
    	 //alert(con_id);
    	 if((con_id>0)&&(con_id)){
    		 parent.$.ligerDialog.open({url:'hrp/ass/assconference/upLodePage.do?isCheck=false&con_id='+con_id,data:{},height: 500,width: 900,
     			 title:'上传',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,parentframename:window.name });
    	 }else{
    		 $.etDialog.error('请先保存会议记录') 
    	 }
     }; 
     
     function openFlie(){
    	 var con_id=$("#con_id").val();
    	 if((con_id>0)&&(con_id)){
    	 parent.$.ligerDialog.open({
 			title: '招标文件',
 			height: 600,
 			width: 1000,
 			url: 'hrp/ass/assconference/assConferenceFilePage.do?isCheck=false&con_id='+con_id,
 			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
 			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

 		}); 
    	 }else{
    		 $.etDialog.error('请先保存会议记录') 
    	 }
 	 
  };
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        
           con_type1 = $('#con_type1').etCheck();
           con_type2 = $('#con_type2').etCheck();
           con_type3 = $('#con_type3').etCheck();
           con_type4 = $('#con_type4').etCheck();
           con_type5 = $('#con_type5').etCheck();
           con_type6 = $('#con_type6').etCheck();
     
           conpicker = $("#con_date").etDatepicker({
    			//defaultDate: "yyyy-fM-fd"
    		});
    	
        	conpickerd = $("#con_dated").etDatepicker({
    			//defaultDate: "yyyy-fM-fd"
    		});
	}
    </script>
</head>

<body  style="overflow: scroll; ">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">会议类型：</td>
            <td class="ipt" ><select id="con_type" style="width: 180px"></select> </td>
            <td align="left"></td>
            <td class="label" style="width: 100px;">会议日期：</td>
            <td class="ipt">  <input id="con_date" type="text" style="width: 100px"/> </td>
            <td align="left"></td>
            <td class="label" style="width: 100px;">项目：</td>
            <td class="ipt"> <input type="text" id="prj_name"> </td>
        </tr>
       
    </table>

   <div class="container" style="width: 100%;" > 
	     <div align="left" style="width: 30%" >
            <div id="maingrid"></div>
        </div>
        
      <div align="left" style="width: 70%;" id="con" >  
       <table  class="table-layout">
       <tr>
       <td class="label" align="right" style="width: 100px;">会议类型：</td>
       <td style="width:100px;"><input type="radio" id="con_type1"  name="con_radio"/>招标</td>
       <td style="width:100px;"><input type="radio" id="con_type2" name="con_radio"/>投标</td>
       <td style="width:100px;"><input type="radio" id="con_type3" name="con_radio"/>开标</td>
       <td style="width:100px;"><input type="radio" id="con_type4" name="con_radio"/>评标</td>
       <td style="width:100px;"><input type="radio" id="con_type5" name="con_radio"/>定标</td>
       <td style="width:100px;"><input type="radio" id="con_type6" name="con_radio"/>签订合同</td>
       <td style="width:100px;"></td>
       </tr>
        <tr >
       <td class="label no-empty"  align="right" style="width: 100px;">会议主题：</td>
       <td colspan="7" ><input id="con_theme" type="text" style="width: 727px;"/></td>
       </tr>
    
       <tr>
       <td class="label"  align="right"  style="width: 100px;">项目：</td>
       <td colspan="7" ><input id="prj_named" type="text" style="width: 727px"/></td>
       </tr>
       
       <tr>
       <td class="label no-empty"   style="width: 100px;">召开人：</td>
       <td style="width: 100px;"><input id="con_convener" type="text" style="width: 100px;"/></td>
       <td style="width:100px;"></td>
       <td class="label no-empty" style="width: 90px;">主持人：</td>
       <td style="width: 100px;"><input id="con_host" type="text" style="width: 100px;"/></td>
       <td style="width:100px;"></td>
       <td  class="label no-empty" style="width: 90px;">会议日期：</td>
       <td style="width: 100px;"><input id="con_dated" type="text" style="width: 100px;"/></td>
       </tr>
       
       <tr>
       <td class="label no-empty"   style="width: 100px;">起始时间：</td>
       <td style="width: 100px;"><input id="start_time" type="text" style="width: 100px;"/></td>
       <td style="width:100px;"></td>
       <td class="label no-empty" style="width: 90px;">结束时间：</td>
       <td style="width: 100px;"><input id="end_time" type="text" style="width: 100px;"/></td>
       <td style="width:100px;"></td>
       <td  class="label no-empty" style="width: 90px;">记录人：</td>
       <td style="width: 100px;"><input id="con_recorder" type="text" style="width: 100px;"/></td>
       </tr>
               
       <tr>
       <td class="label"   style="width: 100px;">会议地点：</td>
       <td style="width: 100px;"><input id="con_address" type="text" style="width: 100px;"/></td>
       <td style="width:100px;"></td>
       <td class="label" style="width: 90px;">会议文件：</td>
       <td  style="width: 100px;"><div  id="con_uplode" style="width: 80px;"><a href="javascript:uplode();">上传文件</a></div></td>
       <td colspan="6" style="width: 318px;"><div  id="con_file" style="width: 318px;"><a href="javascript:openFlie();">查看文件</a></div></td>
       </tr>
       
       <tr>
       <td class="label no-empty"   style="width: 100px;">参会人员：</td>
       <td style="width: 100px;"><select id="conp_personelid" style="width: 100px;"></select></td>
       <td colspan="6" style="width: 628px;"> <div id="conp_personelids" class="container" style="width: 628px;height:30px;overflow:scroll;" > </div></td>
       </tr>
       
        <tr>
       <td class="label"   style="width: 100px;">其他参会人员：</td>
      
       <td colspan="7" style="width: 728px;"> <textarea id="conp_otherpersonel" style="width: 728px;height: 30px" > </textarea></td>
       </tr>
       
       <tr>
       <td class="label"   style="width: 100px;">缺席人员：</td>
       <td style="width: 100px;"><select id="conp_personelidn" style="width: 100px;"></select></td>
       <td colspan="6" style="width: 628px;"> <div id="conp_personelidns" class="container" style="width: 628px;height:30px;overflow:scroll;" > </div></td>
       </tr>
       <tr>
       <td class="label no-empty"   style="width: 100px;">会议内容：</td>
       <td style="width: 735px;" colspan="7"><textarea id="con_content"  style="width: 735px;height: 150px"></textarea></td>      
       </tr>
       
       <tr>
       <td class="label"   style="width: 100px;">会议结论：</td>
       <td style="width: 735px;" colspan="7"><textarea id="con_conclusion" style="width: 735px;height: 100px"></textarea></td>      
       </tr>
       <tr>
       <td class="label"   style="width: 100px;display: none">con_id</td>
       <td style="width: 735px;display: none" colspan="7"><textarea id="con_id" style="width: 735px;height: 100px"></textarea></td>      
        </tr>
         </table>
        </div>
</div>
</body>

</html>


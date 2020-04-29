/**
* @author: wanghc
* @date:   2014-06-04
* @desc: 通过重写websys_ul方法, 实现放大镜与日期在当前界面显示.
* 往界面中写入id为dhcpopup的层, 存放放大镜与日期, Esc键隐藏层
**/
if ("undefined" !== typeof Ext){
	Ext.namespace('dhcc.icare');
	dhcc.icare.lu = {
		preSrcElementId:"",		//放大镜关联的input的id
		popupDiv : "zdhcpopup",
		lookUp: "websys.lookup.csp",
		lookUpDate: "websys.lookupdate.csp",
		defaultHeight: 90,		
		defaultWidth: 360,
		gridHeight:0,	//实现调整后grid的高度
		gridWidth:0,  //实现调整后grid的宽度
		dateHeight:210,
		dateWidth:180,
		maxHeight: 380,	//一页显示15行,是高度最高时		
		pageSize: 15,
		fontSize: 13
	};
	Ext.onReady(function(){
		var s ,html;
		var lookupDiv = document.createElement("div");
		lookupDiv.id = dhcc.icare.lu.popupDiv ;
		lookupDiv.style.display = "none";
		lookupDiv.style.zIndex = 12000;	
		lookupDiv.style.position = "absolute";
		lookupDiv.style.borderStyle = 'outset';
		//lookupDiv.style.borderWidth = '1px';
		lookupDiv.style.borderColor = '#a3bae9';
		lookupDiv.style.backgroundColor ='#ffffff';
		document.body.appendChild(lookupDiv);
		if(Ext.isIE6){
			var prop = function (n){return n&&n.constructor==Number?n+'px':n;}
			s = {top:'auto',left:'auto',width:'auto',height:'auto',opacity:true,src:'javascript:false;'};
			html= '<iframe class="bgiframe" frameborder="0" tabindex="-1" src="'+s.src+'"'+
						   ' style="display:block;position:absolute;z-index:-1;'+
							   (s.opacity !== false?'filter:Alpha(Opacity=\'0\');':'')+
							   'top:'+(s.top=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+\'px\')':prop(s.top))+';'+
							   'left:'+(s.left=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+\'px\')':prop(s.left))+';'+
							   'width:'+(s.width=='auto'?'expression(this.parentNode.offsetWidth+\'px\')':prop(s.width))+';'+
							   'height:'+(s.height=='auto'?'expression(this.parentNode.offsetHeight+\'px\')':prop(s.height))+';'+
						'"/>';
			lookupDiv.insertBefore(document.createElement(html));
		}
		
		var extStyle = document.createElement("style");
		extStyle.type = "text/css";
		extStyle.id = dhcc.icare.lu.popupDiv+"extStyle";
		//extStyle.innerHTML = "";
		document.body.appendChild(extStyle);
		extStyle="";
		Ext.EventManager.on(document.body,"keydown",function(e){
			var keycode = e.getKey();
			if (keycode==Ext.EventObject.ESC){ //esc 27
				clearlu();			
			}		
			if (dhcc.icare.lu.component && dhcc.icare.lu.component.bbar){			
				//grid
				var t = dhcc.icare.lu.component;
				var bottomBar = t.getBottomToolbar();
				if (keycode == Ext.EventObject.PAGE_DOWN){			
					if (bottomBar.getPageData().activePage< bottomBar.getPageData().pages){
						bottomBar.moveNext();
					}
				} else if (keycode == Ext.EventObject.PAGE_UP){
					if (bottomBar.getPageData().activePage>1){
						bottomBar.movePrevious();
					}
				} else if ((keycode == Ext.EventObject.NUM_PLUS)||(keycode == 187)){			
					if (bottomBar.getPageData().activePage< bottomBar.getPageData().pages){
						bottomBar.moveNext();
					}
				}else if ((keycode == Ext.EventObject.NUM_MINUS)||(keycode == 189)){
					if (bottomBar.getPageData().activePage>1){
						bottomBar.movePrevious();
					}
				}			
			}
		})
		Ext.EventManager.on(document.body,"mousedown",function(e){
			var height = Ext.getBody().getHeight() - 30;
			var width = Ext.getBody().getWidth() - 30;
			if (e.xy[1]>height)return ; //ie8下点滚动条
			if (e.xy[0]>width)return;
			if (e.target){
				if(e.target.id.indexOf(dhcc.icare.lu.preSrcElementId)>-1){
					return false;
				}
				var tmp = e.target;
				var maxTimes=20;	
				while(tmp){
					if(tmp.id && tmp.id==dhcc.icare.lu.popupDiv){return false;}
					if(tmp.tagName=="BODY"){
						clearlu();
						return false;
					}	
					tmp = tmp.parentElement;
					maxTimes--;
					if(maxTimes==1) return ;
				}
			}
		})
	});		
	
	/**
	* @param : {Dom | String}  srcElement 事件源 
	* @return : {Boolean} flag . true--能显示放大镜 . false--没有位置显示放大镜则用老的方式弹出放大镜
	*/
    vaildInputPosition = function (srcElement){
		if ("string"==typeof srcElement){ srcElement = document.getElementById(srcElement); }
		var bodyHeight = document.body.clientHeight;
		var bodyWidth = document.body.clientWidth;
		var height = dhcc.icare.lu.maxHeight; //defaultHeight;
		var width = dhcc.icare.lu.defaultWidth;
		if(dhcc.icare.lu.gridHeight>0){
			height = dhcc.icare.lu.gridHeight;
		}
		if(dhcc.icare.lu.gridWidth>0){
			width = dhcc.icare.lu.gridWidth;
		}
		var tmpWidth = srcElement.offsetWidth; 		
		var tmpHeight = srcElement.offsetHeight;
		var el = Ext.get(srcElement.id)
		var extxy = el.getAnchorXY();
		var tmpLeft = extxy[0] ;
		var tmpTop = extxy[1]+tmpHeight;
		//下面不能显示,但上面也不能显示
		/// 36 232 height=180
		if (((bodyHeight-tmpTop) < height) && ( extxy[1] < height )){
			return false;
		}
		return true;
	};	
	vaildDInputPosition = function (srcElement){
		if ("string"==typeof srcElement){ srcElement = document.getElementById(srcElement); }
		var bodyHeight = document.body.clientHeight;
		var bodyWidth = document.body.clientWidth;
		var height = dhcc.icare.lu.dateHeight; //defaultHeight;
		var width = dhcc.icare.lu.dateWidth;
		var tmpWidth = srcElement.offsetWidth; 		
		var tmpHeight = srcElement.offsetHeight;
		var el = Ext.get(srcElement.id)
		var extxy = el.getAnchorXY();
		var tmpLeft = extxy[0] ;
		var tmpTop = extxy[1]+tmpHeight;
		//下面不能显示,但上面也不能显示
		/// 36 232 height=180
		if (((bodyHeight-tmpTop) < height) && ( extxy[1] < height )){
			return false;
		}
		return true;
	};	
	/**
	*@param: {Dom} container
	*@param: {Dom} srcElement 放大镜输入框
	*@desc: 把容器移动到输入框下面或上面  	
	*/
	setPosition = function(container, srcElement){
		if ("string"==typeof container){ container = document.getElementById(container); }
		if ("string"==typeof srcElement){ srcElement = document.getElementById(srcElement); }
		var bodyHeight = document.body.clientHeight;
		var bodyWidth = document.body.clientWidth;
		
		var height = dhcc.icare.lu.defaultHeight;
		var width = dhcc.icare.lu.defaultWidth;
		if(dhcc.icare.lu.gridHeight>0){
			height = dhcc.icare.lu.gridHeight;
		}
		if(dhcc.icare.lu.gridWidth>0){
			width = dhcc.icare.lu.gridWidth;
		}
		//alert("setPosition, dhcc.icare.lu.gridHeight="+dhcc.icare.lu.gridHeight);
		var tmpWidth = srcElement.offsetWidth; 		
		var tmpHeight = srcElement.offsetHeight;
		var el = Ext.get(srcElement.id)
		var extxy = el.getAnchorXY();
		var tmpLeft = extxy[0] ;
		var tmpTop = extxy[1]+tmpHeight;
		
		if (((bodyHeight-tmpTop) < height) && ( (tmpTop-tmpHeight) > height )){					//下面不能显示,但上面能显示
			tmpTop = tmpTop  - tmpHeight - height - 3;
		}
		if ( ((bodyWidth-tmpLeft)<width) && ((tmpLeft+tmpWidth)>width) ){	//右边不能显示,但左边能显示			
			tmpLeft = bodyWidth - width - (bodyWidth-tmpLeft-tmpWidth);		//减放大镜的宽度,再减文本框右边空白宽度
		}
		//container.style.pixelTop = tmpTop ; 
		//container.style.pixelLeft = tmpLeft ;
		container.style.top = tmpTop + "px";
		container.style.left = tmpLeft + "px";		
		container.style.borderWidth = '1px';
		container.style.display = "";
	};
	/**
	*@param: {Dom} container
	*@param: {Dom} srcElement 放大镜输入框
	*@desc: 把容器移动到输入框下面或上面  	
	*/
	setDPosition = function(container, srcElement){
		if ("string"==typeof container){ container = document.getElementById(container); }
		if ("string"==typeof srcElement){ srcElement = document.getElementById(srcElement); }
		var bodyHeight = document.body.clientHeight;
		var bodyWidth = document.body.clientWidth;
		var height = dhcc.icare.lu.dateHeight;
		var width = dhcc.icare.lu.dateWidth;
		var tmpWidth = srcElement.offsetWidth; 		
		var tmpHeight = srcElement.offsetHeight;
		var el = Ext.get(srcElement.id)
		var extxy = el.getAnchorXY();
		var tmpLeft = extxy[0] ;
		var tmpTop = extxy[1]+tmpHeight;
		
		if (((bodyHeight-tmpTop) < height) && ( (tmpTop-tmpHeight) > height )){					//下面不能显示,但上面能显示
			tmpTop = tmpTop  - tmpHeight - height - 3;
		}
		if ( ((bodyWidth-tmpLeft)<width) && ((tmpLeft+tmpWidth)>width) ){	//右边不能显示,但左边能显示			
			tmpLeft = bodyWidth - width - (bodyWidth-tmpLeft-tmpWidth);		//减放大镜的宽度,再减文本框右边空白宽度
		}
		//container.style.pixelTop = tmpTop ; 
		//container.style.pixelLeft = tmpLeft ;
		container.style.top = tmpTop + "px";
		container.style.left = tmpLeft + "px";		
		container.style.borderWidth = '1px';
		container.style.display = "";
	};
	clearlu = function(){
		var container = document.getElementById(dhcc.icare.lu.popupDiv);
		container.style.borderWidth = '0px';
		container.style.display = "none";
		container.innerHTML = "";
		if (dhcc.icare.lu.component) {
			dhcc.icare.lu.component.destroy();
			delete dhcc.icare.lu.component;
			dhcc.icare.lu.gridHeight = 0;
			dhcc.icare.lu.gridWidth = 0;
		}
		if(Ext.isIE) CollectGarbage();
	};
	dhcc.icare.lu.createLookup = function(url,srcElement){
		//是否是查询参数查询的结果
		var findByParamFlag = false;
		Ext.QuickTips.init();
		var cspNameLen = url.indexOf(dhcc.icare.lu.lookUp) + dhcc.icare.lu.lookUp.length;
		if (cspNameLen>-1){url = url.slice(cspNameLen+1);}
		var p = Ext.urlDecode(url);
		var myCONTEXT = p["CONTEXT"];
		if (('undefined' == typeof myCONTEXT) && (myCONTEXT=="")) return false;
		var mycontextary = myCONTEXT.split(":");
		if (mycontextary.length !=2 ) return false;
		p.rnd = Math.random();
		p.pClassName = mycontextary[0].substring(1);
		p.pClassQuery = mycontextary[1];
		p.resizeColumn = 1;
		delete p.CONTEXT;		
		var mymodalstr = tkMakeServerCall("ext.websys.QueryBroker", "ReadRSNew2", p.pClassName, p.pClassQuery);		
		var json = Ext.decode(mymodalstr);		
		var cm = new Ext.grid.ColumnModel({columns : json.cms, defaults:{ sortable: false, menuDisabled: true }});
		dhcc.icare.lu.pageSize = json.pageSize || dhcc.icare.lu.pageSize;
		var styleObj = document.getElementById(dhcc.icare.lu.popupDiv+"extStyle");
		if(json.fontSize){
			var bubbleCssStyle = ".x-grid3-row td, .x-grid3-summary-row td {font: normal "+json.fontSize+"px arial, tahoma, helvetica, sans-serif;} .x-grid3-hd-row td {font: normal "+json.fontSize+"px arial, tahoma, helvetica, sans-serif;}";
			if (Ext.isIE8 || Ext.isIE7 || Ext.isIE6){
				//window.bc_bubble_css = bubbleCssStyle;
				var styleSheetLast = document.styleSheets[document.styleSheets.length-1];
				/*if(styleSheetLast.cssRules && styleSheetLast.cssRules.length==2){
					styleSheetLast.deleteRule(0);
					styleSheetLast.deleteRule(1);
				}*/
				if (styleSheetLast.cssText){
					styleSheetLast.cssText="";
				}
				styleSheetLast.addRule(".x-grid3-row td","font: normal "+json.fontSize+"px arial, tahoma, helvetica, sans-serif;",0);
				styleSheetLast.addRule(".x-grid3-hd-row td","font: normal "+json.fontSize+"px arial, tahoma, helvetica, sans-serif;",1);
				//document.createStyleSheet("javascript:bc_bubble_css");
			}else{
				styleObj.innerHTML = bubbleCssStyle;
			}
		}
		var mycmurl = "ext.websys.querydatatrans.csp";
		var resizeColumnFun = function(s,rs,obj){
			if (obj.params["resizeColumn"] != 1) return false;
			var gridWidth,gridHeight,cm,objWidth = {};			
			gridWidth = 0;
			cm = grid.colModel ;
			for(var cm_i=0; cm_i<cm.config.length; cm_i++){
				if (cm.config[cm_i].hidden) continue;
				objWidth.key = cm.config[cm_i].dataIndex;				
				objWidth.val = cm.config[cm_i].width;				
				//objWidth.val = 60 ;
				for(var rs_i=0;rs_i<rs.length;rs_i++){					
					var len = rs[rs_i].get(objWidth.key).toString().trim().replace(/[^\x00-\xff]/g,"**").length * 7;
					if (len > objWidth.val){
						objWidth.val = len ;
					}
				}				
				cm.config[cm_i].width = objWidth.val;
				gridWidth += objWidth.val;
			}
			//grid的高与宽
			//40为滚动条宽
			if (dhcc.icare.lu.gridWidth<=0){
				dhcc.icare.lu.gridWidth = (gridWidth+40<dhcc.icare.lu.defaultWidth)?dhcc.icare.lu.defaultWidth:(gridWidth+40);
			}
			cm.totalWidth = dhcc.icare.lu.gridWidth-38; //控制行 x-grid3-row的背景宽度
			gridHeight = rs.length*25 + 60;
			
			if(dhcc.icare.lu.gridHeight<=0){
				//alert("dhcc.icare.lu.gridHeight="+dhcc.icare.lu.gridHeight+",进入<=0");
				dhcc.icare.lu.gridHeight = (gridHeight < dhcc.icare.lu.defaultHeight)?dhcc.icare.lu.defaultHeight:((gridHeight > dhcc.icare.lu.maxHeight)?dhcc.icare.lu.maxHeight:gridHeight);	
			}	
			//console.log("宽="+dhcc.icare.lu.gridWidth+",高="+dhcc.icare.lu.gridHeight);
			grid.setWidth(dhcc.icare.lu.gridWidth);
			//alert("grid.setheight="+dhcc.icare.lu.gridHeight);
			grid.setHeight(dhcc.icare.lu.gridHeight);
			
			setPosition(dhcc.icare.lu.popupDiv,srcElement);
			grid.reconfigure(grid.store, cm);
			grid.store.baseParams.resizeColumn = 0;				
		};    
		var storeLoaded = function(obj,records,options){
			// ds.onload  加载数据后触发事件，重新计算列宽
			resizeColumnFun(obj,records,options);		//wanghc20161214--gridHeight根椐行数变化	
			if (!p["TBAR"] || !findByParamFlag){
				//// 第一次打开放大镜要把焦点放到grid上
				grid.getSelectionModel().selectFirstRow();
				grid.getView().focusRow(0);
			}
			/*
			var container = document.getElementById(dhcc.icare.lu.popupDiv);
			var srcElement = document.getElementById(dhcc.icare.lu.preSrcElementId);
			dhcc.icare.lu.gridHeight = 100;
			*/
			//setPosition(container,srcElement); 			
		};
		var ds = new Ext.data.JsonStore({
			url: mycmurl,
			baseParams: p,
			root: "record",
			totalProperty: "total",
			fields: json.fns ,
			listeners:{'load': storeLoaded}
		});
		var pagingBar = new Ext.PagingToolbar({
			pageSize: parseInt(dhcc.icare.lu.pageSize),
			store: ds,
			items:['-',{text:'关闭',handler:clearlu}],
			displayInfo: true,
			displayMsg: '{0}-{1},共{2}条'
		});
		var myloadM;
		if (Ext.isIE){
			myloadM = new Ext.LoadMask(document.getElementById(dhcc.icare.lu.popupDiv), {msg: "正在加载数据...",store:ds});
		} else {
			myloadM = true;
		}		
		var isSelected = 0;
		var myjsf = p["TLUJSF"];
		var GridRowSelect = function(firstCol, allCol) {
			try {
				eval(srcElement.id + "_lookupsel('" + firstCol + "')");
			} catch (err) {
				// 如果是列表中有放大镜,这个srcElement.id应该为 colnamez123 -->colname_lookupsel()
				var tmpArr = srcElement.id.match(/(.+)z[0-9]+/);
				if (tmpArr && tmpArr.length>1){
					try{
						eval(tmpArr[1] + "_lookupsel('" + firstCol + "')");
					}catch(err){}
				}
			}
			if (typeof myjsf !="undefined" && myjsf != "") {
				try {
					eval(myjsf + "('" + allCol + "')");
				} catch (err) {}
			}
			isSelected = 1;
			grid.destroy();			
			clearlu();
			RegainFocus();
		};
		var RegainFocus = function() {
			if (!isSelected) {
				try {
					var obj = document.getElementById(dhcc.icare.lu.preSrcElementId);
					websys_setfocus(obj);
				} catch (e) {};
			}
		};
		var selectRow = function(grid,rowIndex,e) {
			var tmpRecord = grid.getSelectionModel().getSelected();
			if (!!tmpRecord){
				var myColAry = "";
				for (var myIdx = 0; myIdx < json.fns.length; myIdx++) {
					if (myIdx==0){
						myColAry = tmpRecord.get(json.fns[myIdx].name);
					}else{
						myColAry = myColAry+"^"+ tmpRecord.get(json.fns[myIdx].name)
					}					
				}
				GridRowSelect(tmpRecord.get(json.cms[0].dataIndex), myColAry);
			}
		}
		var cmslen = json.cms.length;
		var displayFieldCmCount = 0;
		for(var i = 0 ; i < cmslen ; i++){
			if(!json.cms[i].hidden){		    	   			   
				displayFieldCmCount++;
			}
		}
		var tmpWidth = (((displayFieldCmCount*140) > document.body.clientWidth) ? document.body.clientWidth : displayFieldCmCount*140);
		if(tmpWidth < dhcc.icare.lu.defaultWidth) tmpWidth=dhcc.icare.lu.defaultWidth;
		var NextRow = function(g){
			var record = g.getSelectionModel().getSelected();
			var rowIndex=g.store.indexOf(record)
			g.getSelectionModel().selectRow(rowIndex+1)
			var view=g.getView()
			var firstRow = Ext.get(view.getRow(0));  
			var count=g.store.getCount()
			if(rowIndex+1==count) return 
			var row = Ext.get(view.getRow(rowIndex+1));  
			var distance = row.getOffsetsTo(firstRow)[1];
			if(distance>(g.height-80)){
				view.scroller.dom.scrollTop = distance-(this.height-80);  
			}
		}
		var PreRow = function(g){
			var record = g.getSelectionModel().getSelected();
			var rowIndex=g.store.indexOf(record)
			if(rowIndex==0) return 
			g.getSelectionModel().selectRow(rowIndex-1)
			var view=g.getView()
			var count=g.store.getCount()
			var lastRow = Ext.get(view.getRow(count-1));  
			var row = Ext.get(view.getRow(rowIndex-1));  
			var distance = lastRow.getOffsetsTo(row)[1];
			view.scroller.dom.scrollTop = view.scroller.dom.scrollTop-(distance-(this.height-80));  
		}
		/*find by param --- */
		var selectRowAuto = function(){
			var record = grid.getSelectionModel().getSelected();
			if (grid.store && !!record){
				selectRow(grid, grid.store.indexOf(record));
			}
		}
		var findByParam = function(e){
			var itemdescArr = p["TBAR"].split(",");
			for (var i =0; i<itemdescArr.length; i++){
				var tmpdescArr = itemdescArr[i].split(":");
				grid.store.baseParams[tmpdescArr[1]] = Ext.getCmp(tmpdescArr[1]+"TTF").getValue();
				//console.log(tmpdescArr[1]+","+Ext.getCmp(tmpdescArr[1]+"TTF").getValue());
			}
			grid.store.load();
			findByParamFlag = true;
		};
		var dqTask="";
		if (!!p["TBAR"]){
           dqTask = new Ext.util.DelayedTask(findByParam, window);
        }
		if (!!p["TBAR"]){
			//科室:P3,医院:P2
			var itemdescArr = p["TBAR"].split(",");
			var tmpobj=[];
			for (var i =0; i<itemdescArr.length; i++){
				var tmpdescArr = itemdescArr[i].split(":");
				tmpobj.push({xtype: 'tbtext',text:tmpdescArr[0]+":",width:55})
				tmpobj.push(new Ext.form.TwinTriggerField({
						height:25,
						id:tmpdescArr[1]+"TTF",
						trigger1Class: 'x-form-clear-trigger',
						trigger2Class: 'x-form-search-trigger',
						onTrigger1Click: function(e){this.setValue("");}, 
						onTrigger2Click: function(e){
							findByParam();
						},
						enableKeyEvents: true,
						listeners:{
							"keydown":function(t,e){
								if([Ext.EventObject.PAGE_UP,Ext.EventObject.PAGE_DOWN,Ext.EventObject.UP,Ext.EventObject.PAGE_DOWN].indexOf(e.getKey())>-1){
									/*cancel bubble*/
									e.stopEvent();
									e.preventDefault();
									e.stopPropagation();
								}
								if (e.getKey()==Ext.EventObject.DOWN){
									NextRow(grid);
								}else if (e.getKey() == Ext.EventObject.UP){
									PreRow(grid);
								}else if(e.getKey() == Ext.EventObject.LEFT){
			
								}else if(e.getKey() == Ext.EventObject.RIGHT){
			
								}else if (e.getKey() == Ext.EventObject.PAGE_DOWN){	
										
								}else if (e.getKey() == Ext.EventObject.PAGE_UP){
									
								}else if(e.getKey() == e.ENTER){
									selectRowAuto();
								}else if((e.getKey()==e.BACKSPACE)||(e.getKey()==e.DELETE)||(e.getKey()>=65 && e.getKey()<=90)||(e.getKey()>=48 && e.getKey()<=57)||(e.getKey()>=96 && e.getKey()<=122)) {
									dqTask.delay(300);
								}
								/*if(e.getKey() == e.ENTER){
									findByParam();
								}*/
							}
						}
					}));
				tmpobj.push("-");
			}
			var toolbar = new Ext.Toolbar({
				items: tmpobj
			});	
		}
		var grid = new Ext.grid.GridPanel({	
			tbar: toolbar,		
			title: "", split: true, border: false,width: tmpWidth, height: dhcc.icare.lu.defaultHeight,cm: cm, ds: ds,
			loadMask: myloadM, stripeRows: true,bbar: pagingBar,
			listeners:{
				'headerdblclick':function(){
						var flag = tkMakeServerCall("web.SSGroup","GetAllowWebColumnManager",session['LOGON.GROUPID']);
						if(flag==1) websys_lu('../csp/websys.component.customiselayout.csp?ID=1872&CONTEXT=K'+p.pClassName+':'+p.pClassQuery+"&DHCICARE=2",false);	//	
					},
				'rowclick': selectRow,
				'keydown': function(e) {
					if (e.keyCode == Ext.EventObject.ENTER) {						
						var record = this.getSelectionModel().getSelected();
						selectRow(this, this.store.indexOf(record),e);
					}
				},
				'afterrender': function(t){
					t.store.load({params: {start: 0, limit: dhcc.icare.lu.pageSize}});
				}
			}
		});	
		//clearlu();
		grid.render(dhcc.icare.lu.popupDiv);
		return grid;
	};
	dhcc.icare.lu.createLookupDate = function(url,srcElement){
		var formate ="d/m/Y" ;
		if ((typeof dtformat!="undefined") && (typeof dtseparator!="undefined")){
			if ((dtformat=="YMD")&&(dtseparator=="-")){
				formate ="Y-m-d"
			}
			if ((dtformat=="DMY")&&(dtseparator=="/")){
				formate ="d/m/Y"
			}
		}
		return new Ext.DatePicker({
			dateSrcElementId:srcElement.id,				
			value: Date.parseDate(srcElement.value,formate),
			renderTo:dhcc.icare.lu.popupDiv,					
			listeners:{
				'select':function(t,date){
					var obj=document.getElementById(this.dateSrcElementId)
					if (obj){
						obj.value = date.format(formate);
						if(document.createEventObject){
							var evt = document.createEventObject();
							if (obj) obj.fireEvent("onchange",evt);
						}else{
							var evt = document.createEvent("HTMLEvents");
							evt.initEvent('change', true, true);
							if(obj) obj.dispatchEvent(evt);
						}
					}
					t.hide(); 
					t.destroy();
					clearlu();
				}
			}
		});
	};
	websys_lubak = websys_lu;
	/*url: {
		"LookupID":"SSGRPDesc",
		"CONTEXT":"Kweb.SSGroup:LookUpPartial",
		"TLUDESC":"%E5%AE%89%E5%85%A8%E7%BB%84",
		"TLUJSF":"GroupHanlder",
		"P1":"",
		"P2":""
	},*/
	/*
	@param {String} url 
	///http://127.0.0.1/dthealth/web/csp/websys.lookup.csp?ID=d1434iSSGRPDesc&CONTEXT=Kweb.SSGroup:LookUpPartial&TLUDESC=%E5%AE%89%E5%85%A8%E7%BB%84&TLUJSF=GroupHanlder&P1=&P2=
	///http://127.0.0.1/dthealth/web/csp/websys.lookupdate.csp?ID=DateOfBirth&TLUDESC=%B3%F6%C9%FA%C8%D5%C6%DA&STARTVAL=&DATEVAL=
	//column-->websys.lookup.csp?ID=d54369inamelu&CONTEXT=Kweb.CTPayMode:LookUp&TLUDESC=namelu&TBLI=nameluz1
	// 2017-1-13增加查询条件框 ,TBAR=科室:P3,医院:P2---生成科室与医院查询条件, 即时查询
	
	@param {Int}  islookup  1表示是走Div放大镜,其它表示走老的websys_lu方法
	@param {Object} posn  {top:1,left:1,width:100,height:100} 控制放大镜大小
	
	*/
	websys_lu = function(url,islookup,posn){
		var container = document.getElementById(dhcc.icare.lu.popupDiv);			
		var ludFlagIndex = url.indexOf(dhcc.icare.lu.lookUpDate);
		var luFlagIndex = url.indexOf(dhcc.icare.lu.lookUp);
		var srcElement = null,srcElementId = "";	
		if (islookup===1){ //true==1 --> true
			
			if (url.indexOf("?ID=") == -1) return false;			
			if (url.indexOf("TBLI=") > -1){
				//在tableitem中的日期
				srcElementId = url.slice(url.indexOf("TBLI=")+5).split('&')[0];
			}else{
				srcElementId = url.slice(url.indexOf("?ID=")+4).split('&')[0];
			}
			//console.log(srcElementId);
			srcElement =  document.getElementById(srcElementId);
			if (ludFlagIndex > -1) {				
				if ((dhcc.icare.lu.preSrcElementId==srcElementId)&&(container.style.display != "none")) return false;
				if (srcElement) { 
					clearlu();
					setDPosition(container,srcElement); 					
					dhcc.icare.lu.component = dhcc.icare.lu.createLookupDate(url,srcElement); 
					//container.style.width = 183;
				}
			}else if (luFlagIndex > -1){
				if(!srcElement){ //放大镜ID入参有时为d22222iLookup					
					srcElementId = srcElementId.slice(srcElementId.indexOf('i')+1);			
					srcElement = document.getElementById(srcElementId);
				}
				if(!srcElement){ //自己找源事件框
					srcElement = websys_getSrcElement();
					srcElementId = srcElement.id;
				}
				//alert(srcElementId);
				//if ((dhcc.icare.lu.preSrcElementId==srcElementId)&&(container.style.display != "none")) return false;
				if (srcElement) {
					clearlu();
					
					if (posn && posn.height && posn.width) {
						dhcc.icare.lu.gridHeight = posn.height;
						dhcc.icare.lu.gridWidth = posn.width;
					}
					//alert(dhcc.icare.lu.gridHeight);
					setPosition(container,srcElement); 
					try{
						dhcc.icare.lu.component = dhcc.icare.lu.createLookup(url,srcElement); 
					}catch(e){
						alert("生成放大镜失败! \n" + e.message);
						return false;
					}
					//container.style.width = dhcc.icare.lu.component.getWidth();
					//alert(" wanghc "+dhcc.icare.lu.component.getWidth());
				}
			}							
			if (srcElement) dhcc.icare.lu.preSrcElementId = srcElement.id;
			return false;	
		}	
		websys_lubak(url, islookup, posn);	
	};
}
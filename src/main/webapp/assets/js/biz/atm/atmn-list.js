$(document).ready(function(){
	    var map;//map全局对象
	    var start;//出发地
	    var startInputId;//出发地输入框的ID
	    var end;//目的地
	    var localAddr;//自己的当前地址
	    var inde;//下标 根据他来获取目的地
	    var infoWindows=new Array();//记录所有的弹出窗，点击 详情的时候使用
	    var longitudeMe=null;//定位的我的经度
	    var latitudeMe=null;//定位的我的纬度
        initMap();//创建和初始化地图
        //创建和初始化地图函数：
       function initMap(){
           createMap();//创建地图
       }      
       //创建地图函数：
       function createMap(){          
       map = new BMap.Map("allmap");
   	   var point = new BMap.Point(113.688284,34.784482);//定义一个中心点坐标
   	   map.centerAndZoom(point,15);//设定地图的中心点和坐标并将地图显示在地图容器中 15位放大倍数 最大18
       map.enableScrollWheelZoom();                 //启用滚轮放大缩小
	   map.addControl(new BMap.NavigationControl());
	   map.addControl(new BMap.ScaleControl());
	   map.addControl(new BMap.OverviewMapControl());
   	   //定位开始
   	  var geolocation = new BMap.Geolocation();
   	  geolocation.getCurrentPosition(function(r){
   		if(this.getStatus() == BMAP_STATUS_SUCCESS){
   			var mk = new BMap.Marker(r.point);
   			var iconImg = contextPath +"/assets/img/bluePoint.png";
   			var icon = new BMap.Icon(iconImg, new BMap.Size(30, 30));
   			mk.setIcon(icon);
   			map.addOverlay(mk);
   		    mk.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
   		    mk.addEventListener("click", function () {    //监听标注事件  
   		        var opts = {    //创建信息窗口
   		            width: 250,     // 信息窗口宽度    
   		            height: 100,     // 信息窗口高度    
   		            title: '您的位置'  // 信息窗口标题   
   		        }
   		        var infoWindow = new BMap.InfoWindow(null, opts);  // 创建信息窗口对象    
   		        map.openInfoWindow(infoWindow, point);      //打开信息窗口	
   		    });
   			map.panTo(r.point);
   			var geoc = new BMap.Geocoder();          
   			var pt = r.point;
   			geoc.getLocation(pt, function(rs){
   			var addComp = rs.addressComponents;
   			localAddr=addComp.province + addComp.city  + addComp.district +  addComp.street +  addComp.streetNumber;
   			alert('你的实际地址'+addComp.province + addComp.city  + addComp.district +  addComp.street +  addComp.streetNumber);
   			});
   			alert('您的位置经纬度：'+r.point.lng+','+r.point.lat);
   			longitudeMe=r.point.lng;
   			latitudeMe=r.point.lat;
   			getList();
   		  //关键字搜索开始 定位成功后在附近搜索关键字
//   		  var myKeys = ["邮储银行 ATM", "邮储"];
//   	      var local = new BMap.LocalSearch(map, {
//   		      renderOptions:{map: map, panel:"r-result"},
//   		      pageCapacity:5
//   	       });
//   	      local.searchInBounds(myKeys, map.getBounds());
   		  //关键字搜索结束
   		}
   		else {
   			alert('failed'+this.getStatus());
   		}        
   	},{enableHighAccuracy: true})
   		//定位结束

   	//关于状态码
   	//BMAP_STATUS_SUCCESS	检索成功。对应数值“0”。
   	//BMAP_STATUS_CITY_LIST	城市列表。对应数值“1”。
   	//BMAP_STATUS_UNKNOWN_LOCATION	位置结果未知。对应数值“2”。
   	//BMAP_STATUS_UNKNOWN_ROUTE	导航结果未知。对应数值“3”。
   	//BMAP_STATUS_INVALID_KEY	非法密钥。对应数值“4”。
   	//BMAP_STATUS_INVALID_REQUEST	非法请求。对应数值“5”。
   	//BMAP_STATUS_PERMISSION_DENIED	没有权限。对应数值“6”。(自 1.1 新增)
   	//BMAP_STATUS_SERVICE_UNAVAILABLE	服务不可用。对应数值“7”。(自 1.1 新增)
   	//BMAP_STATUS_TIMEOUT	超时。对应数值“8”。(自 1.1 新增)        
         //window.map = map;//将map变量存储在全局
       }
   	// 编写自定义函数,创建标注 til信息窗的title add是地址 Tel电话
   	function addMarker(point,infoWindow) {
   		
   		var marker = new BMap.Marker(point);
   	    map.addOverlay(marker);
   	    //当点击 标注的时候弹出信息窗
   	    marker.addEventListener("click", function () {    //监听标注事件  mouseover
        map.openInfoWindow(infoWindow, point);      //打开信息窗口,然后定义事件,因为此时窗口的html元素才会生成 此时定义事件才可以	
        routePlan()			
   	    });
   	}

	//列表数据查询
	function getList(){
		var url = contextPath + "/biz/atm/getNList";
		//异步请求理财产品管理列表数据
		Util.ajax.postJson(url, null, function(data, flag){
			var source = $("#list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#listTable").html(htmlStr);
				var arr=data.beans;
				for(var i=0;i<arr.length;i++){
					var point = new BMap.Point(arr[i].longitude,arr[i].latitude);
					var title="<div style='color: #CC5522;font-size: 14px;font-weight: bold;white-space:nowrap;' id='endAddr"+i+"'>"+arr[i].orgShtName+"</div>"
			   	    var inf="<div style='font: 12px arial,sans-serif;line-height: 160%;'><p>地址："+arr[i].address+"</p><p>电话："+arr[i].tactorPhone+"</p></div>"
			   	    inf=inf+"<div style='color: #CC5522;font-size: 14px;font-weight: bold;white-space:nowrap;'><p>到这去：</p></div>"
			   	    inf=inf+'<div style="padding: 10px 5px 0px;"><span style="margin-right: 5px;">起点</span><input type="text" placeholder="默认为你当前位置" class="addr" id="from-addr'+i+'" autocomplete="off" style="height: 22px; line-height: 22px; padding: 0px; margin: 0px; border: 1px solid rgb(165, 172, 178); width: 125px;"><input type="button" class="bus" inde='+i+' value="公交" style="border: 0px; width: 47px; height: 25px; line-height: 25px; margin: 0px 0px 0px 5px; vertical-align: bottom; background: url(&quot;http://api0.map.bdimg.com/images/iw_bg.png&quot;) 0px -87px repeat-x;"><input type="button" class="driver" inde='+i+' value="驾车" style="border: 0px; width: 47px; height: 25px; line-height: 25px; margin: 0px 0px 0px 5px; vertical-align: bottom; background: url(&quot;http://api0.map.bdimg.com/images/iw_bg.png&quot;) 0px -87px repeat-x;"><input type="button" class="walk" inde='+i+' value="步行" style="border: 0px; width: 47px; height: 25px; line-height: 25px; margin: 0px 0px 0px 5px; vertical-align: bottom; background: url(&quot;http://api0.map.bdimg.com/images/iw_bg.png&quot;) 0px -87px repeat-x;"></div>';	
					
		   	        var opts = {    //创建信息窗口
			   	            width: 350,     // 信息窗口宽度    
			   	            height: 160,     // 信息窗口高度  
			   	            title: title  // 信息窗口标题   
			   	        }
					 var infoWindow= new BMap.InfoWindow(inf, opts);  // 创建信息窗口对象  
			   	     infoWindows.push(infoWindow);
					 addMarker(point,infoWindow);
				}	
				//当点击详情的时候打开信息窗
				$(".showAtmInfo").click(function(){
			            var inde=parseInt($(this).attr("inde"));
			   	        map.openInfoWindow(infoWindows[inde], point);      //打开信息窗口,然后定义事件,因为此时窗口的html元素才会生成 此时定义事件才可以	
			   	        routePlan();
				});

			}
		});
	}
   //该方法是根据输入内容，显示出输入提示,然后将选择的结果赋写到input框里
	function getResult(map,id){
		var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
				{"input" : id
				,"location" : map
			});

			ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
			var str = "";
				var _value = e.fromitem.value;
				var value = "";
				if (e.fromitem.index > -1) {
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
				
				value = "";
				if (e.toitem.index > -1) {
					_value = e.toitem.value;
					value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
				}    
				str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
			});

			var myValue;
			ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
			var _value = e.item.value;
				myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			});
	}

	//路线规划 需要的方法
	function routePlan(){
		   //输入框执行的方法
        $(".addr").click(function(){
			startInputId=$(this).attr("id");
			getResult(map,startInputId);
		});
		//公交执行的方法
		$(".bus").click(function(){
			inde=$(this).attr("inde");
			start=$("#"+startInputId).val();
			if(start==null || $.trim(start)==''){//如果不输入出发地默认是定位的位置
				start=localAddr;
			}
			end=$("#endAddr"+inde).text();
			console.log(start+","+end);
			//最少时间;最少换乘;最少步行;不乘地铁
			var routePolicy = [BMAP_TRANSIT_POLICY_LEAST_TIME,BMAP_TRANSIT_POLICY_LEAST_TRANSFER,BMAP_TRANSIT_POLICY_LEAST_WALKING,BMAP_TRANSIT_POLICY_AVOID_SUBWAYS];
			var transit = new BMap.TransitRoute(map, {
					renderOptions: {map: map,
					panel: "r-result", 
					autoViewport: true
			},
			policy:0
			});
			map.clearOverlays(); //清空map上的信息
			search(start,end,routePolicy[0]); 
			function search(start,end,route){ 
				transit.setPolicy(route);
				transit.search(start,end);
			}
		});
		
		//驾车
		$(".driver").click(function(){
			inde=$(this).attr("inde");
			start=$("#"+startInputId).val();
			if(start==null || $.trim(start)==''){//如果不输入出发地默认是定位的位置
				start=localAddr;
			}
			end=$("#endAddr"+inde).text();
			console.log(start+","+end);
			var drive = new BMap.DrivingRoute(map, {
					renderOptions: {map: map,
					panel: "r-result", 
					autoViewport: true
			}
			});  
			drive.search(start,end);
		});
		
		//步行
		$(".walk").click(function(){
			inde=$(this).attr("inde");
			start=$("#"+startInputId).val();
			if(start==null || $.trim(start)==''){//如果不输入出发地默认是定位的位置
				start=localAddr;
			}
			end=$("#endAddr"+inde).text();
			console.log(start+","+end);
			var walk = new BMap.WalkingRoute(map, {
					renderOptions: {map: map,
					panel: "r-result", 
					autoViewport: true
			}
			});  
			walk.search(start,end);
		});
	}
});




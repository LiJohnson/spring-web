//box
(function(){
	var Box = function(opt , cb){
		var html = '<div class="modal fade">	<div class="modal-dialog">	<div class="modal-content">		<div class="modal-header">		<button type="button" class="close">&times;</button>		<h4 class="modal-title"></h4>		</div>		<div class="modal-body">		</div>		<div class="modal-footer">		</div>	</div>	</div></div>';
		var $box = $(html);
		var defaultOption = {title:'消息',buttons:[]};
		var _this = this;
		if( typeof opt == 'string' ){
				opt = {message:opt,close:cb};
		}

		opt = $.extend(defaultOption,opt);
		opt.html = opt.html || opt.message ;

		opt.close && opt.buttons.push({name:opt.closeName || '关闭',click:opt.close});
		opt.ok && opt.buttons.push({name: opt.okName || '确定',click:opt.ok});
		opt.cancel && opt.buttons.push({name: opt.cancelName || '取消',click:opt.cancel});

		$.each(opt.buttons,function(i,btn){
			$box.find('.modal-footer').append( $("<a class='btn btn-default btn-xs'></a>").html(btn.name).click(function(e){
				var result = btn.click.call(this,e);
				if( result === false )return;
				_this.hide();
			}));
		});
		
		$box.hide().appendTo("body");
		$box.find(".modal-header,.modal-footer").css("padding","5px");
		$box.on("hidden.bs.modal",function(){
			$box.remove()
		});
		$box.on("shown.bs.modal",function(){
			$.fn.drag && $box.drag({handle:$box.find(".modal-header")}).attr("style","position: fixed;display: block;");
		});
		//
		
		this.show = function(html){
			$box.find(".modal-body").html(html);
			$box.find(".modal-title").html(opt.title);
			$box.modal('show');
		};

		this.hide = function(){
			$box.modal('hide');
		};
		this.html = function(){return $box};
		
		$box.find(".close").click(function(){
			_this.hide();
		});
		this.show(opt.html);
		
	};
	
	$.box = function(opt,cb){
		return new Box(opt,cb).html();
	};
})();


(function(){
	$.json = {
		toObject:function(str){
			try{
				return JSON.parse(str);
			}catch (e) {
				return false;
			}
		},
		toString:function(obj){
			try{
				return JSON.stringify(obj);
			}catch (e) {
				return "";
			}
		}
	};
})();

//地区信息jquery工具
(function($){
	if(!$)return;
	var localData = {};
	var getAreaData = window.cc = function( id , callback ){
		callback = callback || function(){};
		if( localData[id] ){
			callback( localData[id] );
		}else{
			$.post($.frontPath+"/area.json",{parentId:id},function(data){
				localData[id] = data && data.data;
				callback( localData[id] );
			});
		}
	};

	var updateSelect = function($select , areaData){
		$select.empty();
		$.each(areaData||[],function(i,area){
			$select.append("<option value='"+area.areaId+"'>"+area.name+"</option>");
		});
		return $select;
	};

	$.fn.areaSelect = function( areaId ){
		if(this.length == 0)return this;
		var setSelectVal = function($sel ,val){ try{ $sel.val(val);  }catch (e) {};return $sel;};
		this.each(function(){
			var $this = $(this);
			var $prov = $this.find("select:eq(0)"),
				$city = $this.find("select:eq(1)"),
				$area = $this.find("select:eq(2)"),
				$input= $this.find("input");
			
			var defaultAreaId = areaId || $this.data("areaId") || $input.val() || 0;
			var noArea = $this.data("noArea") == "true" || $this.data("noArea") == true;
			var provData , cityData;

			$prov.length == 0 && ($prov = $("<select name=prov />")).appendTo($this);
			$city.length == 0 && ($city = $("<select name=city />")).appendTo($this);
			$area.length == 0 && ($area = $("<select name=area />")).appendTo($this);
			$input.length== 0 && ($input= $("<input name=areaId type=hidden />")).appendTo($this);
			
			var changeProv = function( provId , selectedCityId , selectAreaId){
				//$prov.val(provId);
				setSelectVal($prov , provId);
				getAreaData( provId , function(data){
					data = data || [{}];
					updateSelect($city , data );
					changeCity( selectedCityId || data[0].areaId , selectAreaId );
				});
			};

			var changeCity = function( cityId , selectAreaId ){
				$input.val(cityId);
				//$city.val(cityId);
				setSelectVal($city , cityId);
				
				!noArea && getAreaData( cityId , function(data){
					if( data && data.length > 0){
						$area.show();
						updateSelect($area , data );
						selectAreaId = selectAreaId || data[0].areaId;
						setSelectVal($area,selectAreaId).trigger("change");						
					}else{
						$area.empty().hide();
					}
				} );
				
			};

			$prov.change(function(){
				changeProv($prov.val());
			});

			$city.change(function(){
				changeCity($city.val());
			});
			
			$area.change(function(){
				$input.val($area.val());
			});
			
			noArea && $area.remove();
			
			getAreaData(0,function(data){
				updateSelect($prov , data);
				if( defaultAreaId ){
					$.post($.frontPath+"/area.json",{areaId:defaultAreaId},function(defaultData){
						if( defaultData && defaultData.data && defaultData.data[0] ){
							defaultData = defaultData.data ;
						}else{
							defaultData = [{areaId:1}];
						}
						
						var selectedProvId = (defaultData[0]||{}).areaId,
							selectedCityId = (defaultData[1]||{}).areaId,
							selectedAreaId = (defaultData[2]||{}).areaId;
						changeProv( selectedProvId || defaultData[0].areaId , selectedCityId , selectedAreaId);
					});
				}else{
					changeProv(data[0].areaId);
				}
			});
			
			
		});

		return this;
	};
	
	$.fn.area = function(areaId){
		if(this.length == 0)return this;
		
		this.each(function(){
			var $this = $(this);
			var id = areaId || $this.data("areaId");
			$.post($.frontPath + "/area.json", {areaId:id}, function(defaultData){
				defaultData = (defaultData && defaultData.data ) || [];;
				var prov = (defaultData[0]||{}).name || "",
					city = (defaultData[1]||{}).name || "",
					town = (defaultData[2]||{}).name || "";
				var name = prov.concat(city).concat(town);
				$this.html(name);
				$this.attr("title", name);
			});
		});
		
	};
})(window.jQuery);

//行业信息jquery工具
(function($){
	if(!$)return;
	
	var getIndustryDate = function(){
		$.post($.frontPath + "/industry.json", function(data){
			if( !(data && data.list) ){
				return;
			}
			
		});
	};
	
	$.fn.industrySelect = function(industryId){
		if(this.length == 0)return this;
		
		var $this = $(this);
		
		var $parent = $this.find("select:eq(0)"),
			$child  = $this.find("select:eq(1)"),
			$input  = $this.find("input");

		$parent.length == 0 && ($parent = $("<select name=parent />")).appendTo($this);
		$child.length == 0 && ($child = $("<select name=child />")).appendTo($this);
		$input.length == 0 && ($input= $("<input name=industryId type=hidden />")).appendTo($this);
	
		$.post($.frontPath + "/industry.json", function(data){
			if( !(data && data.list) ){
				return;
			}
			
			var list = {};
			$parent.empty();
			$child.empty();
			$.each(data.list, function(i, parent){
				$parent.append("<option value='"+parent.industryId+"'>"+parent.name+"</option>");
				list[parent.industryId] = parent.children;
			});
			$parent.change(function(){
				var id = this.value;
				if(list[id].length == 0){
					$child.hide();
					$input.val(id);
					return;
				}
				$child.show().empty();
				$.each(list[id], function(j, child){
					$child.append("<option value='"+child.industryId+"'>"+child.name+"</option>");
				});
				$child.change();
			});
			$child.change(function(){
				$input.val(this.value);
			});
			
			if(!!industryId){
				if(!!list[industryId] && list[industryId].length > 0){
					//是父级目录
					$parent.find("[value=" + industryId + "]").prop({selected:"selected"});
				}else{
					//是子级目录
					$.each(list, function(){
						$.each(this, function(){
							if(industryId == this.industryId){
								$parent.find("[value=" + this.parentId + "]").prop({selected:"selected"});
								$parent.change();
								$child.find("[value=" + this.industryId + "]").prop({selected:"selected"});
								$child.change();
								return false;
							}
						});
					});
				}
			}else{
				$parent.change();
			}
		});
		
		return this;
	};
	
	$.fn.industry = function(industryId){
		if(this.length == 0)return this;
		
		this.each(function(){
			var $this = $(this);
			industryId = industryId || $this.data("industryId");
			$.post($.frontPath + "/industry.json", {industryId:industryId}, function(data){
				if( !(data && data.list) ){
					return;
				}
				var name = "";
				$.each(data.list, function(){
					name += this.name || "";
				});
				$this.html(name);
				$this.attr("title", name);
			});
		});
	};
})(window.jQuery);

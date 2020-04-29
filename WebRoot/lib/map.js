function HashMap(data){
	if(data){
		this.map=data;
	}else{
		this.map = {};
	}
    
    
   /* 用法
    hashMap.put('key' ,'value');
    hashMap.put('key1' ,'value');
    alert(hashMap.get('key'));
    alert(hashMap.keySet());
    alert(hashMap.remove('key'));
    alert(hashMap.keySet());*/
}
HashMap.prototype = {
    put : function(key , value){
        this.map[key] = value;
    },
    get : function(key){
        if(this.map.hasOwnProperty(key)){
            return this.map[key];
        }
        return null;
    },
    remove : function(key){
        if(this.map.hasOwnProperty(key)){
            return delete this.map[key];
        }
        return false;
    },
    removeAll : function(){
        this.map = {};
    },
    keySet : function(){
        var _keys = [];
        for(var i in this.map){
            _keys.push(i);
        }
        return _keys;
    }
};
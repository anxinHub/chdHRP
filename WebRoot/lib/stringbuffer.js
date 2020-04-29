function StringBuffer() { 
   this.buffer = []; 
 } 
 StringBuffer.prototype.append = function append(string) { 
   this.buffer.push(string); 
   return this; 
 }; 
 StringBuffer.prototype.toString = function toString() { 
   return this.buffer.join(""); 
 };
 
/* var buf = new StringBuffer();
 buf.append("hello");
 buf.append("world");
 alert(buf.toString())*/
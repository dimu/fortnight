"use strict"
//es中对变量作用域的限定，es采用let来解决var造成的变量提前问题，即变量可以在声明前使用，只是值为undefined

{
    let a = 1;
    const b = 2;
    var c = 3;
}

// console.log(a); //reference error:a is not defined
//console.log(b); //refrence error
console.log(c);

var a = [];
for(var i = 0; i < 10; i++ ) {
    a[i] = function() {
        console.log(i);
    }
}
a[5]();

for(let i = 0; i < 10; i++ ) {
    a[i] = function() {
        console.log(i);
    }
}
a[5]();


//let 必须在先声明在使用
//console.log(xx); //ReferenceError
let xx = 123;

//let & const 不允许在相同作用域内，重复声明同一个变量
function f() {
    let a = 1;
    let a = 2;
}
// f(); //SyntaxError: Identifier xx has already been declared

//立即执行函数表达式
(function(){
    var tmp = 'abc';
    console.log(tmp);
}())

//const 常量声明时必须初始化
//const a; //SyntaxError: Missing initializer in const declaration


//基本类型const不发改变，但是对象可以，对于基本类型，const变量存放的实际就是值，而对于对象存放的为址
const person = {};
person.age = 18;
console.log(person);

//采用对象本身冻结
const device = Object.freeze({});
//device.sn = '123333'; //TypeError:Can't add property xx, object is not extensible

//全局对象与顶层对象，为了保持兼容性，function跟var依旧为全局对象的属性，而新增的let，const以及class则脱离了顶层对象的属性
let xx1 =123;
console.log(global.xx1); //undefined
var xx2 = 123;
console.log(xx2);
console.log(global.xx2);
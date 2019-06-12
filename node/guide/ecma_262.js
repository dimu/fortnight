
const test = "abc".normalize();
console.log(test);

// console.log(String.constructor.prototype.prototype.atan(12));
const promise = new Promise((resolve, reject) =>{
    console.log('step into promise')
    resolve();
    console.log(reject());
    // reject();
});
promise.then(function(error){
   console.log('resolved!');
}, function(){
    console.log('rejected!');
});

// promise.reject(functi);
console.log('out promise!');

const set = new Set();
set.add(123);
set.add('123');
set.forEach((item, index) => {
    console.log(index + ":" + item);
});

var str = new String("wxx");
var str1 = str.concat("efg");
console.log(str.toUpperCase().trim());
console.log(str1.toUpperCase());
// console.log(&str);

// TypeError
// const te = new TypeError("file not found exception");
// throw te;

//WeakMap object

//Await

const arg = {};
arg.name = 'dwx';
arg.age = 30;
console.log(arg);

var a = 1;
//unary operation
console.log(a++);
console.log(a);
console.log(typeof a);
console.log(delete arg.name);
console.log(arg);
console.log(~1);
console.log(~2);

//
console.log(new String('abc') == 'abc');
console.log(new String('abc') == new String('abc'));

const {first, second } = {second: 'abc', first1: 1455};
console.log(first);
console.log(second);

[third, fourth] = [123, 456]
//([third, fourth] = [123, 456])
console.log(third);
console.log(fourth);

//rest 参数解析
[a, b, ...rest] = [1,2,3,4,5,6];
console.log(a);
console.log(b);
console.log(rest);

let xx;
console.log(xx);  //undefined
xx = 123;
console.log(xx);







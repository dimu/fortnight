//desctrucuring assignment res

//完全匹配,结构不一定要求相同数据类型
let [a,b,c] = ['123', 345, true];
console.log(a);
console.log(b);
console.log(c);

//rest解构
let  {head, ...other}  = { second:'bb', head: 'aa', third:'cc', fourth:'dd'};
console.log(head);
console.log(other);
console.log(other.second);

let [first, ...tail] = [1,2,3,4];
console.log(tail);


//set default value
let [page = 12]= [];
console.log(page);

//严格使用===undefined定义
let [x = 1] = [undefined];
console.log(x);
[x = 2] = [null];
console.log(x);

//
function f() {
    console.log('aaa');
}

// 延迟调用
let [m = f()] = [1];
console.log(m);
[m = f()] = [];

//修改属性名
let {name : nickname} = {name : 'zhangsan'};
// console.log(name);
console.log(nickname);
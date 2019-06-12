//函数扩展

//1. 支撑函数默认值
function add(x, y = 1) {
    console.log(x, y);
}

add(2);
add(2, 3);
add(2, '');
add(2, undefined);
add(2, NaN);

//2.与解构赋值默认值结合使用
function minus({x, y=1}) {
    console.log(x, y);
}

// minus(); //cannot destructure property 
minus({x:2});  //2 1
minus({y:2, x:4}); //4 2
minus({x:5, y:undefined}); //5 1

//3.默认参数的位置约定，目前es中约定默认值参数最好是尾参数，
//否则传承时没法省略默认值参数，必须用undefined占位
function multi(a = 2, b) {
    console.log(a * b);
}

// multi(, 3);
multi(undefined, 3);
multi(3, 3);

//4.函数length属性不计算默认值及以后的参数个数，函数的length属性是指可预见的参数长度
console.log((function search(a, b=1, c, d){}).length); //1d

//5.rest参数，用于取代arguments对象,rest参数默认为数组，可以使用Array对象的方法
function sum(...values) {
    let sum = 0;

    for(let value of values) {
        sum += value;
    }

    return sum;
}

console.log(sum(1, 2, 3, 4));

//6.name属性
console.log(sum.name);

//7.箭头函数，写法类似于java的λ函数,箭头函数的this对象作用域比较复杂，使用需要留意
const headAndTail = (head, ...tail) => [head, tail];
console.log(headAndTail(1, 2, 3, 4, 5));

//双冒号运算符：
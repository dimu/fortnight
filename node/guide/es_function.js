//function res

//es6添加函数参数默认值功能
function f(x = 1, y = 2) {
    return x + y;
}

console.log(f()); //3
console.log(f(2)); //4
// console.log(f( , 3)); //可以省略的参数放到尾部，估计后续会提供支持
console.log(f(2,3)); //5

//与解构函数配合使用
// function fetch(url, { body = '', method = 'GET', headers = {} }) {
//     console.log(method);
// }
//
// fetch('http://example.com', {})

//如果参数可省，可以将参数定义为undefined，标明可省略
function  miss(optional = undefined) {
    if (typeof optional == undefined) {
        console.log('argument missing!');
    }
}

miss();

//rest 参数
function add(array, ...items) {
    items.forEach(item => {
       array.push(item);
       // console.log(item);
    });
}
const a = [0];
add(a, 1, 2, 3);
console.log('array add ' + a);

//箭头函数,返回一个对象，注意必须用括号
let arrowObject = id => ({id:id, name: "xxx"});
let instanceValue = arrowObject(12);
console.log(instanceValue); //return object {id:12, name:'xxx'}

const divide = ({first, second}) => first / second;
console.log(divide({first:4, second:2})); //return 2

//map映射
console.log([1,2,3].map(item => item *item));

//sort排序
const arrowArray = [{age:12}, {age: 8}, {age:15}];
let arrowCompare =  arrowArray.sort((item1, item2) => item2.age - item1.age );
console.log(arrowCompare);

//rest参数与箭头函数结合使用
const contact = (head, ...tail) => [head, tail];
console.log('lily', 'lucy', 'andy');
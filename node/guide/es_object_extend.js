//es 对object的支持

//属性的简洁表示法，es6允许直接写入变量与函数，作为对象的属性与方法
const age = 19;
const person = {age, getAge() {return this.age}};

person.setAge = function(age) {
    this.age = age;
}

console.log(person);
console.log(person.getAge());
person.setAge(20);
console.log(person.getAge());

//参数返回值
function getPoint(x, y) {
    return {x, y}
}

console.log(getPoint(1, 2));

//采用getter，setter进行对象定义
const cart = {
    _total: 10,

    get total() {
        return this._total;
    },

    set total(value) {
        this._total = value;
    },

    print() {
        console.log(this._total);
    }
}

cart.print();
cart.total = 11;
cart.print();

//属性名表达式，即用中括号表示属性
let stu = {};
stu['name'] = 'dwx';
console.log(stu);

stu = {
    ['name']: 19,
    ['sex']: 'male'
}
console.log(stu);

//属性名表达式与简洁表示法不能同时使用，会报错
// let level = 2;
// stu = {
//     [level]
// }

//方法的name属性
console.log(person.getAge.name);
console.log(cart.total.name); //undefined，对于取值函数与存值函数，name属性不是在该方法上，而是在该属性的描述对象的get与set属性上

const descriptor = Object.getOwnPropertyDescriptor(cart, 'total');
console.log(descriptor.get.name);
console.log(descriptor.set.name);

//Function构造函数创造的函数，name属性返回anonymous
console.log((new Function()).name); //返回anonymous

let somefunction = function() {

};

console.log(somefunction.bind().name); //bound somefunction

//对象合并
const target = {a: 1};
const source1 = {b: 2};
const source12 = {c: 3};
Object.assign(target, source1, source12);
console.log(target);

//对于字符串、布尔值以及数值，只有字符串合入目标对象，数值与布尔值都会被忽略
console.log(Object('abc'));
Object.assign(target, 'efg', true, 456);
console.log(target);  //{ '0': 'e', '1': 'f', '2': 'g', a: 1, b: 2, c: 3 }

//对象合并是浅拷贝，如果源对象某个属性是对象，那么目标对象拷贝的是这个对象的引用
const obj1 = {a: {b: 1}};
const obj2 = Object.assign({}, obj1);
obj1.a.b = 2;
console.log(obj2.a.b); //2

//对象拷贝采用覆盖的作用机理
const x = {};
Object.assign(x, {a:1}, {a:2});
console.log(x); //{a:2}

//常见用途
//1.为对象添加属性
//2.为对象添加方法
//3.克隆对象
//4.合并多个对象
const merge =(target, ...sources) => Object.assign(target, ...sources);
//5.为属性指定默认值

//扩展运算符，用于取出参数对象的所有可遍历属性，拷贝到当前对象之中
let xx1 = {a:1, b:2}
let xx2 = {...xx1};
console.log(xx2) //{a:1, b:2}

//扩展运算符用于合并两个对象
let ab = {...{x:1}, ...{x:2, y:1}};
console.log(ab); //{x:2,y:1}

//Todo:对象创建以及原型链相关设计
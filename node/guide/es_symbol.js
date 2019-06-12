//为了防止属性名的冲突，采用symbol来标记属性名，用于保证属性名字都是独一无二的
//es6扩展的原始数据类型为Symbol，表示独一无二的数值，与undefined, null, Boolean,String, Number,Object并列


//由于symbol是原始数据类型，而不是对象，故不能使用new命令，它是一种类似于字符串的数据类型
let s = Symbol();

console.log(typeof s);

let abc = Symbol('abc');
console.log(abc.toString());

console.log(Symbol('abc') === Symbol('abc')); //false , Symbol参数只是表达对当前Symbol值的描述，因此相同参数的Symbol函数的返回值是不相等的

//属性名的写法
let a = {};
a[abc] = 'hello';
console.log(a[abc]);  

a = {
    [abc]: 'hello1'
};
console.log(a[abc]); //hello1

Object.defineProperty(a, abc, {value: 'hello2'});
console.log(a[abc]); //hello2

a.abc = 'hello3';
console.log(a[abc]); //hello2
console.log(a['abc']); //hello3

//消除魔法数
const shapeType = {
    triangle: 'Triangle'
};

//改为
const shapeType1 = {
    triangle: Symbol()
};
  
//Symbol作为属性名，不会出现在for ...in， for ... of循环中，不会被iterator实现所返回。
console.log(Object.getOwnPropertySymbols(a));


//由于以Symbol值作为名称的属性，不会被常规方法遍历得到，可以为对象定义一些非私有的，但又希望只用于内部的方法

//由于Symbol每次调用返回不同的Symbol值，引入Symbol.for用于登记全局的Symbol，并且用Symbol.keyFor来获取已登记的Symbol的key值
console.log(Symbol.for('abc') === Symbol.for('abc')); //true

//7：内置的Symbol值

//7.1 类型check，实例调用instanceof运算符是，调用这个方法
class Even {
    static [Symbol.hasInstance](obj) {  //为什么必须使用static，否则两者都会返回false
        return Number(obj) % 2 === 0;
    } 
}

console.log(1 instanceof Even);
console.log(2 instanceof Even);

//7.2 Symbol.isConcatSreadable用于数组合并时，是否展开合并，默认为展开合并
let arr1 = ['c', 'd'];
console.log(['a','b'].concat(arr1, 'e'));
arr1[Symbol.isConcatSpreadable] = false;
console.log(['a','b'].concat(arr1, 'e'));
let arr2 = ['c', 'd'];
arr2[Symbol.isConcatSpreadable] = false;
console.log(['a','b'].concat(arr2, 'e'));

//7.3 Symbol.match ，当执行str.match(object)时，如果该属性存在，就会调用他，返回覆盖方法的返回值
class MyMatcher {
    [Symbol.match](param) {
        return 'e' === param;
    }
}

console.log('e'.match(new MyMatcher())); //true

//7.4 Symbol.replace 对象的Symbol.replace属性，指向一个方法，当改对象被String.prototype.replace方法调用时，会返回该方法的返回值
const xx = {};
xx[Symbol.replace] = (...args) => console.log(args);
'Hello'.replace(xx, 'world');

//7.5 Symbol.search
class MySearch {
    constructor(value) {
      this.value = value;
    }
    [Symbol.search](string) {
      return string.indexOf(this.value);
    }
}
console.log('foobar'.search(new MySearch('bar'))); //3

//7.6 Symbol.split

//7.7 Symbol.iterator

//7.8 Symbol.toPrimitive

//7.9 Symbol.unscopables

//7.10 Symbol.toStringTag 
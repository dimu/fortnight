let a = new Set([1, 2, 3]);
let b = new Set([4, 3, 2]);

// 并集
let union = new Set([...a, ...b]);// Set {1, 2, 3, 4}

// 交集
let intersect = new Set([...a].filter(x => b.has(x)));// set {2, 3}

// 差集
let difference = new Set([...a].filter(x => !b.has(x))); // Set {1}

//WeakSet成员只允许为对象，任务具有Iterable接口的对象都可以作为WeakSet的参数
const a1 = [[1, 2], [3, 4]]; //必须为成员本身为对象
const ws = new WeakSet(a1);

const myMap = new Map()
  .set(true, 7)
  .set({foo: 3}, ['abc']);
console.log([...myMap]);

//对象转map
function objToStrMap(obj) {
    let strMap = new Map();
    for (let k of Object.keys(obj)) {
      strMap.set(k, obj[k]);
    }
    return strMap;
  }
  
  objToStrMap({a: 1, b: 2})
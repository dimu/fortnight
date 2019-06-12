var foo = function(){};

foo.prototype.add = function(a, b) {
    return a + b;
};

console.log(foo.add(1, 2));

foo.call(add, 1, 3);


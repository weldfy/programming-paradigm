"use strict";

const vals = ["x", "y", "z"];
const variable = name => (...val) => val[vals.indexOf(name)]//valsNum[name]];

const cnst = val => () => val;
const one = cnst(1);
const two = cnst(2);

const operation = func => (...ops) => (...val) => func(...ops.map(op => op(...val)));
const add = operation((x, y) => x + y);
const subtract = operation((x, y) => x - y);
const multiply = operation((x, y) => x * y);
const divide = operation((x, y) => x / y);
const negate = operation(x => -x);

const madd = operation((x, y, z) => x * y + z);

const ceil = operation(x => Math.ceil(x));
const floor = operation(x => Math.floor(x));

const ops = {
    "*+": [3, madd],
    "_": [1, floor],
    "^": [1, ceil],
    "+": [2, add],
    "-": [2, subtract],
    "*": [2, multiply],
    "/": [2, divide],
    "negate": [1, negate],
};

const cnsts = {
    "one": one,
    "two": two,
};

const parsing = exp => {
    let ans = [];
    for (const val of exp) {
        if (vals.includes(val)) {
            ans.push(variable(val));
            continue;
        }
        if (val in cnsts) {
            ans.push(cnsts[val]);
            continue;
        }
        if (val in ops) {
            ans.push(ops[val][1](...ans.splice(-ops[val][0])));
            continue;
        }
        ans.push(cnst(Number.parseInt(val)));
    }
    return ans.pop();
}
const parse = exp => parsing(exp.split(" ").filter(x => x.length !== 0));

const exp = parse("x x * 2 x * - 1 +");

for (let i = 0; i < 11; i++) {
    println(exp(i, 0, 0));
}














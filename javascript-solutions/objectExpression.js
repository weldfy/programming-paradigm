"use strict";

const Const = function(x) {
	this.x = x;
};
Const.prototype.toString = function() {
    return this.x.toString();
};
Const.prototype.evaluate = function() {
     return this.x;
};
Const.prototype.diff = function(variable) {
    // :NOTE: new Const(0) создается заново на каждый вызов, то же самое дальше по коду много раз
    return new Const(0);
};

const variables = ['x', 'y', 'z'];

const Variable = function(x) {
	this.x = x;
};
Variable.prototype.toString = function() {
    return this.x;
};
Variable.prototype.evaluate = function(...variable) {
    let index = variables.indexOf(this.x);
    return variable[index];
};
Variable.prototype.diff = function(variable) {
    if (variable === this.x) {
        return new Const(1);
    } else {
        return new Const(0);
    }
};

const AbstractOperation = function(...exps) {
	this.exps = exps;
};
AbstractOperation.prototype.toString = function() {
	return this.exps.join(" ") + " " + this.sign();
};
AbstractOperation.prototype.evaluate = function(...vars) {
	return this.comp(...this.exps.map((exp) => exp.evaluate(...vars)));
};
AbstractOperation.prototype.diff = function(variable) {
	return this.diffExp(variable, ...this.exps);
};

const newOp = function (sign, comp, diffExp) {
	let Opr = function(...exps) {
		AbstractOperation.call(this, ...exps);
	};

	Opr.prototype = Object.create(AbstractOperation.prototype);
    Opr.prototype.sign = sign;
	Opr.prototype.comp = comp;
	Opr.prototype.diffExp = diffExp;
	Opr.prototype.constructor = Opr;

	return Opr;
};
const Add = newOp(() => '+',
	(x, y) => (x + y),
	(variable, x, y) => (new Add(x.diff(variable), y.diff(variable))));
const Subtract = newOp(() => '-',
	(x, y) => (x - y),
	(variable, x, y) => (new Subtract(x.diff(variable), y.diff(variable))));
const Multiply = newOp(() => '*',
	(x, y) => (x * y),
	(variable, x, y) => (new Add( new Multiply(x, y.diff(variable)), new Multiply(x.diff(variable), y))));
const Divide = newOp(() => '/',
	(x, y) => (x / y),
	(variable, x, y) => (new Divide( new Subtract( new Multiply(x.diff(variable), y), new Multiply(x, y.diff(variable))), new Multiply(y, y))));
const Negate = newOp(() => 'negate',
	(x) => (-x),
	(variable, x) => (new Negate(x.diff(variable))));

let sumSqr = function (...vars) {
    let ans = 0;
    // :NOTE: reduce?
    for (let i = 0; i < vars.length; i++) {
        ans += vars[i] * vars[i];
    }
    return ans;
}
let expSumSqr = function (...vars) {
    let ans = new Multiply(vars[0], vars[0]);
    // :NOTE: reduce?
    for (let i = 1; i < vars.length; i++) {
        ans = new Add(ans, new Multiply(vars[i], vars[i]));
    }
    return ans;
}

const SumSqN = newOp(
    function() {
        return 'sumsq' + this.exps.length;
    },
    function(...vars) {
        return sumSqr(...vars);
    },
    function(variable, ...vars) {
        return expSumSqr(...vars).diff(variable);
    },)

const Sumsq2 = SumSqN;
const Sumsq3 = SumSqN;
const Sumsq4 = SumSqN;
const Sumsq5 = SumSqN;

const DistanceN = newOp(
    function() {
        return 'distance' + this.exps.length;
    },
    function(...vars) {
        return Math.sqrt(sumSqr(...vars));
    },
    function (variable, ...vars) {
        return new Divide( new SumSqN(...vars).diff(variable), 
                new Multiply(
                    new Const(2),
                    // :NOTE: new DistanceN(...vars) это текущая операция, не нужно ее создавать заново
                    new DistanceN(...vars)));
    }
)
const Distance2 = DistanceN;
const Distance3 = DistanceN;
const Distance4 = DistanceN;
const Distance5 = DistanceN;

let ops = {
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "negate": [Negate, 1],
}

// :NOTE: раз хочется сократить копипасту, то можно было бы занести добавление в мапу ops внутрь создания операции newOp
for (let i = 2; i <= 5; i++) {
    ops["sumsq" + i] = [SumSqN, i];
    ops["distance" + i] = [DistanceN, i];
}

const parsing = exp => {
    let ans = [];
    for (const val of exp) {
        if (val in ops) {
            let operation = ops[val][0];
            let cnt = ops[val][1];
            ans.push(new operation(...ans.splice(-cnt)));
        } else if (variables.includes(val)) {
            ans.push(new Variable(val));
        } else {
            ans.push(new Const(Number.parseInt(val)));
        }
    }
    return ans.pop();
};

const parse = exp => parsing(exp.trim().split(/\s+/));

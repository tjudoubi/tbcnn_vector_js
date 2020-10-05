

function f() {}

var fn = f;
for (var i = 0; i < 100000; ++i) {
    fn = fn.bind();

    Object.defineProperty(fn, Symbol.hasInstance, {
        value: undefined, writable: true, enumerable: true, writable: true
    });


    Object.defineProperty(fn, "name", {
        value: "", writable: true, enumerable: true, writable: true
    });
}

var exception;
try {
    ({}) instanceof fn;
} catch (e) {
    exception = e;
}

if (exception != "RangeError: Maximum call stack size exceeded.")
    throw "FAILED";

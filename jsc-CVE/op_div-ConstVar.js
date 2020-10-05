

load("./resources/binary-op-test.js");



var opName = "div";
var op = "/";

load("./resources/binary-op-values.js");

tests = [];
generateBinaryTests(tests, opName, op, "ConstVar", values, values);

run();

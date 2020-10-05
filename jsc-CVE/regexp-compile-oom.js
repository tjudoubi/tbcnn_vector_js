
var failures = [];

class TestAndExpectedException
{
    constructor(func, exception)
    {
        this.func = func;
        this.exception = exception;
    }

    runTest()
    {
        try {
            this.func();
            failures.push("Running " + this.func + ", expected OOM exception, but didn't get one");
        } catch (e) {
            let errStr = e.toString();
            if (errStr != this.exception)
                failures.push("Running " + this.func + ", expected: \"" + this.exception + "\" but got \"" + errStr + "\"");
        }       
    }
}

function recurseAndTest(depth, testList)
{

    try {
        let result = recurseAndTest(depth + 1, testList);
        if (result == 0) {

            for (const test of testList)
                test.runTest();

            return 1;
        } else if (result < 0)
            return result + 1;
        else
            return result;
    } catch (e) {

        return -24;
    }

    return 1;
}

let deepRE = new RegExp("((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((x))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
let deepGlobalRE = new RegExp(deepRE, "g");

let matchLen = 401; 

let regExpOOMError = "Error: Out of memory: Invalid regular expression: too many nested disjunctions";

testList = [];


testList.push(new TestAndExpectedException(() => { deepRE.exec("x"); }, regExpOOMError));
testList.push(new TestAndExpectedException(() => { deepRE.test("x"); }, regExpOOMError));
testList.push(new TestAndExpectedException(() => { "x".match(deepRE); }, regExpOOMError));
testList.push(new TestAndExpectedException(() => { "x".match(deepGlobalRE); }, regExpOOMError));
testList.push(new TestAndExpectedException(() => { "x".replace(deepGlobalRE, ""); }, regExpOOMError));
testList.push(new TestAndExpectedException(() => { "x".replace(deepGlobalRE, "X"); }, regExpOOMError));
testList.push(new TestAndExpectedException(() => { "x".replace(deepGlobalRE, () => { return "X" }); }, regExpOOMError));
testList.push(new TestAndExpectedException(() => { "x".search(deepRE); }, regExpOOMError));

recurseAndTest(1, testList);

if (failures.length) {
    print("Got the following failures:");
    for (const failure of failures)
        print(failure);
    throw "Got failures";
}


let m = deepRE.exec("x");
let matched = true;
if (m.length != matchLen)
    matched = false
else {
    for (i = 0; i < matchLen; i++) {
        if (m[i] != "x")
            matched = false;
    }
}

if (!matched) {
    let expectedMatch = [];
    for (i = 0; i < matchLen; i++)
        expectedMatch[i] = "x";

    throw "Expected RegExp.exec(...) to be [" + expectedMatch + "] but got [" + m + "]";
}

if (!deepRE.test("x"))
    throw "Expected RegExp.test(...) to be true, but was false";

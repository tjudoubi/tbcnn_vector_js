var setGlobalConstRedeclarationShouldNotThrow = $vm.setGlobalConstRedeclarationShouldNotThrow;

function assert(b) {
    if (!b)
        throw new Error("Bad assertion.");
}

setGlobalConstRedeclarationShouldNotThrow(); 

load("./global-const-redeclaration-setting/let.js");
assert(foo === 50);
let threw = false;
try {
    load("./global-const-redeclaration-setting/first.js"); 
} catch(e) {
    threw = true;
}

assert(threw);
assert(foo === 50);

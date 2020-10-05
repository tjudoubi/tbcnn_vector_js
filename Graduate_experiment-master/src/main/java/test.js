function foo()
{
    if(x!==1){
        var o = Error();
    }
    for(let i in o)
    {
        o[i];
    }
}

var bb = foo();

set Components;
param T;
param c{Components};
param k{Components,0..T+1,0..T+1} default 0;
param d;
param U{Components};

var x{Components,0..T+1,0..T+1}, binary;
var z{1..T}, binary;

minimize Cost: sum{t in 1..T}d*z[t] + sum{i in Components,s in 0..T,t in s+1..T+1}k[i,s,t]*x[i,s,t];

subject to banan{i in Components, t in 1..T}:
	sum{s in 0..t-1}x[i,s,t] <= z[t];

subject to kaka{i in Components, t in 1..T}:
	sum{s in 0..t-1}x[i,s,t] = sum{r in t+1..T+1}x[i,t,r];

subject to kebab {i in Components}:
	sum{t in 1..T+1}x[i,0,t] = 1;




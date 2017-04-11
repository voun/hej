
# objective: minimize total cost of maintenance during the planning period
minimize Cost: sum{i in Components, t in 1..T}
    c[i]*x[i,t] + sum{t in 1..T} d*z[t]; 
 
# replace each component at least "once in a lifetime"
subject to ReplaceWithinLife {i in Components, ell in 0..T-U[i]}:
    (if T >= U[i] then sum{t in ell+1..U[i]+ell} x[i,t] else 1) >= 1;

# replace parts only at maintenence occasions
subject to ReplaceOnlyAtMaintenance {i in Components, t in 1..T}:
    x[i,t] <= z[t]; 



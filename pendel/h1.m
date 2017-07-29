function ekv = h1(t,x,A,w,l)
ekv = [x(2);1/l*(9.82-A*w*w*(cos(w*t)))*x(1)];
end

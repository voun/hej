
clc;clf;clear
hold on;
n = 25;
A = ones(n);
fill([1,n+1,n+1,1],[1,1,n+1,n+1],'green');
axis([1 n+1 1 n+1]);
for k = 1:n+1
    plot([1 n+1], [k k],'k');
    plot([k k],[1 n+1],'k');
end
for i = 1:n
    for j = 1:n
        if rand(1) <= 0.05 %%initialiserar matrisen, 10% chans att en ruta ska bli svart
            A(i,j) = 0;
            fill([i,i+1,i+1,i],[j,j,j+1,j+1],'k');
        end
    end
end
            
axis off;

while 1
    [x,y,b] = ginput(1);
    x = floor(x);y=floor(y);
    if b==1
        if A(x,y)== 1
            A(x,y) = 2;
            fill([x,x+1,x+1,x],[y,y,y+1,y+1],'y');
        end
    else
        break;
    end
end
Any = A;

while sum(sum((A(1:n,1:n)==0))) ~= n*n && sum(sum((A(1:n,1:n)==2))) >= 1 %%kör while inte helt uppbrunnit och fortfarande brinner någonstans
    for i = 1:n
        for j=1:n
            if A(i,j) == 2
                Any(i,j) = 0;
            end
            if A(i,j) == 1 && antalgrannar(A,i,j,n) >= 1
                    if rand(1) <= 0.90
                        display('hej');
                        Any(i,j) = 2;
                    end
            end
            if A(i,j) == 0 %%egentligen onödigt
                Any(i,j) = 0;
            end
        end
    end
    A = Any; %%uppdatera
    update(A,n);
end
display ('tjena');
      
    
        
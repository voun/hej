function [] = update(A,n)
pause(1);
axis([1 n+1 1 n+1]);
for k = 1:n+1
    plot([1 n+1], [k k],'k');
    plot([k k],[1 n+1],'k');
end
for i= 1:n
    for j = 1:n
        if A(i,j) == 0
            fill([i,i+1,i+1,i],[j,j,j+1,j+1],'k');
        end
        if A(i,j) == 1
            fill([i,i+1,i+1,i],[j,j,j+1,j+1],'g');
        end
        if A(i,j) == 2
            fill([i,i+1,i+1,i],[j,j,j+1,j+1],'y');
        end
        
    end
end

        
        
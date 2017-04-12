function antal = antalgrannar(A,i,j,n)
igrannar = max(1,j-1):min(n,j+1);
jgrannar = max(1,j-1):min(n,j+1);
if A(i,j) == 2
    antal = sum(sum(A(igrannar,jgrannar) == 2)) - 1;
else
    antal = sum(sum(A(igrannar,jgrannar) == 2));
end
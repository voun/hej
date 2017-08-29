clc;clf;clear
N=100;
X = normrnd(0,1,[1,N]);
hist(X)

X=sort(X);

phat=mle(X);

x=zeros(1,N);
y=zeros(1,N);
for i=1:N
    x(1,i)=(i-0.5)/N;
    y(i,1)=normcdf(X(1,i),phat(1),phat(2));
end

plot(x,y)

hold on;

plot(x,x)

% vi får då att P( D <= 0.136) = 0.95.

D=max(abs(normcdf(X,phat(1),phat(2))-(1:N)./N))
a=0.136

%%
clc;clf;clear
N=100;
X=gamrnd(2,2,[1,N]);
hist(X)
X=sort(X)

phat=mle(X);

x=zeros(1,N);
y=zeros(1,N);

for i=1:N
    x(1,i)=(i-0.5)/N;
    y(i,1)=normcdf(X(1,i),phat(1),phat(2));
end

plot(x,y,x,x);

D=max(abs(normcdf(X,phat(1),phat(2))-(1:N)./N))
a=0.136

%%
clf;clc;clear
N=100;
M=1000;
alpha=0.05;
r=0;
avgSum=0;
for i=1:M
    X = normrnd(0,1,[1,N]);
    [muhat,sigmahat,muci,sigmaci] = normfit(X,alpha);
    avgSum=avgSum+sigmaci(2)-sigmaci(1);
    if sigmaci(1) < 1 && sigmaci(2) > 1
        r=r+1;
    end
end

avgSum/M
r/M

%%
%%
clf;clc;clear
N=100;
M=1000;
alpha=0.05;
r=0;
avgSum=0;
for i=1:M
    X = gamrnd(2,2,[1,N]);
    [muhat,sigmahat,muci,sigmaci] = normfit(X,alpha);
    avgSum=avgSum+sigmaci(2)-sigmaci(1);
    if sigmaci(1) < 8 && sigmaci(2) > 8
        r=r+1;
    end
end

avgSum/M
r/M
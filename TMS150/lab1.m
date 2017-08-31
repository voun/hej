%% Assignment 1.1
clc;clf;clear
N=100;
X = normrnd(0,1,[1,N]);

subplot(1,2,1);
hist(X)

X=sort(X);

%maximum likelihood
phat=mle(X);

%do a pp-plot
x=zeros(1,N);
y=zeros(1,N);
for i=1:N
    x(1,i)=(i-0.5)/N;
    y(i,1)=normcdf(X(1,i),phat(1),phat(2));
end

subplot(1,2,2);
plot(x,y,x,x)

%D is test statistic. Performs Kolmogorov-Smirnov goodness of fit test.
%reject null hypothesis if D > 0.136. 5% sign level, otherwise accept
%D=max(abs(normcdf(X,phat(1),phat(2))-(1:N)./N))
a=0.136
[h,p] = chi2gof(X)

%% Assignemnt 1.1 continued
clc;clf;clear
N=100;
X=gamrnd(2,2,[1,N]);
subplot(1,2,1)
hist(X)
X=sort(X);

phat=mle(X);

x=zeros(1,N);
y=zeros(1,N);

for i=1:N
    x(1,i)=(i-0.5)/N;
    y(i,1)=normcdf(X(1,i),phat(1),phat(2));
end
%we observe a systematic deviation from y=x
subplot(1,2,2)
plot(x,y,x,x);

D=max(abs(normcdf(X,phat(1),phat(2))-(1:N)./N))
a=0.136

[h,p] = chi2gof(X) % kollar om X är normalfördelad (med estimerade parametrar)

%% Assignment 1.2
clf;clc;clear
N=100;
M=1000;
alpha=0.05;
r=0;
avgSum=0;
for i=1:M
    X = normrnd(0,1,[1,N]);
    [muhat,sigmahat,muci,sigmaci] = normfit(X,alpha);
    avgSum=avgSum+sigmaci(2)^2-sigmaci(1)^2;
    if sigmaci(1)^2 < 1 && sigmaci(2)^2 > 1
        r=r+1;
    end
end

avgSum/M
r/M

%% Assignment 1.2 continued
clf;clc;clear
N=100;
M=1000;
alpha=0.05;
r=0;
avgSum=0;
for i=1:M
    X = gamrnd(2,2,[1,N]);
    [muhat,sigmahat,muci,sigmaci] = normfit(X,alpha);
    avgSum=avgSum+sigmaci(2)^2-sigmaci(1)^2;
    if sigmaci(1)^2 < 8 && sigmaci(2)^2 > 8
        r=r+1;
    end
end

avgSum/M
r/M

%% Assignment 2.1
clc;clf;clear
N = 1000;
X = zeros(1,N);

%generate a sample from the contaminated distribution
for i=1:N
    if rand(1) <= 0.95
        X(1,i) = normrnd(0,1);
    else
        X(1,i) = trnd(1);
    end
end

subplot(1,2,1)
hist(X,100);

X=sort(X);

phat=mle(X);

x=zeros(1,N);
y=zeros(1,N);
for i=1:N
    x(1,i)=(i-0.5)/N;
    y(i,1)=normcdf(X(1,i),phat(1),phat(2));
end

subplot(1,2,2);
plot(x,y,x,x)

%D is test statistic. Performs Kolmogorov-Smirnov goodness of fit test.
%reject null hypothesis if D > 0.136. 5% sign level, otherwise accept

D=max(abs(normcdf(X,phat(1),phat(2))-(1:N)./N))
a=0.136

%% Assignment 2.2
clc;clf;clear

M = 1000;
N=100;
meanVec=zeros(1,M);
for j=1:M
X = zeros(1,N);
    for i=1:N
        if rand(1) <= 0.95
            X(1,i) = normrnd(0,1);
        else
            X(1,i) = trnd(1);
        end
    end
    meanVec(1,j)=mean(X);
end

hist(meanVec,100)

meanVec=sort(meanVec);
meanVec(25), meanVec(975)
%% Assignment 2.2 continued
clc;clf;clear

meanVec = zeros(1,1000);
for i=1:1000
    meanVec(1,i) = mean(normrnd(0,1,[1 100]));
end
hist(meanVec,100);
meanVec=sort(meanVec);
meanVec(25),meanVec(975)

%% Assignment 2.3
clc;clf;clear;

M=1000;
alpha=0.1;
N=100;
k=N*alpha;
meanVec=zeros(1,M);
for j=1:M
    X=zeros(1,1000);
    for i=1:N
        if rand(1) <= 0.95
            X(1,i) = normrnd(0,1);
        else
            X(1,i) = trnd(1);
        end
    end
    X=sort(X);
    meanVec(1,j)=mean(X(k+1:N-k));
end
hist(meanVec,100)
meanVec=sort(meanVec);
meanVec(25)
meanVec(975)

%% Assignment 2.3 continued
clc;clf;clear
M=1000;
alpha=0.1;
N=100;
k=N*alpha;
meanVec = zeros(1,M);
for i=1:M
    X=normrnd(0,1,[1 N]);
    X=sort(X);
    meanVec(1,i) = mean(X(k+1:N-k));
end
hist(meanVec,100);
meanVec=sort(meanVec);
meanVec(25),meanVec(975)

%% Assignment 2, Question 
clc;clf;clear;

M=1000;
alpha=0.1;
N=100;
k=N*alpha;

%Histogram for sample of contaminated distribution
Y=zeros(1,N);
for i=1:N
        if rand(1) <= 0.95
            Y(1,i) = normrnd(0,1);
        else
            Y(1,i) = trnd(1);
        end
end
subplot(1,2,1)
hist(Y,100)

%Calculates the mean, median and trimmed mean for 1000 different samples
alphaMeanVec=zeros(1,M);
meanVec=zeros(1,M);
medianVec=zeros(1,M);
for j=1:M
    X=zeros(1,N);
    for i=1:N
        if rand(1) <= 0.95
            X(1,i) = normrnd(0,1);
        else
            X(1,i) = trnd(1);
        end
    end
    X=sort(X);
    meanVec(1,j)=mean(X);
    alphaMeanVec(1,j)=mean(X(k+1:N-k));
    medianVec(1,j)=median(X);
end
subplot(1,2,2)
hist(meanVec,100)
meanVec=sort(meanVec);
alphaMeanVec = sort(alphaMeanVec);
medianVec = sort(medianVec);

meanVec(25), meanVec(975)
alphaMeanVec(25), alphaMeanVec(975)
medianVec(25), medianVec(975)

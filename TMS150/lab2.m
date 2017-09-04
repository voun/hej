%% stock 1

clc;clf;clear 
S = load('stockdata.tsv');
S1 = S(:,2);
time = 1:length(S1);
subplot(3,2,1);
plot(time,S1);
X1 = logreturn(S1);
subplot(3,2,2)
plot(time,X1);
subplot(3,2,3);
hist(X1,100);
subplot(3,2,4);
qqplot(X1);

[h,p] = chi2gof(X1);
p

subplot(3,2,[5 6]);
autocorr(X1);

mean1 = mean(X1)
stdDev1 = sqrt(var(X1))


%% stock 2


clc;clf;clear 
S = load('stockdata.tsv');
S2 = S(:,3);
time = 1:length(S2);
subplot(2,2,1);
plot(time,S2);
X2 = logreturn(S2);
subplot(2,2,2)
plot(time,X2);
subplot(2,2,3);
hist(X2,100);
subplot(2,2,4);
qqplot(X2);

[h,p] = chi2gof(X2);
p

mean2 = mean(X2)
stdDev2 = sqrt(var(X2))
%% stock 3

clc;clf;clear 
S = load('stockdata.tsv');
S3 = S(:,4);
time = 1:length(S3);
subplot(2,2,1);
plot(time,S3);
X3 = logreturn(S3);
subplot(2,2,2)
plot(time,X3);
subplot(2,2,3);
hist(X3,100);
subplot(2,2,4);
qqplot(X3);

[h,p] = chi2gof(X3);
p

mean3 = mean(X3)
stdDev3 = sqrt(var(X3))
%% stock 4 

clc;clf;clear 
S = load('stockdata.tsv');
S4 = S(:,5);
time = 1:length(S4);
subplot(2,2,1);
plot(time,S4);
X4 = logreturn(S4);
subplot(2,2,2)
plot(time,X4);
subplot(2,2,3);
hist(X4,100);
subplot(2,2,4);
qqplot(X4);

[h,p] = chi2gof(X4);
[h,p]

mean4 = mean(X4)
stdDev4 = sqrt(var(X4))
%% stock 5

clc;clf;clear 
S = load('stockdata.tsv');
S5 = S(:,6);
time = 1:length(S5);
subplot(2,2,1);
plot(time,S5);
X5 = logreturn(S5);
subplot(2,2,2)
plot(time,X5);
subplot(2,2,3);
hist(X5,100);
subplot(2,2,4);
qqplot(X5);

[h,p] = chi2gof(X5);
[h,p]

mean5 = mean(X5)
stdDev5 = sqrt(var(X5))
%% stock 6

clc;clf;clear 
S = load('stockdata.tsv');
S6 = S(:,7);
time = 1:length(S6);
subplot(2,2,1);
plot(time,S6);
X6 = logreturn(S6);
subplot(2,2,2)
plot(time,X6);
subplot(2,2,3);
hist(X6,100);
subplot(2,2,4);
qqplot(X6);

[h,p] = chi2gof(X6);
[h,p]

mean6 = mean(X6)
stdDev6 = sqrt(var(X6))
%% stock 7

clc;clf;clear 
S = load('stockdata.tsv');
S7 = S(:,8);
time = 1:length(S7);
subplot(2,2,1);
plot(time,S7);
X7 = logreturn(S7);
subplot(2,2,2)
plot(time,X7);
subplot(2,2,3);
hist(X7,100);
subplot(2,2,4);
qqplot(X7);

[h,p] = chi2gof(X7);
[h,p]

mean7 = mean(X7)
stdDev7 = sqrt(var(X7))
%% correlation 

clc;clf;clear 
S = load('stockdata.tsv');

X=zeros(1006,7);
for i = 1:7
    X(:,i) = logreturn(S(:,i+1));
end

R=corrcoef(X)

%% utility function

clc;clf;clear
x=linspace(-1,1,1000);
y1=@(x,k) util(x,k);
K=[1 4 7 10]
for k=1:4
    subplot(2,2,k)
    plot(x,y1(x,K(k)))
end

%% stock 1
format long
clc;clf;clear %% om talen blir för stora eller för små så kan inte läsa in den i en numeric

S = load('stockdata.tsv');
S1 = S(:,2);
X1 = logreturn(S1);
mean = mean(X1);
std = sqrt(var(X1));

f = @(x,k) util(x,k).*normpdf(x,mean,std);

int1 = integral(@(x)f(x,1),-170,170)
int2 = integral(@(x)f(x,2),-170,170)
int3 = integral(@(x)f(x,3),-170,170)
int4 = integral(@(x)f(x,4),-170,170)

%% stock 2
format long
clc;clf;clear %% om talen blir för stora eller för små så kan inte läsa in den i en numeric

S = load('stockdata.tsv');
S2 = S(:,3);
X2 = logreturn(S2);
mean = mean(X2);
std = sqrt(var(X2));

f = @(x,k) util(x,k).*normpdf(x,mean,std);

int1 = integral(@(x)f(x,1),-170,170)
int2 = integral(@(x)f(x,2),-170,170)
int3 = integral(@(x)f(x,3),-170,170)
int4 = integral(@(x)f(x,4),-170,170)

%% stock 3
format long
clc;clf;clear %% om talen blir för stora eller för små så kan inte läsa in den i en numeric

S = load('stockdata.tsv');
S3 = S(:,4);
X3 = logreturn(S3);
mean = mean(X3);
std = sqrt(var(X3));

f = @(x,k) util(x,k).*normpdf(x,mean,std);

int1 = integral(@(x)f(x,1),-170,170)
int2 = integral(@(x)f(x,2),-170,170)
int3 = integral(@(x)f(x,3),-170,170)
int4 = integral(@(x)f(x,4),-170,170)

%% stock 4
format long
clc;clf;clear %% om talen blir för stora eller för små så kan inte läsa in den i en numeric

S = load('stockdata.tsv');
S4 = S(:,5);
X4 = logreturn(S4);
mean = mean(X4);
std = sqrt(var(X4));

f = @(x,k) util(x,k).*normpdf(x,mean,std);

int1 = integral(@(x)f(x,1),-170,170)
int2 = integral(@(x)f(x,2),-170,170)
int3 = integral(@(x)f(x,3),-170,170)
int4 = integral(@(x)f(x,4),-170,170)

%% stock 5
format long
clc;clf;clear %% om talen blir för stora eller för små så kan inte läsa in den i en numeric

S = load('stockdata.tsv');
S5 = S(:,6);
X5 = logreturn(S5);
mean = mean(X5);
std = sqrt(var(X5));

f = @(x,k) util(x,k).*normpdf(x,mean,std);

int1 = integral(@(x)f(x,1),-170,170)
int2 = integral(@(x)f(x,2),-170,170)
int3 = integral(@(x)f(x,3),-170,170)
int4 = integral(@(x)f(x,4),-170,170)

%% stock 6
format long
clc;clf;clear %% om talen blir för stora eller för små så kan inte läsa in den i en numeric

S = load('stockdata.tsv');
S6 = S(:,7);
X6 = logreturn(S6);
mean = mean(X6);
std = sqrt(var(X6));

f = @(x,k) util(x,k).*normpdf(x,mean,std);

int1 = integral(@(x)f(x,1),-170,170)
int2 = integral(@(x)f(x,2),-170,170)
int3 = integral(@(x)f(x,3),-170,170)
int4 = integral(@(x)f(x,4),-170,170)

%% stock 7
format long
clc;clf;clear %% om talen blir för stora eller för små så kan inte läsa in den i en numeric

S = load('stockdata.tsv');
S7 = S(:,8);
X7 = logreturn(S7);
mean = mean(X7);
std = sqrt(var(X7));

f = @(x,k) util(x,k).*normpdf(x,mean,std);

int1 = integral(@(x)f(x,1),-170,170)
int2 = integral(@(x)f(x,2),-170,170)
int3 = integral(@(x)f(x,3),-170,170)
int4 = integral(@(x)f(x,4),-170,170)

x=linspace(-1,1,1000);
subplot(1,2,1)
plot(x,util(x,1))
subplot(1,2,2)
plot(x,normpdf(x,mean,std))
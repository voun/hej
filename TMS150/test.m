clc;clf;clear

S = load('stockdata.tsv');
S1 = S(:,2);
time = 1:length(S1);
X1 = logreturn(S1);
qqplot(X1)
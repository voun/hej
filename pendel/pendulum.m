clc;clf;clear;

w0 = 0.8;
A0 = 0.1;
l = 3; %% length of the pendulum
x = zeros(0); %% vector which will contain the x-coordinate for the points that have bounded solutions
y = zeros(0); %% vector which will contain the y-coordinate for the points that have bounded solutions
options = odeset('RelTol',1e-8,'AbsTol',1e-10); %%higher accuracy KOMMENTERA h1 
tic
for i = 1:200
    A = A0+0.07*i; %%Amplitude of oscillating pivot
    for j = 1:800
        w = w0+0.020*j; %%frequency of oscillating pivot
        p = 2*pi/w; %%period of the matrix
        [t1,x1] = ode45(@(t1,x) h1(t1,x,A,w,l),[0 p],[0.2;0],options); %%finds a solution that is the first colummn in the transition matrix. Solve for small initial angle so that our linear model holds
        [t2,x2] = ode45(@(t2,x) h1(t2,x,A,w,l),[0 p],[0;0.2],options); %%finds a solution that is the second column in the transition matrix
        M = zeros(2); %% define the monodromy matrix
        M(1,1) = 5*x1(size(t1,1),1);
        M(2,1) = 5*x1(size(t1,1),2);
        M(1,2) = 5*x2(size(t2,1),1);
        M(2,2) = 5*x2(size(t2,1),2);
        gamma = 1/2*trace(M);
        if abs(gamma) <1 %%see report
            x = [x,1/(w*w)]; %%save this point, it has bounded solutions
            y = [y,A/l];
        end
        if abs(gamma) == 1 %%see report
            if M(1,1)-gamma == 0 && M(2,2)- gamma == 0 && M(1,2) == 0 && M(2,1) == 0
              x = [x,1/(w*w)]; %%save this point, it has bounded solutions
              y = [y,A/l];
            end
        end
        i
    end
end
toc
plot(x,y,'k.') %%plot the points for which all solutions are bounded
xlabel('1/w^2'), ylabel('A/L');

%%
clc;clf;clear

%%[t1,x1] = ode45(@(t1,x) h1(t1,x,0.1033*3,28.8675,3),[0 15],[0;0.2]);
%%subplot(1,3,1)
%%plot(x1(:,1),x1(:,2));

options = odeset('RelTol',1e-8,'AbsTol',1e-10); %%higher accuracy
[t2,x2] = ode45(@(t2,x) h1(t2,x,0.1033*3,28.8675,3),[0 15],[0.2;0],options); %%solves
subplot(1,2,1);
plot(x2(:,1),x2(:,2));
axis([-0.3 0.3 -0.7 0.7]);
xlabel('x1(t)');
ylabel('x2(t)');

[t3,x3] = ode45(@(t3,x) h1(t3,x,0.1033*3,15,3),[0 3],[0.2;0],options);
subplot(1,2,2);
plot(x3(:,1),x3(:,2));
xlabel('x1(t)');
ylabel('x2(t)');


%%
clc;clf;clear
w0 = 0.5
A0 = 0.1;
l = 3; %%length of the pendulum
x = zeros(0); %% vector which will contain the x-coordinate for the points that have bounded solutions
y = zeros(0); %% vector which will contain the y-coordinate for the points that have bounded solutions

options = odeset('RelTol',1e-8,'AbsTol',1e-10);
tic
for i = 1:150
    A = A0+0.3*i; %%Amplitude of oscillating pivot
    for j = 1:450
        w = w0+0.006*j; %%frequency of oscillating pivot %%
        p = 2*pi/w; %%period of the matrix
        [t1,x1] = ode45(@(t1,x) h2(t1,x,A,w,l),[0 p],[0.2;0],options);  %%finds a solution that is the first colummn in the transition matrix. Solve for small initial angle so that our linear model holds
        [t2,x2] = ode45(@(t2,x) h2(t2,x,A,w,l),[0 p],[0;0.2],options);  %%finds a solution that is the second colummn in the transition matrix
        M = zeros(2);%%define the monodromy matrix
        M(1,1) = 5*x1(size(t1,1),1);
        M(2,1) = 5*x1(size(t1,1),2);
        M(1,2) = 5*x2(size(t2,1),1);
        M(2,2) = 5*x2(size(t2,1),2);
        gamma = 1/2*trace(M);
        if abs(gamma) <1 %%see report
            x = [x,1/(w*w)]; %%save this point, it has bounded solutions
            y = [y,A/l];
        end
        if abs(gamma) == 1 %%see report
            if M(1,1)-gamma == 0 && M(2,2)- gamma == 0 && M(1,2) == 0 && M(2,1) == 0
              x = [x,1/(w*w)]; %%save this point, it has bounded solutions
              y = [y,A/l];
            end
        end
        i
    end
end
toc
plot(x,y,'k.') %%plot the points for which all solutions are bounded
xlabel('1/w^2'), ylabel('A/l');

%%
clc;clf;clear
options = odeset('RelTol',1e-8,'AbsTol',1e-10); %%higher accuracy
[t2,x2] = ode45(@(t2,x) h2(t2,x,6,1,3),[0 15],[0.2;0],options); %%solves
subplot(1,2,1);
plot(x2(:,1),x2(:,2));
axis([-0.3 0.3 -0.4 0.4]);
xlabel('x1(t)');
ylabel('x2(t)');

[t3,x3] = ode45(@(t3,x) h2(t3,x,3,50,3),[0 0.5],[0.2;0],options);
subplot(1,2,2);
plot(x3(:,1),x3(:,2));
xlabel('x1(t)');
ylabel('x2(t)');






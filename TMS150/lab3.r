
surfun1.1 <- function(t)
{
  (1-pexp(t,1/2))*(1-pweibull(t,1/2,1)^3)
}
life1.1 = integrate(surfun,0,Inf)

time = seq(0,10,0.01)
dr1.1 = numDeriv::grad(function(t) -log(surfun1.1(t)),time) # installera paketet numderiv
plot(time,dr1.1,col="BLACK",type="l",yaxt='n')


########################
myfun1.2 <- function(t) ## gör detta snyggare
{
  (1-pweibull(t,1/2,1)^3)*dexp(t,1/2)
}
P = integrate(myfun1.2,0,Inf)
########################



surfun2.1W <- function(t)
{
  (1-pexp(t,1/2)^2)*(1-pweibull(t,1/2,1)^3)
}
life2.1W = integrate(myfun2.1,0,Inf)

par(new=TRUE)

dr2.1W = numderiv::grad(function(t) -log(surfun2.1W(t)),x)
plot(time,dr2.1W,col="RED",type="l",yaxt="n")



surfun2.1C <- function(t)
{
  (1-pweibull(t,1/2,1)^3)*(1-pgamma(t,2,1/2))
}
life2.1C = integrate(myfun2.1C,0,Inf)


par(new = TRUE)

dr2.1C = numDeriv::grad(function(t) -log(surfun2.1C(t)),x)
plot(time,dr2.1C,col="BLUE",type="l",yaxt='n')

################################

surfun2.2 <- function(t,rho)
{
  (1-pexp(t,rho))*(1-pweibull(t,1/2,1)^3)
}

################
paramIntC <- Vectorize(function(rho) ## Vectorize så att du kan stoppa in en vektor
{
  integral = integrate(function(t) surfun2.2(t,rho)-surfun2.1C(t),0,Inf)$value ## den returnerar mer an bara vardet
})

rhos = seq(0,0.5,0.01)
valuesC = c()

for (i in rhos)
  valuesC = append(valuesC,paramIntC(i))

plot(rhos,valuesC) # we see that root between 0.2 and 0.3
rootC = uniroot(paramIntC,c(0.2,0.3))$root ## far en lista med massa grejjer

#####
paramIntW <- Vectorize(function(rho)
{
  integral = integrate(function(t) surfun2.2(t,rho)-surfun2.1W(t),0,Inf)$value
})

valuesW = c()
for(i in rhos)
  valuesW = append(valuesW,paramIntW(i))


plot(rhos,valuesW) # root between 0.2 and 0.4
rootW = uniroot(paramIntW,c(0.2,0.4))$root





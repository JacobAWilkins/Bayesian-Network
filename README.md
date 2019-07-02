# Bayesian-Network
This program computes the probability of any combination of events given any other combination of events.
The Bayesian network it uses is described by bnet_graph.gif.
### Compile/Run
```
javac bnet.java
java bnet
```
### Rules
1. First, write 1-5 arguments: specify actions by their first letter and after each action write a "t" for true or a "f" for false.
2. Next, optionally, write the word "given" followed by 1-4 arguments with the same style.
3. Can only take a total of 1-6 arguments
### Examples
1. Type this to calculate the probability P(Alarm=false and Earthquake=true)
```
bnet Af Et
```
2. Type this to calculate the probability P(JohnCalls=true and Alarm=false | Burglary=true and Earthquake=false)
```
bnet Jt Af given Bt Ef
```
3. Type this to calculate the probability P(Burglary=true and Alarm=false and MaryCalls=false and JohnCalls=true and Earthquake=true)
```
bnet Bt Af Mf Jt Et
```

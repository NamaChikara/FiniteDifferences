# FiniteDifferences

This project was an opportunity to merge my introduction to Scala with my graduate research in Finite Difference methods for Partial Differential Equations. There are two packages implemented so far:
     
1. `com.ztbarry.finitedifferences` - 1D mesh implementations of Simpson's Rule and the Trapezoidal Rule. Contains the following:

    a. `Difference1D`, a trait that handles integral approximation and also approximates the order of convergence of a numerical method.
    
    b. `SimpsonsRule`, extends `Difference1D` by defining the numerical method to be [Simpson's Rule](https://en.wikipedia.org/wiki/Simpson%27s_rule).
    
    c. `TrapezoidalRule`, extends `Difference1D` by defining the numerical method to be the [Trapezoidal Rule](https://en.wikipedia.org/wiki/Trapezoidal_rule).
    
2. `com.ztbarry.nicefunctions` - 

     a. `Polynomial`, a class that constructs a polynomial given coefficient values.  Satisfies the requirements for evaluating a function's integral with the methods listed above.

I wrote a few blog posts to document my progress:

* [Importing a Class - Scala REPL](https://zackbarry.github.io/blog/2019/importing-classes-repl/)
* [Error Handling Methods](https://zackbarry.github.io/blog/2019/error-handling/)
* [SBT Project Structure](https://zackbarry.github.io/blog/2019/project-structure/)

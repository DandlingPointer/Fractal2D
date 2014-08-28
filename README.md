#Fractal2D

This is a simple javafx application, in which you can create fractals through Lua-Code. At the moment it's WIP

It features a basic Complex Library written in lua, for lua. Just make a new Complex Table with `Complex:new({r=<real>, i = <img>})`.
If you complex table is stored in a variable named `c`, then you can access the real part with `c.r` and the imaginary part with `c.i`.
The basic operators `+,-,*,/` and `^` work, so you can do stuff like this: `(c1 + c2) * c2` where c1 and c2 are Complex tables.

###TODOs

* Canvas resize
* ~~Accordion should never be collapsed [Stack Overflow Question regarding this](http://stackoverflow.com/questions/10403838/prevent-an-accordion-in-javafx-from-collapsing)~~
* Syntax highlighting when using interactive mode
* Draw area should expand with he window
* Zoom
* Better error handling, and user informing
* returning the values in lua doesn't work, use globals r,g,b instead!
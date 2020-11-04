# TSP-Approach

[JavaFX TSP Approaches] My approaches to the Travelling Salesman Problem

## Travelling Salesman Problem

The TSP is an NP-hard problem, discussed in theoretical compqsdf science.
> Given a list of cities and the distances between each pair of cities, what is the shortest possible route that visits each city and returns to the origin city? (Wikipedia, Travelling Salesman Problem)

## My approaches

The algorithms I wrote first sorts all the points by their distance relative to the origin and connects the three furthest points. It then loops through that ordered list and connects every point with the ends of the closest segment.

## Why it's not the solution yet

The algorithms work well with points where the solution has a rather round shape. It will connect all the points to make it continuous, but sometimes there are shorter paths. Segments often overlap, which would be fixed with an intersection check. I haven't added that yet here to keep the program as fast as possible in lineair time.

## Install

This program requires the Java JDK to be installed to run JavaFX applications. You can download it and build it in Eclipse.

## Usage

Run the program. Click the canvas to mark points. Click 'start' to see the algorithm do it's job. Click 'clear' to clear the points.

## Note

This is just a test. I might continue working on this further when I have some more spare time.
The thread sleeps for a second after every new connection to show the working of the algorithm. In real time, this takes less than a millisecond.

## Screenshots

[SCREENSHOT1](http://prntscr.com/t0c17r)
[SCREENSHOT2](http://prntscr.com/t0c1x8)
[SCREENSHOT3](http://prntscr.com/t0c2iy)

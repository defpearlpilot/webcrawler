# org.webcrawler

A basic web crawler that will traverse a website a URL and print out a 
site map.

Pre-requisites:

JDK 8 and Maven 3+ installed and configured.

Building:

To build, simply run in the checkout directory: mvn package.

Running:

To run, in the checkout directory, enter the command:

java -jar target/webcrawler-1.0-SNAPSHOT.jar << url >>

<< url >> is simply a valid url such as "http://google.com"

Thought process:

I wanted to use a mixture of OO and functional concepts in this 
exercise.  I didn't want to reimplement the html parsing so I reused
a library called Jsoup that has a "selector" type syntax for querying
tag elements.

Each of the various element types of concern are represented by
their own class.  The base class is Link which has the fundamental
traversal logic.  The name could be better like perhaps PageElement.
It's just been Murphy's law at my house for the past couple of weeks.

If I had more time, I'd redo the relationships between Domain and the
other classes that inherit from Link.  I'm not happy with how that
is at the moment.  


Some open questions:

How should #links be dealt with?  Should I have included them as childre
of the parent link?


Limitations to testing:

I had a difficult time finding sites that had largely nested structures
to test in the real world.  I do want to create a test website layout that 
would do that.  I would need to re-architect the solution a bit since
the main code crawls using Jsoup's connect method which won't do local
parsing.  This is what the tests use.  I would refactor to inject a function
for "connecting or parsing" the site so that remote and local page
traversal can be supported.




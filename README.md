# AsynchronousMultithreading
Example for https://stackoverflow.com/questions/46673542/paraller-exeuction-of-queries-java-by-multi-threads/46673727#46673727

So basically if you want to use this code now for asynchronous multithreading you need to do one thing first:
Edit your pom.xml and add the depencies i used. You could also use the binary file as a resource/library located at 
"out\artifacts\AsonychronousMultithreading_jar".

What you have to do now is simply creating a new instance of the "Schedeuler"-class!

private final Schedeuler schedeuler = new Schedeuler();

and then just start your tasks asynchronously like that:


//Schedeuler             Function to call 
schedeuler.runAsync(() -> someFunction());


If you want to delay it:

//Schedeuler             Function to call    Delay Timeunit
schedeuler.run(() -> someFunction(), 1L, TimeUnit.MINUTES);


And finally if you want it to be repeating:


//Schedeuler             Function to call    Delay RepeatingDelay Timeunit
schedeuler.runRepeating(() -> someFunction(), 0L, 1L, TimeUnit.MINUTES);


(Note: Java 8 Syntax [Lambdas])

Making Your First Program
=========================

Now that you know a bit about how to write Clojure code, let's look at how to create a standalone application.

In order to do that, you'll first create a *project*. You'll learn how to organize your project with *namespaces*. You'll also learn how to specify your project's *dependencies*. Finally, you'll learn how to *build* your project to create the standalone application.

## Create a Project

Up until now you've been experimenting in a REPL. Unfortunately, all the work you do in a REPL is lost when you close the REPL. You can think of a project as a permanent home for your code. You'll be using a tool called "Leiningen" to help you create and manage your project. To create a new project, run this command:

```clojure
lein new quil drawing
```

This should create a directory structure that looks like this:

```
| LICENSE
| README.md
| project.clj
| src
| | drawing 
| | | core.clj
```

There's nothing inherently special or Clojure-y about this project skeleton. It's just a convention used by Leiningen. You'll be using Leiningen to build and run Clojure apps, and Leiningen expects your app to be laid out this way. Here's the function of each part of the skeleton:

- `project.clj` is a configuration file for Leiningen. It helps
  Leiningen answer questions like, "What dependencies does this
  project have?" and "When this Clojure program runs, what function
  should get executed first?"
- `src/drawing/core.clj` is where the Clojure code goes

This uses a Clojure library, Quil, that creates drawings called sketches.

Now let's go ahead and actually run the Quil sketch. Open up Light Table and do File - Open Folder - find the drawing folder and click Upload

Press `Ctrl + Shift + Enter` (or `Cmd + Shift + Enter`) to evaluate the file.

## Modify Project

Let's create another Quil sketch. In Light Table, do File - New File. Do File - Save File As - Enter lines.clj as the name - and select the directory - drawing/src/drawing - then click Save. 

## Organization

As your programs get more complex, you'll need to organize them. You organize your Clojure code by placing related functions and data in separate files. Clojure expects each file to correspond to a *namespace*, so you must *declare* a namespace at the top of each file.

Until now, you haven't really had to care about namespaces. Namespaces allow you to define new functions and data structures without worrying about whether the name you'd like is already taken. For example, you could create a function named `println` within the custom namespace `my-special-namespace`, and it would not interfere with Clojure's built-in `println` function. You can use the *fully-qualified name* `my-special-namespace/println` to distinguish your function from the built-in `println`.

Create a namespace in the file `src/drawing/lines.clj`. Open it, and type the following:

```clojure
(ns drawing.lines)
```

This line establishes that everything you define in this file will be stored within the `drawing.lines` namespace.


## Dependencies

The final part of working with projects is managing their *dependencies*. Dependencies are just code libraries that others have written which you can incorporate in your own project.

To add a dependency, open `project.clj`. You should see a section which reads

```clj
:dependencies [[org.clojure/clojure "1.6.0"]
               [quil "2.2.2"]])
```

This is where our dependencies are listed. All the dependencies we need for this project are already included.

In order to use these libraries, we have to _require_ them in our own project. See `src/drawing/lines.clj`, for example:

```clojure
(ns drawing.lines
   (:require [quil.core :as q]))
```

This gives us access to the library we will need to make our project.

There are a couple of things going on here. First, the `:require` in `ns` tells Clojure to load other namespaces. The `:as` part of `:require` creates an *alias* for a namespace, letting you refer to its definitions without having to type out the entire namespace. For example, you can use `q/fill` instead of `quil.core/fill`.

## Your first real program

### Drawing with quil

TODO: fill in the very basics of quil needed for lines.

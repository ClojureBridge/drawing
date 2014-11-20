I want to create something cool using Quil!
===========================================

    This is a story of Clara who attended ClojureBridge workshop recently.
    At workshop, she learned what is Clojure and how to write Clojure code.
    That really impressed her, "what a functional!"
    Also, Clara met an interesting drawing tool, Quil,  written by Clojure.
    When Clara learned how to use Quil,
    she thought, "I want to create something cool using Quil!"
    Here's how Clara developed her own Quil application.

## Step 1. Snowflake on the blue background

Clara thought where to start.
Then, a small light turned on in her mind, "a white snowflake on the blue background loks nice."
What she wants to know is how to make background blue and put a snowflake on it.
Clara already learned how to find the way:

1. go to API document website
2. google it

Clara went to Quil API web site, [http://quil.info/api](http://quil.info.api),
and found [Loading and Displaying](http://quil.info/api/image/loading-and-displaying) section and [background-image](http://quil.info/api/color/setting#background-image) function.
Then, she googled and found a stackoverflow question,
[Load/display image in clojure with quil](http://stackoverflow.com/questions/18714941/load-display-image-in-clojure-with-quil).

Those told her enough to accomplish the step 1.


At ClojureBridge workshop, Clara went though the Quil app tutorial,
[Making Your First Program with Quil](https://github.com/ClojureBridge/drawing/blob/master/curriculum/first-program.md), so she already had `drawing` Clojure project.
She decided to use the same project for her own app.

### create a new source file

Clara added a new file under `src/drawing` directory with the name `practice.clj`.
Her directory structure looks like below:

```
| LICENSE
| README.md
| project.clj
| src
| | drawing 
| | | core.clj
| | | lines.clj
| | | <b>practice.clj</b>
```

### make the source code clojure-ish

Mostly, Clojure source code has a namspace declaration, so Clara copy-pasted `ns` form from `lines.clj` file. But, she changed the name from `drawing.lines` to `drawing.practice` because her file name got the name `practice`.
At this moment, `practice.clj` looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]))
```

### add basic Quil code

The basic Quil code has `setup`, `draw` functions and `defsketch` macro which defines the app. Next, Clara added those basic stuffs in her `practice.clj`.
Then, the code looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]))

(defn setup [])

(defn draw [])

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :draw draw
  )
```

### load snowflake and background images

Looking at Quil API and stackoverflow question, Clara learned where to put those image files was important.
She created a new directory under top `drawing` and put two images there.
The directory structure became like in below:

```
| LICENSE
| README.md
| project.clj
| src
| | drawing 
| | | core.clj
| | | lines.clj
| | | practice.clj
| images
| | blue_background.png
| | white_flake.png
```

Well, images were ready, it was time to code using Quil API.
Clara added a few lines of code to `practice.clj` to load and draw two images.
She was careful to write image filenames since it should reflect actual directory structure.
At this moment, the code looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]))

(def flake (ref nil))        ;; reference to snowflake image
(def background (ref nil))   ;; reference to blue background image

(defn setup []
  ;; loading two images
  (dosync
   (ref-set flake (q/load-image "images/white_flake.png"))
   (ref-set background (q/load-image "images/blue_background.png"))))

(defn draw []
  ;; drawing blue background and a snowflake on it
  (q/background-image @background)
  (q/image @flake 400 10))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :draw draw)
```

When Clara ran this code, she saw this image:

![step 1 screenshot]("images/step-1.png "Step 1"

Woohoo! She made it!


## Step 2. make nowflake falling down

Clara was satisfied with the image, white snowflake on the blue background.
However, that was boring.
Next, she wanted to move the snowflake like falling down.
This needed further Quil API study and googling.
Moving some pices in the image is called "animation".
She learned Quil had two choices, a legacy, simple way and new style using framework.
Her choice was the new framework style since coding looked simple.


### add framework

Clara read the document [Functional mode (fun mode)](https://github.com/quil/quil/wiki/Functional-mode-(fun-mode)), and added two lines to her `practice.clj`.

1. `[quil.middleware :as m]` in `ns` form
2. `:middleware [m/fun-mode]` in `q/defsketch` form


At this moment, the `practice.clj` looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def flake (ref nil))        ;; reference to snowflake image
(def background (ref nil))   ;; reference to blue background image

(defn setup []
  ;; loading two images
  (dosync
   (ref-set flake (q/load-image "images/white_flake.png"))
   (ref-set background (q/load-image "images/blue_background.png"))))

(defn draw []
  ;; drawing blue background and a snowflake on it
  (q/background-image @background)
  (q/image @flake 400 10))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :draw draw
  :middleware [m/fun-mode])
```

### add state update

Well, Clara thought,
"what does 'moving the snowflake like falling down' mean in terms of prorgamming?"

To draw snowflake, she used Quil's `image` function,
described in the API, [image](http://quil.info/api/image/loading-and-displaying#image).
The x and y parameters, 400 and 10, from the upper-left corner
 was the position she set to draw the snowflake.
To make it falling down, the y parameter should be increased as time goes by.
In terms of programming, 'moving the snowflake like falling down' means:

1. set the initial state - (x, y) = (400, 10)
2. draw background, then snowflake
3. update the state - increase y parameter, say (x, y) = (400, 11)
4. draw background, then snowflake
5. repeat 2 and 3, increasing y parameter

In her application, the "changing state" is the y parameter only.
How she could increment y value by one?
Yes, Clojure has `inc` function. This is the one she used.

Here's what she did:

1. add a new function `update` which will increment y parameter by one
2. change the `draw` function to take argument `state`
3. change `q/image` function's parameter to see the `state`
4. add `update` function in `q/defsketch` form


At this moment, the `practice.clj` looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def flake (ref nil))        ;; reference to snowflake image
(def background (ref nil))   ;; reference to blue background image

(defn setup []
  ;; loading two images
  (dosync
   (ref-set flake (q/load-image "images/white_flake.png"))
   (ref-set background (q/load-image "images/blue_background.png")))
  (q/smooth)
  (q/frame-rate 60)
  10)

(defn update [state]
  ;; updating y paraemter by one
  (inc state))

(defn draw [state]
  ;; drawing blue background and a snowflake on it
  (q/background-image @background)
  (q/image @flake 400 state))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
```

When Clara ran this code, hey, she saw the snowflake was faliing down.


## Step 3. make the nowflake keeps falling down from top to bottom

Clara got a nice Quil app. But, once the snowfake went down beyond the bottom line,
that's it. Just a blue background stayed there.
So, she wanted it to repeat again and again.
In another word, if the snowfake reaches the bottom, it should come back to the top.
Then, it falls down again.

In terms of programming, what does that mean?
If the `state`(y parameter) is greater than the hight of the image,
`state` will be back to 0.
Otherwise, the `state` will be incremented by one.

OK, Clara learned `if`, which is used for flow control at ClojureBridge workshop, [Flow Control](https://github.com/ClojureBridge/curriculum/blob/master/outline/flow_control.md).
So, she used `if` to make it back to the top.

At this momment, the `practice.clj` looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def flake (ref nil))        ;; reference to snowflake image
(def background (ref nil))   ;; reference to blue background image

(defn setup []
  ;; loading two images
  (dosync
   (ref-set flake (q/load-image "images/white_flake.png"))
   (ref-set background (q/load-image "images/blue_background.png")))
  (q/smooth)
  (q/frame-rate 60)
  10)

(defn update [state]
  (if (>= state (q/height))  ;; state is greater than or equal to image height?
    0                        ;; true - get it back to the 0 (top)
    (inc state)              ;; false - increment y paraemter by one
    ))

(defn draw [state]
  ;; drawing blue background and a snowflake on it
  (q/background-image @background)
  (q/image @flake 400 state))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
```

Clara saw the snowfake appeared from the top after it went down blow the bottom line.


## Step 4. make more snowflakes keep falling down from top to bottom

Looking at the snowflake falling down many times is nice.
But, Clara thought she wanted to see more snowflakes falling down,
one or more on the left half, also one or more on the right half.

Again, she needed to think by the words of programming.
It would be "draw mutiple images with the different x parameters."
The easiest way is copy pasting `(q/image @flake 400 state)` mutiple times with the different x parameters.
For example:

```clojure
(q/image @flake 150 state)
(q/image @flake 400 state)
(q/image @flake 650 state)
```

But, for Clara, this looked not nice since she learned a lot about Clojure and wanted to use what she knew.

Well, firstly, she thought about how to keep multiple x parameters.
She remembered there was a `vector` in [Data Structure](https://github.com/ClojureBridge/curriculum/blob/master/outline/data_structures.md) section,
which looked a good fit in this case.

Here's what she did for multiple snowfakes:

1. add vector which has multiple x parameters with `def`
2. draw snowflakes as many times as the number of x-params using `doseq`.

* See, [doseq](http://clojuredocs.org/clojure.core/doseq) for more info.

At this moment, the `practice.clj` looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def flake (ref nil))        ;; reference to snowflake image
(def background (ref nil))   ;; reference to blue background image
(def x-params [100 400 700]) ;; x parameters for three snowflakes

(defn setup []
  ;; loading two images
  (dosync
   (ref-set flake (q/load-image "images/white_flake.png"))
   (ref-set background (q/load-image "images/blue_background.png")))
  (q/smooth)
  (q/frame-rate 60)
  10)

(defn update [state]
  (if (>= state (q/height)) ;; state is greater than or equal to image height?
    0                       ;; true - get it back to the 0 (top)
    (inc state)             ;; false - increment y paraemter by one
    ))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image @background)
  (doseq [x x-params]
    (q/image @flake x state)))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
```

Yeah, Clara saw three snowfalkes kept falling down.


## Step 5. make snowflakes keep falling down from differently

So far, so good.
But, Clara felt something not quite right.
All three snowflakes fell down simultaneously, which would not look like natural.
So, she wanted to make them falling down differently, more natural way.

Using the words of programming,
the problem here is all three snowflakes share the same y parameter.

Obviously, adding multiple y values would solve the problem.
The question is how?

As she used `vector` for x parameters, the `vector` is a good data structure for y parameters as well.
But, not just the height, Clara wanted to change the speed of falling down in each snowflake.
So, she looked at the ClojureBridge curriculum,
[More Data Structures](https://github.com/ClojureBridge/curriculum/blob/master/outline/data_structures2.md), and found `Maps` section.
Then, she changed the `state` form a single value to a vector of 3 maps.

```clojure
[{:y 10 :speed 1} {:y 300 :speed 5} {:y 100 :speed 3}]
```

It was a nice data structure.
However, her `update` function was no more that simple.
She needed to update all y values in the three maps.
Ok, she already learned how to get and update the values in the map.
There was a `assoc` function, which will change the value in a map.
[More Functions](https://github.com/ClojureBridge/curriculum/blob/master/outline/functions2.md).
(See [http://clojuredocs.org/clojure.core/assoc](http://clojuredocs.org/clojure.core/assoc) for more info)

To iterate over the all element in the vector, Clojure has a few functions.
But, be careful.
`update` function must return updated state.
So, she used `for` for updating the state like this:

```clojure
(for [p state]
  (if (>= (:y p) (q/height))
    (assoc p :y 0)
    (assoc p :y (+ (:y p) (:speed p))))
  )
```

Another challenge was a `draw` function.
Clara found a couple of ways to repeat in Clojure.
Among them, she chose `dotimes` and `nth` to repeatedly draw images.
The `nth` is the one in 
[More Functions](https://github.com/ClojureBridge/curriculum/blob/master/outline/functions2.md)

In this case, she knew there were exactly 3 snowflakes, so she changed the code to draw 3 snowflakes as in blow:

```clojure
(dotimes [n 3]
    (q/image @flake (nth x-params n) (:y (nth state n))))
```

At this moment, her entire `practice.clj` looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def flake (ref nil))        ;; reference to snowflake image
(def background (ref nil))   ;; reference to blue background image
(def x-params [100 400 700]) ;; x parameters for three snowflakes

(defn setup []
  ;; loading two images
  (dosync
   (ref-set flake (q/load-image "images/white_flake.png"))
   (ref-set background (q/load-image "images/blue_background.png")))
  (q/smooth)
  (q/frame-rate 60)
  [{:y 10 :speed 1} {:y 300 :speed 5} {:y 100 :speed 3}])

(defn update [state]
  (for [p state]
    (if (>= (:y p) (q/height)) ;; y is greater than or equal to image height?
      (assoc p :y 0)                      ;; true - get it back to the 0 (top)
      (assoc p :y (+ (:y p) (:speed p)))) ;; false - increment y paraemter by one
    ))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image @background)
  (dotimes [n 3]
    (q/image @flake (nth x-params n) (:y (nth state n)))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
```

When she ran the code above, three snowflakes kept falling down in different speeds.
It looked more natural.


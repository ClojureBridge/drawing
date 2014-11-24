I want to create something cool by Quil!
===========================================

    This is a story of Clara who attended ClojureBridge workshop recently.
    At workshop, she learned what is Clojure and how to write Clojure code.
    That really impressed her, "what a functional!"
    Also, Clara met an interesting drawing tool, Quil, which is written by Clojure.
    When Clara learned how to use Quil, she thought, "I want to create something cool by Quil!"
    Here's how Clara developed her own Quil application.

## Step 1. Snowflake on the blue background

Clara thought where to start.
Then, a small light turned on in her mind, "a white snowflake on the blue background looks nice."
What she wanted to know was how to make background blue and put a snowflake on it.
Clara already learned how to find the way. It was:

1. go to API document website
2. google it

So, Clara went to Quil API web site, [http://quil.info/api](http://quil.info.api),
and found [Loading and Displaying](http://quil.info/api/image/loading-and-displaying) section. also, [background-image](http://quil.info/api/color/setting#background-image) fu
nction.
Then, she googled and found a stackoverflow question,
[Load/display image in clojure with quil](http://stackoverflow.com/questions/18714941/load-display-image-in-clojure-with-quil), which looked what she needed.

Those told her enough to accomplish the step 1.


At ClojureBridge workshop, Clara went though the Quil app tutorial,
[Making Your First Program with Quil](https://github.com/ClojureBridge/drawing/blob/master/curriculum/first-program.md), so she already had `drawing` Clojure project.
She decided to use the same project for her own app.

### create a new source file

Clara added a new file under `src/drawing` directory with the name `practice.clj`.
At this moment, her directory structure looks like below:

```
| LICENSE
| README.md
| project.clj
| src
| | drawing 
| | | core.clj
| | | lines.clj
| | | practice.clj
```

### make the source code clojure-ish

Mostly, Clojure source code has a namspace declaration, so Clara copy-pasted `ns` form from `lines.clj` file. But, she changed the name from `drawing.lines` to `drawing.practice` because her file name got the name `practice`.
At this moment, `practice.clj` looks like this:

```clojure
(ns drawing.practice
  (:require [quil.core :as q]))
```

### add basic Quil code

The basic Quil code has `setup`, `draw` functions and `defsketch` macro which defines the app. 
Following the Quil rule, Clara added those basic stuffs in her `practice.clj`.
At this moment, `practice.clj` looks like this:

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
She created a new directory, `images`, under the top `drawing` directory and put two images there.
At this moment, her directory structure looks like below:

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

Well, since images were ready, it was time to code using Quil API.
Clara added a few lines of code to `setup` and `draw` functions in `practice.clj` to load and draw two images.
She was careful to write image filenames because it should reflect actual directory structure.
At this moment, `practice.clj` looks like this:

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

![step 1 screenshot](images/step-1.png)

Woohoo! She made it!


## Step 2. make snowflake falling down

Clara was satisfied with the image, the white snowflake on the blue background.
However, that was boring.
Next, she wanted to move the snowflake like falling down.
This needed further Quil API study and googling.
Moving some pieces in the image is called "animation".
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
The x and y parameters were 400 and 10 from the upper-left corner,
which was the position she set to draw the snowflake.
To make it falling down, the y parameter should be increased as time goes by.
In terms of programming, 'moving the snowflake like falling down' means:

1. set the initial state, for example, (x, y) = (400, 10)
2. draw background first, then snowflake
3. update the state - increase y parameter, for example (x, y) = (400, 11)
4. again draw background first, then snowflake
5. repeat 2 and 3, increasing y parameter

In her application, the "changing state" is the y parameter only.
How she could increment y value by one?
Yes, Clojure has `inc` function. This was the one she used.

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


## Step 3. make the snowflake keeps falling down from top to bottom

Clara got a nice Quil app. But, once the snowflake went down beyond the bottom line,
that's it. Just a blue background stayed there.
So, she wanted it to repeat again and again.
In another word, if the snowflake reaches the bottom, it should come back to the top.
Then, it falls down again.

In terms of programming, what does that mean?
If the `state`(y parameter) is greater than the hight of the image,
`state` will be back to 0.
Otherwise, the `state` will be incremented by one.

OK, Clara learned `if`, which is used for flow control at ClojureBridge workshop, [Flow Control](https://github.com/ClojureBridge/curriculum/blob/master/outline/flow_control.md).
So, she used `if` to make it back to the top in the update function.

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

Clara saw the snowflake appeared from the top after it went down below the bottom line.


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

Here's what she did for adding more snowflakes:

1. add vector which has multiple x parameters by `def`.
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

Yeah, Clara saw three snowflakes kept falling down.


## Step 5. make snowflakes keep falling down differently

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
There was a `assoc` function, which will change the value in a map, which was appeared in
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


## Step 6. do some "refactoring"

Clara looked her `practice.clj` thinking her code got longer.
When she looked at her code from top to bottom again,
she thought "`x-params` may be one of the state."
So, she changed her `state` to have x parameter also, like
`[{:x 100 :y 10 :speed 1} {:x 400 :y 300 :speed 5} {:x 700 :y 100 :speed 3}]`.
She found this new format was easy to maintain the state of each snowflakes.

Now, Clara also needed to change drawing snowflakes part.
At first, she wrote like this:

```clojure
  (dotimes [n 3]
    (q/image @flake (:x (nth state n)) (:y (nth state n))))
```

But, the exactly the same thing, `(nth state n)`, appeared twice.
"Is there anything to avoid repetition?" she thought and went to
[Clojure Cheat Sheet](http://clojure.org/cheatsheet) for `dotimes` syntax.
She clicked on `dotimes` in the Macros, Loop section and saw the syntax described in [dotimes](http://clojuredocs.org/clojure.core/dotimes).
It says `(dotimes bindings & body)`.

Clara remembered she learned `let` in [Flow Control](https://github.com/ClojureBridge/curriculum/blob/master/outline/flow_control.md),
which was the "bindings". So, she rewrote using `let` like this:

```clojure
(dotimes [n 3]
  (let [snowflake (nth state n)]
    (q/image @flake (:x snowflake) (:y snowflake))))
```

It looked nice and Clojure-ish.
At this moment, her entire `practice.clj` looks like this:

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
  [{:x 100 :y 10 :speed 1} {:x 400 :y 300 :speed 5} {:x 700 :y 100 :speed 3}])

(defn update [state]
  (for [p state]
    (if (>= (:y p) (q/height)) ;; y is greater than or equal to image height?
      (assoc p :y 0)                      ;; true - get it back to the 0 (top)
      (assoc p :y (+ (:y p) (:speed p)))) ;; false - add a value of speed
    ))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image @background)
  (dotimes [n 3]
    (let [snowflake (nth state n)]
      (q/image @flake (:x snowflake) (:y snowflake)))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
```

She saw the exactly the same result as the previous code, but her code looked nicer.
This sort of work is often called "refatoring".


## Step 7. make snowflakes swing as they fall down

Clara was getting familiar with Clojure coding.
Her Quil app was getting much better as well.
But, looking at snowflakes falling down, she thought she could swing them left adn right as they fall down.
Right now, all snowflakes were falling down straight forward.

In the words of programming,
x parameter should both increase and descrease when the value is updated.
This means `update` function should update x parameters as well as y parameters.

To realize this feature, she changed the initial state like this:

```clojure
[{:x 100 :swing 10 :y 10 :speed 8}
 {:x 400 :swing 5 :y 300 :speed 11}
 {:x 700 :swing 8 :y 100 :speed 9}]
```
The map got a new `:swing` key, which holds a range of left and right from a current position.
This means the updated x parameter will have between current x parameter + swing and current x parameter - swing. For example, the first snowflake's next x parameter will be between 100 + 10 and 100 - 10.

To update x parameter by a random value between some range, she needed to do something not just using existing clojure functions.
She found [`rand-int`](http://clojuredocs.org/clojure.core/rand-int) from [Clojure Cheat Sheet](http://clojure.org/cheatsheet), but it only returned between 0 and specified value. Googling led her to this clojure code
(from [https://github.com/sjl/roul/blob/master/src/roul/random.clj](https://github.com/sjl/roul/blob/master/src/roul/random.clj):

```clojure
(defn rand-int
  "Return a random int between start (inclusive) and end (exclusive).
  start defaults to 0
  "
  ([end] (clojure.core/rand-int end))
  ([start end] (+ start (clojure.core/rand-int (- end start)))))
```

This was the random generation function what she wanted. But, she wanted a bit more.
When the value goes to less than 0, it should take the value close to the image width, so that the snowflake will appear from the right. Likewise, the value goes more than the image width, it should have 0 so that the snowflake will appear from the left.
She couldn't use `if` anymore since `if` takes only one predicate (comparison).
Instead of `if`, she used `cond` and wrote `update-x` funcion.

```clojure
(defn update-x
  [x swing]
  (let [start (- x swing)
        end (+ x swing)
        new-x (+ start (rand-int (- end start)))]
    (cond
     (> 0 new-x) (q/width)
     (< (q/width) new-x) 0
     :else new-x )))
```

This `update-x` function hinted her she could refactor update function and write `update-y` function. The below is the `update-y` function.

```clojure
(defn update-y
  [y speed]
  (if (>= y (q/height))
    0
    (+ y speed)))
```

Lastly, she rewrote `update` function.
She could still use `assoc`, but it will be like this:

```
(assoc (assoc p :y (update-y (:y p) (:speed p))) :x (update-x (:x p) (:swing p)))
```

She remembered that there was another function for map. It was `merge` appeared in [More Functions](https://github.com/ClojureBridge/curriculum/blob/master/outline/functions2.md).
Using `merge`, her `update` function turned to like this:

```clojure
(defn update [state]
  (for [p state]
    (merge p {:x (update-x (:x p) (:swing p)) :y (update-y (:y p) (:speed p))})))
```

At this momenet, her entire `practice.clj` looks like this:

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
  (q/frame-rate 30)
  [{:x 100 :swing 10 :y 10 :speed 8}
   {:x 400 :swing 5 :y 300 :speed 11}
   {:x 700 :swing 8 :y 100 :speed 9}])

(defn update-x
  [x swing]
  (let [start (- x swing)
        end (+ x swing)
        new-x (+ start (rand-int (- end start)))]
    (cond
     (> 0 new-x) (q/width)
     (< (q/width) new-x) 0
     :else new-x )))

(defn update-y
  [y speed]
  (if (>= y (q/height)) ;; y is greater than or equal to image height?
    0                   ;; true - get it back to the 0 (top)
    (+ y speed)))       ;; false - add a value of speed

(defn update [state]
  (for [p state]
    (merge p {:x (update-x (:x p) (:swing p)) :y (update-y (:y p) (:speed p))})))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image @background)
  (dotimes [n 3]
    (let [snowflake (nth state n)]
      (q/image @flake (:x snowflake) (:y snowflake)))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :middleware [m/fun-mode])
```

(frame-rate has been changed to 30)

When Clara ran this code, she saw snowflakes were falling down swinging left and right randomly.


Still there were a couple of problems as well as rooms for refactoring,
Clara was satified with her app;
however, she started thinking her next more advanced app in Clojure.


The end.
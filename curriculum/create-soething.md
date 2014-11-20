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


## Step 2. Snowflake falls down

Clara was satisfied with the image, white snowflake on the blue background.
However, that was boring.
Next, she wanted to move the snowflake like falling down.
This needed further Quil API study and googling.
Moving some pices in the image is called "animation".
She learned Quil had two choces, a legacy, simple way and new style using framework.
Her choice was the new framework style since coding looked simple.


### add framework

Clara read the document [Functional mode (fun mode)](https://github.com/quil/quil/wiki/Functional-mode-(fun-mode)), and added two lines to her `practice.clj`.

1. `[quil.middleware :as m]` in `ns` form
2. `:middleware [m/fun-mode]` in `q/defsketch` form


At this moment, the code looks like this:

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

In her application, the changing state is a y parameter.
By the way, how she could increment y value by one?
Yes, Clojure has `inc` function. This is the one she used.

Here's what she did:

1. add a new function `update` which will increment y parameter by one
2. change the `draw` function to take argument `state`
3. change `q/image` function's parameter to see the `state`
4. add `update` function in `q/defsketch` form


At this moment, the code looks like this:

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


## Step 3. Snowflake keeps falling down from top to bottom

Clara got a nice Quil app. But, once snowfake goes down beyond the bottom line,
that's it. Just a blue background stays there.
So, she wanted it to repeat again and again.
In another word, if snowfake reaches the bottom, it should come back to the top.
Then, it can fall down.

In terms of programming, what does that mean?
If the `state`(y parameter) is greater than the hight of the image,
`state` will be back to 0.
Otherwise, the `state` will be increased by one.

OK, Clara learned `if`, which is used for flow control at ClojureBridge workshop, [Flow Control](https://github.com/ClojureBridge/curriculum/blob/master/outline/flow_control.md).
So, she used `if` to realize that feature.

At this momment, the code looks like this:

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
  (if (>= state (q/height))
    0           ;; get it back to the 0 (top)
    (inc state) ;; updating y paraemter by one
    )
  )

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


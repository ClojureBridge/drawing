(ns drawing.lines
  (:require [quil.core :as q]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to RGB
  (q/color-mode :rgb)
  ; Set color of line to Red 
  (q/stroke 255 0 0))

(defn mouse-color [x y]
  "Calculate a color based on two values" 

  ;TODO: Take a look at B (of RGB). Right now it is just 0.
  [(mod x 255)  (mod y 255) 0])

(defn draw []

  ; Throughout the draw function, get the position of the 
  ; mouse with mouse-x and mouse-y

  ; Set the line color based on mouse position
  (apply q/stroke  (mouse-color (q/mouse-x) (q/mouse-y))) 

  ; Draw a line from the origin to the position of the mouse
  (q/line 0 0 (q/mouse-x) (q/mouse-y))
  ; Draw a line from 200 pixels right from the top left 
  ; to the position of the mouse
  (q/line 200 0 (q/mouse-x) (q/mouse-y))
  ; Draw a line from 200 pixels down from the top left 
  ; to the position of the mouse
  (q/line 0 200 (q/mouse-x) (q/mouse-y))
  ; Draw a line from 200 pixels right and down from the top left 
  ; to the position of the mouse
  (q/line 200 200 (q/mouse-x) (q/mouse-y)))

(q/defsketch hello-lines
  ; Set the title of the sketch
  :title "You can see lines"
  ; Set the size of the sketch
  :size [500 500]
  ; setup function is called setup
  :setup setup
  ; draw function is called draw
  :draw draw
  :features [:keep-on-top])

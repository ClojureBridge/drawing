(ns circles.lines
  (:require [quil.core :as q]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:color 0
   :angle 0})

(defn draw []
  (q/line 0 0 (q/mouse-x) (q/mouse-y))
  (q/line 200 0 (q/mouse-x) (q/mouse-y))
  (q/line 0 200 (q/mouse-x) (q/mouse-y))
  (q/line 200 200 (q/mouse-x) (q/mouse-y))
)

(q/defsketch hello-lines
  :title "You can see lines"
  :size [500 500]
  :setup setup
  :draw draw
  )

(ns drawing.practice
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ;; loading two images
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")})

(defn draw [{flake :flake background :background}]
  ;; drawing blue background and a snowflake on it
  (q/background-image background)
  (q/image flake 200 10))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [500 500]
  :setup setup
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])

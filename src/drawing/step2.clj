(ns drawing.step2
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ;; loading two images
  (q/smooth)
  (q/frame-rate 60)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :vertical-pos 10})

(defn update [state]
  ;; updating y paraemter by one
  (update-in state [:vertical-pos] inc))

(defn draw [state]
  ;; drawing blue background and a snowflake on it
  (q/background-image (:background state))
  (q/image (:flake state) 400 (:vertical-pos state)))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])

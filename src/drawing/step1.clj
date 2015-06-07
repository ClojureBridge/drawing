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
  (q/image @flake 400 10)
  )

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :draw draw
  :features [:keep-on-top])

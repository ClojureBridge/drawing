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

(ns drawing.step5
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def x-params [10 200 390]) ;; x parameters for three snowflakes
(def speeds [1 4 2])

(defn setup []
  ;; loading two images
  (q/smooth)
  (q/frame-rate 60)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :y-params [10 150 50]})

(defn update-y
  [y speed]
  (if (>= y (q/height))  ;; p is greater than or equal to image height?
    0                    ;; true - get it back to the 0 (top)
    (+ y speed)))        ;; false - add y value and speed

(defn update [state]
  (let [y-params (:y-params state)
        updated  (map #(update-y %1 %2) y-params speeds)]
    (assoc state :y-params updated)))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image (:background state))
  (let [y-params (:y-params state)]
    (dotimes [n 3]
      (q/image (:flake state) (nth x-params n) (nth y-params n)))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [500 500]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])

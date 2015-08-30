(ns drawing.step5
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def x-params [10 200 390]) ;; x parameters for three snowflakes

(defn setup []
  ;; loading two images
  (q/smooth)
  (q/frame-rate 60)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :y-params [{:y 10 :speed 1} {:y 150 :speed 4} {:y 50 :speed 2}]})

#_(defn update-y
  [m]
  (let [y (:y m)
        speed (:speed m)]
    (if (>= y (q/height))           ;; y is greater than or equal to image height?
      (assoc m :y 0)                ;; true - get it back to the 0 (top)
      (update-in m [:y] + speed)))) ;; false - add y value and speed

(defn update-y
  [{y :y speed :speed :as m}]
  (if (>= y (q/height))          ;; y is greater than or equal to image height?
    (assoc m :y 0)               ;; true - get it back to the 0 (top)
    (update-in m [:y] + speed))) ;; false - add y value and speed

(defn update [state]
  (let [y-params (:y-params state)]
    (assoc state :y-params (map update-y y-params))))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image (:background state))
  (let [y-params (:y-params state)]
    (dotimes [n 3]
      (q/image (:flake state) (nth x-params n) (:y (nth y-params n))))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [500 500]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])

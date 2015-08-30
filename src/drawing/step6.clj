(ns drawing.step6
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ;; loading two images
  (q/smooth)
  (q/frame-rate 60)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :params [{:x 10  :y 10  :speed 1}
            {:x 200 :y 150 :speed 4}
            {:x 390 :y 50  :speed 2}]})

(defn update-y
  [m]
  (let [y (:y m)
        speed (:speed m)]
    (if (>= y (q/height))           ;; y is greater than or equal to image height?
      (assoc m :y 0)                ;; true - get it back to the 0 (top)
      (update-in m [:y] + speed)))) ;; false - add y value and speed

(defn update [state]
  (let [params (:params state)]
    (assoc state :params (map update-y params))))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image (:background state))
  (let [params (:params state)]
    (dotimes [n 3]
      (let [param (nth params n)]
        (q/image (:flake state) (:x param) (:y param))))))

#_(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image (:background state))
  (let [params (:params state)]
    (dotimes [n 3]
      (q/image (:flake state) (:x (nth params n)) (:y (nth params n))))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [500 500]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])

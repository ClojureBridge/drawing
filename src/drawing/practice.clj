(ns drawing.practice
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ;; loading two images
  (q/smooth)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :params [{:x 10  :swing 1 :y 10  :speed 1}
            {:x 200 :swing 3 :y 100 :speed 4}
            {:x 390 :swing 2 :y 50  :speed 2}]})

(defn update-x
  [m]
  (let [x (:x m)
        swing (:swing m)
        y (:y m)]
    (cond
     (< x 0) (assoc m :x (q/width))                                  ;; too left
     (< x (q/width)) (update-in m [:x] + (* swing (q/sin (/ y 50)))) ;; within frame
     :else (assoc m :x 0))))                                         ;; too right

(defn update-y
  [m]
  (let [y (:y m)
        speed (:speed m)]
    (if (>= y (q/height))           ;; y is greater than or equal to image height?
      (assoc m :y 0)                ;; true - get it back to the 0 (top)
      (update-in m [:y] + speed)))) ;; false - add y value and speed

(defn update [state]
  (let [params  (:params state)
        params (map update-y params)
        params (map update-x params)]
    (assoc state :params params)))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image (:background state))
  (let [params (:params state)]
    (dotimes [n 3]
      (let [param (nth params n)]
        (q/image (:flake state) (:x param) (:y param))))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [500 500]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])

(ns drawing.step6
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ;; loading two images
  (q/smooth)
  (q/frame-rate 60)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :params [{:x 100 :y 10  :speed 1}
            {:x 400 :y 300 :speed 5}
            {:x 700 :y 100 :speed 3}]})

(defn update-y
  [y speed]
  (if (>= y (q/height))  ;; p is greater than or equal to image height?
    0                    ;; true - get it back to the 0 (top)
    (+ y speed)))        ;; false - add y value and speed

(defn update [state]
  (let [params  (:params state)
        updated (map #(update-in % [:y] update-y (:speed %)) params)]
    (assoc state :params updated)))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image (:background state))
  (let [snowflakes (:params state)]
    (dotimes [n 3]
      (let [snowflake (nth snowflakes n)]
        (q/image (:flake state) (:x snowflake) (:y snowflake))))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [1000 1000]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])

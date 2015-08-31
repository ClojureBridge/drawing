(ns drawing.step4
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def x-params [10 200 390]) ;; x parameters for three snowflakes

(defn setup []
  ;; loading two images
  (q/smooth)
  (q/frame-rate 60)
  {:flake (q/load-image "images/white_flake.png")
   :background (q/load-image "images/blue_background.png")
   :y-param 10})

(defn update [state]
  (if (>= (:y-param state) (q/height)) ;; y-param is greater than or equal to image height?
    (assoc state :y-param 0)         ;; true - get it back to the 0 (top)
    (update-in state [:y-param] inc) ;; false - updating y paraemter by one
    ))

(defn draw [state]
  ;; drawing blue background and mutiple snowflakes on it
  (q/background-image (:background state))
  (doseq [x x-params]
    (q/image (:flake state) x (:y-param state))))

(q/defsketch practice
  :title "Clara's Quil practice"
  :size [500 500]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])
